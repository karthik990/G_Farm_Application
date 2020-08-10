package com.firebase.p037ui.firestore;

import com.firebase.p037ui.common.ChangeEventType;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.firestore.FirestoreArray */
public class FirestoreArray<T> extends ObservableSnapshotArray<T> implements EventListener<QuerySnapshot> {
    private final MetadataChanges mMetadataChanges;
    private final Query mQuery;
    private ListenerRegistration mRegistration;
    private final List<DocumentSnapshot> mSnapshots;

    /* renamed from: com.firebase.ui.firestore.FirestoreArray$1 */
    static /* synthetic */ class C14461 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$DocumentChange$Type = new int[Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.google.firebase.firestore.DocumentChange$Type[] r0 = com.google.firebase.firestore.DocumentChange.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$google$firebase$firestore$DocumentChange$Type = r0
                int[] r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.DocumentChange$Type r1 = com.google.firebase.firestore.DocumentChange.Type.ADDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.DocumentChange$Type r1 = com.google.firebase.firestore.DocumentChange.Type.REMOVED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$google$firebase$firestore$DocumentChange$Type     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.DocumentChange$Type r1 = com.google.firebase.firestore.DocumentChange.Type.MODIFIED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.firebase.p037ui.firestore.FirestoreArray.C14461.<clinit>():void");
        }
    }

    public FirestoreArray(Query query, SnapshotParser<T> snapshotParser) {
        this(query, MetadataChanges.EXCLUDE, snapshotParser);
    }

    public FirestoreArray(Query query, MetadataChanges metadataChanges, SnapshotParser<T> snapshotParser) {
        super(snapshotParser);
        this.mSnapshots = new ArrayList();
        this.mQuery = query;
        this.mMetadataChanges = metadataChanges;
    }

    /* access modifiers changed from: protected */
    public List<DocumentSnapshot> getSnapshots() {
        return this.mSnapshots;
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
        super.onCreate();
        this.mRegistration = this.mQuery.addSnapshotListener(this.mMetadataChanges, (EventListener<QuerySnapshot>) this);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mRegistration.remove();
        this.mRegistration = null;
    }

    public void onEvent(QuerySnapshot querySnapshot, FirebaseFirestoreException firebaseFirestoreException) {
        if (firebaseFirestoreException != null) {
            notifyOnError(firebaseFirestoreException);
            return;
        }
        for (DocumentChange documentChange : querySnapshot.getDocumentChanges(this.mMetadataChanges)) {
            int i = C14461.$SwitchMap$com$google$firebase$firestore$DocumentChange$Type[documentChange.getType().ordinal()];
            if (i == 1) {
                onDocumentAdded(documentChange);
            } else if (i == 2) {
                onDocumentRemoved(documentChange);
            } else if (i == 3) {
                onDocumentModified(documentChange);
            }
        }
        notifyOnDataChanged();
    }

    private void onDocumentAdded(DocumentChange documentChange) {
        QueryDocumentSnapshot document = documentChange.getDocument();
        this.mSnapshots.add(documentChange.getNewIndex(), document);
        notifyOnChildChanged(ChangeEventType.ADDED, document, documentChange.getNewIndex(), -1);
    }

    private void onDocumentRemoved(DocumentChange documentChange) {
        this.mSnapshots.remove(documentChange.getOldIndex());
        notifyOnChildChanged(ChangeEventType.REMOVED, documentChange.getDocument(), -1, documentChange.getOldIndex());
    }

    private void onDocumentModified(DocumentChange documentChange) {
        QueryDocumentSnapshot document = documentChange.getDocument();
        if (documentChange.getOldIndex() == documentChange.getNewIndex()) {
            this.mSnapshots.set(documentChange.getNewIndex(), document);
            notifyOnChildChanged(ChangeEventType.CHANGED, document, documentChange.getNewIndex(), documentChange.getNewIndex());
            return;
        }
        this.mSnapshots.remove(documentChange.getOldIndex());
        this.mSnapshots.add(documentChange.getNewIndex(), document);
        notifyOnChildChanged(ChangeEventType.MOVED, document, documentChange.getNewIndex(), documentChange.getOldIndex());
        notifyOnChildChanged(ChangeEventType.CHANGED, document, documentChange.getNewIndex(), documentChange.getNewIndex());
    }
}
