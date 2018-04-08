package com.adja.apps.mohamednagy.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    public static final int FIT_WIDGET_APP_SIZE = 300;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e("update", " done");
        Bundle option = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int height = option.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        RemoteViews views;

        if(height >= FIT_WIDGET_APP_SIZE){
            Log.e("done","300 more");
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
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

    private static RemoteViews getRecipeRemoteViews(Context context, int layout){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layout);
        // Connect Adapter
        Intent remoteViewAdapterListService = new Intent(context, ListRemoteViewsService.class);
        remoteViews.setRemoteAdapter(R.id.widget_list_view, remoteViewAdapterListService);
        // Handle Click Event
        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);
        remoteViews.setEmptyView(R.id.widget_list_view, R.id.empty_widget_view);
        return remoteViews;
    }
}

