package com.mobiroller.helpers;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.recyclerview.widget.RecyclerView;
import com.mobiroller.MobiRollerApplication;
import com.startapp.android.publish.ads.banner.Banner;

public class BannerHelper {
    private Context context;
    private NetworkHelper networkHelper;
    private SharedPrefHelper sharedPrefHelper;

    public BannerHelper(SharedPrefHelper sharedPrefHelper2, Context context2, NetworkHelper networkHelper2) {
        this.sharedPrefHelper = sharedPrefHelper2;
        this.networkHelper = networkHelper2;
        this.context = context2;
        this.sharedPrefHelper.setBannerAd(context2);
    }

    public BannerHelper() {
        this.sharedPrefHelper = UtilManager.sharedPrefHelper();
        this.networkHelper = UtilManager.networkHelper();
        this.context = MobiRollerApplication.app;
        this.sharedPrefHelper.setBannerAd(this.context);
    }

    public void addBannerAd(RelativeLayout relativeLayout, View view) {
        if (this.networkHelper.isConnected() && this.sharedPrefHelper.isBannerActive()) {
            addSpaceForBanner(view);
            if (this.sharedPrefHelper.getIsAdmobBannerAdEnabled()) {
                addAdmobBanner(relativeLayout, view);
            } else {
                addStartAppBanner(relativeLayout);
            }
        }
    }

    public int getBannerHeight() {
        if (this.networkHelper.isConnected() && this.sharedPrefHelper.isBannerActive() && this.sharedPrefHelper.getBannerAd() != null) {
            this.sharedPrefHelper.getBannerAd().getAdSize().getHeight();
        }
        return 0;
    }

    public void addBannerTop(RelativeLayout relativeLayout) {
        if (this.networkHelper.isConnected() && this.sharedPrefHelper.isBannerActive()) {
            if (this.sharedPrefHelper.getIsAdmobBannerAdEnabled()) {
                addAdmobBannerTop(relativeLayout);
            } else {
                addStartAppBannerTop(relativeLayout);
            }
        }
    }

    private void addAdmobBannerTop(RelativeLayout relativeLayout) {
        if (this.sharedPrefHelper.getBannerAd() == null) {
            this.sharedPrefHelper.setBannerAd(this.context, null, relativeLayout);
        } else if (!this.sharedPrefHelper.getBannerAdUnitID().isEmpty() && this.sharedPrefHelper.getBannerAd() != null) {
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.addRule(10);
            if (this.sharedPrefHelper.getBannerAd().getParent() != null) {
                ((ViewGroup) this.sharedPrefHelper.getBannerAd().getParent()).removeView(this.sharedPrefHelper.getBannerAd());
            }
            relativeLayout.addView(this.sharedPrefHelper.getBannerAd(), layoutParams);
        }
    }

    private void addStartAppBannerTop(RelativeLayout relativeLayout) {
        Banner banner = new Banner(this.context);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(10);
        relativeLayout.addView(banner, layoutParams);
    }

    private void addStartAppBanner(RelativeLayout relativeLayout) {
        Banner banner = new Banner(this.context);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(14);
        layoutParams.addRule(12);
        relativeLayout.addView(banner, layoutParams);
    }

    private void addAdmobBanner(RelativeLayout relativeLayout, View view) {
        if (this.sharedPrefHelper.getBannerAd() == null) {
            this.sharedPrefHelper.setBannerAd(this.context, view, relativeLayout);
        } else if (!this.sharedPrefHelper.getBannerAdUnitID().isEmpty() && this.sharedPrefHelper.getBannerAd() != null) {
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.addRule(12);
            if (this.sharedPrefHelper.getBannerAd().getParent() != null) {
                ((ViewGroup) this.sharedPrefHelper.getBannerAd().getParent()).removeView(this.sharedPrefHelper.getBannerAd());
            }
            relativeLayout.addView(this.sharedPrefHelper.getBannerAd(), layoutParams);
        }
    }

    private void addSpaceForBanner(View view) {
        float f;
        DisplayMetrics displayMetrics = this.context.getResources().getDisplayMetrics();
        float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.density;
        if (f2 <= 400.0f) {
            f = TypedValue.applyDimension(1, 32.0f, this.context.getResources().getDisplayMetrics());
        } else if (f2 <= 720.0f) {
            f = TypedValue.applyDimension(1, 50.0f, this.context.getResources().getDisplayMetrics());
        } else {
            f = TypedValue.applyDimension(1, 90.0f, this.context.getResources().getDisplayMetrics());
        }
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, (int) f);
            view.setLayoutParams(layoutParams);
        } else if (view instanceof RecyclerView) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(0, 0, 0, (int) f);
            view.setLayoutParams(marginLayoutParams);
        } else if (view.getLayoutParams() instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.setMargins(0, 0, 0, (int) f);
            view.setLayoutParams(layoutParams2);
        } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams3.setMargins(0, 0, 0, (int) f);
            view.setLayoutParams(layoutParams3);
        }
    }
}
