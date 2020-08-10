package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.InputChunked;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.p035io.OutputChunked;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class DeflateSerializer extends Serializer {
    private int compressionLevel = 4;
    private boolean noHeaders = true;
    private final Serializer serializer;

    public DeflateSerializer(Serializer serializer2) {
        this.serializer = serializer2;
    }

    public void write(Kryo kryo, Output output, Object obj) {
        OutputChunked outputChunked = new OutputChunked(output, 256);
        Deflater deflater = new Deflater(this.compressionLevel, this.noHeaders);
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputChunked, deflater);
            Output output2 = new Output((OutputStream) deflaterOutputStream, 256);
            this.serializer.write(kryo, output2, obj);
            output2.flush();
            deflaterOutputStream.finish();
            deflater.end();
            outputChunked.endChunks();
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        } catch (Throwable th) {
            deflater.end();
            throw th;
        }
    }

    public Object read(Kryo kryo, Input input, Class cls) {
        Inflater inflater = new Inflater(this.noHeaders);
        try {
            return this.serializer.read(kryo, new Input(new InflaterInputStream(new InputChunked(input, 256), inflater), 256), cls);
        } finally {
            inflater.end();
        }
    }

    public void setNoHeaders(boolean z) {
        this.noHeaders = z;
    }

    public void setCompressionLevel(int i) {
        this.compressionLevel = i;
    }

    public Object copy(Kryo kryo, Object obj) {
        return this.serializer.copy(kryo, obj);
    }
}
