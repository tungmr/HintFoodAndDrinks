package com.tungmr.hintfoodanddrinks.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.tungmr.hintfoodanddrinks.constants.CoreConstants;

public class DatabaseHelper extends SQLiteAssetHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, CoreConstants.DATABASE_NAME, null, CoreConstants.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("old" + oldVersion + "new ver " + newVersion);
        super.setForcedUpgrade(newVersion);
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
