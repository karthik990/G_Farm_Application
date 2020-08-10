package p043io.reactivex.schedulers;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import p043io.reactivex.Scheduler;
import p043io.reactivex.internal.schedulers.ComputationScheduler;
import p043io.reactivex.internal.schedulers.ExecutorScheduler;
import p043io.reactivex.internal.schedulers.IoScheduler;
import p043io.reactivex.internal.schedulers.NewThreadScheduler;
import p043io.reactivex.internal.schedulers.SchedulerPoolFactory;
import p043io.reactivex.internal.schedulers.SingleScheduler;
import p043io.reactivex.internal.schedulers.TrampolineScheduler;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.schedulers.Schedulers */
public final class Schedulers {
    static final Scheduler COMPUTATION = RxJavaPlugins.initComputationScheduler(new ComputationTask());

    /* renamed from: IO */
    static final Scheduler f4188IO = RxJavaPlugins.initIoScheduler(new IOTask());
    static final Scheduler NEW_THREAD = RxJavaPlugins.initNewThreadScheduler(new NewThreadTask());
    static final Scheduler SINGLE = RxJavaPlugins.initSingleScheduler(new SingleTask());
    static final Scheduler TRAMPOLINE = TrampolineScheduler.instance();

    /* renamed from: io.reactivex.schedulers.Schedulers$ComputationHolder */
    static final class ComputationHolder {
        static final Scheduler DEFAULT = new ComputationScheduler();

        ComputationHolder() {
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$ComputationTask */
    static final class ComputationTask implements Callable<Scheduler> {
        ComputationTask() {
        }

        public Scheduler call() throws Exception {
            return ComputationHolder.DEFAULT;
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$IOTask */
    static final class IOTask implements Callable<Scheduler> {
        IOTask() {
        }

        public Scheduler call() throws Exception {
            return IoHolder.DEFAULT;
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$IoHolder */
    static final class IoHolder {
        static final Scheduler DEFAULT = new IoScheduler();

        IoHolder() {
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$NewThreadHolder */
    static final class NewThreadHolder {
        static final Scheduler DEFAULT = new NewThreadScheduler();

        NewThreadHolder() {
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$NewThreadTask */
    static final class NewThreadTask implements Callable<Scheduler> {
        NewThreadTask() {
        }

        public Scheduler call() throws Exception {
            return NewThreadHolder.DEFAULT;
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$SingleHolder */
    static final class SingleHolder {
        static final Scheduler DEFAULT = new SingleScheduler();

        SingleHolder() {
        }
    }

    /* renamed from: io.reactivex.schedulers.Schedulers$SingleTask */
    static final class SingleTask implements Callable<Scheduler> {
        SingleTask() {
        }

        public Scheduler call() throws Exception {
            return SingleHolder.DEFAULT;
        }
    }

    private Schedulers() {
        throw new IllegalStateException("No instances!");
    }

    public static Scheduler computation() {
        return RxJavaPlugins.onComputationScheduler(COMPUTATION);
    }

    /* renamed from: io */
    public static Scheduler m4010io() {
        return RxJavaPlugins.onIoScheduler(f4188IO);
    }

    public static Scheduler trampoline() {
        return TRAMPOLINE;
    }

    public static Scheduler newThread() {
        return RxJavaPlugins.onNewThreadScheduler(NEW_THREAD);
    }

    public static Scheduler single() {
        return RxJavaPlugins.onSingleScheduler(SINGLE);
    }

    public static Scheduler from(Executor executor) {
        return new ExecutorScheduler(executor);
    }

    public static void shutdown() {
        computation().shutdown();
        m4010io().shutdown();
        newThread().shutdown();
        single().shutdown();
        trampoline().shutdown();
        SchedulerPoolFactory.shutdown();
    }

    public static void start() {
        computation().start();
        m4010io().start();
        newThread().start();
        single().start();
        trampoline().start();
        SchedulerPoolFactory.start();
    }
}
