package p043io.netty.handler.codec;

import java.text.ParseException;
import java.util.Date;
import p043io.netty.util.AsciiString;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.codec.CharSequenceValueConverter */
public class CharSequenceValueConverter implements ValueConverter<CharSequence> {
    public static final CharSequenceValueConverter INSTANCE = new CharSequenceValueConverter();

    public CharSequence convertObject(Object obj) {
        if (obj instanceof CharSequence) {
            return (CharSequence) obj;
        }
        return obj.toString();
    }

    public CharSequence convertInt(int i) {
        return String.valueOf(i);
    }

    public CharSequence convertLong(long j) {
        return String.valueOf(j);
    }

    public CharSequence convertDouble(double d) {
        return String.valueOf(d);
    }

    public CharSequence convertChar(char c) {
        return String.valueOf(c);
    }

    public CharSequence convertBoolean(boolean z) {
        return String.valueOf(z);
    }

    public CharSequence convertFloat(float f) {
        return String.valueOf(f);
    }

    public boolean convertToBoolean(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseBoolean();
        }
        return Boolean.parseBoolean(charSequence.toString());
    }

    public CharSequence convertByte(byte b) {
        return String.valueOf(b);
    }

    public byte convertToByte(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).byteAt(0);
        }
        return Byte.parseByte(charSequence.toString());
    }

    public char convertToChar(CharSequence charSequence) {
        return charSequence.charAt(0);
    }

    public CharSequence convertShort(short s) {
        return String.valueOf(s);
    }

    public short convertToShort(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseShort();
        }
        return Short.parseShort(charSequence.toString());
    }

    public int convertToInt(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseInt();
        }
        return Integer.parseInt(charSequence.toString());
    }

    public long convertToLong(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseLong();
        }
        return Long.parseLong(charSequence.toString());
    }

    public CharSequence convertTimeMillis(long j) {
        return String.valueOf(j);
    }

    public long convertToTimeMillis(CharSequence charSequence) {
        Date parseHttpDate = DateFormatter.parseHttpDate(charSequence);
        if (parseHttpDate != null) {
            return parseHttpDate.getTime();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("header can't be parsed into a Date: ");
        sb.append(charSequence);
        PlatformDependent.throwException(new ParseException(sb.toString(), 0));
        return 0;
    }

    public float convertToFloat(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseFloat();
        }
        return Float.parseFloat(charSequence.toString());
    }

    public double convertToDouble(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseDouble();
        }
        return Double.parseDouble(charSequence.toString());
    }
}
