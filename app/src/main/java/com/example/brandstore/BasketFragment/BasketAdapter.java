package com.example.brandstore.BasketFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandstore.Data.BasketData;
import com.example.brandstore.R;

import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.Holder>{
    private List<BasketData> dataList = new ArrayList<>();

    void setBasket(List<BasketData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BasketAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.Holder holder, int position) {
        BasketData basketData = dataList.get(position);
        holder.textViewTitle.setText(basketData.getProduct_name());
//        holder.imageView.setText(basketData.getImageView());
        holder.overPrice.setText(String.valueOf(basketData.getCount()));
        holder.textViewAmount.setText(String.valueOf(basketData.getAmount()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView overPrice;
        private TextView textViewAmount;
        private ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.txt_name_basket);
            overPrice = itemView.findViewById(R.id.txt_over_price);
            textViewAmount = itemView.findViewById(R.id.txt_amount_basket);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
