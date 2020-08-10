package com.google.firebase.firestore;

import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentChange {
    private final QueryDocumentSnapshot document;
    private final int newIndex;
    private final int oldIndex;
    private final Type type;

    /* renamed from: com.google.firebase.firestore.DocumentChange$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36371 {

        /* renamed from: $SwitchMap$com$google$firebase$firestore$core$DocumentViewChange$Type */
        static final /* synthetic */ int[] f2018x33862af7 = new int[com.google.firebase.firestore.core.DocumentViewChange.Type.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.google.firebase.firestore.core.DocumentViewChange$Type[] r0 = com.google.firebase.firestore.core.DocumentViewChange.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2018x33862af7 = r0
                int[] r0 = f2018x33862af7     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2018x33862af7     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2018x33862af7     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.MODIFIED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2018x33862af7     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.firestore.core.DocumentViewChange$Type r1 = com.google.firebase.firestore.core.DocumentViewChange.Type.REMOVED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.DocumentChange.C36371.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Type {
        ADDED,
        MODIFIED,
        REMOVED
    }

    DocumentChange(QueryDocumentSnapshot queryDocumentSnapshot, Type type2, int i, int i2) {
        this.type = type2;
        this.document = queryDocumentSnapshot;
        this.oldIndex = i;
        this.newIndex = i2;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DocumentChange)) {
            return false;
        }
        DocumentChange documentChange = (DocumentChange) obj;
        if (!this.type.equals(documentChange.type) || !this.document.equals(documentChange.document) || this.oldIndex != documentChange.oldIndex || this.newIndex != documentChange.newIndex) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((this.type.hashCode() * 31) + this.document.hashCode()) * 31) + this.oldIndex) * 31) + this.newIndex;
    }

    public Type getType() {
        return this.type;
    }

    public QueryDocumentSnapshot getDocument() {
        return this.document;
    }

    public int getOldIndex() {
        return this.oldIndex;
    }

    public int getNewIndex() {
        return this.newIndex;
    }

    static List<DocumentChange> changesFromSnapshot(FirebaseFirestore firebaseFirestore, MetadataChanges metadataChanges, ViewSnapshot viewSnapshot) {
        int i;
        int i2;
        ArrayList arrayList = new ArrayList();
        if (viewSnapshot.getOldDocuments().isEmpty()) {
            Document document2 = null;
            int i3 = 0;
            for (DocumentViewChange documentViewChange : viewSnapshot.getChanges()) {
                Document document3 = documentViewChange.getDocument();
                QueryDocumentSnapshot fromDocument = QueryDocumentSnapshot.fromDocument(firebaseFirestore, document3, viewSnapshot.isFromCache(), viewSnapshot.getMutatedKeys().contains(document3.getKey()));
                Assert.hardAssert(documentViewChange.getType() == com.google.firebase.firestore.core.DocumentViewChange.Type.ADDED, "Invalid added event for first snapshot", new Object[0]);
                Assert.hardAssert(document2 == null || viewSnapshot.getQuery().comparator().compare(document2, document3) < 0, "Got added events in wrong order", new Object[0]);
                int i4 = i3 + 1;
                arrayList.add(new DocumentChange(fromDocument, Type.ADDED, -1, i3));
                document2 = document3;
                i3 = i4;
            }
        } else {
            DocumentSet oldDocuments = viewSnapshot.getOldDocuments();
            for (DocumentViewChange documentViewChange2 : viewSnapshot.getChanges()) {
                if (metadataChanges != MetadataChanges.EXCLUDE || documentViewChange2.getType() != com.google.firebase.firestore.core.DocumentViewChange.Type.METADATA) {
                    Document document4 = documentViewChange2.getDocument();
                    QueryDocumentSnapshot fromDocument2 = QueryDocumentSnapshot.fromDocument(firebaseFirestore, document4, viewSnapshot.isFromCache(), viewSnapshot.getMutatedKeys().contains(document4.getKey()));
                    Type type2 = getType(documentViewChange2);
                    String str = "Index for document not found";
                    if (type2 != Type.ADDED) {
                        i = oldDocuments.indexOf(document4.getKey());
                        Assert.hardAssert(i >= 0, str, new Object[0]);
                        oldDocuments = oldDocuments.remove(document4.getKey());
                    } else {
                        i = -1;
                    }
                    if (type2 != Type.REMOVED) {
                        oldDocuments = oldDocuments.add(document4);
                        i2 = oldDocuments.indexOf(document4.getKey());
                        Assert.hardAssert(i2 >= 0, str, new Object[0]);
                    } else {
                        i2 = -1;
                    }
                    arrayList.add(new DocumentChange(fromDocument2, type2, i, i2));
                }
            }
        }
        return arrayList;
    }

    private static Type getType(DocumentViewChange documentViewChange) {
        int i = C36371.f2018x33862af7[documentViewChange.getType().ordinal()];
        if (i == 1) {
            return Type.ADDED;
        }
        if (i == 2 || i == 3) {
            return Type.MODIFIED;
        }
        if (i == 4) {
            return Type.REMOVED;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown view change type: ");
        sb.append(documentViewChange.getType());
        throw new IllegalArgumentException(sb.toString());
    }
}
