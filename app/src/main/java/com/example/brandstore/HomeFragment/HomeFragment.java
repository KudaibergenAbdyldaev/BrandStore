package com.example.brandstore.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

import android.view.View;
import android.view.ViewGroup;
import com.example.brandstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment  extends Fragment implements LifecycleOwner{

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private HomeViewModel viewModel;
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
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), userListUpdateObserver);
        list = new ArrayList<>();
        getFirebase();
        return view;

    }
    private Observer<ArrayList<ProductData>> userListUpdateObserver = new Observer<ArrayList<ProductData>>() {
        @Override
        public void onChanged(ArrayList<ProductData> userArrayList) {
            homeAdapter = new HomeAdapter(requireActivity(),userArrayList);
            recyclerView.setAdapter(homeAdapter);
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
