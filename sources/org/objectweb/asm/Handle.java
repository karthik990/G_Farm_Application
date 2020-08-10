package org.objectweb.asm;

public final class Handle {

    /* renamed from: a */
    final int f4658a;

    /* renamed from: b */
    final String f4659b;

    /* renamed from: c */
    final String f4660c;

    /* renamed from: d */
    final String f4661d;

    public Handle(int i, String str, String str2, String str3) {
        this.f4658a = i;
        this.f4659b = str;
        this.f4660c = str2;
        this.f4661d = str3;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Handle)) {
            return false;
        }
        Handle handle = (Handle) obj;
        if (this.f4658a != handle.f4658a || !this.f4659b.equals(handle.f4659b) || !this.f4660c.equals(handle.f4660c) || !this.f4661d.equals(handle.f4661d)) {
            z = false;
        }
        return z;
    }

    public String getDesc() {
        return this.f4661d;
    }

    public String getName() {
        return this.f4660c;
    }

    public String getOwner() {
        return this.f4659b;
    }

    public int getTag() {
        return this.f4658a;
    }

    public int hashCode() {
        return this.f4658a + (this.f4659b.hashCode() * this.f4660c.hashCode() * this.f4661d.hashCode());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.f4659b);
        stringBuffer.append('.');
        stringBuffer.append(this.f4660c);
        stringBuffer.append(this.f4661d);
        stringBuffer.append(" (");
        stringBuffer.append(this.f4658a);
        stringBuffer.append(')');
        return stringBuffer.toString();
    }
}
