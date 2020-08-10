package p043io.netty.handler.codec;

/* renamed from: io.netty.handler.codec.CorruptedFrameException */
public class CorruptedFrameException extends DecoderException {
    private static final long serialVersionUID = 3918052232492988408L;

    public CorruptedFrameException() {
    }

    public CorruptedFrameException(String str, Throwable th) {
        super(str, th);
    }

    public CorruptedFrameException(String str) {
        super(str);
    }

    public CorruptedFrameException(Throwable th) {
        super(th);
    }
}
