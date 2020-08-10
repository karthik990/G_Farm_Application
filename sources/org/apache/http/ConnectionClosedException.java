package org.apache.http;

import java.io.IOException;

public class ConnectionClosedException extends IOException {
    private static final long serialVersionUID = 617550366255636674L;

    public ConnectionClosedException() {
        super("Connection is closed");
    }

    public ConnectionClosedException(String str) {
        super(HttpException.clean(str));
    }

    public ConnectionClosedException(String str, Object... objArr) {
        super(HttpException.clean(String.format(str, objArr)));
    }
}
