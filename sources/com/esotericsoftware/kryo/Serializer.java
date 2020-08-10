package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;

public abstract class Serializer<T> {
    private boolean acceptsNull;
    private boolean immutable;

    public abstract T read(Kryo kryo, Input input, Class<T> cls);

    public void setGenerics(Kryo kryo, Class[] clsArr) {
    }

    public abstract void write(Kryo kryo, Output output, T t);

    public Serializer() {
    }

    public Serializer(boolean z) {
        this.acceptsNull = z;
    }

    public Serializer(boolean z, boolean z2) {
        this.acceptsNull = z;
        this.immutable = z2;
    }

    public boolean getAcceptsNull() {
        return this.acceptsNull;
    }

    public void setAcceptsNull(boolean z) {
        this.acceptsNull = z;
    }

    public boolean isImmutable() {
        return this.immutable;
    }

    public void setImmutable(boolean z) {
        this.immutable = z;
    }

    public T copy(Kryo kryo, T t) {
        if (this.immutable) {
            return t;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Serializer does not support copy: ");
        sb.append(getClass().getName());
        throw new KryoException(sb.toString());
    }
}
