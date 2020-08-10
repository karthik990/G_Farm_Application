package org.apache.http.client.methods;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderGroup;
import org.apache.http.util.Args;

public class RequestBuilder {
    private Charset charset;
    private RequestConfig config;
    private HttpEntity entity;
    private HeaderGroup headerGroup;
    private String method;
    private List<NameValuePair> parameters;
    private URI uri;
    private ProtocolVersion version;

    static class InternalEntityEclosingRequest extends HttpEntityEnclosingRequestBase {
        private final String method;

        InternalEntityEclosingRequest(String str) {
            this.method = str;
        }

        public String getMethod() {
            return this.method;
        }
    }

    static class InternalRequest extends HttpRequestBase {
        private final String method;

        InternalRequest(String str) {
            this.method = str;
        }

        public String getMethod() {
            return this.method;
        }
    }

    RequestBuilder(String str) {
        this.charset = Consts.UTF_8;
        this.method = str;
    }

    RequestBuilder(String str, URI uri2) {
        this.method = str;
        this.uri = uri2;
    }

    RequestBuilder(String str, String str2) {
        this.method = str;
        this.uri = str2 != null ? URI.create(str2) : null;
    }

    RequestBuilder() {
        this(null);
    }

    public static RequestBuilder create(String str) {
        Args.notBlank(str, "HTTP method");
        return new RequestBuilder(str);
    }

    public static RequestBuilder get() {
        return new RequestBuilder("GET");
    }

    public static RequestBuilder get(URI uri2) {
        return new RequestBuilder("GET", uri2);
    }

    public static RequestBuilder get(String str) {
        return new RequestBuilder("GET", str);
    }

    public static RequestBuilder head() {
        return new RequestBuilder("HEAD");
    }

    public static RequestBuilder head(URI uri2) {
        return new RequestBuilder("HEAD", uri2);
    }

    public static RequestBuilder head(String str) {
        return new RequestBuilder("HEAD", str);
    }

    public static RequestBuilder patch() {
        return new RequestBuilder("PATCH");
    }

    public static RequestBuilder patch(URI uri2) {
        return new RequestBuilder("PATCH", uri2);
    }

    public static RequestBuilder patch(String str) {
        return new RequestBuilder("PATCH", str);
    }

    public static RequestBuilder post() {
        return new RequestBuilder("POST");
    }

    public static RequestBuilder post(URI uri2) {
        return new RequestBuilder("POST", uri2);
    }

    public static RequestBuilder post(String str) {
        return new RequestBuilder("POST", str);
    }

    public static RequestBuilder put() {
        return new RequestBuilder("PUT");
    }

    public static RequestBuilder put(URI uri2) {
        return new RequestBuilder("PUT", uri2);
    }

    public static RequestBuilder put(String str) {
        return new RequestBuilder("PUT", str);
    }

    public static RequestBuilder delete() {
        return new RequestBuilder("DELETE");
    }

    public static RequestBuilder delete(URI uri2) {
        return new RequestBuilder("DELETE", uri2);
    }

    public static RequestBuilder delete(String str) {
        return new RequestBuilder("DELETE", str);
    }

    public static RequestBuilder trace() {
        return new RequestBuilder("TRACE");
    }

    public static RequestBuilder trace(URI uri2) {
        return new RequestBuilder("TRACE", uri2);
    }

    public static RequestBuilder trace(String str) {
        return new RequestBuilder("TRACE", str);
    }

    public static RequestBuilder options() {
        return new RequestBuilder("OPTIONS");
    }

    public static RequestBuilder options(URI uri2) {
        return new RequestBuilder("OPTIONS", uri2);
    }

    public static RequestBuilder options(String str) {
        return new RequestBuilder("OPTIONS", str);
    }

    public static RequestBuilder copy(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return new RequestBuilder().doCopy(httpRequest);
    }

