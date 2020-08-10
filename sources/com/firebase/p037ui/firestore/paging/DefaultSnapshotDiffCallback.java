package com.firebase.p037ui.firestore.paging;

import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import com.firebase.p037ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.paging.DefaultSnapshotDiffCallback */
public class DefaultSnapshotDiffCallback<T> extends ItemCallback<DocumentSnapshot> {
    private final SnapshotParser<T> mParser;

    public DefaultSnapshotDiffCallback(SnapshotParser<T> snapshotParser) {
        this.mParser = snapshotParser;
    }

    public boolean areItemsTheSame(DocumentSnapshot documentSnapshot, DocumentSnapshot documentSnapshot2) {
        return documentSnapshot.getId().equals(documentSnapshot2.getId());
    }

    public boolean areContentsTheSame(DocumentSnapshot documentSnapshot, DocumentSnapshot documentSnapshot2) {
        return this.mParser.parseSnapshot(documentSnapshot).equals(this.mParser.parseSnapshot(documentSnapshot2));
    }
}
