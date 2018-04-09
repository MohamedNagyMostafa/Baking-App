package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationPaneSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system.SaverSystemController;

public class MainActivity extends AppCompatActivity {

    public final SaverSystemController SAVER_SYSTEM_CONTROLLER = new SaverSystemController(this.getSupportFragmentManager());

    public static final int TABLET_SCREEN_WIDTH = 0xAB;
    public static final int PHONE_SCREEN_WIDTH  = 0xAC;

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String INGREDIENT_FRAGMENT_TAG = "gradient-fg";

    private static final String RECIPE_SAVER_SYSTEM_ID = "recipe_sv.sys";
    private static final String STEP_SAVER_SYSTEM_ID = "step_sv.sys";
    private static final String INGREDIENT_SAVER_SYSTEM_ID = "ingredient_sv.sys";

    private NavigationBottomSystem mNavigationBottomSystem;
    private NavigationPaneSystem   mNavigationPaneSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();
        switch (getScreenType()){
            case PHONE_SCREEN_WIDTH:
                Log.e("phone","detect");
                initializeScreenAsPhone();
                break;
            case TABLET_SCREEN_WIDTH:
                initializeScreenAsTablet();
                break;
        }
    }

    private void initializeScreenAsPhone(){
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mNavigationBottomSystem = new NavigationBottomSystem(this, R.id.fragment);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);

        addFragmentsToNavigationBottomSys();
    }

    private void initializeScreenAsTablet(){
        mNavigationPaneSystem = new NavigationPaneSystem(this);

        addFragmentsToNavigationPaneSys();
    }

    // Setup Navigation Bottom System
    private void addFragmentsToNavigationBottomSys(){
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        StepFragment stepFragment             = new StepFragment();
        IngredientFragment IngredientFragment = new IngredientFragment();

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.ingredient_nav;

        // Connect with Navigation Sys.
        recipeListFragment.addListener(mNavigationBottomSystem);
        IngredientFragment.addListener(mNavigationBottomSystem);
        stepFragment.addListener(mNavigationBottomSystem);

        // Navigation Item
        recipeListFragment.setNavigationItem(HOME_NAV);
        IngredientFragment.setNavigationItem(GRADIENT_NV);
        stepFragment.setNavigationItem(STEP_NAV);


        mNavigationBottomSystem.put(recipeListFragment, RECIPE_FRAGMENT_TAG);
        mNavigationBottomSystem.put(IngredientFragment, INGREDIENT_FRAGMENT_TAG);
        mNavigationBottomSystem.put(stepFragment, STEP_FRAGMENT_TAG);
    }

    // Setup Navigation Pane System
    private void addFragmentsToNavigationPaneSys(){
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        StepFragment stepFragment             = new StepFragment();
        IngredientFragment IngredientFragment = new IngredientFragment();

        // Connect with Navigation Sys.
        recipeListFragment.addListener(mNavigationPaneSystem);
        IngredientFragment.addListener(mNavigationPaneSystem);
        stepFragment.addListener(mNavigationPaneSystem);

        mNavigationPaneSystem.put(recipeListFragment, RECIPE_FRAGMENT_TAG);
        mNavigationPaneSystem.put(IngredientFragment, INGREDIENT_FRAGMENT_TAG);
        mNavigationPaneSystem.put(stepFragment,       STEP_FRAGMENT_TAG);

        // Add Frames
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.recipeListFrame, RECIPE_FRAGMENT_TAG);
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.stepFrame, STEP_FRAGMENT_TAG);
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.ingredientListFrame, INGREDIENT_FRAGMENT_TAG);
    }

    public int getScreenType(){
        return findViewById(R.id.recipeListFrame) != null?TABLET_SCREEN_WIDTH:PHONE_SCREEN_WIDTH;
    }


    @Override
    protected void onResume() {
        handleSaverSystem();
        super.onResume();
        switch (getScreenType()){
            case TABLET_SCREEN_WIDTH:
                mNavigationPaneSystem.launchFragments();
                initializeState(mNavigationPaneSystem);
                break;
            case PHONE_SCREEN_WIDTH:
                mNavigationBottomSystem.launchCurrentFragment();
                initializeState(mNavigationBottomSystem);
                break;
        }
    }

    public void initializeState(NavigationSystem navigationSystemClass){
        FragmentNav recipeListFragment = (FragmentNav) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT_TAG);
        FragmentNav IngredientFragment = (FragmentNav) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT_TAG);
        FragmentNav stepFragment       = (FragmentNav) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT_TAG);

        // Connect with Navigation Sys.
        recipeListFragment.addListener(navigationSystemClass);
        IngredientFragment.addListener(navigationSystemClass);
        stepFragment.addListener(navigationSystemClass);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onSaveViewInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("caaaaaaaaled","dasdasdasdasdas");
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onRestoreViewInstance(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

    private void handleSaverSystem(){
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(StepFragment.class, STEP_SAVER_SYSTEM_ID);
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(RecipeListFragment.class, RECIPE_SAVER_SYSTEM_ID);
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(IngredientFragment.class, INGREDIENT_SAVER_SYSTEM_ID);
    }

}
