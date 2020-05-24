package com.tungmr.hintfoodanddrinks.db;

import android.content.ContentValues;
import android.content.Context;

import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.model.Meal;
import com.tungmr.hintfoodanddrinks.utils.DBImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MealDBHelper extends DatabaseAccess {

    public MealDBHelper(Context context) {
        super(context);
    }

    private static MealDBHelper instance;


    public static MealDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MealDBHelper(context);
        }
        return instance;
    }

    public boolean saveMeal(Meal meal) {
        ContentValues cv = new ContentValues();
        cv.put(CoreConstants.TABLE_MEAL_COLUMN_NAME, meal.getName());
        cv.put(CoreConstants.TABLE_MEAL_DESCRIPTION, meal.getDescription());
        cv.put(CoreConstants.TABLE_MEAL_CATEGORY_NAME, meal.getCategoryName());
        cv.put(CoreConstants.TABLE_MEAL_IMAGE, meal.getImageArray());
        cv.put(CoreConstants.TABLE_MEAL_STATUS, meal.getStatus());
        long check = sqLiteDatabase.insert(CoreConstants.TABLE_MEAL, null, cv);
        return check != -1;
    }

    public List<Meal> getAllMealByCategory(String categoryName, Integer status) {
        List<Meal> meals = new ArrayList<>();

        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? AND " + CoreConstants.TABLE_MEAL_STATUS + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{categoryName, String.valueOf(status)});
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setCategoryName(cursor.getString(3));
            meal.setImageArray(cursor.getBlob(4));
            meal.setStatus(cursor.getInt(5));
            meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public List<Meal> getAllMeal(Integer status) {
        List<Meal> meals = new ArrayList<>();

        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_STATUS + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{ String.valueOf(status)});
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(0));
            meal.setName(cursor.getString(1));
            meal.setDescription(cursor.getString(2));
            meal.setCategoryName(cursor.getString(3));
            meal.setImageArray(cursor.getBlob(4));
            meal.setStatus(cursor.getInt(5));
            meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }


}
