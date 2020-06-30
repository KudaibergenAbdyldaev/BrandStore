package com.example.brandstore.FavouriteFragment;

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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandstore.BasketFragment.BasketAdapter;
import com.example.brandstore.BasketFragment.BasketViewModel;
import com.example.brandstore.BasketRoomData.BasketData;
import com.example.brandstore.FavouriteRoomData.FavouriteDao;
import com.example.brandstore.FavouriteRoomData.FavouriteData;
import com.example.brandstore.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.marcoscg.dialogsheet.DialogSheet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    private BasketViewModel viewModel;
    private FavouriteViewModel favViewModel;
    private int count;
    private int amount;
    private int totalPrice;
    private FavouriteAdapter adapter;
    private RecyclerView recyclerView;
    public FavouriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favourite, container, false);
        this.setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerViewFav);
        adapter  = new FavouriteAdapter();
        recyclerView.setAdapter(adapter);

        favViewModel = new ViewModelProvider(requireActivity()).get(FavouriteViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);

        favViewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<FavouriteData>>() {
            @Override
            public void onChanged(@Nullable final List<FavouriteData> data) {
                adapter.submitList(data);
                assert data != null;
            }
        });
        adapterSetOnClickListener();
        return view;
    }

    private void adapterSetOnClickListener() {
        adapter.setOnItemClickListener(new FavouriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(final FavouriteData data) {

                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View view = inflater.inflate(R.layout.item_diaolog_fav, null);
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
                final CardView delete_card = view.findViewById(R.id.delete_favourite_item);
                totalPrice = Integer.parseInt(data.getPrice());
                amount = data.getAmount();
                count = count + data.getCount();

                txt_name.setText(data.getProduct_name());
                txt_count.setText(String.valueOf(data.getCount()));
                Picasso.get().load(data.getImageView()).fit().centerCrop().into(imageView);

                txt_amount.setText(String.valueOf(amount));
                txt_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = count+totalPrice;
                        amount++;
                        txt_count.setText(String.valueOf(count));
                        txt_amount.setText(String.valueOf(amount));
                    }
                });
                txt_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txt_amount.getText().toString().trim().equals("1")) {
                        } else {
                            amount--;
                            count = count-totalPrice;
                            txt_amount.setText(String.valueOf(amount));
                            txt_count.setText(String.valueOf(count));
                        }
                    }
                });
                add_basket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BasketData basketData = new BasketData(data.getProduct_name(),
                                data.getImageView(),
                                data.getPrice(),count,amount
                        );
                        viewModel.insert(basketData);
                        dialogSheet.dismiss();
                    }
                });
                dialogSheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        count = 0;
                        amount = data.getAmount();
                    }
                });
                dialogSheet.show();

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_all_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            final DialogSheet dialogSheet = new DialogSheet(getContext());
            dialogSheet.setSingleLineTitle(true)
                    .setTitle(R.string.delete)
                    .setMessage(R.string.delete_this)
                    .setColoredNavigationBar(true)
                    .setButtonsColorRes(R.color.color_black)
                    .setPositiveButton(android.R.string.ok, new DialogSheet.OnPositiveClickListener() {
                        @Override
                        public void onClick(View v) {
                            favViewModel.deleteAllNotes();
                            Snackbar snackbar = Snackbar
                                    .make(getView(), R.string.all_deleted, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogSheet.OnNegativeClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSheet.dismiss();
                        }
                    }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteAll(){
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
                        Snackbar snackbar = Snackbar
                                .make(getView(), R.string.all_deleted, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogSheet.OnNegativeClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSheet.dismiss();
                    }
                }).show();
    }
}
