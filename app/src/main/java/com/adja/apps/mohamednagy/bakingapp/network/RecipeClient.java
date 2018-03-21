package com.adja.apps.mohamednagy.bakingapp.network;

import com.adja.apps.mohamednagy.bakingapp.database.structure.DbContent;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public interface RecipeClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<Recipe[]> recipesResponse();
}
