package p043io.netty.handler.codec.compression;

import p043io.netty.handler.codec.EncoderException;

/* renamed from: io.netty.handler.codec.compression.CompressionException */
public class CompressionException extends EncoderException {
    private static final long serialVersionUID = 5603413481274811897L;

    public CompressionException() {
    }

    public CompressionException(String str, Throwable th) {
        super(str, th);
    }

    public CompressionException(String str) {
        super(str);
    }

    public CompressionException(Throwable th) {
        super(th);
    }
}
