package com.startapp.p054a.p055a.p060e;

import com.startapp.p054a.p055a.p056a.C4698c;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.PrintStream;

/* renamed from: com.startapp.a.a.e.d */
/* compiled from: StartAppSDK */
public abstract class C4715d {

    /* renamed from: a */
    private final C4714c f2421a = new C4714c();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract C4698c mo61118a(DataInput dataInput);

    /* renamed from: a */
    public C4698c mo61122a(String str) {
        if (str == null) {
            return null;
        }
        try {
            byte[] a = this.f2421a.mo61121a(str);
            if (a == null) {
                return null;
            }
            return mo61118a(mo61123a(a));
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("HighPageCountException")) {
                PrintStream printStream = System.err;
                StringBuilder sb = new StringBuilder();
                sb.append("HighPageCountException (PLM-2573) ");
                sb.append(e.getMessage());
                sb.append(", bad bloom token: ");
                sb.append(str);
                printStream.println(sb.toString());
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61124a(DataInput dataInput, C4698c cVar, long j) {
        int c = cVar.mo61095c();
        long j2 = j;
        for (int i = 0; i < c; i++) {
            long[] a = cVar.mo61093a(i);
            long j3 = j2;
            int i2 = 0;
            while (true) {
                if (i2 >= 4096) {
                    j2 = j3;
                    break;
                }
                long j4 = j3 - 1;
                if (j3 <= 0) {
                    j2 = j4;
                    break;
                }
                a[i2] = dataInput.readLong();
                i2++;
                j3 = j4;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public DataInput mo61123a(byte[] bArr) {
        return new DataInputStream(new ByteArrayInputStream(bArr));
    }
}
