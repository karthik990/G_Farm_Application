package p043io.netty.util.internal;

import java.util.Arrays;

/* renamed from: io.netty.util.internal.AppendableCharSequence */
public final class AppendableCharSequence implements CharSequence, Appendable {
    private char[] chars;
    private int pos;

    public AppendableCharSequence(int i) {
        if (i >= 1) {
            this.chars = new char[i];
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("length: ");
        sb.append(i);
        sb.append(" (length: >= 1)");
        throw new IllegalArgumentException(sb.toString());
    }

    private AppendableCharSequence(char[] cArr) {
        if (cArr.length >= 1) {
            this.chars = cArr;
            this.pos = cArr.length;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("length: ");
        sb.append(cArr.length);
        sb.append(" (length: >= 1)");
        throw new IllegalArgumentException(sb.toString());
    }

    public int length() {
        return this.pos;
    }

    public char charAt(int i) {
        if (i <= this.pos) {
            return this.chars[i];
        }
        throw new IndexOutOfBoundsException();
    }

    public char charAtUnsafe(int i) {
        return this.chars[i];
    }

    public AppendableCharSequence subSequence(int i, int i2) {
        return new AppendableCharSequence(Arrays.copyOfRange(this.chars, i, i2));
    }

    public AppendableCharSequence append(char c) {
        try {
            char[] cArr = this.chars;
            int i = this.pos;
            this.pos = i + 1;
            cArr[i] = c;
        } catch (IndexOutOfBoundsException unused) {
            expand();
            this.chars[this.pos - 1] = c;
        }
        return this;
    }

    public AppendableCharSequence append(CharSequence charSequence) {
        return append(charSequence, 0, charSequence.length());
    }

    public AppendableCharSequence append(CharSequence charSequence, int i, int i2) {
        if (charSequence.length() >= i2) {
            int i3 = i2 - i;
            char[] cArr = this.chars;
            int length = cArr.length;
            int i4 = this.pos;
            if (i3 > length - i4) {
                this.chars = expand(cArr, i4 + i3, i4);
            }
            if (charSequence instanceof AppendableCharSequence) {
                System.arraycopy(((AppendableCharSequence) charSequence).chars, i, this.chars, this.pos, i3);
                this.pos += i3;
                return this;
            }
            while (i < i2) {
                char[] cArr2 = this.chars;
                int i5 = this.pos;
                this.pos = i5 + 1;
                cArr2[i5] = charSequence.charAt(i);
                i++;
            }
            return this;
        }
        throw new IndexOutOfBoundsException();
    }

    public void reset() {
        this.pos = 0;
    }

    public String toString() {
        return new String(this.chars, 0, this.pos);
    }

    public String substring(int i, int i2) {
        int i3 = i2 - i;
        int i4 = this.pos;
        if (i <= i4 && i3 <= i4) {
            return new String(this.chars, i, i3);
        }
        throw new IndexOutOfBoundsException();
    }

    public String subStringUnsafe(int i, int i2) {
        return new String(this.chars, i, i2 - i);
    }

    private void expand() {
        char[] cArr = this.chars;
        int length = cArr.length << 1;
        if (length >= 0) {
            this.chars = new char[length];
            System.arraycopy(cArr, 0, this.chars, 0, cArr.length);
            return;
        }
        throw new IllegalStateException();
    }

    private static char[] expand(char[] cArr, int i, int i2) {
        int length = cArr.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        char[] cArr2 = new char[length];
        System.arraycopy(cArr, 0, cArr2, 0, i2);
        return cArr2;
    }
}
