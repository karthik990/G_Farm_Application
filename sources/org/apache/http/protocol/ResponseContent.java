package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.util.Args;

public class ResponseContent implements HttpResponseInterceptor {
    private final boolean overwrite;

    public ResponseContent() {
        this(false);
    }

    public ResponseContent(boolean z) {
        this.overwrite = z;
    }

    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP response");
        String str = "Transfer-Encoding";
        String str2 = "Content-Length";
        if (this.overwrite) {
            httpResponse.removeHeaders(str);
            httpResponse.removeHeaders(str2);
        } else if (httpResponse.containsHeader(str)) {
            throw new ProtocolException("Transfer-encoding header already present");
        } else if (httpResponse.containsHeader(str2)) {
            throw new ProtocolException("Content-Length header already present");
        }
        ProtocolVersion protocolVersion = httpResponse.getStatusLine().getProtocolVersion();
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            long contentLength = entity.getContentLength();
            if (entity.isChunked() && !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                httpResponse.addHeader(str, HTTP.CHUNK_CODING);
            } else if (contentLength >= 0) {
                httpResponse.addHeader(str2, Long.toString(entity.getContentLength()));
            }
            if (entity.getContentType() != null && !httpResponse.containsHeader("Content-Type")) {
                httpResponse.addHeader(entity.getContentType());
            }
            if (entity.getContentEncoding() != null && !httpResponse.containsHeader("Content-Encoding")) {
                httpResponse.addHeader(entity.getContentEncoding());
                return;
            }
            return;
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != 204 && statusCode != 304 && statusCode != 205) {
            httpResponse.addHeader(str2, "0");
        }
    }
}
