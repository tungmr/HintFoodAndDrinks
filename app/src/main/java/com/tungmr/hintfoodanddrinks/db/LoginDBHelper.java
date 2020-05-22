package com.tungmr.hintfoodanddrinks.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tungmr.hintfoodanddrinks.constants.CoreConstants;
import com.tungmr.hintfoodanddrinks.model.User;
import com.tungmr.hintfoodanddrinks.security.SHAHashing;

public class LoginDBHelper extends DatabaseAccess {

    private static LoginDBHelper instance;


    public LoginDBHelper(Context context) {
        super(context);
    }

    public static LoginDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new LoginDBHelper(context);
        }
        return instance;
    }


    public boolean insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("name", user.getName());
        contentValues.put("password", SHAHashing.getSHAHash(user.getPassword()));
        long ins = sqLiteDatabase.insert("user", null, contentValues);
        return ins != -1;
    }

    public boolean checkEmailExisted(String email) {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + CoreConstants.TABLE_USER + " WHERE " + CoreConstants.TABLE_USER_COLUMN_EMAIL + " = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public User checkUser(String email, String password) {
        User user = new User();
        String passwordHash = SHAHashing.getSHAHash(password);
        String sql = "SELECT * FROM " + CoreConstants.TABLE_USER + " WHERE " + CoreConstants.TABLE_USER_COLUMN_EMAIL + "=? AND " + CoreConstants.TABLE_USER_COLUMN_PASSWORD + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{email, passwordHash});
        while (cursor.moveToNext()) {
            user.setEmail(cursor.getString(0));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        return user;
    }

    public User findUserByEmail(String email) {
        User user = new User();
        String sql = "SELECT * FROM " + CoreConstants.TABLE_USER + " WHERE " + CoreConstants.TABLE_USER_COLUMN_EMAIL + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{email});
        while (cursor.moveToNext()) {
            user.setEmail(cursor.getString(0));
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
        }
        return user;
    }

    public boolean editUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(CoreConstants.TABLE_USER_COLUMN_NAME, user.getName());
        cv.put(CoreConstants.TABLE_USER_COLUMN_PASSWORD, user.getPassword());
        long ins = sqLiteDatabase.update(CoreConstants.TABLE_USER, cv, CoreConstants.TABLE_USER_COLUMN_EMAIL + "=?", new String[]{user.getEmail()});
        return ins != -1;

    }
}
