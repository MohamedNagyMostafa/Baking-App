package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

/*
 *
 * Created by Mohamed Nagy on 4/2/2018 .
 * Project projects submission
 * Time    10:11 AM
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.testing_tools.BakingResourceIdle;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system.SaverSystem;

/**
 * Class to hold fragments which will be showed by navigation bar
 * and holds fragment details.
 */
public abstract class FragmentNav extends Fragment {

    private FragmentNavListener mFragmentNavListener;
    @Nullable volatile private BakingResourceIdle.onStateChangingListener mOnStateChangingListener;
    private int                 mNavigationItem;

    public FragmentNav(){}

    public void setNavigationItem(int mNavigationItem) {
        this.mNavigationItem = mNavigationItem;
    }

    public void addListener(FragmentNavListener fragmentNavListener){
        mFragmentNavListener = fragmentNavListener;
    }

    int getNavigationItem() {
        return mNavigationItem;
    }

    // Launch new fragment
    public void startFragment(NavigationSystem.FragmentIntent fragmentIntent){
        mFragmentNavListener.startFragment(fragmentIntent);
    }


    @Override
    public void onDestroy() {
        if(getSaverSystem() != null)
        onSaveData(getSaverSystem().savedData());
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("view","created");
        if(getSaverSystem() != null)
            onRestoreData(getSaverSystem().savedData());
        return this.onCreateView(inflater, container);
    }

    public SaverSystem getSaverSystem(){
        return ((MainActivity)getActivity()).SAVER_SYSTEM_CONTROLLER.getSaverSystemInstance(this);
    }

    /** Testing **/
    public void addOnStateChangingListener(BakingResourceIdle.onStateChangingListener onStateChangingListener){
        mOnStateChangingListener = onStateChangingListener;
    }

    protected void notifyStateChanging(boolean newState){
        if(mOnStateChangingListener != null)
            mOnStateChangingListener.onChanged(newState);
    }

    /**
     * Listen to start new fragment.
     */
    interface FragmentNavListener{
        void startFragment(NavigationSystem.FragmentIntent fragmentIntent);
    }

    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup container);
    public abstract void onSaveData(Bundle bundle);
    public abstract void onRestoreData(Bundle bundle);
}