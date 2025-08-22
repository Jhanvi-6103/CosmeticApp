package com.app.cosmeticapp;

public class Product {
    public String id;
    public String name;
    public String category;
    public int price;
    public String imageUrl;
    public double rating;
    public String description;


    public Product() {
        // Required for Firebase
    }

    public Product(String id, String name, String category, double price, String imageUrl, double rating, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = (int) price;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.description = description;
    }
}
