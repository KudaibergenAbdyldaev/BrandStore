package com.example.brandstore.HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brandstore.R;
import com.marcoscg.dialogsheet.DialogSheet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.Holder> {


    private Context context;
    private List<ProductData> productsList;

    HomeAdapter(Context context, List<ProductData> productsList) {
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
        final ProductData productData = productsList.get(position);
        holder.txt_name.setText(productData.getName());
        holder.txt_price.setText(productData.getPrice());
        Picasso.get()
                .load(productData.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.item_dialog, null);
                TextView txt_name = view.findViewById(R.id.txt_name);
                TextView txt_price = view.findViewById(R.id.txt_price);
                ImageView imageView = view.findViewById(R.id.img_product);
                DialogSheet dialogSheet = new DialogSheet(context)
                        .setSingleLineTitle(true)
                        .setColoredNavigationBar(true)
                        .setView(view);
                dialogSheet.show();
                txt_name.setText(productData.getName());
                txt_price.setText(productData.getPrice());
                Picasso.get()
                        .load(productData.getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(imageView);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private TextView txt_name, txt_price;
        private ImageView imageView;
        private CardView card_view;

        Holder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.txt_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            imageView = itemView.findViewById(R.id.img_product);
            card_view = itemView.findViewById(R.id.card_view);
        }
    }
}
