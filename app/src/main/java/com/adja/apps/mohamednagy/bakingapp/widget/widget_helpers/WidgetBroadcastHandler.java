package com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers;

import android.content.Context;
import android.content.Intent;

import com.adja.apps.mohamednagy.bakingapp.widget.widget_container.BakingWidgetProvider;

/**
 * Created by Mohamed Nagy on 4/8/2018 .
 * Project projects submission
 * Time    4:44 AM
 */

public class WidgetBroadcastHandler {
    public static final String SELECTED_RECIPE_ACTION = "selected_recipe";
    public static final String WIDGET_SAVED_DATA      = "data";

    public static void sendBroadcastToWidget(long selectedRecipe, Context context){
        Intent intent = new Intent(context, BakingWidgetProvider.class);
        intent.setAction(SELECTED_RECIPE_ACTION);
        intent.putExtra(WIDGET_SAVED_DATA, selectedRecipe);

        context.sendBroadcast(intent);
    }
}
