package com.google.android.exoplayer2.ext.okhttp;

import android.net.Uri;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource.HttpDataSourceException;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidContentTypeException;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.upstream.HttpDataSource.RequestProperties;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Predicate;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.CacheControl;
import okhttp3.Call.Factory;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.protocol.HTTP;

public class OkHttpDataSource extends BaseDataSource implements HttpDataSource {
    private static final byte[] SKIP_BUFFER = new byte[4096];
    private long bytesRead;
    private long bytesSkipped;
    private long bytesToRead;
    private long bytesToSkip;
    private final CacheControl cacheControl;
    private final Factory callFactory;
    private Predicate<String> contentTypePredicate;
    private DataSpec dataSpec;
    private final RequestProperties defaultRequestProperties;
    private boolean opened;
    private final RequestProperties requestProperties;
    private Response response;
    private InputStream responseByteStream;
    private final String userAgent;

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.okhttp");
    }

    public OkHttpDataSource(Factory factory, String str) {
        this(factory, str, null, null);
    }

    public OkHttpDataSource(Factory factory, String str, CacheControl cacheControl2, RequestProperties requestProperties2) {
        super(true);
        this.callFactory = (Factory) Assertions.checkNotNull(factory);
        this.userAgent = str;
        this.cacheControl = cacheControl2;
        this.defaultRequestProperties = requestProperties2;
        this.requestProperties = new RequestProperties();
    }

    @Deprecated
    public OkHttpDataSource(Factory factory, String str, Predicate<String> predicate) {
        this(factory, str, predicate, null, null);
    }

    @Deprecated
    public OkHttpDataSource(Factory factory, String str, Predicate<String> predicate, CacheControl cacheControl2, RequestProperties requestProperties2) {
        super(true);
        this.callFactory = (Factory) Assertions.checkNotNull(factory);
        this.userAgent = str;
        this.contentTypePredicate = predicate;
        this.cacheControl = cacheControl2;
        this.defaultRequestProperties = requestProperties2;
        this.requestProperties = new RequestProperties();
    }

    public void setContentTypePredicate(Predicate<String> predicate) {
        this.contentTypePredicate = predicate;
    }

    public Uri getUri() {
        Response response2 = this.response;
        if (response2 == null) {
            return null;
        }
        return Uri.parse(response2.request().url().toString());
    }

    public int getResponseCode() {
        Response response2 = this.response;
        if (response2 == null) {
            return -1;
        }
        return response2.code();
    }

    public Map<String, List<String>> getResponseHeaders() {
        Response response2 = this.response;
        return response2 == null ? Collections.emptyMap() : response2.headers().toMultimap();
    }

    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.requestProperties.set(str, str2);
    }

    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.requestProperties.remove(str);
    }

    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    public long open(DataSpec dataSpec2) throws HttpDataSourceException {
        this.dataSpec = dataSpec2;
        long j = 0;
        this.bytesRead = 0;
        this.bytesSkipped = 0;
        transferInitializing(dataSpec2);
        try {
            this.response = this.callFactory.newCall(makeRequest(dataSpec2)).execute();
            Response response2 = this.response;
            ResponseBody responseBody = (ResponseBody) Assertions.checkNotNull(response2.body());
            this.responseByteStream = responseBody.byteStream();
            int code = response2.code();
            if (!response2.isSuccessful()) {
                Map multimap = response2.headers().toMultimap();
                closeConnectionQuietly();
                InvalidResponseCodeException invalidResponseCodeException = new InvalidResponseCodeException(code, response2.message(), multimap, dataSpec2);
                if (code == 416) {
                    invalidResponseCodeException.initCause(new DataSourceException(0));
                }
                throw invalidResponseCodeException;
            }
            MediaType contentType = responseBody.contentType();
            String mediaType = contentType != null ? contentType.toString() : "";
            Predicate<String> predicate = this.contentTypePredicate;
            if (predicate == null || predicate.evaluate(mediaType)) {
                if (code == 200 && dataSpec2.position != 0) {
                    j = dataSpec2.position;
                }
                this.bytesToSkip = j;
                if (dataSpec2.length != -1) {
                    this.bytesToRead = dataSpec2.length;
                } else {
                    long contentLength = responseBody.contentLength();
                    this.bytesToRead = contentLength != -1 ? contentLength - this.bytesToSkip : -1;
                }
                this.opened = true;
                transferStarted(dataSpec2);
                return this.bytesToRead;
            }
            closeConnectionQuietly();
            throw new InvalidContentTypeException(mediaType, dataSpec2);
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to connect to ");
            sb.append(dataSpec2.uri);
            throw new HttpDataSourceException(sb.toString(), e, dataSpec2, 1);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws HttpDataSourceException {
        try {
            skipInternal();
            return readInternal(bArr, i, i2);
        } catch (IOException e) {
            throw new HttpDataSourceException(e, (DataSpec) Assertions.checkNotNull(this.dataSpec), 2);
        }
    }

    public void close() throws HttpDataSourceException {
        if (this.opened) {
            this.opened = false;
            transferEnded();
            closeConnectionQuietly();
        }
    }

    /* access modifiers changed from: protected */
    public final long bytesSkipped() {
        return this.bytesSkipped;
    }

    /* access modifiers changed from: protected */
    public final long bytesRead() {
        return this.bytesRead;
    }

    /* access modifiers changed from: protected */
    public final long bytesRemaining() {
        long j = this.bytesToRead;
        return j == -1 ? j : j - this.bytesRead;
    }

    private Request makeRequest(DataSpec dataSpec2) throws HttpDataSourceException {
        long j = dataSpec2.position;
        long j2 = dataSpec2.length;
        HttpUrl parse = HttpUrl.parse(dataSpec2.uri.toString());
        if (parse != null) {
            Builder url = new Builder().url(parse);
            CacheControl cacheControl2 = this.cacheControl;
            if (cacheControl2 != null) {
                url.cacheControl(cacheControl2);
            }
            HashMap hashMap = new HashMap();
            RequestProperties requestProperties2 = this.defaultRequestProperties;
            if (requestProperties2 != null) {
                hashMap.putAll(requestProperties2.getSnapshot());
            }
            hashMap.putAll(this.requestProperties.getSnapshot());
            hashMap.putAll(dataSpec2.httpRequestHeaders);
            for (Entry entry : hashMap.entrySet()) {
                url.header((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(j == 0 && j2 == -1)) {
                StringBuilder sb = new StringBuilder();
                sb.append("bytes=");
                sb.append(j);
                sb.append("-");
                String sb2 = sb.toString();
                if (j2 != -1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(sb2);
                    sb3.append((j + j2) - 1);
                    sb2 = sb3.toString();
                }
                url.addHeader("Range", sb2);
            }
            String str = this.userAgent;
            if (str != null) {
                url.addHeader("User-Agent", str);
            }
            if (!dataSpec2.isFlagSet(1)) {
                url.addHeader("Accept-Encoding", HTTP.IDENTITY_CODING);
            }
            RequestBody requestBody = null;
            if (dataSpec2.httpBody != null) {
                requestBody = RequestBody.create((MediaType) null, dataSpec2.httpBody);
            } else if (dataSpec2.httpMethod == 2) {
                requestBody = RequestBody.create((MediaType) null, Util.EMPTY_BYTE_ARRAY);
            }
            url.method(dataSpec2.getHttpMethodString(), requestBody);
            return url.build();
        }
        throw new HttpDataSourceException("Malformed URL", dataSpec2, 1);
    }

    private void skipInternal() throws IOException {
        if (this.bytesSkipped != this.bytesToSkip) {
            while (true) {
                long j = this.bytesSkipped;
                long j2 = this.bytesToSkip;
                if (j != j2) {
                    int read = ((InputStream) Util.castNonNull(this.responseByteStream)).read(SKIP_BUFFER, 0, (int) Math.min(j2 - j, (long) SKIP_BUFFER.length));
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedIOException();
                    } else if (read != -1) {
                        this.bytesSkipped += (long) read;
                        bytesTransferred(read);
                    } else {
                        throw new EOFException();
                    }
                } else {
                    return;
                }
            }
        }
    }

    private int readInternal(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.bytesToRead;
        if (j != -1) {
            long j2 = j - this.bytesRead;
            if (j2 == 0) {
                return -1;
            }
            i2 = (int) Math.min((long) i2, j2);
        }
        int read = ((InputStream) Util.castNonNull(this.responseByteStream)).read(bArr, i, i2);
        if (read != -1) {
            this.bytesRead += (long) read;
            bytesTransferred(read);
            return read;
        } else if (this.bytesToRead == -1) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    private void closeConnectionQuietly() {
        Response response2 = this.response;
        if (response2 != null) {
            ((ResponseBody) Assertions.checkNotNull(response2.body())).close();
            this.response = null;
        }
        this.responseByteStream = null;
    }
}
