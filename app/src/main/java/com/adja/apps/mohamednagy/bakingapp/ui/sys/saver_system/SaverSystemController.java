package com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;

import java.util.HashMap;

/**
 * Created by Mohamed Nagy on 4/9/2018 .
 * Project projects submission
 * Time    11:21 AM
 */

public class SaverSystemController {
    private HashMap<Class<?extends FragmentNav>, String> mSaverSystemClassesList;
    private FragmentManager mFragmentManager;

    public SaverSystemController(FragmentManager fragmentManager ){
        mFragmentManager = fragmentManager;
        mSaverSystemClassesList = new HashMap<>();
    }

    public void generateNewInstance(Class<?extends FragmentNav> classType, String id){
        SaverSystem saverSystem = (SaverSystem) mFragmentManager.findFragmentByTag(id);

        if(saverSystem == null){
            saverSystem = new SaverSystem();
            mFragmentManager.beginTransaction().add(saverSystem, id).commitNow();
            saverSystem = (SaverSystem) mFragmentManager.findFragmentByTag(id);
            if(saverSystem == null)
                Log.e("errrrrrrrrrror","errrrrrrrrrrrrrrrrrrrrror");
            mSaverSystemClassesList.put(classType, id);
        }else{
            if(!mSaverSystemClassesList.containsKey(classType))
                mSaverSystemClassesList.put(classType, id);
        }
    }

    public SaverSystem getSaverSystemInstance(FragmentNav fragmentNav) {
        String id = mSaverSystemClassesList.get(fragmentNav.getClass());
        Log.e("saver id", "restoruing ");
        SaverSystem saverSystem = (SaverSystem)mFragmentManager.findFragmentByTag(id);
        return saverSystem;
    }


}
