package com.startapp.android.publish.adsCommon.p081e;

import android.content.Context;
import android.text.TextUtils;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p082f.C5018g.C5020a;
import com.startapp.common.p093b.C5164a;
import com.startapp.common.p093b.C5174b.C5176a;
import com.startapp.common.p093b.p094a.C5169a;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Map;

/* renamed from: com.startapp.android.publish.adsCommon.e.a */
/* compiled from: StartAppSDK */
public class C5004a implements UncaughtExceptionHandler {

    /* renamed from: a */
    private final UncaughtExceptionHandler f3206a = Thread.getDefaultUncaughtExceptionHandler();

    /* renamed from: com.startapp.android.publish.adsCommon.e.a$a */
    /* compiled from: StartAppSDK */
    public static final class C5005a implements C5169a {
        public C5170b create(int i) {
            if (i != 868418937) {
                return null;
            }
            return new C5170b() {
                /* renamed from: a */
                public void mo44557a(Context context, int i, Map<String, String> map, final C5172b bVar) {
                    String str = (String) map.get("KEY_STACK_TRACE");
                    if (!TextUtils.isEmpty(str)) {
                        if (str.length() > 1000) {
                            str = str.substring(0, 1000);
                        }
                        String str2 = "uncaughtException";
                        Context context2 = context;
                        C5017f.m3257a(context2, C5015d.EXCEPTION, str2, str, "", new C5020a() {
                            /* renamed from: a */
                            public void mo62257a(boolean z) {
                                bVar.mo62538a(C5171a.SUCCESS);
                            }
                        });
                    }
                }
            };
        }
    }

    /* renamed from: a */
    public static synchronized void m3202a(Context context) {
        synchronized (C5004a.class) {
            new C5004a(context);
        }
    }

    private C5004a(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
        C5164a.m3838a(context);
        C5164a.m3846a((C5169a) new C5005a());
    }

    public void uncaughtException(Thread thread, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String stringWriter2 = stringWriter.toString();
        if (!TextUtils.isEmpty(stringWriter2) && (stringWriter2.contains("startapp") || stringWriter2.contains("truenet"))) {
            C5164a.m3851a(new C5176a(868418937).mo62892a(1000).mo62893a("KEY_STACK_TRACE", stringWriter2).mo62896a());
        }
        this.f3206a.uncaughtException(thread, th);
    }
}
