package p043io.grpc;

import java.nio.charset.Charset;
import p043io.grpc.Metadata.AsciiMarshaller;
import p043io.grpc.Metadata.Key;

/* renamed from: io.grpc.InternalMetadata */
public final class InternalMetadata {
    public static final Charset US_ASCII = Charset.forName("US-ASCII");

    /* renamed from: io.grpc.InternalMetadata$TrustedAsciiMarshaller */
    public interface TrustedAsciiMarshaller<T> extends TrustedAsciiMarshaller<T> {
    }

    public static <T> Key<T> keyOf(String str, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
        boolean z = false;
        if (str != null && !str.isEmpty() && str.charAt(0) == ':') {
            z = true;
        }
        return Key.m3995of(str, z, (TrustedAsciiMarshaller<T>) trustedAsciiMarshaller);
    }

    public static <T> Key<T> keyOf(String str, AsciiMarshaller<T> asciiMarshaller) {
        boolean z = false;
        if (str != null && !str.isEmpty() && str.charAt(0) == ':') {
            z = true;
        }
        return Key.m3994of(str, z, asciiMarshaller);
    }

    public static Metadata newMetadata(byte[]... bArr) {
        return new Metadata(bArr);
    }

    public static Metadata newMetadata(int i, byte[]... bArr) {
        return new Metadata(i, bArr);
    }

    public static byte[][] serialize(Metadata metadata) {
        return metadata.serialize();
    }

    public static int headerCount(Metadata metadata) {
        return metadata.headerCount();
    }
}
