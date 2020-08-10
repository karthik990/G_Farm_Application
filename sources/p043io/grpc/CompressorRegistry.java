package p043io.grpc;

import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;
import p043io.grpc.Codec.Gzip;
import p043io.grpc.Codec.Identity;

/* renamed from: io.grpc.CompressorRegistry */
public final class CompressorRegistry {
    private static final CompressorRegistry DEFAULT_INSTANCE = new CompressorRegistry(new Gzip(), Identity.NONE);
    private final ConcurrentMap<String, Compressor> compressors = new ConcurrentHashMap();

    public static CompressorRegistry getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static CompressorRegistry newEmptyInstance() {
        return new CompressorRegistry(new Compressor[0]);
    }

    CompressorRegistry(Compressor... compressorArr) {
        for (Compressor compressor : compressorArr) {
            this.compressors.put(compressor.getMessageEncoding(), compressor);
        }
    }

    @Nullable
    public Compressor lookupCompressor(String str) {
        return (Compressor) this.compressors.get(str);
    }

    public void register(Compressor compressor) {
        String messageEncoding = compressor.getMessageEncoding();
        Preconditions.checkArgument(!messageEncoding.contains(","), "Comma is currently not allowed in message encoding");
        this.compressors.put(messageEncoding, compressor);
    }
}
