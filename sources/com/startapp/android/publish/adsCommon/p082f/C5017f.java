package com.startapp.android.publish.adsCommon.p082f;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.support.p009v4.media.session.PlaybackStateCompat;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5018g.C5020a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.adsCommon.f.f */
/* compiled from: StartAppSDK */
public class C5017f {
    /* renamed from: a */
    public static void m3256a(Context context, C5015d dVar, String str, String str2, String str3) {
        m3259a(context, new C5016e(dVar, str, str2), str3, null);
    }

    /* renamed from: a */
    public static void m3257a(Context context, C5015d dVar, String str, String str2, String str3, C5020a aVar) {
        m3259a(context, new C5016e(dVar, str, str2), str3, aVar);
    }

    /* renamed from: a */
    public static void m3258a(Context context, C5016e eVar, String str) {
        m3259a(context, eVar, str, null);
    }

    /* renamed from: a */
    public static void m3259a(Context context, C5016e eVar, String str, C5020a aVar) {
        String str2 = "InfoEventsManager";
        if (!MetaData.getInstance().getAnalyticsConfig().mo62266c()) {
            eVar.mo62288e(str);
            eVar.mo62284a(context);
            try {
                eVar.mo62290f(C4946i.m2929b(context));
                MemoryInfo memoryInfo = new MemoryInfo();
                ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
                eVar.mo62294h(Long.toString(memoryInfo.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED));
                Long a = C5146c.m3745a(memoryInfo);
                if (a != null) {
                    eVar.mo62292g(Long.toString((a.longValue() - memoryInfo.availMem) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED));
                }
            } catch (Throwable th) {
                C5155g.m3808a(str2, 6, "Error filling infoEvent", th);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Sending ");
            sb.append(eVar);
            C5155g.m3807a(str2, 3, sb.toString());
            new C5018g(context, new AdPreferences(), eVar, aVar).mo62303a();
        }
    }
}
