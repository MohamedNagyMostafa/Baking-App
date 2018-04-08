package com.adja.apps.mohamednagy.bakingapp.database.structure;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.database.helper.DbUriMatcher;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class ContentProviderDatabase extends ContentProvider{

    private DbUriMatcher mUriMatcher = new DbUriMatcher();
    private DbHelper mDbHelper;

    private static final SQLiteQueryBuilder RECIPE_JOIN_INGREDIENT_STEP_JOIN_QUERY = new SQLiteQueryBuilder();
    static {
        RECIPE_JOIN_INGREDIENT_STEP_JOIN_QUERY.setTables(DbContent.RECIPE_JOIN_WITH_STEP_INGREDIENT_QUERY);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int match = mUriMatcher.match(uri);

        switch (match){
            case DbUriMatcher.RECIPE_ID:
                return mDbHelper.getReadableDatabase().query(
                        DbContent.Recipe.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case DbUriMatcher.STEP_ID:
                return mDbHelper.getReadableDatabase().query(
                        DbContent.Step.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case DbUriMatcher.INGREDIENT_ID:
                return mDbHelper.getReadableDatabase().query(
                        DbContent.Ingredient.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case DbUriMatcher.RECIPE_JOIN_STEP_WITH_INGREDIENT_ID:
                return RECIPE_JOIN_INGREDIENT_STEP_JOIN_QUERY.query(
                        mDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case DbUriMatcher.STEP_RECIPE_ID:
                return getStepByRecipeId(uri, projection);
            case DbUriMatcher.INGREDIENT_RECIPE_ID:
                return getIngredientByRecipeId(uri, projection);
            case DbUriMatcher.RECIPE_WITH_ID:
                return getRecipeById(uri, projection);
            default:
                throw new UnsupportedOperationException("Unknown Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int match = mUriMatcher.match(uri);
        long id = -1;
        Log.e("asdddddddddddddddddd",String.valueOf(match));

        switch (match){
            case DbUriMatcher.RECIPE_ID:
                id = mDbHelper.getWritableDatabase().insert(
                        DbContent.Recipe.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            case DbUriMatcher.INGREDIENT_ID:
                id = mDbHelper.getWritableDatabase().insert(
                        DbContent.Ingredient.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            case DbUriMatcher.STEP_ID:
                id = mDbHelper.getWritableDatabase().insert(
                        DbContent.Step.TABLE_NAME,
                        null,
                        contentValues
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri :" + uri);
        }

        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = mUriMatcher.match(uri);

        switch (match){
            case DbUriMatcher.RECIPE_ID:
                return mDbHelper.getWritableDatabase().delete(
                        DbContent.Recipe.TABLE_NAME,
                        selection,
                        selectionArgs
                );
            case DbUriMatcher.INGREDIENT_ID:
                return mDbHelper.getWritableDatabase().delete(
                        DbContent.Ingredient.TABLE_NAME,
                        selection,
                        selectionArgs
                );
            case DbUriMatcher.STEP_ID:
                return mDbHelper.getWritableDatabase().delete(
                        DbContent.Step.TABLE_NAME,
                        selection,
                        selectionArgs
                );
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = mUriMatcher.match(uri);

        switch (match){
            case DbUriMatcher.RECIPE_ID:
                return mDbHelper.getWritableDatabase().update(
                        DbContent.Recipe.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );
            case DbUriMatcher.INGREDIENT_ID:
                return mDbHelper.getWritableDatabase().update(
                        DbContent.Ingredient.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );
            case DbUriMatcher.STEP_ID:
                return mDbHelper.getWritableDatabase().update(
                        DbContent.Step.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs
                );
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        int match = mUriMatcher.match(uri);

        switch (match){
            case DbUriMatcher.RECIPE_ID:
                return bulkInsertByTableName(DbContent.Recipe.TABLE_NAME,
                        values);
            case DbUriMatcher.INGREDIENT_ID:
                return bulkInsertByTableName(DbContent.Ingredient.TABLE_NAME,
                        values);
            case DbUriMatcher.STEP_ID:
                return bulkInsertByTableName(DbContent.Step.TABLE_NAME,
                        values);
            default:
                throw new UnsupportedOperationException("Unknown uri: "+ uri);
        }

    }

    private int bulkInsertByTableName(@NonNull String tableName, @NonNull ContentValues[] values){
        for(ContentValues contentValues: values){
            mDbHelper.getWritableDatabase().insert(
                    tableName,
                    null,
                    contentValues
            );
        }

        return values.length;
    }

    private Cursor getStepByRecipeId(Uri uri, String[] projection){
        long recipeId = ContentUris.parseId(uri);
        String selection = DbContent.Step.STEP_RECIPE_ID_COLUMN + "=?";
        String[] selectionArgs = {
                String.valueOf(recipeId)
        };

        return mDbHelper.getReadableDatabase().query(
                DbContent.Step.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor getIngredientByRecipeId(Uri uri, String[] projection){
        long recipeId = ContentUris.parseId(uri);
        String selection = DbContent.Ingredient.INGREDIENT_RECIPE_ID_COLUMN + "=?";
        String[] selectionArgs = {
                String.valueOf(recipeId)
        };

        return mDbHelper.getReadableDatabase().query(
                DbContent.Ingredient.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    private Cursor getRecipeById(Uri uri, String[] projection){
        long recipeId = ContentUris.parseId(uri);
        String selection = DbContent.Recipe._ID +"=?";
        String[] selectionArgs= {
                String.valueOf(recipeId)
        };

        return mDbHelper.getReadableDatabase().query(
                DbContent.Recipe.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
