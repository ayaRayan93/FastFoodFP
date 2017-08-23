package com.example.ayaali.fastfoodfp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ayaali.fastfoodfp.R;
import com.example.ayaali.fastfoodfp.fragment.RecipeFragment;

public class RecipesListActivity extends AppCompatActivity {

    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (findViewById(R.id.recipe_detail_container) != null)
        {
            mTwoPane = true;

        }


        RecipeFragment listitemsfragment=new RecipeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.item_container, listitemsfragment).commit();
    }
}
