package p043io.netty.channel;

import p043io.netty.util.IntSupplier;

/* renamed from: io.netty.channel.SelectStrategy */
public interface SelectStrategy {
    public static final int CONTINUE = -2;
    public static final int SELECT = -1;

    int calculateStrategy(IntSupplier intSupplier, boolean z) throws Exception;
}
