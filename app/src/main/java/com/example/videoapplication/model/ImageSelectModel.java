package com.example.videoapplication.model;

import android.widget.ImageView;

public class ImageSelectModel {
    private String imageView;
    private boolean selectImage;

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public boolean isSelectImage() {
        return selectImage;
    }

    public void setSelectImage(boolean selectImage) {
        this.selectImage = selectImage;
    }
}
