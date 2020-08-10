package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class ResolvingDataSource implements DataSource {
    private final Resolver resolver;
    private final DataSource upstreamDataSource;
    private boolean upstreamOpened;

    public static final class Factory implements com.google.android.exoplayer2.upstream.DataSource.Factory {
        private final Resolver resolver;
        private final com.google.android.exoplayer2.upstream.DataSource.Factory upstreamFactory;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory, Resolver resolver2) {
            this.upstreamFactory = factory;
            this.resolver = resolver2;
        }

        public ResolvingDataSource createDataSource() {
            return new ResolvingDataSource(this.upstreamFactory.createDataSource(), this.resolver);
        }
    }

    public interface Resolver {

        /* renamed from: com.google.android.exoplayer2.upstream.ResolvingDataSource$Resolver$-CC reason: invalid class name */
        public final /* synthetic */ class CC {
            public static Uri $default$resolveReportedUri(Resolver resolver, Uri uri) {
                return uri;
            }
        }

        DataSpec resolveDataSpec(DataSpec dataSpec) throws IOException;

        Uri resolveReportedUri(Uri uri);
    }

    public ResolvingDataSource(DataSource dataSource, Resolver resolver2) {
        this.upstreamDataSource = dataSource;
        this.resolver = resolver2;
    }

    public void addTransferListener(TransferListener transferListener) {
        this.upstreamDataSource.addTransferListener(transferListener);
    }

    public long open(DataSpec dataSpec) throws IOException {
        DataSpec resolveDataSpec = this.resolver.resolveDataSpec(dataSpec);
        this.upstreamOpened = true;
        return this.upstreamDataSource.open(resolveDataSpec);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.upstreamDataSource.read(bArr, i, i2);
    }

    public Uri getUri() {
        Uri uri = this.upstreamDataSource.getUri();
        if (uri == null) {
            return null;
        }
        return this.resolver.resolveReportedUri(uri);
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.upstreamDataSource.getResponseHeaders();
    }

    public void close() throws IOException {
        if (this.upstreamOpened) {
            this.upstreamOpened = false;
            this.upstreamDataSource.close();
        }
    }
}
