package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class aveShareViewFragment_ViewBinding implements Unbinder {
    private aveShareViewFragment target;
    private View view7f0a0533;

    public aveShareViewFragment_ViewBinding(final aveShareViewFragment aveshareviewfragment, View view) {
        this.target = aveshareviewfragment;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.share_image, "field 'shareImage' and method 'onClickShareImage'");
        aveshareviewfragment.shareImage = (ImageView) C0812Utils.castView(findRequiredView, R.id.share_image, "field 'shareImage'", ImageView.class);
        this.view7f0a0533 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveshareviewfragment.onClickShareImage();
            }
        });
    }

    public void unbind() {
        aveShareViewFragment aveshareviewfragment = this.target;
        if (aveshareviewfragment != null) {
            this.target = null;
            aveshareviewfragment.shareImage = null;
            this.view7f0a0533.setOnClickListener(null);
            this.view7f0a0533 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
