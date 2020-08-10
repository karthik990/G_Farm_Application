package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.io.OutputStream;
import org.objectweb.asm.Opcodes;

/* renamed from: com.esotericsoftware.kryo.io.OutputChunked */
public class OutputChunked extends Output {
    public OutputChunked() {
        super(2048);
    }

    public OutputChunked(int i) {
        super(i);
    }

    public OutputChunked(OutputStream outputStream) {
        super(outputStream, 2048);
    }

    public OutputChunked(OutputStream outputStream, int i) {
        super(outputStream, i);
    }

    public void flush() throws KryoException {
        if (position() > 0) {
            try {
                writeChunkSize();
                super.flush();
            } catch (IOException e) {
                throw new KryoException((Throwable) e);
            }
        }
        super.flush();
    }

    private void writeChunkSize() throws IOException {
        int position = position();
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Write chunk: ");
            sb.append(position);
            Log.trace("kryo", sb.toString());
        }
        OutputStream outputStream = getOutputStream();
        if ((position & -128) == 0) {
            outputStream.write(position);
            return;
        }
        outputStream.write((position & Opcodes.LAND) | 128);
        int i = position >>> 7;
        if ((i & -128) == 0) {
            outputStream.write(i);
            return;
        }
        outputStream.write((i & Opcodes.LAND) | 128);
        int i2 = i >>> 7;
        if ((i2 & -128) == 0) {
            outputStream.write(i2);
            return;
        }
        outputStream.write((i2 & Opcodes.LAND) | 128);
        int i3 = i2 >>> 7;
        if ((i3 & -128) == 0) {
            outputStream.write(i3);
            return;
        }
        outputStream.write((i3 & Opcodes.LAND) | 128);
        outputStream.write(i3 >>> 7);
    }

    public void endChunks() {
        flush();
        if (Log.TRACE) {
            Log.trace("kryo", "End chunks.");
        }
        try {
            getOutputStream().write(0);
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        }
    }
}
