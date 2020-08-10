package com.google.android.gms.internal.firebase_remote_config;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzce extends FilterOutputStream {
    private final zzcc zzgf;

    public zzce(OutputStream outputStream, Logger logger, Level level, int i) {
        super(outputStream);
        this.zzgf = new zzcc(logger, level, i);
    }

    public final void write(int i) throws IOException {
        this.out.write(i);
        this.zzgf.write(i);
    }

    public final void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        this.zzgf.write(bArr, i, i2);
    }

    public final void close() throws IOException {
        this.zzgf.close();
        super.close();
    }

    public final zzcc zzcd() {
        return this.zzgf;
    }
}
