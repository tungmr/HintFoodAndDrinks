package com.tungmr.hintfoodanddrinks.model;

import android.graphics.Bitmap;

import java.util.List;

public class Meal {

    private Long mealId;
    private String name;
    private String description;
    private Bitmap image;
    private Integer status;
    private String categoryName;
    private byte[] imageArray;
    private Double protein;
    private Double carbohydrate;
    private Double fat;
    private Double vitamins;
    private Double minerals;
    private Double totalCalories; // 25

    public Meal() {
    }

    public Meal(String name, String description, Integer status, String categoryName, byte[] imageArray) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.categoryName = categoryName;
        this.imageArray = imageArray;
    }


    public Meal(Long mealId,String name, String description, Integer status, String categoryName, byte[] imageArray) {
        this.mealId = mealId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.categoryName = categoryName;
        this.imageArray = imageArray;
    }

    public Meal(String name, String description, Integer status, String categoryName, byte[] imageArray, Double protein, Double carbohydrate, Double fat, Double vitamins, Double minerals) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.categoryName = categoryName;
        this.imageArray = imageArray;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.vitamins = vitamins;
        this.minerals = minerals;
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

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getVitamins() {
        return vitamins;
    }

    public void setVitamins(Double vitamins) {
        this.vitamins = vitamins;
    }

    public Double getMinerals() {
        return minerals;
    }

    public void setMinerals(Double minerals) {
        this.minerals = minerals;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }
}
