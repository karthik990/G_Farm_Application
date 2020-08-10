package com.google.api.client.http;

import com.google.api.client.util.StreamingContent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import p043io.fabric.sdk.android.services.network.HttpRequest;

public class GZipEncoding implements HttpEncoding {
    public String getName() {
        return HttpRequest.ENCODING_GZIP;
    }

    public void encode(StreamingContent streamingContent, OutputStream outputStream) throws IOException {
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(new BufferedOutputStream(outputStream) {
            public void close() throws IOException {
                try {
                    flush();
                } catch (IOException unused) {
                }
            }
        });
        streamingContent.writeTo(gZIPOutputStream);
        gZIPOutputStream.close();
    }
}
