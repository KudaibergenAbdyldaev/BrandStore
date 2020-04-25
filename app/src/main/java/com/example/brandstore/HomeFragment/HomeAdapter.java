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

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {

    private Context context;
    List<ProductData> productsList = new ArrayList<>();

    public HomeAdapter(Context context, List<ProductData> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final ProductData uploadCurrent = productsList.get(position);
        holder.txt_name.setText(uploadCurrent.getmName());
        holder.txt_price.setText(uploadCurrent.getPrice());
        Picasso.get()
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView txt_name, txt_price;
        private ImageView imageView;
        private ImageView img_plus;

        private Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.name_product);
            txt_price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.img);
            img_plus = itemView.findViewById(R.id.img_plus);
        }
    }
}
