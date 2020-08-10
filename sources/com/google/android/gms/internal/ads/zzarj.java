package com.google.android.gms.internal.ads;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.gmsg.zzv;
import java.util.Map;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

final class zzarj implements zzv<zzaqw> {
    private final /* synthetic */ zzari zzdec;

    zzarj(zzari zzari) {
        this.zzdec = zzari;
    }

    public final /* synthetic */ void zza(Object obj, Map map) {
        if (map != null) {
            String str = (String) map.get(SettingsJsonConstants.ICON_HEIGHT_KEY);
            if (!TextUtils.isEmpty(str)) {
                try {
                    int parseInt = Integer.parseInt(str);
                    synchronized (this.zzdec) {
                        if (this.zzdec.zzddu != parseInt) {
                            this.zzdec.zzddu = parseInt;
                            this.zzdec.requestLayout();
                        }
                    }
                } catch (Exception e) {
                    zzakb.zzc("Exception occurred while getting webview content height", e);
                }
            }
        }
    }
}
