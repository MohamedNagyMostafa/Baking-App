package com.adja.apps.mohamednagy.bakingapp.ui.util;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.adja.apps.mohamednagy.bakingapp.database.helper.Projection;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
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
                    Projection.STEP_PROJECTION,
                    null,
                    null,
                    null
            );

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
