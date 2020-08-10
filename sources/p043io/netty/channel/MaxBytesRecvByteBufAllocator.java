package p043io.netty.channel;

import java.util.Map.Entry;

/* renamed from: io.netty.channel.MaxBytesRecvByteBufAllocator */
public interface MaxBytesRecvByteBufAllocator extends RecvByteBufAllocator {
    int maxBytesPerIndividualRead();

    MaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int i);

    int maxBytesPerRead();

    MaxBytesRecvByteBufAllocator maxBytesPerRead(int i);

    MaxBytesRecvByteBufAllocator maxBytesPerReadPair(int i, int i2);

    Entry<Integer, Integer> maxBytesPerReadPair();
}
