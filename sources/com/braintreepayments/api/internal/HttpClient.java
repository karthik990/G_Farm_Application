package com.braintreepayments.api.internal;

import android.os.Handler;
import android.os.Looper;
import com.braintreepayments.api.exceptions.AuthenticationException;
import com.braintreepayments.api.exceptions.AuthorizationException;
import com.braintreepayments.api.exceptions.DownForMaintenanceException;
import com.braintreepayments.api.exceptions.RateLimitException;
import com.braintreepayments.api.exceptions.ServerException;
import com.braintreepayments.api.exceptions.UnexpectedException;
import com.braintreepayments.api.exceptions.UnprocessableEntityException;
import com.braintreepayments.api.exceptions.UpgradeRequiredException;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpHost;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public class HttpClient<T extends HttpClient> {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String UTF_8 = "UTF-8";
    protected String mBaseUrl;
    private int mConnectTimeout = ((int) TimeUnit.SECONDS.toMillis(30));
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private int mReadTimeout = ((int) TimeUnit.SECONDS.toMillis(30));
    private SSLSocketFactory mSSLSocketFactory;
    protected final ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private String mUserAgent = "braintree/core/2.21.0";

    public HttpClient() {
        try {
            this.mSSLSocketFactory = new TLSSocketFactory();
        } catch (SSLException unused) {
            this.mSSLSocketFactory = null;
        }
    }

    public T setUserAgent(String str) {
        this.mUserAgent = str;
        return this;
    }

    public T setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.mSSLSocketFactory = sSLSocketFactory;
        return this;
    }

    public T setBaseUrl(String str) {
        if (str == null) {
            str = "";
        }
        this.mBaseUrl = str;
        return this;
    }

    public T setConnectTimeout(int i) {
        this.mConnectTimeout = i;
        return this;
    }

    public T setReadTimeout(int i) {
        this.mReadTimeout = i;
        return this;
    }

    public void get(final String str, final HttpResponseCallback httpResponseCallback) {
        if (str == null) {
            postCallbackOnMainThread(httpResponseCallback, (Exception) new IllegalArgumentException("Path cannot be null"));
            return;
        }
        if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mBaseUrl);
            sb.append(str);
            str = sb.toString();
        }
        this.mThreadPool.submit(new Runnable() {
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    httpURLConnection = HttpClient.this.init(str);
                    httpURLConnection.setRequestMethod("GET");
                    HttpClient.this.postCallbackOnMainThread(httpResponseCallback, HttpClient.this.parseResponse(httpURLConnection));
                    if (httpURLConnection == null) {
                        return;
                    }
                } catch (Exception e) {
                    HttpClient.this.postCallbackOnMainThread(httpResponseCallback, e);
                    if (httpURLConnection == null) {
                        return;
                    }
                } catch (Throwable th) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    throw th;
                }
                httpURLConnection.disconnect();
            }
        });
    }

    public void post(final String str, final String str2, final HttpResponseCallback httpResponseCallback) {
        if (str == null) {
            postCallbackOnMainThread(httpResponseCallback, (Exception) new IllegalArgumentException("Path cannot be null"));
        } else {
            this.mThreadPool.submit(new Runnable() {
                public void run() {
                    try {
                        HttpClient.this.postCallbackOnMainThread(httpResponseCallback, HttpClient.this.post(str, str2));
                    } catch (Exception e) {
                        HttpClient.this.postCallbackOnMainThread(httpResponseCallback, e);
                    }
                }
            });
        }
    }

    /* JADX INFO: finally extract failed */
    public String post(String str, String str2) throws Exception {
        HttpURLConnection init;
        HttpURLConnection httpURLConnection = null;
        try {
            if (str.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                init = init(str);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(this.mBaseUrl);
                sb.append(str);
                init = init(sb.toString());
            }
            HttpURLConnection httpURLConnection2 = init;
            httpURLConnection2.setRequestProperty("Content-Type", "application/json");
            httpURLConnection2.setRequestMethod("POST");
            httpURLConnection2.setDoOutput(true);
            writeOutputStream(httpURLConnection2.getOutputStream(), str2);
            String parseResponse = parseResponse(httpURLConnection2);
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            return parseResponse;
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection init(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        if (httpURLConnection instanceof HttpsURLConnection) {
            SSLSocketFactory sSLSocketFactory = this.mSSLSocketFactory;
            if (sSLSocketFactory != null) {
                ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sSLSocketFactory);
            } else {
                throw new SSLException("SSLSocketFactory was not set or failed to initialize");
            }
        }
        httpURLConnection.setRequestProperty("User-Agent", this.mUserAgent);
        httpURLConnection.setRequestProperty("Accept-Language", Locale.getDefault().getLanguage());
        httpURLConnection.setRequestProperty("Accept-Encoding", HttpRequest.ENCODING_GZIP);
        httpURLConnection.setConnectTimeout(this.mConnectTimeout);
        httpURLConnection.setReadTimeout(this.mReadTimeout);
        return httpURLConnection;
    }

    /* access modifiers changed from: protected */
    public void writeOutputStream(OutputStream outputStream, String str) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
        outputStreamWriter.write(str, 0, str.length());
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    /* access modifiers changed from: protected */
    public String parseResponse(HttpURLConnection httpURLConnection) throws Exception {
        int responseCode = httpURLConnection.getResponseCode();
        boolean equals = HttpRequest.ENCODING_GZIP.equals(httpURLConnection.getContentEncoding());
        if (responseCode != 400) {
            if (responseCode == 401) {
                throw new AuthenticationException(readStream(httpURLConnection.getErrorStream(), equals));
            } else if (responseCode == 403) {
                throw new AuthorizationException(readStream(httpURLConnection.getErrorStream(), equals));
            } else if (responseCode != 422) {
                if (responseCode == 426) {
                    throw new UpgradeRequiredException(readStream(httpURLConnection.getErrorStream(), equals));
                } else if (responseCode == 429) {
                    throw new RateLimitException("You are being rate-limited. Please try again in a few minutes.");
                } else if (responseCode == 500) {
                    throw new ServerException(readStream(httpURLConnection.getErrorStream(), equals));
                } else if (responseCode != 503) {
                    switch (responseCode) {
                        case 200:
                        case 201:
                        case 202:
                            return readStream(httpURLConnection.getInputStream(), equals);
                        default:
                            throw new UnexpectedException(readStream(httpURLConnection.getErrorStream(), equals));
                    }
                } else {
                    throw new DownForMaintenanceException(readStream(httpURLConnection.getErrorStream(), equals));
                }
            }
        }
        throw new UnprocessableEntityException(readStream(httpURLConnection.getErrorStream(), equals));
    }

    /* access modifiers changed from: 0000 */
    public void postCallbackOnMainThread(final HttpResponseCallback httpResponseCallback, final String str) {
        if (httpResponseCallback != null) {
            this.mMainThreadHandler.post(new Runnable() {
                public void run() {
                    httpResponseCallback.success(str);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void postCallbackOnMainThread(final HttpResponseCallback httpResponseCallback, final Exception exc) {
        if (httpResponseCallback != null) {
            this.mMainThreadHandler.post(new Runnable() {
                public void run() {
                    httpResponseCallback.failure(exc);
                }
            });
        }
    }

    private String readStream(InputStream inputStream, boolean z) throws IOException {
        if (inputStream == null) {
            return null;
        }
        if (z) {
            try {
                inputStream = new GZIPInputStream(inputStream);
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
                throw th;
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
        String str = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
        try {
            inputStream.close();
        } catch (IOException unused2) {
        }
        return str;
    }
}
