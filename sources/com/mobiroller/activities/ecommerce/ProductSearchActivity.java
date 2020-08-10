package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.adapters.ecommerce.ProductListAdapter;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.util.ScreenUtil;
import com.mobiroller.views.EndlessRecyclerViewScrollListener;
import com.mobiroller.views.MaterialSearchView;
import com.mobiroller.views.MaterialSearchView.OnQueryTextListener;
import com.mobiroller.views.MaterialSearchView.SearchViewListener;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerToolbar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductSearchActivity extends ECommerceBaseActivity {
    private final int ITEM_PER_PAGE = 20;
    ProductListAdapter adapter;
    ECommerceRequestHelper ecommerceRequestHelper;
    @BindView(2131362333)
    MobirollerEmptyView emptyView;
    GridLayoutManager gridLayoutManager;
    @BindView(2131362617)
    RecyclerView list;
    /* access modifiers changed from: private */
    public String mKeyword;
    /* access modifiers changed from: private */
    public int mPage = 1;
    private MenuItem mSearchItem;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363110)
    MaterialSearchView searchView;
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    ToolbarHelper toolbarHelper;

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_ecommerce_search_list_view);
        ButterKnife.bind((Activity) this);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.toolbarHelper = new ToolbarHelper(this.sharedPrefHelper);
        setToolbar();
        new ToolbarHelper().setStatusBar(this);
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.ecommerceRequestHelper = new ECommerceRequestHelper();
        this.gridLayoutManager = new GridLayoutManager(this, 2);
        this.adapter = new ProductListAdapter(this, new ArrayList());
        this.list.setAdapter(this.adapter);
        this.list.setLayoutManager(this.gridLayoutManager);
    }

    /* access modifiers changed from: 0000 */
    public void getProducts() {
        if (UtilManager.networkHelper().isConnected()) {
            if (this.mPage == 1) {
                this.adapter.deleteAll();
            }
            if (!isFinishing()) {
                ProgressViewHelper progressViewHelper2 = this.progressViewHelper;
                if (progressViewHelper2 != null && !progressViewHelper2.isShowing()) {
                    this.progressViewHelper.show();
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("page", String.valueOf(this.mPage));
            hashMap.put("perPage", String.valueOf(20));
            hashMap.put("title", this.mKeyword);
            this.emptyView.setVisibility(8);
            this.list.setVisibility(8);
            this.ecommerceRequestHelper.enqueue(this.ecommerceRequestHelper.getAPIService().getProducts(hashMap), new ECommerceCallBack<List<ProductDetailModel>>() {
                public void onNetworkError(String str) {
                }

                public void done() {
                    if (!ProductSearchActivity.this.isFinishing() && ProductSearchActivity.this.progressViewHelper != null && ProductSearchActivity.this.progressViewHelper.isShowing()) {
                        ProductSearchActivity.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(List<ProductDetailModel> list) {
                    ProductSearchActivity.this.addToAdapter(list);
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(ProductSearchActivity.this);
                    ProductSearchActivity.this.addToAdapter(null);
                }
            });
            return;
        }
        DialogUtil.showNoConnectionInfo(this);
    }

    /* access modifiers changed from: 0000 */
    public void addToAdapter(List<ProductDetailModel> list2) {
        if (this.adapter.getItemCount() == 0 && (list2 == null || list2.size() == 0)) {
            this.emptyView.setVisibility(0);
            this.list.setVisibility(8);
            this.emptyView.setTitle(getString(R.string.e_commerce_product_search_no_result_title, new Object[]{this.mKeyword}));
            return;
        }
        this.emptyView.setVisibility(8);
        this.list.setVisibility(0);
        this.adapter.addItems(list2);
    }

    private void setToolbar() {
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductSearchActivity.this.onBackPressed();
            }
        });
        String str = "";
        this.toolbar.setTitle((CharSequence) str);
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductSearchActivity.this.finish();
                try {
                    View currentFocus = ProductSearchActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        ((InputMethodManager) ProductSearchActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle((CharSequence) str);
        this.toolbarHelper.setStatusBar(this);
        getSupportActionBar().setTitle((CharSequence) this.mKeyword);
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MobirollerToolbar mobirollerToolbar = this.toolbar;
        if (!(mobirollerToolbar == null || mobirollerToolbar.getMenu() == null)) {
            this.toolbar.getMenu().clear();
        }
        this.toolbar.inflateMenu(R.menu.e_commerce_search_option_menu);
        this.mSearchItem = menu.findItem(R.id.m_search);
        this.searchView.setMenuItem(this.mSearchItem);
        this.searchView.setHint(getString(R.string.action_search));
        this.searchView.setSubmitOnClick(true);
        this.searchView.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextChange(String str) {
                return false;
            }

            public boolean onQueryTextSubmit(String str) {
                if (str == null) {
                    return false;
                }
                if (str.trim().equalsIgnoreCase("")) {
                    return true;
                }
                ECommerceUtil.addSearchSuggestion(str);
                ProductSearchActivity.this.searchView.setSuggestions(ECommerceUtil.getSearchSuggestions());
                ProductSearchActivity.this.mKeyword = str;
                ProductSearchActivity.this.mPage = 1;
                ProductSearchActivity.this.toolbar.setTitle((CharSequence) ProductSearchActivity.this.mKeyword);
                ProductSearchActivity.this.getProducts();
                ProductSearchActivity.this.list.setOnScrollListener(new EndlessRecyclerViewScrollListener(ProductSearchActivity.this.gridLayoutManager) {
                    public void onLoadMore(int i, int i2) {
                        ProductSearchActivity.this.mPage = ProductSearchActivity.this.mPage + 1;
                        ProductSearchActivity.this.getProducts();
                    }
                });
                return false;
            }
        });
        this.searchView.setOnSearchViewListener(new SearchViewListener() {
            public void onSearchViewShown() {
                ProductSearchActivity.this.searchView.showSuggestions();
                ProductSearchActivity.this.emptyView.setVisibility(8);
                ScreenUtil.showKeyboard(ProductSearchActivity.this);
            }

            public void onSearchViewClosed() {
                if (ProductSearchActivity.this.mKeyword == null) {
                    ProductSearchActivity.this.finish();
                }
            }
        });
        this.searchView.showSearch();
        this.searchView.setSuggestions(ECommerceUtil.getSearchSuggestions());
        this.searchView.showSuggestions();
        return true;
    }
}
