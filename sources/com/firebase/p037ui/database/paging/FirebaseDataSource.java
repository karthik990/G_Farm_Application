package com.firebase.p037ui.database.paging;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PageKeyedDataSource.LoadCallback;
import androidx.paging.PageKeyedDataSource.LoadInitialCallback;
import androidx.paging.PageKeyedDataSource.LoadInitialParams;
import androidx.paging.PageKeyedDataSource.LoadParams;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.firebase.ui.database.paging.FirebaseDataSource */
public class FirebaseDataSource extends PageKeyedDataSource<String, DataSnapshot> {
    private static final String DETAILS_DATABASE_NOT_FOUND = "No data was returned for the given query: ";
    private static final String MESSAGE_DATABASE_NOT_FOUND = "Data not found at given child path!";
    private static final String STATUS_DATABASE_NOT_FOUND = "DATA_NOT_FOUND";
    private static final String TAG = "FirebaseDataSource";
    private final MutableLiveData<DatabaseError> mError = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public final MutableLiveData<LoadingState> mLoadingState = new MutableLiveData<>();
    private Query mQuery;
    /* access modifiers changed from: private */
    public Runnable mRetryRunnable;

    /* renamed from: com.firebase.ui.database.paging.FirebaseDataSource$Factory */
    public static class Factory extends androidx.paging.DataSource.Factory<String, DataSnapshot> {
        private final Query mQuery;

        public Factory(Query query) {
            this.mQuery = query;
        }

        public DataSource<String, DataSnapshot> create() {
            return new FirebaseDataSource(this.mQuery);
        }
    }

    public void loadBefore(LoadParams<String> loadParams, LoadCallback<String, DataSnapshot> loadCallback) {
    }

    FirebaseDataSource(Query query) {
        this.mQuery = query;
    }

    public void loadInitial(final LoadInitialParams<String> loadInitialParams, final LoadInitialCallback<String, DataSnapshot> loadInitialCallback) {
        this.mLoadingState.postValue(LoadingState.LOADING_INITIAL);
        this.mQuery.limitToFirst(loadInitialParams.requestedLoadSize).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList arrayList = new ArrayList();
                    for (DataSnapshot add : dataSnapshot.getChildren()) {
                        arrayList.add(add);
                    }
                    String access$000 = FirebaseDataSource.this.getLastPageKey(arrayList);
                    FirebaseDataSource.this.mLoadingState.postValue(LoadingState.LOADED);
                    FirebaseDataSource.this.mRetryRunnable = null;
                    loadInitialCallback.onResult(arrayList, access$000, access$000);
                    return;
                }
                FirebaseDataSource firebaseDataSource = FirebaseDataSource.this;
                firebaseDataSource.mRetryRunnable = firebaseDataSource.getRetryLoadInitial(loadInitialParams, loadInitialCallback);
                FirebaseDataSource.this.setDatabaseNotFoundError();
            }

            public void onCancelled(DatabaseError databaseError) {
                FirebaseDataSource firebaseDataSource = FirebaseDataSource.this;
                firebaseDataSource.mRetryRunnable = firebaseDataSource.getRetryLoadInitial(loadInitialParams, loadInitialCallback);
                FirebaseDataSource.this.setError(databaseError);
            }
        });
    }

    public void loadAfter(final LoadParams<String> loadParams, final LoadCallback<String, DataSnapshot> loadCallback) {
        this.mLoadingState.postValue(LoadingState.LOADING_MORE);
        this.mQuery.startAt((String) null, (String) loadParams.key).limitToFirst(loadParams.requestedLoadSize + 1).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList arrayList = new ArrayList();
                    Iterator it = dataSnapshot.getChildren().iterator();
                    if (it.hasNext()) {
                        it.next();
                    }
                    while (it.hasNext()) {
                        arrayList.add((DataSnapshot) it.next());
                    }
                    FirebaseDataSource.this.mLoadingState.postValue(LoadingState.LOADED);
                    String str = null;
                    FirebaseDataSource.this.mRetryRunnable = null;
                    if (arrayList.isEmpty()) {
                        FirebaseDataSource.this.mLoadingState.postValue(LoadingState.FINISHED);
                    } else {
                        str = FirebaseDataSource.this.getLastPageKey(arrayList);
                    }
                    loadCallback.onResult(arrayList, str);
                    return;
                }
                FirebaseDataSource firebaseDataSource = FirebaseDataSource.this;
                firebaseDataSource.mRetryRunnable = firebaseDataSource.getRetryLoadAfter(loadParams, loadCallback);
                FirebaseDataSource.this.setDatabaseNotFoundError();
            }

            public void onCancelled(DatabaseError databaseError) {
                FirebaseDataSource firebaseDataSource = FirebaseDataSource.this;
                firebaseDataSource.mRetryRunnable = firebaseDataSource.getRetryLoadAfter(loadParams, loadCallback);
                FirebaseDataSource.this.setError(databaseError);
            }
        });
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadAfter(final LoadParams<String> loadParams, final LoadCallback<String, DataSnapshot> loadCallback) {
        return new Runnable() {
            public void run() {
                FirebaseDataSource.this.loadAfter(loadParams, loadCallback);
            }
        };
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadInitial(final LoadInitialParams<String> loadInitialParams, final LoadInitialCallback<String, DataSnapshot> loadInitialCallback) {
        return new Runnable() {
            public void run() {
                FirebaseDataSource.this.loadInitial(loadInitialParams, loadInitialCallback);
            }
        };
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

    /* access modifiers changed from: private */
    public String getLastPageKey(List<DataSnapshot> list) {
        if (list.isEmpty()) {
            return null;
        }
        return ((DataSnapshot) list.get(list.size() - 1)).getKey();
    }

    /* access modifiers changed from: private */
    public void setDatabaseNotFoundError() {
        StringBuilder sb = new StringBuilder();
        sb.append(DETAILS_DATABASE_NOT_FOUND);
        sb.append(this.mQuery.toString());
        this.mError.postValue(DatabaseError.fromStatus(STATUS_DATABASE_NOT_FOUND, MESSAGE_DATABASE_NOT_FOUND, sb.toString()));
        this.mLoadingState.postValue(LoadingState.ERROR);
    }

    /* access modifiers changed from: private */
    public void setError(DatabaseError databaseError) {
        this.mError.postValue(databaseError);
        this.mLoadingState.postValue(LoadingState.ERROR);
    }

    public LiveData<LoadingState> getLoadingState() {
        return this.mLoadingState;
    }

    public LiveData<DatabaseError> getLastError() {
        return this.mError;
    }
}
