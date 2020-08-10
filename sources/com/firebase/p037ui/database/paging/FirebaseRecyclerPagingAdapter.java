package com.firebase.p037ui.database.paging;

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
import com.firebase.p037ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/* renamed from: com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter */
public abstract class FirebaseRecyclerPagingAdapter<T, VH extends ViewHolder> extends PagedListAdapter<DataSnapshot, VH> implements LifecycleObserver {
    private final String TAG = "FirebasePagingAdapter";
    private final Observer<PagedList<DataSnapshot>> mDataObserver = new Observer<PagedList<DataSnapshot>>() {
        public void onChanged(PagedList<DataSnapshot> pagedList) {
            if (pagedList != null) {
                FirebaseRecyclerPagingAdapter.this.submitList(pagedList);
            }
        }
    };
    private final LiveData<FirebaseDataSource> mDataSource;
    private final Observer<FirebaseDataSource> mDataSourceObserver = new Observer<FirebaseDataSource>() {
        public void onChanged(FirebaseDataSource firebaseDataSource) {
        }
    };
    private final LiveData<DatabaseError> mDatabaseError;
    private final Observer<DatabaseError> mErrorObserver = new Observer<DatabaseError>() {
        public void onChanged(DatabaseError databaseError) {
            FirebaseRecyclerPagingAdapter.this.onError(databaseError);
        }
    };
    private final LiveData<LoadingState> mLoadingState;
    private final LiveData<PagedList<DataSnapshot>> mPagedList;
    private final SnapshotParser<T> mParser;
    private final Observer<LoadingState> mStateObserver = new Observer<LoadingState>() {
        public void onChanged(LoadingState loadingState) {
            if (loadingState != null) {
                FirebaseRecyclerPagingAdapter.this.onLoadingStateChanged(loadingState);
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void onBindViewHolder(VH vh, int i, T t);

    /* access modifiers changed from: protected */
    public void onError(DatabaseError databaseError) {
    }

    /* access modifiers changed from: protected */
    public abstract void onLoadingStateChanged(LoadingState loadingState);

    public FirebaseRecyclerPagingAdapter(DatabasePagingOptions<T> databasePagingOptions) {
        super(databasePagingOptions.getDiffCallback());
        this.mPagedList = databasePagingOptions.getData();
        this.mDataSource = Transformations.map(this.mPagedList, new Function<PagedList<DataSnapshot>, FirebaseDataSource>() {
            public FirebaseDataSource apply(PagedList<DataSnapshot> pagedList) {
                return pagedList.getDataSource();
            }
        });
        this.mLoadingState = Transformations.switchMap(this.mPagedList, new Function<PagedList<DataSnapshot>, LiveData<LoadingState>>() {
            public LiveData<LoadingState> apply(PagedList<DataSnapshot> pagedList) {
                return pagedList.getDataSource().getLoadingState();
            }
        });
        this.mDatabaseError = Transformations.switchMap(this.mPagedList, new Function<PagedList<DataSnapshot>, LiveData<DatabaseError>>() {
            public LiveData<DatabaseError> apply(PagedList<DataSnapshot> pagedList) {
                return pagedList.getDataSource().getLastError();
            }
        });
        this.mParser = databasePagingOptions.getParser();
        if (databasePagingOptions.getOwner() != null) {
            databasePagingOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    public void retry() {
        FirebaseDataSource firebaseDataSource = (FirebaseDataSource) this.mDataSource.getValue();
        if (firebaseDataSource == null) {
            Log.w("FirebasePagingAdapter", "Called retry() when FirebaseDataSource is null!");
        } else {
            firebaseDataSource.retry();
        }
    }

    public void refresh() {
        FirebaseDataSource firebaseDataSource = (FirebaseDataSource) this.mDataSource.getValue();
        if (firebaseDataSource == null) {
            Log.w("FirebasePagingAdapter", "Called refresh() when FirebaseDataSource is null!");
        } else {
            firebaseDataSource.invalidate();
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    public void startListening() {
        this.mPagedList.observeForever(this.mDataObserver);
        this.mLoadingState.observeForever(this.mStateObserver);
        this.mDatabaseError.observeForever(this.mErrorObserver);
        this.mDataSource.observeForever(this.mDataSourceObserver);
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void stopListening() {
        this.mPagedList.removeObserver(this.mDataObserver);
        this.mLoadingState.removeObserver(this.mStateObserver);
        this.mDatabaseError.removeObserver(this.mErrorObserver);
        this.mDataSource.removeObserver(this.mDataSourceObserver);
    }

    public void onBindViewHolder(VH vh, int i) {
        onBindViewHolder(vh, i, this.mParser.parseSnapshot((DataSnapshot) getItem(i)));
    }

    public DatabaseReference getRef(int i) {
        return ((DataSnapshot) getItem(i)).getRef();
    }
}
