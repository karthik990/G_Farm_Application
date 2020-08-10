package p043io.netty.util;

/* renamed from: io.netty.util.ResourceLeakTracker */
public interface ResourceLeakTracker<T> {
    boolean close(T t);

    void record();

    void record(Object obj);
}
