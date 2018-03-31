package com.adja.apps.mohamednagy.bakingapp.ui.screen;

import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;

import com.adja.apps.mohamednagy.bakingapp.databinding.StepFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.media.Media;
import com.adja.apps.mohamednagy.bakingapp.media.sys.AudioFocusSystem;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;
import com.adja.apps.mohamednagy.bakingapp.ui.stepper.StepperRecycleView;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.StepperSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:53 PM
 */

public class StepFragment extends Fragment implements StepperSystem.OnCurrentStepViewListener {

    private Media         mMedia;
    private Long          mMediaPosition;
    private Long          mRecipeId;
    private SaverSystem   mSaverSystem;
    private StepperSystem mStepperSystem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        // Handle fragment orientation.
        if(savedInstanceState != null){
            mSaverSystem = new SaverSystem(MainActivity.STEP_SAVER_SYSTEM_ID);
            Bundle bundle = savedInstanceState.getBundle(MainActivity.STEP_SAVER_SYSTEM_ID);
            mSaverSystem.save(bundle);
        }

        List<Step> steps = new ArrayList<>();

        // Retrieve data from database.
        // Handle fragment swap.
        if(mSaverSystem.savedData() != null){
            mRecipeId      = mSaverSystem.savedData().getLong(Extras.StepFragmentData.RECIPE_ID);
            mMediaPosition = mSaverSystem.savedData().getLong(Extras.StepFragmentData.CURRENT_MEDIA_MINT);
            // Get data from database
            steps = DatabaseRetriever.StepFragmentRetriever.getStepsFromDatabase(getContext(), mRecipeId);
        }

        // Set data binding view
        StepFragmentBinding stepFragmentBinding = DataBindingUtil.bind(rootView);
        // Handle Recycle View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        StepperRecycleView stepperRecycleView = new StepperRecycleView(steps);
        // Add Recycle View to Stepper System
        // To Handle Stepper Process.
        mStepperSystem = new StepperSystem(getContext(), stepperRecycleView,
                layoutManager,this );

        // Handle rotation
        {
            Integer currentActiveStep = mSaverSystem.savedData().getInt(Extras.StepFragmentData.CURRENT_STEP_POSITION);
            // return zero if there's no value exist.
            mStepperSystem.setCurrentActiveStepPosition(currentActiveStep);
        }

        stepFragmentBinding.stepperRecycleView.setLayoutManager(layoutManager);
        stepFragmentBinding.stepperRecycleView.setItemAnimator(new DefaultItemAnimator());
        stepFragmentBinding.stepperRecycleView.setAdapter(stepperRecycleView);

        return rootView;
    }


    @Override
    public void updateView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step) {
        stepperViewHolder.STEPPER_VIEW.descriptionText.setText(parseDescription(step.getDescription()));

        if(!step.getVideoLink().isEmpty()) {
            mMedia = new Media.Builder(getContext())
                    .mediaStateListener(null)
                    .mediaView(stepperViewHolder.STEPPER_VIEW.exoPlayerView)
                    .audioFocusSystem(new AudioFocusSystem(getContext()))
                    .videoLink(step.getVideoLink())
                    .defaultImage(BitmapFactory.decodeResource(getResources(), R.drawable.step_default_image))
                    .build();

            // Handle rotation.
            {
                if(mMediaPosition != null)
                    mMedia.setCurrentMediaPosition(mMediaPosition);
            }

            mMedia.play();
        }
    }
    // Called when Next button of current active step
    // is clicked.
    @Override
    public void nextButtonClickListener() {
        if(mMedia != null)
            mMedia.release();
    }
    // Called when Cancel button of current active step
    // is clicked.
    @Override
    public void cancelButtonClickListener() {
        if(mMedia != null)
            mMedia.release();
    }

    // Used to remove the number of step Ex: 1. , 2. ....
    private String parseDescription(String description){
        String newDescription = description;
        if(!description.isEmpty()){
            String focusedArea = description.substring(0, 3);
            if(focusedArea.contains(".")){
                newDescription = description.substring(0, focusedArea.indexOf(".") + 1);
            }
        }
        return newDescription.trim();
    }
    // Apply Save System to save data during fragment swap.
    public void applySaverSystem(SaverSystem saverSystem){
        mSaverSystem = saverSystem;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBundle(mSaverSystem.ID, getSavedData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        if(mSaverSystem != null){
            mSaverSystem.save(getSavedData());
        }
        super.onDestroyView();
    }

    public Bundle getSavedData(){
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.StepFragmentData.CURRENT_STEP_POSITION, mStepperSystem.getCurrentActiveStepPosition());
        bundle.putLong(Extras.StepFragmentData.RECIPE_ID, mRecipeId);
        bundle.putLong(Extras.StepFragmentData.CURRENT_MEDIA_MINT, mMediaPosition);

        return bundle;
    }

}
