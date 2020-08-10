package com.mobiroller.activities;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.HackyViewPager;
import com.mobiroller.views.PullBackLayout;

public class ImagePagerActivity_ViewBinding implements Unbinder {
    private ImagePagerActivity target;

    public ImagePagerActivity_ViewBinding(ImagePagerActivity imagePagerActivity) {
        this(imagePagerActivity, imagePagerActivity.getWindow().getDecorView());
    }

    public ImagePagerActivity_ViewBinding(ImagePagerActivity imagePagerActivity, View view) {
        this.target = imagePagerActivity;
        imagePagerActivity.pager = (HackyViewPager) C0812Utils.findRequiredViewAsType(view, R.id.pager, "field 'pager'", HackyViewPager.class);
        imagePagerActivity.puller = (PullBackLayout) C0812Utils.findRequiredViewAsType(view, R.id.puller, "field 'puller'", PullBackLayout.class);
        imagePagerActivity.toolbar = (Toolbar) C0812Utils.findRequiredViewAsType(view, R.id.toolbar_top, "field 'toolbar'", Toolbar.class);
    }

    public void unbind() {
        ImagePagerActivity imagePagerActivity = this.target;
        if (imagePagerActivity != null) {
            this.target = null;
            imagePagerActivity.pager = null;
            imagePagerActivity.puller = null;
            imagePagerActivity.toolbar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
