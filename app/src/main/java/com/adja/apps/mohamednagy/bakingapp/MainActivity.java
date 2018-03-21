package com.adja.apps.mohamednagy.bakingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;
import com.adja.apps.mohamednagy.bakingapp.permission.PermissionHandler;

public class MainActivity extends AppCompatActivity {

    private NetworkHandler networkHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkHandler = new NetworkHandler(getBaseContext()) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionHandler.REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    networkHandler.execute();
                }else{
                    // TODO: error msg
                }
        }
    }
}
