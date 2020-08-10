package p043io.fabric.sdk.android.services.persistence;

/* renamed from: io.fabric.sdk.android.services.persistence.PersistenceStrategy */
public interface PersistenceStrategy<T> {
    void clear();

    T restore();

    void save(T t);
}
