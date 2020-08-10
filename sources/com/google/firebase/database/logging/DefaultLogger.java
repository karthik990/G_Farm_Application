package com.google.firebase.database.logging;

import com.google.firebase.database.logging.Logger.Level;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DefaultLogger implements Logger {
    private final Set<String> enabledComponents;
    private final Level minLevel;

    /* renamed from: com.google.firebase.database.logging.DefaultLogger$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C36221 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$database$logging$Logger$Level = new int[Level.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.google.firebase.database.logging.Logger$Level[] r0 = com.google.firebase.database.logging.Logger.Level.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$firebase$database$logging$Logger$Level = r0
                int[] r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.logging.Logger$Level r1 = com.google.firebase.database.logging.Logger.Level.ERROR     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.logging.Logger$Level r1 = com.google.firebase.database.logging.Logger.Level.WARN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.database.logging.Logger$Level r1 = com.google.firebase.database.logging.Logger.Level.INFO     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$google$firebase$database$logging$Logger$Level     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.database.logging.Logger$Level r1 = com.google.firebase.database.logging.Logger.Level.DEBUG     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.logging.DefaultLogger.C36221.<clinit>():void");
        }
    }

    public DefaultLogger(Level level, List<String> list) {
        if (list != null) {
            this.enabledComponents = new HashSet(list);
        } else {
            this.enabledComponents = null;
        }
        this.minLevel = level;
    }

    public Level getLogLevel() {
        return this.minLevel;
    }

    public void onLogMessage(Level level, String str, String str2, long j) {
        if (shouldLog(level, str)) {
            String buildLogMessage = buildLogMessage(level, str, str2, j);
            int i = C36221.$SwitchMap$com$google$firebase$database$logging$Logger$Level[level.ordinal()];
            if (i == 1) {
                error(str, buildLogMessage);
            } else if (i == 2) {
                warn(str, buildLogMessage);
            } else if (i == 3) {
                info(str, buildLogMessage);
            } else if (i == 4) {
                debug(str, buildLogMessage);
            } else {
                throw new RuntimeException("Should not reach here!");
            }
        }
    }

    /* access modifiers changed from: protected */
    public String buildLogMessage(Level level, String str, String str2, long j) {
        Date date = new Date(j);
        StringBuilder sb = new StringBuilder();
        sb.append(date.toString());
        sb.append(" [");
        sb.append(level);
        sb.append("] ");
        sb.append(str);
        sb.append(": ");
        sb.append(str2);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void error(String str, String str2) {
        System.err.println(str2);
    }

    /* access modifiers changed from: protected */
    public void warn(String str, String str2) {
        System.out.println(str2);
    }

    /* access modifiers changed from: protected */
    public void info(String str, String str2) {
        System.out.println(str2);
    }

    /* access modifiers changed from: protected */
    public void debug(String str, String str2) {
        System.out.println(str2);
    }

    /* access modifiers changed from: protected */
    public boolean shouldLog(Level level, String str) {
        return level.ordinal() >= this.minLevel.ordinal() && (this.enabledComponents == null || level.ordinal() > Level.DEBUG.ordinal() || this.enabledComponents.contains(str));
    }
}
