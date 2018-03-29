package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.stepper.StepperRecycleView;

/**
 * Created by Mohamed Nagy on 3/29/2018.
 */

public class StepperSystem implements StepperRecycleView.OnItemCreatedListener{
    private static final int ACTIVE_NODE     = 0x01;
    private static final int NON_ACTIVE_NODE = 0x02;
    private static final int COMPLETED_NODE  = 0x03;

    private OnCurrentStepViewListener mOnCurrentStepViewListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private StepperRecycleView mStepperRecycleView;
    private int mCurrentActivePosition;
    private Context mContext;

    private View.OnClickListener mOnClickListenerNextStep = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Set current node as completed node
            StepperRecycleView.StepperViewHolder currentNodeViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step currentNodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            setAsCompletedNode(currentNodeViewHolder, currentNodeData.getShortDescription());

            // Set new node.
            mCurrentActivePosition++;
            // Scroll to new node.
            mLayoutManager.scrollToPosition(mCurrentActivePosition);

            StepperRecycleView.StepperViewHolder stepperViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step nodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            bindView(stepperViewHolder, nodeData, mCurrentActivePosition);

        }
    };

    private View.OnClickListener mOnClickListenerPreviousStep = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Set current node as non-active node
            StepperRecycleView.StepperViewHolder currentNodeViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step currentNodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            setAsNonActiveNode(currentNodeViewHolder, currentNodeData.getShortDescription(), mCurrentActivePosition);
            // Set new node.
            mCurrentActivePosition--;
            // Scroll to new node.
            mLayoutManager.scrollToPosition(mCurrentActivePosition);

            StepperRecycleView.StepperViewHolder stepperViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step nodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            bindView(stepperViewHolder, nodeData, mCurrentActivePosition);
        }
    };

    public StepperSystem(
            Context context,
            StepperRecycleView stepperRecycleView,
            RecyclerView.LayoutManager layoutManager,
            @NonNull OnCurrentStepViewListener onCurrentStepViewListener){

        mOnCurrentStepViewListener = onCurrentStepViewListener;
        mContext                   = context;
        mLayoutManager             = layoutManager;
        mStepperRecycleView        = stepperRecycleView;
        init();
    }

    private void init(){
        mStepperRecycleView.setOnItemCreatedListener(this);
        mCurrentActivePosition = 0;
    }

    @Override
    public void bindView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step, int position) {
        switch (currentNodeMode(position)){
            case ACTIVE_NODE:
                setAsActiveNode(stepperViewHolder, step);
                break;
            case NON_ACTIVE_NODE:
                setAsNonActiveNode(stepperViewHolder, step.getShortDescription(), position);
                break;
            case COMPLETED_NODE:
                setAsCompletedNode(stepperViewHolder, step.getShortDescription());
                break;
        }
    }

    private int currentNodeMode(int position){
        return (position >= mCurrentActivePosition)?
                (position == mCurrentActivePosition)? ACTIVE_NODE: NON_ACTIVE_NODE :COMPLETED_NODE;
    }

    private void setAsActiveNode(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step){
        // Circle Settings.
        stepperViewHolder.STEPPER_VIEW.stepCircle.setBackground(mContext.getDrawable(R.drawable.step_circle_active));
        stepperViewHolder.STEPPER_VIEW.circleDone.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setText(String.valueOf(mCurrentActivePosition + 1));

        // Inner View Settings.
        stepperViewHolder.STEPPER_VIEW.stepDetails.setVisibility(View.VISIBLE);

        // Set Listeners.
        setViewActions(stepperViewHolder);

        mOnCurrentStepViewListener.updateView(stepperViewHolder, step);
    }

    private void setAsCompletedNode(StepperRecycleView.StepperViewHolder stepperViewHolder, String stepTitle){
        // Circle Settings.
        stepperViewHolder.STEPPER_VIEW.stepCircle.setBackground(mContext.getDrawable(R.drawable.step_circle_active));
        stepperViewHolder.STEPPER_VIEW.circleDone.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setVisibility(View.GONE);

        // Inner View Settings.
        stepperViewHolder.STEPPER_VIEW.stepDetails.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setText(stepTitle);
    }

    private void setAsNonActiveNode(StepperRecycleView.StepperViewHolder stepperViewHolder, String stepTitle, int position){
        // Circle Settings.
        stepperViewHolder.STEPPER_VIEW.stepCircle.setBackground(mContext.getDrawable(R.drawable.step_circle_unactive));
        stepperViewHolder.STEPPER_VIEW.circleDone.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setText(String.valueOf(position + 1));

        // Inner View Settings.
        stepperViewHolder.STEPPER_VIEW.stepDetails.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setText(stepTitle);
    }

    private void setViewActions(StepperRecycleView.StepperViewHolder stepperViewHolder){
        if(mCurrentActivePosition > 0){
            stepperViewHolder.STEPPER_VIEW.previousButton.setOnClickListener(mOnClickListenerPreviousStep);
            stepperViewHolder.STEPPER_VIEW.previousButton.setVisibility(View.VISIBLE);
        }else{
            stepperViewHolder.STEPPER_VIEW.previousButton.setVisibility(View.GONE);
        }

        if(mCurrentActivePosition < mStepperRecycleView.getItemCount() - 1){
            stepperViewHolder.STEPPER_VIEW.nextButton.setOnClickListener(mOnClickListenerNextStep);
            stepperViewHolder.STEPPER_VIEW.nextButton.setVisibility(View.VISIBLE);
        }else{
            stepperViewHolder.STEPPER_VIEW.nextButton.setVisibility(View.GONE);
        }
    }

    public interface OnCurrentStepViewListener{
        void updateView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step);
    }

}