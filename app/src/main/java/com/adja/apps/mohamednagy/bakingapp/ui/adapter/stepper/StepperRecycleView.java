package com.adja.apps.mohamednagy.bakingapp.ui.adapter.stepper;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.databinding.StepperViewBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Step;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/29/2018 .
 * Project projects submission
 * Time    3:52 PM
 */

public class StepperRecycleView extends RecyclerView.Adapter<StepperRecycleView.StepperViewHolder> {

    private OnItemCreatedListener mOnItemCreatedListener;
    private List<Step> mSteps;
    private HashMap<Integer, StepperViewHolder> mViewContainer;

    public StepperRecycleView(List<Step> steps){
        mSteps = steps;
        mViewContainer = new HashMap<>();
    }

    @Override
    public StepperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StepperViewBinding stepperViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.stepper_view, parent, false);

        return new StepperViewHolder(stepperViewBinding);
    }

    @Override
    public void onBindViewHolder(StepperViewHolder holder, int position) {
        if(setToContainer(holder, position)) // Avoid repeating.
            mOnItemCreatedListener.bindView(holder, mSteps.get(position), position);
    }

    private boolean setToContainer(StepperViewHolder stepperViewHolder, Integer position){
        boolean isContained = mViewContainer.containsKey(position);

        if(!isContained)
            mViewContainer.put(position, stepperViewHolder);

        return !isContained;
    }

    public StepperViewHolder getView(int position){
        return mViewContainer.get(position);
    }

    public Step getData(int position){
        return mSteps.get(position);
    }

    @Override
    public int getItemCount() {
        return (mSteps != null)?mSteps.size():0;
    }

    public void setOnItemCreatedListener(OnItemCreatedListener onItemCreatedListener){
        assert mOnItemCreatedListener != null;
        mOnItemCreatedListener = onItemCreatedListener;
    }

    public class StepperViewHolder extends RecyclerView.ViewHolder{
        public final StepperViewBinding STEPPER_VIEW;

        StepperViewHolder(@NonNull StepperViewBinding stepperViewBinding) {
            super(stepperViewBinding.getRoot());
            STEPPER_VIEW = stepperViewBinding;
        }
    }

    public interface OnItemCreatedListener{
        void bindView(StepperViewHolder stepperViewHolder, Step step, int position);
    }
}
