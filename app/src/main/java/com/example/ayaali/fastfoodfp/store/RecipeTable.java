package com.example.ayaali.fastfoodfp.store;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ayaali.fastfoodfp.curser.MainActivity;

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
    private static String CREATE_Database_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("

            +KEY_Name+" TEXT PRIMARY KEY,"
            +KEY_Cover+" TEXT,"
            +KEY_INGREDIENTS+" TEXT,"
            + KEY_DETAILIMAGE + " TEXT"+ ")";

    public RecipeTable(MainActivity mainActivity, Runnable callback) {
        callback.run();
    }

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_Database_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(database);
    }

    public static Cursor getItems(Context context) {
        String[] projection={RecipeTable.KEY_Name,
                RecipeTable.KEY_Cover,
                RecipeTable.KEY_INGREDIENTS,
                RecipeTable.KEY_DETAILIMAGE
        };

// Select All Query
        String selectQuery = "SELECT * FROM " + RecipeTable.TABLE_RECIPE;
        ContentProvider movieContentProvider=new ContentProvider(context);
        //SQLiteDatabase db = this.getWritableDatabase();
        return movieContentProvider.query(ContentProvider.CONTENT_URI,projection,selectQuery,null,null);
    }
}
