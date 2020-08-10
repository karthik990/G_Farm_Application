package com.mobiroller.fragments.ecommerce;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerToolbar;

public class ProductDescriptionBottomSheetFragment extends BottomSheetDialogFragment {
    public static String INTENT_PRODUCT_DESCRIPTION = "ProductDescription";
    public String description = "";
    private BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetCallback() {
        public void onSlide(View view, float f) {
        }

        public void onStateChanged(View view, int i) {
            if (i == 5) {
                ProductDescriptionBottomSheetFragment.this.dismiss();
            }
        }
    };
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    Unbinder unbinder;
    @BindView(2131363395)
    VideoEnabledWebView webView;

    public Dialog onCreateDialog(Bundle bundle) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(bundle);
        bottomSheetDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                FrameLayout frameLayout = (FrameLayout) ((BottomSheetDialog) dialogInterface).findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior.from(frameLayout).setState(3);
                BottomSheetBehavior.from(frameLayout).setSkipCollapsed(true);
                BottomSheetBehavior.from(frameLayout).setHideable(true);
            }
        });
        return bottomSheetDialog;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.activity_e_commerce_product_description, null);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        dialog.setContentView(inflate);
        Behavior behavior = ((LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if (behavior != null && (behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        if (getArguments() == null || !getArguments().containsKey(INTENT_PRODUCT_DESCRIPTION)) {
            dismiss();
        } else {
            this.description = getArguments().getString(INTENT_PRODUCT_DESCRIPTION);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.webView.getLayoutParams();
        layoutParams.height = ScreenHelper.getDeviceHeight(getActivity()) - this.toolbar.getHeight();
        this.webView.setLayoutParams(layoutParams);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
