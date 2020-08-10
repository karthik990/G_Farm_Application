package p043io.netty.buffer;

/* renamed from: io.netty.buffer.PoolChunkListMetric */
public interface PoolChunkListMetric extends Iterable<PoolChunkMetric> {
    int maxUsage();

    int minUsage();
}
