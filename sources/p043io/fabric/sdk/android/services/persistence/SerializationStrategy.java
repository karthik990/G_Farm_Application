package p043io.fabric.sdk.android.services.persistence;

/* renamed from: io.fabric.sdk.android.services.persistence.SerializationStrategy */
public interface SerializationStrategy<T> {
    T deserialize(String str);

    String serialize(T t);
}
