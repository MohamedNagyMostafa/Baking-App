package com.adja.apps.mohamednagy.bakingapp.util.actions;

import android.support.design.widget.BottomNavigationView;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;

/**
 * Created by Mohamed Nagy on 4/11/2018 .
 * Project projects submission
 * Time    6:30 PM
 */

public class BottomNavigationActions {
    public static final String NAVIGATION_CLICK_DESCRIPTION = "there's an error is happened during navigation click action";

    public static ViewAction clickOnNavigationAt(int nav_id){
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE);// Ensure that the view is visible

            }

            @Override
            public String getDescription() {
                return NAVIGATION_CLICK_DESCRIPTION;
            }

            @Override
            public void perform(UiController uiController, View view) {
                BottomNavigationView bottomNavigationView = (BottomNavigationView) view;
                View itemView = bottomNavigationView.findViewById(nav_id);
                itemView.performClick();
            }
        };
    }
}
