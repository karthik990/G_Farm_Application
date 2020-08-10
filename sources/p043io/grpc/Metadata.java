package p043io.grpc;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.p049io.BaseEncoding;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.grpc.Metadata */
public final class Metadata {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final AsciiMarshaller<String> ASCII_STRING_MARSHALLER = new AsciiMarshaller<String>() {
        public String parseAsciiString(String str) {
            return str;
        }

        public String toAsciiString(String str) {
            return str;
        }
    };
    public static final BinaryMarshaller<byte[]> BINARY_BYTE_MARSHALLER = new BinaryMarshaller<byte[]>() {
        public byte[] parseBytes(byte[] bArr) {
            return bArr;
        }

        public byte[] toBytes(byte[] bArr) {
            return bArr;
        }
    };
    public static final String BINARY_HEADER_SUFFIX = "-bin";
    private byte[][] namesAndValues;
    /* access modifiers changed from: private */
    public int size;

    /* renamed from: io.grpc.Metadata$AsciiKey */
    private static class AsciiKey<T> extends Key<T> {
        private final AsciiMarshaller<T> marshaller;

        private AsciiKey(String str, boolean z, AsciiMarshaller<T> asciiMarshaller) {
            super(str, z);
            String str2 = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(!str.endsWith(str2), "ASCII header is named %s.  Only binary headers may end with %s", (Object) str, (Object) str2);
            this.marshaller = (AsciiMarshaller) Preconditions.checkNotNull(asciiMarshaller, "marshaller");
        }

        /* access modifiers changed from: 0000 */
        public byte[] toBytes(T t) {
            return this.marshaller.toAsciiString(t).getBytes(Charsets.US_ASCII);
        }

        /* access modifiers changed from: 0000 */
        public T parseBytes(byte[] bArr) {
            return this.marshaller.parseAsciiString(new String(bArr, Charsets.US_ASCII));
        }
    }

    /* renamed from: io.grpc.Metadata$AsciiMarshaller */
    public interface AsciiMarshaller<T> {
        T parseAsciiString(String str);

        String toAsciiString(T t);
    }

    /* renamed from: io.grpc.Metadata$BinaryKey */
    private static class BinaryKey<T> extends Key<T> {
        private final BinaryMarshaller<T> marshaller;

        private BinaryKey(String str, BinaryMarshaller<T> binaryMarshaller) {
            boolean z = false;
            super(str, false);
            String str2 = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(str.endsWith(str2), "Binary header is named %s. It must end with %s", (Object) str, (Object) str2);
            if (str.length() > 4) {
                z = true;
            }
            Preconditions.checkArgument(z, "empty key name");
            this.marshaller = (BinaryMarshaller) Preconditions.checkNotNull(binaryMarshaller, "marshaller is null");
        }

        /* access modifiers changed from: 0000 */
        public byte[] toBytes(T t) {
            return this.marshaller.toBytes(t);
        }

        /* access modifiers changed from: 0000 */
        public T parseBytes(byte[] bArr) {
            return this.marshaller.parseBytes(bArr);
        }
    }

    /* renamed from: io.grpc.Metadata$BinaryMarshaller */
    public interface BinaryMarshaller<T> {
        T parseBytes(byte[] bArr);

        byte[] toBytes(T t);
    }

    /* renamed from: io.grpc.Metadata$IterableAt */
    private final class IterableAt<T> implements Iterable<T> {
        /* access modifiers changed from: private */
        public final Key<T> key;
        /* access modifiers changed from: private */
        public int startIdx;

        private IterableAt(Key<T> key2, int i) {
            this.key = key2;
            this.startIdx = i;
        }

        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private boolean hasNext = true;
                private int idx = IterableAt.this.startIdx;

                public boolean hasNext() {
                    if (this.hasNext) {
                        return true;
                    }
                    while (this.idx < Metadata.this.size) {
                        if (Metadata.this.bytesEqual(IterableAt.this.key.asciiName(), Metadata.this.name(this.idx))) {
                            this.hasNext = true;
                            return this.hasNext;
                        }
                        this.idx++;
                    }
                    return false;
                }

