package com.startapp.p054a.p055a.p058c;

import java.io.Serializable;
import java.io.Writer;

/* renamed from: com.startapp.a.a.c.e */
/* compiled from: StartAppSDK */
public class C4705e extends Writer implements Serializable {

    /* renamed from: b */
    private final StringBuilder f2409b;

    public void close() {
    }

    public void flush() {
    }

    public C4705e() {
        this.f2409b = new StringBuilder();
    }

    public C4705e(int i) {
        this.f2409b = new StringBuilder(i);
    }

    public Writer append(char c) {
        this.f2409b.append(c);
        return this;
    }

    public Writer append(CharSequence charSequence) {
        this.f2409b.append(charSequence);
        return this;
    }

    public Writer append(CharSequence charSequence, int i, int i2) {
        this.f2409b.append(charSequence, i, i2);
        return this;
    }

    public void write(String str) {
        if (str != null) {
            this.f2409b.append(str);
        }
    }

    public void write(char[] cArr, int i, int i2) {
        if (cArr != null) {
            this.f2409b.append(cArr, i, i2);
        }
    }

    public String toString() {
        return this.f2409b.toString();
    }
}
