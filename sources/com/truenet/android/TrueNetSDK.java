package com.truenet.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import com.fasterxml.jackson.core.JsonPointer;
import com.mobiroller.constants.ChatConstants;
import com.startapp.common.p042c.C5180b;
import com.startapp.common.p093b.C5164a;
import com.startapp.common.p093b.C5174b.C5176a;
import com.startapp.common.p093b.p094a.C5169a;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;
import com.truenet.android.C3030b.C3032b;
import com.truenet.android.p096a.C5194a;
import com.truenet.android.p096a.C5200g;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import p000a.p001a.C0075j;
import p000a.p001a.p002a.C0007g;
import p000a.p001a.p003b.p004a.C0022a;
import p000a.p001a.p003b.p004a.C0023b;
import p000a.p001a.p003b.p005b.C0032h;
import p000a.p001a.p003b.p005b.C0033i;
import p000a.p001a.p003b.p005b.C0037m.C0038a;
import p000a.p001a.p003b.p005b.C0039n;
import p000a.p001a.p006c.C0042a;
import p000a.p001a.p007d.C0049c;

/* compiled from: StartAppSDK */
public final class TrueNetSDK implements C5169a {
    private static final String BASE_INIT_URL = new String(new char[]{'h', 't', 't', 'p', 's', ':', JsonPointer.SEPARATOR, JsonPointer.SEPARATOR, 'v', 'a', 'l', 'i', 'd', 'a', 't', 'i', 'o', 'n', '-', 'e', 'n', 'g', 'i', 'n', 'e', '.', 't', 'r', 'u', 'e', 'n', 'e', 't', '.', 'a', 'i'});
    private static final String BASE_RESULT_URL = new String(new char[]{'h', 't', 't', 'p', 's', ':', JsonPointer.SEPARATOR, JsonPointer.SEPARATOR, 'r', 'e', 's', 'u', 'l', 't', '-', 'a', 'p', 'i', '.', 't', 'r', 'u', 'e', 'n', 'e', 't', '.', 'a', 'i'});
    public static final C3018a Companion = new C3018a(null);
    private static final String INIT_URL;
    private static final int JOB_ID = 97764;
    public static final String JOB_TAG = "TruenetCheckLinksJob";
    private static final String PREFS_ENABLED = "PrefsEnabled";
    private static final String PREFS_PUBLISHER_ID = "PrefsPublisherId";
    public static final String PREFS_TAG = "TruenetJobKey";
    private static final String RESULT_URL;
    /* access modifiers changed from: private */
    public static final URL initUrl = new URL(INIT_URL);
    /* access modifiers changed from: private */
    public static int intervalPosition;
    /* access modifiers changed from: private */
    public static final List<Long> intervals = C0007g.m6a((T[]) new Long[]{Long.valueOf(15), Long.valueOf(30), Long.valueOf(60), Long.valueOf(120), Long.valueOf(240), Long.valueOf(480)});
    /* access modifiers changed from: private */
    public static long requestDelay;
    /* access modifiers changed from: private */
    public static final URL resultUrl = new URL(RESULT_URL);
    /* access modifiers changed from: private */
    public static ThreadFactory threadFactory = C3025b.f1909a;
    /* access modifiers changed from: private */
    public static boolean wasInitCalled;

    /* renamed from: com.truenet.android.TrueNetSDK$a */
    /* compiled from: StartAppSDK */
    public static final class C3018a {

        /* renamed from: com.truenet.android.TrueNetSDK$a$a */
        /* compiled from: StartAppSDK */
        static final class C3019a extends C0033i implements C0022a<C0075j> {
            final /* synthetic */ ConcurrentLinkedQueue $bulkQueue;
            final /* synthetic */ Context $context;
            final /* synthetic */ C0022a $finish;
            final /* synthetic */ LinksData $links;

            C3019a(LinksData linksData, ConcurrentLinkedQueue concurrentLinkedQueue, Context context, C0022a aVar) {
                this.$links = linksData;
                this.$bulkQueue = concurrentLinkedQueue;
                this.$context = context;
                this.$finish = aVar;
                super(0);
            }

