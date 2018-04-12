package com.adja.apps.mohamednagy.bakingapp.database.helper;

import android.content.UriMatcher;

import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 * Project projects submission
 * Time    11:02 AM
 */

public class DbUriMatcher extends UriMatcher{
    private static final String SPLIT = "/";
    private static final String NUMBER = "#";

    public static final int RECIPE_ID       = 0x0;
    public static final int INGREDIENT_ID   = 0x1;
    public static final int STEP_ID         = 0x2;

    public static final int RECIPE_JOIN_STEP_WITH_INGREDIENT_ID = 0x3;

    public static final int STEP_RECIPE_ID       = 0x4;
    public static final int INGREDIENT_RECIPE_ID = 0x5;

    public static final int RECIPE_WITH_ID       = 0x6;

    public DbUriMatcher() {
        super(NO_MATCH);
        init();
    }

    private void init(){
        // ../recipe
        final String RECIPE_PATH      = DbContent.Recipe.TABLE_NAME;
        // ../ingredient
        final String INGREDIENT_PATH  = DbContent.Ingredient.TABLE_NAME;
        // ../step
        final String STEP_PATH        = DbContent.Step.TABLE_NAME;
        // ../recipe/#
        final String RECIPE_WITH_ID_PATH    = RECIPE_PATH + SPLIT + NUMBER;
        // ../step/recipe/#
        final String STEP_RECIPE_PATH       = STEP_PATH + SPLIT + RECIPE_PATH + SPLIT + NUMBER;
        // ../ingredient/recipe/#
        final String INGREDIENT_RECIPE_PATH = INGREDIENT_PATH + SPLIT + RECIPE_PATH + SPLIT + NUMBER;

        // ../recipe/step/ingredient
        final String RECIPE_JOIN_STEP_WITH_INGREDIENT_PATH =
                RECIPE_PATH + "/" + STEP_PATH + "/" + INGREDIENT_PATH;

        addURI(DbContent.CONTENT_AUTHORITY, RECIPE_PATH,            RECIPE_ID);
        addURI(DbContent.CONTENT_AUTHORITY, INGREDIENT_PATH,        INGREDIENT_ID);
        addURI(DbContent.CONTENT_AUTHORITY, STEP_PATH,              STEP_ID);
        addURI(DbContent.CONTENT_AUTHORITY, STEP_RECIPE_PATH,       STEP_RECIPE_ID);
        addURI(DbContent.CONTENT_AUTHORITY, INGREDIENT_RECIPE_PATH, INGREDIENT_RECIPE_ID);
        addURI(DbContent.CONTENT_AUTHORITY, RECIPE_WITH_ID_PATH,    RECIPE_WITH_ID);

        addURI(DbContent.CONTENT_AUTHORITY, RECIPE_JOIN_STEP_WITH_INGREDIENT_PATH, RECIPE_JOIN_STEP_WITH_INGREDIENT_ID);
    }



}
