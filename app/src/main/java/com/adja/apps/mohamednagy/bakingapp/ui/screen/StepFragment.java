package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adja.apps.mohamednagy.bakingapp.R;

import com.adja.apps.mohamednagy.bakingapp.databinding.StepFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.media.Media;
import com.adja.apps.mohamednagy.bakingapp.media.sys.AudioFocusSystem;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.stepper.StepperRecycleView;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.StepperSystem;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class StepFragment extends Fragment implements StepperSystem.OnCurrentStepViewListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_fragment, container, false);

        List<Step> steps = new ArrayList<>();
        steps.add(
                new Step(
                        0,
                        "Melt butter and bittersweet chocolate.",
                        "2. Melt the butter and bittersweet chocolate together in a microwave or a double boiler. If microwaving, heat for 30 seconds at a time, removing bowl and stirring ingredients in between.",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc43_1-melt-choclate-chips-and-butter-brownies/1-melt-choclate-chips-and-butter-brownies.mp4",
                        ""));
        steps.add(
                new Step(
                        0,
                        "Mix together dry ingredients.",
                        "4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. ",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc9e_4-sift-flower-add-coco-powder-salt-brownies/4-sift-flower-add-coco-powder-salt-brownies.mp4",
                        "")
        );
        steps.add(
                new Step(
                        0,
                        "MO together dry ingredients.",
                        "4. Sift together the flour, cocoa, and salt in a small bowl and whisk until mixture is uniform and no clumps remain. ",
                        "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdc9e_4-sift-flower-add-coco-powder-salt-brownies/4-sift-flower-add-coco-powder-salt-brownies.mp4",
                        "")
        );

        StepFragmentBinding stepFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.step_fragment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        StepperRecycleView stepperRecycleView = new StepperRecycleView(steps);

        StepperSystem stepperSystem = new StepperSystem(getContext(), stepperRecycleView,
                layoutManager,this );

        stepFragmentBinding.stepperRecycleView.setLayoutManager(layoutManager);
        stepFragmentBinding.stepperRecycleView.setItemAnimator(new DefaultItemAnimator());
        stepFragmentBinding.stepperRecycleView.setAdapter(stepperRecycleView);

        return rootView;
    }

    @Override
    public void updateView(StepperRecycleView.StepperViewHolder stepperViewHolder, Step step) {
        Log.e("done","view is here");
        stepperViewHolder.STEPPER_VIEW.descriptionText.setText(step.getDescription());
    }
}
