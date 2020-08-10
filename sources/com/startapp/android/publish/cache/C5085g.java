package com.startapp.android.publish.cache;

import android.app.Activity;
import android.content.Context;
import com.startapp.android.publish.ads.p067b.C4760d;
import com.startapp.android.publish.ads.p067b.C4761e;
import com.startapp.android.publish.ads.p068c.p069a.C4789b;
import com.startapp.android.publish.ads.p068c.p070b.C4791b;
import com.startapp.android.publish.ads.splash.C4824b;
import com.startapp.android.publish.ads.video.C4893e;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4952a;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.adListeners.C4965b;
import com.startapp.android.publish.adsCommon.p078b.C4987c;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.p092a.C5155g;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.startapp.android.publish.cache.g */
/* compiled from: StartAppSDK */
public class C5085g {

    /* renamed from: h */
    public static boolean f3402h = false;

    /* renamed from: a */
    protected C5021g f3403a;

    /* renamed from: b */
    protected AtomicBoolean f3404b;

    /* renamed from: c */
    protected long f3405c;

    /* renamed from: d */
    protected C5084f f3406d;

    /* renamed from: e */
    protected C5079b f3407e;

    /* renamed from: f */
    protected Map<AdEventListener, List<StartAppAd>> f3408f;

    /* renamed from: g */
    protected C5090b f3409g;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public final Placement f3410i;

    /* renamed from: j */
    private Context f3411j;

    /* renamed from: k */
    private C4952a f3412k;

    /* renamed from: l */
    private AdPreferences f3413l;

    /* renamed from: m */
    private String f3414m;

    /* renamed from: n */
    private boolean f3415n;

    /* renamed from: o */
    private int f3416o;

    /* renamed from: p */
    private boolean f3417p;

    /* renamed from: com.startapp.android.publish.cache.g$3 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C50883 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3421a = new int[Placement.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.startapp.android.publish.common.model.AdPreferences$Placement[] r0 = com.startapp.android.publish.common.model.AdPreferences.Placement.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3421a = r0
                int[] r0 = f3421a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_FULL_SCREEN     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3421a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_OVERLAY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3421a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_OFFER_WALL     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3421a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_RETURN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f3421a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r1 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_SPLASH     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.cache.C5085g.C50883.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.cache.g$a */
    /* compiled from: StartAppSDK */
    class C5089a implements AdEventListener {

        /* renamed from: b */
        private boolean f3423b = false;

        /* renamed from: c */
        private boolean f3424c = false;

        C5089a() {
        }

        public void onReceiveAd(C4925Ad ad) {
            boolean z = C5085g.this.f3403a != null && C5085g.this.f3403a.getVideoCancelCallBack();
            if (!this.f3423b && !z) {
                this.f3423b = true;
                synchronized (C5085g.this.f3408f) {
                    for (AdEventListener adEventListener : C5085g.this.f3408f.keySet()) {
                        if (adEventListener != null) {
                            List<StartAppAd> list = (List) C5085g.this.f3408f.get(adEventListener);
                            if (list != null) {
                                for (StartAppAd startAppAd : list) {
                                    startAppAd.setErrorMessage(ad.getErrorMessage());
                                    new C4965b(adEventListener).onReceiveAd(startAppAd);
                                }
                            }
                        }
                    }
                    C5085g.this.f3408f.clear();
                }
            }
            C5085g.this.f3406d.mo62490f();
            C5085g.this.f3407e.mo62479a();
            C5085g.this.f3404b.set(false);
        }

        public void onFailedToReceiveAd(C4925Ad ad) {
            ConcurrentHashMap concurrentHashMap;
            Map map = null;
            if (!this.f3424c) {
                synchronized (C5085g.this.f3408f) {
                    concurrentHashMap = new ConcurrentHashMap(C5085g.this.f3408f);
                    C5085g.this.f3403a = null;
                    C5085g.this.f3408f.clear();
                }
                map = concurrentHashMap;
            }
            if (map != null) {
                for (AdEventListener adEventListener : map.keySet()) {
                    if (adEventListener != null) {
                        List<StartAppAd> list = (List) map.get(adEventListener);
                        if (list != null) {
                            for (StartAppAd startAppAd : list) {
                                startAppAd.setErrorMessage(ad.getErrorMessage());
                                new C4965b(adEventListener).onFailedToReceiveAd(startAppAd);
                            }
                        }
                    }
                }
            }
            this.f3424c = true;
            C5085g.this.f3407e.mo62490f();
            C5085g.this.f3406d.mo62479a();
            C5085g.this.f3404b.set(false);
        }
    }

