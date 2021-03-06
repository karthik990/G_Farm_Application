package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzaf;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import p043io.netty.util.internal.StringUtil;

@Deprecated
public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    private static GoogleCloudMessaging zzac;
    private static final AtomicInteger zzaf = new AtomicInteger(1);
    private PendingIntent zzad;
    private final Map<String, Handler> zzae = Collections.synchronizedMap(new ArrayMap());
    /* access modifiers changed from: private */
    public final BlockingQueue<Intent> zzag = new LinkedBlockingQueue();
    private final Messenger zzah = new Messenger(new zzf(this, Looper.getMainLooper()));
    /* access modifiers changed from: private */
    public Context zzk;

    @Deprecated
    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (zzac == null) {
                zze(context);
                GoogleCloudMessaging googleCloudMessaging2 = new GoogleCloudMessaging();
                zzac = googleCloudMessaging2;
                googleCloudMessaging2.zzk = context.getApplicationContext();
            }
            googleCloudMessaging = zzac;
        }
        return googleCloudMessaging;
    }

    @Deprecated
    private final Intent zzd(Bundle bundle, boolean z) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzf(this.zzk) >= 0) {
            Intent intent = new Intent(z ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzaf.zzl(this.zzk));
            zze(intent);
            int andIncrement = zzaf.getAndIncrement();
            StringBuilder sb = new StringBuilder(21);
            sb.append("google.rpc");
            sb.append(andIncrement);
            intent.putExtra("google.message_id", sb.toString());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.zzah);
            Context context = this.zzk;
            if (z) {
                context.sendBroadcast(intent);
            } else {
                context.startService(intent);
            }
            try {
                return (Intent) this.zzag.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        } else {
            throw new IOException("Google Play Services missing");
        }
    }

    @Deprecated
    private final synchronized String zzd(boolean z, String... strArr) throws IOException {
        String zzl = zzaf.zzl(this.zzk);
        if (zzl == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        } else if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        } else {
            StringBuilder sb = new StringBuilder(strArr[0]);
            for (int i = 1; i < strArr.length; i++) {
                sb.append(StringUtil.COMMA);
                sb.append(strArr[i]);
            }
            String sb2 = sb.toString();
            Bundle bundle = new Bundle();
            if (zzl.contains(".gsf")) {
                bundle.putString("legacy.sender", sb2);
                return InstanceID.getInstance(this.zzk).getToken(sb2, "GCM", bundle);
            }
            bundle.putString("sender", sb2);
            Intent zzd = zzd(bundle, z);
            String str = "registration_id";
            if (zzd != null) {
                String stringExtra = zzd.getStringExtra(str);
                if (stringExtra != null) {
                    return stringExtra;
                }
                String stringExtra2 = zzd.getStringExtra("error");
                if (stringExtra2 != null) {
                    throw new IOException(stringExtra2);
                }
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzd(Intent intent) {
        String stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.zzae.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    static void zze(Context context) {
        String packageName = context.getPackageName();
        StringBuilder sb = new StringBuilder(String.valueOf(packageName).length() + 48);
        sb.append("GCM SDK is deprecated, ");
        sb.append(packageName);
        sb.append(" should update to use FCM");
        Log.w("GCM", sb.toString());
    }

    private final synchronized void zze(Intent intent) {
        if (this.zzad == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzad = PendingIntent.getBroadcast(this.zzk, 0, intent2, 0);
        }
        intent.putExtra(SettingsJsonConstants.APP_KEY, this.zzad);
    }

    public static int zzf(Context context) {
        String zzl = zzaf.zzl(context);
        if (zzl != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(zzl, 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException unused) {
            }
        }
        return -1;
    }

    private final synchronized void zzg() {
        if (this.zzad != null) {
            this.zzad.cancel();
            this.zzad = null;
        }
    }

    @Deprecated
    public void close() {
        zzac = null;
        zzd.zzj = null;
        zzg();
    }

    @Deprecated
    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra != null ? stringExtra : MESSAGE_TYPE_MESSAGE;
    }

    @Deprecated
    public synchronized String register(String... strArr) throws IOException {
        return zzd(zzaf.zzk(this.zzk), strArr);
    }

    @Deprecated
    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        if (str != null) {
            String zzl = zzaf.zzl(this.zzk);
            if (zzl != null) {
                Intent intent = new Intent("com.google.android.gcm.intent.SEND");
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                zze(intent);
                intent.setPackage(zzl);
                String str3 = "google.to";
                intent.putExtra(str3, str);
                String str4 = "google.message_id";
                intent.putExtra(str4, str2);
                intent.putExtra("google.ttl", Long.toString(j));
                int indexOf = str.indexOf(64);
                String substring = indexOf > 0 ? str.substring(0, indexOf) : str;
                InstanceID.getInstance(this.zzk);
                String str5 = "GCM";
                intent.putExtra("google.from", InstanceID.zzn().zze("", substring, str5));
                if (zzl.contains(".gsf")) {
                    Bundle bundle2 = new Bundle();
                    for (String str6 : bundle.keySet()) {
                        Object obj = bundle.get(str6);
                        if (obj instanceof String) {
                            String str7 = "gcm.";
                            String valueOf = String.valueOf(str6);
                            bundle2.putString(valueOf.length() != 0 ? str7.concat(valueOf) : new String(str7), (String) obj);
                        }
                    }
                    bundle2.putString(str3, str);
                    bundle2.putString(str4, str2);
                    InstanceID.getInstance(this.zzk).zze(str5, "upstream", bundle2);
                    return;
                }
                this.zzk.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
                return;
            }
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        throw new IllegalArgumentException("Missing 'to'");
    }

    @Deprecated
    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    @Deprecated
    public synchronized void unregister() throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            InstanceID.getInstance(this.zzk).deleteInstanceID();
        } else {
            throw new IOException("MAIN_THREAD");
        }
    }
}
