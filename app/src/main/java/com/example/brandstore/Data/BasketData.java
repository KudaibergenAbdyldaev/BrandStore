package com.example.brandstore.Data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "basket_table")
public class BasketData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String product_name;
    public String imageView;
    public String price;
    public int count;
    public int amount;

    public BasketData() {
    }

    public BasketData(String product_name, String imageView, String price, int count, int amount) {
        this.product_name = product_name;
        this.imageView = imageView;
        this.price = price;
        this.count = count;
        this.amount = amount;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
