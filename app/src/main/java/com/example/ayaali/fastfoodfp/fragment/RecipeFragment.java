package com.example.ayaali.fastfoodfp.fragment;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ayaali.fastfoodfp.R;
import com.example.ayaali.fastfoodfp.adapter.RecipeAdapter;
import com.example.ayaali.fastfoodfp.app.AppController;
import com.example.ayaali.fastfoodfp.models.Recipe;
import com.example.ayaali.fastfoodfp.parser.RecipeParser;
import com.example.ayaali.fastfoodfp.store.ContentProvider;
import com.example.ayaali.fastfoodfp.store.RecipeTable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.fragment;

/**
 * Created by AyaAli on 19/08/2017.
 */

public class RecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static final String ARG_ITEM_ID = "item_id";
    Menu menu;
    protected RecipeAdapter recipeAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Recipe> dataSet;
    private int flag;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("dataset", ((Serializable) dataSet));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public RecipeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        this.setRetainInstance(true);

        setHasOptionsMenu(true);
        dataSet = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.activity_recipe_list, container, false);
        ButterKnife.bind(this, view);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mRecyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(getActivity(),dataSet);
        mRecyclerView.setAdapter(recipeAdapter);

        // Set the color scheme of the SwipeRefreshLayout by providing 4 color resource ids
        //noinspection ResourceAsColor
        mSwipeRefreshLayout.setColorScheme(
                R.color.colorPrimaryDark, R.color.colorAccent,
                R.color.colorAccent, R.color.colorPrimaryDark);

        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (!mSwipeRefreshLayout.isRefreshing())
        {
            mSwipeRefreshLayout.setRefreshing(true);
        }

        initiateRefresh();
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState!=null) {

            dataSet = (List<Recipe>) savedInstanceState.getSerializable("dataset");
        }
        // handel swipe refresh listener
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

              //  if(flag<=2)
                    initiateRefresh();
                /*else
                    showFavorite();
                    */

            }
        });
    }
    public  void initiateRefresh()
    {
        String Url;

            Url="http://api.yummly.com/v1/api/recipes?_app_id=0b222cee&_app_key=899f4b45e5a634e1439d892f8a84a536";

        /**
         * Execute the background task, which uses {@link AsyncTask} to load the data.
         */
        // We first check for cached request
      Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Url);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                clearDataSet();
                Iterator iterator = RecipeParser.parseStringToJson(data).iterator();
                while (iterator.hasNext()){
                    Recipe movie = (Recipe) iterator.next();
                    dataSet.add(movie);
                    recipeAdapter.notifyItemInserted(dataSet.size() - 1);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        /////////////connection//////////
        StringRequest strReq = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("response", response);
                clearDataSet();
                Iterator iterator = RecipeParser.parseStringToJson(response).iterator();
                while (iterator.hasNext()){
                    Recipe movie = (Recipe) iterator.next();
                    dataSet.add(movie);
                    recipeAdapter.notifyItemInserted(dataSet.size() - 1);
                }
                onRefreshComplete();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Stop the refreshing indicator
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("response", error.toString());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(strReq);

    }

    private void clearDataSet()
    {
        if (dataSet != null){
            dataSet.clear();
            recipeAdapter.notifyDataSetChanged();
        }
    }
    private void onRefreshComplete()
    {
        mSwipeRefreshLayout.setRefreshing(false);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);


        this.menu =  menu;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {


            case R.id.favorite:

                showFavorite();
                return true;
            case R.id.analytic:
                Intent questions=new Intent("analysis");
                startActivity(questions);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void showFavorite()
    {

        // movieDB=new DBHandler(getContext());
        /*ContentProvider m=new ContentProvider(getContext());
        clearDataSet();
        dataSet.addAll(0,getAllRecipes());

        recipeAdapter.notifyDataSetChanged();*/
        Intent questions=new Intent("loader");
        startActivity(questions);


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
        ContentProvider  movieContentProvider=new ContentProvider(getContext());
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
