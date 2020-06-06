package com.tungmr.hintfoodanddrinks.db;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.model.Meal;
import com.tungmr.hintfoodanddrinks.utils.BMIUtils;
import com.tungmr.hintfoodanddrinks.utils.DBImageUtils;
import com.tungmr.hintfoodanddrinks.utils.MealUtils;

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
        cv.put(CoreConstants.TABLE_MEAL_PROTEIN, meal.getProtein());
        cv.put(CoreConstants.TABLE_MEAL_FAT, meal.getFat());
        cv.put(CoreConstants.TABLE_MEAL_VITAMINS, meal.getVitamins());
        cv.put(CoreConstants.TABLE_MEAL_MINERALS, meal.getMinerals());
        cv.put(CoreConstants.TABLE_MEAL_CARBOHYDRATE, meal.getCarbohydrate());
        cv.put(CoreConstants.TABLE_MEAL_TOTAL_CALORIES, MealUtils.calculateCalories(meal));

        long check = sqLiteDatabase.insert(CoreConstants.TABLE_MEAL, null, cv);
        return check != -1;
    }

    public List<Meal> getAllMealByCategory(String category, Integer status) {
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? AND " + CoreConstants.TABLE_MEAL_STATUS + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{category, String.valueOf(status)});
        List<Meal> meals = new ArrayList<>();
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));
            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public List<Meal> getAllMealByCategoryBMIAndGender(String categoryName, Integer status, Double BMI, String gender) {
        if (BMI.equals(Double.NaN) || BMI.equals(0d)) {
            return getAllMealByCategory(categoryName, status);
        }
        List<Meal> meals = new ArrayList<>();
        String type = BMIUtils.getTypeFromBMI(BMI);
        double[] rs = MealUtils.calculateCaloriesPerMealByGender(type, gender);
        double start = rs[0];
        double end = rs[1];
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? AND " + CoreConstants.TABLE_MEAL_STATUS + "=? AND "
                + CoreConstants.TABLE_MEAL_TOTAL_CALORIES + ">=? AND " + CoreConstants.TABLE_MEAL_TOTAL_CALORIES + " <= ?";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{categoryName, String.valueOf(status), String.valueOf(start), String.valueOf(end)});
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));
            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public List<Meal> getAllMeal() {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL;
        cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));

            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public Meal getMealById(Long mealId) {
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_COLUMN_ID + "=?";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(mealId)});
        Meal meal = new Meal();
        while (cursor.moveToNext()) {
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));

            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
        }
        return meal;
    }

    public boolean editMeal(Meal meal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CoreConstants.TABLE_MEAL_COLUMN_NAME, meal.getName());
        contentValues.put(CoreConstants.TABLE_MEAL_DESCRIPTION, meal.getDescription());
        contentValues.put(CoreConstants.TABLE_MEAL_CATEGORY_NAME, meal.getCategoryName());
        contentValues.put(CoreConstants.TABLE_MEAL_IMAGE, meal.getImageArray());
        contentValues.put(CoreConstants.TABLE_MEAL_STATUS, meal.getStatus());
        contentValues.put(CoreConstants.TABLE_MEAL_PROTEIN, meal.getProtein());
        contentValues.put(CoreConstants.TABLE_MEAL_FAT, meal.getFat());
        contentValues.put(CoreConstants.TABLE_MEAL_MINERALS , meal.getMinerals());
        contentValues.put(CoreConstants.TABLE_MEAL_CARBOHYDRATE, meal.getCarbohydrate());
        contentValues.put(CoreConstants.TABLE_MEAL_TOTAL_CALORIES, MealUtils.calculateCalories(meal));
        long check = sqLiteDatabase.update(CoreConstants.TABLE_MEAL, contentValues, CoreConstants.TABLE_MEAL_COLUMN_ID + "=?", new String[]{String.valueOf(meal.getMealId())});
        return check != -1;

    }

    public boolean delete() {
        return sqLiteDatabase.delete(CoreConstants.TABLE_MEAL, null, null) > 0;

    }

    public List<Meal> selectRandomForHint(Integer quantity) {
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " ORDER BY RANDOM() LIMIT ?";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(quantity)});
        List<Meal> meals = new ArrayList<>();
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));

            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public List<Meal> filterMeal(Integer protein, Integer fat, Integer carbohydrate, Integer vitamins, Integer minerals) {
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_PROTEIN + "<= ? AND " +
                CoreConstants.TABLE_MEAL_FAT + "<= ? AND " +
                CoreConstants.TABLE_MEAL_CARBOHYDRATE + "<= ? AND " +
                CoreConstants.TABLE_MEAL_VITAMINS + "<= ? AND " +
                CoreConstants.TABLE_MEAL_MINERALS + "<= ? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(protein), String.valueOf(fat), String.valueOf(carbohydrate), String.valueOf(vitamins), String.valueOf(minerals)});
        List<Meal> meals = new ArrayList<>();
        while (cursor.moveToNext()) {
            Meal meal = new Meal();
            meal.setMealId((long) cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            meal.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            meal.setDescription(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_DESCRIPTION)));
            meal.setCategoryName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CATEGORY_NAME)));
            meal.setImageArray(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)));
            meal.setStatus(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_STATUS)));
            meal.setProtein(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_PROTEIN)));
            meal.setFat(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_FAT)));
            meal.setVitamins(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_VITAMINS)));
            meal.setMinerals(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_MINERALS)));
            meal.setCarbohydrate(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_CARBOHYDRATE)));
            meal.setTotalCalories(cursor.getDouble(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_TOTAL_CALORIES)));

            if (meal.getImageArray() != null && meal.getImageArray().length > 0)
                meal.setImage(DBImageUtils.getImage(meal.getImageArray()));
            meals.add(meal);
        }

        return meals;
    }

    public void getImageBitmapAndMealName(List<Bitmap> images, List<String> mealNames, List<Long> mealIds, Integer status, Double BMI, String gender, String category) {

        String type = BMIUtils.getTypeFromBMI(BMI);
        double[] rs = MealUtils.calculateCaloriesPerMealByGender(type, gender);
        double start = rs[0];
        double end = rs[1];
        if (start != 0 && end != 0) {
            String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_STATUS + "=? AND "
                    + CoreConstants.TABLE_MEAL_TOTAL_CALORIES + ">=? AND " + CoreConstants.TABLE_MEAL_TOTAL_CALORIES + " <= ? AND " +
                    CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? ORDER BY RANDOM() LIMIT 5";
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(status), String.valueOf(start), String.valueOf(end), category});
            while (cursor.moveToNext()) {
                mealNames.add(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
                if (cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)) != null && cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)).length > 0)
                    images.add(DBImageUtils.getImage(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE))));
                mealIds.add(cursor.getLong(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            }
        }
        if (images.size() == 0 && start != 0 && end != 0) {
            String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_STATUS + "=? AND "
                    + CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? ORDER BY RANDOM() LIMIT 5";
            cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(status), category});
            while (cursor.moveToNext()) {
                mealNames.add(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
                if (cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)) != null && cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)).length > 0)
                    images.add(DBImageUtils.getImage(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE))));
                mealIds.add(cursor.getLong(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            }
        }
    }

    public void getImageBitmapAndMealNameRand(List<Bitmap> images, List<String> mealNames, List<Long> mealIds, Integer status, String category) {
        String sql = "SELECT * FROM " + CoreConstants.TABLE_MEAL + " WHERE " + CoreConstants.TABLE_MEAL_STATUS + "=? AND " +
                CoreConstants.TABLE_MEAL_CATEGORY_NAME + "=? ORDER BY RANDOM() LIMIT 5";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(status), category});
        while (cursor.moveToNext()) {
            mealIds.add(cursor.getLong(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_ID)));
            mealNames.add(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_COLUMN_NAME)));
            if (cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)) != null && cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE)).length > 0)
                images.add(DBImageUtils.getImage(cursor.getBlob(cursor.getColumnIndex(CoreConstants.TABLE_MEAL_IMAGE))));
        }
    }


}
