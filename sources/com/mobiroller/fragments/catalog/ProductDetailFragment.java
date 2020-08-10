package com.mobiroller.fragments.catalog;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.mobiroller.constants.Constants;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.CategoryItemModel;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.ImageManager;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailFragment extends Fragment {
    @BindView(2131362102)
    CardView buyLayout;
    @BindView(2131362103)
    TextView buyText;
    /* access modifiers changed from: private */
    public CategoryItemModel categoryItemModel;
    private LocalizationHelper localizationHelper;
    @BindView(2131362988)
    WebView productDescription;
    @BindView(2131362993)
    TextView productPrice;
    @BindView(2131362995)
    TextView productPriceCurrency;
    @BindView(2131362996)
    TextView productPriceText;
    private SharedPrefHelper sharedPrefHelper;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363281)
    CardView topLayout;
    Unbinder unbinder;
    @BindView(2131363382)
    ViewPager viewPager;
    @BindView(2131363383)
    LinearLayout viewPagerCountDots;

    public class ImageAdapter extends PagerAdapter {
        Context context;
        /* access modifiers changed from: private */
        public ArrayList<String> images;
        LayoutInflater inflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        ImageAdapter(Context context2, List<String> list) {
            this.inflater = (LayoutInflater) context2.getSystemService("layout_inflater");
            this.images = new ArrayList<>(list);
            this.context = context2;
        }

        public int getCount() {
            return this.images.size();
        }

        public void add(String str) {
            this.images.add(str);
        }

        public Object instantiateItem(ViewGroup viewGroup, final int i) {
            View inflate = this.inflater.inflate(R.layout.layout_e_commerce_image_view_pager_item, viewGroup, false);
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            if (i == 0 && VERSION.SDK_INT >= 21) {
                imageView.setTransitionName("featuredImage");
            }
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment = new ProductGalleryBottomSheetFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(ECommerceConstant.PRODUCT_IMAGE_LIST, ImageAdapter.this.images);
                    bundle.putInt(ECommerceConstant.PRODUCT_IMAGE_LIST_POSITION, i);
                    productGalleryBottomSheetFragment.setArguments(bundle);
                    productGalleryBottomSheetFragment.setSharedElementEnterTransition(imageView);
                    productGalleryBottomSheetFragment.setSharedElementReturnTransition(imageView);
                    productGalleryBottomSheetFragment.show(ProductDetailFragment.this.getActivity().getSupportFragmentManager(), productGalleryBottomSheetFragment.getTag());
                }
            });
            if (this.images.get(i) != null) {
                ImageManager.loadImageView((String) this.images.get(i), imageView);
            } else {
                Glide.with(this.context).load(Integer.valueOf(R.drawable.no_image_e_commerce)).into(imageView);
            }
            viewGroup.addView(inflate, -1, -1);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            View view = (View) obj;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.catalog_product_details, viewGroup, false);
        this.unbinder = ButterKnife.bind((Object) this, inflate);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.localizationHelper = UtilManager.localizationHelper();
        if (getArguments() != null) {
            Bundle arguments = getArguments();
            String str = Constants.KEY_CATALOG_CATEGORY_PRODUCT;
            if (arguments.containsKey(str)) {
                this.categoryItemModel = (CategoryItemModel) getArguments().getSerializable(str);
                loadUi();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) this.localizationHelper.getLocalizedTitle(this.categoryItemModel.getItemTitle()));
            }
        }
        return inflate;
    }

    private void loadUi() {
        this.title.setText(this.localizationHelper.getLocalizedTitle(this.categoryItemModel.getItemTitle()));
        if (this.categoryItemModel.getItemPrice() == null || this.categoryItemModel.getCurrency() == null) {
            this.topLayout.setVisibility(8);
        } else {
            this.productPrice.setText(this.categoryItemModel.getItemPrice());
            this.productPriceCurrency.setText(this.categoryItemModel.getCurrency());
            if (this.categoryItemModel.getItemLink() != null) {
                if (this.categoryItemModel.getButton() != null) {
                    this.buyText.setText(UtilManager.localizationHelper().getLocalizedTitle(this.categoryItemModel.getButton()));
                }
                this.buyLayout.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (URLUtil.isValidUrl(ProductDetailFragment.this.categoryItemModel.getItemLink())) {
                            new Builder().build().launchUrl(ProductDetailFragment.this.getActivity(), Uri.parse(URLUtil.guessUrl(ProductDetailFragment.this.categoryItemModel.getItemLink())));
                        }
                    }
                });
            } else {
                this.buyLayout.setVisibility(8);
            }
        }
        if (this.categoryItemModel.getTableImages() == null || this.categoryItemModel.getTableImages().size() == 0) {
            this.viewPager.setVisibility(8);
        } else {
            ImageAdapter imageAdapter = new ImageAdapter(getActivity(), this.categoryItemModel.getTableImages());
            this.viewPager.setAdapter(imageAdapter);
            ImageView[] imageViewArr = new ImageView[imageAdapter.getCount()];
            Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.e_commerce_selecteditem_dot);
            drawable.setColorFilter(new PorterDuffColorFilter(this.sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
            Drawable drawable2 = ContextCompat.getDrawable(getActivity(), R.drawable.e_commerce_nonselecteditem_dot);
            drawable2.setColorFilter(new PorterDuffColorFilter(ColorUtil.getLighterColor(this.sharedPrefHelper.getActionBarColor(), 0.7f), Mode.MULTIPLY));
            ViewPager viewPager2 = this.viewPager;
            final ImageAdapter imageAdapter2 = imageAdapter;
            final ImageView[] imageViewArr2 = imageViewArr;
            final Drawable drawable3 = drawable2;
            final Drawable drawable4 = drawable;
            C41542 r2 = new OnPageChangeListener() {
                public void onPageScrollStateChanged(int i) {
                }

                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    for (int i2 = 0; i2 < imageAdapter2.getCount(); i2++) {
                        imageViewArr2[i2].setImageDrawable(drawable3);
                    }
                    imageViewArr2[i].setImageDrawable(drawable4);
                }
            };
            viewPager2.addOnPageChangeListener(r2);
            for (int i = 0; i < imageAdapter.getCount(); i++) {
                imageViewArr[i] = new ImageView(getActivity());
                imageViewArr[i].setImageDrawable(drawable2);
                LayoutParams layoutParams = new LayoutParams(-2, -2);
                layoutParams.setMargins(4, 0, 4, 0);
                this.viewPagerCountDots.addView(imageViewArr[i], layoutParams);
            }
            imageViewArr[0].setImageDrawable(drawable);
        }
        WebView webView = this.productDescription;
        StringBuilder sb = new StringBuilder();
        sb.append("<style>img{display: inline;height: auto;max-width: 100%;}</style>");
        sb.append(this.categoryItemModel.getItemDescription());
        webView.loadDataWithBaseURL("", sb.toString(), "text/html", "UTF-8", "");
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.unbinder.unbind();
    }
}
