package com.startapp.android.publish.cache;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.ads.list3d.C4810e;
import com.startapp.android.publish.ads.list3d.C4811f;
import com.startapp.android.publish.ads.p067b.C4759c;
import com.startapp.android.publish.ads.p068c.p070b.C4791b;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.Utils.C4946i.C4951a;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p078b.C4984a;
import com.startapp.android.publish.adsCommon.p078b.C4985b;
import com.startapp.android.publish.adsCommon.p078b.C4987c;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5152e;
import com.startapp.common.p092a.C5155g;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.startapp.android.publish.cache.i */
/* compiled from: StartAppSDK */
public class C5092i {

    /* renamed from: com.startapp.android.publish.cache.i$a */
    /* compiled from: StartAppSDK */
    protected interface C5100a {
        /* renamed from: a */
        void mo62516a(C5021g gVar);
    }

    /* renamed from: com.startapp.android.publish.cache.i$b */
    /* compiled from: StartAppSDK */
    protected static class C5101b implements Serializable {
        private static final long serialVersionUID = 1;
        protected AdPreferences adPreferences;
        private int numberOfLoadedAd;
        protected Placement placement;

        protected C5101b(Placement placement2, AdPreferences adPreferences2) {
            this.placement = placement2;
            this.adPreferences = adPreferences2;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Placement mo62529a() {
            return this.placement;
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public AdPreferences mo62531b() {
            return this.adPreferences;
        }

        /* access modifiers changed from: protected */
        /* renamed from: c */
        public int mo62532c() {
            return this.numberOfLoadedAd;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void mo62530a(int i) {
            this.numberOfLoadedAd = i;
        }
    }

    /* renamed from: com.startapp.android.publish.cache.i$c */
    /* compiled from: StartAppSDK */
    protected interface C5102c {
        /* renamed from: a */
        void mo62475a(List<C5101b> list);
    }

    /* renamed from: com.startapp.android.publish.cache.i$d */
    /* compiled from: StartAppSDK */
    protected static class C5103d implements Serializable {
        private static final long serialVersionUID = 1;

        /* renamed from: ad */
        private C5021g f3440ad;
        private String html;

        protected C5103d(C5021g gVar) {
            m3614a(gVar);
            m3615c();
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public C5021g mo62533a() {
            return this.f3440ad;
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public String mo62534b() {
            return this.html;
        }

        /* renamed from: a */
        private void m3614a(C5021g gVar) {
            this.f3440ad = gVar;
        }

        /* renamed from: c */
        private void m3615c() {
            C5021g gVar = this.f3440ad;
            if (gVar != null && (gVar instanceof C5003e)) {
                this.html = ((C5003e) gVar).mo62243f();
            }
        }
    }

    /* renamed from: com.startapp.android.publish.cache.i$e */
    /* compiled from: StartAppSDK */
    protected interface C5104e {
        /* renamed from: a */
        void mo62476a();
    }

    /* renamed from: a */
    protected static String m3591a() {
        return "startapp_ads";
    }

    /* renamed from: a */
    private static boolean m3603a(C4759c cVar) {
        return true;
    }

    /* renamed from: a */
    protected static void m3598a(final Context context, final C5104e eVar) {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                try {
                    C5152e.m3789a(context, C5092i.m3591a());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            eVar.mo62476a();
                        }
                    });
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, " DiskAdCacheManager.deleteDiskCacheAsync - Unexpected Thread Exception", e.getMessage(), "");
                }
            }
        });
    }

    /* renamed from: a */
    protected static void m3599a(Context context, Placement placement, AdPreferences adPreferences, String str, int i) {
        C5101b bVar = new C5101b(placement, adPreferences);
        bVar.mo62530a(i);
        C5152e.m3800b(context, m3604b(), str, (Serializable) bVar);
    }

    /* renamed from: a */
    protected static void m3596a(final Context context, final C5102c cVar) {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                try {
                    final List b = C5152e.m3798b(context, C5092i.m3604b(), C5101b.class);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            cVar.mo62475a(b);
                        }
                    });
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, " DiskAdCacheManager.loadCacheKeysAsync - Unexpected Thread Exception", e.getMessage(), "");
                }
            }
        });
    }

    /* renamed from: a */
    protected static void m3595a(Context context, C5085g gVar, String str) {
        C5152e.m3800b(context, m3605c(), str, (Serializable) new C5103d(gVar.mo62502b()));
    }

    /* renamed from: a */
    protected static void m3600a(final Context context, final String str, final C5100a aVar, final AdEventListener adEventListener) {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                try {
                    final C5103d dVar = (C5103d) C5152e.m3795b(context, C5092i.m3605c(), str, C5103d.class);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            try {
                                String str = "DiskAdCacheManager";
                                if (dVar == null) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("File not found or error: ");
                                    sb.append(str);
                                    C5155g.m3807a(str, 4, sb.toString());
                                    adEventListener.onFailedToReceiveAd(null);
                                    return;
                                }
                                if (dVar.mo62533a() != null) {
                                    if (dVar.mo62533a().isReady()) {
                                        if (dVar.mo62533a().hasAdCacheTtlPassed()) {
                                            C5155g.m3807a(str, 3, "Disk ad TTL has passed");
                                            adEventListener.onFailedToReceiveAd(null);
                                            return;
                                        }
                                        C5092i.m3597a(context, dVar, aVar, adEventListener);
                                        return;
                                    }
                                }
                                C5155g.m3807a(str, 3, "Disk ad is not ready or null");
                                adEventListener.onFailedToReceiveAd(null);
                            } catch (Exception e) {
                                C5017f.m3256a(context, C5015d.EXCEPTION, "DiskAdCacheManager.loadCachedAdAsync - Unexpected Thread Exception", e.getMessage(), "");
                                adEventListener.onFailedToReceiveAd(null);
                            }
                        }
                    });
                } catch (Exception e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, "DiskAdCacheManager.loadCachedAdAsync - Unexpected Thread Exception", e.getMessage(), "");
                    adEventListener.onFailedToReceiveAd(null);
                }
            }
        });
    }

    /* renamed from: b */
    protected static String m3604b() {
        return m3591a().concat(File.separator).concat("keys");
    }

    /* renamed from: c */
    protected static String m3605c() {
        return m3591a().concat(File.separator).concat("interstitials");
    }

    /* renamed from: a */
    protected static void m3597a(Context context, C5103d dVar, C5100a aVar, AdEventListener adEventListener) {
        C5021g a = dVar.mo62533a();
        a.setContext(context);
        if (C4946i.m2923a(2) && (a instanceof C4759c)) {
            m3593a(context, (C4759c) a, dVar.mo62534b(), aVar, adEventListener);
        } else if (!C4946i.m2923a(64) || !(a instanceof C4791b)) {
            C5155g.m3807a("DiskAdCacheManager", 4, "Unsupported disk ad type");
            adEventListener.onFailedToReceiveAd(null);
        } else {
            m3594a(context, (C4791b) a, aVar, adEventListener);
        }
    }

    /* renamed from: a */
    private static void m3594a(Context context, C4791b bVar, C5100a aVar, AdEventListener adEventListener) {
        List d = bVar.mo62336d();
        String str = "DiskAdCacheManager";
        if (d == null) {
            C5155g.m3807a(str, 4, "No ad details");
            adEventListener.onFailedToReceiveAd(null);
            return;
        }
        if (C4983b.m3032a().mo62151E()) {
            d = C4987c.m3089a(context, d, 0, new HashSet());
        }
        if (d == null || d.size() <= 0) {
            C5155g.m3807a(str, 4, "App presence - no interstitials to display");
            adEventListener.onFailedToReceiveAd(null);
            return;
        }
        aVar.mo62516a(bVar);
        m3601a(bVar, adEventListener, d);
    }

    /* renamed from: a */
    private static void m3593a(Context context, C4759c cVar, String str, C5100a aVar, AdEventListener adEventListener) {
        String str2 = "DiskAdCacheManager";
        if (str == null || str.equals("")) {
            C5155g.m3807a(str2, 3, "Missing Html");
            adEventListener.onFailedToReceiveAd(null);
        } else if (!m3603a(cVar)) {
            C5155g.m3807a(str2, 3, "Missing video file");
            adEventListener.onFailedToReceiveAd(null);
        } else if (!m3602a(context, str)) {
            C5155g.m3807a(str2, 3, "App is present");
            adEventListener.onFailedToReceiveAd(null);
        } else {
            C5071a.m3485a().mo62460a(str, cVar.mo62244g());
            aVar.mo62516a(cVar);
            m3592a(context, cVar, str, adEventListener);
        }
    }

    /* renamed from: a */
    private static void m3592a(Context context, final C4759c cVar, String str, final AdEventListener adEventListener) {
        C4946i.m2914a(context, str, (C4951a) new C4951a() {
            /* renamed from: a */
            public void mo62044a() {
                adEventListener.onReceiveAd(cVar);
            }

            /* renamed from: a */
            public void mo62045a(String str) {
                StringBuilder sb = new StringBuilder();
                sb.append("Html Cache failed: ");
                sb.append(str);
                C5155g.m3807a("DiskAdCacheManager", 3, sb.toString());
                adEventListener.onFailedToReceiveAd(cVar);
            }
        });
    }

    /* renamed from: a */
    private static void m3601a(C4791b bVar, AdEventListener adEventListener, List<AdDetails> list) {
        C4810e a = C4811f.m2473a().mo61493a(bVar.mo61342a());
        a.mo61483a();
        for (AdDetails a2 : list) {
            a.mo61486a(a2);
        }
        adEventListener.onReceiveAd(bVar);
    }

    /* renamed from: a */
    private static boolean m3602a(Context context, String str) {
        if (C4983b.m3032a().mo62151E()) {
            List a = C4987c.m3091a(str, 0);
            if (a != null) {
                ArrayList arrayList = new ArrayList();
                if (C4987c.m3088a(context, a, 0, (Set<String>) new HashSet<String>(), (List<C4984a>) arrayList).booleanValue()) {
                    new C4985b(context, arrayList).mo62195a();
                    return false;
                }
            }
        }
        return true;
    }
}
