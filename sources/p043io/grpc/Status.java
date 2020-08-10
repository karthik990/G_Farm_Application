package p043io.grpc;

import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.mobiroller.constants.Constants;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nullable;
import p043io.grpc.Metadata.Key;

/* renamed from: io.grpc.Status */
public final class Status {
    public static final Status ABORTED = Code.ABORTED.toStatus();
    public static final Status ALREADY_EXISTS = Code.ALREADY_EXISTS.toStatus();
    public static final Status CANCELLED = Code.CANCELLED.toStatus();
    static final Key<Status> CODE_KEY = Key.m3995of("grpc-status", false, (TrustedAsciiMarshaller<T>) new StatusCodeMarshaller<T>());
    public static final Status DATA_LOSS = Code.DATA_LOSS.toStatus();
    public static final Status DEADLINE_EXCEEDED = Code.DEADLINE_EXCEEDED.toStatus();
    public static final Status FAILED_PRECONDITION = Code.FAILED_PRECONDITION.toStatus();
    public static final Status INTERNAL = Code.INTERNAL.toStatus();
    public static final Status INVALID_ARGUMENT = Code.INVALID_ARGUMENT.toStatus();
    static final Key<String> MESSAGE_KEY = Key.m3995of("grpc-message", false, STATUS_MESSAGE_MARSHALLER);
    public static final Status NOT_FOUND = Code.NOT_FOUND.toStatus();

    /* renamed from: OK */
    public static final Status f3691OK = Code.OK.toStatus();
    public static final Status OUT_OF_RANGE = Code.OUT_OF_RANGE.toStatus();
    public static final Status PERMISSION_DENIED = Code.PERMISSION_DENIED.toStatus();
    public static final Status RESOURCE_EXHAUSTED = Code.RESOURCE_EXHAUSTED.toStatus();
    /* access modifiers changed from: private */
    public static final List<Status> STATUS_LIST = buildStatusList();
    private static final TrustedAsciiMarshaller<String> STATUS_MESSAGE_MARSHALLER = new StatusMessageMarshaller();
    public static final Status UNAUTHENTICATED = Code.UNAUTHENTICATED.toStatus();
    public static final Status UNAVAILABLE = Code.UNAVAILABLE.toStatus();
    public static final Status UNIMPLEMENTED = Code.UNIMPLEMENTED.toStatus();
    public static final Status UNKNOWN = Code.UNKNOWN.toStatus();
    private final Throwable cause;
    private final Code code;
    private final String description;

    /* renamed from: io.grpc.Status$Code */
    public enum Code {
        OK(0),
        CANCELLED(1),
        UNKNOWN(2),
        INVALID_ARGUMENT(3),
        DEADLINE_EXCEEDED(4),
        NOT_FOUND(5),
        ALREADY_EXISTS(6),
        PERMISSION_DENIED(7),
        RESOURCE_EXHAUSTED(8),
        FAILED_PRECONDITION(9),
        ABORTED(10),
        OUT_OF_RANGE(11),
        UNIMPLEMENTED(12),
        INTERNAL(13),
        UNAVAILABLE(14),
        DATA_LOSS(15),
        UNAUTHENTICATED(16);
        
        private final int value;
        private final byte[] valueAscii;

        private Code(int i) {
            this.value = i;
            this.valueAscii = Integer.toString(i).getBytes(Charsets.US_ASCII);
        }

        public int value() {
            return this.value;
        }

        public Status toStatus() {
            return (Status) Status.STATUS_LIST.get(this.value);
        }

        /* access modifiers changed from: private */
        public byte[] valueAscii() {
            return this.valueAscii;
        }
    }

    /* renamed from: io.grpc.Status$StatusCodeMarshaller */
    private static final class StatusCodeMarshaller implements TrustedAsciiMarshaller<Status> {
        private StatusCodeMarshaller() {
        }

        public byte[] toAsciiString(Status status) {
            return status.getCode().valueAscii();
        }

        public Status parseAsciiString(byte[] bArr) {
            return Status.fromCodeValue(bArr);
        }
    }

    /* renamed from: io.grpc.Status$StatusMessageMarshaller */
    private static final class StatusMessageMarshaller implements TrustedAsciiMarshaller<String> {
        private static final byte[] HEX = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};

