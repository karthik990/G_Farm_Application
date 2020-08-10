package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.util.Args;

public class RequestContent implements HttpRequestInterceptor {
    private final boolean overwrite;

    public RequestContent() {
        this(false);
    }

    public RequestContent(boolean z) {
        this.overwrite = z;
    }

    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            String str = "Transfer-Encoding";
            String str2 = "Content-Length";
            if (this.overwrite) {
                httpRequest.removeHeaders(str);
                httpRequest.removeHeaders(str2);
            } else if (httpRequest.containsHeader(str)) {
                throw new ProtocolException("Transfer-encoding header already present");
            } else if (httpRequest.containsHeader(str2)) {
                throw new ProtocolException("Content-Length header already present");
            }
            ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            if (entity == null) {
                httpRequest.addHeader(str2, "0");
                return;
            }
            if (!entity.isChunked() && entity.getContentLength() >= 0) {
                httpRequest.addHeader(str2, Long.toString(entity.getContentLength()));
            } else if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpRequest.addHeader(str, HTTP.CHUNK_CODING);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Chunked transfer encoding not allowed for ");
                sb.append(protocolVersion);
                throw new ProtocolException(sb.toString());
            }
            if (entity.getContentType() != null && !httpRequest.containsHeader("Content-Type")) {
                httpRequest.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !httpRequest.containsHeader("Content-Encoding")) {
                httpRequest.addHeader(entity.getContentEncoding());
            }
        }
    }
}
