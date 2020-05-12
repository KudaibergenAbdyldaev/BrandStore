package com.example.brandstore.BasketFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;
import com.example.brandstore.Data.BasketData;
import com.example.brandstore.R;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {


    private BasketViewModel viewModel;
    // use to pass data between fragments
//    private SharedViewModel sharedViewModel;
    public BasketFragment() {
        //1 Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_basket, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final BasketAdapter adapter= new BasketAdapter();
        recyclerView.setAdapter(adapter);
        this.setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(requireActivity()).get(BasketViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<BasketData>>() {
            @Override
            public void onChanged(@Nullable List<BasketData> data) {
                adapter.setBasket(data);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getDataAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

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
                viewModel.deleteAllNotes();
                Toast.makeText(getActivity(), "All notes deleted", Toast.LENGTH_SHORT).show();
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
