package com.mobiroller.activities.inapppurchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.browser.customtabs.CustomTabsIntent.Builder;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.anjlab.android.iab.p020v3.BillingProcessor;
import com.anjlab.android.iab.p020v3.BillingProcessor.IBillingHandler;
import com.anjlab.android.iab.p020v3.SkuDetails;
import com.anjlab.android.iab.p020v3.TransactionDetails;
import com.mobiroller.activities.ActivityHandler;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.LocalizationHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.InAppPurchaseProduct;
import com.mobiroller.models.InAppPurchaseProductImageModel;
import com.mobiroller.models.events.InAppPurchaseSuccessEvent;
import com.mobiroller.util.ImageManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;

public class InAppPurchaseDetailActivity extends AppCompatActivity implements IBillingHandler {
    public static final String INTENT_EXTRA_IN_APP_PURCHASE_PRODUCT = "inAppPurchaseProduct";
    @BindView(2131361960)
    ImageView backButton;
    @BindView(2131362100)
    Button buyActionButton;
    @BindView(2131362252)
    TextView description;
    @BindView(2131362265)
    Button detailActionButton;
    /* access modifiers changed from: private */
    public ImageView[] dots;
    /* access modifiers changed from: private */
    public int dotsCount;
    /* access modifiers changed from: private */
    public YouTubePlayer initializedYouTubePlayer;
    private boolean isLifeTimeSelected = false;
    private boolean isSubscriptionSelected = false;
    @BindView(2131362607)
    AppCompatTextView lifeTimePriceText;
    private BillingProcessor mBillingProcessor;
    private boolean mIsFromActivity;
    private SkuDetails mLifeTimeSkuDetails;
    private LocalizationHelper mLocalizationHelper;
    private InAppPurchaseProduct mProduct;
    private String mScreenId;
    private String mScreenType;
    /* access modifiers changed from: private */
    public SharedPrefHelper mSharedPref;
    private String mSubScreenType;
    private SkuDetails mSubscribeSkuDetails;
    @BindView(2131362649)
    LinearLayout mainLayout;
    @BindView(2131362840)
    ImageView oneTimeButton;
    @BindView(2131362841)
    RelativeLayout oneTimeLayout;
    @BindView(2131363379)
    LinearLayout pagerIndicator;
    @BindView(2131363192)
    ImageView subscriptionButton;
    @BindView(2131363194)
    RelativeLayout subscriptionLayout;
    @BindView(2131363195)
    AppCompatTextView subscriptionPeriod;
    @BindView(2131363196)
    AppCompatTextView subscriptionPriceText;
    @BindView(2131363258)
    TextView title;
    @BindView(2131363382)
    ViewPager viewPager;
    /* access modifiers changed from: private */
    public YouTubePlayerView youtubePlayerView;

    public class CustomPagerAdapter extends PagerAdapter {
        private boolean isVideoActive;
        private Context mContext;
        /* access modifiers changed from: private */
        public List<Object> mDataList = new ArrayList();

