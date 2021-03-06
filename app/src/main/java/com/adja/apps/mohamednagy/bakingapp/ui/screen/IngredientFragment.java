package com.adja.apps.mohamednagy.bakingapp.ui.screen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.databinding.IngredientFragmentBinding;
import com.adja.apps.mohamednagy.bakingapp.ui.adapter.IngredientListAdapter;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.navigation.FragmentNav;
import com.adja.apps.mohamednagy.bakingapp.ui.util.DatabaseRetriever;
import com.adja.apps.mohamednagy.bakingapp.ui.util.Extras;

import java.util.List;

/**
 * Created by Mohamed Nagy on 3/27/2018 .
 * Project projects submission
 * Time    10:56 AM
 */

public class IngredientFragment extends FragmentNav {

    private DatabaseRetriever.IngredientFragmentRetriever mIngredientFragmentRetriever;
    private IngredientFragmentBinding mIngredientFragmentBinding;

    private Long mRecipeId;
    private Parcelable mListScrollPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIngredientFragmentRetriever = new DatabaseRetriever.IngredientFragmentRetriever(getActivity().getContentResolver());
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container) {
        View rootView = layoutInflater.inflate(R.layout.ingredient_fragment, container, false);
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
        if (mRecipeId != null){
            // Testing Block
            {
                notifyStateChanging(false);
            }
            mIngredientFragmentRetriever.getIngredientFromDatabase(
                    UriController.getIngredientTableUriByRecipeId(mRecipeId),
                    ingredients -> {
                        if (ingredients.size() > 0) {
                            mIngredientFragmentBinding.emptyView.setVisibility(View.GONE);
                            ingredientListAdapter.swap(ingredients);

                            checkPreviousScroll();

                        } else {
                            mIngredientFragmentBinding.emptyView.setVisibility(View.VISIBLE);
                        }

                        // Testing Block
                        {
                            notifyStateChanging(true);
                        }
                        mIngredientFragmentBinding.progressBar.setVisibility(View.GONE);

                    }
            );
        }else
            mIngredientFragmentBinding.progressBar.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onSaveData(Bundle bundle) {
        if(mRecipeId != 0)
            getSavedData(bundle);
    }

    @Override
    public void onRestoreData(Bundle bundle) {
        mListScrollPosition = bundle.getParcelable(Extras.IngredientData.INGREDIENT_LIST_SCROLL_POSITION);
        mRecipeId = bundle.getLong(Extras.IngredientData.RECIPE_ID);

    }

    @Override
    public void onDestroyView() {
        mIngredientFragmentRetriever.release();
        super.onDestroyView();
    }

    private void checkPreviousScroll(){
        if(mListScrollPosition != null)
            mIngredientFragmentBinding.ingredientListView.onRestoreInstanceState(mListScrollPosition);
    }

    private void getSavedData(Bundle bundle){
        if(mRecipeId != null) bundle.putLong(Extras.IngredientData.RECIPE_ID, mRecipeId);
        bundle.putParcelable(Extras.IngredientData.INGREDIENT_LIST_SCROLL_POSITION,
                mIngredientFragmentBinding.ingredientListView.onSaveInstanceState());
    }

}

