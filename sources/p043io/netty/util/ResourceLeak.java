package p043io.netty.util;

@Deprecated
/* renamed from: io.netty.util.ResourceLeak */
public interface ResourceLeak {
    boolean close();

    void record();

    void record(Object obj);
}
