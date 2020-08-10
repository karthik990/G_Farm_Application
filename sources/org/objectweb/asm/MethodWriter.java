package org.objectweb.asm;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.google.common.base.Ascii;

class MethodWriter extends MethodVisitor {

    /* renamed from: $ */
    private ByteVector f4689$;

    /* renamed from: A */
    private int f4690A;

    /* renamed from: B */
    private Handler f4691B;

    /* renamed from: C */
    private Handler f4692C;

    /* renamed from: D */
    private int f4693D;

    /* renamed from: E */
    private ByteVector f4694E;

    /* renamed from: F */
    private int f4695F;

    /* renamed from: G */
    private ByteVector f4696G;

    /* renamed from: H */
    private int f4697H;

    /* renamed from: I */
    private ByteVector f4698I;

    /* renamed from: J */
    private Attribute f4699J;

    /* renamed from: K */
    private boolean f4700K;

    /* renamed from: L */
    private int f4701L;

    /* renamed from: M */
    private final int f4702M;

    /* renamed from: N */
    private Label f4703N;

    /* renamed from: O */
    private Label f4704O;

    /* renamed from: P */
    private Label f4705P;

    /* renamed from: Q */
    private int f4706Q;

    /* renamed from: R */
    private int f4707R;

    /* renamed from: S */
    private int f4708S;

    /* renamed from: T */
    private int f4709T;

    /* renamed from: U */
    private AnnotationWriter f4710U;

    /* renamed from: V */
    private AnnotationWriter f4711V;

    /* renamed from: W */
    private AnnotationWriter f4712W;

    /* renamed from: X */
    private AnnotationWriter f4713X;

    /* renamed from: Y */
    private int f4714Y;

    /* renamed from: Z */
    private int f4715Z;

    /* renamed from: b */
    final ClassWriter f4716b;

    /* renamed from: c */
    private int f4717c;

    /* renamed from: d */
    private final int f4718d;

    /* renamed from: e */
    private final int f4719e;

    /* renamed from: f */
    private final String f4720f;

    /* renamed from: g */
    String f4721g;

    /* renamed from: h */
    int f4722h;

    /* renamed from: i */
    int f4723i;

    /* renamed from: j */
    int f4724j;

    /* renamed from: k */
    int[] f4725k;

    /* renamed from: l */
    private ByteVector f4726l;

    /* renamed from: m */
    private AnnotationWriter f4727m;

    /* renamed from: n */
    private AnnotationWriter f4728n;

    /* renamed from: o */
    private AnnotationWriter[] f4729o;

    /* renamed from: p */
    private AnnotationWriter[] f4730p;

    /* renamed from: q */
    private Attribute f4731q;

    /* renamed from: r */
    private ByteVector f4732r = new ByteVector();

    /* renamed from: s */
    private int f4733s;

    /* renamed from: t */
    private int f4734t;

    /* renamed from: u */
    private int f4735u;

    /* renamed from: v */
    private ByteVector f4736v;

    /* renamed from: w */
    private int f4737w;

    /* renamed from: x */
    private int[] f4738x;

    /* renamed from: z */
    private int[] f4739z;

    MethodWriter(ClassWriter classWriter, int i, String str, String str2, String str3, String[] strArr, boolean z, boolean z2) {
        super(Opcodes.ASM5);
        if (classWriter.f4578D == null) {
            classWriter.f4578D = this;
        } else {
            classWriter.f4579E.f4688mv = this;
        }
        classWriter.f4579E = this;
        this.f4716b = classWriter;
        this.f4717c = i;
        if ("<init>".equals(str)) {
            this.f4717c |= 524288;
        }
        this.f4718d = classWriter.newUTF8(str);
        this.f4719e = classWriter.newUTF8(str2);
        this.f4720f = str2;
        this.f4721g = str3;
        int i2 = 0;
        if (strArr != null && strArr.length > 0) {
            this.f4724j = strArr.length;
            this.f4725k = new int[this.f4724j];
            for (int i3 = 0; i3 < this.f4724j; i3++) {
                this.f4725k[i3] = classWriter.newClass(strArr[i3]);
            }
        }
        if (!z2) {
            i2 = z ? 1 : 2;
        }
        this.f4702M = i2;
        if (z || z2) {
            int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(this.f4720f) >> 2;
            if ((i & 8) != 0) {
                argumentsAndReturnSizes--;
            }
            this.f4734t = argumentsAndReturnSizes;
            this.f4709T = argumentsAndReturnSizes;
            this.f4703N = new Label();
            this.f4703N.f4677a |= 8;
            visitLabel(this.f4703N);
        }
    }

    /* renamed from: a */
    private int m4226a(int i, int i2, int i3) {
        int i4 = i2 + 3 + i3;
        int[] iArr = this.f4739z;
        if (iArr == null || iArr.length < i4) {
            this.f4739z = new int[i4];
        }
        int[] iArr2 = this.f4739z;
        iArr2[0] = i;
        iArr2[1] = i2;
        iArr2[2] = i3;
        return 3;
    }

