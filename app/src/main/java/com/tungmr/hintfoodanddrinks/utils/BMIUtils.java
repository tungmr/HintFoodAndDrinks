package com.tungmr.hintfoodanddrinks.utils;

public class BMIUtils {

    public static String UNDER_WEIGHT = "UNDER_WEIGHT";
    public static String NORMAL = "NORMAL";
    public static String PRE_OBESE = "PRE_OBESE";
    public static String OBESE_I = "OBESE_I";
    public static String OBESE_II = "OBESE_II";

    public static Double calculateBMI (Integer height, Integer weight){

        double heightMeter = Double.valueOf(height)/100;
        Double weightD = Double.valueOf(weight);
        return weightD/(Math.pow(heightMeter,2));

    }

    public static String getTypeFromBMI(Double BMI) {

        if (BMI < 18.5){
            return UNDER_WEIGHT;
        }else if (BMI >=18.5 && BMI <22.9){
            return NORMAL;
        } if (BMI >=23 && BMI <24.9){
            return PRE_OBESE;
        } if (BMI >=25 && BMI <29.9){
            return OBESE_I;
        } if (BMI >=30){
            return OBESE_II;
        }
        return null;
    }
}
