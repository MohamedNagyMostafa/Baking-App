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
    private Long mId;
    private View mView;

    public SelectedSystem(){}

    public SelectedSystem(Long id, View view){
        mId   = id;
        mView = view;
    }

    public Long getId() {
        return mId;
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
        if(mId != null && id == mId){
            mId = id;
            mView = view;

            mView.setBackground(context.getDrawable(R.drawable.selected_card));
        }else{
            view.setBackground(context.getDrawable(R.drawable.unselected_card));
        }
    }

    public Long updateSelected(long id, View view, Context context){
        Long previousId = mId;
        mId = id;
        if(mView != null && previousId != null)
            checkSelected(previousId, mView, context);
        checkSelected(id, view, context);
        mView = view;

        return mId;
    }
}
