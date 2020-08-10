package com.mobiroller.viewholders.ecommerce;

import android.os.Build.VERSION;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.OrderProduct;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.util.ECommerceUtil;

public class OrderProductViewHolder extends ViewHolder {
    @BindView(2131362252)
    TextView description;
    @BindView(2131362989)
    public ImageView image;
    @BindView(2131362983)
    TextView price;
    @BindView(2131362997)
    TextView title;

    public OrderProductViewHolder(View view) {
        super(view);
        ButterKnife.bind((Object) this, view);
    }

    public void bind(OrderProduct orderProduct) {
        ProductImage productImage = orderProduct.featuredImage;
        Integer valueOf = Integer.valueOf(R.drawable.no_image_e_commerce);
        if (productImage == null || orderProduct.featuredImage.f2182t == null) {
            Glide.with(this.itemView).load(valueOf).into(this.image);
        } else {
            ((RequestBuilder) Glide.with(this.itemView).load(orderProduct.featuredImage.f2182t).error(Glide.with(this.itemView).load(valueOf)).placeholder((int) R.drawable.no_image_e_commerce)).into(this.image);
        }
        if (VERSION.SDK_INT >= 21) {
            this.image.setTransitionName("featuredImage");
        }
        this.title.setText(UtilManager.localizationHelper().getLocalizedTitle(orderProduct.title));
        this.price.setText(String.format("%s %s", new Object[]{ECommerceUtil.getPriceString(orderProduct.price), ECommerceUtil.getCurrencySymbol(orderProduct.currency)}));
        String str = " ";
        if (orderProduct.campaignPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            TextView textView = this.price;
            StringBuilder sb = new StringBuilder();
            double d = orderProduct.price;
            double d2 = (double) orderProduct.quantity;
            Double.isNaN(d2);
            sb.append(ECommerceUtil.getPriceString(d * d2));
            sb.append(str);
            sb.append(ECommerceUtil.getCurrencySymbol(orderProduct.currency));
            textView.setText(sb.toString());
        } else {
            TextView textView2 = this.price;
            StringBuilder sb2 = new StringBuilder();
            double d3 = orderProduct.campaignPrice;
            double d4 = (double) orderProduct.quantity;
            Double.isNaN(d4);
            sb2.append(ECommerceUtil.getPriceString(d3 * d4));
            sb2.append(str);
            sb2.append(ECommerceUtil.getCurrencySymbol(orderProduct.currency));
            textView2.setText(sb2.toString());
        }
        this.description.setText(Html.fromHtml(this.itemView.getContext().getString(R.string.e_commerce_order_details_quantity, new Object[]{String.valueOf(orderProduct.quantity)})));
    }
}
