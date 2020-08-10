package org.objectweb.asm;

final class FieldWriter extends FieldVisitor {

    /* renamed from: b */
    private final ClassWriter f4638b;

    /* renamed from: c */
    private final int f4639c;

    /* renamed from: d */
    private final int f4640d;

    /* renamed from: e */
    private final int f4641e;

    /* renamed from: f */
    private int f4642f;

    /* renamed from: g */
    private int f4643g;

    /* renamed from: h */
    private AnnotationWriter f4644h;

    /* renamed from: i */
    private AnnotationWriter f4645i;

    /* renamed from: j */
    private Attribute f4646j;

    /* renamed from: k */
    private AnnotationWriter f4647k;

    /* renamed from: l */
    private AnnotationWriter f4648l;

    FieldWriter(ClassWriter classWriter, int i, String str, String str2, String str3, Object obj) {
        super(Opcodes.ASM5);
        if (classWriter.f4576B == null) {
            classWriter.f4576B = this;
        } else {
            classWriter.f4577C.f4637fv = this;
        }
        classWriter.f4577C = this;
        this.f4638b = classWriter;
        this.f4639c = i;
        this.f4640d = classWriter.newUTF8(str);
        this.f4641e = classWriter.newUTF8(str2);
        if (str3 != null) {
            this.f4642f = classWriter.newUTF8(str3);
        }
        if (obj != null) {
            this.f4643g = classWriter.mo76750a(obj).f4668a;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public int mo76772a() {
        int i;
        if (this.f4643g != 0) {
            this.f4638b.newUTF8("ConstantValue");
            i = 16;
        } else {
            i = 8;
        }
        if ((this.f4639c & 4096) != 0 && ((this.f4638b.f4589b & 65535) < 49 || (this.f4639c & 262144) != 0)) {
            this.f4638b.newUTF8("Synthetic");
            i += 6;
        }
        if ((this.f4639c & 131072) != 0) {
            this.f4638b.newUTF8("Deprecated");
            i += 6;
        }
        if (this.f4642f != 0) {
            this.f4638b.newUTF8("Signature");
            i += 8;
        }
        if (this.f4644h != null) {
            this.f4638b.newUTF8("RuntimeVisibleAnnotations");
            i += this.f4644h.mo76695a() + 8;
        }
        if (this.f4645i != null) {
            this.f4638b.newUTF8("RuntimeInvisibleAnnotations");
            i += this.f4645i.mo76695a() + 8;
        }
        if (this.f4647k != null) {
            this.f4638b.newUTF8("RuntimeVisibleTypeAnnotations");
            i += this.f4647k.mo76695a() + 8;
        }
        if (this.f4648l != null) {
            this.f4638b.newUTF8("RuntimeInvisibleTypeAnnotations");
            i += this.f4648l.mo76695a() + 8;
        }
        Attribute attribute = this.f4646j;
        return attribute != null ? i + attribute.mo76698a(this.f4638b, null, 0, -1, -1) : i;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76773a(ByteVector byteVector) {
        int i = this.f4639c;
        byteVector.putShort(i & ((((i & 262144) / 64) | 393216) ^ -1)).putShort(this.f4640d).putShort(this.f4641e);
        int i2 = this.f4643g != 0 ? 1 : 0;
        if ((this.f4639c & 4096) != 0 && ((this.f4638b.f4589b & 65535) < 49 || (this.f4639c & 262144) != 0)) {
            i2++;
        }
        if ((this.f4639c & 131072) != 0) {
            i2++;
        }
        if (this.f4642f != 0) {
            i2++;
        }
        if (this.f4644h != null) {
            i2++;
        }
        if (this.f4645i != null) {
            i2++;
        }
        if (this.f4647k != null) {
            i2++;
        }
        if (this.f4648l != null) {
            i2++;
        }
        Attribute attribute = this.f4646j;
        if (attribute != null) {
            i2 += attribute.mo76697a();
        }
        byteVector.putShort(i2);
        if (this.f4643g != 0) {
            byteVector.putShort(this.f4638b.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.f4643g);
        }
        if ((this.f4639c & 4096) != 0 && ((this.f4638b.f4589b & 65535) < 49 || (this.f4639c & 262144) != 0)) {
            byteVector.putShort(this.f4638b.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.f4639c & 131072) != 0) {
            byteVector.putShort(this.f4638b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.f4642f != 0) {
            byteVector.putShort(this.f4638b.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.f4642f);
        }
        if (this.f4644h != null) {
            byteVector.putShort(this.f4638b.newUTF8("RuntimeVisibleAnnotations"));
            this.f4644h.mo76696a(byteVector);
        }
        if (this.f4645i != null) {
            byteVector.putShort(this.f4638b.newUTF8("RuntimeInvisibleAnnotations"));
            this.f4645i.mo76696a(byteVector);
        }
        if (this.f4647k != null) {
            byteVector.putShort(this.f4638b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.f4647k.mo76696a(byteVector);
        }
        if (this.f4648l != null) {
            byteVector.putShort(this.f4638b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.f4648l.mo76696a(byteVector);
        }
        Attribute attribute2 = this.f4646j;
        if (attribute2 != null) {
            attribute2.mo76699a(this.f4638b, null, 0, -1, -1, byteVector);
        }
    }

    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.f4638b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4638b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.f4563g = this.f4644h;
            this.f4644h = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4645i;
            this.f4645i = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitAttribute(Attribute attribute) {
        attribute.f4565a = this.f4646j;
        this.f4646j = attribute;
    }

    public void visitEnd() {
    }

    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.m4144a(i, typePath, byteVector);
        byteVector.putShort(this.f4638b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.f4638b, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4647k;
            this.f4647k = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4648l;
            this.f4648l = annotationWriter;
        }
        return annotationWriter;
    }
}
