package com.google.android.youtube.player.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.internal.C2777c.C2778a;
import com.google.android.youtube.player.internal.C2795i.C2796a;
import com.google.android.youtube.player.internal.C2827t.C2828a;
import com.google.android.youtube.player.internal.C2827t.C2829b;
import java.util.ArrayList;

/* renamed from: com.google.android.youtube.player.internal.r */
public abstract class C2815r<T extends IInterface> implements C2827t {

    /* renamed from: a */
    final Handler f1704a;

    /* renamed from: b */
    private final Context f1705b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public T f1706c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public ArrayList<C2828a> f1707d;

    /* renamed from: e */
    private final ArrayList<C2828a> f1708e = new ArrayList<>();

    /* renamed from: f */
    private boolean f1709f = false;

    /* renamed from: g */
    private ArrayList<C2829b> f1710g;

    /* renamed from: h */
    private boolean f1711h = false;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public final ArrayList<C2818b<?>> f1712i = new ArrayList<>();

    /* renamed from: j */
    private ServiceConnection f1713j;
    /* access modifiers changed from: private */

    /* renamed from: k */
    public boolean f1714k = false;

    /* renamed from: com.google.android.youtube.player.internal.r$1 */
    static /* synthetic */ class C28161 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1715a = new int[YouTubeInitializationResult.values().length];

