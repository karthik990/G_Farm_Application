package com.esotericsoftware.kryo.p035io;

import com.esotericsoftware.kryo.KryoException;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

/* renamed from: com.esotericsoftware.kryo.io.KryoDataInput */
public class KryoDataInput implements DataInput {
    protected Input input;

    public KryoDataInput(Input input2) {
        setInput(input2);
    }

    public void setInput(Input input2) {
        this.input = input2;
    }

    public void readFully(byte[] bArr) throws IOException {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) throws IOException {
        try {
            this.input.readBytes(bArr, i, i2);
        } catch (KryoException e) {
            throw new EOFException(e.getMessage());
        }
    }

    public int skipBytes(int i) throws IOException {
        return (int) this.input.skip((long) i);
    }

    public boolean readBoolean() throws IOException {
        return this.input.readBoolean();
    }

    public byte readByte() throws IOException {
        return this.input.readByte();
    }

    public int readUnsignedByte() throws IOException {
        return this.input.readByteUnsigned();
    }

    public short readShort() throws IOException {
        return this.input.readShort();
    }

    public int readUnsignedShort() throws IOException {
        return this.input.readShortUnsigned();
    }

    public char readChar() throws IOException {
        return this.input.readChar();
    }

    public int readInt() throws IOException {
        return this.input.readInt();
    }

    public long readLong() throws IOException {
        return this.input.readLong();
    }

    public float readFloat() throws IOException {
        return this.input.readFloat();
    }

    public double readDouble() throws IOException {
        return this.input.readDouble();
    }

    public String readLine() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public String readUTF() throws IOException {
        return this.input.readString();
    }
}
