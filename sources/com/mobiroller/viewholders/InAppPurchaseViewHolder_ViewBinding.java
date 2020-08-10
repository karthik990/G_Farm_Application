package com.mobiroller.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class InAppPurchaseViewHolder_ViewBinding implements Unbinder {
    private InAppPurchaseViewHolder target;

    public InAppPurchaseViewHolder_ViewBinding(InAppPurchaseViewHolder inAppPurchaseViewHolder, View view) {
        this.target = inAppPurchaseViewHolder;
        inAppPurchaseViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
        inAppPurchaseViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
    }

    public void unbind() {
        InAppPurchaseViewHolder inAppPurchaseViewHolder = this.target;
        if (inAppPurchaseViewHolder != null) {
            this.target = null;
            inAppPurchaseViewHolder.image = null;
            inAppPurchaseViewHolder.title = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
