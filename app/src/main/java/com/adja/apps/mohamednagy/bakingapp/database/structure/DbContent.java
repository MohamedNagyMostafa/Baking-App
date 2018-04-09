package com.adja.apps.mohamednagy.bakingapp.database.structure;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed Nagy on 3/21/2018 .
 * Project projects submission
 * Time    11:00 AM
 */

public class DbContent {
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String REFERENCES  = "REFERENCES";
    private static final String FOREIGN_KEY = "FOREIGN KEY";

    private static final String SPACE = " ";

    private static final String TEXT_TYPE    = "TEXT";
    private static final String INTEGER_TYPE = "INTEGER";
    private static final String BLOB_TYPE    = "BLOB";
    private static final String REAL_TYPE    = "REAL";

    private static final String NOT_NULL     = "NOT NULL";
    private static final String CREATE_TABLE = "CREATE TABLE";

    private static final String INNER_JOIN   = "INNER JOIN";
    private static final String ON           = "ON";

    public static final String CONTENT_AUTHORITY = "com.adja.apps.mohamednagy.bakingapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" +  CONTENT_AUTHORITY);

    public static class Recipe implements BaseColumns {
        public static final String TABLE_NAME = "recipe";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String RECIPE_NAME_COLUMN    = "name";
        public static final String RECIPE_SERVING_COLUMN = "serving";
        public static final String RECIPE_IMAGE_COLUMN   = "image";

        public static String CREATE_RECIPE_TABLE = CREATE_TABLE + SPACE +
                TABLE_NAME + "(" +
                _ID                   + SPACE + INTEGER_TYPE + SPACE            + PRIMARY_KEY + "," +
                RECIPE_NAME_COLUMN    + SPACE + TEXT_TYPE    + SPACE + NOT_NULL               + "," +
                RECIPE_SERVING_COLUMN + SPACE + INTEGER_TYPE                                  + "," +
                RECIPE_IMAGE_COLUMN   + SPACE + BLOB_TYPE                                     + ");";

    }

    public static class Ingredient implements BaseColumns{
        public static final String TABLE_NAME = "ingredient";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String INGREDIENT_QUANTITY_COLUMN    = "quantity";
        public static final String INGREDIENT_MEASURE_COLUMN     = "measure";
        public static final String INGREDIENT_COLUMN             = "ingredient";
        public static final String INGREDIENT_RECIPE_ID_COLUMN   = "rec_id";

        public static String CREATE_INGREDIENT_TABLE = CREATE_TABLE + SPACE +
                TABLE_NAME + "(" +
                _ID                         + SPACE + INTEGER_TYPE + SPACE            + PRIMARY_KEY  + ","+
                INGREDIENT_QUANTITY_COLUMN  + SPACE + INTEGER_TYPE + SPACE + NOT_NULL                + ","+
                INGREDIENT_MEASURE_COLUMN   + SPACE + TEXT_TYPE    + SPACE + NOT_NULL                + ","+
                INGREDIENT_COLUMN           + SPACE + TEXT_TYPE    + SPACE + NOT_NULL                + ","+
                INGREDIENT_RECIPE_ID_COLUMN + SPACE + INTEGER_TYPE + SPACE                           + ","+

                FOREIGN_KEY + SPACE + "(" +INGREDIENT_RECIPE_ID_COLUMN + ")" +SPACE + REFERENCES + SPACE +
                Recipe.TABLE_NAME + "(" + Recipe._ID + "));";
    }

    public static class Step implements BaseColumns {
        public static final String TABLE_NAME = "step";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String STEP_SHORT_DESCRIPTION_COLUMN = "short_description";
        public static final String STEP_DESCRIPTION_COLUMN       = "description";
        public static final String STEP_VIDEO_URL_COLUMN         = "video_url";
        public static final String STEP_THUMBNAIL_COLUMN         = "thumbnail";
        public static final String STEP_RECIPE_ID_COLUMN         = "recp_id";

        public static final String CREATE_STEP_TABLE = CREATE_TABLE + SPACE +
                TABLE_NAME + "(" +
                _ID                           + SPACE + INTEGER_TYPE + SPACE + PRIMARY_KEY  + "," +
                STEP_SHORT_DESCRIPTION_COLUMN + SPACE + TEXT_TYPE                           + "," +
                STEP_DESCRIPTION_COLUMN       + SPACE + TEXT_TYPE                           + "," +
                STEP_VIDEO_URL_COLUMN         + SPACE + TEXT_TYPE                           + "," +
                STEP_THUMBNAIL_COLUMN         + SPACE + BLOB_TYPE                           + "," +
                STEP_RECIPE_ID_COLUMN         + SPACE + INTEGER_TYPE                        + "," +

                FOREIGN_KEY + SPACE + "(" + STEP_RECIPE_ID_COLUMN + ")" + SPACE + REFERENCES + SPACE +
                Recipe.TABLE_NAME + "(" + Recipe._ID + "));";
    }


    public static final String RECIPE_JOIN_WITH_STEP_INGREDIENT_QUERY =
            Recipe.TABLE_NAME + SPACE +
                    INNER_JOIN + SPACE + Ingredient.TABLE_NAME + SPACE +
                    ON + SPACE + Recipe.TABLE_NAME + "." + Recipe._ID + "=" +
                    Ingredient.TABLE_NAME + "." + Ingredient.INGREDIENT_RECIPE_ID_COLUMN + SPACE +

                    INNER_JOIN + SPACE + Step.TABLE_NAME + SPACE +
                    ON + SPACE + Step.TABLE_NAME + "." + Step.STEP_RECIPE_ID_COLUMN + "=" + Recipe.TABLE_NAME + "." + Recipe._ID;
}
