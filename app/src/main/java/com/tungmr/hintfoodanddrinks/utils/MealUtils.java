package com.tungmr.hintfoodanddrinks.utils;

import com.tungmr.hintfoodanddrinks.model.Meal;

public class MealUtils {


    /**
     * Gender
     */
    public static final String MALE = "Male";
    public static final String FEMALE = "Female";

    public static Double calculateCalories(Meal meal) {
        return meal.getProtein() * 4 + meal.getFat() * 9 + meal.getCarbohydrate() * 4;
    }

    public static double[] calculateCaloriesPerMealByGender(String type, String gender) {

        double[] rs = new double[2];
        double start = 0, end = 0;

        switch (gender) {
            case MALE:
                if (type.equals(BMIUtils.UNDER_WEIGHT)) {
                    start = 2300;
                    end = 50000;
                }
                if (type.equals(BMIUtils.NORMAL)) {
                    start = 1800;
                    end = 2300;
                }
                if (type.equals(BMIUtils.PRE_OBESE)) {
                    start = 1500;
                    end = 1800;
                }
                if (type.equals(BMIUtils.OBESE_I)) {
                    start = 1200;
                    end = 1500;
                }
                if (type.equals(BMIUtils.OBESE_II)) {
                    start = 0;
                    end = 1200;
                }
                break;
            case FEMALE:
                if (type.equals(BMIUtils.UNDER_WEIGHT)) {
                    start = 1800;
                    end = 50000;
                }
                if (type.equals(BMIUtils.NORMAL)) {
                    start = 1500;
                    end = 1800;
                }
                if (type.equals(BMIUtils.PRE_OBESE)) {
                    start = 1200;
                    end = 1500;
                }
                if (type.equals(BMIUtils.OBESE_I)) {
                    start = 1000;
                    end = 1200;
                }
                if (type.equals(BMIUtils.OBESE_II)) {
                    start = 0;
                    end = 1000;
                }
                break;
            default:
                break;
        }
        rs[0] = start;
        rs[1] = end;
        return rs;

    }
}
