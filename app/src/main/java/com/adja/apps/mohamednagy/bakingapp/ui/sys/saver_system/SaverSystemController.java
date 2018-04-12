package com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system;

import android.support.v4.app.FragmentManager;

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

            mSaverSystemClassesList.put(classType, id);
        }else{
            if(!mSaverSystemClassesList.containsKey(classType))
                mSaverSystemClassesList.put(classType, id);
        }
    }

    public SaverSystem getSaverSystemInstance(FragmentNav fragmentNav) {
        String id = mSaverSystemClassesList.get(fragmentNav.getClass());
        return (SaverSystem)mFragmentManager.findFragmentByTag(id);
    }


}
