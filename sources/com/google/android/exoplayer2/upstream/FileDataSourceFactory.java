package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.upstream.DataSource.Factory;

@Deprecated
public final class FileDataSourceFactory implements Factory {
    private final FileDataSource.Factory wrappedFactory;

    public FileDataSourceFactory() {
        this(null);
    }

    public FileDataSourceFactory(TransferListener transferListener) {
        this.wrappedFactory = new FileDataSource.Factory().setListener(transferListener);
    }

    public FileDataSource createDataSource() {
        return this.wrappedFactory.createDataSource();
    }
}
