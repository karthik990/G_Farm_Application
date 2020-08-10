package p043io.netty.channel;

/* renamed from: io.netty.channel.MaxMessagesRecvByteBufAllocator */
public interface MaxMessagesRecvByteBufAllocator extends RecvByteBufAllocator {
    int maxMessagesPerRead();

    MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int i);
}
