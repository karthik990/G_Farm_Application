package com.firebase.p037ui.common;

import com.firebase.p037ui.common.BaseChangeEventListener;
import java.util.AbstractList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.firebase.ui.common.BaseObservableSnapshotArray */
public abstract class BaseObservableSnapshotArray<S, E, L extends BaseChangeEventListener<S, E>, T> extends AbstractList<T> {
    private final BaseCachingSnapshotParser<S, T> mCachingParser;
    private boolean mHasDataChanged = false;
    private final List<L> mListeners = new CopyOnWriteArrayList();

    /* access modifiers changed from: protected */
    public abstract List<S> getSnapshots();

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    public BaseObservableSnapshotArray(BaseCachingSnapshotParser<S, T> baseCachingSnapshotParser) {
        this.mCachingParser = (BaseCachingSnapshotParser) Preconditions.checkNotNull(baseCachingSnapshotParser);
    }

    public T get(int i) {
        return this.mCachingParser.parseSnapshot(getSnapshot(i));
    }

    public int size() {
        return getSnapshots().size();
    }

    public S getSnapshot(int i) {
        return getSnapshots().get(i);
    }

    public L addChangeEventListener(L l) {
        Preconditions.checkNotNull(l);
        boolean isListening = isListening();
        this.mListeners.add(l);
        for (int i = 0; i < size(); i++) {
            l.onChildChanged(ChangeEventType.ADDED, getSnapshot(i), i, -1);
        }
        if (this.mHasDataChanged) {
            l.onDataChanged();
        }
        if (!isListening) {
            onCreate();
        }
        return l;
    }

    public void removeChangeEventListener(L l) {
        Preconditions.checkNotNull(l);
        boolean isListening = isListening();
        this.mListeners.remove(l);
        if (!isListening() && isListening) {
            onDestroy();
        }
    }

    public void removeAllListeners() {
        for (L removeChangeEventListener : this.mListeners) {
            removeChangeEventListener(removeChangeEventListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mHasDataChanged = false;
        getSnapshots().clear();
        this.mCachingParser.clear();
    }

    public boolean isListening() {
        return !this.mListeners.isEmpty();
    }

    public boolean isListening(L l) {
        return this.mListeners.contains(l);
    }

    /* access modifiers changed from: protected */
    public final void notifyOnChildChanged(ChangeEventType changeEventType, S s, int i, int i2) {
        if (changeEventType == ChangeEventType.CHANGED || changeEventType == ChangeEventType.REMOVED) {
            this.mCachingParser.invalidate(s);
        }
        for (L onChildChanged : this.mListeners) {
            onChildChanged.onChildChanged(changeEventType, s, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnDataChanged() {
        this.mHasDataChanged = true;
        for (L onDataChanged : this.mListeners) {
            onDataChanged.onDataChanged();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnError(E e) {
        for (L onError : this.mListeners) {
            onError.onError(e);
        }
    }
}
