package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by Mohamed Nagy on 3/28/2018.
 */

public class VerticalStepSystem implements VerticalStepperForm {

    private List<Step> mSteps;
    private ViewCreation mViewCreation;
    private VerticalStepperFormLayout mVerticalStepperFormLayout;

    public VerticalStepSystem(List<Step> steps, ViewCreation viewCreation,
                              VerticalStepperFormLayout verticalStepperFormLayout, Activity activity){
        mSteps                     = steps;
        mViewCreation              = viewCreation;
        mVerticalStepperFormLayout = verticalStepperFormLayout;
        init(activity);
    }

    private void init(Activity activity){
        VerticalStepperFormLayout.Builder mVerticalStepperFormLayoutBuilder = VerticalStepperFormLayout.Builder.newInstance(
                mVerticalStepperFormLayout,
                getStepsTitle(),
                this,
                activity
        );

        mViewCreation.buildStepFormView(mVerticalStepperFormLayoutBuilder);
    }

    private String[] getStepsTitle(){
        String[] titles = new String [mSteps.size()];

        for(int i = 0; i < mSteps.size(); i++){
            titles[i] = mSteps.get(i).getShortDescription();
        }

        return titles;
    }

    @Override
    public View createStepContentView(int stepNumber) {
        return mViewCreation.createView(mSteps.get(stepNumber), mVerticalStepperFormLayout);
    }

    @Override
    public void onStepOpening(int stepNumber) {
        mVerticalStepperFormLayout.setStepAsCompleted(stepNumber);
    }

    @Override
    public void sendData() {}

    public interface ViewCreation{
        View createView(Step step, VerticalStepperFormLayout verticalStepperFormLayout);
        void buildStepFormView(VerticalStepperFormLayout.Builder builder);
    }
}
