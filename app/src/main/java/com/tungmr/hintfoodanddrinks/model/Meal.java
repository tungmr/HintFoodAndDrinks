package com.tungmr.hintfoodanddrinks.model;

import java.util.List;

public class Meal {

    private Long mealId;
    private String name;
    private String description;
    private Category category;
    private List<FoodOrDrink> foodOrDrinks;

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

    public List<FoodOrDrink> getFoodOrDrinks() {
        return foodOrDrinks;
    }

    public void setFoodOrDrinks(List<FoodOrDrink> foodOrDrinks) {
        this.foodOrDrinks = foodOrDrinks;
    }
}
