package com.esotericsoftware.kryo.p035io;

import java.io.DataOutput;
import java.io.IOException;

/* renamed from: com.esotericsoftware.kryo.io.KryoDataOutput */
public class KryoDataOutput implements DataOutput {
    protected Output output;

    public KryoDataOutput(Output output2) {
        setOutput(output2);
    }

    public void setOutput(Output output2) {
        this.output = output2;
    }

    public void write(int i) throws IOException {
        this.output.write(i);
    }

    public void write(byte[] bArr) throws IOException {
        this.output.write(bArr);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.output.write(bArr, i, i2);
    }

    public void writeBoolean(boolean z) throws IOException {
        this.output.writeBoolean(z);
    }

    public void writeByte(int i) throws IOException {
        this.output.writeByte(i);
    }

    public void writeShort(int i) throws IOException {
        this.output.writeShort(i);
    }

    public void writeChar(int i) throws IOException {
        this.output.writeChar((char) i);
    }

    public void writeInt(int i) throws IOException {
        this.output.writeInt(i);
    }

    public void writeLong(long j) throws IOException {
        this.output.writeLong(j);
    }

    public void writeFloat(float f) throws IOException {
        this.output.writeFloat(f);
    }

    public void writeDouble(double d) throws IOException {
        this.output.writeDouble(d);
    }

    public void writeBytes(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            this.output.write((int) (byte) str.charAt(i));
        }
    }

    public void writeChars(String str) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            this.output.write((charAt >>> 8) & 255);
            this.output.write((charAt >>> 0) & 255);
        }
    }

    public void writeUTF(String str) throws IOException {
        this.output.writeString(str);
    }
}
