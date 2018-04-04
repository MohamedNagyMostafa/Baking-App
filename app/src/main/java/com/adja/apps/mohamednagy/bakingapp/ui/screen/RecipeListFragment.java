package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.databinding.DataBindingUtil;
import android.net.Uri;
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
import android.widget.Toast;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.RecipeFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.RecipeRecycleView;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class RecipeListFragment extends FragmentNav {

    private DatabaseRetriever.RecipeFragmentRetriever mRecipeFragmentRetriever;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecipeFragmentRetriever = new DatabaseRetriever.RecipeFragmentRetriever(getActivity().getContentResolver());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        Log.e("fragment view","aaaaaaaaaaaaaaa fragment view called");

        RecipeFragmentBinding recipeFragmentBinding = DataBindingUtil.bind(rootView);

        //Handle Recycle View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecipeRecycleView recipeRecycleView      = new RecipeRecycleView(null, getContext());

        recipeFragmentBinding.recipeRecycleView.setLayoutManager(layoutManager);
        recipeFragmentBinding.recipeRecycleView.setItemAnimator(new DefaultItemAnimator());
        recipeFragmentBinding.recipeRecycleView.setAdapter(recipeRecycleView);

        mRecipeFragmentRetriever.getRecipesFromDatabase(UriController.getRecipeTableUri(), recipes -> {
            if(recipes.size() > 0){
                Toast.makeText(getContext(), "data in database", Toast.LENGTH_SHORT).show();
                recipeRecycleView.swap(recipes);

            }else{
                NetworkHandler networkHandler = new NetworkHandler(getContext()) {
                    @Override
                    protected void onPostExecute(Recipe[] recipes) {
                        recipeRecycleView.swap(Arrays.asList(recipes));
                        // Save data.
                        insertDataToDatabase(Arrays.asList(recipes));
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
        outState.putInt("deo", 1);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(getSaverSystem().savedData() != null)
            Log.e("data aaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaa " + getSaverSystem().savedData().getInt("deo"));
    }

    @Override
    public void onDestroyView() {
        mRecipeFragmentRetriever.release();
        super.onDestroyView();
    }

}
