package com.google.android.gms.ads.internal.gmsg;

import android.text.TextUtils;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.mobiroller.constants.Constants;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzaf implements zzv<Object> {
    private final Object mLock = new Object();
    private final Map<String, zzag> zzbnf = new HashMap();

    public final void zza(Object obj, Map<String, String> map) {
        String str;
        String str2 = (String) map.get(TtmlNode.ATTR_ID);
        String str3 = (String) map.get("fail");
        String str4 = (String) map.get("fail_reason");
        String str5 = (String) map.get("fail_stack");
        String str6 = (String) map.get("result");
        if (TextUtils.isEmpty(str5)) {
            str4 = "Unknown Fail Reason.";
        }
        if (TextUtils.isEmpty(str5)) {
            str = "";
        } else {
            String str7 = Constants.NEW_LINE;
            String valueOf = String.valueOf(str5);
            str = valueOf.length() != 0 ? str7.concat(valueOf) : new String(str7);
        }
        synchronized (this.mLock) {
            zzag zzag = (zzag) this.zzbnf.remove(str2);
            if (zzag == null) {
                String str8 = "Received result for unexpected method invocation: ";
                String valueOf2 = String.valueOf(str2);
                zzakb.zzdk(valueOf2.length() != 0 ? str8.concat(valueOf2) : new String(str8));
            } else if (!TextUtils.isEmpty(str3)) {
                String valueOf3 = String.valueOf(str4);
                String valueOf4 = String.valueOf(str);
                zzag.zzau(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3));
            } else if (str6 == null) {
                zzag.zzd(null);
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(str6);
                    if (zzakb.zzqp()) {
                        String str9 = "Result GMSG: ";
                        String valueOf5 = String.valueOf(jSONObject.toString(2));
                        zzakb.m1419v(valueOf5.length() != 0 ? str9.concat(valueOf5) : new String(str9));
                    }
                    zzag.zzd(jSONObject);
                } catch (JSONException e) {
                    zzag.zzau(e.getMessage());
                }
            }
        }
    }

    public final void zza(String str, zzag zzag) {
        synchronized (this.mLock) {
            this.zzbnf.put(str, zzag);
        }
    }
}
