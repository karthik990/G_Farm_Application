package com.startapp.android.publish.adsCommon.Utils;

import android.content.Context;
import com.startapp.android.publish.adsCommon.p077a.C4954b;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.p092a.C5155g;
import java.util.UUID;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.g */
/* compiled from: StartAppSDK */
public class C4944g {

    /* renamed from: a */
    private static C4944g f3050a = new C4944g();

    /* renamed from: b */
    private String f3051b = "";

    /* renamed from: c */
    private long f3052c = 0;

    /* renamed from: d */
    private C5109a f3053d = C5109a.LAUNCH;

    /* renamed from: a */
    public String mo62033a() {
        return this.f3051b;
    }

    /* renamed from: b */
    public long mo62035b() {
        return this.f3052c;
    }

    /* renamed from: c */
    public C5109a mo62036c() {
        return this.f3053d;
    }

    /* renamed from: a */
    public synchronized void mo62034a(Context context, C5109a aVar) {
        this.f3051b = UUID.randomUUID().toString();
        this.f3052c = System.currentTimeMillis();
        this.f3053d = aVar;
        StringBuilder sb = new StringBuilder();
        sb.append("Starting new session: reason=");
        sb.append(aVar);
        sb.append(" sessionId=");
        sb.append(this.f3051b);
        C5155g.m3807a("SessionManager", 3, sb.toString());
        if (!C4946i.m2922a()) {
            C4954b.m2946a().mo62056b();
        }
        AdPreferences adPreferences = new AdPreferences();
        C4946i.m2913a(context, adPreferences);
        MetaData.getInstance().loadFromServer(context, adPreferences, aVar, false, null, true);
    }

    /* renamed from: d */
    public static C4944g m2886d() {
        return f3050a;
    }
}
