package com.adja.apps.mohamednagy.bakingapp.ui.screen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.MainActivity;
import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.IngredientFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.IngredientListAdapter;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SaverSystem;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018.
 */

public class IngredientFragment extends FragmentNav {

    private DatabaseRetriever.IngredientFragmentRetriever mIngredientFragmentRetriever;
    private IngredientFragmentBinding mIngredientFragmentBinding;
    private Long mRecipeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIngredientFragmentRetriever = new DatabaseRetriever.IngredientFragmentRetriever(getActivity().getContentResolver());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.ingredient_fragment, container, false);

        Bundle bundle = getPreviousState(savedInstanceState);
        if(bundle != null) mRecipeId = getPreviousState(savedInstanceState).getLong(Extras.IngredientData.RECIPE_ID);
        // Get Views
        mIngredientFragmentBinding = DataBindingUtil.bind(rootView);
        // Handle ListView
        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(getContext());
        mIngredientFragmentBinding.ingredientListView.setAdapter(ingredientListAdapter);
        // Retrieve Data From Database
        {
            mIngredientFragmentBinding.emptyView.setVisibility(View.VISIBLE);
            mIngredientFragmentBinding.progressBar.setVisibility(View.VISIBLE);
        }
        if(mRecipeId != null)
            mIngredientFragmentRetriever.getIngredientFromDatabase(
                    UriController.getIngredientTableUriByRecipeId(mRecipeId),
                    ingredients -> {
                        if(ingredients.size() > 0){
                            mIngredientFragmentBinding.emptyView.setVisibility(View.GONE);
                            ingredientListAdapter.swap(ingredients);

                            checkPreviousScroll();

                        }else{
                            mIngredientFragmentBinding.emptyView.setVisibility(View.VISIBLE);
                        }

                        mIngredientFragmentBinding.progressBar.setVisibility(View.GONE);

                    }
            );
        else
            mIngredientFragmentBinding.progressBar.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(getSavedData(outState));
    }

    @Override
    public void onDestroyView() {
        getSaverSystem().save(getSavedData());
        mIngredientFragmentRetriever.release();
        super.onDestroyView();
    }

    private void checkPreviousScroll(){
        Parcelable parcelable = getSaverSystem().savedData().getParcelable(Extras.IngredientData.INGREDIENT_LIST_SCROLL_POSITION);
        if(parcelable != null)
            mIngredientFragmentBinding.ingredientListView.onRestoreInstanceState(parcelable);

    }

    private Bundle getSavedData(){
        Bundle bundle = new Bundle();
        if(mRecipeId != null) bundle.putLong(Extras.IngredientData.RECIPE_ID, mRecipeId);
        bundle.putParcelable(Extras.IngredientData.INGREDIENT_LIST_SCROLL_POSITION,
                mIngredientFragmentBinding.ingredientListView.onSaveInstanceState());

        return bundle;
    }

    private Bundle getSavedData(Bundle bundle){
        if(mRecipeId != null) bundle.putLong(Extras.IngredientData.RECIPE_ID, mRecipeId);
        bundle.putParcelable(Extras.IngredientData.INGREDIENT_LIST_SCROLL_POSITION,
                mIngredientFragmentBinding.ingredientListView.onSaveInstanceState());

        return bundle;
    }

    private Bundle getPreviousState(Bundle saveInstanceState){
        if(saveInstanceState == null){
            return getSaverSystem().savedData();
        }else{
            return saveInstanceState;
        }
    }
}

