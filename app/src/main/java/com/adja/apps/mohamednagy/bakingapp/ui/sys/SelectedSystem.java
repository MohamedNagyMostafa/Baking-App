package com.adja.apps.mohamednagy.bakingapp.ui.sys;

import android.content.Context;
import android.view.View;

import com.adja.apps.mohamednagy.bakingapp.R;

/**
 * Created by Mohamed Nagy on 4/4/2018 .
 * Project projects submission
 * Time    2:23 PM
 */

public class SelectedSystem {
    public static final Long DEFAULT_SELECTED_ID = 1L;
    private Long mId;
    private View mView;

    public SelectedSystem(){}

    public SelectedSystem(Long id, View view){
        mId   = id;
        mView = view;
    }

    public Long getId() {
        return (mId == null)?DEFAULT_SELECTED_ID: mId;
    }

    public View getView() {
        return mView;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    public void checkSelected(long id, View view, Context context){
        if(id == getId()){
            mId = id;
            mView = view;

            mView.setBackground(context.getDrawable(R.drawable.selected_card));
        }else{
            view.setBackground(context.getDrawable(R.drawable.unselected_card));
        }
    }

    public Long updateSelected(long id, View view, Context context){
        long previousId = mId;
        mId = id;
        if(mView != null)
            checkSelected(previousId, mView, context);
        checkSelected(id, view, context);
        mView = view;

        return mId;
    }
}
