package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.ads.mediation.zza;
import com.google.android.gms.ads.zzb;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzzp extends zzzk {
    private final zzatg zzbvi;

    public zzzp(zzatg zzatg) {
        this.zzbvi = zzatg;
    }

    private static Bundle zzbt(String str) throws RemoteException {
        String valueOf = String.valueOf(str);
        String str2 = "Server parameters: ";
        zzane.zzdk(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        try {
            Bundle bundle = new Bundle();
            if (str == null) {
                return bundle;
            }
            JSONObject jSONObject = new JSONObject(str);
            Bundle bundle2 = new Bundle();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str3 = (String) keys.next();
                bundle2.putString(str3, jSONObject.getString(str3));
            }
            return bundle2;
        } catch (JSONException e) {
            zzane.zzb("", e);
            throw new RemoteException();
        }
    }

    public final zzlo getVideoController() {
        zzatg zzatg = this.zzbvi;
        if (!(zzatg instanceof zza)) {
            return null;
        }
        try {
            return ((zza) zzatg).getVideoController();
        } catch (Throwable th) {
            zzane.zzb("", th);
            return null;
        }
    }

    public final void showInterstitial() throws RemoteException {
        zzate zzate = null;
        try {
            zzate.zzoy();
        } catch (Throwable th) {
            zzane.zzb("", th);
            throw new RemoteException();
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [com.google.android.gms.internal.ads.zzzs, com.google.android.gms.internal.ads.zzatj] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0, types: [com.google.android.gms.internal.ads.zzzs, com.google.android.gms.internal.ads.zzatj]
      assigns: [com.google.android.gms.internal.ads.zzzs]
      uses: [com.google.android.gms.internal.ads.zzatj]
      mth insns count: 50
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.dynamic.IObjectWrapper r8, java.lang.String r9, android.os.Bundle r10, com.google.android.gms.internal.ads.zzzm r11) throws android.os.RemoteException {
        /*
            r7 = this;
            com.google.android.gms.internal.ads.zzzs r0 = new com.google.android.gms.internal.ads.zzzs     // Catch:{ all -> 0x0064 }
            r0.<init>(r7, r11)     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.ads.zzatg r11 = r7.zzbvi     // Catch:{ all -> 0x0064 }
            com.google.android.gms.internal.ads.zzati r1 = new com.google.android.gms.internal.ads.zzati     // Catch:{ all -> 0x0064 }
            java.lang.Object r8 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r8)     // Catch:{ all -> 0x0064 }
            android.content.Context r8 = (android.content.Context) r8     // Catch:{ all -> 0x0064 }
            r2 = -1
            int r3 = r9.hashCode()     // Catch:{ all -> 0x0064 }
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r3) {
                case -1396342996: goto L_0x0039;
                case -1052618729: goto L_0x002f;
                case -239580146: goto L_0x0025;
                case 604727084: goto L_0x001b;
                default: goto L_0x001a;
            }     // Catch:{ all -> 0x0064 }
        L_0x001a:
            goto L_0x0042
        L_0x001b:
            java.lang.String r3 = "interstitial"
            boolean r9 = r9.equals(r3)     // Catch:{ all -> 0x0064 }
            if (r9 == 0) goto L_0x0042
            r2 = 1
            goto L_0x0042
        L_0x0025:
            java.lang.String r3 = "rewarded"
            boolean r9 = r9.equals(r3)     // Catch:{ all -> 0x0064 }
            if (r9 == 0) goto L_0x0042
            r2 = 2
            goto L_0x0042
        L_0x002f:
            java.lang.String r3 = "native"
            boolean r9 = r9.equals(r3)     // Catch:{ all -> 0x0064 }
            if (r9 == 0) goto L_0x0042
            r2 = 3
            goto L_0x0042
        L_0x0039:
            java.lang.String r3 = "banner"
            boolean r9 = r9.equals(r3)     // Catch:{ all -> 0x0064 }
            if (r9 == 0) goto L_0x0042
            r2 = 0
        L_0x0042:
            if (r2 == 0) goto L_0x005b
            if (r2 == r6) goto L_0x0058
            if (r2 == r5) goto L_0x0055
            if (r2 != r4) goto L_0x004d
            int r9 = com.google.android.gms.internal.ads.zzath.zzdgq     // Catch:{ all -> 0x0064 }
            goto L_0x005d
        L_0x004d:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0064 }
            java.lang.String r9 = "Internal Error"
            r8.<init>(r9)     // Catch:{ all -> 0x0064 }
            throw r8     // Catch:{ all -> 0x0064 }
        L_0x0055:
            int r9 = com.google.android.gms.internal.ads.zzath.zzdgp     // Catch:{ all -> 0x0064 }
            goto L_0x005d
        L_0x0058:
            int r9 = com.google.android.gms.internal.ads.zzath.zzdgo     // Catch:{ all -> 0x0064 }
            goto L_0x005d
        L_0x005b:
            int r9 = com.google.android.gms.internal.ads.zzath.zzdgn     // Catch:{ all -> 0x0064 }
        L_0x005d:
            r1.<init>(r8, r9, r10)     // Catch:{ all -> 0x0064 }
            r11.zza(r1, r0)     // Catch:{ all -> 0x0064 }
            return
        L_0x0064:
            r8 = move-exception
            java.lang.String r9 = "Error generating signals for RTB"
            com.google.android.gms.internal.ads.zzane.zzb(r9, r8)
            android.os.RemoteException r8 = new android.os.RemoteException
            r8.<init>()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzzp.zza(com.google.android.gms.dynamic.IObjectWrapper, java.lang.String, android.os.Bundle, com.google.android.gms.internal.ads.zzzm):void");
    }

    public final void zza(byte[] bArr, String str, Bundle bundle, IObjectWrapper iObjectWrapper, zzzf zzzf, zzxt zzxt, zzjn zzjn) throws RemoteException {
        try {
            zzzq zzzq = new zzzq(this, zzzf, zzxt);
            zzatg zzatg = this.zzbvi;
            new zzatf((Context) ObjectWrapper.unwrap(iObjectWrapper), bArr, zzbt(str), bundle);
            zzb.zza(zzjn.width, zzjn.height, zzjn.zzarb);
            zzzq.zzau(String.valueOf(zzatg.getClass().getSimpleName()).concat(" does not support banner."));
        } catch (Throwable th) {
            zzane.zzb("Adapter failed to render banner ad.", th);
            throw new RemoteException();
        }
    }

    public final void zza(byte[] bArr, String str, Bundle bundle, IObjectWrapper iObjectWrapper, zzzh zzzh, zzxt zzxt) throws RemoteException {
        try {
            zzzr zzzr = new zzzr(this, zzzh, zzxt);
            zzatg zzatg = this.zzbvi;
            new zzatf((Context) ObjectWrapper.unwrap(iObjectWrapper), bArr, zzbt(str), bundle);
            zzzr.zzau(String.valueOf(zzatg.getClass().getSimpleName()).concat(" does not support interstitial."));
        } catch (Throwable th) {
            zzane.zzb("Adapter failed to render interstitial ad.", th);
            throw new RemoteException();
        }
    }

    public final zzzt zznc() throws RemoteException {
        return zzzt.zza(this.zzbvi.zzwa());
    }

    public final zzzt zznd() throws RemoteException {
        return zzzt.zza(this.zzbvi.zzvz());
    }
}
