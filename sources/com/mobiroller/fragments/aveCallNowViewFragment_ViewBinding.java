package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class aveCallNowViewFragment_ViewBinding implements Unbinder {
    private aveCallNowViewFragment target;
    private View view7f0a0138;

    public aveCallNowViewFragment_ViewBinding(final aveCallNowViewFragment avecallnowviewfragment, View view) {
        this.target = avecallnowviewfragment;
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.call_now_image, "field 'callNowImage' and method 'onClickCallImage'");
        avecallnowviewfragment.callNowImage = (ImageView) C0812Utils.castView(findRequiredView, R.id.call_now_image, "field 'callNowImage'", ImageView.class);
        this.view7f0a0138 = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avecallnowviewfragment.onClickCallImage();
            }
        });
    }

    public void unbind() {
        aveCallNowViewFragment avecallnowviewfragment = this.target;
        if (avecallnowviewfragment != null) {
            this.target = null;
            avecallnowviewfragment.callNowImage = null;
            this.view7f0a0138.setOnClickListener(null);
            this.view7f0a0138 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
