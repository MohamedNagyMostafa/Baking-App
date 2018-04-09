package com.adja.apps.mohamednagy.bakingapp.network;

import android.content.Context;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.R;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.helper.NetworkState;
import com.adja.apps.mohamednagy.bakingapp.permission.PermissionHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public abstract class NetworkHandler {
    private RecipeClient mRecipeClient;
    private Context mContext;

    protected NetworkHandler(Context context){
        mContext = context;
        init();
    }

    private void init(){
        mRecipeClient =
                ApiClient.getClient().create(RecipeClient.class);
    }

    public void execute(){
        if(testNetwork()) {
            Call<Recipe[]> call = mRecipeClient.recipesResponse();

            call.enqueue(new Callback<Recipe[]>() {
                @Override
                public void onResponse(Call<Recipe[]> call, Response<Recipe[]> response) {
                    onPostExecute(response.body());
                }

                @Override
                public void onFailure(Call<Recipe[]> call, Throwable t) {
                    //TODO: show msg to user.
                    NetworkHandler.this.onFailure(t.getMessage());
                }
            });
        }else{
            Log.e("network","not exist");
        }
    }

    private boolean testNetwork(){
        if(PermissionHandler.checkPermission(PermissionHandler.INTERNET_PERMISSION, mContext) &&
                PermissionHandler.checkPermission(PermissionHandler.ACCESS_INTERNET_STATE, mContext)){
            if(NetworkState.hasInternet(mContext)){
                return true;
            }else{
                onFailure(mContext.getResources().getString(R.string.no_network_connection));
            }
        }else{
            PermissionHandler.askPermission(mContext, PermissionHandler.ACCESS_INTERNET_STATE, PermissionHandler.INTERNET_PERMISSION);
        }
        return false;
    }

    /**
     * Notify Network Process error.
     * @param msg   error message
     */
    public void notifyError(String msg){
        onFailure(msg);
    }

    protected abstract void onPostExecute(Recipe[] recipes);
    protected abstract void onFailure(String message);
}