                public T next() {
                    if (hasNext()) {
                        this.hasNext = false;
                        Key access$200 = IterableAt.this.key;
                        Metadata metadata = Metadata.this;
                        int i = this.idx;
                        this.idx = i + 1;
                        return access$200.parseBytes(metadata.value(i));
                    }
                    throw new NoSuchElementException();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    /* renamed from: io.grpc.Metadata$Key */
    public static abstract class Key<T> {
        private static final BitSet VALID_T_CHARS = generateValidTChars();
        private final String name;
        private final byte[] nameBytes;
        private final String originalName;

        /* access modifiers changed from: 0000 */
        public abstract T parseBytes(byte[] bArr);

        /* access modifiers changed from: 0000 */
        public abstract byte[] toBytes(T t);

        /* renamed from: of */
        public static <T> Key<T> m3993of(String str, BinaryMarshaller<T> binaryMarshaller) {
            return new BinaryKey(str, binaryMarshaller);
        }

        /* renamed from: of */
        public static <T> Key<T> m3992of(String str, AsciiMarshaller<T> asciiMarshaller) {
            return m3994of(str, false, asciiMarshaller);
        }

        /* renamed from: of */
        static <T> Key<T> m3994of(String str, boolean z, AsciiMarshaller<T> asciiMarshaller) {
            return new AsciiKey(str, z, asciiMarshaller);
        }

        /* renamed from: of */
        static <T> Key<T> m3995of(String str, boolean z, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
            return new TrustedAsciiKey(str, z, trustedAsciiMarshaller);
        }

        private static BitSet generateValidTChars() {
            BitSet bitSet = new BitSet(Opcodes.LAND);
            bitSet.set(45);
            bitSet.set(95);
            bitSet.set(46);
            for (char c = '0'; c <= '9'; c = (char) (c + 1)) {
                bitSet.set(c);
            }
            for (char c2 = 'a'; c2 <= 'z'; c2 = (char) (c2 + 1)) {
                bitSet.set(c2);
            }
            return bitSet;
        }

        private static String validateName(String str, boolean z) {
            Preconditions.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            Preconditions.checkArgument(!str.isEmpty(), "token must have at least 1 tchar");
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (!z || charAt != ':' || i != 0) {
                    Preconditions.checkArgument(VALID_T_CHARS.get(charAt), "Invalid character '%s' in key name '%s'", charAt, (Object) str);
                }
            }
            return str;
        }

        private Key(String str, boolean z) {
            this.originalName = (String) Preconditions.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            this.name = validateName(this.originalName.toLowerCase(Locale.ROOT), z);
            this.nameBytes = this.name.getBytes(Charsets.US_ASCII);
        }

        public final String originalName() {
            return this.originalName;
        }

        public final String name() {
            return this.name;
        }

        /* access modifiers changed from: 0000 */
        public byte[] asciiName() {
            return this.nameBytes;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.name.equals(((Key) obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Key{name='");
            sb.append(this.name);
            sb.append("'}");
            return sb.toString();
        }
    }

    /* renamed from: io.grpc.Metadata$TrustedAsciiKey */
    private static final class TrustedAsciiKey<T> extends Key<T> {
        private final TrustedAsciiMarshaller<T> marshaller;

        private TrustedAsciiKey(String str, boolean z, TrustedAsciiMarshaller<T> trustedAsciiMarshaller) {
            super(str, z);
            String str2 = Metadata.BINARY_HEADER_SUFFIX;
            Preconditions.checkArgument(!str.endsWith(str2), "ASCII header is named %s.  Only binary headers may end with %s", (Object) str, (Object) str2);
            this.marshaller = (TrustedAsciiMarshaller) Preconditions.checkNotNull(trustedAsciiMarshaller, "marshaller");
        }

        /* access modifiers changed from: 0000 */
        public byte[] toBytes(T t) {
            return this.marshaller.toAsciiString(t);
        }

        /* access modifiers changed from: 0000 */
        public T parseBytes(byte[] bArr) {
            return this.marshaller.parseAsciiString(bArr);
        }
    }

    /* renamed from: io.grpc.Metadata$TrustedAsciiMarshaller */
    interface TrustedAsciiMarshaller<T> {
        T parseAsciiString(byte[] bArr);

        byte[] toAsciiString(T t);
    }

    Metadata(byte[]... bArr) {
        this(bArr.length / 2, bArr);
    }

    Metadata(int i, byte[]... bArr) {
        this.size = i;
        this.namesAndValues = bArr;
    }

    /* access modifiers changed from: private */
    public byte[] name(int i) {
        return this.namesAndValues[i * 2];
    }

    private void name(int i, byte[] bArr) {
        this.namesAndValues[i * 2] = bArr;
    }

    /* access modifiers changed from: private */
    public byte[] value(int i) {
        return this.namesAndValues[(i * 2) + 1];
    }

    private void value(int i, byte[] bArr) {
        this.namesAndValues[(i * 2) + 1] = bArr;
    }

    private int cap() {
        byte[][] bArr = this.namesAndValues;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    private int len() {
        return this.size * 2;
    }

    private boolean isEmpty() {
        return this.size == 0;
    }

    public Metadata() {
    }

    /* access modifiers changed from: 0000 */
    public int headerCount() {
        return this.size;
    }

    public boolean containsKey(Key<?> key) {
        for (int i = 0; i < this.size; i++) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public <T> T get(Key<T> key) {
        for (int i = this.size - 1; i >= 0; i--) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return key.parseBytes(value(i));
            }
        }
        return null;
    }

    @Nullable
    public <T> Iterable<T> getAll(Key<T> key) {
        for (int i = 0; i < this.size; i++) {
            if (bytesEqual(key.asciiName(), name(i))) {
                return new IterableAt(key, i);
            }
        }
        return null;
    }

    public Set<String> keys() {
        if (isEmpty()) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(this.size);
        for (int i = 0; i < this.size; i++) {
            hashSet.add(new String(name(i), 0));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public <T> void put(Key<T> key, T t) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(t, Param.VALUE);
        maybeExpand();
        name(this.size, key.asciiName());
        value(this.size, key.toBytes(t));
        this.size++;
    }

    private void maybeExpand() {
        if (len() == 0 || len() == cap()) {
            expand(Math.max(len() * 2, 8));
        }
    }

    private void expand(int i) {
        byte[][] bArr = new byte[i][];
        if (!isEmpty()) {
            System.arraycopy(this.namesAndValues, 0, bArr, 0, len());
        }
        this.namesAndValues = bArr;
    }

    public <T> boolean remove(Key<T> key, T t) {
        Preconditions.checkNotNull(key, "key");
        Preconditions.checkNotNull(t, Param.VALUE);
        for (int i = 0; i < this.size; i++) {
            if (bytesEqual(key.asciiName(), name(i)) && t.equals(key.parseBytes(value(i)))) {
                int i2 = i * 2;
                int i3 = (i + 1) * 2;
                int len = len() - i3;
                byte[][] bArr = this.namesAndValues;
                System.arraycopy(bArr, i3, bArr, i2, len);
                this.size--;
                name(this.size, null);
                value(this.size, null);
                return true;
            }
        }
        return false;
    }

    public <T> Iterable<T> removeAll(Key<T> key) {
        if (isEmpty()) {
            return null;
        }
        ArrayList arrayList = null;
        int i = 0;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (bytesEqual(key.asciiName(), name(i2))) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(key.parseBytes(value(i2)));
            } else {
                name(i, name(i2));
                value(i, value(i2));
                i++;
            }
        }
        Arrays.fill(this.namesAndValues, i * 2, len(), null);
        this.size = i;
        return arrayList;
    }

    public <T> void discardAll(Key<T> key) {
        if (!isEmpty()) {
            int i = 0;
            for (int i2 = 0; i2 < this.size; i2++) {
                if (!bytesEqual(key.asciiName(), name(i2))) {
                    name(i, name(i2));
                    value(i, value(i2));
                    i++;
                }
            }
            Arrays.fill(this.namesAndValues, i * 2, len(), null);
            this.size = i;
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public byte[][] serialize() {
        if (len() == cap()) {
            return this.namesAndValues;
        }
        byte[][] bArr = new byte[len()][];
        System.arraycopy(this.namesAndValues, 0, bArr, 0, len());
        return bArr;
    }

    public void merge(Metadata metadata) {
        if (!metadata.isEmpty()) {
            int cap = cap() - len();
            if (isEmpty() || cap < metadata.len()) {
                expand(len() + metadata.len());
            }
            System.arraycopy(metadata.namesAndValues, 0, this.namesAndValues, len(), metadata.len());
            this.size += metadata.size;
        }
    }

    public void merge(Metadata metadata, Set<Key<?>> set) {
        Preconditions.checkNotNull(metadata, "other");
        HashMap hashMap = new HashMap(set.size());
        for (Key key : set) {
            hashMap.put(ByteBuffer.wrap(key.asciiName()), key);
        }
        for (int i = 0; i < metadata.size; i++) {
            if (hashMap.containsKey(ByteBuffer.wrap(metadata.name(i)))) {
                maybeExpand();
                name(this.size, metadata.name(i));
                value(this.size, metadata.value(i));
                this.size++;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Metadata(");
        for (int i = 0; i < this.size; i++) {
            if (i != 0) {
                sb.append(StringUtil.COMMA);
            }
            String str = new String(name(i), Charsets.US_ASCII);
            sb.append(str);
            sb.append(SignatureVisitor.INSTANCEOF);
            if (str.endsWith(BINARY_HEADER_SUFFIX)) {
                sb.append(BaseEncoding.base64().encode(value(i)));
            } else {
                sb.append(new String(value(i), Charsets.US_ASCII));
            }
        }
        sb.append(')');
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public boolean bytesEqual(byte[] bArr, byte[] bArr2) {
        return Arrays.equals(bArr, bArr2);
    }
}
