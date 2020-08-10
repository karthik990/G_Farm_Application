package com.firebase.p037ui.firestore.paging;

import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.firebase.p037ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingAdapter */
public abstract class FirestorePagingAdapter<T, VH extends ViewHolder> extends PagedListAdapter<DocumentSnapshot, VH> implements LifecycleObserver {
    private static final String TAG = "FirestorePagingAdapter";
    private final Observer<PagedList<DocumentSnapshot>> mDataObserver = new Observer<PagedList<DocumentSnapshot>>() {
        public void onChanged(PagedList<DocumentSnapshot> pagedList) {
            if (pagedList != null) {
                FirestorePagingAdapter.this.submitList(pagedList);
            }
        }
    };
    private final LiveData<FirestoreDataSource> mDataSource;
    private final Observer<FirestoreDataSource> mDataSourceObserver = new Observer<FirestoreDataSource>() {
        public void onChanged(FirestoreDataSource firestoreDataSource) {
        }
    };
    private final Observer<Exception> mErrorObserver = new Observer<Exception>() {
        public void onChanged(Exception exc) {
            FirestorePagingAdapter.this.onError(exc);
        }
    };
    private final LiveData<Exception> mException;
    private final LiveData<LoadingState> mLoadingState;
    private final SnapshotParser<T> mParser;
    private final LiveData<PagedList<DocumentSnapshot>> mSnapshots;
    private final Observer<LoadingState> mStateObserver = new Observer<LoadingState>() {
        public void onChanged(LoadingState loadingState) {
            if (loadingState != null) {
                FirestorePagingAdapter.this.onLoadingStateChanged(loadingState);
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void onBindViewHolder(VH vh, int i, T t);

    /* access modifiers changed from: protected */
    public void onError(Exception exc) {
    }

    /* access modifiers changed from: protected */
    public void onLoadingStateChanged(LoadingState loadingState) {
    }

    public FirestorePagingAdapter(FirestorePagingOptions<T> firestorePagingOptions) {
        super(firestorePagingOptions.getDiffCallback());
        this.mSnapshots = firestorePagingOptions.getData();
        this.mLoadingState = Transformations.switchMap(this.mSnapshots, new Function<PagedList<DocumentSnapshot>, LiveData<LoadingState>>() {
            public LiveData<LoadingState> apply(PagedList<DocumentSnapshot> pagedList) {
                return pagedList.getDataSource().getLoadingState();
            }
        });
        this.mDataSource = Transformations.map(this.mSnapshots, new Function<PagedList<DocumentSnapshot>, FirestoreDataSource>() {
            public FirestoreDataSource apply(PagedList<DocumentSnapshot> pagedList) {
                return pagedList.getDataSource();
            }
        });
        this.mException = Transformations.switchMap(this.mSnapshots, new Function<PagedList<DocumentSnapshot>, LiveData<Exception>>() {
            public LiveData<Exception> apply(PagedList<DocumentSnapshot> pagedList) {
                return pagedList.getDataSource().getLastError();
            }
        });
        this.mParser = firestorePagingOptions.getParser();
        if (firestorePagingOptions.getOwner() != null) {
            firestorePagingOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    public void retry() {
        FirestoreDataSource firestoreDataSource = (FirestoreDataSource) this.mDataSource.getValue();
        if (firestoreDataSource == null) {
            Log.w(TAG, "Called retry() when FirestoreDataSource is null!");
        } else {
            firestoreDataSource.retry();
        }
    }

    public void refresh() {
        FirestoreDataSource firestoreDataSource = (FirestoreDataSource) this.mDataSource.getValue();
        if (firestoreDataSource == null) {
            Log.w(TAG, "Called refresh() when FirestoreDataSource is null!");
        } else {
            firestoreDataSource.invalidate();
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    public void startListening() {
        this.mSnapshots.observeForever(this.mDataObserver);
        this.mLoadingState.observeForever(this.mStateObserver);
        this.mDataSource.observeForever(this.mDataSourceObserver);
        this.mException.observeForever(this.mErrorObserver);
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void stopListening() {
        this.mSnapshots.removeObserver(this.mDataObserver);
        this.mLoadingState.removeObserver(this.mStateObserver);
        this.mDataSource.removeObserver(this.mDataSourceObserver);
        this.mException.removeObserver(this.mErrorObserver);
    }

    public void onBindViewHolder(VH vh, int i) {
        onBindViewHolder(vh, i, this.mParser.parseSnapshot((DocumentSnapshot) getItem(i)));
    }
}
