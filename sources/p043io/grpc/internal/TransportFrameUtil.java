package p043io.grpc.internal;

import com.google.common.base.Charsets;
import com.google.common.p049io.BaseEncoding;
import java.util.Arrays;
import java.util.logging.Logger;
import p043io.grpc.InternalMetadata;
import p043io.grpc.Metadata;

/* renamed from: io.grpc.internal.TransportFrameUtil */
public final class TransportFrameUtil {
    private static final byte[] binaryHeaderSuffixBytes = Metadata.BINARY_HEADER_SUFFIX.getBytes(Charsets.US_ASCII);
    private static final Logger logger = Logger.getLogger(TransportFrameUtil.class.getName());

    public static byte[][] toHttp2Headers(Metadata metadata) {
        byte[][] serialize = InternalMetadata.serialize(metadata);
        if (serialize == null) {
            return new byte[0][];
        }
        int i = 0;
        for (int i2 = 0; i2 < serialize.length; i2 += 2) {
            byte[] bArr = serialize[i2];
            byte[] bArr2 = serialize[i2 + 1];
            if (endsWith(bArr, binaryHeaderSuffixBytes)) {
                serialize[i] = bArr;
                serialize[i + 1] = BaseEncoding.base64().encode(bArr2).getBytes(Charsets.US_ASCII);
            } else if (isSpecCompliantAscii(bArr2)) {
                serialize[i] = bArr;
                serialize[i + 1] = bArr2;
            } else {
                String str = new String(bArr, Charsets.US_ASCII);
                Logger logger2 = logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Metadata key=");
                sb.append(str);
                sb.append(", value=");
                sb.append(Arrays.toString(bArr2));
                sb.append(" contains invalid ASCII characters");
                logger2.warning(sb.toString());
            }
            i += 2;
        }
        if (i == serialize.length) {
            return serialize;
        }
        return (byte[][]) Arrays.copyOfRange(serialize, 0, i);
    }

    public static byte[][] toRawSerializedHeaders(byte[][] bArr) {
        for (int i = 0; i < bArr.length; i += 2) {
            byte[] bArr2 = bArr[i];
            int i2 = i + 1;
            byte[] bArr3 = bArr[i2];
            bArr[i] = bArr2;
            if (endsWith(bArr2, binaryHeaderSuffixBytes)) {
                bArr[i2] = BaseEncoding.base64().decode(new String(bArr3, Charsets.US_ASCII));
            }
        }
        return bArr;
    }

    private static boolean endsWith(byte[] bArr, byte[] bArr2) {
        int length = bArr.length - bArr2.length;
        if (length < 0) {
            return false;
        }
        for (int i = length; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i - length]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSpecCompliantAscii(byte[] bArr) {
        for (byte b : bArr) {
            if (b < 32 || b > 126) {
                return false;
            }
        }
        return true;
    }

    private TransportFrameUtil() {
    }
}
