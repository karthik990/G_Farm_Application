package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.NativeAd.AdChoicesInfo;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.ArrayList;
import java.util.List;

@zzadh
public final class zzqn extends NativeAppInstallAd {
    private final VideoController zzasv = new VideoController();
    private final zzqk zzbkn;
    private final List<Image> zzbko = new ArrayList();
    private final zzpz zzbkp;
    private final AdChoicesInfo zzbkq;

    /* JADX WARNING: Removed duplicated region for block: B:17:0x004c A[Catch:{ RemoteException -> 0x0057 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0078 A[Catch:{ RemoteException -> 0x0085 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0022 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzqn(com.google.android.gms.internal.ads.zzqk r6) {
        /*
            r5 = this;
            java.lang.String r0 = ""
            r5.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r5.zzbko = r1
            com.google.android.gms.ads.VideoController r1 = new com.google.android.gms.ads.VideoController
            r1.<init>()
            r5.zzasv = r1
            r5.zzbkn = r6
            r6 = 0
            com.google.android.gms.internal.ads.zzqk r1 = r5.zzbkn     // Catch:{ RemoteException -> 0x0057 }
            java.util.List r1 = r1.getImages()     // Catch:{ RemoteException -> 0x0057 }
            if (r1 == 0) goto L_0x005b
            java.util.Iterator r1 = r1.iterator()     // Catch:{ RemoteException -> 0x0057 }
        L_0x0022:
            boolean r2 = r1.hasNext()     // Catch:{ RemoteException -> 0x0057 }
            if (r2 == 0) goto L_0x005b
            java.lang.Object r2 = r1.next()     // Catch:{ RemoteException -> 0x0057 }
            boolean r3 = r2 instanceof android.os.IBinder     // Catch:{ RemoteException -> 0x0057 }
            if (r3 == 0) goto L_0x0049
            android.os.IBinder r2 = (android.os.IBinder) r2     // Catch:{ RemoteException -> 0x0057 }
            if (r2 == 0) goto L_0x0049
            java.lang.String r3 = "com.google.android.gms.ads.internal.formats.client.INativeAdImage"
            android.os.IInterface r3 = r2.queryLocalInterface(r3)     // Catch:{ RemoteException -> 0x0057 }
            boolean r4 = r3 instanceof com.google.android.gms.internal.ads.zzpw     // Catch:{ RemoteException -> 0x0057 }
            if (r4 == 0) goto L_0x0042
            r2 = r3
            com.google.android.gms.internal.ads.zzpw r2 = (com.google.android.gms.internal.ads.zzpw) r2     // Catch:{ RemoteException -> 0x0057 }
            goto L_0x004a
        L_0x0042:
            com.google.android.gms.internal.ads.zzpy r3 = new com.google.android.gms.internal.ads.zzpy     // Catch:{ RemoteException -> 0x0057 }
            r3.<init>(r2)     // Catch:{ RemoteException -> 0x0057 }
            r2 = r3
            goto L_0x004a
        L_0x0049:
            r2 = r6
        L_0x004a:
            if (r2 == 0) goto L_0x0022
            java.util.List<com.google.android.gms.ads.formats.NativeAd$Image> r3 = r5.zzbko     // Catch:{ RemoteException -> 0x0057 }
            com.google.android.gms.internal.ads.zzpz r4 = new com.google.android.gms.internal.ads.zzpz     // Catch:{ RemoteException -> 0x0057 }
            r4.<init>(r2)     // Catch:{ RemoteException -> 0x0057 }
            r3.add(r4)     // Catch:{ RemoteException -> 0x0057 }
            goto L_0x0022
        L_0x0057:
            r1 = move-exception
            com.google.android.gms.internal.ads.zzane.zzb(r0, r1)
        L_0x005b:
            com.google.android.gms.internal.ads.zzqk r1 = r5.zzbkn     // Catch:{ RemoteException -> 0x0069 }
            com.google.android.gms.internal.ads.zzpw r1 = r1.zzjz()     // Catch:{ RemoteException -> 0x0069 }
            if (r1 == 0) goto L_0x006d
            com.google.android.gms.internal.ads.zzpz r2 = new com.google.android.gms.internal.ads.zzpz     // Catch:{ RemoteException -> 0x0069 }
            r2.<init>(r1)     // Catch:{ RemoteException -> 0x0069 }
            goto L_0x006e
        L_0x0069:
            r1 = move-exception
            com.google.android.gms.internal.ads.zzane.zzb(r0, r1)
        L_0x006d:
            r2 = r6
        L_0x006e:
            r5.zzbkp = r2
            com.google.android.gms.internal.ads.zzqk r1 = r5.zzbkn     // Catch:{ RemoteException -> 0x0085 }
            com.google.android.gms.internal.ads.zzps r1 = r1.zzkf()     // Catch:{ RemoteException -> 0x0085 }
            if (r1 == 0) goto L_0x0089
            com.google.android.gms.internal.ads.zzpv r1 = new com.google.android.gms.internal.ads.zzpv     // Catch:{ RemoteException -> 0x0085 }
            com.google.android.gms.internal.ads.zzqk r2 = r5.zzbkn     // Catch:{ RemoteException -> 0x0085 }
            com.google.android.gms.internal.ads.zzps r2 = r2.zzkf()     // Catch:{ RemoteException -> 0x0085 }
            r1.<init>(r2)     // Catch:{ RemoteException -> 0x0085 }
            r6 = r1
            goto L_0x0089
        L_0x0085:
            r1 = move-exception
            com.google.android.gms.internal.ads.zzane.zzb(r0, r1)
        L_0x0089:
            r5.zzbkq = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzqn.<init>(com.google.android.gms.internal.ads.zzqk):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: zzka */
    public final IObjectWrapper zzbe() {
        try {
            return this.zzbkn.zzka();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final void destroy() {
        try {
            this.zzbkn.destroy();
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }

    public final AdChoicesInfo getAdChoicesInfo() {
        return this.zzbkq;
    }

    public final CharSequence getBody() {
        try {
            return this.zzbkn.getBody();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final CharSequence getCallToAction() {
        try {
            return this.zzbkn.getCallToAction();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final Bundle getExtras() {
        try {
            return this.zzbkn.getExtras();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final CharSequence getHeadline() {
        try {
            return this.zzbkn.getHeadline();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final Image getIcon() {
        return this.zzbkp;
    }

    public final List<Image> getImages() {
        return this.zzbko;
    }

    public final CharSequence getMediationAdapterClassName() {
        try {
            return this.zzbkn.getMediationAdapterClassName();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final CharSequence getPrice() {
        try {
            return this.zzbkn.getPrice();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final Double getStarRating() {
        try {
            double starRating = this.zzbkn.getStarRating();
            if (starRating == -1.0d) {
                return null;
            }
            return Double.valueOf(starRating);
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final CharSequence getStore() {
        try {
            return this.zzbkn.getStore();
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return null;
        }
    }

    public final VideoController getVideoController() {
        try {
            if (this.zzbkn.getVideoController() != null) {
                this.zzasv.zza(this.zzbkn.getVideoController());
            }
        } catch (RemoteException e) {
            zzane.zzb("Exception occurred while getting video controller", e);
        }
        return this.zzasv;
    }

    public final void performClick(Bundle bundle) {
        try {
            this.zzbkn.performClick(bundle);
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }

    public final boolean recordImpression(Bundle bundle) {
        try {
            return this.zzbkn.recordImpression(bundle);
        } catch (RemoteException e) {
            zzane.zzb("", e);
            return false;
        }
    }

    public final void reportTouchEvent(Bundle bundle) {
        try {
            this.zzbkn.reportTouchEvent(bundle);
        } catch (RemoteException e) {
            zzane.zzb("", e);
        }
    }
}
