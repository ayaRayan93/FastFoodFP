package com.example.ayaali.fastfoodfp.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AyaAli on 22/08/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "recipeDB";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RecipeTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        RecipeTable.onUpgrade(db,oldVersion,newVersion);
    }
}
