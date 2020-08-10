package com.google.android.gms.internal.ads;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;

public final class zzbj extends zzbh<Integer, Object> {
    public String zzcx;
    public String zzcz;
    public String zzda;
    public String zzdb;
    public long zzhx;

    public zzbj() {
        String str = ExifInterface.LONGITUDE_EAST;
        this.zzcx = str;
        this.zzhx = -1;
        this.zzcz = str;
        this.zzda = str;
        this.zzdb = str;
    }

    public zzbj(String str) {
        this();
        zzj(str);
    }

    /* access modifiers changed from: protected */
    public final void zzj(String str) {
        HashMap zzk = zzk(str);
        if (zzk != null) {
            Object obj = zzk.get(Integer.valueOf(0));
            String str2 = ExifInterface.LONGITUDE_EAST;
            this.zzcx = obj == null ? str2 : (String) zzk.get(Integer.valueOf(0));
            this.zzhx = zzk.get(Integer.valueOf(1)) == null ? -1 : ((Long) zzk.get(Integer.valueOf(1))).longValue();
            this.zzcz = zzk.get(Integer.valueOf(2)) == null ? str2 : (String) zzk.get(Integer.valueOf(2));
            this.zzda = zzk.get(Integer.valueOf(3)) == null ? str2 : (String) zzk.get(Integer.valueOf(3));
            if (zzk.get(Integer.valueOf(4)) != null) {
                str2 = (String) zzk.get(Integer.valueOf(4));
            }
            this.zzdb = str2;
        }
    }

    /* access modifiers changed from: protected */
    public final HashMap<Integer, Object> zzu() {
        HashMap<Integer, Object> hashMap = new HashMap<>();
        hashMap.put(Integer.valueOf(0), this.zzcx);
        hashMap.put(Integer.valueOf(4), this.zzdb);
        hashMap.put(Integer.valueOf(3), this.zzda);
        hashMap.put(Integer.valueOf(2), this.zzcz);
        hashMap.put(Integer.valueOf(1), Long.valueOf(this.zzhx));
        return hashMap;
    }
}
