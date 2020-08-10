package com.startapp.android.publish.ads.p067b;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.activities.AppWallActivity;
import com.startapp.android.publish.adsCommon.activities.FullScreenActivity;
import com.startapp.android.publish.adsCommon.activities.OverlayActivity;
import com.startapp.android.publish.common.model.AdPreferences.Placement;

/* renamed from: com.startapp.android.publish.ads.b.c */
/* compiled from: StartAppSDK */
public abstract class C4759c extends C5003e implements C5021g {
    private static final long serialVersionUID = 1;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61220a() {
        return false;
    }

    public C4759c(Context context, Placement placement) {
        super(context, placement);
    }

    /* JADX WARNING: type inference failed for: r10v5, types: [java.lang.Boolean[], java.io.Serializable] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v5, types: [java.lang.Boolean[], java.io.Serializable]
      assigns: [java.lang.Boolean[]]
      uses: [java.io.Serializable]
      mth insns count: 131
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo61221a(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = com.startapp.android.publish.adsCommon.C4988c.m3121b()
            boolean r1 = r9.mo61220a()
            r2 = 0
            if (r1 == 0) goto L_0x002d
            com.startapp.android.publish.adsCommon.b r1 = com.startapp.android.publish.adsCommon.C4983b.m3032a()
            com.startapp.android.publish.adsCommon.n r1 = r1.mo62154H()
            com.startapp.android.publish.adsCommon.n$a r1 = r1.mo62422a()
            com.startapp.android.publish.adsCommon.n$a r3 = com.startapp.android.publish.adsCommon.C5067n.C5068a.DISABLED
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x002d
            java.lang.String r1 = "back"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x002d
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r10 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.VIDEO_BACK
            r9.setNotDisplayedReason(r10)
            return r2
        L_0x002d:
            java.lang.Boolean r1 = com.startapp.android.publish.adsCommon.AdsConstants.OVERRIDE_NETWORK
            boolean r1 = r1.booleanValue()
            if (r1 != 0) goto L_0x003a
            com.startapp.android.publish.adsCommon.Ad$AdState r1 = com.startapp.android.publish.adsCommon.C4925Ad.AdState.UN_INITIALIZED
            r9.setState(r1)
        L_0x003a:
            java.lang.String r1 = r9.mo62243f()
            if (r1 != 0) goto L_0x0046
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r10 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.INTERNAL_ERROR
            r9.setNotDisplayedReason(r10)
            return r2
        L_0x0046:
            boolean r1 = r9.hasAdCacheTtlPassed()
            if (r1 == 0) goto L_0x0052
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r10 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.AD_EXPIRED
            r9.setNotDisplayedReason(r10)
            return r2
        L_0x0052:
            com.startapp.android.publish.adsCommon.a r1 = r9.activityExtra
            if (r1 == 0) goto L_0x005d
            com.startapp.android.publish.adsCommon.a r1 = r9.activityExtra
            boolean r1 = r1.mo62047a()
            goto L_0x005e
        L_0x005d:
            r1 = 0
        L_0x005e:
            android.content.Intent r3 = new android.content.Intent
            android.content.Context r4 = r9.context
            java.lang.Class r5 = r9.m2289f(r0)
            r3.<init>(r4, r5)
            java.lang.String r4 = "fileUrl"
            java.lang.String r5 = "exit.html"
            r3.putExtra(r4, r5)
            java.lang.String[] r4 = r9.mo62249l()
            java.lang.String r5 = com.startapp.android.publish.adsCommon.C4988c.m3095a()
            r6 = 0
        L_0x0079:
            int r7 = r4.length
            if (r6 >= r7) goto L_0x00a0
            r7 = r4[r6]
            if (r7 == 0) goto L_0x009d
            r7 = r4[r6]
            java.lang.String r8 = ""
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x009d
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            r8 = r4[r6]
            r7.append(r8)
            r7.append(r5)
            java.lang.String r7 = r7.toString()
            r4[r6] = r7
        L_0x009d:
            int r6 = r6 + 1
            goto L_0x0079
        L_0x00a0:
            java.lang.String r5 = "tracking"
            r3.putExtra(r5, r4)
            java.lang.String[] r4 = r9.mo62250m()
            java.lang.String r5 = "trackingClickUrl"
            r3.putExtra(r5, r4)
            java.lang.String[] r4 = r9.mo62252o()
            java.lang.String r5 = "packageNames"
            r3.putExtra(r5, r4)
            java.lang.String r4 = r9.mo62244g()
            java.lang.String r5 = "htmlUuid"
            r3.putExtra(r5, r4)
            boolean[] r4 = r9.smartRedirect
            java.lang.String r5 = "smartRedirect"
            r3.putExtra(r5, r4)
            boolean[] r4 = r9.mo62248k()
            java.lang.String r5 = "browserEnabled"
            r3.putExtra(r5, r4)
            com.startapp.android.publish.common.model.AdPreferences$Placement r4 = r9.placement
            int r4 = r4.getIndex()
            java.lang.String r5 = "placement"
            r3.putExtra(r5, r4)
            com.startapp.android.publish.adsCommon.adinformation.c r4 = r9.getAdInfoOverride()
            java.lang.String r5 = "adInfoOverride"
            r3.putExtra(r5, r4)
            java.lang.String r4 = "ad"
            r3.putExtra(r4, r9)
            boolean r4 = r9.mo61220a()
            java.lang.String r5 = "videoAd"
            r3.putExtra(r5, r4)
            java.lang.String r4 = "fullscreen"
            r3.putExtra(r4, r1)
            int r1 = r9.mo61223b()
            java.lang.String r4 = "orientation"
            r3.putExtra(r4, r1)
            java.lang.String r1 = "adTag"
            r3.putExtra(r1, r10)
            java.lang.Long r10 = r9.getLastLoadTime()
            java.lang.String r1 = "lastLoadTime"
            r3.putExtra(r1, r10)
            java.lang.Long r10 = r9.getAdCacheTtl()
            java.lang.String r1 = "adCacheTtl"
            r3.putExtra(r1, r10)
            java.lang.String[] r10 = r9.mo62246i()
            java.lang.String r1 = "closingUrl"
            r3.putExtra(r1, r10)
            java.lang.Long r10 = r9.mo62253p()
            if (r10 == 0) goto L_0x012f
            java.lang.Long r10 = r9.mo62253p()
            java.lang.String r1 = "delayImpressionSeconds"
            r3.putExtra(r1, r10)
        L_0x012f:
            java.lang.Boolean[] r10 = r9.mo62254q()
            java.lang.String r1 = "sendRedirectHops"
            r3.putExtra(r1, r10)
            boolean r10 = r9.mo62255r()
            java.lang.String r1 = "mraidAd"
            r3.putExtra(r1, r10)
            boolean r10 = r9.mo62255r()
            if (r10 == 0) goto L_0x014c
            java.lang.String r10 = "activityShouldLockOrientation"
            r3.putExtra(r10, r2)
        L_0x014c:
            r1 = 8
            boolean r10 = com.startapp.android.publish.adsCommon.Utils.C4946i.m2923a(r1)
            r1 = 1
            if (r10 == 0) goto L_0x015e
            boolean r10 = r9 instanceof com.startapp.android.publish.ads.splash.C4824b
            if (r10 == 0) goto L_0x015e
            java.lang.String r10 = "isSplash"
            r3.putExtra(r10, r1)
        L_0x015e:
            java.lang.String r10 = "position"
            r3.putExtra(r10, r0)
            r10 = 343932928(0x14800000, float:1.2924697E-26)
            r3.addFlags(r10)
            android.content.Context r10 = r9.context
            r10.startActivity(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.ads.p067b.C4759c.mo61221a(java.lang.String):boolean");
    }

    /* renamed from: f */
    private Class<?> m2289f(String str) {
        if (m2290g(str)) {
            return FullScreenActivity.class;
        }
        return C4946i.m2903a(getContext(), OverlayActivity.class, AppWallActivity.class);
    }

    /* renamed from: g */
    private boolean m2290g(String str) {
        return (mo61770d() || mo61220a() || mo62255r() || str.equals("back")) && C4946i.m2925a(getContext(), FullScreenActivity.class);
    }

    /* renamed from: d */
    private boolean mo61770d() {
        return (mo62251n() == 0 || mo62251n() == this.context.getResources().getConfiguration().orientation) ? false : true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public int mo61223b() {
        return mo62251n() == 0 ? this.context.getResources().getConfiguration().orientation : mo62251n();
    }

    /* renamed from: a_ */
    public String mo61222a_() {
        return super.mo61222a_();
    }

    public Long getLastLoadTime() {
        return super.getLastLoadTime();
    }

    public Long getAdCacheTtl() {
        return super.getAdCacheTtl();
    }

    public boolean hasAdCacheTtlPassed() {
        return super.hasAdCacheTtlPassed();
    }

    public boolean getVideoCancelCallBack() {
        return super.getVideoCancelCallBack();
    }

    public void setVideoCancelCallBack(boolean z) {
        super.setVideoCancelCallBack(z);
    }
}
