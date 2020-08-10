package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.io.InputStream;
import org.objectweb.asm.Opcodes;

/* renamed from: com.esotericsoftware.kryo.io.InputChunked */
public class InputChunked extends Input {
    private int chunkSize = -1;

    public InputChunked() {
        super(2048);
    }

    public InputChunked(int i) {
        super(i);
    }

    public InputChunked(InputStream inputStream) {
        super(inputStream, 2048);
    }

    public InputChunked(InputStream inputStream, int i) {
        super(inputStream, i);
    }

    public void setInputStream(InputStream inputStream) {
        super.setInputStream(inputStream);
        this.chunkSize = -1;
    }

    public void setBuffer(byte[] bArr, int i, int i2) {
        super.setBuffer(bArr, i, i2);
        this.chunkSize = -1;
    }

    public void rewind() {
        super.rewind();
        this.chunkSize = -1;
    }

    /* access modifiers changed from: protected */
    public int fill(byte[] bArr, int i, int i2) throws KryoException {
        int i3 = this.chunkSize;
        if (i3 == -1) {
            readChunkSize();
        } else if (i3 == 0) {
            return -1;
        }
        int fill = super.fill(bArr, i, Math.min(this.chunkSize, i2));
        this.chunkSize -= fill;
        if (this.chunkSize == 0) {
            readChunkSize();
        }
        return fill;
    }

    private void readChunkSize() {
        try {
            InputStream inputStream = getInputStream();
            int i = 0;
            int i2 = 0;
            while (i < 32) {
                int read = inputStream.read();
                if (read != -1) {
                    i2 |= (read & Opcodes.LAND) << i;
                    if ((read & 128) == 0) {
                        this.chunkSize = i2;
                        if (Log.TRACE) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Read chunk: ");
                            sb.append(this.chunkSize);
                            Log.trace("kryo", sb.toString());
                            return;
                        }
                        return;
                    }
                    i += 7;
                } else {
                    throw new KryoException("Buffer underflow.");
                }
            }
            throw new KryoException("Malformed integer.");
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        }
    }

    public void nextChunks() {
        if (this.chunkSize == -1) {
            readChunkSize();
        }
        while (true) {
            int i = this.chunkSize;
            if (i <= 0) {
                break;
            }
            skip(i);
        }
        this.chunkSize = -1;
        if (Log.TRACE) {
            Log.trace("kryo", "Next chunks.");
        }
    }
}
