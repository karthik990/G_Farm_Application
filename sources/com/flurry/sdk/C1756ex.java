package com.flurry.sdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.flurry.sdk.ex */
public final class C1756ex {

    /* renamed from: a */
    private static final Map<C1758a, C1735e> f1016a;

    /* renamed from: com.flurry.sdk.ex$1 */
    static /* synthetic */ class C17571 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1017a = new int[C1758a.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.flurry.sdk.ex$a[] r0 = com.flurry.sdk.C1756ex.C1758a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1017a = r0
                int[] r0 = f1017a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.ex$a r1 = com.flurry.sdk.C1756ex.C1758a.PUBLIC_API     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1017a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.ex$a r1 = com.flurry.sdk.C1756ex.C1758a.CORE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1017a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.flurry.sdk.ex$a r1 = com.flurry.sdk.C1756ex.C1758a.DATA_PROCESSOR     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f1017a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.flurry.sdk.ex$a r1 = com.flurry.sdk.C1756ex.C1758a.PROVIDER     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f1017a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.flurry.sdk.ex$a r1 = com.flurry.sdk.C1756ex.C1758a.CONFIG     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1756ex.C17571.<clinit>():void");
        }
    }

    /* renamed from: com.flurry.sdk.ex$a */
    public enum C1758a {
        CORE,
        DATA_PROCESSOR,
        PROVIDER,
        PUBLIC_API,
        REPORTS,
        CONFIG,
        MISC
    }

    /* renamed from: com.flurry.sdk.ex$b */
    static class C1759b implements RejectedExecutionHandler {
        private C1759b() {
        }

        /* synthetic */ C1759b(byte b) {
            this();
        }

        public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            StringBuilder sb = new StringBuilder();
            sb.append(runnable.toString());
            sb.append("is rejected");
            C1685cy.m754a(6, "ActorFactory", sb.toString());
        }
    }

    /* renamed from: com.flurry.sdk.ex$c */
    static class C1760c implements ThreadFactory {

        /* renamed from: a */
        private final AtomicInteger f1026a = new AtomicInteger(0);

        /* renamed from: b */
        private final String f1027b = "Flurry #";

        C1760c() {
        }

        public final Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f1027b);
            sb.append(this.f1026a.incrementAndGet());
            Thread thread = new Thread(runnable, sb.toString());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            thread.setPriority(10);
            return thread;
        }
    }

    static {
        C1758a[] values;
        Executor executor;
        HashMap hashMap = new HashMap();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        for (C1758a aVar : C1758a.values()) {
            String name = aVar.name();
            int i = C17571.f1017a[aVar.ordinal()];
            if (i == 1) {
                executor = m905a();
            } else if (i == 2) {
                executor = m906a(availableProcessors);
            } else if (i == 3) {
                executor = m905a();
            } else if (i == 4) {
                executor = m906a(availableProcessors);
            } else if (i != 5) {
                executor = m905a();
            } else {
                executor = m905a();
            }
            hashMap.put(aVar, new C1735e(executor, name));
        }
        f1016a = Collections.unmodifiableMap(hashMap);
    }

    /* renamed from: a */
    public static synchronized C1735e m904a(C1758a aVar) {
        C1735e eVar;
        synchronized (C1756ex.class) {
            eVar = (C1735e) f1016a.get(aVar);
        }
        return eVar;
    }

    /* renamed from: a */
    private static Executor m906a(int i) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, i, 6, TimeUnit.SECONDS, new LinkedBlockingQueue(), new C1760c(), new C1759b(0));
        return threadPoolExecutor;
    }

    /* renamed from: a */
    private static Executor m905a() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new C1760c(), new C1759b(0));
        return threadPoolExecutor;
    }
}