            /* renamed from: a */
            public /* synthetic */ Object mo45a() {
                mo44549b();
                return C0075j.f19a;
            }

            /* renamed from: b */
            public final void mo44549b() {
                if (this.$links.getBulkResponse()) {
                    String a = C5180b.m3909a(new ValidationResults(C0007g.m10a((Iterable<? extends T>) this.$bulkQueue)));
                    URL access$getResultUrl$cp = TrueNetSDK.resultUrl;
                    C0032h.m41a((Object) a, "json");
                    C5200g.m3948b(access$getResultUrl$cp, a, this.$context);
                }
                TrueNetSDK.Companion.m1797a(this.$context, this.$links.getSleep());
                if (this.$links.getSleep() != 0) {
                    this.$finish.mo45a();
                }
            }
        }

        /* renamed from: com.truenet.android.TrueNetSDK$a$b */
        /* compiled from: StartAppSDK */
        static final class C3020b extends C0033i implements C0023b<C3030b, Integer, C0075j> {
            final /* synthetic */ ConcurrentLinkedQueue $bulkQueue;
            final /* synthetic */ Context $context;
            final /* synthetic */ LinksData $links;

            C3020b(LinksData linksData, Context context, ConcurrentLinkedQueue concurrentLinkedQueue) {
                this.$links = linksData;
                this.$context = context;
                this.$bulkQueue = concurrentLinkedQueue;
                super(2);
            }

            /* renamed from: a */
            public /* synthetic */ Object mo46a(Object obj, Object obj2) {
                mo44550a((C3030b) obj, ((Number) obj2).intValue());
                return C0075j.f19a;
            }

            /* renamed from: a */
            public final void mo44550a(C3030b bVar, int i) {
                C0032h.m44b(bVar, ChatConstants.ARG_USER_INFO);
                Iterable<C3032b> d = bVar.mo44591d();
                Collection arrayList = new ArrayList(C0007g.m8a(d, 10));
                for (C3032b bVar2 : d) {
                    String a = bVar2.mo44596a();
                    long b = bVar2.mo44597b();
                    int c = bVar2.mo44598c();
                    List d2 = bVar2.mo44599d();
                    if (d2 == null) {
                        d2 = C0007g.m5a();
                    }
                    RedirectsResult redirectsResult = new RedirectsResult(a, b, c, d2);
                    arrayList.add(redirectsResult);
                }
                List list = (List) arrayList;
                Link link = (Link) this.$links.getValidation().get(i);
                String instanceId = link.getInstanceId();
                int b2 = bVar.mo44589b();
                long c2 = bVar.mo44590c();
                String e = bVar.mo44592e();
                String f = bVar.mo44593f();
                String str = "";
                String htmlStorage = (f == null || !C5200g.m3947a(new URL(link.getHtmlStorage()), f, this.$context)) ? str : link.getHtmlStorage();
                Bitmap a2 = bVar.mo44588a();
                ValidationResult validationResult = new ValidationResult(instanceId, b2, c2, list, e, htmlStorage, (a2 == null || !C5194a.m3936a(a2, link.getImageStorage())) ? str : link.getImageStorage(), TrueNetSDK.Companion.m1808c(this.$context), link.getMetaData());
                if (this.$links.getBulkResponse()) {
                    this.$bulkQueue.add(validationResult);
                    return;
                }
                String a3 = C5180b.m3909a(new ValidationResults(C0007g.m3a(validationResult)));
                URL access$getResultUrl$cp = TrueNetSDK.resultUrl;
                C0032h.m41a((Object) a3, "json");
                C5200g.m3948b(access$getResultUrl$cp, a3, this.$context);
            }
        }

        /* renamed from: com.truenet.android.TrueNetSDK$a$c */
        /* compiled from: StartAppSDK */
        static final class C3021c implements Runnable {

            /* renamed from: a */
            final /* synthetic */ Context f1904a;

            /* renamed from: b */
            final /* synthetic */ C0022a f1905b;

            C3021c(Context context, C0022a aVar) {
                this.f1904a = context;
                this.f1905b = aVar;
            }

