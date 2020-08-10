package p043io.netty.handler.timeout;

/* renamed from: io.netty.handler.timeout.WriteTimeoutException */
public final class WriteTimeoutException extends TimeoutException {
    public static final WriteTimeoutException INSTANCE = new WriteTimeoutException();
    private static final long serialVersionUID = -144786655770296065L;

    private WriteTimeoutException() {
    }
}
