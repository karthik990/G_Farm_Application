package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerTextView;

public class ShoppingCartViewHolder_ViewBinding implements Unbinder {
    private ShoppingCartViewHolder target;
    private View view7f0a03b4;
    private View view7f0a049e;
    private View view7f0a04da;

    public ShoppingCartViewHolder_ViewBinding(final ShoppingCartViewHolder shoppingCartViewHolder, View view) {
        this.target = shoppingCartViewHolder;
        shoppingCartViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image_view, "field 'image'", ImageView.class);
        shoppingCartViewHolder.title = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.title_text_view, "field 'title'", MobirollerTextView.class);
        shoppingCartViewHolder.price = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.price_text_view, "field 'price'", MobirollerTextView.class);
        shoppingCartViewHolder.campaignPrice = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.price_sale_text_view, "field 'campaignPrice'", MobirollerTextView.class);
        shoppingCartViewHolder.countTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.count_text_view, "field 'countTextView'", MobirollerTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.remove_image_view, "field 'removeImageView' and method 'onClickRemoveItem'");
        shoppingCartViewHolder.removeImageView = (ImageView) C0812Utils.castView(findRequiredView, R.id.remove_image_view, "field 'removeImageView'", ImageView.class);
        this.view7f0a04da = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartViewHolder.onClickRemoveItem();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.minus_text_view, "field 'minusLayout' and method 'onClickMinus'");
        shoppingCartViewHolder.minusLayout = (MobirollerClickableLayout) C0812Utils.castView(findRequiredView2, R.id.minus_text_view, "field 'minusLayout'", MobirollerClickableLayout.class);
        this.view7f0a03b4 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartViewHolder.onClickMinus();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.plus_text_view, "field 'plusLayout' and method 'onClickPlusItem'");
        shoppingCartViewHolder.plusLayout = (MobirollerClickableLayout) C0812Utils.castView(findRequiredView3, R.id.plus_text_view, "field 'plusLayout'", MobirollerClickableLayout.class);
        this.view7f0a049e = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingCartViewHolder.onClickPlusItem();
            }
        });
        shoppingCartViewHolder.cargoLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_layout, "field 'cargoLayout'", CardView.class);
        shoppingCartViewHolder.cargoText = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.cargo_text, "field 'cargoText'", MobirollerTextView.class);
    }

    public void unbind() {
        ShoppingCartViewHolder shoppingCartViewHolder = this.target;
        if (shoppingCartViewHolder != null) {
            this.target = null;
            shoppingCartViewHolder.image = null;
            shoppingCartViewHolder.title = null;
            shoppingCartViewHolder.price = null;
            shoppingCartViewHolder.campaignPrice = null;
            shoppingCartViewHolder.countTextView = null;
            shoppingCartViewHolder.removeImageView = null;
            shoppingCartViewHolder.minusLayout = null;
            shoppingCartViewHolder.plusLayout = null;
            shoppingCartViewHolder.cargoLayout = null;
            shoppingCartViewHolder.cargoText = null;
            this.view7f0a04da.setOnClickListener(null);
            this.view7f0a04da = null;
            this.view7f0a03b4.setOnClickListener(null);
            this.view7f0a03b4 = null;
            this.view7f0a049e.setOnClickListener(null);
            this.view7f0a049e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
