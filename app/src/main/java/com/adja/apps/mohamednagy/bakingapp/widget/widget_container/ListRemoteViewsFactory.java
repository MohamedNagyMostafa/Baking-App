package com.adja.apps.mohamednagy.bakingapp.widget.widget_container;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.Projection;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

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
    private Long mRecipeId;
    private List<Ingredient> mIngredientList;

    ListRemoteViewsFactory(Context applicationContext, Intent intent){
        PACKAGE_NAME     = applicationContext.getPackageName();
        init(applicationContext, intent);
    }

    private void init(Context context, Intent intent){
        mWidgetRetriever = new DatabaseRetriever.WidgetRetriever(context.getContentResolver());
        mIngredientList      = new ArrayList<>();
        // Set Recipe Data
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                mRecipeId = bundle.getLong(Extras.WidgetData.WIDGET_DATA_SELECTED_RECIPE);
            }else{
                mRecipeId = null;
            }
        }
    }

    @Override
    public void onCreate() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onDataSetChanged() {
        if(mRecipeId != null)
            mWidgetRetriever.getIngredientFromDatabase(
                    UriController.getIngredientTableUriByRecipeId(mRecipeId),
                    data -> mIngredientList = (List<Ingredient>)data);
    }

    @Override
    public void onDestroy() {
        if(mWidgetRetriever != null) mWidgetRetriever.release();
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mIngredientList.size() == 0) return null;

        return getRemoteViews(mIngredientList.get(position));
    }

    private RemoteViews getRemoteViews(Ingredient ingredient){
        RemoteViews remoteViews = new RemoteViews(PACKAGE_NAME, R.layout.widget_item_view);
        remoteViews.setTextViewText(R.id.wd_ingredient_name, ingredient.getIngredient());
        remoteViews.setTextViewText(R.id.wd_measure_unit, ingredient.getMeasure());
        remoteViews.setTextViewText(R.id.wd_measure_quantity, String.valueOf(ingredient.getQuantity()));

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
