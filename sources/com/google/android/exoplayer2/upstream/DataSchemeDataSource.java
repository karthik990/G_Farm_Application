package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.net.URLDecoder;

public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private byte[] data;
    private DataSpec dataSpec;
    private int endPosition;
    private int readPosition;

    public DataSchemeDataSource() {
        super(false);
    }

    public long open(DataSpec dataSpec2) throws IOException {
        transferInitializing(dataSpec2);
        this.dataSpec = dataSpec2;
        this.readPosition = (int) dataSpec2.position;
        Uri uri = dataSpec2.uri;
        String scheme = uri.getScheme();
        if ("data".equals(scheme)) {
            String[] split = Util.split(uri.getSchemeSpecificPart(), ",");
            if (split.length == 2) {
                String str = split[1];
                if (split[0].contains(";base64")) {
                    try {
                        this.data = Base64.decode(str, 0);
                    } catch (IllegalArgumentException e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Error while parsing Base64 encoded string: ");
                        sb.append(str);
                        throw new ParserException(sb.toString(), e);
                    }
                } else {
                    this.data = Util.getUtf8Bytes(URLDecoder.decode(str, "US-ASCII"));
                }
                this.endPosition = dataSpec2.length != -1 ? ((int) dataSpec2.length) + this.readPosition : this.data.length;
                int i = this.endPosition;
                if (i > this.data.length || this.readPosition > i) {
                    this.data = null;
                    throw new DataSourceException(0);
                }
                transferStarted(dataSpec2);
                return ((long) this.endPosition) - ((long) this.readPosition);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unexpected URI format: ");
            sb2.append(uri);
            throw new ParserException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Unsupported scheme: ");
        sb3.append(scheme);
        throw new ParserException(sb3.toString());
    }

    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.endPosition - this.readPosition;
        if (i3 == 0) {
            return -1;
        }
        int min = Math.min(i2, i3);
        System.arraycopy(Util.castNonNull(this.data), this.readPosition, bArr, i, min);
        this.readPosition += min;
        bytesTransferred(min);
        return min;
    }

    public Uri getUri() {
        DataSpec dataSpec2 = this.dataSpec;
        if (dataSpec2 != null) {
            return dataSpec2.uri;
        }
        return null;
    }

    public void close() {
        if (this.data != null) {
            this.data = null;
            transferEnded();
        }
        this.dataSpec = null;
    }
}
