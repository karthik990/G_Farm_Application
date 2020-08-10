package p043io.netty.channel.local;

import java.util.concurrent.ThreadFactory;
import p043io.netty.channel.DefaultEventLoopGroup;

@Deprecated
/* renamed from: io.netty.channel.local.LocalEventLoopGroup */
public class LocalEventLoopGroup extends DefaultEventLoopGroup {
    public LocalEventLoopGroup() {
    }

    public LocalEventLoopGroup(int i) {
        super(i);
    }

    public LocalEventLoopGroup(int i, ThreadFactory threadFactory) {
        super(i, threadFactory);
    }
}
