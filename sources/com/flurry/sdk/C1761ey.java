package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

/* renamed from: com.flurry.sdk.ey */
public final class C1761ey extends C1766f implements C1931jq {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public PriorityQueue<String> f1028a;

    /* renamed from: b */
    private C1603bn f1029b;

    /* renamed from: d */
    private C1603bn f1030d;

    public C1761ey() {
        super("FrameLogDataSender", C1756ex.m904a(C1758a.CORE));
        this.f1028a = null;
        this.f1028a = new PriorityQueue<>(4, new C1779fh());
        this.f1029b = new C1627bs();
        this.f1030d = new C1626br();
    }

    /* renamed from: a */
    public final void mo16243a() {
        this.f1029b.mo16243a();
        this.f1030d.mo16243a();
    }

    /* renamed from: a */
    public final void mo16457a(final List<String> list) {
        String str = "FrameLogDataSender";
        if (list.size() == 0) {
            C1685cy.m754a(6, str, "File List is null or empty");
            return;
        }
        StringBuilder sb = new StringBuilder("Number of files being added:");
        sb.append(list.toString());
        C1685cy.m766c(str, sb.toString());
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1761ey.this.f1028a.addAll(list);
                C1761ey.this.m911b();
            }
        });
    }

    /* renamed from: a */
    private static byte[] m910a(File file) throws IOException {
        int length = (int) file.length();
        byte[] bArr = new byte[length];
        byte[] bArr2 = new byte[length];
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            int read = fileInputStream.read(bArr, 0, length);
            if (read < length) {
                int i = length - read;
                while (i > 0) {
                    int read2 = fileInputStream.read(bArr2, 0, i);
                    System.arraycopy(bArr2, 0, bArr, length - i, read2);
                    i -= read2;
                }
            }
        } catch (IOException e) {
            C1685cy.m754a(6, "FrameLogDataSender", "Error reading file. ".concat(String.valueOf(e)));
        } catch (Throwable th) {
            fileInputStream.close();
            throw th;
        }
        fileInputStream.close();
        return bArr;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m911b() {
        StringBuilder sb = new StringBuilder(" Starting processNextFile ");
        sb.append(this.f1028a.size());
        String str = "FrameLogDataSender";
        C1685cy.m766c(str, sb.toString());
        if (this.f1028a.peek() == null) {
            C1685cy.m766c(str, "No file present to process.");
            return;
        }
        String str2 = (String) this.f1028a.poll();
        if (C1776ff.m944b(str2)) {
            C1685cy.m766c(str, "Starting to upload file: ".concat(String.valueOf(str2)));
            byte[] bArr = new byte[0];
            try {
                bArr = m910a(new File(str2));
            } catch (IOException e) {
                StringBuilder sb2 = new StringBuilder("Error in getting bytes form the file: ");
                sb2.append(e.getMessage());
                C1685cy.m754a(6, str, sb2.toString());
            }
            String b = C1598bi.m531a().mo16288b();
            StringBuilder sb3 = new StringBuilder();
            C1601bl.m537a();
            sb3.append(313);
            this.f1029b.mo16295a(bArr, b, sb3.toString());
            this.f1029b.mo16294a((C1602bm) new C1602bm() {
                /* renamed from: a */
                public final void mo16291a() {
                    C1771fb.m926a().mo16467a(new C1912iz(new C1914ja(true)));
                }

                /* renamed from: b */
                public final void mo16292b() {
                    C1771fb.m926a().mo16467a(new C1912iz(new C1914ja(false)));
                }
            });
            m908a(str2);
            C1685cy.m766c(str, "File appended for upload: ".concat(String.valueOf(str2)));
            return;
        }
        C1685cy.m754a(6, str, "Something wrong with the file. File does not exist.");
    }

    /* renamed from: a */
    private synchronized void m908a(String str) {
        C1685cy.m766c("FrameLogDataSender", "File upload status: ".concat(String.valueOf(str)));
        boolean a = C1776ff.m942a(str);
        StringBuilder sb = new StringBuilder("Deleting file ");
        sb.append(str);
        sb.append(" deleted ");
        sb.append(a);
        C1685cy.m754a(2, "FrameLogDataSender", sb.toString());
        m911b();
    }
}
