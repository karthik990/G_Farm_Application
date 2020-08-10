package com.mobiroller.activities.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.adapters.user.UserOrderAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.interfaces.ActivityComponent;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.Order;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class UserOrderActivity extends AveActivity {
    private UserOrderAdapter adapter;
    ECommerceRequestHelper eCommerceRequestHelper;
    @BindView(2131362329)
    ConstraintLayout emptyView;
    @BindView(2131362617)
    RecyclerView list;
    /* access modifiers changed from: private */
    public List<Order> orderListResponse;
    @BindView(2131362978)
    CardView previewLayout;
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    @Inject
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363178)
    MobirollerButton startShoppingButton;
    @BindView(2131363207)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @Inject
    ToolbarHelper toolbarHelper;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_user_order);
        ButterKnife.bind((Activity) this);
        if (DynamicConstants.MobiRoller_Stage) {
            this.previewLayout.setVisibility(0);
        }
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        new ToolbarHelper().setStatusBar(this);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                UserOrderActivity.this.onBackPressed();
            }
        });
        if (UtilManager.networkHelper().isConnected()) {
            this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
            getSupportActionBar().setTitle((CharSequence) getString(R.string.e_commerce_my_orders_title));
            this.eCommerceRequestHelper = new ECommerceRequestHelper();
            if (DynamicConstants.MobiRoller_Stage) {
                this.swipeRefreshLayout.setEnabled(false);
            }
            this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                public void onRefresh() {
                    UserOrderActivity.this.getUserOrders();
                }
            });
            getUserOrders();
            return;
        }
        DialogUtil.showNoConnectionError(this);
    }

    public AppCompatActivity injectActivity(ActivityComponent activityComponent) {
        activityComponent.inject(this);
        return this;
    }

    /* access modifiers changed from: private */
    public void getUserOrders() {
        if (!DynamicConstants.MobiRoller_Stage) {
            this.progressViewHelper.show();
            this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getOrderList(UserHelper.getUserId()), new ECommerceCallBack<List<Order>>() {
                public void done() {
                    UserOrderActivity.this.swipeRefreshLayout.setRefreshing(false);
                    if (!UserOrderActivity.this.isFinishing() && UserOrderActivity.this.progressViewHelper.isShowing()) {
                        UserOrderActivity.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(List<Order> list) {
                    if (list == null || list.size() == 0) {
                        UserOrderActivity.this.swipeRefreshLayout.setVisibility(8);
                        UserOrderActivity.this.emptyView.setVisibility(0);
                        return;
                    }
                    UserOrderActivity.this.orderListResponse = list;
                    UserOrderActivity.this.setupView();
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(UserOrderActivity.this);
                }

                public void onNetworkError(String str) {
                    ErrorUtils.showErrorToast(UserOrderActivity.this);
                    UserOrderActivity.this.swipeRefreshLayout.setRefreshing(false);
                    UserOrderActivity.this.swipeRefreshLayout.setVisibility(8);
                    UserOrderActivity.this.emptyView.setVisibility(0);
                    if (!UserOrderActivity.this.isFinishing() && UserOrderActivity.this.progressViewHelper.isShowing()) {
                        UserOrderActivity.this.progressViewHelper.dismiss();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setupView() {
        this.swipeRefreshLayout.setVisibility(0);
        this.emptyView.setVisibility(8);
        this.adapter = new UserOrderAdapter(this, this.orderListResponse);
        this.list.setAdapter(this.adapter);
        this.list.setLayoutManager(new LinearLayoutManager(this));
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.progressViewHelper.isShowing()) {
            this.progressViewHelper.dismiss();
        }
    }

    @OnClick({2131363178})
    public void onClickStartShopping() {
        if (Constants.E_COMMERCE_SCREEN_ID != null && !Constants.E_COMMERCE_SCREEN_ID.equalsIgnoreCase("")) {
            ActivityHandler.startActivity(this, Constants.E_COMMERCE_SCREEN_ID, "aveECommerceView", "", "", new ArrayList());
            finish();
        }
    }
}
