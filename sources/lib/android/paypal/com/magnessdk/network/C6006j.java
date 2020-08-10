package lib.android.paypal.com.magnessdk.network;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import lib.android.paypal.com.magnessdk.p046b.C5988a;

/* renamed from: lib.android.paypal.com.magnessdk.network.j */
public final class C6006j extends C6002f {

    /* renamed from: b */
    private Context f4500b;

    /* renamed from: c */
    private Handler f4501c;

    public C6006j(Context context, Handler handler) {
        this.f4500b = context;
        this.f4501c = handler;
    }

    public void run() {
        C5988a.m4031a(getClass(), 0, "entering LoadRemoteConfigRequest.");
        Handler handler = this.f4501c;
        if (handler != null) {
            try {
                handler.sendMessage(Message.obtain(handler, 10, "https://www.paypalobjects.com/digitalassets/c/rda-magnes/magnes_config_android_v4.json"));
                this.f4501c.sendMessage(Message.obtain(this.f4501c, 12, new C6005i(this.f4500b)));
            } catch (Exception e) {
                C5988a.m4032a(getClass(), 3, (Throwable) e);
                Handler handler2 = this.f4501c;
                handler2.sendMessage(Message.obtain(handler2, 11, e));
            }
            C5988a.m4031a(getClass(), 0, "leaving LoadRemoteConfigRequest.");
        }
    }
}
