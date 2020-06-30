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

import com.example.brandstore.BasketRoomData.BasketData;
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
public class BasketFragment extends Fragment{

    private BasketViewModel viewModel;
    private int count;
    private int amount;
    private int totalPrice;
    private BasketAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView empty_text;
    private TextView textView;
    private Button buttonNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_basket, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewBasket);
        final CardView cardView = view.findViewById(R.id.cardView_basket);
        imageView = view.findViewById(R.id.imageView3);
        empty_text = view.findViewById(R.id.emty_text_view);
        textView = view.findViewById(R.id.text_over_price);
        buttonNext = view.findViewById(R.id.button_next);
        cardView.setBackgroundResource(R.drawable.card_corner);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter  = new BasketAdapter();
        recyclerView.setAdapter(adapter);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy ");
        final String strDate = sdf.format(c.getTime());

        viewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<BasketData>>() {
            @Override
            public void onChanged(@Nullable final List<BasketData> data) {
                adapter.submitList(data);
                assert data != null;
                textView.setText(String.valueOf(grandTotal(data)));
                // if basket is empty, then say that it is empty
                // если корзина пуста, то скажи что она пуста
                if (grandTotal(data)==0){
                    imageView.setVisibility(View.VISIBLE);
                    empty_text.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    setHasOptionsMenu(false);
                }else {
                    imageView.setVisibility(View.GONE);
                    empty_text.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                    setHasOptionsMenu(true);
                }
                buttonNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        final View view = inflater.inflate(R.layout.fragment_order, null);
                        final DialogSheet dialogSheet = new DialogSheet(requireActivity());
                        dialogSheet.setView(view)
                                .setSingleLineTitle(true)
                                .setColoredNavigationBar(true)
                                .show();
                        final TextView txt_over_price = view.findViewById(R.id.text_over_price_order);
                        final EditText edt_address = view.findViewById(R.id.edt_address);
                        final EditText edt_phone = view.findViewById(R.id.edt_phone);
                        final Button btn_order = view.findViewById(R.id.btn_order);


                        btn_order.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(edt_address.getText().toString())
                                        || TextUtils.isEmpty(edt_phone.getText().toString())){
                                    Toast.makeText(getActivity(),"Fill in the fields!",Toast.LENGTH_SHORT).show();
                                }else {
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BrandOrder");
                                    assert user != null;
                                    final String edit_address = edt_address.getText().toString().trim();
                                    final String edit_phone = edt_phone.getText().toString().trim();

                                    Map<String, Object> map= new HashMap<>();

                                    Map<String, Object> map2= new HashMap<>();
                                    for (BasketData i : data) {
                                        map2.put(i.getProduct_name(), i);
                                    }

                                    Map<String, Object> map3= new HashMap<>();
                                    map3.put("UserAddress",edit_address);
                                    map3.put("UserPhone",edit_phone);
                                    map3.put("TotalPrice",textView.getText().toString().trim());
                                    map3.put("Time",strDate);

                                    map.put("UserFoods", map2);
                                    map.put("UserInfo",map3);
                                    reference
                                            .child(user.getUid())
                                            .setValue(map)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getActivity(), "Блюдо заказано", Toast.LENGTH_SHORT).show();
                                                    dialogSheet.dismiss();
                                                    deleteAll();

                                                }
                                            });
                                }
                            }
                        });

                        txt_over_price.setText(String.valueOf(grandTotal(data)));
                    }
                });
            }
        });

        adapterOnItemTouch();
        adapterSetOnClickListener();
        return view;
    }

    private int grandTotal(List<BasketData> items){
        int totalPrice1 = 0;
        for(int i = 0 ; i < items.size(); i++) {
            totalPrice1 += items.get(i).getCount();
        }
//        Log.i(TAG, Integer.toString(totalPrice1));
        return totalPrice1;
    }
    private void adapterOnItemTouch() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getDataAt(viewHolder.getAdapterPosition()));
                Snackbar snackbar = Snackbar
                        .make(getView(), R.string.deleted, Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void adapterSetOnClickListener() {
        adapter.setOnItemClickListener(new BasketAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(final BasketData data) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View view = inflater.inflate(R.layout.item_dialog_basket, null);
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
                        int id = data.getId();
                        BasketData basketData = new BasketData(data.getProduct_name(),
                                data.getImageView(),
                                data.getPrice(),count,amount
                        );
                        basketData.setId(id);
                        viewModel.update(basketData);
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
