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
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class GradientFragment extends FragmentNav {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("view","called21");
        return inflater.inflate(R.layout.gradient_fragment, container, false);
    }



}

