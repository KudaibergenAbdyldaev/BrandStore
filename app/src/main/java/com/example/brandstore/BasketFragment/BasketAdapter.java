package com.example.brandstore.BasketFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brandstore.Data.BasketData;
import com.example.brandstore.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends ListAdapter<BasketData, BasketAdapter.Holder> implements Serializable {
    private OnItemClickListener listener;
    private static final String TAG = "MyActivity";
    public int total_count;
    private int count;
    List<BasketData> dataList = new ArrayList<>();

    public BasketAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<BasketData> DIFF_CALLBACK = new DiffUtil.ItemCallback<BasketData>() {
        @Override
        public boolean areItemsTheSame(BasketData oldItem, BasketData newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(BasketData oldItem, BasketData newItem) {
            return oldItem.getAmount() == newItem.getAmount() &&
                    oldItem.getCount() == newItem.getCount();
        }
    };

    @NonNull
    @Override
    public BasketAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_basket, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.Holder holder, int position) {
        BasketData basketData = getItem(position);
        holder.textViewTitle.setText(basketData.getProduct_name());
        Picasso.get()
                .load(basketData.getImageView())
                .into(holder.imageView);
        holder.overPrice.setText(String.valueOf(basketData.getCount()));
        holder.textViewAmount.setText(String.valueOf(basketData.getAmount()));
    }
    public BasketData getDataAt(int position) {
        return getItem(position);
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClickListener(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(BasketData data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
