package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

@zzadh
@ParametersAreNonnullByDefault
public final class zzoh implements zzbfy {
    private CustomTabsSession zzbgw;
    private CustomTabsClient zzbgx;
    private CustomTabsServiceConnection zzbgy;
    private zzoi zzbgz;

    public static boolean zzh(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        List queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        if (!(queryIntentActivities == null || resolveActivity == null)) {
            for (int i = 0; i < queryIntentActivities.size(); i++) {
                if (resolveActivity.activityInfo.name.equals(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.name)) {
                    return resolveActivity.activityInfo.packageName.equals(zzbfw.zzbn(context));
                }
            }
        }
        return false;
    }

    public final boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> list) {
        CustomTabsClient customTabsClient = this.zzbgx;
        if (customTabsClient == null) {
            return false;
        }
        if (customTabsClient == null) {
            this.zzbgw = null;
        } else if (this.zzbgw == null) {
            this.zzbgw = customTabsClient.newSession(null);
        }
        CustomTabsSession customTabsSession = this.zzbgw;
        if (customTabsSession == null) {
            return false;
        }
        return customTabsSession.mayLaunchUrl(uri, null, null);
    }

    public final void zza(CustomTabsClient customTabsClient) {
        this.zzbgx = customTabsClient;
        this.zzbgx.warmup(0);
        zzoi zzoi = this.zzbgz;
        if (zzoi != null) {
            zzoi.zzjp();
        }
    }

    public final void zza(zzoi zzoi) {
        this.zzbgz = zzoi;
    }

    public final void zzc(Activity activity) {
        CustomTabsServiceConnection customTabsServiceConnection = this.zzbgy;
        if (customTabsServiceConnection != null) {
            activity.unbindService(customTabsServiceConnection);
            this.zzbgx = null;
            this.zzbgw = null;
            this.zzbgy = null;
        }
    }

    public final void zzd(Activity activity) {
        if (this.zzbgx == null) {
            String zzbn = zzbfw.zzbn(activity);
            if (zzbn != null) {
                this.zzbgy = new zzbfx(this);
                CustomTabsClient.bindCustomTabsService(activity, zzbn, this.zzbgy);
            }
        }
    }

    public final void zzjo() {
        this.zzbgx = null;
        this.zzbgw = null;
        zzoi zzoi = this.zzbgz;
        if (zzoi != null) {
            zzoi.zzjq();
        }
    }
}
