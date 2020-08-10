package p043io.netty.util.concurrent;

import java.lang.Thread.State;

/* renamed from: io.netty.util.concurrent.ThreadProperties */
public interface ThreadProperties {
    /* renamed from: id */
    long mo68685id();

    boolean isAlive();

    boolean isDaemon();

    boolean isInterrupted();

    String name();

    int priority();

    StackTraceElement[] stackTrace();

    State state();
}
