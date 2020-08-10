package com.startapp.android.publish.adsCommon.Utils;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.C5117e;
import com.startapp.android.publish.common.metaData.C5119f;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p093b.C5164a;
import com.startapp.common.p093b.C5174b.C5176a;
import com.startapp.common.p093b.p094a.C5169a;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5173c;

/* renamed from: com.startapp.android.publish.adsCommon.Utils.b */
/* compiled from: StartAppSDK */
public class C4936b {

    /* renamed from: a */
    private static volatile boolean f3045a = false;

    /* renamed from: com.startapp.android.publish.adsCommon.Utils.b$a */
    /* compiled from: StartAppSDK */
    public static final class C4938a implements C5169a {
        public C5170b create(int i) {
            if (i == 586482792) {
                return new C5119f();
            }
            if (i != 786564404) {
                return null;
            }
            return new C5117e();
        }
    }

    /* renamed from: a */
    public static void m2865a(Context context) {
        if (!f3045a) {
            f3045a = true;
            C5164a.m3847a((C5173c) new C5173c() {
                /* renamed from: a */
                public void mo62025a(int i, String str, String str2, Throwable th) {
                    C5155g.m3808a(str, i, str2, th);
                }
            });
            C5164a.m3838a(context);
            C5164a.m3846a((C5169a) new C4938a());
        }
    }

    /* renamed from: a */
    public static long m2863a() {
        return SystemClock.elapsedRealtime() + (((long) MetaData.getInstance().getPeriodicMetaDataInterval()) * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
    }

    /* renamed from: b */
    public static long m2869b(Context context) {
        return SystemClock.elapsedRealtime() + (((long) MetaData.getInstance().getPeriodicInfoEventIntervalInMinutes(context)) * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
    }

    /* renamed from: c */
    public static void m2870c(Context context) {
        m2868a(context, Long.valueOf(m2869b(context)));
    }

    /* renamed from: a */
    public static void m2868a(Context context, Long l) {
        StringBuilder sb = new StringBuilder();
        sb.append("setMetaDataPeriodicAlarm executes ");
        sb.append(l);
        C5155g.m3807a("StartAppWall.DataUtils", 3, sb.toString());
        if (!C5051k.m3335a(context, "periodicMetadataPaused", Boolean.valueOf(false)).booleanValue() && MetaData.getInstance().isPeriodicMetaDataEnabled()) {
            m2866a(context, 586482792, l.longValue() - SystemClock.elapsedRealtime(), "periodicMetadataTriggerTime");
        }
    }

    /* renamed from: d */
    public static void m2871d(Context context) {
        m2867a(context, m2869b(context));
    }

    /* renamed from: a */
    public static void m2867a(Context context, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("setInfoEventPeriodicAlarm executes ");
        sb.append(j);
        C5155g.m3807a("StartAppWall.DataUtils", 3, sb.toString());
        if (!C5051k.m3335a(context, "periodicInfoEventPaused", Boolean.valueOf(false)).booleanValue() && MetaData.getInstance().isPeriodicInfoEventEnabled()) {
            m2866a(context, 786564404, j - SystemClock.elapsedRealtime(), "periodicInfoEventTriggerTime");
        }
    }

    /* renamed from: a */
    public static void m2864a(int i) {
        C5164a.m3844a(i, false);
    }

    /* renamed from: a */
    private static void m2866a(Context context, int i, long j, String str) {
        if (C5164a.m3851a(new C5176a(i).mo62892a(j).mo62896a())) {
            C5051k.m3345b(context, str, Long.valueOf(j + SystemClock.elapsedRealtime()));
            return;
        }
        C5015d dVar = C5015d.EXCEPTION;
        StringBuilder sb = new StringBuilder();
        sb.append("Util.setPeriodicAlarm - failed setting alarm ");
        sb.append(i);
        String sb2 = sb.toString();
        String str2 = "";
        C5017f.m3256a(context, dVar, sb2, str2, str2);
    }
}
