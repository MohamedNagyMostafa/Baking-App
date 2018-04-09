package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationPaneSystem;

public class MainActivity extends AppCompatActivity {

    public static final int TABLET_SCREEN_WIDTH = 0xAB;
    public static final int PHONE_SCREEN_WIDTH  = 0xAC;

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String INGREDIENT_FRAGMENT_TAG = "gradient-fg";

    private static final SaverSystem RECIPE_SAVER_SYSTEM      = new SaverSystem();
    private static final SaverSystem STEP_SAVER_SYSTEM       = new SaverSystem();
    private static final SaverSystem INGREDIENT_SAVER_SYSTEM = new SaverSystem();

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

        mNavigationBottomSystem = new NavigationBottomSystem(getSupportFragmentManager(), R.id.fragment);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);

        addFragmentsToNavigationBottomSys();
    }

    private void initializeScreenAsTablet(){
        mNavigationPaneSystem = new NavigationPaneSystem(getSupportFragmentManager());

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
        // Add Data Saver Sys.
        recipeListFragment.addSaverSystem(RECIPE_SAVER_SYSTEM);
        IngredientFragment.addSaverSystem(INGREDIENT_SAVER_SYSTEM);
        stepFragment.addSaverSystem(STEP_SAVER_SYSTEM);
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
        // Add Data Saver Sys.
        recipeListFragment.addSaverSystem(RECIPE_SAVER_SYSTEM);
        IngredientFragment.addSaverSystem(INGREDIENT_SAVER_SYSTEM);
        stepFragment.addSaverSystem(STEP_SAVER_SYSTEM);

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
        super.onResume();
        switch (getScreenType()){
            case TABLET_SCREEN_WIDTH:
                mNavigationPaneSystem.launchFragments();
                break;
            case PHONE_SCREEN_WIDTH:
                mNavigationBottomSystem.launchCurrentFragment();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onSaveViewInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onRestoreViewInstance(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

}
