package com.app.cosmeticapp;

public class TipItem {
    private String title;
    private String description;
    private String imageUrl;

    public TipItem() {
        // Needed for Firebase
    }

    public TipItem(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
