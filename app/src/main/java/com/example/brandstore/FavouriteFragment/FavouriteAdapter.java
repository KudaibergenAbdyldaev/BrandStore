package com.example.brandstore.FavouriteFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brandstore.BasketRoomData.BasketData;
import com.example.brandstore.FavouriteRoomData.FavouriteData;
import com.example.brandstore.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class FavouriteAdapter extends ListAdapter<FavouriteData, FavouriteAdapter.Holder> implements Serializable {
    private OnItemClickListener listener;
    public FavouriteAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<FavouriteData> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavouriteData>() {
        @Override
        public boolean areItemsTheSame(@NonNull FavouriteData oldItem, @NonNull FavouriteData newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavouriteData oldItem, @NonNull FavouriteData newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public FavouriteAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourite, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.Holder holder, int position) {
        FavouriteData basketData = getItem(position);
        holder.txt_name.setText(basketData.getProduct_name());
        holder.txt_price.setText(basketData.getPrice());
        Picasso.get()
                .load(basketData.getImageView())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    public FavouriteData getDataAt(int position) {
        return getItem(position);
    }
    public class Holder extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_price;
        private ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            imageView = itemView.findViewById(R.id.img_product);
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
        void onItemClickListener(FavouriteData data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
