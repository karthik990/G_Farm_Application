package com.startapp.common.p093b;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ServiceInfo;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.startapp.android.publish.common.metaData.InfoEventService;
import com.startapp.android.publish.common.metaData.PeriodicJobService;
import com.startapp.common.p093b.C5174b.C5176a;
import com.startapp.common.p093b.p094a.C5169a;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;
import com.startapp.common.p093b.p094a.C5173c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.internal.StringUtil;

/* renamed from: com.startapp.common.b.a */
/* compiled from: StartAppSDK */
public class C5164a {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static volatile C5164a f3584a = null;

    /* renamed from: b */
    private static volatile C5173c f3585b = null;

    /* renamed from: c */
    private static volatile Integer f3586c = null;

    /* renamed from: d */
    private static volatile int f3587d = 60000;

    /* renamed from: j */
    private static final ExecutorService f3588j = Executors.newSingleThreadExecutor();

    /* renamed from: k */
    private static final ScheduledExecutorService f3589k = Executors.newScheduledThreadPool(1);
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Context f3590e;

    /* renamed from: f */
    private List<C5169a> f3591f = Collections.synchronizedList(new ArrayList());
    /* access modifiers changed from: private */

    /* renamed from: g */
    public Map<Integer, Integer> f3592g = new ConcurrentHashMap();

    /* renamed from: h */
    private AtomicInteger f3593h = new AtomicInteger(0);

    /* renamed from: i */
    private boolean f3594i;

    /* renamed from: b */
    private static int m3853b(int i) {
        return i & Integer.MAX_VALUE;
    }

    /* renamed from: b */
    private static int m3854b(int i, boolean z) {
        return z ? i | Integer.MIN_VALUE : i;
    }

    private C5164a(Context context) {
        this.f3590e = context.getApplicationContext();
        this.f3594i = m3863d(context);
    }

    /* renamed from: a */
    public static C5164a m3838a(Context context) {
        if (f3584a == null) {
            synchronized (C5164a.class) {
                if (f3584a == null) {
                    if (context.getApplicationContext() != null) {
                        context = context.getApplicationContext();
                    }
                    f3584a = new C5164a(context);
                    try {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("RunnerManager", 0);
                        String str = null;
                        String string = sharedPreferences.getString("RegisteredClassesNames", null);
                        if (string != null) {
                            String[] split = string.split(",");
                            StringBuilder sb = new StringBuilder(string.length());
                            for (String str2 : split) {
                                String str3 = "RunnerManager";
                                try {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append("create CLS: ");
                                    sb2.append(str2);
                                    m3842a(3, str3, sb2.toString());
                                    Class cls = Class.forName(str2);
                                    if (C5169a.class.isAssignableFrom(cls)) {
                                        f3584a.f3591f.add((C5169a) cls.newInstance());
                                        if (sb.length() > 0) {
                                            sb.append(StringUtil.COMMA);
                                        }
                                        sb.append(str2);
                                    }
                                } catch (ClassNotFoundException unused) {
                                } catch (Throwable th) {
                                    String str4 = "RunnerManager";
                                    StringBuilder sb3 = new StringBuilder();
                                    sb3.append("create :");
                                    sb3.append(str2);
                                    m3843a(6, str4, sb3.toString(), th);
                                }
                            }
                            if (!sb.toString().equals(string)) {
                                Editor edit = sharedPreferences.edit();
                                String str5 = "RegisteredClassesNames";
                                if (sb.length() > 0) {
                                    str = sb.toString();
                                }
                                edit.putString(str5, str).commit();
                            }
                        }
                    } catch (Exception e) {
                        m3843a(6, "RunnerManager", "create", (Throwable) e);
                    }
                }
            }
        }
        return f3584a;
    }

