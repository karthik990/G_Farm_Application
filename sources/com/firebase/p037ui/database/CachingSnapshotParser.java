package com.firebase.p037ui.database;

import com.firebase.p037ui.common.BaseCachingSnapshotParser;
import com.firebase.p037ui.common.BaseSnapshotParser;
import com.google.firebase.database.DataSnapshot;

/* renamed from: com.firebase.ui.database.CachingSnapshotParser */
public class CachingSnapshotParser<T> extends BaseCachingSnapshotParser<DataSnapshot, T> {
    public CachingSnapshotParser(BaseSnapshotParser<DataSnapshot, T> baseSnapshotParser) {
        super(baseSnapshotParser);
    }

    public String getId(DataSnapshot dataSnapshot) {
        return dataSnapshot.getKey();
    }
}
