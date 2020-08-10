package com.mobiroller.fragments.user;

import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerEmptyView;

public class UserAddressFragment_ViewBinding implements Unbinder {
    private UserAddressFragment target;
    private View view7f0a0247;

    public UserAddressFragment_ViewBinding(final UserAddressFragment userAddressFragment, View view) {
        this.target = userAddressFragment;
        userAddressFragment.emptyView = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", MobirollerEmptyView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.fab_button, "field 'addAddress' and method 'onClickFabButton'");
        userAddressFragment.addAddress = (FloatingActionButton) C0812Utils.castView(findRequiredView, R.id.fab_button, "field 'addAddress'", FloatingActionButton.class);
        this.view7f0a0247 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userAddressFragment.onClickFabButton();
            }
        });
        userAddressFragment.addressList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'addressList'", RecyclerView.class);
        userAddressFragment.mainLayout = (CoordinatorLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", CoordinatorLayout.class);
    }

    public void unbind() {
        UserAddressFragment userAddressFragment = this.target;
        if (userAddressFragment != null) {
            this.target = null;
            userAddressFragment.emptyView = null;
            userAddressFragment.addAddress = null;
            userAddressFragment.addressList = null;
            userAddressFragment.mainLayout = null;
            this.view7f0a0247.setOnClickListener(null);
            this.view7f0a0247 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