    /* renamed from: a */
    static int m4227a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
    }

    /* renamed from: a */
    static int m4228a(int[] iArr, int[] iArr2, int i, int i2) {
        int i3 = i2 - i;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            if (i < iArr[i4] && iArr[i4] <= i2) {
                i3 += iArr2[i4];
            } else if (i2 < iArr[i4] && iArr[i4] <= i) {
                i3 -= iArr2[i4];
            }
        }
        return i3;
    }

    /* renamed from: a */
    private void m4229a(int i, int i2) {
        int i3;
        ByteVector byteVector;
        char c;
        while (i < i2) {
            int i4 = this.f4739z[i];
            int i5 = -268435456 & i4;
            if (i5 == 0) {
                int i6 = i4 & 1048575;
                int i7 = i4 & 267386880;
                if (i7 == 24117248) {
                    byteVector = this.f4736v.putByte(7);
                    ClassWriter classWriter = this.f4716b;
                    i3 = classWriter.newClass(classWriter.f4581H[i6].f4672g);
                } else if (i7 != 25165824) {
                    this.f4736v.putByte(i6);
                    i++;
                } else {
                    byteVector = this.f4736v.putByte(8);
                    i3 = this.f4716b.f4581H[i6].f4670c;
                }
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                int i8 = i5 >> 28;
                while (true) {
                    int i9 = i8 - 1;
                    if (i8 <= 0) {
                        break;
                    }
                    stringBuffer.append('[');
                    i8 = i9;
                }
                if ((i4 & 267386880) == 24117248) {
                    stringBuffer.append('L');
                    stringBuffer.append(this.f4716b.f4581H[i4 & 1048575].f4672g);
                    c = ';';
                } else {
                    int i10 = i4 & 15;
                    if (i10 == 1) {
                        c = 'I';
                    } else if (i10 == 2) {
                        c = 'F';
                    } else if (i10 != 3) {
                        switch (i10) {
                            case 9:
                                c = 'Z';
                                break;
                            case 10:
                                c = 'B';
                                break;
                            case 11:
                                c = 'C';
                                break;
                            case 12:
                                c = 'S';
                                break;
                            default:
                                c = 'J';
                                break;
                        }
                    } else {
                        c = 'D';
                    }
                }
                stringBuffer.append(c);
                byteVector = this.f4736v.putByte(7);
                i3 = this.f4716b.newClass(stringBuffer.toString());
            }
            byteVector.putShort(i3);
            i++;
        }
    }

    /* renamed from: a */
    private void m4230a(int i, Label label) {
        Edge edge = new Edge();
        edge.f4634a = i;
        edge.f4635b = label;
        edge.f4636c = this.f4705P.f4686j;
        this.f4705P.f4686j = edge;
    }

    /* renamed from: a */
    private void m4231a(Object obj) {
        ByteVector putByte;
        int i;
        if (obj instanceof String) {
            putByte = this.f4736v.putByte(7);
            i = this.f4716b.newClass((String) obj);
        } else if (obj instanceof Integer) {
            this.f4736v.putByte(((Integer) obj).intValue());
            return;
        } else {
            putByte = this.f4736v.putByte(8);
            i = ((Label) obj).f4679c;
        }
        putByte.putShort(i);
    }

    /* renamed from: a */
    private void m4232a(Label label, Label[] labelArr) {
        Label label2 = this.f4705P;
        if (label2 != null) {
            if (this.f4702M == 0) {
                label2.f4684h.mo76774a((int) Opcodes.LOOKUPSWITCH, 0, (ClassWriter) null, (Item) null);
                m4230a(0, label);
                Label a = label.mo76792a();
                a.f4677a |= 16;
                for (int i = 0; i < labelArr.length; i++) {
                    m4230a(0, labelArr[i]);
                    Label a2 = labelArr[i].mo76792a();
                    a2.f4677a |= 16;
                }
            } else {
                this.f4706Q--;
                m4230a(this.f4706Q, label);
                for (Label a3 : labelArr) {
                    m4230a(this.f4706Q, a3);
                }
            }
            m4241e();
        }
    }

    /* renamed from: a */
    static void m4233a(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 8);
        bArr[i + 1] = (byte) i2;
    }

    /* renamed from: a */
    static void m4234a(int[] iArr, int[] iArr2, Label label) {
        if ((label.f4677a & 4) == 0) {
            label.f4679c = m4228a(iArr, iArr2, 0, label.f4679c);
            label.f4677a |= 4;
        }
    }

    /* renamed from: b */
    static short m4235b(byte[] bArr, int i) {
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    /* renamed from: b */
    private void m4236b() {
        if (this.f4738x != null) {
            if (this.f4736v == null) {
                this.f4736v = new ByteVector();
            }
            m4239c();
            this.f4735u++;
        }
        this.f4738x = this.f4739z;
        this.f4739z = null;
    }

    /* renamed from: b */
    private void m4237b(Frame frame) {
        int[] iArr = frame.f4651c;
        int[] iArr2 = frame.f4652d;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < iArr.length) {
            int i5 = iArr[i2];
            i4++;
            if (i5 != 16777216) {
                i3 += i4;
                i4 = 0;
            }
            if (i5 == 16777220 || i5 == 16777219) {
                i2++;
            }
            i2++;
        }
        int i6 = 0;
        int i7 = 0;
        while (i6 < iArr2.length) {
            int i8 = iArr2[i6];
            i7++;
            if (i8 == 16777220 || i8 == 16777219) {
                i6++;
            }
            i6++;
        }
        int a = m4226a(frame.f4650b.f4679c, i3, i7);
        int i9 = 0;
        while (i3 > 0) {
            int i10 = iArr[i9];
            int i11 = a + 1;
            this.f4739z[a] = i10;
            if (i10 == 16777220 || i10 == 16777219) {
                i9++;
            }
            i9++;
            i3--;
            a = i11;
        }
        while (i < iArr2.length) {
            int i12 = iArr2[i];
            int i13 = a + 1;
            this.f4739z[a] = i12;
            if (i12 == 16777220 || i12 == 16777219) {
                i++;
            }
            i++;
            a = i13;
        }
        m4236b();
    }

    /* renamed from: c */
    static int m4238c(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    /* renamed from: c */
    private void m4239c() {
        int i;
        char c;
        ByteVector byteVector;
        char c2;
        int[] iArr = this.f4739z;
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = 0;
        if ((this.f4716b.f4589b & 65535) < 50) {
            this.f4736v.putShort(this.f4739z[0]).putShort(i2);
            int i5 = i2 + 3;
            m4229a(3, i5);
            this.f4736v.putShort(i3);
            m4229a(i5, i3 + i5);
            return;
        }
        int[] iArr2 = this.f4738x;
        int i6 = iArr2[1];
        int i7 = this.f4735u == 0 ? this.f4739z[0] : (this.f4739z[0] - iArr2[0]) - 1;
        if (i3 == 0) {
            int i8 = i2 - i6;
            switch (i8) {
                case -3:
                case -2:
                case -1:
                    i6 = i2;
                    c2 = 248;
                    break;
                case 0:
                    if (i7 >= 64) {
                        c2 = 251;
                        break;
                    } else {
                        c2 = 0;
                        break;
                    }
                case 1:
                case 2:
                case 3:
                    c2 = 252;
                    break;
                default:
                    c2 = 255;
                    break;
            }
            char c3 = c2;
            i = i8;
            c = c3;
        } else {
            c = (i2 == i6 && i3 == 1) ? i7 < 63 ? '@' : 247 : 255;
            i = 0;
        }
        if (c != 255) {
            int i9 = 3;
            while (true) {
                if (i4 < i6) {
                    if (this.f4739z[i9] != this.f4738x[i9]) {
                        c = 255;
                    } else {
                        i9++;
                        i4++;
                    }
                }
            }
        }
        if (c != 0) {
            if (c == '@') {
                this.f4736v.putByte(i7 + 64);
            } else if (c != 247) {
                if (c == 248) {
                    byteVector = this.f4736v.putByte(i + 251);
                } else if (c == 251) {
                    byteVector = this.f4736v.putByte(251);
                } else if (c != 252) {
                    this.f4736v.putByte(255).putShort(i7).putShort(i2);
                    int i10 = i2 + 3;
                    m4229a(3, i10);
                    this.f4736v.putShort(i3);
                    m4229a(i10, i3 + i10);
                } else {
                    this.f4736v.putByte(i + 251).putShort(i7);
                    m4229a(i6 + 3, i2 + 3);
                }
                byteVector.putShort(i7);
            } else {
                this.f4736v.putByte(247).putShort(i7);
            }
            m4229a(i2 + 3, i2 + 4);
        } else {
            this.f4736v.putByte(i7);
        }
    }

    /* JADX WARNING: type inference failed for: r10v10, types: [int] */
    /* JADX WARNING: type inference failed for: r10v18 */
    /* JADX WARNING: type inference failed for: r10v19, types: [int] */
    /* JADX WARNING: type inference failed for: r10v20, types: [int] */
    /* JADX WARNING: type inference failed for: r10v47 */
    /* JADX WARNING: type inference failed for: r10v49 */
    /* JADX WARNING: type inference failed for: r10v50, types: [int] */
    /* JADX WARNING: type inference failed for: r10v51, types: [int] */
    /* JADX WARNING: type inference failed for: r10v71 */
    /* JADX WARNING: type inference failed for: r10v72 */
    /* JADX WARNING: type inference failed for: r10v73 */
    /* JADX WARNING: type inference failed for: r10v74 */
    /* JADX WARNING: type inference failed for: r10v75 */
    /* JADX WARNING: type inference failed for: r10v76 */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0090, code lost:
        r3 = r11 + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e2, code lost:
        if (r10 == 0) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e4, code lost:
        r11 = new int[(r7.length + 1)];
        r8 = new int[(r9.length + 1)];
        java.lang.System.arraycopy(r7, 0, r11, 0, r7.length);
        java.lang.System.arraycopy(r9, 0, r8, 0, r9.length);
        r11[r7.length] = r3;
        r8[r9.length] = r10;
        r9 = r8;
        r7 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fc, code lost:
        if (r10 <= 0) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00fe, code lost:
        r4 = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0036, code lost:
        r10 = 0;
     */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r10v18
      assigns: []
      uses: []
      mth insns count: 344
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
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
    /* JADX WARNING: Unknown variable types count: 6 */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m4240d() {
        /*
            r18 = this;
            r0 = r18
            org.objectweb.asm.ByteVector r1 = r0.f4732r
            byte[] r1 = r1.f4567a
            r2 = 0
            int[] r3 = new int[r2]
            int[] r4 = new int[r2]
            org.objectweb.asm.ByteVector r5 = r0.f4732r
            int r5 = r5.f4568b
            boolean[] r5 = new boolean[r5]
            r6 = 3
            r7 = r4
            r4 = r3
            r3 = 3
        L_0x0015:
            if (r3 != r6) goto L_0x0018
            r3 = 2
        L_0x0018:
            r9 = r7
            r7 = r4
            r4 = r3
            r3 = 0
        L_0x001c:
            int r10 = r1.length
            r11 = 218(0xda, float:3.05E-43)
            r12 = 132(0x84, float:1.85E-43)
            r13 = 201(0xc9, float:2.82E-43)
            r14 = 8
            r8 = 1
            r15 = 4
            if (r3 >= r10) goto L_0x0101
            byte r10 = r1[r3]
            r10 = r10 & 255(0xff, float:3.57E-43)
            byte[] r17 = org.objectweb.asm.ClassWriter.f4574a
            byte r17 = r17[r10]
            switch(r17) {
                case 0: goto L_0x00de;
                case 1: goto L_0x00da;
                case 2: goto L_0x00d6;
                case 3: goto L_0x00da;
                case 4: goto L_0x00de;
                case 5: goto L_0x00d6;
                case 6: goto L_0x00d6;
                case 7: goto L_0x00d2;
                case 8: goto L_0x00d2;
                case 9: goto L_0x0093;
                case 10: goto L_0x00d2;
                case 11: goto L_0x00da;
                case 12: goto L_0x00d6;
                case 13: goto L_0x00d6;
                case 14: goto L_0x0066;
                case 15: goto L_0x0044;
                case 16: goto L_0x0034;
                case 17: goto L_0x0039;
                default: goto L_0x0034;
            }
        L_0x0034:
            int r3 = r3 + 4
        L_0x0036:
            r10 = 0
            goto L_0x00e2
        L_0x0039:
            int r10 = r3 + 1
            byte r10 = r1[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            if (r10 != r12) goto L_0x0034
            int r3 = r3 + 6
            goto L_0x0036
        L_0x0044:
            if (r4 != r8) goto L_0x004d
            int r10 = m4228a(r7, r9, r2, r3)
            r10 = r10 & r6
            int r10 = -r10
            goto L_0x0057
        L_0x004d:
            boolean r10 = r5[r3]
            if (r10 != 0) goto L_0x0056
            r10 = r3 & 3
            r5[r3] = r8
            goto L_0x0057
        L_0x0056:
            r10 = 0
        L_0x0057:
            int r11 = r3 + 4
            r3 = r3 & 3
            int r11 = r11 - r3
            int r3 = r11 + 4
            int r3 = m4227a(r1, r3)
            int r3 = r3 * 8
            int r3 = r3 + r14
            goto L_0x0090
        L_0x0066:
            if (r4 != r8) goto L_0x006f
            int r10 = m4228a(r7, r9, r2, r3)
            r10 = r10 & r6
            int r10 = -r10
            goto L_0x0079
        L_0x006f:
            boolean r10 = r5[r3]
            if (r10 != 0) goto L_0x0078
            r10 = r3 & 3
            r5[r3] = r8
            goto L_0x0079
        L_0x0078:
            r10 = 0
        L_0x0079:
            int r11 = r3 + 4
            r3 = r3 & 3
            int r11 = r11 - r3
            int r3 = r11 + 8
            int r3 = m4227a(r1, r3)
            int r12 = r11 + 4
            int r12 = m4227a(r1, r12)
            int r3 = r3 - r12
            int r3 = r3 + r8
            int r3 = r3 * 4
            int r3 = r3 + 12
        L_0x0090:
            int r11 = r11 + r3
            r3 = r11
            goto L_0x00e2
        L_0x0093:
            if (r10 <= r13) goto L_0x00a3
            if (r10 >= r11) goto L_0x009a
            int r10 = r10 + -49
            goto L_0x009c
        L_0x009a:
            int r10 = r10 + -20
        L_0x009c:
            int r11 = r3 + 1
            int r11 = m4238c(r1, r11)
            goto L_0x00a9
        L_0x00a3:
            int r11 = r3 + 1
            short r11 = m4235b(r1, r11)
        L_0x00a9:
            int r11 = r11 + r3
            int r11 = m4228a(r7, r9, r3, r11)
            r12 = -32768(0xffffffffffff8000, float:NaN)
            if (r11 < r12) goto L_0x00b6
            r12 = 32767(0x7fff, float:4.5916E-41)
            if (r11 <= r12) goto L_0x00cb
        L_0x00b6:
            boolean r11 = r5[r3]
            if (r11 != 0) goto L_0x00cb
            r11 = 167(0xa7, float:2.34E-43)
            if (r10 == r11) goto L_0x00c6
            r11 = 168(0xa8, float:2.35E-43)
            if (r10 != r11) goto L_0x00c3
            goto L_0x00c6
        L_0x00c3:
            r16 = 5
            goto L_0x00c8
        L_0x00c6:
            r16 = 2
        L_0x00c8:
            r5[r3] = r8
            goto L_0x00cd
        L_0x00cb:
            r16 = 0
        L_0x00cd:
            int r3 = r3 + 3
            r10 = r16
            goto L_0x00e2
        L_0x00d2:
            int r3 = r3 + 5
            goto L_0x0036
        L_0x00d6:
            int r3 = r3 + 3
            goto L_0x0036
        L_0x00da:
            int r3 = r3 + 2
            goto L_0x0036
        L_0x00de:
            int r3 = r3 + 1
            goto L_0x0036
        L_0x00e2:
            if (r10 == 0) goto L_0x001c
            int r11 = r7.length
            int r11 = r11 + r8
            int[] r11 = new int[r11]
            int r12 = r9.length
            int r12 = r12 + r8
            int[] r8 = new int[r12]
            int r12 = r7.length
            java.lang.System.arraycopy(r7, r2, r11, r2, r12)
            int r12 = r9.length
            java.lang.System.arraycopy(r9, r2, r8, r2, r12)
            int r7 = r7.length
            r11[r7] = r3
            int r7 = r9.length
            r8[r7] = r10
            r9 = r8
            r7 = r11
            if (r10 <= 0) goto L_0x001c
            r4 = 3
            goto L_0x001c
        L_0x0101:
            if (r4 >= r6) goto L_0x0105
            int r4 = r4 + -1
        L_0x0105:
            r3 = r4
            if (r3 != 0) goto L_0x0333
            org.objectweb.asm.ByteVector r3 = new org.objectweb.asm.ByteVector
            org.objectweb.asm.ByteVector r4 = r0.f4732r
            int r4 = r4.f4568b
            r3.<init>(r4)
            r4 = 0
        L_0x0112:
            org.objectweb.asm.ByteVector r10 = r0.f4732r
            int r10 = r10.f4568b
            if (r4 >= r10) goto L_0x0265
            byte r10 = r1[r4]
            r10 = r10 & 255(0xff, float:3.57E-43)
            byte[] r17 = org.objectweb.asm.ClassWriter.f4574a
            byte r17 = r17[r10]
            switch(r17) {
                case 0: goto L_0x025a;
                case 1: goto L_0x0252;
                case 2: goto L_0x024a;
                case 3: goto L_0x0252;
                case 4: goto L_0x025a;
                case 5: goto L_0x024a;
                case 6: goto L_0x024a;
                case 7: goto L_0x0243;
                case 8: goto L_0x0243;
                case 9: goto L_0x01ed;
                case 10: goto L_0x01d7;
                case 11: goto L_0x0252;
                case 12: goto L_0x024a;
                case 13: goto L_0x024a;
                case 14: goto L_0x0189;
                case 15: goto L_0x0142;
                case 16: goto L_0x0123;
                case 17: goto L_0x012b;
                default: goto L_0x0123;
            }
        L_0x0123:
            r6 = 5
            r3.putByteArray(r1, r4, r15)
            int r4 = r4 + 4
            goto L_0x0260
        L_0x012b:
            int r10 = r4 + 1
            byte r10 = r1[r10]
            r10 = r10 & 255(0xff, float:3.57E-43)
            if (r10 != r12) goto L_0x013b
            r10 = 6
            r3.putByteArray(r1, r4, r10)
            int r4 = r4 + 6
            goto L_0x01ea
        L_0x013b:
            r3.putByteArray(r1, r4, r15)
            int r4 = r4 + 4
            goto L_0x01ea
        L_0x0142:
            int r10 = r4 + 4
            r17 = r4 & 3
            int r10 = r10 - r17
            r12 = 171(0xab, float:2.4E-43)
            r3.putByte(r12)
            r12 = 0
            int r6 = r3.f4568b
            int r6 = r6 % r15
            int r6 = 4 - r6
            int r6 = r6 % r15
            r3.putByteArray(r12, r2, r6)
            int r6 = m4227a(r1, r10)
            int r6 = r6 + r4
            int r10 = r10 + r15
            int r6 = m4228a(r7, r9, r4, r6)
            r3.putInt(r6)
            int r6 = m4227a(r1, r10)
            int r10 = r10 + r15
            r3.putInt(r6)
        L_0x016c:
            if (r6 <= 0) goto L_0x0187
            int r12 = m4227a(r1, r10)
            r3.putInt(r12)
            int r10 = r10 + 4
            int r12 = m4227a(r1, r10)
            int r12 = r12 + r4
            int r10 = r10 + r15
            int r12 = m4228a(r7, r9, r4, r12)
            r3.putInt(r12)
            int r6 = r6 + -1
            goto L_0x016c
        L_0x0187:
            r4 = r10
            goto L_0x01ea
        L_0x0189:
            int r6 = r4 + 4
            r10 = r4 & 3
            int r6 = r6 - r10
            r10 = 170(0xaa, float:2.38E-43)
            r3.putByte(r10)
            r10 = 0
            int r12 = r3.f4568b
            int r12 = r12 % r15
            int r12 = 4 - r12
            int r12 = r12 % r15
            r3.putByteArray(r10, r2, r12)
            int r10 = m4227a(r1, r6)
            int r10 = r10 + r4
            int r6 = r6 + r15
            int r10 = m4228a(r7, r9, r4, r10)
            r3.putInt(r10)
            int r10 = m4227a(r1, r6)
            int r6 = r6 + r15
            r3.putInt(r10)
            int r12 = m4227a(r1, r6)
            int r12 = r12 - r10
            int r12 = r12 + r8
            int r6 = r6 + r15
            int r10 = r6 + -4
            int r10 = m4227a(r1, r10)
            r3.putInt(r10)
        L_0x01c2:
            if (r12 <= 0) goto L_0x01d5
            int r10 = m4227a(r1, r6)
            int r10 = r10 + r4
            int r6 = r6 + 4
            int r10 = m4228a(r7, r9, r4, r10)
            r3.putInt(r10)
            int r12 = r12 + -1
            goto L_0x01c2
        L_0x01d5:
            r4 = r6
            goto L_0x01ea
        L_0x01d7:
            int r6 = r4 + 1
            int r6 = m4227a(r1, r6)
            int r6 = r6 + r4
            int r6 = m4228a(r7, r9, r4, r6)
            r3.putByte(r10)
            r3.putInt(r6)
            int r4 = r4 + 5
        L_0x01ea:
            r6 = 5
            goto L_0x0260
        L_0x01ed:
            if (r10 <= r13) goto L_0x01fd
            if (r10 >= r11) goto L_0x01f4
            int r10 = r10 + -49
            goto L_0x01f6
        L_0x01f4:
            int r10 = r10 + -20
        L_0x01f6:
            int r6 = r4 + 1
            int r6 = m4238c(r1, r6)
            goto L_0x0203
        L_0x01fd:
            int r6 = r4 + 1
            short r6 = m4235b(r1, r6)
        L_0x0203:
            int r6 = r6 + r4
            int r6 = m4228a(r7, r9, r4, r6)
            boolean r12 = r5[r4]
            if (r12 == 0) goto L_0x023a
            r12 = 167(0xa7, float:2.34E-43)
            if (r10 != r12) goto L_0x0216
            r10 = 200(0xc8, float:2.8E-43)
            r3.putByte(r10)
            goto L_0x0236
        L_0x0216:
            r12 = 168(0xa8, float:2.35E-43)
            if (r10 != r12) goto L_0x021e
            r3.putByte(r13)
            goto L_0x0236
        L_0x021e:
            r12 = 166(0xa6, float:2.33E-43)
            if (r10 > r12) goto L_0x0227
            int r10 = r10 + 1
            r10 = r10 ^ r8
            int r10 = r10 - r8
            goto L_0x0229
        L_0x0227:
            r10 = r10 ^ 1
        L_0x0229:
            r3.putByte(r10)
            r3.putShort(r14)
            r10 = 200(0xc8, float:2.8E-43)
            r3.putByte(r10)
            int r6 = r6 + -3
        L_0x0236:
            r3.putInt(r6)
            goto L_0x0240
        L_0x023a:
            r3.putByte(r10)
            r3.putShort(r6)
        L_0x0240:
            int r4 = r4 + 3
            goto L_0x01ea
        L_0x0243:
            r6 = 5
            r3.putByteArray(r1, r4, r6)
            int r4 = r4 + 5
            goto L_0x0260
        L_0x024a:
            r6 = 5
            r10 = 3
            r3.putByteArray(r1, r4, r10)
            int r4 = r4 + 3
            goto L_0x0260
        L_0x0252:
            r6 = 5
            r10 = 2
            r3.putByteArray(r1, r4, r10)
            int r4 = r4 + 2
            goto L_0x0260
        L_0x025a:
            r6 = 5
            r3.putByte(r10)
            int r4 = r4 + 1
        L_0x0260:
            r6 = 3
            r12 = 132(0x84, float:1.85E-43)
            goto L_0x0112
        L_0x0265:
            int r1 = r0.f4702M
            if (r1 != 0) goto L_0x02aa
            org.objectweb.asm.Label r1 = r0.f4703N
        L_0x026b:
            if (r1 == 0) goto L_0x0283
            int r4 = r1.f4679c
            r6 = 3
            int r4 = r4 - r6
            if (r4 < 0) goto L_0x027d
            boolean r4 = r5[r4]
            if (r4 == 0) goto L_0x027d
            int r4 = r1.f4677a
            r4 = r4 | 16
            r1.f4677a = r4
        L_0x027d:
            m4234a(r7, r9, r1)
            org.objectweb.asm.Label r1 = r1.f4685i
            goto L_0x026b
        L_0x0283:
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            org.objectweb.asm.Item[] r1 = r1.f4581H
            if (r1 == 0) goto L_0x02b2
            r1 = 0
        L_0x028a:
            org.objectweb.asm.ClassWriter r4 = r0.f4716b
            org.objectweb.asm.Item[] r4 = r4.f4581H
            int r4 = r4.length
            if (r1 >= r4) goto L_0x02b2
            org.objectweb.asm.ClassWriter r4 = r0.f4716b
            org.objectweb.asm.Item[] r4 = r4.f4581H
            r4 = r4[r1]
            if (r4 == 0) goto L_0x02a7
            int r5 = r4.f4669b
            r6 = 31
            if (r5 != r6) goto L_0x02a7
            int r5 = r4.f4670c
            int r5 = m4228a(r7, r9, r2, r5)
            r4.f4670c = r5
        L_0x02a7:
            int r1 = r1 + 1
            goto L_0x028a
        L_0x02aa:
            int r1 = r0.f4735u
            if (r1 <= 0) goto L_0x02b2
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            r1.f4585L = r8
        L_0x02b2:
            org.objectweb.asm.Handler r1 = r0.f4691B
        L_0x02b4:
            if (r1 == 0) goto L_0x02c8
            org.objectweb.asm.Label r4 = r1.f4662a
            m4234a(r7, r9, r4)
            org.objectweb.asm.Label r4 = r1.f4663b
            m4234a(r7, r9, r4)
            org.objectweb.asm.Label r4 = r1.f4664c
            m4234a(r7, r9, r4)
            org.objectweb.asm.Handler r1 = r1.f4667f
            goto L_0x02b4
        L_0x02c8:
            r1 = 0
            r4 = 2
        L_0x02ca:
            if (r1 >= r4) goto L_0x02fc
            if (r1 != 0) goto L_0x02d1
            org.objectweb.asm.ByteVector r5 = r0.f4694E
            goto L_0x02d3
        L_0x02d1:
            org.objectweb.asm.ByteVector r5 = r0.f4696G
        L_0x02d3:
            if (r5 == 0) goto L_0x02f9
            byte[] r6 = r5.f4567a
            r10 = 0
        L_0x02d8:
            int r11 = r5.f4568b
            if (r10 >= r11) goto L_0x02f9
            int r11 = m4238c(r6, r10)
            int r12 = m4228a(r7, r9, r2, r11)
            m4233a(r6, r10, r12)
            int r13 = r10 + 2
            int r14 = m4238c(r6, r13)
            int r11 = r11 + r14
            int r11 = m4228a(r7, r9, r2, r11)
            int r11 = r11 - r12
            m4233a(r6, r13, r11)
            int r10 = r10 + 10
            goto L_0x02d8
        L_0x02f9:
            int r1 = r1 + 1
            goto L_0x02ca
        L_0x02fc:
            org.objectweb.asm.ByteVector r1 = r0.f4698I
            if (r1 == 0) goto L_0x0317
            byte[] r1 = r1.f4567a
            r4 = 0
        L_0x0303:
            org.objectweb.asm.ByteVector r5 = r0.f4698I
            int r5 = r5.f4568b
            if (r4 >= r5) goto L_0x0317
            int r5 = m4238c(r1, r4)
            int r5 = m4228a(r7, r9, r2, r5)
            m4233a(r1, r4, r5)
            int r4 = r4 + 4
            goto L_0x0303
        L_0x0317:
            org.objectweb.asm.Attribute r1 = r0.f4699J
        L_0x0319:
            if (r1 == 0) goto L_0x0330
            org.objectweb.asm.Label[] r2 = r1.getLabels()
            if (r2 == 0) goto L_0x032d
            int r4 = r2.length
            int r4 = r4 - r8
        L_0x0323:
            if (r4 < 0) goto L_0x032d
            r5 = r2[r4]
            m4234a(r7, r9, r5)
            int r4 = r4 + -1
            goto L_0x0323
        L_0x032d:
            org.objectweb.asm.Attribute r1 = r1.f4565a
            goto L_0x0319
        L_0x0330:
            r0.f4732r = r3
            return
        L_0x0333:
            r4 = r7
            r7 = r9
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.MethodWriter.m4240d():void");
    }

    /* renamed from: e */
    private void m4241e() {
        if (this.f4702M == 0) {
            Label label = new Label();
            label.f4684h = new Frame();
            label.f4684h.f4650b = label;
            label.mo76797a(this, this.f4732r.f4568b, this.f4732r.f4567a);
            this.f4704O.f4685i = label;
            this.f4704O = label;
        } else {
            this.f4705P.f4683g = this.f4707R;
        }
        this.f4705P = null;
    }

    /* renamed from: f */
    private void m4242f() {
        int i;
        int i2;
        int i3;
        int i4;
        int a = m4226a(0, this.f4720f.length() + 1, 0);
        int i5 = this.f4717c;
        if ((i5 & 8) != 0) {
            i = a;
        } else if ((i5 & 524288) == 0) {
            int[] iArr = this.f4739z;
            i = a + 1;
            ClassWriter classWriter = this.f4716b;
            iArr[a] = classWriter.mo76756c(classWriter.f4582I) | 24117248;
        } else {
            i = a + 1;
            this.f4739z[a] = 6;
        }
        int i6 = 1;
        while (true) {
            int i7 = i6 + 1;
            char charAt = this.f4720f.charAt(i6);
            if (charAt == 'F') {
                i2 = i + 1;
                this.f4739z[i] = 2;
            } else if (charAt != 'L') {
                if (!(charAt == 'S' || charAt == 'I')) {
                    if (charAt == 'J') {
                        i2 = i + 1;
                        this.f4739z[i] = 4;
                    } else if (charAt != 'Z') {
                        if (charAt != '[') {
                            switch (charAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i4 = i + 1;
                                    this.f4739z[i] = 3;
                                    break;
                                default:
                                    this.f4739z[1] = i - 3;
                                    m4236b();
                                    return;
                            }
                        } else {
                            while (this.f4720f.charAt(i7) == '[') {
                                i7++;
                            }
                            if (this.f4720f.charAt(i7) == 'L') {
                                do {
                                    i7++;
                                } while (this.f4720f.charAt(i7) != ';');
                            }
                            i4 = i + 1;
                            i7++;
                            this.f4739z[i] = this.f4716b.mo76756c(this.f4720f.substring(i6, i7)) | 24117248;
                        }
                        i6 = i7;
                        i3 = i4;
                    }
                }
                i2 = i + 1;
                this.f4739z[i] = 1;
            } else {
                int i8 = i7;
                while (this.f4720f.charAt(i8) != ';') {
                    i8++;
                }
                int i9 = i + 1;
                int i10 = i8 + 1;
                this.f4739z[i] = this.f4716b.mo76756c(this.f4720f.substring(i7, i8)) | 24117248;
                i3 = i9;
                i6 = i10;
            }
            i3 = i2;
            i6 = i7;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final int mo76832a() {
        int i;
        if (this.f4722h != 0) {
            return this.f4723i + 6;
        }
        String str = "RuntimeInvisibleTypeAnnotations";
        String str2 = "RuntimeVisibleTypeAnnotations";
        if (this.f4732r.f4568b <= 0) {
            i = 8;
        } else if (this.f4732r.f4568b <= 65536) {
            this.f4716b.newUTF8("Code");
            i = this.f4732r.f4568b + 18 + (this.f4690A * 8) + 8;
            if (this.f4694E != null) {
                this.f4716b.newUTF8("LocalVariableTable");
                i += this.f4694E.f4568b + 8;
            }
            if (this.f4696G != null) {
                this.f4716b.newUTF8("LocalVariableTypeTable");
                i += this.f4696G.f4568b + 8;
            }
            if (this.f4698I != null) {
                this.f4716b.newUTF8("LineNumberTable");
                i += this.f4698I.f4568b + 8;
            }
            if (this.f4736v != null) {
                this.f4716b.newUTF8((this.f4716b.f4589b & 65535) >= 50 ? "StackMapTable" : "StackMap");
                i += this.f4736v.f4568b + 8;
            }
            if (this.f4712W != null) {
                this.f4716b.newUTF8(str2);
                i += this.f4712W.mo76695a() + 8;
            }
            if (this.f4713X != null) {
                this.f4716b.newUTF8(str);
                i += this.f4713X.mo76695a() + 8;
            }
            Attribute attribute = this.f4699J;
            if (attribute != null) {
                i += attribute.mo76698a(this.f4716b, this.f4732r.f4567a, this.f4732r.f4568b, this.f4733s, this.f4734t);
            }
        } else {
            throw new RuntimeException("Method code too large!");
        }
        if (this.f4724j > 0) {
            this.f4716b.newUTF8("Exceptions");
            i += (this.f4724j * 2) + 8;
        }
        if ((this.f4717c & 4096) != 0 && ((65535 & this.f4716b.f4589b) < 49 || (this.f4717c & 262144) != 0)) {
            this.f4716b.newUTF8("Synthetic");
            i += 6;
        }
        if ((this.f4717c & 131072) != 0) {
            this.f4716b.newUTF8("Deprecated");
            i += 6;
        }
        if (this.f4721g != null) {
            this.f4716b.newUTF8("Signature");
            this.f4716b.newUTF8(this.f4721g);
            i += 8;
        }
        if (this.f4689$ != null) {
            this.f4716b.newUTF8("MethodParameters");
            i += this.f4689$.f4568b + 7;
        }
        if (this.f4726l != null) {
            this.f4716b.newUTF8("AnnotationDefault");
            i += this.f4726l.f4568b + 6;
        }
        if (this.f4727m != null) {
            this.f4716b.newUTF8("RuntimeVisibleAnnotations");
            i += this.f4727m.mo76695a() + 8;
        }
        if (this.f4728n != null) {
            this.f4716b.newUTF8("RuntimeInvisibleAnnotations");
            i += this.f4728n.mo76695a() + 8;
        }
        if (this.f4710U != null) {
            this.f4716b.newUTF8(str2);
            i += this.f4710U.mo76695a() + 8;
        }
        if (this.f4711V != null) {
            this.f4716b.newUTF8(str);
            i += this.f4711V.mo76695a() + 8;
        }
        if (this.f4729o != null) {
            this.f4716b.newUTF8("RuntimeVisibleParameterAnnotations");
            AnnotationWriter[] annotationWriterArr = this.f4729o;
            int length = i + ((annotationWriterArr.length - this.f4708S) * 2) + 7;
            for (int length2 = annotationWriterArr.length - 1; length2 >= this.f4708S; length2--) {
                AnnotationWriter[] annotationWriterArr2 = this.f4729o;
                length = i + (annotationWriterArr2[length2] == null ? 0 : annotationWriterArr2[length2].mo76695a());
            }
        }
        if (this.f4730p != null) {
            this.f4716b.newUTF8("RuntimeInvisibleParameterAnnotations");
            AnnotationWriter[] annotationWriterArr3 = this.f4730p;
            int length3 = i + ((annotationWriterArr3.length - this.f4708S) * 2) + 7;
            for (int length4 = annotationWriterArr3.length - 1; length4 >= this.f4708S; length4--) {
                AnnotationWriter[] annotationWriterArr4 = this.f4730p;
                length3 = i + (annotationWriterArr4[length4] == null ? 0 : annotationWriterArr4[length4].mo76695a());
            }
        }
        Attribute attribute2 = this.f4731q;
        if (attribute2 != null) {
            i += attribute2.mo76698a(this.f4716b, null, 0, -1, -1);
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x029b  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x02eb  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x02fe  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x031d  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0346  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0367  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x037b  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x038f  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x03a1  */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x03b3  */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x03c9  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x03df  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo76833a(org.objectweb.asm.ByteVector r23) {
        /*
            r22 = this;
            r0 = r22
            r8 = r23
            int r1 = r0.f4717c
            r9 = 262144(0x40000, float:3.67342E-40)
            r2 = r1 & r9
            int r2 = r2 / 64
            r3 = 917504(0xe0000, float:1.285697E-39)
            r2 = r2 | r3
            r2 = r2 ^ -1
            r1 = r1 & r2
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            int r2 = r0.f4718d
            org.objectweb.asm.ByteVector r1 = r1.putShort(r2)
            int r2 = r0.f4719e
            r1.putShort(r2)
            int r1 = r0.f4722h
            if (r1 == 0) goto L_0x0033
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            org.objectweb.asm.ClassReader r1 = r1.f4586M
            byte[] r1 = r1.f4570b
            int r2 = r0.f4722h
            int r3 = r0.f4723i
            r8.putByteArray(r1, r2, r3)
            return
        L_0x0033:
            org.objectweb.asm.ByteVector r1 = r0.f4732r
            int r1 = r1.f4568b
            r10 = 0
            if (r1 <= 0) goto L_0x003c
            r1 = 1
            goto L_0x003d
        L_0x003c:
            r1 = 0
        L_0x003d:
            int r2 = r0.f4724j
            if (r2 <= 0) goto L_0x0043
            int r1 = r1 + 1
        L_0x0043:
            int r2 = r0.f4717c
            r2 = r2 & 4096(0x1000, float:5.74E-42)
            r12 = 49
            r13 = 65535(0xffff, float:9.1834E-41)
            if (r2 == 0) goto L_0x005c
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            int r2 = r2.f4589b
            r2 = r2 & r13
            if (r2 < r12) goto L_0x005a
            int r2 = r0.f4717c
            r2 = r2 & r9
            if (r2 == 0) goto L_0x005c
        L_0x005a:
            int r1 = r1 + 1
        L_0x005c:
            int r2 = r0.f4717c
            r14 = 131072(0x20000, float:1.83671E-40)
            r2 = r2 & r14
            if (r2 == 0) goto L_0x0065
            int r1 = r1 + 1
        L_0x0065:
            java.lang.String r2 = r0.f4721g
            if (r2 == 0) goto L_0x006b
            int r1 = r1 + 1
        L_0x006b:
            org.objectweb.asm.ByteVector r2 = r0.f4689$
            if (r2 == 0) goto L_0x0071
            int r1 = r1 + 1
        L_0x0071:
            org.objectweb.asm.ByteVector r2 = r0.f4726l
            if (r2 == 0) goto L_0x0077
            int r1 = r1 + 1
        L_0x0077:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4727m
            if (r2 == 0) goto L_0x007d
            int r1 = r1 + 1
        L_0x007d:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4728n
            if (r2 == 0) goto L_0x0083
            int r1 = r1 + 1
        L_0x0083:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4710U
            if (r2 == 0) goto L_0x0089
            int r1 = r1 + 1
        L_0x0089:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4711V
            if (r2 == 0) goto L_0x008f
            int r1 = r1 + 1
        L_0x008f:
            org.objectweb.asm.AnnotationWriter[] r2 = r0.f4729o
            if (r2 == 0) goto L_0x0095
            int r1 = r1 + 1
        L_0x0095:
            org.objectweb.asm.AnnotationWriter[] r2 = r0.f4730p
            if (r2 == 0) goto L_0x009b
            int r1 = r1 + 1
        L_0x009b:
            org.objectweb.asm.Attribute r2 = r0.f4731q
            if (r2 == 0) goto L_0x00a4
            int r2 = r2.mo76697a()
            int r1 = r1 + r2
        L_0x00a4:
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4732r
            int r1 = r1.f4568b
            java.lang.String r15 = "RuntimeInvisibleTypeAnnotations"
            java.lang.String r7 = "RuntimeVisibleTypeAnnotations"
            r6 = 2
            if (r1 <= 0) goto L_0x0295
            org.objectweb.asm.ByteVector r1 = r0.f4732r
            int r1 = r1.f4568b
            int r1 = r1 + 12
            int r2 = r0.f4690A
            int r2 = r2 * 8
            int r1 = r1 + r2
            org.objectweb.asm.ByteVector r2 = r0.f4694E
            if (r2 == 0) goto L_0x00c6
            int r2 = r2.f4568b
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00c6:
            org.objectweb.asm.ByteVector r2 = r0.f4696G
            if (r2 == 0) goto L_0x00cf
            int r2 = r2.f4568b
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00cf:
            org.objectweb.asm.ByteVector r2 = r0.f4698I
            if (r2 == 0) goto L_0x00d8
            int r2 = r2.f4568b
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00d8:
            org.objectweb.asm.ByteVector r2 = r0.f4736v
            if (r2 == 0) goto L_0x00e1
            int r2 = r2.f4568b
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00e1:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4712W
            if (r2 == 0) goto L_0x00ec
            int r2 = r2.mo76695a()
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00ec:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4713X
            if (r2 == 0) goto L_0x00f7
            int r2 = r2.mo76695a()
            int r2 = r2 + 8
            int r1 = r1 + r2
        L_0x00f7:
            org.objectweb.asm.Attribute r2 = r0.f4699J
            if (r2 == 0) goto L_0x011a
            org.objectweb.asm.ClassWriter r3 = r0.f4716b
            org.objectweb.asm.ByteVector r4 = r0.f4732r
            byte[] r4 = r4.f4567a
            org.objectweb.asm.ByteVector r5 = r0.f4732r
            int r5 = r5.f4568b
            int r11 = r0.f4733s
            int r14 = r0.f4734t
            r16 = r2
            r17 = r3
            r18 = r4
            r19 = r5
            r20 = r11
            r21 = r14
            int r2 = r16.mo76698a(r17, r18, r19, r20, r21)
            int r1 = r1 + r2
        L_0x011a:
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            java.lang.String r3 = "Code"
            int r2 = r2.newUTF8(r3)
            org.objectweb.asm.ByteVector r2 = r8.putShort(r2)
            r2.putInt(r1)
            int r1 = r0.f4733s
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            int r2 = r0.f4734t
            r1.putShort(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4732r
            int r1 = r1.f4568b
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            org.objectweb.asm.ByteVector r2 = r0.f4732r
            byte[] r2 = r2.f4567a
            org.objectweb.asm.ByteVector r3 = r0.f4732r
            int r3 = r3.f4568b
            r1.putByteArray(r2, r10, r3)
            int r1 = r0.f4690A
            r8.putShort(r1)
            int r1 = r0.f4690A
            if (r1 <= 0) goto L_0x0174
            org.objectweb.asm.Handler r1 = r0.f4691B
        L_0x0152:
            if (r1 == 0) goto L_0x0174
            org.objectweb.asm.Label r2 = r1.f4662a
            int r2 = r2.f4679c
            org.objectweb.asm.ByteVector r2 = r8.putShort(r2)
            org.objectweb.asm.Label r3 = r1.f4663b
            int r3 = r3.f4679c
            org.objectweb.asm.ByteVector r2 = r2.putShort(r3)
            org.objectweb.asm.Label r3 = r1.f4664c
            int r3 = r3.f4679c
            org.objectweb.asm.ByteVector r2 = r2.putShort(r3)
            int r3 = r1.f4666e
            r2.putShort(r3)
            org.objectweb.asm.Handler r1 = r1.f4667f
            goto L_0x0152
        L_0x0174:
            org.objectweb.asm.ByteVector r1 = r0.f4694E
            if (r1 == 0) goto L_0x017a
            r1 = 1
            goto L_0x017b
        L_0x017a:
            r1 = 0
        L_0x017b:
            org.objectweb.asm.ByteVector r2 = r0.f4696G
            if (r2 == 0) goto L_0x0181
            int r1 = r1 + 1
        L_0x0181:
            org.objectweb.asm.ByteVector r2 = r0.f4698I
            if (r2 == 0) goto L_0x0187
            int r1 = r1 + 1
        L_0x0187:
            org.objectweb.asm.ByteVector r2 = r0.f4736v
            if (r2 == 0) goto L_0x018d
            int r1 = r1 + 1
        L_0x018d:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4712W
            if (r2 == 0) goto L_0x0193
            int r1 = r1 + 1
        L_0x0193:
            org.objectweb.asm.AnnotationWriter r2 = r0.f4713X
            if (r2 == 0) goto L_0x0199
            int r1 = r1 + 1
        L_0x0199:
            org.objectweb.asm.Attribute r2 = r0.f4699J
            if (r2 == 0) goto L_0x01a2
            int r2 = r2.mo76697a()
            int r1 = r1 + r2
        L_0x01a2:
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4694E
            if (r1 == 0) goto L_0x01cd
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "LocalVariableTable"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4694E
            int r1 = r1.f4568b
            int r1 = r1 + r6
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            int r2 = r0.f4693D
            r1.putShort(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4694E
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4694E
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x01cd:
            org.objectweb.asm.ByteVector r1 = r0.f4696G
            if (r1 == 0) goto L_0x01f5
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "LocalVariableTypeTable"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4696G
            int r1 = r1.f4568b
            int r1 = r1 + r6
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            int r2 = r0.f4695F
            r1.putShort(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4696G
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4696G
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x01f5:
            org.objectweb.asm.ByteVector r1 = r0.f4698I
            if (r1 == 0) goto L_0x021d
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "LineNumberTable"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4698I
            int r1 = r1.f4568b
            int r1 = r1 + r6
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            int r2 = r0.f4697H
            r1.putShort(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4698I
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4698I
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x021d:
            org.objectweb.asm.ByteVector r1 = r0.f4736v
            if (r1 == 0) goto L_0x0256
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.f4589b
            r1 = r1 & r13
            r2 = 50
            if (r1 < r2) goto L_0x022c
            r1 = 1
            goto L_0x022d
        L_0x022c:
            r1 = 0
        L_0x022d:
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            if (r1 == 0) goto L_0x0234
            java.lang.String r1 = "StackMapTable"
            goto L_0x0236
        L_0x0234:
            java.lang.String r1 = "StackMap"
        L_0x0236:
            int r1 = r2.newUTF8(r1)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4736v
            int r1 = r1.f4568b
            int r1 = r1 + r6
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            int r2 = r0.f4735u
            r1.putShort(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4736v
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4736v
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x0256:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4712W
            if (r1 == 0) goto L_0x0268
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.newUTF8(r7)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4712W
            r1.mo76696a(r8)
        L_0x0268:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4713X
            if (r1 == 0) goto L_0x027a
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.newUTF8(r15)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4713X
            r1.mo76696a(r8)
        L_0x027a:
            org.objectweb.asm.Attribute r1 = r0.f4699J
            if (r1 == 0) goto L_0x0295
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            org.objectweb.asm.ByteVector r3 = r0.f4732r
            byte[] r3 = r3.f4567a
            org.objectweb.asm.ByteVector r4 = r0.f4732r
            int r4 = r4.f4568b
            int r5 = r0.f4734t
            int r11 = r0.f4733s
            r14 = 2
            r6 = r11
            r11 = r7
            r7 = r23
            r1.mo76699a(r2, r3, r4, r5, r6, r7)
            goto L_0x0297
        L_0x0295:
            r11 = r7
            r14 = 2
        L_0x0297:
            int r1 = r0.f4724j
            if (r1 <= 0) goto L_0x02c3
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "Exceptions"
            int r1 = r1.newUTF8(r2)
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            int r2 = r0.f4724j
            int r2 = r2 * 2
            int r2 = r2 + r14
            r1.putInt(r2)
            int r1 = r0.f4724j
            r8.putShort(r1)
            r1 = 0
        L_0x02b5:
            int r2 = r0.f4724j
            if (r1 >= r2) goto L_0x02c3
            int[] r2 = r0.f4725k
            r2 = r2[r1]
            r8.putShort(r2)
            int r1 = r1 + 1
            goto L_0x02b5
        L_0x02c3:
            int r1 = r0.f4717c
            r1 = r1 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L_0x02e4
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.f4589b
            r1 = r1 & r13
            if (r1 < r12) goto L_0x02d5
            int r1 = r0.f4717c
            r1 = r1 & r9
            if (r1 == 0) goto L_0x02e4
        L_0x02d5:
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "Synthetic"
            int r1 = r1.newUTF8(r2)
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            r1.putInt(r10)
        L_0x02e4:
            int r1 = r0.f4717c
            r2 = 131072(0x20000, float:1.83671E-40)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x02fa
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "Deprecated"
            int r1 = r1.newUTF8(r2)
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            r1.putInt(r10)
        L_0x02fa:
            java.lang.String r1 = r0.f4721g
            if (r1 == 0) goto L_0x0319
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "Signature"
            int r1 = r1.newUTF8(r2)
            org.objectweb.asm.ByteVector r1 = r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r1.putInt(r14)
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            java.lang.String r3 = r0.f4721g
            int r2 = r2.newUTF8(r3)
            r1.putShort(r2)
        L_0x0319:
            org.objectweb.asm.ByteVector r1 = r0.f4689$
            if (r1 == 0) goto L_0x0342
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "MethodParameters"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4689$
            int r1 = r1.f4568b
            r2 = 1
            int r1 = r1 + r2
            org.objectweb.asm.ByteVector r1 = r8.putInt(r1)
            int r2 = r0.f4715Z
            r1.putByte(r2)
            org.objectweb.asm.ByteVector r1 = r0.f4689$
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4689$
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x0342:
            org.objectweb.asm.ByteVector r1 = r0.f4726l
            if (r1 == 0) goto L_0x0363
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "AnnotationDefault"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4726l
            int r1 = r1.f4568b
            r8.putInt(r1)
            org.objectweb.asm.ByteVector r1 = r0.f4726l
            byte[] r1 = r1.f4567a
            org.objectweb.asm.ByteVector r2 = r0.f4726l
            int r2 = r2.f4568b
            r8.putByteArray(r1, r10, r2)
        L_0x0363:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4727m
            if (r1 == 0) goto L_0x0377
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "RuntimeVisibleAnnotations"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4727m
            r1.mo76696a(r8)
        L_0x0377:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4728n
            if (r1 == 0) goto L_0x038b
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "RuntimeInvisibleAnnotations"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4728n
            r1.mo76696a(r8)
        L_0x038b:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4710U
            if (r1 == 0) goto L_0x039d
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.newUTF8(r11)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4710U
            r1.mo76696a(r8)
        L_0x039d:
            org.objectweb.asm.AnnotationWriter r1 = r0.f4711V
            if (r1 == 0) goto L_0x03af
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            int r1 = r1.newUTF8(r15)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter r1 = r0.f4711V
            r1.mo76696a(r8)
        L_0x03af:
            org.objectweb.asm.AnnotationWriter[] r1 = r0.f4729o
            if (r1 == 0) goto L_0x03c5
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "RuntimeVisibleParameterAnnotations"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter[] r1 = r0.f4729o
            int r2 = r0.f4708S
            org.objectweb.asm.AnnotationWriter.m4145a(r1, r2, r8)
        L_0x03c5:
            org.objectweb.asm.AnnotationWriter[] r1 = r0.f4730p
            if (r1 == 0) goto L_0x03db
            org.objectweb.asm.ClassWriter r1 = r0.f4716b
            java.lang.String r2 = "RuntimeInvisibleParameterAnnotations"
            int r1 = r1.newUTF8(r2)
            r8.putShort(r1)
            org.objectweb.asm.AnnotationWriter[] r1 = r0.f4730p
            int r2 = r0.f4708S
            org.objectweb.asm.AnnotationWriter.m4145a(r1, r2, r8)
        L_0x03db:
            org.objectweb.asm.Attribute r1 = r0.f4731q
            if (r1 == 0) goto L_0x03ea
            org.objectweb.asm.ClassWriter r2 = r0.f4716b
            r3 = 0
            r4 = 0
            r5 = -1
            r6 = -1
            r7 = r23
            r1.mo76699a(r2, r3, r4, r5, r6, r7)
        L_0x03ea:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.MethodWriter.mo76833a(org.objectweb.asm.ByteVector):void");
    }

    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.f4563g = this.f4727m;
            this.f4727m = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4728n;
            this.f4728n = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitAnnotationDefault() {
        this.f4726l = new ByteVector();
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, false, this.f4726l, null, 0);
        return annotationWriter;
    }

    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.f4565a = this.f4699J;
            this.f4699J = attribute;
            return;
        }
        attribute.f4565a = this.f4731q;
        this.f4731q = attribute;
    }

    public void visitCode() {
    }

    public void visitEnd() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        r0 = r0 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        r0 = r0 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
        if (r0 <= r4.f4707R) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0052, code lost:
        r4.f4707R = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0054, code lost:
        r4.f4706Q = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void visitFieldInsn(int r5, java.lang.String r6, java.lang.String r7, java.lang.String r8) {
        /*
            r4 = this;
            org.objectweb.asm.ByteVector r0 = r4.f4732r
            int r0 = r0.f4568b
            r4.f4714Y = r0
            org.objectweb.asm.ClassWriter r0 = r4.f4716b
            org.objectweb.asm.Item r6 = r0.mo76753a(r6, r7, r8)
            org.objectweb.asm.Label r7 = r4.f4705P
            if (r7 == 0) goto L_0x0056
            int r0 = r4.f4702M
            r1 = 0
            if (r0 != 0) goto L_0x001d
            org.objectweb.asm.Frame r7 = r7.f4684h
            org.objectweb.asm.ClassWriter r8 = r4.f4716b
            r7.mo76774a(r5, r1, r8, r6)
            goto L_0x0056
        L_0x001d:
            char r7 = r8.charAt(r1)
            r8 = 1
            r0 = -2
            r2 = 74
            r3 = 68
            switch(r5) {
                case 178: goto L_0x0043;
                case 179: goto L_0x003a;
                case 180: goto L_0x0031;
                default: goto L_0x002a;
            }
        L_0x002a:
            int r8 = r4.f4706Q
            if (r7 == r3) goto L_0x004c
            if (r7 != r2) goto L_0x004d
            goto L_0x004c
        L_0x0031:
            int r0 = r4.f4706Q
            if (r7 == r3) goto L_0x004a
            if (r7 != r2) goto L_0x0038
            goto L_0x004a
        L_0x0038:
            r8 = 0
            goto L_0x004a
        L_0x003a:
            int r8 = r4.f4706Q
            if (r7 == r3) goto L_0x004d
            if (r7 != r2) goto L_0x0041
            goto L_0x004d
        L_0x0041:
            r0 = -1
            goto L_0x004d
        L_0x0043:
            int r0 = r4.f4706Q
            if (r7 == r3) goto L_0x0049
            if (r7 != r2) goto L_0x004a
        L_0x0049:
            r8 = 2
        L_0x004a:
            int r0 = r0 + r8
            goto L_0x004e
        L_0x004c:
            r0 = -3
        L_0x004d:
            int r0 = r0 + r8
        L_0x004e:
            int r7 = r4.f4707R
            if (r0 <= r7) goto L_0x0054
            r4.f4707R = r0
        L_0x0054:
            r4.f4706Q = r0
        L_0x0056:
            org.objectweb.asm.ByteVector r7 = r4.f4732r
            int r6 = r6.f4668a
            r7.mo76706b(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.MethodWriter.visitFieldInsn(int, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        ByteVector byteVector;
        String str;
        int i5;
        int i6;
        if (this.f4702M != 0) {
            int i7 = 0;
            if (i == -1) {
                if (this.f4738x == null) {
                    m4242f();
                }
                this.f4709T = i2;
                int a = m4226a(this.f4732r.f4568b, i2, i3);
                int i8 = 0;
                while (true) {
                    str = "";
                    if (i8 >= i2) {
                        break;
                    }
                    if (objArr[i8] instanceof String) {
                        i6 = a + 1;
                        this.f4739z[a] = 24117248 | this.f4716b.mo76756c(objArr[i8]);
                    } else if (objArr[i8] instanceof Integer) {
                        i6 = a + 1;
                        this.f4739z[a] = objArr[i8].intValue();
                    } else {
                        int i9 = a + 1;
                        this.f4739z[a] = this.f4716b.mo76744a(str, objArr[i8].f4679c) | 25165824;
                        a = i9;
                        i8++;
                    }
                    a = i6;
                    i8++;
                }
                while (i7 < i3) {
                    if (objArr2[i7] instanceof String) {
                        i5 = a + 1;
                        this.f4739z[a] = this.f4716b.mo76756c(objArr2[i7]) | 24117248;
                    } else if (objArr2[i7] instanceof Integer) {
                        i5 = a + 1;
                        this.f4739z[a] = objArr2[i7].intValue();
                    } else {
                        i5 = a + 1;
                        this.f4739z[a] = this.f4716b.mo76744a(str, objArr2[i7].f4679c) | 25165824;
                    }
                    a = i5;
                    i7++;
                }
                m4236b();
            } else {
                if (this.f4736v == null) {
                    this.f4736v = new ByteVector();
                    i4 = this.f4732r.f4568b;
                } else {
                    i4 = (this.f4732r.f4568b - this.f4737w) - 1;
                    if (i4 < 0) {
                        if (i != 3) {
                            throw new IllegalStateException();
                        }
                        return;
                    }
                }
                if (i == 0) {
                    this.f4709T = i2;
                    this.f4736v.putByte(255).putShort(i4).putShort(i2);
                    for (int i10 = 0; i10 < i2; i10++) {
                        m4231a(objArr[i10]);
                    }
                    this.f4736v.putShort(i3);
                    while (i7 < i3) {
                        m4231a(objArr2[i7]);
                        i7++;
                    }
                } else if (i != 1) {
                    int i11 = 251;
                    if (i == 2) {
                        this.f4709T -= i2;
                        byteVector = this.f4736v;
                        i11 = 251 - i2;
                    } else if (i == 3) {
                        byteVector = this.f4736v;
                        if (i4 < 64) {
                            byteVector.putByte(i4);
                        }
                    } else if (i == 4) {
                        ByteVector byteVector2 = this.f4736v;
                        if (i4 < 64) {
                            byteVector2.putByte(i4 + 64);
                        } else {
                            byteVector2.putByte(247).putShort(i4);
                        }
                        m4231a(objArr2[0]);
                    }
                    byteVector.putByte(i11).putShort(i4);
                } else {
                    this.f4709T += i2;
                    this.f4736v.putByte(i2 + 251).putShort(i4);
                    while (i7 < i2) {
                        m4231a(objArr[i7]);
                        i7++;
                    }
                }
                this.f4737w = this.f4732r.f4568b;
                this.f4735u++;
            }
            this.f4733s = Math.max(this.f4733s, i3);
            this.f4734t = Math.max(this.f4734t, this.f4709T);
        }
    }

    public void visitIincInsn(int i, int i2) {
        this.f4714Y = this.f4732r.f4568b;
        Label label = this.f4705P;
        if (label != null && this.f4702M == 0) {
            label.f4684h.mo76774a((int) Opcodes.IINC, i, (ClassWriter) null, (Item) null);
        }
        if (this.f4702M != 2) {
            int i3 = i + 1;
            if (i3 > this.f4734t) {
                this.f4734t = i3;
            }
        }
        if (i > 255 || i2 > 127 || i2 < -128) {
            this.f4732r.putByte(196).mo76706b(Opcodes.IINC, i).putShort(i2);
        } else {
            this.f4732r.putByte(Opcodes.IINC).mo76705a(i, i2);
        }
    }

    public void visitInsn(int i) {
        this.f4714Y = this.f4732r.f4568b;
        this.f4732r.putByte(i);
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(i, 0, (ClassWriter) null, (Item) null);
            } else {
                int i2 = this.f4706Q + Frame.f4649a[i];
                if (i2 > this.f4707R) {
                    this.f4707R = i2;
                }
                this.f4706Q = i2;
            }
            if ((i >= 172 && i <= 177) || i == 191) {
                m4241e();
            }
        }
    }

    public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.m4144a((i & -16776961) | (this.f4714Y << 8), typePath, byteVector);
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4712W;
            this.f4712W = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4713X;
            this.f4713X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitIntInsn(int i, int i2) {
        this.f4714Y = this.f4732r.f4568b;
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i != 188) {
                int i3 = this.f4706Q + 1;
                if (i3 > this.f4707R) {
                    this.f4707R = i3;
                }
                this.f4706Q = i3;
            }
        }
        if (i == 17) {
            this.f4732r.mo76706b(i, i2);
        } else {
            this.f4732r.mo76705a(i, i2);
        }
    }

    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        this.f4714Y = this.f4732r.f4568b;
        Item a = this.f4716b.mo76755a(str, str2, handle, objArr);
        int i = a.f4670c;
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a((int) Opcodes.INVOKEDYNAMIC, 0, this.f4716b, a);
            } else {
                if (i == 0) {
                    i = Type.getArgumentsAndReturnSizes(str2);
                    a.f4670c = i;
                }
                int i2 = (this.f4706Q - (i >> 2)) + (i & 3) + 1;
                if (i2 > this.f4707R) {
                    this.f4707R = i2;
                }
                this.f4706Q = i2;
            }
        }
        this.f4732r.mo76706b(Opcodes.INVOKEDYNAMIC, a.f4668a);
        this.f4732r.putShort(0);
    }

    public void visitJumpInsn(int i, Label label) {
        this.f4714Y = this.f4732r.f4568b;
        Label label2 = this.f4705P;
        Label label3 = null;
        if (label2 != null) {
            if (this.f4702M == 0) {
                label2.f4684h.mo76774a(i, 0, (ClassWriter) null, (Item) null);
                Label a = label.mo76792a();
                a.f4677a |= 16;
                m4230a(0, label);
                if (i != 167) {
                    label3 = new Label();
                }
            } else if (i == 168) {
                if ((label.f4677a & 512) == 0) {
                    label.f4677a |= 512;
                    this.f4701L++;
                }
                this.f4705P.f4677a |= 128;
                m4230a(this.f4706Q + 1, label);
                label3 = new Label();
            } else {
                this.f4706Q += Frame.f4649a[i];
                m4230a(this.f4706Q, label);
            }
        }
        if ((label.f4677a & 2) == 0 || label.f4679c - this.f4732r.f4568b >= -32768) {
            this.f4732r.putByte(i);
            ByteVector byteVector = this.f4732r;
            label.mo76794a(this, byteVector, byteVector.f4568b - 1, false);
        } else {
            if (i != 167) {
                if (i == 168) {
                    this.f4732r.putByte(201);
                    ByteVector byteVector2 = this.f4732r;
                    label.mo76794a(this, byteVector2, byteVector2.f4568b - 1, true);
                } else {
                    if (label3 != null) {
                        label3.f4677a |= 16;
                    }
                    this.f4732r.putByte(i <= 166 ? ((i + 1) ^ 1) - 1 : i ^ 1);
                    this.f4732r.putShort(8);
                }
            }
            this.f4732r.putByte(200);
            ByteVector byteVector22 = this.f4732r;
            label.mo76794a(this, byteVector22, byteVector22.f4568b - 1, true);
        }
        if (this.f4705P != null) {
            if (label3 != null) {
                visitLabel(label3);
            }
            if (i == 167) {
                m4241e();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008b, code lost:
        if (r0 != null) goto L_0x008d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void visitLabel(org.objectweb.asm.Label r4) {
        /*
            r3 = this;
            boolean r0 = r3.f4700K
            org.objectweb.asm.ByteVector r1 = r3.f4732r
            int r1 = r1.f4568b
            org.objectweb.asm.ByteVector r2 = r3.f4732r
            byte[] r2 = r2.f4567a
            boolean r1 = r4.mo76797a(r3, r1, r2)
            r0 = r0 | r1
            r3.f4700K = r0
            int r0 = r4.f4677a
            r1 = 1
            r0 = r0 & r1
            if (r0 == 0) goto L_0x0018
            return
        L_0x0018:
            int r0 = r3.f4702M
            r2 = 0
            if (r0 != 0) goto L_0x0074
            org.objectweb.asm.Label r0 = r3.f4705P
            if (r0 == 0) goto L_0x003e
            int r0 = r4.f4679c
            org.objectweb.asm.Label r1 = r3.f4705P
            int r1 = r1.f4679c
            if (r0 != r1) goto L_0x003b
            org.objectweb.asm.Label r0 = r3.f4705P
            int r1 = r0.f4677a
            int r2 = r4.f4677a
            r2 = r2 & 16
            r1 = r1 | r2
            r0.f4677a = r1
            org.objectweb.asm.Label r0 = r3.f4705P
            org.objectweb.asm.Frame r0 = r0.f4684h
            r4.f4684h = r0
            return
        L_0x003b:
            r3.m4230a(r2, r4)
        L_0x003e:
            r3.f4705P = r4
            org.objectweb.asm.Frame r0 = r4.f4684h
            if (r0 != 0) goto L_0x004f
            org.objectweb.asm.Frame r0 = new org.objectweb.asm.Frame
            r0.<init>()
            r4.f4684h = r0
            org.objectweb.asm.Frame r0 = r4.f4684h
            r0.f4650b = r4
        L_0x004f:
            org.objectweb.asm.Label r0 = r3.f4704O
            if (r0 == 0) goto L_0x008f
            int r0 = r4.f4679c
            org.objectweb.asm.Label r1 = r3.f4704O
            int r1 = r1.f4679c
            if (r0 != r1) goto L_0x0071
            org.objectweb.asm.Label r0 = r3.f4704O
            int r1 = r0.f4677a
            int r2 = r4.f4677a
            r2 = r2 & 16
            r1 = r1 | r2
            r0.f4677a = r1
            org.objectweb.asm.Label r0 = r3.f4704O
            org.objectweb.asm.Frame r0 = r0.f4684h
            r4.f4684h = r0
            org.objectweb.asm.Label r4 = r3.f4704O
            r3.f4705P = r4
            return
        L_0x0071:
            org.objectweb.asm.Label r0 = r3.f4704O
            goto L_0x008d
        L_0x0074:
            if (r0 != r1) goto L_0x0091
            org.objectweb.asm.Label r0 = r3.f4705P
            if (r0 == 0) goto L_0x0083
            int r1 = r3.f4707R
            r0.f4683g = r1
            int r0 = r3.f4706Q
            r3.m4230a(r0, r4)
        L_0x0083:
            r3.f4705P = r4
            r3.f4706Q = r2
            r3.f4707R = r2
            org.objectweb.asm.Label r0 = r3.f4704O
            if (r0 == 0) goto L_0x008f
        L_0x008d:
            r0.f4685i = r4
        L_0x008f:
            r3.f4704O = r4
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.MethodWriter.visitLabel(org.objectweb.asm.Label):void");
    }

    public void visitLdcInsn(Object obj) {
        ByteVector byteVector;
        int i;
        this.f4714Y = this.f4732r.f4568b;
        Item a = this.f4716b.mo76750a(obj);
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(18, 0, this.f4716b, a);
            } else {
                int i2 = (a.f4669b == 5 || a.f4669b == 6) ? this.f4706Q + 2 : this.f4706Q + 1;
                if (i2 > this.f4707R) {
                    this.f4707R = i2;
                }
                this.f4706Q = i2;
            }
        }
        int i3 = a.f4668a;
        if (a.f4669b == 5 || a.f4669b == 6) {
            byteVector = this.f4732r;
            i = 20;
        } else if (i3 >= 256) {
            byteVector = this.f4732r;
            i = 19;
        } else {
            this.f4732r.mo76705a(18, i3);
            return;
        }
        byteVector.mo76706b(i, i3);
    }

    public void visitLineNumber(int i, Label label) {
        if (this.f4698I == null) {
            this.f4698I = new ByteVector();
        }
        this.f4697H++;
        this.f4698I.putShort(label.f4679c);
        this.f4698I.putShort(i);
    }

    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (str3 != null) {
            if (this.f4696G == null) {
                this.f4696G = new ByteVector();
            }
            this.f4695F++;
            this.f4696G.putShort(label.f4679c).putShort(label2.f4679c - label.f4679c).putShort(this.f4716b.newUTF8(str)).putShort(this.f4716b.newUTF8(str3)).putShort(i);
        }
        if (this.f4694E == null) {
            this.f4694E = new ByteVector();
        }
        this.f4693D++;
        this.f4694E.putShort(label.f4679c).putShort(label2.f4679c - label.f4679c).putShort(this.f4716b.newUTF8(str)).putShort(this.f4716b.newUTF8(str2)).putShort(i);
        int i2 = 2;
        if (this.f4702M != 2) {
            char charAt = str2.charAt(0);
            if (!(charAt == 'J' || charAt == 'D')) {
                i2 = 1;
            }
            int i3 = i + i2;
            if (i3 > this.f4734t) {
                this.f4734t = i3;
            }
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labelArr, Label[] labelArr2, int[] iArr, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(i >>> 24).putShort(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            byteVector.putShort(labelArr[i2].f4679c).putShort(labelArr2[i2].f4679c - labelArr[i2].f4679c).putShort(iArr[i2]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            byteVector.putByteArray(typePath.f4744a, typePath.f4745b, (typePath.f4744a[typePath.f4745b] * 2) + 1);
        }
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4712W;
            this.f4712W = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4713X;
            this.f4713X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        this.f4714Y = this.f4732r.f4568b;
        int i = this.f4732r.f4568b;
        this.f4732r.putByte(Opcodes.LOOKUPSWITCH);
        ByteVector byteVector = this.f4732r;
        byteVector.putByteArray(null, 0, (4 - (byteVector.f4568b % 4)) % 4);
        label.mo76794a(this, this.f4732r, i, true);
        this.f4732r.putInt(labelArr.length);
        for (int i2 = 0; i2 < labelArr.length; i2++) {
            this.f4732r.putInt(iArr[i2]);
            labelArr[i2].mo76794a(this, this.f4732r, i, true);
        }
        m4232a(label, labelArr);
    }

    public void visitMaxs(int i, int i2) {
        String str;
        if (this.f4700K) {
            m4240d();
        }
        int i3 = this.f4702M;
        int i4 = 0;
        if (i3 == 0) {
            Handler handler = this.f4691B;
            while (true) {
                str = "java/lang/Throwable";
                if (handler == null) {
                    break;
                }
                Label a = handler.f4664c.mo76792a();
                Label a2 = handler.f4663b.mo76792a();
                if (handler.f4665d != null) {
                    str = handler.f4665d;
                }
                int c = 24117248 | this.f4716b.mo76756c(str);
                a.f4677a |= 16;
                for (Label a3 = handler.f4662a.mo76792a(); a3 != a2; a3 = a3.f4685i) {
                    Edge edge = new Edge();
                    edge.f4634a = c;
                    edge.f4635b = a;
                    edge.f4636c = a3.f4686j;
                    a3.f4686j = edge;
                }
                handler = handler.f4667f;
            }
            Frame frame = this.f4703N.f4684h;
            frame.mo76775a(this.f4716b, this.f4717c, Type.getArgumentTypes(this.f4720f), this.f4734t);
            m4237b(frame);
            Label label = this.f4703N;
            int i5 = 0;
            while (label != null) {
                Label label2 = label.f4687k;
                label.f4687k = null;
                Frame frame2 = label.f4684h;
                if ((label.f4677a & 16) != 0) {
                    label.f4677a |= 32;
                }
                label.f4677a |= 64;
                int length = frame2.f4652d.length + label.f4683g;
                if (length > i5) {
                    i5 = length;
                }
                for (Edge edge2 = label.f4686j; edge2 != null; edge2 = edge2.f4636c) {
                    Label a4 = edge2.f4635b.mo76792a();
                    if (frame2.mo76776a(this.f4716b, a4.f4684h, edge2.f4634a) && a4.f4687k == null) {
                        a4.f4687k = label2;
                        label2 = a4;
                    }
                }
                label = label2;
            }
            for (Label label3 = this.f4703N; label3 != null; label3 = label3.f4685i) {
                Frame frame3 = label3.f4684h;
                if ((label3.f4677a & 32) != 0) {
                    m4237b(frame3);
                }
                if ((label3.f4677a & 64) == 0) {
                    Label label4 = label3.f4685i;
                    int i6 = label3.f4679c;
                    int i7 = (label4 == null ? this.f4732r.f4568b : label4.f4679c) - 1;
                    if (i7 >= i6) {
                        i5 = Math.max(i5, 1);
                        for (int i8 = i6; i8 < i7; i8++) {
                            this.f4732r.f4567a[i8] = 0;
                        }
                        this.f4732r.f4567a[i7] = ByteSourceJsonBootstrapper.UTF8_BOM_3;
                        this.f4739z[m4226a(i6, 0, 1)] = this.f4716b.mo76756c(str) | 24117248;
                        m4236b();
                        this.f4691B = Handler.m4209a(this.f4691B, label3, label4);
                    }
                }
            }
            this.f4690A = 0;
            for (Handler handler2 = this.f4691B; handler2 != null; handler2 = handler2.f4667f) {
                this.f4690A++;
            }
            this.f4733s = i5;
        } else if (i3 == 1) {
            for (Handler handler3 = this.f4691B; handler3 != null; handler3 = handler3.f4667f) {
                Label label5 = handler3.f4664c;
                Label label6 = handler3.f4663b;
                for (Label label7 = handler3.f4662a; label7 != label6; label7 = label7.f4685i) {
                    Edge edge3 = new Edge();
                    edge3.f4634a = Integer.MAX_VALUE;
                    edge3.f4635b = label5;
                    if ((label7.f4677a & 128) == 0) {
                        edge3.f4636c = label7.f4686j;
                        label7.f4686j = edge3;
                    } else {
                        edge3.f4636c = label7.f4686j.f4636c.f4636c;
                        label7.f4686j.f4636c.f4636c = edge3;
                    }
                }
            }
            int i9 = this.f4701L;
            if (i9 > 0) {
                this.f4703N.mo76798b(null, 1, i9);
                int i10 = 0;
                for (Label label8 = this.f4703N; label8 != null; label8 = label8.f4685i) {
                    if ((label8.f4677a & 128) != 0) {
                        Label label9 = label8.f4686j.f4636c.f4635b;
                        if ((label9.f4677a & 1024) == 0) {
                            i10++;
                            label9.mo76798b(null, ((((long) i10) / 32) << 32) | (1 << (i10 % 32)), this.f4701L);
                        }
                    }
                }
                for (Label label10 = this.f4703N; label10 != null; label10 = label10.f4685i) {
                    if ((label10.f4677a & 128) != 0) {
                        Label label11 = this.f4703N;
                        while (label11 != null) {
                            label11.f4677a &= -2049;
                            label11 = label11.f4685i;
                        }
                        label10.f4686j.f4636c.f4635b.mo76798b(label10, 0, this.f4701L);
                    }
                }
            }
            Label label12 = this.f4703N;
            while (label12 != null) {
                Label label13 = label12.f4687k;
                int i11 = label12.f4682f;
                int i12 = label12.f4683g + i11;
                if (i12 > i4) {
                    i4 = i12;
                }
                Edge edge4 = label12.f4686j;
                if ((label12.f4677a & 128) != 0) {
                    edge4 = edge4.f4636c;
                }
                label12 = label13;
                while (edge4 != null) {
                    Label label14 = edge4.f4635b;
                    if ((label14.f4677a & 8) == 0) {
                        label14.f4682f = edge4.f4634a == Integer.MAX_VALUE ? 1 : edge4.f4634a + i11;
                        label14.f4677a |= 8;
                        label14.f4687k = label12;
                        label12 = label14;
                    }
                    edge4 = edge4.f4636c;
                }
            }
            this.f4733s = Math.max(i, i4);
        } else {
            this.f4733s = i;
            this.f4734t = i2;
        }
    }

    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        this.f4714Y = this.f4732r.f4568b;
        Item a = this.f4716b.mo76754a(str, str2, str3, z);
        int i2 = a.f4670c;
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(i, 0, this.f4716b, a);
            } else {
                if (i2 == 0) {
                    i2 = Type.getArgumentsAndReturnSizes(str3);
                    a.f4670c = i2;
                }
                int i3 = i == 184 ? (this.f4706Q - (i2 >> 2)) + (i2 & 3) + 1 : (this.f4706Q - (i2 >> 2)) + (i2 & 3);
                if (i3 > this.f4707R) {
                    this.f4707R = i3;
                }
                this.f4706Q = i3;
            }
        }
        if (i == 185) {
            if (i2 == 0) {
                i2 = Type.getArgumentsAndReturnSizes(str3);
                a.f4670c = i2;
            }
            this.f4732r.mo76706b(Opcodes.INVOKEINTERFACE, a.f4668a).mo76705a(i2 >> 2, 0);
            return;
        }
        this.f4732r.mo76706b(i, a.f4668a);
    }

    public void visitMultiANewArrayInsn(String str, int i) {
        this.f4714Y = this.f4732r.f4568b;
        Item a = this.f4716b.mo76751a(str);
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a((int) Opcodes.MULTIANEWARRAY, i, this.f4716b, a);
            } else {
                this.f4706Q += 1 - i;
            }
        }
        this.f4732r.mo76706b(Opcodes.MULTIANEWARRAY, a.f4668a).putByte(i);
    }

    public void visitParameter(String str, int i) {
        if (this.f4689$ == null) {
            this.f4689$ = new ByteVector();
        }
        this.f4715Z++;
        this.f4689$.putShort(str == null ? 0 : this.f4716b.newUTF8(str)).putShort(i);
    }

    public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(str)) {
            this.f4708S = Math.max(this.f4708S, i + 1);
            AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, false, byteVector, null, 0);
            return annotationWriter;
        }
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter2 = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, 2);
        if (z) {
            if (this.f4729o == null) {
                this.f4729o = new AnnotationWriter[Type.getArgumentTypes(this.f4720f).length];
            }
            AnnotationWriter[] annotationWriterArr = this.f4729o;
            annotationWriter2.f4563g = annotationWriterArr[i];
            annotationWriterArr[i] = annotationWriter2;
        } else {
            if (this.f4730p == null) {
                this.f4730p = new AnnotationWriter[Type.getArgumentTypes(this.f4720f).length];
            }
            AnnotationWriter[] annotationWriterArr2 = this.f4730p;
            annotationWriter2.f4563g = annotationWriterArr2[i];
            annotationWriterArr2[i] = annotationWriter2;
        }
        return annotationWriter2;
    }

    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        this.f4714Y = this.f4732r.f4568b;
        int i3 = this.f4732r.f4568b;
        this.f4732r.putByte(Opcodes.TABLESWITCH);
        ByteVector byteVector = this.f4732r;
        byteVector.putByteArray(null, 0, (4 - (byteVector.f4568b % 4)) % 4);
        label.mo76794a(this, this.f4732r, i3, true);
        this.f4732r.putInt(i).putInt(i2);
        for (Label a : labelArr) {
            a.mo76794a(this, this.f4732r, i3, true);
        }
        m4232a(label, labelArr);
    }

    public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.m4144a(i, typePath, byteVector);
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4712W;
            this.f4712W = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4713X;
            this.f4713X = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        this.f4690A++;
        Handler handler = new Handler();
        handler.f4662a = label;
        handler.f4663b = label2;
        handler.f4664c = label3;
        handler.f4665d = str;
        handler.f4666e = str != null ? this.f4716b.newClass(str) : 0;
        Handler handler2 = this.f4692C;
        if (handler2 == null) {
            this.f4691B = handler;
        } else {
            handler2.f4667f = handler;
        }
        this.f4692C = handler;
    }

    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.m4144a(i, typePath, byteVector);
        byteVector.putShort(this.f4716b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4716b, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4710U;
            this.f4710U = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4711V;
            this.f4711V = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitTypeInsn(int i, String str) {
        this.f4714Y = this.f4732r.f4568b;
        Item a = this.f4716b.mo76751a(str);
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(i, this.f4732r.f4568b, this.f4716b, a);
            } else if (i == 187) {
                int i2 = this.f4706Q + 1;
                if (i2 > this.f4707R) {
                    this.f4707R = i2;
                }
                this.f4706Q = i2;
            }
        }
        this.f4732r.mo76706b(i, a.f4668a);
    }

    public void visitVarInsn(int i, int i2) {
        this.f4714Y = this.f4732r.f4568b;
        Label label = this.f4705P;
        if (label != null) {
            if (this.f4702M == 0) {
                label.f4684h.mo76774a(i, i2, (ClassWriter) null, (Item) null);
            } else if (i == 169) {
                label.f4677a |= 256;
                this.f4705P.f4682f = this.f4706Q;
                m4241e();
            } else {
                int i3 = this.f4706Q + Frame.f4649a[i];
                if (i3 > this.f4707R) {
                    this.f4707R = i3;
                }
                this.f4706Q = i3;
            }
        }
        if (this.f4702M != 2) {
            int i4 = (i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1;
            if (i4 > this.f4734t) {
                this.f4734t = i4;
            }
        }
        if (i2 >= 4 || i == 169) {
            ByteVector byteVector = this.f4732r;
            if (i2 >= 256) {
                byteVector.putByte(196).mo76706b(i, i2);
            } else {
                byteVector.mo76705a(i, i2);
            }
        } else {
            this.f4732r.putByte((i < 54 ? ((i - 21) << 2) + 26 : ((i - 54) << 2) + 59) + i2);
        }
        if (i >= 54 && this.f4702M == 0 && this.f4690A > 0) {
            visitLabel(new Label());
        }
    }
}
