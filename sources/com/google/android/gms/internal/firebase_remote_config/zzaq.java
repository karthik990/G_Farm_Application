package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

final class zzaq extends zzai {
    private final HttpURLConnection zzct;

    zzaq(HttpURLConnection httpURLConnection) {
        this.zzct = httpURLConnection;
        httpURLConnection.setInstanceFollowRedirects(false);
    }

    public final void addHeader(String str, String str2) {
        this.zzct.addRequestProperty(str, str2);
    }

    public final void zza(int i, int i2) {
        this.zzct.setReadTimeout(i2);
        this.zzct.setConnectTimeout(i);
    }

    public final zzaj zzai() throws IOException {
        HttpURLConnection httpURLConnection = this.zzct;
        if (zzah() != null) {
            String contentType = getContentType();
            if (contentType != null) {
                addHeader("Content-Type", contentType);
            }
            String contentEncoding = getContentEncoding();
            if (contentEncoding != null) {
                addHeader("Content-Encoding", contentEncoding);
            }
            long contentLength = getContentLength();
            if (contentLength >= 0) {
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));
            }
            String requestMethod = httpURLConnection.getRequestMethod();
            if ("POST".equals(requestMethod) || "PUT".equals(requestMethod)) {
                httpURLConnection.setDoOutput(true);
                if (contentLength < 0 || contentLength > 2147483647L) {
                    httpURLConnection.setChunkedStreamingMode(0);
                } else {
                    httpURLConnection.setFixedLengthStreamingMode((int) contentLength);
                }
                OutputStream outputStream = httpURLConnection.getOutputStream();
                try {
                    zzah().writeTo(outputStream);
                    try {
                    } catch (IOException e) {
                        throw e;
                    }
                } finally {
                    try {
                        outputStream.close();
                    } catch (IOException unused) {
                    }
                }
            } else {
                Object[] objArr = {requestMethod};
                if (!(contentLength == 0)) {
                    throw new IllegalArgumentException(zzdz.zza("%s with non-zero content length is not supported", objArr));
                }
            }
        }
        try {
            httpURLConnection.connect();
            return new zzar(httpURLConnection);
        } catch (Throwable th) {
            httpURLConnection.disconnect();
            throw th;
        }
    }
}
