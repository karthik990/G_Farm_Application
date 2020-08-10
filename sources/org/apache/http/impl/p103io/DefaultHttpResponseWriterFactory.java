package org.apache.http.impl.p103io;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicLineFormatter;
import org.apache.http.message.LineFormatter;
import org.apache.http.p104io.HttpMessageWriter;
import org.apache.http.p104io.HttpMessageWriterFactory;
import org.apache.http.p104io.SessionOutputBuffer;

/* renamed from: org.apache.http.impl.io.DefaultHttpResponseWriterFactory */
public class DefaultHttpResponseWriterFactory implements HttpMessageWriterFactory<HttpResponse> {
    public static final DefaultHttpResponseWriterFactory INSTANCE = new DefaultHttpResponseWriterFactory();
    private final LineFormatter lineFormatter;

    public DefaultHttpResponseWriterFactory(LineFormatter lineFormatter2) {
        if (lineFormatter2 == null) {
            lineFormatter2 = BasicLineFormatter.INSTANCE;
        }
        this.lineFormatter = lineFormatter2;
    }

    public DefaultHttpResponseWriterFactory() {
        this(null);
    }

    public HttpMessageWriter<HttpResponse> create(SessionOutputBuffer sessionOutputBuffer) {
        return new DefaultHttpResponseWriter(sessionOutputBuffer, this.lineFormatter);
    }
}
