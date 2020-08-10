package com.mobiroller.viewholders.catalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class CatalogProductViewHolder_ViewBinding implements Unbinder {
    private CatalogProductViewHolder target;

    public CatalogProductViewHolder_ViewBinding(CatalogProductViewHolder catalogProductViewHolder, View view) {
        this.target = catalogProductViewHolder;
        catalogProductViewHolder.image = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.image, "field 'image'", ImageView.class);
        catalogProductViewHolder.title = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title, "field 'title'", TextView.class);
        catalogProductViewHolder.titleDescription = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.title_description, "field 'titleDescription'", TextView.class);
        catalogProductViewHolder.price = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.currency, "field 'price'", TextView.class);
    }

    public void unbind() {
        CatalogProductViewHolder catalogProductViewHolder = this.target;
        if (catalogProductViewHolder != null) {
            this.target = null;
            catalogProductViewHolder.image = null;
            catalogProductViewHolder.title = null;
            catalogProductViewHolder.titleDescription = null;
            catalogProductViewHolder.price = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
