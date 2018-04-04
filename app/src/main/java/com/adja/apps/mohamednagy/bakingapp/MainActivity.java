package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.GradientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;

public class MainActivity extends AppCompatActivity {

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String GRADIENT_FRAGMENT_TAG = "gradient-fg";

    public static final String STEP_SAVER_SYSTEM_ID      = "step-sv";
    public static final String RECIPE_SAVER_SYSTEM_ID    = "recipe-sv";
    public static final String GRADIENT_SAVER_SYSTEM_ID  = "gradient-sv";

    public static final SaverSystem RECIPE_SAVER_SYSTEM   = new SaverSystem(RECIPE_SAVER_SYSTEM_ID);
    private static final SaverSystem STEP_SAVER_SYSTEM     = new SaverSystem(STEP_SAVER_SYSTEM_ID);
    private static final SaverSystem GRADIENT_SAVER_SYSTEM = new SaverSystem(GRADIENT_SAVER_SYSTEM_ID);

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
        final GradientFragment gradientFragment     = new GradientFragment();

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.gradient_nav;

        // Connect with Navigation Sys.
        recipeListFragment.addListener(mNavigationBottomSystem);
        gradientFragment.addListener(mNavigationBottomSystem);
        stepFragment.addListener(mNavigationBottomSystem);
        // Add Data Saver Sys.
        recipeListFragment.addSaverSystem(RECIPE_SAVER_SYSTEM);
        gradientFragment.addSaverSystem(STEP_SAVER_SYSTEM);
        stepFragment.addSaverSystem(GRADIENT_SAVER_SYSTEM);
        // Navigation Item
        recipeListFragment.setNavigationItem(HOME_NAV);
        gradientFragment.setNavigationItem(GRADIENT_NV);
        stepFragment.setNavigationItem(STEP_NAV);

        mNavigationBottomSystem.put(recipeListFragment, RECIPE_FRAGMENT_TAG);
        mNavigationBottomSystem.put(gradientFragment, GRADIENT_FRAGMENT_TAG);
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

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode){
//            case PermissionHandler.REQUEST_CODE:
//                if (grantResults.length > 0 &&
//                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                }else{
//                    // TODO: error msg
//                    Log.e("error","erro " + String.valueOf(grantResults[0]) + " " + String.valueOf(grantResults[1]));
//                }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        media.release();
//        super.onPause();
//    }


//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.e("data restore activity","data aaaaaaaaaaaaaaa save fragment");
//        STEP_SAVER_SYSTEM.save(savedInstanceState.getBundle(STEP_SAVER_SYSTEM.ID));
//        GRADIENT_SAVER_SYSTEM.save(savedInstanceState.getBundle(GRADIENT_SAVER_SYSTEM.ID));
//        RECIPE_SAVER_SYSTEM.save(savedInstanceState.getBundle(RECIPE_SAVER_SYSTEM.ID));
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putBundle(STEP_SAVER_SYSTEM.ID, STEP_SAVER_SYSTEM.savedData());
//        outState.putBundle(GRADIENT_SAVER_SYSTEM.ID, GRADIENT_SAVER_SYSTEM.savedData());
//        outState.putBundle(RECIPE_SAVER_SYSTEM.ID, RECIPE_SAVER_SYSTEM.savedData());
//        Log.e("data save activity","data aaaaaaaaaaaaaaa save fragment");
//
//        super.onSaveInstanceState(outState);
//    }


}
