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
import com.tungmr.hintfoodanddrinks.utils.BMIUtils;

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
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_NAME, user.getName());
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_PASSWORD, SHAHashing.getSHAHash(user.getPassword()));
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_ROLE, CoreConstants.ROLE_USER);
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_STATUS, CoreConstants.STATUS_NEW);
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_HEIGHT, 0);
        contentValues.put(CoreConstants.TABLE_USER_COLUMN_WEIGHT, 0);
        long ins = sqLiteDatabase.insert(CoreConstants.TABLE_USER, null, contentValues);
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
            user.setEmail(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_EMAIL)));
            user.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_PASSWORD)));
            user.setRole(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_ROLE)));
            user.setStatus(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_STATUS)));
            user.setGender(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_GENDER)));
            user.setHeight(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_HEIGHT)));
            user.setWeight(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_WEIGHT)));
            if (user.getHeight() != null && user.getWeight() != null)
                user.setBMI(BMIUtils.calculateBMI(user.getHeight(), user.getWeight()));

        }
        return user;
    }

    public User findUserByEmail(String email) {
        User user = new User();
        String sql = "SELECT * FROM " + CoreConstants.TABLE_USER + " WHERE " + CoreConstants.TABLE_USER_COLUMN_EMAIL + "=? ";
        cursor = sqLiteDatabase.rawQuery(sql, new String[]{email});
        while (cursor.moveToNext()) {
            user.setEmail(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_EMAIL)));
            user.setName(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_PASSWORD)));
            user.setRole(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_ROLE)));
            user.setStatus(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_STATUS)));
            user.setGender(cursor.getString(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_GENDER)));
            user.setHeight(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_HEIGHT)));
            user.setWeight(cursor.getInt(cursor.getColumnIndex(CoreConstants.TABLE_USER_COLUMN_WEIGHT)));
            if (user.getHeight() != null && user.getWeight() != null && !user.getHeight().equals(0) && !user.getWeight().equals(0) )
                user.setBMI(BMIUtils.calculateBMI(user.getHeight(), user.getWeight()));
            else
                user.setBMI(0d);
        }
        return user;
    }

    public boolean editUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(CoreConstants.TABLE_USER_COLUMN_NAME, user.getName());
        cv.put(CoreConstants.TABLE_USER_COLUMN_PASSWORD, user.getPassword());
        cv.put(CoreConstants.TABLE_USER_COLUMN_WEIGHT, user.getWeight());
        cv.put(CoreConstants.TABLE_USER_COLUMN_HEIGHT, user.getHeight());
        cv.put(CoreConstants.TABLE_USER_COLUMN_STATUS, user.getStatus());
        cv.put(CoreConstants.TABLE_USER_COLUMN_GENDER, user.getGender());
        long ins = sqLiteDatabase.update(CoreConstants.TABLE_USER, cv, CoreConstants.TABLE_USER_COLUMN_EMAIL + "=?", new String[]{user.getEmail()});
        return ins != -1;

    }
}
