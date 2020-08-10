package com.mobiroller.viewholders.catalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class CatalogCategoryViewHolder_ViewBinding implements Unbinder {
    private CatalogCategoryViewHolder target;

    public CatalogCategoryViewHolder_ViewBinding(CatalogCategoryViewHolder catalogCategoryViewHolder, View view) {
        this.target = catalogCategoryViewHolder;
        catalogCategoryViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
        catalogCategoryViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        catalogCategoryViewHolder.titleDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title_description, "field 'titleDescription'", TextView.class);
    }

    public void unbind() {
        CatalogCategoryViewHolder catalogCategoryViewHolder = this.target;
        if (catalogCategoryViewHolder != null) {
            this.target = null;
            catalogCategoryViewHolder.image = null;
            catalogCategoryViewHolder.title = null;
            catalogCategoryViewHolder.titleDescription = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
