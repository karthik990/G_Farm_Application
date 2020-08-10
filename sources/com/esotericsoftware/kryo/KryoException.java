package com.esotericsoftware.kryo;

public class KryoException extends RuntimeException {
    private StringBuffer trace;

    public KryoException() {
    }

    public KryoException(String str, Throwable th) {
        super(str, th);
    }

    public KryoException(String str) {
        super(str);
    }

    public KryoException(Throwable th) {
        super(th);
    }

    public String getMessage() {
        if (this.trace == null) {
            return super.getMessage();
        }
        StringBuffer stringBuffer = new StringBuffer(512);
        stringBuffer.append(super.getMessage());
        if (stringBuffer.length() > 0) {
            stringBuffer.append(10);
        }
        stringBuffer.append("Serialization trace:");
        stringBuffer.append(this.trace);
        return stringBuffer.toString();
    }

    public void addTrace(String str) {
        if (str != null) {
            if (this.trace == null) {
                this.trace = new StringBuffer(512);
            }
            this.trace.append(10);
            this.trace.append(str);
            return;
        }
        throw new IllegalArgumentException("info cannot be null.");
    }
}
