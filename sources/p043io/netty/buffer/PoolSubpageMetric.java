package p043io.netty.buffer;

/* renamed from: io.netty.buffer.PoolSubpageMetric */
public interface PoolSubpageMetric {
    int elementSize();

    int maxNumElements();

    int numAvailable();

    int pageSize();
}
