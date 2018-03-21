package com.adja.apps.mohamednagy.bakingapp.network;

import com.adja.apps.mohamednagy.bakingapp.model.Recipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public abstract class NetworkHandler {
    private RecipeClient mRecipeClient;

    public NetworkHandler(){
        init();
    }

    private void init(){
        mRecipeClient =
                ApiClient.getClient().create(RecipeClient.class);
    }

    public void execute(){
        Call<Recipe[]> call = mRecipeClient.recipesResponse();
        call.enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(Call<Recipe[]> call, Response<Recipe[]> response) {
                onPostExecute(response.body());
            }

            @Override
            public void onFailure(Call<Recipe[]> call, Throwable t) {
                //TODO: show msg to user.
            }
        });
    }

    protected abstract void onPostExecute(Recipe[] recipes);
}
