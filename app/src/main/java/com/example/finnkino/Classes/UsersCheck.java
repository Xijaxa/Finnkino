package com.example.finnkino.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Checks the existance of users in SQL database
public class UsersCheck extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public UsersCheck( Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists users");
    }
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        Long result = myDB.insert("users", null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if(cursor.getCount() > 0) return true;
        else
            return false;
    }
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username, password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}