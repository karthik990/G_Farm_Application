package com.mobiroller.activities.menu;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobiroller.mobi942763453128.R;

public class MainActivity_ViewBinding implements Unbinder {
    private MainActivity target;

    public MainActivity_ViewBinding(MainActivity mainActivity) {
        this(mainActivity, mainActivity.getWindow().getDecorView());
    }

    public MainActivity_ViewBinding(MainActivity mainActivity, View view) {
        this.target = mainActivity;
        mainActivity.frameContainer = (FrameLayout) C0812Utils.findRequiredViewAsType(view, R.id.frame_container, "field 'frameContainer'", FrameLayout.class);
        mainActivity.mainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", LinearLayout.class);
        mainActivity.menuList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.mainList, "field 'menuList'", RecyclerView.class);
        mainActivity.bottomNavigationView = (BottomNavigationView) C0812Utils.findRequiredViewAsType(view, R.id.navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
    }

    public void unbind() {
        MainActivity mainActivity = this.target;
        if (mainActivity != null) {
            this.target = null;
            mainActivity.frameContainer = null;
            mainActivity.mainLayout = null;
            mainActivity.menuList = null;
            mainActivity.bottomNavigationView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
