package com.example.ayaali.fastfoodfp.curser;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.example.ayaali.fastfoodfp.store.RecipeTable;

/**
 * Created by ejohn on 9/4/13.
 */
public class DumbLoader extends CursorLoader {
    private static final String TAG = "DumbLoader";

    public DumbLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = RecipeTable.getItems(getContext());
        //while (c.moveToNext())
          // Log.d(TAG, "item: " + c.getInt(0) + " - " + c.getString(1));
        return c;
    }
}
