package com.example.foodhealth;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int productId;
    private String productName;
    private int quantity;
    private double price;

    public CartItem(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = 0;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

