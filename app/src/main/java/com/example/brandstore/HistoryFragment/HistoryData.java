package com.example.brandstore.HistoryFragment;

public class HistoryData {
    private int amount;
    private int count;
    private String imageView;
    private String price;
    private String product_name;

    public HistoryData() {
    }

    public HistoryData(int amount, int count, String imageView, String price, String product_name) {
        this.amount = amount;
        this.count = count;
        this.imageView = imageView;
        this.price = price;
        this.product_name = product_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
