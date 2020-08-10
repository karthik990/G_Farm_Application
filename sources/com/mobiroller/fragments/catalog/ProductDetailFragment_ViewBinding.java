package com.mobiroller.fragments.catalog;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class ProductDetailFragment_ViewBinding implements Unbinder {
    private ProductDetailFragment target;

    public ProductDetailFragment_ViewBinding(ProductDetailFragment productDetailFragment, View view) {
        this.target = productDetailFragment;
        productDetailFragment.viewPager = (ViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", ViewPager.class);
        productDetailFragment.viewPagerCountDots = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.view_pager_count_dots, "field 'viewPagerCountDots'", LinearLayout.class);
        productDetailFragment.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        productDetailFragment.productPriceText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_price_text, "field 'productPriceText'", TextView.class);
        productDetailFragment.productPrice = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_price, "field 'productPrice'", TextView.class);
        productDetailFragment.productPriceCurrency = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_price_currency, "field 'productPriceCurrency'", TextView.class);
        productDetailFragment.buyText = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.buy_text, "field 'buyText'", TextView.class);
        productDetailFragment.buyLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.buy_layout, "field 'buyLayout'", CardView.class);
        productDetailFragment.topLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.top_layout, "field 'topLayout'", CardView.class);
        productDetailFragment.productDescription = (WebView) C0812Utils.findRequiredViewAsType(view, R.id.product_description, "field 'productDescription'", WebView.class);
    }

    public void unbind() {
        ProductDetailFragment productDetailFragment = this.target;
        if (productDetailFragment != null) {
            this.target = null;
            productDetailFragment.viewPager = null;
            productDetailFragment.viewPagerCountDots = null;
            productDetailFragment.title = null;
            productDetailFragment.productPriceText = null;
            productDetailFragment.productPrice = null;
            productDetailFragment.productPriceCurrency = null;
            productDetailFragment.buyText = null;
            productDetailFragment.buyLayout = null;
            productDetailFragment.topLayout = null;
            productDetailFragment.productDescription = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
