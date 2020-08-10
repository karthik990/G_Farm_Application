package com.flurry.sdk;

import java.io.File;

/* renamed from: com.flurry.sdk.l */
public final class C1941l<T> {

    /* renamed from: a */
    private final File f1403a;

    /* renamed from: b */
    private final C1724ds<T> f1404b;

    public C1941l(File file, String str, int i, C1729dv<T> dvVar) {
        this.f1403a = file;
        this.f1404b = new C1720dq(new C1726du(str, i, dvVar));
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005c  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final T mo16527a() {
        /*
            r7 = this;
            java.io.File r0 = r7.f1403a
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r0 = r0.exists()
            java.lang.String r2 = "VersionedDataFile"
            if (r0 != 0) goto L_0x0027
            r0 = 5
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "No data to read for file:"
            r3.<init>(r4)
            java.io.File r4 = r7.f1403a
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.flurry.sdk.C1685cy.m754a(r0, r2, r3)
            return r1
        L_0x0027:
            r0 = 0
            r3 = 3
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            java.io.File r5 = r7.f1403a     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            r4.<init>(r5)     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            com.flurry.sdk.ds<T> r5 = r7.f1404b     // Catch:{ Exception -> 0x003a }
            java.lang.Object r1 = r5.mo16250a(r4)     // Catch:{ Exception -> 0x003a }
        L_0x0036:
            com.flurry.sdk.C1734dz.m871a(r4)
            goto L_0x005a
        L_0x003a:
            r0 = move-exception
            goto L_0x0041
        L_0x003c:
            r0 = move-exception
            r4 = r1
            goto L_0x007a
        L_0x003f:
            r0 = move-exception
            r4 = r1
        L_0x0041:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0079 }
            java.lang.String r6 = "Error reading data file:"
            r5.<init>(r6)     // Catch:{ all -> 0x0079 }
            java.io.File r6 = r7.f1403a     // Catch:{ all -> 0x0079 }
            java.lang.String r6 = r6.getName()     // Catch:{ all -> 0x0079 }
            r5.append(r6)     // Catch:{ all -> 0x0079 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0079 }
            com.flurry.sdk.C1685cy.m755a(r3, r2, r5, r0)     // Catch:{ all -> 0x0079 }
            r0 = 1
            goto L_0x0036
        L_0x005a:
            if (r0 == 0) goto L_0x0078
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r4 = "Deleting data file:"
            r0.<init>(r4)
            java.io.File r4 = r7.f1403a
            java.lang.String r4 = r4.getName()
            r0.append(r4)
            java.lang.String r0 = r0.toString()
            com.flurry.sdk.C1685cy.m754a(r3, r2, r0)
            java.io.File r0 = r7.f1403a
            r0.delete()
        L_0x0078:
            return r1
        L_0x0079:
            r0 = move-exception
        L_0x007a:
            com.flurry.sdk.C1734dz.m871a(r4)
            goto L_0x007f
        L_0x007e:
            throw r0
        L_0x007f:
            goto L_0x007e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1941l.mo16527a():java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo16528a(T r6) {
        /*
            r5 = this;
            java.lang.String r0 = "VersionedDataFile"
            r1 = 3
            r2 = 0
            java.io.File r3 = r5.f1403a     // Catch:{ Exception -> 0x002d }
            boolean r3 = com.flurry.sdk.C1732dy.m861a(r3)     // Catch:{ Exception -> 0x002d }
            if (r3 == 0) goto L_0x0023
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x002d }
            java.io.File r4 = r5.f1403a     // Catch:{ Exception -> 0x002d }
            r3.<init>(r4)     // Catch:{ Exception -> 0x002d }
            com.flurry.sdk.ds<T> r2 = r5.f1404b     // Catch:{ Exception -> 0x0020, all -> 0x001d }
            r2.mo16251a(r3, r6)     // Catch:{ Exception -> 0x0020, all -> 0x001d }
            com.flurry.sdk.C1734dz.m871a(r3)
            r6 = 0
            goto L_0x0049
        L_0x001d:
            r6 = move-exception
            r2 = r3
            goto L_0x0068
        L_0x0020:
            r6 = move-exception
            r2 = r3
            goto L_0x002e
        L_0x0023:
            java.io.IOException r6 = new java.io.IOException     // Catch:{ Exception -> 0x002d }
            java.lang.String r3 = "Cannot create parent directory!"
            r6.<init>(r3)     // Catch:{ Exception -> 0x002d }
            throw r6     // Catch:{ Exception -> 0x002d }
        L_0x002b:
            r6 = move-exception
            goto L_0x0068
        L_0x002d:
            r6 = move-exception
        L_0x002e:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            java.lang.String r4 = "Error writing data file:"
            r3.<init>(r4)     // Catch:{ all -> 0x002b }
            java.io.File r4 = r5.f1403a     // Catch:{ all -> 0x002b }
            java.lang.String r4 = r4.getName()     // Catch:{ all -> 0x002b }
            r3.append(r4)     // Catch:{ all -> 0x002b }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x002b }
            com.flurry.sdk.C1685cy.m755a(r1, r0, r3, r6)     // Catch:{ all -> 0x002b }
            r6 = 1
            com.flurry.sdk.C1734dz.m871a(r2)
        L_0x0049:
            if (r6 == 0) goto L_0x0067
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r2 = "Deleting data file:"
            r6.<init>(r2)
            java.io.File r2 = r5.f1403a
            java.lang.String r2 = r2.getName()
            r6.append(r2)
            java.lang.String r6 = r6.toString()
            com.flurry.sdk.C1685cy.m754a(r1, r0, r6)
            java.io.File r6 = r5.f1403a
            r6.delete()
        L_0x0067:
            return
        L_0x0068:
            com.flurry.sdk.C1734dz.m871a(r2)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1941l.mo16528a(java.lang.Object):void");
    }

    /* renamed from: b */
    public final boolean mo16529b() {
        File file = this.f1403a;
        if (file == null) {
            return false;
        }
        return file.delete();
    }
}
