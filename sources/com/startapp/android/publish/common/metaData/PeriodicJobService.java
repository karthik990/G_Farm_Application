package com.startapp.android.publish.common.metaData;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import com.startapp.common.p093b.C5164a;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;

/* compiled from: StartAppSDK */
public class PeriodicJobService extends JobService {

    /* renamed from: a */
    private static final String f3457a = "PeriodicJobService";

    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    public boolean onStartJob(final JobParameters jobParameters) {
        C5164a.m3838a((Context) this);
        boolean a = C5164a.m3849a(jobParameters, (C5172b) new C5172b() {
            /* renamed from: a */
            public void mo62538a(C5171a aVar) {
                PeriodicJobService.this.jobFinished(jobParameters, false);
            }
        });
        StringBuilder sb = new StringBuilder();
        sb.append("onStartJob: RunnerManager.runJob");
        sb.append(a);
        C5164a.m3843a(3, f3457a, sb.toString(), (Throwable) null);
        return a;
    }
}
