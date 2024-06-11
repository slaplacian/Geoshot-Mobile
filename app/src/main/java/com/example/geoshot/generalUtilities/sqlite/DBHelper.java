package com.example.geoshot.generalUtilities.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String database = "session.db";
    public static final String table  = "loggedUsername";
    public static final String username = "username";

    public DBHelper(Context context) {
        super(context, database, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table + "(" + username + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }
}

