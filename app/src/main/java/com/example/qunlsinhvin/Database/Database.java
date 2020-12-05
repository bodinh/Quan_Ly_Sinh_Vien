package com.example.qunlsinhvin.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void Query(String query){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(query);
    }

    public Cursor QueryGetData(String query){
        SQLiteDatabase database = getWritableDatabase();
         return database.rawQuery(query,null);
    }

    public void beginTransaction(){
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
    }
    public void setTransactionSuccessful(){
        SQLiteDatabase database = getWritableDatabase();
        database.setTransactionSuccessful();
    }
    public void endTransaction(){
        SQLiteDatabase database = getWritableDatabase();
        database.endTransaction();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
