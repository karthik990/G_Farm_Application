package com.mobiroller.activities.inapppurchase;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.WrapContentViewPager;

public class InAppPurchaseActivity_ViewBinding implements Unbinder {
    private InAppPurchaseActivity target;
    private View view7f0a0177;

    public InAppPurchaseActivity_ViewBinding(InAppPurchaseActivity inAppPurchaseActivity) {
        this(inAppPurchaseActivity, inAppPurchaseActivity.getWindow().getDecorView());
    }

    public InAppPurchaseActivity_ViewBinding(final InAppPurchaseActivity inAppPurchaseActivity, View view) {
        this.target = inAppPurchaseActivity;
        inAppPurchaseActivity.viewPager = (WrapContentViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", WrapContentViewPager.class);
        inAppPurchaseActivity.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
        inAppPurchaseActivity.imageFilterOne = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image_filter_one, "field 'imageFilterOne'", ImageView.class);
        inAppPurchaseActivity.imageFilterTwo = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image_filter_two, "field 'imageFilterTwo'", ImageView.class);
        inAppPurchaseActivity.viewPagerCountDots = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.view_pager_count_dots, "field 'viewPagerCountDots'", LinearLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.close_button, "field 'closeButton' and method 'onClickCloseButton'");
        inAppPurchaseActivity.closeButton = (ImageView) C0812Utils.castView(findRequiredView, R.id.close_button, "field 'closeButton'", ImageView.class);
        this.view7f0a0177 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseActivity.onClickCloseButton();
            }
        });
    }

    public void unbind() {
        InAppPurchaseActivity inAppPurchaseActivity = this.target;
        if (inAppPurchaseActivity != null) {
            this.target = null;
            inAppPurchaseActivity.viewPager = null;
            inAppPurchaseActivity.image = null;
            inAppPurchaseActivity.imageFilterOne = null;
            inAppPurchaseActivity.imageFilterTwo = null;
            inAppPurchaseActivity.viewPagerCountDots = null;
            inAppPurchaseActivity.closeButton = null;
            this.view7f0a0177.setOnClickListener(null);
            this.view7f0a0177 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
