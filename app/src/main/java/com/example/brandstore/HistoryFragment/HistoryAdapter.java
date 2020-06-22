package com.example.brandstore.HistoryFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandstore.HomeFragment.HomeAdapter;
import com.example.brandstore.HomeFragment.ProductData;
import com.example.brandstore.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<HistoryData> dataList;
    private OnItemClickListener clickListener;

    public HistoryAdapter(Context context, ArrayList<HistoryData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        clickListener = listener;
    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
        return new ViewHolder(rootView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryData data = dataList.get(position);
        holder.textViewTitle.setText(data.getProduct_name());
        holder.overPrice.setText(String.valueOf(data.getCount()));
        holder.textViewAmount.setText(String.valueOf(data.getAmount()));
        Picasso.get()
                .load(data.getImageView())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView overPrice;
        private TextView textViewAmount;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView, final HistoryAdapter.OnItemClickListener listener) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.txt_name_basket);
            overPrice = itemView.findViewById(R.id.txt_over_price);
            textViewAmount = itemView.findViewById(R.id.txt_amount_history);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}