            public final void run() {
                boolean z = TrueNetSDK.requestDelay != 0;
                Log.d("JobManager", "sending initial request");
                String b = C5200g.m3948b(TrueNetSDK.initUrl, TrueNetSDK.Companion.m1806b(this.f1904a), this.f1904a);
                if (b != null) {
                    TrueNetSDK.Companion.m1799a(this.f1904a, b, this.f1905b);
                    return;
                }
                TrueNetSDK.Companion.m1795a(z ? TrueNetSDK.intervalPosition : TrueNetSDK.intervalPosition + 1, 0);
                this.f1905b.mo45a();
            }
        }

        /* renamed from: com.truenet.android.TrueNetSDK$a$d */
        /* compiled from: StartAppSDK */
        static final class C3022d implements Runnable {

            /* renamed from: a */
            final /* synthetic */ long f1906a;

            /* renamed from: b */
            final /* synthetic */ Context f1907b;

            C3022d(long j, Context context) {
                this.f1906a = j;
                this.f1907b = context;
            }

            public final void run() {
                final C0038a aVar = new C0038a();
                aVar.element = (String) null;
                if (this.f1906a != 0 || new C0022a<String>(this) {
                    final /* synthetic */ C3022d this$0;

                    {
                        this.this$0 = r1;
                    }

                    /* renamed from: b */
                    public final String mo45a() {
                        aVar.element = C5200g.m3948b(TrueNetSDK.initUrl, TrueNetSDK.Companion.m1806b(this.this$0.f1907b), this.this$0.f1907b);
                        return (String) aVar.element;
                    }
                }.mo45a() == null) {
                    TrueNetSDK.Companion.m1795a(0, this.f1906a);
                    return;
                }
                C3018a aVar2 = TrueNetSDK.Companion;
                Context context = this.f1907b;
                String str = (String) aVar.element;
                if (str == null) {
                    C0032h.m40a();
                }
                aVar2.m1799a(context, str, (C0022a<C0075j>) C30242.f1908a);
            }
        }

        private C3018a() {
        }

        public /* synthetic */ C3018a(C0029e eVar) {
            this();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public final void m1805a(Thread thread, Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Something went wrong in thread: ");
            sb.append(String.valueOf(thread.getId()));
            Log.e("TrueNetSDK", sb.toString(), th);
        }

        /* renamed from: a */
        public final void mo44547a(Context context, String str) {
            C0032h.m44b(context, "context");
            C0032h.m44b(str, "publisherID");
            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences(TrueNetSDK.PREFS_TAG, 0);
                sharedPreferences.edit().putString(TrueNetSDK.PREFS_PUBLISHER_ID, str).apply();
                if (sharedPreferences.getBoolean(TrueNetSDK.PREFS_ENABLED, true) && !TrueNetSDK.wasInitCalled) {
                    m1796a(context);
                    TrueNetSDK.wasInitCalled = true;
                }
            } catch (Throwable th) {
                C3018a aVar = this;
                Thread currentThread = Thread.currentThread();
                C0032h.m41a((Object) currentThread, "Thread.currentThread()");
                aVar.m1805a(currentThread, th);
            }
        }

        /* renamed from: a */
        public final void mo44548a(Context context, boolean z) {
            C0032h.m44b(context, "context");
            try {
                context.getSharedPreferences(TrueNetSDK.PREFS_TAG, 0).edit().putBoolean(TrueNetSDK.PREFS_ENABLED, z).apply();
                if (z && !TrueNetSDK.wasInitCalled) {
                    m1796a(context);
                    TrueNetSDK.wasInitCalled = true;
                }
            } catch (Throwable th) {
                C3018a aVar = this;
                Thread currentThread = Thread.currentThread();
                C0032h.m41a((Object) currentThread, "Thread.currentThread()");
                aVar.m1805a(currentThread, th);
            }
        }

        /* renamed from: a */
        private final void m1796a(Context context) {
            C5164a.m3838a(context);
            C5164a.m3846a((C5169a) new TrueNetSDK());
            m1802a(this, context, 0, 2, null);
        }

