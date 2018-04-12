package com.adja.apps.mohamednagy.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adja.apps.mohamednagy.bakingapp.util.actions.RecycleViewActions;
import com.adja.apps.mohamednagy.bakingapp.util.matchers.RecycleViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Mohamed Nagy on 4/11/2018 .
 * Project projects submission
 * Time    7:36 PM
 */

@RunWith(AndroidJUnit4.class)
public class StepFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mMainActivityActivityTestRule.getActivity().getIdleResource();
        Espresso.registerIdlingResources(mIdlingResource);
        onView(withId(R.id.recipeRecycleView)).perform(RecycleViewActions.clickOnViewAt(0));
    }

    /**
     * Check stepper system behavior
     */
    @Test
    public void movingThroughSteps_ClickOnContinueAndCancelButtons(){
        // Check current step number
        onView(withId(R.id.stepperRecycleView))
                .check(matches(RecycleViewMatcher
                        .withTextByPosition(0, R.id.circleNumber, String.valueOf(1))));
        // Click on continue button to go to next step
        onView(withId(R.id.stepperRecycleView)
        ).perform(RecycleViewActions.clickOnItemInViewAt(0, R.id.nextButton));
        // Check current step number
        onView(withId(R.id.stepperRecycleView))
                .check(matches(RecycleViewMatcher
                        .withTextByPosition(1, R.id.circleNumber, String.valueOf(2))));
        // Click on cancel button to go back to previous step
        onView(withId(R.id.stepperRecycleView)
        ).perform(RecycleViewActions.clickOnItemInViewAt(1, R.id.previousButton));
        // Check current step number
        onView(withId(R.id.stepperRecycleView))
                .check(matches(RecycleViewMatcher
                        .withTextByPosition(0, R.id.circleNumber, String.valueOf(1))));
    }

    @After
    public void unregisterIdlingResource(){
        Espresso.unregisterIdlingResources(mIdlingResource);
    }


}
