package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class aveNotificationBoxViewFragment_ViewBinding implements Unbinder {
    private aveNotificationBoxViewFragment target;

    public aveNotificationBoxViewFragment_ViewBinding(aveNotificationBoxViewFragment avenotificationboxviewfragment, View view) {
        this.target = avenotificationboxviewfragment;
        avenotificationboxviewfragment.notificationList = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.notification_list, "field 'notificationList'", RecyclerView.class);
        avenotificationboxviewfragment.notificationEmptyImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.notification_empty_image, "field 'notificationEmptyImage'", ImageView.class);
        avenotificationboxviewfragment.notificationEmptyText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.notification_empty_text, "field 'notificationEmptyText'", TextView.class);
        avenotificationboxviewfragment.emptyView = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", RelativeLayout.class);
        avenotificationboxviewfragment.notificationRelLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.notification_rel_layout, "field 'notificationRelLayout'", RelativeLayout.class);
        avenotificationboxviewfragment.notificationBoxLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.notification_box_layout, "field 'notificationBoxLayout'", RelativeLayout.class);
        avenotificationboxviewfragment.notificationNotSupportedLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.notification_not_supported_layout, "field 'notificationNotSupportedLayout'", RelativeLayout.class);
    }

    public void unbind() {
        aveNotificationBoxViewFragment avenotificationboxviewfragment = this.target;
        if (avenotificationboxviewfragment != null) {
            this.target = null;
            avenotificationboxviewfragment.notificationList = null;
            avenotificationboxviewfragment.notificationEmptyImage = null;
            avenotificationboxviewfragment.notificationEmptyText = null;
            avenotificationboxviewfragment.emptyView = null;
            avenotificationboxviewfragment.notificationRelLayout = null;
            avenotificationboxviewfragment.notificationBoxLayout = null;
            avenotificationboxviewfragment.notificationNotSupportedLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
