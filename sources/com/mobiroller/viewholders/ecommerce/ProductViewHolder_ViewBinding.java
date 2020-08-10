package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerBadgeView;
import com.mobiroller.views.custom.MobirollerTextView;

public class ProductViewHolder_ViewBinding implements Unbinder {
    private ProductViewHolder target;

    public ProductViewHolder_ViewBinding(ProductViewHolder productViewHolder, View view) {
        this.target = productViewHolder;
        productViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.product_image, "field 'image'", ImageView.class);
        productViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_title, "field 'title'", TextView.class);
        productViewHolder.price = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_price, "field 'price'", TextView.class);
        productViewHolder.priceCampaign = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_price_campaign, "field 'priceCampaign'", TextView.class);
        productViewHolder.soldOutBadge = (MobirollerBadgeView) C0812Utils.findRequiredViewAsType(view, R.id.sold_out_badge, "field 'soldOutBadge'", MobirollerBadgeView.class);
        productViewHolder.saleBadge = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.sale_badge, "field 'saleBadge'", CardView.class);
        productViewHolder.freeShippingBadge = (MobirollerBadgeView) C0812Utils.findRequiredViewAsType(view, R.id.free_shipping_badge, "field 'freeShippingBadge'", MobirollerBadgeView.class);
        productViewHolder.saleRateTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.sale_rate_text_view, "field 'saleRateTextView'", MobirollerTextView.class);
    }

    public void unbind() {
        ProductViewHolder productViewHolder = this.target;
        if (productViewHolder != null) {
            this.target = null;
            productViewHolder.image = null;
            productViewHolder.title = null;
            productViewHolder.price = null;
            productViewHolder.priceCampaign = null;
            productViewHolder.soldOutBadge = null;
            productViewHolder.saleBadge = null;
            productViewHolder.freeShippingBadge = null;
            productViewHolder.saleRateTextView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
