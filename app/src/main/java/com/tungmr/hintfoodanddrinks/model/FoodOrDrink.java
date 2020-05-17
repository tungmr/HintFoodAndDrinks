package com.tungmr.hintfoodanddrinks.model;

public class FoodOrDrink {

    private Long foodOrDrinkId;
    private String name;
    private String description;
    private Meal meal;

    public Long getFoodOrDrinkId() {
        return foodOrDrinkId;
    }

    public void setFoodOrDrinkId(Long foodOrDrinkId) {
        this.foodOrDrinkId = foodOrDrinkId;
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

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
