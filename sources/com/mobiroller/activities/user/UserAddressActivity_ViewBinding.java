package com.mobiroller.activities.user;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.tabs.TabLayout;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerToolbar;

public class UserAddressActivity_ViewBinding implements Unbinder {
    private UserAddressActivity target;

    public UserAddressActivity_ViewBinding(UserAddressActivity userAddressActivity) {
        this(userAddressActivity, userAddressActivity.getWindow().getDecorView());
    }

    public UserAddressActivity_ViewBinding(UserAddressActivity userAddressActivity, View view) {
        this.target = userAddressActivity;
        userAddressActivity.tabLayout = (TabLayout) C0812Utils.findRequiredViewAsType(view, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
        userAddressActivity.viewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", ViewPager.class);
        userAddressActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
    }

    public void unbind() {
        UserAddressActivity userAddressActivity = this.target;
        if (userAddressActivity != null) {
            this.target = null;
            userAddressActivity.tabLayout = null;
            userAddressActivity.viewPager = null;
            userAddressActivity.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
