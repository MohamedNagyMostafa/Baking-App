package com.adja.apps.mohamednagy.bakingapp.testing_tools;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mohamed Nagy on 4/10/2018 .
 * Project projects submission
 * Time    4:50 PM
 */

public class BakingResourceIdle implements IdlingResource {
    @Nullable private volatile ResourceCallback mResourceCallback;
    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mAtomicBoolean.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }

    public void setIdleState(boolean idleState){
        mAtomicBoolean.set(idleState);
        if(isIdleNow() && mResourceCallback != null)
            mResourceCallback.onTransitionToIdle();
    }

    public interface onStateChangingListener{
        void onChanged(boolean newState);
    }
}
