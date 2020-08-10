package com.startapp.android.publish.ads.banner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.startapp.android.publish.adsCommon.C5040i;
import com.startapp.android.publish.adsCommon.C5069o;
import com.startapp.android.publish.adsCommon.Utils.C4945h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p077a.C4953a;
import com.startapp.android.publish.adsCommon.p077a.C4954b;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p077a.C4959g;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5159i;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.objectweb.asm.Opcodes;

/* compiled from: StartAppSDK */
public abstract class BannerBase extends RelativeLayout {
    private static final String TAG = "BannerLayout";
    protected AdPreferences adPreferences;
    protected C4958f adRulesResult;
    protected String adTag;
    private boolean attachedToWindow;
    private boolean clicked;
    protected boolean drawn;
    private String error;
    private boolean firstLoad;
    protected int innerBanner3dId;
    protected int innerBannerStandardId;
    protected int offset;
    private boolean shouldReloadBanner;
    private C4763a task;
    private Timer timer;
    private C5069o viewabilityChecker;
    /* access modifiers changed from: private */
    public Handler visibilityHandler;

    /* renamed from: com.startapp.android.publish.ads.banner.BannerBase$a */
    /* compiled from: StartAppSDK */
    class C4763a extends TimerTask {
        C4763a() {
        }

        public void run() {
            BannerBase.this.post(new Runnable() {
                public void run() {
                    if (BannerBase.this.isShown() || (BannerBase.this.adRulesResult != null && !BannerBase.this.adRulesResult.mo62064a())) {
                        C5155g.m3807a(BannerBase.TAG, 3, "REFRESH");
                        BannerBase.this.load();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public abstract int getBannerId();

    /* access modifiers changed from: protected */
    public abstract String getBannerName();

    /* access modifiers changed from: protected */
    public abstract int getHeightInDp();

    /* access modifiers changed from: protected */
    public abstract int getOffset();

    /* access modifiers changed from: protected */
    public abstract int getRefreshRate();

    /* access modifiers changed from: protected */
    public abstract int getWidthInDp();

    /* access modifiers changed from: protected */
    public abstract void initRuntime();

    /* access modifiers changed from: protected */
    public abstract void reload();

    public abstract void setAdTag(String str);

    /* access modifiers changed from: protected */
    public abstract void setBannerId(int i);

    public BannerBase(Context context) {
        super(context);
        this.attachedToWindow = false;
        this.offset = 0;
        this.firstLoad = true;
        this.drawn = false;
        this.innerBanner3dId = new Random().nextInt(100000) + 159868227;
        this.innerBannerStandardId = this.innerBanner3dId + 1;
        this.adTag = null;
        this.visibilityHandler = new Handler();
        this.viewabilityChecker = new C5069o();
        this.clicked = false;
        this.shouldReloadBanner = false;
        this.timer = new Timer();
        this.task = new C4763a();
    }

    public BannerBase(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BannerBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.attachedToWindow = false;
        this.offset = 0;
        this.firstLoad = true;
        this.drawn = false;
        this.innerBanner3dId = new Random().nextInt(100000) + 159868227;
        this.innerBannerStandardId = this.innerBanner3dId + 1;
        this.adTag = null;
        this.visibilityHandler = new Handler();
        this.viewabilityChecker = new C5069o();
        this.clicked = false;
        this.shouldReloadBanner = false;
        this.timer = new Timer();
        this.task = new C4763a();
        setBannerAttrs(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public void init() {
        if (!isInEditMode()) {
            initRuntime();
        } else {
            initDebug();
        }
    }

    private void initDebug() {
        setMinimumWidth(C4945h.m2891a(getContext(), getWidthInDp()));
        setMinimumHeight(C4945h.m2891a(getContext(), getHeightInDp()));
        setBackgroundColor(Color.rgb(Opcodes.RET, Opcodes.RET, Opcodes.RET));
        TextView textView = new TextView(getContext());
        textView.setText(getBannerName());
        textView.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(13);
        addView(textView, layoutParams);
    }

    /* access modifiers changed from: protected */
    public String getAdTag() {
        return this.adTag;
    }

    /* access modifiers changed from: protected */
    public void loadBanner() {
        scheduleReloadTask();
        load();
    }

    /* access modifiers changed from: protected */
    public void load() {
        clearVisibilityHandler();
        if (this.adRulesResult == null || C4959g.m2962a().mo62067b().mo62062a()) {
            this.adRulesResult = C4959g.m2962a().mo62067b().mo62061a(Placement.INAPP_BANNER, getAdTag());
            if (this.adRulesResult.mo62064a()) {
                reload();
                return;
            }
            setVisibility(4);
            if (Constants.m3707a().booleanValue()) {
                C5159i.m3829a().mo62876a(getContext(), this.adRulesResult.mo62065b());
            }
        } else if (this.adRulesResult.mo62064a()) {
            reload();
        }
    }

    /* access modifiers changed from: private */
    public void clearVisibilityHandler() {
        try {
            this.visibilityHandler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("BannerBase.reload - removeCallbacksAndMessages failed ");
            sb.append(e.getMessage());
            C5155g.m3807a(TAG, 6, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldSendImpression(C5040i iVar) {
        return iVar != null && !iVar.mo62348c();
    }

    /* access modifiers changed from: protected */
    public int getMinViewabilityPercentage() {
        return C4786c.m2356a().mo61369b().mo61289q();
    }

    /* access modifiers changed from: protected */
    public boolean isVisible() {
        return this.viewabilityChecker.mo62441a(this, getMinViewabilityPercentage());
    }

    /* access modifiers changed from: protected */
    public void startVisibilityRunnable(final C5040i iVar) {
        if (shouldSendImpression(iVar)) {
            C5155g.m3807a(TAG, 3, "BannerBase.startVisibilityRunnable - run visibility check");
            new Runnable() {

                /* renamed from: c */
                private boolean f2524c = true;

                public void run() {
                    try {
                        if (BannerBase.this.shouldSendImpression(iVar)) {
                            boolean isVisible = BannerBase.this.isVisible();
                            if (isVisible && this.f2524c) {
                                this.f2524c = false;
                                iVar.mo62344a();
                            } else if (!isVisible && !this.f2524c) {
                                this.f2524c = true;
                                iVar.mo62346b();
                            }
                            BannerBase.this.visibilityHandler.postDelayed(this, 100);
                            return;
                        }
                        BannerBase.this.clearVisibilityHandler();
                    } catch (Exception e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("BannerBase.startVisibilityRunnable.run - runnable error ");
                        sb.append(e.getMessage());
                        C5155g.m3807a(BannerBase.TAG, 6, sb.toString());
                        BannerBase.this.clearVisibilityHandler();
                    }
                }
            }.run();
        }
    }

    private void setBannerAttrs(Context context, AttributeSet attributeSet) {
        setAdTag(new C4766b(context, attributeSet).mo61298a());
    }

    private void scheduleReloadTask() {
        if (this.attachedToWindow && !isInEditMode()) {
            C4763a aVar = this.task;
            if (aVar != null) {
                aVar.cancel();
            }
            Timer timer2 = this.timer;
            if (timer2 != null) {
                timer2.cancel();
            }
            this.task = new C4763a();
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(this.task, (long) getRefreshRate(), (long) getRefreshRate());
        }
    }

    private void cancelReloadTask() {
        if (!isInEditMode()) {
            C4763a aVar = this.task;
            if (aVar != null) {
                aVar.cancel();
            }
            Timer timer2 = this.timer;
            if (timer2 != null) {
                timer2.cancel();
            }
            this.task = null;
            this.timer = null;
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        if (isClicked()) {
            setClicked(false);
            this.shouldReloadBanner = true;
        }
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putInt("bannerId", getBannerId());
        bundle.putParcelable("upperState", onSaveInstanceState);
        bundle.putSerializable("adRulesResult", this.adRulesResult);
        bundle.putSerializable("adPreferences", this.adPreferences);
        bundle.putInt("offset", this.offset);
        bundle.putBoolean("firstLoad", this.firstLoad);
        bundle.putBoolean("shouldReloadBanner", this.shouldReloadBanner);
        return bundle;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        setBannerId(bundle.getInt("bannerId"));
        this.adRulesResult = (C4958f) bundle.getSerializable("adRulesResult");
        this.adPreferences = (AdPreferences) bundle.getSerializable("adPreferences");
        this.offset = bundle.getInt("offset");
        this.firstLoad = bundle.getBoolean("firstLoad");
        this.shouldReloadBanner = bundle.getBoolean("shouldReloadBanner");
        super.onRestoreInstanceState(bundle.getParcelable("upperState"));
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        C5155g.m3807a(TAG, 3, "onAttachedToWindow");
        this.attachedToWindow = true;
        scheduleReloadTask();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C5155g.m3807a(TAG, 3, "onDetachedFromWindow");
        this.attachedToWindow = false;
        cancelReloadTask();
        clearVisibilityHandler();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        C5155g.m3807a(TAG, 3, "onWindowFocusChanged");
        if (z) {
            if (this.shouldReloadBanner) {
                this.shouldReloadBanner = false;
                load();
            }
            this.attachedToWindow = true;
            scheduleReloadTask();
            return;
        }
        this.attachedToWindow = false;
        cancelReloadTask();
    }

    public boolean isFirstLoad() {
        return this.firstLoad;
    }

    public void setFirstLoad(boolean z) {
        this.firstLoad = z;
    }

    /* access modifiers changed from: protected */
    public void addDisplayEventOnLoad() {
        if (isFirstLoad() || C4959g.m2962a().mo62067b().mo62062a()) {
            setFirstLoad(false);
            C4954b.m2946a().mo62055a(new C4953a(Placement.INAPP_BANNER, getAdTag()));
        }
    }

    /* access modifiers changed from: protected */
    public void setHardwareAcceleration(AdPreferences adPreferences2) {
        C4946i.m2919a(adPreferences2, "hardwareAccelerated", C5146c.m3763a((View) this, this.attachedToWindow));
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setClicked(boolean z) {
        this.clicked = z;
    }

    public void setErrorMessage(String str) {
        this.error = str;
    }

    public String getErrorMessage() {
        return this.error;
    }
}
