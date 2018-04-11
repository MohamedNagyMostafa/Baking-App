package com.adja.apps.mohamednagy.bakingapp;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Mohamed Nagy on 4/10/2018 .
 * Project projects submission
 * Time    6:59 PM
 */

public class RecycleViewMatcher {

    public static Matcher<View> withTextByPosition(int position, int textViewId, String text){
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                View recyclerChildView = recyclerView.getChildAt(position);
                TextView textView = recyclerChildView.findViewById(textViewId);

                return textView.getText().toString().equals(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with class name: ");
            }
        };
    }
}
