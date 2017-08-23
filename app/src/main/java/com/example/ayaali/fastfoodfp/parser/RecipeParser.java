package com.example.ayaali.fastfoodfp.parser;

import android.util.Log;

import com.example.ayaali.fastfoodfp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by AyaAli on 19/08/2017.
 */

public class RecipeParser {

    public static List<Recipe> parseStringToJson(String data) {
        List<Recipe> myRecipes;

        try {


            JSONObject mainJsonObject = new JSONObject(data);
            JSONArray jsonArray =  mainJsonObject.optJSONArray("matches");
            myRecipes = new ArrayList<>();
            Log.d(TAG,"json");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeJsonObject = jsonArray.getJSONObject(i);
                JSONObject arr1= recipeJsonObject.optJSONObject("imageUrlsBySize");
                String coverImage = arr1.opt("90").toString();

                JSONArray arr2= recipeJsonObject.optJSONArray("smallImageUrls");
                String detailImage =arr2.getString(0);
                String recipeName = recipeJsonObject.optString("recipeName");
                JSONArray ingredients =  recipeJsonObject.optJSONArray("ingredients");

                String  ins="";

                for (int j=0;j<ingredients.length();j++)
                {
                    //JSONObject ingredientObject = ingredients.getJSONObject(j);
                    String ing = ingredients.optString(Integer.parseInt(j+""));
                    ins+=ing+"\n";
                }


                Recipe mRecipe=new Recipe();
                mRecipe.setCoverImage(coverImage);
                mRecipe.setDetailImage(detailImage);
                mRecipe.setNameRecipe(recipeName);
                mRecipe.setIngredients(ins);



                myRecipes.add(mRecipe);
            }

            return myRecipes;

        } catch (Exception e) {
            e.printStackTrace();

        }


        return null;
    }
}