        public CharSequence getPageTitle(int i) {
            return "";
        }

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public CustomPagerAdapter(Context context, List<InAppPurchaseProductImageModel> list, String str) {
            this.mContext = context;
            if (str != null) {
                this.mDataList.add(0, str);
                this.isVideoActive = true;
            }
            this.mDataList.addAll(list);
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            LayoutInflater from = LayoutInflater.from(this.mContext);
            if (i != 0 || !this.isVideoActive) {
                ViewGroup viewGroup2 = (ViewGroup) from.inflate(R.layout.layout_in_app_purchase_detail_view_pager_item, viewGroup, false);
                ImageManager.loadImageViewInAppPurchase(this.mContext, ((InAppPurchaseProductImageModel) this.mDataList.get(i)).imageUrl, (ImageView) viewGroup2.findViewById(R.id.image_view));
                viewGroup.addView(viewGroup2);
                return viewGroup2;
            }
            ViewGroup viewGroup3 = (ViewGroup) from.inflate(R.layout.layout_in_app_purchase_video_detail_view_pager_item, viewGroup, false);
            InAppPurchaseDetailActivity.this.youtubePlayerView = (YouTubePlayerView) viewGroup3.findViewById(R.id.video_view);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showCurrentTime(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showSeekBar(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showYouTubeButton(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showBufferingProgress(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showMenuButton(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showFullscreenButton(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showVideoTitle(false);
            InAppPurchaseDetailActivity.this.youtubePlayerView.getPlayerUIController().showDuration(false);
            InAppPurchaseDetailActivity.this.getLifecycle().addObserver(InAppPurchaseDetailActivity.this.youtubePlayerView);
            InAppPurchaseDetailActivity.this.youtubePlayerView.initialize(new YouTubePlayerInitListener() {
                public void onInitSuccess(final YouTubePlayer youTubePlayer) {
                    InAppPurchaseDetailActivity.this.initializedYouTubePlayer = youTubePlayer;
                    youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        public void onReady() {
                            youTubePlayer.loadVideo(String.valueOf(CustomPagerAdapter.this.mDataList.get(0)), 0.0f);
                        }
                    });
                }
            }, true);
            viewGroup.addView(viewGroup3);
            return viewGroup3;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        public int getCount() {
            return this.mDataList.size();
        }
    }

    /* renamed from: com.mobiroller.activities.inapppurchase.InAppPurchaseDetailActivity$Utils */
    static class C3957Utils {
        static SortedMap<Currency, Locale> currencyLocaleMap = new TreeMap(new Comparator<Currency>() {
            public int compare(Currency currency, Currency currency2) {
                return currency.getCurrencyCode().compareTo(currency2.getCurrencyCode());
            }
        });

        C3957Utils() {
        }

        static {
            Locale[] availableLocales;
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    currencyLocaleMap.put(Currency.getInstance(locale), locale);
                } catch (Exception unused) {
                }
            }
        }

        static String getCurrencySymbol(String str) {
            Currency instance = Currency.getInstance(str);
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(":-");
            sb.append(instance.getSymbol((Locale) currencyLocaleMap.get(instance)));
            printStream.println(sb.toString());
            return instance.getSymbol((Locale) currencyLocaleMap.get(instance));
        }
    }

    public void onBillingError(int i, Throwable th) {
    }

    public void onPurchaseHistoryRestored() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_in_app_purchase_detail);
        ButterKnife.bind((Activity) this);
        this.mSharedPref = UtilManager.sharedPrefHelper();
        this.mLocalizationHelper = UtilManager.localizationHelper();
        Intent intent = getIntent();
        String str = INTENT_EXTRA_IN_APP_PURCHASE_PRODUCT;
        if (intent.hasExtra(str)) {
            this.mProduct = (InAppPurchaseProduct) getIntent().getSerializableExtra(str);
        }
        Intent intent2 = getIntent();
        String str2 = Constants.KEY_SCREEN_ID;
        if (intent2.hasExtra(str2)) {
            this.mScreenId = getIntent().getStringExtra(str2);
        }
        Intent intent3 = getIntent();
        String str3 = Constants.KEY_SCREEN_TYPE;
        if (intent3.hasExtra(str3)) {
            this.mScreenType = getIntent().getStringExtra(str3);
        }
        Intent intent4 = getIntent();
        String str4 = Constants.KEY_SUB_SCREEN_TYPE;
        if (intent4.hasExtra(str4)) {
            this.mSubScreenType = getIntent().getStringExtra(str4);
        }
        if (getIntent().hasExtra(Constants.KEY_IS_FROM_ACTIVITY)) {
            this.mIsFromActivity = true;
        }
        loadUi();
        this.mBillingProcessor = new BillingProcessor(this, InAppPurchaseHelper.getInAppPurchaseLicenseKey(), this);
        this.mBillingProcessor.initialize();
        this.mBillingProcessor.loadOwnedPurchasesFromGoogle();
        if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 21) {
            InAppPurchaseActivity.setWindowFlag(this, 67108864, true);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
        }
        if (VERSION.SDK_INT >= 21) {
            InAppPurchaseActivity.setWindowFlag(this, 67108864, false);
            getWindow().setStatusBarColor(0);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
    }

