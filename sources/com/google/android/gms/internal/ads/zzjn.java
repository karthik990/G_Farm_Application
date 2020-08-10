package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.zzb;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

@zzadh
public class zzjn extends AbstractSafeParcelable {
    public static final Creator<zzjn> CREATOR = new zzjo();
    public final int height;
    public final int heightPixels;
    public final int width;
    public final int widthPixels;
    public final String zzarb;
    public final boolean zzarc;
    public final zzjn[] zzard;
    public final boolean zzare;
    public final boolean zzarf;
    public boolean zzarg;

    public zzjn() {
        this("interstitial_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    public zzjn(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0082  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzjn(android.content.Context r13, com.google.android.gms.ads.AdSize[] r14) {
        /*
            r12 = this;
            r12.<init>()
            r0 = 0
            r1 = r14[r0]
            r12.zzarc = r0
            boolean r2 = r1.isFluid()
            r12.zzarf = r2
            boolean r2 = r12.zzarf
            if (r2 == 0) goto L_0x0021
            com.google.android.gms.ads.AdSize r2 = com.google.android.gms.ads.AdSize.BANNER
            int r2 = r2.getWidth()
            r12.width = r2
            com.google.android.gms.ads.AdSize r2 = com.google.android.gms.ads.AdSize.BANNER
            int r2 = r2.getHeight()
            goto L_0x002b
        L_0x0021:
            int r2 = r1.getWidth()
            r12.width = r2
            int r2 = r1.getHeight()
        L_0x002b:
            r12.height = r2
            int r2 = r12.width
            r3 = -1
            r4 = 1
            if (r2 != r3) goto L_0x0035
            r2 = 1
            goto L_0x0036
        L_0x0035:
            r2 = 0
        L_0x0036:
            int r3 = r12.height
            r5 = -2
            if (r3 != r5) goto L_0x003d
            r3 = 1
            goto L_0x003e
        L_0x003d:
            r3 = 0
        L_0x003e:
            android.content.res.Resources r5 = r13.getResources()
            android.util.DisplayMetrics r5 = r5.getDisplayMetrics()
            if (r2 == 0) goto L_0x0085
            com.google.android.gms.internal.ads.zzkb.zzif()
            boolean r6 = com.google.android.gms.internal.ads.zzamu.zzbi(r13)
            if (r6 == 0) goto L_0x0065
            com.google.android.gms.internal.ads.zzkb.zzif()
            boolean r6 = com.google.android.gms.internal.ads.zzamu.zzbj(r13)
            if (r6 == 0) goto L_0x0065
            int r6 = r5.widthPixels
            com.google.android.gms.internal.ads.zzkb.zzif()
            int r7 = com.google.android.gms.internal.ads.zzamu.zzbk(r13)
            int r6 = r6 - r7
            goto L_0x0067
        L_0x0065:
            int r6 = r5.widthPixels
        L_0x0067:
            r12.widthPixels = r6
            int r6 = r12.widthPixels
            float r6 = (float) r6
            float r7 = r5.density
            float r6 = r6 / r7
            double r6 = (double) r6
            int r8 = (int) r6
            double r9 = (double) r8
            java.lang.Double.isNaN(r6)
            java.lang.Double.isNaN(r9)
            double r6 = r6 - r9
            r9 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            int r11 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r11 < 0) goto L_0x0092
            int r8 = r8 + 1
            goto L_0x0092
        L_0x0085:
            int r8 = r12.width
            com.google.android.gms.internal.ads.zzkb.zzif()
            int r6 = r12.width
            int r6 = com.google.android.gms.internal.ads.zzamu.zza(r5, r6)
            r12.widthPixels = r6
        L_0x0092:
            if (r3 == 0) goto L_0x0099
            int r6 = zzd(r5)
            goto L_0x009b
        L_0x0099:
            int r6 = r12.height
        L_0x009b:
            com.google.android.gms.internal.ads.zzkb.zzif()
            int r5 = com.google.android.gms.internal.ads.zzamu.zza(r5, r6)
            r12.heightPixels = r5
            if (r2 != 0) goto L_0x00b5
            if (r3 == 0) goto L_0x00a9
            goto L_0x00b5
        L_0x00a9:
            boolean r2 = r12.zzarf
            if (r2 == 0) goto L_0x00b0
            java.lang.String r1 = "320x50_mb"
            goto L_0x00d0
        L_0x00b0:
            java.lang.String r1 = r1.toString()
            goto L_0x00d0
        L_0x00b5:
            r1 = 26
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            r2.append(r8)
            java.lang.String r1 = "x"
            r2.append(r1)
            r2.append(r6)
            java.lang.String r1 = "_as"
            r2.append(r1)
            java.lang.String r1 = r2.toString()
        L_0x00d0:
            r12.zzarb = r1
            int r1 = r14.length
            if (r1 <= r4) goto L_0x00ec
            int r1 = r14.length
            com.google.android.gms.internal.ads.zzjn[] r1 = new com.google.android.gms.internal.ads.zzjn[r1]
            r12.zzard = r1
            r1 = 0
        L_0x00db:
            int r2 = r14.length
            if (r1 >= r2) goto L_0x00ef
            com.google.android.gms.internal.ads.zzjn[] r2 = r12.zzard
            com.google.android.gms.internal.ads.zzjn r3 = new com.google.android.gms.internal.ads.zzjn
            r4 = r14[r1]
            r3.<init>(r13, r4)
            r2[r1] = r3
            int r1 = r1 + 1
            goto L_0x00db
        L_0x00ec:
            r13 = 0
            r12.zzard = r13
        L_0x00ef:
            r12.zzare = r0
            r12.zzarg = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzjn.<init>(android.content.Context, com.google.android.gms.ads.AdSize[]):void");
    }

    public zzjn(zzjn zzjn, zzjn[] zzjnArr) {
        this(zzjn.zzarb, zzjn.height, zzjn.heightPixels, zzjn.zzarc, zzjn.width, zzjn.widthPixels, zzjnArr, zzjn.zzare, zzjn.zzarf, zzjn.zzarg);
    }

    zzjn(String str, int i, int i2, boolean z, int i3, int i4, zzjn[] zzjnArr, boolean z2, boolean z3, boolean z4) {
        this.zzarb = str;
        this.height = i;
        this.heightPixels = i2;
        this.zzarc = z;
        this.width = i3;
        this.widthPixels = i4;
        this.zzard = zzjnArr;
        this.zzare = z2;
        this.zzarf = z3;
        this.zzarg = z4;
    }

    public static int zzb(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int zzc(DisplayMetrics displayMetrics) {
        return (int) (((float) zzd(displayMetrics)) * displayMetrics.density);
    }

    private static int zzd(DisplayMetrics displayMetrics) {
        int i = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
        if (i <= 400) {
            return 32;
        }
        return i <= 720 ? 50 : 90;
    }

    public static zzjn zzf(Context context) {
        zzjn zzjn = new zzjn("320x50_mb", 0, 0, false, 0, 0, null, true, false, false);
        return zzjn;
    }

    public static zzjn zzhx() {
        zzjn zzjn = new zzjn("reward_mb", 0, 0, true, 0, 0, null, false, false, false);
        return zzjn;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzarb, false);
        SafeParcelWriter.writeInt(parcel, 3, this.height);
        SafeParcelWriter.writeInt(parcel, 4, this.heightPixels);
        SafeParcelWriter.writeBoolean(parcel, 5, this.zzarc);
        SafeParcelWriter.writeInt(parcel, 6, this.width);
        SafeParcelWriter.writeInt(parcel, 7, this.widthPixels);
        SafeParcelWriter.writeTypedArray(parcel, 8, this.zzard, i, false);
        SafeParcelWriter.writeBoolean(parcel, 9, this.zzare);
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzarf);
        SafeParcelWriter.writeBoolean(parcel, 11, this.zzarg);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final AdSize zzhy() {
        return zzb.zza(this.width, this.height, this.zzarb);
    }
}
