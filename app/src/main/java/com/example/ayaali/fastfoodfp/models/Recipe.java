package com.example.ayaali.fastfoodfp.models;

import java.io.Serializable;

/**
 * Created by AyaAli on 19/08/2017.
 */

public class Recipe implements Serializable {

    private String coverImage;
    private String detailImage;
    private String nameRecipe ;
    private String ingredients ;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

    public String getNameRecipe() {
        return nameRecipe;
    }

    public void setNameRecipe(String nameRecipe) {
        this.nameRecipe = nameRecipe;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
