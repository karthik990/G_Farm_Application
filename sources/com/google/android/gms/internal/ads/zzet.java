package com.google.android.gms.internal.ads;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings.System;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzet implements OnGlobalLayoutListener, OnScrollChangedListener {
    private final Object mLock = new Object();
    private boolean zzaaq = false;
    private zzamj zzadz;
    private final Context zzaeo;
    private final WeakReference<zzajh> zzaeq;
    private WeakReference<ViewTreeObserver> zzaer;
    private final zzgd zzaes;
    protected final zzer zzaet;
    private final WindowManager zzaeu;
    private final PowerManager zzaev;
    private final KeyguardManager zzaew;
    private final DisplayMetrics zzaex;
    private zzfa zzaey;
    private boolean zzaez;
    private boolean zzafa = false;
    private boolean zzafb;
    private boolean zzafc;
    private boolean zzafd;
    private BroadcastReceiver zzafe;
    private final HashSet<zzeq> zzaff = new HashSet<>();
    private final HashSet<zzfo> zzafg = new HashSet<>();
    private final Rect zzafh = new Rect();
    private final zzew zzafi;
    private float zzafj;

    public zzet(Context context, zzjn zzjn, zzajh zzajh, zzang zzang, zzgd zzgd) {
        this.zzaeq = new WeakReference<>(zzajh);
        this.zzaes = zzgd;
        this.zzaer = new WeakReference<>(null);
        this.zzafb = true;
        this.zzafd = false;
        this.zzadz = new zzamj(200);
        zzer zzer = new zzer(UUID.randomUUID().toString(), zzang, zzjn.zzarb, zzajh.zzcob, zzajh.zzfz(), zzjn.zzare);
        this.zzaet = zzer;
        this.zzaeu = (WindowManager) context.getSystemService("window");
        this.zzaev = (PowerManager) context.getApplicationContext().getSystemService("power");
        this.zzaew = (KeyguardManager) context.getSystemService("keyguard");
        this.zzaeo = context;
        this.zzafi = new zzew(this, new Handler());
        this.zzaeo.getContentResolver().registerContentObserver(System.CONTENT_URI, true, this.zzafi);
        this.zzaex = context.getResources().getDisplayMetrics();
        Display defaultDisplay = this.zzaeu.getDefaultDisplay();
        this.zzafh.right = defaultDisplay.getWidth();
        this.zzafh.bottom = defaultDisplay.getHeight();
        zzgb();
    }

    private final boolean isScreenOn() {
        return VERSION.SDK_INT >= 20 ? this.zzaev.isInteractive() : this.zzaev.isScreenOn();
    }

    private static int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    private final JSONObject zza(View view, Boolean bool) throws JSONException {
        View view2 = view;
        String str = "isVisible";
        String str2 = "isAttachedToWindow";
        if (view2 == null) {
            return zzgg().put(str2, false).put("isScreenOn", isScreenOn()).put(str, false);
        }
        boolean isAttachedToWindow = zzbv.zzem().isAttachedToWindow(view2);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view2.getLocationOnScreen(iArr);
            view2.getLocationInWindow(iArr2);
        } catch (Exception e) {
            zzakb.zzb("Failure getting view location.", e);
        }
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        boolean globalVisibleRect = view2.getGlobalVisibleRect(rect2, null);
        Rect rect3 = new Rect();
        boolean localVisibleRect = view2.getLocalVisibleRect(rect3);
        Rect rect4 = new Rect();
        view2.getHitRect(rect4);
        JSONObject zzgg = zzgg();
        JSONObject put = zzgg.put("windowVisibility", view.getWindowVisibility()).put(str2, isAttachedToWindow);
        String str3 = "top";
        String str4 = "bottom";
        JSONObject put2 = new JSONObject().put(str3, zza(this.zzafh.top, this.zzaex)).put(str4, zza(this.zzafh.bottom, this.zzaex));
        int zza = zza(this.zzafh.left, this.zzaex);
        String str5 = TtmlNode.LEFT;
        JSONObject put3 = put2.put(str5, zza);
        String str6 = str;
        int zza2 = zza(this.zzafh.right, this.zzaex);
        String str7 = TtmlNode.RIGHT;
        JSONObject jSONObject = zzgg;
        put.put("viewBox", put3.put(str7, zza2)).put("adBox", new JSONObject().put(str3, zza(rect.top, this.zzaex)).put(str4, zza(rect.bottom, this.zzaex)).put(str5, zza(rect.left, this.zzaex)).put(str7, zza(rect.right, this.zzaex))).put("globalVisibleBox", new JSONObject().put(str3, zza(rect2.top, this.zzaex)).put(str4, zza(rect2.bottom, this.zzaex)).put(str5, zza(rect2.left, this.zzaex)).put(str7, zza(rect2.right, this.zzaex))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put(str3, zza(rect3.top, this.zzaex)).put(str4, zza(rect3.bottom, this.zzaex)).put(str5, zza(rect3.left, this.zzaex)).put(str7, zza(rect3.right, this.zzaex))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put(str3, zza(rect4.top, this.zzaex)).put(str4, zza(rect4.bottom, this.zzaex)).put(str5, zza(rect4.left, this.zzaex)).put(str7, zza(rect4.right, this.zzaex))).put("screenDensity", (double) this.zzaex.density);
        Boolean valueOf = bool == null ? Boolean.valueOf(zzbv.zzek().zza(view2, this.zzaev, this.zzaew)) : bool;
        JSONObject jSONObject2 = jSONObject;
        jSONObject2.put(str6, valueOf.booleanValue());
        return jSONObject2;
    }

    private static JSONObject zza(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        return jSONObject2;
    }

    private final void zza(JSONObject jSONObject, boolean z) {
        try {
            JSONObject zza = zza(jSONObject);
            ArrayList arrayList = new ArrayList(this.zzafg);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzfo) obj).zzb(zza, z);
            }
        } catch (Throwable th) {
            zzakb.zzb("Skipping active view message.", th);
        }
    }

    private final void zzgd() {
        zzfa zzfa = this.zzaey;
        if (zzfa != null) {
            zzfa.zza(this);
        }
    }

    private final void zzgf() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzaer.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    private final JSONObject zzgg() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = "activeViewJSON";
        String str2 = "adFormat";
        String str3 = "hashCode";
        String str4 = "isMraid";
        String str5 = "isStopped";
        String str6 = "isPaused";
        String str7 = "isNative";
        String str8 = "isScreenOn";
        String str9 = "appMuted";
        String str10 = "appVolume";
        String str11 = "deviceVolume";
        jSONObject.put("afmaVersion", this.zzaet.zzfw()).put(str, this.zzaet.zzfx()).put(Param.TIMESTAMP, zzbv.zzer().elapsedRealtime()).put(str2, this.zzaet.zzfv()).put(str3, this.zzaet.zzfy()).put(str4, this.zzaet.zzfz()).put(str5, this.zzafa).put(str6, this.zzaaq).put(str7, this.zzaet.zzga()).put(str8, isScreenOn()).put(str9, zzbv.zzfj().zzdp()).put(str10, (double) zzbv.zzfj().zzdo()).put(str11, (double) this.zzafj);
        return jSONObject;
    }

    public final void onGlobalLayout() {
        zzl(2);
    }

    public final void onScrollChanged() {
        zzl(1);
    }

    public final void pause() {
        synchronized (this.mLock) {
            this.zzaaq = true;
            zzl(3);
        }
    }

    public final void resume() {
        synchronized (this.mLock) {
            this.zzaaq = false;
            zzl(3);
        }
    }

    public final void stop() {
        synchronized (this.mLock) {
            this.zzafa = true;
            zzl(3);
        }
    }

    public final void zza(zzfa zzfa) {
        synchronized (this.mLock) {
            this.zzaey = zzfa;
        }
    }

    public final void zza(zzfo zzfo) {
        if (this.zzafg.isEmpty()) {
            synchronized (this.mLock) {
                if (this.zzafe == null) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SCREEN_ON");
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    this.zzafe = new zzeu(this);
                    zzbv.zzfk().zza(this.zzaeo, this.zzafe, intentFilter);
                }
            }
            zzl(3);
        }
        this.zzafg.add(zzfo);
        try {
            zzfo.zzb(zza(zza(this.zzaes.zzgh(), (Boolean) null)), false);
        } catch (JSONException e) {
            zzakb.zzb("Skipping measurement update for new client.", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void zza(zzfo zzfo, Map<String, String> map) {
        String valueOf = String.valueOf(this.zzaet.zzfy());
        String str = "Received request to untrack: ";
        zzakb.zzck(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        zzb(zzfo);
    }

    public final void zzb(zzfo zzfo) {
        this.zzafg.remove(zzfo);
        zzfo.zzgl();
        if (this.zzafg.isEmpty()) {
            synchronized (this.mLock) {
                zzgf();
                synchronized (this.mLock) {
                    if (this.zzafe != null) {
                        try {
                            zzbv.zzfk().zza(this.zzaeo, this.zzafe);
                        } catch (IllegalStateException e) {
                            zzakb.zzb("Failed trying to unregister the receiver", e);
                        } catch (Exception e2) {
                            zzbv.zzeo().zza(e2, "ActiveViewUnit.stopScreenStatusMonitoring");
                        }
                        this.zzafe = null;
                    }
                }
                this.zzaeo.getContentResolver().unregisterContentObserver(this.zzafi);
                int i = 0;
                this.zzafb = false;
                zzgd();
                ArrayList arrayList = new ArrayList(this.zzafg);
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzb((zzfo) obj);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzc(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = (String) map.get("hashCode");
        return !TextUtils.isEmpty(str) && str.equals(this.zzaet.zzfy());
    }

    /* access modifiers changed from: 0000 */
    public final void zzd(Map<String, String> map) {
        zzl(3);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031 A[LOOP:0: B:10:0x002b->B:12:0x0031, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zze(java.util.Map<java.lang.String, java.lang.String> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "isVisible"
            boolean r1 = r4.containsKey(r0)
            if (r1 != 0) goto L_0x0009
            return
        L_0x0009:
            java.lang.Object r1 = r4.get(r0)
            java.lang.String r2 = "1"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0024
            java.lang.Object r4 = r4.get(r0)
            java.lang.String r0 = "true"
            boolean r4 = r0.equals(r4)
            if (r4 == 0) goto L_0x0022
            goto L_0x0024
        L_0x0022:
            r4 = 0
            goto L_0x0025
        L_0x0024:
            r4 = 1
        L_0x0025:
            java.util.HashSet<com.google.android.gms.internal.ads.zzeq> r0 = r3.zzaff
            java.util.Iterator r0 = r0.iterator()
        L_0x002b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x003b
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.ads.zzeq r1 = (com.google.android.gms.internal.ads.zzeq) r1
            r1.zza(r3, r4)
            goto L_0x002b
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzet.zze(java.util.Map):void");
    }

    public final void zzgb() {
        this.zzafj = zzalb.zzay(this.zzaeo);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0036 A[Catch:{ JSONException -> 0x0020, RuntimeException -> 0x0019 }] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003b A[Catch:{ JSONException -> 0x0020, RuntimeException -> 0x0019 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzgc() {
        /*
            r5 = this;
            java.lang.Object r0 = r5.mLock
            monitor-enter(r0)
            boolean r1 = r5.zzafb     // Catch:{ all -> 0x0046 }
            if (r1 == 0) goto L_0x0044
            r1 = 1
            r5.zzafc = r1     // Catch:{ all -> 0x0046 }
            org.json.JSONObject r2 = r5.zzgg()     // Catch:{ JSONException -> 0x0020, RuntimeException -> 0x0019 }
            java.lang.String r3 = "doneReasonCode"
            java.lang.String r4 = "u"
            r2.put(r3, r4)     // Catch:{ JSONException -> 0x0020, RuntimeException -> 0x0019 }
            r5.zza(r2, r1)     // Catch:{ JSONException -> 0x0020, RuntimeException -> 0x0019 }
            goto L_0x0024
        L_0x0019:
            r1 = move-exception
            java.lang.String r2 = "Failure while processing active view data."
        L_0x001c:
            com.google.android.gms.internal.ads.zzakb.zzb(r2, r1)     // Catch:{ all -> 0x0046 }
            goto L_0x0024
        L_0x0020:
            r1 = move-exception
            java.lang.String r2 = "JSON failure while processing active view data."
            goto L_0x001c
        L_0x0024:
            java.lang.String r1 = "Untracking ad unit: "
            com.google.android.gms.internal.ads.zzer r2 = r5.zzaet     // Catch:{ all -> 0x0046 }
            java.lang.String r2 = r2.zzfy()     // Catch:{ all -> 0x0046 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0046 }
            int r3 = r2.length()     // Catch:{ all -> 0x0046 }
            if (r3 == 0) goto L_0x003b
            java.lang.String r1 = r1.concat(r2)     // Catch:{ all -> 0x0046 }
            goto L_0x0041
        L_0x003b:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x0046 }
            r2.<init>(r1)     // Catch:{ all -> 0x0046 }
            r1 = r2
        L_0x0041:
            com.google.android.gms.internal.ads.zzakb.zzck(r1)     // Catch:{ all -> 0x0046 }
        L_0x0044:
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            return
        L_0x0046:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            goto L_0x004a
        L_0x0049:
            throw r1
        L_0x004a:
            goto L_0x0049
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzet.zzgc():void");
    }

    public final boolean zzge() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzafb;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00cf, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzl(int r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            java.util.HashSet<com.google.android.gms.internal.ads.zzfo> r1 = r7.zzafg     // Catch:{ all -> 0x00d0 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00d0 }
        L_0x0009:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x00d0 }
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x001f
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x00d0 }
            com.google.android.gms.internal.ads.zzfo r2 = (com.google.android.gms.internal.ads.zzfo) r2     // Catch:{ all -> 0x00d0 }
            boolean r2 = r2.zzgk()     // Catch:{ all -> 0x00d0 }
            if (r2 == 0) goto L_0x0009
            r1 = 1
            goto L_0x0020
        L_0x001f:
            r1 = 0
        L_0x0020:
            if (r1 == 0) goto L_0x00ce
            boolean r1 = r7.zzafb     // Catch:{ all -> 0x00d0 }
            if (r1 != 0) goto L_0x0028
            goto L_0x00ce
        L_0x0028:
            com.google.android.gms.internal.ads.zzgd r1 = r7.zzaes     // Catch:{ all -> 0x00d0 }
            android.view.View r1 = r1.zzgh()     // Catch:{ all -> 0x00d0 }
            if (r1 == 0) goto L_0x0040
            com.google.android.gms.internal.ads.zzakk r2 = com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x00d0 }
            android.os.PowerManager r5 = r7.zzaev     // Catch:{ all -> 0x00d0 }
            android.app.KeyguardManager r6 = r7.zzaew     // Catch:{ all -> 0x00d0 }
            boolean r2 = r2.zza(r1, r5, r6)     // Catch:{ all -> 0x00d0 }
            if (r2 == 0) goto L_0x0040
            r2 = 1
            goto L_0x0041
        L_0x0040:
            r2 = 0
        L_0x0041:
            if (r1 == 0) goto L_0x0053
            if (r2 == 0) goto L_0x0053
            android.graphics.Rect r5 = new android.graphics.Rect     // Catch:{ all -> 0x00d0 }
            r5.<init>()     // Catch:{ all -> 0x00d0 }
            r6 = 0
            boolean r5 = r1.getGlobalVisibleRect(r5, r6)     // Catch:{ all -> 0x00d0 }
            if (r5 == 0) goto L_0x0053
            r5 = 1
            goto L_0x0054
        L_0x0053:
            r5 = 0
        L_0x0054:
            com.google.android.gms.internal.ads.zzgd r6 = r7.zzaes     // Catch:{ all -> 0x00d0 }
            boolean r6 = r6.zzgi()     // Catch:{ all -> 0x00d0 }
            if (r6 == 0) goto L_0x0061
            r7.zzgc()     // Catch:{ all -> 0x00d0 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            return
        L_0x0061:
            if (r8 != r4) goto L_0x0071
            com.google.android.gms.internal.ads.zzamj r6 = r7.zzadz     // Catch:{ all -> 0x00d0 }
            boolean r6 = r6.tryAcquire()     // Catch:{ all -> 0x00d0 }
            if (r6 != 0) goto L_0x0071
            boolean r6 = r7.zzafd     // Catch:{ all -> 0x00d0 }
            if (r5 != r6) goto L_0x0071
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            return
        L_0x0071:
            if (r5 != 0) goto L_0x007b
            boolean r6 = r7.zzafd     // Catch:{ all -> 0x00d0 }
            if (r6 != 0) goto L_0x007b
            if (r8 != r4) goto L_0x007b
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            return
        L_0x007b:
            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r2)     // Catch:{ JSONException -> 0x008b, RuntimeException -> 0x0089 }
            org.json.JSONObject r8 = r7.zza(r1, r8)     // Catch:{ JSONException -> 0x008b, RuntimeException -> 0x0089 }
            r7.zza(r8, r3)     // Catch:{ JSONException -> 0x008b, RuntimeException -> 0x0089 }
            r7.zzafd = r5     // Catch:{ JSONException -> 0x008b, RuntimeException -> 0x0089 }
            goto L_0x0091
        L_0x0089:
            r8 = move-exception
            goto L_0x008c
        L_0x008b:
            r8 = move-exception
        L_0x008c:
            java.lang.String r1 = "Active view update failed."
            com.google.android.gms.internal.ads.zzakb.zza(r1, r8)     // Catch:{ all -> 0x00d0 }
        L_0x0091:
            com.google.android.gms.internal.ads.zzgd r8 = r7.zzaes     // Catch:{ all -> 0x00d0 }
            com.google.android.gms.internal.ads.zzgd r8 = r8.zzgj()     // Catch:{ all -> 0x00d0 }
            android.view.View r8 = r8.zzgh()     // Catch:{ all -> 0x00d0 }
            if (r8 == 0) goto L_0x00c9
            java.lang.ref.WeakReference<android.view.ViewTreeObserver> r1 = r7.zzaer     // Catch:{ all -> 0x00d0 }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x00d0 }
            android.view.ViewTreeObserver r1 = (android.view.ViewTreeObserver) r1     // Catch:{ all -> 0x00d0 }
            android.view.ViewTreeObserver r8 = r8.getViewTreeObserver()     // Catch:{ all -> 0x00d0 }
            if (r8 == r1) goto L_0x00c9
            r7.zzgf()     // Catch:{ all -> 0x00d0 }
            boolean r2 = r7.zzaez     // Catch:{ all -> 0x00d0 }
            if (r2 == 0) goto L_0x00ba
            if (r1 == 0) goto L_0x00c2
            boolean r1 = r1.isAlive()     // Catch:{ all -> 0x00d0 }
            if (r1 == 0) goto L_0x00c2
        L_0x00ba:
            r7.zzaez = r4     // Catch:{ all -> 0x00d0 }
            r8.addOnScrollChangedListener(r7)     // Catch:{ all -> 0x00d0 }
            r8.addOnGlobalLayoutListener(r7)     // Catch:{ all -> 0x00d0 }
        L_0x00c2:
            java.lang.ref.WeakReference r1 = new java.lang.ref.WeakReference     // Catch:{ all -> 0x00d0 }
            r1.<init>(r8)     // Catch:{ all -> 0x00d0 }
            r7.zzaer = r1     // Catch:{ all -> 0x00d0 }
        L_0x00c9:
            r7.zzgd()     // Catch:{ all -> 0x00d0 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            return
        L_0x00ce:
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            return
        L_0x00d0:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d0 }
            goto L_0x00d4
        L_0x00d3:
            throw r8
        L_0x00d4:
            goto L_0x00d3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzet.zzl(int):void");
    }
}
