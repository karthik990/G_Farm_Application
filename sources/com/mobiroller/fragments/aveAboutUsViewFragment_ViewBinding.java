package com.mobiroller.fragments;

import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;

public class aveAboutUsViewFragment_ViewBinding implements Unbinder {
    private aveAboutUsViewFragment target;
    private View view7f0a020d;
    private View view7f0a0250;
    private View view7f0a02a9;
    private View view7f0a0338;
    private View view7f0a05ff;
    private View view7f0a0641;

    public aveAboutUsViewFragment_ViewBinding(final aveAboutUsViewFragment aveaboutusviewfragment, View view) {
        this.target = aveaboutusviewfragment;
        aveaboutusviewfragment.headerTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.header_text_view, "field 'headerTextView'", TextView.class);
        aveaboutusviewfragment.aboutTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.about_text_view, "field 'aboutTextView'", TextView.class);
        aveaboutusviewfragment.descriptionTextView = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.description_text_view, "field 'descriptionTextView'", TextView.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.twitter_image, "field 'twitterImage' and method 'onClickTwitterImage'");
        aveaboutusviewfragment.twitterImage = (ImageView) C0812Utils.castView(findRequiredView, R.id.twitter_image, "field 'twitterImage'", ImageView.class);
        this.view7f0a05ff = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickTwitterImage();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.facebook_image, "field 'facebookImage' and method 'onClickFacebookImage'");
        aveaboutusviewfragment.facebookImage = (ImageView) C0812Utils.castView(findRequiredView2, R.id.facebook_image, "field 'facebookImage'", ImageView.class);
        this.view7f0a0250 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickFacebookImage();
            }
        });
        View findRequiredView3 = C0812Utils.findRequiredView(view, R.id.linkedin_image, "field 'linkedinImage' and method 'onClickLinkedinImage'");
        aveaboutusviewfragment.linkedinImage = (ImageView) C0812Utils.castView(findRequiredView3, R.id.linkedin_image, "field 'linkedinImage'", ImageView.class);
        this.view7f0a0338 = findRequiredView3;
        findRequiredView3.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickLinkedinImage();
            }
        });
        View findRequiredView4 = C0812Utils.findRequiredView(view, R.id.web_image, "field 'webImage' and method 'onClickWebImage'");
        aveaboutusviewfragment.webImage = (ImageView) C0812Utils.castView(findRequiredView4, R.id.web_image, "field 'webImage'", ImageView.class);
        this.view7f0a0641 = findRequiredView4;
        findRequiredView4.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickWebImage();
            }
        });
        View findRequiredView5 = C0812Utils.findRequiredView(view, R.id.google_image, "field 'googleImage' and method 'onClickGoogleImage'");
        aveaboutusviewfragment.googleImage = (ImageView) C0812Utils.castView(findRequiredView5, R.id.google_image, "field 'googleImage'", ImageView.class);
        this.view7f0a02a9 = findRequiredView5;
        findRequiredView5.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickGoogleImage();
            }
        });
        View findRequiredView6 = C0812Utils.findRequiredView(view, R.id.email_image, "field 'emailImage' and method 'onClickEmailImage'");
        aveaboutusviewfragment.emailImage = (ImageView) C0812Utils.castView(findRequiredView6, R.id.email_image, "field 'emailImage'", ImageView.class);
        this.view7f0a020d = findRequiredView6;
        findRequiredView6.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                aveaboutusviewfragment.onClickEmailImage();
            }
        });
        aveaboutusviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        aveaboutusviewfragment.overlayLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.overlay_layout, "field 'overlayLayout'", ConstraintLayout.class);
        aveaboutusviewfragment.gridLayout = (GridLayout) C0812Utils.findRequiredViewAsType(view, R.id.grid_layout, "field 'gridLayout'", GridLayout.class);
    }

    public void unbind() {
        aveAboutUsViewFragment aveaboutusviewfragment = this.target;
        if (aveaboutusviewfragment != null) {
            this.target = null;
            aveaboutusviewfragment.headerTextView = null;
            aveaboutusviewfragment.aboutTextView = null;
            aveaboutusviewfragment.descriptionTextView = null;
            aveaboutusviewfragment.twitterImage = null;
            aveaboutusviewfragment.facebookImage = null;
            aveaboutusviewfragment.linkedinImage = null;
            aveaboutusviewfragment.webImage = null;
            aveaboutusviewfragment.googleImage = null;
            aveaboutusviewfragment.emailImage = null;
            aveaboutusviewfragment.mainLayout = null;
            aveaboutusviewfragment.overlayLayout = null;
            aveaboutusviewfragment.gridLayout = null;
            this.view7f0a05ff.setOnClickListener(null);
            this.view7f0a05ff = null;
            this.view7f0a0250.setOnClickListener(null);
            this.view7f0a0250 = null;
            this.view7f0a0338.setOnClickListener(null);
            this.view7f0a0338 = null;
            this.view7f0a0641.setOnClickListener(null);
            this.view7f0a0641 = null;
            this.view7f0a02a9.setOnClickListener(null);
            this.view7f0a02a9 = null;
            this.view7f0a020d.setOnClickListener(null);
            this.view7f0a020d = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
