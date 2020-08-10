package p043io.netty.handler.flow;

import java.util.ArrayDeque;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.flow.FlowControlHandler */
public class FlowControlHandler extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(FlowControlHandler.class);
    private ChannelConfig config;
    private RecyclableArrayDeque queue;
    private final boolean releaseMessages;
    private boolean shouldConsume;

    /* renamed from: io.netty.handler.flow.FlowControlHandler$RecyclableArrayDeque */
    private static final class RecyclableArrayDeque extends ArrayDeque<Object> {
        private static final int DEFAULT_NUM_ELEMENTS = 2;
        private static final Recycler<RecyclableArrayDeque> RECYCLER = new Recycler<RecyclableArrayDeque>() {
            /* access modifiers changed from: protected */
            public RecyclableArrayDeque newObject(Handle<RecyclableArrayDeque> handle) {
                return new RecyclableArrayDeque(2, handle);
            }
        };
        private static final long serialVersionUID = 0;
        private final Handle<RecyclableArrayDeque> handle;

        public static RecyclableArrayDeque newInstance() {
            return (RecyclableArrayDeque) RECYCLER.get();
        }

        private RecyclableArrayDeque(int i, Handle<RecyclableArrayDeque> handle2) {
            super(i);
            this.handle = handle2;
        }

        public void recycle() {
            clear();
            this.handle.recycle(this);
        }
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public FlowControlHandler() {
        this(true);
    }

    public FlowControlHandler(boolean z) {
        this.releaseMessages = z;
    }

    /* access modifiers changed from: 0000 */
    public boolean isQueueEmpty() {
        return this.queue.isEmpty();
    }

    private void destroy() {
        RecyclableArrayDeque recyclableArrayDeque = this.queue;
        if (recyclableArrayDeque != null) {
            if (!recyclableArrayDeque.isEmpty()) {
                logger.trace("Non-empty queue: {}", (Object) this.queue);
                if (this.releaseMessages) {
                    while (true) {
                        Object poll = this.queue.poll();
                        if (poll == null) {
                            break;
                        }
                        ReferenceCountUtil.safeRelease(poll);
                    }
                }
            }
            this.queue.recycle();
            this.queue = null;
        }
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.config = channelHandlerContext.channel().config();
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        destroy();
        channelHandlerContext.fireChannelInactive();
    }

    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (dequeue(channelHandlerContext, 1) == 0) {
            this.shouldConsume = true;
            channelHandlerContext.read();
        }
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.queue == null) {
            this.queue = RecyclableArrayDeque.newInstance();
        }
        this.queue.offer(obj);
        boolean z = this.shouldConsume;
        this.shouldConsume = false;
        dequeue(channelHandlerContext, z ? 1 : 0);
    }

    private int dequeue(ChannelHandlerContext channelHandlerContext, int i) {
        int i2 = 0;
        if (this.queue == null) {
            return 0;
        }
        while (true) {
            if (i2 >= i && !this.config.isAutoRead()) {
                break;
            }
            Object poll = this.queue.poll();
            if (poll == null) {
                break;
            }
            i2++;
            channelHandlerContext.fireChannelRead(poll);
        }
        if (this.queue.isEmpty() && i2 > 0) {
            channelHandlerContext.fireChannelReadComplete();
        }
        return i2;
    }
}
