package com.mobiroller.activities.user;

import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerToolbar;

public class UserOrderActivity_ViewBinding implements Unbinder {
    private UserOrderActivity target;
    private View view7f0a056a;

    public UserOrderActivity_ViewBinding(UserOrderActivity userOrderActivity) {
        this(userOrderActivity, userOrderActivity.getWindow().getDecorView());
    }

    public UserOrderActivity_ViewBinding(final UserOrderActivity userOrderActivity, View view) {
        this.target = userOrderActivity;
        userOrderActivity.emptyView = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.empty_layout, "field 'emptyView'", ConstraintLayout.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.start_shopping_button, "field 'startShoppingButton' and method 'onClickStartShopping'");
        userOrderActivity.startShoppingButton = (MobirollerButton) C0812Utils.castView(findRequiredView, R.id.start_shopping_button, "field 'startShoppingButton'", MobirollerButton.class);
        this.view7f0a056a = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                userOrderActivity.onClickStartShopping();
            }
        });
        userOrderActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        userOrderActivity.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", RecyclerView.class);
        userOrderActivity.swipeRefreshLayout = (SwipeRefreshLayout) C0812Utils.findRequiredViewAsType(view, R.id.swipe_refresh_layout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
        userOrderActivity.previewLayout = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.preview_layout, "field 'previewLayout'", CardView.class);
    }

    public void unbind() {
        UserOrderActivity userOrderActivity = this.target;
        if (userOrderActivity != null) {
            this.target = null;
            userOrderActivity.emptyView = null;
            userOrderActivity.startShoppingButton = null;
            userOrderActivity.toolbar = null;
            userOrderActivity.list = null;
            userOrderActivity.swipeRefreshLayout = null;
            userOrderActivity.previewLayout = null;
            this.view7f0a056a.setOnClickListener(null);
            this.view7f0a056a = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
