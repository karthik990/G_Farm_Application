package com.mobiroller.fragments.catalog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import butterknife.BindView;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.views.HackyViewPager;
import java.util.List;

public class ProductGalleryBottomSheetFragment extends BottomSheetDialogFragment {
    @BindView(2131362167)
    ImageView closeButton;
    /* access modifiers changed from: private */
    public List<String> images;
    private BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetCallback() {
        public void onSlide(View view, float f) {
        }

        public void onStateChanged(View view, int i) {
            if (i == 5) {
                ProductGalleryBottomSheetFragment.this.dismiss();
            }
        }
    };
    private int position;
    private SharedPrefHelper sharedPrefHelper;
    Unbinder unbinder;
    @BindView(2131363382)
    HackyViewPager viewPager;
    @BindView(2131363383)
    LinearLayout viewPagerCountDots;

    private class ImagePagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        ImagePagerAdapter() {
            this.inflater = (LayoutInflater) ProductGalleryBottomSheetFragment.this.getActivity().getSystemService("layout_inflater");
        }

        public int getCount() {
            return ProductGalleryBottomSheetFragment.this.images.size();
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = this.inflater.inflate(R.layout.layout_e_commerce_gallery_item, viewGroup, false);
            PhotoView photoView = (PhotoView) inflate.findViewById(R.id.image_view);
            if (ProductGalleryBottomSheetFragment.this.images.get(i) != null) {
                Glide.with(ProductGalleryBottomSheetFragment.this.getActivity()).load((String) ProductGalleryBottomSheetFragment.this.images.get(i)).into((ImageView) photoView);
            } else {
                Glide.with(ProductGalleryBottomSheetFragment.this.getActivity()).load(Integer.valueOf(R.drawable.no_image)).into((ImageView) photoView);
            }
            viewGroup.addView(inflate, -1, -1);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

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

    /* JADX WARNING: Removed duplicated region for block: B:17:0x00e0 A[LOOP:0: B:15:0x00da->B:17:0x00e0, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setupDialog(android.app.Dialog r12, int r13) {
        /*
            r11 = this;
            super.setupDialog(r12, r13)
            android.content.Context r13 = r11.getContext()
            r0 = 2131558575(0x7f0d00af, float:1.874247E38)
            r1 = 0
            android.view.View r13 = android.view.View.inflate(r13, r0, r1)
            butterknife.Unbinder r0 = butterknife.ButterKnife.bind(r11, r13)
            r11.unbinder = r0
            r12.setContentView(r13)
            android.view.ViewParent r12 = r13.getParent()
            android.view.View r12 = (android.view.View) r12
            android.view.ViewGroup$LayoutParams r12 = r12.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r12 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r12
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r12 = r12.getBehavior()
            if (r12 == 0) goto L_0x0035
            boolean r13 = r12 instanceof com.google.android.material.bottomsheet.BottomSheetBehavior
            if (r13 == 0) goto L_0x0035
            com.google.android.material.bottomsheet.BottomSheetBehavior r12 = (com.google.android.material.bottomsheet.BottomSheetBehavior) r12
            com.google.android.material.bottomsheet.BottomSheetBehavior$BottomSheetCallback r13 = r11.mBottomSheetBehaviorCallback
            r12.setBottomSheetCallback(r13)
        L_0x0035:
            com.mobiroller.helpers.SharedPrefHelper r12 = com.mobiroller.helpers.UtilManager.sharedPrefHelper()
            r11.sharedPrefHelper = r12
            android.os.Bundle r12 = r11.getArguments()
            r13 = 0
            if (r12 == 0) goto L_0x0079
            android.os.Bundle r12 = r11.getArguments()
            java.lang.String r0 = "productImageList"
            boolean r12 = r12.containsKey(r0)
            if (r12 == 0) goto L_0x0079
            android.os.Bundle r12 = r11.getArguments()
            java.util.ArrayList r12 = r12.getStringArrayList(r0)
            r11.images = r12
            java.util.List<java.lang.String> r12 = r11.images
            int r12 = r12.size()
            if (r12 != 0) goto L_0x006c
            java.util.List<java.lang.String> r12 = r11.images
            java.lang.String r0 = new java.lang.String
            java.lang.String r1 = ""
            r0.<init>(r1)
            r12.add(r0)
        L_0x006c:
            android.os.Bundle r12 = r11.getArguments()
            java.lang.String r0 = "productImageListPosition"
            int r12 = r12.getInt(r0, r13)
            r11.position = r12
            goto L_0x007c
        L_0x0079:
            r11.dismiss()
        L_0x007c:
            com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$ImagePagerAdapter r12 = new com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$ImagePagerAdapter
            r12.<init>()
            com.mobiroller.views.HackyViewPager r0 = r11.viewPager
            r0.setAdapter(r12)
            int r0 = r12.getCount()
            android.widget.ImageView[] r6 = new android.widget.ImageView[r0]
            androidx.fragment.app.FragmentActivity r0 = r11.getActivity()
            r1 = 2131230891(0x7f0800ab, float:1.8077848E38)
            android.graphics.drawable.Drawable r7 = androidx.core.content.ContextCompat.getDrawable(r0, r1)
            android.graphics.PorterDuffColorFilter r0 = new android.graphics.PorterDuffColorFilter
            com.mobiroller.helpers.SharedPrefHelper r1 = r11.sharedPrefHelper
            int r1 = r1.getActionBarColor()
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.MULTIPLY
            r0.<init>(r1, r2)
            r7.setColorFilter(r0)
            androidx.fragment.app.FragmentActivity r0 = r11.getActivity()
            r1 = 2131230887(0x7f0800a7, float:1.807784E38)
            android.graphics.drawable.Drawable r8 = androidx.core.content.ContextCompat.getDrawable(r0, r1)
            android.graphics.PorterDuffColorFilter r0 = new android.graphics.PorterDuffColorFilter
            com.mobiroller.helpers.SharedPrefHelper r1 = r11.sharedPrefHelper
            int r1 = r1.getActionBarColor()
            r2 = 1060320051(0x3f333333, float:0.7)
            int r1 = com.mobiroller.util.ColorUtil.getLighterColor(r1, r2)
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.MULTIPLY
            r0.<init>(r1, r2)
            r8.setColorFilter(r0)
            com.mobiroller.views.HackyViewPager r9 = r11.viewPager
            com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$3 r10 = new com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$3
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r6
            r4 = r8
            r5 = r7
            r0.<init>(r2, r3, r4, r5)
            r9.addOnPageChangeListener(r10)
            r0 = 0
        L_0x00da:
            int r1 = r12.getCount()
            if (r0 >= r1) goto L_0x0104
            android.widget.ImageView r1 = new android.widget.ImageView
            androidx.fragment.app.FragmentActivity r2 = r11.getActivity()
            r1.<init>(r2)
            r6[r0] = r1
            r1 = r6[r0]
            r1.setImageDrawable(r8)
            android.widget.LinearLayout$LayoutParams r1 = new android.widget.LinearLayout$LayoutParams
            r2 = -2
            r1.<init>(r2, r2)
            r2 = 4
            r1.setMargins(r2, r13, r2, r13)
            android.widget.LinearLayout r2 = r11.viewPagerCountDots
            r3 = r6[r0]
            r2.addView(r3, r1)
            int r0 = r0 + 1
            goto L_0x00da
        L_0x0104:
            r12 = r6[r13]
            r12.setImageDrawable(r7)
            com.mobiroller.views.HackyViewPager r12 = r11.viewPager
            int r13 = r11.position
            r12.setCurrentItem(r13)
            android.widget.ImageView r12 = r11.closeButton
            com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$4 r13 = new com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment$4
            r13.<init>()
            r12.setOnClickListener(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.fragments.catalog.ProductGalleryBottomSheetFragment.setupDialog(android.app.Dialog, int):void");
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
