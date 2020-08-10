package p043io.netty.util.internal.shaded.org.jctools.queues;

/* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue */
public interface MessagePassingQueue<T> {
    public static final int UNBOUNDED_CAPACITY = -1;

    /* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Consumer */
    public interface Consumer<T> {
        void accept(T t);
    }

    /* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$ExitCondition */
    public interface ExitCondition {
        boolean keepRunning();
    }

    /* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$Supplier */
    public interface Supplier<T> {
        T get();
    }

    /* renamed from: io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue$WaitStrategy */
    public interface WaitStrategy {
        int idle(int i);
    }

    int capacity();

    void clear();

    int drain(Consumer<T> consumer);

    int drain(Consumer<T> consumer, int i);

    void drain(Consumer<T> consumer, WaitStrategy waitStrategy, ExitCondition exitCondition);

    int fill(Supplier<T> supplier);

    int fill(Supplier<T> supplier, int i);

    void fill(Supplier<T> supplier, WaitStrategy waitStrategy, ExitCondition exitCondition);

    boolean isEmpty();

    boolean offer(T t);

    T peek();

    T poll();

    boolean relaxedOffer(T t);

    T relaxedPeek();

    T relaxedPoll();

    int size();
}
