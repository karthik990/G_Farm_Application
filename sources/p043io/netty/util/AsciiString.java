package p043io.netty.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import p043io.netty.util.ByteProcessor.IndexOfProcessor;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.util.AsciiString */
public final class AsciiString implements CharSequence, Comparable<CharSequence> {
    public static final HashingStrategy<CharSequence> CASE_INSENSITIVE_HASHER = new HashingStrategy<CharSequence>() {
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2);
        }
    };
    public static final HashingStrategy<CharSequence> CASE_SENSITIVE_HASHER = new HashingStrategy<CharSequence>() {
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEquals(charSequence, charSequence2);
        }
    };
    public static final AsciiString EMPTY_STRING = new AsciiString((CharSequence) "");
    public static final int INDEX_NOT_FOUND = -1;
    private static final char MAX_CHAR_VALUE = 'ÿ';
    private int hash;
    private final int length;
    private final int offset;
    private String string;
    private final byte[] value;

    /* renamed from: io.netty.util.AsciiString$AsciiCaseInsensitiveCharEqualityComparator */
    private static final class AsciiCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
        static final AsciiCaseInsensitiveCharEqualityComparator INSTANCE = new AsciiCaseInsensitiveCharEqualityComparator();

        private AsciiCaseInsensitiveCharEqualityComparator() {
        }

        public boolean equals(char c, char c2) {
            return AsciiString.equalsIgnoreCase(c, c2);
        }
    }

    /* renamed from: io.netty.util.AsciiString$CharEqualityComparator */
    private interface CharEqualityComparator {
        boolean equals(char c, char c2);
    }

    /* renamed from: io.netty.util.AsciiString$DefaultCharEqualityComparator */
    private static final class DefaultCharEqualityComparator implements CharEqualityComparator {
        static final DefaultCharEqualityComparator INSTANCE = new DefaultCharEqualityComparator();

        public boolean equals(char c, char c2) {
            return c == c2;
        }

        private DefaultCharEqualityComparator() {
        }
    }

    /* renamed from: io.netty.util.AsciiString$GeneralCaseInsensitiveCharEqualityComparator */
    private static final class GeneralCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
        static final GeneralCaseInsensitiveCharEqualityComparator INSTANCE = new GeneralCaseInsensitiveCharEqualityComparator();

        private GeneralCaseInsensitiveCharEqualityComparator() {
        }

        public boolean equals(char c, char c2) {
            return Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
        }
    }

    public static char b2c(byte b) {
        return (char) (b & 255);
    }

    public static byte c2b(char c) {
        if (c > 255) {
            c = '?';
        }
        return (byte) c;
    }

    private static boolean isLowerCase(byte b) {
        return b >= 97 && b <= 122;
    }

    public static boolean isUpperCase(byte b) {
        return b >= 65 && b <= 90;
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public AsciiString(byte[] bArr) {
        this(bArr, true);
    }

    public AsciiString(byte[] bArr, boolean z) {
        this(bArr, 0, bArr.length, z);
    }

    public AsciiString(byte[] bArr, int i, int i2, boolean z) {
        if (z) {
            this.value = Arrays.copyOfRange(bArr, i, i + i2);
            this.offset = 0;
        } else if (!MathUtil.isOutOfBounds(i, i2, bArr.length)) {
            this.value = bArr;
            this.offset = i;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("expected: 0 <= start(");
            sb.append(i);
            sb.append(") <= start + length(");
            sb.append(i2);
            sb.append(") <= value.length(");
            sb.append(bArr.length);
            sb.append(')');
            throw new IndexOutOfBoundsException(sb.toString());
        }
        this.length = i2;
    }

    public AsciiString(ByteBuffer byteBuffer) {
        this(byteBuffer, true);
    }

    public AsciiString(ByteBuffer byteBuffer, boolean z) {
        this(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), z);
    }

    public AsciiString(ByteBuffer byteBuffer, int i, int i2, boolean z) {
        if (!MathUtil.isOutOfBounds(i, i2, byteBuffer.capacity())) {
            if (!byteBuffer.hasArray()) {
                this.value = new byte[i2];
                int position = byteBuffer.position();
                byteBuffer.get(this.value, 0, i2);
                byteBuffer.position(position);
                this.offset = 0;
            } else if (z) {
                int arrayOffset = byteBuffer.arrayOffset() + i;
                this.value = Arrays.copyOfRange(byteBuffer.array(), arrayOffset, arrayOffset + i2);
                this.offset = 0;
            } else {
                this.value = byteBuffer.array();
                this.offset = i;
            }
            this.length = i2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= start(");
        sb.append(i);
        sb.append(") <= start + length(");
        sb.append(i2);
        sb.append(") <= value.capacity(");
        sb.append(byteBuffer.capacity());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public AsciiString(char[] cArr) {
        this(cArr, 0, cArr.length);
    }

    public AsciiString(char[] cArr, int i, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, cArr.length)) {
            this.value = new byte[i2];
            int i3 = i;
            int i4 = 0;
            while (i4 < i2) {
                this.value[i4] = c2b(cArr[i3]);
                i4++;
                i3++;
            }
            this.offset = 0;
            this.length = i2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= start(");
        sb.append(i);
        sb.append(") <= start + length(");
        sb.append(i2);
        sb.append(") <= value.length(");
        sb.append(cArr.length);
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public AsciiString(char[] cArr, Charset charset) {
        this(cArr, charset, 0, cArr.length);
    }

    public AsciiString(char[] cArr, Charset charset, int i, int i2) {
        CharBuffer wrap = CharBuffer.wrap(cArr, i, i2);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer allocate = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * ((float) i2)));
        encoder.encode(wrap, allocate, true);
        int arrayOffset = allocate.arrayOffset();
        this.value = Arrays.copyOfRange(allocate.array(), arrayOffset, allocate.position() + arrayOffset);
        this.offset = 0;
        this.length = this.value.length;
    }

    public AsciiString(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, int i, int i2) {
        if (!MathUtil.isOutOfBounds(i, i2, charSequence.length())) {
            this.value = new byte[i2];
            int i3 = i;
            int i4 = 0;
            while (i4 < i2) {
                this.value[i4] = c2b(charSequence.charAt(i3));
                i4++;
                i3++;
            }
            this.offset = 0;
            this.length = i2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= start(");
        sb.append(i);
        sb.append(") <= start + length(");
        sb.append(i2);
        sb.append(") <= value.length(");
        sb.append(charSequence.length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public AsciiString(CharSequence charSequence, Charset charset) {
        this(charSequence, charset, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, Charset charset, int i, int i2) {
        CharBuffer wrap = CharBuffer.wrap(charSequence, i, i + i2);
        CharsetEncoder encoder = CharsetUtil.encoder(charset);
        ByteBuffer allocate = ByteBuffer.allocate((int) (encoder.maxBytesPerChar() * ((float) i2)));
        encoder.encode(wrap, allocate, true);
        int arrayOffset = allocate.arrayOffset();
        this.value = Arrays.copyOfRange(allocate.array(), arrayOffset, allocate.position() + arrayOffset);
        this.offset = 0;
        this.length = this.value.length;
    }

    public int forEachByte(ByteProcessor byteProcessor) throws Exception {
        return forEachByte0(0, length(), byteProcessor);
    }

    public int forEachByte(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        if (!MathUtil.isOutOfBounds(i, i2, length())) {
            return forEachByte0(i, i2, byteProcessor);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= index(");
        sb.append(i);
        sb.append(") <= start + length(");
        sb.append(i2);
        sb.append(") <= length(");
        sb.append(length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private int forEachByte0(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        int i3 = this.offset;
        int i4 = i3 + i + i2;
        for (int i5 = i3 + i; i5 < i4; i5++) {
            if (!byteProcessor.process(this.value[i5])) {
                return i5 - this.offset;
            }
        }
        return -1;
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) throws Exception {
        return forEachByteDesc0(0, length(), byteProcessor);
    }

    public int forEachByteDesc(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        if (!MathUtil.isOutOfBounds(i, i2, length())) {
            return forEachByteDesc0(i, i2, byteProcessor);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= index(");
        sb.append(i);
        sb.append(") <= start + length(");
        sb.append(i2);
        sb.append(") <= length(");
        sb.append(length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private int forEachByteDesc0(int i, int i2, ByteProcessor byteProcessor) throws Exception {
        int i3 = this.offset;
        int i4 = i3 + i;
        for (int i5 = ((i3 + i) + i2) - 1; i5 >= i4; i5--) {
            if (!byteProcessor.process(this.value[i5])) {
                return i5 - this.offset;
            }
        }
        return -1;
    }

    public byte byteAt(int i) {
        if (i < 0 || i >= this.length) {
            StringBuilder sb = new StringBuilder();
            sb.append("index: ");
            sb.append(i);
            sb.append(" must be in the range [0,");
            sb.append(this.length);
            sb.append(")");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.getByte(this.value, i + this.offset);
        } else {
            return this.value[i + this.offset];
        }
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int length() {
        return this.length;
    }

    public void arrayChanged() {
        this.string = null;
        this.hash = 0;
    }

    public byte[] array() {
        return this.value;
    }

    public int arrayOffset() {
        return this.offset;
    }

    public boolean isEntireArrayUsed() {
        return this.offset == 0 && this.length == this.value.length;
    }

    public byte[] toByteArray() {
        return toByteArray(0, length());
    }

    public byte[] toByteArray(int i, int i2) {
        byte[] bArr = this.value;
        int i3 = this.offset;
        return Arrays.copyOfRange(bArr, i + i3, i2 + i3);
    }

    public void copy(int i, byte[] bArr, int i2, int i3) {
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            System.arraycopy(this.value, i + this.offset, ObjectUtil.checkNotNull(bArr, "dst"), i2, i3);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= srcIdx(");
        sb.append(i);
        sb.append(") <= srcIdx + length(");
        sb.append(i3);
        sb.append(") <= srcLen(");
        sb.append(length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public char charAt(int i) {
        return b2c(byteAt(i));
    }

    public boolean contains(CharSequence charSequence) {
        return indexOf(charSequence) >= 0;
    }

    public int compareTo(CharSequence charSequence) {
        int i = 0;
        if (this == charSequence) {
            return 0;
        }
        int length2 = length();
        int length3 = charSequence.length();
        int min = Math.min(length2, length3);
        int arrayOffset = arrayOffset();
        while (i < min) {
            int b2c = b2c(this.value[arrayOffset]) - charSequence.charAt(i);
            if (b2c != 0) {
                return b2c;
            }
            i++;
            arrayOffset++;
        }
        return length2 - length3;
    }

    public AsciiString concat(CharSequence charSequence) {
        int length2 = length();
        int length3 = charSequence.length();
        if (length3 == 0) {
            return this;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString) charSequence;
            if (isEmpty()) {
                return asciiString;
            }
            byte[] bArr = new byte[(length2 + length3)];
            System.arraycopy(this.value, arrayOffset(), bArr, 0, length2);
            System.arraycopy(asciiString.value, asciiString.arrayOffset(), bArr, length2, length3);
            return new AsciiString(bArr, false);
        } else if (isEmpty()) {
            return new AsciiString(charSequence);
        } else {
            byte[] bArr2 = new byte[(length3 + length2)];
            System.arraycopy(this.value, arrayOffset(), bArr2, 0, length2);
            int i = 0;
            while (length2 < bArr2.length) {
                bArr2[length2] = c2b(charSequence.charAt(i));
                length2++;
                i++;
            }
            return new AsciiString(bArr2, false);
        }
    }

    public boolean endsWith(CharSequence charSequence) {
        int length2 = charSequence.length();
        return regionMatches(length() - length2, charSequence, 0, length2);
    }

    public boolean contentEqualsIgnoreCase(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != length()) {
            return false;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString) charSequence;
            int arrayOffset = arrayOffset();
            int arrayOffset2 = asciiString.arrayOffset();
            while (arrayOffset < length()) {
                if (!equalsIgnoreCase(this.value[arrayOffset], asciiString.value[arrayOffset2])) {
                    return false;
                }
                arrayOffset++;
                arrayOffset2++;
            }
            return true;
        }
        int arrayOffset3 = arrayOffset();
        int i = 0;
        while (arrayOffset3 < length()) {
            if (!equalsIgnoreCase(b2c(this.value[arrayOffset3]), charSequence.charAt(i))) {
                return false;
            }
            arrayOffset3++;
            i++;
        }
        return true;
    }

    public char[] toCharArray() {
        return toCharArray(0, length());
    }

    public char[] toCharArray(int i, int i2) {
        int i3 = i2 - i;
        if (i3 == 0) {
            return EmptyArrays.EMPTY_CHARS;
        }
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            char[] cArr = new char[i3];
            int i4 = 0;
            int arrayOffset = i + arrayOffset();
            while (i4 < i3) {
                cArr[i4] = b2c(this.value[arrayOffset]);
                i4++;
                arrayOffset++;
            }
            return cArr;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= start(");
        sb.append(i);
        sb.append(") <= srcIdx + length(");
        sb.append(i3);
        sb.append(") <= srcLen(");
        sb.append(length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public void copy(int i, char[] cArr, int i2, int i3) {
        if (cArr == null) {
            throw new NullPointerException("dst");
        } else if (!MathUtil.isOutOfBounds(i, i3, length())) {
            int i4 = i3 + i2;
            int arrayOffset = i + arrayOffset();
            while (i2 < i4) {
                cArr[i2] = b2c(this.value[arrayOffset]);
                i2++;
                arrayOffset++;
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("expected: 0 <= srcIdx(");
            sb.append(i);
            sb.append(") <= srcIdx + length(");
            sb.append(i3);
            sb.append(") <= srcLen(");
            sb.append(length());
            sb.append(')');
            throw new IndexOutOfBoundsException(sb.toString());
        }
    }

    public AsciiString subSequence(int i) {
        return subSequence(i, length());
    }

    public AsciiString subSequence(int i, int i2) {
        return subSequence(i, i2, true);
    }

    public AsciiString subSequence(int i, int i2, boolean z) {
        int i3 = i2 - i;
        if (MathUtil.isOutOfBounds(i, i3, length())) {
            StringBuilder sb = new StringBuilder();
            sb.append("expected: 0 <= start(");
            sb.append(i);
            sb.append(") <= end (");
            sb.append(i2);
            sb.append(") <= length(");
            sb.append(length());
            sb.append(')');
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i == 0 && i2 == length()) {
            return this;
        } else {
            if (i2 == i) {
                return EMPTY_STRING;
            }
            return new AsciiString(this.value, i + this.offset, i3, z);
        }
    }

    public int indexOf(CharSequence charSequence) {
        return indexOf(charSequence, 0);
    }

    public int indexOf(CharSequence charSequence, int i) {
        if (i < 0) {
            i = 0;
        }
        int length2 = length();
        int length3 = charSequence.length();
        if (length3 <= 0) {
            if (i >= length2) {
                i = length2;
            }
            return i;
        } else if (length3 > length2 - i) {
            return -1;
        } else {
            char charAt = charSequence.charAt(0);
            if (charAt > 255) {
                return -1;
            }
            IndexOfProcessor indexOfProcessor = new IndexOfProcessor((byte) charAt);
            while (true) {
                try {
                    int forEachByte = forEachByte(i, length2 - i, indexOfProcessor);
                    if (forEachByte == -1) {
                        break;
                    } else if (length3 + forEachByte > length2) {
                        break;
                    } else {
                        int i2 = forEachByte;
                        int i3 = 0;
                        do {
                            i3++;
                            if (i3 >= length3) {
                                break;
                            }
                            i2++;
                        } while (b2c(this.value[arrayOffset() + i2]) == charSequence.charAt(i3));
                        if (i3 == length3) {
                            return forEachByte;
                        }
                        i = forEachByte + 1;
                    }
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                    return -1;
                }
            }
            return -1;
        }
    }

    public int indexOf(char c, int i) {
        if (i < 0) {
            i = 0;
        }
        int length2 = length();
        if (c > 255) {
            return -1;
        }
        try {
            return forEachByte(i, length2 - i, new IndexOfProcessor((byte) c));
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return -1;
        }
    }

    public int lastIndexOf(CharSequence charSequence) {
        return lastIndexOf(charSequence, length());
    }

    public int lastIndexOf(CharSequence charSequence, int i) {
        int length2 = length();
        int length3 = charSequence.length();
        if (length3 <= length2 && i >= 0) {
            if (length3 <= 0) {
                if (i >= length2) {
                    i = length2;
                }
                return i;
            }
            int min = Math.min(i, length2 - length3);
            char charAt = charSequence.charAt(0);
            if (charAt > 255) {
                return -1;
            }
            IndexOfProcessor indexOfProcessor = new IndexOfProcessor((byte) charAt);
            while (true) {
                try {
                    int forEachByteDesc = forEachByteDesc(min, length2 - min, indexOfProcessor);
                    if (forEachByteDesc == -1) {
                        return -1;
                    }
                    int i2 = forEachByteDesc;
                    int i3 = 0;
                    do {
                        i3++;
                        if (i3 >= length3) {
                            break;
                        }
                        i2++;
                    } while (b2c(this.value[arrayOffset() + i2]) == charSequence.charAt(i3));
                    if (i3 == length3) {
                        return forEachByteDesc;
                    }
                    min = forEachByteDesc - 1;
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                }
            }
        }
        return -1;
    }

    public boolean regionMatches(int i, CharSequence charSequence, int i2, int i3) {
        if (charSequence != null) {
            if (i2 >= 0 && charSequence.length() - i2 >= i3) {
                int length2 = length();
                if (i >= 0 && length2 - i >= i3) {
                    if (i3 <= 0) {
                        return true;
                    }
                    int i4 = i3 + i2;
                    int arrayOffset = i + arrayOffset();
                    while (i2 < i4) {
                        if (b2c(this.value[arrayOffset]) != charSequence.charAt(i2)) {
                            return false;
                        }
                        i2++;
                        arrayOffset++;
                    }
                    return true;
                }
            }
            return false;
        }
        throw new NullPointerException("string");
    }

    public boolean regionMatches(boolean z, int i, CharSequence charSequence, int i2, int i3) {
        if (!z) {
            return regionMatches(i, charSequence, i2, i3);
        }
        if (charSequence != null) {
            int length2 = length();
            if (i < 0 || i3 > length2 - i || i2 < 0 || i3 > charSequence.length() - i2) {
                return false;
            }
            int arrayOffset = i + arrayOffset();
            int i4 = i3 + arrayOffset;
            while (arrayOffset < i4) {
                int i5 = arrayOffset + 1;
                int i6 = i2 + 1;
                if (!equalsIgnoreCase(b2c(this.value[arrayOffset]), charSequence.charAt(i2))) {
                    return false;
                }
                i2 = i6;
                arrayOffset = i5;
            }
            return true;
        }
        throw new NullPointerException("string");
    }

    public AsciiString replace(char c, char c2) {
        if (c > 255) {
            return this;
        }
        byte c2b = c2b(c);
        try {
            if (forEachByte(new IndexOfProcessor(c2b)) == -1) {
                return this;
            }
            byte c2b2 = c2b(c2);
            byte[] bArr = new byte[length()];
            int arrayOffset = arrayOffset();
            int i = 0;
            while (i < bArr.length) {
                byte b = this.value[arrayOffset];
                if (b == c2b) {
                    b = c2b2;
                }
                bArr[i] = b;
                i++;
                arrayOffset++;
            }
            return new AsciiString(bArr, false);
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            return this;
        }
    }

    public boolean startsWith(CharSequence charSequence) {
        return startsWith(charSequence, 0);
    }

    public boolean startsWith(CharSequence charSequence, int i) {
        return regionMatches(i, charSequence, 0, charSequence.length());
    }

    public AsciiString toLowerCase() {
        boolean z;
        int length2 = length() + arrayOffset();
        int arrayOffset = arrayOffset();
        while (true) {
            if (arrayOffset >= length2) {
                z = true;
                break;
            }
            byte b = this.value[arrayOffset];
            if (b >= 65 && b <= 90) {
                z = false;
                break;
            }
            arrayOffset++;
        }
        if (z) {
            return this;
        }
        byte[] bArr = new byte[length()];
        int arrayOffset2 = arrayOffset();
        int i = 0;
        while (i < bArr.length) {
            bArr[i] = toLowerCase(this.value[arrayOffset2]);
            i++;
            arrayOffset2++;
        }
        return new AsciiString(bArr, false);
    }

    public AsciiString toUpperCase() {
        boolean z;
        int length2 = length() + arrayOffset();
        int arrayOffset = arrayOffset();
        while (true) {
            if (arrayOffset >= length2) {
                z = true;
                break;
            }
            byte b = this.value[arrayOffset];
            if (b >= 97 && b <= 122) {
                z = false;
                break;
            }
            arrayOffset++;
        }
        if (z) {
            return this;
        }
        byte[] bArr = new byte[length()];
        int arrayOffset2 = arrayOffset();
        int i = 0;
        while (i < bArr.length) {
            bArr[i] = toUpperCase(this.value[arrayOffset2]);
            i++;
            arrayOffset2++;
        }
        return new AsciiString(bArr, false);
    }

    public AsciiString trim() {
        int arrayOffset = arrayOffset();
        int arrayOffset2 = (arrayOffset() + length()) - 1;
        while (arrayOffset <= arrayOffset2 && this.value[arrayOffset] <= 32) {
            arrayOffset++;
        }
        int i = arrayOffset2;
        while (i >= arrayOffset && this.value[i] <= 32) {
            i--;
        }
        if (arrayOffset == 0 && i == arrayOffset2) {
            return this;
        }
        return new AsciiString(this.value, arrayOffset, (i - arrayOffset) + 1, false);
    }

    public boolean contentEquals(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != length()) {
            return false;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return equals(charSequence);
        }
        int arrayOffset = arrayOffset();
        for (int i = 0; i < charSequence.length(); i++) {
            if (b2c(this.value[arrayOffset]) != charSequence.charAt(i)) {
                return false;
            }
            arrayOffset++;
        }
        return true;
    }

    public boolean matches(String str) {
        return Pattern.matches(str, this);
    }

    public AsciiString[] split(String str, int i) {
        return toAsciiStringArray(Pattern.compile(str).split(this, i));
    }

    public AsciiString[] split(char c) {
        ArrayList arrayList = InternalThreadLocalMap.get().arrayList();
        int length2 = length();
        int i = 0;
        for (int i2 = 0; i2 < length2; i2++) {
            if (charAt(i2) == c) {
                if (i == i2) {
                    arrayList.add(EMPTY_STRING);
                } else {
                    arrayList.add(new AsciiString(this.value, arrayOffset() + i, i2 - i, false));
                }
                i = i2 + 1;
            }
        }
        if (i == 0) {
            arrayList.add(this);
        } else if (i != length2) {
            arrayList.add(new AsciiString(this.value, arrayOffset() + i, length2 - i, false));
        } else {
            int size = arrayList.size() - 1;
            while (size >= 0 && ((AsciiString) arrayList.get(size)).isEmpty()) {
                arrayList.remove(size);
                size--;
            }
        }
        return (AsciiString[]) arrayList.toArray(new AsciiString[arrayList.size()]);
    }

    public int hashCode() {
        if (this.hash == 0) {
            this.hash = PlatformDependent.hashCodeAscii(this.value, this.offset, this.length);
        }
        return this.hash;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null && obj.getClass() == AsciiString.class) {
            if (this == obj) {
                return true;
            }
            AsciiString asciiString = (AsciiString) obj;
            if (length() == asciiString.length() && hashCode() == asciiString.hashCode() && PlatformDependent.equals(array(), arrayOffset(), asciiString.array(), asciiString.arrayOffset(), length())) {
                z = true;
            }
        }
        return z;
    }

    public String toString() {
        String str = this.string;
        if (str != null) {
            return str;
        }
        this.string = toString(0);
        return this.string;
    }

    public String toString(int i) {
        return toString(i, length());
    }

    public String toString(int i, int i2) {
        int i3 = i2 - i;
        if (i3 == 0) {
            return "";
        }
        if (!MathUtil.isOutOfBounds(i, i3, length())) {
            return new String(this.value, 0, i + this.offset, i3);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: 0 <= start(");
        sb.append(i);
        sb.append(") <= srcIdx + length(");
        sb.append(i3);
        sb.append(") <= srcLen(");
        sb.append(length());
        sb.append(')');
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public boolean parseBoolean() {
        return this.length >= 1 && this.value[this.offset] != 0;
    }

    public char parseChar() {
        return parseChar(0);
    }

    public char parseChar(int i) {
        if (i + 1 < length()) {
            int i2 = i + this.offset;
            return (char) (b2c(this.value[i2 + 1]) | (b2c(this.value[i2]) << 8));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("2 bytes required to convert to character. index ");
        sb.append(i);
        sb.append(" would go out of bounds.");
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public short parseShort() {
        return parseShort(0, length(), 10);
    }

    public short parseShort(int i) {
        return parseShort(0, length(), i);
    }

    public short parseShort(int i, int i2) {
        return parseShort(i, i2, 10);
    }

    public short parseShort(int i, int i2, int i3) {
        int parseInt = parseInt(i, i2, i3);
        short s = (short) parseInt;
        if (s == parseInt) {
            return s;
        }
        throw new NumberFormatException(subSequence(i, i2, false).toString());
    }

    public int parseInt() {
        return parseInt(0, length(), 10);
    }

    public int parseInt(int i) {
        return parseInt(0, length(), i);
    }

    public int parseInt(int i, int i2) {
        return parseInt(i, i2, 10);
    }

    public int parseInt(int i, int i2, int i3) {
        if (i3 < 2 || i3 > 36) {
            throw new NumberFormatException();
        } else if (i != i2) {
            boolean z = byteAt(i) == 45;
            if (z) {
                int i4 = i + 1;
                if (i4 != i2) {
                    i = i4;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            }
            return parseInt(i, i2, i3, z);
        } else {
            throw new NumberFormatException();
        }
    }

    private int parseInt(int i, int i2, int i3, boolean z) {
        int i4 = Integer.MIN_VALUE / i3;
        int i5 = i;
        int i6 = 0;
        while (i5 < i2) {
            int i7 = i5 + 1;
            int digit = Character.digit((char) (this.value[i5 + this.offset] & 255), i3);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            } else if (i4 <= i6) {
                int i8 = (i6 * i3) - digit;
                if (i8 <= i6) {
                    i6 = i8;
                    i5 = i7;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            } else {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            }
        }
        if (!z) {
            i6 = -i6;
            if (i6 < 0) {
                throw new NumberFormatException(subSequence(i, i2, false).toString());
            }
        }
        return i6;
    }

    public long parseLong() {
        return parseLong(0, length(), 10);
    }

    public long parseLong(int i) {
        return parseLong(0, length(), i);
    }

    public long parseLong(int i, int i2) {
        return parseLong(i, i2, 10);
    }

    public long parseLong(int i, int i2, int i3) {
        if (i3 < 2 || i3 > 36) {
            throw new NumberFormatException();
        } else if (i != i2) {
            boolean z = byteAt(i) == 45;
            if (z) {
                int i4 = i + 1;
                if (i4 != i2) {
                    i = i4;
                } else {
                    throw new NumberFormatException(subSequence(i, i2, false).toString());
                }
            }
            return parseLong(i, i2, i3, z);
        } else {
            throw new NumberFormatException();
        }
    }

    private long parseLong(int i, int i2, int i3, boolean z) {
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        long j = (long) i6;
        long j2 = Long.MIN_VALUE / j;
        int i7 = i4;
        long j3 = 0;
        while (i7 < i5) {
            int i8 = i7 + 1;
            int digit = Character.digit((char) (this.value[i7 + this.offset] & 255), i6);
            if (digit == -1) {
                throw new NumberFormatException(subSequence(i4, i5, false).toString());
            } else if (j2 <= j3) {
                long j4 = (j3 * j) - ((long) digit);
                if (j4 <= j3) {
                    j3 = j4;
                    i7 = i8;
                } else {
                    throw new NumberFormatException(subSequence(i4, i5, false).toString());
                }
            } else {
                throw new NumberFormatException(subSequence(i4, i5, false).toString());
            }
        }
        if (!z) {
            j3 = -j3;
            if (j3 < 0) {
                throw new NumberFormatException(subSequence(i4, i5, false).toString());
            }
        }
        return j3;
    }

    public float parseFloat() {
        return parseFloat(0, length());
    }

    public float parseFloat(int i, int i2) {
        return Float.parseFloat(toString(i, i2));
    }

    public double parseDouble() {
        return parseDouble(0, length());
    }

    public double parseDouble(int i, int i2) {
        return Double.parseDouble(toString(i, i2));
    }

    /* renamed from: of */
    public static AsciiString m4005of(CharSequence charSequence) {
        return charSequence.getClass() == AsciiString.class ? (AsciiString) charSequence : new AsciiString(charSequence);
    }

    public static int hashCode(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return charSequence.hashCode();
        }
        return PlatformDependent.hashCodeAscii(charSequence);
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return contains(charSequence, charSequence2, DefaultCharEqualityComparator.INSTANCE);
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return contains(charSequence, charSequence2, AsciiCaseInsensitiveCharEqualityComparator.INSTANCE);
    }

    public static boolean contentEqualsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        Class<AsciiString> cls = AsciiString.class;
        boolean z = true;
        if (charSequence == null || charSequence2 == null) {
            if (charSequence != charSequence2) {
                z = false;
            }
            return z;
        } else if (charSequence.getClass() == cls) {
            return ((AsciiString) charSequence).contentEqualsIgnoreCase(charSequence2);
        } else {
            if (charSequence2.getClass() == cls) {
                return ((AsciiString) charSequence2).contentEqualsIgnoreCase(charSequence);
            }
            if (charSequence.length() != charSequence2.length()) {
                return false;
            }
            int i = 0;
            int i2 = 0;
            while (i < charSequence.length()) {
                if (!equalsIgnoreCase(charSequence.charAt(i), charSequence2.charAt(i2))) {
                    return false;
                }
                i++;
                i2++;
            }
            return true;
        }
    }

    public static boolean containsContentEqualsIgnoreCase(Collection<CharSequence> collection, CharSequence charSequence) {
        for (CharSequence contentEqualsIgnoreCase : collection) {
            if (contentEqualsIgnoreCase(charSequence, contentEqualsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAllContentEqualsIgnoreCase(Collection<CharSequence> collection, Collection<CharSequence> collection2) {
        for (CharSequence containsContentEqualsIgnoreCase : collection2) {
            if (!containsContentEqualsIgnoreCase(collection, containsContentEqualsIgnoreCase)) {
                return false;
            }
        }
        return true;
    }

    public static boolean contentEquals(CharSequence charSequence, CharSequence charSequence2) {
        Class<AsciiString> cls = AsciiString.class;
        boolean z = true;
        if (charSequence == null || charSequence2 == null) {
            if (charSequence != charSequence2) {
                z = false;
            }
            return z;
        } else if (charSequence.getClass() == cls) {
            return ((AsciiString) charSequence).contentEquals(charSequence2);
        } else {
            if (charSequence2.getClass() == cls) {
                return ((AsciiString) charSequence2).contentEquals(charSequence);
            }
            if (charSequence.length() != charSequence2.length()) {
                return false;
            }
            for (int i = 0; i < charSequence.length(); i++) {
                if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static AsciiString[] toAsciiStringArray(String[] strArr) {
        AsciiString[] asciiStringArr = new AsciiString[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            asciiStringArr[i] = new AsciiString((CharSequence) strArr[i]);
        }
        return asciiStringArr;
    }

    private static boolean contains(CharSequence charSequence, CharSequence charSequence2, CharEqualityComparator charEqualityComparator) {
        if (!(charSequence == null || charSequence2 == null || charSequence.length() < charSequence2.length())) {
            if (charSequence2.length() == 0) {
                return true;
            }
            int i = 0;
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                if (charEqualityComparator.equals(charSequence2.charAt(i), charSequence.charAt(i2))) {
                    i++;
                    if (i == charSequence2.length()) {
                        return true;
                    }
                } else if (charSequence.length() - i2 < charSequence2.length()) {
                    return false;
                } else {
                    i = 0;
                }
            }
        }
        return false;
    }

    private static boolean regionMatchesCharSequences(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3, CharEqualityComparator charEqualityComparator) {
        if (i < 0 || i3 > charSequence.length() - i || i2 < 0 || i3 > charSequence2.length() - i2) {
            return false;
        }
        int i4 = i3 + i;
        while (i < i4) {
            int i5 = i + 1;
            int i6 = i2 + 1;
            if (!charEqualityComparator.equals(charSequence.charAt(i), charSequence2.charAt(i2))) {
                return false;
            }
            i = i5;
            i2 = i6;
        }
        return true;
    }

    public static boolean regionMatches(CharSequence charSequence, boolean z, int i, CharSequence charSequence2, int i2, int i3) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return ((String) charSequence).regionMatches(z, i, (String) charSequence2, i2, i3);
        } else if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).regionMatches(z, i, charSequence2, i2, i3);
        } else {
            return regionMatchesCharSequences(charSequence, i, charSequence2, i2, i3, z ? GeneralCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
        }
    }

    public static boolean regionMatchesAscii(CharSequence charSequence, boolean z, int i, CharSequence charSequence2, int i2, int i3) {
        if (charSequence == null || charSequence2 == null) {
            return false;
        }
        if (!z && (charSequence instanceof String) && (charSequence2 instanceof String)) {
            return ((String) charSequence).regionMatches(false, i, (String) charSequence2, i2, i3);
        } else if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).regionMatches(z, i, charSequence2, i2, i3);
        } else {
            return regionMatchesCharSequences(charSequence, i, charSequence2, i2, i3, z ? AsciiCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
        }
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (!(charSequence == null || charSequence2 == null)) {
            if (i < 0) {
                i = 0;
            }
            int length2 = charSequence2.length();
            int length3 = (charSequence.length() - length2) + 1;
            if (i > length3) {
                return -1;
            }
            if (length2 == 0) {
                return i;
            }
            while (i < length3) {
                if (regionMatches(charSequence, true, i, charSequence2, 0, length2)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static int indexOfIgnoreCaseAscii(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (!(charSequence == null || charSequence2 == null)) {
            if (i < 0) {
                i = 0;
            }
            int length2 = charSequence2.length();
            int length3 = (charSequence.length() - length2) + 1;
            if (i > length3) {
                return -1;
            }
            if (length2 == 0) {
                return i;
            }
            while (i < length3) {
                if (regionMatchesAscii(charSequence, true, i, charSequence2, 0, length2)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static int indexOf(CharSequence charSequence, char c, int i) {
        if (charSequence instanceof String) {
            return ((String) charSequence).indexOf(c, i);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).indexOf(c, i);
        }
        if (charSequence == null) {
            return -1;
        }
        int length2 = charSequence.length();
        if (i < 0) {
            i = 0;
        }
        while (i < length2) {
            if (charSequence.charAt(i) == c) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static boolean equalsIgnoreCase(byte b, byte b2) {
        return b == b2 || toLowerCase(b) == toLowerCase(b2);
    }

    /* access modifiers changed from: private */
    public static boolean equalsIgnoreCase(char c, char c2) {
        return c == c2 || toLowerCase(c) == toLowerCase(c2);
    }

    private static byte toLowerCase(byte b) {
        return isUpperCase(b) ? (byte) (b + 32) : b;
    }

    private static char toLowerCase(char c) {
        return isUpperCase(c) ? (char) (c + ' ') : c;
    }

    private static byte toUpperCase(byte b) {
        return isLowerCase(b) ? (byte) (b - 32) : b;
    }
}
