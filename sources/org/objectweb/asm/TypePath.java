package org.objectweb.asm;

public class TypePath {
    public static final int ARRAY_ELEMENT = 0;
    public static final int INNER_TYPE = 1;
    public static final int TYPE_ARGUMENT = 3;
    public static final int WILDCARD_BOUND = 2;

    /* renamed from: a */
    byte[] f4744a;

    /* renamed from: b */
    int f4745b;

    TypePath(byte[] bArr, int i) {
        this.f4744a = bArr;
        this.f4745b = i;
    }

    public static TypePath fromString(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length();
        ByteVector byteVector = new ByteVector(length);
        byteVector.putByte(0);
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            char charAt = str.charAt(i);
            if (charAt == '[') {
                byteVector.mo76705a(0, 0);
            } else if (charAt == '.') {
                byteVector.mo76705a(1, 0);
            } else if (charAt == '*') {
                byteVector.mo76705a(2, 0);
            } else if (charAt >= '0' && charAt <= '9') {
                int i3 = charAt - '0';
                while (i2 < length) {
                    char charAt2 = str.charAt(i2);
                    if (charAt2 < '0' || charAt2 > '9') {
                        break;
                    }
                    i3 = ((i3 * 10) + charAt2) - 48;
                    i2++;
                }
                if (i2 < length && str.charAt(i2) == ';') {
                    i2++;
                }
                byteVector.mo76705a(3, i3);
            }
            i = i2;
        }
        byteVector.f4567a[0] = (byte) (byteVector.f4568b / 2);
        return new TypePath(byteVector.f4567a, 0);
    }

    public int getLength() {
        return this.f4744a[this.f4745b];
    }

    public int getStep(int i) {
        return this.f4744a[this.f4745b + (i * 2) + 1];
    }

    public int getStepArgument(int i) {
        return this.f4744a[this.f4745b + (i * 2) + 2];
    }

    public String toString() {
        char c;
        int length = getLength();
        StringBuffer stringBuffer = new StringBuffer(length * 2);
        for (int i = 0; i < length; i++) {
            int step = getStep(i);
            if (step == 0) {
                c = '[';
            } else if (step == 1) {
                c = '.';
            } else if (step == 2) {
                c = '*';
            } else if (step != 3) {
                c = '_';
            } else {
                stringBuffer.append(getStepArgument(i));
                c = ';';
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }
}
