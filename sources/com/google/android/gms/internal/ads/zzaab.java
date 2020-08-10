package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.common.util.CollectionUtils;
import java.util.Set;
import org.apache.http.client.config.CookieSpecs;

@zzadh
public final class zzaab extends zzaal {
    private static final Set<String> zzbvy = CollectionUtils.setOf("top-left", "top-right", "top-center", TtmlNode.CENTER, "bottom-left", "bottom-right", "bottom-center");
    private final Object mLock = new Object();
    private zzaam zzbmy;
    private final zzaqw zzbnd;
    private final Activity zzbvp;
    private String zzbvz = "top-right";
    private boolean zzbwa = true;
    private int zzbwb = 0;
    private int zzbwc = 0;
    private int zzbwd = 0;
    private int zzbwe = 0;
    private zzasi zzbwf;
    private ImageView zzbwg;
    private LinearLayout zzbwh;
    private PopupWindow zzbwi;
    private RelativeLayout zzbwj;
    private ViewGroup zzbwk;
    private int zzuq = -1;
    private int zzur = -1;

    public zzaab(zzaqw zzaqw, zzaam zzaam) {
        super(zzaqw, "resize");
        this.zzbnd = zzaqw;
        this.zzbvp = zzaqw.zzto();
        this.zzbmy = zzaam;
    }

