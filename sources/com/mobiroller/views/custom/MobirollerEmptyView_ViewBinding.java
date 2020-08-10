package com.mobiroller.views.custom;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import com.mobiroller.mobi942763453128.R;

public class MobirollerEmptyView_ViewBinding implements Unbinder {
    private MobirollerEmptyView target;

    public MobirollerEmptyView_ViewBinding(MobirollerEmptyView mobirollerEmptyView) {
        this(mobirollerEmptyView, mobirollerEmptyView);
    }

    public MobirollerEmptyView_ViewBinding(MobirollerEmptyView mobirollerEmptyView, View view) {
        this.target = mobirollerEmptyView;
        mobirollerEmptyView.backgroundImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_background_image_view, "field 'backgroundImageView'", ImageView.class);
        mobirollerEmptyView.imageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.empty_image_view, "field 'imageView'", ImageView.class);
        mobirollerEmptyView.titleTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_title_text_view, "field 'titleTextView'", MobirollerTextView.class);
        mobirollerEmptyView.descriptionTextView = (MobirollerTextView) C0812Utils.findRequiredViewAsType(view, R.id.empty_description_text_view, "field 'descriptionTextView'", MobirollerTextView.class);
        mobirollerEmptyView.noContentImageView = (ImageView) C0812Utils.findRequiredViewAsType(view, R.id.no_content_image, "field 'noContentImageView'", ImageView.class);
    }

    public void unbind() {
        MobirollerEmptyView mobirollerEmptyView = this.target;
        if (mobirollerEmptyView != null) {
            this.target = null;
            mobirollerEmptyView.backgroundImageView = null;
            mobirollerEmptyView.imageView = null;
            mobirollerEmptyView.titleTextView = null;
            mobirollerEmptyView.descriptionTextView = null;
            mobirollerEmptyView.noContentImageView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
