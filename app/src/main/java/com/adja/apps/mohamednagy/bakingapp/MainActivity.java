package com.adja.apps.mohamednagy.bakingapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adja.apps.mohamednagy.bakingapp.databinding.ActivityMainBinding;
import com.adja.apps.mohamednagy.bakingapp.media.Media;
import com.adja.apps.mohamednagy.bakingapp.model.Recipe;
import com.adja.apps.mohamednagy.bakingapp.network.NetworkHandler;
import com.adja.apps.mohamednagy.bakingapp.permission.PermissionHandler;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Media.Builder mediaBuilder = new Media.Builder()
                .mediaView(activityMainBinding.simpleExoPlayer)
                .videoLink("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4")
                .mediaStateListener(new Media.OnMediaStateChanged() {
                    @Override
                    public void onStateChanged(Media.State state, boolean isPlaying) {

                        switch (state){
                            case READY:
                                Toast.makeText(MainActivity.this, "ready", Toast.LENGTH_SHORT).show();
                            break;
                            case IDLE:
                                Toast.makeText(MainActivity.this, "idle", Toast.LENGTH_SHORT).show();
                            break;
                            case ENDED:
                            Toast.makeText(MainActivity.this, "ended", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                });
        final Media media = new Media(mediaBuilder, this);
        activityMainBinding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                media.play();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionHandler.REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                }else{
                    // TODO: error msg
                    Log.e("error","erro " + String.valueOf(grantResults[0]) + " " + String.valueOf(grantResults[1]));
                }
        }
    }
}
