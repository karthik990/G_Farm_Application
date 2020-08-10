package p043io.grpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.http.protocol.HTTP;
import p043io.fabric.sdk.android.services.network.HttpRequest;

/* renamed from: io.grpc.Codec */
public interface Codec extends Compressor, Decompressor {

    /* renamed from: io.grpc.Codec$Gzip */
    public static final class Gzip implements Codec {
        public String getMessageEncoding() {
            return HttpRequest.ENCODING_GZIP;
        }

        public OutputStream compress(OutputStream outputStream) throws IOException {
            return new GZIPOutputStream(outputStream);
        }

        public InputStream decompress(InputStream inputStream) throws IOException {
            return new GZIPInputStream(inputStream);
        }
    }

    /* renamed from: io.grpc.Codec$Identity */
    public static final class Identity implements Codec {
        public static final Codec NONE = new Identity();

        public OutputStream compress(OutputStream outputStream) throws IOException {
            return outputStream;
        }

        public InputStream decompress(InputStream inputStream) throws IOException {
            return inputStream;
        }

        public String getMessageEncoding() {
            return HTTP.IDENTITY_CODING;
        }

        private Identity() {
        }
    }
}
