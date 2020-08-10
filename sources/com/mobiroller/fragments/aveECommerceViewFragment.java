package com.mobiroller.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.appbar.AppBarLayout.LayoutParams;
import com.mobiroller.activities.base.AveActivity;
import com.mobiroller.activities.ecommerce.ProductSearchActivity;
import com.mobiroller.activities.ecommerce.ShoppingCartActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.adapters.ecommerce.ProductListAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class aveECommerceViewFragment extends BaseModuleFragment {
    private final int ITEM_PER_PAGE = 10;
    private ProductListAdapter adapter;
    private ECommerceRequestHelper ecommerceRequestHelper;
    @BindView(2131362333)
    MobirollerEmptyView emptyView;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    /* access modifiers changed from: private */
    public GridLayoutManager gridLayoutManager;
    private boolean isFromClassicMenu = false;
    private Activity mActivity;
    /* access modifiers changed from: private */
    public int mPage = 1;
    @BindView(2131362617)
    RecyclerView mRecyclerView;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363207)
    SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_ecommerce_list_view, viewGroup, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind((Object) this, inflate);
        this.ecommerceRequestHelper = new ECommerceRequestHelper();
        this.gridLayoutManager = new GridLayoutManager(getContext(), 2);
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        this.progressViewHelper.show();
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                aveECommerceViewFragment.this.mPage = 1;
                if (aveECommerceViewFragment.this.endlessRecyclerViewScrollListener != null) {
                    aveECommerceViewFragment.this.mRecyclerView.removeOnScrollListener(aveECommerceViewFragment.this.endlessRecyclerViewScrollListener);
                }
                aveECommerceViewFragment aveecommerceviewfragment = aveECommerceViewFragment.this;
                aveecommerceviewfragment.endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(aveecommerceviewfragment.gridLayoutManager) {
                    public void onLoadMore(int i, int i2) {
                        aveECommerceViewFragment.this.mPage = aveECommerceViewFragment.this.mPage + 1;
                        aveECommerceViewFragment.this.getProducts();
                    }
                };
                aveECommerceViewFragment.this.mRecyclerView.addOnScrollListener(aveECommerceViewFragment.this.endlessRecyclerViewScrollListener);
                aveECommerceViewFragment.this.getProducts();
            }
        });
        this.adapter = new ProductListAdapter(getActivity(), new ArrayList());
        this.mRecyclerView.setAdapter(this.adapter);
        this.mRecyclerView.setLayoutManager(this.gridLayoutManager);
        this.endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(this.gridLayoutManager) {
            public void onLoadMore(int i, int i2) {
                aveECommerceViewFragment.this.mPage = aveECommerceViewFragment.this.mPage + 1;
                aveECommerceViewFragment.this.getProducts();
            }
        };
        this.mRecyclerView.addOnScrollListener(this.endlessRecyclerViewScrollListener);
        if (Constants.E_COMMERCE_SCREEN_ID == null || Constants.E_COMMERCE_SCREEN_ID.equals("")) {
            Constants.E_COMMERCE_SCREEN_ID = this.screenId;
        }
        getProducts();
        return inflate;
    }

    public void onResume() {
        super.onResume();
        new ECommerceUtil().getBadgeCount();
        resetToolbar();
    }

    private void resetToolbar() {
        if (((AveActivity) getActivity()).getToolbar() != null) {
            ((LayoutParams) ((AveActivity) getActivity()).getToolbar().getLayoutParams()).setScrollFlags(5);
        }
    }

    /* access modifiers changed from: private */
    public void addToAdapter(List<ProductDetailModel> list) {
        if (this.mPage == 1) {
            this.adapter.deleteAll();
            this.adapter.notifyDataSetChanged();
        }
        if (getActivity() != null) {
            List checkProductListIsValid = checkProductListIsValid(list);
            if (this.adapter.getItemCount() == 0 && (checkProductListIsValid == null || checkProductListIsValid.size() == 0)) {
                this.emptyView.setVisibility(0);
                this.mRecyclerView.setVisibility(8);
                this.swipeRefreshLayout.setVisibility(8);
            } else {
                if (this.adapter.getItemCount() == 0) {
                    this.emptyView.setVisibility(8);
                    this.swipeRefreshLayout.setVisibility(0);
                    this.mRecyclerView.setVisibility(0);
                }
                this.adapter.addItems(checkProductListIsValid);
            }
        }
    }

    private List<ProductDetailModel> checkProductListIsValid(List<ProductDetailModel> list) {
        if (list == null) {
            return null;
        }
        int i = 0;
        while (i < list.size()) {
            if (list.get(i) == null || !((ProductDetailModel) list.get(i)).isValid()) {
                list.remove(i);
                i--;
            }
            i++;
        }
        return list;
    }

    /* access modifiers changed from: private */
    public void getProducts() {
        HashMap hashMap = new HashMap();
        hashMap.put("page", String.valueOf(this.mPage));
        hashMap.put("perPage", String.valueOf(10));
        this.ecommerceRequestHelper.enqueue(this.ecommerceRequestHelper.getAPIService().getProducts(hashMap), new ECommerceCallBack<List<ProductDetailModel>>() {
            public void done() {
                aveECommerceViewFragment.this.swipeRefreshLayout.setRefreshing(false);
                if (aveECommerceViewFragment.this.getActivity() != null && !aveECommerceViewFragment.this.getActivity().isFinishing() && aveECommerceViewFragment.this.progressViewHelper != null && aveECommerceViewFragment.this.progressViewHelper.isShowing()) {
                    aveECommerceViewFragment.this.progressViewHelper.dismiss();
                }
            }

            public void onSuccess(List<ProductDetailModel> list) {
                aveECommerceViewFragment.this.addToAdapter(list);
            }

            public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                ErrorUtils.showErrorToast(aveECommerceViewFragment.this.getActivity());
                aveECommerceViewFragment.this.addToAdapter(null);
            }

            public void onNetworkError(String str) {
                aveECommerceViewFragment.this.addToAdapter(null);
                ErrorUtils.showErrorToast(aveECommerceViewFragment.this.getActivity());
            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (Activity) context;
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        ((AveActivity) getActivity()).getToolbar().inflateMenu(R.menu.ecommerce_main_menu);
        View actionView = ((AveActivity) getActivity()).getToolbar().getMenu().findItem(R.id.action_shopping_cart).getActionView();
        ImageViewCompat.setImageTintList((ImageView) actionView.findViewById(R.id.shopping_bag_icon), ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK));
        setBadgeCount(ECommerceUtil.badgeCount);
        actionView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!UtilManager.sharedPrefHelper().getUserLoginStatus()) {
                    aveECommerceViewFragment aveecommerceviewfragment = aveECommerceViewFragment.this;
                    aveecommerceviewfragment.startActivity(new Intent(aveecommerceviewfragment.getActivity(), UserLoginActivity.class));
                    return;
                }
                aveECommerceViewFragment aveecommerceviewfragment2 = aveECommerceViewFragment.this;
                aveecommerceviewfragment2.startActivity(new Intent(aveecommerceviewfragment2.getActivity(), ShoppingCartActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_search) {
            startActivity(new Intent(getActivity(), ProductSearchActivity.class));
            return true;
        } else if (itemId != R.id.action_shopping_cart) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            if (!UtilManager.sharedPrefHelper().getUserLoginStatus()) {
                startActivity(new Intent(getActivity(), UserLoginActivity.class));
            } else {
                startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
            }
            return true;
        }
    }

    private void setBadgeCount(int i) {
        MobirollerToolbar toolbar = ((AveActivity) getActivity()).getToolbar();
        if (toolbar != null && toolbar.getMenu() != null && toolbar.getMenu().findItem(R.id.action_shopping_cart) != null && toolbar.getMenu().findItem(R.id.action_shopping_cart).getActionView() != null) {
            TextView textView = (TextView) toolbar.getMenu().findItem(R.id.action_shopping_cart).getActionView().findViewById(R.id.cart_badge);
            if (i > 0) {
                textView.setText(String.valueOf(i));
                textView.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
        }
    }

    @Subscribe
    public void onPostShoppingCartCountEvent(ShoppingCartCountEvent shoppingCartCountEvent) {
        setBadgeCount(shoppingCartCountEvent.shoppingCartItemCount);
    }

    public void onDestroyView() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }
}
