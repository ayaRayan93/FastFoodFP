package com.example.ayaali.fastfoodfp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ayaali.fastfoodfp.models.Recipe;
import com.example.ayaali.fastfoodfp.store.ContentProvider;
import com.example.ayaali.fastfoodfp.store.RecipeTable;

import java.util.ArrayList;
import java.util.List;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    List<String> mCollection = new ArrayList<>();
    Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(position));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        mCollection.clear();
        List<Recipe> mRwcipes= getAllRecipes();
        for (int i = 0; i < mRwcipes.size(); i++) {
            mCollection.add(mRwcipes.get(i).getNameRecipe());
        }
    }
    private List<Recipe> getAllRecipes()
    {
        String[] projection={RecipeTable.KEY_Name,
                RecipeTable.KEY_Cover,
                RecipeTable.KEY_INGREDIENTS,
                RecipeTable.KEY_DETAILIMAGE
        };

        List<Recipe> movieList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + RecipeTable.TABLE_RECIPE;
        ContentProvider movieContentProvider=new ContentProvider(mContext);
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = movieContentProvider.query(ContentProvider.CONTENT_URI,projection,selectQuery,null,null); //db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Recipe mRecipe = new Recipe();
                String s;
                mRecipe.setCoverImage(cursor.getString(1));
                mRecipe.setDetailImage(cursor.getString(3));
                mRecipe.setNameRecipe(cursor.getString(0));
                mRecipe.setIngredients(cursor.getString(2));
                s=cursor.getString(0);
                s=cursor.getString(1);
                s=cursor.getString(2);
                s=cursor.getString(3);

// Adding contact to list
                movieList.add(mRecipe);
            } while (cursor.moveToNext());
            cursor.close();
        }
// return contact list
        return movieList;

    }
}
