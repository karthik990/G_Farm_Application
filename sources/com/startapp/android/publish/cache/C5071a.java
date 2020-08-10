package com.startapp.android.publish.cache;

import android.content.Context;
import com.startapp.android.publish.ads.p067b.C4761e;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppAd.AdMode;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5016e;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.cache.C5085g.C5090b;
import com.startapp.android.publish.common.metaData.C5116d;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5152e;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: com.startapp.android.publish.cache.a */
/* compiled from: StartAppSDK */
public class C5071a {

    /* renamed from: c */
    private static C5071a f3370c = new C5071a();

    /* renamed from: a */
    final Map<C5080c, C5085g> f3371a = new ConcurrentHashMap();

    /* renamed from: b */
    protected boolean f3372b = false;

    /* renamed from: d */
    private Map<String, String> f3373d = new WeakHashMap();

    /* renamed from: e */
    private boolean f3374e = false;

    /* renamed from: f */
    private Queue<C5078a> f3375f = new ConcurrentLinkedQueue();

    /* renamed from: g */
    private C5090b f3376g;

    /* renamed from: h */
    private Context f3377h;

    /* renamed from: com.startapp.android.publish.cache.a$6 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C50776 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3386a = new int[Placement.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|(2:1|2)|3|5|6|7|8|9|10|11|12|(2:13|14)|15|17|18|(3:19|20|22)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|(2:1|2)|3|5|6|7|8|9|10|11|12|(2:13|14)|15|17|18|19|20|22) */
        /* JADX WARNING: Can't wrap try/catch for region: R(19:0|1|2|3|5|6|7|8|9|10|11|12|(2:13|14)|15|17|18|19|20|22) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0035 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x005e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x002a */
        static {
            /*
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode[] r0 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3387b = r0
                r0 = 1
                int[] r1 = f3387b     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r2 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.OFFERWALL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3387b     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.OVERLAY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r2 = f3387b     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.FULLPAGE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r2 = f3387b     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.VIDEO     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4 = 4
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r2 = f3387b     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.REWARDED_VIDEO     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r4 = 5
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r2 = f3387b     // Catch:{ NoSuchFieldError -> 0x004b }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.AUTOMATIC     // Catch:{ NoSuchFieldError -> 0x004b }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r4 = 6
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                com.startapp.android.publish.common.model.AdPreferences$Placement[] r2 = com.startapp.android.publish.common.model.AdPreferences.Placement.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3386a = r2
                int[] r2 = f3386a     // Catch:{ NoSuchFieldError -> 0x005e }
                com.startapp.android.publish.common.model.AdPreferences$Placement r3 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_SPLASH     // Catch:{ NoSuchFieldError -> 0x005e }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x005e }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x005e }
            L_0x005e:
                int[] r0 = f3386a     // Catch:{ NoSuchFieldError -> 0x0068 }
                com.startapp.android.publish.common.model.AdPreferences$Placement r2 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_RETURN     // Catch:{ NoSuchFieldError -> 0x0068 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0068 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0068 }
            L_0x0068:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.cache.C5071a.C50776.<clinit>():void");
        }
    }

    /* renamed from: com.startapp.android.publish.cache.a$a */
    /* compiled from: StartAppSDK */
    private class C5078a {

        /* renamed from: a */
        StartAppAd f3388a;

        /* renamed from: b */
        Placement f3389b;

        /* renamed from: c */
        AdPreferences f3390c;

        /* renamed from: d */
        AdEventListener f3391d;

        C5078a(StartAppAd startAppAd, Placement placement, AdPreferences adPreferences, AdEventListener adEventListener) {
            this.f3388a = startAppAd;
            this.f3389b = placement;
            this.f3390c = adPreferences;
            this.f3391d = adEventListener;
        }
    }

    private C5071a() {
    }

    /* renamed from: a */
    public static C5071a m3485a() {
        return f3370c;
    }

    /* renamed from: a */
    public C5080c mo62456a(Context context, StartAppAd startAppAd, AdPreferences adPreferences, AdEventListener adEventListener) {
        if (!m3492b(Placement.INAPP_SPLASH)) {
            return null;
        }
        C5155g.m3807a("AdCacheManager", 3, "Loading splash");
        return mo62454a(context, startAppAd, Placement.INAPP_SPLASH, adPreferences, adEventListener);
    }

    /* renamed from: a */
    public C5080c mo62457a(Context context, AdPreferences adPreferences) {
        if (!m3492b(Placement.INAPP_RETURN)) {
            return null;
        }
        C5155g.m3807a("AdCacheManager", 3, "Loading return ad");
        return mo62454a(context, (StartAppAd) null, Placement.INAPP_RETURN, adPreferences, (AdEventListener) null);
    }

    /* renamed from: a */
    public C5080c mo62453a(Context context, StartAppAd startAppAd, AdMode adMode, AdPreferences adPreferences, AdEventListener adEventListener) {
        if (adPreferences == null) {
            adPreferences = new AdPreferences();
        }
        AdPreferences adPreferences2 = adPreferences;
        Placement b = m3491b(adMode, adPreferences2);
        m3487a(adMode, adPreferences2);
        return mo62455a(context, startAppAd, b, adPreferences2, adEventListener, false, 0);
    }

    /* renamed from: a */
    public void mo62462a(final Context context) {
        this.f3377h = context.getApplicationContext();
        if (m3494e()) {
            this.f3374e = true;
            C5092i.m3596a(context, (C5102c) new C5102c() {
                /* renamed from: a */
                public void mo62475a(List<C5101b> list) {
                    String str = "AdCacheManager";
                    if (list != null) {
                        try {
                            for (C5101b bVar : list) {
                                if (C5071a.this.m3492b(bVar.placement)) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Loading from disk: ");
                                    sb.append(bVar.placement);
                                    C5155g.m3807a(str, 4, sb.toString());
                                    C5071a.this.mo62455a(context, null, bVar.mo62529a(), bVar.mo62531b(), null, true, bVar.mo62532c());
                                }
                            }
                        } catch (Exception e) {
                            C5155g.m3808a(str, 6, "loadFromDisk - onCacheKeysLoaded failed", e);
                        }
                    }
                    C5071a.this.mo62474d(context);
                }
            });
        }
    }

