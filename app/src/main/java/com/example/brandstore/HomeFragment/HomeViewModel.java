package com.example.brandstore.HomeFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProductData>> productLiveData;
    private ArrayList<ProductData> productArrayList;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");


    public HomeViewModel() {
        productLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    MutableLiveData<ArrayList<ProductData>> getUserMutableLiveData() {
        return productLiveData;
    }

    private void init(){
        populateList();
        productLiveData.setValue(productArrayList);
    }

    private void populateList(){
        productArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ProductData productData = postSnapshot.getValue(ProductData.class);
                    productArrayList.add(productData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
