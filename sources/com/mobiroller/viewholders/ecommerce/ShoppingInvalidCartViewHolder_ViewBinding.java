package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerClickableLayout;
import com.mobiroller.views.custom.MobirollerTextView;

public class ShoppingInvalidCartViewHolder_ViewBinding implements Unbinder {
    private ShoppingInvalidCartViewHolder target;
    private View view7f0a03b4;
    private View view7f0a049e;

    public ShoppingInvalidCartViewHolder_ViewBinding(final ShoppingInvalidCartViewHolder shoppingInvalidCartViewHolder, View view) {
        this.target = shoppingInvalidCartViewHolder;
        shoppingInvalidCartViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image_view, "field 'image'", ImageView.class);
        shoppingInvalidCartViewHolder.title = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.title_text_view, "field 'title'", MobirollerTextView.class);
        shoppingInvalidCartViewHolder.badgeTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.badge_text, "field 'badgeTextView'", MobirollerTextView.class);
        shoppingInvalidCartViewHolder.quantityLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.quantity_layout, "field 'quantityLayout'", ConstraintLayout.class);
        shoppingInvalidCartViewHolder.countTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.count_text_view, "field 'countTextView'", MobirollerTextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.minus_text_view, "field 'minusLayout' and method 'onClickMinus'");
        shoppingInvalidCartViewHolder.minusLayout = (MobirollerClickableLayout) C0812Utils.castView(findRequiredView, R.id.minus_text_view, "field 'minusLayout'", MobirollerClickableLayout.class);
        this.view7f0a03b4 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingInvalidCartViewHolder.onClickMinus();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.plus_text_view, "field 'plusLayout' and method 'onClickPlusItem'");
        shoppingInvalidCartViewHolder.plusLayout = (MobirollerClickableLayout) C0812Utils.castView(findRequiredView2, R.id.plus_text_view, "field 'plusLayout'", MobirollerClickableLayout.class);
        this.view7f0a049e = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                shoppingInvalidCartViewHolder.onClickPlusItem();
            }
        });
        shoppingInvalidCartViewHolder.badgeView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.badge_view, "field 'badgeView'", CardView.class);
    }

    public void unbind() {
        ShoppingInvalidCartViewHolder shoppingInvalidCartViewHolder = this.target;
        if (shoppingInvalidCartViewHolder != null) {
            this.target = null;
            shoppingInvalidCartViewHolder.image = null;
            shoppingInvalidCartViewHolder.title = null;
            shoppingInvalidCartViewHolder.badgeTextView = null;
            shoppingInvalidCartViewHolder.quantityLayout = null;
            shoppingInvalidCartViewHolder.countTextView = null;
            shoppingInvalidCartViewHolder.minusLayout = null;
            shoppingInvalidCartViewHolder.plusLayout = null;
            shoppingInvalidCartViewHolder.badgeView = null;
            this.view7f0a03b4.setOnClickListener(null);
            this.view7f0a03b4 = null;
            this.view7f0a049e.setOnClickListener(null);
            this.view7f0a049e = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
