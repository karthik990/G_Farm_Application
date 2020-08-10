package com.mobiroller.viewholders.user;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build.VERSION;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.activities.ecommerce.OrderDetailsActivity;
import com.mobiroller.models.ecommerce.Order;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.views.custom.MobirollerTextView;
import java.text.ParseException;
import java.util.TimeZone;

public class OrderViewHolder extends ViewHolder {
    private AppCompatActivity context;
    private Order order;
    @BindView(2131362849)
    MobirollerTextView orderDate;
    @BindView(2131362851)
    MobirollerTextView orderNumber;
    @BindView(2131362853)
    MobirollerTextView orderPaid;
    @BindView(2131363182)
    ConstraintLayout orderStatusBackground;
    @BindView(2131363184)
    MobirollerTextView orderStatusTextView;
    @BindView(2131362857)
    MobirollerTextView orderTime;

    public OrderViewHolder(View view, AppCompatActivity appCompatActivity) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.context = appCompatActivity;
    }

    public void bind(Order order2) {
        String str = "UTC";
        this.order = order2;
        this.orderNumber.setText(String.valueOf(order2.orderCode));
        try {
            ECommerceUtil.dateFormat.setTimeZone(TimeZone.getTimeZone(str));
            this.orderDate.setText(ECommerceUtil.dateFormatOrderDate.format(ECommerceUtil.dateFormat.parse(order2.createdDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            ECommerceUtil.dateFormat.setTimeZone(TimeZone.getTimeZone(str));
            this.orderTime.setText(ECommerceUtil.dateFormatOrderTime.format(ECommerceUtil.dateFormat.parse(order2.createdDate)));
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
        this.orderPaid.setText(String.format("%s %s", new Object[]{ECommerceUtil.getPriceString(order2.totalPrice), ECommerceUtil.getCurrency(order2.currency)}));
        this.orderStatusTextView.setText(ECommerceUtil.getOrderStatus(order2.currentStatus, this.context));
        if (VERSION.SDK_INT >= 21) {
            this.orderStatusBackground.setBackgroundTintList(ColorStateList.valueOf(ECommerceUtil.getOrderStatusColor(order2.currentStatus, this.context)));
        }
    }

    @OnClick({2131362649})
    public void onClickOrderDetail() {
        OrderDetailsActivity.startActivity((Context) this.context, this.order.f2177id);
    }
}
