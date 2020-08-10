package com.firebase.p037ui.database.paging;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.paging.PagedList.Config;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import com.firebase.p037ui.database.ClassSnapshotParser;
import com.firebase.p037ui.database.SnapshotParser;
import com.firebase.p037ui.database.paging.FirebaseDataSource.Factory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

/* renamed from: com.firebase.ui.database.paging.DatabasePagingOptions */
public final class DatabasePagingOptions<T> {
    private final LiveData<PagedList<DataSnapshot>> mData;
    private final ItemCallback<DataSnapshot> mDiffCallback;
    private final LifecycleOwner mOwner;
    private final SnapshotParser<T> mParser;

    /* renamed from: com.firebase.ui.database.paging.DatabasePagingOptions$Builder */
    public static final class Builder<T> {
        private LiveData<PagedList<DataSnapshot>> mData;
        private ItemCallback<DataSnapshot> mDiffCallback;
        private LifecycleOwner mOwner;
        private SnapshotParser<T> mParser;

        public Builder<T> setQuery(Query query, Config config, Class<T> cls) {
            return setQuery(query, config, (SnapshotParser<T>) new ClassSnapshotParser<T>(cls));
        }

        public Builder<T> setQuery(Query query, Config config, SnapshotParser<T> snapshotParser) {
            this.mData = new LivePagedListBuilder(new Factory(query), config).build();
            this.mParser = snapshotParser;
            return this;
        }

        public Builder<T> setDiffCallback(ItemCallback<DataSnapshot> itemCallback) {
            this.mDiffCallback = itemCallback;
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner lifecycleOwner) {
            this.mOwner = lifecycleOwner;
            return this;
        }

        public DatabasePagingOptions<T> build() {
            if (this.mData != null) {
                if (this.mDiffCallback == null) {
                    this.mDiffCallback = new DefaultSnapshotDiffCallback(this.mParser);
                }
                DatabasePagingOptions databasePagingOptions = new DatabasePagingOptions(this.mData, this.mParser, this.mDiffCallback, this.mOwner);
                return databasePagingOptions;
            }
            throw new IllegalStateException("Must call setQuery() before calling build().");
        }
    }

    private DatabasePagingOptions(LiveData<PagedList<DataSnapshot>> liveData, SnapshotParser<T> snapshotParser, ItemCallback<DataSnapshot> itemCallback, LifecycleOwner lifecycleOwner) {
        this.mParser = snapshotParser;
        this.mData = liveData;
        this.mDiffCallback = itemCallback;
        this.mOwner = lifecycleOwner;
    }

    public LiveData<PagedList<DataSnapshot>> getData() {
        return this.mData;
    }

    public SnapshotParser<T> getParser() {
        return this.mParser;
    }

    public ItemCallback<DataSnapshot> getDiffCallback() {
        return this.mDiffCallback;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }
}
