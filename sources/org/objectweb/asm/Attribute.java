package org.objectweb.asm;

public class Attribute {

    /* renamed from: a */
    Attribute f4565a;

    /* renamed from: b */
    byte[] f4566b;
    public final String type;

    protected Attribute(String str) {
        this.type = str;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final int mo76697a() {
        int i = 0;
        for (Attribute attribute = this; attribute != null; attribute = attribute.f4565a) {
            i++;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final int mo76698a(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        for (Attribute attribute = this; attribute != null; attribute = attribute.f4565a) {
            classWriter.newUTF8(attribute.type);
            i4 += attribute.write(classWriter, bArr, i, i2, i3).f4568b + 6;
        }
        return i4;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo76699a(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3, ByteVector byteVector) {
        for (Attribute attribute = this; attribute != null; attribute = attribute.f4565a) {
            ByteVector write = attribute.write(classWriter, bArr, i, i2, i3);
            byteVector.putShort(classWriter.newUTF8(attribute.type)).putInt(write.f4568b);
            byteVector.putByteArray(write.f4567a, 0, write.f4568b);
        }
    }

    /* access modifiers changed from: protected */
    public Label[] getLabels() {
        return null;
    }

    public boolean isCodeAttribute() {
        return false;
    }

    public boolean isUnknown() {
        return true;
    }

    /* access modifiers changed from: protected */
    public Attribute read(ClassReader classReader, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        Attribute attribute = new Attribute(this.type);
        attribute.f4566b = new byte[i2];
        System.arraycopy(classReader.f4570b, i, attribute.f4566b, 0, i2);
        return attribute;
    }

    /* access modifiers changed from: protected */
    public ByteVector write(ClassWriter classWriter, byte[] bArr, int i, int i2, int i3) {
        ByteVector byteVector = new ByteVector();
        byte[] bArr2 = this.f4566b;
        byteVector.f4567a = bArr2;
        byteVector.f4568b = bArr2.length;
        return byteVector;
    }
}
