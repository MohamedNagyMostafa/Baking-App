package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
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

public class MainActivity extends AppCompatActivity {

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String GRADIENT_FRAGMENT_TAG = "gradient-fg";

    private static final String STEP_FRAGMENT_SAVER_SYSTEM_ID       = "step-id";
    private static final String RECIPE_FRAGMENT_SAVER_SYSTEM_ID     = "recipe-fg";
    private static final String INGREDIENT_FRAGMENT_SAVER_SYSTEM_ID = "gradient-fg";

    public static final SaverSystem RECIPE_SAVER_SYSTEM      = new SaverSystem(RECIPE_FRAGMENT_SAVER_SYSTEM_ID);
    private static final SaverSystem STEP_SAVER_SYSTEM       = new SaverSystem(STEP_FRAGMENT_SAVER_SYSTEM_ID);
    private static final SaverSystem INGREDIENT_SAVER_SYSTEM = new SaverSystem(INGREDIENT_FRAGMENT_SAVER_SYSTEM_ID);

    private NavigationBottomSystem mNavigationBottomSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mNavigationBottomSystem = new NavigationBottomSystem(getSupportFragmentManager(), R.id.fragment);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);
        addFragmentsToNavigationSys();

    }

    private void addFragmentsToNavigationSys(){
        Log.e("add navigation sys", "data aaaaaaaaaaaaaaa");
        final RecipeListFragment recipeListFragment = new RecipeListFragment();
        final StepFragment stepFragment             = new StepFragment();
        final IngredientFragment IngredientFragment = new IngredientFragment();

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.gradient_nav;

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
        mNavigationBottomSystem.put(IngredientFragment, GRADIENT_FRAGMENT_TAG);
        mNavigationBottomSystem.put(stepFragment, STEP_FRAGMENT_TAG);

        {
            NavigationBottomSystem.FragmentIntent fragmentIntent =
                    new NavigationBottomSystem.FragmentIntent(RecipeListFragment.class);

            mNavigationBottomSystem.startFragment(fragmentIntent);

        }
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }

}
