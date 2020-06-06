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
    public static final String TABLE_USER_COLUMN_WEIGHT = "weight";
    public static final String TABLE_USER_COLUMN_HEIGHT = "height";
    public static final String TABLE_USER_COLUMN_STATUS = "status";
    public static final String TABLE_USER_COLUMN_GENDER = "gender";

    /**
     * status user
     * */
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_OLD = "OLD";


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
    public static final String TABLE_MEAL_COLUMN_ID = "mealID";
    public static final String TABLE_MEAL_COLUMN_NAME = "name";
    public static final String TABLE_MEAL_DESCRIPTION = "description";
    public static final String TABLE_MEAL_IMAGE = "image";
    public static final String TABLE_MEAL_STATUS = "status";
    public static final String TABLE_MEAL_CATEGORY_NAME = "categoryName";
    public static final String TABLE_MEAL_PROTEIN = "protein";
    public static final String TABLE_MEAL_FAT = "fat";
    public static final String TABLE_MEAL_CARBOHYDRATE = "carbohydrate";
    public static final String TABLE_MEAL_MINERALS = "minerals";
    public static final String TABLE_MEAL_VITAMINS = "vitamins";
    public static final String TABLE_MEAL_TOTAL_CALORIES = "totalCalo";

    /**
     * Meals
     * */
    public static final String BREAKFAST = "Breakfast";
    public static final String LUNCH = "Lunch";
    public static final String DINNER = "Dinner";






}
