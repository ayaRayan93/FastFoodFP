package com.example.ayaali.fastfoodfp.fragment;

import android.content.ContentValues;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ayaali.fastfoodfp.R;
import com.example.ayaali.fastfoodfp.models.Recipe;
import com.example.ayaali.fastfoodfp.store.ContentProvider;
import com.example.ayaali.fastfoodfp.store.RecipeTable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AyaAli on 20/08/2017.
 */

public class DetailsFragment extends Fragment {
    @BindView(R.id.recipeName)
    TextView titel;
    @BindView(R.id.Ingredients)
    TextView Ingredients;
    @BindView(R.id.imageView)
    ImageView background;



    @BindView(R.id.imageButton)
    ImageButton imageButton;

    public static final String ARG_ITEM_ID = "item_id";
    private static Recipe model;
   // private DataBaseHelper dataBase;
    private  String video_id;

    public LinearLayoutManager mLayoutManager;
    /* public static SharedPreferences settings;
     public static int index=0;*/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            model = (Recipe) getArguments().getSerializable(ARG_ITEM_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, view);


        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

               addRecipe(model);
            }
        });

        titel.setText(model.getNameRecipe());
        String str=model.getIngredients();

        Ingredients.setText(str);
//        rate.setRating(Float.parseFloat(model.getVote_average())/2);

        Picasso.with(getActivity()).load(model.getDetailImage()).into(background);
        return view;
    }
    public void addRecipe(Recipe recipe) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RecipeTable.KEY_Cover, recipe.getCoverImage());
        values.put(RecipeTable.KEY_Name,recipe.getNameRecipe());
        values.put(RecipeTable.KEY_DETAILIMAGE,recipe.getDetailImage());
        values.put(RecipeTable.KEY_INGREDIENTS,recipe.getIngredients());
        // Inserting Row
        //db.insert(TABLE_MOVIES, null, values);
        //db.close(); // Closing database connection
        ContentProvider contentProvider=new ContentProvider(getContext());
        contentProvider.insert(ContentProvider.CONTENT_URI_add,values);
    }
}
