package lib.android.paypal.com.magnessdk.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;
import lib.android.paypal.com.magnessdk.MagnesSDK;
import lib.android.paypal.com.magnessdk.p046b.C5988a;

/* renamed from: lib.android.paypal.com.magnessdk.network.h */
public final class C6004h extends Handler {

    /* renamed from: a */
    private static C6004h f4483a;

    /* renamed from: b */
    private WeakReference<MagnesSDK> f4484b;

    private C6004h(Looper looper, MagnesSDK magnesSDK) {
        super(looper);
        this.f4484b = new WeakReference<>(magnesSDK);
    }

    /* renamed from: a */
    public static synchronized C6004h m4112a(Looper looper, MagnesSDK magnesSDK) {
        C6004h hVar;
        synchronized (C6004h.class) {
            if (f4483a == null) {
                f4483a = new C6004h(looper, magnesSDK);
            }
            hVar = f4483a;
        }
        return hVar;
    }

    public void handleMessage(Message message) {
        String str;
        StringBuilder sb;
        Class cls;
        String str2;
        StringBuilder sb2;
        Class cls2;
        if (((MagnesSDK) this.f4484b.get()) != null) {
            int i = message.what;
            if (i != 0) {
                if (i == 1) {
                    cls2 = getClass();
                    sb2 = new StringBuilder();
                    str2 = "device info payload error. ";
                } else if (i != 2) {
                    switch (i) {
                        case 10:
                            cls = getClass();
                            sb = new StringBuilder();
                            str = "remote config started. fetching ";
                            break;
                        case 11:
                            cls2 = getClass();
                            sb2 = new StringBuilder();
                            str2 = "remote config error. ";
                            break;
                        case 12:
                            cls = getClass();
                            sb = new StringBuilder();
                            str = "remote config successed. ";
                            break;
                        default:
                            switch (i) {
                                case 20:
                                    cls = getClass();
                                    sb = new StringBuilder();
                                    str = "beacon started. ";
                                    break;
                                case 21:
                                    cls2 = getClass();
                                    sb2 = new StringBuilder();
                                    str2 = "beacon error. ";
                                    break;
                                case 22:
                                    cls = getClass();
                                    sb = new StringBuilder();
                                    str = "beacon successed. ";
                                    break;
                                default:
                                    return;
                            }
                    }
                } else {
                    cls = getClass();
                    sb = new StringBuilder();
                    str = "device info payload successed. ";
                }
                sb2.append(str2);
                sb2.append(message.obj);
                C5988a.m4031a(cls2, 3, sb2.toString());
                return;
            }
            cls = getClass();
            sb = new StringBuilder();
            str = "device info payload started. ";
            sb.append(str);
            sb.append(message.obj);
            C5988a.m4031a(cls, 0, sb.toString());
        }
    }
}
