package com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed Nagy on 4/7/2018 .
 * Project projects submission
 * Time    7:06 AM
 */

public class NavigationPaneSystem extends NavigationSystem
        implements FragmentNav.FragmentNavListener{

    private HashMap<String, Integer> mFrames;

    NavigationPaneSystem(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFrames = new HashMap<>();
    }

    public void addFrameToFragmentByTag(@NonNull Integer frameId, @NonNull String fragmentTag){
        mFrames.put(fragmentTag, frameId);
    }


    @Override
    public void startFragment(FragmentIntent fragmentIntent) {
        super.startFragment(fragmentIntent, getFragmentFrame(fragmentIntent.mFragmentNavHolder.second));
    }

    private Integer getFragmentFrame(String fragmentTag){
        return mFrames.get(fragmentTag);
    }

}