    /* renamed from: a */
    public static void m3846a(C5169a aVar) {
        f3584a.f3591f.add(aVar);
        String name = aVar.getClass().getName();
        SharedPreferences sharedPreferences = f3584a.f3590e.getSharedPreferences("RunnerManager", 0);
        String str = "RegisteredClassesNames";
        String string = sharedPreferences.getString(str, null);
        if (string == null) {
            sharedPreferences.edit().putString(str, name).commit();
        } else if (!string.contains(name)) {
            Editor edit = sharedPreferences.edit();
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(",");
            sb.append(name);
            edit.putString(str, sb.toString()).commit();
        }
    }

    /* renamed from: a */
    public static void m3847a(C5173c cVar) {
        f3585b = cVar;
    }

    /* renamed from: a */
    public static boolean m3851a(C5174b bVar) {
        String str = "RunnerManager";
        try {
            int b = m3854b(bVar.mo62885a(), bVar.mo62889e());
            StringBuilder sb = new StringBuilder();
            sb.append("schedule ");
            sb.append(b);
            sb.append(" ");
            sb.append(bVar);
            m3842a(3, str, sb.toString());
            if (!m3861c()) {
                return m3862c(b, bVar);
            }
            if (m3857b()) {
                return m3848a(b, bVar);
            }
            return m3858b(b, bVar);
        } catch (Exception e) {
            m3843a(6, str, "schedule error", (Throwable) e);
            return false;
        }
    }

    /* renamed from: a */
    private static boolean m3848a(int i, C5174b bVar) {
        JobScheduler c = m3860c(f3584a.f3590e);
        if (c == null) {
            return false;
        }
        PersistableBundle persistableBundle = new PersistableBundle();
        Map b = bVar.mo62886b();
        for (String str : b.keySet()) {
            persistableBundle.putString(str, (String) b.get(str));
        }
        persistableBundle.putInt("__RUNNER_RECURRING_ID__", bVar.mo62889e() ? 1 : 0);
        persistableBundle.putLong("__RUNNER_TRIGGER_ID__", bVar.mo62887c());
        Builder builder = new Builder(i, new ComponentName(f3584a.f3590e, PeriodicJobService.class));
        builder.setExtras(persistableBundle);
        if (bVar.mo62889e()) {
            builder.setPeriodic(bVar.mo62887c());
        } else {
            builder.setMinimumLatency(bVar.mo62887c()).setOverrideDeadline(bVar.mo62887c() + ((long) f3587d));
        }
        builder.setRequiredNetworkType(bVar.mo62890f() ? 1 : 0);
        int schedule = c.schedule(builder.build());
        StringBuilder sb = new StringBuilder();
        sb.append("jobScheduler.schedule ");
        sb.append(schedule);
        m3842a(3, "RunnerManager", sb.toString());
        boolean z = true;
        if (schedule != 1) {
            z = false;
        }
        return z;
    }

    /* renamed from: b */
    private static boolean m3858b(int i, C5174b bVar) {
        AlarmManager b = m3855b(f3584a.f3590e);
        if (b == null) {
            return false;
        }
        Intent intent = new Intent(f3584a.f3590e, InfoEventService.class);
        Map b2 = bVar.mo62886b();
        for (String str : b2.keySet()) {
            intent.putExtra(str, (String) b2.get(str));
        }
        intent.putExtra("__RUNNER_TASK_ID__", i);
        intent.putExtra("__RUNNER_RECURRING_ID__", bVar.mo62889e());
        intent.putExtra("__RUNNER_TRIGGER_ID__", bVar.mo62887c());
        PendingIntent service = PendingIntent.getService(f3584a.f3590e, i, intent, 134217728);
        b.cancel(service);
        if (bVar.mo62889e()) {
            b.setRepeating(0, System.currentTimeMillis() + bVar.mo62888d(), bVar.mo62887c(), service);
        } else {
            b.set(3, SystemClock.elapsedRealtime() + bVar.mo62887c(), service);
        }
        return true;
    }

