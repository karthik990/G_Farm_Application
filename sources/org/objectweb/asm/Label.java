package org.objectweb.asm;

import com.google.common.base.Ascii;

public class Label {

    /* renamed from: a */
    int f4677a;

    /* renamed from: b */
    int f4678b;

    /* renamed from: c */
    int f4679c;

    /* renamed from: d */
    private int f4680d;

    /* renamed from: e */
    private int[] f4681e;

    /* renamed from: f */
    int f4682f;

    /* renamed from: g */
    int f4683g;

    /* renamed from: h */
    Frame f4684h;

    /* renamed from: i */
    Label f4685i;
    public Object info;

    /* renamed from: j */
    Edge f4686j;

    /* renamed from: k */
    Label f4687k;

    /* renamed from: a */
    private void m4218a(int i, int i2) {
        if (this.f4681e == null) {
            this.f4681e = new int[6];
        }
        int i3 = this.f4680d;
        int[] iArr = this.f4681e;
        if (i3 >= iArr.length) {
            int[] iArr2 = new int[(iArr.length + 6)];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.f4681e = iArr2;
        }
        int[] iArr3 = this.f4681e;
        int i4 = this.f4680d;
        this.f4680d = i4 + 1;
        iArr3[i4] = i;
        int i5 = this.f4680d;
        this.f4680d = i5 + 1;
        iArr3[i5] = i2;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Label mo76792a() {
        Frame frame = this.f4684h;
        return frame == null ? this : frame.f4650b;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76793a(long j, int i) {
        int i2 = this.f4677a;
        if ((i2 & 1024) == 0) {
            this.f4677a = i2 | 1024;
            this.f4681e = new int[((i / 32) + 1)];
        }
        int[] iArr = this.f4681e;
        int i3 = (int) (j >>> 32);
        iArr[i3] = iArr[i3] | ((int) j);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001a, code lost:
        if (r4 != false) goto L_0x001c;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo76794a(org.objectweb.asm.MethodWriter r1, org.objectweb.asm.ByteVector r2, int r3, boolean r4) {
        /*
            r0 = this;
            int r1 = r0.f4677a
            r1 = r1 & 2
            if (r1 != 0) goto L_0x0017
            r1 = -1
            if (r4 == 0) goto L_0x0011
            int r3 = -1 - r3
            int r4 = r2.f4568b
            r0.m4218a(r3, r4)
            goto L_0x001c
        L_0x0011:
            int r4 = r2.f4568b
            r0.m4218a(r3, r4)
            goto L_0x0020
        L_0x0017:
            int r1 = r0.f4679c
            int r1 = r1 - r3
            if (r4 == 0) goto L_0x0020
        L_0x001c:
            r2.putInt(r1)
            goto L_0x0023
        L_0x0020:
            r2.putShort(r1)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Label.mo76794a(org.objectweb.asm.MethodWriter, org.objectweb.asm.ByteVector, int, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo76795a(long j) {
        return ((this.f4677a & 1024) == 0 || (this.f4681e[(int) (j >>> 32)] & ((int) j)) == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo76796a(Label label) {
        if ((this.f4677a & 1024) != 0 && (label.f4677a & 1024) != 0) {
            int i = 0;
            while (true) {
                int[] iArr = this.f4681e;
                if (i >= iArr.length) {
                    break;
                } else if ((iArr[i] & label.f4681e[i]) != 0) {
                    return true;
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo76797a(MethodWriter methodWriter, int i, byte[] bArr) {
        this.f4677a |= 2;
        this.f4679c = i;
        int i2 = 0;
        boolean z = false;
        while (i2 < this.f4680d) {
            int[] iArr = this.f4681e;
            int i3 = i2 + 1;
            int i4 = iArr[i2];
            int i5 = i3 + 1;
            int i6 = iArr[i3];
            if (i4 >= 0) {
                int i7 = i - i4;
                if (i7 < -32768 || i7 > 32767) {
                    int i8 = i6 - 1;
                    byte b = bArr[i8] & 255;
                    if (b <= 168) {
                        bArr[i8] = (byte) (b + 49);
                    } else {
                        bArr[i8] = (byte) (b + Ascii.DC4);
                    }
                    z = true;
                }
                int i9 = i6 + 1;
                bArr[i6] = (byte) (i7 >>> 8);
                bArr[i9] = (byte) i7;
            } else {
                int i10 = i4 + i + 1;
                int i11 = i6 + 1;
                bArr[i6] = (byte) (i10 >>> 24);
                int i12 = i11 + 1;
                bArr[i11] = (byte) (i10 >>> 16);
                int i13 = i12 + 1;
                bArr[i12] = (byte) (i10 >>> 8);
                bArr[i13] = (byte) i10;
            }
            i2 = i5;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    /*  JADX ERROR: JadxOverflowException in pass: LoopRegionVisitor
        jadx.core.utils.exceptions.JadxOverflowException: LoopRegionVisitor.assignOnlyInLoop endless recursion
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:29)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    /* renamed from: b */
    public void mo76798b(org.objectweb.asm.Label r5, long r6, int r8) {
        /*
            r4 = this;
            r0 = r4
        L_0x0001:
            if (r0 == 0) goto L_0x0061
            org.objectweb.asm.Label r1 = r0.f4687k
            r2 = 0
            r0.f4687k = r2
            if (r5 == 0) goto L_0x0037
            int r2 = r0.f4677a
            r3 = r2 & 2048(0x800, float:2.87E-42)
            if (r3 == 0) goto L_0x0011
            goto L_0x003d
        L_0x0011:
            r2 = r2 | 2048(0x800, float:2.87E-42)
            r0.f4677a = r2
            int r2 = r0.f4677a
            r2 = r2 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x0042
            boolean r2 = r0.mo76796a(r5)
            if (r2 != 0) goto L_0x0042
            org.objectweb.asm.Edge r2 = new org.objectweb.asm.Edge
            r2.<init>()
            int r3 = r0.f4682f
            r2.f4634a = r3
            org.objectweb.asm.Edge r3 = r5.f4686j
            org.objectweb.asm.Label r3 = r3.f4635b
            r2.f4635b = r3
            org.objectweb.asm.Edge r3 = r0.f4686j
            r2.f4636c = r3
            r0.f4686j = r2
            goto L_0x0042
        L_0x0037:
            boolean r2 = r0.mo76795a(r6)
            if (r2 == 0) goto L_0x003f
        L_0x003d:
            r0 = r1
            goto L_0x0001
        L_0x003f:
            r0.mo76793a(r6, r8)
        L_0x0042:
            org.objectweb.asm.Edge r2 = r0.f4686j
        L_0x0044:
            if (r2 == 0) goto L_0x003d
            int r3 = r0.f4677a
            r3 = r3 & 128(0x80, float:1.794E-43)
            if (r3 == 0) goto L_0x0052
            org.objectweb.asm.Edge r3 = r0.f4686j
            org.objectweb.asm.Edge r3 = r3.f4636c
            if (r2 == r3) goto L_0x005e
        L_0x0052:
            org.objectweb.asm.Label r3 = r2.f4635b
            org.objectweb.asm.Label r3 = r3.f4687k
            if (r3 != 0) goto L_0x005e
            org.objectweb.asm.Label r3 = r2.f4635b
            r3.f4687k = r1
            org.objectweb.asm.Label r1 = r2.f4635b
        L_0x005e:
            org.objectweb.asm.Edge r2 = r2.f4636c
            goto L_0x0044
        L_0x0061:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Label.mo76798b(org.objectweb.asm.Label, long, int):void");
    }

    public int getOffset() {
        if ((this.f4677a & 2) != 0) {
            return this.f4679c;
        }
        throw new IllegalStateException("Label offset position has not been resolved yet");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("L");
        stringBuffer.append(System.identityHashCode(this));
        return stringBuffer.toString();
    }
}