        static {
            try {
                f1715a[YouTubeInitializationResult.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$a */
    final class C2817a extends Handler {
        C2817a() {
        }

        public final void handleMessage(Message message) {
            if (message.what == 3) {
                C2815r.this.mo38169a((YouTubeInitializationResult) message.obj);
            } else if (message.what == 4) {
                synchronized (C2815r.this.f1707d) {
                    if (C2815r.this.f1714k && C2815r.this.mo38172f() && C2815r.this.f1707d.contains(message.obj)) {
                        ((C2828a) message.obj).mo38023a();
                    }
                }
            } else if (message.what == 2 && !C2815r.this.mo38172f()) {
            } else {
                if (message.what == 2 || message.what == 1) {
                    ((C2818b) message.obj).mo38178a();
                }
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$b */
    protected abstract class C2818b<TListener> {

        /* renamed from: b */
        private TListener f1718b;

        public C2818b(TListener tlistener) {
            this.f1718b = tlistener;
            synchronized (C2815r.this.f1712i) {
                C2815r.this.f1712i.add(this);
            }
        }

        /* renamed from: a */
        public final void mo38178a() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.f1718b;
            }
            mo38179a(tlistener);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public abstract void mo38179a(TListener tlistener);

        /* renamed from: b */
        public final void mo38180b() {
            synchronized (this) {
                this.f1718b = null;
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$c */
    protected final class C2819c extends C2818b<Boolean> {

        /* renamed from: b */
        public final YouTubeInitializationResult f1719b;

        /* renamed from: c */
        public final IBinder f1720c;

        public C2819c(String str, IBinder iBinder) {
            super(Boolean.valueOf(true));
            this.f1719b = C2815r.m1681b(str);
            this.f1720c = iBinder;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public final /* synthetic */ void mo38179a(Object obj) {
            if (((Boolean) obj) != null) {
                if (C28161.f1715a[this.f1719b.ordinal()] != 1) {
                    C2815r.this.mo38169a(this.f1719b);
                } else {
                    try {
                        if (C2815r.this.mo38164b().equals(this.f1720c.getInterfaceDescriptor())) {
                            C2815r.this.f1706c = C2815r.this.mo38162a(this.f1720c);
                            if (C2815r.this.f1706c != null) {
                                C2815r.this.mo38173g();
                                return;
                            }
                        }
                    } catch (RemoteException unused) {
                    }
                    C2815r.this.mo38058a();
                    C2815r.this.mo38169a(YouTubeInitializationResult.INTERNAL_ERROR);
                }
            }
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$d */
    protected final class C2820d extends C2778a {
        protected C2820d() {
        }

        /* renamed from: a */
        public final void mo38061a(String str, IBinder iBinder) {
            C2815r.this.f1704a.sendMessage(C2815r.this.f1704a.obtainMessage(1, new C2819c(str, iBinder)));
        }
    }

    /* renamed from: com.google.android.youtube.player.internal.r$e */
    final class C2821e implements ServiceConnection {
        C2821e() {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            C2815r.this.mo38170b(iBinder);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            C2815r.this.f1706c = null;
            C2815r.this.mo38174h();
        }
    }

    protected C2815r(Context context, C2828a aVar, C2829b bVar) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            this.f1705b = (Context) C2774ab.m1495a(context);
            this.f1707d = new ArrayList<>();
            this.f1707d.add(C2774ab.m1495a(aVar));
            this.f1710g = new ArrayList<>();
            this.f1710g.add(C2774ab.m1495a(bVar));
            this.f1704a = new C2817a();
            return;
        }
        throw new IllegalStateException("Clients must be created on the UI thread.");
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void mo38058a() {
        ServiceConnection serviceConnection = this.f1713j;
        if (serviceConnection != null) {
            try {
                this.f1705b.unbindService(serviceConnection);
            } catch (IllegalArgumentException e) {
                Log.w("YouTubeClient", "Unexpected error from unbindService()", e);
            }
        }
        this.f1706c = null;
        this.f1713j = null;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static YouTubeInitializationResult m1681b(String str) {
        try {
            return YouTubeInitializationResult.valueOf(str);
        } catch (IllegalArgumentException unused) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        } catch (NullPointerException unused2) {
            return YouTubeInitializationResult.UNKNOWN_ERROR;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract T mo38162a(IBinder iBinder);

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo38169a(YouTubeInitializationResult youTubeInitializationResult) {
        this.f1704a.removeMessages(4);
        synchronized (this.f1710g) {
            this.f1711h = true;
            ArrayList<C2829b> arrayList = this.f1710g;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                if (this.f1714k) {
                    if (this.f1710g.contains(arrayList.get(i))) {
                        ((C2829b) arrayList.get(i)).mo38025a(youTubeInitializationResult);
                    }
                    i++;
                } else {
                    return;
                }
            }
            this.f1711h = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo38163a(C2795i iVar, C2820d dVar) throws RemoteException;

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract String mo38164b();

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo38170b(IBinder iBinder) {
        try {
            mo38163a(C2796a.m1625a(iBinder), new C2820d());
        } catch (RemoteException unused) {
            Log.w("YouTubeClient", "service died");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public abstract String mo38165c();

    /* renamed from: d */
    public void mo38166d() {
        mo38174h();
        this.f1714k = false;
        synchronized (this.f1712i) {
            int size = this.f1712i.size();
            for (int i = 0; i < size; i++) {
                ((C2818b) this.f1712i.get(i)).mo38180b();
            }
            this.f1712i.clear();
        }
        mo38058a();
    }

    /* renamed from: e */
    public final void mo38171e() {
        this.f1714k = true;
        YouTubeInitializationResult isYouTubeApiServiceAvailable = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this.f1705b);
        if (isYouTubeApiServiceAvailable != YouTubeInitializationResult.SUCCESS) {
            Handler handler = this.f1704a;
            handler.sendMessage(handler.obtainMessage(3, isYouTubeApiServiceAvailable));
            return;
        }
        Intent intent = new Intent(mo38165c()).setPackage(C2838z.m1750a(this.f1705b));
        if (this.f1713j != null) {
            Log.e("YouTubeClient", "Calling connect() while still connected, missing disconnect().");
            mo38058a();
        }
        this.f1713j = new C2821e();
        if (!this.f1705b.bindService(intent, this.f1713j, 129)) {
            Handler handler2 = this.f1704a;
            handler2.sendMessage(handler2.obtainMessage(3, YouTubeInitializationResult.ERROR_CONNECTING_TO_SERVICE));
        }
    }

    /* renamed from: f */
    public final boolean mo38172f() {
        return this.f1706c != null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public final void mo38173g() {
        synchronized (this.f1707d) {
            boolean z = true;
            C2774ab.m1498a(!this.f1709f);
            this.f1704a.removeMessages(4);
            this.f1709f = true;
            if (this.f1708e.size() != 0) {
                z = false;
            }
            C2774ab.m1498a(z);
            ArrayList<C2828a> arrayList = this.f1707d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.f1714k && mo38172f(); i++) {
                if (!this.f1708e.contains(arrayList.get(i))) {
                    ((C2828a) arrayList.get(i)).mo38023a();
                }
            }
            this.f1708e.clear();
            this.f1709f = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public final void mo38174h() {
        this.f1704a.removeMessages(4);
        synchronized (this.f1707d) {
            this.f1709f = true;
            ArrayList<C2828a> arrayList = this.f1707d;
            int size = arrayList.size();
            for (int i = 0; i < size && this.f1714k; i++) {
                if (this.f1707d.contains(arrayList.get(i))) {
                    ((C2828a) arrayList.get(i)).mo38024b();
                }
            }
            this.f1709f = false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public final void mo38175i() {
        if (!mo38172f()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: j */
    public final T mo38176j() {
        mo38175i();
        return this.f1706c;
    }
}
