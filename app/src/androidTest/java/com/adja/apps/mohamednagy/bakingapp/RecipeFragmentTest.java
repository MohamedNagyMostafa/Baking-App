package com.adja.apps.mohamednagy.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.util.actions.BottomNavigationActions;
import com.adja.apps.mohamednagy.bakingapp.util.matchers.ListViewMatcher;
import com.adja.apps.mohamednagy.bakingapp.util.matchers.RecycleViewMatcher;
import com.adja.apps.mohamednagy.bakingapp.util.actions.RecycleViewActions;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.adja.apps.mohamednagy.bakingapp.util.Util.sleeping;

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

    /**
     * Check recipe data retrieved from the API
     */
    @Test
    public void checkRecipeData_RetrievingRecipeDataFromAPI(){

        checkRecipeItemExistence(ExpectedData.RECIPE_1, ExpectedData.FIRST_RECIPE_POSITION);
        checkRecipeItemExistence(ExpectedData.RECIPE_2, ExpectedData.SECOND_RECIPE_POSITION);
        checkRecipeItemExistence(ExpectedData.RECIPE_3, ExpectedData.THIRD_RECIPE_POSITION);
    }

    /**
     * Check the selected recipe move to its own right steps.
     */
    @Test
    public void openRightStepForRecipe_ClickOnRecipeItem(){
        // Scroll to first item
        onView(withId(R.id.recipeRecycleView)).perform(RecycleViewActions.scrollToPosition(0))
                // Click on the item
                .perform(RecycleViewActions.clickOnViewAt(0));
        // To avoid any conflicting with database retrieving time
        // and fragments swap.
        sleeping();

        onView(withId(R.id.stepperRecycleView))
                // Scroll two third step
                .perform(RecycleViewActions.scrollToPosition(2))
                // check step title to expected one
                .check(matches(RecycleViewMatcher.withTextByPosition(2, R.id.stepTitle,
                        ExpectedData.THIRD_STEP_TITLE_FOR_FIRST_RECIPE)));
    }

    /**
     * Check the current recipe has its own ingredients
     */
    @Test
    public void openRightIngredientForRecipe_ClickOnRecipeItem(){
        // Scroll to first item
        onView(withId(R.id.recipeRecycleView)).perform(RecycleViewActions.scrollToPosition(0))
                // Click on the item
                .perform(RecycleViewActions.clickOnViewAt(0));
        // To avoid any conflicting with database retrieving time
        // and fragments swap.
        sleeping();
        // Click on ingredient item at the navigation bar.
        onView(withId(R.id.bottom_navigation))
                .perform(BottomNavigationActions.clickOnNavigationAt(R.id.ingredient_nav));
        // avoid conflicting
        sleeping();
        // Check the first ingredient name with expected one.
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

        static final int FIRST_RECIPE_POSITION = 0;
        static final int SECOND_RECIPE_POSITION = 1;
        static final int THIRD_RECIPE_POSITION = 2;

        static final String THIRD_STEP_TITLE_FOR_FIRST_RECIPE = "Prep the cookie crust.";
        static final String FIRST_INGREDIENT_TITLE_FOR_FIRST_RECIPE = "Graham Cracker crumbs";
    }

}
