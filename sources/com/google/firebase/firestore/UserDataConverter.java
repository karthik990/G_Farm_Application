package com.google.firebase.firestore;

import com.google.common.base.Preconditions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.core.UserData.ParseAccumulator;
import com.google.firebase.firestore.core.UserData.ParseContext;
import com.google.firebase.firestore.core.UserData.ParsedSetData;
import com.google.firebase.firestore.core.UserData.ParsedUpdateData;
import com.google.firebase.firestore.core.UserData.Source;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Remove;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Union;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.BlobValue;
import com.google.firebase.firestore.model.value.BooleanValue;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.GeoPointValue;
import com.google.firebase.firestore.model.value.IntegerValue;
import com.google.firebase.firestore.model.value.NullValue;
import com.google.firebase.firestore.model.value.NumberValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.model.value.StringValue;
import com.google.firebase.firestore.model.value.TimestampValue;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.CustomClassMapper;
import com.google.firebase.firestore.util.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class UserDataConverter {
    private final DatabaseId databaseId;

    public UserDataConverter(DatabaseId databaseId2) {
        this.databaseId = databaseId2;
    }

    public ParsedSetData parseSetData(Object obj) {
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.Set);
        return parseAccumulator.toSetData(convertAndParseDocumentData(obj, parseAccumulator.rootContext()));
    }

    public ParsedSetData parseMergeData(Object obj, @Nullable FieldMask fieldMask) {
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.MergeSet);
        ObjectValue convertAndParseDocumentData = convertAndParseDocumentData(obj, parseAccumulator.rootContext());
        if (fieldMask == null) {
            return parseAccumulator.toMergeData(convertAndParseDocumentData);
        }
        for (FieldPath fieldPath : fieldMask.getMask()) {
            if (!parseAccumulator.contains(fieldPath)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Field '");
                sb.append(fieldPath.toString());
                sb.append("' is specified in your field mask but not in your input data.");
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return parseAccumulator.toMergeData(convertAndParseDocumentData, fieldMask);
    }

    public ParsedUpdateData parseUpdateData(Map<String, Object> map) {
        Preconditions.checkNotNull(map, "Provided update data must not be null.");
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.Update);
        ParseContext rootContext = parseAccumulator.rootContext();
        ObjectValue emptyObject = ObjectValue.emptyObject();
        for (Entry entry : map.entrySet()) {
            FieldPath internalPath = FieldPath.fromDotSeparatedPath((String) entry.getKey()).getInternalPath();
            Object value = entry.getValue();
            if (value instanceof DeleteFieldValue) {
                rootContext.addToFieldMask(internalPath);
            } else {
                FieldValue convertAndParseFieldData = convertAndParseFieldData(value, rootContext.childContext(internalPath));
                if (convertAndParseFieldData != null) {
                    rootContext.addToFieldMask(internalPath);
                    emptyObject = emptyObject.set(internalPath, convertAndParseFieldData);
                }
            }
        }
        return parseAccumulator.toUpdateData(emptyObject);
    }

    public ParsedUpdateData parseUpdateData(List<Object> list) {
        FieldPath fieldPath;
        Assert.hardAssert(list.size() % 2 == 0, "Expected fieldAndValues to contain an even number of elements", new Object[0]);
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.Update);
        ParseContext rootContext = parseAccumulator.rootContext();
        ObjectValue emptyObject = ObjectValue.emptyObject();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            Object next2 = it.next();
            boolean z = next instanceof String;
            Assert.hardAssert(z || (next instanceof FieldPath), "Expected argument to be String or FieldPath.", new Object[0]);
            if (z) {
                fieldPath = FieldPath.fromDotSeparatedPath((String) next).getInternalPath();
            } else {
                fieldPath = ((FieldPath) next).getInternalPath();
            }
            if (next2 instanceof DeleteFieldValue) {
                rootContext.addToFieldMask(fieldPath);
            } else {
                FieldValue convertAndParseFieldData = convertAndParseFieldData(next2, rootContext.childContext(fieldPath));
                if (convertAndParseFieldData != null) {
                    rootContext.addToFieldMask(fieldPath);
                    emptyObject = emptyObject.set(fieldPath, convertAndParseFieldData);
                }
            }
        }
        return parseAccumulator.toUpdateData(emptyObject);
    }

    public FieldValue parseQueryValue(Object obj) {
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.Argument);
        FieldValue convertAndParseFieldData = convertAndParseFieldData(obj, parseAccumulator.rootContext());
        Assert.hardAssert(convertAndParseFieldData != null, "Parsed data should not be null.", new Object[0]);
        Assert.hardAssert(parseAccumulator.getFieldTransforms().isEmpty(), "Field transforms should have been disallowed.", new Object[0]);
        return convertAndParseFieldData;
    }

    private FieldValue convertAndParseFieldData(Object obj, ParseContext parseContext) {
        return parseData(CustomClassMapper.convertToPlainJavaTypes(obj), parseContext);
    }

    private ObjectValue convertAndParseDocumentData(Object obj, ParseContext parseContext) {
        String str = "Invalid data. Data must be a Map<String, Object> or a suitable POJO object, but it was ";
        if (!obj.getClass().isArray()) {
            FieldValue parseData = parseData(CustomClassMapper.convertToPlainJavaTypes(obj), parseContext);
            if (parseData instanceof ObjectValue) {
                return (ObjectValue) parseData;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("of type: ");
            sb.append(Util.typeName(obj));
            throw new IllegalArgumentException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("an array");
        throw new IllegalArgumentException(sb2.toString());
    }

    @Nullable
    private FieldValue parseData(Object obj, ParseContext parseContext) {
        if (obj instanceof Map) {
            return parseMap((Map) obj, parseContext);
        }
        if (obj instanceof FieldValue) {
            parseSentinelFieldValue((FieldValue) obj, parseContext);
            return null;
        }
        if (parseContext.getPath() != null) {
            parseContext.addToFieldMask(parseContext.getPath());
        }
        if (!(obj instanceof List)) {
            return parseScalarValue(obj, parseContext);
        }
        if (!parseContext.isArrayElement()) {
            return parseList((List) obj, parseContext);
        }
        throw parseContext.createError("Nested arrays are not supported");
    }

    private <K, V> ObjectValue parseMap(Map<K, V> map, ParseContext parseContext) {
        HashMap hashMap = new HashMap();
        if (map.isEmpty()) {
            if (parseContext.getPath() != null && !parseContext.getPath().isEmpty()) {
                parseContext.addToFieldMask(parseContext.getPath());
            }
            return ObjectValue.emptyObject();
        }
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() instanceof String) {
                String str = (String) entry.getKey();
                FieldValue parseData = parseData(entry.getValue(), parseContext.childContext(str));
                if (parseData != null) {
                    hashMap.put(str, parseData);
                }
            } else {
                throw parseContext.createError(String.format("Non-String Map key (%s) is not allowed", new Object[]{entry.getValue()}));
            }
        }
        return ObjectValue.fromMap(hashMap);
    }

    private <T> ArrayValue parseList(List<T> list, ParseContext parseContext) {
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        for (T parseData : list) {
            Object parseData2 = parseData(parseData, parseContext.childContext(i));
            if (parseData2 == null) {
                parseData2 = NullValue.nullValue();
            }
            arrayList.add(parseData2);
            i++;
        }
        return ArrayValue.fromList(arrayList);
    }

    private void parseSentinelFieldValue(FieldValue fieldValue, ParseContext parseContext) {
        boolean z = true;
        if (!parseContext.isWrite()) {
            throw parseContext.createError(String.format("%s() can only be used with set() and update()", new Object[]{fieldValue.getMethodName()}));
        } else if (parseContext.getPath() == null) {
            throw parseContext.createError(String.format("%s() is not currently supported inside arrays", new Object[]{fieldValue.getMethodName()}));
        } else if (fieldValue instanceof DeleteFieldValue) {
            if (parseContext.getDataSource() == Source.MergeSet) {
                parseContext.addToFieldMask(parseContext.getPath());
            } else if (parseContext.getDataSource() == Source.Update) {
                if (parseContext.getPath().length() <= 0) {
                    z = false;
                }
                Assert.hardAssert(z, "FieldValue.delete() at the top level should have already been handled.", new Object[0]);
                throw parseContext.createError("FieldValue.delete() can only appear at the top level of your update data");
            } else {
                throw parseContext.createError("FieldValue.delete() can only be used with update() and set() with SetOptions.merge()");
            }
        } else if (fieldValue instanceof ServerTimestampFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), ServerTimestampOperation.getInstance());
        } else if (fieldValue instanceof ArrayUnionFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), new Union(parseArrayTransformElements(((ArrayUnionFieldValue) fieldValue).getElements())));
        } else if (fieldValue instanceof ArrayRemoveFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), new Remove(parseArrayTransformElements(((ArrayRemoveFieldValue) fieldValue).getElements())));
        } else if (fieldValue instanceof NumericIncrementFieldValue) {
            parseContext.addToFieldTransforms(parseContext.getPath(), new NumericIncrementTransformOperation((NumberValue) parseQueryValue(((NumericIncrementFieldValue) fieldValue).getOperand())));
        } else {
            throw Assert.fail("Unknown FieldValue type: %s", Util.typeName(fieldValue));
        }
    }

    @Nullable
    private FieldValue parseScalarValue(Object obj, ParseContext parseContext) {
        if (obj == null) {
            return NullValue.nullValue();
        }
        if (obj instanceof Integer) {
            return IntegerValue.valueOf(Long.valueOf(((Integer) obj).longValue()));
        }
        if (obj instanceof Long) {
            return IntegerValue.valueOf((Long) obj);
        }
        if (obj instanceof Float) {
            return DoubleValue.valueOf(Double.valueOf(((Float) obj).doubleValue()));
        }
        if (obj instanceof Double) {
            return DoubleValue.valueOf((Double) obj);
        }
        if (obj instanceof Boolean) {
            return BooleanValue.valueOf((Boolean) obj);
        }
        if (obj instanceof String) {
            return StringValue.valueOf((String) obj);
        }
        if (obj instanceof Date) {
            return TimestampValue.valueOf(new Timestamp((Date) obj));
        }
        if (obj instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) obj;
            return TimestampValue.valueOf(new Timestamp(timestamp.getSeconds(), (timestamp.getNanoseconds() / 1000) * 1000));
        } else if (obj instanceof GeoPoint) {
            return GeoPointValue.valueOf((GeoPoint) obj);
        } else {
            if (obj instanceof Blob) {
                return BlobValue.valueOf((Blob) obj);
            }
            if (obj instanceof DocumentReference) {
                DocumentReference documentReference = (DocumentReference) obj;
                if (documentReference.getFirestore() != null) {
                    DatabaseId databaseId2 = documentReference.getFirestore().getDatabaseId();
                    if (!databaseId2.equals(this.databaseId)) {
                        throw parseContext.createError(String.format("Document reference is for database %s/%s but should be for database %s/%s", new Object[]{databaseId2.getProjectId(), databaseId2.getDatabaseId(), this.databaseId.getProjectId(), this.databaseId.getDatabaseId()}));
                    }
                }
                return ReferenceValue.valueOf(this.databaseId, documentReference.getKey());
            } else if (obj.getClass().isArray()) {
                throw parseContext.createError("Arrays are not supported; use a List instead");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unsupported type: ");
                sb.append(Util.typeName(obj));
                throw parseContext.createError(sb.toString());
            }
        }
    }

    private List<FieldValue> parseArrayTransformElements(List<Object> list) {
        ParseAccumulator parseAccumulator = new ParseAccumulator(Source.Argument);
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(convertAndParseFieldData(list.get(i), parseAccumulator.rootContext().childContext(i)));
        }
        return arrayList;
    }
}