    /* renamed from: c */
    private static boolean m3862c(final int i, final C5174b bVar) {
        final int incrementAndGet = f3584a.f3593h.incrementAndGet();
        C51651 r2 = new Runnable() {
            public void run() {
                Integer num = (Integer) C5164a.f3584a.f3592g.get(Integer.valueOf(i));
                if (num != null && num.intValue() == incrementAndGet) {
                    if (!bVar.mo62889e()) {
                        C5164a.f3584a.f3592g.remove(Integer.valueOf(i));
                    }
                    C5164a.m3859b(bVar, (C5172b) new C5172b() {
                        /* renamed from: a */
                        public void mo62538a(C5171a aVar) {
                        }
                    });
                }
            }
        };
        if (bVar.mo62889e()) {
            f3589k.scheduleAtFixedRate(r2, bVar.mo62888d(), bVar.mo62888d(), TimeUnit.MILLISECONDS);
        } else {
            f3589k.schedule(r2, bVar.mo62887c(), TimeUnit.MILLISECONDS);
        }
        f3584a.f3592g.put(Integer.valueOf(i), Integer.valueOf(incrementAndGet));
        return true;
    }

    /* renamed from: a */
    public static void m3844a(int i, boolean z) {
        StringBuilder sb = new StringBuilder();
        String str = "cancelAlarm ";
        sb.append(str);
        sb.append(i);
        String str2 = "RunnerManager";
        m3842a(3, str2, sb.toString());
        try {
            int b = m3854b(i, z);
            if (!f3584a.f3594i) {
                f3584a.f3592g.remove(Integer.valueOf(b));
            } else if (m3857b()) {
                JobScheduler c = m3860c(f3584a.f3590e);
                if (c != null) {
                    c.cancel(b);
                }
            } else {
                AlarmManager b2 = m3855b(f3584a.f3590e);
                if (b2 != null) {
                    m3845a(f3584a.f3590e, new Intent(f3584a.f3590e, InfoEventService.class), b2, b);
                }
            }
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(i);
            m3843a(6, str2, sb2.toString(), (Throwable) e);
        }
    }

    /* renamed from: a */
    public static void m3842a(int i, String str, String str2) {
        m3843a(i, str, str2, (Throwable) null);
    }

    /* renamed from: a */
    public static void m3843a(int i, String str, String str2, Throwable th) {
        if (f3585b != null) {
            f3585b.mo62025a(i, str, str2, th);
        }
    }

    /* renamed from: a */
    public static boolean m3850a(Intent intent, C5172b bVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("runJob ");
        sb.append(intent != 0 ? intent : "NULL");
        m3842a(3, "RunnerManager", sb.toString());
        return intent != 0 && m3859b(m3840a(intent), bVar);
    }

