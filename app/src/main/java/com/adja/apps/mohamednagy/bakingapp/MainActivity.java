package com.adja.apps.mohamednagy.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkHandler networkHandler = new NetworkHandler(getBaseContext()) {
            @Override
            protected void onPostExecute(Recipe[] recipes) {
                Log.e("data done", String.valueOf(recipes.length));
            }

            @Override
            protected void onFailure(String message) {
                Log.e("erroor", message);
            }
        };
        networkHandler.execute();
    }

}
