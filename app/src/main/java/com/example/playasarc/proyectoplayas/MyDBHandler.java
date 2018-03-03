package com.example.playasarc.proyectoplayas;

import android.accounts.Account;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "accounts.db";

    //USERS TABLE NAME
    public static final String TABLE_USERS = "users";
    //USERS TABLE COLUMNS NAME
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT " +
                ");";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    //Add Row
    public void addAccount(Accounts account) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUsername());
        values.put(COLUMN_PASSWORD, account.getPassword());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    //Check if Username Already Exists
    public int checkUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " =\"" + username + "\";", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        return count;
    }

    //Check if account exists to log in
    public int checkAccount(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " =\"" + username + "\"" +
                " AND " + COLUMN_PASSWORD + " =\"" + password + "\";", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        return count;
    }

}
