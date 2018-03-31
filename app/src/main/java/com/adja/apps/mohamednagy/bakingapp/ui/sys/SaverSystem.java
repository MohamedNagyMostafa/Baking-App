package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.os.Bundle;

/**
 * Created by Mohamed Nagy on 3/30/2018 .
 * Project projects submission
 * Time    6:15 PM
 */

public class SaverSystem {
    public final String ID;
    private Bundle mSavedData;

    public SaverSystem(String id){
        ID = id;
    }

    public void save(Bundle data){
        mSavedData = data;
    }

    public Bundle savedData(){
        return mSavedData;
    }
}
