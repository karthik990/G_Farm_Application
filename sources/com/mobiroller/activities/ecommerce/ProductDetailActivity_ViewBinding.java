package com.mobiroller.activities.ecommerce;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerBadgeView;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductDetailActivity_ViewBinding implements Unbinder {
    private ProductDetailActivity target;
    private View view7f0a0135;
    private View view7f0a01d0;
    private View view7f0a04c0;
    private View view7f0a04c3;
    private View view7f0a04e6;
    private View view7f0a053c;

    public ProductDetailActivity_ViewBinding(ProductDetailActivity productDetailActivity) {
        this(productDetailActivity, productDetailActivity.getWindow().getDecorView());
    }

    public ProductDetailActivity_ViewBinding(final ProductDetailActivity productDetailActivity, View view) {
        this.target = productDetailActivity;
        productDetailActivity.imageViewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.image_view_pager, "field 'imageViewPager'", ViewPager.class);
        productDetailActivity.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        productDetailActivity.price = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.price, "field 'price'", TextView.class);
        productDetailActivity.discountedPrice = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.discounted_price, "field 'discountedPrice'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.quantity_up, "field 'quantityUp' and method 'onClickQuantityUp'");
        productDetailActivity.quantityUp = (ImageView) C0812Utils.castView(findRequiredView, R.id.quantity_up, "field 'quantityUp'", ImageView.class);
        this.view7f0a04c3 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickQuantityUp();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.quantity_down, "field 'quantityDown' and method 'onClickQuantityDown'");
        productDetailActivity.quantityDown = (ImageView) C0812Utils.castView(findRequiredView2, R.id.quantity_down, "field 'quantityDown'", ImageView.class);
        this.view7f0a04c0 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickQuantityDown();
            }
        });
        productDetailActivity.quantity = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.quantity_amount_text, "field 'quantity'", TextView.class);
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.return_terms_layout, "field 'returnTermsLayout' and method 'onClickTermsLayout'");
        productDetailActivity.returnTermsLayout = (ConstraintLayout) C0812Utils.castView(findRequiredView3, R.id.return_terms_layout, "field 'returnTermsLayout'", ConstraintLayout.class);
        this.view7f0a04e6 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickTermsLayout();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.shipping_terms_layout, "field 'shippingTermsLayout' and method 'onClickShippingTermsLayout'");
        productDetailActivity.shippingTermsLayout = (ConstraintLayout) C0812Utils.castView(findRequiredView4, R.id.shipping_terms_layout, "field 'shippingTermsLayout'", ConstraintLayout.class);
        this.view7f0a053c = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickShippingTermsLayout();
            }
        });
        productDetailActivity.quantityLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.quantity_layout, "field 'quantityLayout'", ConstraintLayout.class);
        productDetailActivity.descriptionLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.description_layout, "field 'descriptionLayout'", ConstraintLayout.class);
        productDetailActivity.badgeLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.info_layout, "field 'badgeLayout'", ConstraintLayout.class);
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.buy_button, "field 'buyButton' and method 'onClickBuyButton'");
        productDetailActivity.buyButton = (MobirollerButton) C0812Utils.castView(findRequiredView5, R.id.buy_button, "field 'buyButton'", MobirollerButton.class);
        this.view7f0a0135 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickBuyButton();
            }
        });
        productDetailActivity.viewPagerCountDots = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.view_pager_count_dots, "field 'viewPagerCountDots'", LinearLayout.class);
        productDetailActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        productDetailActivity.gradientImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.gradient_image_view, "field 'gradientImageView'", ImageView.class);
        productDetailActivity.imageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image_view, "field 'imageView'", ImageView.class);
        productDetailActivity.soldOutBadge = (MobirollerBadgeView) C0812Utils.findRequiredViewAsType(view, R.id.sold_out_badge, "field 'soldOutBadge'", MobirollerBadgeView.class);
        productDetailActivity.freeShippingBadge = (MobirollerBadgeView) C0812Utils.findRequiredViewAsType(view, R.id.free_shipping_badge, "field 'freeShippingBadge'", MobirollerBadgeView.class);
        productDetailActivity.saleCardView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.sale_badge, "field 'saleCardView'", CardView.class);
        productDetailActivity.saleRateTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.sale_rate_text_view, "field 'saleRateTextView'", MobirollerTextView.class);
        productDetailActivity.cargoCardView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_layout, "field 'cargoCardView'", CardView.class);
        productDetailActivity.cargoTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_text, "field 'cargoTextView'", MobirollerTextView.class);
        productDetailActivity.webViewLayout = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.constraintLayout, "field 'webViewLayout'", LinearLayout.class);
        productDetailActivity.toolbarWebView = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_webview, "field 'toolbarWebView'", MobirollerToolbar.class);
        productDetailActivity.webView = (VideoEnabledWebView) C0812Utils.findRequiredViewAsType(view, R.id.web_view, "field 'webView'", VideoEnabledWebView.class);
        View findRequiredView6 = C0812Utils.findRequiredView(view, R.id.description_text, "method 'onClickDescriptionLayout'");
        this.view7f0a01d0 = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                productDetailActivity.onClickDescriptionLayout();
            }
        });
    }

    public void unbind() {
        ProductDetailActivity productDetailActivity = this.target;
        if (productDetailActivity != null) {
            this.target = null;
            productDetailActivity.imageViewPager = null;
            productDetailActivity.title = null;
            productDetailActivity.price = null;
            productDetailActivity.discountedPrice = null;
            productDetailActivity.quantityUp = null;
            productDetailActivity.quantityDown = null;
            productDetailActivity.quantity = null;
            productDetailActivity.returnTermsLayout = null;
            productDetailActivity.shippingTermsLayout = null;
            productDetailActivity.quantityLayout = null;
            productDetailActivity.descriptionLayout = null;
            productDetailActivity.badgeLayout = null;
            productDetailActivity.buyButton = null;
            productDetailActivity.viewPagerCountDots = null;
            productDetailActivity.toolbar = null;
            productDetailActivity.gradientImageView = null;
            productDetailActivity.imageView = null;
            productDetailActivity.soldOutBadge = null;
            productDetailActivity.freeShippingBadge = null;
            productDetailActivity.saleCardView = null;
            productDetailActivity.saleRateTextView = null;
            productDetailActivity.cargoCardView = null;
            productDetailActivity.cargoTextView = null;
            productDetailActivity.webViewLayout = null;
            productDetailActivity.toolbarWebView = null;
            productDetailActivity.webView = null;
            this.view7f0a04c3.setOnClickListener(null);
            this.view7f0a04c3 = null;
            this.view7f0a04c0.setOnClickListener(null);
            this.view7f0a04c0 = null;
            this.view7f0a04e6.setOnClickListener(null);
            this.view7f0a04e6 = null;
            this.view7f0a053c.setOnClickListener(null);
            this.view7f0a053c = null;
            this.view7f0a0135.setOnClickListener(null);
            this.view7f0a0135 = null;
            this.view7f0a01d0.setOnClickListener(null);
            this.view7f0a01d0 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
