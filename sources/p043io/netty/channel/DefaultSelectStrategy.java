package p043io.netty.channel;

import p043io.netty.util.IntSupplier;

/* renamed from: io.netty.channel.DefaultSelectStrategy */
final class DefaultSelectStrategy implements SelectStrategy {
    static final SelectStrategy INSTANCE = new DefaultSelectStrategy();

    private DefaultSelectStrategy() {
    }

    public int calculateStrategy(IntSupplier intSupplier, boolean z) throws Exception {
        if (z) {
            return intSupplier.get();
        }
        return -1;
    }
}