    /* renamed from: b */
    public void mo62467b() {
        if (!this.f3374e) {
            synchronized (this.f3371a) {
                for (C5085g d : this.f3371a.values()) {
                    d.mo62505d();
                }
            }
        }
    }

    /* renamed from: a */
    public void mo62463a(Context context, boolean z) {
        m3493e(context);
        m3489a(z);
    }

    /* renamed from: b */
    public void mo62468b(Context context) {
        this.f3372b = true;
        C5092i.m3598a(context, (C5104e) new C5104e() {
            /* renamed from: a */
            public void mo62476a() {
                C5071a.this.f3372b = false;
            }
        });
    }

    /* renamed from: c */
    public void mo62472c(final Context context) {
        C50743 r0 = new C5116d() {
            /* renamed from: a */
            public void mo61600a() {
                MetaData.getInstance().removeMetaDataListener(this);
                Set autoLoad = C5081d.m3532a().mo62488b().getAutoLoad();
                if (autoLoad != null) {
                    for (AdMode adMode : C5071a.this.mo62461a(autoLoad)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("preCacheAds load ");
                        sb.append(adMode.name());
                        C5155g.m3807a("preCache", 3, sb.toString());
                        int b = C4983b.m3032a().mo62162b();
                        if (adMode == AdMode.FULLPAGE) {
                            if (b > 0) {
                                C5071a.this.mo62453a(context, (StartAppAd) null, AdMode.FULLPAGE, new AdPreferences(), (AdEventListener) null);
                            }
                        } else if (adMode != AdMode.OFFERWALL) {
                            C5071a.this.mo62453a(context, (StartAppAd) null, adMode, new AdPreferences(), (AdEventListener) null);
                        } else if (b < 100) {
                            C5071a.this.mo62453a(context, (StartAppAd) null, AdMode.OFFERWALL, new AdPreferences(), (AdEventListener) null);
                        }
                        String a = C5071a.this.mo62458a(adMode);
                        if (a != null) {
                            C5051k.m3344b(context, a, Integer.valueOf(C5051k.m3337a(context, a, Integer.valueOf(0)).intValue() + 1));
                        }
                    }
                }
            }

            /* renamed from: b */
            public void mo61601b() {
                C5155g.m3807a("AdCacheManager", 3, "Failed loading metadata, unable to pre-cache.");
                MetaData.getInstance().removeMetaDataListener(this);
            }
        };
        synchronized (MetaData.getLock()) {
            if (MetaData.getInstance().isReady()) {
                r0.mo61600a();
            } else {
                MetaData.getInstance().addMetaDataListener(r0);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Set<AdMode> mo62461a(Set<AdMode> set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            AdMode adMode = (AdMode) it.next();
            boolean z = false;
            if (C5051k.m3337a(this.f3377h, mo62458a(adMode), Integer.valueOf(0)).intValue() >= MetaData.getInstance().getStopAutoLoadPreCacheAmount()) {
                z = true;
            }
            if (z) {
                StringBuilder sb = new StringBuilder();
                sb.append("preCacheAds.remove ");
                sb.append(adMode.name());
                C5155g.m3807a("preCache", 3, sb.toString());
                it.remove();
            }
        }
        if (!C4946i.m2923a(128) && !C4946i.m2923a(64)) {
            set.remove(AdMode.OFFERWALL);
        }
        if (!C4946i.m2923a(2) && !C4946i.m2923a(4)) {
            set.remove(AdMode.FULLPAGE);
        }
        if (!C4946i.m2923a(4)) {
            set.remove(AdMode.REWARDED_VIDEO);
            set.remove(AdMode.VIDEO);
        }
        return set;
    }

    /* renamed from: c */
    public int mo62469c() {
        return this.f3371a.size();
    }

    /* renamed from: a */
    public C5021g mo62452a(C5080c cVar) {
        String str = "AdCacheManager";
        if (cVar == null) {
            C5155g.m3807a(str, 3, "Cache key is null");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Retrieving ad with ");
        sb.append(cVar);
        C5155g.m3807a(str, 3, sb.toString());
        C5085g gVar = (C5085g) this.f3371a.get(cVar);
        if (gVar != null) {
            return gVar.mo62510i();
        }
        return null;
    }

    /* renamed from: b */
    public C5021g mo62465b(C5080c cVar) {
        C5085g gVar = cVar != null ? (C5085g) this.f3371a.get(cVar) : null;
        if (gVar != null) {
            return gVar.mo62502b();
        }
        return null;
    }

    /* renamed from: d */
    public synchronized List<C5085g> mo62473d() {
        return new ArrayList(this.f3371a.values());
    }

    /* renamed from: a */
    public String mo62459a(String str) {
        return mo62460a(str, UUID.randomUUID().toString());
    }

    /* renamed from: a */
    public String mo62460a(String str, String str2) {
        this.f3373d.put(str2, str);
        return str2;
    }

    /* renamed from: b */
    public String mo62466b(String str) {
        return (String) this.f3373d.get(str);
    }

    /* renamed from: c */
    public String mo62471c(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("cache size: ");
        sb.append(this.f3373d.size());
        sb.append(" - removing ");
        sb.append(str);
        C5155g.m3807a("AdCacheManager", 3, sb.toString());
        return (String) this.f3373d.remove(str);
    }

    /* renamed from: e */
    private boolean m3494e() {
        return !this.f3372b && C5081d.m3532a().mo62488b().isLocalCache();
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public void mo62474d(Context context) {
        this.f3374e = false;
        for (C5078a aVar : this.f3375f) {
            if (m3492b(aVar.f3389b)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Loading pending request for: ");
                sb.append(aVar.f3389b);
                C5155g.m3807a("AdCacheManager", 4, sb.toString());
                mo62454a(context, aVar.f3388a, aVar.f3389b, aVar.f3390c, aVar.f3391d);
            }
        }
        this.f3375f.clear();
    }

    /* renamed from: e */
    private void m3493e(final Context context) {
        String str = "AdCacheManager";
        C5155g.m3807a(str, 3, "Saving to disk: eneter save to disk ");
        if (m3494e()) {
            C5155g.m3807a(str, 3, "Saving to disk: cache to disk is enebaled ");
            C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
                public void run() {
                    String str = "AdCacheManager";
                    try {
                        C5152e.m3789a(context, C5092i.m3604b());
                        C5152e.m3789a(context, C5092i.m3605c());
                        C5080c cVar = null;
                        for (Entry entry : C5071a.this.f3371a.entrySet()) {
                            C5080c cVar2 = (C5080c) entry.getKey();
                            if (cVar2.mo62484a() == Placement.INAPP_SPLASH) {
                                cVar = cVar2;
                            } else {
                                C5085g gVar = (C5085g) entry.getValue();
                                StringBuilder sb = new StringBuilder();
                                sb.append("Saving to disk: ");
                                sb.append(cVar2.toString());
                                C5155g.m3807a(str, 3, sb.toString());
                                C5092i.m3599a(context, cVar2.mo62484a(), gVar.mo62495a(), C5071a.this.mo62470c(cVar2), gVar.mo62513l());
                                C5092i.m3595a(context, gVar, C5071a.this.mo62470c(cVar2));
                            }
                        }
                        if (cVar != null) {
                            C5071a.this.f3371a.remove(cVar);
                        }
                    } catch (Exception e) {
                        C5155g.m3807a(str, 3, "Saving to disk: exception caught");
                        C5017f.m3256a(context, C5015d.EXCEPTION, "AdCacheManager.saveToDisk - Unexpected Thread Exception", e.getMessage(), "");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public String mo62470c(C5080c cVar) {
        return String.valueOf(cVar.hashCode()).replace('-', '_');
    }

    /* renamed from: a */
    private void m3489a(boolean z) {
        for (C5085g gVar : this.f3371a.values()) {
            if (gVar.mo62502b() == null || !C4946i.m2923a(2) || !(gVar.mo62502b() instanceof C4761e) || z) {
                gVar.mo62507f();
            } else if (!C5081d.m3532a().mo62488b().shouldReturnAdLoadInBg()) {
                gVar.mo62507f();
            }
            gVar.mo62506e();
        }
    }

    /* renamed from: a */
    public C5080c mo62454a(Context context, StartAppAd startAppAd, Placement placement, AdPreferences adPreferences, AdEventListener adEventListener) {
        return mo62455a(context, startAppAd, placement, adPreferences, adEventListener, false, 0);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C5080c mo62455a(Context context, StartAppAd startAppAd, Placement placement, AdPreferences adPreferences, AdEventListener adEventListener, boolean z, int i) {
        C5085g gVar;
        this.f3377h = context.getApplicationContext();
        if (adPreferences == null) {
            adPreferences = new AdPreferences();
        }
        AdPreferences adPreferences2 = adPreferences;
        C5080c cVar = new C5080c(placement, adPreferences2);
        if (!this.f3374e || z) {
            AdPreferences adPreferences3 = new AdPreferences(adPreferences2);
            synchronized (this.f3371a) {
                gVar = (C5085g) this.f3371a.get(cVar);
                if (gVar == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("CachedAd for ");
                    sb.append(placement);
                    sb.append(" not found. Adding new CachedAd with ");
                    sb.append(cVar);
                    C5155g.m3807a("AdCacheManager", 3, sb.toString());
                    if (C50776.f3386a[placement.ordinal()] != 1) {
                        gVar = new C5085g(context, placement, adPreferences3);
                    } else {
                        gVar = new C5085g(context, placement, adPreferences3, false);
                    }
                    gVar.mo62498a(m3495f());
                    if (z) {
                        gVar.mo62500a(mo62470c(cVar));
                        gVar.mo62501a(true);
                        gVar.mo62496a(i);
                    }
                    m3488a(cVar, gVar, context);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CachedAd for ");
                    sb2.append(placement);
                    sb2.append(" already exists.");
                    C5155g.m3807a("AdCacheManager", 3, sb2.toString());
                    gVar.mo62499a(adPreferences3);
                }
            }
            gVar.mo62497a(startAppAd, adEventListener);
            return cVar;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Adding to pending queue: ");
        sb3.append(placement);
        C5155g.m3807a("AdCacheManager", 4, sb3.toString());
        Queue<C5078a> queue = this.f3375f;
        C5078a aVar = new C5078a(startAppAd, placement, adPreferences2, adEventListener);
        queue.add(aVar);
        return cVar;
    }

    /* renamed from: a */
    public void mo62464a(Placement placement) {
        synchronized (this.f3371a) {
            Iterator it = this.f3371a.entrySet().iterator();
            while (it.hasNext()) {
                if (((C5080c) ((Entry) it.next()).getKey()).mo62484a() == placement) {
                    it.remove();
                }
            }
        }
    }

    /* renamed from: a */
    private void m3488a(C5080c cVar, C5085g gVar, Context context) {
        synchronized (this.f3371a) {
            int maxCacheSize = C5081d.m3532a().mo62488b().getMaxCacheSize();
            if (maxCacheSize != 0 && mo62469c() >= maxCacheSize) {
                long j = Long.MAX_VALUE;
                Object obj = null;
                for (C5080c cVar2 : this.f3371a.keySet()) {
                    C5085g gVar2 = (C5085g) this.f3371a.get(cVar2);
                    if (gVar2.mo62504c() == gVar.mo62504c() && gVar2.f3405c < j) {
                        j = gVar2.f3405c;
                        obj = cVar2;
                    }
                }
                if (obj != null) {
                    this.f3371a.remove(obj);
                }
            }
            this.f3371a.put(cVar, gVar);
            if (Math.random() * 100.0d < ((double) C5081d.m3532a().mo62489c())) {
                C5017f.m3258a(context, new C5016e(C5015d.GENERAL, "Cache Size", String.valueOf(mo62469c())), "");
            }
        }
    }

    /* renamed from: a */
    private void m3487a(AdMode adMode, AdPreferences adPreferences) {
        String str = "type";
        if (adMode.equals(AdMode.REWARDED_VIDEO)) {
            C4988c.m3113a(adPreferences, str, AdType.REWARDED_VIDEO);
        }
        if (adMode.equals(AdMode.VIDEO)) {
            C4988c.m3113a(adPreferences, str, AdType.VIDEO);
        }
    }

    /* renamed from: b */
    private Placement m3491b(AdMode adMode, AdPreferences adPreferences) {
        boolean z = false;
        switch (adMode) {
            case OFFERWALL:
                if (C4946i.m2923a(128) || C4946i.m2923a(64)) {
                    z = true;
                }
                return z ? Placement.INAPP_OFFER_WALL : Placement.INAPP_FULL_SCREEN;
            case OVERLAY:
            case FULLPAGE:
            case VIDEO:
            case REWARDED_VIDEO:
                return Placement.INAPP_OVERLAY;
            case AUTOMATIC:
                if (C4946i.m2923a(128) || C4946i.m2923a(64)) {
                    z = true;
                }
                boolean a = C4946i.m2923a(4);
                boolean a2 = C4946i.m2923a(2);
                if (a && a2 && z) {
                    if (new Random().nextInt(100) < C4983b.m3032a().mo62162b()) {
                        return m3486a(adPreferences);
                    }
                    return Placement.INAPP_FULL_SCREEN;
                } else if (a || a2) {
                    return Placement.INAPP_OVERLAY;
                } else {
                    if (z) {
                        return Placement.INAPP_OFFER_WALL;
                    }
                }
                break;
        }
        return Placement.INAPP_FULL_SCREEN;
    }

    /* renamed from: a */
    private Placement m3486a(AdPreferences adPreferences) {
        if ((new Random().nextInt(100) < C4983b.m3032a().mo62163c() || C4946i.m2926a(adPreferences, "forceFullpage")) && !C4946i.m2926a(adPreferences, "forceOverlay")) {
            return Placement.INAPP_FULL_SCREEN;
        }
        return Placement.INAPP_OVERLAY;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public boolean m3492b(Placement placement) {
        int i = C50776.f3386a[placement.ordinal()];
        boolean z = false;
        if (i == 1) {
            if (C5059m.m3378a().mo62405k() && !C4983b.m3032a().mo62186z()) {
                z = true;
            }
            return z;
        } else if (i != 2) {
            return true;
        } else {
            if (C5059m.m3378a().mo62402h() && !C4983b.m3032a().mo62185y()) {
                z = true;
            }
            return z;
        }
    }

    /* renamed from: f */
    private C5090b m3495f() {
        if (this.f3376g == null) {
            this.f3376g = new C5090b() {
                /* renamed from: a */
                public void mo62478a(C5085g gVar) {
                    synchronized (C5071a.this.f3371a) {
                        Object obj = null;
                        for (C5080c cVar : C5071a.this.f3371a.keySet()) {
                            if (((C5085g) C5071a.this.f3371a.get(cVar)) == gVar) {
                                obj = cVar;
                            }
                        }
                        if (obj != null) {
                            C5071a.this.f3371a.remove(obj);
                        }
                    }
                }
            };
        }
        return this.f3376g;
    }

    /* renamed from: a */
    public String mo62458a(AdMode adMode) {
        if (adMode == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("autoLoadNotShownAdPrefix");
        sb.append(adMode.name());
        return sb.toString();
    }
}
