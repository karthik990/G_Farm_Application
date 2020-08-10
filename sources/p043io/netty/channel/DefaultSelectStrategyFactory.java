package p043io.netty.channel;

/* renamed from: io.netty.channel.DefaultSelectStrategyFactory */
public final class DefaultSelectStrategyFactory implements SelectStrategyFactory {
    public static final SelectStrategyFactory INSTANCE = new DefaultSelectStrategyFactory();

    private DefaultSelectStrategyFactory() {
    }

    public SelectStrategy newSelectStrategy() {
        return DefaultSelectStrategy.INSTANCE;
    }
}
