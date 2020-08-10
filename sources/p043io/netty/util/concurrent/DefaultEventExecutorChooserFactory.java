package p043io.netty.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.concurrent.EventExecutorChooserFactory.EventExecutorChooser;

/* renamed from: io.netty.util.concurrent.DefaultEventExecutorChooserFactory */
public final class DefaultEventExecutorChooserFactory implements EventExecutorChooserFactory {
    public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();

    /* renamed from: io.netty.util.concurrent.DefaultEventExecutorChooserFactory$GenericEventExecutorChooser */
    private static final class GenericEventExecutorChooser implements EventExecutorChooser {
        private final EventExecutor[] executors;
        private final AtomicInteger idx = new AtomicInteger();

        GenericEventExecutorChooser(EventExecutor[] eventExecutorArr) {
            this.executors = eventExecutorArr;
        }

        public EventExecutor next() {
            return this.executors[Math.abs(this.idx.getAndIncrement() % this.executors.length)];
        }
    }

    /* renamed from: io.netty.util.concurrent.DefaultEventExecutorChooserFactory$PowerOfTwoEventExecutorChooser */
    private static final class PowerOfTwoEventExecutorChooser implements EventExecutorChooser {
        private final EventExecutor[] executors;
        private final AtomicInteger idx = new AtomicInteger();

        PowerOfTwoEventExecutorChooser(EventExecutor[] eventExecutorArr) {
            this.executors = eventExecutorArr;
        }

        public EventExecutor next() {
            return this.executors[this.idx.getAndIncrement() & (this.executors.length - 1)];
        }
    }

    private static boolean isPowerOfTwo(int i) {
        return ((-i) & i) == i;
    }

    private DefaultEventExecutorChooserFactory() {
    }

    public EventExecutorChooser newChooser(EventExecutor[] eventExecutorArr) {
        if (isPowerOfTwo(eventExecutorArr.length)) {
            return new PowerOfTwoEventExecutorChooser(eventExecutorArr);
        }
        return new GenericEventExecutorChooser(eventExecutorArr);
    }
}
