package com.mobiroller.activities.ecommerce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.GsonBuilder;
import com.mobiroller.DynamicConstants;
import com.mobiroller.activities.base.ECommerceBaseActivity;
import com.mobiroller.activities.user.UserLoginActivity;
import com.mobiroller.constants.ECommerceConstant;
import com.mobiroller.enums.MobirollerDialogType;
import com.mobiroller.fragments.ecommerce.ProductGalleryBottomSheetFragment;
import com.mobiroller.helpers.ECommerceRequestHelper;
import com.mobiroller.helpers.ECommerceRequestHelper.ECommerceCallBack;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.ProgressViewHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.ToolbarHelper;
import com.mobiroller.helpers.UserHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.ecommerce.AddProductModel;
import com.mobiroller.models.ecommerce.ECommerceErrorResponse;
import com.mobiroller.models.ecommerce.ECommerceResponse;
import com.mobiroller.models.ecommerce.PaymentSettings;
import com.mobiroller.models.ecommerce.ProductDetailModel;
import com.mobiroller.models.ecommerce.ProductImage;
import com.mobiroller.models.ecommerce.ProductListModel;
import com.mobiroller.models.events.ShoppingCartCountEvent;
import com.mobiroller.preview.BuildConfig;
import com.mobiroller.util.ColorUtil;
import com.mobiroller.util.DialogUtil;
import com.mobiroller.util.ECommerceUtil;
import com.mobiroller.util.ErrorUtils;
import com.mobiroller.views.VideoEnabledWebView;
import com.mobiroller.views.custom.MobirollerBadgeView;
import com.mobiroller.views.custom.MobirollerButton;
import com.mobiroller.views.custom.MobirollerDialog.Builder;
import com.mobiroller.views.custom.MobirollerDialog.DialogButtonCallback;
import com.mobiroller.views.custom.MobirollerTextView;
import com.mobiroller.views.custom.MobirollerToolbar;
import com.mobiroller.views.theme.Theme;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.protocol.HTTP;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends ECommerceBaseActivity {
    private int amount;
    @BindView(2131362570)
    ConstraintLayout badgeLayout;
    @BindView(2131362101)
    MobirollerButton buyButton;
    @BindView(2131362117)
    CardView cargoCardView;
    @BindView(2131362119)
    MobirollerTextView cargoTextView;
    @BindView(2131362255)
    ConstraintLayout descriptionLayout;
    @BindView(2131362298)
    TextView discountedPrice;
    ECommerceRequestHelper eCommerceRequestHelper;
    String featuredImageUrl;
    @BindView(2131362462)
    MobirollerBadgeView freeShippingBadge;
    @BindView(2131362474)
    ImageView gradientImageView;
    ProductImageAdapter imageAdapter;
    @BindView(2131362542)
    ImageView imageView;
    @BindView(2131362543)
    ViewPager imageViewPager;
    LocalizationHelper localizationHelper;
    int mCurrentPosition;
    View mCurrentView;
    PaymentSettings paymentSettings;
    @BindView(2131362983)
    TextView price;
    String productId;
    private ProductListModel productListModel;
    ProductDetailModel productModel;
    ProgressViewHelper progressViewHelper;
    @BindView(2131363007)
    TextView quantity;
    @BindView(2131363008)
    ImageView quantityDown;
    @BindView(2131363009)
    ConstraintLayout quantityLayout;
    @BindView(2131363011)
    ImageView quantityUp;
    @BindView(2131363046)
    ConstraintLayout returnTermsLayout;
    @BindView(2131363079)
    CardView saleCardView;
    @BindView(2131363080)
    MobirollerTextView saleRateTextView;
    int selectedPosition = 0;
    SharedPrefHelper sharedPrefHelper;
    @BindView(2131363132)
    ConstraintLayout shippingTermsLayout;
    @BindView(2131363161)
    MobirollerBadgeView soldOutBadge;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363270)
    MobirollerToolbar toolbar;
    @BindView(2131363277)
    MobirollerToolbar toolbarWebView;
    @BindView(2131363383)
    LinearLayout viewPagerCountDots;
    @BindView(2131363395)
    VideoEnabledWebView webView;
    @BindView(2131362187)
    LinearLayout webViewLayout;

    public class ProductImageAdapter extends PagerAdapter {
        AppCompatActivity context;
        /* access modifiers changed from: private */
        public ArrayList<ProductImage> images;
        LayoutInflater inflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        ProductImageAdapter(AppCompatActivity appCompatActivity, List<ProductImage> list) {
            this.inflater = (LayoutInflater) appCompatActivity.getSystemService("layout_inflater");
            this.images = new ArrayList<>(list);
            this.context = appCompatActivity;
        }

        public int getCount() {
            return this.images.size();
        }

        public void add(ProductImage productImage) {
            this.images.add(productImage);
        }

        public void addAll(List<ProductImage> list) {
            for (ProductImage productImage : list) {
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= this.images.size()) {
                        break;
                    } else if (((ProductImage) this.images.get(i)).f2181n.equalsIgnoreCase(productImage.f2181n)) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
                if (!z) {
                    this.images.add(productImage);
                }
            }
        }

        public Object instantiateItem(ViewGroup viewGroup, final int i) {
            View inflate = this.inflater.inflate(R.layout.layout_e_commerce_image_view_pager_item, viewGroup, false);
            final ImageView imageView = (ImageView) inflate.findViewById(R.id.image);
            if (ProductDetailActivity.this.selectedPosition == i && VERSION.SDK_INT >= 21) {
                imageView.setTransitionName("featuredImage");
            }
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ProductGalleryBottomSheetFragment productGalleryBottomSheetFragment = new ProductGalleryBottomSheetFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(ECommerceConstant.PRODUCT_IMAGE_LIST, ProductImageAdapter.this.images);
                    bundle.putInt(ECommerceConstant.PRODUCT_IMAGE_LIST_POSITION, i);
                    productGalleryBottomSheetFragment.setArguments(bundle);
                    productGalleryBottomSheetFragment.setSharedElementEnterTransition(imageView);
                    productGalleryBottomSheetFragment.setSharedElementReturnTransition(imageView);
                    productGalleryBottomSheetFragment.show(ProductImageAdapter.this.context.getSupportFragmentManager(), productGalleryBottomSheetFragment.getTag());
                }
            });
            ArrayList<ProductImage> arrayList = this.images;
            if (arrayList == null || arrayList.get(i) == null || ((ProductImage) this.images.get(i)).f2182t == null) {
                Glide.with((FragmentActivity) this.context).load(Integer.valueOf(R.drawable.no_image_e_commerce)).into(imageView);
            } else {
                Glide.with((FragmentActivity) this.context).load(((ProductImage) this.images.get(i)).f2182t).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(imageView);
            }
            viewGroup.addView(inflate, -1, -1);
            return inflate;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            ProductDetailActivity productDetailActivity = ProductDetailActivity.this;
            productDetailActivity.mCurrentPosition = i;
            productDetailActivity.mCurrentView = (View) obj;
        }
    }

    public static void startActivity(Context context, String str, String str2, String str3, ActivityOptionsCompat activityOptionsCompat) {
        if (str == null) {
            Toast.makeText(context, R.string.common_error, 0).show();
        } else {
            context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("productId", str).putExtra(ECommerceConstant.PRODUCT_TITLE, str2).putExtra(ECommerceConstant.PRODUCT_FEATURED_IMAGE, str3), activityOptionsCompat.toBundle());
        }
    }

    public static void startActivity(Context context, ProductDetailModel productDetailModel, String str, String str2, ActivityOptionsCompat activityOptionsCompat) {
        if (productDetailModel == null) {
            Toast.makeText(context, R.string.common_error, 0).show();
        } else {
            context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra(ECommerceConstant.PRODUCT_DETAIL_MODEL, productDetailModel).putExtra(ECommerceConstant.PRODUCT_TITLE, str).putExtra(ECommerceConstant.PRODUCT_FEATURED_IMAGE, str2), activityOptionsCompat.toBundle());
        }
    }

    public static void startActivity(Context context, ProductListModel productListModel2, String str, String str2, ActivityOptionsCompat activityOptionsCompat) {
        if (productListModel2 == null) {
            Toast.makeText(context, R.string.common_error, 0).show();
        } else {
            context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra(ECommerceConstant.PRODUCT_LIST_MODEL, productListModel2).putExtra(ECommerceConstant.PRODUCT_TITLE, str).putExtra(ECommerceConstant.PRODUCT_FEATURED_IMAGE, str2), activityOptionsCompat.toBundle());
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_product_details);
        ButterKnife.bind((Activity) this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (VERSION.SDK_INT < 26) {
            setRequestedOrientation(1);
        }
        if (VERSION.SDK_INT >= 21) {
            supportPostponeEnterTransition();
        }
        LayoutParams layoutParams = (LayoutParams) this.toolbar.getLayoutParams();
        layoutParams.topMargin = ScreenHelper.dpToPx(24);
        this.toolbar.setLayoutParams(layoutParams);
        this.webViewLayout.setBackgroundColor(UtilManager.sharedPrefHelper().getActionBarColor());
        if (VERSION.SDK_INT >= 23) {
            View decorView = getWindow().getDecorView();
            if ((ColorUtil.isColorDark(Theme.primaryColor) ? (char) 65535 : 0) == 65535) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & -8193);
            } else {
                decorView.setSystemUiVisibility(8192);
            }
        }
        this.toolbar.setNavigationIcon((int) R.drawable.ic_arrow_back_white_24dp);
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.localizationHelper = UtilManager.localizationHelper();
        this.progressViewHelper = new ProgressViewHelper((AppCompatActivity) this);
        this.eCommerceRequestHelper = new ECommerceRequestHelper();
        String str = "productId";
        if (getIntent().hasExtra(str)) {
            this.productId = getIntent().getStringExtra(str);
        } else {
            Intent intent = getIntent();
            String str2 = ECommerceConstant.PRODUCT_LIST_MODEL;
            if (intent.hasExtra(str2)) {
                this.productListModel = (ProductListModel) getIntent().getSerializableExtra(str2);
                this.productId = this.productListModel.f2183id;
                if (this.productListModel.featuredImage != null) {
                    this.featuredImageUrl = this.productListModel.featuredImage.f2182t;
                }
            } else {
                this.productModel = (ProductDetailModel) getIntent().getSerializableExtra(ECommerceConstant.PRODUCT_DETAIL_MODEL);
                this.productId = this.productModel.f2183id;
            }
        }
        this.featuredImageUrl = getIntent().getStringExtra(ECommerceConstant.PRODUCT_FEATURED_IMAGE);
        loadPreUI();
        if (this.productModel == null) {
            getProduct();
            return;
        }
        loadUi();
        int i = 0;
        if (this.productModel.images.size() != 0 && this.featuredImageUrl != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.productModel.images.size()) {
                    break;
                } else if (((ProductImage) this.productModel.images.get(i2)).f2182t.equalsIgnoreCase(this.productModel.featuredImage.f2182t)) {
                    i = i2;
                    break;
                } else {
                    i2++;
                }
            }
        }
        this.imageViewPager.setCurrentItem(i);
    }

    private void setSharedElement() {
        if (VERSION.SDK_INT >= 21) {
            ImageView imageView2 = this.gradientImageView;
            int i = -1;
            if (!ColorUtil.isColorDark(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK)) {
                i = ViewCompat.MEASURED_STATE_MASK;
            }
            imageView2.setBackgroundTintList(ColorStateList.valueOf(i));
            getWindow().getSharedElementEnterTransition().addListener(new TransitionListener() {
                public void onTransitionCancel(Transition transition) {
                }

                public void onTransitionPause(Transition transition) {
                }

                public void onTransitionResume(Transition transition) {
                }

                public void onTransitionStart(Transition transition) {
                }

                public void onTransitionEnd(Transition transition) {
                    ProductDetailActivity.this.imageView.setVisibility(8);
                }
            });
            if (this.productModel.featuredImage == null || this.productModel.featuredImage.f2182t == null) {
                supportStartPostponedEnterTransition();
            } else {
                Glide.with((FragmentActivity) this).load(this.productModel.featuredImage.f2182t).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).addListener(new RequestListener<Drawable>() {
                    public boolean onLoadFailed(GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                        ProductDetailActivity.this.supportStartPostponedEnterTransition();
                        return false;
                    }

                    public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                        ProductDetailActivity.this.supportStartPostponedEnterTransition();
                        return false;
                    }
                }).into(this.imageView);
            }
        } else {
            this.imageView.setVisibility(8);
        }
    }

    private void loadPreUI() {
        if (this.productListModel != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.productListModel.featuredImage);
            this.imageAdapter = new ProductImageAdapter(this, arrayList);
            this.imageViewPager.setAdapter(this.imageAdapter);
            this.imageViewPager.setCurrentItem(0);
            this.title.setText(this.localizationHelper.getLocalizedTitle(this.productListModel.title));
            String str = " ";
            if (this.productListModel.campaignPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                TextView textView = this.price;
                StringBuilder sb = new StringBuilder();
                sb.append(ECommerceUtil.getPriceString(this.productListModel.price));
                sb.append(str);
                sb.append(ECommerceUtil.getCurrencySymbol(this.productListModel.currency));
                textView.setText(sb.toString());
                this.discountedPrice.setVisibility(8);
                this.saleCardView.setVisibility(8);
            } else {
                TextView textView2 = this.price;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(ECommerceUtil.getPriceString(this.productListModel.campaignPrice));
                sb2.append(str);
                sb2.append(ECommerceUtil.getCurrencySymbol(this.productListModel.currency));
                textView2.setText(sb2.toString());
                this.discountedPrice.setVisibility(0);
                this.saleCardView.setVisibility(0);
                this.saleRateTextView.setText(String.format("%%%s", new Object[]{Double.valueOf(((this.productListModel.price - this.productListModel.campaignPrice) * 100.0d) / this.productListModel.price)}));
                this.saleRateTextView.setTextSize(2, 15.0f);
                TextView textView3 = this.discountedPrice;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("<strike>");
                sb3.append(ECommerceUtil.getPriceString(this.productListModel.price));
                sb3.append(str);
                sb3.append(ECommerceUtil.getCurrencySymbol(this.productListModel.currency));
                sb3.append("</strike>");
                textView3.setText(Html.fromHtml(sb3.toString()));
            }
            if (this.productListModel.stock <= 0) {
                this.quantityLayout.setVisibility(8);
                setContinueButton(false);
            }
            this.amount = 1;
        }
        setSupportActionBar(this.toolbar);
        this.toolbar.setTitleTypeface();
        setTitle("");
        this.toolbar.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductDetailActivity.this.finish();
            }
        });
    }

    private void onClickShareButton() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.localizationHelper.getLocalizedTitle(this.productModel.title));
        String str = " ";
        sb.append(str);
        sb.append(this.productModel.getPriceString());
        sb.append(str);
        sb.append(getString(R.string.app_name));
        sb.append(" https://play.google.com/store/apps/details?id=");
        sb.append(BuildConfig.APPLICATION_ID);
        String sb2 = sb.toString();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType(HTTP.PLAIN_TEXT_TYPE);
        intent.putExtra("android.intent.extra.TEXT", sb2);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.action_share)));
    }

    private void getProduct() {
        if (UtilManager.networkHelper().isConnected()) {
            this.progressViewHelper.show();
            this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getProduct(this.productId), new ECommerceCallBack<ProductDetailModel>() {
                public void done() {
                    if (!ProductDetailActivity.this.isFinishing() && ProductDetailActivity.this.progressViewHelper.isShowing()) {
                        ProductDetailActivity.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(ProductDetailModel productDetailModel) {
                    ProductDetailActivity productDetailActivity = ProductDetailActivity.this;
                    productDetailActivity.productModel = productDetailModel;
                    productDetailActivity.loadUi();
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(ProductDetailActivity.this);
                }

                public void onNetworkError(String str) {
                    ErrorUtils.showErrorToast(ProductDetailActivity.this);
                }
            });
            return;
        }
        DialogUtil.showNoConnectionError(this);
    }

    /* access modifiers changed from: private */
    public void loadUi() {
        this.toolbarWebView.setTitleTypeface();
        this.toolbarWebView.setTitle((CharSequence) getString(R.string.e_commerce_product_detail_description));
        this.toolbarWebView.setNavigationOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProductDetailActivity.this.webViewLayout.setVisibility(8);
                new ToolbarHelper().setStatusBarTransparent(ProductDetailActivity.this);
            }
        });
        if (VERSION.SDK_INT >= 21) {
            this.webView.getSettings().setMixedContentMode(0);
            this.webView.setLayerType(2, null);
        } else if (VERSION.SDK_INT >= 19) {
            this.webView.setLayerType(2, null);
        } else {
            this.webView.setLayerType(1, null);
        }
        VideoEnabledWebView videoEnabledWebView = this.webView;
        StringBuilder sb = new StringBuilder();
        sb.append("<style>img{display: inline;height: auto;max-width: 100%;}</style>");
        sb.append(UtilManager.localizationHelper().getLocalizedTitle(this.productModel.description));
        videoEnabledWebView.loadDataWithBaseURL("", sb.toString(), "text/html", "UTF-8", "");
        setSharedElement();
        ProductImageAdapter productImageAdapter = this.imageAdapter;
        if (productImageAdapter != null) {
            productImageAdapter.addAll(this.productModel.images);
            this.imageAdapter.notifyDataSetChanged();
        } else {
            this.imageAdapter = new ProductImageAdapter(this, this.productModel.images);
            this.imageViewPager.setAdapter(this.imageAdapter);
        }
        this.title.setText(this.localizationHelper.getLocalizedTitle(this.productModel.title));
        if (this.productModel.useFixPrice || this.productModel.shippingPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.cargoCardView.setVisibility(8);
        } else {
            this.cargoCardView.setVisibility(0);
            MobirollerTextView mobirollerTextView = this.cargoTextView;
            mobirollerTextView.setText(Html.fromHtml(mobirollerTextView.getContext().getString(R.string.e_commerce_product_detail_cargo_warning, new Object[]{String.format("%s %s", new Object[]{ECommerceUtil.getPriceString(this.productModel.shippingPrice), ECommerceUtil.getCurrencySymbol(this.productModel.currency)})})));
        }
        String str = " ";
        if (this.productModel.campaignPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            TextView textView = this.price;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(ECommerceUtil.getPriceString(this.productModel.price));
            sb2.append(str);
            sb2.append(ECommerceUtil.getCurrencySymbol(this.productModel.currency));
            textView.setText(sb2.toString());
        } else {
            TextView textView2 = this.price;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(ECommerceUtil.getPriceString(this.productModel.campaignPrice));
            sb3.append(str);
            sb3.append(ECommerceUtil.getCurrencySymbol(this.productModel.currency));
            textView2.setText(sb3.toString());
        }
        if (this.productModel.campaignPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            TextView textView3 = this.price;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(ECommerceUtil.getPriceString(this.productModel.price));
            sb4.append(str);
            sb4.append(ECommerceUtil.getCurrencySymbol(this.productModel.currency));
            textView3.setText(sb4.toString());
            this.discountedPrice.setVisibility(8);
            this.saleCardView.setVisibility(8);
        } else {
            TextView textView4 = this.price;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(ECommerceUtil.getPriceString(this.productModel.campaignPrice));
            sb5.append(str);
            sb5.append(ECommerceUtil.getCurrencySymbol(this.productModel.currency));
            textView4.setText(sb5.toString());
            this.discountedPrice.setVisibility(0);
            this.saleCardView.setVisibility(0);
            this.saleRateTextView.setText(String.format("%%%s", new Object[]{Long.valueOf(Math.round(((this.productModel.price - this.productModel.campaignPrice) * 100.0d) / this.productModel.price))}));
            this.saleRateTextView.setTextSize(2, 15.0f);
            TextView textView5 = this.discountedPrice;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("<strike>");
            sb6.append(ECommerceUtil.getPriceString(this.productModel.price));
            sb6.append(str);
            sb6.append(ECommerceUtil.getCurrencySymbol(this.productModel.currency));
            sb6.append("</strike>");
            textView5.setText(Html.fromHtml(sb6.toString()));
        }
        if (this.productModel.stock <= 0) {
            this.badgeLayout.setVisibility(0);
            this.soldOutBadge.setVisibility(0);
            this.quantityLayout.setVisibility(8);
            setContinueButton(false);
        } else {
            this.badgeLayout.setVisibility(8);
            this.soldOutBadge.setVisibility(8);
        }
        if (this.productModel.shippingPrice == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            this.badgeLayout.setVisibility(0);
            this.freeShippingBadge.setVisibility(0);
            if (this.soldOutBadge.getVisibility() == 8) {
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.freeShippingBadge.getLayoutParams();
                layoutParams.setMarginStart(0);
                this.freeShippingBadge.setLayoutParams(layoutParams);
            }
        } else {
            this.freeShippingBadge.setVisibility(8);
        }
        this.amount = 1;
        this.quantity.setText(String.valueOf(this.amount));
        ProductImageAdapter productImageAdapter2 = this.imageAdapter;
        if (productImageAdapter2 == null || productImageAdapter2.getCount() <= 1) {
            this.viewPagerCountDots.setVisibility(8);
            return;
        }
        final ImageView[] imageViewArr = new ImageView[this.imageAdapter.getCount()];
        final Drawable drawable = ContextCompat.getDrawable(this, R.drawable.e_commerce_selecteditem_dot);
        drawable.setColorFilter(new PorterDuffColorFilter(this.sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
        final Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.e_commerce_nonselecteditem_dot);
        drawable2.setColorFilter(new PorterDuffColorFilter(ColorUtil.getLighterColor(this.sharedPrefHelper.getActionBarColor(), 0.7f), Mode.MULTIPLY));
        this.imageViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < ProductDetailActivity.this.imageAdapter.getCount(); i2++) {
                    imageViewArr[i2].setImageDrawable(drawable2);
                }
                imageViewArr[i].setImageDrawable(drawable);
            }
        });
        for (int i = 0; i < this.imageAdapter.getCount(); i++) {
            imageViewArr[i] = new ImageView(this);
            imageViewArr[i].setImageDrawable(drawable2);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams2.setMargins(0, 0, 0, 0);
            this.viewPagerCountDots.addView(imageViewArr[i], layoutParams2);
        }
        imageViewArr[0].setImageDrawable(drawable);
    }

    public void setContinueButton(boolean z) {
        this.buyButton.setEnabled(z);
        if (z) {
            this.buyButton.setText(getString(R.string.e_commerce_product_detail_buy_now));
        } else {
            this.buyButton.setText(getString(R.string.e_commerce_product_detail_sold_out_button));
            this.buyButton.removeIcon();
        }
        this.buyButton.setColor();
    }

    @OnClick({2131363008})
    public void onClickQuantityDown() {
        int i = this.amount;
        if (i != 1) {
            this.amount = i - 1;
            this.quantity.setText(String.valueOf(this.amount));
        }
    }

    @OnClick({2131363011})
    public void onClickQuantityUp() {
        if (this.amount == this.productModel.maxQuantityPerOrder || this.amount == this.productModel.stock) {
            Toast.makeText(this, getString(R.string.e_commerce_product_detail_maximum_product_message, new Object[]{String.valueOf(this.amount)}), 0).show();
            return;
        }
        this.amount++;
        this.quantity.setText(String.valueOf(this.amount));
    }

    @OnClick({2131362101})
    public void onClickBuyButton() {
        if (DynamicConstants.MobiRoller_Stage) {
            OrderFlowActivity.startActivity(this);
            return;
        }
        if (!UtilManager.networkHelper().isConnected()) {
            DialogUtil.showNoConnectionInfo(this);
        } else if (this.sharedPrefHelper.getUserLoginStatus() && this.productModel != null) {
            addProductToCart();
        } else if (!this.sharedPrefHelper.getUserLoginStatus()) {
            startActivity(new Intent(this, UserLoginActivity.class));
        } else {
            Toast.makeText(this, R.string.common_error, 0).show();
        }
    }

    private void addProductToCart() {
        this.progressViewHelper.show();
        this.eCommerceRequestHelper.getAPIService().addProductToShoppingCart(UserHelper.getUserId(), new AddProductModel(this.productId, this.amount, this.localizationHelper.getLocalizedTitle(this.productModel.title))).enqueue(new Callback<ECommerceResponse>() {
            public void onResponse(Call<ECommerceResponse> call, Response<ECommerceResponse> response) {
                if (!ProductDetailActivity.this.isFinishing() && ProductDetailActivity.this.progressViewHelper != null && ProductDetailActivity.this.progressViewHelper.isShowing()) {
                    ProductDetailActivity.this.progressViewHelper.dismiss();
                }
                if (response.isSuccessful() || response.code() == 204) {
                    ProductDetailActivity.this.buyButton.setTextAnimated(ProductDetailActivity.this.getString(R.string.e_commerce_product_detail_added_to_shopping_cart_button));
                    new ECommerceUtil().getBadgeCount();
                } else if (response.code() == 400) {
                    try {
                        ECommerceResponse eCommerceResponse = (ECommerceResponse) new GsonBuilder().create().fromJson(response.errorBody().string(), ECommerceResponse.class);
                        if (eCommerceResponse != null) {
                            if (eCommerceResponse.key.equalsIgnoreCase(ECommerceConstant.PRODUCT_MAX_QUANTITY_PER_ORDER_EXCEEDED)) {
                                ProductDetailActivity productDetailActivity = ProductDetailActivity.this;
                                String string = productDetailActivity.getString(R.string.e_commerce_product_detail_maximum_product_limit_title);
                                ProductDetailActivity productDetailActivity2 = ProductDetailActivity.this;
                                productDetailActivity.showWarning(string, productDetailActivity2.getString(R.string.e_commerce_product_detail_maximum_product_limit_description, new Object[]{String.valueOf(productDetailActivity2.productModel.maxQuantityPerOrder)}), ProductDetailActivity.this.getString(R.string.e_commerce_product_detail_maximum_product_limit_button), null);
                            } else if (eCommerceResponse.key.equalsIgnoreCase(ECommerceConstant.PRODUCT_MAX_STOCK_EXCEEDED)) {
                                ProductDetailActivity productDetailActivity3 = ProductDetailActivity.this;
                                productDetailActivity3.showWarning(productDetailActivity3.getString(R.string.e_commerce_product_detail_out_of_stock_title), ProductDetailActivity.this.getString(R.string.e_commerce_product_detail_out_of_stock_description), ProductDetailActivity.this.getString(R.string.e_commerce_product_detail_out_of_stock_button), new DialogButtonCallback() {
                                    public void onClickButton() {
                                        ProductDetailActivity.this.finish();
                                    }
                                });
                            } else if (eCommerceResponse.isUserFriendlyMessage) {
                                Toast.makeText(ProductDetailActivity.this, "UserFriendly message", 0).show();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onFailure(Call<ECommerceResponse> call, Throwable th) {
                if (!ProductDetailActivity.this.isFinishing() && ProductDetailActivity.this.progressViewHelper != null && ProductDetailActivity.this.progressViewHelper.isShowing()) {
                    ProductDetailActivity.this.progressViewHelper.dismiss();
                }
                ErrorUtils.showErrorToast(ProductDetailActivity.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void showWarning(String str, String str2, String str3, DialogButtonCallback dialogButtonCallback) {
        new Builder().setContext(this).setTitle(str).setListener(dialogButtonCallback).setDescription(str2).setType(MobirollerDialogType.BASIC).setColor(Color.parseColor("#F8E7D8")).setIconResource(R.drawable.ic_order_warning_icon).setButtonText(str3).show();
    }

    public void onBackPressed() {
        if (this.webViewLayout.getVisibility() == 0) {
            this.webViewLayout.setVisibility(8);
            new ToolbarHelper().setStatusBarTransparent(this);
            return;
        }
        finish();
    }

    @OnClick({2131363046})
    public void onClickTermsLayout() {
        if (this.paymentSettings == null) {
            getTerms(false);
        } else {
            showTerms(getString(R.string.e_commerce_product_detail_return_terms), this.localizationHelper.getLocalizedTitle(this.paymentSettings.cancellationProcedure));
        }
    }

    @OnClick({2131363132})
    public void onClickShippingTermsLayout() {
        if (this.paymentSettings == null) {
            getTerms(true);
        } else {
            showTerms(getString(R.string.e_commerce_product_detail_delivery_conditions), this.localizationHelper.getLocalizedTitle(this.paymentSettings.deliveryConditions));
        }
    }

    @OnClick({2131362256})
    public void onClickDescriptionLayout() {
        this.webViewLayout.setVisibility(0);
        new ToolbarHelper().setStatusBarColor(this);
    }

    private void getTerms(final boolean z) {
        if (UtilManager.networkHelper().isConnected()) {
            this.progressViewHelper.show();
            this.eCommerceRequestHelper.enqueue(this.eCommerceRequestHelper.getAPIService().getPaymentSettings(), new ECommerceCallBack<PaymentSettings>() {
                public void done() {
                    if (!ProductDetailActivity.this.isFinishing() && ProductDetailActivity.this.progressViewHelper.isShowing()) {
                        ProductDetailActivity.this.progressViewHelper.dismiss();
                    }
                }

                public void onSuccess(PaymentSettings paymentSettings) {
                    ProductDetailActivity productDetailActivity = ProductDetailActivity.this;
                    productDetailActivity.paymentSettings = paymentSettings;
                    if (z) {
                        productDetailActivity.showTerms(productDetailActivity.getString(R.string.e_commerce_product_detail_delivery_conditions), ProductDetailActivity.this.localizationHelper.getLocalizedTitle(ProductDetailActivity.this.paymentSettings.deliveryConditions));
                    } else {
                        productDetailActivity.showTerms(productDetailActivity.getString(R.string.e_commerce_product_detail_return_terms), ProductDetailActivity.this.localizationHelper.getLocalizedTitle(ProductDetailActivity.this.paymentSettings.cancellationProcedure));
                    }
                }

                public void onFailure(ECommerceErrorResponse eCommerceErrorResponse) {
                    ErrorUtils.showErrorToast(ProductDetailActivity.this);
                }

                public void onNetworkError(String str) {
                    ErrorUtils.showErrorToast(ProductDetailActivity.this);
                }
            });
            return;
        }
        DialogUtil.showNoConnectionInfo(this);
    }

    /* access modifiers changed from: private */
    public void showTerms(String str, String str2) {
        Spannable spannable;
        if (!isFinishing()) {
            if (VERSION.SDK_INT >= 24) {
                spannable = (Spannable) Html.fromHtml(str2, 0, null, null);
            } else {
                spannable = (Spannable) Html.fromHtml(str2, null, null);
            }
            new Builder().setContext(this).setTitle(str).setIconResource(R.drawable.ic_document_icon).setDescription(spannable).setButtonText(getString(R.string.e_commerce_product_detail_terms_delivery_conditions_popup_button)).setType(MobirollerDialogType.BASIC).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.toolbar.inflateMenu(R.menu.ecommerce_detail_menu);
        View actionView = this.toolbar.getMenu().findItem(R.id.action_shopping_cart).getActionView();
        ImageViewCompat.setImageTintList((ImageView) actionView.findViewById(R.id.shopping_bag_icon), ColorStateList.valueOf(ColorUtil.isColorDark(Theme.primaryColor) ? -1 : ViewCompat.MEASURED_STATE_MASK));
        setBadgeCount(ECommerceUtil.badgeCount);
        actionView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!UtilManager.sharedPrefHelper().getUserLoginStatus()) {
                    ProductDetailActivity productDetailActivity = ProductDetailActivity.this;
                    productDetailActivity.startActivity(new Intent(productDetailActivity, UserLoginActivity.class));
                    return;
                }
                ProductDetailActivity productDetailActivity2 = ProductDetailActivity.this;
                productDetailActivity2.startActivity(new Intent(productDetailActivity2, ShoppingCartActivity.class));
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_search /*2131361903*/:
                startActivity(new Intent(this, ProductSearchActivity.class));
                return true;
            case R.id.action_share /*2131361904*/:
                onClickShareButton();
                return true;
            case R.id.action_shopping_cart /*2131361905*/:
                if (!UtilManager.sharedPrefHelper().getUserLoginStatus()) {
                    startActivity(new Intent(this, UserLoginActivity.class));
                } else {
                    startActivity(new Intent(this, ShoppingCartActivity.class));
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void setBadgeCount(int i) {
        MobirollerToolbar mobirollerToolbar = this.toolbar;
        if (mobirollerToolbar != null && mobirollerToolbar.getMenu() != null && this.toolbar.getMenu().findItem(R.id.action_shopping_cart) != null && this.toolbar.getMenu().findItem(R.id.action_shopping_cart).getActionView() != null) {
            TextView textView = (TextView) this.toolbar.getMenu().findItem(R.id.action_shopping_cart).getActionView().findViewById(R.id.cart_badge);
            if (i > 0) {
                textView.setText(String.valueOf(i));
                textView.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
        }
    }

    @Subscribe
    public void onPostShoppingCartCountEvent(ShoppingCartCountEvent shoppingCartCountEvent) {
        setBadgeCount(shoppingCartCountEvent.shoppingCartItemCount);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
