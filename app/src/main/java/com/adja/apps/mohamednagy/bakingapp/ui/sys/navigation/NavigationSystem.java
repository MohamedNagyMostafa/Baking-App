package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system.SaverSystemController;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 4/7/2018 .
 * Project projects submission
 * Time    6:01 AM
 */

abstract public class NavigationSystem implements FragmentNav.FragmentNavListener{
    // Holds Fragment with NavigationItem
    static List<Pair<FragmentNav, String>> mFragmentNavsHolder;
    static SaverSystemController mSaverSystemController;

    private FragmentManager mFragmentManager;

    NavigationSystem(AppCompatActivity appCompatActivity){
        mFragmentNavsHolder = new ArrayList<>();
        mFragmentManager = appCompatActivity.getSupportFragmentManager();
        mSaverSystemController = ((MainActivity)appCompatActivity).SAVER_SYSTEM_CONTROLLER;
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
            ).commitNow();
        }
    }

    // Called At Tablet Mode
    private void loadFragmentOrReattachFragment(Pair<FragmentNav, String> fragmentNavHolder, int frameId){
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNavHolder.second);
        if(fragment != null) {
            mFragmentManager.beginTransaction().detach(fragment).commit();
            mFragmentManager.beginTransaction().attach(fragment).commit();
        }else
            mFragmentManager.beginTransaction().replace(
                    frameId,
                    fragmentNavHolder.first,
                    fragmentNavHolder.second
            ).commitNow();

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
            SaverSystem saverSystem = mSaverSystemController.getSaverSystemInstance(mFragmentNavHolder.first);

            if(data instanceof Integer){
                saverSystem.savedData().putInt(extraName, (Integer) data);
            }else if(data instanceof Long){
                saverSystem.savedData().putLong(extraName, (Long) data);
            }else if(data instanceof String){
                saverSystem.savedData().putString(extraName, (String) data);
            }
        }
    }
}
