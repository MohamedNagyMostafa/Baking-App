package com.adja.apps.mohamednagy.bakingapp.ui.util;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.database.helper.Projection;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/30/2018 .
 * Project projects submission
 * Time    5:15 PM
 */

public class DatabaseRetriever{
    /**
     * Retrieval system for ingredient fragment.
     */
    public static class IngredientFragmentRetriever extends AsyncQueryHandler{
        private static final int TOKEN = 0x01C;

        private OnCompletedListener mOnCompletedListener;

        public IngredientFragmentRetriever(ContentResolver contentResolver) {
            super(contentResolver);
        }

        // Retrieve Ingredients for specific recipe.
        public synchronized void getIngredientFromDatabase(Uri uri, @NonNull OnCompletedListener onCompletedListener){
            mOnCompletedListener = onCompletedListener;

            startQuery(
                    TOKEN,
                    null,
                    uri,
                    Projection.INGREDIENT_PROJECTION,
                    null,
                    null,
                    null
            );

        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            List<Ingredient> ingredients = new ArrayList<>();

            assert cursor != null;
            while(cursor.moveToNext()){
                Ingredient ingredient = new Ingredient(
                        cursor.getInt(Projection.INGREDIENT_QUANTITY_COLUMN),
                        cursor.getString(Projection.INGREDIENT_MEASURE_COLUMN),
                        cursor.getString(Projection.INGREDIENT_COLUMN)
                );

                ingredients.add(ingredient);
            }

            cursor.close();

            if(mOnCompletedListener != null)
                mOnCompletedListener.onCompleted(ingredients);
        }

        /**
         * Stop Any Working Operation.
         */
        public void release(){
            cancelOperation(TOKEN);
        }

        /**
         * Called when operation is completed.
         */
        public interface OnCompletedListener{
            void onCompleted(List<Ingredient> ingredients);
        }
    }
    /**
     * Retrieval system for step fragment.
     */
    public static class StepFragmentRetriever extends AsyncQueryHandler{
        private static final int TOKEN = 0x01A;

        private OnCompletedListener mOnCompletedListener;

        public StepFragmentRetriever(ContentResolver contentResolver) {
            super(contentResolver);
        }

        // Retrieve Steps for specific recipe.
        public synchronized void getStepsFromDatabase(Uri uri, @NonNull OnCompletedListener onCompletedListener){
            mOnCompletedListener = onCompletedListener;

            startQuery(
                    TOKEN,
                    null,
                    uri,
                    Projection.STEP_PROJECTION,
                    null,
                    null,
                    null
            );

        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            List<Step> steps = new ArrayList<>();

            assert cursor != null;
            while(cursor.moveToNext()){
                Step step = new Step(
                        0,
                        cursor.getString(Projection.STEP_SHORT_DESCRIPTION_COLUMN),
                        cursor.getString(Projection.STEP_DESCRIPTION_COLUMN),
                        cursor.getString(Projection.STEP_VIDEO_URL_COLUMN),
                        cursor.getString(Projection.STEP_THUMBNAIL_COLUMN)
                );

                steps.add(step);
            }

            cursor.close();

            if(mOnCompletedListener != null)
                mOnCompletedListener.onCompleted(steps);
        }

        /**
         * Stop Any Working Operation.
         */
        public void release(){
            cancelOperation(TOKEN);
        }

        /**
         * Called when operation is completed.
         */
        public interface OnCompletedListener{
            void onCompleted(List<Step> steps);
        }
    }
    /**
     * Retrieval system for recipe fragment.
     */
    public static class RecipeFragmentRetriever extends AsyncQueryHandler{
        private static final int TOKEN = 0x01B;

        private OnCompletedListener mOnCompletedListener;

        public RecipeFragmentRetriever(ContentResolver contentResolver) {
            super(contentResolver);
        }

        // Retrieve all recipes.
        public synchronized void getRecipesFromDatabase(Uri uri, @NonNull OnCompletedListener onCompletedListener){
            mOnCompletedListener = onCompletedListener;

            startQuery(
                    TOKEN,
                    null,
                    uri,
                    Projection.RECIPE_PROJECTION,
                    null,
                    null,
                    null
            );

        }

        // Insert recipes
        public synchronized void insertRecipesToDatabase(Uri uri, List<Recipe> recipes){
            for(Recipe recipe: recipes){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContent.Recipe.RECIPE_NAME_COLUMN, recipe.getName());
                contentValues.put(DbContent.Recipe.RECIPE_IMAGE_COLUMN, recipe.getImageURL());
                contentValues.put(DbContent.Recipe.RECIPE_SERVING_COLUMN, recipe.getServings());
                startInsert(TOKEN, null, uri, contentValues);
            }
        }

        // Insert steps
        public synchronized void insertStepsToDatabase(Uri uri, List<Step> steps, long recipeId){
            for(Step step: steps){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContent.Step.STEP_RECIPE_ID_COLUMN, recipeId);
                contentValues.put(DbContent.Step.STEP_VIDEO_URL_COLUMN, step.getVideoLink());
                contentValues.put(DbContent.Step.STEP_THUMBNAIL_COLUMN, step.getThumbnailURL());
                contentValues.put(DbContent.Step.STEP_DESCRIPTION_COLUMN, step.getDescription());
                contentValues.put(DbContent.Step.STEP_SHORT_DESCRIPTION_COLUMN, step.getShortDescription());

                startInsert(TOKEN, null, uri, contentValues);
            }
        }

        // Insert gradient
        public synchronized void insertIngredientToDatabase(Uri uri, List<Ingredient> ingredients, long recipeId){
            for(Ingredient ingredient: ingredients){
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbContent.Ingredient.INGREDIENT_RECIPE_ID_COLUMN, recipeId);
                contentValues.put(DbContent.Ingredient.INGREDIENT_MEASURE_COLUMN, ingredient.getMeasure());
                contentValues.put(DbContent.Ingredient.INGREDIENT_COLUMN, ingredient.getIngredient());
                contentValues.put(DbContent.Ingredient.INGREDIENT_QUANTITY_COLUMN, ingredient.getQuantity());

                startInsert(TOKEN, null, uri, contentValues);
            }
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            List<Recipe> recipes = new ArrayList<>();

            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Recipe recipe = new Recipe(
                            cursor.getLong(Projection.RECIPE_ID_COLUMN),
                            cursor.getString(Projection.RECIPE_NAME_COLUMN),
                            null,
                            null,
                            cursor.getInt(Projection.RECIPE_SERVING_COLUMN),
                            cursor.getString(Projection.RECIPE_IMAGE_COLUMN)
                    );
                    Log.e("ids", "id = " + String.valueOf(recipe.getId()));
                    recipes.add(recipe);
                }

                cursor.close();
            }
            if(mOnCompletedListener != null)
                mOnCompletedListener.onCompleted(recipes);
        }

        /**
         * Stop Any Working Operation.
         */
        public void release(){
            cancelOperation(TOKEN);
        }

        /**
         * Called when operation is completed.
         */
        public interface OnCompletedListener{
            void onCompleted(List<Recipe> recipes);
        }
    }
}
