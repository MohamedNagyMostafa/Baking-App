package com.adja.apps.mohamednagy.bakingapp.ui.screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class GradientFragment extends Fragment {

    private SaverSystem mSaverSystem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("view","called21");
        return inflater.inflate(R.layout.gradient_fragment, container, false);
    }

    public void applySaverSystem(SaverSystem saverSystem){
        mSaverSystem = saverSystem;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(mSaverSystem.ID, mSaverSystem.savedData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.e("restore","called1");
        super.onViewStateRestored(savedInstanceState);
        Log.e("restore","called21");
        mSaverSystem = new SaverSystem(MainActivity.GRADIENT_SAVER_SYSTEM_ID);
        mSaverSystem.save(savedInstanceState != null ? savedInstanceState.getBundle(MainActivity.GRADIENT_SAVER_SYSTEM_ID) : null);
    }

    @Override
    public void onDestroyView() {
        if(mSaverSystem != null){
            //mSaverSystem.save();
        }
        super.onDestroyView();
    }

}

