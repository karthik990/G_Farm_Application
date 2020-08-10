package org.apache.http.client.utils;

import com.fasterxml.jackson.core.JsonPointer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

public class URIUtils {
    public static final EnumSet<UriFlag> DROP_FRAGMENT = EnumSet.of(UriFlag.DROP_FRAGMENT);
    public static final EnumSet<UriFlag> DROP_FRAGMENT_AND_NORMALIZE = EnumSet.of(UriFlag.DROP_FRAGMENT, UriFlag.NORMALIZE);
    public static final EnumSet<UriFlag> NORMALIZE = EnumSet.of(UriFlag.NORMALIZE);
    public static final EnumSet<UriFlag> NO_FLAGS = EnumSet.noneOf(UriFlag.class);

    public enum UriFlag {
        DROP_FRAGMENT,
        NORMALIZE
    }

    @Deprecated
    public static URI createURI(String str, String str2, int i, String str3, String str4, String str5) throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            if (str != null) {
                sb.append(str);
                sb.append("://");
            }
            sb.append(str2);
            if (i > 0) {
                sb.append(':');
                sb.append(i);
            }
        }
        if (str3 == null || !str3.startsWith("/")) {
            sb.append(JsonPointer.SEPARATOR);
        }
        if (str3 != null) {
            sb.append(str3);
        }
        if (str4 != null) {
            sb.append('?');
            sb.append(str4);
        }
        if (str5 != null) {
            sb.append('#');
            sb.append(str5);
        }
        return new URI(sb.toString());
    }

    @Deprecated
    public static URI rewriteURI(URI uri, HttpHost httpHost, boolean z) throws URISyntaxException {
        return rewriteURI(uri, httpHost, z ? DROP_FRAGMENT : NO_FLAGS);
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost, EnumSet<UriFlag> enumSet) throws URISyntaxException {
        Args.notNull(uri, "URI");
        Args.notNull(enumSet, "URI flags");
        if (uri.isOpaque()) {
            return uri;
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        if (httpHost != null) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        } else {
            uRIBuilder.setScheme(null);
            uRIBuilder.setHost(null);
            uRIBuilder.setPort(-1);
        }
        if (enumSet.contains(UriFlag.DROP_FRAGMENT)) {
            uRIBuilder.setFragment(null);
        }
        if (enumSet.contains(UriFlag.NORMALIZE)) {
            ArrayList arrayList = new ArrayList(uRIBuilder.getPathSegments());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).isEmpty() && it.hasNext()) {
                    it.remove();
                }
            }
            uRIBuilder.setPathSegments((List<String>) arrayList);
        }
        if (uRIBuilder.isPathEmpty()) {
            uRIBuilder.setPathSegments("");
        }
        return uRIBuilder.build();
    }

    public static URI rewriteURI(URI uri, HttpHost httpHost) throws URISyntaxException {
        return rewriteURI(uri, httpHost, NORMALIZE);
    }

    public static URI rewriteURI(URI uri) throws URISyntaxException {
        Args.notNull(uri, "URI");
        if (uri.isOpaque()) {
            return uri;
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        if (uRIBuilder.getUserInfo() != null) {
            uRIBuilder.setUserInfo(null);
        }
        if (uRIBuilder.getPathSegments().isEmpty()) {
            uRIBuilder.setPathSegments("");
        }
        if (TextUtils.isEmpty(uRIBuilder.getPath())) {
            uRIBuilder.setPath("/");
        }
        if (uRIBuilder.getHost() != null) {
            uRIBuilder.setHost(uRIBuilder.getHost().toLowerCase(Locale.ROOT));
        }
        uRIBuilder.setFragment(null);
        return uRIBuilder.build();
    }

    public static URI rewriteURIForRoute(URI uri, RouteInfo routeInfo) throws URISyntaxException {
        return rewriteURIForRoute(uri, routeInfo, true);
    }

    public static URI rewriteURIForRoute(URI uri, RouteInfo routeInfo, boolean z) throws URISyntaxException {
        URI uri2;
        URI uri3;
        if (uri == null) {
            return null;
        }
        if (routeInfo.getProxyHost() == null || routeInfo.isTunnelled()) {
            if (uri.isAbsolute()) {
                uri2 = rewriteURI(uri, (HttpHost) null, z ? DROP_FRAGMENT_AND_NORMALIZE : DROP_FRAGMENT);
            } else {
                uri2 = rewriteURI(uri);
            }
            return uri2;
        }
        if (uri.isAbsolute()) {
            uri3 = rewriteURI(uri);
        } else {
            uri3 = rewriteURI(uri, routeInfo.getTargetHost(), z ? DROP_FRAGMENT_AND_NORMALIZE : DROP_FRAGMENT);
        }
        return uri3;
    }

    public static URI resolve(URI uri, String str) {
        return resolve(uri, URI.create(str));
    }

    public static URI resolve(URI uri, URI uri2) {
        URI uri3;
        Args.notNull(uri, "Base URI");
        Args.notNull(uri2, "Reference URI");
        String aSCIIString = uri2.toASCIIString();
        if (aSCIIString.startsWith("?")) {
            String aSCIIString2 = uri.toASCIIString();
            int indexOf = aSCIIString2.indexOf(63);
            if (indexOf > -1) {
                aSCIIString2 = aSCIIString2.substring(0, indexOf);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(aSCIIString2);
            sb.append(aSCIIString);
            return URI.create(sb.toString());
        }
        if (aSCIIString.isEmpty()) {
            String aSCIIString3 = uri.resolve(URI.create("#")).toASCIIString();
            uri3 = URI.create(aSCIIString3.substring(0, aSCIIString3.indexOf(35)));
        } else {
            uri3 = uri.resolve(uri2);
        }
        try {
            return normalizeSyntax(uri3);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static URI normalizeSyntax(URI uri) throws URISyntaxException {
        if (uri.isOpaque() || uri.getAuthority() == null) {
            return uri;
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        List<String> pathSegments = uRIBuilder.getPathSegments();
        Stack stack = new Stack();
        for (String str : pathSegments) {
            if (!".".equals(str)) {
                if (!"..".equals(str)) {
                    stack.push(str);
                } else if (!stack.isEmpty()) {
                    stack.pop();
                }
            }
        }
        if (stack.size() == 0) {
            stack.add("");
        }
        uRIBuilder.setPathSegments((List<String>) stack);
        if (uRIBuilder.getScheme() != null) {
            uRIBuilder.setScheme(uRIBuilder.getScheme().toLowerCase(Locale.ROOT));
        }
        if (uRIBuilder.getHost() != null) {
            uRIBuilder.setHost(uRIBuilder.getHost().toLowerCase(Locale.ROOT));
        }
        return uRIBuilder.build();
    }

    public static HttpHost extractHost(URI uri) {
        HttpHost httpHost = null;
        if (uri == null) {
            return null;
        }
        if (uri.isAbsolute()) {
            int port = uri.getPort();
            String host = uri.getHost();
            if (host == null) {
                host = uri.getAuthority();
                if (host != null) {
                    int indexOf = host.indexOf(64);
                    if (indexOf >= 0) {
                        int i = indexOf + 1;
                        host = host.length() > i ? host.substring(i) : null;
                    }
                    if (host != null) {
                        int indexOf2 = host.indexOf(58);
                        if (indexOf2 >= 0) {
                            int i2 = indexOf2 + 1;
                            int i3 = i2;
                            int i4 = 0;
                            while (i3 < host.length() && Character.isDigit(host.charAt(i3))) {
                                i4++;
                                i3++;
                            }
                            if (i4 > 0) {
                                try {
                                    port = Integer.parseInt(host.substring(i2, i4 + i2));
                                } catch (NumberFormatException unused) {
                                }
                            }
                            host = host.substring(0, indexOf2);
                        }
                    }
                }
            }
            String scheme = uri.getScheme();
            if (!TextUtils.isBlank(host)) {
                try {
                    httpHost = new HttpHost(host, port, scheme);
                } catch (IllegalArgumentException unused2) {
                }
            }
        }
        return httpHost;
    }

    public static URI resolve(URI uri, HttpHost httpHost, List<URI> list) throws URISyntaxException {
        URIBuilder uRIBuilder;
        Args.notNull(uri, "Request URI");
        if (list == null || list.isEmpty()) {
            uRIBuilder = new URIBuilder(uri);
        } else {
            uRIBuilder = new URIBuilder((URI) list.get(list.size() - 1));
            String fragment = uRIBuilder.getFragment();
            int size = list.size() - 1;
            while (fragment == null && size >= 0) {
                fragment = ((URI) list.get(size)).getFragment();
                size--;
            }
            uRIBuilder.setFragment(fragment);
        }
        if (uRIBuilder.getFragment() == null) {
            uRIBuilder.setFragment(uri.getFragment());
        }
        if (httpHost != null && !uRIBuilder.isAbsolute()) {
            uRIBuilder.setScheme(httpHost.getSchemeName());
            uRIBuilder.setHost(httpHost.getHostName());
            uRIBuilder.setPort(httpHost.getPort());
        }
        return uRIBuilder.build();
    }

    private URIUtils() {
    }
}
