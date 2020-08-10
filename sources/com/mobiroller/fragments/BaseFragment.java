package com.mobiroller.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.interfaces.DaggerFragmentComponent;
import com.mobiroller.interfaces.FragmentComponent;
import com.mobiroller.models.ScreenModel;
import com.mobiroller.module.FragmentModule;

public abstract class BaseFragment extends Fragment {
    private FragmentComponent fragmentComponent;
    public String screenId;
    public ScreenModel screenModel;
    public String screenType;

    public abstract Fragment injectFragment(FragmentComponent fragmentComponent2);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.fragmentComponent = fragmentComponent();
        this.fragmentComponent.inject((Fragment) this);
        injectFragment(this.fragmentComponent);
    }

    /* access modifiers changed from: protected */
    public final FragmentComponent fragmentComponent() {
        if (this.fragmentComponent == null) {
            this.fragmentComponent = DaggerFragmentComponent.builder().appComponent(MobiRollerApplication.getMainComponent()).fragmentModule(new FragmentModule(this, getActivity())).build();
        }
        return this.fragmentComponent;
    }

    public void hideToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setVisibility(8);
        }
    }

    public int getStatusBarColor(int i) {
        return Color.argb(Color.alpha(i), Math.min(Math.round(((float) Color.red(i)) * 0.9f), 255), Math.min(Math.round(((float) Color.green(i)) * 0.9f), 255), Math.min(Math.round(((float) Color.blue(i)) * 0.9f), 255));
    }

    public void closeKeyboard() {
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
}
