package com.mobiroller.viewholders.ecommerce;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.views.custom.MobirollerBadgeView;
import com.mobiroller.views.custom.MobirollerTextView;

public class ProductViewHolder extends ViewHolder {
    @BindView(2131362462)
    MobirollerBadgeView freeShippingBadge;
    @BindView(2131362989)
    public ImageView image;
    @BindView(2131362993)
    TextView price;
    @BindView(2131362994)
    TextView priceCampaign;
    @BindView(2131363079)
    CardView saleBadge;
    @BindView(2131363080)
    MobirollerTextView saleRateTextView;
    @BindView(2131363161)
    MobirollerBadgeView soldOutBadge;
    @BindView(2131362997)
    TextView title;

    public ProductViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(ProductDetailModel productDetailModel) {
        ProductImage productImage = productDetailModel.featuredImage;
        Integer valueOf = Integer.valueOf(R.drawable.no_image_e_commerce);
        if (productImage == null || productDetailModel.featuredImage.f2182t == null) {
            Glide.with(this.itemView).load(valueOf).into(this.image);
        } else {
            ((RequestBuilder) Glide.with(this.itemView).load(productDetailModel.featuredImage.f2182t).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).error(Glide.with(this.itemView).load(valueOf)).placeholder((int) R.drawable.no_image_e_commerce)).into(this.image);
        }
        if (VERSION.SDK_INT >= 21) {
            this.image.setTransitionName("featuredImage");
        }
        this.title.setText(UtilManager.localizationHelper().getLocalizedTitle(productDetailModel.title));
        String str = "%s %s";
        if (productDetailModel.campaignPrice != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.price.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(productDetailModel.campaignPrice), ECommerceUtil.getCurrencySymbol(productDetailModel.currency)}));
            this.priceCampaign.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(productDetailModel.price), ECommerceUtil.getCurrencySymbol(productDetailModel.currency)}));
            TextView textView = this.priceCampaign;
            textView.setPaintFlags(textView.getPaintFlags() | 16);
            this.priceCampaign.setVisibility(0);
            this.saleBadge.setVisibility(0);
            this.saleRateTextView.setText(String.format("%%%s", new Object[]{Long.valueOf(Math.round(((productDetailModel.price - productDetailModel.campaignPrice) * 100.0d) / productDetailModel.price))}));
            this.saleRateTextView.setTextSize(2, 13.0f);
        } else {
            this.saleBadge.setVisibility(8);
            this.priceCampaign.setVisibility(8);
            this.price.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(productDetailModel.price), ECommerceUtil.getCurrencySymbol(productDetailModel.currency)}));
        }
        if (productDetailModel.shippingPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.freeShippingBadge.setVisibility(0);
        } else {
            this.freeShippingBadge.setVisibility(8);
        }
        if (productDetailModel.stock == 0) {
            this.soldOutBadge.setVisibility(0);
            this.soldOutBadge.requestLayout();
            return;
        }
        this.soldOutBadge.setVisibility(8);
    }
}
