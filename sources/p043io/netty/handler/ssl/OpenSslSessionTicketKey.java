package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.SessionTicketKey;

/* renamed from: io.netty.handler.ssl.OpenSslSessionTicketKey */
public final class OpenSslSessionTicketKey {
    public static final int AES_KEY_SIZE = 16;
    public static final int HMAC_KEY_SIZE = 16;
    public static final int NAME_SIZE = 16;
    public static final int TICKET_KEY_SIZE = 48;
    final SessionTicketKey key;

    public OpenSslSessionTicketKey(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.key = new SessionTicketKey((byte[]) bArr.clone(), (byte[]) bArr2.clone(), (byte[]) bArr3.clone());
    }

    public byte[] name() {
        return (byte[]) this.key.getName().clone();
    }

    public byte[] hmacKey() {
        return (byte[]) this.key.getHmacKey().clone();
    }

    public byte[] aesKey() {
        return (byte[]) this.key.getAesKey().clone();
    }
}
