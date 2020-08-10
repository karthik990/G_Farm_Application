package org.jdom2.input.sax;

import org.jdom2.Verifier;
import org.jdom2.internal.ArrayCopy;

final class TextBuffer {
    private char[] array = new char[1024];
    private int arraySize = 0;

    TextBuffer() {
    }

    /* access modifiers changed from: 0000 */
    public void append(char[] cArr, int i, int i2) {
        int i3 = this.arraySize;
        int i4 = i2 + i3;
        char[] cArr2 = this.array;
        if (i4 > cArr2.length) {
            this.array = ArrayCopy.copyOf(cArr2, i3 + i2 + (cArr2.length >> 2));
        }
        System.arraycopy(cArr, i, this.array, this.arraySize, i2);
        this.arraySize += i2;
    }

    /* access modifiers changed from: 0000 */
    public void clear() {
        this.arraySize = 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean isAllWhitespace() {
        int i = this.arraySize;
        do {
            i--;
            if (i < 0) {
                return true;
            }
        } while (Verifier.isXMLWhitespace(this.array[i]));
        return false;
    }

    public String toString() {
        int i = this.arraySize;
        if (i == 0) {
            return "";
        }
        return String.valueOf(this.array, 0, i);
    }
}
