package org.apache.http.impl.execchain;

import java.io.IOException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.params.HttpParams;

class HttpResponseProxy implements CloseableHttpResponse {
    private final ConnectionHolder connHolder;
    private final HttpResponse original;

    public HttpResponseProxy(HttpResponse httpResponse, ConnectionHolder connectionHolder) {
        this.original = httpResponse;
        this.connHolder = connectionHolder;
        ResponseEntityProxy.enchance(httpResponse, connectionHolder);
    }

    public void close() throws IOException {
        ConnectionHolder connectionHolder = this.connHolder;
        if (connectionHolder != null) {
            connectionHolder.close();
        }
    }

    public StatusLine getStatusLine() {
        return this.original.getStatusLine();
    }

    public void setStatusLine(StatusLine statusLine) {
        this.original.setStatusLine(statusLine);
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i) {
        this.original.setStatusLine(protocolVersion, i);
    }

    public void setStatusLine(ProtocolVersion protocolVersion, int i, String str) {
        this.original.setStatusLine(protocolVersion, i, str);
    }

    public void setStatusCode(int i) throws IllegalStateException {
        this.original.setStatusCode(i);
    }

    public void setReasonPhrase(String str) throws IllegalStateException {
        this.original.setReasonPhrase(str);
    }

    public HttpEntity getEntity() {
        return this.original.getEntity();
    }

    public void setEntity(HttpEntity httpEntity) {
        this.original.setEntity(httpEntity);
    }

    public Locale getLocale() {
        return this.original.getLocale();
    }

    public void setLocale(Locale locale) {
        this.original.setLocale(locale);
    }

    public ProtocolVersion getProtocolVersion() {
        return this.original.getProtocolVersion();
    }

    public boolean containsHeader(String str) {
        return this.original.containsHeader(str);
    }

    public Header[] getHeaders(String str) {
        return this.original.getHeaders(str);
    }

    public Header getFirstHeader(String str) {
        return this.original.getFirstHeader(str);
    }

    public Header getLastHeader(String str) {
        return this.original.getLastHeader(str);
    }

    public Header[] getAllHeaders() {
        return this.original.getAllHeaders();
    }

    public void addHeader(Header header) {
        this.original.addHeader(header);
    }

    public void addHeader(String str, String str2) {
        this.original.addHeader(str, str2);
    }

    public void setHeader(Header header) {
        this.original.setHeader(header);
    }

    public void setHeader(String str, String str2) {
        this.original.setHeader(str, str2);
    }

    public void setHeaders(Header[] headerArr) {
        this.original.setHeaders(headerArr);
    }

    public void removeHeader(Header header) {
        this.original.removeHeader(header);
    }

    public void removeHeaders(String str) {
        this.original.removeHeaders(str);
    }

    public HeaderIterator headerIterator() {
        return this.original.headerIterator();
    }

    public HeaderIterator headerIterator(String str) {
        return this.original.headerIterator(str);
    }

    public HttpParams getParams() {
        return this.original.getParams();
    }

    public void setParams(HttpParams httpParams) {
        this.original.setParams(httpParams);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("HttpResponseProxy{");
        sb.append(this.original);
        sb.append('}');
        return sb.toString();
    }
}
