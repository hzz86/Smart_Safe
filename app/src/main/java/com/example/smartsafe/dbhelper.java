package com.example.smartsafe;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class dbhelper extends SQLiteOpenHelper {
    private static final String DB_name="ourdb1.db";
    private static final int DB_version=1;

    public dbhelper(Context context) {super(context, DB_name, null, DB_version); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table Member(id INTEGER PRIMARY KEY AUTOINCREMENT,name text, pw text, mobile number, email text)");
        } catch (Exception e) {
            e.printStackTrace();//여기서만 name은 사용자 아이디를 가리킴
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

