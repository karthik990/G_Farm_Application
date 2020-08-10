package org.apache.http.impl.pool;

import java.io.IOException;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.pool.PoolEntry;

public class BasicPoolEntry extends PoolEntry<HttpHost, HttpClientConnection> {
    public BasicPoolEntry(String str, HttpHost httpHost, HttpClientConnection httpClientConnection) {
        super(str, httpHost, httpClientConnection);
    }

    public void close() {
        try {
            ((HttpClientConnection) getConnection()).close();
        } catch (IOException unused) {
        }
    }

    public boolean isClosed() {
        return !((HttpClientConnection) getConnection()).isOpen();
    }
}