        private static boolean isEscapingChar(byte b) {
            return b < 32 || b >= 126 || b == 37;
        }

        private StatusMessageMarshaller() {
        }

        public byte[] toAsciiString(String str) {
            byte[] bytes = str.getBytes(Charsets.UTF_8);
            for (int i = 0; i < bytes.length; i++) {
                if (isEscapingChar(bytes[i])) {
                    return toAsciiStringSlow(bytes, i);
                }
            }
            return bytes;
        }

        private static byte[] toAsciiStringSlow(byte[] bArr, int i) {
            byte[] bArr2 = new byte[(((bArr.length - i) * 3) + i)];
            if (i != 0) {
                System.arraycopy(bArr, 0, bArr2, 0, i);
            }
            int i2 = i;
            while (i < bArr.length) {
                byte b = bArr[i];
                if (isEscapingChar(b)) {
                    bArr2[i2] = 37;
                    int i3 = i2 + 1;
                    byte[] bArr3 = HEX;
                    bArr2[i3] = bArr3[(b >> 4) & 15];
                    bArr2[i2 + 2] = bArr3[b & Ascii.f1875SI];
                    i2 += 3;
                } else {
                    int i4 = i2 + 1;
                    bArr2[i2] = b;
                    i2 = i4;
                }
                i++;
            }
            byte[] bArr4 = new byte[i2];
            System.arraycopy(bArr2, 0, bArr4, 0, i2);
            return bArr4;
        }

        public String parseAsciiString(byte[] bArr) {
            for (int i = 0; i < bArr.length; i++) {
                byte b = bArr[i];
                if (b < 32 || b >= 126 || (b == 37 && i + 2 < bArr.length)) {
                    return parseAsciiStringSlow(bArr);
                }
            }
            return new String(bArr, 0);
        }

