package com.startapp.android.publish.common.metaData;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.ads.banner.C4786c;
import com.startapp.android.publish.ads.splash.C4834f;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adinformation.C4968a;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5016e;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.cache.C5081d;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5155g;
import java.net.UnknownHostException;

/* renamed from: com.startapp.android.publish.common.metaData.c */
/* compiled from: StartAppSDK */
public class C5113c {

    /* renamed from: a */
    private final Context f3460a;

    /* renamed from: b */
    private final AdPreferences f3461b;

    /* renamed from: c */
    private C5109a f3462c;

    /* renamed from: d */
    private MetaData f3463d = null;

    /* renamed from: e */
    private C4786c f3464e = null;

    /* renamed from: f */
    private C4834f f3465f = null;

    /* renamed from: g */
    private C5081d f3466g = null;

    /* renamed from: h */
    private C4968a f3467h = null;

    /* renamed from: i */
    private C4983b f3468i = null;

    /* renamed from: j */
    private boolean f3469j = false;

    public C5113c(Context context, AdPreferences adPreferences, C5109a aVar) {
        this.f3460a = context;
        this.f3461b = adPreferences;
        this.f3462c = aVar;
    }

    /* renamed from: a */
    public void mo62644a() {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                final Boolean c = C5113c.this.mo62647c();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        C5113c.this.mo62645a(c);
                    }
                });
            }
        });
    }

    /* renamed from: b */
    public void mo62646b() {
        this.f3469j = true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public Boolean mo62647c() {
        C5155g.m3805a(3, "Loading MetaData");
        MetaDataRequest metaDataRequest = new MetaDataRequest(this.f3460a, this.f3462c);
        try {
            metaDataRequest.fillApplicationDetails(this.f3460a, this.f3461b, false);
            metaDataRequest.fillLocationDetails(this.f3461b, this.f3460a);
            C5155g.m3805a(3, "Networking MetaData");
            String a = C5052a.m3348a(this.f3460a, AdsConstants.m2856a(ServiceApiType.METADATA), metaDataRequest, null).mo62865a();
            this.f3463d = (MetaData) C4946i.m2905a(a, MetaData.class);
            if (!C4946i.m2922a()) {
                this.f3468i = (C4983b) C4946i.m2905a(a, C4983b.class);
                if (C4946i.m2923a(16) || C4946i.m2923a(32)) {
                    this.f3464e = (C4786c) C4946i.m2905a(a, C4786c.class);
                }
                if (C4946i.m2923a(8)) {
                    this.f3465f = (C4834f) C4946i.m2905a(a, C4834f.class);
                }
                if (C4946i.m2923a(512)) {
                    this.f3466g = (C5081d) C4946i.m2905a(a, C5081d.class);
                }
                if (C4946i.m2935e()) {
                    this.f3467h = (C4968a) C4946i.m2905a(a, C4968a.class);
                }
            }
            m3629d();
            return Boolean.TRUE;
        } catch (Exception e) {
            C5155g.m3806a(6, "Unable to handle GetMetaData command!!!!", (Throwable) e);
            if (!(e instanceof UnknownHostException) || !e.getMessage().contains("init.startappservice.com")) {
                C5017f.m3256a(this.f3460a, C5015d.EXCEPTION, "GetMetaDataAsync.doInBackground - MetaData request failed", e.getMessage(), "");
            }
            return Boolean.FALSE;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:2|3|(4:9|(10:11|12|17|(2:21|22)|27|(2:29|30)|35|(2:37|38)|43|(2:45|46))|51|52)|53|54) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x00a2 */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3629d() {
        /*
            r3 = this;
            java.lang.Object r0 = com.startapp.android.publish.common.metaData.MetaData.getLock()
            monitor-enter(r0)
            boolean r1 = r3.f3469j     // Catch:{ all -> 0x00a4 }
            if (r1 != 0) goto L_0x00a2
            com.startapp.android.publish.common.metaData.MetaData r1 = r3.f3463d     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x00a2
            android.content.Context r1 = r3.f3460a     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x00a2
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2922a()     // Catch:{ all -> 0x00a4 }
            if (r1 != 0) goto L_0x0097
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x001f }
            com.startapp.android.publish.adsCommon.b r2 = r3.f3468i     // Catch:{ Exception -> 0x001f }
            com.startapp.android.publish.adsCommon.C4983b.m3034a(r1, r2)     // Catch:{ Exception -> 0x001f }
            goto L_0x0029
        L_0x001f:
            r1 = move-exception
            java.lang.String r2 = "GetMetaDataAsyncTask-adscommon update fail"
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.m3628a(r2, r1)     // Catch:{ all -> 0x00a4 }
        L_0x0029:
            r1 = 16
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2923a(r1)     // Catch:{ all -> 0x00a4 }
            if (r1 != 0) goto L_0x0039
            r1 = 32
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2923a(r1)     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x004b
        L_0x0039:
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x0041 }
            com.startapp.android.publish.ads.banner.c r2 = r3.f3464e     // Catch:{ Exception -> 0x0041 }
            com.startapp.android.publish.ads.banner.C4786c.m2358a(r1, r2)     // Catch:{ Exception -> 0x0041 }
            goto L_0x004b
        L_0x0041:
            r1 = move-exception
            java.lang.String r2 = "GetMetaDataAsyncTask-banner update fail"
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.m3628a(r2, r1)     // Catch:{ all -> 0x00a4 }
        L_0x004b:
            r1 = 8
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2923a(r1)     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x0065
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x005b }
            com.startapp.android.publish.ads.splash.f r2 = r3.f3465f     // Catch:{ Exception -> 0x005b }
            com.startapp.android.publish.ads.splash.C4834f.m2518a(r1, r2)     // Catch:{ Exception -> 0x005b }
            goto L_0x0065
        L_0x005b:
            r1 = move-exception
            java.lang.String r2 = "GetMetaDataAsyncTask-splash update fail"
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.m3628a(r2, r1)     // Catch:{ all -> 0x00a4 }
        L_0x0065:
            r1 = 512(0x200, double:2.53E-321)
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2923a(r1)     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x007f
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x0075 }
            com.startapp.android.publish.cache.d r2 = r3.f3466g     // Catch:{ Exception -> 0x0075 }
            com.startapp.android.publish.cache.C5081d.m3534a(r1, r2)     // Catch:{ Exception -> 0x0075 }
            goto L_0x007f
        L_0x0075:
            r1 = move-exception
            java.lang.String r2 = "GetMetaDataAsyncTask-cache update fail"
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.m3628a(r2, r1)     // Catch:{ all -> 0x00a4 }
        L_0x007f:
            boolean r1 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2935e()     // Catch:{ all -> 0x00a4 }
            if (r1 == 0) goto L_0x0097
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x008d }
            com.startapp.android.publish.adsCommon.adinformation.a r2 = r3.f3467h     // Catch:{ Exception -> 0x008d }
            com.startapp.android.publish.adsCommon.adinformation.C4968a.m2984a(r1, r2)     // Catch:{ Exception -> 0x008d }
            goto L_0x0097
        L_0x008d:
            r1 = move-exception
            java.lang.String r2 = "GetMetaDataAsyncTask-adInfo update fail"
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x00a4 }
            r3.m3628a(r2, r1)     // Catch:{ all -> 0x00a4 }
        L_0x0097:
            android.content.Context r1 = r3.f3460a     // Catch:{ Exception -> 0x00a2 }
            com.startapp.android.publish.common.metaData.MetaData r2 = r3.f3463d     // Catch:{ Exception -> 0x00a2 }
            java.lang.String r2 = r2.getAssetsBaseUrl()     // Catch:{ Exception -> 0x00a2 }
            com.startapp.android.publish.common.metaData.MetaData.preCacheResources(r1, r2)     // Catch:{ Exception -> 0x00a2 }
        L_0x00a2:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            return
        L_0x00a4:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.common.metaData.C5113c.m3629d():void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62645a(Boolean bool) {
        synchronized (MetaData.getLock()) {
            if (!this.f3469j) {
                if (!bool.booleanValue() || this.f3463d == null || this.f3460a == null) {
                    MetaData.failedLoading();
                } else {
                    try {
                        MetaData.update(this.f3460a, this.f3463d);
                    } catch (Exception e) {
                        m3628a("GetMetaDataAsyncTask.onPostExecute-metadata update fail", e.getMessage());
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private void m3628a(String str, String str2) {
        C5017f.m3258a(this.f3460a, new C5016e(C5015d.EXCEPTION, str, str2), "");
    }
}
