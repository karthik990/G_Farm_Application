package com.google.android.gms.internal.ads;

import java.util.ArrayList;
import java.util.List;

@zzadh
public final class zzalp {
    private final String[] zzcsu;
    private final double[] zzcsv;
    private final double[] zzcsw;
    private final int[] zzcsx;
    private int zzcsy;

    private zzalp(zzals zzals) {
        int size = zzals.zzctd.size();
        this.zzcsu = (String[]) zzals.zzctc.toArray(new String[size]);
        this.zzcsv = zzo(zzals.zzctd);
        this.zzcsw = zzo(zzals.zzcte);
        this.zzcsx = new int[size];
        this.zzcsy = 0;
    }

    private static double[] zzo(List<Double> list) {
        double[] dArr = new double[list.size()];
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = ((Double) list.get(i)).doubleValue();
        }
        return dArr;
    }

    public final void zza(double d) {
        this.zzcsy++;
        int i = 0;
        while (true) {
            double[] dArr = this.zzcsw;
            if (i < dArr.length) {
                if (dArr[i] <= d && d < this.zzcsv[i]) {
                    int[] iArr = this.zzcsx;
                    iArr[i] = iArr[i] + 1;
                }
                if (d >= this.zzcsw[i]) {
                    i++;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final List<zzalr> zzry() {
        ArrayList arrayList = new ArrayList(this.zzcsu.length);
        int i = 0;
        while (true) {
            String[] strArr = this.zzcsu;
            if (i >= strArr.length) {
                return arrayList;
            }
            String str = strArr[i];
            double d = this.zzcsw[i];
            double d2 = this.zzcsv[i];
            int[] iArr = this.zzcsx;
            double d3 = (double) iArr[i];
            double d4 = (double) this.zzcsy;
            Double.isNaN(d3);
            Double.isNaN(d4);
            zzalr zzalr = new zzalr(str, d, d2, d3 / d4, iArr[i]);
            arrayList.add(zzalr);
            i++;
        }
    }
}
