package com.example.brandstore.HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandstore.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {

    private Context context;
    private List<ProductData> productsList;
    private OnItemClickListener clickListener;

    public HomeAdapter(Context context, List<ProductData> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener){
        clickListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);

        return new Holder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final ProductData productData = productsList.get(position);
        holder.txt_name.setText(productData.getName());
        holder.txt_price.setText(productData.getPrice());
        Picasso.get()
                .load(productData.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private TextView txt_name, txt_price;
        private ImageView imageView;

        Holder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            imageView = itemView.findViewById(R.id.img_product);

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
