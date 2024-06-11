package com.example.geoshot.generalUtilities.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBController {
    private SQLiteDatabase db;
    private DBHelper banco;
    public DBController (Context context){
        banco = new DBHelper(context);
    }
    public String insert(String username){

        ContentValues valores ;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(DBHelper.username, username);

        resultado = db.insert(DBHelper.table, null, valores);

        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro" ;
        else
            return "Registro Inserido com sucesso" ;

    }

    public void delete() {
        banco.onUpgrade(db,1,1);
    }

    public String getUsername(){
        Cursor cursor;
        String[] campos = {banco.username};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.table, campos, null, null, null, null, null, null);
        String username = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                username = cursor.getString(cursor.getColumnIndexOrThrow(banco.username));
            }
            cursor.close();
        }
        db.close();
        return username != null ? username : "DEU RUIM";
    }
}