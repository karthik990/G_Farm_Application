package p043io.reactivex.observables;

import p043io.reactivex.Observable;

/* renamed from: io.reactivex.observables.GroupedObservable */
public abstract class GroupedObservable<K, T> extends Observable<T> {
    final K key;

    protected GroupedObservable(K k) {
        this.key = k;
    }

    public K getKey() {
        return this.key;
    }
}
