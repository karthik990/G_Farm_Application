package com.firebase.p037ui.database.paging;

import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import com.firebase.p037ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;

/* renamed from: com.firebase.ui.database.paging.DefaultSnapshotDiffCallback */
public class DefaultSnapshotDiffCallback<T> extends ItemCallback<DataSnapshot> {
    private final SnapshotParser<T> mParser;

    public DefaultSnapshotDiffCallback(SnapshotParser<T> snapshotParser) {
        this.mParser = snapshotParser;
    }

    public boolean areItemsTheSame(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot2) {
        return dataSnapshot.getKey().equals(dataSnapshot2.getKey());
    }

    public boolean areContentsTheSame(DataSnapshot dataSnapshot, DataSnapshot dataSnapshot2) {
        return this.mParser.parseSnapshot(dataSnapshot).equals(this.mParser.parseSnapshot(dataSnapshot2));
    }
}
