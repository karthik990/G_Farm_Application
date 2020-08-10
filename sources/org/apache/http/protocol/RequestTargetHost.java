package org.apache.http.protocol;

import com.google.api.client.http.HttpMethods;
import java.io.IOException;
import java.net.InetAddress;
import org.apache.http.HttpConnection;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpInetConnection;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.util.Args;

public class RequestTargetHost implements HttpRequestInterceptor {
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        HttpCoreContext adapt = HttpCoreContext.adapt(httpContext);
        ProtocolVersion protocolVersion = httpRequest.getRequestLine().getProtocolVersion();
        if (!httpRequest.getRequestLine().getMethod().equalsIgnoreCase(HttpMethods.CONNECT) || !protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
            String str = "Host";
            if (!httpRequest.containsHeader(str)) {
                HttpHost targetHost = adapt.getTargetHost();
                if (targetHost == null) {
                    HttpConnection connection = adapt.getConnection();
                    if (connection instanceof HttpInetConnection) {
                        HttpInetConnection httpInetConnection = (HttpInetConnection) connection;
                        InetAddress remoteAddress = httpInetConnection.getRemoteAddress();
                        int remotePort = httpInetConnection.getRemotePort();
                        if (remoteAddress != null) {
                            targetHost = new HttpHost(remoteAddress.getHostName(), remotePort);
                        }
                    }
                    if (targetHost == null) {
                        if (!protocolVersion.lessEquals(HttpVersion.HTTP_1_0)) {
                            throw new ProtocolException("Target host missing");
                        }
                        return;
                    }
                }
                httpRequest.addHeader(str, targetHost.toHostString());
            }
        }
    }
}