    @OnClick({2131361960})
    public void onBackButtonClick() {
        finish();
    }

    private String getYouTubeId(String str) {
        Matcher matcher = Pattern.compile("(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*").matcher(str);
        return matcher.find() ? matcher.group() : "error";
    }

    private void loadUi() {
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this, this.mProduct.productImages, this.mProduct.videoUrl != null ? getYouTubeId(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.videoUrl)) : null);
        this.viewPager.setAdapter(customPagerAdapter);
        if (this.mProduct.description != null) {
            this.description.setText(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.description));
        }
        if (this.mProduct.title != null) {
            this.title.setText(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.title));
        }
        if (this.mProduct.detailActionText != null) {
            this.detailActionButton.setText(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.detailActionText));
        }
        if (this.mProduct.detailActionUrl == null) {
            this.detailActionButton.setVisibility(8);
            LayoutParams layoutParams = (LayoutParams) this.buyActionButton.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            this.buyActionButton.setLayoutParams(layoutParams);
        }
        if (this.mProduct.buyActionText != null) {
            this.buyActionButton.setText(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.buyActionText));
        }
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < InAppPurchaseDetailActivity.this.dotsCount; i2++) {
                    InAppPurchaseDetailActivity.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(InAppPurchaseDetailActivity.this, R.drawable.in_app_purchase_nonselecteditem_dot));
                }
                Drawable drawable = ContextCompat.getDrawable(InAppPurchaseDetailActivity.this, R.drawable.selecteditem_dot);
                drawable.setColorFilter(new PorterDuffColorFilter(InAppPurchaseDetailActivity.this.mSharedPref.getActionBarColor(), Mode.MULTIPLY));
                InAppPurchaseDetailActivity.this.dots[i].setImageDrawable(drawable);
                if (InAppPurchaseDetailActivity.this.initializedYouTubePlayer != null) {
                    InAppPurchaseDetailActivity.this.initializedYouTubePlayer.pause();
                }
            }
        });
        this.viewPager.setOffscreenPageLimit(10);
        this.dotsCount = customPagerAdapter.getCount();
        this.dots = new ImageView[this.dotsCount];
        for (int i = 0; i < this.dotsCount; i++) {
            this.dots[i] = new ImageView(this);
            this.dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.in_app_purchase_nonselecteditem_dot));
            LayoutParams layoutParams2 = new LayoutParams(-2, -2);
            layoutParams2.setMargins(4, 0, 4, 0);
            this.pagerIndicator.addView(this.dots[i], layoutParams2);
        }
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.selecteditem_dot);
        drawable.setColorFilter(new PorterDuffColorFilter(this.mSharedPref.getActionBarColor(), Mode.MULTIPLY));
        this.dots[0].setImageDrawable(drawable);
    }

    @OnClick({2131362265})
    public void onClickDetailButton() {
        Builder builder = new Builder();
        builder.setToolbarColor(this.mSharedPref.getActionBarColor());
        builder.setShowTitle(true);
        builder.addDefaultShareMenuItem();
        builder.build().launchUrl(this, Uri.parse(this.mLocalizationHelper.getLocalizedTitle(this.mProduct.detailActionUrl)));
    }

    @OnClick({2131362100})
    public void onClickBuyButton() {
        if (this.isLifeTimeSelected && InAppPurchaseHelper.checkIabAvailable(this)) {
            this.mBillingProcessor.purchase(this, this.mProduct.oneTimeProductId);
        } else if (this.isSubscriptionSelected && InAppPurchaseHelper.checkIabAvailable(this)) {
            this.mBillingProcessor.subscribe(this, this.mProduct.subscriptionProductId[0]);
        }
    }

    @OnClick({2131363192})
    public void onClickSubscriptionButton() {
        this.isSubscriptionSelected = true;
        this.isLifeTimeSelected = false;
        setPackageBackground();
    }

    @OnClick({2131362840})
    public void onClickOneTimeButton() {
        this.isSubscriptionSelected = false;
        this.isLifeTimeSelected = true;
        setPackageBackground();
    }

    public void onProductPurchased(String str, TransactionDetails transactionDetails) {
        if (((this.mProduct.oneTimeProductId == null || !str.equalsIgnoreCase(this.mProduct.oneTimeProductId)) && (this.mProduct.subscriptionProductId.length == 0 || !str.equalsIgnoreCase(this.mProduct.subscriptionProductId[0]))) || !InAppPurchaseHelper.isScreenPurchased(this.mBillingProcessor, this.mScreenId)) {
            Toast.makeText(this, getString(R.string.common_error), 0).show();
        } else {
            verifyPurchase(transactionDetails.purchaseInfo.responseData, transactionDetails.purchaseInfo.signature);
        }
    }

    private void verifyPurchase(String str, String str2) {
        if (InAppPurchaseHelper.verifyTransaction(str, str2)) {
            onProductPurchased();
        } else {
            Toast.makeText(this, getString(R.string.common_error), 0).show();
        }
    }

    private void onProductPurchased() {
        if (this.mIsFromActivity) {
            ActivityHandler.startActivity(this, this.mScreenId, this.mScreenType, this.mSubScreenType, null, null);
        }
        finish();
        EventBus.getDefault().post(new InAppPurchaseSuccessEvent(this.mScreenId, null, this.mIsFromActivity));
    }

    public void onBillingInitialized() {
        if (this.mProduct.oneTimeProductId != null) {
            this.mLifeTimeSkuDetails = this.mBillingProcessor.getPurchaseListingDetails(this.mProduct.oneTimeProductId);
            if (this.mLifeTimeSkuDetails != null) {
                AppCompatTextView appCompatTextView = this.lifeTimePriceText;
                StringBuilder sb = new StringBuilder();
                sb.append(C3957Utils.getCurrencySymbol(this.mLifeTimeSkuDetails.currency));
                sb.append(this.mLifeTimeSkuDetails.priceValue);
                appCompatTextView.setText(sb.toString());
                this.isLifeTimeSelected = true;
                this.oneTimeLayout.setVisibility(0);
            } else {
                this.oneTimeLayout.setVisibility(8);
            }
        }
        if (!(this.mProduct.subscriptionProductId == null || this.mProduct.subscriptionProductId.length == 0)) {
            this.mSubscribeSkuDetails = this.mBillingProcessor.getSubscriptionListingDetails(this.mProduct.subscriptionProductId[0]);
            SkuDetails skuDetails = this.mSubscribeSkuDetails;
            if (skuDetails != null) {
                this.subscriptionPeriod.setText(InAppPurchaseHelper.getPeriodString(this, skuDetails.subscriptionPeriod));
                AppCompatTextView appCompatTextView2 = this.subscriptionPriceText;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(C3957Utils.getCurrencySymbol(this.mSubscribeSkuDetails.currency));
                sb2.append(this.mSubscribeSkuDetails.priceValue);
                appCompatTextView2.setText(sb2.toString());
                this.isSubscriptionSelected = true;
                this.subscriptionLayout.setVisibility(0);
            } else {
                this.subscriptionLayout.setVisibility(8);
            }
        }
        setPackageBackground();
    }

    private void setPackageBackground() {
        if (this.isSubscriptionSelected) {
            this.subscriptionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.in_app_purchase_plan_selected));
            this.oneTimeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.in_app_purchase_plan_unselected));
            return;
        }
        this.subscriptionButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.in_app_purchase_plan_unselected));
        this.oneTimeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.in_app_purchase_plan_selected));
    }

    public void onDestroy() {
        BillingProcessor billingProcessor = this.mBillingProcessor;
        if (billingProcessor != null) {
            billingProcessor.release();
        }
        super.onDestroy();
        YouTubePlayerView youTubePlayerView = this.youtubePlayerView;
        if (youTubePlayerView != null) {
            youTubePlayerView.release();
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (!this.mBillingProcessor.handleActivityResult(i, i2, intent)) {
            super.onActivityResult(i, i2, intent);
        }
    }
}
