package p043io.netty.channel;

import java.net.ConnectException;

/* renamed from: io.netty.channel.ConnectTimeoutException */
public class ConnectTimeoutException extends ConnectException {
    private static final long serialVersionUID = 2317065249988317463L;

    public ConnectTimeoutException(String str) {
        super(str);
    }

    public ConnectTimeoutException() {
    }
}
