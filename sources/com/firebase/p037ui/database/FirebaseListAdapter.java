package com.firebase.p037ui.database;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import com.firebase.p037ui.common.ChangeEventType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

/* renamed from: com.firebase.ui.database.FirebaseListAdapter */
public abstract class FirebaseListAdapter<T> extends BaseAdapter implements FirebaseAdapter<T> {
    private static final String TAG = "FirebaseListAdapter";
    protected final int mLayout;
    private final ObservableSnapshotArray<T> mSnapshots;

    public void onDataChanged() {
    }

    /* access modifiers changed from: protected */
    public abstract void populateView(View view, T t, int i);

    public FirebaseListAdapter(FirebaseListOptions<T> firebaseListOptions) {
        this.mSnapshots = firebaseListOptions.getSnapshots();
        this.mLayout = firebaseListOptions.getLayout();
        if (firebaseListOptions.getOwner() != null) {
            firebaseListOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Event.ON_START)
    public void startListening() {
        if (!this.mSnapshots.isListening(this)) {
            this.mSnapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void stopListening() {
        this.mSnapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
    }

    /* access modifiers changed from: 0000 */
    @OnLifecycleEvent(Event.ON_DESTROY)
    public void cleanup(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().removeObserver(this);
    }

    public void onChildChanged(ChangeEventType changeEventType, DataSnapshot dataSnapshot, int i, int i2) {
        notifyDataSetChanged();
    }

    public void onError(DatabaseError databaseError) {
        Log.w(TAG, databaseError.toException());
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public T getItem(int i) {
        return this.mSnapshots.get(i);
    }

    public DatabaseReference getRef(int i) {
        return ((DataSnapshot) this.mSnapshots.getSnapshot(i)).getRef();
    }

    public int getCount() {
        return this.mSnapshots.size();
    }

    public long getItemId(int i) {
        return (long) ((DataSnapshot) this.mSnapshots.getSnapshot(i)).getKey().hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(this.mLayout, viewGroup, false);
        }
        populateView(view, getItem(i), i);
        return view;
    }
}
