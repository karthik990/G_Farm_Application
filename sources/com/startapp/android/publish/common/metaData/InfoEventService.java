package com.startapp.android.publish.common.metaData;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.startapp.common.p093b.C5164a;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;

/* compiled from: StartAppSDK */
public class InfoEventService extends Service {

    /* renamed from: a */
    private static final String f3441a = "InfoEventService";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        C5164a.m3838a((Context) this);
        boolean a = C5164a.m3850a(intent, (C5172b) new C5172b() {
            /* renamed from: a */
            public void mo62538a(C5171a aVar) {
                InfoEventService.this.stopSelf();
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("onHandleIntent: RunnerManager.runJob");
        sb.append(a);
        C5164a.m3843a(3, f3441a, sb.toString(), (Throwable) null);
        return super.onStartCommand(intent, i, i2);
    }
}