    /* renamed from: a */
    public static boolean m3849a(JobParameters jobParameters, C5172b bVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("runJob ");
        sb.append(jobParameters);
        m3842a(3, "RunnerManager", sb.toString());
        return m3859b(m3839a(jobParameters), bVar);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static boolean m3859b(final C5174b bVar, final C5172b bVar2) {
        StringBuilder sb = new StringBuilder();
        sb.append("RunnerJob ");
        sb.append(bVar.mo62885a());
        sb.append(" ");
        sb.append(m3853b(bVar.mo62885a()));
        String str = "RunnerManager";
        m3842a(3, str, sb.toString());
        final int b = m3853b(bVar.mo62885a());
        final C5170b a = m3836a(b);
        if (a != null) {
            f3588j.execute(new Runnable() {
                public void run() {
                    a.mo44557a(C5164a.f3584a.f3590e, b, bVar.mo62886b(), new C5172b() {
                        /* renamed from: a */
                        public void mo62538a(C5171a aVar) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("job.execute ");
                            sb.append(bVar.mo62885a());
                            sb.append(" ");
                            sb.append(aVar);
                            C5164a.m3842a(3, "RunnerManager", sb.toString());
                            if (aVar == C5171a.RESCHEDULE && !bVar.mo62889e()) {
                                C5164a.m3851a(bVar);
                            }
                            bVar2.mo62538a(aVar);
                        }
                    });
                }
            });
            return true;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("runJob: failed to get job for ID ");
        sb2.append(bVar.mo62885a());
        m3842a(5, str, sb2.toString());
        bVar2.mo62538a(C5171a.FAILED);
        return false;
    }

    /* renamed from: a */
    private static C5170b m3836a(int i) {
        C5170b bVar = null;
        for (C5169a create : f3584a.f3591f) {
            bVar = create.create(i);
            if (bVar != null) {
                break;
            }
        }
        return bVar;
    }

    /* renamed from: a */
    private static void m3845a(Context context, Intent intent, AlarmManager alarmManager, int i) {
        PendingIntent service = PendingIntent.getService(context, i, intent, 134217728);
        if (PendingIntent.getService(context, 0, intent, 268435456) != null) {
            alarmManager.cancel(service);
            service.cancel();
        }
    }

    /* renamed from: a */
    private static C5174b m3840a(Intent intent) {
        HashMap hashMap;
        int intExtra = intent.getIntExtra("__RUNNER_TASK_ID__", -1);
        boolean booleanExtra = intent.getBooleanExtra("__RUNNER_RECURRING_ID__", false);
        long longExtra = intent.getLongExtra("__RUNNER_TRIGGER_ID__", 0);
        if (intent.getExtras() != null) {
            hashMap = new HashMap(intent.getExtras().keySet().size());
            for (String str : intent.getExtras().keySet()) {
                Object obj = intent.getExtras().get(str);
                if (obj instanceof String) {
                    hashMap.put(str, (String) obj);
                }
            }
        } else {
            hashMap = null;
        }
        return new C5176a(intExtra).mo62894a((Map<String, String>) hashMap).mo62895a(booleanExtra).mo62892a(longExtra).mo62896a();
    }

    /* renamed from: a */
    private static C5174b m3839a(JobParameters jobParameters) {
        PersistableBundle extras = jobParameters.getExtras();
        boolean z = true;
        if (extras.getInt("__RUNNER_RECURRING_ID__") != 1) {
            z = false;
        }
        long j = extras.getLong("__RUNNER_TRIGGER_ID__", 0);
        HashMap hashMap = new HashMap(extras.keySet().size());
        for (String str : extras.keySet()) {
            Object obj = extras.get(str);
            if (obj instanceof String) {
                hashMap.put(str, (String) obj);
            }
        }
        return new C5176a(jobParameters.getJobId()).mo62894a((Map<String, String>) hashMap).mo62895a(z).mo62892a(j).mo62896a();
    }

    /* renamed from: b */
    private static AlarmManager m3855b(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        if (alarmManager == null) {
            m3842a(6, "RunnerManager", "failed to get AlarmManager");
        }
        return alarmManager;
    }

    /* renamed from: c */
    private static JobScheduler m3860c(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler == null) {
            m3842a(6, "RunnerManager", "failed to get JobScheduler");
        }
        return jobScheduler;
    }

    /* renamed from: b */
    private static boolean m3857b() {
        int i = VERSION.SDK_INT;
        if (f3586c != null) {
            i = f3586c.intValue();
        }
        return i >= 21;
    }

    /* renamed from: d */
    private boolean m3863d(Context context) {
        try {
            for (ServiceInfo serviceInfo : context.getPackageManager().getPackageInfo(context.getPackageName(), 4).services) {
                if (serviceInfo.name.equals(InfoEventService.class.getName())) {
                    return true;
                }
            }
        } catch (Throwable th) {
            m3843a(6, "RunnerManager", "servicesDefined", th);
        }
        return false;
    }

    /* renamed from: c */
    private static boolean m3861c() {
        return f3584a.f3594i;
    }
}
