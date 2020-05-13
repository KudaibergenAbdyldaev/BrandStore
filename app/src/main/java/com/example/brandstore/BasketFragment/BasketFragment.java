package com.example.brandstore.BasketFragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandstore.Data.BasketData;
import com.example.brandstore.HomeFragment.ProductData;
import com.example.brandstore.R;
import com.marcoscg.dialogsheet.DialogSheet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {


    private BasketViewModel viewModel;
    private CardView cardView;
    private int count;
    private int amount;


    // use to pass data between fragments
//    private SharedViewModel sharedViewModel;
    public BasketFragment() {
        //1 Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_basket, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBasket);
        cardView = view.findViewById(R.id.cardView_basket);
        cardView.setBackgroundResource(R.drawable.card_corner);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final BasketAdapter adapter = new BasketAdapter();
        recyclerView.setAdapter(adapter);
        this.setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<BasketData>>() {
            @Override
            public void onChanged(@Nullable List<BasketData> data) {
                adapter.submitList(data);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getDataAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BasketAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(final BasketData data) {
                LayoutInflater inflater = LayoutInflater.from(getContext());

                final View view = inflater.inflate(R.layout.item_dialog, null);
                final DialogSheet dialogSheet = new DialogSheet(getContext())
                        .setSingleLineTitle(true)
                        .setColoredNavigationBar(true)
                        .setView(view);
                final TextView txt_name = view.findViewById(R.id.txt_name);
                final TextView txt_count = view.findViewById(R.id.txt_count);
                final ImageView imageView = view.findViewById(R.id.img_product);
                final TextView txt_plus = view.findViewById(R.id.txt_plus);
                final TextView txt_minus = view.findViewById(R.id.txt_minus);
                final TextView txt_amount = view.findViewById(R.id.txt_amount);
                final Button add_basket = view.findViewById(R.id.add_basket);
                final int totalPrice = data.getCount();

                txt_name.setText(data.getProduct_name());
                txt_count.setText(Integer.toString(data.getCount()));
                Picasso.get().load(data.getImageView()).fit().centerCrop().into(imageView);

                count = count + totalPrice;
                amount = data.getAmount();
                txt_amount.setText(Integer.toString(amount));
                txt_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = count + totalPrice;
                        amount++;
                        txt_count.setText(Integer.toString(count));
                        txt_amount.setText(Integer.toString(amount));
                    }
                });
                txt_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (amount == 1) {
                        } else {
                            count = count - totalPrice;
                            amount--;
                            txt_amount.setText(Integer.toString(amount));
                            txt_count.setText(Integer.toString(count));

                        }
                    }
                });
                add_basket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = data.getId();
                        BasketData basketData = new BasketData(data.getProduct_name(), data.getImageView(), count,amount);
                        basketData.setId(id);
                        viewModel.update(basketData);
                        dialogSheet.dismiss();
                        //use to pass data between fragments
//                            sharedViewModel.setTextName(txt_name.getText());
//                            sharedViewModel.setTextImage(productData.getImageUrl());
//                            sharedViewModel.setTextAmount(txt_amount.getText());
//                            sharedViewModel.setTextCount(txt_count.getText());
                    }
                });
                dialogSheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        count = 0;
                    }
                });
                dialogSheet.show();

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_all_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                final DialogSheet dialogSheet = new DialogSheet(getContext());
                dialogSheet.setSingleLineTitle(true)
                        .setTitle(R.string.delete)
                        .setMessage(R.string.delete_this)
                        .setColoredNavigationBar(true)
                        .setButtonsColorRes(R.color.color_black)
                        .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewModel.deleteAllNotes();
                                Toast.makeText(getActivity(), "All files deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogSheet.OnNegativeClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogSheet.dismiss();
                            }
                        }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //use to pass data between fragments
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);

//        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
//        sharedViewModel.getTextName().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
//            @Override
//            public void onChanged(@Nullable CharSequence charSequence) {
//                text_view = charSequence.toString();
//            }
//        });
//        sharedViewModel.getTextImage().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
//            @Override
//            public void onChanged(@Nullable CharSequence charSequence) {
//                assert charSequence != null;
//                Picasso.get().load(charSequence.toString()).fit().centerCrop().into(imageView);
//            }
//        });
//        sharedViewModel.getTextAmount().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
//            @Override
//            public void onChanged(@Nullable CharSequence charSequence) {
//                text_amount.setText(charSequence);
//            }
//        });
//        sharedViewModel.getTextCount().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
//            @Override
//            public void onChanged(@Nullable CharSequence charSequence) {
//                text_count.setText(charSequence);
//            }
//        });
//    }
}
