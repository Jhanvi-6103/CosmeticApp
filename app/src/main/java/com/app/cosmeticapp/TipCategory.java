package com.app.cosmeticapp;

public class TipCategory {
    private final String title;
    private final int iconResId;

    public TipCategory(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    public String getTitle() {
        return title;
    }

    public int getIconResId() {
        return iconResId;
    }
}
