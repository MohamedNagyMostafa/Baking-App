package com.adja.apps.mohamednagy.bakingapp.database.helper;

import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;

/**
 * Created by Mohamed Nagy on 3/21/2018 .
 * Project projects submission
 * Time    11:01 AM
 */

public class Projection {
    public static final String[] RECIPE_PROJECTION = new String[]{
            DbContent.Recipe._ID,
            DbContent.Recipe.RECIPE_NAME_COLUMN,
            DbContent.Recipe.RECIPE_IMAGE_COLUMN,
            DbContent.Recipe.RECIPE_SERVING_COLUMN
    };

    public static final int RECIPE_ID_COLUMN      = 0;
    public static final int RECIPE_NAME_COLUMN    = 1;
    public static final int RECIPE_IMAGE_COLUMN   = 2;
    public static final int RECIPE_SERVING_COLUMN = 3;

    public static final String[] STEP_PROJECTION = new String[]{
            DbContent.Step._ID,
            DbContent.Step.STEP_SHORT_DESCRIPTION_COLUMN,
            DbContent.Step.STEP_DESCRIPTION_COLUMN,
            DbContent.Step.STEP_THUMBNAIL_COLUMN,
            DbContent.Step.STEP_VIDEO_URL_COLUMN
    };
    public static final int STEP_SHORT_DESCRIPTION_COLUMN    = 1;
    public static final int STEP_DESCRIPTION_COLUMN          = 2;
    public static final int STEP_THUMBNAIL_COLUMN            = 3;
    public static final int STEP_VIDEO_URL_COLUMN            = 4;

    public static final String[] INGREDIENT_PROJECTION = new String[]{
            DbContent.Ingredient._ID,
            DbContent.Ingredient.INGREDIENT_QUANTITY_COLUMN,
            DbContent.Ingredient.INGREDIENT_MEASURE_COLUMN,
            DbContent.Ingredient.INGREDIENT_COLUMN,
    };

    public static final int INGREDIENT_QUANTITY_COLUMN = 1;
    public static final int INGREDIENT_MEASURE_COLUMN  = 2;
    public static final int INGREDIENT_COLUMN          = 3;

    public static final String[] RECIPE_INGREDIENT_STEP_JOIN = new String[]{
            DbContent.Recipe.TABLE_NAME + "." + DbContent.Recipe._ID,
            DbContent.Step.STEP_DESCRIPTION_COLUMN,
            DbContent.Ingredient.INGREDIENT_QUANTITY_COLUMN,
            DbContent.Recipe.RECIPE_NAME_COLUMN
    };

    public static final int STEP_DESCRIPTION_JOIN_COLUMN     = 1;
    public static final int INGREDIENT_QUANTITY_JOIN_COLUMN  = 2;
    public static final int RECIPE_NAME_JOIN_COLUMN          = 3;
}
