package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class RawResourceDataSource extends BaseDataSource {
    public static final String RAW_RESOURCE_SCHEME = "rawresource";
    private AssetFileDescriptor assetFileDescriptor;
    private long bytesRemaining;
    private InputStream inputStream;
    private boolean opened;
    private final Resources resources;
    private Uri uri;

    public static class RawResourceDataSourceException extends IOException {
        public RawResourceDataSourceException(String str) {
            super(str);
        }

        public RawResourceDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public static Uri buildRawResourceUri(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("rawresource:///");
        sb.append(i);
        return Uri.parse(sb.toString());
    }

    public RawResourceDataSource(Context context) {
        super(false);
        this.resources = context.getResources();
    }

    public long open(DataSpec dataSpec) throws RawResourceDataSourceException {
        try {
            Uri uri2 = dataSpec.uri;
            this.uri = uri2;
            if (TextUtils.equals(RAW_RESOURCE_SCHEME, uri2.getScheme())) {
                int parseInt = Integer.parseInt((String) Assertions.checkNotNull(uri2.getLastPathSegment()));
                transferInitializing(dataSpec);
                AssetFileDescriptor openRawResourceFd = this.resources.openRawResourceFd(parseInt);
                this.assetFileDescriptor = openRawResourceFd;
                if (openRawResourceFd != null) {
                    FileInputStream fileInputStream = new FileInputStream(openRawResourceFd.getFileDescriptor());
                    this.inputStream = fileInputStream;
                    fileInputStream.skip(openRawResourceFd.getStartOffset());
                    if (fileInputStream.skip(dataSpec.position) >= dataSpec.position) {
                        long j = -1;
                        if (dataSpec.length != -1) {
                            this.bytesRemaining = dataSpec.length;
                        } else {
                            long length = openRawResourceFd.getLength();
                            if (length != -1) {
                                j = length - dataSpec.position;
                            }
                            this.bytesRemaining = j;
                        }
                        this.opened = true;
                        transferStarted(dataSpec);
                        return this.bytesRemaining;
                    }
                    throw new EOFException();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Resource is compressed: ");
                sb.append(uri2);
                throw new RawResourceDataSourceException(sb.toString());
            }
            throw new RawResourceDataSourceException("URI must use scheme rawresource");
        } catch (NumberFormatException unused) {
            throw new RawResourceDataSourceException("Resource identifier must be an integer.");
        } catch (IOException e) {
            throw new RawResourceDataSourceException(e);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws RawResourceDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i2 = (int) Math.min(j, (long) i2);
            } catch (IOException e) {
                throw new RawResourceDataSourceException(e);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.inputStream)).read(bArr, i, i2);
        if (read != -1) {
            long j2 = this.bytesRemaining;
            if (j2 != -1) {
                this.bytesRemaining = j2 - ((long) read);
            }
            bytesTransferred(read);
            return read;
        } else if (this.bytesRemaining == -1) {
            return -1;
        } else {
            throw new RawResourceDataSourceException((IOException) new EOFException());
        }
    }

    public Uri getUri() {
        return this.uri;
    }

    public void close() throws RawResourceDataSourceException {
        this.uri = null;
        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
            } catch (IOException e) {
                throw new RawResourceDataSourceException(e);
            } catch (Throwable th) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th;
            }
        } catch (IOException e2) {
            throw new RawResourceDataSourceException(e2);
        } catch (Throwable th2) {
            this.inputStream = null;
            try {
                if (this.assetFileDescriptor != null) {
                    this.assetFileDescriptor.close();
                }
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th2;
            } catch (IOException e3) {
                throw new RawResourceDataSourceException(e3);
            } catch (Throwable th3) {
                this.assetFileDescriptor = null;
                if (this.opened) {
                    this.opened = false;
                    transferEnded();
                }
                throw th3;
            }
        }
    }
}
