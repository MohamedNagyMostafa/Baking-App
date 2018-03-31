package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class RecipeListFragment extends Fragment {

    private SaverSystem mSaverSystem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_fragment, container, false);
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
        super.onViewStateRestored(savedInstanceState);
        mSaverSystem = new SaverSystem(MainActivity.RECIPE_SAVER_SYSTEM_ID);
        mSaverSystem.save(savedInstanceState != null ? savedInstanceState.getBundle(MainActivity.RECIPE_SAVER_SYSTEM_ID) : null);
    }

    @Override
    public void onDestroyView() {
        if(mSaverSystem != null){
            //mSaverSystem.save();
        }
        super.onDestroyView();
    }

}
