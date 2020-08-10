package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;

public abstract class zzaha extends zzek implements zzagz {
    public zzaha() {
        super("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAd");
    }

    public static zzagz zzy(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.reward.client.IRewardedVideoAd");
        return queryLocalInterface instanceof zzagz ? (zzagz) queryLocalInterface : new zzahb(iBinder);
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [com.google.android.gms.internal.ads.zzahe] */
    /* JADX WARNING: type inference failed for: r1v2, types: [com.google.android.gms.internal.ads.zzahg] */
    /* JADX WARNING: type inference failed for: r1v4, types: [com.google.android.gms.internal.ads.zzahe] */
    /* JADX WARNING: type inference failed for: r1v5, types: [com.google.android.gms.internal.ads.zzagx] */
    /* JADX WARNING: type inference failed for: r1v6, types: [com.google.android.gms.internal.ads.zzagy] */
    /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.ads.zzagx] */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: type inference failed for: r1v11 */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], com.google.android.gms.internal.ads.zzagy, com.google.android.gms.internal.ads.zzahg, com.google.android.gms.internal.ads.zzahe, com.google.android.gms.internal.ads.zzagx]
      uses: [com.google.android.gms.internal.ads.zzahe, com.google.android.gms.internal.ads.zzagx]
      mth insns count: 66
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
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchTransaction(int r3, android.os.Parcel r4, android.os.Parcel r5, int r6) throws android.os.RemoteException {
        /*
            r2 = this;
            r6 = 1
            if (r3 == r6) goto L_0x00c5
            r0 = 2
            if (r3 == r0) goto L_0x00c1
            r0 = 3
            r1 = 0
            if (r3 == r0) goto L_0x00a3
            r0 = 34
            if (r3 == r0) goto L_0x009b
            switch(r3) {
                case 5: goto L_0x0090;
                case 6: goto L_0x008c;
                case 7: goto L_0x0088;
                case 8: goto L_0x0084;
                case 9: goto L_0x0078;
                case 10: goto L_0x006c;
                case 11: goto L_0x0060;
                case 12: goto L_0x0054;
                case 13: goto L_0x004b;
                case 14: goto L_0x003e;
                case 15: goto L_0x0032;
                case 16: goto L_0x0013;
                default: goto L_0x0011;
            }
        L_0x0011:
            r3 = 0
            return r3
        L_0x0013:
            android.os.IBinder r3 = r4.readStrongBinder()
            if (r3 != 0) goto L_0x001a
            goto L_0x002d
        L_0x001a:
            java.lang.String r4 = "com.google.android.gms.ads.internal.reward.client.IRewardedAdSkuListener"
            android.os.IInterface r4 = r3.queryLocalInterface(r4)
            boolean r0 = r4 instanceof com.google.android.gms.internal.ads.zzagx
            if (r0 == 0) goto L_0x0028
            r1 = r4
            com.google.android.gms.internal.ads.zzagx r1 = (com.google.android.gms.internal.ads.zzagx) r1
            goto L_0x002d
        L_0x0028:
            com.google.android.gms.internal.ads.zzagy r1 = new com.google.android.gms.internal.ads.zzagy
            r1.<init>(r3)
        L_0x002d:
            r2.zza(r1)
            goto L_0x00d0
        L_0x0032:
            android.os.Bundle r3 = r2.zzba()
            r5.writeNoException()
            com.google.android.gms.internal.ads.zzel.zzb(r5, r3)
            goto L_0x00d3
        L_0x003e:
            android.os.IBinder r3 = r4.readStrongBinder()
            com.google.android.gms.internal.ads.zzkx r3 = com.google.android.gms.internal.ads.zzky.zzc(r3)
            r2.zza(r3)
            goto L_0x00d0
        L_0x004b:
            java.lang.String r3 = r4.readString()
            r2.setUserId(r3)
            goto L_0x00d0
        L_0x0054:
            java.lang.String r3 = r2.getMediationAdapterClassName()
            r5.writeNoException()
            r5.writeString(r3)
            goto L_0x00d3
        L_0x0060:
            android.os.IBinder r3 = r4.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            r2.zzf(r3)
            goto L_0x00d0
        L_0x006c:
            android.os.IBinder r3 = r4.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            r2.zze(r3)
            goto L_0x00d0
        L_0x0078:
            android.os.IBinder r3 = r4.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            r2.zzd(r3)
            goto L_0x00d0
        L_0x0084:
            r2.destroy()
            goto L_0x00d0
        L_0x0088:
            r2.resume()
            goto L_0x00d0
        L_0x008c:
            r2.pause()
            goto L_0x00d0
        L_0x0090:
            boolean r3 = r2.isLoaded()
            r5.writeNoException()
            com.google.android.gms.internal.ads.zzel.zza(r5, r3)
            goto L_0x00d3
        L_0x009b:
            boolean r3 = com.google.android.gms.internal.ads.zzel.zza(r4)
            r2.setImmersiveMode(r3)
            goto L_0x00d0
        L_0x00a3:
            android.os.IBinder r3 = r4.readStrongBinder()
            if (r3 != 0) goto L_0x00aa
            goto L_0x00bd
        L_0x00aa:
            java.lang.String r4 = "com.google.android.gms.ads.internal.reward.client.IRewardedVideoAdListener"
            android.os.IInterface r4 = r3.queryLocalInterface(r4)
            boolean r0 = r4 instanceof com.google.android.gms.internal.ads.zzahe
            if (r0 == 0) goto L_0x00b8
            r1 = r4
            com.google.android.gms.internal.ads.zzahe r1 = (com.google.android.gms.internal.ads.zzahe) r1
            goto L_0x00bd
        L_0x00b8:
            com.google.android.gms.internal.ads.zzahg r1 = new com.google.android.gms.internal.ads.zzahg
            r1.<init>(r3)
        L_0x00bd:
            r2.zza(r1)
            goto L_0x00d0
        L_0x00c1:
            r2.show()
            goto L_0x00d0
        L_0x00c5:
            android.os.Parcelable$Creator<com.google.android.gms.internal.ads.zzahk> r3 = com.google.android.gms.internal.ads.zzahk.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.ads.zzel.zza(r4, r3)
            com.google.android.gms.internal.ads.zzahk r3 = (com.google.android.gms.internal.ads.zzahk) r3
            r2.zza(r3)
        L_0x00d0:
            r5.writeNoException()
        L_0x00d3:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaha.dispatchTransaction(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
