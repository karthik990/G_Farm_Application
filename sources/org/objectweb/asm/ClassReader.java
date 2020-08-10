package org.objectweb.asm;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
    public static final int EXPAND_FRAMES = 8;
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;

    /* renamed from: a */
    private final int[] f4569a;

    /* renamed from: b */
    public final byte[] f4570b;

    /* renamed from: c */
    private final String[] f4571c;

    /* renamed from: d */
    private final int f4572d;
    public final int header;

    public ClassReader(InputStream inputStream) throws IOException {
        this(m4167a(inputStream, false));
    }

    public ClassReader(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.replace('.', JsonPointer.SEPARATOR));
        stringBuffer.append(".class");
        this(m4167a(ClassLoader.getSystemResourceAsStream(stringBuffer.toString()), true));
    }

    public ClassReader(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ClassReader(byte[] bArr, int i, int i2) {
        this.f4570b = bArr;
        if (readShort(i + 6) <= 52) {
            this.f4569a = new int[readUnsignedShort(i + 8)];
            int length = this.f4569a.length;
            this.f4571c = new String[length];
            int i3 = 0;
            int i4 = i + 10;
            int i5 = 1;
            while (i5 < length) {
                int i6 = i4 + 1;
                this.f4569a[i5] = i6;
                byte b = bArr[i4];
                int i7 = 5;
                if (b == 1) {
                    i7 = readUnsignedShort(i6) + 3;
                    if (i7 > i3) {
                        i3 = i7;
                    }
                } else if (b != 15) {
                    if (!(b == 18 || b == 3 || b == 4)) {
                        if (b != 5 && b != 6) {
                            switch (b) {
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                    break;
                                default:
                                    i7 = 3;
                                    break;
                            }
                        } else {
                            i7 = 9;
                            i5++;
                        }
                    }
                } else {
                    i7 = 4;
                }
                i4 += i7;
                i5++;
            }
            this.f4572d = i3;
            this.header = i4;
            return;
        }
        throw new IllegalArgumentException();
    }

    /* renamed from: a */
    private int m4155a() {
        int i = this.header;
        int readUnsignedShort = i + 8 + (readUnsignedShort(i + 6) * 2);
        for (int readUnsignedShort2 = readUnsignedShort(readUnsignedShort); readUnsignedShort2 > 0; readUnsignedShort2--) {
            for (int readUnsignedShort3 = readUnsignedShort(readUnsignedShort + 8); readUnsignedShort3 > 0; readUnsignedShort3--) {
                readUnsignedShort += readInt(readUnsignedShort + 12) + 6;
            }
            readUnsignedShort += 8;
        }
        int i2 = readUnsignedShort + 2;
        for (int readUnsignedShort4 = readUnsignedShort(i2); readUnsignedShort4 > 0; readUnsignedShort4--) {
            for (int readUnsignedShort5 = readUnsignedShort(i2 + 8); readUnsignedShort5 > 0; readUnsignedShort5--) {
                i2 += readInt(i2 + 12) + 6;
            }
            i2 += 8;
        }
        return i2 + 2;
    }

    /* renamed from: a */
    private int m4156a(int i, boolean z, boolean z2, Context context) {
        int i2;
        int i3;
        int i4;
        int i5;
        char[] cArr = context.f4616c;
        Label[] labelArr = context.f4621h;
        if (z) {
            int i6 = i + 1;
            i2 = this.f4570b[i] & 255;
            i3 = i6;
        } else {
            context.f4627o = -1;
            i3 = i;
            i2 = 255;
        }
        context.f4630r = 0;
        if (i2 < 64) {
            context.f4628p = 3;
            context.f4632t = 0;
        } else if (i2 < 128) {
            i2 -= 64;
            i3 = m4161a(context.f4633u, 0, i3, cArr, labelArr);
            context.f4628p = 4;
            context.f4632t = 1;
        } else {
            i4 = readUnsignedShort(i3);
            int i7 = i3 + 2;
            if (i2 == 247) {
                i3 = m4161a(context.f4633u, 0, i7, cArr, labelArr);
                context.f4628p = 4;
                context.f4632t = 1;
            } else {
                if (i2 >= 248 && i2 < 251) {
                    context.f4628p = 2;
                    context.f4630r = 251 - i2;
                    i5 = context.f4629q - context.f4630r;
                } else if (i2 == 251) {
                    context.f4628p = 3;
                    context.f4632t = 0;
                } else if (i2 < 255) {
                    int i8 = i2 - 251;
                    int i9 = z2 ? context.f4629q : 0;
                    int i10 = i8;
                    while (i10 > 0) {
                        int i11 = i9 + 1;
                        i7 = m4161a(context.f4631s, i9, i7, cArr, labelArr);
                        i10--;
                        i9 = i11;
                    }
                    context.f4628p = 1;
                    context.f4630r = i8;
                    i5 = context.f4629q + context.f4630r;
                } else {
                    context.f4628p = 0;
                    int readUnsignedShort = readUnsignedShort(i7);
                    int i12 = i7 + 2;
                    context.f4630r = readUnsignedShort;
                    context.f4629q = readUnsignedShort;
                    int i13 = 0;
                    while (readUnsignedShort > 0) {
                        int i14 = i13 + 1;
                        i12 = m4161a(context.f4631s, i13, i12, cArr, labelArr);
                        readUnsignedShort--;
                        i13 = i14;
                    }
                    int readUnsignedShort2 = readUnsignedShort(i12);
                    int i15 = i12 + 2;
                    context.f4632t = readUnsignedShort2;
                    int i16 = 0;
                    while (readUnsignedShort2 > 0) {
                        int i17 = i16 + 1;
                        i15 = m4161a(context.f4633u, i16, i3, cArr, labelArr);
                        readUnsignedShort2--;
                        i16 = i17;
                    }
                }
                context.f4629q = i5;
                context.f4632t = 0;
            }
            context.f4627o += i4 + 1;
            readLabel(context.f4627o, labelArr);
            return i3;
        }
        i4 = i2;
        context.f4627o += i4 + 1;
        readLabel(context.f4627o, labelArr);
        return i3;
    }

    /* renamed from: a */
    private int m4157a(int i, char[] cArr, String str, AnnotationVisitor annotationVisitor) {
        Object obj;
        Object sh;
        int i2 = 0;
        if (annotationVisitor == null) {
            byte b = this.f4570b[i] & 255;
            return b != 64 ? b != 91 ? b != 101 ? i + 3 : i + 5 : m4158a(i + 1, cArr, false, (AnnotationVisitor) null) : m4158a(i + 3, cArr, true, (AnnotationVisitor) null);
        }
        int i3 = i + 1;
        byte b2 = this.f4570b[i] & 255;
        if (b2 != 64) {
            if (b2 != 70) {
                if (b2 != 83) {
                    if (b2 == 99) {
                        obj = Type.getType(readUTF8(i3, cArr));
                    } else if (b2 == 101) {
                        annotationVisitor.visitEnum(str, readUTF8(i3, cArr), readUTF8(i3 + 2, cArr));
                        i3 += 4;
                    } else if (b2 == 115) {
                        obj = readUTF8(i3, cArr);
                    } else if (!(b2 == 73 || b2 == 74)) {
                        if (b2 == 90) {
                            obj = readInt(this.f4569a[readUnsignedShort(i3)]) == 0 ? Boolean.FALSE : Boolean.TRUE;
                        } else if (b2 != 91) {
                            switch (b2) {
                                case 66:
                                    sh = new Byte((byte) readInt(this.f4569a[readUnsignedShort(i3)]));
                                    break;
                                case 67:
                                    sh = new Character((char) readInt(this.f4569a[readUnsignedShort(i3)]));
                                    break;
                                case 68:
                                    break;
                            }
                        } else {
                            int readUnsignedShort = readUnsignedShort(i3);
                            int i4 = i3 + 2;
                            if (readUnsignedShort == 0) {
                                return m4158a(i4 - 2, cArr, false, annotationVisitor.visitArray(str));
                            }
                            int i5 = i4 + 1;
                            byte b3 = this.f4570b[i4] & 255;
                            if (b3 == 70) {
                                float[] fArr = new float[readUnsignedShort];
                                while (i2 < readUnsignedShort) {
                                    fArr[i2] = Float.intBitsToFloat(readInt(this.f4569a[readUnsignedShort(i5)]));
                                    i5 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, fArr);
                            } else if (b3 == 83) {
                                short[] sArr = new short[readUnsignedShort];
                                while (i2 < readUnsignedShort) {
                                    sArr[i2] = (short) readInt(this.f4569a[readUnsignedShort(i5)]);
                                    i5 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, sArr);
                            } else if (b3 == 90) {
                                boolean[] zArr = new boolean[readUnsignedShort];
                                for (int i6 = 0; i6 < readUnsignedShort; i6++) {
                                    zArr[i6] = readInt(this.f4569a[readUnsignedShort(i5)]) != 0;
                                    i5 += 3;
                                }
                                annotationVisitor.visit(str, zArr);
                            } else if (b3 == 73) {
                                int[] iArr = new int[readUnsignedShort];
                                while (i2 < readUnsignedShort) {
                                    iArr[i2] = readInt(this.f4569a[readUnsignedShort(i5)]);
                                    i5 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, iArr);
                            } else if (b3 != 74) {
                                switch (b3) {
                                    case 66:
                                        byte[] bArr = new byte[readUnsignedShort];
                                        while (i2 < readUnsignedShort) {
                                            bArr[i2] = (byte) readInt(this.f4569a[readUnsignedShort(i5)]);
                                            i5 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, bArr);
                                        break;
                                    case 67:
                                        char[] cArr2 = new char[readUnsignedShort];
                                        while (i2 < readUnsignedShort) {
                                            cArr2[i2] = (char) readInt(this.f4569a[readUnsignedShort(i5)]);
                                            i5 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, cArr2);
                                        break;
                                    case 68:
                                        double[] dArr = new double[readUnsignedShort];
                                        while (i2 < readUnsignedShort) {
                                            dArr[i2] = Double.longBitsToDouble(readLong(this.f4569a[readUnsignedShort(i5)]));
                                            i5 += 3;
                                            i2++;
                                        }
                                        annotationVisitor.visit(str, dArr);
                                        break;
                                    default:
                                        i3 = m4158a(i5 - 3, cArr, false, annotationVisitor.visitArray(str));
                                        break;
                                }
                            } else {
                                long[] jArr = new long[readUnsignedShort];
                                while (i2 < readUnsignedShort) {
                                    jArr[i2] = readLong(this.f4569a[readUnsignedShort(i5)]);
                                    i5 += 3;
                                    i2++;
                                }
                                annotationVisitor.visit(str, jArr);
                            }
                            i3 = i5 - 1;
                        }
                    }
                    annotationVisitor.visit(str, obj);
                    i3 += 2;
                } else {
                    sh = new Short((short) readInt(this.f4569a[readUnsignedShort(i3)]));
                }
                annotationVisitor.visit(str, sh);
                i3 += 2;
            }
            obj = readConst(readUnsignedShort(i3), cArr);
            annotationVisitor.visit(str, obj);
            i3 += 2;
        } else {
            i3 = m4158a(i3 + 2, cArr, true, annotationVisitor.visitAnnotation(str, readUTF8(i3, cArr)));
        }
        return i3;
    }

    /* renamed from: a */
    private int m4158a(int i, char[] cArr, boolean z, AnnotationVisitor annotationVisitor) {
        int readUnsignedShort = readUnsignedShort(i);
        int i2 = i + 2;
        if (z) {
            while (readUnsignedShort > 0) {
                i2 = m4157a(i2 + 2, cArr, readUTF8(i2, cArr), annotationVisitor);
                readUnsignedShort--;
            }
        } else {
            while (readUnsignedShort > 0) {
                i2 = m4157a(i2, cArr, (String) null, annotationVisitor);
                readUnsignedShort--;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return i2;
    }

    /* renamed from: a */
    private int m4159a(ClassVisitor classVisitor, Context context, int i) {
        int i2;
        Context context2 = context;
        int i3 = i;
        char[] cArr = context2.f4616c;
        int readUnsignedShort = readUnsignedShort(i3);
        String readUTF8 = readUTF8(i3 + 2, cArr);
        String readUTF82 = readUTF8(i3 + 4, cArr);
        int i4 = i3 + 6;
        int i5 = i4;
        int i6 = readUnsignedShort;
        int readUnsignedShort2 = readUnsignedShort(i4);
        Attribute attribute = null;
        String str = null;
        Object obj = null;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (readUnsignedShort2 > 0) {
            String readUTF83 = readUTF8(i5 + 2, cArr);
            if ("ConstantValue".equals(readUTF83)) {
                int readUnsignedShort3 = readUnsignedShort(i5 + 8);
                obj = readUnsignedShort3 == 0 ? null : readConst(readUnsignedShort3, cArr);
            } else if ("Signature".equals(readUTF83)) {
                str = readUTF8(i5 + 8, cArr);
            } else {
                if ("Deprecated".equals(readUTF83)) {
                    i2 = 131072;
                } else if ("Synthetic".equals(readUTF83)) {
                    i2 = 266240;
                } else if ("RuntimeVisibleAnnotations".equals(readUTF83)) {
                    i10 = i5 + 8;
                } else if ("RuntimeVisibleTypeAnnotations".equals(readUTF83)) {
                    i8 = i5 + 8;
                } else if ("RuntimeInvisibleAnnotations".equals(readUTF83)) {
                    i9 = i5 + 8;
                } else if ("RuntimeInvisibleTypeAnnotations".equals(readUTF83)) {
                    i7 = i5 + 8;
                } else {
                    Attribute attribute2 = attribute;
                    int i11 = i7;
                    int i12 = i8;
                    int i13 = i9;
                    int i14 = i10;
                    Attribute a = m4163a(context2.f4614a, readUTF83, i5 + 8, readInt(i5 + 4), cArr, -1, null);
                    Attribute attribute3 = attribute2;
                    if (a != null) {
                        a.f4565a = attribute3;
                        i9 = i13;
                        attribute = a;
                    } else {
                        i9 = i13;
                        attribute = attribute3;
                    }
                    i10 = i14;
                    i7 = i11;
                    i8 = i12;
                }
                i6 |= i2;
            }
            i5 += readInt(i5 + 4) + 6;
            readUnsignedShort2--;
            context2 = context;
        }
        Attribute attribute4 = attribute;
        int i15 = i7;
        int i16 = i8;
        int i17 = i9;
        int i18 = i10;
        int i19 = i5 + 2;
        FieldVisitor visitField = classVisitor.visitField(i6, readUTF8, readUTF82, str, obj);
        if (visitField == null) {
            return i19;
        }
        if (i18 != 0) {
            int i20 = i18 + 2;
            for (int readUnsignedShort4 = readUnsignedShort(i18); readUnsignedShort4 > 0; readUnsignedShort4--) {
                i20 = m4158a(i20 + 2, cArr, true, visitField.visitAnnotation(readUTF8(i20, cArr), true));
            }
        }
        if (i17 != 0) {
            int i21 = i17;
            int i22 = i21 + 2;
            for (int readUnsignedShort5 = readUnsignedShort(i21); readUnsignedShort5 > 0; readUnsignedShort5--) {
                i22 = m4158a(i22 + 2, cArr, true, visitField.visitAnnotation(readUTF8(i22, cArr), false));
            }
        }
        int i23 = i16;
        if (i23 != 0) {
            int i24 = i23 + 2;
            for (int readUnsignedShort6 = readUnsignedShort(i23); readUnsignedShort6 > 0; readUnsignedShort6--) {
                Context context3 = context;
                int a2 = m4160a(context3, i24);
                i24 = m4158a(a2 + 2, cArr, true, visitField.visitTypeAnnotation(context3.f4622i, context3.f4623j, readUTF8(a2, cArr), true));
            }
        }
        Context context4 = context;
        int i25 = i15;
        if (i25 != 0) {
            int i26 = i25 + 2;
            for (int readUnsignedShort7 = readUnsignedShort(i25); readUnsignedShort7 > 0; readUnsignedShort7--) {
                int a3 = m4160a(context4, i26);
                i26 = m4158a(a3 + 2, cArr, true, visitField.visitTypeAnnotation(context4.f4622i, context4.f4623j, readUTF8(a3, cArr), false));
            }
        }
        while (attribute4 != null) {
            Attribute attribute5 = attribute4.f4565a;
            attribute4.f4565a = null;
            visitField.visitAttribute(attribute4);
            attribute4 = attribute5;
        }
        visitField.visitEnd();
        return i19;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0084  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m4160a(org.objectweb.asm.Context r9, int r10) {
        /*
            r8 = this;
            int r0 = r8.readInt(r10)
            int r1 = r0 >>> 24
            r2 = 1
            if (r1 == 0) goto L_0x0075
            if (r1 == r2) goto L_0x0075
            r3 = 64
            r4 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            if (r1 == r3) goto L_0x002f
            r3 = 65
            if (r1 == r3) goto L_0x002f
            switch(r1) {
                case 19: goto L_0x002c;
                case 20: goto L_0x002c;
                case 21: goto L_0x002c;
                case 22: goto L_0x0075;
                default: goto L_0x0018;
            }
        L_0x0018:
            switch(r1) {
                case 71: goto L_0x0025;
                case 72: goto L_0x0025;
                case 73: goto L_0x0025;
                case 74: goto L_0x0025;
                case 75: goto L_0x0025;
                default: goto L_0x001b;
            }
        L_0x001b:
            r3 = 67
            if (r1 >= r3) goto L_0x0021
            r4 = -256(0xffffffffffffff00, float:NaN)
        L_0x0021:
            r0 = r0 & r4
            int r10 = r10 + 3
            goto L_0x007a
        L_0x0025:
            r1 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r0 = r0 & r1
            int r10 = r10 + 4
            goto L_0x007a
        L_0x002c:
            r0 = r0 & r4
            int r10 = r10 + r2
            goto L_0x007a
        L_0x002f:
            r0 = r0 & r4
            int r1 = r10 + 1
            int r1 = r8.readUnsignedShort(r1)
            org.objectweb.asm.Label[] r3 = new org.objectweb.asm.Label[r1]
            r9.f4624l = r3
            org.objectweb.asm.Label[] r3 = new org.objectweb.asm.Label[r1]
            r9.f4625m = r3
            int[] r3 = new int[r1]
            r9.f4626n = r3
            int r10 = r10 + 3
            r3 = 0
        L_0x0045:
            if (r3 >= r1) goto L_0x007a
            int r4 = r8.readUnsignedShort(r10)
            int r5 = r10 + 2
            int r5 = r8.readUnsignedShort(r5)
            org.objectweb.asm.Label[] r6 = r9.f4624l
            org.objectweb.asm.Label[] r7 = r9.f4621h
            org.objectweb.asm.Label r7 = r8.readLabel(r4, r7)
            r6[r3] = r7
            org.objectweb.asm.Label[] r6 = r9.f4625m
            int r4 = r4 + r5
            org.objectweb.asm.Label[] r5 = r9.f4621h
            org.objectweb.asm.Label r4 = r8.readLabel(r4, r5)
            r6[r3] = r4
            int[] r4 = r9.f4626n
            int r5 = r10 + 4
            int r5 = r8.readUnsignedShort(r5)
            r4[r3] = r5
            int r10 = r10 + 6
            int r3 = r3 + 1
            goto L_0x0045
        L_0x0075:
            r1 = -65536(0xffffffffffff0000, float:NaN)
            r0 = r0 & r1
            int r10 = r10 + 2
        L_0x007a:
            int r1 = r8.readByte(r10)
            r9.f4622i = r0
            if (r1 != 0) goto L_0x0084
            r0 = 0
            goto L_0x008b
        L_0x0084:
            org.objectweb.asm.TypePath r0 = new org.objectweb.asm.TypePath
            byte[] r3 = r8.f4570b
            r0.<init>(r3, r10)
        L_0x008b:
            r9.f4623j = r0
            int r10 = r10 + r2
            int r1 = r1 * 2
            int r10 = r10 + r1
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.m4160a(org.objectweb.asm.Context, int):int");
    }

    /* renamed from: a */
    private int m4161a(Object[] objArr, int i, int i2, char[] cArr, Label[] labelArr) {
        int i3 = i2 + 1;
        switch (this.f4570b[i2] & 255) {
            case 0:
                objArr[i] = Opcodes.TOP;
                return i3;
            case 1:
                objArr[i] = Opcodes.INTEGER;
                return i3;
            case 2:
                objArr[i] = Opcodes.FLOAT;
                return i3;
            case 3:
                objArr[i] = Opcodes.DOUBLE;
                return i3;
            case 4:
                objArr[i] = Opcodes.LONG;
                return i3;
            case 5:
                objArr[i] = Opcodes.NULL;
                return i3;
            case 6:
                objArr[i] = Opcodes.UNINITIALIZED_THIS;
                return i3;
            case 7:
                objArr[i] = readClass(i3, cArr);
                break;
            default:
                objArr[i] = readLabel(readUnsignedShort(i3), labelArr);
                break;
        }
        return i3 + 2;
    }

    /* renamed from: a */
    private String m4162a(int i, int i2, char[] cArr) {
        byte b;
        int i3 = i2 + i;
        byte[] bArr = this.f4570b;
        int i4 = 0;
        char c = 0;
        int i5 = 0;
        while (i < i3) {
            int i6 = i + 1;
            byte b2 = bArr[i];
            if (c != 0) {
                if (c == 1) {
                    int i7 = i4 + 1;
                    cArr[i4] = (char) ((b2 & 63) | (i5 << 6));
                    i4 = i7;
                    c = 0;
                } else if (c == 2) {
                    b = (b2 & 63) | (i5 << 6);
                }
                i = i6;
            } else {
                byte b3 = b2 & 255;
                if (b3 < 128) {
                    int i8 = i4 + 1;
                    cArr[i4] = (char) b3;
                    i4 = i8;
                } else if (b3 >= 224 || b3 <= 191) {
                    i5 = (char) (b3 & Ascii.f1875SI);
                    c = 2;
                } else {
                    b = b3 & Ascii.f1878US;
                }
                i = i6;
            }
            i5 = (char) b;
            c = 1;
            i = i6;
        }
        return new String(cArr, 0, i4);
    }

    /* renamed from: a */
    private Attribute m4163a(Attribute[] attributeArr, String str, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        Attribute[] attributeArr2 = attributeArr;
        String str2 = str;
        for (int i4 = 0; i4 < attributeArr2.length; i4++) {
            if (attributeArr2[i4].type.equals(str)) {
                return attributeArr2[i4].read(this, i, i2, cArr, i3, labelArr);
            }
        }
        return new Attribute(str).read(this, i, i2, null, -1, null);
    }

    /* renamed from: a */
    private void m4164a(ClassWriter classWriter, Item[] itemArr, char[] cArr) {
        int i;
        boolean z;
        int a = m4155a();
        int readUnsignedShort = readUnsignedShort(a);
        while (true) {
            if (readUnsignedShort <= 0) {
                z = false;
                break;
            }
            if ("BootstrapMethods".equals(readUTF8(a + 2, cArr))) {
                z = true;
                break;
            } else {
                a += readInt(a + 4) + 6;
                readUnsignedShort--;
            }
        }
        if (z) {
            int readUnsignedShort2 = readUnsignedShort(a + 8);
            int i2 = a + 10;
            int i3 = i2;
            for (i = 0; i < readUnsignedShort2; i++) {
                int i4 = (i3 - a) - 10;
                int hashCode = readConst(readUnsignedShort(i3), cArr).hashCode();
                for (int readUnsignedShort3 = readUnsignedShort(i3 + 2); readUnsignedShort3 > 0; readUnsignedShort3--) {
                    hashCode ^= readConst(readUnsignedShort(i3 + 4), cArr).hashCode();
                    i3 += 2;
                }
                i3 += 4;
                Item item = new Item(i);
                item.mo76787a(i4, hashCode & Integer.MAX_VALUE);
                int length = item.f4675j % itemArr.length;
                item.f4676k = itemArr[length];
                itemArr[length] = item;
            }
            int readInt = readInt(a + 4);
            ByteVector byteVector = new ByteVector(readInt + 62);
            byteVector.putByteArray(this.f4570b, i2, readInt - 2);
            classWriter.f4613z = readUnsignedShort2;
            classWriter.f4575A = byteVector;
        }
    }

    /* renamed from: a */
    private void m4165a(Context context) {
        int i;
        int i2;
        String str = context.f4620g;
        Object[] objArr = context.f4631s;
        int i3 = 0;
        if ((context.f4618e & 8) == 0) {
            if ("<init>".equals(context.f4619f)) {
                objArr[0] = Opcodes.UNINITIALIZED_THIS;
            } else {
                objArr[0] = readClass(this.header + 2, context.f4616c);
            }
            i3 = 1;
        }
        int i4 = 1;
        while (true) {
            int i5 = i4 + 1;
            char charAt = str.charAt(i4);
            if (charAt == 'F') {
                i2 = i + 1;
                objArr[i] = Opcodes.FLOAT;
            } else if (charAt != 'L') {
                if (!(charAt == 'S' || charAt == 'I')) {
                    if (charAt == 'J') {
                        i2 = i + 1;
                        objArr[i] = Opcodes.LONG;
                    } else if (charAt != 'Z') {
                        if (charAt != '[') {
                            switch (charAt) {
                                case 'B':
                                case 'C':
                                    break;
                                case 'D':
                                    i2 = i + 1;
                                    objArr[i] = Opcodes.DOUBLE;
                                    break;
                                default:
                                    context.f4629q = i;
                                    return;
                            }
                        } else {
                            while (str.charAt(i5) == '[') {
                                i5++;
                            }
                            if (str.charAt(i5) == 'L') {
                                do {
                                    i5++;
                                } while (str.charAt(i5) != ';');
                            }
                            int i6 = i + 1;
                            int i7 = i5 + 1;
                            objArr[i] = str.substring(i4, i7);
                            i4 = i7;
                            i = i6;
                        }
                    }
                }
                i2 = i + 1;
                objArr[i] = Opcodes.INTEGER;
            } else {
                int i8 = i5;
                while (str.charAt(i8) != ';') {
                    i8++;
                }
                int i9 = i + 1;
                int i10 = i8 + 1;
                objArr[i] = str.substring(i5, i8);
                i = i9;
                i4 = i10;
            }
            i = i2;
            i4 = i5;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:170:0x04d5, code lost:
        r0 = r17;
        r1 = r28;
        r9 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x05dd, code lost:
        r4 = r21 + 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x05f2, code lost:
        r4 = r21 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x05f4, code lost:
        r34 = r9;
        r0 = r17;
        r1 = r28;
        r9 = r36;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00bb, code lost:
        r0 = r0 + 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00bf, code lost:
        r0 = r0 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x0701, code lost:
        r4 = r21 + 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x0715, code lost:
        r4 = r21 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x0725, code lost:
        r4 = r21 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0729, code lost:
        if (r9 == null) goto L_0x0766;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x072c, code lost:
        if (r0 >= r9.length) goto L_0x0766;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x072e, code lost:
        if (r1 > r11) goto L_0x0766;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x0730, code lost:
        if (r1 != r11) goto L_0x074a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0732, code lost:
        r1 = m4160a(r14, r9[r0]);
        m4158a(r1 + 2, r13, true, r12.visitInsnAnnotation(r14.f4622i, r14.f4623j, readUTF8(r1, r13), true));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x074a, code lost:
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x074d, code lost:
        if (r0 >= r9.length) goto L_0x0764;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:231:0x0757, code lost:
        if (readByte(r9[r0]) >= 67) goto L_0x075a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x075a, code lost:
        r1 = readUnsignedShort(r9[r0] + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x0764, code lost:
        r1 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0766, code lost:
        r2 = r19;
        r3 = r29;
        r6 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x076c, code lost:
        if (r6 == null) goto L_0x07c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x076f, code lost:
        if (r2 >= r6.length) goto L_0x07c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x0771, code lost:
        if (r3 > r11) goto L_0x07c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x0773, code lost:
        if (r3 != r11) goto L_0x0795;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:240:0x0775, code lost:
        r3 = m4160a(r14, r6[r2]);
        r17 = r0;
        r19 = r1;
        r21 = r10;
        m4158a(r3 + 2, r13, true, r12.visitInsnAnnotation(r14.f4622i, r14.f4623j, readUTF8(r3, r13), false));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x0795, code lost:
        r17 = r0;
        r19 = r1;
        r21 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x079c, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x079f, code lost:
        if (r2 >= r6.length) goto L_0x07b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x07a9, code lost:
        if (readByte(r6[r2]) >= 67) goto L_0x07ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x07ac, code lost:
        r3 = readUnsignedShort(r6[r2] + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x07ba, code lost:
        r3 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x07bd, code lost:
        r0 = r17;
        r1 = r19;
        r10 = r21;
     */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04bc  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x04dd  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0513  */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0554  */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x0591  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x05a3  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x05b5  */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x05c9  */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x05e0  */
    /* JADX WARNING: Removed duplicated region for block: B:194:0x0600  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x0657  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x06a9  */
    /* JADX WARNING: Removed duplicated region for block: B:210:0x06bc  */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x06dc  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x06ef  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x0705  */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x0719  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m4166a(org.objectweb.asm.MethodVisitor r41, org.objectweb.asm.Context r42, int r43) {
        /*
            r40 = this;
            r7 = r40
            r15 = r41
            r14 = r42
            r0 = r43
            byte[] r8 = r7.f4570b
            char[] r13 = r14.f4616c
            int r12 = r7.readUnsignedShort(r0)
            int r1 = r0 + 2
            int r11 = r7.readUnsignedShort(r1)
            int r1 = r0 + 4
            int r9 = r7.readInt(r1)
            r10 = 8
            int r16 = r0 + 8
            int r6 = r16 + r9
            int r0 = r9 + 2
            org.objectweb.asm.Label[] r5 = new org.objectweb.asm.Label[r0]
            r14.f4621h = r5
            int r0 = r9 + 1
            r7.readLabel(r0, r5)
            r0 = r16
        L_0x002f:
            r4 = 132(0x84, float:1.85E-43)
            r3 = 1
            if (r0 >= r6) goto L_0x00cb
            int r1 = r0 - r16
            byte r2 = r8[r0]
            r2 = r2 & 255(0xff, float:3.57E-43)
            byte[] r17 = org.objectweb.asm.ClassWriter.f4574a
            byte r2 = r17[r2]
            switch(r2) {
                case 0: goto L_0x00c7;
                case 1: goto L_0x00c3;
                case 2: goto L_0x00bf;
                case 3: goto L_0x00c3;
                case 4: goto L_0x00c7;
                case 5: goto L_0x00bf;
                case 6: goto L_0x00bf;
                case 7: goto L_0x00bb;
                case 8: goto L_0x00bb;
                case 9: goto L_0x00b0;
                case 10: goto L_0x00a5;
                case 11: goto L_0x00c3;
                case 12: goto L_0x00bf;
                case 13: goto L_0x00bf;
                case 14: goto L_0x0076;
                case 15: goto L_0x004f;
                case 16: goto L_0x0041;
                case 17: goto L_0x0044;
                default: goto L_0x0041;
            }
        L_0x0041:
            int r0 = r0 + 4
            goto L_0x002f
        L_0x0044:
            int r1 = r0 + 1
            byte r1 = r8[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r1 != r4) goto L_0x0041
            int r0 = r0 + 6
            goto L_0x002f
        L_0x004f:
            int r0 = r0 + 4
            r2 = r1 & 3
            int r0 = r0 - r2
            int r2 = r7.readInt(r0)
            int r2 = r2 + r1
            r7.readLabel(r2, r5)
            int r2 = r0 + 4
            int r2 = r7.readInt(r2)
        L_0x0062:
            if (r2 <= 0) goto L_0x0073
            int r3 = r0 + 12
            int r3 = r7.readInt(r3)
            int r3 = r3 + r1
            r7.readLabel(r3, r5)
            int r0 = r0 + 8
            int r2 = r2 + -1
            goto L_0x0062
        L_0x0073:
            int r0 = r0 + 8
            goto L_0x002f
        L_0x0076:
            int r0 = r0 + 4
            r2 = r1 & 3
            int r0 = r0 - r2
            int r2 = r7.readInt(r0)
            int r2 = r2 + r1
            r7.readLabel(r2, r5)
            int r2 = r0 + 8
            int r2 = r7.readInt(r2)
            int r4 = r0 + 4
            int r4 = r7.readInt(r4)
            int r2 = r2 - r4
            int r2 = r2 + r3
        L_0x0091:
            if (r2 <= 0) goto L_0x00a2
            int r3 = r0 + 12
            int r3 = r7.readInt(r3)
            int r3 = r3 + r1
            r7.readLabel(r3, r5)
            int r0 = r0 + 4
            int r2 = r2 + -1
            goto L_0x0091
        L_0x00a2:
            int r0 = r0 + 12
            goto L_0x002f
        L_0x00a5:
            int r2 = r0 + 1
            int r2 = r7.readInt(r2)
            int r1 = r1 + r2
            r7.readLabel(r1, r5)
            goto L_0x00bb
        L_0x00b0:
            int r2 = r0 + 1
            short r2 = r7.readShort(r2)
            int r1 = r1 + r2
            r7.readLabel(r1, r5)
            goto L_0x00bf
        L_0x00bb:
            int r0 = r0 + 5
            goto L_0x002f
        L_0x00bf:
            int r0 = r0 + 3
            goto L_0x002f
        L_0x00c3:
            int r0 = r0 + 2
            goto L_0x002f
        L_0x00c7:
            int r0 = r0 + 1
            goto L_0x002f
        L_0x00cb:
            int r1 = r7.readUnsignedShort(r0)
        L_0x00cf:
            if (r1 <= 0) goto L_0x0108
            int r2 = r0 + 2
            int r2 = r7.readUnsignedShort(r2)
            org.objectweb.asm.Label r2 = r7.readLabel(r2, r5)
            int r4 = r0 + 4
            int r4 = r7.readUnsignedShort(r4)
            org.objectweb.asm.Label r4 = r7.readLabel(r4, r5)
            int r3 = r0 + 6
            int r3 = r7.readUnsignedShort(r3)
            org.objectweb.asm.Label r3 = r7.readLabel(r3, r5)
            int[] r10 = r7.f4569a
            int r0 = r0 + 8
            int r19 = r7.readUnsignedShort(r0)
            r10 = r10[r19]
            java.lang.String r10 = r7.readUTF8(r10, r13)
            r15.visitTryCatchBlock(r2, r4, r3, r10)
            int r1 = r1 + -1
            r3 = 1
            r4 = 132(0x84, float:1.85E-43)
            r10 = 8
            goto L_0x00cf
        L_0x0108:
            int r0 = r0 + 2
            int r1 = r14.f4615b
            r2 = 8
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0113
            r4 = 1
            goto L_0x0114
        L_0x0113:
            r4 = 0
        L_0x0114:
            int r1 = r7.readUnsignedShort(r0)
            r20 = r0
            r19 = r1
            r0 = 0
            r1 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 1
            r26 = 0
            r27 = 0
            r28 = -1
            r29 = -1
        L_0x0130:
            r10 = 67
            if (r19 <= 0) goto L_0x036a
            int r2 = r20 + 2
            java.lang.String r2 = r7.readUTF8(r2, r13)
            java.lang.String r3 = "LocalVariableTable"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x01ac
            int r2 = r14.f4615b
            r2 = r2 & 2
            if (r2 != 0) goto L_0x01a5
            int r2 = r20 + 8
            int r3 = r7.readUnsignedShort(r2)
            r10 = r20
        L_0x0150:
            if (r3 <= 0) goto L_0x0195
            r32 = r0
            int r0 = r10 + 10
            r33 = r1
            int r1 = r7.readUnsignedShort(r0)
            r23 = r5[r1]
            if (r23 != 0) goto L_0x0171
            r23 = r0
            org.objectweb.asm.Label r0 = r7.readLabel(r1, r5)
            r34 = r2
            int r2 = r0.f4677a
            r17 = 1
            r2 = r2 | 1
            r0.f4677a = r2
            goto L_0x0175
        L_0x0171:
            r23 = r0
            r34 = r2
        L_0x0175:
            int r10 = r10 + 12
            int r0 = r7.readUnsignedShort(r10)
            int r1 = r1 + r0
            r0 = r5[r1]
            if (r0 != 0) goto L_0x018a
            org.objectweb.asm.Label r0 = r7.readLabel(r1, r5)
            int r1 = r0.f4677a
            r2 = 1
            r1 = r1 | r2
            r0.f4677a = r1
        L_0x018a:
            int r3 = r3 + -1
            r10 = r23
            r0 = r32
            r1 = r33
            r2 = r34
            goto L_0x0150
        L_0x0195:
            r32 = r0
            r33 = r1
            r34 = r2
            r37 = r4
            r43 = r5
            r38 = r6
            r23 = r34
            goto L_0x02cf
        L_0x01a5:
            r32 = r0
            r33 = r1
        L_0x01a9:
            r3 = 1
            goto L_0x02c5
        L_0x01ac:
            r32 = r0
            r33 = r1
            java.lang.String r0 = "LocalVariableTypeTable"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x01be
            int r0 = r20 + 8
            r24 = r0
            goto L_0x02c5
        L_0x01be:
            java.lang.String r0 = "LineNumberTable"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x020d
            int r0 = r14.f4615b
            r0 = r0 & 2
            if (r0 != 0) goto L_0x01a9
            int r0 = r20 + 8
            int r0 = r7.readUnsignedShort(r0)
            r1 = r20
        L_0x01d4:
            if (r0 <= 0) goto L_0x01a9
            int r2 = r1 + 10
            int r2 = r7.readUnsignedShort(r2)
            r3 = r5[r2]
            if (r3 != 0) goto L_0x01ec
            org.objectweb.asm.Label r3 = r7.readLabel(r2, r5)
            int r10 = r3.f4677a
            r17 = 1
            r10 = r10 | 1
            r3.f4677a = r10
        L_0x01ec:
            r2 = r5[r2]
        L_0x01ee:
            int r3 = r2.f4678b
            if (r3 <= 0) goto L_0x0200
            org.objectweb.asm.Label r3 = r2.f4687k
            if (r3 != 0) goto L_0x01fd
            org.objectweb.asm.Label r3 = new org.objectweb.asm.Label
            r3.<init>()
            r2.f4687k = r3
        L_0x01fd:
            org.objectweb.asm.Label r2 = r2.f4687k
            goto L_0x01ee
        L_0x0200:
            int r3 = r1 + 12
            int r3 = r7.readUnsignedShort(r3)
            r2.f4678b = r3
            int r1 = r1 + 4
            int r0 = r0 + -1
            goto L_0x01d4
        L_0x020d:
            java.lang.String r0 = "RuntimeVisibleTypeAnnotations"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x023f
            int r0 = r20 + 8
            r1 = 1
            int[] r0 = r7.m4168a(r15, r14, r0, r1)
            int r2 = r0.length
            if (r2 == 0) goto L_0x0231
            r2 = 0
            r3 = r0[r2]
            int r3 = r7.readByte(r3)
            if (r3 >= r10) goto L_0x0229
            goto L_0x0231
        L_0x0229:
            r3 = r0[r2]
            int r3 = r3 + r1
            int r2 = r7.readUnsignedShort(r3)
            goto L_0x0232
        L_0x0231:
            r2 = -1
        L_0x0232:
            r1 = r0
            r28 = r2
            r37 = r4
            r43 = r5
            r38 = r6
            r0 = r32
            goto L_0x02cf
        L_0x023f:
            java.lang.String r0 = "RuntimeInvisibleTypeAnnotations"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0272
            int r0 = r20 + 8
            r1 = 0
            int[] r0 = r7.m4168a(r15, r14, r0, r1)
            int r2 = r0.length
            if (r2 == 0) goto L_0x0263
            r2 = r0[r1]
            int r2 = r7.readByte(r2)
            if (r2 >= r10) goto L_0x025a
            goto L_0x0263
        L_0x025a:
            r2 = r0[r1]
            r3 = 1
            int r2 = r2 + r3
            int r2 = r7.readUnsignedShort(r2)
            goto L_0x0265
        L_0x0263:
            r3 = 1
            r2 = -1
        L_0x0265:
            r29 = r2
            r37 = r4
            r43 = r5
            r38 = r6
            r32 = r9
            r1 = r33
            goto L_0x02d1
        L_0x0272:
            r3 = 1
            java.lang.String r0 = "StackMapTable"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0296
            int r0 = r14.f4615b
            r0 = r0 & 4
            if (r0 != 0) goto L_0x02c5
            int r0 = r20 + 10
            int r1 = r20 + 4
            int r1 = r7.readInt(r1)
            int r2 = r20 + 8
            int r2 = r7.readUnsignedShort(r2)
            r21 = r0
            r22 = r1
            r27 = r2
            goto L_0x02c5
        L_0x0296:
            java.lang.String r0 = "StackMap"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x02d4
            int r0 = r14.f4615b
            r0 = r0 & 4
            if (r0 != 0) goto L_0x02c5
            int r0 = r20 + 10
            int r1 = r20 + 4
            int r1 = r7.readInt(r1)
            int r2 = r20 + 8
            int r2 = r7.readUnsignedShort(r2)
            r21 = r0
            r22 = r1
            r27 = r2
            r37 = r4
            r43 = r5
            r38 = r6
            r0 = r32
            r1 = r33
            r25 = 0
            goto L_0x02cf
        L_0x02c5:
            r37 = r4
            r43 = r5
            r38 = r6
            r0 = r32
            r1 = r33
        L_0x02cf:
            r32 = r9
        L_0x02d1:
            r9 = -1
            goto L_0x0352
        L_0x02d4:
            r1 = r26
            r10 = 0
        L_0x02d7:
            org.objectweb.asm.Attribute[] r0 = r14.f4614a
            int r0 = r0.length
            if (r10 >= r0) goto L_0x033e
            org.objectweb.asm.Attribute[] r0 = r14.f4614a
            r0 = r0[r10]
            java.lang.String r0 = r0.type
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0319
            org.objectweb.asm.Attribute[] r0 = r14.f4614a
            r0 = r0[r10]
            int r17 = r20 + 8
            int r3 = r20 + 4
            int r3 = r7.readInt(r3)
            int r26 = r16 + -8
            r35 = r32
            r15 = r1
            r36 = r33
            r1 = r40
            r30 = r2
            r32 = r9
            r9 = -1
            r2 = r17
            r37 = r4
            r4 = r13
            r43 = r5
            r5 = r26
            r38 = r6
            r6 = r43
            org.objectweb.asm.Attribute r0 = r0.read(r1, r2, r3, r4, r5, r6)
            if (r0 == 0) goto L_0x0329
            r0.f4565a = r15
            r1 = r0
            goto L_0x032a
        L_0x0319:
            r15 = r1
            r30 = r2
            r37 = r4
            r43 = r5
            r38 = r6
            r35 = r32
            r36 = r33
            r32 = r9
            r9 = -1
        L_0x0329:
            r1 = r15
        L_0x032a:
            int r10 = r10 + 1
            r15 = r41
            r5 = r43
            r2 = r30
            r9 = r32
            r32 = r35
            r33 = r36
            r4 = r37
            r6 = r38
            r3 = 1
            goto L_0x02d7
        L_0x033e:
            r15 = r1
            r37 = r4
            r43 = r5
            r38 = r6
            r35 = r32
            r36 = r33
            r32 = r9
            r9 = -1
            r26 = r15
            r0 = r35
            r1 = r36
        L_0x0352:
            int r2 = r20 + 4
            int r2 = r7.readInt(r2)
            int r2 = r2 + 6
            int r20 = r20 + r2
            int r19 = r19 + -1
            r15 = r41
            r5 = r43
            r9 = r32
            r4 = r37
            r6 = r38
            goto L_0x0130
        L_0x036a:
            r35 = r0
            r36 = r1
            r37 = r4
            r43 = r5
            r38 = r6
            r32 = r9
            r9 = -1
            if (r21 == 0) goto L_0x03d1
            r14.f4627o = r9
            r0 = 0
            r14.f4628p = r0
            r14.f4629q = r0
            r14.f4630r = r0
            r14.f4632t = r0
            java.lang.Object[] r0 = new java.lang.Object[r11]
            r14.f4631s = r0
            java.lang.Object[] r0 = new java.lang.Object[r12]
            r14.f4633u = r0
            r6 = r37
            if (r6 == 0) goto L_0x0393
            r7.m4165a(r14)
        L_0x0393:
            r0 = r21
        L_0x0395:
            int r1 = r21 + r22
            int r1 = r1 + -2
            if (r0 >= r1) goto L_0x03cb
            byte r1 = r8[r0]
            r2 = 8
            if (r1 != r2) goto L_0x03c0
            int r1 = r0 + 1
            int r1 = r7.readUnsignedShort(r1)
            if (r1 < 0) goto L_0x03c0
            r15 = r32
            if (r1 >= r15) goto L_0x03bd
            int r2 = r16 + r1
            byte r2 = r8[r2]
            r2 = r2 & 255(0xff, float:3.57E-43)
            r3 = 187(0xbb, float:2.62E-43)
            if (r2 != r3) goto L_0x03bd
            r5 = r43
            r7.readLabel(r1, r5)
            goto L_0x03c4
        L_0x03bd:
            r5 = r43
            goto L_0x03c4
        L_0x03c0:
            r5 = r43
            r15 = r32
        L_0x03c4:
            int r0 = r0 + 1
            r43 = r5
            r32 = r15
            goto L_0x0395
        L_0x03cb:
            r5 = r43
            r15 = r32
            r0 = r14
            goto L_0x03d8
        L_0x03d1:
            r5 = r43
            r15 = r32
            r6 = r37
            r0 = 0
        L_0x03d8:
            r3 = r0
            r4 = r16
            r17 = 0
            r19 = 0
        L_0x03df:
            r2 = r38
            if (r4 >= r2) goto L_0x07ee
            int r1 = r4 - r16
            r0 = r5[r1]
            if (r0 == 0) goto L_0x040e
            org.objectweb.asm.Label r10 = r0.f4687k
            r9 = 0
            r0.f4687k = r9
            r20 = r12
            r12 = r41
            r12.visitLabel(r0)
            int r9 = r14.f4615b
            r9 = r9 & 2
            if (r9 != 0) goto L_0x0412
            int r9 = r0.f4678b
            if (r9 <= 0) goto L_0x0412
            int r9 = r0.f4678b
            r12.visitLineNumber(r9, r0)
        L_0x0404:
            if (r10 == 0) goto L_0x0412
            int r9 = r10.f4678b
            r12.visitLineNumber(r9, r0)
            org.objectweb.asm.Label r10 = r10.f4687k
            goto L_0x0404
        L_0x040e:
            r20 = r12
            r12 = r41
        L_0x0412:
            r9 = r3
            r10 = r21
        L_0x0415:
            if (r9 == 0) goto L_0x04a4
            int r0 = r9.f4627o
            if (r0 == r1) goto L_0x0421
            int r0 = r9.f4627o
            r3 = -1
            if (r0 != r3) goto L_0x04a4
            goto L_0x0422
        L_0x0421:
            r3 = -1
        L_0x0422:
            int r0 = r9.f4627o
            if (r0 == r3) goto L_0x0471
            r3 = r25
            if (r3 == 0) goto L_0x0456
            if (r6 == 0) goto L_0x042d
            goto L_0x0456
        L_0x042d:
            int r0 = r9.f4628p
            r38 = r2
            int r2 = r9.f4630r
            r21 = r3
            java.lang.Object[] r3 = r9.f4631s
            r22 = r4
            int r4 = r9.f4632t
            r25 = r5
            java.lang.Object[] r5 = r9.f4633u
            r31 = r0
            r0 = r41
            r32 = r11
            r11 = r1
            r1 = r31
            r31 = r38
            r33 = r15
            r15 = r21
            r21 = r22
            r22 = r25
            r0.visitFrame(r1, r2, r3, r4, r5)
            goto L_0x047e
        L_0x0456:
            r31 = r2
            r21 = r4
            r22 = r5
            r32 = r11
            r33 = r15
            r11 = r1
            r15 = r3
            r1 = -1
            int r2 = r9.f4629q
            java.lang.Object[] r3 = r9.f4631s
            int r4 = r9.f4632t
            java.lang.Object[] r5 = r9.f4633u
            r0 = r41
            r0.visitFrame(r1, r2, r3, r4, r5)
            goto L_0x047e
        L_0x0471:
            r31 = r2
            r21 = r4
            r22 = r5
            r32 = r11
            r33 = r15
            r15 = r25
            r11 = r1
        L_0x047e:
            if (r27 <= 0) goto L_0x0494
            int r10 = r7.m4156a(r10, r15, r6, r9)
            int r27 = r27 + -1
            r1 = r11
            r25 = r15
            r4 = r21
            r5 = r22
            r2 = r31
            r11 = r32
            r15 = r33
            goto L_0x0415
        L_0x0494:
            r1 = r11
            r25 = r15
            r4 = r21
            r5 = r22
            r2 = r31
            r11 = r32
            r15 = r33
            r9 = 0
            goto L_0x0415
        L_0x04a4:
            r31 = r2
            r21 = r4
            r22 = r5
            r32 = r11
            r33 = r15
            r15 = r25
            r11 = r1
            byte r0 = r8[r21]
            r5 = r0 & 255(0xff, float:3.57E-43)
            byte[] r0 = org.objectweb.asm.ClassWriter.f4574a
            byte r0 = r0[r5]
            switch(r0) {
                case 0: goto L_0x0719;
                case 1: goto L_0x0705;
                case 2: goto L_0x06ef;
                case 3: goto L_0x06dc;
                case 4: goto L_0x06bc;
                case 5: goto L_0x06a9;
                case 6: goto L_0x0657;
                case 7: goto L_0x0657;
                case 8: goto L_0x0600;
                case 9: goto L_0x05e0;
                case 10: goto L_0x05c9;
                case 11: goto L_0x05b5;
                case 12: goto L_0x05a3;
                case 13: goto L_0x0591;
                case 14: goto L_0x0554;
                case 15: goto L_0x0513;
                case 16: goto L_0x04bc;
                case 17: goto L_0x04dd;
                default: goto L_0x04bc;
            }
        L_0x04bc:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            int r4 = r21 + 1
            java.lang.String r0 = r7.readClass(r4, r13)
            int r4 = r21 + 3
            byte r1 = r8[r4]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r12.visitMultiANewArrayInsn(r0, r1)
            int r4 = r21 + 4
        L_0x04d5:
            r0 = r17
            r1 = r28
            r9 = r36
            goto L_0x0729
        L_0x04dd:
            int r4 = r21 + 1
            byte r0 = r8[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r4 = 132(0x84, float:1.85E-43)
            if (r0 != r4) goto L_0x04f9
            int r0 = r21 + 2
            int r0 = r7.readUnsignedShort(r0)
            int r1 = r21 + 4
            short r1 = r7.readShort(r1)
            r12.visitIincInsn(r0, r1)
            int r0 = r21 + 6
            goto L_0x0504
        L_0x04f9:
            int r1 = r21 + 2
            int r1 = r7.readUnsignedShort(r1)
            r12.visitVarInsn(r0, r1)
            int r0 = r21 + 4
        L_0x0504:
            r4 = r0
            r37 = r6
            r34 = r9
            r0 = r17
            r1 = r28
            r9 = r36
            r18 = 8
            goto L_0x05fc
        L_0x0513:
            r4 = 132(0x84, float:1.85E-43)
            int r0 = r21 + 4
            r1 = r11 & 3
            int r0 = r0 - r1
            int r1 = r7.readInt(r0)
            int r1 = r1 + r11
            int r2 = r0 + 4
            int r2 = r7.readInt(r2)
            int[] r3 = new int[r2]
            org.objectweb.asm.Label[] r5 = new org.objectweb.asm.Label[r2]
            r18 = 8
            int r0 = r0 + 8
            r4 = r0
            r0 = 0
        L_0x052f:
            if (r0 >= r2) goto L_0x054b
            int r21 = r7.readInt(r4)
            r3[r0] = r21
            r21 = r2
            int r2 = r4 + 4
            int r2 = r7.readInt(r2)
            int r2 = r2 + r11
            r2 = r22[r2]
            r5[r0] = r2
            int r4 = r4 + 8
            int r0 = r0 + 1
            r2 = r21
            goto L_0x052f
        L_0x054b:
            r0 = r22[r1]
            r12.visitLookupSwitchInsn(r0, r3, r5)
            r37 = r6
            goto L_0x05f4
        L_0x0554:
            r18 = 8
            int r4 = r21 + 4
            r0 = r11 & 3
            int r4 = r4 - r0
            int r0 = r7.readInt(r4)
            int r1 = r11 + r0
            int r0 = r4 + 4
            int r0 = r7.readInt(r0)
            int r2 = r4 + 8
            int r2 = r7.readInt(r2)
            int r3 = r2 - r0
            r5 = 1
            int r3 = r3 + r5
            org.objectweb.asm.Label[] r3 = new org.objectweb.asm.Label[r3]
            int r4 = r4 + 12
            r5 = r4
            r37 = r6
            r4 = 0
        L_0x0579:
            int r6 = r3.length
            if (r4 >= r6) goto L_0x058a
            int r6 = r7.readInt(r5)
            int r6 = r6 + r11
            r6 = r22[r6]
            r3[r4] = r6
            int r5 = r5 + 4
            int r4 = r4 + 1
            goto L_0x0579
        L_0x058a:
            r1 = r22[r1]
            r12.visitTableSwitchInsn(r0, r2, r1, r3)
            r4 = r5
            goto L_0x05f4
        L_0x0591:
            r37 = r6
            r18 = 8
            int r4 = r21 + 1
            byte r0 = r8[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r4 = r21 + 2
            byte r1 = r8[r4]
            r12.visitIincInsn(r0, r1)
            goto L_0x05f2
        L_0x05a3:
            r37 = r6
            r18 = 8
            int r4 = r21 + 1
            int r0 = r7.readUnsignedShort(r4)
            java.lang.Object r0 = r7.readConst(r0, r13)
            r12.visitLdcInsn(r0)
            goto L_0x05f2
        L_0x05b5:
            r37 = r6
            r18 = 8
            int r4 = r21 + 1
            byte r0 = r8[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            java.lang.Object r0 = r7.readConst(r0, r13)
            r12.visitLdcInsn(r0)
            int r4 = r21 + 2
            goto L_0x05f4
        L_0x05c9:
            r37 = r6
            r6 = 1
            r18 = 8
            int r5 = r5 + -33
            int r4 = r21 + 1
            int r0 = r7.readInt(r4)
            int r1 = r11 + r0
            r0 = r22[r1]
            r12.visitJumpInsn(r5, r0)
        L_0x05dd:
            int r4 = r21 + 5
            goto L_0x05f4
        L_0x05e0:
            r37 = r6
            r6 = 1
            r18 = 8
            int r4 = r21 + 1
            short r0 = r7.readShort(r4)
            int r1 = r11 + r0
            r0 = r22[r1]
            r12.visitJumpInsn(r5, r0)
        L_0x05f2:
            int r4 = r21 + 3
        L_0x05f4:
            r34 = r9
            r0 = r17
            r1 = r28
            r9 = r36
        L_0x05fc:
            r25 = 132(0x84, float:1.85E-43)
            goto L_0x0729
        L_0x0600:
            r37 = r6
            r6 = 1
            r18 = 8
            int[] r0 = r7.f4569a
            int r4 = r21 + 1
            int r1 = r7.readUnsignedShort(r4)
            r0 = r0[r1]
            int[] r1 = r14.f4617d
            int r2 = r7.readUnsignedShort(r0)
            r1 = r1[r2]
            int r2 = r7.readUnsignedShort(r1)
            java.lang.Object r2 = r7.readConst(r2, r13)
            org.objectweb.asm.Handle r2 = (org.objectweb.asm.Handle) r2
            int r3 = r1 + 2
            int r3 = r7.readUnsignedShort(r3)
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r1 = r1 + 4
            r5 = r1
            r1 = 0
        L_0x062d:
            if (r1 >= r3) goto L_0x063f
            int r6 = r7.readUnsignedShort(r5)
            java.lang.Object r6 = r7.readConst(r6, r13)
            r4[r1] = r6
            int r5 = r5 + 2
            int r1 = r1 + 1
            r6 = 1
            goto L_0x062d
        L_0x063f:
            int[] r1 = r7.f4569a
            int r0 = r0 + 2
            int r0 = r7.readUnsignedShort(r0)
            r0 = r1[r0]
            java.lang.String r1 = r7.readUTF8(r0, r13)
            int r0 = r0 + 2
            java.lang.String r0 = r7.readUTF8(r0, r13)
            r12.visitInvokeDynamicInsn(r1, r0, r2, r4)
            goto L_0x05dd
        L_0x0657:
            r37 = r6
            r18 = 8
            int[] r0 = r7.f4569a
            int r4 = r21 + 1
            int r1 = r7.readUnsignedShort(r4)
            r0 = r0[r1]
            int r1 = r0 + -1
            byte r1 = r8[r1]
            r2 = 11
            if (r1 != r2) goto L_0x066f
            r6 = 1
            goto L_0x0670
        L_0x066f:
            r6 = 0
        L_0x0670:
            java.lang.String r2 = r7.readClass(r0, r13)
            int[] r1 = r7.f4569a
            int r0 = r0 + 2
            int r0 = r7.readUnsignedShort(r0)
            r0 = r1[r0]
            java.lang.String r3 = r7.readUTF8(r0, r13)
            int r0 = r0 + 2
            java.lang.String r4 = r7.readUTF8(r0, r13)
            r0 = 182(0xb6, float:2.55E-43)
            if (r5 >= r0) goto L_0x0695
            r12.visitFieldInsn(r5, r2, r3, r4)
            r34 = r9
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            goto L_0x06a1
        L_0x0695:
            r0 = r41
            r1 = r5
            r25 = 132(0x84, float:1.85E-43)
            r34 = r9
            r9 = r5
            r5 = r6
            r0.visitMethodInsn(r1, r2, r3, r4, r5)
        L_0x06a1:
            r0 = 185(0xb9, float:2.59E-43)
            if (r9 != r0) goto L_0x0701
            int r4 = r21 + 5
            goto L_0x04d5
        L_0x06a9:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            int r4 = r21 + 1
            java.lang.String r0 = r7.readClass(r4, r13)
            r12.visitTypeInsn(r9, r0)
            goto L_0x0701
        L_0x06bc:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            r0 = 54
            if (r9 <= r0) goto L_0x06d0
            int r5 = r9 + -59
            int r0 = r5 >> 2
            int r0 = r0 + 54
            goto L_0x06d6
        L_0x06d0:
            int r5 = r9 + -26
            int r0 = r5 >> 2
            int r0 = r0 + 21
        L_0x06d6:
            r1 = r5 & 3
            r12.visitVarInsn(r0, r1)
            goto L_0x0725
        L_0x06dc:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            int r4 = r21 + 1
            byte r0 = r8[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            r12.visitVarInsn(r9, r0)
            goto L_0x0715
        L_0x06ef:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            int r4 = r21 + 1
            short r0 = r7.readShort(r4)
            r12.visitIntInsn(r9, r0)
        L_0x0701:
            int r4 = r21 + 3
            goto L_0x04d5
        L_0x0705:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            int r4 = r21 + 1
            byte r0 = r8[r4]
            r12.visitIntInsn(r9, r0)
        L_0x0715:
            int r4 = r21 + 2
            goto L_0x04d5
        L_0x0719:
            r37 = r6
            r34 = r9
            r18 = 8
            r25 = 132(0x84, float:1.85E-43)
            r9 = r5
            r12.visitInsn(r9)
        L_0x0725:
            int r4 = r21 + 1
            goto L_0x04d5
        L_0x0729:
            if (r9 == 0) goto L_0x0766
            int r2 = r9.length
            if (r0 >= r2) goto L_0x0766
            if (r1 > r11) goto L_0x0766
            if (r1 != r11) goto L_0x074a
            r1 = r9[r0]
            int r1 = r7.m4160a(r14, r1)
            int r2 = r1 + 2
            int r3 = r14.f4622i
            org.objectweb.asm.TypePath r5 = r14.f4623j
            java.lang.String r1 = r7.readUTF8(r1, r13)
            r6 = 1
            org.objectweb.asm.AnnotationVisitor r1 = r12.visitInsnAnnotation(r3, r5, r1, r6)
            r7.m4158a(r2, r13, r6, r1)
        L_0x074a:
            int r0 = r0 + 1
            int r1 = r9.length
            if (r0 >= r1) goto L_0x0764
            r1 = r9[r0]
            int r1 = r7.readByte(r1)
            r2 = 67
            if (r1 >= r2) goto L_0x075a
            goto L_0x0764
        L_0x075a:
            r1 = r9[r0]
            r2 = 1
            int r1 = r1 + r2
            int r2 = r7.readUnsignedShort(r1)
            r1 = r2
            goto L_0x0729
        L_0x0764:
            r1 = -1
            goto L_0x0729
        L_0x0766:
            r2 = r19
            r3 = r29
            r6 = r35
        L_0x076c:
            if (r6 == 0) goto L_0x07c4
            int r5 = r6.length
            if (r2 >= r5) goto L_0x07c4
            if (r3 > r11) goto L_0x07c4
            if (r3 != r11) goto L_0x0795
            r3 = r6[r2]
            int r3 = r7.m4160a(r14, r3)
            int r5 = r3 + 2
            r17 = r0
            int r0 = r14.f4622i
            r19 = r1
            org.objectweb.asm.TypePath r1 = r14.f4623j
            java.lang.String r3 = r7.readUTF8(r3, r13)
            r21 = r10
            r10 = 0
            org.objectweb.asm.AnnotationVisitor r0 = r12.visitInsnAnnotation(r0, r1, r3, r10)
            r1 = 1
            r7.m4158a(r5, r13, r1, r0)
            goto L_0x079c
        L_0x0795:
            r17 = r0
            r19 = r1
            r21 = r10
            r10 = 0
        L_0x079c:
            int r2 = r2 + 1
            int r0 = r6.length
            if (r2 >= r0) goto L_0x07b8
            r0 = r6[r2]
            int r0 = r7.readByte(r0)
            r1 = 67
            if (r0 >= r1) goto L_0x07ac
            goto L_0x07ba
        L_0x07ac:
            r0 = r6[r2]
            r28 = 1
            int r0 = r0 + 1
            int r0 = r7.readUnsignedShort(r0)
            r3 = r0
            goto L_0x07bd
        L_0x07b8:
            r1 = 67
        L_0x07ba:
            r28 = 1
            r3 = -1
        L_0x07bd:
            r0 = r17
            r1 = r19
            r10 = r21
            goto L_0x076c
        L_0x07c4:
            r17 = r0
            r19 = r1
            r21 = r10
            r1 = 67
            r10 = 0
            r28 = 1
            r29 = r3
            r35 = r6
            r36 = r9
            r25 = r15
            r28 = r19
            r12 = r20
            r5 = r22
            r38 = r31
            r11 = r32
            r15 = r33
            r3 = r34
            r6 = r37
            r9 = -1
            r10 = 67
            r19 = r2
            goto L_0x03df
        L_0x07ee:
            r22 = r5
            r32 = r11
            r20 = r12
            r33 = r15
            r6 = r35
            r9 = r36
            r10 = 0
            r28 = 1
            r12 = r41
            r0 = r22[r33]
            if (r0 == 0) goto L_0x0808
            r0 = r22[r33]
            r12.visitLabel(r0)
        L_0x0808:
            int r0 = r14.f4615b
            r0 = r0 & 2
            if (r0 != 0) goto L_0x08a9
            r0 = r23
            if (r0 == 0) goto L_0x08a9
            r1 = r24
            if (r1 == 0) goto L_0x0841
            int r24 = r1 + 2
            int r1 = r7.readUnsignedShort(r1)
            int r1 = r1 * 3
            int[] r3 = new int[r1]
            int r1 = r3.length
            r2 = r24
        L_0x0823:
            if (r1 <= 0) goto L_0x083f
            int r1 = r1 + -1
            int r4 = r2 + 6
            r3[r1] = r4
            r4 = -1
            int r1 = r1 + r4
            int r5 = r2 + 8
            int r5 = r7.readUnsignedShort(r5)
            r3[r1] = r5
            int r1 = r1 + r4
            int r5 = r7.readUnsignedShort(r2)
            r3[r1] = r5
            int r2 = r2 + 10
            goto L_0x0823
        L_0x083f:
            r8 = r3
            goto L_0x0842
        L_0x0841:
            r8 = 0
        L_0x0842:
            int r23 = r0 + 2
            int r0 = r7.readUnsignedShort(r0)
            r11 = r0
            r15 = r23
        L_0x084b:
            if (r11 <= 0) goto L_0x08a9
            int r0 = r7.readUnsignedShort(r15)
            int r1 = r15 + 2
            int r1 = r7.readUnsignedShort(r1)
            int r2 = r15 + 8
            int r5 = r7.readUnsignedShort(r2)
            if (r8 == 0) goto L_0x087a
            r2 = 0
        L_0x0860:
            int r3 = r8.length
            if (r2 >= r3) goto L_0x087a
            r3 = r8[r2]
            if (r3 != r0) goto L_0x0877
            int r3 = r2 + 1
            r3 = r8[r3]
            if (r3 != r5) goto L_0x0877
            int r2 = r2 + 2
            r2 = r8[r2]
            java.lang.String r2 = r7.readUTF8(r2, r13)
            r3 = r2
            goto L_0x087b
        L_0x0877:
            int r2 = r2 + 3
            goto L_0x0860
        L_0x087a:
            r3 = 0
        L_0x087b:
            int r2 = r15 + 4
            java.lang.String r2 = r7.readUTF8(r2, r13)
            int r4 = r15 + 6
            java.lang.String r4 = r7.readUTF8(r4, r13)
            r16 = r22[r0]
            int r0 = r0 + r1
            r17 = r22[r0]
            r0 = r41
            r1 = r2
            r2 = r4
            r4 = r16
            r16 = r5
            r5 = r17
            r39 = r6
            r12 = 1
            r6 = r16
            r0.visitLocalVariable(r1, r2, r3, r4, r5, r6)
            int r15 = r15 + 10
            int r11 = r11 + -1
            r12 = r41
            r6 = r39
            r28 = 1
            goto L_0x084b
        L_0x08a9:
            r39 = r6
            r12 = 1
            if (r9 == 0) goto L_0x0907
            r0 = 0
        L_0x08af:
            int r1 = r9.length
            if (r0 >= r1) goto L_0x0907
            r1 = r9[r0]
            int r1 = r7.readByte(r1)
            int r1 = r1 >> r12
            r2 = 32
            if (r1 != r2) goto L_0x08ef
            r1 = r9[r0]
            int r1 = r7.m4160a(r14, r1)
            int r2 = r1 + 2
            int r3 = r14.f4622i
            org.objectweb.asm.TypePath r4 = r14.f4623j
            org.objectweb.asm.Label[] r11 = r14.f4624l
            org.objectweb.asm.Label[] r5 = r14.f4625m
            int[] r6 = r14.f4626n
            java.lang.String r1 = r7.readUTF8(r1, r13)
            r15 = 1
            r8 = r41
            r16 = r9
            r9 = r3
            r3 = 0
            r10 = r4
            r4 = r32
            r3 = r20
            r12 = r5
            r5 = r13
            r13 = r6
            r6 = r14
            r14 = r1
            r1 = r41
            org.objectweb.asm.AnnotationVisitor r8 = r8.visitLocalVariableAnnotation(r9, r10, r11, r12, r13, r14, r15)
            r15 = 1
            r7.m4158a(r2, r5, r15, r8)
            goto L_0x08fa
        L_0x08ef:
            r1 = r41
            r16 = r9
            r5 = r13
            r6 = r14
            r3 = r20
            r4 = r32
            r15 = 1
        L_0x08fa:
            int r0 = r0 + 1
            r20 = r3
            r32 = r4
            r13 = r5
            r14 = r6
            r9 = r16
            r10 = 0
            r12 = 1
            goto L_0x08af
        L_0x0907:
            r1 = r41
            r5 = r13
            r6 = r14
            r3 = r20
            r4 = r32
            r15 = 1
            r0 = r39
            if (r0 == 0) goto L_0x0958
            r2 = 0
        L_0x0915:
            int r8 = r0.length
            if (r2 >= r8) goto L_0x0958
            r8 = r0[r2]
            int r8 = r7.readByte(r8)
            int r8 = r8 >> r15
            r9 = 32
            if (r8 != r9) goto L_0x094d
            r8 = r0[r2]
            int r8 = r7.m4160a(r6, r8)
            int r14 = r8 + 2
            int r9 = r6.f4622i
            org.objectweb.asm.TypePath r10 = r6.f4623j
            org.objectweb.asm.Label[] r11 = r6.f4624l
            org.objectweb.asm.Label[] r12 = r6.f4625m
            int[] r13 = r6.f4626n
            java.lang.String r16 = r7.readUTF8(r8, r5)
            r17 = 0
            r8 = r41
            r35 = r0
            r0 = r14
            r14 = r16
            r6 = 1
            r15 = r17
            org.objectweb.asm.AnnotationVisitor r8 = r8.visitLocalVariableAnnotation(r9, r10, r11, r12, r13, r14, r15)
            r7.m4158a(r0, r5, r6, r8)
            goto L_0x0950
        L_0x094d:
            r35 = r0
            r6 = 1
        L_0x0950:
            int r2 = r2 + 1
            r6 = r42
            r0 = r35
            r15 = 1
            goto L_0x0915
        L_0x0958:
            r0 = r26
        L_0x095a:
            if (r0 == 0) goto L_0x0966
            org.objectweb.asm.Attribute r2 = r0.f4565a
            r5 = 0
            r0.f4565a = r5
            r1.visitAttribute(r0)
            r0 = r2
            goto L_0x095a
        L_0x0966:
            r1.visitMaxs(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.m4166a(org.objectweb.asm.MethodVisitor, org.objectweb.asm.Context, int):void");
    }

    /* renamed from: a */
    private static byte[] m4167a(InputStream inputStream, boolean z) throws IOException {
        if (inputStream != null) {
            try {
                byte[] bArr = new byte[inputStream.available()];
                int i = 0;
                while (true) {
                    int read = inputStream.read(bArr, i, bArr.length - i);
                    if (read == -1) {
                        if (i < bArr.length) {
                            byte[] bArr2 = new byte[i];
                            System.arraycopy(bArr, 0, bArr2, 0, i);
                            bArr = bArr2;
                        }
                        if (z) {
                            inputStream.close();
                        }
                        return bArr;
                    }
                    i += read;
                    if (i == bArr.length) {
                        int read2 = inputStream.read();
                        if (read2 < 0) {
                            return bArr;
                        }
                        byte[] bArr3 = new byte[(bArr.length + 1000)];
                        System.arraycopy(bArr, 0, bArr3, 0, i);
                        int i2 = i + 1;
                        bArr3[i] = (byte) read2;
                        i = i2;
                        bArr = bArr3;
                    }
                }
            } finally {
                if (z) {
                    inputStream.close();
                }
            }
        } else {
            throw new IOException("Class not found");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x007e  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int[] m4168a(org.objectweb.asm.MethodVisitor r11, org.objectweb.asm.Context r12, int r13, boolean r14) {
        /*
            r10 = this;
            char[] r0 = r12.f4616c
            int r1 = r10.readUnsignedShort(r13)
            int[] r1 = new int[r1]
            int r13 = r13 + 2
            r2 = 0
        L_0x000b:
            int r3 = r1.length
            if (r2 >= r3) goto L_0x008a
            r1[r2] = r13
            int r3 = r10.readInt(r13)
            int r4 = r3 >>> 24
            r5 = 1
            if (r4 == 0) goto L_0x0056
            if (r4 == r5) goto L_0x0056
            r6 = 64
            if (r4 == r6) goto L_0x0032
            r6 = 65
            if (r4 == r6) goto L_0x0032
            switch(r4) {
                case 19: goto L_0x002f;
                case 20: goto L_0x002f;
                case 21: goto L_0x002f;
                case 22: goto L_0x0056;
                default: goto L_0x0026;
            }
        L_0x0026:
            switch(r4) {
                case 71: goto L_0x002c;
                case 72: goto L_0x002c;
                case 73: goto L_0x002c;
                case 74: goto L_0x002c;
                case 75: goto L_0x002c;
                default: goto L_0x0029;
            }
        L_0x0029:
            int r13 = r13 + 3
            goto L_0x0058
        L_0x002c:
            int r13 = r13 + 4
            goto L_0x0058
        L_0x002f:
            int r13 = r13 + 1
            goto L_0x0058
        L_0x0032:
            int r6 = r13 + 1
            int r6 = r10.readUnsignedShort(r6)
        L_0x0038:
            if (r6 <= 0) goto L_0x0029
            int r7 = r13 + 3
            int r7 = r10.readUnsignedShort(r7)
            int r8 = r13 + 5
            int r8 = r10.readUnsignedShort(r8)
            org.objectweb.asm.Label[] r9 = r12.f4621h
            r10.readLabel(r7, r9)
            int r7 = r7 + r8
            org.objectweb.asm.Label[] r8 = r12.f4621h
            r10.readLabel(r7, r8)
            int r13 = r13 + 6
            int r6 = r6 + -1
            goto L_0x0038
        L_0x0056:
            int r13 = r13 + 2
        L_0x0058:
            int r6 = r10.readByte(r13)
            r7 = 66
            r8 = 0
            if (r4 != r7) goto L_0x007e
            if (r6 != 0) goto L_0x0064
            goto L_0x006b
        L_0x0064:
            org.objectweb.asm.TypePath r8 = new org.objectweb.asm.TypePath
            byte[] r4 = r10.f4570b
            r8.<init>(r4, r13)
        L_0x006b:
            int r6 = r6 * 2
            int r6 = r6 + r5
            int r13 = r13 + r6
            int r4 = r13 + 2
            java.lang.String r13 = r10.readUTF8(r13, r0)
            org.objectweb.asm.AnnotationVisitor r13 = r11.visitTryCatchAnnotation(r3, r8, r13, r14)
            int r13 = r10.m4158a(r4, r0, r5, r13)
            goto L_0x0087
        L_0x007e:
            int r13 = r13 + 3
            int r6 = r6 * 2
            int r13 = r13 + r6
            int r13 = r10.m4158a(r13, r0, r5, r8)
        L_0x0087:
            int r2 = r2 + 1
            goto L_0x000b
        L_0x008a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.m4168a(org.objectweb.asm.MethodVisitor, org.objectweb.asm.Context, int, boolean):int[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01bf, code lost:
        if (r1.f4724j == 0) goto L_0x01de;
     */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01e3  */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int m4169b(org.objectweb.asm.ClassVisitor r32, org.objectweb.asm.Context r33, int r34) {
        /*
            r31 = this;
            r8 = r31
            r9 = r33
            r0 = r34
            char[] r10 = r9.f4616c
            int r1 = r8.readUnsignedShort(r0)
            r9.f4618e = r1
            int r1 = r0 + 2
            java.lang.String r1 = r8.readUTF8(r1, r10)
            r9.f4619f = r1
            int r1 = r0 + 4
            java.lang.String r1 = r8.readUTF8(r1, r10)
            r9.f4620g = r1
            int r11 = r0 + 6
            int r0 = r8.readUnsignedShort(r11)
            r14 = r0
            r15 = r11
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r13 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
        L_0x0037:
            if (r14 <= 0) goto L_0x0182
            int r12 = r15 + 2
            java.lang.String r12 = r8.readUTF8(r12, r10)
            r21 = r0
            java.lang.String r0 = "Code"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x0068
            int r0 = r9.f4615b
            r12 = 1
            r0 = r0 & r12
            if (r0 != 0) goto L_0x0057
            int r0 = r15 + 8
            r19 = r0
        L_0x0053:
            r0 = r21
            goto L_0x0175
        L_0x0057:
            r27 = r1
            r28 = r2
            r12 = r3
            r29 = r4
            r30 = r5
            r22 = r7
        L_0x0062:
            r26 = r21
            r21 = r6
            goto L_0x0166
        L_0x0068:
            java.lang.String r0 = "Exceptions"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x0090
            int r0 = r15 + 8
            int r0 = r8.readUnsignedShort(r0)
            java.lang.String[] r0 = new java.lang.String[r0]
            int r6 = r15 + 10
            r20 = r1
            r12 = r6
            r6 = 0
        L_0x007e:
            int r1 = r0.length
            if (r6 >= r1) goto L_0x008c
            java.lang.String r1 = r8.readClass(r12, r10)
            r0[r6] = r1
            int r12 = r12 + 2
            int r6 = r6 + 1
            goto L_0x007e
        L_0x008c:
            r6 = r0
            r17 = r12
            goto L_0x00a1
        L_0x0090:
            r20 = r1
            java.lang.String r0 = "Signature"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00a4
            int r0 = r15 + 8
            java.lang.String r0 = r8.readUTF8(r0, r10)
            r7 = r0
        L_0x00a1:
            r1 = r20
            goto L_0x0053
        L_0x00a4:
            java.lang.String r0 = "Deprecated"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00bf
            int r0 = r9.f4618e
            r1 = 131072(0x20000, float:1.83671E-40)
        L_0x00b0:
            r0 = r0 | r1
            r9.f4618e = r0
            r28 = r2
            r12 = r3
            r29 = r4
            r30 = r5
            r22 = r7
            r27 = r20
            goto L_0x0062
        L_0x00bf:
            java.lang.String r0 = "RuntimeVisibleAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00cb
            int r0 = r15 + 8
            r5 = r0
            goto L_0x00a1
        L_0x00cb:
            java.lang.String r0 = "RuntimeVisibleTypeAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00d7
            int r0 = r15 + 8
            r2 = r0
            goto L_0x00a1
        L_0x00d7:
            java.lang.String r0 = "AnnotationDefault"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00e3
            int r0 = r15 + 8
            r3 = r0
            goto L_0x00a1
        L_0x00e3:
            java.lang.String r0 = "Synthetic"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00f1
            int r0 = r9.f4618e
            r1 = 266240(0x41000, float:3.73082E-40)
            goto L_0x00b0
        L_0x00f1:
            java.lang.String r0 = "RuntimeInvisibleAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00fd
            int r0 = r15 + 8
            r4 = r0
            goto L_0x00a1
        L_0x00fd:
            java.lang.String r0 = "RuntimeInvisibleTypeAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x010a
            int r0 = r15 + 8
            r1 = r0
            goto L_0x0053
        L_0x010a:
            java.lang.String r0 = "RuntimeVisibleParameterAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x0118
            int r0 = r15 + 8
            r1 = r20
            goto L_0x0175
        L_0x0118:
            java.lang.String r0 = "RuntimeInvisibleParameterAnnotations"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x0126
            int r0 = r15 + 8
            r18 = r0
            goto L_0x00a1
        L_0x0126:
            java.lang.String r0 = "MethodParameters"
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x0134
            int r0 = r15 + 8
            r16 = r0
            goto L_0x00a1
        L_0x0134:
            org.objectweb.asm.Attribute[] r1 = r9.f4614a
            int r22 = r15 + 8
            int r0 = r15 + 4
            int r23 = r8.readInt(r0)
            r24 = -1
            r25 = 0
            r26 = r21
            r0 = r31
            r27 = r20
            r28 = r2
            r2 = r12
            r12 = r3
            r3 = r22
            r29 = r4
            r4 = r23
            r30 = r5
            r5 = r10
            r21 = r6
            r6 = r24
            r22 = r7
            r7 = r25
            org.objectweb.asm.Attribute r0 = r0.m4163a(r1, r2, r3, r4, r5, r6, r7)
            if (r0 == 0) goto L_0x0166
            r0.f4565a = r13
            r13 = r0
        L_0x0166:
            r3 = r12
            r6 = r21
            r7 = r22
            r0 = r26
            r1 = r27
            r2 = r28
            r4 = r29
            r5 = r30
        L_0x0175:
            int r12 = r15 + 4
            int r12 = r8.readInt(r12)
            int r12 = r12 + 6
            int r15 = r15 + r12
            int r14 = r14 + -1
            goto L_0x0037
        L_0x0182:
            r26 = r0
            r27 = r1
            r28 = r2
            r12 = r3
            r29 = r4
            r30 = r5
            r21 = r6
            r22 = r7
            int r15 = r15 + 2
            int r1 = r9.f4618e
            java.lang.String r2 = r9.f4619f
            java.lang.String r3 = r9.f4620g
            r0 = r32
            r4 = r22
            r5 = r21
            org.objectweb.asm.MethodVisitor r0 = r0.visitMethod(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x01a6
            return r15
        L_0x01a6:
            boolean r1 = r0 instanceof org.objectweb.asm.MethodWriter
            if (r1 == 0) goto L_0x01ea
            r1 = r0
            org.objectweb.asm.MethodWriter r1 = (org.objectweb.asm.MethodWriter) r1
            org.objectweb.asm.ClassWriter r2 = r1.f4716b
            org.objectweb.asm.ClassReader r2 = r2.f4586M
            if (r2 != r8) goto L_0x01ea
            java.lang.String r2 = r1.f4721g
            r7 = r22
            if (r7 != r2) goto L_0x01ea
            r6 = r21
            if (r6 != 0) goto L_0x01c2
            int r2 = r1.f4724j
            if (r2 != 0) goto L_0x01e0
            goto L_0x01de
        L_0x01c2:
            int r2 = r6.length
            int r3 = r1.f4724j
            if (r2 != r3) goto L_0x01e0
            int r2 = r6.length
            r3 = 1
            int r2 = r2 - r3
        L_0x01ca:
            if (r2 < 0) goto L_0x01de
            int r3 = r17 + -2
            int[] r4 = r1.f4725k
            r4 = r4[r2]
            int r5 = r8.readUnsignedShort(r3)
            if (r4 == r5) goto L_0x01d9
            goto L_0x01e0
        L_0x01d9:
            int r2 = r2 + -1
            r17 = r3
            goto L_0x01ca
        L_0x01de:
            r2 = 1
            goto L_0x01e1
        L_0x01e0:
            r2 = 0
        L_0x01e1:
            if (r2 == 0) goto L_0x01ea
            r1.f4722h = r11
            int r0 = r15 - r11
            r1.f4723i = r0
            return r15
        L_0x01ea:
            if (r16 == 0) goto L_0x020b
            byte[] r1 = r8.f4570b
            byte r1 = r1[r16]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = 1
            int r16 = r16 + 1
            r2 = r16
        L_0x01f7:
            if (r1 <= 0) goto L_0x020b
            java.lang.String r3 = r8.readUTF8(r2, r10)
            int r4 = r2 + 2
            int r4 = r8.readUnsignedShort(r4)
            r0.visitParameter(r3, r4)
            int r1 = r1 + -1
            int r2 = r2 + 4
            goto L_0x01f7
        L_0x020b:
            if (r12 == 0) goto L_0x021a
            org.objectweb.asm.AnnotationVisitor r1 = r0.visitAnnotationDefault()
            r2 = 0
            r8.m4157a(r12, r10, r2, r1)
            if (r1 == 0) goto L_0x021a
            r1.visitEnd()
        L_0x021a:
            r5 = r30
            if (r5 == 0) goto L_0x0238
            int r1 = r8.readUnsignedShort(r5)
            int r5 = r5 + 2
        L_0x0224:
            if (r1 <= 0) goto L_0x0238
            int r2 = r5 + 2
            java.lang.String r3 = r8.readUTF8(r5, r10)
            r4 = 1
            org.objectweb.asm.AnnotationVisitor r3 = r0.visitAnnotation(r3, r4)
            int r5 = r8.m4158a(r2, r10, r4, r3)
            int r1 = r1 + -1
            goto L_0x0224
        L_0x0238:
            r4 = r29
            if (r4 == 0) goto L_0x0258
            int r1 = r8.readUnsignedShort(r4)
            int r4 = r4 + 2
        L_0x0242:
            if (r1 <= 0) goto L_0x0258
            int r2 = r4 + 2
            java.lang.String r3 = r8.readUTF8(r4, r10)
            r4 = 0
            org.objectweb.asm.AnnotationVisitor r3 = r0.visitAnnotation(r3, r4)
            r4 = 1
            int r2 = r8.m4158a(r2, r10, r4, r3)
            int r1 = r1 + -1
            r4 = r2
            goto L_0x0242
        L_0x0258:
            r2 = r28
            if (r2 == 0) goto L_0x027e
            int r1 = r8.readUnsignedShort(r2)
            int r2 = r2 + 2
        L_0x0262:
            if (r1 <= 0) goto L_0x027e
            int r2 = r8.m4160a(r9, r2)
            int r3 = r2 + 2
            int r4 = r9.f4622i
            org.objectweb.asm.TypePath r5 = r9.f4623j
            java.lang.String r2 = r8.readUTF8(r2, r10)
            r6 = 1
            org.objectweb.asm.AnnotationVisitor r2 = r0.visitTypeAnnotation(r4, r5, r2, r6)
            int r2 = r8.m4158a(r3, r10, r6, r2)
            int r1 = r1 + -1
            goto L_0x0262
        L_0x027e:
            r1 = r27
            if (r1 == 0) goto L_0x02a5
            int r2 = r8.readUnsignedShort(r1)
            int r1 = r1 + 2
        L_0x0288:
            if (r2 <= 0) goto L_0x02a5
            int r1 = r8.m4160a(r9, r1)
            int r3 = r1 + 2
            int r4 = r9.f4622i
            org.objectweb.asm.TypePath r5 = r9.f4623j
            java.lang.String r1 = r8.readUTF8(r1, r10)
            r6 = 0
            org.objectweb.asm.AnnotationVisitor r1 = r0.visitTypeAnnotation(r4, r5, r1, r6)
            r4 = 1
            int r1 = r8.m4158a(r3, r10, r4, r1)
            int r2 = r2 + -1
            goto L_0x0288
        L_0x02a5:
            r4 = 1
            r1 = r26
            if (r1 == 0) goto L_0x02ad
            r8.m4170b(r0, r9, r1, r4)
        L_0x02ad:
            r1 = r18
            if (r1 == 0) goto L_0x02b5
            r2 = 0
            r8.m4170b(r0, r9, r1, r2)
        L_0x02b5:
            if (r13 == 0) goto L_0x02c1
            org.objectweb.asm.Attribute r1 = r13.f4565a
            r2 = 0
            r13.f4565a = r2
            r0.visitAttribute(r13)
            r13 = r1
            goto L_0x02b5
        L_0x02c1:
            r13 = r19
            if (r13 == 0) goto L_0x02cb
            r0.visitCode()
            r8.m4166a(r0, r9, r13)
        L_0x02cb:
            r0.visitEnd()
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.m4169b(org.objectweb.asm.ClassVisitor, org.objectweb.asm.Context, int):int");
    }

    /* renamed from: b */
    private void m4170b(MethodVisitor methodVisitor, Context context, int i, boolean z) {
        int i2 = i + 1;
        byte b = this.f4570b[i] & 255;
        int length = Type.getArgumentTypes(context.f4620g).length - b;
        int i3 = 0;
        while (i3 < length) {
            AnnotationVisitor visitParameterAnnotation = methodVisitor.visitParameterAnnotation(i3, "Ljava/lang/Synthetic;", false);
            if (visitParameterAnnotation != null) {
                visitParameterAnnotation.visitEnd();
            }
            i3++;
        }
        char[] cArr = context.f4616c;
        while (i3 < b + length) {
            i2 += 2;
            for (int readUnsignedShort = readUnsignedShort(i2); readUnsignedShort > 0; readUnsignedShort--) {
                i2 = m4158a(i2 + 2, cArr, true, methodVisitor.visitParameterAnnotation(i3, readUTF8(i2, cArr), z));
            }
            i3++;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76714a(ClassWriter classWriter) {
        char[] cArr = new char[this.f4572d];
        int length = this.f4569a.length;
        Item[] itemArr = new Item[length];
        int i = 1;
        while (i < length) {
            int i2 = this.f4569a[i];
            byte b = this.f4570b[i2 - 1];
            Item item = new Item(i);
            if (b == 1) {
                String[] strArr = this.f4571c;
                String str = strArr[i];
                if (str == null) {
                    int i3 = this.f4569a[i];
                    str = m4162a(i3 + 2, readUnsignedShort(i3), cArr);
                    strArr[i] = str;
                }
                item.mo76788a(b, str, null, null);
            } else if (b == 15) {
                int i4 = this.f4569a[readUnsignedShort(i2 + 1)];
                int i5 = this.f4569a[readUnsignedShort(i4 + 2)];
                item.mo76788a(readByte(i2) + 20, readClass(i4, cArr), readUTF8(i5, cArr), readUTF8(i5 + 2, cArr));
            } else if (b == 18) {
                if (classWriter.f4575A == null) {
                    m4164a(classWriter, itemArr, cArr);
                }
                int i6 = this.f4569a[readUnsignedShort(i2 + 2)];
                item.mo76790a(readUTF8(i6, cArr), readUTF8(i6 + 2, cArr), readUnsignedShort(i2));
            } else if (b == 3) {
                item.mo76786a(readInt(i2));
            } else if (b != 4) {
                if (b == 5) {
                    item.mo76789a(readLong(i2));
                } else if (b != 6) {
                    switch (b) {
                        case 9:
                        case 10:
                        case 11:
                            int i7 = this.f4569a[readUnsignedShort(i2 + 2)];
                            item.mo76788a(b, readClass(i2, cArr), readUTF8(i7, cArr), readUTF8(i7 + 2, cArr));
                            break;
                        case 12:
                            item.mo76788a(b, readUTF8(i2, cArr), readUTF8(i2 + 2, cArr), null);
                            break;
                        default:
                            item.mo76788a(b, readUTF8(i2, cArr), null, null);
                            break;
                    }
                } else {
                    item.mo76784a(Double.longBitsToDouble(readLong(i2)));
                }
                i++;
            } else {
                item.mo76785a(Float.intBitsToFloat(readInt(i2)));
            }
            int length2 = item.f4675j % itemArr.length;
            item.f4676k = itemArr[length2];
            itemArr[length2] = item;
            i++;
        }
        int i8 = this.f4569a[1] - 1;
        classWriter.f4591d.putByteArray(this.f4570b, i8, this.header - i8);
        classWriter.f4592e = itemArr;
        double d = (double) length;
        Double.isNaN(d);
        classWriter.f4593f = (int) (d * 0.75d);
        classWriter.f4590c = length;
    }

    public void accept(ClassVisitor classVisitor, int i) {
        accept(classVisitor, new Attribute[0], i);
    }

    public void accept(ClassVisitor classVisitor, Attribute[] attributeArr, int i) {
        Context context;
        String[] strArr;
        char[] cArr;
        int i2;
        String str;
        String str2;
        String str3;
        String str4;
        Attribute attribute;
        int i3;
        ClassVisitor classVisitor2 = classVisitor;
        int i4 = i;
        int i5 = this.header;
        char[] cArr2 = new char[this.f4572d];
        Context context2 = new Context();
        context2.f4614a = attributeArr;
        context2.f4615b = i4;
        context2.f4616c = cArr2;
        int readUnsignedShort = readUnsignedShort(i5);
        String readClass = readClass(i5 + 2, cArr2);
        String readClass2 = readClass(i5 + 4, cArr2);
        String[] strArr2 = new String[readUnsignedShort(i5 + 6)];
        int i6 = i5 + 8;
        for (int i7 = 0; i7 < strArr2.length; i7++) {
            strArr2[i7] = readClass(i6, cArr2);
            i6 += 2;
        }
        int a = m4155a();
        int i8 = a;
        int i9 = readUnsignedShort;
        int readUnsignedShort2 = readUnsignedShort(a);
        String[] strArr3 = strArr2;
        String str5 = null;
        int i10 = 0;
        String str6 = null;
        String str7 = null;
        String str8 = null;
        String str9 = null;
        String str10 = null;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        Attribute attribute2 = null;
        while (readUnsignedShort2 > 0) {
            String readUTF8 = readUTF8(i8 + 2, cArr2);
            if ("SourceFile".equals(readUTF8)) {
                str8 = readUTF8(i8 + 8, cArr2);
            } else if ("InnerClasses".equals(readUTF8)) {
                i14 = i8 + 8;
            } else if ("EnclosingMethod".equals(readUTF8)) {
                str5 = readClass(i8 + 8, cArr2);
                int readUnsignedShort3 = readUnsignedShort(i8 + 10);
                if (readUnsignedShort3 != 0) {
                    str10 = readUTF8(this.f4569a[readUnsignedShort3], cArr2);
                    str6 = readUTF8(this.f4569a[readUnsignedShort3] + 2, cArr2);
                }
            } else if ("Signature".equals(readUTF8)) {
                str9 = readUTF8(i8 + 8, cArr2);
            } else if ("RuntimeVisibleAnnotations".equals(readUTF8)) {
                i10 = i8 + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(readUTF8)) {
                i12 = i8 + 8;
            } else {
                if ("Deprecated".equals(readUTF8)) {
                    i3 = 131072;
                } else if ("Synthetic".equals(readUTF8)) {
                    i3 = 266240;
                } else if ("SourceDebugExtension".equals(readUTF8)) {
                    int readInt = readInt(i8 + 4);
                    str7 = m4162a(i8 + 8, readInt, new char[readInt]);
                } else if ("RuntimeInvisibleAnnotations".equals(readUTF8)) {
                    i11 = i8 + 8;
                } else if ("RuntimeInvisibleTypeAnnotations".equals(readUTF8)) {
                    i13 = i8 + 8;
                } else {
                    if ("BootstrapMethods".equals(readUTF8)) {
                        int[] iArr = new int[readUnsignedShort(i8 + 8)];
                        int i15 = i8 + 10;
                        for (int i16 = 0; i16 < iArr.length; i16++) {
                            iArr[i16] = i15;
                            i15 += (readUnsignedShort(i15 + 2) + 2) << 1;
                        }
                        context2.f4617d = iArr;
                        str2 = str5;
                        i2 = i10;
                        cArr = cArr2;
                        context = context2;
                        attribute = attribute2;
                        strArr = strArr3;
                        str = str6;
                        str3 = str7;
                        str4 = str8;
                    } else {
                        str2 = str5;
                        context = context2;
                        i2 = i10;
                        String str11 = str6;
                        String str12 = readUTF8;
                        String str13 = str7;
                        int i17 = i8 + 8;
                        String str14 = str8;
                        String str15 = str13;
                        char[] cArr3 = cArr2;
                        cArr = cArr2;
                        str4 = str14;
                        strArr = strArr3;
                        str = str11;
                        str3 = str15;
                        Attribute a2 = m4163a(attributeArr, str12, i17, readInt(i8 + 4), cArr3, -1, null);
                        attribute = attribute2;
                        if (a2 != null) {
                            a2.f4565a = attribute;
                            attribute2 = a2;
                            str8 = str4;
                            str7 = str3;
                            str5 = str2;
                            str6 = str;
                            i10 = i2;
                            i8 += readInt(i8 + 4) + 6;
                            readUnsignedShort2--;
                            Attribute[] attributeArr2 = attributeArr;
                            cArr2 = cArr;
                            strArr3 = strArr;
                            context2 = context;
                        }
                    }
                    attribute2 = attribute;
                    str8 = str4;
                    str7 = str3;
                    str5 = str2;
                    str6 = str;
                    i10 = i2;
                    i8 += readInt(i8 + 4) + 6;
                    readUnsignedShort2--;
                    Attribute[] attributeArr22 = attributeArr;
                    cArr2 = cArr;
                    strArr3 = strArr;
                    context2 = context;
                }
                i9 |= i3;
            }
            cArr = cArr2;
            context = context2;
            strArr = strArr3;
            i8 += readInt(i8 + 4) + 6;
            readUnsignedShort2--;
            Attribute[] attributeArr222 = attributeArr;
            cArr2 = cArr;
            strArr3 = strArr;
            context2 = context;
        }
        String str16 = str5;
        int i18 = i10;
        char[] cArr4 = cArr2;
        Context context3 = context2;
        Attribute attribute3 = attribute2;
        String[] strArr4 = strArr3;
        String str17 = str6;
        String str18 = str7;
        String str19 = str8;
        classVisitor.visit(readInt(this.f4569a[1] - 7), i9, readClass, str9, readClass2, strArr4);
        if ((i4 & 2) == 0 && !(str19 == null && str18 == null)) {
            classVisitor2.visitSource(str19, str18);
        }
        if (str16 != null) {
            classVisitor2.visitOuterClass(str16, str10, str17);
        }
        if (i18 != 0) {
            int i19 = i18;
            int i20 = i19 + 2;
            for (int readUnsignedShort4 = readUnsignedShort(i19); readUnsignedShort4 > 0; readUnsignedShort4--) {
                char[] cArr5 = cArr4;
                i20 = m4158a(i20 + 2, cArr5, true, classVisitor2.visitAnnotation(readUTF8(i20, cArr5), true));
            }
        }
        char[] cArr6 = cArr4;
        int i21 = i11;
        if (i21 != 0) {
            int i22 = i21 + 2;
            for (int readUnsignedShort5 = readUnsignedShort(i21); readUnsignedShort5 > 0; readUnsignedShort5--) {
                i22 = m4158a(i22 + 2, cArr6, true, classVisitor2.visitAnnotation(readUTF8(i22, cArr6), false));
            }
        }
        int i23 = i12;
        if (i23 != 0) {
            int i24 = i23 + 2;
            for (int readUnsignedShort6 = readUnsignedShort(i23); readUnsignedShort6 > 0; readUnsignedShort6--) {
                Context context4 = context3;
                int a3 = m4160a(context4, i24);
                i24 = m4158a(a3 + 2, cArr6, true, classVisitor2.visitTypeAnnotation(context4.f4622i, context4.f4623j, readUTF8(a3, cArr6), true));
            }
        }
        Context context5 = context3;
        int i25 = i13;
        if (i25 != 0) {
            int i26 = i25 + 2;
            for (int readUnsignedShort7 = readUnsignedShort(i25); readUnsignedShort7 > 0; readUnsignedShort7--) {
                int a4 = m4160a(context5, i26);
                i26 = m4158a(a4 + 2, cArr6, true, classVisitor2.visitTypeAnnotation(context5.f4622i, context5.f4623j, readUTF8(a4, cArr6), false));
            }
        }
        while (attribute3 != null) {
            Attribute attribute4 = attribute3.f4565a;
            attribute3.f4565a = null;
            classVisitor2.visitAttribute(attribute3);
            attribute3 = attribute4;
        }
        int i27 = i14;
        if (i27 != 0) {
            int i28 = i27 + 2;
            int i29 = i28;
            for (int readUnsignedShort8 = readUnsignedShort(i27); readUnsignedShort8 > 0; readUnsignedShort8--) {
                classVisitor2.visitInnerClass(readClass(i29, cArr6), readClass(i29 + 2, cArr6), readUTF8(i29 + 4, cArr6), readUnsignedShort(i29 + 6));
                i29 += 8;
            }
        }
        int length = this.header + 10 + (strArr4.length * 2);
        for (int readUnsignedShort9 = readUnsignedShort(length - 2); readUnsignedShort9 > 0; readUnsignedShort9--) {
            length = m4159a(classVisitor2, context5, length);
        }
        int i30 = length + 2;
        for (int readUnsignedShort10 = readUnsignedShort(i30 - 2); readUnsignedShort10 > 0; readUnsignedShort10--) {
            i30 = m4169b(classVisitor2, context5, i30);
        }
        classVisitor.visitEnd();
    }

    public int getAccess() {
        return readUnsignedShort(this.header);
    }

    public String getClassName() {
        return readClass(this.header + 2, new char[this.f4572d]);
    }

    public String[] getInterfaces() {
        int i = this.header + 6;
        int readUnsignedShort = readUnsignedShort(i);
        String[] strArr = new String[readUnsignedShort];
        if (readUnsignedShort > 0) {
            char[] cArr = new char[this.f4572d];
            for (int i2 = 0; i2 < readUnsignedShort; i2++) {
                i += 2;
                strArr[i2] = readClass(i, cArr);
            }
        }
        return strArr;
    }

    public int getItem(int i) {
        return this.f4569a[i];
    }

    public int getItemCount() {
        return this.f4569a.length;
    }

    public int getMaxStringLength() {
        return this.f4572d;
    }

    public String getSuperName() {
        return readClass(this.header + 4, new char[this.f4572d]);
    }

    public int readByte(int i) {
        return this.f4570b[i] & 255;
    }

    public String readClass(int i, char[] cArr) {
        return readUTF8(this.f4569a[readUnsignedShort(i)], cArr);
    }

    public Object readConst(int i, char[] cArr) {
        int i2 = this.f4569a[i];
        byte b = this.f4570b[i2 - 1];
        if (b == 16) {
            return Type.getMethodType(readUTF8(i2, cArr));
        }
        switch (b) {
            case 3:
                return new Integer(readInt(i2));
            case 4:
                return new Float(Float.intBitsToFloat(readInt(i2)));
            case 5:
                return new Long(readLong(i2));
            case 6:
                return new Double(Double.longBitsToDouble(readLong(i2)));
            case 7:
                return Type.getObjectType(readUTF8(i2, cArr));
            case 8:
                return readUTF8(i2, cArr);
            default:
                int readByte = readByte(i2);
                int[] iArr = this.f4569a;
                int i3 = iArr[readUnsignedShort(i2 + 1)];
                String readClass = readClass(i3, cArr);
                int i4 = iArr[readUnsignedShort(i3 + 2)];
                return new Handle(readByte, readClass, readUTF8(i4, cArr), readUTF8(i4 + 2, cArr));
        }
    }

    public int readInt(int i) {
        byte[] bArr = this.f4570b;
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
    }

    /* access modifiers changed from: protected */
    public Label readLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            labelArr[i] = new Label();
        }
        return labelArr[i];
    }

    public long readLong(int i) {
        return (((long) readInt(i)) << 32) | (((long) readInt(i + 4)) & 4294967295L);
    }

    public short readShort(int i) {
        byte[] bArr = this.f4570b;
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public String readUTF8(int i, char[] cArr) {
        int readUnsignedShort = readUnsignedShort(i);
        if (i == 0 || readUnsignedShort == 0) {
            return null;
        }
        String[] strArr = this.f4571c;
        String str = strArr[readUnsignedShort];
        if (str != null) {
            return str;
        }
        int i2 = this.f4569a[readUnsignedShort];
        String a = m4162a(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[readUnsignedShort] = a;
        return a;
    }

    public int readUnsignedShort(int i) {
        byte[] bArr = this.f4570b;
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }
}
