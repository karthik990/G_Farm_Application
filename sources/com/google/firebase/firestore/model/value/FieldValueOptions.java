package com.google.firebase.firestore.model.value;

import com.google.firebase.firestore.util.Assert;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FieldValueOptions {
    private final ServerTimestampBehavior serverTimestampBehavior;
    private final boolean timestampsInSnapshotsEnabled;

    /* renamed from: com.google.firebase.firestore.model.value.FieldValueOptions$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36581 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$DocumentSnapshot$ServerTimestampBehavior */
        static final /* synthetic */ int[] f2035x1f31a5ae = new int[com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.google.firebase.firestore.DocumentSnapshot$ServerTimestampBehavior[] r0 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2035x1f31a5ae = r0
                int[] r0 = f2035x1f31a5ae     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.DocumentSnapshot$ServerTimestampBehavior r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.ESTIMATE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2035x1f31a5ae     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.DocumentSnapshot$ServerTimestampBehavior r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.PREVIOUS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2035x1f31a5ae     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.DocumentSnapshot$ServerTimestampBehavior r1 = com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior.NONE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.model.value.FieldValueOptions.C36581.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    enum ServerTimestampBehavior {
        NONE,
        PREVIOUS,
        ESTIMATE
    }

    private FieldValueOptions(ServerTimestampBehavior serverTimestampBehavior2, boolean z) {
        this.serverTimestampBehavior = serverTimestampBehavior2;
        this.timestampsInSnapshotsEnabled = z;
    }

    /* access modifiers changed from: 0000 */
    public ServerTimestampBehavior getServerTimestampBehavior() {
        return this.serverTimestampBehavior;
    }

    /* access modifiers changed from: 0000 */
    public boolean areTimestampsInSnapshotsEnabled() {
        return this.timestampsInSnapshotsEnabled;
    }

    public static FieldValueOptions create(com.google.firebase.firestore.DocumentSnapshot.ServerTimestampBehavior serverTimestampBehavior2, boolean z) {
        ServerTimestampBehavior serverTimestampBehavior3;
        int i = C36581.f2035x1f31a5ae[serverTimestampBehavior2.ordinal()];
        if (i == 1) {
            serverTimestampBehavior3 = ServerTimestampBehavior.ESTIMATE;
        } else if (i == 2) {
            serverTimestampBehavior3 = ServerTimestampBehavior.PREVIOUS;
        } else if (i == 3) {
            serverTimestampBehavior3 = ServerTimestampBehavior.NONE;
        } else {
            throw Assert.fail("Unexpected case for ServerTimestampBehavior: %s", serverTimestampBehavior2.name());
        }
        return new FieldValueOptions(serverTimestampBehavior3, z);
    }
}
