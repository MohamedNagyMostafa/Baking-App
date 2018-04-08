package com.adja.apps.mohamednagy.bakingapp.widget.widget_helpers;

import android.app.IntentService;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adja.apps.mohamednagy.bakingapp.widget.widget_container.BakingWidgetProvider;

/**
 * Created by Mohamed Nagy on 4/8/2018 .
 * Project projects submission
 * Time    4:50 AM
 */

public class IngredientDatabaseRetrievalService {
//    extends
//} IntentService {
//
//    private static final String RETRIEVAL_SERVICE      = "retrieval service";
//    public static final String ACTION_RECIPE_SELECTED = "com.adja.apps.mohamednagy.bakingapp.recipe_selected_action";
//
//    public IngredientDatabaseRetrievalService() {
//        super(RETRIEVAL_SERVICE);
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if(intent != null){
//            final String action = intent.getAction();
//            assert action != null;
//            switch (action){
//                case ACTION_RECIPE_SELECTED:
//                    handleSelectedRecipeAction(intent);
//                    break;
//            }
//        }
//    }
//
//    private void handleSelectedRecipeAction(Intent intent){
//        Bundle extras = intent.getExtras();
//        if(extras != null){
//            Long selectedRecipeId = extras.getLong(WidgetBroadcastHandler.SELECTED_RECIPE_ACTION);
//
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//            int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
//
//            BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, widgetIds, selectedRecipeId);
//        }
//
//    }

}
