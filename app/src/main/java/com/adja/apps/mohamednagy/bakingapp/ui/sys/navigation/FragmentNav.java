package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

/**
 * Created by Mohamed Nagy on 4/2/2018 .
 * Project projects submission
 * Time    10:11 AM
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;

/**
 * Class to hold fragments which will be showed by navigation bar
 * and holds fragment details.
 */
public class FragmentNav extends Fragment {

    private static final String FRAGMENT_DATA_SAVER_ID = "fragment_data_sv";
    private static final String FRAGMENT_DATA_SAVER_BUNDLE = "fragment_data_bundle";

    private SaverSystem         mSaverSystem;
    private FragmentNavListener mFragmentNavListener;
    private int                 mNavigationItem;

    public FragmentNav(){}
    // *** Inserting Data Section Start ***
    public void setNavigationItem(int mNavigationItem) {
        this.mNavigationItem = mNavigationItem;
    }

    public void addListener(FragmentNavListener fragmentNavListener){
        mFragmentNavListener = fragmentNavListener;
    }

    public void addSaverSystem(SaverSystem saverSystem){
        mSaverSystem = saverSystem;
    }
    // *** Inserting Data Section End ***

    // *** Retrieve Data Section Start***
    public SaverSystem getSaverSystem(){
        return mSaverSystem;
    }

    int getNavigationItem() {
        return mNavigationItem;
    }
    // *** Retrieve Data Section Start ***

    // *** Fragment Services Section Start ***
    // Launch new fragment
    public void startFragment(NavigationBottomSystem.FragmentIntent fragmentIntent){
        mFragmentNavListener.startFragment(fragmentIntent);
    }

    // Orientation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(FRAGMENT_DATA_SAVER_ID, mSaverSystem.ID);
        outState.putBundle(FRAGMENT_DATA_SAVER_BUNDLE, outState);
        Log.e("data save fragment","data aaaaaaaaaaaaaaa save fragment");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            String dataSaverId = savedInstanceState.getString(FRAGMENT_DATA_SAVER_ID);
            Bundle dataSaverBundle = savedInstanceState.getBundle(FRAGMENT_DATA_SAVER_BUNDLE);

            mSaverSystem = new SaverSystem(dataSaverId);
            mSaverSystem.save(dataSaverBundle);
        }
        Log.e("data restore fragment","data aaaaaaaaaaaaaaa save fragment");
    }

    // *** Fragment Services Section End ***


    @Override
    public void onDestroyView() {
        Log.e("onDestroyed view","done");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroyed","done");
        super.onDestroy();
    }

    /**
     * Listen to start new fragment.
     */
    interface FragmentNavListener{
        void startFragment(NavigationBottomSystem.FragmentIntent fragmentIntent);
    }

}