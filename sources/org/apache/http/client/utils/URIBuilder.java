package org.apache.http.client.utils;

import com.fasterxml.jackson.core.JsonPointer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.TextUtils;

public class URIBuilder {
    private Charset charset;
    private String encodedAuthority;
    private String encodedFragment;
    private String encodedPath;
    private String encodedQuery;
    private String encodedSchemeSpecificPart;
    private String encodedUserInfo;
    private String fragment;
    private String host;
    private List<String> pathSegments;
    private int port;
    private String query;
    private List<NameValuePair> queryParams;
    private String scheme;
    private String userInfo;

    public URIBuilder() {
        this.port = -1;
    }

    public URIBuilder(String str) throws URISyntaxException {
        digestURI(new URI(str));
    }

    public URIBuilder(URI uri) {
        digestURI(uri);
    }

    public URIBuilder setCharset(Charset charset2) {
        this.charset = charset2;
        return this;
    }

    public Charset getCharset() {
        return this.charset;
    }

    private List<NameValuePair> parseQuery(String str, Charset charset2) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return URLEncodedUtils.parse(str, charset2);
    }

    private List<String> parsePath(String str, Charset charset2) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return URLEncodedUtils.parsePathSegments(str, charset2);
    }

    public URI build() throws URISyntaxException {
        return new URI(buildString());
    }

    private String buildString() {
        StringBuilder sb = new StringBuilder();
        String str = this.scheme;
        if (str != null) {
            sb.append(str);
            sb.append(':');
        }
        String str2 = this.encodedSchemeSpecificPart;
        if (str2 != null) {
            sb.append(str2);
        } else {
            String str3 = "//";
            if (this.encodedAuthority != null) {
                sb.append(str3);
                sb.append(this.encodedAuthority);
            } else if (this.host != null) {
                sb.append(str3);
                String str4 = this.encodedUserInfo;
                String str5 = "@";
                if (str4 != null) {
                    sb.append(str4);
                    sb.append(str5);
                } else {
                    String str6 = this.userInfo;
                    if (str6 != null) {
                        sb.append(encodeUserInfo(str6));
                        sb.append(str5);
                    }
                }
                if (InetAddressUtils.isIPv6Address(this.host)) {
                    sb.append("[");
                    sb.append(this.host);
                    sb.append("]");
                } else {
                    sb.append(this.host);
                }
                if (this.port >= 0) {
                    sb.append(":");
                    sb.append(this.port);
                }
            }
            String str7 = this.encodedPath;
            if (str7 != null) {
                sb.append(normalizePath(str7, sb.length() == 0));
            } else {
                List<String> list = this.pathSegments;
                if (list != null) {
                    sb.append(encodePath(list));
                }
            }
            String str8 = "?";
            if (this.encodedQuery != null) {
                sb.append(str8);
                sb.append(this.encodedQuery);
            } else {
                List<NameValuePair> list2 = this.queryParams;
                if (list2 != null && !list2.isEmpty()) {
                    sb.append(str8);
                    sb.append(encodeUrlForm(this.queryParams));
                } else if (this.query != null) {
                    sb.append(str8);
                    sb.append(encodeUric(this.query));
                }
            }
        }
        String str9 = "#";
        if (this.encodedFragment != null) {
            sb.append(str9);
            sb.append(this.encodedFragment);
        } else if (this.fragment != null) {
            sb.append(str9);
            sb.append(encodeUric(this.fragment));
        }
        return sb.toString();
    }

    private static String normalizePath(String str, boolean z) {
        if (TextUtils.isBlank(str)) {
            return "";
        }
        if (!z) {
            String str2 = "/";
            if (!str.startsWith(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(str);
                str = sb.toString();
            }
        }
        return str;
    }

    private void digestURI(URI uri) {
        this.scheme = uri.getScheme();
        this.encodedSchemeSpecificPart = uri.getRawSchemeSpecificPart();
        this.encodedAuthority = uri.getRawAuthority();
        this.host = uri.getHost();
        this.port = uri.getPort();
        this.encodedUserInfo = uri.getRawUserInfo();
        this.userInfo = uri.getUserInfo();
        this.encodedPath = uri.getRawPath();
        String rawPath = uri.getRawPath();
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        this.pathSegments = parsePath(rawPath, charset2);
        this.encodedQuery = uri.getRawQuery();
        String rawQuery = uri.getRawQuery();
        Charset charset3 = this.charset;
        if (charset3 == null) {
            charset3 = Consts.UTF_8;
        }
        this.queryParams = parseQuery(rawQuery, charset3);
        this.encodedFragment = uri.getRawFragment();
        this.fragment = uri.getFragment();
    }

    private String encodeUserInfo(String str) {
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        return URLEncodedUtils.encUserInfo(str, charset2);
    }

    private String encodePath(List<String> list) {
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        return URLEncodedUtils.formatSegments(list, charset2);
    }

    private String encodeUrlForm(List<NameValuePair> list) {
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        return URLEncodedUtils.format((Iterable<? extends NameValuePair>) list, charset2);
    }

    private String encodeUric(String str) {
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        return URLEncodedUtils.encUric(str, charset2);
    }

    public URIBuilder setScheme(String str) {
        this.scheme = str;
        return this;
    }

    public URIBuilder setUserInfo(String str) {
        this.userInfo = str;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        this.encodedUserInfo = null;
        return this;
    }

    public URIBuilder setUserInfo(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(':');
        sb.append(str2);
        return setUserInfo(sb.toString());
    }

    public URIBuilder setHost(String str) {
        this.host = str;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }

    public URIBuilder setPort(int i) {
        if (i < 0) {
            i = -1;
        }
        this.port = i;
        this.encodedSchemeSpecificPart = null;
        this.encodedAuthority = null;
        return this;
    }

    public URIBuilder setPath(String str) {
        return setPathSegments(str != null ? URLEncodedUtils.splitPathSegments(str) : null);
    }

    public URIBuilder setPathSegments(String... strArr) {
        this.pathSegments = strArr.length > 0 ? Arrays.asList(strArr) : null;
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        return this;
    }

    public URIBuilder setPathSegments(List<String> list) {
        this.pathSegments = (list == null || list.size() <= 0) ? null : new ArrayList<>(list);
        this.encodedSchemeSpecificPart = null;
        this.encodedPath = null;
        return this;
    }

    public URIBuilder removeQuery() {
        this.queryParams = null;
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    @Deprecated
    public URIBuilder setQuery(String str) {
        Charset charset2 = this.charset;
        if (charset2 == null) {
            charset2 = Consts.UTF_8;
        }
        this.queryParams = parseQuery(str, charset2);
        this.query = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setParameters(List<NameValuePair> list) {
        List<NameValuePair> list2 = this.queryParams;
        if (list2 == null) {
            this.queryParams = new ArrayList();
        } else {
            list2.clear();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder addParameters(List<NameValuePair> list) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        this.queryParams.addAll(list);
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder setParameters(NameValuePair... nameValuePairArr) {
        List<NameValuePair> list = this.queryParams;
        if (list == null) {
            this.queryParams = new ArrayList();
        } else {
            list.clear();
        }
        for (NameValuePair add : nameValuePairArr) {
            this.queryParams.add(add);
        }
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder addParameter(String str, String str2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        this.queryParams.add(new BasicNameValuePair(str, str2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder setParameter(String str, String str2) {
        if (this.queryParams == null) {
            this.queryParams = new ArrayList();
        }
        if (!this.queryParams.isEmpty()) {
            Iterator it = this.queryParams.iterator();
            while (it.hasNext()) {
                if (((NameValuePair) it.next()).getName().equals(str)) {
                    it.remove();
                }
            }
        }
        this.queryParams.add(new BasicNameValuePair(str, str2));
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.query = null;
        return this;
    }

    public URIBuilder clearParameters() {
        this.queryParams = null;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        return this;
    }

    public URIBuilder setCustomQuery(String str) {
        this.query = str;
        this.encodedQuery = null;
        this.encodedSchemeSpecificPart = null;
        this.queryParams = null;
        return this;
    }

    public URIBuilder setFragment(String str) {
        this.fragment = str;
        this.encodedFragment = null;
        return this;
    }

    public boolean isAbsolute() {
        return this.scheme != null;
    }

    public boolean isOpaque() {
        return isPathEmpty();
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getUserInfo() {
        return this.userInfo;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isPathEmpty() {
        List<String> list = this.pathSegments;
        return (list == null || list.isEmpty()) && this.encodedPath == null;
    }

    public List<String> getPathSegments() {
        List<String> list = this.pathSegments;
        return list != null ? new ArrayList(list) : Collections.emptyList();
    }

    public String getPath() {
        if (this.pathSegments == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : this.pathSegments) {
            sb.append(JsonPointer.SEPARATOR);
            sb.append(str);
        }
        return sb.toString();
    }

    public boolean isQueryEmpty() {
        List<NameValuePair> list = this.queryParams;
        return (list == null || list.isEmpty()) && this.encodedQuery == null;
    }

    public List<NameValuePair> getQueryParams() {
        List<NameValuePair> list = this.queryParams;
        return list != null ? new ArrayList(list) : Collections.emptyList();
    }

    public String getFragment() {
        return this.fragment;
    }

    public String toString() {
        return buildString();
    }
}
