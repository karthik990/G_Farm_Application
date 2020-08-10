package com.startapp.android.publish.adsCommon.Utils;

import com.startapp.common.C5186e;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.c */
/* compiled from: StartAppSDK */
public class C4939c extends C4941e {

    /* renamed from: a */
    private JSONObject f3046a;

    public C4939c() {
        this.f3046a = null;
        this.f3046a = new JSONObject();
    }

    /* renamed from: a */
    public void mo62026a(String str, Object obj, boolean z, boolean z2) {
        if (z && obj == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Required key: [");
            sb.append(str);
            sb.append("] is missing");
            throw new C5186e(sb.toString(), null);
        } else if (obj != null && !obj.toString().equals("")) {
            try {
                this.f3046a.put(str, obj);
            } catch (JSONException e) {
                if (z) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("failed converting to json object value: [");
                    sb2.append(obj);
                    sb2.append("]");
                    throw new C5186e(sb2.toString(), e);
                }
            }
        }
    }

    /* renamed from: a */
    public void mo62027a(String str, Set<String> set, boolean z, boolean z2) {
        if (z && (set == null || set.size() == 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Required key: [");
            sb.append(str);
            sb.append("] is missing");
            throw new C5186e(sb.toString(), null);
        } else if (set != null && set.size() > 0) {
            try {
                this.f3046a.put(str, new JSONArray(set));
            } catch (JSONException e) {
                if (z) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("failed converting to json array values: [");
                    sb2.append(set);
                    sb2.append("]");
                    throw new C5186e(sb2.toString(), e);
                }
            }
        }
    }

    public String toString() {
        return this.f3046a.toString();
    }
}
