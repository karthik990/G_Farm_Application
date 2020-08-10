package org.apache.http.p104io;

import org.apache.http.HttpMessage;

/* renamed from: org.apache.http.io.HttpMessageWriterFactory */
public interface HttpMessageWriterFactory<T extends HttpMessage> {
    HttpMessageWriter<T> create(SessionOutputBuffer sessionOutputBuffer);
}
