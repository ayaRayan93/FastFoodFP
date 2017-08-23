package com.example.ayaali.fastfoodfp.store;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by AyaAli on 22/08/2017.
 */

public class RecipeTable {
    // Contacts table name
    public static final String TABLE_RECIPE ="recipe" ;
    // recipe Table Columns names

    public static final String KEY_ID ="ID" ;
    public static final String KEY_Name ="nameRecipe" ;
    public static final String KEY_Cover="coverImage" ;
    public static final String KEY_INGREDIENTS="ingredients" ;
    public static final String KEY_DETAILIMAGE="detailImage" ;
    public static String CREATE_Database_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("

            +KEY_Name+" TEXT PRIMARY KEY,"
            +KEY_Cover+" TEXT,"
            +KEY_INGREDIENTS+" TEXT,"
            + KEY_DETAILIMAGE + " TEXT"+ ")";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_Database_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(database);
    }
}
