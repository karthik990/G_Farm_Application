package com.flurry.sdk;

/* renamed from: com.flurry.sdk.fy */
public interface C1812fy {

    /* renamed from: com.flurry.sdk.fy$a */
    public enum C1813a {
        f1131a(r2),
        REASON_STICKY_SET_COMPLETE("Sticky set is complete"),
        REASON_APP_STATE_CHANGE("App State has changed"),
        REASON_SESSION_FINALIZE("Session Finalized"),
        REASON_APP_CRASH("App crashed"),
        REASON_FORCE_FLUSH("Force to Flush"),
        REASON_STARTUP("App Started"),
        REASON_PUSH_TOKEN_REFRESH("Push Token Refreshed"),
        REASON_DATA_DELETION("Delete Data");
        

        /* renamed from: j */
        public final String f1141j;

        private C1813a(String str) {
            this.f1141j = str;
        }
    }

    /* renamed from: a */
    void mo16490a();

    /* renamed from: a */
    void mo16491a(C1930jp jpVar);
}
