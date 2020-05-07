package com.tungmr.hintfoodanddrinks.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class LoginDBHelper extends DatabaseAccess  {

    private static LoginDBHelper instance;

    private static final String TAG = "SQLite";

    private static final String TABLE_USER = "user";

    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";

    public LoginDBHelper(Context context) {
        super(context);
    }

    public static LoginDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LoginDBHelper(context);
        }
        return instance;
    }


    public boolean insertUser(String email, String name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        long ins = sqLiteDatabase.insert("user", null, contentValues);
        return ins != -1;
    }

    public boolean checkEmailExisted(String email) {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public boolean checkUser(String email, String password) {
        cursor = sqLiteDatabase.query(TABLE_USER, new String[]{COLUMN_EMAIL, COLUMN_NAME, COLUMN_PASSWORD}, COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password}, null, null, null);
        return cursor.getCount() > 0;
    }
}
