package com.mobiroller.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.C0812Utils;
import butterknife.internal.DebouncingOnClickListener;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VerticalViewPager;

public class avePDFViewFragment_ViewBinding implements Unbinder {
    private avePDFViewFragment target;
    private View view7f0a03ec;
    private View view7f0a04a6;

    public avePDFViewFragment_ViewBinding(final avePDFViewFragment avepdfviewfragment, View view) {
        this.target = avepdfviewfragment;
        avepdfviewfragment.pageCount = (TextView) C0812Utils.findRequiredViewAsType(view, R.id.page_count, "field 'pageCount'", TextView.class);
        avepdfviewfragment.verticalViewPager = (VerticalViewPager) C0812Utils.findRequiredViewAsType(view, R.id.vertical_view_pager, "field 'verticalViewPager'", VerticalViewPager.class);
        View findRequiredView = C0812Utils.findRequiredView(view, R.id.next, "field 'next' and method 'onClickNext'");
        avepdfviewfragment.next = (ImageView) C0812Utils.castView(findRequiredView, R.id.next, "field 'next'", ImageView.class);
        this.view7f0a03ec = findRequiredView;
        findRequiredView.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avepdfviewfragment.onClickNext();
            }
        });
        View findRequiredView2 = C0812Utils.findRequiredView(view, R.id.previus, "field 'previus' and method 'onClickPrevius'");
        avepdfviewfragment.previus = (ImageView) C0812Utils.castView(findRequiredView2, R.id.previus, "field 'previus'", ImageView.class);
        this.view7f0a04a6 = findRequiredView2;
        findRequiredView2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View view) {
                avepdfviewfragment.onClickPrevius();
            }
        });
        avepdfviewfragment.bottomLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.bottom_layout, "field 'bottomLayout'", ConstraintLayout.class);
        avepdfviewfragment.mainLayout = (RelativeLayout) C0812Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'mainLayout'", RelativeLayout.class);
        avepdfviewfragment.innerLayout = (ConstraintLayout) C0812Utils.findRequiredViewAsType(view, R.id.inner_layout, "field 'innerLayout'", ConstraintLayout.class);
        avepdfviewfragment.progressBar = (ProgressBar) C0812Utils.findRequiredViewAsType(view, R.id.indeterminate_horizontal_progress, "field 'progressBar'", ProgressBar.class);
    }

    public void unbind() {
        avePDFViewFragment avepdfviewfragment = this.target;
        if (avepdfviewfragment != null) {
            this.target = null;
            avepdfviewfragment.pageCount = null;
            avepdfviewfragment.verticalViewPager = null;
            avepdfviewfragment.next = null;
            avepdfviewfragment.previus = null;
            avepdfviewfragment.bottomLayout = null;
            avepdfviewfragment.mainLayout = null;
            avepdfviewfragment.innerLayout = null;
            avepdfviewfragment.progressBar = null;
            this.view7f0a03ec.setOnClickListener(null);
            this.view7f0a03ec = null;
            this.view7f0a04a6.setOnClickListener(null);
            this.view7f0a04a6 = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
