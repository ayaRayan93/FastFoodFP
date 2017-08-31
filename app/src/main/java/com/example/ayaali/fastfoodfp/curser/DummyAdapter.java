package com.example.ayaali.fastfoodfp.curser;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import com.example.ayaali.fastfoodfp.R;

/**
 * Created by ejohn on 9/2/13.
 */
public class DummyAdapter extends SimpleCursorAdapter {

    public DummyAdapter(Context context, Cursor c) {
        super(context, R.layout.list_item, c, new String[]{"nameRecipe"}, new int[]{R.id.tv1}, 0);
    }

}
