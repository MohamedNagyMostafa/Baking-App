package com.adja.apps.mohamednagy.bakingapp.widget.widget_container;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers.WidgetBroadcastHandler;
import com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers.WidgetSharedPreferences;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    public static final int BEST_WIDGET_APP_HEIGHT = 230;
    public static final int BEST_WIDGET_APP_WIDTH = 280;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Long recipeId) {
        Bundle option = appWidgetManager.getAppWidgetOptions(appWidgetId);

        int height = option.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int width = option.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_ingredient);

        if(height >= BEST_WIDGET_APP_HEIGHT && width >= BEST_WIDGET_APP_WIDTH){
            views = getRecipeRemoteViews(context, R.layout.widget_recipe_ingredient);
        }else{
           views = getRecipeRemoteViews(context, R.layout.widget_recipe__serving_ingredient);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingWidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds, WidgetSharedPreferences.getData(context));
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, Long recipeId){

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent != null){
            String action = intent.getAction();
            assert action != null;

            switch (action){
                case WidgetBroadcastHandler.SELECTED_RECIPE_ACTION:
                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        Long recipeId = bundle.getLong(WidgetBroadcastHandler.WIDGET_SAVED_DATA);
                        WidgetSharedPreferences.saveData(recipeId, context);
                        updateAppWidgetsWithNewData(recipeId, context);
                    }
                    break;
            }
        }
    }

    private void updateAppWidgetsWithNewData(Long recipeId, Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));

        BakingWidgetProvider.updateBakingWidgets(context, appWidgetManager, widgetIds, recipeId);
    }


}

