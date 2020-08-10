package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueueConsumerNodeRef */
/* compiled from: BaseLinkedQueue */
abstract class BaseLinkedQueueConsumerNodeRef<E> extends BaseLinkedQueuePad1<E> {
    protected static final long C_NODE_OFFSET;
    protected LinkedQueueNode<E> consumerNode;

    BaseLinkedQueueConsumerNodeRef() {
    }

    static {
        try {
            C_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueConsumerNodeRef.class.getDeclaredField("consumerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final void spConsumerNode(LinkedQueueNode<E> linkedQueueNode) {
        this.consumerNode = linkedQueueNode;
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueNode<E> lvConsumerNode() {
        return (LinkedQueueNode) UnsafeAccess.UNSAFE.getObjectVolatile(this, C_NODE_OFFSET);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueNode<E> lpConsumerNode() {
        return this.consumerNode;
    }
}
