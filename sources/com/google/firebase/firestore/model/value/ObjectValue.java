package com.google.firebase.firestore.model.value;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ObjectValue extends FieldValue {
    private static final ObjectValue EMPTY_INSTANCE = new ObjectValue(Builder.emptyMap(Util.comparator()));
    private final ImmutableSortedMap<String, FieldValue> internalValue;

    public int typeOrder() {
        return 9;
    }

    public static ObjectValue fromMap(Map<String, FieldValue> map) {
        return fromImmutableMap(Builder.fromMap(map, Util.comparator()));
    }

    public static ObjectValue fromImmutableMap(ImmutableSortedMap<String, FieldValue> immutableSortedMap) {
        if (immutableSortedMap.isEmpty()) {
            return EMPTY_INSTANCE;
        }
        return new ObjectValue(immutableSortedMap);
    }

    public static ObjectValue emptyObject() {
        return EMPTY_INSTANCE;
    }

    private ObjectValue(ImmutableSortedMap<String, FieldValue> immutableSortedMap) {
        this.internalValue = immutableSortedMap;
    }

    public Map<String, Object> value() {
        HashMap hashMap = new HashMap();
        Iterator it = this.internalValue.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((String) entry.getKey(), ((FieldValue) entry.getValue()).value());
        }
        return hashMap;
    }

    public Map<String, Object> value(FieldValueOptions fieldValueOptions) {
        HashMap hashMap = new HashMap();
        Iterator it = this.internalValue.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((String) entry.getKey(), ((FieldValue) entry.getValue()).value(fieldValueOptions));
        }
        return hashMap;
    }

    public ImmutableSortedMap<String, FieldValue> getInternalValue() {
        return this.internalValue;
    }

    public String toString() {
        return this.internalValue.toString();
    }

    public int hashCode() {
        return this.internalValue.hashCode();
    }

    public int compareTo(FieldValue fieldValue) {
        if (!(fieldValue instanceof ObjectValue)) {
            return defaultCompareTo(fieldValue);
        }
        ObjectValue objectValue = (ObjectValue) fieldValue;
        Iterator it = this.internalValue.iterator();
        Iterator it2 = objectValue.internalValue.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Entry entry = (Entry) it.next();
            Entry entry2 = (Entry) it2.next();
            int compareTo = ((String) entry.getKey()).compareTo((String) entry2.getKey());
            if (compareTo != 0) {
                return compareTo;
            }
            int compareTo2 = ((FieldValue) entry.getValue()).compareTo((FieldValue) entry2.getValue());
            if (compareTo2 != 0) {
                return compareTo2;
            }
        }
        return Util.compareBooleans(it.hasNext(), it2.hasNext());
    }

    public boolean equals(Object obj) {
        return (obj instanceof ObjectValue) && this.internalValue.equals(((ObjectValue) obj).internalValue);
    }

    public ObjectValue set(FieldPath fieldPath, FieldValue fieldValue) {
        ObjectValue objectValue;
        Assert.hardAssert(!fieldPath.isEmpty(), "Cannot set field for empty path on ObjectValue", new Object[0]);
        String firstSegment = fieldPath.getFirstSegment();
        if (fieldPath.length() == 1) {
            return setChild(firstSegment, fieldValue);
        }
        FieldValue fieldValue2 = (FieldValue) this.internalValue.get(firstSegment);
        if (fieldValue2 instanceof ObjectValue) {
            objectValue = (ObjectValue) fieldValue2;
        } else {
            objectValue = emptyObject();
        }
        return setChild(firstSegment, objectValue.set((FieldPath) fieldPath.popFirst(), fieldValue));
    }

    public ObjectValue delete(FieldPath fieldPath) {
        Assert.hardAssert(!fieldPath.isEmpty(), "Cannot delete field for empty path on ObjectValue", new Object[0]);
        String firstSegment = fieldPath.getFirstSegment();
        if (fieldPath.length() == 1) {
            return fromImmutableMap(this.internalValue.remove(firstSegment));
        }
        FieldValue fieldValue = (FieldValue) this.internalValue.get(firstSegment);
        return fieldValue instanceof ObjectValue ? setChild(firstSegment, ((ObjectValue) fieldValue).delete((FieldPath) fieldPath.popFirst())) : this;
    }

    @Nullable
    public FieldValue get(FieldPath fieldPath) {
        FieldValue fieldValue = this;
        for (int i = 0; i < fieldPath.length(); i++) {
            if (!(fieldValue instanceof ObjectValue)) {
                return null;
            }
            fieldValue = (FieldValue) ((ObjectValue) fieldValue).internalValue.get(fieldPath.getSegment(i));
        }
        return fieldValue;
    }

    private ObjectValue setChild(String str, FieldValue fieldValue) {
        return fromImmutableMap(this.internalValue.insert(str, fieldValue));
    }
}
