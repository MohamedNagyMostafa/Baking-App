package com.adja.apps.mohamednagy.bakingapp.ui.screen;

import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;

import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.StepFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.media.Media;
import com.adja.apps.mohamednagy.bakingapp.media.sys.AudioFocusSystem;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.stepper.StepperRecycleView;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.StepperSystem;


/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    3:53 PM
 */

public class StepFragment extends FragmentNav implements StepperSystem.OnCurrentStepViewListener {
    // Media
    private Media         mMedia;
    // Current time of the running video.
    private Long          mMediaPosition;
    // Recipe id to retrieve steps data.
    private Long          mRecipeId;
    private int           mCurrentActiveStep;
    // Save system to handle saving data
    // during rotation and moving through
    // others fragments.
    private StepperSystem mStepperSystem;
    // Handle retrieving data from database on
    // isolated thread.
    private DatabaseRetriever.StepFragmentRetriever mStepFragmentRetriever;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStepFragmentRetriever = new DatabaseRetriever.StepFragmentRetriever(
                getContext().getContentResolver());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        // Set data binding view
        final StepFragmentBinding stepFragmentBinding = DataBindingUtil.bind(rootView);

        mMediaPosition     = getPreviousState(savedInstanceState).getLong(Extras.StepFragmentData.CURRENT_MEDIA_MINT);
        mRecipeId          = getPreviousState(savedInstanceState).getLong(Extras.StepFragmentData.RECIPE_ID);
        mCurrentActiveStep = getPreviousState(savedInstanceState).getInt(Extras.StepFragmentData.CURRENT_STEP_POSITION);

        stepFragmentBinding.emptyView.setVisibility(View.VISIBLE);
        stepFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        // Get data from database.
        mStepFragmentRetriever.getStepsFromDatabase(
                UriController.getStepTableUriByRecipeId(mRecipeId),
                steps -> {
                    if(steps.size() > 0){
                        stepFragmentBinding.emptyView.setVisibility(View.GONE);
                        stepFragmentBinding.progressBar.setVisibility(View.GONE);
                    }else{
                        stepFragmentBinding.progressBar.setVisibility(View.GONE);
                        Snackbar.make(stepFragmentBinding.getRoot(),getString(R.string.no_step_empty), Snackbar.LENGTH_LONG).show();
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                    StepperRecycleView stepperRecycleView = new StepperRecycleView(steps);
                    // Add Recycle View to Stepper System
                    // To Handle Stepper Process.
                    mStepperSystem = new StepperSystem(getContext(), stepperRecycleView,
                            layoutManager,StepFragment.this );
                    // Set Active Step (When rotation/Fragments-Swap is happened).
                    {
                        mStepperSystem.setCurrentActiveStepPosition(mCurrentActiveStep);
                    }
                    stepFragmentBinding.stepperRecycleView.setLayoutManager(layoutManager);
                    stepFragmentBinding.stepperRecycleView.setItemAnimator(new DefaultItemAnimator());
                    stepFragmentBinding.stepperRecycleView.setAdapter(stepperRecycleView);


                }
        );

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
                newDescription = description.substring(focusedArea.indexOf(".") + 1, description.length());
            }
        }
        return newDescription.trim();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(getSavedData(outState));
    }

    @Override
    public void onDestroyView() {
        getSaverSystem().save(getSavedData());

        if(mMedia != null)
            mMedia.release();
        if(mStepFragmentRetriever != null)
            mStepFragmentRetriever.release();
        super.onDestroyView();
    }

    public Bundle getSavedData(){
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.StepFragmentData.CURRENT_STEP_POSITION, mStepperSystem.getCurrentActiveStepPosition());
        bundle.putLong(Extras.StepFragmentData.RECIPE_ID, mRecipeId);
        bundle.putLong(Extras.StepFragmentData.CURRENT_MEDIA_MINT, mMediaPosition != null? mMediaPosition:0L);

        return bundle;
    }

    public Bundle getSavedData(Bundle bundle){
        bundle.putInt(Extras.StepFragmentData.CURRENT_STEP_POSITION, mStepperSystem.getCurrentActiveStepPosition());
        bundle.putLong(Extras.StepFragmentData.RECIPE_ID, mRecipeId);
        bundle.putLong(Extras.StepFragmentData.CURRENT_MEDIA_MINT, mMediaPosition != null? mMediaPosition:0L);

        return bundle;
    }

    private Bundle getPreviousState(Bundle saveInstanceState){
        return saveInstanceState == null? getSaverSystem().savedData() :saveInstanceState;
    }

}
