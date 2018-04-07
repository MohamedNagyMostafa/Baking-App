package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;

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

    void startFragment(NavigationSystem.FragmentIntent fragmentIntent, int frameId) {
        loadFragment(fragmentIntent.mFragmentNavHolder, frameId);
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
