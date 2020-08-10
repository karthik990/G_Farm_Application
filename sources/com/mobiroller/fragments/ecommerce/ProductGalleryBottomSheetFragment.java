package com.mobiroller.fragments.ecommerce;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.views.HackyViewPager;
import com.mobiroller.views.custom.MobirollerTextView;
import java.util.List;

public class ProductGalleryBottomSheetFragment extends BottomSheetDialogFragment {
    @BindView(2131362167)
    ImageView closeButton;
    @BindView(2131362211)
    MobirollerTextView countTextView;
    /* access modifiers changed from: private */
    public List<ProductImage> images;
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
    /* access modifiers changed from: private */
    public ProgressViewHelper progressViewHelper;
    Unbinder unbinder;
    @BindView(2131363382)
    HackyViewPager viewPager;

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
            ProductGalleryBottomSheetFragment.this.progressViewHelper.show();
            if (((ProductImage) ProductGalleryBottomSheetFragment.this.images.get(i)).f2181n != null) {
                ((RequestBuilder) Glide.with(ProductGalleryBottomSheetFragment.this.getActivity()).load(((ProductImage) ProductGalleryBottomSheetFragment.this.images.get(i)).f2181n).placeholder((int) R.drawable.no_image_e_commerce)).thumbnail(Glide.with(ProductGalleryBottomSheetFragment.this.getActivity()).load(((ProductImage) ProductGalleryBottomSheetFragment.this.images.get(i)).f2182t)).addListener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        ProductGalleryBottomSheetFragment.this.progressViewHelper.dismiss();
                        return false;
                    }

                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        ProductGalleryBottomSheetFragment.this.progressViewHelper.dismiss();
                        return false;
                    }
                }).into((ImageView) photoView);
            } else {
                Glide.with(ProductGalleryBottomSheetFragment.this.getActivity()).load(Integer.valueOf(R.drawable.no_image_e_commerce)).addListener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        ProductGalleryBottomSheetFragment.this.progressViewHelper.dismiss();
                        return false;
                    }

                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        ProductGalleryBottomSheetFragment.this.progressViewHelper.dismiss();
                        return false;
                    }
                }).into((ImageView) photoView);
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

    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.layout_e_commerce_product_image_gallery, null);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        dialog.setContentView(inflate);
        Behavior behavior = ((LayoutParams) ((View) inflate.getParent()).getLayoutParams()).getBehavior();
        if (behavior != null && (behavior instanceof BottomSheetBehavior)) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(this.mBottomSheetBehaviorCallback);
        }
        this.progressViewHelper = new ProgressViewHelper(getActivity());
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = ECommerceConstant.PRODUCT_IMAGE_LIST;
            if (arguments.containsKey(str)) {
                this.images = (List) getArguments().getSerializable(str);
                if (this.images.size() == 0) {
                    this.images.add(new ProductImage());
                }
                this.position = getArguments().getInt(ECommerceConstant.PRODUCT_IMAGE_LIST_POSITION, 0);
                final ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter();
                this.viewPager.setAdapter(imagePagerAdapter);
                this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
                    public void onPageScrollStateChanged(int i) {
                    }

                    public void onPageScrolled(int i, float f, int i2) {
                    }

                    public void onPageSelected(int i) {
                        ProductGalleryBottomSheetFragment.this.countTextView.setText(Html.fromHtml(String.format("<font color=#969fa2>%s/</font><font color=#000000>%s</font>", new Object[]{Integer.valueOf(i + 1), Integer.valueOf(imagePagerAdapter.getCount())})));
                    }
                });
                this.viewPager.setCurrentItem(this.position);
                this.countTextView.setText(Html.fromHtml(String.format("<font color=#969fa2>%s/</font><font color=#000000>%s</font>", new Object[]{Integer.valueOf(this.position + 1), Integer.valueOf(imagePagerAdapter.getCount())})));
                this.closeButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        ProductGalleryBottomSheetFragment.this.dismiss();
                    }
                });
            }
        }
        dismiss();
        final ImagePagerAdapter imagePagerAdapter2 = new ImagePagerAdapter();
        this.viewPager.setAdapter(imagePagerAdapter2);
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                ProductGalleryBottomSheetFragment.this.countTextView.setText(Html.fromHtml(String.format("<font color=#969fa2>%s/</font><font color=#000000>%s</font>", new Object[]{Integer.valueOf(i + 1), Integer.valueOf(imagePagerAdapter2.getCount())})));
            }
        });
        this.viewPager.setCurrentItem(this.position);
        this.countTextView.setText(Html.fromHtml(String.format("<font color=#969fa2>%s/</font><font color=#000000>%s</font>", new Object[]{Integer.valueOf(this.position + 1), Integer.valueOf(imagePagerAdapter2.getCount())})));
        this.closeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductGalleryBottomSheetFragment.this.dismiss();
            }
        });
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
