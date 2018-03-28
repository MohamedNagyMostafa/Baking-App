package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.databinding.StepFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.VerticalStepSystem;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class StepFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        StepFragmentBinding stepFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.step_fragment);
        VerticalStepperFormLayout.Builder.newInstance(
                stepFragmentBinding.verticalStepper,
                new String[]{"Step1","Step2","Step3","Step4","Step5"},
                new VerticalStepSystem(),
                getActivity()
        )
                .primaryColor(R.color.colorPrimary)
                .primaryDarkColor(R.color.colorPrimaryDark)
                .init();

        return rootView;
    }
}
