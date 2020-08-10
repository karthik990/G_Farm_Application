package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Tasks;
import java.io.IOException;
import java.security.KeyPair;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public final class zzaf {
    private static int zzck = 0;
    private static final zzaj<Boolean> zzco = zzai.zzw().zzd("gcm_iid_use_messenger_ipc", true);
    private static String zzcp = null;
    private static boolean zzcq = false;
    private static int zzcr = 0;
    private static int zzcs = 0;
    private static BroadcastReceiver zzct = null;
    private PendingIntent zzad;
    private Messenger zzah;
    private Map<String, Object> zzcu = new ArrayMap();
    private Messenger zzcv;
    private MessengerCompat zzcw;
    private Context zzk;

    public zzaf(Context context) {
        this.zzk = context;
    }

    private static void zzd(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Failed to send response ");
                sb.append(valueOf);
                Log.w("InstanceID", sb.toString());
            }
        }
    }

    private final void zzd(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.zzcu.get(str);
            this.zzcu.put(str, obj);
            zzd(obj2, obj);
        }
    }

    private static boolean zzd(PackageManager packageManager, String str) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            zzcp = applicationInfo.packageName;
            zzcs = applicationInfo.uid;
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    private static boolean zzd(PackageManager packageManager, String str, String str2) {
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", str) == 0) {
            return zzd(packageManager, str);
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 56 + String.valueOf(str2).length());
        sb.append("Possible malicious package ");
        sb.append(str);
        sb.append(" declares ");
        sb.append(str2);
        sb.append(" without permission");
        Log.w("InstanceID", sb.toString());
        return false;
    }

    private final synchronized void zzg(Intent intent) {
        if (this.zzad == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzad = PendingIntent.getBroadcast(this.zzk, 0, intent2, 0);
        }
        intent.putExtra(SettingsJsonConstants.APP_KEY, this.zzad);
    }

    static String zzi(Bundle bundle) throws IOException {
        String str = "SERVICE_NOT_AVAILABLE";
        if (bundle != null) {
            String string = bundle.getString("registration_id");
            if (string == null) {
                string = bundle.getString("unregistered");
            }
            if (string != null) {
                return string;
            }
            String string2 = bundle.getString("error");
            if (string2 != null) {
                throw new IOException(string2);
            }
            String valueOf = String.valueOf(bundle);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29);
            sb.append("Unexpected response from GCM ");
            sb.append(valueOf);
            Log.w("InstanceID", sb.toString(), new Throwable());
            throw new IOException(str);
        }
        throw new IOException(str);
    }

    private final Bundle zzj(Bundle bundle) throws IOException {
        Bundle zzk2 = zzk(bundle);
        if (zzk2 == null) {
            return zzk2;
        }
        String str = "google.messenger";
        if (!zzk2.containsKey(str)) {
            return zzk2;
        }
        Bundle zzk3 = zzk(bundle);
        if (zzk3 == null || !zzk3.containsKey(str)) {
            return zzk3;
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x0186 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final android.os.Bundle zzk(android.os.Bundle r8) throws java.io.IOException {
        /*
            r7 = this;
            android.os.ConditionVariable r0 = new android.os.ConditionVariable
            r0.<init>()
            java.lang.String r1 = zzv()
            java.lang.Class r2 = r7.getClass()
            monitor-enter(r2)
            java.util.Map<java.lang.String, java.lang.Object> r3 = r7.zzcu     // Catch:{ all -> 0x01d7 }
            r3.put(r1, r0)     // Catch:{ all -> 0x01d7 }
            monitor-exit(r2)     // Catch:{ all -> 0x01d7 }
            android.os.Messenger r2 = r7.zzah
            if (r2 != 0) goto L_0x002d
            android.content.Context r2 = r7.zzk
            zzl(r2)
            android.os.Messenger r2 = new android.os.Messenger
            com.google.android.gms.iid.zzag r3 = new com.google.android.gms.iid.zzag
            android.os.Looper r4 = android.os.Looper.getMainLooper()
            r3.<init>(r7, r4)
            r2.<init>(r3)
            r7.zzah = r2
        L_0x002d:
            java.lang.String r2 = zzcp
            if (r2 == 0) goto L_0x01cf
            android.content.Intent r2 = new android.content.Intent
            boolean r3 = zzcq
            if (r3 == 0) goto L_0x003a
            java.lang.String r3 = "com.google.iid.TOKEN_REQUEST"
            goto L_0x003c
        L_0x003a:
            java.lang.String r3 = "com.google.android.c2dm.intent.REGISTER"
        L_0x003c:
            r2.<init>(r3)
            java.lang.String r3 = zzcp
            r2.setPackage(r3)
            r2.putExtras(r8)
            r7.zzg(r2)
            java.lang.String r8 = java.lang.String.valueOf(r1)
            int r8 = r8.length()
            int r8 = r8 + 5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r8)
            java.lang.String r8 = "|ID|"
            r3.append(r8)
            r3.append(r1)
            java.lang.String r8 = "|"
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            java.lang.String r3 = "kid"
            r2.putExtra(r3, r8)
            java.lang.String r8 = java.lang.String.valueOf(r1)
            int r8 = r8.length()
            int r8 = r8 + 5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r8)
            java.lang.String r8 = "|ID|"
            r3.append(r8)
            r3.append(r1)
            java.lang.String r8 = "|"
            r3.append(r8)
            java.lang.String r8 = r3.toString()
            java.lang.String r3 = "X-kid"
            r2.putExtra(r3, r8)
            java.lang.String r8 = zzcp
            java.lang.String r3 = "com.google.android.gsf"
            boolean r8 = r3.equals(r8)
            java.lang.String r3 = "useGsf"
            java.lang.String r3 = r2.getStringExtra(r3)
            if (r3 == 0) goto L_0x00aa
            java.lang.String r8 = "1"
            boolean r8 = r8.equals(r3)
        L_0x00aa:
            r3 = 3
            java.lang.String r4 = "InstanceID"
            boolean r4 = android.util.Log.isLoggable(r4, r3)
            if (r4 == 0) goto L_0x00db
            android.os.Bundle r4 = r2.getExtras()
            java.lang.String r4 = java.lang.String.valueOf(r4)
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 8
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Sending "
            r6.append(r5)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            java.lang.String r5 = "InstanceID"
            android.util.Log.d(r5, r4)
        L_0x00db:
            android.os.Messenger r4 = r7.zzcv
            if (r4 == 0) goto L_0x0103
            android.os.Messenger r4 = r7.zzah
            java.lang.String r5 = "google.messenger"
            r2.putExtra(r5, r4)
            android.os.Message r4 = android.os.Message.obtain()
            r4.obj = r2
            android.os.Messenger r5 = r7.zzcv     // Catch:{ RemoteException -> 0x00f3 }
            r5.send(r4)     // Catch:{ RemoteException -> 0x00f3 }
            goto L_0x017c
        L_0x00f3:
            java.lang.String r4 = "InstanceID"
            boolean r4 = android.util.Log.isLoggable(r4, r3)
            if (r4 == 0) goto L_0x0103
            java.lang.String r4 = "InstanceID"
            java.lang.String r5 = "Messenger failed, fallback to startService"
            android.util.Log.d(r4, r5)
        L_0x0103:
            if (r8 == 0) goto L_0x013f
            monitor-enter(r7)
            android.content.BroadcastReceiver r8 = zzct     // Catch:{ all -> 0x013c }
            if (r8 != 0) goto L_0x013a
            com.google.android.gms.iid.zzah r8 = new com.google.android.gms.iid.zzah     // Catch:{ all -> 0x013c }
            r8.<init>(r7)     // Catch:{ all -> 0x013c }
            zzct = r8     // Catch:{ all -> 0x013c }
            java.lang.String r8 = "InstanceID"
            boolean r8 = android.util.Log.isLoggable(r8, r3)     // Catch:{ all -> 0x013c }
            if (r8 == 0) goto L_0x0120
            java.lang.String r8 = "InstanceID"
            java.lang.String r3 = "Registered GSF callback receiver"
            android.util.Log.d(r8, r3)     // Catch:{ all -> 0x013c }
        L_0x0120:
            android.content.IntentFilter r8 = new android.content.IntentFilter     // Catch:{ all -> 0x013c }
            java.lang.String r3 = "com.google.android.c2dm.intent.REGISTRATION"
            r8.<init>(r3)     // Catch:{ all -> 0x013c }
            android.content.Context r3 = r7.zzk     // Catch:{ all -> 0x013c }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ all -> 0x013c }
            r8.addCategory(r3)     // Catch:{ all -> 0x013c }
            android.content.Context r3 = r7.zzk     // Catch:{ all -> 0x013c }
            android.content.BroadcastReceiver r4 = zzct     // Catch:{ all -> 0x013c }
            java.lang.String r5 = "com.google.android.c2dm.permission.SEND"
            r6 = 0
            r3.registerReceiver(r4, r8, r5, r6)     // Catch:{ all -> 0x013c }
        L_0x013a:
            monitor-exit(r7)     // Catch:{ all -> 0x013c }
            goto L_0x0171
        L_0x013c:
            r8 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x013c }
            throw r8
        L_0x013f:
            android.os.Messenger r8 = r7.zzah
            java.lang.String r4 = "google.messenger"
            r2.putExtra(r4, r8)
            java.lang.String r8 = "messenger2"
            java.lang.String r4 = "1"
            r2.putExtra(r8, r4)
            com.google.android.gms.iid.MessengerCompat r8 = r7.zzcw
            if (r8 == 0) goto L_0x016d
            android.os.Message r8 = android.os.Message.obtain()
            r8.obj = r2
            com.google.android.gms.iid.MessengerCompat r4 = r7.zzcw     // Catch:{ RemoteException -> 0x015d }
            r4.send(r8)     // Catch:{ RemoteException -> 0x015d }
            goto L_0x017c
        L_0x015d:
            java.lang.String r8 = "InstanceID"
            boolean r8 = android.util.Log.isLoggable(r8, r3)
            if (r8 == 0) goto L_0x016d
            java.lang.String r8 = "InstanceID"
            java.lang.String r3 = "Messenger failed, fallback to startService"
            android.util.Log.d(r8, r3)
        L_0x016d:
            boolean r8 = zzcq
            if (r8 == 0) goto L_0x0177
        L_0x0171:
            android.content.Context r8 = r7.zzk
            r8.sendBroadcast(r2)
            goto L_0x017c
        L_0x0177:
            android.content.Context r8 = r7.zzk
            r8.startService(r2)
        L_0x017c:
            r2 = 30000(0x7530, double:1.4822E-319)
            r0.block(r2)
            java.lang.Class r8 = r7.getClass()
            monitor-enter(r8)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r7.zzcu     // Catch:{ all -> 0x01cc }
            java.lang.Object r0 = r0.remove(r1)     // Catch:{ all -> 0x01cc }
            boolean r1 = r0 instanceof android.os.Bundle     // Catch:{ all -> 0x01cc }
            if (r1 == 0) goto L_0x0194
            android.os.Bundle r0 = (android.os.Bundle) r0     // Catch:{ all -> 0x01cc }
            monitor-exit(r8)     // Catch:{ all -> 0x01cc }
            return r0
        L_0x0194:
            boolean r1 = r0 instanceof java.lang.String     // Catch:{ all -> 0x01cc }
            if (r1 == 0) goto L_0x01a0
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x01cc }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x01cc }
            r1.<init>(r0)     // Catch:{ all -> 0x01cc }
            throw r1     // Catch:{ all -> 0x01cc }
        L_0x01a0:
            java.lang.String r1 = "InstanceID"
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x01cc }
            java.lang.String r2 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x01cc }
            int r2 = r2.length()     // Catch:{ all -> 0x01cc }
            int r2 = r2 + 12
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cc }
            r3.<init>(r2)     // Catch:{ all -> 0x01cc }
            java.lang.String r2 = "No response "
            r3.append(r2)     // Catch:{ all -> 0x01cc }
            r3.append(r0)     // Catch:{ all -> 0x01cc }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x01cc }
            android.util.Log.w(r1, r0)     // Catch:{ all -> 0x01cc }
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x01cc }
            java.lang.String r1 = "TIMEOUT"
            r0.<init>(r1)     // Catch:{ all -> 0x01cc }
            throw r0     // Catch:{ all -> 0x01cc }
        L_0x01cc:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x01cc }
            throw r0
        L_0x01cf:
            java.io.IOException r8 = new java.io.IOException
            java.lang.String r0 = "MISSING_INSTANCEID_SERVICE"
            r8.<init>(r0)
            throw r8
        L_0x01d7:
            r8 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x01d7 }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzaf.zzk(android.os.Bundle):android.os.Bundle");
    }

    public static boolean zzk(Context context) {
        if (zzcp != null) {
            zzl(context);
        }
        return zzcq;
    }

    public static String zzl(Context context) {
        boolean z;
        String str = zzcp;
        if (str != null) {
            return str;
        }
        zzcr = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        boolean z2 = true;
        if (!PlatformVersion.isAtLeastO()) {
            String str2 = "com.google.android.c2dm.intent.REGISTER";
            Iterator it = packageManager.queryIntentServices(new Intent(str2), 0).iterator();
            while (true) {
                if (it.hasNext()) {
                    if (zzd(packageManager, ((ResolveInfo) it.next()).serviceInfo.packageName, str2)) {
                        zzcq = false;
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                return zzcp;
            }
        }
        String str3 = "com.google.iid.TOKEN_REQUEST";
        Iterator it2 = packageManager.queryBroadcastReceivers(new Intent(str3), 0).iterator();
        while (true) {
            if (it2.hasNext()) {
                if (zzd(packageManager, ((ResolveInfo) it2.next()).activityInfo.packageName, str3)) {
                    zzcq = true;
                    break;
                }
            } else {
                z2 = false;
                break;
            }
        }
        if (z2) {
            return zzcp;
        }
        String str4 = "InstanceID";
        Log.w(str4, "Failed to resolve IID implementation package, falling back");
        if (zzd(packageManager, "com.google.android.gms")) {
            zzcq = PlatformVersion.isAtLeastO();
            return zzcp;
        } else if (PlatformVersion.isAtLeastLollipop() || !zzd(packageManager, "com.google.android.gsf")) {
            Log.w(str4, "Google Play services is missing, unable to get tokens");
            return null;
        } else {
            zzcq = false;
            return zzcp;
        }
    }

    private static int zzm(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzl(context), 0).versionCode;
        } catch (NameNotFoundException unused) {
            return -1;
        }
    }

    private static synchronized String zzv() {
        String num;
        synchronized (zzaf.class) {
            int i = zzck;
            zzck = i + 1;
            num = Integer.toString(i);
        }
        return num;
    }

    /* access modifiers changed from: 0000 */
    public final Bundle zzd(Bundle bundle, KeyPair keyPair) throws IOException {
        int zzm = zzm(this.zzk);
        bundle.putString("gmsv", Integer.toString(zzm));
        bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
        bundle.putString("app_ver", Integer.toString(InstanceID.zzg(this.zzk)));
        bundle.putString("app_ver_name", InstanceID.zzh(this.zzk));
        bundle.putString("cliv", "iid-12451000");
        bundle.putString("appid", InstanceID.zzd(keyPair));
        if (zzm < 12000000 || !((Boolean) zzco.get()).booleanValue()) {
            return zzj(bundle);
        }
        try {
            return (Bundle) Tasks.await(new zzr(this.zzk).zzd(1, bundle));
        } catch (InterruptedException | ExecutionException e) {
            String str = "InstanceID";
            if (Log.isLoggable(str, 3)) {
                String valueOf = String.valueOf(e);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
                sb.append("Error making request: ");
                sb.append(valueOf);
                Log.d(str, sb.toString());
            }
            if (!(e.getCause() instanceof zzaa) || ((zzaa) e.getCause()).getErrorCode() != 4) {
                return null;
            }
            return zzj(bundle);
        }
    }

    public final void zze(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                String str = "google.messenger";
                if (intent.hasExtra(str)) {
                    Parcelable parcelableExtra = intent.getParcelableExtra(str);
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.zzcw = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.zzcv = (Messenger) parcelableExtra;
                    }
                }
                zzh((Intent) message.obj);
                return;
            }
            Log.w("InstanceID", "Dropping invalid message");
        }
    }

    public final void zzh(Intent intent) {
        String str;
        if (intent == null) {
            if (Log.isLoggable("InstanceID", 3)) {
                Log.d("InstanceID", "Unexpected response: null");
            }
            return;
        }
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action) || "com.google.android.gms.iid.InstanceID".equals(action)) {
            String stringExtra = intent.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                String stringExtra2 = intent.getStringExtra("error");
                if (stringExtra2 == null) {
                    String valueOf = String.valueOf(intent.getExtras());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
                    sb.append("Unexpected response, no error or registration id ");
                    sb.append(valueOf);
                    Log.w("InstanceID", sb.toString());
                    return;
                }
                if (Log.isLoggable("InstanceID", 3)) {
                    String str2 = "Received InstanceID error ";
                    String valueOf2 = String.valueOf(stringExtra2);
                    Log.d("InstanceID", valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
                }
                String str3 = null;
                if (stringExtra2.startsWith("|")) {
                    String[] split = stringExtra2.split("\\|");
                    if (!"ID".equals(split[1])) {
                        String str4 = "Unexpected structured response ";
                        String valueOf3 = String.valueOf(stringExtra2);
                        Log.w("InstanceID", valueOf3.length() != 0 ? str4.concat(valueOf3) : new String(str4));
                    }
                    if (split.length > 2) {
                        String str5 = split[2];
                        str = split[3];
                        if (str.startsWith(":")) {
                            str = str.substring(1);
                        }
                        str3 = str5;
                    } else {
                        str = "UNKNOWN";
                    }
                    stringExtra2 = str;
                    intent.putExtra("error", stringExtra2);
                }
                if (str3 == null) {
                    synchronized (getClass()) {
                        for (String str6 : this.zzcu.keySet()) {
                            Object obj = this.zzcu.get(str6);
                            this.zzcu.put(str6, stringExtra2);
                            zzd(obj, (Object) stringExtra2);
                        }
                    }
                    return;
                }
                zzd(str3, (Object) stringExtra2);
                return;
            }
            Matcher matcher = Pattern.compile("\\|ID\\|([^|]+)\\|:?+(.*)").matcher(stringExtra);
            if (!matcher.matches()) {
                if (Log.isLoggable("InstanceID", 3)) {
                    String str7 = "Unexpected response string: ";
                    String valueOf4 = String.valueOf(stringExtra);
                    Log.d("InstanceID", valueOf4.length() != 0 ? str7.concat(valueOf4) : new String(str7));
                }
                return;
            }
            String group = matcher.group(1);
            String group2 = matcher.group(2);
            Bundle extras = intent.getExtras();
            extras.putString("registration_id", group2);
            zzd(group, (Object) extras);
            return;
        }
        if (Log.isLoggable("InstanceID", 3)) {
            String str8 = "Unexpected response ";
            String valueOf5 = String.valueOf(intent.getAction());
            Log.d("InstanceID", valueOf5.length() != 0 ? str8.concat(valueOf5) : new String(str8));
        }
    }
}
