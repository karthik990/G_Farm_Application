package com.mobiroller.activities.inapppurchase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mobiroller.adapters.InAppPurchasePagerAdapter;
import com.mobiroller.constants.Constants;
import com.mobiroller.helpers.InAppPurchaseHelper;
import com.mobiroller.helpers.ScreenHelper;
import com.mobiroller.helpers.SharedPrefHelper;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.mobi942763453128.R;
import com.mobiroller.models.events.InAppPurchaseSuccessEvent;
import com.mobiroller.util.RssUtil;
import com.mobiroller.views.WrapContentViewPager;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class InAppPurchaseActivity extends AppCompatActivity {
    @BindView(2131362167)
    ImageView closeButton;
    /* access modifiers changed from: private */
    public ImageView[] dots;
    /* access modifiers changed from: private */
    public int dotsCount;
    @BindView(2131362531)
    ImageView image;
    @BindView(2131362538)
    ImageView imageFilterOne;
    @BindView(2131362539)
    ImageView imageFilterTwo;
    private boolean mIsFromActivity;
    private String mScreenId;
    private String mScreenType;
    @BindView(2131363382)
    WrapContentViewPager viewPager;
    @BindView(2131363383)
    LinearLayout viewPagerCountDots;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_in_app_purchase);
        ButterKnife.bind((Activity) this);
        final SharedPrefHelper sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.image.setColorFilter(sharedPrefHelper.getActionBarColor());
        this.imageFilterOne.setColorFilter(ScreenHelper.getLighterColor(sharedPrefHelper.getActionBarColor(), 0.5f));
        this.imageFilterTwo.setColorFilter(ScreenHelper.getLighterColor(sharedPrefHelper.getActionBarColor(), 0.8f));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Intent intent = getIntent();
        String str = Constants.KEY_SCREEN_ID;
        if (intent.hasExtra(str)) {
            this.mScreenId = getIntent().getStringExtra(str);
        }
        Intent intent2 = getIntent();
        String str2 = Constants.KEY_SCREEN_TYPE;
        if (intent2.hasExtra(str2)) {
            this.mScreenType = getIntent().getStringExtra(str2);
        }
        Intent intent3 = getIntent();
        String str3 = Constants.KEY_IS_FROM_ACTIVITY;
        if (intent3.hasExtra(str3)) {
            this.mIsFromActivity = getIntent().getBooleanExtra(str3, false);
        }
        InAppPurchasePagerAdapter inAppPurchasePagerAdapter = new InAppPurchasePagerAdapter(this, InAppPurchaseHelper.getScreenProductList(this.mScreenId), this.mScreenId, this.mScreenType, this.mIsFromActivity);
        this.viewPager.setAdapter(inAppPurchasePagerAdapter);
        this.viewPager.setPageMargin(RssUtil.convertDpToPixel(25.0f, this));
        this.viewPager.setPageTransformer(true, new PageTransformer() {
            public void transformPage(View view, float f) {
                int measuredWidth = InAppPurchaseActivity.this.viewPager.getMeasuredWidth();
                int height = InAppPurchaseActivity.this.viewPager.getHeight();
                float left = ((float) (view.getLeft() - (InAppPurchaseActivity.this.viewPager.getScrollX() + InAppPurchaseActivity.this.viewPager.getPaddingLeft()))) / ((float) measuredWidth);
                int i = (-height) / 20;
                if (left < -1.0f) {
                    view.setTranslationY(0.0f);
                } else if (left <= 1.0f) {
                    view.setTranslationY(((float) i) * (1.0f - Math.abs(left)));
                } else {
                    view.setTranslationY(0.0f);
                }
            }
        });
        this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < InAppPurchaseActivity.this.dotsCount; i2++) {
                    InAppPurchaseActivity.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(InAppPurchaseActivity.this, R.drawable.nonselecteditem_dot));
                }
                Drawable drawable = ContextCompat.getDrawable(InAppPurchaseActivity.this, R.drawable.selecteditem_dot);
                drawable.setColorFilter(new PorterDuffColorFilter(sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
                InAppPurchaseActivity.this.dots[i].setImageDrawable(drawable);
                InAppPurchaseActivity.this.viewPager.reMeasureCurrentPage(InAppPurchaseActivity.this.viewPager.getCurrentItem());
            }
        });
        this.dotsCount = inAppPurchasePagerAdapter.getCount();
        this.dots = new ImageView[this.dotsCount];
        for (int i = 0; i < this.dotsCount; i++) {
            this.dots[i] = new ImageView(this);
            this.dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.setMargins(4, 0, 4, 0);
            this.viewPagerCountDots.addView(this.dots[i], layoutParams);
        }
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.selecteditem_dot);
        drawable.setColorFilter(new PorterDuffColorFilter(sharedPrefHelper.getActionBarColor(), Mode.MULTIPLY));
        this.dots[0].setImageDrawable(drawable);
        if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 21) {
            setWindowFlag(this, 67108864, true);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(1280);
        }
        if (VERSION.SDK_INT >= 21) {
            setWindowFlag(this, 67108864, false);
            getWindow().setStatusBarColor(0);
        }
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
        }
    }

    @OnClick({2131362167})
    public void onClickCloseButton() {
        finish();
    }

    public static void setWindowFlag(Activity activity, int i, boolean z) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (z) {
            attributes.flags = i | attributes.flags;
        } else {
            attributes.flags = (i ^ -1) & attributes.flags;
        }
        window.setAttributes(attributes);
    }

    @Subscribe
    public void onPostInAppPurchaseSuccessEvent(InAppPurchaseSuccessEvent inAppPurchaseSuccessEvent) {
        finish();
    }
}
