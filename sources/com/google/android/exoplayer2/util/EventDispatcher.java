package com.google.android.exoplayer2.util;

import android.os.Handler;
import com.google.android.exoplayer2.util.EventDispatcher.Event;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventDispatcher<T> {
    private final CopyOnWriteArrayList<HandlerAndListener<T>> listeners = new CopyOnWriteArrayList<>();

    public interface Event<T> {
        void sendTo(T t);
    }

    private static final class HandlerAndListener<T> {
        private final Handler handler;
        /* access modifiers changed from: private */
        public final T listener;
        private boolean released;

        public HandlerAndListener(Handler handler2, T t) {
            this.handler = handler2;
            this.listener = t;
        }

        public void release() {
            this.released = true;
        }

        public void dispatch(Event<T> event) {
            this.handler.post(new Runnable(event) {
                private final /* synthetic */ Event f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    HandlerAndListener.this.lambda$dispatch$0$EventDispatcher$HandlerAndListener(this.f$1);
                }
            });
        }

        public /* synthetic */ void lambda$dispatch$0$EventDispatcher$HandlerAndListener(Event event) {
            if (!this.released) {
                event.sendTo(this.listener);
            }
        }
    }

    public void addListener(Handler handler, T t) {
        Assertions.checkArgument((handler == null || t == null) ? false : true);
        removeListener(t);
        this.listeners.add(new HandlerAndListener(handler, t));
    }

    public void removeListener(T t) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            HandlerAndListener handlerAndListener = (HandlerAndListener) it.next();
            if (handlerAndListener.listener == t) {
                handlerAndListener.release();
                this.listeners.remove(handlerAndListener);
            }
        }
    }

    public void dispatch(Event<T> event) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((HandlerAndListener) it.next()).dispatch(event);
        }
    }
}
