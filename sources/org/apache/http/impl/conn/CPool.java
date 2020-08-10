package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.AbstractConnPool;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.PoolEntryCallback;

class CPool extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
    private static final AtomicLong COUNTER = new AtomicLong();
    private final Log log = LogFactory.getLog(CPool.class);
    private final long timeToLive;
    private final TimeUnit timeUnit;

    public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int i, int i2, long j, TimeUnit timeUnit2) {
        super(connFactory, i, i2);
        this.timeToLive = j;
        this.timeUnit = timeUnit2;
    }

    /* access modifiers changed from: protected */
    public CPoolEntry createEntry(HttpRoute httpRoute, ManagedHttpClientConnection managedHttpClientConnection) {
        CPoolEntry cPoolEntry = new CPoolEntry(this.log, Long.toString(COUNTER.getAndIncrement()), httpRoute, managedHttpClientConnection, this.timeToLive, this.timeUnit);
        return cPoolEntry;
    }

    /* access modifiers changed from: protected */
    public boolean validate(CPoolEntry cPoolEntry) {
        return !((ManagedHttpClientConnection) cPoolEntry.getConnection()).isStale();
    }

    /* access modifiers changed from: protected */
    public void enumAvailable(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        super.enumAvailable(poolEntryCallback);
    }

    /* access modifiers changed from: protected */
    public void enumLeased(PoolEntryCallback<HttpRoute, ManagedHttpClientConnection> poolEntryCallback) {
        super.enumLeased(poolEntryCallback);
    }
}
