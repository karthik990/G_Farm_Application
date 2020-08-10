package com.mobiroller.viewholders.ecommerce;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ecommerce.ShoppingCartListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.models.ecommerce.ShoppingCartItem;
import com.mobiroller.models.ecommerce.ShoppingCartMessage;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerTextView;

public class ShoppingInvalidCartViewHolder extends ViewHolder {
    @BindView(2131361966)
    MobirollerTextView badgeTextView;
    @BindView(2131361967)
    CardView badgeView;
    @BindView(2131362211)
    MobirollerTextView countTextView;
    @BindView(2131362542)
    public ImageView image;
    @BindView(2131362740)
    MobirollerClickableLayout minusLayout;
    @BindView(2131362974)
    MobirollerClickableLayout plusLayout;
    @BindView(2131363009)
    ConstraintLayout quantityLayout;
    private ShoppingCartItem shoppingCartItem;
    private ShoppingCartListener shoppingCartListener;
    @BindView(2131363265)
    MobirollerTextView title;

    public ShoppingInvalidCartViewHolder(View view, ShoppingCartListener shoppingCartListener2) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.shoppingCartListener = shoppingCartListener2;
    }

    public void bind(ShoppingCartItem shoppingCartItem2) {
        String str;
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
        if (shoppingCartItem2.quantity == 1) {
            this.minusLayout.setEnabled(false);
        } else {
            this.minusLayout.setEnabled(true);
        }
        if (shoppingCartItem2.quantity >= shoppingCartItem2.product.maxQuantityPerOrder || shoppingCartItem2.quantity >= shoppingCartItem2.product.stock) {
            this.plusLayout.setEnabled(false);
        } else {
            this.plusLayout.setEnabled(true);
        }
        if (shoppingCartItem2.messages == null || shoppingCartItem2.messages.size() == 0) {
            this.badgeView.setVisibility(8);
            return;
        }
        if (ECommerceConstant.INVALID_CART_ITEM_STATUS.containsKey(((ShoppingCartMessage) shoppingCartItem2.messages.get(0)).key)) {
            this.badgeTextView.setTextColor(Color.parseColor("#fa2e2e"));
            this.badgeTextView.setAllCaps(true);
            int intValue = ((Integer) ECommerceConstant.INVALID_CART_ITEM_STATUS.get(((ShoppingCartMessage) shoppingCartItem2.messages.get(0)).key)).intValue();
            if (ECommerceConstant.SHOPPING_CART_NOT_ENOUGH_STOCK.equalsIgnoreCase(((ShoppingCartMessage) shoppingCartItem2.messages.get(0)).key)) {
                str = this.itemView.getContext().getString(intValue, new Object[]{Integer.valueOf(shoppingCartItem2.product.stock)});
                this.countTextView.setText(String.valueOf(shoppingCartItem2.product.stock));
                this.quantityLayout.setVisibility(0);
            } else {
                str = this.itemView.getContext().getString(intValue);
                this.quantityLayout.setVisibility(8);
            }
        } else {
            str = "";
        }
        if (str != null) {
            this.badgeView.setVisibility(0);
            this.badgeTextView.setText(str);
            return;
        }
        this.badgeView.setVisibility(8);
    }

    @OnClick({2131362740})
    public void onClickMinus() {
        if (this.shoppingCartItem.quantity != 1) {
            this.shoppingCartListener.onClickUpdateQuantity(this.shoppingCartItem.f2184id, this.shoppingCartItem.quantity - 1);
        }
    }

    @OnClick({2131362974})
    public void onClickPlusItem() {
        this.shoppingCartListener.onClickUpdateQuantity(this.shoppingCartItem.f2184id, this.shoppingCartItem.quantity + 1);
    }
}
