package p043io.netty.handler.codec;

/* renamed from: io.netty.handler.codec.EncoderException */
public class EncoderException extends CodecException {
    private static final long serialVersionUID = -5086121160476476774L;

    public EncoderException() {
    }

    public EncoderException(String str, Throwable th) {
        super(str, th);
    }

    public EncoderException(String str) {
        super(str);
    }

    public EncoderException(Throwable th) {
        super(th);
    }
}
