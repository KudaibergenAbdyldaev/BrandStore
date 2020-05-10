package com.example.brandstore.BasketFragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandstore.Data.BasketData;
import com.example.brandstore.HomeFragment.HomeViewModel;
import com.example.brandstore.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {


    private BasketViewModel viewModel;
    public BasketFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_basket, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final BasketAdapter adapter= new BasketAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<BasketData>>() {
            @Override
            public void onChanged(@Nullable List<BasketData> data) {
                adapter.setBasket(data);
            }
        });

        TextView textView = view.findViewById(R.id.textView3);
        Bundle bundle  = this.getArguments();
        assert bundle != null;
        String name = bundle.getString("name");
        textView.setText(name);
        Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
        return view;
    }
}
