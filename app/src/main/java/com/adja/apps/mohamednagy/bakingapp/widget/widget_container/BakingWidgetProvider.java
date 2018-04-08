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
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;
import com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers.WidgetBroadcastHandler;
import com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers.WidgetSharedPreferences;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    public static final int BEST_WIDGET_APP_HEIGHT = 230;
    public static final int BEST_WIDGET_APP_WIDTH = 280;

    public static final int BEST_WIDGET_APP_LAYOUT_ID = R.layout.widget_recipe__serving_ingredient;
    public static final int NORMAL_WIDGET_APP_LAYOUT_ID = R.layout.widget_recipe_ingredient;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Long recipeId) {
        Bundle option = appWidgetManager.getAppWidgetOptions(appWidgetId);

        int height = option.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int width = option.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        RemoteViews views;

        if(height >= BEST_WIDGET_APP_HEIGHT && width >= BEST_WIDGET_APP_WIDTH){
            Log.e("best wedget","done");
            views = handleWidgetView(context, BEST_WIDGET_APP_LAYOUT_ID, recipeId);
        }else{
            Log.e("normal wedget","done");
            views = handleWidgetView(context, NORMAL_WIDGET_APP_LAYOUT_ID, recipeId);
        }
        // Instruct the widget manager to update the widget
        Log.e("widget","updated");
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
        Log.e("data recieved","done");
        if(intent != null){
            String action = intent.getAction();
            assert action != null;

            switch (action){
                case WidgetBroadcastHandler.SELECTED_RECIPE_ACTION:
                    Log.e("same action","detect right intent");
                    Bundle bundle = intent.getExtras();
                    if(bundle != null){
                        Long recipeId = bundle.getLong(WidgetBroadcastHandler.WIDGET_SAVED_DATA);
                        Log.e("intent","recipe id "+ String.valueOf(recipeId));
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

    @SuppressWarnings("unchecked")
    private static RemoteViews handleWidgetView(Context context, int layout, Long recipeId){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layout);

        if(recipeId != null){
            new DatabaseRetriever.WidgetRetriever(context.getContentResolver())
                    .getRecipeFromDatabase(UriController.getRecipeTableUriByRecipeId(recipeId),
                            data -> {
                                Recipe recipe = (Recipe) data;
                                if(recipe != null){
                                    Log.e("recipe data widget","inserted " + recipe.getName());
                                    remoteViews.setTextViewText(R.id.wd_recipe_name, recipe.getName());
                                    remoteViews.setTextViewText(R.id.wd_serving_size, String.valueOf(recipe.getServings()));
                                }
                            }
                    );
            Intent intent = new Intent(context, ListRemoteViewsService.class);
            intent.putExtra(Extras.WidgetData.WIDGET_DATA_SELECTED_RECIPE, recipeId);

            remoteViews.setRemoteAdapter(R.id.wd_ingredient_grid_view, intent);
            remoteViews.setEmptyView(R.id.wd_ingredient_grid_view, R.id.wd_empty_view);
        }

        return remoteViews;
    }

}

