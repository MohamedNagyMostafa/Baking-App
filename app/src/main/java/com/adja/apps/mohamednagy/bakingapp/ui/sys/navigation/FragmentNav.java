package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

/*
 *
 * Created by Mohamed Nagy on 4/2/2018 .
 * Project projects submission
 * Time    10:11 AM
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

/**
 * Class to hold fragments which will be showed by navigation bar
 * and holds fragment details.
 */
public abstract class FragmentNav extends Fragment {

    private static final String FRAGMENT_DATA_SAVER_ID  = "fragment_data_id";

    private SaverSystem                 mSaverSystem;
    private FragmentNavListener         mFragmentNavListener;
    private int                         mNavigationItem;

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

    public SaverSystem getSaverSystem(){
        return mSaverSystem;
    }

    int getNavigationItem() {
        return mNavigationItem;
    }

    // Launch new fragment
    public void startFragment(NavigationSystem.FragmentIntent fragmentIntent){
        mFragmentNavListener.startFragment(fragmentIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(FRAGMENT_DATA_SAVER_ID, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null)
            mSaverSystem.save(savedInstanceState.getBundle(FRAGMENT_DATA_SAVER_ID));
    }

    @Override
    public void onPause() {
        try {
            if (mSaverSystem != null && mSaverSystem.savedData() != null && isTabletMode()) {
                setArguments(mSaverSystem.savedData());
            }
        }catch (IllegalStateException e){
            Log.e(getClass().getName(), e.getLocalizedMessage());
        }
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            if(mSaverSystem == null) {
                mSaverSystem = new SaverSystem();
            }
            if(isTabletMode()) {
                mSaverSystem.save(getArguments());
            }

        }catch (IllegalStateException e){
            Log.e(getClass().getName(), e.getLocalizedMessage());
        }
        return this.onCreateView(inflater, container);
    }

    private boolean isTabletMode(){
        return ((MainActivity)getActivity()).getScreenType() == MainActivity.TABLET_SCREEN_WIDTH;
    }
    /**
     * Listen to start new fragment.
     */
    interface FragmentNavListener{
        void startFragment(NavigationSystem.FragmentIntent fragmentIntent);
    }

    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup container);
}