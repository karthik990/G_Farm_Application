package com.mobiroller.fragments.ecommerce;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.HackyViewPager;
import com.mobiroller.views.custom.MobirollerTextView;

public class ProductGalleryBottomSheetFragment_ViewBinding implements Unbinder {
    private ProductGalleryBottomSheetFragment target;

    public ProductGalleryBottomSheetFragment_ViewBinding(ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment, View view) {
        this.target = productGalleryBottomSheetFragment;
        productGalleryBottomSheetFragment.viewPager = (HackyViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", HackyViewPager.class);
        productGalleryBottomSheetFragment.countTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.count_text_view, "field 'countTextView'", MobirollerTextView.class);
        productGalleryBottomSheetFragment.closeButton = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.close_button, "field 'closeButton'", ImageView.class);
    }

    public void unbind() {
        ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment = this.target;
        if (productGalleryBottomSheetFragment != null) {
            this.target = null;
            productGalleryBottomSheetFragment.viewPager = null;
            productGalleryBottomSheetFragment.countTextView = null;
            productGalleryBottomSheetFragment.closeButton = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
