package com.startapp.android.publish.adsCommon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.util.Pair;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.metaData.C5116d;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.C5187f;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.startapp.android.publish.adsCommon.l */
/* compiled from: StartAppSDK */
public class C5053l {

    /* renamed from: a */
    private static List<PackageInfo> f3312a = null;

    /* renamed from: b */
    private static List<PackageInfo> f3313b = null;

    /* renamed from: c */
    private static long f3314c = 0;

    /* renamed from: d */
    private static Pair<C5058a, String> f3315d = null;

    /* renamed from: e */
    private static Pair<C5058a, String> f3316e = null;

    /* renamed from: f */
    private static boolean f3317f = true;

    /* renamed from: g */
    private static boolean f3318g = false;

    /* renamed from: h */
    private static C5058a f3319h = C5058a.UNDEFINED;

    /* renamed from: com.startapp.android.publish.adsCommon.l$a */
    /* compiled from: StartAppSDK */
    private enum C5058a {
        T1("token"),
        T2("token2"),
        UNDEFINED("");
        
        private final String text;

        private C5058a(String str) {
            this.text = str;
        }

        public String toString() {
            return this.text;
        }
    }

    /* renamed from: a */
    public static long m3356a() {
        return f3314c;
    }

    /* renamed from: a */
    public static void m3359a(final Context context) {
        m3367c(context);
        f3317f = true;
        f3318g = false;
        f3319h = C5058a.UNDEFINED;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        context.getApplicationContext().registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                C5053l.m3364b();
                C5053l.m3367c(context);
            }
        }, intentFilter);
        MetaData.getInstance().addMetaDataListener(new C5116d() {
            /* renamed from: b */
            public void mo61601b() {
            }

            /* renamed from: a */
            public void mo61600a() {
                C5053l.m3364b();
                C5053l.m3367c(context);
            }
        });
    }

    /* renamed from: b */
    public static void m3365b(Context context) {
        m3360a(context, MetaData.getInstance().getSimpleTokenConfig().mo62664b(context));
    }

    /* renamed from: c */
    public static void m3367c(final Context context) {
        String str = "SimpleToken";
        C5155g.m3807a(str, 3, "initSimpleTokenAsync entered");
        try {
            if ((f3315d == null || f3316e == null) && MetaData.getInstance().getSimpleTokenConfig().mo62664b(context)) {
                C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
                    public void run() {
                        C5053l.m3365b(context);
                    }
                });
            }
        } catch (Exception e) {
            C5155g.m3808a(str, 6, "initSimpleTokenAsync failed", e);
            C5017f.m3256a(context, C5015d.EXCEPTION, "initSimpleTokenAsync", e.getMessage(), "");
        }
    }

    /* renamed from: a */
    public static void m3360a(Context context, boolean z) {
        String str = "]";
        String str2 = "SimpleToken";
        C5155g.m3807a(str2, 3, "initSimpleToken entered");
        if ((f3315d == null || f3316e == null) && z) {
            try {
                m3373g(context);
                f3315d = new Pair<>(C5058a.T1, C5187f.m3932a(m3358a(f3312a)));
                f3316e = new Pair<>(C5058a.T2, C5187f.m3932a(m3358a(f3313b)));
                StringBuilder sb = new StringBuilder();
                sb.append("simpleToken : [");
                sb.append(f3315d);
                sb.append(str);
                C5155g.m3807a(str2, 3, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("simpleToken2 : [");
                sb2.append(f3316e);
                sb2.append(str);
                C5155g.m3807a(str2, 3, sb2.toString());
            } catch (Exception e) {
                C5155g.m3808a(str2, 6, "initSimpleToken failed", e);
                C5017f.m3256a(context, C5015d.EXCEPTION, "initSimpleToken", e.getMessage(), "");
            }
        }
    }

    /* renamed from: b */
    public static void m3364b() {
        f3315d = null;
        f3316e = null;
    }

    /* renamed from: d */
    public static Pair<String, String> m3370d(Context context) {
        return m3357a(context, MetaData.getInstance().getSimpleTokenConfig().mo62664b(context), MetaData.getInstance().isAlwaysSendToken(), MetaData.getInstance().isToken1Mandatory());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004d, code lost:
        if (com.startapp.android.publish.adsCommon.C5051k.m3339a(r4, "shared_prefs_simple_token", "").equals(r2.second) == false) goto L_0x004f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0032 A[Catch:{ Exception -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0033 A[Catch:{ Exception -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e A[Catch:{ Exception -> 0x0062 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x003f A[Catch:{ Exception -> 0x0062 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized android.util.Pair<java.lang.String, java.lang.String> m3357a(android.content.Context r4, boolean r5, boolean r6, boolean r7) {
        /*
            java.lang.Class<com.startapp.android.publish.adsCommon.l> r0 = com.startapp.android.publish.adsCommon.C5053l.class
            monitor-enter(r0)
            java.lang.String r1 = "SimpleToken"
            r2 = 3
            java.lang.String r3 = "getSimpleToken entered"
            com.startapp.common.p092a.C5155g.m3807a(r1, r2, r3)     // Catch:{ all -> 0x007c }
            android.util.Pair r1 = new android.util.Pair     // Catch:{ all -> 0x007c }
            com.startapp.android.publish.adsCommon.l$a r2 = com.startapp.android.publish.adsCommon.C5053l.C5058a.T1     // Catch:{ all -> 0x007c }
            java.lang.String r3 = ""
            r1.<init>(r2, r3)     // Catch:{ all -> 0x007c }
            if (r5 == 0) goto L_0x006b
            com.startapp.android.publish.adsCommon.l$a r5 = f3319h     // Catch:{ Exception -> 0x0062 }
            com.startapp.android.publish.adsCommon.l$a r2 = com.startapp.android.publish.adsCommon.C5053l.C5058a.UNDEFINED     // Catch:{ Exception -> 0x0062 }
            if (r5 != r2) goto L_0x0051
            boolean r5 = f3317f     // Catch:{ Exception -> 0x0062 }
            boolean r2 = f3318g     // Catch:{ Exception -> 0x0062 }
            if (r2 == 0) goto L_0x002c
            boolean r2 = f3317f     // Catch:{ Exception -> 0x0062 }
            if (r2 == 0) goto L_0x0027
            goto L_0x002c
        L_0x0027:
            android.util.Pair r2 = m3372f(r4)     // Catch:{ Exception -> 0x0062 }
            goto L_0x0030
        L_0x002c:
            android.util.Pair r2 = m3371e(r4)     // Catch:{ Exception -> 0x0062 }
        L_0x0030:
            if (r7 == 0) goto L_0x0033
            goto L_0x003a
        L_0x0033:
            boolean r5 = f3318g     // Catch:{ Exception -> 0x0062 }
            if (r5 != 0) goto L_0x0039
            r5 = 1
            goto L_0x003a
        L_0x0039:
            r5 = 0
        L_0x003a:
            f3318g = r5     // Catch:{ Exception -> 0x0062 }
            if (r6 == 0) goto L_0x003f
            goto L_0x004f
        L_0x003f:
            java.lang.String r5 = "shared_prefs_simple_token"
            java.lang.String r6 = ""
            java.lang.String r4 = com.startapp.android.publish.adsCommon.C5051k.m3339a(r4, r5, r6)     // Catch:{ Exception -> 0x0062 }
            java.lang.Object r5 = r2.second     // Catch:{ Exception -> 0x0062 }
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0062 }
            if (r4 != 0) goto L_0x006b
        L_0x004f:
            r1 = r2
            goto L_0x006b
        L_0x0051:
            com.startapp.android.publish.adsCommon.l$a r5 = f3319h     // Catch:{ Exception -> 0x0062 }
            com.startapp.android.publish.adsCommon.l$a r6 = com.startapp.android.publish.adsCommon.C5053l.C5058a.T1     // Catch:{ Exception -> 0x0062 }
            if (r5 != r6) goto L_0x005c
            android.util.Pair r4 = m3371e(r4)     // Catch:{ Exception -> 0x0062 }
            goto L_0x0060
        L_0x005c:
            android.util.Pair r4 = m3372f(r4)     // Catch:{ Exception -> 0x0062 }
        L_0x0060:
            r1 = r4
            goto L_0x006b
        L_0x0062:
            r4 = move-exception
            java.lang.String r5 = "SimpleToken"
            r6 = 6
            java.lang.String r7 = "failed to get simpleToken "
            com.startapp.common.p092a.C5155g.m3808a(r5, r6, r7, r4)     // Catch:{ all -> 0x007c }
        L_0x006b:
            android.util.Pair r4 = new android.util.Pair     // Catch:{ all -> 0x007c }
            java.lang.Object r5 = r1.first     // Catch:{ all -> 0x007c }
            com.startapp.android.publish.adsCommon.l$a r5 = (com.startapp.android.publish.adsCommon.C5053l.C5058a) r5     // Catch:{ all -> 0x007c }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x007c }
            java.lang.Object r6 = r1.second     // Catch:{ all -> 0x007c }
            r4.<init>(r5, r6)     // Catch:{ all -> 0x007c }
            monitor-exit(r0)
            return r4
        L_0x007c:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.C5053l.m3357a(android.content.Context, boolean, boolean, boolean):android.util.Pair");
    }

    /* renamed from: a */
    public static void m3362a(Pair<String, String> pair) {
        C5155g.m3807a("SimpleToken", 3, "errorSendingToken entered");
        f3319h = C5058a.valueOf((String) pair.first);
    }

    /* renamed from: c */
    public static Pair<String, String> m3366c() {
        Pair<C5058a, String> pair = f3315d;
        if (pair != null) {
            return new Pair<>(((C5058a) pair.first).toString(), f3315d.second);
        }
        return new Pair<>(C5058a.T1.toString(), "");
    }

    /* renamed from: d */
    public static Pair<String, String> m3369d() {
        Pair<C5058a, String> pair = f3316e;
        if (pair != null) {
            return new Pair<>(((C5058a) pair.first).toString(), f3316e.second);
        }
        return new Pair<>(C5058a.T2.toString(), "");
    }

    /* renamed from: e */
    private static Pair<C5058a, String> m3371e(Context context) {
        if (f3315d == null) {
            m3365b(context);
        }
        C5051k.m3346b(context, "shared_prefs_simple_token", (String) f3315d.second);
        f3317f = false;
        f3319h = C5058a.UNDEFINED;
        return new Pair<>(C5058a.T1, f3315d.second);
    }

    /* renamed from: f */
    private static Pair<C5058a, String> m3372f(Context context) {
        if (f3316e == null) {
            m3365b(context);
        }
        C5051k.m3346b(context, "shared_prefs_simple_token2", (String) f3316e.second);
        f3317f = false;
        f3319h = C5058a.UNDEFINED;
        return new Pair<>(C5058a.T2, f3316e.second);
    }

    /* renamed from: g */
    private static synchronized void m3373g(Context context) {
        synchronized (C5053l.class) {
            C5155g.m3807a("SimpleToken", 3, "getPackages entered");
            PackageManager packageManager = context.getPackageManager();
            Set installersList = MetaData.getInstance().getInstallersList();
            Set preInstalledPackages = MetaData.getInstance().getPreInstalledPackages();
            f3312a = new CopyOnWriteArrayList();
            f3313b = new CopyOnWriteArrayList();
            try {
                List<PackageInfo> a = C5146c.m3748a(packageManager);
                f3314c = VERSION.SDK_INT >= 9 ? Long.MAX_VALUE : 0;
                PackageInfo packageInfo = null;
                for (PackageInfo packageInfo2 : a) {
                    if (VERSION.SDK_INT >= 9 && f3314c > packageInfo2.firstInstallTime) {
                        f3314c = packageInfo2.firstInstallTime;
                    }
                    if (!C5146c.m3762a(packageInfo2)) {
                        f3312a.add(packageInfo2);
                        m3361a(packageInfo2, packageManager, installersList);
                    } else if (preInstalledPackages.contains(packageInfo2.packageName)) {
                        f3312a.add(packageInfo2);
                    } else if (packageInfo2.packageName.equals(Constants.f3532a)) {
                        packageInfo = packageInfo2;
                    }
                }
                f3312a = m3363b(f3312a);
                f3313b = m3363b(f3313b);
                if (packageInfo != null) {
                    f3312a.add(0, packageInfo);
                }
            } catch (Exception e) {
                C5155g.m3808a("SimpleToken", 6, "Could not complete getInstalledPackages", e);
            }
        }
    }

    /* renamed from: a */
    private static void m3361a(PackageInfo packageInfo, PackageManager packageManager, Set<String> set) {
        try {
            String installerPackageName = packageManager.getInstallerPackageName(packageInfo.packageName);
            if (set != null && set.contains(installerPackageName)) {
                f3313b.add(packageInfo);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("addToPackagesFromInstallers - can't add app to list ");
            sb.append(e.getMessage());
            C5155g.m3807a("SimpleToken", 6, sb.toString());
        }
    }

    /* renamed from: a */
    private static List<String> m3358a(List<PackageInfo> list) {
        C5155g.m3807a("SimpleToken", 3, "getPackagesNames entered");
        ArrayList arrayList = new ArrayList();
        for (PackageInfo packageInfo : list) {
            arrayList.add(packageInfo.packageName);
        }
        return arrayList;
    }

    /* renamed from: b */
    private static List<PackageInfo> m3363b(List<PackageInfo> list) {
        if (list.size() <= 100) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list);
        m3368c((List<PackageInfo>) arrayList);
        return arrayList.subList(0, 100);
    }

    /* renamed from: c */
    private static void m3368c(List<PackageInfo> list) {
        if (VERSION.SDK_INT >= 9) {
            Collections.sort(list, new Comparator<PackageInfo>() {
                /* renamed from: a */
                public int compare(PackageInfo packageInfo, PackageInfo packageInfo2) {
                    long j = packageInfo.firstInstallTime;
                    long j2 = packageInfo2.firstInstallTime;
                    if (j > j2) {
                        return -1;
                    }
                    return j == j2 ? 0 : 1;
                }
            });
        }
    }
}
