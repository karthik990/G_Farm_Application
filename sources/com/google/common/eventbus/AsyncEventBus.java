package com.google.common.eventbus;

import java.util.concurrent.Executor;
import org.apache.http.client.config.CookieSpecs;

public class AsyncEventBus extends EventBus {
    public AsyncEventBus(String str, Executor executor) {
        super(str, executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(CookieSpecs.DEFAULT, executor, Dispatcher.legacyAsync(), subscriberExceptionHandler);
    }

    public AsyncEventBus(Executor executor) {
        super(CookieSpecs.DEFAULT, executor, Dispatcher.legacyAsync(), LoggingHandler.INSTANCE);
    }
}
