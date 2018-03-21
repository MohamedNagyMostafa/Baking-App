package com.adja.apps.mohamednagy.bakingapp.network.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mohamed Nagy on 3/21/2018.
 */

public class NetworkState {

    private NetworkState(){}

    public static boolean hasInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

}
