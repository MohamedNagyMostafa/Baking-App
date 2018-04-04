package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.RecipeFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.RecipeRecycleView;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SelectedSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.NavigationBottomSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    1:12 PM
 */

public class RecipeListFragment extends FragmentNav {

    private DatabaseRetriever.RecipeFragmentRetriever mRecipeFragmentRetriever;
    private RecipeFragmentBinding                     mRecipeFragmentBinding;
    private Long                                      mCurrentSelectedRecipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeFragmentRetriever = new DatabaseRetriever.RecipeFragmentRetriever(getActivity().getContentResolver());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);

        mRecipeFragmentBinding = DataBindingUtil.bind(rootView);

        // Handle Rotation.
        if(savedInstanceState != null){
            mCurrentSelectedRecipe = savedInstanceState.getLong(Extras.RecipeListFragmentData.SELECTED_RECIPE_ID);
        }

        //Handle Recycle View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecipeRecycleView recipeRecycleView      = new RecipeRecycleView(null, getContext());

        recipeRecycleView.setSelectedView(mCurrentSelectedRecipe);

        recipeRecycleView.setRecipeClickListener(((recipeId) -> {
            mCurrentSelectedRecipe = recipeId;
            NavigationBottomSystem.FragmentIntent fragmentIntent = new NavigationBottomSystem.FragmentIntent(StepFragment.class);

            Bundle bundle = new Bundle();
            bundle.putLong(Extras.StepFragmentData.RECIPE_ID, recipeId);
            fragmentIntent.put(bundle);

            startFragment(fragmentIntent);
        }));

        mRecipeFragmentBinding.recipeRecycleView.setLayoutManager(layoutManager);
        mRecipeFragmentBinding.recipeRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecipeFragmentBinding.recipeRecycleView.setAdapter(recipeRecycleView);

        mRecipeFragmentRetriever.getRecipesFromDatabase(UriController.getRecipeTableUri(), recipes -> {
            if(recipes.size() > 0){
                Toast.makeText(getContext(), "data in database", Toast.LENGTH_SHORT).show();
                recipeRecycleView.swap(recipes);
                // Handle scroll rotation.
                mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onRestoreInstanceState(
                        savedInstanceState != null ? savedInstanceState.getParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION) : null);

            }else{
                NetworkHandler networkHandler = new NetworkHandler(getContext()) {
                    @Override
                    protected void onPostExecute(Recipe[] recipes) {
                        recipeRecycleView.swap(Arrays.asList(recipes));
                        // Save data.
                        insertDataToDatabase(Arrays.asList(recipes));
                        // Handle scroll rotation.
                        mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onRestoreInstanceState(
                                savedInstanceState != null ? savedInstanceState.getParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION) : null);
                    }

                    @Override
                    protected void onFailure(String message) {

                    }
                };
                networkHandler.execute();
            }
        });

        return rootView;
    }

    private void insertDataToDatabase(List<Recipe> recipes){
        mRecipeFragmentRetriever.insertRecipesToDatabase(UriController.getRecipeTableUri(), recipes);

        for(Recipe recipe: recipes){

            mRecipeFragmentRetriever.insertStepsToDatabase(
                    UriController.getStepTableUri(),
                    recipe.getSteps(),
                    recipe.getId() + 1
            );

            mRecipeFragmentRetriever.insertIngredientToDatabase(
                    UriController.getIngredientTableUri(),
                    recipe.getIngredients(),
                    recipe.getId() +1
            );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(Extras.RecipeListFragmentData.SELECTED_RECIPE_ID, (mCurrentSelectedRecipe!= null)?mCurrentSelectedRecipe: SelectedSystem.DEFAULT_SELECTED_ID);
        outState.putParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION, mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onSaveInstanceState());

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        mRecipeFragmentRetriever.release();
        // To Handle Data During Swap.
        getSaverSystem().save(getSavedData());
        super.onDestroyView();
    }

    private Bundle getSavedData(){
        Bundle bundle = new Bundle();
        bundle.putLong(Extras.RecipeListFragmentData.SELECTED_RECIPE_ID, mCurrentSelectedRecipe != null ?mCurrentSelectedRecipe: SelectedSystem.DEFAULT_SELECTED_ID);
        bundle.putParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION,
                mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onSaveInstanceState()
        );

        return bundle;
    }

}
