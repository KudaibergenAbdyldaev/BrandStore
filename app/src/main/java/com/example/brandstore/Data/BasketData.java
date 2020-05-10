package com.example.brandstore.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "basket_table")
public class BasketData {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String product_name;
    public String imageView;
    public long count;
    public int amount;

    public BasketData(String product_name, String imageView, long count, int amount) {
        this.product_name = product_name;
        this.imageView = imageView;
        this.count = count;
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
