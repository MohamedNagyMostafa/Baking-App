package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.MenuItem;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:51 PM
 */

public class NavigationBottomSystem extends NavigationSystem {
    private BottomNavigationView mBottomNavigationView;
    private final Integer FRAME_ID;

    public NavigationBottomSystem(AppCompatActivity appCompatActivity, Integer frameId){
        super(appCompatActivity);
        FRAME_ID = frameId;
    }

    public void addView(BottomNavigationView bottomNavigationView){
        if(mBottomNavigationView == null) {
            mBottomNavigationView = bottomNavigationView;
            applyListener();
        }
    }

    /**
     * Add Navigation Bottom Items listener.
     */
    private void applyListener(){
        mBottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            int id = item.getItemId();

            for (Pair<FragmentNav, String> pair : mFragmentNavsHolder) {
                if (pair.first.getNavigationItem() == id) {
                    super.loadFragment(pair, FRAME_ID);
                    break;
                }
            }
            return true;
        });
    }

    public void onSaveViewInstance(Bundle saveInstance){
        saveInstance.putInt(Extras.NavigationSystemData.SELECTED_NAVIGATION_BOTTOM_ITEM, mBottomNavigationView.getSelectedItemId());
    }

    public void onRestoreViewInstance(Bundle saveInstance){
        int currentFragmentNav;
        if(saveInstance != null) {
            currentFragmentNav = saveInstance.getInt(Extras.NavigationSystemData.SELECTED_NAVIGATION_BOTTOM_ITEM);
        }else{
            currentFragmentNav = 0;
        }
        FragmentIntent fragmentIntent = new FragmentIntent(mFragmentNavsHolder.get(currentFragmentNav).first.getClass());

        startFragment(fragmentIntent);
    }

    /**
     * Launch Previous Selected Fragment After Rotation Or
     * Set Default Fragment At Initial State.
     */
    public void launchCurrentFragment(){
        int fragmentId = mBottomNavigationView.getSelectedItemId();
        FragmentIntent fragmentIntent = new FragmentIntent(getFragmentClassFromId(fragmentId));
        startFragment(fragmentIntent);
    }

    private Class<? extends FragmentNav> getFragmentClassFromId(int fragmentId){
        switch (fragmentId){
            case R.id.step_nav:
                return StepFragment.class;
            case R.id.ingredient_nav:
                return IngredientFragment.class;
            case R.id.home_nav:
            default:
                return RecipeListFragment.class;
        }
    }

    @Override
    public void startFragment(FragmentIntent fragmentIntent) {
        mBottomNavigationView.setSelectedItemId(fragmentIntent.mFragmentNavHolder.first.getNavigationItem());
        super.startFragment(fragmentIntent, FRAME_ID);
    }
}
