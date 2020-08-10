package com.rometools.rome.feed.impl;

import com.mobiroller.constants.Constants;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToStringBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ToStringBean.class);
    private static final Object[] NO_PARAMS = new Object[0];
    private static final ThreadLocal<Stack<String[]>> PREFIX_TL = new ThreadLocal<>();
    private static final long serialVersionUID = 1;
    private final Class<?> beanClass;
    private final Object obj;

    protected ToStringBean(Class<?> cls) {
        this.beanClass = cls;
        this.obj = this;
    }

    public ToStringBean(Class<?> cls, Object obj2) {
        this.beanClass = cls;
        this.obj = obj2;
    }

    public String toString() {
        boolean z;
        String[] strArr;
        String str;
        Stack stack = (Stack) PREFIX_TL.get();
        if (stack == null) {
            stack = new Stack();
            PREFIX_TL.set(stack);
            z = true;
        } else {
            z = false;
        }
        if (stack.isEmpty()) {
            strArr = null;
        } else {
            strArr = (String[]) stack.peek();
        }
        if (strArr == null) {
            String name = this.obj.getClass().getName();
            str = name.substring(name.lastIndexOf(".") + 1);
        } else {
            String str2 = strArr[0];
            strArr[1] = str2;
            str = str2;
        }
        String toStringBean = toString(str);
        if (z) {
            PREFIX_TL.remove();
        }
        return toStringBean;
    }

    private String toString(String str) {
        StringBuffer stringBuffer = new StringBuffer(128);
        try {
            for (PropertyDescriptor propertyDescriptor : BeanIntrospector.getPropertyDescriptorsWithGetters(this.beanClass)) {
                String name = propertyDescriptor.getName();
                Object invoke = propertyDescriptor.getReadMethod().invoke(this.obj, NO_PARAMS);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(".");
                sb.append(name);
                printProperty(stringBuffer, sb.toString(), invoke);
            }
        } catch (Exception e) {
            LOG.error("Error while generating toString", (Throwable) e);
            stringBuffer.append(String.format("\n\nEXCEPTION: Could not complete %s.toString(): %s\n", new Object[]{this.obj.getClass(), e.getMessage()}));
        }
        return stringBuffer.toString();
    }

    private void printProperty(StringBuffer stringBuffer, String str, Object obj2) {
        String str2;
        String str3;
        if (obj2 == null) {
            stringBuffer.append(str);
            stringBuffer.append("=null\n");
        } else if (obj2.getClass().isArray()) {
            printArrayProperty(stringBuffer, str, obj2);
        } else {
            boolean z = obj2 instanceof Map;
            String str4 = "null";
            String str5 = "%s[%s]";
            String str6 = "=[]\n";
            String str7 = Constants.NEW_LINE;
            String str8 = "=";
            if (z) {
                Set<Entry> entrySet = ((Map) obj2).entrySet();
                if (entrySet.isEmpty()) {
                    stringBuffer.append(str);
                    stringBuffer.append(str6);
                    return;
                }
                for (Entry entry : entrySet) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    String format = String.format(str5, new Object[]{str, key});
                    String[] strArr = new String[2];
                    strArr[0] = format;
                    Stack stack = (Stack) PREFIX_TL.get();
                    stack.push(strArr);
                    if (value == null) {
                        str3 = str4;
                    } else {
                        str3 = value.toString();
                    }
                    stack.pop();
                    if (strArr[1] == null) {
                        stringBuffer.append(format);
                        stringBuffer.append(str8);
                        stringBuffer.append(str3);
                        stringBuffer.append(str7);
                    } else {
                        stringBuffer.append(str3);
                    }
                }
            } else if (obj2 instanceof Collection) {
                Collection collection = (Collection) obj2;
                if (collection.isEmpty()) {
                    stringBuffer.append(str);
                    stringBuffer.append(str6);
                    return;
                }
                int i = 0;
                for (Object next : collection) {
                    int i2 = i + 1;
                    String format2 = String.format(str5, new Object[]{str, Integer.valueOf(i)});
                    String[] strArr2 = new String[2];
                    strArr2[0] = format2;
                    Stack stack2 = (Stack) PREFIX_TL.get();
                    stack2.push(strArr2);
                    if (next == null) {
                        str2 = str4;
                    } else {
                        str2 = next.toString();
                    }
                    stack2.pop();
                    if (strArr2[1] == null) {
                        stringBuffer.append(format2);
                        stringBuffer.append(str8);
                        stringBuffer.append(str2);
                        stringBuffer.append(str7);
                    } else {
                        stringBuffer.append(str2);
                    }
                    i = i2;
                }
            } else {
                String[] strArr3 = new String[2];
                strArr3[0] = str;
                Stack stack3 = (Stack) PREFIX_TL.get();
                stack3.push(strArr3);
                String obj3 = obj2.toString();
                stack3.pop();
                if (strArr3[1] == null) {
                    stringBuffer.append(str);
                    stringBuffer.append(str8);
                    stringBuffer.append(obj3);
                    stringBuffer.append(str7);
                    return;
                }
                stringBuffer.append(obj3);
            }
        }
    }

    private void printArrayProperty(StringBuffer stringBuffer, String str, Object obj2) {
        int length = Array.getLength(obj2);
        for (int i = 0; i < length; i++) {
            printProperty(stringBuffer, String.format("%s[%s]", new Object[]{str, Integer.valueOf(i)}), Array.get(obj2, i));
        }
    }
}
