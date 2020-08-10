package com.mobiroller.viewholders.ecommerce;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class OrderProductViewHolder_ViewBinding implements Unbinder {
    private OrderProductViewHolder target;

    public OrderProductViewHolder_ViewBinding(OrderProductViewHolder orderProductViewHolder, View view) {
        this.target = orderProductViewHolder;
        orderProductViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.product_image, "field 'image'", ImageView.class);
        orderProductViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.product_title, "field 'title'", TextView.class);
        orderProductViewHolder.price = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.price, "field 'price'", TextView.class);
        orderProductViewHolder.description = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.description, "field 'description'", TextView.class);
    }

    public void unbind() {
        OrderProductViewHolder orderProductViewHolder = this.target;
        if (orderProductViewHolder != null) {
            this.target = null;
            orderProductViewHolder.image = null;
            orderProductViewHolder.title = null;
            orderProductViewHolder.price = null;
            orderProductViewHolder.description = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
