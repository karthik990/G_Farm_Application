package lib.android.paypal.com.magnessdk.network;

import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

/* renamed from: lib.android.paypal.com.magnessdk.network.g */
final class C6003g {

    /* renamed from: a */
    private static final Object f4480a = new Object();

    /* renamed from: b */
    private static C6003g f4481b;

    /* renamed from: c */
    private ThreadPoolExecutor f4482c;

    private C6003g() {
        try {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(256), new DiscardPolicy());
            this.f4482c = threadPoolExecutor;
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    static C6003g m4110a() {
        C6003g gVar;
        synchronized (f4480a) {
            if (f4481b == null) {
                f4481b = new C6003g();
            }
            gVar = f4481b;
        }
        return gVar;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo72560a(C6002f fVar) {
        this.f4482c.execute(fVar);
    }
}
