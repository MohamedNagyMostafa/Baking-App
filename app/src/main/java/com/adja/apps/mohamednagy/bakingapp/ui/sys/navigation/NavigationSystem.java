package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Pair;

import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 4/7/2018 .
 * Project projects submission
 * Time    6:01 AM
 */

public class NavigationSystem{
    // Holds Fragment with NavigationItem
    static List<Pair<FragmentNav, String>> mFragmentNavsHolder;
    private FragmentManager mFragmentManager;

    NavigationSystem(FragmentManager fragmentManager){
        mFragmentNavsHolder = new ArrayList<>();
        mFragmentManager = fragmentManager;
    }

    public void put(@NonNull FragmentNav fragmentNav, String tag){
        mFragmentNavsHolder.add(new Pair<>(fragmentNav, tag));
    }

    /**
     * Replace current fragment with the new fragment based on navigation bar.
     */
    void loadFragment(Pair<FragmentNav, String> fragmentNavHolder, int frameId){
        // Check If the fragment is created before.
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNavHolder.second);

        if(fragment != null) {
            mFragmentManager.beginTransaction().show(fragment).commit();
        }else {
            mFragmentManager.beginTransaction().replace(
                    frameId,
                    fragmentNavHolder.first,
                    fragmentNavHolder.second
            ).commit();
        }
    }

    // Called At Tablet Mode
    private void loadFragmentOrReattachFragment(Pair<FragmentNav, String> fragmentNavHolder, int frameId){
        // Check If the fragment is created before.
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNavHolder.second);
        if(fragment != null) {
            FragmentNav fragmentNav = (FragmentNav) fragment;
            // This Line Is Added To Solve Strange Problem I Faced
            // Link : https://discussions.udacity.com/t/refresh-fragment/657570
            // I Hope If there's a logically answer for this situation I get it
            // at project reviewer's comment or reply on the forum
            // Thanks.
            if(fragmentNav instanceof IngredientFragment) {
                /**
                 * Start Test Block
                 */
                {
                    Log.e(getClass().getName(), "navigation sytem detect ingredient fragment\n" +
                            "Start Testing to save system data ");
                    if (fragmentNav.getSaverSystem() != null) {
                        Log.e(getClass().getName(), "detect ingredient saver system");
                        if (fragmentNav.getSaverSystem().savedData() != null) {
                            Log.e(getClass().getName(), "detect saved data at saver system");
                            Long recipeId = fragmentNav.getSaverSystem().savedData().getLong(Extras.IngredientData.RECIPE_ID);
                            if (recipeId != null && recipeId != 0) {
                                Log.e(getClass().getName(), " detect data at recipe id\n" + "data value is :" + recipeId);
                            } else {
                                Log.e(getClass().getName(), "there's no data for recipe id extra");
                            }
                        } else {
                            Log.e(getClass().getName(), "saver system is empty");
                        }
                    } else {
                        Log.e(getClass().getName(), "no ingredient saver system .. error");
                    }
                }
            }
            /**
             * End test block
             */
            fragment.setArguments(fragmentNav.getSaverSystem().savedData());

            mFragmentManager.beginTransaction().detach(fragment).attach(fragment).commit();
        }else {
            mFragmentManager.beginTransaction().replace(
                    frameId,
                    fragmentNavHolder.first,
                    fragmentNavHolder.second
            ).commit();
        }
    }

    void startFragment(NavigationSystem.FragmentIntent fragmentIntent, int frameId) {
        loadFragment(fragmentIntent.mFragmentNavHolder, frameId);
    }

    void startFragmentOrReattach(NavigationSystem.FragmentIntent fragmentIntent, int frameId) {
        loadFragmentOrReattachFragment(fragmentIntent.mFragmentNavHolder, frameId);
    }

    /**
     * Class used to move through navigation bar fragments
     * and passing data through its fragments
     */
    public static class FragmentIntent{
        private Class<? extends FragmentNav> mFragmentClass;
        Pair<FragmentNav, String> mFragmentNavHolder;

        public FragmentIntent(Class<? extends FragmentNav> fragmentClass){
            mFragmentClass = fragmentClass;
            init();
        }

        private void init(){
            for (Pair<FragmentNav, String> fragmentNavHolder : mFragmentNavsHolder) {
                if(mFragmentClass.isInstance(fragmentNavHolder.first)){
                    mFragmentNavHolder = fragmentNavHolder;
                    break;
                }
            }
        }

        /**
         * Save data for specific fragment of Bottom Navigation Fragments.
         */
        public <T>void putExtra(String extraName, T data){
            Bundle previousSavedData = mFragmentNavHolder.first.getSaverSystem().savedData();

            if(data instanceof Integer){
                if(previousSavedData != null) {
                    previousSavedData.putInt(extraName, (Integer) data);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(extraName, (Integer) data);

                    mFragmentNavHolder.first.getSaverSystem().save(bundle);
                }
            }else if(data instanceof Long){
                if(previousSavedData != null) {
                    previousSavedData.putLong(extraName, (Long) data);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putLong(extraName, (Long) data);

                    mFragmentNavHolder.first.getSaverSystem().save(bundle);
                }

            }else if(data instanceof String){
                if(previousSavedData != null) {
                    previousSavedData.putString(extraName, (String) data);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString(extraName, (String) data);

                    mFragmentNavHolder.first.getSaverSystem().save(bundle);
                }
            }
        }
    }
}
