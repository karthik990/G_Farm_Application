package com.google.android.gms.internal.firebase_remote_config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class zzcc extends ByteArrayOutputStream {
    private boolean closed;
    private final Logger logger;
    private int zzgc;
    private final int zzgd;
    private final Level zzge;

    public zzcc(Logger logger2, Level level, int i) {
        this.logger = (Logger) zzds.checkNotNull(logger2);
        this.zzge = (Level) zzds.checkNotNull(level);
        if (i >= 0) {
            this.zzgd = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public final synchronized void write(int i) {
        if (!this.closed) {
            this.zzgc++;
            if (this.count < this.zzgd) {
                super.write(i);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public final synchronized void write(byte[] bArr, int i, int i2) {
        if (!this.closed) {
            this.zzgc += i2;
            if (this.count < this.zzgd) {
                int i3 = this.count + i2;
                if (i3 > this.zzgd) {
                    i2 += this.zzgd - i3;
                }
                super.write(bArr, i, i2);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public final synchronized void close() throws IOException {
        if (!this.closed) {
            if (this.zzgc != 0) {
                StringBuilder sb = new StringBuilder("Total: ");
                zza(sb, this.zzgc);
                if (this.count != 0 && this.count < this.zzgc) {
                    sb.append(" (logging first ");
                    zza(sb, this.count);
                    sb.append(")");
                }
                this.logger.logp(Level.CONFIG, "com.google.api.client.util.LoggingByteArrayOutputStream", "close", sb.toString());
                if (this.count != 0) {
                    this.logger.logp(this.zzge, "com.google.api.client.util.LoggingByteArrayOutputStream", "close", toString("UTF-8").replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", " "));
                }
            }
            this.closed = true;
        }
    }

    private static void zza(StringBuilder sb, int i) {
        if (i == 1) {
            sb.append("1 byte");
            return;
        }
        sb.append(NumberFormat.getInstance().format((long) i));
        sb.append(" bytes");
    }
}
