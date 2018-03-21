package com.adja.apps.mohamednagy.bakingapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.database.helper.Projection;
import com.adja.apps.mohamednagy.bakingapp.database.helper.UriController;
import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.model.Ingredient;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testing();
    }

    private void testing(){
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(new Ingredient(5, "DOM", "KOK"));
//        ingredients.add(new Ingredient(10, "DOM", "KOK"));
//        ingredients.add(new Ingredient(5, "SO", "KOK"));
//        ingredients.add(new Ingredient(4, "DOM", "KOK"));
//        ingredients.add(new Ingredient(2, "COP", "KOK"));
//
//        List<Step> steps = new ArrayList<>();
//        steps.add(new Step(1, "asdasdasd", "dasdasdadsad", "www.asdasd", "dasdasdas"));
//        steps.add(new Step(2, "asdasdasd", "dasdasdadsad", "www.asd", "sada"));
//        steps.add(new Step(3, "asdasdasd", "dasdasdadsad", "www.asdasd", "dasdasdas"));
//        steps.add(new Step(4, "asdasdasd", "asda", "www.asdasd", "sad"));
//
//        Recipe recipe = new Recipe(1, "mohamed", ingredients, steps, 12, null);
//
//        ContentValues contentValuesRecipe = new ContentValues();
//        contentValuesRecipe.put(DbContent.Recipe.RECIPE_NAME_COLUMN, recipe.getName());
//
//        ContentValues contentValuesIngredient = new ContentValues();
//        contentValuesIngredient.put(DbContent.Ingredient.INGREDIENT_QUANTITY_COLUMN,
//                ingredients.get(0).getQuantity());
//        contentValuesIngredient.put(DbContent.Ingredient.INGREDIENT_MEASURE_COLUMN,
//                ingredients.get(0).getMeasure());
//        contentValuesIngredient.put(DbContent.Ingredient.INGREDIENT_COLUMN,
//                ingredients.get(0).getIngredient());
//
//        ContentValues contentValuesStep = new ContentValues();
//        contentValuesStep.put(DbContent.Step.STEP_DESCRIPTION_COLUMN,
//                steps.get(0).getDescription());
//        contentValuesStep.put(DbContent.Step.STEP_SHORT_DESCRIPTION_COLUMN,
//                steps.get(0).getShortDescription());
//        contentValuesStep.put(DbContent.Step.STEP_VIDEO_URL_COLUMN,
//                steps.get(0).getVideoLink());
//
//
//
//        recipe.setId(ContentUris.parseId(getContentResolver().insert(
//                UriController.getRecipeTableUri(),
//                contentValuesRecipe
//        )));
//
//        contentValuesStep.put(DbContent.Step.STEP_RECIPE_ID_COLUMN, recipe.getId());
//        contentValuesIngredient.put(DbContent.Ingredient.INGREDIENT_RECIPE_ID_COLUMN, recipe.getId());
//        getContentResolver().insert(
//                UriController.getIngredientTableUri(),
//                contentValuesIngredient
//        );
//        getContentResolver().insert(
//                UriController.getStepTableUri(),
//                contentValuesStep
//        );

        Cursor cursor = getContentResolver().query(
                UriController.getRecipeStepIngredientJoinUri(),
                Projection.RECIPE_INGREDIENT_STEP_JOIN,
                null,
                null,
                null
        );
        if(cursor.moveToNext()) {
            String name = cursor.getString(Projection.RECIPE_NAME_JOIN_COLUMN);
            int quantity = cursor.getInt(Projection.INGREDIENT_QUANTITY_JOIN_COLUMN);
            String des = cursor.getString(Projection.STEP_DESCRIPTION_JOIN_COLUMN);

            Log.e("data", name + " + " + String.valueOf(quantity) + " + " + des);
        }else{
            Log.e("cursor", "0");
        }
    }
}
