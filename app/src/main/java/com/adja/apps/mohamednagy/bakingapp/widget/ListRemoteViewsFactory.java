package com.adja.apps.mohamednagy.bakingapp.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.Projection;
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
    //private DatabaseRetriever.WidgetRetriever mWidgetRetriever;
    private final String PACKAGE_NAME;
    //private List<Recipe> mRecipeList;
    private ContentResolver contentResolver;
    private Cursor cursor;

    ListRemoteViewsFactory(Context applicationContext){
//        mWidgetRetriever = new DatabaseRetriever.WidgetRetriever(applicationContext.getContentResolver());
//        mRecipeList      = new ArrayList<>();
        PACKAGE_NAME     = applicationContext.getPackageName();
        contentResolver = applicationContext.getContentResolver();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
//        mWidgetRetriever.getRecipesFromDatabase(UriController.getRecipeTableUri(), recipes -> mRecipeList = recipes);
        cursor = contentResolver.query(
                UriController.getRecipeTableUri(),
                Projection.RECIPE_PROJECTION,
                null,
                null,
                null
        );
        Log.e("get data"," data getting");
    }

    @Override
    public void onDestroy() {
        if(cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        return cursor != null ? cursor.getCount():0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(cursor == null || cursor.getCount() == 0) return null;
        cursor.moveToPosition(i);
        RemoteViews remoteViews = new RemoteViews(PACKAGE_NAME, R.layout.widget_item_view);
        remoteViews.setTextViewText(R.id.recipe_name_text_view, cursor.getString(Projection.RECIPE_NAME_COLUMN));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
