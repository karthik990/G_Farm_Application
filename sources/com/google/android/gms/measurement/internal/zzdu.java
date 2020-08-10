package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

final class zzdu implements ActivityLifecycleCallbacks {
    private final /* synthetic */ zzda zzarh;

    private zzdu(zzda zzda) {
        this.zzarh = zzda;
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zzarh.zzgt().zzjo().zzby("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    String str = "auto";
                    if (bundle == null) {
                        Bundle zza = this.zzarh.zzgr().zza(data);
                        this.zzarh.zzgr();
                        String str2 = zzfx.zzc(intent) ? "gs" : str;
                        if (zza != null) {
                            this.zzarh.logEvent(str2, "_cmp", zza);
                        }
                    }
                    String queryParameter = data.getQueryParameter("referrer");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        if (!(queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content")))) {
                            this.zzarh.zzgt().zzjn().zzby("Activity created with data 'referrer' param without gclid and at least one utm field");
                            return;
                        }
                        this.zzarh.zzgt().zzjn().zzg("Activity created with referrer", queryParameter);
                        if (!TextUtils.isEmpty(queryParameter)) {
                            this.zzarh.zzb(str, "_ldl", (Object) queryParameter, true);
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            this.zzarh.zzgt().zzjg().zzg("Throwable caught in onActivityCreated", e);
        }
        this.zzarh.zzgm().onActivityCreated(activity, bundle);
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zzarh.zzgm().onActivityDestroyed(activity);
    }

    public final void onActivityPaused(Activity activity) {
        this.zzarh.zzgm().onActivityPaused(activity);
        zzfd zzgo = this.zzarh.zzgo();
        zzgo.zzgs().zzc((Runnable) new zzfh(zzgo, zzgo.zzbx().elapsedRealtime()));
    }

    public final void onActivityResumed(Activity activity) {
        this.zzarh.zzgm().onActivityResumed(activity);
        zzfd zzgo = this.zzarh.zzgo();
        zzgo.zzgs().zzc((Runnable) new zzfg(zzgo, zzgo.zzbx().elapsedRealtime()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zzarh.zzgm().onActivitySaveInstanceState(activity, bundle);
    }

    /* synthetic */ zzdu(zzda zzda, zzdb zzdb) {
        this(zzda);
    }
}
