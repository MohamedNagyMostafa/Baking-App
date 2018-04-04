package com.adja.apps.mohamednagy.bakingapp.ui.sys;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:51 PM
 */

public class NavigationBottomSystem implements NavigationBottomSystem.FragmentNav.FragmentNavListener{
    // Holds Fragment with NavigationItem
    private static List<FragmentNav> mFragmentNavs;
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
    private void loadFragment(FragmentNav fragmentNav){
        // Check If the fragment is created before.
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNav.getTag());

        if(fragment != null) {
            mFragmentManager.beginTransaction().show(fragment).commit();
        }else {
            mFragmentManager.beginTransaction().replace(
                    FRAME_ID,
                    fragmentNav,
                    fragmentNav.getTag()
            ).commit();
        }
    }

    @Override
    public void startFragment(FragmentIntent fragmentIntent) {
        mBottomNavigationView.setSelectedItemId(fragmentIntent.mFragmentNav.getNavigationItem());
        loadFragment(fragmentIntent.mFragmentNav);
    }
    /**
     * Class to hold fragments which will be showed by navigation bar
     * and holds fragment details.
     */
    public static class FragmentNav extends Fragment{

        private SaverSystem         mSaverSystem;
        private FragmentNavListener mFragmentNavListener;
        private int                 mNavigationItem;

        public FragmentNav(){}

        public void setNavigationItem(int mNavigationItem) {
            this.mNavigationItem = mNavigationItem;
        }

        public void addListener(FragmentNavListener fragmentNavListener){
            mFragmentNavListener = fragmentNavListener;
        }

        public void addSaverSystem(SaverSystem saverSystem){
            mSaverSystem = saverSystem;
        }

        private SaverSystem getSaverSystem(){
            return mSaverSystem;
        }

        public void startFragment(FragmentIntent fragmentIntent){
            mFragmentNavListener.startFragment(fragmentIntent);
        }

        int getNavigationItem() {
            return mNavigationItem;
        }

        /**
         * Listen to start new fragment.
         */
        interface FragmentNavListener{
            void startFragment(FragmentIntent fragmentIntent);
        }

    }

    /**
     * Class used to move through navigation bar fragments
     * and passing data through its fragments
     */
    public static class FragmentIntent{
        private Class<? extends Fragment> mFragmentClass;
        private FragmentNav mFragmentNav;

        public FragmentIntent(Class<? extends Fragment> fragmentClass){
            mFragmentClass = fragmentClass;
        }

        /**
         * Save data for specific fragment of Bottom Navigation Fragments.
         * @param bundle    Data To Save
         */
        public void put(Bundle bundle){
            for (FragmentNav fragmentNav : mFragmentNavs) {
                if(mFragmentClass.isInstance(fragmentNav)){
                    mFragmentNav = fragmentNav;
                    fragmentNav.getSaverSystem().save(bundle);
                    break;
                }
            }
        }
    }
}
