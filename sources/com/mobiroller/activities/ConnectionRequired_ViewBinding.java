package com.mobiroller.activities;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class ConnectionRequired_ViewBinding implements Unbinder {
    private ConnectionRequired target;
    private View view7f0a03b5;
    private View view7f0a05e3;
    private View view7f0a064c;

    public ConnectionRequired_ViewBinding(ConnectionRequired connectionRequired) {
        this(connectionRequired, connectionRequired.getWindow().getDecorView());
    }

    public ConnectionRequired_ViewBinding(final ConnectionRequired connectionRequired, View view) {
        this.target = connectionRequired;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.try_again, "field 'try_again' and method 'tryAgain'");
        connectionRequired.try_again = (Button) C0812Utils.castView(findRequiredView, R.id.try_again, "field 'try_again'", Button.class);
        this.view7f0a05e3 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                connectionRequired.tryAgain();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.wifi_check, "field 'wifiCheck' and method 'openWifiSettings'");
        connectionRequired.wifiCheck = (Button) C0812Utils.castView(findRequiredView2, R.id.wifi_check, "field 'wifiCheck'", Button.class);
        this.view7f0a064c = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                connectionRequired.openWifiSettings();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.mobile_check, "field 'mobileCheck' and method 'openMobileSettings'");
        connectionRequired.mobileCheck = (Button) C0812Utils.castView(findRequiredView3, R.id.mobile_check, "field 'mobileCheck'", Button.class);
        this.view7f0a03b5 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                connectionRequired.openMobileSettings();
            }
        });
        connectionRequired.mainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.connection_main_layout, "field 'mainLayout'", LinearLayout.class);
    }

    public void unbind() {
        ConnectionRequired connectionRequired = this.target;
        if (connectionRequired != null) {
            this.target = null;
            connectionRequired.try_again = null;
            connectionRequired.wifiCheck = null;
            connectionRequired.mobileCheck = null;
            connectionRequired.mainLayout = null;
            this.view7f0a05e3.setOnClickListener(null);
            this.view7f0a05e3 = null;
            this.view7f0a064c.setOnClickListener(null);
            this.view7f0a064c = null;
            this.view7f0a03b5.setOnClickListener(null);
            this.view7f0a03b5 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
