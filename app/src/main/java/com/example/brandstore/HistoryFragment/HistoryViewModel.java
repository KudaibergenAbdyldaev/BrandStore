package com.example.brandstore.HistoryFragment;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.brandstore.HomeFragment.ProductData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<ArrayList<HistoryData>> historyViewModel;
    private ArrayList<HistoryData> historyArrayList;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BrandOrder")
            .child(user.getUid())
            .child("list");
    public HistoryViewModel() {
        historyViewModel = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<HistoryData>> getUserMutableLiveData() {
        return historyViewModel;
    }

    private void init(){
        populateList();
        historyViewModel.setValue(historyArrayList);
    }

    private void populateList(){
        historyArrayList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    HistoryData historyData = postSnapshot.getValue(HistoryData.class);
                    historyArrayList.add(historyData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

