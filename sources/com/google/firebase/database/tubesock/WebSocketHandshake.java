package com.google.firebase.database.tubesock;

import android.util.Base64;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class WebSocketHandshake {
    private static final String WEBSOCKET_VERSION = "13";
    private Map<String, String> extraHeaders = null;
    private String nonce = null;
    private String protocol = null;
    private URI url = null;

    public WebSocketHandshake(URI uri, String str, Map<String, String> map) {
        this.url = uri;
        this.protocol = str;
        this.extraHeaders = map;
        this.nonce = createNonce();
    }

    public byte[] getHandshake() {
        String str;
        String path = this.url.getPath();
        String query = this.url.getQuery();
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        if (query == null) {
            str = "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("?");
            sb2.append(query);
            str = sb2.toString();
        }
        sb.append(str);
        String sb3 = sb.toString();
        String host = this.url.getHost();
        if (this.url.getPort() != -1) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(host);
            sb4.append(":");
            sb4.append(this.url.getPort());
            host = sb4.toString();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("Host", host);
        String str2 = "Upgrade";
        linkedHashMap.put(str2, "websocket");
        linkedHashMap.put("Connection", str2);
        linkedHashMap.put("Sec-WebSocket-Version", WEBSOCKET_VERSION);
        linkedHashMap.put("Sec-WebSocket-Key", this.nonce);
        String str3 = this.protocol;
        if (str3 != null) {
            linkedHashMap.put("Sec-WebSocket-Protocol", str3);
        }
        Map<String, String> map = this.extraHeaders;
        if (map != null) {
            for (String str4 : map.keySet()) {
                if (!linkedHashMap.containsKey(str4)) {
                    linkedHashMap.put(str4, (String) this.extraHeaders.get(str4));
                }
            }
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("GET ");
        sb5.append(sb3);
        sb5.append(" HTTP/1.1\r\n");
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append(generateHeader(linkedHashMap));
        String sb8 = sb7.toString();
        StringBuilder sb9 = new StringBuilder();
        sb9.append(sb8);
        sb9.append("\r\n");
        byte[] bytes = sb9.toString().getBytes(Charset.defaultCharset());
        byte[] bArr = new byte[bytes.length];
        System.arraycopy(bytes, 0, bArr, 0, bytes.length);
        return bArr;
    }

    private String generateHeader(LinkedHashMap<String, String> linkedHashMap) {
        String str = new String();
        for (String str2 : linkedHashMap.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2);
            sb.append(": ");
            sb.append((String) linkedHashMap.get(str2));
            sb.append("\r\n");
            str = sb.toString();
        }
        return str;
    }

    private String createNonce() {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 16; i++) {
            bArr[i] = (byte) rand(0, 255);
        }
        return Base64.encodeToString(bArr, 2);
    }

    public void verifyServerStatusLine(String str) {
        int parseInt = Integer.parseInt(str.substring(9, 12));
        if (parseInt == 407) {
            throw new WebSocketException("connection failed: proxy authentication not supported");
        } else if (parseInt == 404) {
            throw new WebSocketException("connection failed: 404 not found");
        } else if (parseInt != 101) {
            StringBuilder sb = new StringBuilder();
            sb.append("connection failed: unknown status code ");
            sb.append(parseInt);
            throw new WebSocketException(sb.toString());
        }
    }

    public void verifyServerHandshakeHeaders(HashMap<String, String> hashMap) {
        String str = "upgrade";
        if (!"websocket".equals(hashMap.get(str))) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Upgrade");
        } else if (!str.equals(hashMap.get("connection"))) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Connection");
        }
    }

    private int rand(int i, int i2) {
        double random = Math.random();
        double d = (double) i2;
        Double.isNaN(d);
        double d2 = random * d;
        double d3 = (double) i;
        Double.isNaN(d3);
        return (int) (d2 + d3);
    }
}
