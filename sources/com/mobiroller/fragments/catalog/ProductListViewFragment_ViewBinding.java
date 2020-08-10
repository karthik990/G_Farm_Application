package com.mobiroller.fragments.catalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobiroller.mobi942763453128.R;

public class ProductListViewFragment_ViewBinding implements Unbinder {
    private ProductListViewFragment target;

    public ProductListViewFragment_ViewBinding(ProductListViewFragment productListViewFragment, View view) {
        this.target = productListViewFragment;
        productListViewFragment.searchView = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.search_view, "field 'searchView'", TextInputEditText.class);
        productListViewFragment.searchTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.search_text_input_layout, "field 'searchTextInputLayout'", TextInputLayout.class);
        productListViewFragment.mainImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.main_image, "field 'mainImage'", ImageView.class);
        productListViewFragment.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        productListViewFragment.titleDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title_description, "field 'titleDescription'", TextView.class);
        productListViewFragment.imageLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.image_layout, "field 'imageLayout'", ConstraintLayout.class);
        productListViewFragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", RecyclerView.class);
        productListViewFragment.emptyView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", CardView.class);
    }

    public void unbind() {
        ProductListViewFragment productListViewFragment = this.target;
        if (productListViewFragment != null) {
            this.target = null;
            productListViewFragment.searchView = null;
            productListViewFragment.searchTextInputLayout = null;
            productListViewFragment.mainImage = null;
            productListViewFragment.title = null;
            productListViewFragment.titleDescription = null;
            productListViewFragment.imageLayout = null;
            productListViewFragment.list = null;
            productListViewFragment.emptyView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
