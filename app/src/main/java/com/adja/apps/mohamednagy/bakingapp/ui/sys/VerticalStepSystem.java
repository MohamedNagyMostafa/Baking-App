package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.view.View;

import com.adja.apps.mohamednagy.bakingapp.model.Step;

import java.util.List;

import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Mohamed Nagy on 3/28/2018.
 */

public class VerticalStepSystem implements VerticalStepperForm {

    private List<Step> mSteps;

    public VerticalStepSystem(List<Step> steps){
        mSteps = steps;
    }

    public String[] getStepsTitle(){
        String[] titles = new String [mSteps.size()];

        for(int i = 0; i < mSteps.size(); i++){
            titles[i] = mSteps.get(i).getShortDescription();
        }

        return titles;
    }

    @Override
    public View createStepContentView(int stepNumber) {
        return null;
    }

    @Override
    public void onStepOpening(int stepNumber) {

    }

    @Override
    public void sendData() {

    }
}
