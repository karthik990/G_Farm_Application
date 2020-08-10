package p043io.netty.util.internal.shaded.org.jctools.queues;

import p043io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.BaseLinkedQueueProducerNodeRef */
/* compiled from: BaseLinkedQueue */
abstract class BaseLinkedQueueProducerNodeRef<E> extends BaseLinkedQueuePad0<E> {
    protected static final long P_NODE_OFFSET;
    protected LinkedQueueNode<E> producerNode;

    BaseLinkedQueueProducerNodeRef() {
    }

    static {
        try {
            P_NODE_OFFSET = UnsafeAccess.UNSAFE.objectFieldOffset(BaseLinkedQueueProducerNodeRef.class.getDeclaredField("producerNode"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final void spProducerNode(LinkedQueueNode<E> linkedQueueNode) {
        this.producerNode = linkedQueueNode;
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueNode<E> lvProducerNode() {
        return (LinkedQueueNode) UnsafeAccess.UNSAFE.getObjectVolatile(this, P_NODE_OFFSET);
    }

    /* access modifiers changed from: protected */
    public final LinkedQueueNode<E> lpProducerNode() {
        return this.producerNode;
    }
}
