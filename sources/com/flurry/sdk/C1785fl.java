package com.flurry.sdk;

import com.flurry.sdk.C1788fm.C1789a;
import com.flurry.sdk.C1798fr.C1803b;
import com.flurry.sdk.C1824gc.C1825a;
import com.mobiroller.constants.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/* renamed from: com.flurry.sdk.fl */
public final class C1785fl extends C1798fr {

    /* renamed from: a */
    protected C1824gc f1066a;

    /* renamed from: b */
    protected C1933js f1067b;

    C1785fl() {
        super("FileWriterModule", null);
        this.f1066a = null;
        this.f1067b = null;
        this.f1066a = new C1820ga();
        this.f1067b = new C1933js();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public boolean m962a(String str) {
        if (this.f1066a.mo16500b()) {
            C1685cy.m754a(6, "FileWriterModule", "File was open, closing now.");
            this.f1066a.mo16243a();
        }
        return this.f1066a.mo16499a(C1776ff.m946d(), str);
    }

    /* renamed from: a */
    public final void mo16471a(final C1930jp jpVar) {
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                String str = "FileWriterModule";
                if (C1785fl.this.f1094d == C1803b.f1103c) {
                    C1785fl.this.f1095e.add(jpVar);
                    StringBuilder sb = new StringBuilder("In paused state, cannot process message now. ");
                    sb.append(jpVar.mo16501a());
                    C1685cy.m754a(4, str, sb.toString());
                    return;
                }
                if (!C1785fl.this.f1066a.mo16500b()) {
                    if (C1785fl.this.m962a("currentFile")) {
                        C1685cy.m754a(4, str, "File created. Adding counter");
                        C1785fl.this.f1066a.mo16498a((C1930jp) C1902ip.m1151b(), (C1825a) null);
                    } else {
                        C1685cy.m754a(4, str, "File creation failed.");
                    }
                }
                if (jpVar.mo16501a().equals(C1928jn.FLUSH_FRAME)) {
                    C1785fl.this.f1094d = C1803b.f1103c;
                    StringBuilder sb2 = new StringBuilder("Adding flush frame:");
                    sb2.append(jpVar.mo16518e());
                    C1685cy.m754a(4, str, sb2.toString());
                    C1785fl.this.f1066a.mo16498a(jpVar, (C1825a) new C1825a() {
                        /* renamed from: a */
                        public final void mo16473a() {
                            C1785fl.this.f1094d = C1803b.f1103c;
                            C1785fl.this.f1066a.mo16243a();
                            C1785fl.this.m963e();
                            C1785fl.this.mo16486d();
                            C1785fl.this.f1094d = C1803b.f1104d;
                        }
                    });
                    return;
                }
                StringBuilder sb3 = new StringBuilder("Adding frame ");
                sb3.append(jpVar.mo16501a());
                sb3.append(": ");
                sb3.append(jpVar.mo16518e());
                C1685cy.m754a(4, str, sb3.toString());
                C1785fl.this.f1066a.mo16498a(jpVar, (C1825a) null);
            }
        });
    }

    /* renamed from: b */
    public final C1789a mo16472b(C1930jp jpVar) {
        C1820ga gaVar = new C1820ga();
        if (gaVar.mo16499a(C1776ff.m946d(), "crashFile")) {
            gaVar.mo16497a(jpVar);
            gaVar.mo16243a();
        } else {
            C1685cy.m754a(4, "FileWriterModule", "Can't create crash file. Cannot write crash frame to disc");
        }
        return C1789a.QUEUED;
    }

    /* access modifiers changed from: private */
    /* renamed from: e */
    public void m963e() {
        if (this.f1066a.mo16500b()) {
            this.f1066a.mo16243a();
        }
        boolean a = C1933js.m1205a(new C1932jr(C1776ff.m946d(), "currentFile"), new C1932jr(C1776ff.m943b(), C1776ff.m945c()));
        StringBuilder sb = new StringBuilder("File moved status: ");
        sb.append(a);
        sb.append(" InProgress to Completed.");
        C1685cy.m754a(4, "FileWriterModule", sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x013c  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0154  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01ab  */
    /* renamed from: f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.flurry.sdk.C1836gk m964f() {
        /*
            java.lang.String r0 = "FileWriterModule"
            r1 = 4
            java.lang.String r2 = "Start getting native crash entity."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r2)
            android.content.Context r2 = com.flurry.sdk.C1576b.m502a()
            java.lang.String r3 = ".yflurrynativecrash"
            java.io.File r2 = r2.getFileStreamPath(r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = ".*"
            r3.<init>(r4)
            java.lang.String r5 = ".dmp"
            java.lang.String r5 = java.util.regex.Pattern.quote(r5)
            r3.append(r5)
            java.lang.String r5 = "$"
            r3.append(r5)
            java.lang.String r3 = r3.toString()
            java.util.regex.Pattern r3 = java.util.regex.Pattern.compile(r3)
            boolean r5 = r2.exists()
            r6 = 0
            if (r5 != 0) goto L_0x0039
            java.lang.String[] r3 = new java.lang.String[r6]
            goto L_0x0046
        L_0x0039:
            com.flurry.sdk.ff$1 r5 = new com.flurry.sdk.ff$1
            r5.<init>(r3)
            java.lang.String[] r3 = r2.list(r5)
            if (r3 != 0) goto L_0x0046
            java.lang.String[] r3 = new java.lang.String[r6]
        L_0x0046:
            r5 = 0
            if (r3 == 0) goto L_0x01bb
            int r7 = r3.length
            if (r7 != 0) goto L_0x004e
            goto L_0x01bb
        L_0x004e:
            int r7 = r3.length
            r9 = r5
            r8 = 0
        L_0x0051:
            if (r8 >= r7) goto L_0x01b5
            r10 = r3[r8]
            java.lang.String r11 = java.lang.String.valueOf(r10)
            java.lang.String r12 = "Native crash occurred in previous session! Found minidump file - "
            java.lang.String r11 = r12.concat(r11)
            com.flurry.sdk.C1685cy.m766c(r0, r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r10)
            java.lang.String r12 = ".fcb"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>(r4)
            java.lang.String r11 = java.util.regex.Pattern.quote(r11)
            r12.append(r11)
            r12.append(r4)
            java.lang.String r11 = r12.toString()
            java.util.regex.Pattern r11 = java.util.regex.Pattern.compile(r11)
            java.lang.String[] r11 = com.flurry.sdk.C1732dy.m862a(r2, r11)
            int r12 = r11.length
            if (r12 <= 0) goto L_0x0094
            r11 = r11[r6]
            goto L_0x0095
        L_0x0094:
            r11 = r5
        L_0x0095:
            boolean r12 = android.text.TextUtils.isEmpty(r11)
            if (r12 == 0) goto L_0x00a2
            java.lang.String r12 = "There was no breadcrumbs file associated with the minidump file."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r12)
            r12 = 1
            goto L_0x00a3
        L_0x00a2:
            r12 = 0
        L_0x00a3:
            java.lang.String r14 = java.lang.String.valueOf(r11)
            java.lang.String r15 = "Breadcrumbs file associated with minidump file - "
            java.lang.String r14 = r15.concat(r14)
            com.flurry.sdk.C1685cy.m754a(r1, r0, r14)
            boolean r14 = android.text.TextUtils.isEmpty(r11)
            r15 = 5
            java.lang.String r6 = "\\."
            if (r14 == 0) goto L_0x00bb
        L_0x00b9:
            r13 = r5
            goto L_0x00c6
        L_0x00bb:
            java.lang.String[] r14 = r11.split(r6)
            int r13 = r14.length
            if (r13 == r15) goto L_0x00c3
            goto L_0x00b9
        L_0x00c3:
            r13 = 3
            r13 = r14[r13]
        L_0x00c6:
            boolean r14 = android.text.TextUtils.isEmpty(r11)
            if (r14 == 0) goto L_0x00ce
        L_0x00cc:
            r6 = r5
            goto L_0x00d8
        L_0x00ce:
            java.lang.String[] r6 = r11.split(r6)
            int r14 = r6.length
            if (r14 == r15) goto L_0x00d6
            goto L_0x00cc
        L_0x00d6:
            r6 = r6[r1]
        L_0x00d8:
            boolean r14 = android.text.TextUtils.isEmpty(r13)
            if (r14 == 0) goto L_0x00ec
            java.lang.String r12 = java.lang.String.valueOf(r11)
            java.lang.String r14 = "There is no session id specified with crash breadcrumbs file: "
            java.lang.String r12 = r14.concat(r12)
            com.flurry.sdk.C1685cy.m754a(r1, r0, r12)
            r12 = 1
        L_0x00ec:
            long r14 = java.lang.System.currentTimeMillis()
            java.lang.Long.parseLong(r13)     // Catch:{ NumberFormatException -> 0x00fb }
            long r14 = java.lang.Long.parseLong(r6)     // Catch:{ NumberFormatException -> 0x00fb }
            r13 = r12
            r20 = r14
            goto L_0x010b
        L_0x00fb:
            java.lang.String r6 = java.lang.String.valueOf(r13)
            java.lang.String r12 = "Issue parsing session id into start time: "
            java.lang.String r6 = r12.concat(r6)
            com.flurry.sdk.C1685cy.m754a(r1, r0, r6)
            r20 = r14
            r13 = 1
        L_0x010b:
            java.io.File r6 = new java.io.File
            r6.<init>(r2, r11)
            boolean r11 = r6.exists()
            if (r11 == 0) goto L_0x013c
            com.flurry.sdk.w r11 = new com.flurry.sdk.w
            r11.<init>(r6)
            java.util.List r11 = r11.mo16536a()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r14 = "Number of crash breadcrumbs - "
            r12.<init>(r14)
            int r14 = r11.size()
            r12.append(r14)
            java.lang.String r12 = r12.toString()
            com.flurry.sdk.C1685cy.m754a(r1, r0, r12)
            r6.delete()
            r30 = r11
            r16 = r13
            goto L_0x0145
        L_0x013c:
            java.lang.String r6 = "Breadcrumbs file does not exist."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r6)
            r30 = r5
            r16 = 1
        L_0x0145:
            com.flurry.sdk.y r6 = com.flurry.sdk.C1963y.NATIVE_CRASH
            java.lang.String r6 = r6.f1467c
            java.io.File r11 = new java.io.File
            r11.<init>(r2, r10)
            boolean r10 = r11.exists()
            if (r10 == 0) goto L_0x01ab
            if (r16 == 0) goto L_0x015f
            java.lang.String r6 = "Some error occurred with minidump file. Deleting it."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r6)
            r11.delete()
            goto L_0x01b0
        L_0x015f:
            java.lang.String r31 = com.flurry.sdk.C1732dy.m865c(r11)
            r11.delete()
            java.lang.String r32 = m965g()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Logcat size: "
            r9.<init>(r10)
            int r10 = r32.length()
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.flurry.sdk.C1685cy.m754a(r1, r0, r9)
            java.util.concurrent.atomic.AtomicInteger r9 = com.flurry.sdk.C1833gj.m1071b()
            int r18 = r9.incrementAndGet()
            com.flurry.sdk.gk r9 = new com.flurry.sdk.gk
            com.flurry.sdk.gj$a r10 = com.flurry.sdk.C1833gj.C1834a.UNRECOVERABLE_CRASH
            int r10 = r10.f1181d
            com.flurry.sdk.gj$b r11 = com.flurry.sdk.C1833gj.C1835b.NATIVE_CRASH_ATTACHED
            int r11 = r11.f1186d
            r27 = 0
            r28 = 0
            int r29 = com.flurry.sdk.C1961w.m1240b()
            java.lang.String r22 = ""
            java.lang.String r23 = ""
            java.lang.String r24 = ""
            r17 = r9
            r19 = r6
            r25 = r10
            r26 = r11
            r17.<init>(r18, r19, r20, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
            goto L_0x01b0
        L_0x01ab:
            java.lang.String r6 = "Minidump file doesn't exist."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r6)
        L_0x01b0:
            int r8 = r8 + 1
            r6 = 0
            goto L_0x0051
        L_0x01b5:
            java.lang.String r2 = "Finished getting native crash entity."
            com.flurry.sdk.C1685cy.m754a(r1, r0, r2)
            return r9
        L_0x01bb:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1785fl.m964f():com.flurry.sdk.gk");
    }

    /* renamed from: g */
    private static String m965g() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d").getInputStream()));
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null || i >= 1000 || sb.length() + readLine.length() > 524288) {
                    C1685cy.m754a(4, "FileWriterModule", "Get Logcat lines: ".concat(String.valueOf(i)));
                } else {
                    sb.append(readLine);
                    sb.append(Constants.NEW_LINE);
                    i++;
                }
            }
            C1685cy.m754a(4, "FileWriterModule", "Get Logcat lines: ".concat(String.valueOf(i)));
            return sb.toString();
        } catch (IOException unused) {
            return "";
        }
    }

    /* renamed from: a */
    public final void mo16243a() {
        C1776ff.m941a();
        File file = new File(C1776ff.m946d());
        if (!file.exists()) {
            file.mkdirs();
        }
        C1776ff.m941a();
        File file2 = new File(C1776ff.m943b());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        C1836gk f = m964f();
        C1930jp a = f != null ? C1833gj.m1069a(f) : null;
        StringBuilder sb = new StringBuilder();
        sb.append(C1776ff.m946d());
        sb.append(File.separator);
        String str = "currentFile";
        sb.append(str);
        if (C1776ff.m944b(sb.toString())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(C1776ff.m946d());
            sb2.append(File.separator);
            String str2 = "crashFile";
            sb2.append(str2);
            if (C1776ff.m944b(sb2.toString())) {
                C1932jr jrVar = new C1932jr(C1776ff.m946d(), str);
                C1932jr jrVar2 = new C1932jr(C1776ff.m946d(), str2);
                if (C1778fg.m948a(jrVar, jrVar2)) {
                    if (C1778fg.m949a(jrVar.f1387a, jrVar.f1388b, jrVar2.f1387a, jrVar2.f1388b) && C1933js.m1207b(jrVar, jrVar2)) {
                        C1933js.m1204a(jrVar2);
                    }
                }
                C1933js.m1204a(jrVar2);
            }
            m963e();
        }
        if (m962a(str)) {
            this.f1066a.mo16498a((C1930jp) C1902ip.m1151b(), (C1825a) null);
            if (a != null) {
                this.f1066a.mo16497a(a);
            }
        }
    }
}