        private static String parseAsciiStringSlow(byte[] bArr) {
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
            int i = 0;
            while (i < bArr.length) {
                if (bArr[i] == 37 && i + 2 < bArr.length) {
                    try {
                        allocate.put((byte) Integer.parseInt(new String(bArr, i + 1, 2, Charsets.US_ASCII), 16));
                        i += 3;
                    } catch (NumberFormatException unused) {
                    }
                }
                allocate.put(bArr[i]);
                i++;
            }
            return new String(allocate.array(), 0, allocate.position(), Charsets.UTF_8);
        }
    }

    private static List<Status> buildStatusList() {
        TreeMap treeMap = new TreeMap();
        Code[] values = Code.values();
        int length = values.length;
        int i = 0;
        while (i < length) {
            Code code2 = values[i];
            Status status = (Status) treeMap.put(Integer.valueOf(code2.value()), new Status(code2));
            if (status == null) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Code value duplication between ");
                sb.append(status.getCode().name());
                sb.append(" & ");
                sb.append(code2.name());
                throw new IllegalStateException(sb.toString());
            }
        }
        return Collections.unmodifiableList(new ArrayList(treeMap.values()));
    }

    public static Status fromCodeValue(int i) {
        if (i >= 0 && i <= STATUS_LIST.size()) {
            return (Status) STATUS_LIST.get(i);
        }
        Status status = UNKNOWN;
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown code ");
        sb.append(i);
        return status.withDescription(sb.toString());
    }

    /* access modifiers changed from: private */
    public static Status fromCodeValue(byte[] bArr) {
        if (bArr.length == 1 && bArr[0] == 48) {
            return f3691OK;
        }
        return fromCodeValueSlow(bArr);
    }

    private static Status fromCodeValueSlow(byte[] bArr) {
        int length = bArr.length;
        char c = 1;
        int i = 0;
        if (length != 1) {
            if (length == 2 && bArr[0] >= 48 && bArr[0] <= 57) {
                i = 0 + ((bArr[0] - 48) * 10);
            }
            Status status = UNKNOWN;
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown code ");
            sb.append(new String(bArr, Charsets.US_ASCII));
            return status.withDescription(sb.toString());
        }
        c = 0;
        if (bArr[c] >= 48 && bArr[c] <= 57) {
            int i2 = i + (bArr[c] - 48);
            if (i2 < STATUS_LIST.size()) {
                return (Status) STATUS_LIST.get(i2);
            }
        }
        Status status2 = UNKNOWN;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Unknown code ");
        sb2.append(new String(bArr, Charsets.US_ASCII));
        return status2.withDescription(sb2.toString());
    }

    public static Status fromCode(Code code2) {
        return code2.toStatus();
    }

    public static Status fromThrowable(Throwable th) {
        for (Throwable th2 = (Throwable) Preconditions.checkNotNull(th, "t"); th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof StatusException) {
                return ((StatusException) th2).getStatus();
            }
            if (th2 instanceof StatusRuntimeException) {
                return ((StatusRuntimeException) th2).getStatus();
            }
        }
        return UNKNOWN.withCause(th);
    }

    public static Metadata trailersFromThrowable(Throwable th) {
        for (Throwable th2 = (Throwable) Preconditions.checkNotNull(th, "t"); th2 != null; th2 = th2.getCause()) {
            if (th2 instanceof StatusException) {
                return ((StatusException) th2).getTrailers();
            }
            if (th2 instanceof StatusRuntimeException) {
                return ((StatusRuntimeException) th2).getTrailers();
            }
        }
        return null;
    }

    static String formatThrowableMessage(Status status) {
        if (status.description == null) {
            return status.code.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(status.code);
        sb.append(": ");
        sb.append(status.description);
        return sb.toString();
    }

    private Status(Code code2) {
        this(code2, null, null);
    }

    private Status(Code code2, @Nullable String str, @Nullable Throwable th) {
        this.code = (Code) Preconditions.checkNotNull(code2, "code");
        this.description = str;
        this.cause = th;
    }

    public Status withCause(Throwable th) {
        if (Objects.equal(this.cause, th)) {
            return this;
        }
        return new Status(this.code, this.description, th);
    }

    public Status withDescription(String str) {
        if (Objects.equal(this.description, str)) {
            return this;
        }
        return new Status(this.code, str, this.cause);
    }

    public Status augmentDescription(String str) {
        if (str == null) {
            return this;
        }
        if (this.description == null) {
            return new Status(this.code, str, this.cause);
        }
        Code code2 = this.code;
        StringBuilder sb = new StringBuilder();
        sb.append(this.description);
        sb.append(Constants.NEW_LINE);
        sb.append(str);
        return new Status(code2, sb.toString(), this.cause);
    }

    public Code getCode() {
        return this.code;
    }

    @Nullable
    public String getDescription() {
        return this.description;
    }

    @Nullable
    public Throwable getCause() {
        return this.cause;
    }

    public boolean isOk() {
        return Code.OK == this.code;
    }

    public StatusRuntimeException asRuntimeException() {
        return new StatusRuntimeException(this);
    }

    public StatusRuntimeException asRuntimeException(@Nullable Metadata metadata) {
        return new StatusRuntimeException(this, metadata);
    }

    public StatusException asException() {
        return new StatusException(this);
    }

    public StatusException asException(@Nullable Metadata metadata) {
        return new StatusException(this, metadata);
    }

    /* JADX WARNING: type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String toString() {
        /*
            r3 = this;
            com.google.common.base.MoreObjects$ToStringHelper r0 = com.google.common.base.MoreObjects.toStringHelper(r3)
            io.grpc.Status$Code r1 = r3.code
            java.lang.String r1 = r1.name()
            java.lang.String r2 = "code"
            com.google.common.base.MoreObjects$ToStringHelper r0 = r0.add(r2, r1)
            java.lang.String r1 = r3.description
            java.lang.String r2 = "description"
            com.google.common.base.MoreObjects$ToStringHelper r0 = r0.add(r2, r1)
            java.lang.Throwable r1 = r3.cause
            if (r1 == 0) goto L_0x0020
            java.lang.String r1 = com.google.common.base.Throwables.getStackTraceAsString(r1)
        L_0x0020:
            java.lang.String r2 = "cause"
            com.google.common.base.MoreObjects$ToStringHelper r0 = r0.add(r2, r1)
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.Status.toString():java.lang.String");
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return super.hashCode();
    }
}
