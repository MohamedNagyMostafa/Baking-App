package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.databinding.StepFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.databinding.StepViewBinding;
import com.adja.apps.mohamednagy.bakingapp.media.Media;
import com.adja.apps.mohamednagy.bakingapp.media.sys.AudioFocusSystem;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.VerticalStepSystem;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;


/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class StepFragment extends Fragment implements VerticalStepSystem.ViewCreation {

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
                        ""));
        // Set stepper sys.
        VerticalStepSystem verticalStepSystem = new VerticalStepSystem(
                steps,
                this,
                (VerticalStepperFormLayout) rootView.findViewById(R.id.verticalStepper),
                getActivity()
        );


        return rootView;
    }

    @Override
    public View createView(Step step, final VerticalStepperFormLayout verticalStepperFormLayout) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.step_view, null, false);
        TextView descriptionText          = view.findViewById(R.id.descriptionText);
        SimpleExoPlayerView exoPlayerView = view.findViewById(R.id.exoPlayerView);
        Button previousStepButton         = view.findViewById(R.id.previousButton);
        Button nextStepButton             = view.findViewById(R.id.nextButton);

        descriptionText.setText(step.getDescription());
        final Media media = setupMedia(exoPlayerView, step.getVideoLink(), step.getThumbnailURL());

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.release();
                verticalStepperFormLayout.goToNextStep();
            }
        });
        previousStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.release();
                verticalStepperFormLayout.goToPreviousStep();
            }
        });

        media.play();
        Log.e("called","done");
        return view;
    }

    @Override
    public void buildStepFormView(VerticalStepperFormLayout.Builder builder) {
        builder.primaryColor(R.color.colorPrimary)
                .primaryDarkColor(R.color.colorPrimary)
                .init();
    }

    Media setupMedia(SimpleExoPlayerView simpleExoPlayerView, String videoLink, String thumbnailLink){
        AudioFocusSystem audioFocusSystem = new AudioFocusSystem(getContext());
        String mediaVideoLink = (videoLink.isEmpty())? thumbnailLink: videoLink;

        return new Media.Builder(getContext())
                .audioFocusSystem(audioFocusSystem)
                .mediaView(simpleExoPlayerView)
                .mediaStateListener(null)
                .videoLink(mediaVideoLink)
                .build();
    }
}
