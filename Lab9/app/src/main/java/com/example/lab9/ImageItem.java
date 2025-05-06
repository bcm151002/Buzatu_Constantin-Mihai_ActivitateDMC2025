package com.example.lab9;

import android.graphics.Bitmap;

public class ImageItem {
    private String imageUrl;
    private String description;
    private String webLink;
    private Bitmap bitmap;

    public ImageItem(String imageUrl, String description, String webLink) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.webLink = webLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getWebLink() {
        return webLink;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}