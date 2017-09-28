package com.example.android.recipefinder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hp on 24/09/2017.
 */

public class RecipeResponse {


    @SerializedName("recipes")
    public List<Recipe> recipes;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }


}



