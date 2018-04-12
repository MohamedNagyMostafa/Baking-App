package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;

import java.util.HashMap;

/**
 * Created by Mohamed Nagy on 4/7/2018 .
 * Project projects submission
 * Time    7:06 AM
 */

public class NavigationPaneSystem extends NavigationSystem {

    private HashMap<String, Integer> mFrames;

    public NavigationPaneSystem(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        mFrames = new HashMap<>();
    }

    public void addFrameToFragmentByTag(@NonNull Integer frameId, @NonNull String fragmentTag){
        mFrames.put(fragmentTag, frameId);
    }

    public void launchFragments(){
        try {
            FragmentIntent recipeListFragment = new FragmentIntent(RecipeListFragment.class);
            FragmentIntent stepFragment       = new FragmentIntent(StepFragment.class);
            FragmentIntent IngredientFragment = new FragmentIntent(IngredientFragment.class);
            super.startFragmentOrReattach(recipeListFragment, getFragmentFrame(recipeListFragment.mFragmentNavHolder.second));
            super.startFragmentOrReattach(stepFragment, getFragmentFrame(stepFragment.mFragmentNavHolder.second));
            super.startFragmentOrReattach(IngredientFragment, getFragmentFrame(IngredientFragment.mFragmentNavHolder.second));

        } catch (FragmentIntent.InValidIntentException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    /**
     * At Tablet Case There are just step and ingredient fragments need to update.
     */
    private void updateFragments(){
        try {
            FragmentIntent stepFragment = new FragmentIntent(StepFragment.class);
            FragmentIntent IngredientFragment = new FragmentIntent(IngredientFragment.class);

            super.startFragmentOrReattach(stepFragment, getFragmentFrame(stepFragment.mFragmentNavHolder.second));
            super.startFragmentOrReattach(IngredientFragment, getFragmentFrame(IngredientFragment.mFragmentNavHolder.second));
        }catch (Exception e){
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void startFragment(FragmentIntent fragmentIntent) {
        updateFragments();
    }

    private Integer getFragmentFrame(String fragmentTag){
        return mFrames.get(fragmentTag);
    }
}
