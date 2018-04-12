package com.adja.apps.mohamednagy.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.IngredientListAdapter;
import com.adja.apps.mohamednagy.bakingapp.util.actions.BottomNavigationActions;
import com.adja.apps.mohamednagy.bakingapp.util.matchers.ListViewMatcher;
import com.adja.apps.mohamednagy.bakingapp.util.matchers.RecycleViewMatcher;
import com.adja.apps.mohamednagy.bakingapp.util.actions.RecycleViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by Mohamed Nagy on 4/10/2018 .
 * Project projects submission
 * Time    5:41 PM
 */
@RunWith(AndroidJUnit4.class)
public class RecipeFragmentTest {

    @Rule public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mMainActivityActivityTestRule.getActivity().getIdleResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void checkRecipeData_RetrievingRecipeDataFromAPI(){

        checkRecipeItemExistence(ExpectedData.RECIPE_1, 0);
        checkRecipeItemExistence(ExpectedData.RECIPE_2, 1);
        checkRecipeItemExistence(ExpectedData.RECIPE_3, 2);
    }

    @Test
    public void openRightStepForRecipe_ClickOnRecipeItem(){
        // Scroll to first item
        onView(withId(R.id.recipeRecycleView)).perform(RecycleViewActions.scrollToPosition(0))
                // Click on the item
                .perform(RecycleViewActions.clickOnViewAt(0));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.stepperRecycleView))
                // Scroll two third step
                .perform(RecycleViewActions.scrollToPosition(2))
                // check step title to expected one
                .check(matches(RecycleViewMatcher.withTextByPosition(2, R.id.stepTitle,
                        ExpectedData.THIRD_STEP_TITLE_FOR_FIRST_RECIPE)));
    }

    @Test
    public void openRightIngredientForRecipe_ClickOnRecipeItem(){
        // Scroll to first item
        onView(withId(R.id.recipeRecycleView)).perform(RecycleViewActions.scrollToPosition(0))
                // Click on the item
                .perform(RecycleViewActions.clickOnViewAt(0));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.bottom_navigation))
                .perform(BottomNavigationActions.clickOnNavigationAt(R.id.ingredient_nav));
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ingredientListView)).check(matches(ListViewMatcher
                .ingredient(0, R.id.ingredientName, ExpectedData.FIRST_INGREDIENT_TITLE_FOR_FIRST_RECIPE)));

    }

    @After
    public void unregisterIdlingResource(){
        Espresso.unregisterIdlingResources(mIdlingResource);
    }

    private void checkRecipeItemExistence(Recipe recipe, int position){

        // Check Data Existence
        onView(withId(R.id.recipeRecycleView))
                .check(matches(RecycleViewMatcher.withTextByPosition(position, R.id.recipeName, recipe.getName())));
        // Check Complete All Expected Incoming Data.
        onView(withId(R.id.recipeRecycleView))
                .check(matches(RecycleViewMatcher.withTextByPosition(position, R.id.servings, String.valueOf(recipe.getServings()))));


    }

    private static class ExpectedData{
        static final Recipe RECIPE_1 = new Recipe(0, "Nutella Pie", null, null, 8, null);
        static final Recipe RECIPE_2 = new Recipe(0, "Brownies", null, null, 8, null);
        static final Recipe RECIPE_3 = new Recipe(0, "Yellow Cake", null, null, 8, null);

        static final String THIRD_STEP_TITLE_FOR_FIRST_RECIPE = "Prep the cookie crust.";
        static final String FIRST_INGREDIENT_TITLE_FOR_FIRST_RECIPE = "Graham Cracker crumbs";
    }

}
