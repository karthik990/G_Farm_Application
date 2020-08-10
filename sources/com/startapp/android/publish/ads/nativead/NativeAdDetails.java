package com.startapp.android.publish.ads.nativead;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd.C4819b;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5134a;
import com.startapp.common.C5134a.C5137a;
import com.startapp.common.p092a.C5155g;

/* compiled from: StartAppSDK */
public class NativeAdDetails implements NativeAdInterface {

    /* renamed from: a */
    int f2666a;

    /* renamed from: b */
    C4817a f2667b;

    /* renamed from: c */
    private AdDetails f2668c;

    /* renamed from: d */
    private Bitmap f2669d;

    /* renamed from: e */
    private Bitmap f2670e;

    /* renamed from: f */
    private boolean f2671f = false;

    /* renamed from: g */
    private String f2672g;

    /* renamed from: com.startapp.android.publish.ads.nativead.NativeAdDetails$3 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C48163 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2676a = new int[C4819b.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.startapp.android.publish.ads.nativead.StartAppNativeAd$b[] r0 = com.startapp.android.publish.ads.nativead.StartAppNativeAd.C4819b.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2676a = r0
                int[] r0 = f2676a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.ads.nativead.StartAppNativeAd$b r1 = com.startapp.android.publish.ads.nativead.StartAppNativeAd.C4819b.OPEN_MARKET     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2676a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.ads.nativead.StartAppNativeAd$b r1 = com.startapp.android.publish.ads.nativead.StartAppNativeAd.C4819b.LAUNCH_APP     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.nativead.NativeAdDetails.C48163.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.ads.nativead.NativeAdDetails$a */
    /* compiled from: StartAppSDK */
    protected interface C4817a {
        void onNativeAdDetailsLoaded(int i);
    }

    public NativeAdDetails(AdDetails adDetails, NativeAdPreferences nativeAdPreferences, int i, C4817a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("Initializiang SingleAd [");
        sb.append(i);
        sb.append("]");
        C5155g.m3807a("StartAppNativeAd", 3, sb.toString());
        this.f2668c = adDetails;
        this.f2666a = i;
        this.f2667b = aVar;
        if (nativeAdPreferences.isAutoBitmapDownload()) {
            new C5134a(getImageUrl(), new C5137a() {
                /* renamed from: a */
                public void mo61329a(Bitmap bitmap, int i) {
                    NativeAdDetails.this.mo61496a(bitmap);
                    new C5134a(NativeAdDetails.this.getSecondaryImageUrl(), new C5137a() {
                        /* renamed from: a */
                        public void mo61329a(Bitmap bitmap, int i) {
                            NativeAdDetails.this.mo61499b(bitmap);
                            NativeAdDetails.this.mo61495a();
                        }
                    }, i).mo62837a();
                }
            }, i).mo62837a();
        } else {
            mo61495a();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("         Title: [");
        sb.append(getTitle());
        String str = "]\n";
        sb.append(str);
        sb.append("         Description: [");
        sb.append(getDescription().substring(0, 30));
        sb.append("]...\n");
        sb.append("         Rating: [");
        sb.append(getRating());
        sb.append(str);
        sb.append("         Installs: [");
        sb.append(getInstalls());
        sb.append(str);
        sb.append("         Category: [");
        sb.append(getCategory());
        sb.append(str);
        sb.append("         PackageName: [");
        sb.append(getPackacgeName());
        sb.append(str);
        sb.append("         CampaginAction: [");
        sb.append(getCampaignAction());
        sb.append(str);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo61496a(Bitmap bitmap) {
        this.f2669d = bitmap;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo61499b(Bitmap bitmap) {
        this.f2670e = bitmap;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo61495a() {
        new Handler().post(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("SingleAd [");
                sb.append(NativeAdDetails.this.f2666a);
                sb.append("] Loaded");
                C5155g.m3807a("StartAppNativeAd", 3, sb.toString());
                if (NativeAdDetails.this.f2667b != null) {
                    NativeAdDetails.this.f2667b.onNativeAdDetailsLoaded(NativeAdDetails.this.f2666a);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61497a(String str) {
        this.f2672g = str;
    }

    public String getTitle() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getTitle() : "";
    }

    public String getDescription() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getDescription() : "";
    }

    public float getRating() {
        AdDetails adDetails = this.f2668c;
        if (adDetails != null) {
            return adDetails.getRating();
        }
        return 5.0f;
    }

    public String getImageUrl() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getImageUrl() : "";
    }

    public String getSecondaryImageUrl() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getSecondaryImageUrl() : "";
    }

    public Bitmap getImageBitmap() {
        return this.f2669d;
    }

    public Bitmap getSecondaryImageBitmap() {
        return this.f2670e;
    }

    public String getInstalls() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getInstalls() : "";
    }

    public String getCategory() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getCategory() : "";
    }

    public String getPackacgeName() {
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? adDetails.getPackageName() : "";
    }

    public C4819b getCampaignAction() {
        C4819b bVar = C4819b.OPEN_MARKET;
        AdDetails adDetails = this.f2668c;
        return (adDetails == null || !adDetails.isCPE()) ? bVar : C4819b.LAUNCH_APP;
    }

    public Boolean isApp() {
        Boolean valueOf = Boolean.valueOf(true);
        AdDetails adDetails = this.f2668c;
        return adDetails != null ? Boolean.valueOf(adDetails.isApp()) : valueOf;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public AdDetails mo61498b() {
        return this.f2668c;
    }

    public void sendClick(Context context) {
        if (this.f2668c != null) {
            int i = C48163.f2676a[getCampaignAction().ordinal()];
            if (i == 1) {
                boolean a = C4988c.m3118a(context, Placement.INAPP_NATIVE);
                if (!this.f2668c.isSmartRedirect() || a) {
                    C4988c.m3105a(context, this.f2668c.getClickUrl(), this.f2668c.getTrackingClickUrl(), new C5002b(this.f2672g), this.f2668c.isStartappBrowserEnabled() && !a, false);
                } else {
                    C4988c.m3107a(context, this.f2668c.getClickUrl(), this.f2668c.getTrackingClickUrl(), this.f2668c.getPackageName(), new C5002b(this.f2672g), C4983b.m3032a().mo62147A(), C4983b.m3032a().mo62148B(), this.f2668c.isStartappBrowserEnabled(), this.f2668c.shouldSendRedirectHops(), false);
                }
            } else if (i == 2) {
                C4988c.m3114a(getPackacgeName(), this.f2668c.getIntentDetails(), this.f2668c.getClickUrl(), context, new C5002b(this.f2672g));
            }
        }
    }

    public void sendImpression(Context context) {
        String str = "]";
        String str2 = "StartAppNativeAd";
        if (!this.f2671f) {
            this.f2671f = true;
            if (this.f2668c != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Sending Impression for [");
                sb.append(this.f2666a);
                sb.append(str);
                C5155g.m3807a(str2, 3, sb.toString());
                C4988c.m3101a(context, this.f2668c.getTrackingUrl(), new C5002b(this.f2672g));
            }
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Already sent impression for [");
            sb2.append(this.f2666a);
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
        }
    }
}
