package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.stepper.StepperRecycleView;

/**
 * Created by Mohamed Nagy on 3/29/2018 .
 * Project projects submission
 * Time    3:51 PM
 */

public class StepperSystem implements StepperRecycleView.OnItemCreatedListener{
    /** State of step **/
    private static final int ACTIVE_NODE     = 0x01;
    private static final int NON_ACTIVE_NODE = 0x02;
    private static final int COMPLETED_NODE  = 0x03;

    // Listener called for current active step to set its views.
    private OnCurrentStepViewListener mOnCurrentStepViewListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private StepperRecycleView mStepperRecycleView;
    private int mCurrentActivePosition;
    // This variable is created to prevent call onBindView() method twice.
    // As this method is called by Adapter and Listener.
    private Integer mFocusingPosition;
    private Context mContext;

    private View.OnClickListener mOnClickListenerNextStep = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // To release data "avoid leaking in memory"
            mOnCurrentStepViewListener.nextButtonClickListener();

            // Set current node as completed node
            StepperRecycleView.StepperViewHolder currentNodeViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step currentNodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            setAsCompletedNode(currentNodeViewHolder, currentNodeData.getShortDescription());

            // Set new node.
            mCurrentActivePosition++;

            StepperRecycleView.StepperViewHolder stepperViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            // bindView method is called by this listener and original adapter
            // so
            if (stepperViewHolder != null) {
                Step nodeData = mStepperRecycleView.getData(mCurrentActivePosition);

                bindView(stepperViewHolder, nodeData, mCurrentActivePosition);
            }
        }
    };

    private View.OnClickListener mOnClickListenerPreviousStep = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // To release data "avoid leaking in memory"
            mOnCurrentStepViewListener.cancelButtonClickListener();

            // Set current node as non-active node
            StepperRecycleView.StepperViewHolder currentNodeViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);
            Step currentNodeData = mStepperRecycleView.getData(mCurrentActivePosition);

            setAsNonActiveNode(currentNodeViewHolder, currentNodeData.getShortDescription(), mCurrentActivePosition);
            // Set new node.
            mCurrentActivePosition--;

            StepperRecycleView.StepperViewHolder stepperViewHolder =
                    mStepperRecycleView.getView(mCurrentActivePosition);

            if(stepperViewHolder != null) {
                Step nodeData = mStepperRecycleView.getData(mCurrentActivePosition);

                bindView(stepperViewHolder, nodeData, mCurrentActivePosition);
            }
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
    public synchronized void bindView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step, int position) {
        switch (currentNodeMode(position)){
            case ACTIVE_NODE:
                setAsActiveNode(stepperViewHolder, step, position);
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

    private void setAsActiveNode(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step, int position){
        // To prevent repeating this process twice.
        {
            if (mFocusingPosition != null && mFocusingPosition == position)
                return;

            mFocusingPosition = position;
        }

        // Circle Settings.
        stepperViewHolder.STEPPER_VIEW.stepCircle.setBackground(mContext.getDrawable(R.drawable.step_circle_active));
        stepperViewHolder.STEPPER_VIEW.circleDone.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setText(String.valueOf(mCurrentActivePosition + 1));

        // Inner View Settings.
        stepperViewHolder.STEPPER_VIEW.stepDetails.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setTypeface(Typeface.DEFAULT_BOLD);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setText(step.getShortDescription());

        // Set Listeners.
        setViewActions(stepperViewHolder);

        // Scroll to active step.
        mLayoutManager.scrollToPosition(mCurrentActivePosition);

        // Check If Last Node.
        if(position == mStepperRecycleView.getItemCount()-1)
            stepperViewHolder.STEPPER_VIEW.verticalDividerLine.setVisibility(View.GONE);

        mOnCurrentStepViewListener.updateView(stepperViewHolder, step);
    }

    private void setAsCompletedNode(StepperRecycleView.StepperViewHolder stepperViewHolder, String stepTitle){
        // Circle Settings.
        stepperViewHolder.STEPPER_VIEW.stepCircle.setBackground(mContext.getDrawable(R.drawable.step_circle_active));
        stepperViewHolder.STEPPER_VIEW.circleDone.setVisibility(View.VISIBLE);
        stepperViewHolder.STEPPER_VIEW.circleNumber.setVisibility(View.GONE);

        // Inner View Settings.
        stepperViewHolder.STEPPER_VIEW.stepDetails.setVisibility(View.GONE);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setTypeface(Typeface.DEFAULT);
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
        stepperViewHolder.STEPPER_VIEW.stepTitle.setTypeface(Typeface.DEFAULT);
        stepperViewHolder.STEPPER_VIEW.stepTitle.setText(stepTitle);

        // Check If Last Node.
        if(position == mStepperRecycleView.getItemCount()-1)
            stepperViewHolder.STEPPER_VIEW.verticalDividerLine.setVisibility(View.GONE);
    }

    private void setViewActions(StepperRecycleView.StepperViewHolder stepperViewHolder){
        if(mCurrentActivePosition > 0){
            stepperViewHolder.STEPPER_VIEW.previousButton.setOnClickListener(mOnClickListenerPreviousStep);
            stepperViewHolder.STEPPER_VIEW.previousButton.setVisibility(View.VISIBLE);
        }else{
            stepperViewHolder.STEPPER_VIEW.previousButton.setVisibility(View.INVISIBLE);
        }

        if(mCurrentActivePosition < mStepperRecycleView.getItemCount() - 1){
            stepperViewHolder.STEPPER_VIEW.nextButton.setOnClickListener(mOnClickListenerNextStep);
            stepperViewHolder.STEPPER_VIEW.nextButton.setVisibility(View.VISIBLE);
        }else{
            stepperViewHolder.STEPPER_VIEW.nextButton.setVisibility(View.INVISIBLE);
        }
    }

    public int getCurrentActiveStepPosition(){
        return mCurrentActivePosition;
    }

    public void setCurrentActiveStepPosition(int stepPosition){
        mCurrentActivePosition = stepPosition;
    }

    public interface OnCurrentStepViewListener{
        void updateView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step);
        void nextButtonClickListener();
        void cancelButtonClickListener();
    }

}
