package com.mobiroller.fragments.catalog;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.HackyViewPager;

public class ProductGalleryBottomSheetFragment_ViewBinding implements Unbinder {
    private ProductGalleryBottomSheetFragment target;

    public ProductGalleryBottomSheetFragment_ViewBinding(ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment, View view) {
        this.target = productGalleryBottomSheetFragment;
        productGalleryBottomSheetFragment.viewPager = (HackyViewPager) C0812Utils.findRequiredViewAsType(view, R.id.view_pager, "field 'viewPager'", HackyViewPager.class);
        productGalleryBottomSheetFragment.viewPagerCountDots = (LinearLayout) C0812Utils.findRequiredViewAsType(view, R.id.view_pager_count_dots, "field 'viewPagerCountDots'", LinearLayout.class);
        productGalleryBottomSheetFragment.closeButton = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.close_button, "field 'closeButton'", ImageView.class);
    }

    public void unbind() {
        ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment = this.target;
        if (productGalleryBottomSheetFragment != null) {
            this.target = null;
            productGalleryBottomSheetFragment.viewPager = null;
            productGalleryBottomSheetFragment.viewPagerCountDots = null;
            productGalleryBottomSheetFragment.closeButton = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
