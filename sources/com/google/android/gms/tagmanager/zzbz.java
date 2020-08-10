package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzpf;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

final class zzbz extends Thread implements zzby {
    private static zzbz zzahd;
    private volatile boolean closed = false;
    private final LinkedBlockingQueue<Runnable> zzahb = new LinkedBlockingQueue<>();
    private volatile boolean zzahc = false;
    /* access modifiers changed from: private */
    public volatile zzcb zzahe;
    /* access modifiers changed from: private */
    public final Context zzrm;

    static zzbz zzm(Context context) {
        if (zzahd == null) {
            zzahd = new zzbz(context);
        }
        return zzahd;
    }

    private zzbz(Context context) {
        super("GAThread");
        if (context != null) {
            this.zzrm = context.getApplicationContext();
        } else {
            this.zzrm = context;
        }
        start();
    }

    public final void zzbd(String str) {
        zzca zzca = new zzca(this, this, System.currentTimeMillis(), str);
        zzc(zzca);
    }

    public final void zzc(Runnable runnable) {
        this.zzahb.add(runnable);
    }

    public final void run() {
        while (true) {
            boolean z = this.closed;
            try {
                Runnable runnable = (Runnable) this.zzahb.take();
                if (!this.zzahc) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                try {
                    zzdi.zzaw(e.toString());
                } catch (Exception e2) {
                    String str = "Error on Google TagManager Thread: ";
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    PrintStream printStream = new PrintStream(byteArrayOutputStream);
                    zzpf.zza(e2, printStream);
                    printStream.flush();
                    String str2 = new String(byteArrayOutputStream.toByteArray());
                    zzdi.zzav(str2.length() != 0 ? str.concat(str2) : new String(str));
                    zzdi.zzav("Google TagManager is shutting down.");
                    this.zzahc = true;
                }
            }
        }
    }
}
