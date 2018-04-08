package com.adja.apps.mohamednagy.bakingapp.ui.util;

/**
 * Created by Mohamed Nagy on 3/30/2018 .
 * Project projects submission
 * Time    5:11 PM
 */

public class Extras {

    public static class StepFragmentData{
        public static final String RECIPE_ID             = "recipe_id";
        public static final String CURRENT_STEP_POSITION = "step_ps";
        public static final String CURRENT_MEDIA_MINT    = "media_ps";
    }

    public static class RecipeListFragmentData{
        public static final String SELECTED_RECIPE_ID             = "recipe_selected_id";
        public static final String RECIPE_RECYCLE_SCROLL_POSITION = "recipe_recycle_pos";
    }

    public static class IngredientData{
        public static final String RECIPE_ID = StepFragmentData.RECIPE_ID;
        public static final String INGREDIENT_LIST_SCROLL_POSITION = "ingredient_list_pos";
    }

    public static class NavigationSystemData{
        public static final String SELECTED_NAVIGATION_BOTTOM_ITEM = "nav_item";
    }

    public static class WidgetData{
        public static final String WIDGET_SHARD_PREFERENCES_SELECTED_RECIPE = "wd_pre_shr_selected_recipe";
        public static final String WIDGET_DATA_SELECTED_RECIPE              = "wd_selected_recipe_data";

    }
}
