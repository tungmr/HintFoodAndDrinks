package com.tungmr.hintfoodanddrinks.db;

import android.content.Context;

import com.tungmr.hintfoodanddrinks.constants.CoreConstants;

import java.util.List;

public class CategoryDBHelper extends DatabaseAccess{

    private static CategoryDBHelper instance;


    public CategoryDBHelper(Context context) {
        super(context);
    }

    public static CategoryDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CategoryDBHelper(context);
        }
        return instance;
    }

    public void getCategories(List<String> categoriesName, List<String> categoriesIntroductionText){
        String sql = "SELECT * FROM category";
        cursor = sqLiteDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()){
            String nameValue = cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_CATEGORY_COLUMN_NAME));
            String introductionValue = cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_CATEGORY_INTRODUCTION_TEXT));
            categoriesName.add(nameValue);
            categoriesIntroductionText.add(introductionValue);
        }
    }
}
