package com.adja.apps.mohamednagy.bakingapp.database.helper;

import android.net.Uri;

import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class UriController {

    private static final Uri RECIPE_TABLE_URI = DbContent.Recipe.CONTENT_URI;
    private static final Uri INGREDIENT_TABLE_URI = DbContent.Ingredient.CONTENT_URI;
    private static final Uri STEP_TABLE_URI = DbContent.Step.CONTENT_URI;

    public static Uri getRecipeTableUri(){
        return RECIPE_TABLE_URI;
    }

    public static Uri getIngredientTableUri(){
        return INGREDIENT_TABLE_URI;
    }

    public static Uri getStepTableUri(){
        return STEP_TABLE_URI;
    }

    public static Uri getRecipeStepIngredientJoinUri(){
        return RECIPE_TABLE_URI.buildUpon().appendPath(DbContent.Step.TABLE_NAME)
                .appendPath(DbContent.Ingredient.TABLE_NAME).build();
    }
}