    private final void zza(int i, int i2) {
        zzb(i, i2 - zzbv.zzek().zzh(this.zzbvp)[0], this.zzuq, this.zzur);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f3, code lost:
        if ((r5 + 50) <= r1[1]) goto L_0x00f6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e9  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0103  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0105  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int[] zzne() {
        /*
            r12 = this;
            com.google.android.gms.internal.ads.zzakk r0 = com.google.android.gms.ads.internal.zzbv.zzek()
            android.app.Activity r1 = r12.zzbvp
            int[] r0 = r0.zzg(r1)
            com.google.android.gms.internal.ads.zzakk r1 = com.google.android.gms.ads.internal.zzbv.zzek()
            android.app.Activity r2 = r12.zzbvp
            int[] r1 = r1.zzh(r2)
            r2 = 0
            r3 = r0[r2]
            r4 = 1
            r0 = r0[r4]
            int r5 = r12.zzuq
            r6 = 2
            r7 = 50
            if (r5 < r7) goto L_0x00fb
            if (r5 <= r3) goto L_0x0025
            goto L_0x00fb
        L_0x0025:
            int r8 = r12.zzur
            if (r8 < r7) goto L_0x00f8
            if (r8 <= r0) goto L_0x002d
            goto L_0x00f8
        L_0x002d:
            if (r8 != r0) goto L_0x0035
            if (r5 != r3) goto L_0x0035
            java.lang.String r0 = "Cannot resize to a full-screen ad."
            goto L_0x00fd
        L_0x0035:
            boolean r0 = r12.zzbwa
            if (r0 == 0) goto L_0x00f6
            java.lang.String r0 = r12.zzbvz
            r5 = -1
            int r8 = r0.hashCode()
            r9 = 5
            r10 = 4
            r11 = 3
            switch(r8) {
                case -1364013995: goto L_0x0079;
                case -1012429441: goto L_0x006f;
                case -655373719: goto L_0x0065;
                case 1163912186: goto L_0x005b;
                case 1288627767: goto L_0x0051;
                case 1755462605: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x0082
        L_0x0047:
            java.lang.String r8 = "top-center"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 1
            goto L_0x0082
        L_0x0051:
            java.lang.String r8 = "bottom-center"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 4
            goto L_0x0082
        L_0x005b:
            java.lang.String r8 = "bottom-right"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 5
            goto L_0x0082
        L_0x0065:
            java.lang.String r8 = "bottom-left"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 3
            goto L_0x0082
        L_0x006f:
            java.lang.String r8 = "top-left"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 0
            goto L_0x0082
        L_0x0079:
            java.lang.String r8 = "center"
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0082
            r5 = 2
        L_0x0082:
            if (r5 == 0) goto L_0x00e1
            if (r5 == r4) goto L_0x00d5
            if (r5 == r6) goto L_0x00be
            if (r5 == r11) goto L_0x00af
            int r0 = r12.zzbwb
            if (r5 == r10) goto L_0x00a5
            if (r5 == r9) goto L_0x009d
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            int r5 = r12.zzuq
            int r0 = r0 + r5
            int r0 = r0 - r7
        L_0x0097:
            int r5 = r12.zzbwc
            int r8 = r12.zzbwe
            int r5 = r5 + r8
            goto L_0x00e7
        L_0x009d:
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            int r5 = r12.zzuq
            int r0 = r0 + r5
            int r0 = r0 - r7
            goto L_0x00b4
        L_0x00a5:
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            int r5 = r12.zzuq
            int r5 = r5 / r6
            int r0 = r0 + r5
            int r0 = r0 + -25
            goto L_0x00b4
        L_0x00af:
            int r0 = r12.zzbwb
            int r5 = r12.zzbwd
            int r0 = r0 + r5
        L_0x00b4:
            int r5 = r12.zzbwc
            int r8 = r12.zzbwe
            int r5 = r5 + r8
            int r8 = r12.zzur
            int r5 = r5 + r8
            int r5 = r5 - r7
            goto L_0x00e7
        L_0x00be:
            int r0 = r12.zzbwb
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            int r5 = r12.zzuq
            int r5 = r5 / r6
            int r0 = r0 + r5
            int r0 = r0 + -25
            int r5 = r12.zzbwc
            int r8 = r12.zzbwe
            int r5 = r5 + r8
            int r8 = r12.zzur
            int r8 = r8 / r6
            int r5 = r5 + r8
            int r5 = r5 + -25
            goto L_0x00e7
        L_0x00d5:
            int r0 = r12.zzbwb
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            int r5 = r12.zzuq
            int r5 = r5 / r6
            int r0 = r0 + r5
            int r0 = r0 + -25
            goto L_0x0097
        L_0x00e1:
            int r0 = r12.zzbwb
            int r5 = r12.zzbwd
            int r0 = r0 + r5
            goto L_0x0097
        L_0x00e7:
            if (r0 < 0) goto L_0x0100
            int r0 = r0 + r7
            if (r0 > r3) goto L_0x0100
            r0 = r1[r2]
            if (r5 < r0) goto L_0x0100
            int r5 = r5 + r7
            r0 = r1[r4]
            if (r5 <= r0) goto L_0x00f6
            goto L_0x0100
        L_0x00f6:
            r0 = 1
            goto L_0x0101
        L_0x00f8:
            java.lang.String r0 = "Height is too small or too large."
            goto L_0x00fd
        L_0x00fb:
            java.lang.String r0 = "Width is too small or too large."
        L_0x00fd:
            com.google.android.gms.internal.ads.zzakb.zzdk(r0)
        L_0x0100:
            r0 = 0
        L_0x0101:
            if (r0 != 0) goto L_0x0105
            r0 = 0
            return r0
        L_0x0105:
            boolean r0 = r12.zzbwa
            if (r0 == 0) goto L_0x011a
            int[] r0 = new int[r6]
            int r1 = r12.zzbwb
            int r3 = r12.zzbwd
            int r1 = r1 + r3
            r0[r2] = r1
            int r1 = r12.zzbwc
            int r2 = r12.zzbwe
            int r1 = r1 + r2
            r0[r4] = r1
            return r0
        L_0x011a:
            com.google.android.gms.internal.ads.zzakk r0 = com.google.android.gms.ads.internal.zzbv.zzek()
            android.app.Activity r1 = r12.zzbvp
            int[] r0 = r0.zzg(r1)
            com.google.android.gms.internal.ads.zzakk r1 = com.google.android.gms.ads.internal.zzbv.zzek()
            android.app.Activity r3 = r12.zzbvp
            int[] r1 = r1.zzh(r3)
            r0 = r0[r2]
            int r3 = r12.zzbwb
            int r5 = r12.zzbwd
            int r3 = r3 + r5
            int r5 = r12.zzbwc
            int r7 = r12.zzbwe
            int r5 = r5 + r7
            if (r3 >= 0) goto L_0x013e
            r0 = 0
            goto L_0x0147
        L_0x013e:
            int r7 = r12.zzuq
            int r8 = r3 + r7
            if (r8 <= r0) goto L_0x0146
            int r0 = r0 - r7
            goto L_0x0147
        L_0x0146:
            r0 = r3
        L_0x0147:
            r3 = r1[r2]
            if (r5 >= r3) goto L_0x014e
            r5 = r1[r2]
            goto L_0x015a
        L_0x014e:
            int r3 = r12.zzur
            int r7 = r5 + r3
            r8 = r1[r4]
            if (r7 <= r8) goto L_0x015a
            r1 = r1[r4]
            int r5 = r1 - r3
        L_0x015a:
            int[] r1 = new int[r6]
            r1[r2] = r0
            r1[r4] = r5
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaab.zzne():int[]");
    }

    public final void zza(int i, int i2, boolean z) {
        synchronized (this.mLock) {
            this.zzbwb = i;
            this.zzbwc = i2;
            if (this.zzbwi != null && z) {
                int[] zzne = zzne();
                if (zzne != null) {
                    PopupWindow popupWindow = this.zzbwi;
                    zzkb.zzif();
                    int zza = zzamu.zza((Context) this.zzbvp, zzne[0]);
                    zzkb.zzif();
                    popupWindow.update(zza, zzamu.zza((Context) this.zzbvp, zzne[1]), this.zzbwi.getWidth(), this.zzbwi.getHeight());
                    zza(zzne[0], zzne[1]);
                } else {
                    zzm(true);
                }
            }
        }
    }

    public final void zzb(int i, int i2) {
        this.zzbwb = i;
        this.zzbwc = i2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x02b3 A[Catch:{ RuntimeException -> 0x02d4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzk(java.util.Map<java.lang.String, java.lang.String> r17) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            java.lang.Object r2 = r1.mLock
            monitor-enter(r2)
            android.app.Activity r3 = r1.zzbvp     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x0012
            java.lang.String r0 = "Not an activity context. Cannot resize."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0012:
            com.google.android.gms.internal.ads.zzaqw r3 = r1.zzbnd     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzasi r3 = r3.zzud()     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x0021
            java.lang.String r0 = "Webview is not yet available, size is not set."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0021:
            com.google.android.gms.internal.ads.zzaqw r3 = r1.zzbnd     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzasi r3 = r3.zzud()     // Catch:{ all -> 0x032a }
            boolean r3 = r3.zzvs()     // Catch:{ all -> 0x032a }
            if (r3 == 0) goto L_0x0034
            java.lang.String r0 = "Is interstitial. Cannot resize an interstitial."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0034:
            com.google.android.gms.internal.ads.zzaqw r3 = r1.zzbnd     // Catch:{ all -> 0x032a }
            boolean r3 = r3.zzuj()     // Catch:{ all -> 0x032a }
            if (r3 == 0) goto L_0x0043
            java.lang.String r0 = "Cannot resize an expanded banner."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0043:
            java.lang.String r3 = "width"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x0062
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            java.lang.String r3 = "width"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032a }
            int r3 = com.google.android.gms.internal.ads.zzakk.zzcv(r3)     // Catch:{ all -> 0x032a }
            r1.zzuq = r3     // Catch:{ all -> 0x032a }
        L_0x0062:
            java.lang.String r3 = "height"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x0081
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            java.lang.String r3 = "height"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032a }
            int r3 = com.google.android.gms.internal.ads.zzakk.zzcv(r3)     // Catch:{ all -> 0x032a }
            r1.zzur = r3     // Catch:{ all -> 0x032a }
        L_0x0081:
            java.lang.String r3 = "offsetX"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x00a0
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            java.lang.String r3 = "offsetX"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032a }
            int r3 = com.google.android.gms.internal.ads.zzakk.zzcv(r3)     // Catch:{ all -> 0x032a }
            r1.zzbwd = r3     // Catch:{ all -> 0x032a }
        L_0x00a0:
            java.lang.String r3 = "offsetY"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x00bf
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            java.lang.String r3 = "offsetY"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032a }
            int r3 = com.google.android.gms.internal.ads.zzakk.zzcv(r3)     // Catch:{ all -> 0x032a }
            r1.zzbwe = r3     // Catch:{ all -> 0x032a }
        L_0x00bf:
            java.lang.String r3 = "allowOffscreen"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x00db
            java.lang.String r3 = "allowOffscreen"
            java.lang.Object r3 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032a }
            boolean r3 = java.lang.Boolean.parseBoolean(r3)     // Catch:{ all -> 0x032a }
            r1.zzbwa = r3     // Catch:{ all -> 0x032a }
        L_0x00db:
            java.lang.String r3 = "customClosePosition"
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x032a }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x032a }
            boolean r3 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x032a }
            if (r3 != 0) goto L_0x00eb
            r1.zzbvz = r0     // Catch:{ all -> 0x032a }
        L_0x00eb:
            int r0 = r1.zzuq     // Catch:{ all -> 0x032a }
            r3 = 1
            r4 = 0
            if (r0 < 0) goto L_0x00f7
            int r0 = r1.zzur     // Catch:{ all -> 0x032a }
            if (r0 < 0) goto L_0x00f7
            r0 = 1
            goto L_0x00f8
        L_0x00f7:
            r0 = 0
        L_0x00f8:
            if (r0 != 0) goto L_0x0101
            java.lang.String r0 = "Invalid width and height options. Cannot resize."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0101:
            android.app.Activity r0 = r1.zzbvp     // Catch:{ all -> 0x032a }
            android.view.Window r0 = r0.getWindow()     // Catch:{ all -> 0x032a }
            if (r0 == 0) goto L_0x0323
            android.view.View r5 = r0.getDecorView()     // Catch:{ all -> 0x032a }
            if (r5 != 0) goto L_0x0111
            goto L_0x0323
        L_0x0111:
            int[] r5 = r16.zzne()     // Catch:{ all -> 0x032a }
            if (r5 != 0) goto L_0x011e
            java.lang.String r0 = "Resize location out of screen or close button is not visible."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x011e:
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ all -> 0x032a }
            android.app.Activity r6 = r1.zzbvp     // Catch:{ all -> 0x032a }
            int r7 = r1.zzuq     // Catch:{ all -> 0x032a }
            int r6 = com.google.android.gms.internal.ads.zzamu.zza(r6, r7)     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ all -> 0x032a }
            android.app.Activity r7 = r1.zzbvp     // Catch:{ all -> 0x032a }
            int r8 = r1.zzur     // Catch:{ all -> 0x032a }
            int r7 = com.google.android.gms.internal.ads.zzamu.zza(r7, r8)     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r8 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r8 = r8.getView()     // Catch:{ all -> 0x032a }
            android.view.ViewParent r8 = r8.getParent()     // Catch:{ all -> 0x032a }
            if (r8 == 0) goto L_0x031c
            boolean r9 = r8 instanceof android.view.ViewGroup     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x031c
            r9 = r8
            android.view.ViewGroup r9 = (android.view.ViewGroup) r9     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r10 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r10 = r10.getView()     // Catch:{ all -> 0x032a }
            r9.removeView(r10)     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r9 = r1.zzbwi     // Catch:{ all -> 0x032a }
            if (r9 != 0) goto L_0x0183
            android.view.ViewGroup r8 = (android.view.ViewGroup) r8     // Catch:{ all -> 0x032a }
            r1.zzbwk = r8     // Catch:{ all -> 0x032a }
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r8 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r8 = r8.getView()     // Catch:{ all -> 0x032a }
            android.graphics.Bitmap r8 = com.google.android.gms.internal.ads.zzakk.zzs(r8)     // Catch:{ all -> 0x032a }
            android.widget.ImageView r9 = new android.widget.ImageView     // Catch:{ all -> 0x032a }
            android.app.Activity r10 = r1.zzbvp     // Catch:{ all -> 0x032a }
            r9.<init>(r10)     // Catch:{ all -> 0x032a }
            r1.zzbwg = r9     // Catch:{ all -> 0x032a }
            android.widget.ImageView r9 = r1.zzbwg     // Catch:{ all -> 0x032a }
            r9.setImageBitmap(r8)     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r8 = r1.zzbnd     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzasi r8 = r8.zzud()     // Catch:{ all -> 0x032a }
            r1.zzbwf = r8     // Catch:{ all -> 0x032a }
            android.view.ViewGroup r8 = r1.zzbwk     // Catch:{ all -> 0x032a }
            android.widget.ImageView r9 = r1.zzbwg     // Catch:{ all -> 0x032a }
            r8.addView(r9)     // Catch:{ all -> 0x032a }
            goto L_0x0188
        L_0x0183:
            android.widget.PopupWindow r8 = r1.zzbwi     // Catch:{ all -> 0x032a }
            r8.dismiss()     // Catch:{ all -> 0x032a }
        L_0x0188:
            android.widget.RelativeLayout r8 = new android.widget.RelativeLayout     // Catch:{ all -> 0x032a }
            android.app.Activity r9 = r1.zzbvp     // Catch:{ all -> 0x032a }
            r8.<init>(r9)     // Catch:{ all -> 0x032a }
            r1.zzbwj = r8     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r8 = r1.zzbwj     // Catch:{ all -> 0x032a }
            r8.setBackgroundColor(r4)     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r8 = r1.zzbwj     // Catch:{ all -> 0x032a }
            android.view.ViewGroup$LayoutParams r9 = new android.view.ViewGroup$LayoutParams     // Catch:{ all -> 0x032a }
            r9.<init>(r6, r7)     // Catch:{ all -> 0x032a }
            r8.setLayoutParams(r9)     // Catch:{ all -> 0x032a }
            com.google.android.gms.ads.internal.zzbv.zzek()     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r8 = r1.zzbwj     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r8 = com.google.android.gms.internal.ads.zzakk.zza(r8, r6, r7, r4)     // Catch:{ all -> 0x032a }
            r1.zzbwi = r8     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r8 = r1.zzbwi     // Catch:{ all -> 0x032a }
            r8.setOutsideTouchable(r3)     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r8 = r1.zzbwi     // Catch:{ all -> 0x032a }
            r8.setTouchable(r3)     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r8 = r1.zzbwi     // Catch:{ all -> 0x032a }
            boolean r9 = r1.zzbwa     // Catch:{ all -> 0x032a }
            if (r9 != 0) goto L_0x01bd
            r9 = 1
            goto L_0x01be
        L_0x01bd:
            r9 = 0
        L_0x01be:
            r8.setClippingEnabled(r9)     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r8 = r1.zzbwj     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r9 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r9 = r9.getView()     // Catch:{ all -> 0x032a }
            r10 = -1
            r8.addView(r9, r10, r10)     // Catch:{ all -> 0x032a }
            android.widget.LinearLayout r8 = new android.widget.LinearLayout     // Catch:{ all -> 0x032a }
            android.app.Activity r9 = r1.zzbvp     // Catch:{ all -> 0x032a }
            r8.<init>(r9)     // Catch:{ all -> 0x032a }
            r1.zzbwh = r8     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout$LayoutParams r8 = new android.widget.RelativeLayout$LayoutParams     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ all -> 0x032a }
            android.app.Activity r9 = r1.zzbvp     // Catch:{ all -> 0x032a }
            r11 = 50
            int r9 = com.google.android.gms.internal.ads.zzamu.zza(r9, r11)     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ all -> 0x032a }
            android.app.Activity r12 = r1.zzbvp     // Catch:{ all -> 0x032a }
            int r11 = com.google.android.gms.internal.ads.zzamu.zza(r12, r11)     // Catch:{ all -> 0x032a }
            r8.<init>(r9, r11)     // Catch:{ all -> 0x032a }
            java.lang.String r9 = r1.zzbvz     // Catch:{ all -> 0x032a }
            int r11 = r9.hashCode()     // Catch:{ all -> 0x032a }
            r12 = 5
            r13 = 4
            r14 = 3
            r15 = 2
            switch(r11) {
                case -1364013995: goto L_0x022f;
                case -1012429441: goto L_0x0225;
                case -655373719: goto L_0x021b;
                case 1163912186: goto L_0x0211;
                case 1288627767: goto L_0x0207;
                case 1755462605: goto L_0x01fd;
                default: goto L_0x01fc;
            }     // Catch:{ all -> 0x032a }
        L_0x01fc:
            goto L_0x0238
        L_0x01fd:
            java.lang.String r11 = "top-center"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 1
            goto L_0x0238
        L_0x0207:
            java.lang.String r11 = "bottom-center"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 4
            goto L_0x0238
        L_0x0211:
            java.lang.String r11 = "bottom-right"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 5
            goto L_0x0238
        L_0x021b:
            java.lang.String r11 = "bottom-left"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 3
            goto L_0x0238
        L_0x0225:
            java.lang.String r11 = "top-left"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 0
            goto L_0x0238
        L_0x022f:
            java.lang.String r11 = "center"
            boolean r9 = r9.equals(r11)     // Catch:{ all -> 0x032a }
            if (r9 == 0) goto L_0x0238
            r10 = 2
        L_0x0238:
            r9 = 9
            r11 = 10
            if (r10 == 0) goto L_0x026e
            r4 = 14
            if (r10 == r3) goto L_0x026a
            if (r10 == r15) goto L_0x0264
            r15 = 12
            if (r10 == r14) goto L_0x025d
            if (r10 == r13) goto L_0x0259
            r4 = 11
            if (r10 == r12) goto L_0x0255
            r8.addRule(r11)     // Catch:{ all -> 0x032a }
        L_0x0251:
            r8.addRule(r4)     // Catch:{ all -> 0x032a }
            goto L_0x0272
        L_0x0255:
            r8.addRule(r15)     // Catch:{ all -> 0x032a }
            goto L_0x0251
        L_0x0259:
            r8.addRule(r15)     // Catch:{ all -> 0x032a }
            goto L_0x0251
        L_0x025d:
            r8.addRule(r15)     // Catch:{ all -> 0x032a }
        L_0x0260:
            r8.addRule(r9)     // Catch:{ all -> 0x032a }
            goto L_0x0272
        L_0x0264:
            r4 = 13
            r8.addRule(r4)     // Catch:{ all -> 0x032a }
            goto L_0x0272
        L_0x026a:
            r8.addRule(r11)     // Catch:{ all -> 0x032a }
            goto L_0x0251
        L_0x026e:
            r8.addRule(r11)     // Catch:{ all -> 0x032a }
            goto L_0x0260
        L_0x0272:
            android.widget.LinearLayout r4 = r1.zzbwh     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaac r9 = new com.google.android.gms.internal.ads.zzaac     // Catch:{ all -> 0x032a }
            r9.<init>(r1)     // Catch:{ all -> 0x032a }
            r4.setOnClickListener(r9)     // Catch:{ all -> 0x032a }
            android.widget.LinearLayout r4 = r1.zzbwh     // Catch:{ all -> 0x032a }
            java.lang.String r9 = "Close button"
            r4.setContentDescription(r9)     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r4 = r1.zzbwj     // Catch:{ all -> 0x032a }
            android.widget.LinearLayout r9 = r1.zzbwh     // Catch:{ all -> 0x032a }
            r4.addView(r9, r8)     // Catch:{ all -> 0x032a }
            android.widget.PopupWindow r4 = r1.zzbwi     // Catch:{ RuntimeException -> 0x02d4 }
            android.view.View r0 = r0.getDecorView()     // Catch:{ RuntimeException -> 0x02d4 }
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ RuntimeException -> 0x02d4 }
            android.app.Activity r8 = r1.zzbvp     // Catch:{ RuntimeException -> 0x02d4 }
            r9 = 0
            r10 = r5[r9]     // Catch:{ RuntimeException -> 0x02d4 }
            int r8 = com.google.android.gms.internal.ads.zzamu.zza(r8, r10)     // Catch:{ RuntimeException -> 0x02d4 }
            com.google.android.gms.internal.ads.zzkb.zzif()     // Catch:{ RuntimeException -> 0x02d4 }
            android.app.Activity r9 = r1.zzbvp     // Catch:{ RuntimeException -> 0x02d4 }
            r10 = r5[r3]     // Catch:{ RuntimeException -> 0x02d4 }
            int r9 = com.google.android.gms.internal.ads.zzamu.zza(r9, r10)     // Catch:{ RuntimeException -> 0x02d4 }
            r10 = 0
            r4.showAtLocation(r0, r10, r8, r9)     // Catch:{ RuntimeException -> 0x02d4 }
            r0 = r5[r10]     // Catch:{ all -> 0x032a }
            r4 = r5[r3]     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaam r8 = r1.zzbmy     // Catch:{ all -> 0x032a }
            if (r8 == 0) goto L_0x02bc
            com.google.android.gms.internal.ads.zzaam r8 = r1.zzbmy     // Catch:{ all -> 0x032a }
            int r9 = r1.zzuq     // Catch:{ all -> 0x032a }
            int r10 = r1.zzur     // Catch:{ all -> 0x032a }
            r8.zza(r0, r4, r9, r10)     // Catch:{ all -> 0x032a }
        L_0x02bc:
            com.google.android.gms.internal.ads.zzaqw r0 = r1.zzbnd     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzasi r4 = com.google.android.gms.internal.ads.zzasi.zzi(r6, r7)     // Catch:{ all -> 0x032a }
            r0.zza(r4)     // Catch:{ all -> 0x032a }
            r0 = 0
            r0 = r5[r0]     // Catch:{ all -> 0x032a }
            r3 = r5[r3]     // Catch:{ all -> 0x032a }
            r1.zza(r0, r3)     // Catch:{ all -> 0x032a }
            java.lang.String r0 = "resized"
            r1.zzby(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x02d4:
            r0 = move-exception
            java.lang.String r3 = "Cannot show popup window: "
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x032a }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x032a }
            int r4 = r0.length()     // Catch:{ all -> 0x032a }
            if (r4 == 0) goto L_0x02ea
            java.lang.String r0 = r3.concat(r0)     // Catch:{ all -> 0x032a }
            goto L_0x02ef
        L_0x02ea:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x032a }
            r0.<init>(r3)     // Catch:{ all -> 0x032a }
        L_0x02ef:
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            android.widget.RelativeLayout r0 = r1.zzbwj     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r3 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r3 = r3.getView()     // Catch:{ all -> 0x032a }
            r0.removeView(r3)     // Catch:{ all -> 0x032a }
            android.view.ViewGroup r0 = r1.zzbwk     // Catch:{ all -> 0x032a }
            if (r0 == 0) goto L_0x031a
            android.view.ViewGroup r0 = r1.zzbwk     // Catch:{ all -> 0x032a }
            android.widget.ImageView r3 = r1.zzbwg     // Catch:{ all -> 0x032a }
            r0.removeView(r3)     // Catch:{ all -> 0x032a }
            android.view.ViewGroup r0 = r1.zzbwk     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r3 = r1.zzbnd     // Catch:{ all -> 0x032a }
            android.view.View r3 = r3.getView()     // Catch:{ all -> 0x032a }
            r0.addView(r3)     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzaqw r0 = r1.zzbnd     // Catch:{ all -> 0x032a }
            com.google.android.gms.internal.ads.zzasi r3 = r1.zzbwf     // Catch:{ all -> 0x032a }
            r0.zza(r3)     // Catch:{ all -> 0x032a }
        L_0x031a:
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x031c:
            java.lang.String r0 = "Webview is detached, probably in the middle of a resize or expand."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x0323:
            java.lang.String r0 = "Activity context is not ready, cannot get window or decor view."
            r1.zzbw(r0)     // Catch:{ all -> 0x032a }
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            return
        L_0x032a:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x032a }
            goto L_0x032e
        L_0x032d:
            throw r0
        L_0x032e:
            goto L_0x032d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaab.zzk(java.util.Map):void");
    }

    public final void zzm(boolean z) {
        synchronized (this.mLock) {
            if (this.zzbwi != null) {
                this.zzbwi.dismiss();
                this.zzbwj.removeView(this.zzbnd.getView());
                if (this.zzbwk != null) {
                    this.zzbwk.removeView(this.zzbwg);
                    this.zzbwk.addView(this.zzbnd.getView());
                    this.zzbnd.zza(this.zzbwf);
                }
                if (z) {
                    zzby(CookieSpecs.DEFAULT);
                    if (this.zzbmy != null) {
                        this.zzbmy.zzcq();
                    }
                }
                this.zzbwi = null;
                this.zzbwj = null;
                this.zzbwk = null;
                this.zzbwh = null;
            }
        }
    }

    public final boolean zznf() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbwi != null;
        }
        return z;
    }
}
