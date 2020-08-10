package com.mobiroller.activities.ecommerce;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.MaterialSearchView;
import com.mobiroller.views.custom.MobirollerEmptyView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductSearchActivity_ViewBinding implements Unbinder {
    private ProductSearchActivity target;

    public ProductSearchActivity_ViewBinding(ProductSearchActivity productSearchActivity) {
        this(productSearchActivity, productSearchActivity.getWindow().getDecorView());
    }

    public ProductSearchActivity_ViewBinding(ProductSearchActivity productSearchActivity, View view) {
        this.target = productSearchActivity;
        productSearchActivity.toolbar = (MobirollerToolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar, "field 'toolbar'", MobirollerToolbar.class);
        productSearchActivity.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", RecyclerView.class);
        productSearchActivity.emptyView = (MobirollerEmptyView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", MobirollerEmptyView.class);
        productSearchActivity.searchView = (MaterialSearchView) C0812Utils.findRequiredViewAsType(view, R.id.search_view, "field 'searchView'", MaterialSearchView.class);
    }

    public void unbind() {
        ProductSearchActivity productSearchActivity = this.target;
        if (productSearchActivity != null) {
            this.target = null;
            productSearchActivity.toolbar = null;
            productSearchActivity.list = null;
            productSearchActivity.emptyView = null;
            productSearchActivity.searchView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
