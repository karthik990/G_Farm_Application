package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource.EventListener;

public final class CacheDataSourceFactory implements Factory {
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final Factory cacheReadDataSourceFactory;
    private final DataSink.Factory cacheWriteDataSinkFactory;
    private final EventListener eventListener;
    private final int flags;
    private final Factory upstreamFactory;

    public CacheDataSourceFactory(Cache cache2, Factory factory) {
        this(cache2, factory, 0);
    }

    public CacheDataSourceFactory(Cache cache2, Factory factory, int i) {
        this(cache2, factory, new FileDataSource.Factory(), new CacheDataSinkFactory(cache2, CacheDataSink.DEFAULT_FRAGMENT_SIZE), i, null);
    }

    public CacheDataSourceFactory(Cache cache2, Factory factory, Factory factory2, DataSink.Factory factory3, int i, EventListener eventListener2) {
        this(cache2, factory, factory2, factory3, i, eventListener2, null);
    }

    public CacheDataSourceFactory(Cache cache2, Factory factory, Factory factory2, DataSink.Factory factory3, int i, EventListener eventListener2, CacheKeyFactory cacheKeyFactory2) {
        this.cache = cache2;
        this.upstreamFactory = factory;
        this.cacheReadDataSourceFactory = factory2;
        this.cacheWriteDataSinkFactory = factory3;
        this.flags = i;
        this.eventListener = eventListener2;
        this.cacheKeyFactory = cacheKeyFactory2;
    }

    public CacheDataSource createDataSource() {
        DataSink dataSink;
        Cache cache2 = this.cache;
        DataSource createDataSource = this.upstreamFactory.createDataSource();
        DataSource createDataSource2 = this.cacheReadDataSourceFactory.createDataSource();
        DataSink.Factory factory = this.cacheWriteDataSinkFactory;
        if (factory == null) {
            dataSink = null;
        } else {
            dataSink = factory.createDataSink();
        }
        CacheDataSource cacheDataSource = new CacheDataSource(cache2, createDataSource, createDataSource2, dataSink, this.flags, this.eventListener, this.cacheKeyFactory);
        return cacheDataSource;
    }
}
