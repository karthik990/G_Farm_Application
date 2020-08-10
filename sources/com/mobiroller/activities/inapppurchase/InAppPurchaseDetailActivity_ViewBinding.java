package com.mobiroller.activities.inapppurchase;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class InAppPurchaseDetailActivity_ViewBinding implements Unbinder {
    private InAppPurchaseDetailActivity target;
    private View view7f0a00a8;
    private View view7f0a0134;
    private View view7f0a01d9;
    private View view7f0a0418;
    private View view7f0a0578;

    public InAppPurchaseDetailActivity_ViewBinding(InAppPurchaseDetailActivity inAppPurchaseDetailActivity) {
        this(inAppPurchaseDetailActivity, inAppPurchaseDetailActivity.getWindow().getDecorView());
    }

    public InAppPurchaseDetailActivity_ViewBinding(final InAppPurchaseDetailActivity inAppPurchaseDetailActivity, View view) {
        this.target = inAppPurchaseDetailActivity;
        inAppPurchaseDetailActivity.mainLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", LinearLayout.class);
        inAppPurchaseDetailActivity.viewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", ViewPager.class);
        inAppPurchaseDetailActivity.description = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.description, "field 'description'", TextView.class);
        inAppPurchaseDetailActivity.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.one_time_button, "field 'oneTimeButton' and method 'onClickOneTimeButton'");
        inAppPurchaseDetailActivity.oneTimeButton = (ImageView) C0812Utils.castView(findRequiredView, R.id.one_time_button, "field 'oneTimeButton'", ImageView.class);
        this.view7f0a0418 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseDetailActivity.onClickOneTimeButton();
            }
        });
        inAppPurchaseDetailActivity.lifeTimePriceText = (AppCompatTextView) C0812Utils.findRequiredViewAsType(view, R.id.life_time_price_text, "field 'lifeTimePriceText'", AppCompatTextView.class);
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.subscription_button, "field 'subscriptionButton' and method 'onClickSubscriptionButton'");
        inAppPurchaseDetailActivity.subscriptionButton = (ImageView) C0812Utils.castView(findRequiredView2, R.id.subscription_button, "field 'subscriptionButton'", ImageView.class);
        this.view7f0a0578 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseDetailActivity.onClickSubscriptionButton();
            }
        });
        inAppPurchaseDetailActivity.subscriptionPeriod = (AppCompatTextView) C0812Utils.findRequiredViewAsType(view, R.id.subscription_period, "field 'subscriptionPeriod'", AppCompatTextView.class);
        inAppPurchaseDetailActivity.subscriptionPriceText = (AppCompatTextView) C0812Utils.findRequiredViewAsType(view, R.id.subscription_price_text, "field 'subscriptionPriceText'", AppCompatTextView.class);
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.detail_action_button, "field 'detailActionButton' and method 'onClickDetailButton'");
        inAppPurchaseDetailActivity.detailActionButton = (Button) C0812Utils.castView(findRequiredView3, R.id.detail_action_button, "field 'detailActionButton'", Button.class);
        this.view7f0a01d9 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseDetailActivity.onClickDetailButton();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.back_button, "field 'backButton' and method 'onBackButtonClick'");
        inAppPurchaseDetailActivity.backButton = (ImageView) C0812Utils.castView(findRequiredView4, R.id.back_button, "field 'backButton'", ImageView.class);
        this.view7f0a00a8 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseDetailActivity.onBackButtonClick();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.buy_action_button, "field 'buyActionButton' and method 'onClickBuyButton'");
        inAppPurchaseDetailActivity.buyActionButton = (Button) C0812Utils.castView(findRequiredView5, R.id.buy_action_button, "field 'buyActionButton'", Button.class);
        this.view7f0a0134 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                inAppPurchaseDetailActivity.onClickBuyButton();
            }
        });
        inAppPurchaseDetailActivity.oneTimeLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.one_time_layout, "field 'oneTimeLayout'", RelativeLayout.class);
        inAppPurchaseDetailActivity.subscriptionLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.subscription_layout, "field 'subscriptionLayout'", RelativeLayout.class);
        inAppPurchaseDetailActivity.pagerIndicator = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.viewPagerCountDots, "field 'pagerIndicator'", LinearLayout.class);
    }

    public void unbind() {
        InAppPurchaseDetailActivity inAppPurchaseDetailActivity = this.target;
        if (inAppPurchaseDetailActivity != null) {
            this.target = null;
            inAppPurchaseDetailActivity.mainLayout = null;
            inAppPurchaseDetailActivity.viewPager = null;
            inAppPurchaseDetailActivity.description = null;
            inAppPurchaseDetailActivity.title = null;
            inAppPurchaseDetailActivity.oneTimeButton = null;
            inAppPurchaseDetailActivity.lifeTimePriceText = null;
            inAppPurchaseDetailActivity.subscriptionButton = null;
            inAppPurchaseDetailActivity.subscriptionPeriod = null;
            inAppPurchaseDetailActivity.subscriptionPriceText = null;
            inAppPurchaseDetailActivity.detailActionButton = null;
            inAppPurchaseDetailActivity.backButton = null;
            inAppPurchaseDetailActivity.buyActionButton = null;
            inAppPurchaseDetailActivity.oneTimeLayout = null;
            inAppPurchaseDetailActivity.subscriptionLayout = null;
            inAppPurchaseDetailActivity.pagerIndicator = null;
            this.view7f0a0418.setOnClickListener(null);
            this.view7f0a0418 = null;
            this.view7f0a0578.setOnClickListener(null);
            this.view7f0a0578 = null;
            this.view7f0a01d9.setOnClickListener(null);
            this.view7f0a01d9 = null;
            this.view7f0a00a8.setOnClickListener(null);
            this.view7f0a00a8 = null;
            this.view7f0a0134.setOnClickListener(null);
            this.view7f0a0134 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
