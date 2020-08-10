package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.Map;

final class zzol implements zzv<Object> {
    private final /* synthetic */ zzok zzbhr;

    zzol(zzok zzok) {
        this.zzbhr = zzok;
    }

    public final void zza(Object obj, Map<String, String> map) {
        try {
            this.zzbhr.zzbhp = Long.valueOf(Long.parseLong((String) map.get(Param.TIMESTAMP)));
        } catch (NumberFormatException unused) {
            zzakb.m1272e("Failed to call parse unconfirmedClickTimestamp.");
        }
        this.zzbhr.zzbho = (String) map.get(TtmlNode.ATTR_ID);
        String str = (String) map.get("asset_id");
        if (this.zzbhr.zzbhm == null) {
            zzakb.zzck("Received unconfirmed click but UnconfirmedClickListener is null.");
            return;
        }
        try {
            this.zzbhr.zzbhm.onUnconfirmedClickReceived(str);
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }
}
