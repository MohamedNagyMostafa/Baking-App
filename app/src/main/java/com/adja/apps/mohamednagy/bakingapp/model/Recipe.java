package com.adja.apps.mohamednagy.bakingapp.model;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class Recipe {
    private long mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private int mServings;
    private String mImageURL;

    public Recipe(){}
    public Recipe(long id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, @Nullable String imageURL){
        mId          = id;
        mName        = name;
        mIngredients = ingredients;
        mSteps       = steps;
        mServings    = servings;
        mImageURL    = imageURL;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public String getImageURL() {
        return mImageURL;
    }
}
