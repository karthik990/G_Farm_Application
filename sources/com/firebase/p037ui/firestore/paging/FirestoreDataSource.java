package com.firebase.p037ui.firestore.paging;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PageKeyedDataSource.LoadCallback;
import androidx.paging.PageKeyedDataSource.LoadInitialCallback;
import androidx.paging.PageKeyedDataSource.LoadInitialParams;
import androidx.paging.PageKeyedDataSource.LoadParams;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import java.util.List;

/* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource */
public class FirestoreDataSource extends PageKeyedDataSource<PageKey, DocumentSnapshot> {
    private static final String TAG = "FirestoreDataSource";
    private final Query mBaseQuery;
    /* access modifiers changed from: private */
    public final MutableLiveData<Exception> mException = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public final MutableLiveData<LoadingState> mLoadingState = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public Runnable mRetryRunnable;
    private final Source mSource;

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$Factory */
    public static class Factory extends androidx.paging.DataSource.Factory<PageKey, DocumentSnapshot> {
        private final Query mQuery;
        private final Source mSource;

        public Factory(Query query, Source source) {
            this.mQuery = query;
            this.mSource = source;
        }

        public DataSource<PageKey, DocumentSnapshot> create() {
            return new FirestoreDataSource(this.mQuery, this.mSource);
        }
    }

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$OnLoadFailureListener */
    private abstract class OnLoadFailureListener implements OnFailureListener {
        /* access modifiers changed from: protected */
        public abstract Runnable getRetryRunnable();

        private OnLoadFailureListener() {
        }

        public void onFailure(Exception exc) {
            Log.w(FirestoreDataSource.TAG, "load:onFailure", exc);
            FirestoreDataSource.this.mLoadingState.postValue(LoadingState.ERROR);
            FirestoreDataSource.this.mRetryRunnable = getRetryRunnable();
            FirestoreDataSource.this.mException.postValue(exc);
        }
    }

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$OnLoadSuccessListener */
    private abstract class OnLoadSuccessListener implements OnSuccessListener<QuerySnapshot> {
        /* access modifiers changed from: protected */
        public abstract void setResult(QuerySnapshot querySnapshot);

        private OnLoadSuccessListener() {
        }

        public void onSuccess(QuerySnapshot querySnapshot) {
            setResult(querySnapshot);
            FirestoreDataSource.this.mLoadingState.postValue(LoadingState.LOADED);
            if (querySnapshot.getDocuments().isEmpty()) {
                FirestoreDataSource.this.mLoadingState.postValue(LoadingState.FINISHED);
            }
            FirestoreDataSource.this.mRetryRunnable = null;
        }
    }

    public void loadBefore(LoadParams<PageKey> loadParams, LoadCallback<PageKey, DocumentSnapshot> loadCallback) {
    }

    public FirestoreDataSource(Query query, Source source) {
        this.mBaseQuery = query;
        this.mSource = source;
    }

    public void loadInitial(final LoadInitialParams<PageKey> loadInitialParams, final LoadInitialCallback<PageKey, DocumentSnapshot> loadInitialCallback) {
        this.mLoadingState.postValue(LoadingState.LOADING_INITIAL);
        this.mBaseQuery.limit((long) loadInitialParams.requestedLoadSize).get(this.mSource).addOnSuccessListener(new OnLoadSuccessListener() {
            /* access modifiers changed from: protected */
            public void setResult(QuerySnapshot querySnapshot) {
                loadInitialCallback.onResult(querySnapshot.getDocuments(), null, FirestoreDataSource.this.getNextPageKey(querySnapshot));
            }
        }).addOnFailureListener(new OnLoadFailureListener() {
            /* access modifiers changed from: protected */
            public Runnable getRetryRunnable() {
                return FirestoreDataSource.this.getRetryLoadInitial(loadInitialParams, loadInitialCallback);
            }
        });
    }

    public void loadAfter(final LoadParams<PageKey> loadParams, final LoadCallback<PageKey, DocumentSnapshot> loadCallback) {
        PageKey pageKey = (PageKey) loadParams.key;
        this.mLoadingState.postValue(LoadingState.LOADING_MORE);
        pageKey.getPageQuery(this.mBaseQuery, loadParams.requestedLoadSize).get(this.mSource).addOnSuccessListener(new OnLoadSuccessListener() {
            /* access modifiers changed from: protected */
            public void setResult(QuerySnapshot querySnapshot) {
                loadCallback.onResult(querySnapshot.getDocuments(), FirestoreDataSource.this.getNextPageKey(querySnapshot));
            }
        }).addOnFailureListener(new OnLoadFailureListener() {
            /* access modifiers changed from: protected */
            public Runnable getRetryRunnable() {
                return FirestoreDataSource.this.getRetryLoadAfter(loadParams, loadCallback);
            }
        });
    }

    /* access modifiers changed from: private */
    public PageKey getNextPageKey(QuerySnapshot querySnapshot) {
        return new PageKey(getLast(querySnapshot.getDocuments()), null);
    }

    public LiveData<LoadingState> getLoadingState() {
        return this.mLoadingState;
    }

    public LiveData<Exception> getLastError() {
        return this.mException;
    }

    public void retry() {
        LoadingState loadingState = (LoadingState) this.mLoadingState.getValue();
        LoadingState loadingState2 = LoadingState.ERROR;
        String str = TAG;
        if (loadingState != loadingState2) {
            StringBuilder sb = new StringBuilder();
            sb.append("retry() not valid when in state: ");
            sb.append(loadingState);
            Log.w(str, sb.toString());
            return;
        }
        Runnable runnable = this.mRetryRunnable;
        if (runnable == null) {
            Log.w(str, "retry() called with no eligible retry runnable.");
        } else {
            runnable.run();
        }
    }

    private DocumentSnapshot getLast(List<DocumentSnapshot> list) {
        if (list.isEmpty()) {
            return null;
        }
        return (DocumentSnapshot) list.get(list.size() - 1);
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadAfter(final LoadParams<PageKey> loadParams, final LoadCallback<PageKey, DocumentSnapshot> loadCallback) {
        return new Runnable() {
            public void run() {
                FirestoreDataSource.this.loadAfter(loadParams, loadCallback);
            }
        };
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadInitial(final LoadInitialParams<PageKey> loadInitialParams, final LoadInitialCallback<PageKey, DocumentSnapshot> loadInitialCallback) {
        return new Runnable() {
            public void run() {
                FirestoreDataSource.this.loadInitial(loadInitialParams, loadInitialCallback);
            }
        };
    }
}
