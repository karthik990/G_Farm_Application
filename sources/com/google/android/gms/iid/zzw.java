package com.google.android.gms.iid;

final /* synthetic */ class zzw implements Runnable {
    private final zzt zzch;

    zzw(zzt zzt) {
        this.zzch = zzt;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0040, code lost:
        if (android.util.Log.isLoggable("MessengerIpcClient", 3) == false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0042, code lost:
        r3 = java.lang.String.valueOf(r1);
        r5 = new java.lang.StringBuilder(java.lang.String.valueOf(r3).length() + 8);
        r5.append("Sending ");
        r5.append(r3);
        android.util.Log.d("MessengerIpcClient", r5.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0066, code lost:
        r3 = r0.zzcg.zzk;
        r4 = r0.zzcc;
        r5 = android.os.Message.obtain();
        r5.what = r1.what;
        r5.arg1 = r1.zzck;
        r5.replyTo = r4;
        r4 = new android.os.Bundle();
        r4.putBoolean("oneWay", r1.zzu());
        r4.putString("pkg", r3.getPackageName());
        r4.putBundle("data", r1.zzcm);
        r5.setData(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1 = r0.zzcd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a1, code lost:
        if (r1.zzab == null) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a3, code lost:
        r1.zzab.send(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ac, code lost:
        if (r1.zzcj == null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ae, code lost:
        r1.zzcj.send(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bc, code lost:
        throw new java.lang.IllegalStateException("Both messengers are null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00bd, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00be, code lost:
        r0.zzd(2, r1.getMessage());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            com.google.android.gms.iid.zzt r0 = r8.zzch
        L_0x0002:
            monitor-enter(r0)
            int r1 = r0.state     // Catch:{ all -> 0x00c7 }
            r2 = 2
            if (r1 == r2) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
            return
        L_0x000a:
            java.util.Queue<com.google.android.gms.iid.zzz<?>> r1 = r0.zzce     // Catch:{ all -> 0x00c7 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x00c7 }
            if (r1 == 0) goto L_0x0017
            r0.zzs()     // Catch:{ all -> 0x00c7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
            return
        L_0x0017:
            java.util.Queue<com.google.android.gms.iid.zzz<?>> r1 = r0.zzce     // Catch:{ all -> 0x00c7 }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x00c7 }
            com.google.android.gms.iid.zzz r1 = (com.google.android.gms.iid.zzz) r1     // Catch:{ all -> 0x00c7 }
            android.util.SparseArray<com.google.android.gms.iid.zzz<?>> r3 = r0.zzcf     // Catch:{ all -> 0x00c7 }
            int r4 = r1.zzck     // Catch:{ all -> 0x00c7 }
            r3.put(r4, r1)     // Catch:{ all -> 0x00c7 }
            com.google.android.gms.iid.zzr r3 = r0.zzcg     // Catch:{ all -> 0x00c7 }
            java.util.concurrent.ScheduledExecutorService r3 = r3.zzbz     // Catch:{ all -> 0x00c7 }
            com.google.android.gms.iid.zzx r4 = new com.google.android.gms.iid.zzx     // Catch:{ all -> 0x00c7 }
            r4.<init>(r0, r1)     // Catch:{ all -> 0x00c7 }
            r5 = 30
            java.util.concurrent.TimeUnit r7 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x00c7 }
            r3.schedule(r4, r5, r7)     // Catch:{ all -> 0x00c7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
            r3 = 3
            java.lang.String r4 = "MessengerIpcClient"
            boolean r3 = android.util.Log.isLoggable(r4, r3)
            if (r3 == 0) goto L_0x0066
            java.lang.String r3 = java.lang.String.valueOf(r1)
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            int r4 = r4 + 8
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Sending "
            r5.append(r4)
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            java.lang.String r4 = "MessengerIpcClient"
            android.util.Log.d(r4, r3)
        L_0x0066:
            com.google.android.gms.iid.zzr r3 = r0.zzcg
            android.content.Context r3 = r3.zzk
            android.os.Messenger r4 = r0.zzcc
            android.os.Message r5 = android.os.Message.obtain()
            int r6 = r1.what
            r5.what = r6
            int r6 = r1.zzck
            r5.arg1 = r6
            r5.replyTo = r4
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            boolean r6 = r1.zzu()
            java.lang.String r7 = "oneWay"
            r4.putBoolean(r7, r6)
            java.lang.String r3 = r3.getPackageName()
            java.lang.String r6 = "pkg"
            r4.putString(r6, r3)
            android.os.Bundle r1 = r1.zzcm
            java.lang.String r3 = "data"
            r4.putBundle(r3, r1)
            r5.setData(r4)
            com.google.android.gms.iid.zzy r1 = r0.zzcd     // Catch:{ RemoteException -> 0x00bd }
            android.os.Messenger r3 = r1.zzab     // Catch:{ RemoteException -> 0x00bd }
            if (r3 == 0) goto L_0x00aa
            android.os.Messenger r1 = r1.zzab     // Catch:{ RemoteException -> 0x00bd }
            r1.send(r5)     // Catch:{ RemoteException -> 0x00bd }
            goto L_0x0002
        L_0x00aa:
            com.google.android.gms.iid.MessengerCompat r3 = r1.zzcj     // Catch:{ RemoteException -> 0x00bd }
            if (r3 == 0) goto L_0x00b5
            com.google.android.gms.iid.MessengerCompat r1 = r1.zzcj     // Catch:{ RemoteException -> 0x00bd }
            r1.send(r5)     // Catch:{ RemoteException -> 0x00bd }
            goto L_0x0002
        L_0x00b5:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ RemoteException -> 0x00bd }
            java.lang.String r3 = "Both messengers are null"
            r1.<init>(r3)     // Catch:{ RemoteException -> 0x00bd }
            throw r1     // Catch:{ RemoteException -> 0x00bd }
        L_0x00bd:
            r1 = move-exception
            java.lang.String r1 = r1.getMessage()
            r0.zzd(r2, r1)
            goto L_0x0002
        L_0x00c7:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
            goto L_0x00cb
        L_0x00ca:
            throw r1
        L_0x00cb:
            goto L_0x00ca
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzw.run():void");
    }
}
