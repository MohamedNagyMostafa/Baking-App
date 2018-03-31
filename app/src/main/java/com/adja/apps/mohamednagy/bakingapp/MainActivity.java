package com.adja.apps.mohamednagy.bakingapp;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.GradientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

public class MainActivity extends AppCompatActivity {

    private static final String STEP_FRAGMENT_TAG     = "step-fg";
    private static final String RECIPE_FRAGMENT_TAG   = "recipe-fg";
    private static final String GRADIENT_FRAGMENT_TAG = "gradient-fg";

    public static final String STEP_SAVER_SYSTEM_ID      = "step-sv";
    public static final String RECIPE_SAVER_SYSTEM_ID    = "recipe-sv";
    public static final String GRADIENT_SAVER_SYSTEM_ID  = "gradient-sv";

    private static final SaverSystem RECIPE_SAVER_SYSTEM   = new SaverSystem(RECIPE_SAVER_SYSTEM_ID);
    private static final SaverSystem STEP_SAVER_SYSTEM     = new SaverSystem(STEP_SAVER_SYSTEM_ID);
    private static final SaverSystem GRADIENT_SAVER_SYSTEM = new SaverSystem(GRADIENT_SAVER_SYSTEM_ID);

    private NavigationBottomSystem mNavigationBottomSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mNavigationBottomSystem = new NavigationBottomSystem(getSupportFragmentManager(), R.id.fragment);
        mNavigationBottomSystem.addView(activityMainBinding.bottomNavigation);
        addFragmentsToNavigationSys();

    }

    private void addFragmentsToNavigationSys(){
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        StepFragment stepFragment             = new StepFragment();
        GradientFragment gradientFragment     = new GradientFragment();

        final Integer HOME_NAV    = R.id.home_nav;
        final Integer STEP_NAV    = R.id.step_nav;
        final Integer GRADIENT_NV = R.id.gradient_nav;

        final NavigationBottomSystem.FragmentNav RECIPE_FRAGMENT_NAV =
                new NavigationBottomSystem.FragmentNav(HOME_NAV, recipeListFragment, RECIPE_SAVER_SYSTEM ,RECIPE_FRAGMENT_TAG);
        final NavigationBottomSystem.FragmentNav STEP_FRAGMENT_NAV =
                new NavigationBottomSystem.FragmentNav(STEP_NAV, stepFragment, STEP_SAVER_SYSTEM, STEP_FRAGMENT_TAG);
        final NavigationBottomSystem.FragmentNav GRADIENT_FRAGMENT_NAV = new
                NavigationBottomSystem.FragmentNav(GRADIENT_NV, gradientFragment, GRADIENT_SAVER_SYSTEM, GRADIENT_FRAGMENT_TAG);
        // Test block
        {
            Bundle b = new Bundle();
            b.putLong(Extras.StepFragmentData.RECIPE_ID, 2);
            STEP_SAVER_SYSTEM.save(b);
        }

        recipeListFragment.applySaverSystem(RECIPE_SAVER_SYSTEM);
        gradientFragment.applySaverSystem(GRADIENT_SAVER_SYSTEM);
        stepFragment.applySaverSystem(STEP_SAVER_SYSTEM);

        mNavigationBottomSystem.put(RECIPE_FRAGMENT_NAV);
        mNavigationBottomSystem.put(STEP_FRAGMENT_NAV);
        mNavigationBottomSystem.put(GRADIENT_FRAGMENT_NAV);
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


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        STEP_SAVER_SYSTEM.save(savedInstanceState.getBundle(STEP_SAVER_SYSTEM.ID));
        GRADIENT_SAVER_SYSTEM.save(savedInstanceState.getBundle(GRADIENT_SAVER_SYSTEM.ID));
        RECIPE_SAVER_SYSTEM.save(savedInstanceState.getBundle(RECIPE_SAVER_SYSTEM.ID));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(STEP_SAVER_SYSTEM.ID, STEP_SAVER_SYSTEM.savedData());
        outState.putBundle(GRADIENT_SAVER_SYSTEM.ID, GRADIENT_SAVER_SYSTEM.savedData());
        outState.putBundle(RECIPE_SAVER_SYSTEM.ID, RECIPE_SAVER_SYSTEM.savedData());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState, PersistableBundle outPersistentState) {

        super.onSaveInstanceState(savedInstanceState, outPersistentState);
    }

}
