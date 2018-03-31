package com.adja.apps.mohamednagy.bakingapp.ui.sys;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.MenuItem;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.GradientFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:51 PM
 */

public class NavigationBottomSystem {
    // Holds Fragment with NavigationItem
    private List<FragmentNav> mFragmentNavs;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;
    private final Integer FRAME_ID;

    public NavigationBottomSystem(FragmentManager fragmentManager, Integer frameId){
        mFragmentNavs = new ArrayList<>();
        mFragmentManager = fragmentManager;
        FRAME_ID = frameId;
    }

    public void put(@NonNull FragmentNav fragmentNav){
        mFragmentNavs.add(fragmentNav);
    }

    public void addView(BottomNavigationView bottomNavigationView){
        if(mBottomNavigationView == null) {
            mBottomNavigationView = bottomNavigationView;
            applyListener();
        }
    }

    private void applyListener(){
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            for(FragmentNav fragmentNav: mFragmentNavs){
                if(fragmentNav.getNavigationItem() == id){
                    loadFragment(fragmentNav);
                    break;
                }
            }
            return true;
        });
    }

    /**
     * Replace current fragment with the new fragment based on navigation bar.
     */
    public void loadFragment(FragmentNav fragmentNav){
        // Check If the fragment is created before.
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNav.TAG);

        if(fragment != null) {
            mFragmentManager.beginTransaction().show(fragment).commit();
        }else {
            mFragmentManager.beginTransaction().replace(
                    FRAME_ID,
                    fragmentNav.getFragment(),
                    fragmentNav.TAG
            ).commit();
        }
    }

    public static class FragmentNav{
        private final String TAG;

        private SaverSystem mSaverSystem;
        private int         mNavigationItem;
        private Fragment    mFragment;

        public FragmentNav(int navigationItem, Fragment fragment, SaverSystem saverSystem, String tag){
            mFragment         = fragment;
            mNavigationItem   = navigationItem;
            TAG               = tag;
            mSaverSystem      = saverSystem;
        }

        public void setNavigationItem(int mNavigationItem) {
            this.mNavigationItem = mNavigationItem;
        }

        public void setFragment(Fragment mFragment) {
            this.mFragment = mFragment;
        }

        public SaverSystem getSaverSystem(){
            return mSaverSystem;
        }

        int getNavigationItem() {
            return mNavigationItem;
        }

        Fragment getFragment() {
            return mFragment;
        }
    }
}