        /* renamed from: a */
        static /* bridge */ /* synthetic */ void m1802a(C3018a aVar, Context context, long j, int i, Object obj) {
            if ((i & 2) != 0) {
                j = 0;
            }
            aVar.m1797a(context, j);
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public final void m1797a(Context context, long j) {
            Executors.newSingleThreadExecutor(TrueNetSDK.threadFactory).execute(new C3022d(j, context));
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public final void m1795a(int i, long j) {
            TrueNetSDK.requestDelay = j;
            TrueNetSDK.intervalPosition = C0042a.m58a(i, TrueNetSDK.intervals.size() - 1);
            if (!(j != 0)) {
                j = TimeUnit.MINUTES.toMillis(((Number) TrueNetSDK.intervals.get(TrueNetSDK.intervalPosition)).longValue());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("scheduled millis: ");
            sb.append(String.valueOf(j));
            Log.d("JobManager", sb.toString());
            C5164a.m3844a((int) TrueNetSDK.JOB_ID, false);
            C5164a.m3851a(new C5176a(TrueNetSDK.JOB_ID).mo62892a(j).mo62895a(false).mo62893a(TrueNetSDK.JOB_TAG, TrueNetSDK.PREFS_TAG).mo62897b(true).mo62896a());
        }

        /* renamed from: a */
        public final void mo44546a(Context context, C0022a<C0075j> aVar) {
            C0032h.m44b(context, "context");
            C0032h.m44b(aVar, "finish");
            try {
                if (!context.getSharedPreferences(TrueNetSDK.PREFS_TAG, 0).getBoolean(TrueNetSDK.PREFS_ENABLED, true)) {
                    C5164a.m3844a((int) TrueNetSDK.JOB_ID, false);
                    aVar.mo45a();
                    return;
                }
                Executors.newSingleThreadExecutor(TrueNetSDK.threadFactory).execute(new C3021c(context, aVar));
            } catch (Throwable th) {
                C3018a aVar2 = this;
                Thread currentThread = Thread.currentThread();
                C0032h.m41a((Object) currentThread, "Thread.currentThread()");
                aVar2.m1805a(currentThread, th);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public final String m1806b(Context context) {
            DeviceInfo a = new C3029a(context, null, 2, null).mo44587a();
            a.setPublisherId(m1808c(context));
            String a2 = C5180b.m3909a(a);
            C0032h.m41a((Object) a2, "JSONParser.toJson(info)");
            return a2;
        }

        /* access modifiers changed from: private */
        /* renamed from: c */
        public final String m1808c(Context context) {
            String string = context.getSharedPreferences(TrueNetSDK.PREFS_TAG, 0).getString(TrueNetSDK.PREFS_PUBLISHER_ID, "Undefined");
            C0032h.m41a((Object) string, "prefs.getString(PREFS_PUBLISHER_ID, \"Undefined\")");
            return string;
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public final void m1799a(Context context, String str, C0022a<C0075j> aVar) {
            TrueNetSDK.intervalPosition = 0;
            TrueNetSDK.requestDelay = 0;
            LinksData linksData = (LinksData) C5180b.m3908a(str, LinksData.class);
            if (linksData.getValidation().size() != 0) {
                C3018a aVar2 = this;
                C0032h.m41a((Object) linksData, "response");
                aVar2.m1798a(context, linksData, aVar);
                return;
            }
            m1797a(context, linksData.getSleep());
            if (linksData.getSleep() != 0) {
                aVar.mo45a();
            }
        }

        /* renamed from: a */
        private final void m1798a(Context context, LinksData linksData, C0022a<C0075j> aVar) {
            Iterable<Link> validation = linksData.getValidation();
            Collection arrayList = new ArrayList(C0007g.m8a(validation, 10));
            for (Link validationUrl : validation) {
                arrayList.add(validationUrl.getValidationUrl());
            }
            C3039c cVar = new C3039c(context, (List) arrayList, TrueNetSDK.threadFactory, linksData.getMaxRedirectTime(), linksData.getNumOfRedirect(), linksData.getValidateParallel());
            ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
            cVar.mo44613a((C0022a<C0075j>) new C3019a<C0075j>(linksData, concurrentLinkedQueue, context, aVar));
            cVar.mo44614a((C0023b<? super C3030b, ? super Integer, C0075j>) new C3020b<Object,Object,C0075j>(linksData, context, concurrentLinkedQueue));
        }
    }

    /* renamed from: com.truenet.android.TrueNetSDK$b */
    /* compiled from: StartAppSDK */
    static final class C3025b implements ThreadFactory {

        /* renamed from: a */
        public static final C3025b f1909a = new C3025b();

        C3025b() {
        }

        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler(new C3043d(new C0023b<Thread, Throwable, C0075j>(TrueNetSDK.Companion) {
                /* renamed from: a */
                public final C0049c mo47a() {
                    return C0039n.m49a(C3018a.class);
                }

                /* renamed from: b */
                public final String mo48b() {
                    return "uncaughtExceptionHandler";
                }

                /* renamed from: c */
                public final String mo49c() {
                    return "uncaughtExceptionHandler(Ljava/lang/Thread;Ljava/lang/Throwable;)V";
                }

                /* renamed from: a */
                public /* bridge */ /* synthetic */ Object mo46a(Object obj, Object obj2) {
                    mo44556a((Thread) obj, (Throwable) obj2);
                    return C0075j.f19a;
                }

                /* renamed from: a */
                public final void mo44556a(Thread thread, Throwable th) {
                    C0032h.m44b(thread, "p1");
                    C0032h.m44b(th, "p2");
                    ((C3018a) this.receiver).m1805a(thread, th);
                }
            }));
            return thread;
        }
    }

    /* renamed from: com.truenet.android.TrueNetSDK$c */
    /* compiled from: StartAppSDK */
    static final class C3027c implements C5170b {

        /* renamed from: a */
        final /* synthetic */ TrueNetSDK f1910a;

        /* renamed from: com.truenet.android.TrueNetSDK$c$a */
        /* compiled from: StartAppSDK */
        static final class C3028a extends C0033i implements C0022a<C0075j> {
            final /* synthetic */ Context $context$inlined;
            final /* synthetic */ Map $extras$inlined;
            final /* synthetic */ int $jobId$inlined;
            final /* synthetic */ C5172b $runnerListener$inlined;

            C3028a(Map map, Context context, int i, C5172b bVar) {
                this.$extras$inlined = map;
                this.$context$inlined = context;
                this.$jobId$inlined = i;
                this.$runnerListener$inlined = bVar;
                super(0);
            }

            /* renamed from: a */
            public /* synthetic */ Object mo45a() {
                mo44558b();
                return C0075j.f19a;
            }

            /* renamed from: b */
            public final void mo44558b() {
                StringBuilder sb = new StringBuilder();
                sb.append("finished ");
                sb.append(String.valueOf(this.$jobId$inlined));
                Log.d("JobManager", sb.toString());
                this.$runnerListener$inlined.mo62538a(C5171a.SUCCESS);
            }
        }

        C3027c(TrueNetSDK trueNetSDK) {
            this.f1910a = trueNetSDK;
        }

        /* renamed from: a */
        public final void mo44557a(Context context, int i, Map<String, String> map, C5172b bVar) {
            synchronized (this.f1910a) {
                if (C0032h.m43a((Object) (String) map.get(TrueNetSDK.JOB_TAG), (Object) TrueNetSDK.PREFS_TAG)) {
                    C3018a aVar = TrueNetSDK.Companion;
                    C0032h.m41a((Object) context, "context");
                    aVar.mo44546a(context, (C0022a<C0075j>) new C3028a<C0075j>(map, context, i, bVar));
                }
                C0075j jVar = C0075j.f19a;
            }
        }
    }

    public static final void enable(Context context, boolean z) {
        Companion.mo44548a(context, z);
    }

    public static final void with(Context context, String str) {
        Companion.mo44547a(context, str);
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_INIT_URL);
        sb.append("/api/initial");
        INIT_URL = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(BASE_RESULT_URL);
        sb2.append("/api/result");
        RESULT_URL = sb2.toString();
    }

    public C5170b create(int i) {
        if (i != JOB_ID) {
            return null;
        }
        Log.d("JobManager", "addJobCreator");
        return new C3027c(this);
    }
}
