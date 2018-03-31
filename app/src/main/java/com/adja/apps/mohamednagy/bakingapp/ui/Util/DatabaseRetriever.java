package com.adja.apps.mohamednagy.bakingapp.ui.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

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

public class DatabaseRetriever {

    public static class StepFragmentRetriever{
        public static List<Step> getStepsFromDatabase(Context context, long recipeId){
            List<Step> steps = new ArrayList<>();

            Cursor cursor = context.getContentResolver().query(
                    UriController.getStepTableUriByRecipeId(recipeId),
                    Projection.STEP_PROJECTION,
                    null,
                    null,
                    null
            );
            Log.e("cursor", "size : " + String.valueOf(cursor.getCount()));
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

            return steps;
        }
    }
}
