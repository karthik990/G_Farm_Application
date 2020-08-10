package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.factories.ReflectionSerializerFactory;
import com.esotericsoftware.kryo.serializers.CollectionSerializer.BindCollection;
import com.esotericsoftware.kryo.serializers.FieldSerializer.Bind;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.kryo.serializers.MapSerializer.BindMap;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

final class FieldSerializerAnnotationsUtil {
    public FieldSerializerAnnotationsUtil(FieldSerializer fieldSerializer) {
    }

    public void processAnnotatedFields(FieldSerializer fieldSerializer) {
        Serializer serializer;
        Serializer serializer2;
        Serializer serializer3;
        CachedField[] fields = fieldSerializer.getFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            Field field = fields[i].getField();
            if (field.isAnnotationPresent(Bind.class)) {
                fields[i].setSerializer(ReflectionSerializerFactory.makeSerializer(fieldSerializer.getKryo(), ((Bind) field.getAnnotation(Bind.class)).value(), field.getClass()));
            }
            if (field.isAnnotationPresent(BindCollection.class)) {
                field.isAnnotationPresent(BindMap.class);
            }
            String str = " does not implement it.";
            String str2 = ", because it has a serializer already.";
            String str3 = ".";
            if (field.isAnnotationPresent(BindCollection.class)) {
                if (fields[i].serializer == null) {
                    BindCollection bindCollection = (BindCollection) field.getAnnotation(BindCollection.class);
                    if (Collection.class.isAssignableFrom(fields[i].field.getType())) {
                        Class<Serializer> elementSerializer = bindCollection.elementSerializer();
                        if (elementSerializer == Serializer.class) {
                            elementSerializer = null;
                        }
                        if (elementSerializer == null) {
                            serializer3 = null;
                        } else {
                            serializer3 = ReflectionSerializerFactory.makeSerializer(fieldSerializer.getKryo(), elementSerializer, field.getClass());
                        }
                        boolean elementsCanBeNull = bindCollection.elementsCanBeNull();
                        Class<Object> elementClass = bindCollection.elementClass();
                        if (elementClass == Object.class) {
                            elementClass = null;
                        }
                        CollectionSerializer collectionSerializer = new CollectionSerializer();
                        collectionSerializer.setElementsCanBeNull(elementsCanBeNull);
                        collectionSerializer.setElementClass(elementClass, serializer3);
                        fields[i].setSerializer(collectionSerializer);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("CollectionSerialier.Bind should be used only with fields implementing java.util.Collection, but field ");
                        sb.append(fields[i].getField().getDeclaringClass().getName());
                        sb.append(str3);
                        sb.append(fields[i].getField().getName());
                        sb.append(str);
                        throw new RuntimeException(sb.toString());
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("CollectionSerialier.Bind cannot be used with field ");
                    sb2.append(fields[i].getField().getDeclaringClass().getName());
                    sb2.append(str3);
                    sb2.append(fields[i].getField().getName());
                    sb2.append(str2);
                    throw new RuntimeException(sb2.toString());
                }
            }
            if (field.isAnnotationPresent(BindMap.class)) {
                if (fields[i].serializer == null) {
                    BindMap bindMap = (BindMap) field.getAnnotation(BindMap.class);
                    if (Map.class.isAssignableFrom(fields[i].field.getType())) {
                        Class<Serializer> valueSerializer = bindMap.valueSerializer();
                        Class<Serializer> keySerializer = bindMap.keySerializer();
                        if (valueSerializer == Serializer.class) {
                            valueSerializer = null;
                        }
                        if (keySerializer == Serializer.class) {
                            keySerializer = null;
                        }
                        if (valueSerializer == null) {
                            serializer = null;
                        } else {
                            serializer = ReflectionSerializerFactory.makeSerializer(fieldSerializer.getKryo(), valueSerializer, field.getClass());
                        }
                        if (keySerializer == null) {
                            serializer2 = null;
                        } else {
                            serializer2 = ReflectionSerializerFactory.makeSerializer(fieldSerializer.getKryo(), keySerializer, field.getClass());
                        }
                        boolean valuesCanBeNull = bindMap.valuesCanBeNull();
                        boolean keysCanBeNull = bindMap.keysCanBeNull();
                        Class<Object> keyClass = bindMap.keyClass();
                        Class<Object> valueClass = bindMap.valueClass();
                        if (keyClass == Object.class) {
                            keyClass = null;
                        }
                        if (valueClass == Object.class) {
                            valueClass = null;
                        }
                        MapSerializer mapSerializer = new MapSerializer();
                        mapSerializer.setKeysCanBeNull(keysCanBeNull);
                        mapSerializer.setValuesCanBeNull(valuesCanBeNull);
                        mapSerializer.setKeyClass(keyClass, serializer2);
                        mapSerializer.setValueClass(valueClass, serializer);
                        fields[i].setSerializer(mapSerializer);
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("MapSerialier.Bind should be used only with fields implementing java.util.Map, but field ");
                        sb3.append(fields[i].getField().getDeclaringClass().getName());
                        sb3.append(str3);
                        sb3.append(fields[i].getField().getName());
                        sb3.append(str);
                        throw new RuntimeException(sb3.toString());
                    }
                } else {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("MapSerialier.Bind cannot be used with field ");
                    sb4.append(fields[i].getField().getDeclaringClass().getName());
                    sb4.append(str3);
                    sb4.append(fields[i].getField().getName());
                    sb4.append(str2);
                    throw new RuntimeException(sb4.toString());
                }
            }
        }
    }
}
