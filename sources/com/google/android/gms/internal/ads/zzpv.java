package com.google.android.gms.internal.ads;

import com.google.android.gms.ads.formats.NativeAd.AdChoicesInfo;
import com.google.android.gms.ads.formats.NativeAd.Image;
import java.util.ArrayList;
import java.util.List;

@zzadh
public final class zzpv extends AdChoicesInfo {
    private final List<Image> zzbhf = new ArrayList();
    private final zzps zzbkk;
    private String zzbkl;

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004d A[Catch:{ RemoteException -> 0x0059 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0025 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzpv(com.google.android.gms.internal.ads.zzps r5) {
        /*
            r4 = this;
            java.lang.String r0 = ""
            r4.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r4.zzbhf = r1
            r4.zzbkk = r5
            com.google.android.gms.internal.ads.zzps r1 = r4.zzbkk     // Catch:{ RemoteException -> 0x0017 }
            java.lang.String r1 = r1.getText()     // Catch:{ RemoteException -> 0x0017 }
            r4.zzbkl = r1     // Catch:{ RemoteException -> 0x0017 }
            goto L_0x001d
        L_0x0017:
            r1 = move-exception
            com.google.android.gms.internal.ads.zzane.zzb(r0, r1)
            r4.zzbkl = r0
        L_0x001d:
            java.util.List r5 = r5.zzjr()     // Catch:{ RemoteException -> 0x0059 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ RemoteException -> 0x0059 }
        L_0x0025:
            boolean r1 = r5.hasNext()     // Catch:{ RemoteException -> 0x0059 }
            if (r1 == 0) goto L_0x0058
            java.lang.Object r1 = r5.next()     // Catch:{ RemoteException -> 0x0059 }
            boolean r2 = r1 instanceof android.os.IBinder     // Catch:{ RemoteException -> 0x0059 }
            if (r2 == 0) goto L_0x004a
            android.os.IBinder r1 = (android.os.IBinder) r1     // Catch:{ RemoteException -> 0x0059 }
            if (r1 == 0) goto L_0x004a
            java.lang.String r2 = "com.google.android.gms.ads.internal.formats.client.INativeAdImage"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)     // Catch:{ RemoteException -> 0x0059 }
            boolean r3 = r2 instanceof com.google.android.gms.internal.ads.zzpw     // Catch:{ RemoteException -> 0x0059 }
            if (r3 == 0) goto L_0x0044
            com.google.android.gms.internal.ads.zzpw r2 = (com.google.android.gms.internal.ads.zzpw) r2     // Catch:{ RemoteException -> 0x0059 }
            goto L_0x004b
        L_0x0044:
            com.google.android.gms.internal.ads.zzpy r2 = new com.google.android.gms.internal.ads.zzpy     // Catch:{ RemoteException -> 0x0059 }
            r2.<init>(r1)     // Catch:{ RemoteException -> 0x0059 }
            goto L_0x004b
        L_0x004a:
            r2 = 0
        L_0x004b:
            if (r2 == 0) goto L_0x0025
            java.util.List<com.google.android.gms.ads.formats.NativeAd$Image> r1 = r4.zzbhf     // Catch:{ RemoteException -> 0x0059 }
            com.google.android.gms.internal.ads.zzpz r3 = new com.google.android.gms.internal.ads.zzpz     // Catch:{ RemoteException -> 0x0059 }
            r3.<init>(r2)     // Catch:{ RemoteException -> 0x0059 }
            r1.add(r3)     // Catch:{ RemoteException -> 0x0059 }
            goto L_0x0025
        L_0x0058:
            return
        L_0x0059:
            r5 = move-exception
            com.google.android.gms.internal.ads.zzane.zzb(r0, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzpv.<init>(com.google.android.gms.internal.ads.zzps):void");
    }

    public final List<Image> getImages() {
        return this.zzbhf;
    }

    public final CharSequence getText() {
        return this.zzbkl;
    }
}
