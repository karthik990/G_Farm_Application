package com.google.firebase.firestore;

import com.google.common.base.Preconditions;
import com.google.firebase.firestore.util.Util;
import com.google.protobuf.ByteString;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Blob implements Comparable<Blob> {
    private final ByteString bytes;

    private Blob(ByteString byteString) {
        this.bytes = byteString;
    }

    public static Blob fromBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr, "Provided bytes array must not be null.");
        return new Blob(ByteString.copyFrom(bArr));
    }

    public static Blob fromByteString(ByteString byteString) {
        Preconditions.checkNotNull(byteString, "Provided ByteString must not be null.");
        return new Blob(byteString);
    }

    public byte[] toBytes() {
        return this.bytes.toByteArray();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Blob { bytes=");
        sb.append(Util.toDebugString(this.bytes));
        sb.append(" }");
        return sb.toString();
    }

    public ByteString toByteString() {
        return this.bytes;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Blob) && this.bytes.equals(((Blob) obj).bytes);
    }

    public int hashCode() {
        return this.bytes.hashCode();
    }

    public int compareTo(Blob blob) {
        int min = Math.min(this.bytes.size(), blob.bytes.size());
        for (int i = 0; i < min; i++) {
            byte byteAt = this.bytes.byteAt(i) & 255;
            byte byteAt2 = blob.bytes.byteAt(i) & 255;
            if (byteAt < byteAt2) {
                return -1;
            }
            if (byteAt > byteAt2) {
                return 1;
            }
        }
        return Util.compareIntegers(this.bytes.size(), blob.bytes.size());
    }
}
