package com.google.android.gms.internal.ads;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.p012os.EnvironmentCompat;
import com.google.android.gms.common.wrappers.Wrappers;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.ParametersAreNonnullByDefault;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@zzadh
@ParametersAreNonnullByDefault
public final class zzadb implements zzadf {
    private static final Object sLock = new Object();
    private static zzadf zzcbw = null;
    private static zzadf zzcbx = null;
    private final Context zzatx;
    private final Object zzcby;
    private final WeakHashMap<Thread, Boolean> zzcbz;
    private final ExecutorService zzru;
    private final zzang zzzw;

    private zzadb(Context context) {
        this(context, zzang.zzsl());
    }

    private zzadb(Context context, zzang zzang) {
        this.zzcby = new Object();
        this.zzcbz = new WeakHashMap<>();
        this.zzru = Executors.newCachedThreadPool();
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        this.zzatx = context;
        this.zzzw = zzang;
    }

    private final Builder zza(String str, String str2, String str3, int i) {
        boolean z;
        String str4;
        try {
            z = Wrappers.packageManager(this.zzatx).isCallerInstantApp();
        } catch (Throwable th) {
            zzane.zzb("Error fetching instant app info", th);
            z = false;
        }
        try {
            str4 = this.zzatx.getPackageName();
        } catch (Throwable unused) {
            zzane.zzdk("Cannot obtain package name, proceeding.");
            str4 = EnvironmentCompat.MEDIA_UNKNOWN;
        }
        String str5 = "os";
        String str6 = "api";
        Builder appendQueryParameter = new Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("is_aia", Boolean.toString(z)).appendQueryParameter(TtmlNode.ATTR_ID, "gmob-apps-report-exception").appendQueryParameter(str5, VERSION.RELEASE).appendQueryParameter(str6, String.valueOf(VERSION.SDK_INT));
        String str7 = Build.MANUFACTURER;
        String str8 = Build.MODEL;
        if (!str8.startsWith(str7)) {
            StringBuilder sb = new StringBuilder(String.valueOf(str7).length() + 1 + String.valueOf(str8).length());
            sb.append(str7);
            sb.append(" ");
            sb.append(str8);
            str8 = sb.toString();
        }
        String str9 = "session_id";
        return appendQueryParameter.appendQueryParameter("device", str8).appendQueryParameter("js", this.zzzw.zzcw).appendQueryParameter("appid", str4).appendQueryParameter("exceptiontype", str).appendQueryParameter("stacktrace", str2).appendQueryParameter("eids", TextUtils.join(",", zznk.zzjb())).appendQueryParameter("exceptionkey", str3).appendQueryParameter("cl", "193400285").appendQueryParameter("rc", "dev").appendQueryParameter(str9, zzkb.zzih()).appendQueryParameter(SettingsJsonConstants.ANALYTICS_SAMPLING_RATE_KEY, Integer.toString(i)).appendQueryParameter("pb_tm", String.valueOf(zzkb.zzik().zzd(zznk.zzbfo)));
    }

    public static zzadf zzc(Context context, zzang zzang) {
        synchronized (sLock) {
            if (zzcbx == null) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzauh)).booleanValue()) {
                    zzadb zzadb = new zzadb(context, zzang);
                    Thread thread = Looper.getMainLooper().getThread();
                    if (thread != null) {
                        synchronized (zzadb.zzcby) {
                            zzadb.zzcbz.put(thread, Boolean.valueOf(true));
                        }
                        thread.setUncaughtExceptionHandler(new zzadd(zzadb, thread.getUncaughtExceptionHandler()));
                    }
                    Thread.setDefaultUncaughtExceptionHandler(new zzadc(zzadb, Thread.getDefaultUncaughtExceptionHandler()));
                    zzcbx = zzadb;
                } else {
                    zzcbx = new zzadg();
                }
            }
        }
        return zzcbx;
    }

    public static zzadf zzl(Context context) {
        synchronized (sLock) {
            if (zzcbw == null) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzauh)).booleanValue()) {
                    zzcbw = new zzadb(context);
                } else {
                    zzcbw = new zzadg();
                }
            }
        }
        return zzcbw;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        if (r3 == false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(java.lang.Thread r10, java.lang.Throwable r11) {
        /*
            r9 = this;
            r10 = 1
            r0 = 0
            if (r11 == 0) goto L_0x0042
            r1 = r11
            r2 = 0
            r3 = 0
        L_0x0007:
            if (r1 == 0) goto L_0x003d
            java.lang.StackTraceElement[] r4 = r1.getStackTrace()
            int r5 = r4.length
            r6 = r3
            r3 = r2
            r2 = 0
        L_0x0011:
            if (r2 >= r5) goto L_0x0036
            r7 = r4[r2]
            java.lang.String r8 = r7.getClassName()
            boolean r8 = com.google.android.gms.internal.ads.zzamu.zzdf(r8)
            if (r8 == 0) goto L_0x0020
            r3 = 1
        L_0x0020:
            java.lang.Class r8 = r9.getClass()
            java.lang.String r8 = r8.getName()
            java.lang.String r7 = r7.getClassName()
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x0033
            r6 = 1
        L_0x0033:
            int r2 = r2 + 1
            goto L_0x0011
        L_0x0036:
            java.lang.Throwable r1 = r1.getCause()
            r2 = r3
            r3 = r6
            goto L_0x0007
        L_0x003d:
            if (r2 == 0) goto L_0x0042
            if (r3 != 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            r10 = 0
        L_0x0043:
            if (r10 == 0) goto L_0x004c
            r10 = 1065353216(0x3f800000, float:1.0)
            java.lang.String r0 = ""
            r9.zza(r11, r0, r10)
        L_0x004c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzadb.zza(java.lang.Thread, java.lang.Throwable):void");
    }

    public final void zza(Throwable th, String str) {
        zza(th, str, 1.0f);
    }

    public final void zza(Throwable th, String str, float f) {
        if (zzamu.zzc(th) != null) {
            String name = th.getClass().getName();
            StringWriter stringWriter = new StringWriter();
            zzazr.zza(th, new PrintWriter(stringWriter));
            String stringWriter2 = stringWriter.toString();
            int i = 0;
            int i2 = 1;
            boolean z = Math.random() < ((double) f);
            if (f > 0.0f) {
                i2 = (int) (1.0f / f);
            }
            if (z) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(zza(name, stringWriter2, str, i2).toString());
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    this.zzru.submit(new zzade(this, new zzanf(), (String) obj));
                }
            }
        }
    }
}
