package com.adja.apps.mohamednagy.bakingapp.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.databinding.RecipeViewBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.model.Step;
import com.adja.apps.mohamednagy.bakingapp.ui.sys.SelectedSystem;
import com.squareup.picasso.Picasso;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mohamed Nagy on 3/31/2018 .
 * Project projects submission
 * Time    1:16 PM
 */

public class RecipeRecycleView extends RecyclerView.Adapter<RecipeRecycleView.RecipeViewHolder> {

    private List<Recipe>          mRecipes;
    private Context               mContext;
    private OnRecipeClickListener mOnRecipeClickListener;
    private SelectedSystem mSelectedSys;

    public RecipeRecycleView(List<Recipe> recipes, Context context){
        mRecipes = recipes;
        mContext = context;
        mSelectedSys = new SelectedSystem();
    }

    public void setRecipeClickListener(OnRecipeClickListener onRecipeClickListener){
        mOnRecipeClickListener = onRecipeClickListener;
    }

    public void setSelectedView(Long id){
        if(id != null)
            mSelectedSys.setId(id);
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new RecipeViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.recipe_view,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);
        Log.e("recipe id",String.valueOf(recipe.getId()));
        holder.RECIPE_VIEW.recipeName.setText(recipe.getName());
        holder.RECIPE_VIEW.servings.setText(String.valueOf(recipe.getServings()));

        mSelectedSys.checkSelected(recipe.getId(), holder.RECIPE_VIEW.horizontalDividerLineLeft, mContext);
        assert mOnRecipeClickListener != null;

        holder.RECIPE_VIEW.recipeCardView.setOnClickListener(
                view -> mOnRecipeClickListener.onClickListener(mSelectedSys.updateSelected(recipe.getId(),
                        holder.RECIPE_VIEW.horizontalDividerLineLeft, mContext))
        );

        if(!recipe.getImageURL().isEmpty()){
            Picasso.with(mContext).load(recipe.getImageURL()).into(holder.RECIPE_VIEW.imageView);
        }
    }

    public void swap(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mRecipes != null)?mRecipes.size():0;
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder{
        final RecipeViewBinding RECIPE_VIEW;

        RecipeViewHolder(RecipeViewBinding recipeViewBinding) {
            super(recipeViewBinding.getRoot());
            RECIPE_VIEW = recipeViewBinding;
        }
    }

    public interface OnRecipeClickListener{
        void onClickListener(long recipeId);
    }
}
