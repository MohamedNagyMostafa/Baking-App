package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private Integer mCurrentSelectedFragment;
    private final Integer FRAME_ID;

    public NavigationBottomSystem(AppCompatActivity appCompatActivity, Integer frameId, int defaultFragmentId){
        super(appCompatActivity);
        FRAME_ID = frameId;
        mCurrentSelectedFragment = defaultFragmentId;
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
        int selected = mBottomNavigationView.getSelectedItemId();
        Log.e("fragment","save :" + String.valueOf(selected));
        saveInstance.putInt(Extras.NavigationSystemData.SELECTED_NAVIGATION_BOTTOM_ITEM, selected);
    }

    public void onRestoreViewInstance(Bundle saveInstance){
        if(saveInstance != null) {
            mCurrentSelectedFragment = saveInstance.getInt(Extras.NavigationSystemData.SELECTED_NAVIGATION_BOTTOM_ITEM);
        }
    }

    /**
     * Launch Previous Selected Fragment After Rotation Or
     * Set Default Fragment At Initial State.
     */
    public void launchCurrentFragment(){
        mBottomNavigationView.setSelectedItemId(mCurrentSelectedFragment);
        try {
            FragmentIntent fragmentIntent = new FragmentIntent(getFragmentClassFromId(mCurrentSelectedFragment));
            startFragment(fragmentIntent);

        } catch (FragmentIntent.InValidIntentException e) {
            e.printStackTrace();
        }
    }

    private Class<? extends FragmentNav> getFragmentClassFromId(int fragmentId){
        Log.e("fragment",String.valueOf(fragmentId));
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
