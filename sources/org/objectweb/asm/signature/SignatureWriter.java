package org.objectweb.asm.signature;

import kotlin.text.Typography;
import org.objectweb.asm.Opcodes;

public class SignatureWriter extends SignatureVisitor {

    /* renamed from: a */
    private final StringBuffer f4748a = new StringBuffer();

    /* renamed from: b */
    private boolean f4749b;

    /* renamed from: c */
    private boolean f4750c;

    /* renamed from: d */
    private int f4751d;

    public SignatureWriter() {
        super(Opcodes.ASM5);
    }

    /* renamed from: a */
    private void m4249a() {
        if (this.f4749b) {
            this.f4749b = false;
            this.f4748a.append(Typography.greater);
        }
    }

    /* renamed from: b */
    private void m4250b() {
        if (this.f4751d % 2 != 0) {
            this.f4748a.append(Typography.greater);
        }
        this.f4751d /= 2;
    }

    public String toString() {
        return this.f4748a.toString();
    }

    public SignatureVisitor visitArrayType() {
        this.f4748a.append('[');
        return this;
    }

    public void visitBaseType(char c) {
        this.f4748a.append(c);
    }

    public SignatureVisitor visitClassBound() {
        return this;
    }

    public void visitClassType(String str) {
        this.f4748a.append('L');
        this.f4748a.append(str);
        this.f4751d *= 2;
    }

    public void visitEnd() {
        m4250b();
        this.f4748a.append(';');
    }

    public SignatureVisitor visitExceptionType() {
        this.f4748a.append('^');
        return this;
    }

    public void visitFormalTypeParameter(String str) {
        if (!this.f4749b) {
            this.f4749b = true;
            this.f4748a.append(Typography.less);
        }
        this.f4748a.append(str);
        this.f4748a.append(':');
    }

    public void visitInnerClassType(String str) {
        m4250b();
        this.f4748a.append('.');
        this.f4748a.append(str);
        this.f4751d *= 2;
    }

    public SignatureVisitor visitInterface() {
        return this;
    }

    public SignatureVisitor visitInterfaceBound() {
        this.f4748a.append(':');
        return this;
    }

    public SignatureVisitor visitParameterType() {
        m4249a();
        if (!this.f4750c) {
            this.f4750c = true;
            this.f4748a.append('(');
        }
        return this;
    }

    public SignatureVisitor visitReturnType() {
        m4249a();
        if (!this.f4750c) {
            this.f4748a.append('(');
        }
        this.f4748a.append(')');
        return this;
    }

    public SignatureVisitor visitSuperclass() {
        m4249a();
        return this;
    }

    public SignatureVisitor visitTypeArgument(char c) {
        int i = this.f4751d;
        if (i % 2 == 0) {
            this.f4751d = i + 1;
            this.f4748a.append(Typography.less);
        }
        if (c != '=') {
            this.f4748a.append(c);
        }
        return this;
    }

    public void visitTypeArgument() {
        int i = this.f4751d;
        if (i % 2 == 0) {
            this.f4751d = i + 1;
            this.f4748a.append(Typography.less);
        }
        this.f4748a.append('*');
    }

    public void visitTypeVariable(String str) {
        this.f4748a.append('T');
        this.f4748a.append(str);
        this.f4748a.append(';');
    }
}
