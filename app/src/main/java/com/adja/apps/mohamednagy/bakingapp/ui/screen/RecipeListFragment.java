package com.adja.apps.mohamednagy.bakingapp.ui.screen;


import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.RecipeFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;
import com.adja.apps.mohamednagy.bakingapp.permission.PermissionHandler;
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
    private NetworkHandler                            mNetworkHandler;

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
        // Handle Rotation or Swap Through Fragments.
        Bundle bundle = getPreviousState(savedInstanceState);
        mCurrentSelectedRecipe =  bundle == null?1L:bundle.getLong(Extras.RecipeListFragmentData.SELECTED_RECIPE_ID);
//        Toast.makeText(getContext(), String.valueOf(mCurrentSelectedRecipe), Toast.LENGTH_LONG).show();
        //Handle Recycle View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecipeRecycleView recipeRecycleView      = new RecipeRecycleView(null, getContext());

        recipeRecycleView.setSelectedView(mCurrentSelectedRecipe);

        recipeRecycleView.setRecipeClickListener(((recipeId) -> {
            if(mCurrentSelectedRecipe == null || mCurrentSelectedRecipe != recipeId) {
                mCurrentSelectedRecipe = recipeId;
                // Reset active step position and video mint to initial state
                openStepFragmentAsNewRecipe();
                updateIngredientRecipe();
            }else{
                // Retrieve the previous state of steps/videos
                openStepFragmentAsSameRecipe();
            }
        }));

        mRecipeFragmentBinding.recipeRecycleView.setLayoutManager(layoutManager);
        mRecipeFragmentBinding.recipeRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecipeFragmentBinding.recipeRecycleView.setAdapter(recipeRecycleView);

        mRecipeFragmentRetriever.getRecipesFromDatabase(UriController.getRecipeTableUri(), recipes -> {
            if(recipes.size() > 0){
                recipeRecycleView.swap(recipes);
                // Handle View upon process.
                {
                    mRecipeFragmentBinding.emptyView.setVisibility(View.GONE);
                    mRecipeFragmentBinding.progressBar.setVisibility(View.GONE);
                }
                // Handle scroll rotation.
                if(bundle != null){
                    mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onRestoreInstanceState(
                            bundle.getParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION)
                    );
                }
            }else{
                mNetworkHandler = new NetworkHandler(getContext()) {
                    @Override
                    protected void onPostExecute(Recipe[] recipes) {
                        recipeRecycleView.swap(Arrays.asList(recipes));
                        // Save data.
                        insertDataToDatabase(Arrays.asList(recipes));
                        // Handle scroll rotation.
                        if(bundle != null){
                            mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onRestoreInstanceState(
                                    bundle.getParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION)
                            );
                        }
                        // Handle View Upon Process
                        {
                            if (recipes.length > 0) {
                                mRecipeFragmentBinding.emptyView.setVisibility(View.GONE);
                            } else {
                                mRecipeFragmentBinding.emptyView.setVisibility(View.VISIBLE);

                            }
                            mRecipeFragmentBinding.progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onFailure(String message) {
                        Snackbar.make((getActivity()).findViewById(R.id.mainScreen), message, Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.retry_action), view -> {
                                    this.execute();
                                }).show();
                        // Handle View upon Process.
                        {
                            mRecipeFragmentBinding.emptyView.setVisibility(View.VISIBLE);
                            mRecipeFragmentBinding.progressBar.setVisibility(View.GONE);
                        }
                    }
                };
                mRecipeFragmentBinding.progressBar.setVisibility(View.VISIBLE);
                mNetworkHandler.execute();
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
        super.onSaveInstanceState(getSavedData(outState));
    }

    @Override
    public void onDestroyView() {
        mRecipeFragmentRetriever.release();
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
    private Bundle getSavedData(Bundle bundle){
        bundle.putLong(Extras.RecipeListFragmentData.SELECTED_RECIPE_ID, mCurrentSelectedRecipe != null ?mCurrentSelectedRecipe: SelectedSystem.DEFAULT_SELECTED_ID);
        bundle.putParcelable(Extras.RecipeListFragmentData.RECIPE_RECYCLE_SCROLL_POSITION,
                mRecipeFragmentBinding.recipeRecycleView.getLayoutManager().onSaveInstanceState()
        );

        return bundle;
    }

    private void openStepFragmentAsNewRecipe(){
        NavigationBottomSystem.FragmentIntent fragmentIntent = new NavigationBottomSystem.FragmentIntent(StepFragment.class);
        fragmentIntent.putExtra(Extras.StepFragmentData.CURRENT_MEDIA_MINT, 0L);
        fragmentIntent.putExtra(Extras.StepFragmentData.CURRENT_STEP_POSITION, 0);
        fragmentIntent.putExtra(Extras.StepFragmentData.RECIPE_ID, mCurrentSelectedRecipe);

        startFragment(fragmentIntent);
    }

    private void openStepFragmentAsSameRecipe(){
        NavigationBottomSystem.FragmentIntent fragmentIntent = new NavigationBottomSystem.FragmentIntent(StepFragment.class);

        fragmentIntent.putExtra(Extras.StepFragmentData.RECIPE_ID, mCurrentSelectedRecipe);

        startFragment(fragmentIntent);
    }

    // Set current selected recipe to ingredient saver system.
    private void updateIngredientRecipe(){
        new NavigationBottomSystem.FragmentIntent(IngredientFragment.class).putExtra(Extras.IngredientData.RECIPE_ID, mCurrentSelectedRecipe);
    }

    private Bundle getPreviousState(Bundle saveInstanceState){
        return saveInstanceState == null? getSaverSystem().savedData() :saveInstanceState;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionHandler.REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    mNetworkHandler.execute();
                }else{
                    mNetworkHandler.notifyError(getString(R.string.permission_denied));
                }

        }
    }
}
