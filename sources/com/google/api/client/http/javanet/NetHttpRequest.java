package com.google.api.client.http.javanet;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.StreamingContent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class NetHttpRequest extends LowLevelHttpRequest {
    private static final OutputWriter DEFAULT_CONNECTION_WRITER = new DefaultOutputWriter();
    private final HttpURLConnection connection;
    private int writeTimeout = 0;

    static class DefaultOutputWriter implements OutputWriter {
        DefaultOutputWriter() {
        }

        public void write(OutputStream outputStream, StreamingContent streamingContent) throws IOException {
            streamingContent.writeTo(outputStream);
        }
    }

    interface OutputWriter {
        void write(OutputStream outputStream, StreamingContent streamingContent) throws IOException;
    }

    NetHttpRequest(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
        httpURLConnection.setInstanceFollowRedirects(false);
    }

    public void addHeader(String str, String str2) {
        this.connection.addRequestProperty(str, str2);
    }

    public void setTimeout(int i, int i2) {
        this.connection.setReadTimeout(i2);
        this.connection.setConnectTimeout(i);
    }

    public void setWriteTimeout(int i) throws IOException {
        this.writeTimeout = i;
    }

    public LowLevelHttpResponse execute() throws IOException {
        return execute(DEFAULT_CONNECTION_WRITER);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:0|(8:2|(1:4)|5|(1:7)|8|(1:10)|11|(7:20|(1:25)(1:24)|26|27|28|29|30)(3:15|(1:17)(1:18)|19))|46|47|48) */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0097, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0098, code lost:
        r0.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x009b, code lost:
        throw r10;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x008e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.api.client.http.LowLevelHttpResponse execute(com.google.api.client.http.javanet.NetHttpRequest.OutputWriter r10) throws java.io.IOException {
        /*
            r9 = this;
            java.net.HttpURLConnection r0 = r9.connection
            com.google.api.client.util.StreamingContent r1 = r9.getStreamingContent()
            if (r1 == 0) goto L_0x008e
            java.lang.String r1 = r9.getContentType()
            if (r1 == 0) goto L_0x0013
            java.lang.String r2 = "Content-Type"
            r9.addHeader(r2, r1)
        L_0x0013:
            java.lang.String r1 = r9.getContentEncoding()
            if (r1 == 0) goto L_0x001e
            java.lang.String r2 = "Content-Encoding"
            r9.addHeader(r2, r1)
        L_0x001e:
            long r1 = r9.getContentLength()
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0031
            java.lang.String r5 = java.lang.Long.toString(r1)
            java.lang.String r6 = "Content-Length"
            r0.setRequestProperty(r6, r5)
        L_0x0031:
            java.lang.String r5 = r0.getRequestMethod()
            java.lang.String r6 = "POST"
            boolean r6 = r6.equals(r5)
            r7 = 0
            r8 = 1
            if (r6 != 0) goto L_0x0059
            java.lang.String r6 = "PUT"
            boolean r6 = r6.equals(r5)
            if (r6 == 0) goto L_0x0048
            goto L_0x0059
        L_0x0048:
            int r10 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r10 != 0) goto L_0x004e
            r10 = 1
            goto L_0x004f
        L_0x004e:
            r10 = 0
        L_0x004f:
            java.lang.Object[] r1 = new java.lang.Object[r8]
            r1[r7] = r5
            java.lang.String r2 = "%s with non-zero content length is not supported"
            com.google.api.client.util.Preconditions.checkArgument(r10, r2, r1)
            goto L_0x008e
        L_0x0059:
            r0.setDoOutput(r8)
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x006c
            r3 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x006c
            int r2 = (int) r1
            r0.setFixedLengthStreamingMode(r2)
            goto L_0x006f
        L_0x006c:
            r0.setChunkedStreamingMode(r7)
        L_0x006f:
            java.io.OutputStream r1 = r0.getOutputStream()
            r9.writeContentToOutputStream(r10, r1)     // Catch:{ IOException -> 0x007e }
            r1.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x008e
        L_0x007a:
            r10 = move-exception
            throw r10
        L_0x007c:
            r10 = move-exception
            goto L_0x008a
        L_0x007e:
            r10 = move-exception
            boolean r2 = r9.hasResponse(r0)     // Catch:{ all -> 0x007c }
            if (r2 == 0) goto L_0x0089
            r1.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x008e
        L_0x0089:
            throw r10     // Catch:{ all -> 0x007c }
        L_0x008a:
            r1.close()     // Catch:{ IOException -> 0x008d }
        L_0x008d:
            throw r10
        L_0x008e:
            r0.connect()     // Catch:{ all -> 0x0097 }
            com.google.api.client.http.javanet.NetHttpResponse r10 = new com.google.api.client.http.javanet.NetHttpResponse     // Catch:{ all -> 0x0097 }
            r10.<init>(r0)     // Catch:{ all -> 0x0097 }
            return r10
        L_0x0097:
            r10 = move-exception
            r0.disconnect()
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.http.javanet.NetHttpRequest.execute(com.google.api.client.http.javanet.NetHttpRequest$OutputWriter):com.google.api.client.http.LowLevelHttpResponse");
    }

    private boolean hasResponse(HttpURLConnection httpURLConnection) {
        try {
            return httpURLConnection.getResponseCode() > 0;
        } catch (IOException unused) {
            return false;
        }
    }

    private void writeContentToOutputStream(final OutputWriter outputWriter, final OutputStream outputStream) throws IOException {
        if (this.writeTimeout == 0) {
            outputWriter.write(outputStream, getStreamingContent());
            return;
        }
        final StreamingContent streamingContent = getStreamingContent();
        C29041 r1 = new Callable<Boolean>() {
            public Boolean call() throws IOException {
                outputWriter.write(outputStream, streamingContent);
                return Boolean.TRUE;
            }
        };
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        Future submit = newSingleThreadExecutor.submit(new FutureTask(r1), null);
        newSingleThreadExecutor.shutdown();
        try {
            submit.get((long) this.writeTimeout, TimeUnit.MILLISECONDS);
            if (!newSingleThreadExecutor.isTerminated()) {
                newSingleThreadExecutor.shutdown();
            }
        } catch (InterruptedException e) {
            throw new IOException("Socket write interrupted", e);
        } catch (ExecutionException e2) {
            throw new IOException("Exception in socket write", e2);
        } catch (TimeoutException e3) {
            throw new IOException("Socket write timed out", e3);
        }
    }
}
