package com.mobiroller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

public abstract class BackHandledFragment extends BaseModuleFragment {
    protected BackHandlerInterface backHandlerInterface;

    public interface BackHandlerInterface {
        void setSelectedFragment(BackHandledFragment backHandledFragment);
    }

    public abstract String getTagText();

    public abstract boolean onBackPressed();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getActivity() instanceof BackHandlerInterface) {
            this.backHandlerInterface = (BackHandlerInterface) getActivity();
            return;
        }
        throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
    }

    public void hideToolbar(Toolbar toolbar) {
        toolbar.setVisibility(8);
    }

    public void onStart() {
        super.onStart();
        this.backHandlerInterface.setSelectedFragment(this);
    }

    public int getStatusBarColor() {
        int actionBarColor = this.sharedPrefHelper.getActionBarColor();
        return Color.argb(Color.alpha(actionBarColor), Math.min(Math.round(((float) Color.red(actionBarColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.green(actionBarColor)) * 0.9f), 255), Math.min(Math.round(((float) Color.blue(actionBarColor)) * 0.9f), 255));
    }
}
