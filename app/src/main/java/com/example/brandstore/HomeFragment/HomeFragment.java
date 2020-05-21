package com.example.brandstore.HomeFragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandstore.BasketFragment.BasketViewModel;
import com.example.brandstore.Data.BasketData;
import com.example.brandstore.R;
import com.example.brandstore.SharedViewModel.SharedViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marcoscg.dialogsheet.DialogSheet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment  extends Fragment implements LifecycleOwner{

    private int count;
    private int amount = 1;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private HomeViewModel viewModel;
    private BasketViewModel basketViewModel;
    private SharedViewModel sharedViewModel;
    private ArrayList<ProductData> list;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");;
    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.setHasOptionsMenu(true);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Using code from
        //https://developer.android.com/topic/libraries/architecture/viewmodel
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        basketViewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        list = new ArrayList<>();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        getFirebase();
        return view;

    }
    private Observer<ArrayList<ProductData>> userListUpdateObserver = new Observer<ArrayList<ProductData>>() {
        @Override
        public void onChanged(final ArrayList<ProductData> userArrayList) {
            homeAdapter = new HomeAdapter(requireActivity(),userArrayList);
            recyclerView.setAdapter(homeAdapter);
            homeAdapter.setOnClickListener(new HomeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final int position) {
                    final ProductData productData = list.get(position);
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
                    final int totalPrice = ((Integer.parseInt(productData.getPrice())));
                    count = count + totalPrice;
                    txt_name.setText(productData.getName());
                    txt_count.setText(productData.getPrice());
                    Picasso.get().load(productData.getImageUrl()).fit().centerCrop().into(imageView);

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
                            BasketData basketData = new BasketData(productData.getName(),
                                    productData.getImageUrl(),
                                    productData.getPrice(),
                                    count,amount
                            );
                            basketViewModel.insert(basketData);
                            dialogSheet.dismiss();
                            //use to pass data between fragments
//                            sharedViewModel.setTextName(txt_name.getText());
//                            sharedViewModel.setTextImage(productData.getImageUrl());
//                            sharedViewModel.setTextAmount(txt_amount.getText());
//                            sharedViewModel.setTextCount(productData.getPrice());
                        }
                    });
                    dialogSheet.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            count = 0;
                            amount =1;
                        }
                    });
                    dialogSheet.show();
                }
            });
        }
    };


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.basket_menu, menu);
    }
    private void getFirebase(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductData productData = postSnapshot.getValue(ProductData.class);
                    list.add(productData);
                }
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
