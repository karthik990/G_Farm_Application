package p043io.netty.util.concurrent;

/* renamed from: io.netty.util.concurrent.EventExecutorChooserFactory */
public interface EventExecutorChooserFactory {

    /* renamed from: io.netty.util.concurrent.EventExecutorChooserFactory$EventExecutorChooser */
    public interface EventExecutorChooser {
        EventExecutor next();
    }

    EventExecutorChooser newChooser(EventExecutor[] eventExecutorArr);
}
