package com.example.geoshot.generalUtilities.sqlite;

import android.content.Context;
import android.util.Log;

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
        try {
            DBController dbcont = new DBController(context);
            return dbcont.getUsername();
        }
        catch (Exception e) {
            Log.d("Error do session", e.getMessage());
            return "";
        }
    }
}


