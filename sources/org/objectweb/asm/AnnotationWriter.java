package org.objectweb.asm;

final class AnnotationWriter extends AnnotationVisitor {

    /* renamed from: a */
    private final ClassWriter f4557a;

    /* renamed from: b */
    private int f4558b;

    /* renamed from: c */
    private final boolean f4559c;

    /* renamed from: d */
    private final ByteVector f4560d;

    /* renamed from: e */
    private final ByteVector f4561e;

    /* renamed from: f */
    private final int f4562f;

    /* renamed from: g */
    AnnotationWriter f4563g;

    /* renamed from: h */
    AnnotationWriter f4564h;

    AnnotationWriter(ClassWriter classWriter, boolean z, ByteVector byteVector, ByteVector byteVector2, int i) {
        super(Opcodes.ASM5);
        this.f4557a = classWriter;
        this.f4559c = z;
        this.f4560d = byteVector;
        this.f4561e = byteVector2;
        this.f4562f = i;
    }

    /* renamed from: a */
    static void m4144a(int i, TypePath typePath, ByteVector byteVector) {
        int i2 = i >>> 24;
        if (!(i2 == 0 || i2 == 1)) {
            switch (i2) {
                case 19:
                case 20:
                case 21:
                    byteVector.putByte(i2);
                    break;
                case 22:
                    break;
                default:
                    switch (i2) {
                        case 71:
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            byteVector.putInt(i);
                            break;
                        default:
                            byteVector.mo76706b(i2, (i & 16776960) >> 8);
                            break;
                    }
            }
        }
        byteVector.putShort(i >>> 16);
        if (typePath == null) {
            byteVector.putByte(0);
            return;
        }
        byteVector.putByteArray(typePath.f4744a, typePath.f4745b, (typePath.f4744a[typePath.f4745b] * 2) + 1);
    }

