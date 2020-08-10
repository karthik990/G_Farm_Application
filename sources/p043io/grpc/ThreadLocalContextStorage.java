package p043io.grpc;

import java.util.logging.Level;
import java.util.logging.Logger;
import p043io.grpc.Context.Storage;

/* renamed from: io.grpc.ThreadLocalContextStorage */
final class ThreadLocalContextStorage extends Storage {
    static final ThreadLocal<Context> localContext = new ThreadLocal<>();
    private static final Logger log = Logger.getLogger(ThreadLocalContextStorage.class.getName());

    ThreadLocalContextStorage() {
    }

    public Context doAttach(Context context) {
        Context current = current();
        localContext.set(context);
        return current;
    }

    public void detach(Context context, Context context2) {
        if (current() != context) {
            log.log(Level.SEVERE, "Context was not attached when detaching", new Throwable().fillInStackTrace());
        }
        if (context2 != Context.ROOT) {
            localContext.set(context2);
        } else {
            localContext.set(null);
        }
    }

    public Context current() {
        Context context = (Context) localContext.get();
        return context == null ? Context.ROOT : context;
    }
}
