package com.adja.apps.mohamednagy.bakingapp.ui;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.MenuItem;

import com.adja.apps.mohamednagy.bakingapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018.
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
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                for(FragmentNav fragmentNav: mFragmentNavs){
                    if(fragmentNav.getNavigationItem() == id){
                        loadFragment(fragmentNav);
                        break;
                    }
                }
                return true;
            }
        });
    }

    /**
     * Replace current fragment with the new fragment based on navigation bar.
     */
    private void loadFragment(FragmentNav fragmentNav){

        mFragmentManager.beginTransaction().replace(
                FRAME_ID,
                fragmentNav.getFragment()
        ).commit();
    }


    public static class FragmentNav{
        private int mNavigationItem;
        private Fragment mFragment;

        public FragmentNav(int navigationItem, Fragment fragment){
            mFragment = fragment;
            mNavigationItem   = navigationItem;
        }

        public void setNavigationItem(int mNavigationItem) {
            this.mNavigationItem = mNavigationItem;
        }

        public void setFragment(Fragment mFragment) {
            this.mFragment = mFragment;
        }

        int getNavigationItem() {
            return mNavigationItem;
        }

        Fragment getFragment() {
            return mFragment;
        }
    }
}
