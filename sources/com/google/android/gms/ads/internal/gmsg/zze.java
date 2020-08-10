package com.google.android.gms.ads.internal.gmsg;

import android.text.TextUtils;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mobiroller.constants.ChatConstants;
import java.util.Map;

@zzadh
public final class zze implements zzv<zzaqw> {
    public final /* synthetic */ void zza(Object obj, Map map) {
        zzaqw zzaqw = (zzaqw) obj;
        String str = (String) map.get("action");
        if ("tick".equals(str)) {
            String str2 = (String) map.get("label");
            String str3 = (String) map.get("start_label");
            String str4 = (String) map.get(Param.TIMESTAMP);
            if (TextUtils.isEmpty(str2)) {
                zzakb.zzdk("No label given for CSI tick.");
            } else if (TextUtils.isEmpty(str4)) {
                zzakb.zzdk("No timestamp given for CSI tick.");
            } else {
                try {
                    long elapsedRealtime = zzbv.zzer().elapsedRealtime() + (Long.parseLong(str4) - zzbv.zzer().currentTimeMillis());
                    if (TextUtils.isEmpty(str3)) {
                        str3 = "native:view_load";
                    }
                    zzaqw.zztp().zza(str2, str3, elapsedRealtime);
                } catch (NumberFormatException e) {
                    zzakb.zzc("Malformed timestamp for CSI tick.", e);
                }
            }
        } else {
            boolean equals = "experiment".equals(str);
            String str5 = FirebaseAnalytics.Param.VALUE;
            if (equals) {
                String str6 = (String) map.get(str5);
                if (TextUtils.isEmpty(str6)) {
                    zzakb.zzdk("No value given for CSI experiment.");
                    return;
                }
                zznx zzji = zzaqw.zztp().zzji();
                if (zzji == null) {
                    zzakb.zzdk("No ticker for WebView, dropping experiment ID.");
                } else {
                    zzji.zze(ChatConstants.ARG_USER_INFO_EMAIL, str6);
                }
            } else {
                if ("extra".equals(str)) {
                    String str7 = (String) map.get(PostalAddressParser.USER_ADDRESS_NAME_KEY);
                    String str8 = (String) map.get(str5);
                    if (TextUtils.isEmpty(str8)) {
                        zzakb.zzdk("No value given for CSI extra.");
                    } else if (TextUtils.isEmpty(str7)) {
                        zzakb.zzdk("No name given for CSI extra.");
                    } else {
                        zznx zzji2 = zzaqw.zztp().zzji();
                        if (zzji2 == null) {
                            zzakb.zzdk("No ticker for WebView, dropping extra parameter.");
                            return;
                        }
                        zzji2.zze(str7, str8);
                    }
                }
            }
        }
    }
}
