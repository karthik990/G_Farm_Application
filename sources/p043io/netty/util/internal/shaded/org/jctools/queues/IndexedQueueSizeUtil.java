package p043io.netty.util.internal.shaded.org.jctools.queues;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil */
final class IndexedQueueSizeUtil {

    /* renamed from: io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil$IndexedQueue */
    protected interface IndexedQueue {
        long lvConsumerIndex();

        long lvProducerIndex();
    }

    private IndexedQueueSizeUtil() {
    }

    static int size(IndexedQueue indexedQueue) {
        long lvProducerIndex;
        long lvConsumerIndex;
        long lvConsumerIndex2 = indexedQueue.lvConsumerIndex();
        while (true) {
            lvProducerIndex = indexedQueue.lvProducerIndex();
            lvConsumerIndex = indexedQueue.lvConsumerIndex();
            if (lvConsumerIndex2 == lvConsumerIndex) {
                break;
            }
            lvConsumerIndex2 = lvConsumerIndex;
        }
        long j = lvProducerIndex - lvConsumerIndex;
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    static boolean isEmpty(IndexedQueue indexedQueue) {
        return indexedQueue.lvConsumerIndex() == indexedQueue.lvProducerIndex();
    }
}
