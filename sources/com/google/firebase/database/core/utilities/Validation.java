package com.google.firebase.database.core.utilities;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.ServerValues;
import com.google.firebase.database.core.ValidationPath;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Validation {
    private static final Pattern INVALID_KEY_REGEX = Pattern.compile("[\\[\\]\\.#\\$\\/\\u0000-\\u001F\\u007F]");
    private static final Pattern INVALID_PATH_REGEX = Pattern.compile("[\\[\\]\\.#$]");

    private static boolean isValidPathString(String str) {
        return !INVALID_PATH_REGEX.matcher(str).find();
    }

    public static void validatePathString(String str) throws DatabaseException {
        if (!isValidPathString(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Firebase Database path: ");
            sb.append(str);
            sb.append(". Firebase Database paths must not contain '.', '#', '$', '[', or ']'");
            throw new DatabaseException(sb.toString());
        }
    }

    public static void validateRootPathString(String str) throws DatabaseException {
        if (str.startsWith(".info")) {
            validatePathString(str.substring(5));
        } else if (str.startsWith("/.info")) {
            validatePathString(str.substring(6));
        } else {
            validatePathString(str);
        }
    }

    private static boolean isWritableKey(String str) {
        return str != null && str.length() > 0 && (str.equals(".value") || str.equals(".priority") || (!str.startsWith(".") && !INVALID_KEY_REGEX.matcher(str).find()));
    }

    private static boolean isValidKey(String str) {
        return str.equals(".info") || !INVALID_KEY_REGEX.matcher(str).find();
    }

    public static void validateNullableKey(String str) throws DatabaseException {
        if (str != null && !isValidKey(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid key: ");
            sb.append(str);
            sb.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
            throw new DatabaseException(sb.toString());
        }
    }

    private static void validateDoubleValue(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new DatabaseException("Invalid value: Value cannot be NaN, Inf or -Inf.");
        }
    }

    private static boolean isWritablePath(Path path) {
        ChildKey front = path.getFront();
        return front == null || !front.asString().startsWith(".");
    }

    public static void validateWritableObject(Object obj) {
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (!map.containsKey(ServerValues.NAME_SUBKEY_SERVERVALUE)) {
                for (Entry entry : map.entrySet()) {
                    validateWritableKey((String) entry.getKey());
                    validateWritableObject(entry.getValue());
                }
            }
        } else if (obj instanceof List) {
            for (Object validateWritableObject : (List) obj) {
                validateWritableObject(validateWritableObject);
            }
        } else if ((obj instanceof Double) || (obj instanceof Float)) {
            validateDoubleValue(((Double) obj).doubleValue());
        }
    }

    public static void validateWritableKey(String str) throws DatabaseException {
        if (!isWritableKey(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid key: ");
            sb.append(str);
            sb.append(". Keys must not contain '/', '.', '#', '$', '[', or ']'");
            throw new DatabaseException(sb.toString());
        }
    }

    public static void validateWritablePath(Path path) throws DatabaseException {
        if (!isWritablePath(path)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid write location: ");
            sb.append(path.toString());
            throw new DatabaseException(sb.toString());
        }
    }

    public static Map<Path, Node> parseAndValidateUpdate(Path path, Map<String, Object> map) throws DatabaseException {
        String str;
        Path path2;
        String asString;
        Node node;
        TreeMap treeMap = new TreeMap();
        Iterator it = map.entrySet().iterator();
        while (true) {
            str = "Path '";
            if (it.hasNext()) {
                Entry entry = (Entry) it.next();
                path2 = new Path((String) entry.getKey());
                Object value = entry.getValue();
                ValidationPath.validateWithObject(path.child(path2), value);
                asString = !path2.isEmpty() ? path2.getBack().asString() : "";
                if (asString.equals(ServerValues.NAME_SUBKEY_SERVERVALUE) || asString.equals(".value")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(path2);
                    sb.append("' contains disallowed child name: ");
                    sb.append(asString);
                } else {
                    if (asString.equals(".priority")) {
                        node = PriorityUtilities.parsePriority(path2, value);
                    } else {
                        node = NodeUtilities.NodeFromJSON(value);
                    }
                    validateWritableObject(value);
                    treeMap.put(path2, node);
                }
            } else {
                Path path3 = null;
                for (Path path4 : treeMap.keySet()) {
                    Utilities.hardAssert(path3 == null || path3.compareTo(path4) < 0);
                    if (path3 == null || !path3.contains(path4)) {
                        path3 = path4;
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(path3);
                        sb2.append("' is an ancestor of '");
                        sb2.append(path4);
                        sb2.append("' in an update.");
                        throw new DatabaseException(sb2.toString());
                    }
                }
                return treeMap;
            }
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append(path2);
        sb3.append("' contains disallowed child name: ");
        sb3.append(asString);
        throw new DatabaseException(sb3.toString());
    }
}
