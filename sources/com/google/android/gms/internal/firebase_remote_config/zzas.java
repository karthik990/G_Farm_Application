package com.google.android.gms.internal.firebase_remote_config;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class zzas extends FilterInputStream {
    private long zzcw = 0;
    private final /* synthetic */ zzar zzcx;

    public zzas(zzar zzar, InputStream inputStream) {
        this.zzcx = zzar;
        super(inputStream);
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read == -1) {
            zzaq();
        } else {
            this.zzcw += (long) read;
        }
        return read;
    }

    public final int read() throws IOException {
        int read = this.in.read();
        if (read == -1) {
            zzaq();
        } else {
            this.zzcw++;
        }
        return read;
    }

    public final long skip(long j) throws IOException {
        long skip = this.in.skip(j);
        this.zzcw += skip;
        return skip;
    }

    private final void zzaq() throws IOException {
        long contentLength = this.zzcx.getContentLength();
        if (contentLength != -1) {
            long j = this.zzcw;
            if (j != 0 && j < contentLength) {
                StringBuilder sb = new StringBuilder(102);
                sb.append("Connection closed prematurely: bytesRead = ");
                sb.append(j);
                sb.append(", Content-Length = ");
                sb.append(contentLength);
                throw new IOException(sb.toString());
            }
        }
    }
}
