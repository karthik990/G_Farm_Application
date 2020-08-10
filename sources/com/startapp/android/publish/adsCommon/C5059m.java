package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import androidx.browser.customtabs.CustomTabsService;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.startapp.android.publish.ads.banner.C4786c;
import com.startapp.android.publish.ads.p067b.C4761e;
import com.startapp.android.publish.ads.splash.C4834f;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType;
import com.startapp.android.publish.adsCommon.Utils.C4936b;
import com.startapp.android.publish.adsCommon.Utils.C4944g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.activities.FullScreenActivity;
import com.startapp.android.publish.adsCommon.adinformation.C4968a;
import com.startapp.android.publish.adsCommon.p077a.C4953a;
import com.startapp.android.publish.adsCommon.p077a.C4954b;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p081e.C5004a;
import com.startapp.android.publish.adsCommon.p082f.C5013c;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.cache.C5080c;
import com.startapp.android.publish.cache.C5081d;
import com.startapp.android.publish.common.metaData.C5116d;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5177c;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5152e;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5159i;
import com.startapp.common.p095d.C5184a;
import com.truenet.android.TrueNetSDK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.m */
/* compiled from: StartAppSDK */
public class C5059m {
    /* access modifiers changed from: private */

    /* renamed from: A */
    public ServiceConnection f3326A;

    /* renamed from: a */
    private SDKAdPreferences f3327a;

    /* renamed from: b */
    private boolean f3328b;

    /* renamed from: c */
    private boolean f3329c;

    /* renamed from: d */
    private boolean f3330d;

    /* renamed from: e */
    private boolean f3331e;

    /* renamed from: f */
    private boolean f3332f;

    /* renamed from: g */
    private long f3333g;

    /* renamed from: h */
    private Application f3334h;

    /* renamed from: i */
    private HashMap<Integer, Integer> f3335i;

    /* renamed from: j */
    private Object f3336j;

    /* renamed from: k */
    private Activity f3337k;

    /* renamed from: l */
    private boolean f3338l;

    /* renamed from: m */
    private String f3339m;

    /* renamed from: n */
    private boolean f3340n;

    /* renamed from: o */
    private boolean f3341o;

    /* renamed from: p */
    private boolean f3342p;

    /* renamed from: q */
    private boolean f3343q;

    /* renamed from: r */
    private Map<String, String> f3344r;

    /* renamed from: s */
    private Bundle f3345s;

    /* renamed from: t */
    private C5080c f3346t;

    /* renamed from: u */
    private boolean f3347u;

    /* renamed from: v */
    private boolean f3348v;

    /* renamed from: w */
    private boolean f3349w;

    /* renamed from: x */
    private boolean f3350x;

    /* renamed from: y */
    private C5021g f3351y;
    /* access modifiers changed from: private */

    /* renamed from: z */
    public boolean f3352z;

