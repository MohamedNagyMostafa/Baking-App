package com.adja.apps.mohamednagy.bakingapp.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Mohamed Nagy on 3/21/2018 .
 * Project projects submission
 * Time    10:57 AM
 */

public class PermissionHandler {

    public static final String INTERNET_PERMISSION = android.Manifest.permission.INTERNET;
    public static final String ACCESS_INTERNET_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE;

    public static final int REQUEST_CODE = 1;

    public static boolean checkPermission(String permission, Context context){
        return (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED);
    }

    public static void askPermission(Context context, String... permissions){
        ActivityCompat.requestPermissions((Activity) context, permissions, REQUEST_CODE);
    }
    // TODO: implement onActivityResult().
}
