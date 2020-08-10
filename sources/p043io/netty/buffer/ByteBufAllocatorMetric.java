package p043io.netty.buffer;

/* renamed from: io.netty.buffer.ByteBufAllocatorMetric */
public interface ByteBufAllocatorMetric {
    long usedDirectMemory();

    long usedHeapMemory();
}
