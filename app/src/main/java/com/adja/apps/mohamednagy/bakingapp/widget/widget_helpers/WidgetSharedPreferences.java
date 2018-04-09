package com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

/**
 * Created by Mohamed Nagy on 4/8/2018 .
 * Project projects submission
 * Time    6:09 AM
 */

public class WidgetSharedPreferences {

    private static final String SHARED_PREFERENCES_NAME       = "com.adja.apps.mohamednagy.bakingapp.widget_shared_preferences";
    private static final String SHARED_PREFERENCES_SAVED_DATA = Extras.WidgetData.WIDGET_SHARD_PREFERENCES_SELECTED_RECIPE;
    private static final Long NULL_DATA = -1L;

    public static void saveData(Long recipeId, Context context){
         context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit()
                .putLong(SHARED_PREFERENCES_SAVED_DATA, recipeId)
                .apply();
    }

    public static Long getData(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        long data =  sharedPreferences.getLong(SHARED_PREFERENCES_SAVED_DATA, NULL_DATA);
        return data == NULL_DATA?null:data;
    }
}
