package org.apache.http.impl.p103io;

import org.apache.http.HttpRequest;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;
import org.apache.http.p104io.HttpMessageWriter;
import org.apache.http.p104io.HttpMessageWriterFactory;
import org.apache.http.p104io.SessionOutputBuffer;

/* renamed from: org.apache.http.impl.io.DefaultHttpRequestWriterFactory */
public class DefaultHttpRequestWriterFactory implements HttpMessageWriterFactory<HttpRequest> {
    public static final DefaultHttpRequestWriterFactory INSTANCE = new DefaultHttpRequestWriterFactory();
    private final LineFormatter lineFormatter;

    public DefaultHttpRequestWriterFactory(LineFormatter lineFormatter2) {
        if (lineFormatter2 == null) {
            lineFormatter2 = BasicLineFormatter.INSTANCE;
        }
        this.lineFormatter = lineFormatter2;
    }

    public DefaultHttpRequestWriterFactory() {
        this(null);
    }

    public HttpMessageWriter<HttpRequest> create(SessionOutputBuffer sessionOutputBuffer) {
        return new DefaultHttpRequestWriter(sessionOutputBuffer, this.lineFormatter);
    }
}
