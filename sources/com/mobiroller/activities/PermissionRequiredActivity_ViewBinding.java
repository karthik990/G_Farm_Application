package com.mobiroller.activities;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class PermissionRequiredActivity_ViewBinding implements Unbinder {
    private PermissionRequiredActivity target;
    private View view7f0a0464;
    private View view7f0a05e3;

    public PermissionRequiredActivity_ViewBinding(PermissionRequiredActivity permissionRequiredActivity) {
        this(permissionRequiredActivity, permissionRequiredActivity.getWindow().getDecorView());
    }

    public PermissionRequiredActivity_ViewBinding(final PermissionRequiredActivity permissionRequiredActivity, View view) {
        this.target = permissionRequiredActivity;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.try_again, "field 'try_again' and method 'restartApp'");
        permissionRequiredActivity.try_again = (Button) C0812Utils.castView(findRequiredView, R.id.try_again, "field 'try_again'", Button.class);
        this.view7f0a05e3 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                permissionRequiredActivity.restartApp();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.permission_check, "field 'permissionCheck' and method 'openPermissionSettings'");
        permissionRequiredActivity.permissionCheck = (Button) C0812Utils.castView(findRequiredView2, R.id.permission_check, "field 'permissionCheck'", Button.class);
        this.view7f0a0464 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                permissionRequiredActivity.openPermissionSettings();
            }
        });
        permissionRequiredActivity.mainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.connection_main_layout, "field 'mainLayout'", LinearLayout.class);
        permissionRequiredActivity.description = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.permission_description, "field 'description'", TextView.class);
    }

    public void unbind() {
        PermissionRequiredActivity permissionRequiredActivity = this.target;
        if (permissionRequiredActivity != null) {
            this.target = null;
            permissionRequiredActivity.try_again = null;
            permissionRequiredActivity.permissionCheck = null;
            permissionRequiredActivity.mainLayout = null;
            permissionRequiredActivity.description = null;
            this.view7f0a05e3.setOnClickListener(null);
            this.view7f0a05e3 = null;
            this.view7f0a0464.setOnClickListener(null);
            this.view7f0a0464 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
