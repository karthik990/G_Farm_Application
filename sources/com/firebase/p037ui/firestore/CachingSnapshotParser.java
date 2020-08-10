package com.firebase.p037ui.firestore;

import com.firebase.p037ui.common.BaseCachingSnapshotParser;
import com.firebase.p037ui.common.BaseSnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.CachingSnapshotParser */
public class CachingSnapshotParser<T> extends BaseCachingSnapshotParser<DocumentSnapshot, T> implements SnapshotParser<T> {
    public CachingSnapshotParser(BaseSnapshotParser<DocumentSnapshot, T> baseSnapshotParser) {
        super(baseSnapshotParser);
    }

    public String getId(DocumentSnapshot documentSnapshot) {
        return documentSnapshot.getId();
    }
}
