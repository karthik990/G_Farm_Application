package org.objectweb.asm.signature;

public class SignatureReader {

    /* renamed from: a */
    private final String f4747a;

    public SignatureReader(String str) {
        this.f4747a = str;
    }

    /* renamed from: a */
    private static int m4248a(String str, int i, SignatureVisitor signatureVisitor) {
        int i2 = i + 1;
        char charAt = str.charAt(i);
        if (!(charAt == 'F' || charAt == 'V' || charAt == 'I' || charAt == 'J' || charAt == 'S')) {
            if (charAt == 'T') {
                int indexOf = str.indexOf(59, i2);
                signatureVisitor.visitTypeVariable(str.substring(i2, indexOf));
                return indexOf + 1;
            } else if (charAt != 'Z') {
                if (charAt != '[') {
                    switch (charAt) {
                        case 'B':
                        case 'C':
                        case 'D':
                            break;
                        default:
                            int i3 = i2;
                            boolean z = false;
                            boolean z2 = false;
                            while (true) {
                                int i4 = i2 + 1;
                                char charAt2 = str.charAt(i2);
                                if (charAt2 == '.' || charAt2 == ';') {
                                    if (!z) {
                                        String substring = str.substring(i3, i4 - 1);
                                        if (z2) {
                                            signatureVisitor.visitInnerClassType(substring);
                                        } else {
                                            signatureVisitor.visitClassType(substring);
                                        }
                                    }
                                    if (charAt2 == ';') {
                                        signatureVisitor.visitEnd();
                                        return i4;
                                    }
                                    i2 = i4;
                                    i3 = i2;
                                    z = false;
                                    z2 = true;
                                } else if (charAt2 != '<') {
                                    i2 = i4;
                                } else {
                                    String substring2 = str.substring(i3, i4 - 1);
                                    if (z2) {
                                        signatureVisitor.visitInnerClassType(substring2);
                                    } else {
                                        signatureVisitor.visitClassType(substring2);
                                    }
                                    while (true) {
                                        char charAt3 = str.charAt(i4);
                                        if (charAt3 != '*') {
                                            if (charAt3 == '+' || charAt3 == '-') {
                                                i4++;
                                            } else if (charAt3 != '>') {
                                                charAt3 = SignatureVisitor.INSTANCEOF;
                                            } else {
                                                i2 = i4;
                                                z = true;
                                            }
                                            i4 = m4248a(str, i4, signatureVisitor.visitTypeArgument(charAt3));
                                        } else {
                                            i4++;
                                            signatureVisitor.visitTypeArgument();
                                        }
                                    }
                                }
                            }
                            break;
                    }
                } else {
                    return m4248a(str, i2, signatureVisitor.visitArrayType());
                }
            }
        }
        signatureVisitor.visitBaseType(charAt);
        return i2;
    }

    public void accept(SignatureVisitor signatureVisitor) {
        char charAt;
        String str = this.f4747a;
        int length = str.length();
        int i = 0;
        if (str.charAt(0) == '<') {
            i = 2;
            do {
                int indexOf = str.indexOf(58, i);
                signatureVisitor.visitFormalTypeParameter(str.substring(i - 1, indexOf));
                int i2 = indexOf + 1;
                char charAt2 = str.charAt(i2);
                if (charAt2 == 'L' || charAt2 == '[' || charAt2 == 'T') {
                    i2 = m4248a(str, i2, signatureVisitor.visitClassBound());
                }
                while (true) {
                    i = i2 + 1;
                    charAt = str.charAt(i2);
                    if (charAt != ':') {
                        break;
                    }
                    i2 = m4248a(str, i, signatureVisitor.visitInterfaceBound());
                }
            } while (charAt != '>');
        }
        if (str.charAt(i) == '(') {
            int i3 = i + 1;
            while (str.charAt(i3) != ')') {
                i3 = m4248a(str, i3, signatureVisitor.visitParameterType());
            }
            int i4 = i3 + 1;
            SignatureVisitor visitReturnType = signatureVisitor.visitReturnType();
            while (true) {
                int a = m4248a(str, i4, visitReturnType);
                if (a < length) {
                    i4 = a + 1;
                    visitReturnType = signatureVisitor.visitExceptionType();
                } else {
                    return;
                }
            }
        } else {
            SignatureVisitor visitSuperclass = signatureVisitor.visitSuperclass();
            while (true) {
                i = m4248a(str, i, visitSuperclass);
                if (i < length) {
                    visitSuperclass = signatureVisitor.visitInterface();
                } else {
                    return;
                }
            }
        }
    }

    public void acceptType(SignatureVisitor signatureVisitor) {
        m4248a(this.f4747a, 0, signatureVisitor);
    }
}
