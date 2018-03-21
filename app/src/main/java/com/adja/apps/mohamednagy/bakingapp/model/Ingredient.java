package com.adja.apps.mohamednagy.bakingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class Ingredient {
    @SerializedName("quantity")
    private int mQuantity;
    @SerializedName("measure")
    private String mMeasure;
    @SerializedName("ingredient")
    private String mIngredient;

    public Ingredient(){}
    public Ingredient(int quantity, String measure, String ingredient){
        mQuantity   = quantity;
        mMeasure    = measure;
        mIngredient = ingredient;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public void setIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }
}
