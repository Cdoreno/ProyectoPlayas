package com.example.playasarc.proyectoplayas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Accounts {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private String username;
    private String password;

    public Accounts (Context context, String username, String password){

        mContext = context.getApplicationContext();
        mDatabase = new MyDBHandler(mContext).getWritableDatabase();

        this.username = username;
        this.password = password;
    }

    public Accounts (){

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
