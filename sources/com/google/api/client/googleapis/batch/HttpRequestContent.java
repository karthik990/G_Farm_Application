package com.google.api.client.googleapis.batch;

import com.google.api.client.http.AbstractHttpContent;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

class HttpRequestContent extends AbstractHttpContent {
    private static final String HTTP_VERSION = "HTTP/1.1";
    static final String NEWLINE = "\r\n";
    private final HttpRequest request;

    HttpRequestContent(HttpRequest httpRequest) {
        super("application/http");
        this.request = httpRequest;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, getCharset());
        outputStreamWriter.write(this.request.getRequestMethod());
        String str = " ";
        outputStreamWriter.write(str);
        outputStreamWriter.write(this.request.getUrl().build());
        outputStreamWriter.write(str);
        outputStreamWriter.write(HTTP_VERSION);
        String str2 = NEWLINE;
        outputStreamWriter.write(str2);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.fromHttpHeaders(this.request.getHeaders());
        httpHeaders.setAcceptEncoding(null).setUserAgent(null).setContentEncoding(null).setContentType(null).setContentLength(null);
        HttpContent content = this.request.getContent();
        if (content != null) {
            httpHeaders.setContentType(content.getType());
            long length = content.getLength();
            if (length != -1) {
                httpHeaders.setContentLength(Long.valueOf(length));
            }
        }
        HttpHeaders.serializeHeadersForMultipartRequests(httpHeaders, null, null, outputStreamWriter);
        outputStreamWriter.write(str2);
        outputStreamWriter.flush();
        if (content != null) {
            content.writeTo(outputStream);
        }
    }
}
