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

public class CategoryViewFragment_ViewBinding implements Unbinder {
    private CategoryViewFragment target;

    public CategoryViewFragment_ViewBinding(CategoryViewFragment categoryViewFragment, View view) {
        this.target = categoryViewFragment;
        categoryViewFragment.searchView = (TextInputEditText) C0812Utils.findRequiredViewAsType(view, R.id.search_view, "field 'searchView'", TextInputEditText.class);
        categoryViewFragment.searchTextInputLayout = (TextInputLayout) C0812Utils.findRequiredViewAsType(view, R.id.search_text_input_layout, "field 'searchTextInputLayout'", TextInputLayout.class);
        categoryViewFragment.mainImage = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.main_image, "field 'mainImage'", ImageView.class);
        categoryViewFragment.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        categoryViewFragment.titleDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title_description, "field 'titleDescription'", TextView.class);
        categoryViewFragment.imageLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.image_layout, "field 'imageLayout'", ConstraintLayout.class);
        categoryViewFragment.list = (RecyclerView) C0812Utils.findRequiredViewAsType(view, R.id.list, "field 'list'", RecyclerView.class);
        categoryViewFragment.emptyView = (CardView) C0812Utils.findRequiredViewAsType(view, R.id.empty_view, "field 'emptyView'", CardView.class);
    }

    public void unbind() {
        CategoryViewFragment categoryViewFragment = this.target;
        if (categoryViewFragment != null) {
            this.target = null;
            categoryViewFragment.searchView = null;
            categoryViewFragment.searchTextInputLayout = null;
            categoryViewFragment.mainImage = null;
            categoryViewFragment.title = null;
            categoryViewFragment.titleDescription = null;
            categoryViewFragment.imageLayout = null;
            categoryViewFragment.list = null;
            categoryViewFragment.emptyView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
