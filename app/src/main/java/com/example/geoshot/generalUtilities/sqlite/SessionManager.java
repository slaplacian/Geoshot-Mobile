package com.example.geoshot.generalUtilities.sqlite;

import android.content.Context;

public class SessionManager {
    public static void saveSession(Context context, String username) {
        DBController dbcont = new DBController(context);
        dbcont.insert(username);
    }

    public static void removeSession(Context context) {
        DBController dbcont = new DBController(context);
        dbcont.delete();
    }

    public static String getSession(Context context) {
        DBController dbcont = new DBController(context);
        return dbcont.getUsername();
    }
}
