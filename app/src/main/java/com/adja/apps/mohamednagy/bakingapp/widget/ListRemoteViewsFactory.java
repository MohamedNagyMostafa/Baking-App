package com.adja.apps.mohamednagy.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 4/6/2018 .
 * Project projects submission
 * Time    12:23 PM
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private DatabaseRetriever.WidgetRetriever mWidgetRetriever;
    private final String PACKAGE_NAME;
    private List<Recipe> mRecipeList;

    ListRemoteViewsFactory(Context applicationContext){
        mWidgetRetriever = new DatabaseRetriever.WidgetRetriever(applicationContext.getContentResolver());
        mRecipeList      = new ArrayList<>();
        PACKAGE_NAME     = applicationContext.getPackageName();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetRetriever.getRecipesFromDatabase(UriController.getRecipeTableUri(), recipes -> mRecipeList = recipes);
    }

    @Override
    public void onDestroy() {
        mWidgetRetriever.release();
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(PACKAGE_NAME, R.layout.widget_item_view);
        remoteViews.setTextViewText(R.id.recipe_name_text_view, mRecipeList.get(i).getName());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
