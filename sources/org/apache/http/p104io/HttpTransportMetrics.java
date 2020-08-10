package org.apache.http.p104io;

/* renamed from: org.apache.http.io.HttpTransportMetrics */
public interface HttpTransportMetrics {
    long getBytesTransferred();

    void reset();
}