    /* renamed from: a */
    static void m4145a(AnnotationWriter[] annotationWriterArr, int i, ByteVector byteVector) {
        int length = ((annotationWriterArr.length - i) * 2) + 1;
        int i2 = i;
        while (true) {
            int i3 = 0;
            if (i2 >= annotationWriterArr.length) {
                break;
            }
            if (annotationWriterArr[i2] != null) {
                i3 = annotationWriterArr[i2].mo76695a();
            }
            length += i3;
            i2++;
        }
        byteVector.putInt(length).putByte(annotationWriterArr.length - i);
        while (i < annotationWriterArr.length) {
            AnnotationWriter annotationWriter = null;
            int i4 = 0;
            for (AnnotationWriter annotationWriter2 = annotationWriterArr[i]; annotationWriter2 != null; annotationWriter2 = annotationWriter2.f4563g) {
                i4++;
                annotationWriter2.visitEnd();
                annotationWriter2.f4564h = annotationWriter;
                annotationWriter = annotationWriter2;
            }
            byteVector.putShort(i4);
            while (annotationWriter != null) {
                byteVector.putByteArray(annotationWriter.f4560d.f4567a, 0, annotationWriter.f4560d.f4568b);
                annotationWriter = annotationWriter.f4564h;
            }
            i++;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public int mo76695a() {
        int i = 0;
        for (AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.f4563g) {
            i += annotationWriter.f4560d.f4568b;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76696a(ByteVector byteVector) {
        int i = 2;
        int i2 = 0;
        AnnotationWriter annotationWriter = null;
        for (AnnotationWriter annotationWriter2 = this; annotationWriter2 != null; annotationWriter2 = annotationWriter2.f4563g) {
            i2++;
            i += annotationWriter2.f4560d.f4568b;
            annotationWriter2.visitEnd();
            annotationWriter2.f4564h = annotationWriter;
            annotationWriter = annotationWriter2;
        }
        byteVector.putInt(i);
        byteVector.putShort(i2);
        while (annotationWriter != null) {
            byteVector.putByteArray(annotationWriter.f4560d.f4567a, 0, annotationWriter.f4560d.f4568b);
            annotationWriter = annotationWriter.f4564h;
        }
    }

    public void visit(String str, Object obj) {
        int i;
        ByteVector byteVector;
        ClassWriter classWriter;
        String descriptor;
        int i2;
        this.f4558b++;
        if (this.f4559c) {
            this.f4560d.putShort(this.f4557a.newUTF8(str));
        }
        if (obj instanceof String) {
            byteVector = this.f4560d;
            i = 115;
            classWriter = this.f4557a;
            descriptor = (String) obj;
        } else {
            i = 66;
            if (obj instanceof Byte) {
                byteVector = this.f4560d;
                i2 = this.f4557a.mo76747a((int) ((Byte) obj).byteValue()).f4668a;
                byteVector.mo76706b(i, i2);
            } else if (obj instanceof Boolean) {
                this.f4560d.mo76706b(90, this.f4557a.mo76747a(((Boolean) obj).booleanValue() ? 1 : 0).f4668a);
                return;
            } else if (obj instanceof Character) {
                this.f4560d.mo76706b(67, this.f4557a.mo76747a((int) ((Character) obj).charValue()).f4668a);
                return;
            } else if (obj instanceof Short) {
                this.f4560d.mo76706b(83, this.f4557a.mo76747a((int) ((Short) obj).shortValue()).f4668a);
                return;
            } else if (obj instanceof Type) {
                byteVector = this.f4560d;
                i = 99;
                classWriter = this.f4557a;
                descriptor = ((Type) obj).getDescriptor();
            } else {
                int i3 = 0;
                if (obj instanceof byte[]) {
                    byte[] bArr = (byte[]) obj;
                    this.f4560d.mo76706b(91, bArr.length);
                    while (i3 < bArr.length) {
                        this.f4560d.mo76706b(66, this.f4557a.mo76747a((int) bArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof boolean[]) {
                    boolean[] zArr = (boolean[]) obj;
                    this.f4560d.mo76706b(91, zArr.length);
                    while (i3 < zArr.length) {
                        this.f4560d.mo76706b(90, this.f4557a.mo76747a(zArr[i3] ? 1 : 0).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof short[]) {
                    short[] sArr = (short[]) obj;
                    this.f4560d.mo76706b(91, sArr.length);
                    while (i3 < sArr.length) {
                        this.f4560d.mo76706b(83, this.f4557a.mo76747a((int) sArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof char[]) {
                    char[] cArr = (char[]) obj;
                    this.f4560d.mo76706b(91, cArr.length);
                    while (i3 < cArr.length) {
                        this.f4560d.mo76706b(67, this.f4557a.mo76747a((int) cArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof int[]) {
                    int[] iArr = (int[]) obj;
                    this.f4560d.mo76706b(91, iArr.length);
                    while (i3 < iArr.length) {
                        this.f4560d.mo76706b(73, this.f4557a.mo76747a(iArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof long[]) {
                    long[] jArr = (long[]) obj;
                    this.f4560d.mo76706b(91, jArr.length);
                    while (i3 < jArr.length) {
                        this.f4560d.mo76706b(74, this.f4557a.mo76749a(jArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof float[]) {
                    float[] fArr = (float[]) obj;
                    this.f4560d.mo76706b(91, fArr.length);
                    while (i3 < fArr.length) {
                        this.f4560d.mo76706b(70, this.f4557a.mo76746a(fArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else if (obj instanceof double[]) {
                    double[] dArr = (double[]) obj;
                    this.f4560d.mo76706b(91, dArr.length);
                    while (i3 < dArr.length) {
                        this.f4560d.mo76706b(68, this.f4557a.mo76745a(dArr[i3]).f4668a);
                        i3++;
                    }
                    return;
                } else {
                    Item a = this.f4557a.mo76750a(obj);
                    this.f4560d.mo76706b(".s.IFJDCS".charAt(a.f4669b), a.f4668a);
                    return;
                }
            }
        }
        i2 = classWriter.newUTF8(descriptor);
        byteVector.mo76706b(i, i2);
    }

    public AnnotationVisitor visitAnnotation(String str, String str2) {
        this.f4558b++;
        if (this.f4559c) {
            this.f4560d.putShort(this.f4557a.newUTF8(str));
        }
        this.f4560d.mo76706b(64, this.f4557a.newUTF8(str2)).putShort(0);
        ClassWriter classWriter = this.f4557a;
        ByteVector byteVector = this.f4560d;
        AnnotationWriter annotationWriter = new AnnotationWriter(classWriter, true, byteVector, byteVector, byteVector.f4568b - 2);
        return annotationWriter;
    }

    public AnnotationVisitor visitArray(String str) {
        this.f4558b++;
        if (this.f4559c) {
            this.f4560d.putShort(this.f4557a.newUTF8(str));
        }
        this.f4560d.mo76706b(91, 0);
        ClassWriter classWriter = this.f4557a;
        ByteVector byteVector = this.f4560d;
        AnnotationWriter annotationWriter = new AnnotationWriter(classWriter, false, byteVector, byteVector, byteVector.f4568b - 2);
        return annotationWriter;
    }

    public void visitEnd() {
        ByteVector byteVector = this.f4561e;
        if (byteVector != null) {
            byte[] bArr = byteVector.f4567a;
            int i = this.f4562f;
            int i2 = this.f4558b;
            bArr[i] = (byte) (i2 >>> 8);
            bArr[i + 1] = (byte) i2;
        }
    }

    public void visitEnum(String str, String str2, String str3) {
        this.f4558b++;
        if (this.f4559c) {
            this.f4560d.putShort(this.f4557a.newUTF8(str));
        }
        this.f4560d.mo76706b(101, this.f4557a.newUTF8(str2)).putShort(this.f4557a.newUTF8(str3));
    }
}
