package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.os.Bundle;

/**
 * Created by Mohamed Nagy on 3/30/2018 .
 * Project projects submission
 * Time    6:15 PM
 */

/**
 * To fragments data during swap through more than one fragment
 * on the same activity
 */
public class SaverSystem {
    private Bundle mSavedData;
    public SaverSystem(){}

    public void save(Bundle data){
        mSavedData = data;
    }

    public Bundle savedData(){
        return mSavedData;
    }

    public boolean hasData(){
        return mSavedData != null;
    }
}
