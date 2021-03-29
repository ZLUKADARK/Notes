package com.example.notes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbhelper extends SQLiteOpenHelper {
    public MyDbhelper(@Nullable Context context) {
        super(context, MyConstans.DB_NAME, null, MyConstans.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MyConstans.TABLE_STRUCTURE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(MyConstans.DROP_TABLE);
        onCreate(db);
    }
}
