package com.adja.apps.mohamednagy.bakingapp.widget.widget_container;

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
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;


/**
 * Created by Mohamed Nagy on 4/6/2018 .
 * Project projects submission
 * Time    12:23 PM
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final String PACKAGE_NAME;
    private Long mRecipeId;
    private Cursor mCursor;
    private Context mContext;

    ListRemoteViewsFactory(Context applicationContext, Intent intent){
        PACKAGE_NAME     = applicationContext.getPackageName();
        init(applicationContext, intent);
    }

    private void init(Context context, Intent intent){
        mContext = context;
        // Set Recipe Data
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                mRecipeId = bundle.getLong(Extras.WidgetData.WIDGET_DATA_SELECTED_RECIPE);
                Log.e("widget adapter get id","right");
            }else{
                Log.e("data no detected"," not");
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
            mCursor = mContext.getContentResolver().query(
                    UriController.getIngredientTableUriByRecipeId(mRecipeId),
                    Projection.INGREDIENT_PROJECTION,
                    null,
                    null,
                    null
            );
    }

    @Override
    public void onDestroy() {
        if(mCursor != null)  mCursor.close();
    }

    @Override
    public int getCount() {
        return mCursor == null?0:mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(mCursor == null || mCursor.getCount() == 0) return null;
        return getRemoteViews(position);
    }

    private RemoteViews getRemoteViews(int position){
        mCursor.moveToPosition(position);

        Ingredient ingredient = new Ingredient(
                mCursor.getInt(Projection.INGREDIENT_QUANTITY_COLUMN),
                mCursor.getString(Projection.INGREDIENT_MEASURE_COLUMN),
                mCursor.getString(Projection.INGREDIENT_COLUMN)
        );

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
