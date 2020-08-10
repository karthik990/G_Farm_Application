package com.flurry.sdk;

/* renamed from: com.flurry.sdk.fu */
public interface C1806fu {

    /* renamed from: a */
    public static final C1807a f1111a = new C1807a(C1808b.DO_NOT_DROP, null);

    /* renamed from: b */
    public static final C1807a f1112b = new C1807a(C1808b.DROP_EVENTS_UNIQUE_NAME_EXCEEDED, null);

    /* renamed from: c */
    public static final C1807a f1113c = new C1807a(C1808b.DROP_EVENTS_NAME_INVALID, null);

    /* renamed from: d */
    public static final C1807a f1114d = new C1807a(C1808b.DROP_EVENTS_COUNT_EXCEEDED, null);

    /* renamed from: e */
    public static final C1807a f1115e = new C1807a(C1808b.DROP_TIMED_EVENTS_START_NOT_FOUND, null);

    /* renamed from: f */
    public static final C1807a f1116f = new C1807a(C1808b.DROP_EVENTS_REASON_UNKNOWN, null);

    /* renamed from: g */
    public static final C1807a f1117g = new C1807a(C1808b.DROP_ERROR_COUNT_EXCEEDED, null);

    /* renamed from: com.flurry.sdk.fu$a */
    public static class C1807a {

        /* renamed from: a */
        public C1808b f1118a;

        /* renamed from: b */
        public C1930jp f1119b;

        C1807a(C1808b bVar, C1930jp jpVar) {
            this.f1118a = bVar;
            this.f1119b = jpVar;
        }
    }

    /* renamed from: com.flurry.sdk.fu$b */
    public enum C1808b {
        DO_NOT_DROP("DoNotDrop"),
        DROP_EVENTS_UNIQUE_NAME_EXCEEDED("Unique Event Name exceeded"),
        DROP_EVENTS_NAME_INVALID("Invalid Event Name"),
        DROP_EVENTS_COUNT_EXCEEDED("Events count exceeded"),
        DROP_TIMED_EVENTS_START_NOT_FOUND("End Timed Event but Start not found"),
        DROP_EVENTS_REASON_UNKNOWN("reason unknown"),
        DROP_ERROR_COUNT_EXCEEDED("Error count exceeded");
        

        /* renamed from: h */
        public final String f1128h;

        private C1808b(String str) {
            this.f1128h = str;
        }
    }

    /* renamed from: a */
    C1807a mo16488a(C1930jp jpVar);

    /* renamed from: a */
    void mo16489a();
}
