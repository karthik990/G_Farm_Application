package org.apache.http.impl.p103io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.p104io.BufferInfo;
import org.apache.http.p104io.SessionInputBuffer;
import org.apache.http.util.Args;

/* renamed from: org.apache.http.impl.io.IdentityInputStream */
public class IdentityInputStream extends InputStream {
    private boolean closed = false;

    /* renamed from: in */
    private final SessionInputBuffer f4541in;

    public IdentityInputStream(SessionInputBuffer sessionInputBuffer) {
        this.f4541in = (SessionInputBuffer) Args.notNull(sessionInputBuffer, "Session input buffer");
    }

    public int available() throws IOException {
        SessionInputBuffer sessionInputBuffer = this.f4541in;
        if (sessionInputBuffer instanceof BufferInfo) {
            return ((BufferInfo) sessionInputBuffer).length();
        }
        return 0;
    }

    public void close() throws IOException {
        this.closed = true;
    }

    public int read() throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.f4541in.read();
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.closed) {
            return -1;
        }
        return this.f4541in.read(bArr, i, i2);
    }
}
