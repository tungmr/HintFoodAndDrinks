package com.tungmr.hintfoodanddrinks.constants;

public class CoreConstants {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "HintFoodAndDrinks.db";

    public static final String TAG = "SQLite";

    /**
     * User table
     * */
    public static final String TABLE_USER = "user";

    public static final String TABLE_USER_COLUMN_EMAIL = "email";
    public static final String TABLE_USER_COLUMN_PASSWORD = "password";
    public static final String TABLE_USER_COLUMN_NAME = "name";
    public static final String TABLE_USER_COLUMN_ROLE = "role";

    /**
     * Category table
     * */
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_CATEGORY_COLUMN_ID = "categoryId";
    public static final String TABLE_CATEGORY_COLUMN_NAME = "categoryName";
    public static final String TABLE_CATEGORY_INTRODUCTION_TEXT = "introductionText";

//    ROLE
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN ="ADMIN";


    /**
     * Meal table
    * */
    public static final String TABLE_MEAL = "meal";
    public static final String TABLE_MEAL_COLUMN_ID = "mealId";
    public static final String TABLE_MEAL_COLUMN_NAME = "name";
    public static final String TABLE_MEAL_DESCRIPTION = "description";
    public static final String TABLE_MEAL_IMAGE = "image";
    public static final String TABLE_MEAL_STATUS = "status";
    public static final String TABLE_MEAL_CATEGORY_NAME = "categoryName";

    /**
     * Meals
     * */
    public static final String BREAKFAST = "Breakfast";
    public static final String LUNCH = "Lunch";
    public static final String DINNER = "Dinner";




}
