package p043io.netty.channel;

/* renamed from: io.netty.channel.MessageSizeEstimator */
public interface MessageSizeEstimator {

    /* renamed from: io.netty.channel.MessageSizeEstimator$Handle */
    public interface Handle {
        int size(Object obj);
    }

    Handle newHandle();
}