    /* renamed from: com.startapp.android.publish.cache.g$b */
    /* compiled from: StartAppSDK */
    public interface C5090b {
        /* renamed from: a */
        void mo62478a(C5085g gVar);
    }

    public C5085g(Context context, Placement placement, AdPreferences adPreferences) {
        this.f3403a = null;
        this.f3404b = new AtomicBoolean(false);
        this.f3414m = null;
        this.f3415n = false;
        this.f3406d = null;
        this.f3407e = null;
        this.f3408f = new ConcurrentHashMap();
        this.f3417p = true;
        this.f3410i = placement;
        this.f3413l = adPreferences;
        m3553a(context);
        m3555o();
    }

    public C5085g(Context context, Placement placement, AdPreferences adPreferences, boolean z) {
        this(context, placement, adPreferences);
        this.f3417p = z;
    }

    /* renamed from: a */
    private void m3553a(Context context) {
        if (context instanceof Activity) {
            this.f3411j = context.getApplicationContext();
            this.f3412k = new C4952a((Activity) context);
            return;
        }
        this.f3411j = context;
        this.f3412k = null;
    }

    /* renamed from: o */
    private void m3555o() {
        this.f3406d = new C5084f(this);
        this.f3407e = new C5079b(this);
    }

    /* renamed from: a */
    public AdPreferences mo62495a() {
        return this.f3413l;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62499a(AdPreferences adPreferences) {
        this.f3413l = adPreferences;
    }

    /* renamed from: b */
    public C5021g mo62502b() {
        return this.f3403a;
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public Placement mo62504c() {
        return this.f3410i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62500a(String str) {
        this.f3414m = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62501a(boolean z) {
        this.f3415n = z;
    }

    /* renamed from: a */
    public void mo62497a(StartAppAd startAppAd, AdEventListener adEventListener) {
        m3554a(startAppAd, adEventListener, false);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62503b(boolean z) {
        m3554a(null, null, z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008a, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x001a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0065  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3554a(com.startapp.android.publish.adsCommon.StartAppAd r6, com.startapp.android.publish.adsCommon.adListeners.AdEventListener r7, boolean r8) {
        /*
            r5 = this;
            java.util.Map<com.startapp.android.publish.adsCommon.adListeners.AdEventListener, java.util.List<com.startapp.android.publish.adsCommon.StartAppAd>> r0 = r5.f3408f
            monitor-enter(r0)
            boolean r1 = r5.mo62508g()     // Catch:{ all -> 0x008b }
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0016
            boolean r1 = r5.m3561u()     // Catch:{ all -> 0x008b }
            if (r1 != 0) goto L_0x0016
            if (r8 == 0) goto L_0x0014
            goto L_0x0016
        L_0x0014:
            r8 = 0
            goto L_0x0017
        L_0x0016:
            r8 = 1
        L_0x0017:
            r1 = 3
            if (r8 == 0) goto L_0x0065
            if (r6 == 0) goto L_0x0035
            if (r7 == 0) goto L_0x0035
            java.util.Map<com.startapp.android.publish.adsCommon.adListeners.AdEventListener, java.util.List<com.startapp.android.publish.adsCommon.StartAppAd>> r8 = r5.f3408f     // Catch:{ all -> 0x008b }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x008b }
            java.util.List r8 = (java.util.List) r8     // Catch:{ all -> 0x008b }
            if (r8 != 0) goto L_0x0032
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ all -> 0x008b }
            r8.<init>()     // Catch:{ all -> 0x008b }
            java.util.Map<com.startapp.android.publish.adsCommon.adListeners.AdEventListener, java.util.List<com.startapp.android.publish.adsCommon.StartAppAd>> r4 = r5.f3408f     // Catch:{ all -> 0x008b }
            r4.put(r7, r8)     // Catch:{ all -> 0x008b }
        L_0x0032:
            r8.add(r6)     // Catch:{ all -> 0x008b }
        L_0x0035:
            java.util.concurrent.atomic.AtomicBoolean r6 = r5.f3404b     // Catch:{ all -> 0x008b }
            boolean r6 = r6.compareAndSet(r2, r3)     // Catch:{ all -> 0x008b }
            if (r6 == 0) goto L_0x004b
            com.startapp.android.publish.cache.f r6 = r5.f3406d     // Catch:{ all -> 0x008b }
            r6.mo62491g()     // Catch:{ all -> 0x008b }
            com.startapp.android.publish.cache.b r6 = r5.f3407e     // Catch:{ all -> 0x008b }
            r6.mo62491g()     // Catch:{ all -> 0x008b }
            r5.m3556p()     // Catch:{ all -> 0x008b }
            goto L_0x0089
        L_0x004b:
            java.lang.String r6 = "CachedAd"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r7.<init>()     // Catch:{ all -> 0x008b }
            com.startapp.android.publish.common.model.AdPreferences$Placement r8 = r5.f3410i     // Catch:{ all -> 0x008b }
            r7.append(r8)     // Catch:{ all -> 0x008b }
            java.lang.String r8 = " ad is currently loading"
            r7.append(r8)     // Catch:{ all -> 0x008b }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x008b }
            com.startapp.common.p092a.C5155g.m3807a(r6, r1, r7)     // Catch:{ all -> 0x008b }
            monitor-exit(r0)     // Catch:{ all -> 0x008b }
            return
        L_0x0065:
            java.lang.String r8 = "CachedAd"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r2.<init>()     // Catch:{ all -> 0x008b }
            com.startapp.android.publish.common.model.AdPreferences$Placement r3 = r5.f3410i     // Catch:{ all -> 0x008b }
            r2.append(r3)     // Catch:{ all -> 0x008b }
            java.lang.String r3 = " ad already loaded"
            r2.append(r3)     // Catch:{ all -> 0x008b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x008b }
            com.startapp.common.p092a.C5155g.m3807a(r8, r1, r2)     // Catch:{ all -> 0x008b }
            if (r6 == 0) goto L_0x0089
            if (r7 == 0) goto L_0x0089
            com.startapp.android.publish.adsCommon.adListeners.b r8 = new com.startapp.android.publish.adsCommon.adListeners.b     // Catch:{ all -> 0x008b }
            r8.<init>(r7)     // Catch:{ all -> 0x008b }
            r8.onReceiveAd(r6)     // Catch:{ all -> 0x008b }
        L_0x0089:
            monitor-exit(r0)     // Catch:{ all -> 0x008b }
            return
        L_0x008b:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x008b }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.cache.C5085g.m3554a(com.startapp.android.publish.adsCommon.StartAppAd, com.startapp.android.publish.adsCommon.adListeners.AdEventListener, boolean):void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public void mo62505d() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invalidating: ");
        sb.append(this.f3410i);
        String str = "CachedAd";
        C5155g.m3807a(str, 4, sb.toString());
        if (mo62508g()) {
            if (C4987c.m3092a(this.f3411j, (C4925Ad) this.f3403a) || m3561u()) {
                C5155g.m3807a(str, 3, "App present or cache TTL passed, reloading");
                mo62503b(true);
            } else if (!this.f3404b.get()) {
                this.f3406d.mo62490f();
            }
        } else if (!this.f3404b.get()) {
            this.f3407e.mo62490f();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public void mo62506e() {
        this.f3407e.mo62492h();
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void mo62507f() {
        this.f3406d.mo62492h();
    }

    /* renamed from: g */
    public boolean mo62508g() {
        C5021g gVar = this.f3403a;
        return gVar != null && gVar.isReady();
    }

    /* renamed from: h */
    public boolean mo62509h() {
        return this.f3417p;
    }

    /* renamed from: i */
    public C5021g mo62510i() {
        if (!mo62508g()) {
            return null;
        }
        C5021g gVar = this.f3403a;
        mo62514m();
        if (!AdsConstants.OVERRIDE_NETWORK.booleanValue() && mo62509h()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ad shown, reloading ");
            sb.append(this.f3410i);
            C5155g.m3807a("CachedAd", 3, sb.toString());
            mo62503b(true);
            return gVar;
        } else if (mo62509h()) {
            return gVar;
        } else {
            C5090b bVar = this.f3409g;
            if (bVar != null) {
                bVar.mo62478a(this);
            }
            C5084f fVar = this.f3406d;
            if (fVar == null) {
                return gVar;
            }
            fVar.mo62479a();
            return gVar;
        }
    }

    /* renamed from: j */
    public C5021g mo62511j() {
        C5021g gVar;
        C4946i.m2913a(this.f3411j, this.f3413l);
        int i = C50883.f3421a[this.f3410i.ordinal()];
        if (i == 1) {
            gVar = new C4760d(this.f3411j);
        } else if (i != 2) {
            if (i == 3) {
                gVar = m3562v();
            } else if (i == 4) {
                gVar = new C4761e(this.f3411j);
            } else if (i != 5) {
                gVar = new C4760d(this.f3411j);
            } else {
                gVar = new C4824b(this.f3411j);
            }
        } else if (C4946i.m2923a(4)) {
            gVar = new C4893e(this.f3411j);
        } else {
            gVar = new C4760d(this.f3411j);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ad Type: [");
        sb.append(gVar.getClass().toString());
        sb.append("]");
        C5155g.m3807a("CachedAd", 4, sb.toString());
        return gVar;
    }

    /* renamed from: p */
    private void m3556p() {
        C5021g gVar = this.f3403a;
        if (gVar != null) {
            gVar.setVideoCancelCallBack(false);
        }
        if (m3557q()) {
            mo62501a(false);
            m3558r();
            return;
        }
        mo62515n();
    }

    /* renamed from: q */
    private boolean m3557q() {
        return this.f3415n && this.f3414m != null;
    }

    /* renamed from: r */
    private void m3558r() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loading ");
        sb.append(this.f3410i);
        sb.append(" from disk ");
        sb.append("file name: ");
        sb.append(this.f3414m);
        C5155g.m3807a("CachedAd", 4, sb.toString());
        final C5089a aVar = new C5089a();
        C5092i.m3600a(this.f3411j, this.f3414m, (C5100a) new C5100a() {
            /* renamed from: a */
            public void mo62516a(C5021g gVar) {
                StringBuilder sb = new StringBuilder();
                sb.append("Success loading from disk: ");
                sb.append(C5085g.this.f3410i);
                C5155g.m3807a("CachedAd", 4, sb.toString());
                C5085g.this.f3403a = gVar;
            }
        }, (AdEventListener) new AdEventListener() {
            public void onReceiveAd(C4925Ad ad) {
                aVar.onReceiveAd(ad);
            }

            public void onFailedToReceiveAd(C4925Ad ad) {
                StringBuilder sb = new StringBuilder();
                sb.append("Failed to load ");
                sb.append(C5085g.this.f3410i);
                sb.append(" from disk");
                C5155g.m3807a("CachedAd", 3, sb.toString());
                C5085g gVar = C5085g.this;
                gVar.f3403a = null;
                gVar.mo62515n();
            }
        });
    }

    /* renamed from: a */
    public void mo62498a(C5090b bVar) {
        this.f3409g = bVar;
    }

    /* renamed from: k */
    public boolean mo62512k() {
        if (m3559s()) {
            mo62503b(true);
            m3560t();
            return true;
        }
        C5090b bVar = this.f3409g;
        if (bVar != null) {
            bVar.mo62478a(this);
        }
        return false;
    }

    /* renamed from: s */
    private boolean m3559s() {
        return this.f3416o < MetaData.getInstance().getStopAutoLoadAmount();
    }

    /* renamed from: t */
    private void m3560t() {
        this.f3416o++;
    }

    /* renamed from: l */
    public int mo62513l() {
        return this.f3416o;
    }

    /* renamed from: a */
    public void mo62496a(int i) {
        this.f3416o = i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: m */
    public void mo62514m() {
        this.f3416o = 0;
    }

    /* access modifiers changed from: protected */
    /* renamed from: n */
    public void mo62515n() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loading ");
        sb.append(this.f3410i);
        sb.append(" from server");
        C5155g.m3807a("CachedAd", 4, sb.toString());
        this.f3403a = mo62511j();
        this.f3403a.setActivityExtra(this.f3412k);
        this.f3403a.load(this.f3413l, new C5089a());
        this.f3405c = System.currentTimeMillis();
    }

    /* renamed from: u */
    private boolean m3561u() {
        C5021g gVar = this.f3403a;
        if (gVar == null) {
            return false;
        }
        return gVar.hasAdCacheTtlPassed();
    }

    /* renamed from: v */
    private C5021g m3562v() {
        boolean z = new Random().nextInt(100) < C4983b.m3032a().mo62164d();
        boolean a = C4946i.m2926a(this.f3413l, "forceOfferWall3D");
        boolean a2 = true ^ C4946i.m2926a(this.f3413l, "forceOfferWall2D");
        boolean a3 = C4946i.m2923a(64);
        if (m3563w() || (a3 && ((z || a) && a2))) {
            return new C4791b(this.f3411j);
        }
        return new C4789b(this.f3411j);
    }

    /* renamed from: w */
    private boolean m3563w() {
        return C4946i.m2923a(64) && !C4946i.m2923a(128);
    }
}
