package com.google.firebase.firestore.remote;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
final class TargetState {
    private boolean current = false;
    private final Map<DocumentKey, Type> documentChanges = new HashMap();
    private boolean hasChanges = true;
    private int outstandingResponses = 0;
    private ByteString resumeToken = ByteString.EMPTY;

    /* renamed from: com.google.firebase.firestore.remote.TargetState$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36731 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type */
        static final /* synthetic */ int[] f2052x33862af7 = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.google.firebase.firestore.core.DocumentViewChange$Type[] r0 = com.google.firebase.firestore.core.DocumentViewChange.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2052x33862af7 = r0
                int[] r0 = f2052x33862af7     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2052x33862af7     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2052x33862af7     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.TargetState.C36731.<clinit>():void");
        }
    }

    TargetState() {
    }

    /* access modifiers changed from: 0000 */
    public boolean isCurrent() {
        return this.current;
    }

    /* access modifiers changed from: 0000 */
    public boolean isPending() {
        return this.outstandingResponses != 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasChanges() {
        return this.hasChanges;
    }

    /* access modifiers changed from: 0000 */
    public void updateResumeToken(ByteString byteString) {
        if (!byteString.isEmpty()) {
            this.hasChanges = true;
            this.resumeToken = byteString;
        }
    }

    /* access modifiers changed from: 0000 */
    public TargetChange toTargetChange() {
        ImmutableSortedSet emptyKeySet = DocumentKey.emptyKeySet();
        ImmutableSortedSet emptyKeySet2 = DocumentKey.emptyKeySet();
        ImmutableSortedSet emptyKeySet3 = DocumentKey.emptyKeySet();
        ImmutableSortedSet immutableSortedSet = emptyKeySet;
        ImmutableSortedSet immutableSortedSet2 = emptyKeySet2;
        ImmutableSortedSet immutableSortedSet3 = emptyKeySet3;
        for (Entry entry : this.documentChanges.entrySet()) {
            DocumentKey documentKey = (DocumentKey) entry.getKey();
            Type type = (Type) entry.getValue();
            int i = C36731.f2052x33862af7[type.ordinal()];
            if (i == 1) {
                immutableSortedSet = immutableSortedSet.insert(documentKey);
            } else if (i == 2) {
                immutableSortedSet2 = immutableSortedSet2.insert(documentKey);
            } else if (i == 3) {
                immutableSortedSet3 = immutableSortedSet3.insert(documentKey);
            } else {
                throw Assert.fail("Encountered invalid change type: %s", type);
            }
        }
        TargetChange targetChange = new TargetChange(this.resumeToken, this.current, immutableSortedSet, immutableSortedSet2, immutableSortedSet3);
        return targetChange;
    }

    /* access modifiers changed from: 0000 */
    public void clearChanges() {
        this.hasChanges = false;
        this.documentChanges.clear();
    }

    /* access modifiers changed from: 0000 */
    public void addDocumentChange(DocumentKey documentKey, Type type) {
        this.hasChanges = true;
        this.documentChanges.put(documentKey, type);
    }

    /* access modifiers changed from: 0000 */
    public void removeDocumentChange(DocumentKey documentKey) {
        this.hasChanges = true;
        this.documentChanges.remove(documentKey);
    }

    /* access modifiers changed from: 0000 */
    public void recordPendingTargetRequest() {
        this.outstandingResponses++;
    }

    /* access modifiers changed from: 0000 */
    public void recordTargetResponse() {
        this.outstandingResponses--;
    }

    /* access modifiers changed from: 0000 */
    public void markCurrent() {
        this.hasChanges = true;
        this.current = true;
    }
}
