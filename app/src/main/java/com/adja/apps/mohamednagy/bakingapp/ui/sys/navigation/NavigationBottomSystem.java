package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.IngredientFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.RecipeListFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.screen.StepFragment;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SelectedSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:51 PM
 */

public class NavigationBottomSystem implements FragmentNav.FragmentNavListener {
    // Holds Fragment with NavigationItem
    private static List<Pair<FragmentNav, String>> mFragmentNavsHolder;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;
    private final Integer FRAME_ID;

    public NavigationBottomSystem(FragmentManager fragmentManager, Integer frameId){
        mFragmentNavsHolder = new ArrayList<>();
        mFragmentManager = fragmentManager;
        FRAME_ID = frameId;
    }

    public void put(@NonNull FragmentNav fragmentNav, String tag){
        mFragmentNavsHolder.add(new Pair<>(fragmentNav, tag));
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
                    loadFragment(pair);
                    break;
                }
            }
            return true;
        });
    }

    /**
     * Replace current fragment with the new fragment based on navigation bar.
     */
    private void loadFragment(Pair<FragmentNav, String> fragmentNavHolder){
        // Check If the fragment is created before.
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentNavHolder.second);

        if(fragment != null) {
            Log.e("existance","aaaaaaaaaaaaaaa");
            mFragmentManager.beginTransaction().show(fragment).commit();
        }else {
            mFragmentManager.beginTransaction().replace(
                    FRAME_ID,
                    fragmentNavHolder.first,
                    fragmentNavHolder.second
            ).commit();
        }
    }

    @Override
    public void startFragment(FragmentIntent fragmentIntent) {
        mBottomNavigationView.setSelectedItemId(fragmentIntent.mFragmentNavHolder.first.getNavigationItem());
        loadFragment(fragmentIntent.mFragmentNavHolder);
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
        Log.e("fragment id"," id : " + String.valueOf(fragmentId));
        Log.e("fragment id","home " + String.valueOf(R.id.home_nav) + " step "  + String.valueOf(R.id.step_nav));
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

    /**
     * Class used to move through navigation bar fragments
     * and passing data through its fragments
     */
    public static class FragmentIntent{
        private Class<? extends FragmentNav> mFragmentClass;
        private Pair<FragmentNav, String> mFragmentNavHolder;

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
