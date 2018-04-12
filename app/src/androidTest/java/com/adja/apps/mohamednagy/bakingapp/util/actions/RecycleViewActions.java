package com.adja.apps.mohamednagy.bakingapp.util.actions;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

/**
 * Created by Mohamed Nagy on 4/10/2018 .
 * Project projects submission
 * Time    9:08 PM
 */

public class RecycleViewActions {
    private static final String CLICK_ON_VIEW = "There's an error is happened during click view action";
    private static final String SCROLL_TO_POSITION_DESCRIPTION = "There's an error is happened during scroll";

    public static ViewAction scrollToPosition(int position){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);// Ensure that the view is visible

            }

            @Override
            public String getDescription() {
                return SCROLL_TO_POSITION_DESCRIPTION;
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.getLayoutManager().scrollToPosition(position);
            }
        };
    }

    public static ViewAction clickOnViewAt(int position){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);
            }

            @Override
            public String getDescription() {
                return CLICK_ON_VIEW;
            }

            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                View childView = recyclerView.getChildAt(position);
                childView.performClick();
            }
        };
    }

}
