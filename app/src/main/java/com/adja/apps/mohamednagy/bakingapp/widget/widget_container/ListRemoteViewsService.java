package com.adja.apps.mohamednagy.bakingapp.widget.widget_container;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Mohamed Nagy on 4/6/2018 .
 * Project projects submission
 * Time    4:01 PM
 */

public class ListRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}