package org.objectweb.asm;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.fasterxml.jackson.core.JsonPointer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
    public static final int ARRAY = 9;
    public static final int BOOLEAN = 1;
    public static final Type BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
    public static final int BYTE = 3;
    public static final Type BYTE_TYPE = new Type(3, null, 1107297537, 1);
    public static final int CHAR = 2;
    public static final Type CHAR_TYPE = new Type(2, null, 1124075009, 1);
    public static final int DOUBLE = 8;
    public static final Type DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
    public static final int FLOAT = 6;
    public static final Type FLOAT_TYPE = new Type(6, null, 1174536705, 1);
    public static final int INT = 5;
    public static final Type INT_TYPE = new Type(5, null, 1224736769, 1);
    public static final int LONG = 7;
    public static final Type LONG_TYPE = new Type(7, null, 1241579778, 1);
    public static final int METHOD = 11;
    public static final int OBJECT = 10;
    public static final int SHORT = 4;
    public static final Type SHORT_TYPE = new Type(4, null, 1392510721, 1);
    public static final int VOID = 0;
    public static final Type VOID_TYPE = new Type(0, null, 1443168256, 1);

    /* renamed from: a */
    private final int f4740a;

    /* renamed from: b */
    private final char[] f4741b;

    /* renamed from: c */
    private final int f4742c;

    /* renamed from: d */
    private final int f4743d;

    static {
        _clinit_();
    }

    private Type(int i, char[] cArr, int i2, int i3) {
        this.f4740a = i;
        this.f4741b = cArr;
        this.f4742c = i2;
        this.f4743d = i3;
    }

    static /* synthetic */ void _clinit_() {
    }

    /* renamed from: a */
    private static Type m4245a(char[] cArr, int i) {
        int i2;
        char c = cArr[i];
        if (c == 'F') {
            return FLOAT_TYPE;
        }
        if (c == 'L') {
            int i3 = 1;
            while (cArr[i + i3] != ';') {
                i3++;
            }
            return new Type(10, cArr, i + 1, i3 - 1);
        } else if (c == 'S') {
            return SHORT_TYPE;
        } else {
            if (c == 'V') {
                return VOID_TYPE;
            }
            if (c == 'I') {
                return INT_TYPE;
            }
            if (c == 'J') {
                return LONG_TYPE;
            }
            if (c == 'Z') {
                return BOOLEAN_TYPE;
            }
            if (c != '[') {
                switch (c) {
                    case 'B':
                        return BYTE_TYPE;
                    case 'C':
                        return CHAR_TYPE;
                    case 'D':
                        return DOUBLE_TYPE;
                    default:
                        return new Type(11, cArr, i, cArr.length - i);
                }
            } else {
                int i4 = 1;
                while (true) {
                    i2 = i + i4;
                    if (cArr[i2] != '[') {
                        break;
                    }
                    i4++;
                }
                if (cArr[i2] == 'L') {
                    do {
                        i4++;
                    } while (cArr[i + i4] != ';');
                }
                return new Type(9, cArr, i, i4 + 1);
            }
        }
    }

    /* renamed from: a */
    private void m4246a(StringBuffer stringBuffer) {
        char c;
        char[] cArr = this.f4741b;
        if (cArr == null) {
            c = (char) ((this.f4742c & ViewCompat.MEASURED_STATE_MASK) >>> 24);
        } else if (this.f4740a == 10) {
            stringBuffer.append('L');
            stringBuffer.append(this.f4741b, this.f4742c, this.f4743d);
            c = ';';
        } else {
            stringBuffer.append(cArr, this.f4742c, this.f4743d);
            return;
        }
        stringBuffer.append(c);
    }

    /* renamed from: a */
    private static void m4247a(StringBuffer stringBuffer, Class cls) {
        while (!cls.isPrimitive()) {
            if (cls.isArray()) {
                stringBuffer.append('[');
                cls = cls.getComponentType();
            } else {
                stringBuffer.append('L');
                String name = cls.getName();
                int length = name.length();
                for (int i = 0; i < length; i++) {
                    char charAt = name.charAt(i);
                    if (charAt == '.') {
                        charAt = JsonPointer.SEPARATOR;
                    }
                    stringBuffer.append(charAt);
                }
                stringBuffer.append(';');
                return;
            }
        }
        char c = cls == Integer.TYPE ? 'I' : cls == Void.TYPE ? 'V' : cls == Boolean.TYPE ? 'Z' : cls == Byte.TYPE ? 'B' : cls == Character.TYPE ? 'C' : cls == Short.TYPE ? 'S' : cls == Double.TYPE ? 'D' : cls == Float.TYPE ? 'F' : 'J';
        stringBuffer.append(c);
    }

    public static Type[] getArgumentTypes(String str) {
        char[] charArray = str.toCharArray();
        int i = 1;
        int i2 = 1;
        int i3 = 0;
        while (true) {
            int i4 = i2 + 1;
            char c = charArray[i2];
            if (c == ')') {
                break;
            } else if (c == 'L') {
                while (true) {
                    i2 = i4 + 1;
                    if (charArray[i4] == ';') {
                        break;
                    }
                    i4 = i2;
                }
                i3++;
            } else {
                if (c != '[') {
                    i3++;
                }
                i2 = i4;
            }
        }
        Type[] typeArr = new Type[i3];
        int i5 = 0;
        while (charArray[i] != ')') {
            typeArr[i5] = m4245a(charArray, i);
            i += typeArr[i5].f4743d + (typeArr[i5].f4740a == 10 ? 2 : 0);
            i5++;
        }
        return typeArr;
    }

    public static Type[] getArgumentTypes(Method method) {
        Class[] parameterTypes = method.getParameterTypes();
        Type[] typeArr = new Type[parameterTypes.length];
        for (int length = parameterTypes.length - 1; length >= 0; length--) {
            typeArr[length] = getType(parameterTypes[length]);
        }
        return typeArr;
    }

    public static int getArgumentsAndReturnSizes(String str) {
        int i;
        char charAt;
        int i2 = 1;
        int i3 = 1;
        int i4 = 1;
        while (true) {
            i = i3 + 1;
            char charAt2 = str.charAt(i3);
            if (charAt2 == ')') {
                break;
            } else if (charAt2 == 'L') {
                while (true) {
                    i3 = i + 1;
                    if (str.charAt(i) == ';') {
                        break;
                    }
                    i = i3;
                }
                i4++;
            } else {
                if (charAt2 == '[') {
                    while (true) {
                        charAt = str.charAt(i);
                        if (charAt != '[') {
                            break;
                        }
                        i++;
                    }
                    if (charAt == 'D' || charAt == 'J') {
                        i4--;
                    }
                } else {
                    i4 = (charAt2 == 'D' || charAt2 == 'J') ? i4 + 2 : i4 + 1;
                }
                i3 = i;
            }
        }
        char charAt3 = str.charAt(i);
        int i5 = i4 << 2;
        if (charAt3 == 'V') {
            i2 = 0;
        } else if (charAt3 == 'D' || charAt3 == 'J') {
            i2 = 2;
        }
        return i5 | i2;
    }

    public static String getConstructorDescriptor(Constructor constructor) {
        Class[] parameterTypes = constructor.getParameterTypes();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Class a : parameterTypes) {
            m4247a(stringBuffer, a);
        }
        stringBuffer.append(")V");
        return stringBuffer.toString();
    }

    public static String getDescriptor(Class cls) {
        StringBuffer stringBuffer = new StringBuffer();
        m4247a(stringBuffer, cls);
        return stringBuffer.toString();
    }

    public static String getInternalName(Class cls) {
        return cls.getName().replace('.', JsonPointer.SEPARATOR);
    }

    public static String getMethodDescriptor(Method method) {
        Class[] parameterTypes = method.getParameterTypes();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Class a : parameterTypes) {
            m4247a(stringBuffer, a);
        }
        stringBuffer.append(')');
        m4247a(stringBuffer, method.getReturnType());
        return stringBuffer.toString();
    }

    public static String getMethodDescriptor(Type type, Type... typeArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Type a : typeArr) {
            a.m4246a(stringBuffer);
        }
        stringBuffer.append(')');
        type.m4246a(stringBuffer);
        return stringBuffer.toString();
    }

    public static Type getMethodType(String str) {
        return m4245a(str.toCharArray(), 0);
    }

    public static Type getMethodType(Type type, Type... typeArr) {
        return getType(getMethodDescriptor(type, typeArr));
    }

    public static Type getObjectType(String str) {
        char[] charArray = str.toCharArray();
        return new Type(charArray[0] == '[' ? 9 : 10, charArray, 0, charArray.length);
    }

    public static Type getReturnType(String str) {
        return m4245a(str.toCharArray(), str.indexOf(41) + 1);
    }

    public static Type getReturnType(Method method) {
        return getType(method.getReturnType());
    }

    public static Type getType(Class cls) {
        return cls.isPrimitive() ? cls == Integer.TYPE ? INT_TYPE : cls == Void.TYPE ? VOID_TYPE : cls == Boolean.TYPE ? BOOLEAN_TYPE : cls == Byte.TYPE ? BYTE_TYPE : cls == Character.TYPE ? CHAR_TYPE : cls == Short.TYPE ? SHORT_TYPE : cls == Double.TYPE ? DOUBLE_TYPE : cls == Float.TYPE ? FLOAT_TYPE : LONG_TYPE : getType(getDescriptor(cls));
    }

    public static Type getType(String str) {
        return m4245a(str.toCharArray(), 0);
    }

    public static Type getType(Constructor constructor) {
        return getType(getConstructorDescriptor(constructor));
    }

    public static Type getType(Method method) {
        return getType(getMethodDescriptor(method));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Type)) {
            return false;
        }
        Type type = (Type) obj;
        int i = this.f4740a;
        if (i != type.f4740a) {
            return false;
        }
        if (i >= 9) {
            int i2 = this.f4743d;
            if (i2 != type.f4743d) {
                return false;
            }
            int i3 = this.f4742c;
            int i4 = type.f4742c;
            int i5 = i2 + i3;
            while (i3 < i5) {
                if (this.f4741b[i3] != type.f4741b[i4]) {
                    return false;
                }
                i3++;
                i4++;
            }
        }
        return true;
    }

    public Type[] getArgumentTypes() {
        return getArgumentTypes(getDescriptor());
    }

    public int getArgumentsAndReturnSizes() {
        return getArgumentsAndReturnSizes(getDescriptor());
    }

    public String getClassName() {
        switch (this.f4740a) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "char";
            case 3:
                return "byte";
            case 4:
                return "short";
            case 5:
                return "int";
            case 6:
                return "float";
            case 7:
                return "long";
            case 8:
                return "double";
            case 9:
                StringBuffer stringBuffer = new StringBuffer(getElementType().getClassName());
                for (int dimensions = getDimensions(); dimensions > 0; dimensions--) {
                    stringBuffer.append("[]");
                }
                return stringBuffer.toString();
            case 10:
                return new String(this.f4741b, this.f4742c, this.f4743d).replace(JsonPointer.SEPARATOR, '.');
            default:
                return null;
        }
    }

    public String getDescriptor() {
        StringBuffer stringBuffer = new StringBuffer();
        m4246a(stringBuffer);
        return stringBuffer.toString();
    }

    public int getDimensions() {
        int i = 1;
        while (this.f4741b[this.f4742c + i] == '[') {
            i++;
        }
        return i;
    }

    public Type getElementType() {
        return m4245a(this.f4741b, this.f4742c + getDimensions());
    }

    public String getInternalName() {
        return new String(this.f4741b, this.f4742c, this.f4743d);
    }

    public int getOpcode(int i) {
        int i2 = 4;
        if (i == 46 || i == 79) {
            if (this.f4741b == null) {
                i2 = (this.f4742c & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
            }
            return i + i2;
        }
        if (this.f4741b == null) {
            i2 = (this.f4742c & 16711680) >> 16;
        }
        return i + i2;
    }

    public Type getReturnType() {
        return getReturnType(getDescriptor());
    }

    public int getSize() {
        if (this.f4741b == null) {
            return this.f4742c & 255;
        }
        return 1;
    }

    public int getSort() {
        return this.f4740a;
    }

    public int hashCode() {
        int i = this.f4740a;
        int i2 = i * 13;
        if (i >= 9) {
            int i3 = this.f4742c;
            int i4 = this.f4743d + i3;
            while (i3 < i4) {
                i2 = (i2 + this.f4741b[i3]) * 17;
                i3++;
            }
        }
        return i2;
    }

    public String toString() {
        return getDescriptor();
    }
}
