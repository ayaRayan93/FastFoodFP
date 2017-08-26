package com.example.ayaali.fastfoodfp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayaali.fastfoodfp.R;
import com.example.ayaali.fastfoodfp.activity.RecipeDetailsActivity;
import com.example.ayaali.fastfoodfp.activity.RecipesListActivity;
import com.example.ayaali.fastfoodfp.fragment.DetailsFragment;
import com.example.ayaali.fastfoodfp.fragment.RecipeFragment;
import com.example.ayaali.fastfoodfp.models.Recipe;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;
import static android.support.v7.recyclerview.R.styleable.RecyclerView;

/**
 * Created by AyaAli on 19/08/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> DataSet;
    private  Context context;

    public RecipeAdapter(Context cont,List<Recipe> dataSet)
    {
        context=cont;
        DataSet = dataSet;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.image)ImageView poster;
        @BindView(R.id.title)TextView title;


        public ViewHolder(View v)
        {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getPosition() + " clicked.");
                    if (RecipesListActivity.mTwoPane)
                    {
                        Bundle arguments = new Bundle();
                        arguments.putSerializable("j",DataSet.get(getPosition()));

                        RecipeFragment fragment = new RecipeFragment();
                        fragment.setArguments(arguments);
                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    }
                    else
                    {
                        Context context2 = v.getContext();
                        Intent intent = new Intent(context2, RecipeDetailsActivity.class);
                        intent.putExtra(DetailsFragment.ARG_ITEM_ID,  DataSet.get(getPosition()));
                        context2.startActivity(intent);
                    }
                }
            });
            ButterKnife.bind(this,v);

        }

        public ImageView getPoster() {
            return poster;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);

        return  new RecipeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        if (DataSet.get(position) != null)
        {
            Log.d("", "Element " + position + " set.");
            holder.getTitle().setText(DataSet.get(position).getNameRecipe());



            // Feed image
            if (DataSet.get(position).getCoverImage() != null)
            {
                //download image.
                Picasso.with(context).load(DataSet.get(position).getCoverImage()).into(holder.getPoster());
            }

        }
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }
}
