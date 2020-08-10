package p043io.netty.handler.codec.string;

import com.mobiroller.constants.Constants;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.util.CharsetUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.handler.codec.string.LineSeparator */
public final class LineSeparator {
    public static final LineSeparator DEFAULT = new LineSeparator(StringUtil.NEWLINE);
    public static final LineSeparator UNIX = new LineSeparator(Constants.NEW_LINE);
    public static final LineSeparator WINDOWS = new LineSeparator("\r\n");
    private final String value;

    public LineSeparator(String str) {
        this.value = (String) ObjectUtil.checkNotNull(str, "lineSeparator");
    }

    public String value() {
        return this.value;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineSeparator)) {
            return false;
        }
        LineSeparator lineSeparator = (LineSeparator) obj;
        String str = this.value;
        String str2 = lineSeparator.value;
        if (str != null) {
            z = str.equals(str2);
        } else if (str2 != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.value;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return ByteBufUtil.hexDump(this.value.getBytes(CharsetUtil.UTF_8));
    }
}
