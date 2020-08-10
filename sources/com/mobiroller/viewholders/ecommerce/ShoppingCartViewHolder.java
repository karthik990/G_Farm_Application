package com.mobiroller.viewholders.ecommerce;

import android.os.Build.VERSION;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ecommerce.ShoppingCartListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.models.ecommerce.ShoppingCartItem;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerTextView;

public class ShoppingCartViewHolder extends ViewHolder {
    @BindView(2131362984)
    MobirollerTextView campaignPrice;
    @BindView(2131362117)
    CardView cargoLayout;
    @BindView(2131362119)
    MobirollerTextView cargoText;
    @BindView(2131362211)
    MobirollerTextView countTextView;
    @BindView(2131362542)
    public ImageView image;
    @BindView(2131362740)
    MobirollerClickableLayout minusLayout;
    @BindView(2131362974)
    MobirollerClickableLayout plusLayout;
    @BindView(2131362985)
    MobirollerTextView price;
    @BindView(2131363034)
    ImageView removeImageView;
    private ShoppingCartItem shoppingCartItem;
    private ShoppingCartListener shoppingCartListener;
    @BindView(2131363265)
    MobirollerTextView title;

    public ShoppingCartViewHolder(View view, ShoppingCartListener shoppingCartListener2) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.shoppingCartListener = shoppingCartListener2;
    }

    public void bind(ShoppingCartItem shoppingCartItem2) {
        this.shoppingCartItem = shoppingCartItem2;
        ProductImage productImage = shoppingCartItem2.product.featuredImage;
        Integer valueOf = Integer.valueOf(R.drawable.no_image_e_commerce);
        if (productImage == null || shoppingCartItem2.product.featuredImage.f2182t == null) {
            Glide.with(this.itemView).load(valueOf).into(this.image);
        } else {
            ((RequestBuilder) Glide.with(this.itemView).load(shoppingCartItem2.product.featuredImage.f2182t).placeholder((int) R.drawable.no_image_e_commerce)).error(Glide.with(this.itemView).load(valueOf)).into(this.image);
        }
        if (VERSION.SDK_INT >= 21) {
            this.image.setTransitionName("featuredImage");
        }
        this.title.setText(UtilManager.localizationHelper().getLocalizedTitle(shoppingCartItem2.product.title));
        String str = "%s %s";
        if (shoppingCartItem2.product.campaignPrice != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            MobirollerTextView mobirollerTextView = this.price;
            double d = shoppingCartItem2.product.campaignPrice;
            double d2 = (double) shoppingCartItem2.quantity;
            Double.isNaN(d2);
            mobirollerTextView.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(d * d2), ECommerceUtil.getCurrencySymbol(shoppingCartItem2.product.currency)}));
            MobirollerTextView mobirollerTextView2 = this.campaignPrice;
            double d3 = shoppingCartItem2.product.price;
            double d4 = (double) shoppingCartItem2.quantity;
            Double.isNaN(d4);
            mobirollerTextView2.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(d3 * d4), ECommerceUtil.getCurrencySymbol(shoppingCartItem2.product.currency)}));
            MobirollerTextView mobirollerTextView3 = this.campaignPrice;
            mobirollerTextView3.setPaintFlags(mobirollerTextView3.getPaintFlags() | 16);
            this.campaignPrice.setVisibility(0);
        } else {
            this.campaignPrice.setVisibility(8);
            MobirollerTextView mobirollerTextView4 = this.price;
            double d5 = shoppingCartItem2.product.price;
            double d6 = (double) shoppingCartItem2.quantity;
            Double.isNaN(d6);
            mobirollerTextView4.setText(String.format(str, new Object[]{ECommerceUtil.getPriceString(d5 * d6), ECommerceUtil.getCurrencySymbol(shoppingCartItem2.product.currency)}));
        }
        this.countTextView.setText(String.valueOf(shoppingCartItem2.quantity));
        if (shoppingCartItem2.quantity == 1) {
            this.minusLayout.setEnabled(false);
        } else {
            this.minusLayout.setEnabled(true);
        }
        if (shoppingCartItem2.product.useFixPrice || shoppingCartItem2.product.shippingPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.cargoLayout.setVisibility(8);
            return;
        }
        this.cargoLayout.setVisibility(0);
        MobirollerTextView mobirollerTextView5 = this.cargoText;
        mobirollerTextView5.setText(Html.fromHtml(mobirollerTextView5.getContext().getString(R.string.e_commerce_shopping_cart_cargo_warning, new Object[]{String.format(str, new Object[]{ECommerceUtil.getPriceString(shoppingCartItem2.product.shippingPrice), ECommerceUtil.getCurrencySymbol(shoppingCartItem2.product.currency)})})));
    }

    @OnClick({2131363034})
    public void onClickRemoveItem() {
        this.shoppingCartListener.onClickRemoveItem(this.shoppingCartItem.f2184id);
    }

    @OnClick({2131362740})
    public void onClickMinus() {
        if (this.shoppingCartItem.quantity != 1) {
            this.shoppingCartListener.onClickUpdateQuantity(this.shoppingCartItem.f2184id, this.shoppingCartItem.quantity - 1);
        }
    }

    @OnClick({2131362974})
    public void onClickPlusItem() {
        if (this.shoppingCartItem.quantity >= this.shoppingCartItem.product.maxQuantityPerOrder || this.shoppingCartItem.quantity >= this.shoppingCartItem.product.stock) {
            Toast.makeText(MobiRollerApplication.app, this.itemView.getContext().getString(R.string.e_commerce_product_detail_maximum_product_message, new Object[]{String.valueOf(this.shoppingCartItem.quantity)}), 0).show();
            return;
        }
        this.shoppingCartListener.onClickUpdateQuantity(this.shoppingCartItem.f2184id, this.shoppingCartItem.quantity + 1);
    }
}