    private RequestBuilder doCopy(HttpRequest httpRequest) {
        if (httpRequest == null) {
            return this;
        }
        this.method = httpRequest.getRequestLine().getMethod();
        this.version = httpRequest.getRequestLine().getProtocolVersion();
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.clear();
        this.headerGroup.setHeaders(httpRequest.getAllHeaders());
        this.parameters = null;
        this.entity = null;
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity2 = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            ContentType contentType = ContentType.get(entity2);
            if (contentType == null || !contentType.getMimeType().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                this.entity = entity2;
            } else {
                try {
                    List<NameValuePair> parse = URLEncodedUtils.parse(entity2);
                    if (!parse.isEmpty()) {
                        this.parameters = parse;
                    }
                } catch (IOException unused) {
                }
            }
        }
        if (httpRequest instanceof HttpUriRequest) {
            this.uri = ((HttpUriRequest) httpRequest).getURI();
        } else {
            this.uri = URI.create(httpRequest.getRequestLine().getUri());
        }
        if (httpRequest instanceof Configurable) {
            this.config = ((Configurable) httpRequest).getConfig();
        } else {
            this.config = null;
        }
        return this;
    }

    public RequestBuilder setCharset(Charset charset2) {
        this.charset = charset2;
        return this;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getMethod() {
        return this.method;
    }

    public ProtocolVersion getVersion() {
        return this.version;
    }

    public RequestBuilder setVersion(ProtocolVersion protocolVersion) {
        this.version = protocolVersion;
        return this;
    }

    public URI getUri() {
        return this.uri;
    }

    public RequestBuilder setUri(URI uri2) {
        this.uri = uri2;
        return this;
    }

    public RequestBuilder setUri(String str) {
        this.uri = str != null ? URI.create(str) : null;
        return this;
    }

    public Header getFirstHeader(String str) {
        HeaderGroup headerGroup2 = this.headerGroup;
        if (headerGroup2 != null) {
            return headerGroup2.getFirstHeader(str);
        }
        return null;
    }

    public Header getLastHeader(String str) {
        HeaderGroup headerGroup2 = this.headerGroup;
        if (headerGroup2 != null) {
            return headerGroup2.getLastHeader(str);
        }
        return null;
    }

    public Header[] getHeaders(String str) {
        HeaderGroup headerGroup2 = this.headerGroup;
        if (headerGroup2 != null) {
            return headerGroup2.getHeaders(str);
        }
        return null;
    }

    public RequestBuilder addHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.addHeader(header);
        return this;
    }

    public RequestBuilder addHeader(String str, String str2) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.addHeader(new BasicHeader(str, str2));
        return this;
    }

    public RequestBuilder removeHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.removeHeader(header);
        return this;
    }

    public RequestBuilder removeHeaders(String str) {
        if (str != null) {
            HeaderGroup headerGroup2 = this.headerGroup;
            if (headerGroup2 != null) {
                HeaderIterator it = headerGroup2.iterator();
                while (it.hasNext()) {
                    if (str.equalsIgnoreCase(it.nextHeader().getName())) {
                        it.remove();
                    }
                }
            }
        }
        return this;
    }

    public RequestBuilder setHeader(Header header) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.updateHeader(header);
        return this;
    }

    public RequestBuilder setHeader(String str, String str2) {
        if (this.headerGroup == null) {
            this.headerGroup = new HeaderGroup();
        }
        this.headerGroup.updateHeader(new BasicHeader(str, str2));
        return this;
    }

    public HttpEntity getEntity() {
        return this.entity;
    }

    public RequestBuilder setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
        return this;
    }

    public List<NameValuePair> getParameters() {
        ArrayList arrayList;
        List<NameValuePair> list = this.parameters;
        if (list != null) {
            arrayList = new ArrayList(list);
        } else {
            arrayList = new ArrayList();
        }
        return arrayList;
    }

    public RequestBuilder addParameter(NameValuePair nameValuePair) {
        Args.notNull(nameValuePair, "Name value pair");
        if (this.parameters == null) {
            this.parameters = new LinkedList();
        }
        this.parameters.add(nameValuePair);
        return this;
    }

    public RequestBuilder addParameter(String str, String str2) {
        return addParameter(new BasicNameValuePair(str, str2));
    }

    public RequestBuilder addParameters(NameValuePair... nameValuePairArr) {
        for (NameValuePair addParameter : nameValuePairArr) {
            addParameter(addParameter);
        }
        return this;
    }

    public RequestConfig getConfig() {
        return this.config;
    }

    public RequestBuilder setConfig(RequestConfig requestConfig) {
        this.config = requestConfig;
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002b, code lost:
        if ("PUT".equalsIgnoreCase(r4.method) != false) goto L_0x002d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.HttpUriRequest build() {
        /*
            r4 = this;
            java.net.URI r0 = r4.uri
            if (r0 == 0) goto L_0x0005
            goto L_0x000b
        L_0x0005:
            java.lang.String r0 = "/"
            java.net.URI r0 = java.net.URI.create(r0)
        L_0x000b:
            org.apache.http.HttpEntity r1 = r4.entity
            java.util.List<org.apache.http.NameValuePair> r2 = r4.parameters
            if (r2 == 0) goto L_0x0053
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0053
            if (r1 != 0) goto L_0x003c
            java.lang.String r2 = r4.method
            java.lang.String r3 = "POST"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 != 0) goto L_0x002d
            java.lang.String r2 = r4.method
            java.lang.String r3 = "PUT"
            boolean r2 = r3.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x003c
        L_0x002d:
            org.apache.http.client.entity.UrlEncodedFormEntity r1 = new org.apache.http.client.entity.UrlEncodedFormEntity
            java.util.List<org.apache.http.NameValuePair> r2 = r4.parameters
            java.nio.charset.Charset r3 = r4.charset
            if (r3 == 0) goto L_0x0036
            goto L_0x0038
        L_0x0036:
            java.nio.charset.Charset r3 = org.apache.http.protocol.HTTP.DEF_CONTENT_CHARSET
        L_0x0038:
            r1.<init>(r2, r3)
            goto L_0x0053
        L_0x003c:
            org.apache.http.client.utils.URIBuilder r2 = new org.apache.http.client.utils.URIBuilder     // Catch:{ URISyntaxException -> 0x0052 }
            r2.<init>(r0)     // Catch:{ URISyntaxException -> 0x0052 }
            java.nio.charset.Charset r3 = r4.charset     // Catch:{ URISyntaxException -> 0x0052 }
            org.apache.http.client.utils.URIBuilder r2 = r2.setCharset(r3)     // Catch:{ URISyntaxException -> 0x0052 }
            java.util.List<org.apache.http.NameValuePair> r3 = r4.parameters     // Catch:{ URISyntaxException -> 0x0052 }
            org.apache.http.client.utils.URIBuilder r2 = r2.addParameters(r3)     // Catch:{ URISyntaxException -> 0x0052 }
            java.net.URI r0 = r2.build()     // Catch:{ URISyntaxException -> 0x0052 }
            goto L_0x0053
        L_0x0052:
        L_0x0053:
            if (r1 != 0) goto L_0x005d
            org.apache.http.client.methods.RequestBuilder$InternalRequest r1 = new org.apache.http.client.methods.RequestBuilder$InternalRequest
            java.lang.String r2 = r4.method
            r1.<init>(r2)
            goto L_0x0068
        L_0x005d:
            org.apache.http.client.methods.RequestBuilder$InternalEntityEclosingRequest r2 = new org.apache.http.client.methods.RequestBuilder$InternalEntityEclosingRequest
            java.lang.String r3 = r4.method
            r2.<init>(r3)
            r2.setEntity(r1)
            r1 = r2
        L_0x0068:
            org.apache.http.ProtocolVersion r2 = r4.version
            r1.setProtocolVersion(r2)
            r1.setURI(r0)
            org.apache.http.message.HeaderGroup r0 = r4.headerGroup
            if (r0 == 0) goto L_0x007b
            org.apache.http.Header[] r0 = r0.getAllHeaders()
            r1.setHeaders(r0)
        L_0x007b:
            org.apache.http.client.config.RequestConfig r0 = r4.config
            r1.setConfig(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.client.methods.RequestBuilder.build():org.apache.http.client.methods.HttpUriRequest");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RequestBuilder [method=");
        sb.append(this.method);
        sb.append(", charset=");
        sb.append(this.charset);
        sb.append(", version=");
        sb.append(this.version);
        sb.append(", uri=");
        sb.append(this.uri);
        sb.append(", headerGroup=");
        sb.append(this.headerGroup);
        sb.append(", entity=");
        sb.append(this.entity);
        sb.append(", parameters=");
        sb.append(this.parameters);
        sb.append(", config=");
        sb.append(this.config);
        sb.append("]");
        return sb.toString();
    }
}
