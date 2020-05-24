package com.tungmr.hintfoodanddrinks.model;

import android.graphics.Bitmap;

import java.util.List;

public class Meal {

    private Long mealId;
    private String name;
    private String description;
    private Category category;
    private Bitmap image;
    private Integer status;
    private String categoryName;
    private byte[] imageArray;

    public Meal() {
    }

    public Meal(String name, String description, Integer status, String categoryName, byte[] imageArray) {
        this.name = name;
        this.description = description;

        this.status = status;
        this.categoryName = categoryName;
        this.imageArray = imageArray;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }
}
