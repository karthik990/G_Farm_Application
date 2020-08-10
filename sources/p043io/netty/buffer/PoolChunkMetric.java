package p043io.netty.buffer;

/* renamed from: io.netty.buffer.PoolChunkMetric */
public interface PoolChunkMetric {
    int chunkSize();

    int freeBytes();

    int usage();
}