    /* renamed from: com.startapp.android.publish.adsCommon.m$a */
    /* compiled from: StartAppSDK */
    private static class C5066a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C5059m f3362a = new C5059m();
    }

    /* renamed from: b */
    public void mo62381b(Activity activity, Bundle bundle) {
    }

    private C5059m() {
        this.f3328b = C4946i.m2923a(512);
        this.f3330d = false;
        this.f3331e = false;
        this.f3332f = false;
        this.f3334h = null;
        this.f3335i = new HashMap<>();
        this.f3338l = false;
        this.f3340n = false;
        this.f3341o = true;
        this.f3342p = false;
        this.f3343q = false;
        this.f3345s = null;
        this.f3346t = null;
        this.f3348v = false;
        this.f3349w = false;
        this.f3350x = false;
        this.f3351y = null;
        this.f3352z = false;
    }

    /* renamed from: a */
    public static C5059m m3378a() {
        return C5066a.f3362a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62376a(Context context, String str, String str2, SDKAdPreferences sDKAdPreferences, boolean z) {
        String str3 = "shared_prefs_first_init";
        String str4 = "]";
        String str5 = "";
        try {
            String str6 = "StartAppSDKInternal";
            if (C4946i.m2923a(2)) {
                if (!C4946i.m2925a(context, FullScreenActivity.class)) {
                    Log.w(str6, "FullScreenActivity is missing from AndroidManifest.xml");
                }
            }
            if (context instanceof Activity) {
                this.f3339m = context.getClass().getName();
            }
            context = context.getApplicationContext();
            try {
                C5004a.m3202a(context);
            } catch (Exception e) {
                C5017f.m3256a(context, C5015d.EXCEPTION, "init AdsExceptionHandler", e.getMessage(), str5);
            }
            mo62383b(!C4946i.m2937f(context));
            m3407q(context);
            C5053l.m3359a(context);
            if (!this.f3338l) {
                C5177c.m3890c(context);
                C4936b.m2865a(context);
                m3400k(context);
                this.f3338l = true;
                StringBuilder sb = new StringBuilder();
                sb.append("Initialize StartAppSDK with DevID:[");
                sb.append(str);
                sb.append("], AppID:[");
                sb.append(str2);
                sb.append(str4);
                C5155g.m3807a(str6, 3, sb.toString());
                C4946i.m2915a(context, str, str2);
                this.f3327a = sDKAdPreferences;
                C5152e.m3799b(context, "shared_prefs_sdk_ad_prefs", (Serializable) sDKAdPreferences);
                C5184a.m3922b(context);
                boolean booleanValue = C5051k.m3335a(context, str3, Boolean.valueOf(true)).booleanValue();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("First Initialization: [");
                sb2.append(booleanValue);
                sb2.append(str4);
                C5155g.m3807a(str6, 3, sb2.toString());
                if (booleanValue) {
                    m3403n(context);
                    C5051k.m3344b(context, "totalSessions", Integer.valueOf(0));
                    C5051k.m3345b(context, "firstSessionTime", Long.valueOf(System.currentTimeMillis()));
                    C5051k.m3342b(context, str3, Boolean.valueOf(false));
                }
                mo62373a(context, C5109a.LAUNCH);
                m3401l(context);
                m3383a(context, z);
                if (this.f3328b) {
                    m3402m(context);
                }
                m3409r(context);
            }
            m3399j(context);
            mo62397f(context);
        } catch (Exception e2) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "StartAppSDKInternal.intialize - unexpected error occurd", e2.getMessage(), str5);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo62372a(Context context) {
        C5051k.m3342b(context, "periodicInfoEventPaused", Boolean.valueOf(true));
        C4936b.m2864a(786564404);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo62382b(Context context) {
        C5051k.m3342b(context, "periodicMetadataPaused", Boolean.valueOf(true));
        C4936b.m2864a(586482792);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public void mo62386c(Context context) {
        C5051k.m3342b(context, "periodicInfoEventPaused", Boolean.valueOf(false));
        C4936b.m2867a(context, C5051k.m3338a(context, "periodicInfoEventTriggerTime", Long.valueOf(C4936b.m2869b(context))).longValue());
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: d */
    public void mo62390d(Context context) {
        C5051k.m3342b(context, "periodicMetadataPaused", Boolean.valueOf(false));
        C4936b.m2868a(context, Long.valueOf(C5051k.m3338a(context, "periodicMetadataTriggerTime", Long.valueOf(C4936b.m2863a())).longValue()));
    }

    /* renamed from: j */
    private void m3399j(final Context context) {
        Boolean valueOf = Boolean.valueOf(false);
        C5051k.m3342b(context, "periodicInfoEventPaused", valueOf);
        C5051k.m3342b(context, "periodicMetadataPaused", valueOf);
        C50601 r0 = new C5116d() {
            /* renamed from: a */
            public void mo61600a() {
                if (MetaData.getInstance().isUserAgentEnabled()) {
                    C5059m.this.m3381a(context, TimeUnit.SECONDS.toMillis(MetaData.getInstance().getUserAgentDelayInSeconds()));
                }
                C4936b.m2870c(context);
                C4936b.m2871d(context);
                C5059m.this.mo62394e(context);
                if (VERSION.SDK_INT <= 8) {
                    return;
                }
                if (MetaData.getInstance().getTrueNetEnabled()) {
                    if (!C5059m.this.f3352z) {
                        C5059m.this.f3352z = true;
                        Context context = context;
                        TrueNetSDK.with(context, C5051k.m3339a(context, "shared_prefs_appId", (String) null));
                    }
                    TrueNetSDK.enable(context, true);
                } else if (C5059m.this.f3352z) {
                    TrueNetSDK.enable(context, false);
                }
            }

            /* renamed from: b */
            public void mo61601b() {
                C5155g.m3807a("StartAppSDKInternal", 3, "setPeriodicAlarms: onFailedLoadingMeta");
                if (MetaData.getInstance().isUserAgentEnabled()) {
                    C5059m.this.m3381a(context, TimeUnit.SECONDS.toMillis(10));
                }
            }
        };
        if (MetaData.getInstance().isReady()) {
            r0.mo61600a();
        } else {
            MetaData.getInstance().addMetaDataListener(r0);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public void mo62394e(Context context) {
        C5013c cVar = new C5013c(context, false);
        cVar.mo62281c().mo62276c(AdsConstants.f3026j);
        cVar.mo62279a();
        if (mo62396e()) {
            C5017f.m3256a(context, C5015d.GENERAL, "packagingType", AdsConstants.f3026j, "");
        }
    }

    /* renamed from: k */
    private void m3400k(Context context) {
        MetaData.init(context);
        if (!C4946i.m2922a()) {
            C4983b.m3033a(context);
            if (C4946i.m2923a(16) || C4946i.m2923a(32)) {
                C4786c.m2357a(context);
            }
            if (C4946i.m2923a(8)) {
                C4834f.m2517a(context);
            }
            if (this.f3328b) {
                C5081d.m3533a(context);
            }
            if (C4946i.m2935e()) {
                C4968a.m2983a(context);
            }
        }
    }

    /* renamed from: l */
    private void m3401l(Context context) {
        String str = "shared_prefs_app_version_id";
        Integer a = C5051k.m3337a(context, str, Integer.valueOf(-1));
        int d = C5146c.m3772d(context);
        if (a.intValue() > 0 && d > a.intValue()) {
            this.f3343q = true;
        }
        C5051k.m3344b(context, str, Integer.valueOf(d));
    }

    /* renamed from: m */
    private void m3402m(Context context) {
        if (this.f3343q || !C5081d.m3532a().mo62488b().isLocalCache()) {
            C5155g.m3807a("StartAppSDKInternal", 3, "App version changed or disabled in meta - deleting existing cache");
            C5071a.m3485a().mo62468b(context);
        } else if (this.f3329c) {
            C5071a.m3485a().mo62462a(context);
        }
        m3405p(context);
        C5071a.m3485a().mo62472c(context);
    }

    /* renamed from: a */
    private void m3383a(Context context, boolean z) {
        m3397g(false);
        m3395f(false);
        String str = "StartAppSDKInternal";
        if (!C5146c.m3769b() || !C4946i.m2923a(2)) {
            C5155g.m3807a(str, 6, "Cannot activate return interstitials, cache to disk, ttl reload - api lower than 14");
            return;
        }
        m3397g(z);
        m3395f(true);
        StringBuilder sb = new StringBuilder();
        sb.append("Return Ads: [");
        sb.append(z);
        sb.append("]");
        C5155g.m3807a(str, 3, sb.toString());
    }

    /* renamed from: n */
    private void m3403n(final Context context) {
        C5155g.m3807a("StartAppSDKInternal", 3, "Sending Download Event");
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                try {
                    C5046j jVar = new C5046j(context);
                    AdPreferences adPreferences = new AdPreferences();
                    C4946i.m2913a(context, adPreferences);
                    jVar.fillApplicationDetails(context, adPreferences);
                    C5052a.m3348a(context, AdsConstants.m2856a(ServiceApiType.DOWNLOAD), jVar, null);
                } catch (Exception e) {
                    C5155g.m3806a(6, "Error occured while sending download event", (Throwable) e);
                    C5017f.m3256a(context, C5015d.EXCEPTION, "StartAppSDKInternal.sendDownloadEvent", e.getMessage(), "");
                }
            }
        });
    }

    /* renamed from: f */
    public void mo62397f(Context context) {
        String str = "StartAppSDKInternal";
        if (C5146c.m3769b()) {
            if (context instanceof Activity) {
                this.f3337k = (Activity) context;
                this.f3334h = this.f3337k.getApplication();
            } else if (context.getApplicationContext() instanceof Application) {
                this.f3334h = (Application) context.getApplicationContext();
            } else {
                C5155g.m3807a(str, 6, "Cannot register activity life cycle callbacks - context is not an Activity");
                return;
            }
            try {
                if (!(this.f3336j == null || this.f3334h == null)) {
                    m3380a(this.f3334h, this.f3336j);
                    C5155g.m3807a(str, 3, "Unregistered LifeCycle Callbacks");
                }
            } catch (Exception unused) {
            }
            C5155g.m3807a(str, 3, "Registring LifeCycle Callbacks");
            this.f3336j = m3379a(this.f3334h);
        } else {
            C5155g.m3807a(str, 6, "Cannot register activity life cycle callbacks - api lower than 14");
        }
    }

    /* renamed from: a */
    public void mo62371a(Activity activity, Bundle bundle) {
        if (bundle == null && this.f3339m != null && activity.getClass().getName().equals(this.f3339m)) {
            this.f3338l = false;
        }
        this.f3345s = bundle;
    }

    /* renamed from: a */
    public void mo62370a(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append("onActivityStarted [");
        sb.append(activity.getClass().getName());
        String str = "]";
        sb.append(str);
        String str2 = "StartAppSDKInternal";
        C5155g.m3807a(str2, 3, sb.toString());
        if (C4946i.m2923a(8) && !C4983b.m3032a().mo62186z() && !this.f3349w && !mo62384b("MoPub") && !mo62384b("AdMob") && !this.f3350x && activity.getClass().getName().equals(this.f3339m) && !mo62403i() && this.f3335i.size() == 0) {
            StartAppAd.showSplash(activity, this.f3345s, new SplashConfig(), new AdPreferences(), null, false);
        }
        this.f3350x = true;
        if (this.f3330d) {
            m3396g(activity);
        }
        this.f3332f = false;
        this.f3330d = false;
        if (((Integer) this.f3335i.get(Integer.valueOf(activity.hashCode()))) == null) {
            this.f3335i.put(Integer.valueOf(activity.hashCode()), Integer.valueOf(new Integer(0).intValue() + 1));
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Activity Added:[");
            sb2.append(activity.getClass().getName());
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Activity [");
        sb3.append(activity.getClass().getName());
        sb3.append("] already exists");
        C5155g.m3807a(str2, 3, sb3.toString());
    }

    /* renamed from: b */
    public void mo62380b(Activity activity) {
        if (this.f3328b && this.f3331e) {
            this.f3331e = false;
            C5071a.m3485a().mo62467b();
        }
        if (this.f3340n) {
            this.f3340n = false;
            C5053l.m3367c(activity.getApplicationContext());
        }
        this.f3337k = activity;
    }

    /* renamed from: b */
    public void mo62379b() {
        this.f3340n = true;
        this.f3331e = true;
    }

    /* renamed from: c */
    public boolean mo62388c() {
        return this.f3342p;
    }

    /* renamed from: a */
    public void mo62377a(boolean z) {
        this.f3342p = z;
    }

    /* renamed from: d */
    public boolean mo62392d() {
        return this.f3341o;
    }

    /* renamed from: b */
    public void mo62383b(boolean z) {
        this.f3341o = z;
    }

    /* renamed from: e */
    public boolean mo62396e() {
        return this.f3343q;
    }

    /* renamed from: c */
    public void mo62385c(Activity activity) {
        this.f3333g = System.currentTimeMillis();
        this.f3337k = null;
    }

    /* renamed from: d */
    public void mo62389d(Activity activity) {
        StringBuilder sb = new StringBuilder();
        sb.append("onActivityStopped [");
        sb.append(activity.getClass().getName());
        String str = "]";
        sb.append(str);
        String str2 = "StartAppSDKInternal";
        C5155g.m3807a(str2, 3, sb.toString());
        Integer num = (Integer) this.f3335i.get(Integer.valueOf(activity.hashCode()));
        if (num != null) {
            Integer valueOf = Integer.valueOf(num.intValue() - 1);
            if (valueOf.intValue() == 0) {
                this.f3335i.remove(Integer.valueOf(activity.hashCode()));
            } else {
                this.f3335i.put(Integer.valueOf(activity.hashCode()), valueOf);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Activity removed:[");
            sb2.append(activity.getClass().getName());
            sb2.append(str);
            C5155g.m3807a(str2, 3, sb2.toString());
            if (this.f3335i.size() == 0) {
                if (!this.f3332f) {
                    m3394f(activity);
                }
                if (this.f3328b) {
                    C5071a.m3485a().mo62463a(activity.getApplicationContext(), this.f3332f);
                    this.f3331e = true;
                    return;
                }
                return;
            }
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Activity hadn't been found:[");
        sb3.append(activity.getClass().getName());
        sb3.append(str);
        C5155g.m3807a(str2, 3, sb3.toString());
    }

    /* renamed from: e */
    public void mo62393e(Activity activity) {
        if (activity.getClass().getName().equals(this.f3339m)) {
            this.f3350x = false;
        }
        if (this.f3335i.size() == 0) {
            this.f3330d = false;
        }
    }

    /* renamed from: f */
    public boolean mo62398f() {
        Activity activity = this.f3337k;
        if (activity != null) {
            return activity.isTaskRoot();
        }
        return true;
    }

    /* renamed from: g */
    public String mo62400g() {
        return this.f3339m;
    }

    /* renamed from: h */
    public boolean mo62402h() {
        return this.f3347u;
    }

    /* renamed from: i */
    public boolean mo62403i() {
        return this.f3348v;
    }

    /* renamed from: c */
    public void mo62387c(boolean z) {
        this.f3348v = z;
    }

    @Deprecated
    /* renamed from: j */
    public void mo62404j() {
        mo62391d(false);
    }

    /* renamed from: d */
    public void mo62391d(boolean z) {
        this.f3349w = !z;
        if (!z) {
            C5071a.m3485a().mo62464a(Placement.INAPP_SPLASH);
        }
    }

    /* renamed from: k */
    public boolean mo62405k() {
        return !this.f3349w;
    }

    /* renamed from: l */
    public boolean mo62406l() {
        return this.f3329c && !this.f3330d && !this.f3332f;
    }

    /* renamed from: a */
    public boolean mo62378a(Placement placement) {
        boolean z = false;
        if (!this.f3329c || this.f3332f) {
            return false;
        }
        if (!this.f3330d) {
            return true;
        }
        if (placement == Placement.INAPP_RETURN && C5081d.m3532a().mo62488b().shouldReturnAdLoadInBg()) {
            z = true;
        }
        return z;
    }

    /* renamed from: f */
    private void m3395f(boolean z) {
        this.f3329c = z;
    }

    /* renamed from: g */
    private void m3397g(boolean z) {
        this.f3347u = z;
    }

    /* renamed from: m */
    public void mo62407m() {
        this.f3330d = false;
        this.f3332f = true;
    }

    /* renamed from: n */
    public boolean mo62408n() {
        return this.f3329c && this.f3330d;
    }

    /* renamed from: a */
    public void mo62375a(Context context, String str, String str2) {
        if (this.f3344r == null) {
            this.f3344r = new TreeMap();
        }
        this.f3344r.put(str, str2);
        m3404o(context);
    }

    /* renamed from: o */
    private void m3404o(Context context) {
        C5051k.m3340a(context, "sharedPrefsWrappers", this.f3344r);
    }

    /* renamed from: a */
    public String mo62369a(String str) {
        Map<String, String> map = this.f3344r;
        if (map == null) {
            return null;
        }
        return (String) map.get(str);
    }

    /* renamed from: o */
    public Map<String, String> mo62409o() {
        return this.f3344r;
    }

    /* renamed from: b */
    public boolean mo62384b(String str) {
        return mo62369a(str) != null;
    }

    /* renamed from: g */
    public SDKAdPreferences mo62399g(Context context) {
        if (this.f3327a == null) {
            SDKAdPreferences sDKAdPreferences = (SDKAdPreferences) C5152e.m3786a(context, "shared_prefs_sdk_ad_prefs", SDKAdPreferences.class);
            if (sDKAdPreferences == null) {
                this.f3327a = new SDKAdPreferences();
            } else {
                this.f3327a = sDKAdPreferences;
            }
        }
        return this.f3327a;
    }

    /* renamed from: f */
    private void m3394f(Activity activity) {
        this.f3330d = true;
        m3405p(activity);
        if (C5177c.m3887a() != null) {
            C5177c.m3887a().mo62900b(activity);
        }
    }

    /* renamed from: g */
    private void m3396g(Activity activity) {
        if (MetaData.getInstance().canShowAd() && mo62402h() && !C4983b.m3032a().mo62185y() && !C4946i.m2922a() && !mo62388c() && m3408q()) {
            this.f3351y = C5071a.m3485a().mo62452a(this.f3346t);
            C5021g gVar = this.f3351y;
            if (gVar != null && gVar.isReady()) {
                C4958f a = C4983b.m3032a().mo62152F().mo62061a(Placement.INAPP_RETURN, null);
                if (!a.mo62064a()) {
                    C4988c.m3112a((Context) activity, ((C4761e) this.f3351y).mo62249l(), (String) null, a.mo62066c());
                    if (Constants.m3707a().booleanValue()) {
                        C5159i.m3829a().mo62876a(activity, a.mo62065b());
                    }
                } else if (this.f3351y.mo61221a(null)) {
                    C4954b.m2946a().mo62055a(new C4953a(Placement.INAPP_RETURN, null));
                }
            }
        }
        if (C5177c.m3887a() != null) {
            C5177c.m3887a().mo62898a(activity);
        }
        if (m3410r()) {
            mo62373a((Context) activity, C5109a.APP_IDLE);
        }
    }

    /* renamed from: q */
    private boolean m3408q() {
        return System.currentTimeMillis() - this.f3333g > C4983b.m3032a().mo62184x();
    }

    /* renamed from: r */
    private boolean m3410r() {
        return System.currentTimeMillis() - this.f3333g > MetaData.getInstance().getSessionMaxBackgroundTime();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62373a(Context context, C5109a aVar) {
        C4944g.m2886d().mo62034a(context, aVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo62395e(boolean z) {
        boolean z2 = z && C5146c.m3769b();
        m3397g(z2);
        if (!z2) {
            C5071a.m3485a().mo62464a(Placement.INAPP_RETURN);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62374a(Context context, String str, long j, boolean z, boolean z2) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            String str2 = IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE;
            String str3 = "0";
            sb.append(z ? str2 : str3);
            sb.append(z2 ? "M" : ExifInterface.GPS_MEASUREMENT_IN_PROGRESS);
            C5017f.m3256a(context, C5015d.USER_CONSENT, str, sb.toString(), "");
            if (str.toLowerCase().equals("pas")) {
                if (!z) {
                    str2 = str3;
                }
                C5051k.m3346b(context, "USER_CONSENT_PERSONALIZED_ADS_SERVING", str2);
                C4944g.m2886d().mo62034a(context, C5109a.PAS);
                return;
            }
            return;
        }
        C5155g.m3807a("StartAppSDKInternal", 6, "setUserConsent: empty consentType");
    }

    /* renamed from: h */
    public void mo62401h(Context context) {
        m3390b(context, "android.permission.ACCESS_FINE_LOCATION", "USER_CONSENT_FINE_LOCATION");
        m3390b(context, "android.permission.ACCESS_COARSE_LOCATION", "USER_CONSENT_COARSE_LOCATION");
    }

    /* renamed from: b */
    private void m3390b(Context context, String str, String str2) {
        boolean booleanValue = C5051k.m3335a(context, str2, Boolean.valueOf(false)).booleanValue();
        boolean a = C5146c.m3760a(context, str);
        if (booleanValue != a) {
            C5051k.m3342b(context, str2, Boolean.valueOf(a));
            mo62374a(context, str, System.currentTimeMillis(), a, false);
        }
    }

    /* renamed from: p */
    private void m3405p(Context context) {
        m3382a(context, new AdPreferences());
    }

    /* renamed from: a */
    private void m3382a(Context context, AdPreferences adPreferences) {
        if (mo62402h() && !C4983b.m3032a().mo62185y()) {
            this.f3346t = C5071a.m3485a().mo62457a(context, adPreferences);
        }
    }

    /* renamed from: q */
    private static void m3407q(Context context) {
        TreeMap treeMap = new TreeMap();
        if (m3414u()) {
            treeMap.put("Cordova", C4946i.m2928b());
        }
        if (m3412s()) {
            treeMap.put("AdMob", m3393d("com.startapp.android.mediation.admob"));
        }
        String str = "MoPub";
        if (m3413t()) {
            treeMap.put(str, m3393d("com.mopub.mobileads"));
        }
        if (m3415v() && !m3378a().mo62409o().containsKey("B4A")) {
            treeMap.put(str, "0");
        }
        if (!treeMap.isEmpty()) {
            C5051k.m3340a(context, "sharedPrefsWrappers", (Map<String, String>) treeMap);
        }
    }

    /* renamed from: s */
    private static boolean m3412s() {
        return m3392c("com.startapp.android.mediation.admob.StartAppCustomEvent");
    }

    /* renamed from: t */
    private static boolean m3413t() {
        return m3392c("com.mopub.mobileads.StartAppCustomEventInterstitial");
    }

    /* renamed from: p */
    public static boolean m3406p() {
        return m3378a().mo62369a("Unity") != null;
    }

    /* renamed from: u */
    private static boolean m3414u() {
        return m3392c("org.apache.cordova.CordovaPlugin");
    }

    /* renamed from: v */
    private static boolean m3415v() {
        return m3392c("anywheresoftware.b4a.BA");
    }

    /* renamed from: c */
    private static boolean m3392c(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException | Exception unused) {
            return false;
        }
    }

    /* renamed from: d */
    private static String m3393d(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(".StartAppConstants");
            return (String) Class.forName(sb.toString()).getField("WRAPPER_VERSION").get(null);
        } catch (Exception unused) {
            return "0";
        }
    }

    /* renamed from: a */
    public static Object m3379a(Application application) {
        C50623 r0 = new ActivityLifecycleCallbacks() {
            public void onActivityStopped(Activity activity) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityStopped [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62389d(activity);
            }

            public void onActivityStarted(Activity activity) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityStarted [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62370a(activity);
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivitySaveInstanceState [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62381b(activity, bundle);
            }

            public void onActivityResumed(Activity activity) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityResumed [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62380b(activity);
            }

            public void onActivityPaused(Activity activity) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityPaused [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62385c(activity);
            }

            public void onActivityDestroyed(Activity activity) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityDestroyed [");
                sb.append(activity.getClass().getName());
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62393e(activity);
            }

            public void onActivityCreated(Activity activity, Bundle bundle) {
                StringBuilder sb = new StringBuilder();
                sb.append("onActivityCreated [");
                sb.append(activity.getClass().getName());
                sb.append(", ");
                sb.append(bundle);
                sb.append("]");
                C5155g.m3807a("StartAppSDKInternal", 3, sb.toString());
                C5059m.m3378a().mo62371a(activity, bundle);
                if (C4946i.m2923a(2)) {
                    C5008f.m3205a().mo62258a(activity, bundle);
                }
            }
        };
        application.registerActivityLifecycleCallbacks(r0);
        return r0;
    }

    /* renamed from: a */
    public static void m3380a(Application application, Object obj) {
        application.unregisterActivityLifecycleCallbacks((ActivityLifecycleCallbacks) obj);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m3381a(final Context context, long j) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    C5051k.m3346b(context, "User-Agent", new WebView(context).getSettings().getUserAgentString());
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, "NetworkUtils.saveUserAgent - Webview failed", e.getMessage(), "");
                }
            }
        }, j);
    }

    /* renamed from: r */
    private void m3409r(final Context context) {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                if (VERSION.SDK_INT >= 18) {
                    String i = C5059m.m3411s(context);
                    if (i != null) {
                        C5059m.this.f3326A = new ServiceConnection() {
                            public void onServiceDisconnected(ComponentName componentName) {
                            }

                            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                                C5059m.this.m3391b(context, true);
                                context.unbindService(C5059m.this.f3326A);
                            }
                        };
                        try {
                            Intent intent = new Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
                            intent.setPackage(i);
                            if (!context.bindService(intent, C5059m.this.f3326A, 33)) {
                                C5059m.this.m3391b(context, false);
                                return;
                            }
                            return;
                        } catch (Exception e) {
                            C5155g.m3808a("StartAppSDKInternal", 6, "failed to check checkChromeTabsSupport", e);
                            C5059m.this.m3391b(context, false);
                            return;
                        }
                    }
                }
                C5059m.this.m3391b(context, false);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: s */
    public static String m3411s(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
            String str = resolveActivity != null ? resolveActivity.activityInfo.packageName : null;
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            ArrayList arrayList = new ArrayList();
            for (ResolveInfo resolveInfo : queryIntentActivities) {
                Intent intent2 = new Intent();
                intent2.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
                intent2.setPackage(resolveInfo.activityInfo.packageName);
                if (packageManager.resolveService(intent2, 0) != null) {
                    arrayList.add(resolveInfo.activityInfo.packageName);
                }
            }
            String str2 = "com.android.chrome";
            if (arrayList.isEmpty()) {
                return null;
            }
            if (arrayList.size() == 1) {
                return (String) arrayList.get(0);
            }
            if (!TextUtils.isEmpty(str) && !m3386a(context, intent) && arrayList.contains(str)) {
                return str;
            }
            if (arrayList.contains(str2)) {
                return str2;
            }
            return null;
        } catch (Exception unused) {
            C5155g.m3807a("StartAppSDKInternal", 6, "Exception inside getPackageNameToUse");
            return null;
        }
    }

    /* renamed from: a */
    private static boolean m3386a(Context context, Intent intent) {
        try {
            List<ResolveInfo> queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 64);
            if (queryIntentActivities != null) {
                if (queryIntentActivities.size() != 0) {
                    for (ResolveInfo resolveInfo : queryIntentActivities) {
                        IntentFilter intentFilter = resolveInfo.filter;
                        if (intentFilter != null) {
                            if (intentFilter.countDataAuthorities() == 0) {
                                continue;
                            } else if (intentFilter.countDataPaths() != 0) {
                                if (resolveInfo.activityInfo != null) {
                                    return true;
                                }
                            }
                        }
                    }
                    return false;
                }
            }
            return false;
        } catch (RuntimeException unused) {
            C5155g.m3807a("StartAppSDKInternal", 6, "Runtime exception while getting specialized handlers");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m3391b(Context context, boolean z) {
        C5051k.m3342b(context, "chromeTabs", Boolean.valueOf(z));
    }
}
