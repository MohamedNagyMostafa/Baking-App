package com.adja.apps.mohamednagy.bakingapp.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.databinding.IngredientViewBinding;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Nagy on 4/4/2018 .
 * Project projects submission
 * Time    5:38 PM
 */

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    private List<Ingredient> mIngredientList;

    public IngredientListAdapter(@NonNull Context context) {
        super(context, 0);
        mIngredientList = new ArrayList<>();
    }

    public void swap(List<Ingredient> ingredients){
        mIngredientList = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        if(rootView == null) {
            IngredientViewBinding ingredientViewBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.ingredient_view, parent, false);
            Ingredient ingredient = mIngredientList.get(position);

            ingredientViewBinding.ingredientMeasureUnit.setText(ingredient.getMeasure());
            ingredientViewBinding.ingredientName.setText(ingredient.getIngredient());
            ingredientViewBinding.quantityNumber.setText(String.valueOf(ingredient.getQuantity()));

            measureUnitCircle(ingredientViewBinding.measureUnitCircle, ingredient.getMeasure());

            rootView = ingredientViewBinding.getRoot();
        }

        return rootView;

    }

    private void measureUnitCircle(View view, String measureUnit){
        if(measureUnit.equals(getContext().getString(R.string.measure_cup))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_cup_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_unit))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_unit_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_k))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_k_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_g))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_g_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_oz))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_oz_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_tsp))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_tsp_circle));
        }else if(measureUnit.equals(getContext().getString(R.string.measure_tblsp))){
            view.setBackground(getContext().getDrawable(R.drawable.ingredient_tblsp_circle));
        }
    }

}
