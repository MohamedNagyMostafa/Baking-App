package com.adja.apps.mohamednagy.bakingapp.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mohamed Nagy on 3/21/2018 .
 * Project projects submission
 * Time    10:59 AM
 */

public class Recipe {
    @SerializedName("id")
    private long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("ingredients")
    private List<Ingredient> mIngredients;
    @SerializedName("steps")
    private List<Step> mSteps;
    @SerializedName("servings")
    private int mServings;
    @SerializedName("image")
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
