package com.startapp.android.publish.adsCommon.p082f;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Pair;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.startapp.android.publish.adsCommon.BaseRequest;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4939c;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.List;
import org.json.JSONArray;

/* renamed from: com.startapp.android.publish.adsCommon.f.e */
/* compiled from: StartAppSDK */
public class C5016e extends BaseRequest {

    /* renamed from: a */
    private C5015d f3243a;

    /* renamed from: b */
    private String f3244b;

    /* renamed from: c */
    private String f3245c;

    /* renamed from: d */
    private String f3246d;

    /* renamed from: e */
    private String f3247e;

    /* renamed from: f */
    private String f3248f;

    /* renamed from: g */
    private String f3249g;

    /* renamed from: h */
    private Long f3250h;

    /* renamed from: i */
    private String f3251i;

    /* renamed from: j */
    private String f3252j;

    /* renamed from: k */
    private JSONArray f3253k;

    public C5016e(C5015d dVar) {
        String str = "";
        this(dVar, str, str);
    }

    public C5016e(C5015d dVar, String str, String str2) {
        this.f3243a = dVar;
        this.f3244b = str;
        this.f3245c = str2;
    }

    public C4941e getNameValueJson() {
        C4941e nameValueJson = super.getNameValueJson();
        if (nameValueJson == null) {
            nameValueJson = new C4939c();
        }
        m3236a(nameValueJson);
        return nameValueJson;
    }

    /* renamed from: a */
    private void m3236a(C4941e eVar) {
        String d = C5138a.m3718d();
        eVar.mo62030a(C5138a.m3710a(), (Object) d, true);
        eVar.mo62030a(C5138a.m3714b(), (Object) C5138a.m3715b(d), true);
        eVar.mo62030a("category", (Object) mo62287e().mo62283a(), true);
        eVar.mo62030a(Param.VALUE, (Object) mo62289f(), false);
        eVar.mo62030a("d", (Object) mo62293h(), false);
        eVar.mo62030a("orientation", (Object) mo62295i(), false);
        eVar.mo62030a("usedRam", (Object) mo62297j(), false);
        eVar.mo62030a("freeRam", (Object) mo62298k(), false);
        eVar.mo62030a("sessionTime", (Object) mo62299l(), false);
        eVar.mo62030a("appActivity", (Object) mo62300m(), false);
        eVar.mo62030a("details", (Object) mo62291g(), false);
        eVar.mo62030a("details_json", (Object) mo62301n(), false);
        eVar.mo62030a("cellScanRes", (Object) mo62302o(), false);
        Pair c = C5053l.m3366c();
        Pair d2 = C5053l.m3369d();
        eVar.mo62030a((String) c.first, c.second, false);
        eVar.mo62030a((String) d2.first, d2.second, false);
    }

    /* renamed from: e */
    public C5015d mo62287e() {
        return this.f3243a;
    }

    /* renamed from: f */
    public String mo62289f() {
        return this.f3244b;
    }

    /* renamed from: d */
    public void mo62286d(String str) {
        this.f3244b = str;
    }

    /* renamed from: g */
    public String mo62291g() {
        return this.f3245c;
    }

    /* renamed from: h */
    public String mo62293h() {
        return this.f3246d;
    }

    /* renamed from: e */
    public void mo62288e(String str) {
        this.f3246d = str;
    }

    /* renamed from: i */
    public String mo62295i() {
        return this.f3247e;
    }

    /* renamed from: f */
    public void mo62290f(String str) {
        this.f3247e = str;
    }

    /* renamed from: j */
    public String mo62297j() {
        return this.f3248f;
    }

    /* renamed from: g */
    public void mo62292g(String str) {
        this.f3248f = str;
    }

    /* renamed from: k */
    public String mo62298k() {
        return this.f3249g;
    }

    /* renamed from: h */
    public void mo62294h(String str) {
        this.f3249g = str;
    }

    /* renamed from: l */
    public Long mo62299l() {
        return this.f3250h;
    }

    /* renamed from: m */
    public String mo62300m() {
        return this.f3251i;
    }

    /* renamed from: n */
    public JSONArray mo62301n() {
        return this.f3253k;
    }

    /* renamed from: a */
    public void mo62285a(JSONArray jSONArray) {
        this.f3253k = jSONArray;
    }

    /* renamed from: o */
    public String mo62302o() {
        return this.f3252j;
    }

    /* renamed from: i */
    public void mo62296i(String str) {
        this.f3252j = str;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo62284a(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                List a = C5146c.m3747a(context, telephonyManager);
                if (a != null && a.size() > 0) {
                    mo62296i(C5138a.m3717c(a.toString()));
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot fillCellDetails ");
            sb.append(e.getMessage());
            C5155g.m3805a(6, sb.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InfoEventRequest [category=");
        sb.append(this.f3243a.mo62283a());
        sb.append(", value=");
        sb.append(this.f3244b);
        sb.append(", details=");
        sb.append(this.f3245c);
        sb.append(", d=");
        sb.append(this.f3246d);
        sb.append(", orientation=");
        sb.append(this.f3247e);
        sb.append(", usedRam=");
        sb.append(this.f3248f);
        sb.append(", freeRam=");
        sb.append(this.f3249g);
        sb.append(", sessionTime=");
        sb.append(this.f3250h);
        sb.append(", appActivity=");
        sb.append(this.f3251i);
        sb.append(", details_json=");
        sb.append(this.f3253k);
        sb.append(", cellScanRes=");
        sb.append(this.f3252j);
        sb.append("]");
        return sb.toString();
    }
}
