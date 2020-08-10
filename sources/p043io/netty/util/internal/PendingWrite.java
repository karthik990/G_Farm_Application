package p043io.netty.util.internal;

import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.util.internal.PendingWrite */
public final class PendingWrite {
    private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
        /* access modifiers changed from: protected */
        public PendingWrite newObject(Handle<PendingWrite> handle) {
            return new PendingWrite(handle);
        }
    };
    private final Handle<PendingWrite> handle;
    private Object msg;
    private Promise<Void> promise;

    public static PendingWrite newInstance(Object obj, Promise<Void> promise2) {
        PendingWrite pendingWrite = (PendingWrite) RECYCLER.get();
        pendingWrite.msg = obj;
        pendingWrite.promise = promise2;
        return pendingWrite;
    }

    private PendingWrite(Handle<PendingWrite> handle2) {
        this.handle = handle2;
    }

    public boolean recycle() {
        this.msg = null;
        this.promise = null;
        this.handle.recycle(this);
        return true;
    }

    public boolean failAndRecycle(Throwable th) {
        ReferenceCountUtil.release(this.msg);
        Promise<Void> promise2 = this.promise;
        if (promise2 != null) {
            promise2.setFailure(th);
        }
        return recycle();
    }

    public boolean successAndRecycle() {
        Promise<Void> promise2 = this.promise;
        if (promise2 != null) {
            promise2.setSuccess(null);
        }
        return recycle();
    }

    public Object msg() {
        return this.msg;
    }

    public Promise<Void> promise() {
        return this.promise;
    }

    public Promise<Void> recycleAndGet() {
        Promise<Void> promise2 = this.promise;
        recycle();
        return promise2;
    }
}
