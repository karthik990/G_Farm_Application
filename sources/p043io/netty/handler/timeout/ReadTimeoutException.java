package p043io.netty.handler.timeout;

/* renamed from: io.netty.handler.timeout.ReadTimeoutException */
public final class ReadTimeoutException extends TimeoutException {
    public static final ReadTimeoutException INSTANCE = new ReadTimeoutException();
    private static final long serialVersionUID = 169287984113283421L;

    private ReadTimeoutException() {
    }
}
