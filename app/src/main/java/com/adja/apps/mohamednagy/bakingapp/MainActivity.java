package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.testing_tools.BakingResourceIdle;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationPaneSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system.SaverSystemController;

public class MainActivity extends AppCompatActivity
    implements BakingResourceIdle.onStateChangingListener{

    public final SaverSystemController SAVER_SYSTEM_CONTROLLER = new SaverSystemController(this.getSupportFragmentManager());
    @Nullable private BakingResourceIdle mBakingResourceIdle;

    public static final int TABLET_SCREEN_WIDTH = 0xAB;
    public static final int PHONE_SCREEN_WIDTH  = 0xAC;

    private static final int FRAGMENT_NUMBER = 0x3;

    private static final int RECIPE_FRAGMENT_INDEX     = 0x0;
    private static final int STEP_FRAGMENT_INDEX       = 0x1;
    private static final int INGREDIENT_FRAGMENT_INDEX = 0x2;

    private static final String STEP_FRAGMENT_TAG       = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG     = "recipe-fg";
    private static final String INGREDIENT_FRAGMENT_TAG = "gradient-fg";

    private static final String RECIPE_SAVER_SYSTEM_ID     = "recipe_sv.sys";
    private static final String STEP_SAVER_SYSTEM_ID       = "step_sv.sys";
    private static final String INGREDIENT_SAVER_SYSTEM_ID = "ingredient_sv.sys";

    private NavigationBottomSystem mNavigationBottomSystem;
    private NavigationPaneSystem   mNavigationPaneSystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hide action bar.
        hideActionBar();
        // Initialize Save System.
        handleSaverSystem();
        // Handle Connect Fragments With Navigation System.
        handleFragments(initializeFragments());
    }

    @Override
    protected void onResume() {
        if(mNavigationBottomSystem != null)
            mNavigationBottomSystem.launchCurrentFragment();
        else
            mNavigationPaneSystem.launchFragments();

        super.onResume();

    }

    private void handleFragments(FragmentNav... fragmentNavs){

        switch (getScreenType()){
            case PHONE_SCREEN_WIDTH:
                initializeScreenAsPhone(fragmentNavs);
                break;
            case TABLET_SCREEN_WIDTH:
                initializeScreenAsTablet(fragmentNavs);
                break;
        }
    }

    private void initializeScreenAsPhone(FragmentNav... fragmentNavs){
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if(mNavigationBottomSystem == null)
            mNavigationBottomSystem = new NavigationBottomSystem(this, R.id.fragment, R.id.home_nav);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);

        addFragmentsToNavigationBottomSys(fragmentNavs);
    }

    private void initializeScreenAsTablet(FragmentNav... fragmentNavs){
        mNavigationPaneSystem = new NavigationPaneSystem(this);

        addFragmentsToNavigationPaneSys(fragmentNavs);
    }

    // Setup Navigation Bottom System
    private void addFragmentsToNavigationBottomSys(FragmentNav... fragmentNavs){

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.ingredient_nav;

        // Connect with Navigation Sys.
        fragmentNavs[RECIPE_FRAGMENT_INDEX]    .addListener(mNavigationBottomSystem);
        fragmentNavs[STEP_FRAGMENT_INDEX]      .addListener(mNavigationBottomSystem);
        fragmentNavs[INGREDIENT_FRAGMENT_INDEX].addListener(mNavigationBottomSystem);

        // Navigation Item
        fragmentNavs[RECIPE_FRAGMENT_INDEX]    .setNavigationItem(HOME_NAV);
        fragmentNavs[INGREDIENT_FRAGMENT_INDEX].setNavigationItem(GRADIENT_NV);
        fragmentNavs[STEP_FRAGMENT_INDEX]      .setNavigationItem(STEP_NAV);

        // Testing notify state changing
        fragmentNavs[RECIPE_FRAGMENT_INDEX]    .addOnStateChangingListener(this);
        fragmentNavs[INGREDIENT_FRAGMENT_INDEX].addOnStateChangingListener(this);
        fragmentNavs[STEP_FRAGMENT_INDEX]      .addOnStateChangingListener(this);


        mNavigationBottomSystem.put(fragmentNavs[RECIPE_FRAGMENT_INDEX]    , RECIPE_FRAGMENT_TAG);
        mNavigationBottomSystem.put(fragmentNavs[INGREDIENT_FRAGMENT_INDEX], INGREDIENT_FRAGMENT_TAG);
        mNavigationBottomSystem.put(fragmentNavs[STEP_FRAGMENT_INDEX]      , STEP_FRAGMENT_TAG);
    }

    // Setup Navigation Pane System
    private void addFragmentsToNavigationPaneSys(FragmentNav... fragmentNavs){

        // Connect with Navigation Sys.
        fragmentNavs[RECIPE_FRAGMENT_INDEX].addListener(mNavigationPaneSystem);
        fragmentNavs[STEP_FRAGMENT_INDEX].addListener(mNavigationPaneSystem);
        fragmentNavs[INGREDIENT_FRAGMENT_INDEX].addListener(mNavigationPaneSystem);

        mNavigationPaneSystem.put(fragmentNavs[RECIPE_FRAGMENT_INDEX]    , RECIPE_FRAGMENT_TAG);
        mNavigationPaneSystem.put(fragmentNavs[INGREDIENT_FRAGMENT_INDEX], INGREDIENT_FRAGMENT_TAG);
        mNavigationPaneSystem.put(fragmentNavs[STEP_FRAGMENT_INDEX]      , STEP_FRAGMENT_TAG);

        // Testing notify state changing
        fragmentNavs[RECIPE_FRAGMENT_INDEX]    .addOnStateChangingListener(this);
        fragmentNavs[INGREDIENT_FRAGMENT_INDEX].addOnStateChangingListener(this);
        fragmentNavs[STEP_FRAGMENT_INDEX]      .addOnStateChangingListener(this);

        // Add Frames
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.recipeListFrame    , RECIPE_FRAGMENT_TAG);
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.stepFrame          , STEP_FRAGMENT_TAG);
        mNavigationPaneSystem.addFrameToFragmentByTag(R.id.ingredientListFrame, INGREDIENT_FRAGMENT_TAG);

    }

    /**
     * Initialize app fragments or retrieve them if they are already existed.
     * @return fragments
     */
    public FragmentNav[] initializeFragments(){
        FragmentNav[] fragmentNavs = new FragmentNav[FRAGMENT_NUMBER];

        FragmentNav recipeListFragment = (FragmentNav) getSupportFragmentManager().findFragmentByTag(RECIPE_FRAGMENT_TAG);
        FragmentNav ingredientFragment = (FragmentNav) getSupportFragmentManager().findFragmentByTag(INGREDIENT_FRAGMENT_TAG);
        FragmentNav stepFragment       = (FragmentNav) getSupportFragmentManager().findFragmentByTag(STEP_FRAGMENT_TAG);

        if(recipeListFragment != null && ingredientFragment != null && stepFragment != null){
            fragmentNavs[RECIPE_FRAGMENT_INDEX]     = recipeListFragment;
            fragmentNavs[STEP_FRAGMENT_INDEX]       = stepFragment;
            fragmentNavs[INGREDIENT_FRAGMENT_INDEX] = ingredientFragment;
        }else{
            fragmentNavs[RECIPE_FRAGMENT_INDEX]     = new RecipeListFragment();
            fragmentNavs[STEP_FRAGMENT_INDEX]       = new StepFragment();
            fragmentNavs[INGREDIENT_FRAGMENT_INDEX] = new IngredientFragment();
        }

        return fragmentNavs;
    }

    private void handleSaverSystem(){
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(StepFragment.class, STEP_SAVER_SYSTEM_ID);
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(RecipeListFragment.class, RECIPE_SAVER_SYSTEM_ID);
        SAVER_SYSTEM_CONTROLLER.generateNewInstance(IngredientFragment.class, INGREDIENT_SAVER_SYSTEM_ID);
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

    /**
     * Get Screen type for current device.
     * @return
     *         TABLET_SCREEN_WIDTH: If current device is tablet
     *         PHONE_SCREEN_WIDTH : If current device is phone
     */
    private int getScreenType(){
        return findViewById(R.id.recipeListFrame) != null?TABLET_SCREEN_WIDTH:PHONE_SCREEN_WIDTH;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onSaveViewInstance(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        if(mNavigationBottomSystem != null) mNavigationBottomSystem.onRestoreViewInstance(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    //** Testing **//
    @VisibleForTesting
    @Nullable
    public BakingResourceIdle getIdleResource(){
        if(mBakingResourceIdle == null)
            mBakingResourceIdle = new BakingResourceIdle();
        return mBakingResourceIdle;
    }

    @Override
    public void onChanged(boolean newState) {
        if(mBakingResourceIdle != null)
            mBakingResourceIdle.setIdleState(newState);
    }
}
