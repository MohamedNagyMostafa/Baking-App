package com.adja.apps.mohamednagy.bakingapp.ui.sys.saver_system;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Mohamed Nagy on 3/30/2018 .
 * Project projects submission
 * Time    6:15 PM
 */

/**
 * To fragments data during swap through more than one fragment
 * on the same activity
 */
public class SaverSystem extends Fragment {
    private static final String SAVING_BUNDLE_DATA_ID = "saver.sy data";
    private Bundle mSavedData;
    public SaverSystem(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("saver restore instacne","dalled");
        if(savedInstanceState != null)
            mSavedData = savedInstanceState.getBundle(SAVING_BUNDLE_DATA_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(SAVING_BUNDLE_DATA_ID, mSavedData);
        Log.e("saver save instacne","dalled");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        Log.e("destroyed","Done");
        super.onDestroy();
    }

    public Bundle savedData(){
        if(mSavedData == null) {
            mSavedData = new Bundle();
        }
        return mSavedData;
    }
}
