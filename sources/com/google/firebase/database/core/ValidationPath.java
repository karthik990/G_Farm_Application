package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ValidationPath {
    public static final int MAX_PATH_DEPTH = 32;
    public static final int MAX_PATH_LENGTH_BYTES = 768;
    private int byteLength;
    private final List<String> parts = new ArrayList();

    private ValidationPath(Path path) throws DatabaseException {
        this.byteLength = 0;
        Iterator it = path.iterator();
        while (it.hasNext()) {
            this.parts.add(((ChildKey) it.next()).asString());
        }
        this.byteLength = Math.max(1, this.parts.size());
        for (int i = 0; i < this.parts.size(); i++) {
            this.byteLength += utf8Bytes((CharSequence) this.parts.get(i));
        }
        checkValid();
    }

    public static void validateWithObject(Path path, Object obj) throws DatabaseException {
        new ValidationPath(path).withObject(obj);
    }

    private void withObject(Object obj) throws DatabaseException {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            for (String str : map.keySet()) {
                if (!str.startsWith(".")) {
                    push(str);
                    withObject(map.get(str));
                    pop();
                }
            }
            return;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                push(Integer.toString(i));
                withObject(list.get(i));
                pop();
            }
        }
    }

    private void push(String str) throws DatabaseException {
        if (this.parts.size() > 0) {
            this.byteLength++;
        }
        this.parts.add(str);
        this.byteLength += utf8Bytes(str);
        checkValid();
    }

    private String pop() {
        List<String> list = this.parts;
        String str = (String) list.remove(list.size() - 1);
        this.byteLength -= utf8Bytes(str);
        if (this.parts.size() > 0) {
            this.byteLength--;
        }
        return str;
    }

    private void checkValid() throws DatabaseException {
        if (this.byteLength > 768) {
            StringBuilder sb = new StringBuilder();
            sb.append("Data has a key path longer than 768 bytes (");
            sb.append(this.byteLength);
            sb.append(").");
            throw new DatabaseException(sb.toString());
        } else if (this.parts.size() > 32) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Path specified exceeds the maximum depth that can be written (32) or object contains a cycle ");
            sb2.append(toErrorString());
            throw new DatabaseException(sb2.toString());
        }
    }

    private String toErrorString() {
        if (this.parts.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("in path '");
        sb.append(joinStringList("/", this.parts));
        sb.append("'");
        return sb.toString();
    }

    private static String joinStringList(String str, List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append((String) list.get(i));
        }
        return sb.toString();
    }

    private static int utf8Bytes(CharSequence charSequence) {
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt <= 127) {
                i2++;
            } else if (charAt <= 2047) {
                i2 += 2;
            } else if (Character.isHighSurrogate(charAt)) {
                i2 += 4;
                i++;
            } else {
                i2 += 3;
            }
            i++;
        }
        return i2;
    }
}
