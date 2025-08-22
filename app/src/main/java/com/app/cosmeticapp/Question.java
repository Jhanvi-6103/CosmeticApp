package com.app.cosmeticapp;

import java.util.List;

public class Question {
    private String text;
    private String imageUrl;
    private List<Option> options;

    public Question() {
        // Default constructor required for Firebase
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Option> getOptions() {
        return options;
    }
}
