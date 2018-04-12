package com.adja.apps.mohamednagy.bakingapp.util.matchers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.IngredientListAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by Mohamed Nagy on 4/11/2018 .
 * Project projects submission
 * Time    7:12 PM
 */

public class ListViewMatcher {

    public static Matcher<View> ingredient(int position, int textViewId, String text){
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ListView recyclerView = (ListView) item;
                IngredientListAdapter ingredientListAdapter = (IngredientListAdapter) recyclerView.getAdapter();
                View view = ingredientListAdapter.getView(position, null, (ViewGroup) recyclerView.getParent());
                TextView textView = view.findViewById(textViewId);

                return textView.getText().toString().equals(text);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(getClass().getName());
            }
        };
    }
}
