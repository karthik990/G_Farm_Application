package p043io.grpc.protobuf.lite;

import com.google.common.base.Preconditions;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import p043io.grpc.Metadata.BinaryMarshaller;
import p043io.grpc.MethodDescriptor.Marshaller;
import p043io.grpc.MethodDescriptor.PrototypeMarshaller;

/* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils */
public final class ProtoLiteUtils {
    private static final int BUF_SIZE = 8192;
    static final int DEFAULT_MAX_MESSAGE_SIZE = 4194304;
    static volatile ExtensionRegistryLite globalRegistry = ExtensionRegistryLite.getEmptyRegistry();

    /* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils$MessageMarshaller */
    private static final class MessageMarshaller<T extends MessageLite> implements PrototypeMarshaller<T> {
        private static final ThreadLocal<Reference<byte[]>> bufs = new ThreadLocal<>();
        private final T defaultInstance;
        private final Parser<T> parser;

        MessageMarshaller(T t) {
            this.defaultInstance = t;
            this.parser = t.getParserForType();
        }

        public Class<T> getMessageClass() {
            return this.defaultInstance.getClass();
        }

        public T getMessagePrototype() {
            return this.defaultInstance;
        }

        public InputStream stream(T t) {
            return new ProtoInputStream(t, this.parser);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
            if (r0.length >= r1) goto L_0x0047;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public T parse(java.io.InputStream r6) {
            /*
                r5 = this;
                boolean r0 = r6 instanceof p043io.grpc.protobuf.lite.ProtoInputStream
                if (r0 == 0) goto L_0x0017
                r0 = r6
                io.grpc.protobuf.lite.ProtoInputStream r0 = (p043io.grpc.protobuf.lite.ProtoInputStream) r0
                com.google.protobuf.Parser r0 = r0.parser()
                com.google.protobuf.Parser<T> r1 = r5.parser
                if (r0 != r1) goto L_0x0017
                r0 = r6
                io.grpc.protobuf.lite.ProtoInputStream r0 = (p043io.grpc.protobuf.lite.ProtoInputStream) r0     // Catch:{ IllegalStateException -> 0x0017 }
                com.google.protobuf.MessageLite r6 = r0.message()     // Catch:{ IllegalStateException -> 0x0017 }
                return r6
            L_0x0017:
                r0 = 0
                boolean r1 = r6 instanceof p043io.grpc.KnownLength     // Catch:{ IOException -> 0x00a7 }
                if (r1 == 0) goto L_0x0084
                int r1 = r6.available()     // Catch:{ IOException -> 0x00a7 }
                if (r1 <= 0) goto L_0x007f
                r2 = 4194304(0x400000, float:5.877472E-39)
                if (r1 > r2) goto L_0x007f
                java.lang.ThreadLocal<java.lang.ref.Reference<byte[]>> r0 = bufs     // Catch:{ IOException -> 0x00a7 }
                java.lang.Object r0 = r0.get()     // Catch:{ IOException -> 0x00a7 }
                java.lang.ref.Reference r0 = (java.lang.ref.Reference) r0     // Catch:{ IOException -> 0x00a7 }
                if (r0 == 0) goto L_0x003b
                java.lang.Object r0 = r0.get()     // Catch:{ IOException -> 0x00a7 }
                byte[] r0 = (byte[]) r0     // Catch:{ IOException -> 0x00a7 }
                if (r0 == 0) goto L_0x003b
                int r2 = r0.length     // Catch:{ IOException -> 0x00a7 }
                if (r2 >= r1) goto L_0x0047
            L_0x003b:
                byte[] r0 = new byte[r1]     // Catch:{ IOException -> 0x00a7 }
                java.lang.ThreadLocal<java.lang.ref.Reference<byte[]>> r2 = bufs     // Catch:{ IOException -> 0x00a7 }
                java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ IOException -> 0x00a7 }
                r3.<init>(r0)     // Catch:{ IOException -> 0x00a7 }
                r2.set(r3)     // Catch:{ IOException -> 0x00a7 }
            L_0x0047:
                r2 = r1
            L_0x0048:
                if (r2 <= 0) goto L_0x0056
                int r3 = r1 - r2
                int r3 = r6.read(r0, r3, r2)     // Catch:{ IOException -> 0x00a7 }
                r4 = -1
                if (r3 != r4) goto L_0x0054
                goto L_0x0056
            L_0x0054:
                int r2 = r2 - r3
                goto L_0x0048
            L_0x0056:
                if (r2 != 0) goto L_0x005e
                r2 = 0
                com.google.protobuf.CodedInputStream r0 = com.google.protobuf.CodedInputStream.newInstance(r0, r2, r1)     // Catch:{ IOException -> 0x00a7 }
                goto L_0x0084
            L_0x005e:
                int r6 = r1 - r2
                java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ IOException -> 0x00a7 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a7 }
                r2.<init>()     // Catch:{ IOException -> 0x00a7 }
                java.lang.String r3 = "size inaccurate: "
                r2.append(r3)     // Catch:{ IOException -> 0x00a7 }
                r2.append(r1)     // Catch:{ IOException -> 0x00a7 }
                java.lang.String r1 = " != "
                r2.append(r1)     // Catch:{ IOException -> 0x00a7 }
                r2.append(r6)     // Catch:{ IOException -> 0x00a7 }
                java.lang.String r6 = r2.toString()     // Catch:{ IOException -> 0x00a7 }
                r0.<init>(r6)     // Catch:{ IOException -> 0x00a7 }
                throw r0     // Catch:{ IOException -> 0x00a7 }
            L_0x007f:
                if (r1 != 0) goto L_0x0084
                T r6 = r5.defaultInstance     // Catch:{ IOException -> 0x00a7 }
                return r6
            L_0x0084:
                if (r0 != 0) goto L_0x008a
                com.google.protobuf.CodedInputStream r0 = com.google.protobuf.CodedInputStream.newInstance(r6)
            L_0x008a:
                r6 = 2147483647(0x7fffffff, float:NaN)
                r0.setSizeLimit(r6)
                com.google.protobuf.MessageLite r6 = r5.parseFrom(r0)     // Catch:{ InvalidProtocolBufferException -> 0x0095 }
                return r6
            L_0x0095:
                r6 = move-exception
                io.grpc.Status r0 = p043io.grpc.Status.INTERNAL
                java.lang.String r1 = "Invalid protobuf byte sequence"
                io.grpc.Status r0 = r0.withDescription(r1)
                io.grpc.Status r6 = r0.withCause(r6)
                io.grpc.StatusRuntimeException r6 = r6.asRuntimeException()
                throw r6
            L_0x00a7:
                r6 = move-exception
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                r0.<init>(r6)
                goto L_0x00af
            L_0x00ae:
                throw r0
            L_0x00af:
                goto L_0x00ae
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.protobuf.lite.ProtoLiteUtils.MessageMarshaller.parse(java.io.InputStream):com.google.protobuf.MessageLite");
        }

        private T parseFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
            T t = (MessageLite) this.parser.parseFrom(codedInputStream, ProtoLiteUtils.globalRegistry);
            try {
                codedInputStream.checkLastTagWas(0);
                return t;
            } catch (InvalidProtocolBufferException e) {
                e.setUnfinishedMessage(t);
                throw e;
            }
        }
    }

    /* renamed from: io.grpc.protobuf.lite.ProtoLiteUtils$MetadataMarshaller */
    private static final class MetadataMarshaller<T extends MessageLite> implements BinaryMarshaller<T> {
        private final T defaultInstance;

        MetadataMarshaller(T t) {
            this.defaultInstance = t;
        }

        public byte[] toBytes(T t) {
            return t.toByteArray();
        }

        public T parseBytes(byte[] bArr) {
            try {
                return (MessageLite) this.defaultInstance.getParserForType().parseFrom(bArr, ProtoLiteUtils.globalRegistry);
            } catch (InvalidProtocolBufferException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public static void setExtensionRegistry(ExtensionRegistryLite extensionRegistryLite) {
        globalRegistry = (ExtensionRegistryLite) Preconditions.checkNotNull(extensionRegistryLite, "newRegistry");
    }

    public static <T extends MessageLite> Marshaller<T> marshaller(T t) {
        return new MessageMarshaller(t);
    }

    public static <T extends MessageLite> BinaryMarshaller<T> metadataMarshaller(T t) {
        return new MetadataMarshaller(t);
    }

    static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] bArr = new byte[8192];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    private ProtoLiteUtils() {
    }
}
