package com.google.android.gms.tagmanager;

import android.os.Looper;
import com.google.android.gms.common.api.Status;

final class zzv implements ContainerHolder {
    private final Looper zzaek;
    private Container zzael;
    private Container zzaem;
    private Status zzaen;
    private zzx zzaeo;
    private zzw zzaep;
    private boolean zzaeq;
    private TagManager zzaer;

    public zzv(Status status) {
        this.zzaen = status;
        this.zzaek = null;
    }

    public zzv(TagManager tagManager, Looper looper, Container container, zzw zzw) {
        this.zzaer = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzaek = looper;
        this.zzael = container;
        this.zzaep = zzw;
        this.zzaen = Status.RESULT_SUCCESS;
        tagManager.zza(this);
    }

    public final Status getStatus() {
        return this.zzaen;
    }

    public final synchronized Container getContainer() {
        if (this.zzaeq) {
            zzdi.zzav("ContainerHolder is released.");
            return null;
        }
        if (this.zzaem != null) {
            this.zzael = this.zzaem;
            this.zzaem = null;
        }
        return this.zzael;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0024, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.zzaeq     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x000c
            java.lang.String r3 = "ContainerHolder is released."
            com.google.android.gms.tagmanager.zzdi.zzav(r3)     // Catch:{ all -> 0x0025 }
            monitor-exit(r2)
            return
        L_0x000c:
            if (r3 != 0) goto L_0x0013
            r3 = 0
            r2.zzaeo = r3     // Catch:{ all -> 0x0025 }
            monitor-exit(r2)
            return
        L_0x0013:
            com.google.android.gms.tagmanager.zzx r0 = new com.google.android.gms.tagmanager.zzx     // Catch:{ all -> 0x0025 }
            android.os.Looper r1 = r2.zzaek     // Catch:{ all -> 0x0025 }
            r0.<init>(r2, r3, r1)     // Catch:{ all -> 0x0025 }
            r2.zzaeo = r0     // Catch:{ all -> 0x0025 }
            com.google.android.gms.tagmanager.Container r3 = r2.zzaem     // Catch:{ all -> 0x0025 }
            if (r3 == 0) goto L_0x0023
            r2.zzhd()     // Catch:{ all -> 0x0025 }
        L_0x0023:
            monitor-exit(r2)
            return
        L_0x0025:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzv.setContainerAvailableListener(com.google.android.gms.tagmanager.ContainerHolder$ContainerAvailableListener):void");
    }

    public final synchronized void refresh() {
        if (this.zzaeq) {
            zzdi.zzav("Refreshing a released ContainerHolder.");
        } else {
            this.zzaep.zzhe();
        }
    }

    public final synchronized void release() {
        if (this.zzaeq) {
            zzdi.zzav("Releasing a released ContainerHolder.");
            return;
        }
        this.zzaeq = true;
        this.zzaer.zzb(this);
        this.zzael.release();
        this.zzael = null;
        this.zzaem = null;
        this.zzaep = null;
        this.zzaeo = null;
    }

    public final synchronized void zza(Container container) {
        if (!this.zzaeq) {
            this.zzaem = container;
            zzhd();
        }
    }

    public final synchronized void zzan(String str) {
        if (!this.zzaeq) {
            this.zzael.zzan(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String getContainerId() {
        if (!this.zzaeq) {
            return this.zzael.getContainerId();
        }
        zzdi.zzav("getContainerId called on a released ContainerHolder.");
        return "";
    }

    /* access modifiers changed from: 0000 */
    public final void zzao(String str) {
        if (this.zzaeq) {
            zzdi.zzav("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        } else {
            this.zzaep.zzao(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public final String zzhc() {
        if (!this.zzaeq) {
            return this.zzaep.zzhc();
        }
        zzdi.zzav("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
        return "";
    }

    private final void zzhd() {
        zzx zzx = this.zzaeo;
        if (zzx != null) {
            zzx.sendMessage(zzx.obtainMessage(1, this.zzaem.zzha()));
        }
    }
}
