package com.google.firebase.database.core;

import android.support.p009v4.media.session.PlaybackStateCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Logger.Level;
import com.google.firebase.database.logging.Logger;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DatabaseConfig extends Context {

    /* renamed from: com.google.firebase.database.core.DatabaseConfig$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C35441 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$Logger$Level = new int[Level.values().length];

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
                com.google.firebase.database.Logger$Level[] r0 = com.google.firebase.database.Logger.Level.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$firebase$database$Logger$Level = r0
                int[] r0 = $SwitchMap$com$google$firebase$database$Logger$Level     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.Logger$Level r1 = com.google.firebase.database.Logger.Level.DEBUG     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$google$firebase$database$Logger$Level     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.Logger$Level r1 = com.google.firebase.database.Logger.Level.INFO     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$google$firebase$database$Logger$Level     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.database.Logger$Level r1 = com.google.firebase.database.Logger.Level.WARN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$google$firebase$database$Logger$Level     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.database.Logger$Level r1 = com.google.firebase.database.Logger.Level.ERROR     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$com$google$firebase$database$Logger$Level     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.firebase.database.Logger$Level r1 = com.google.firebase.database.Logger.Level.NONE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.DatabaseConfig.C35441.<clinit>():void");
        }
    }

    public synchronized void setLogger(Logger logger) {
        assertUnfrozen();
        this.logger = logger;
    }

    public synchronized void setEventTarget(EventTarget eventTarget) {
        assertUnfrozen();
        this.eventTarget = eventTarget;
    }

    public synchronized void setLogLevel(Level level) {
        assertUnfrozen();
        int i = C35441.$SwitchMap$com$google$firebase$database$Logger$Level[level.ordinal()];
        if (i == 1) {
            this.logLevel = Logger.Level.DEBUG;
        } else if (i == 2) {
            this.logLevel = Logger.Level.INFO;
        } else if (i == 3) {
            this.logLevel = Logger.Level.WARN;
        } else if (i == 4) {
            this.logLevel = Logger.Level.ERROR;
        } else if (i == 5) {
            this.logLevel = Logger.Level.NONE;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown log level: ");
            sb.append(level);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public synchronized void setDebugLogComponents(List<String> list) {
        assertUnfrozen();
        setLogLevel(Level.DEBUG);
        this.loggedComponents = list;
    }

    public void setRunLoop(RunLoop runLoop) {
        this.runLoop = runLoop;
    }

    public void setAuthTokenProvider(AuthTokenProvider authTokenProvider) {
        this.authTokenProvider = authTokenProvider;
    }

    public synchronized void setSessionPersistenceKey(String str) {
        assertUnfrozen();
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Session identifier is not allowed to be empty or null!");
        }
        this.persistenceKey = str;
    }

    public synchronized void setPersistenceEnabled(boolean z) {
        assertUnfrozen();
        this.persistenceEnabled = z;
    }

    public synchronized void setPersistenceCacheSizeBytes(long j) {
        assertUnfrozen();
        if (j < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            throw new DatabaseException("The minimum cache size must be at least 1MB");
        } else if (j <= 104857600) {
            this.cacheSize = j;
        } else {
            throw new DatabaseException("Firebase Database currently doesn't support a cache size larger than 100MB");
        }
    }

    public synchronized void setFirebaseApp(FirebaseApp firebaseApp) {
        this.firebaseApp = firebaseApp;
    }
}
