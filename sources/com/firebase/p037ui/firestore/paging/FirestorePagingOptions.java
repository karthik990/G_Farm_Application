package com.firebase.p037ui.firestore.paging;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedList.Config;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import com.firebase.p037ui.firestore.ClassSnapshotParser;
import com.firebase.p037ui.firestore.SnapshotParser;
import com.firebase.p037ui.firestore.paging.FirestoreDataSource.Factory;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingOptions */
public final class FirestorePagingOptions<T> {
    private final LiveData<PagedList<DocumentSnapshot>> mData;
    private final ItemCallback<DocumentSnapshot> mDiffCallback;
    private final LifecycleOwner mOwner;
    private final SnapshotParser<T> mParser;

    /* renamed from: com.firebase.ui.firestore.paging.FirestorePagingOptions$Builder */
    public static final class Builder<T> {
        private LiveData<PagedList<DocumentSnapshot>> mData;
        private ItemCallback<DocumentSnapshot> mDiffCallback;
        private LifecycleOwner mOwner;
        private SnapshotParser<T> mParser;

        public Builder<T> setQuery(Query query, Config config, Class<T> cls) {
            return setQuery(query, Source.DEFAULT, config, cls);
        }

        public Builder<T> setQuery(Query query, Config config, SnapshotParser<T> snapshotParser) {
            return setQuery(query, Source.DEFAULT, config, snapshotParser);
        }

        public Builder<T> setQuery(Query query, Source source, Config config, Class<T> cls) {
            return setQuery(query, source, config, (SnapshotParser<T>) new ClassSnapshotParser<T>(cls));
        }

        public Builder<T> setQuery(Query query, Source source, Config config, SnapshotParser<T> snapshotParser) {
            this.mData = new LivePagedListBuilder(new Factory(query, source), config).build();
            this.mParser = snapshotParser;
            return this;
        }

        public Builder<T> setDiffCallback(ItemCallback<DocumentSnapshot> itemCallback) {
            this.mDiffCallback = itemCallback;
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.mOwner = lifecycleOwner;
            return this;
        }

        public FirestorePagingOptions<T> build() {
            if (this.mData != null) {
                SnapshotParser<T> snapshotParser = this.mParser;
                if (snapshotParser != null) {
                    if (this.mDiffCallback == null) {
                        this.mDiffCallback = new DefaultSnapshotDiffCallback(snapshotParser);
                    }
                    FirestorePagingOptions firestorePagingOptions = new FirestorePagingOptions(this.mData, this.mParser, this.mDiffCallback, this.mOwner);
                    return firestorePagingOptions;
                }
            }
            throw new IllegalStateException("Must call setQuery() before calling build().");
        }
    }

    private FirestorePagingOptions(LiveData<PagedList<DocumentSnapshot>> liveData, SnapshotParser<T> snapshotParser, ItemCallback<DocumentSnapshot> itemCallback, LifecycleOwner lifecycleOwner) {
        this.mData = liveData;
        this.mParser = snapshotParser;
        this.mDiffCallback = itemCallback;
        this.mOwner = lifecycleOwner;
    }

    public LiveData<PagedList<DocumentSnapshot>> getData() {
        return this.mData;
    }

    public SnapshotParser<T> getParser() {
        return this.mParser;
    }

    public ItemCallback<DocumentSnapshot> getDiffCallback() {
        return this.mDiffCallback;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }
}
