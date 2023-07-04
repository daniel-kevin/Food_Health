package com.example.foodhealth;

public class Product {
    private int id;
    private String name;
    private double price;
//    private String imagePath;
    private int imageResourceId;
    public Product(int id, String name, double price, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

//    public String getImagePath() {
//        return imagePath;
//    }
    public  int getImageResourceId(){return imageResourceId;}
}
