package p043io.netty.handler.codec.compression;

import p043io.netty.handler.codec.DecoderException;

/* renamed from: io.netty.handler.codec.compression.DecompressionException */
public class DecompressionException extends DecoderException {
    private static final long serialVersionUID = 3546272712208105199L;

    public DecompressionException() {
    }

    public DecompressionException(String str, Throwable th) {
        super(str, th);
    }

    public DecompressionException(String str) {
        super(str);
    }

    public DecompressionException(Throwable th) {
        super(th);
    }
}
