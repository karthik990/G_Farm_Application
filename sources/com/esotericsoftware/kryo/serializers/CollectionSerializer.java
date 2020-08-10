package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

public class CollectionSerializer extends Serializer<Collection> {
    private Class elementClass;
    private boolean elementsCanBeNull = true;
    private Class genericType;
    private Serializer serializer;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BindCollection {
        Class<?> elementClass() default Object.class;

        Class<? extends Serializer> elementSerializer() default Serializer.class;

        boolean elementsCanBeNull() default true;
    }

    public CollectionSerializer() {
    }

    public CollectionSerializer(Class cls, Serializer serializer2) {
        setElementClass(cls, serializer2);
    }

    public CollectionSerializer(Class cls, Serializer serializer2, boolean z) {
        setElementClass(cls, serializer2);
        this.elementsCanBeNull = z;
    }

    public void setElementsCanBeNull(boolean z) {
        this.elementsCanBeNull = z;
    }

    public void setElementClass(Class cls, Serializer serializer2) {
        this.elementClass = cls;
        this.serializer = serializer2;
    }

    public void setGenerics(Kryo kryo, Class[] clsArr) {
        this.genericType = null;
        if (clsArr != null && clsArr.length > 0 && kryo.isFinal(clsArr[0])) {
            this.genericType = clsArr[0];
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r5v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.esotericsoftware.kryo.Kryo r3, com.esotericsoftware.kryo.p035io.Output r4, java.util.Collection<java.lang.Object> r5) {
        /*
            r2 = this;
            int r0 = r5.size()
            r1 = 1
            r4.writeVarInt(r0, r1)
            com.esotericsoftware.kryo.Serializer r0 = r2.serializer
            java.lang.Class r1 = r2.genericType
            if (r1 == 0) goto L_0x0017
            if (r0 != 0) goto L_0x0014
            com.esotericsoftware.kryo.Serializer r0 = r3.getSerializer(r1)
        L_0x0014:
            r1 = 0
            r2.genericType = r1
        L_0x0017:
            if (r0 == 0) goto L_0x0041
            boolean r1 = r2.elementsCanBeNull
            if (r1 == 0) goto L_0x002f
            java.util.Iterator r5 = r5.iterator()
        L_0x0021:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x0053
            java.lang.Object r1 = r5.next()
            r3.writeObjectOrNull(r4, r1, r0)
            goto L_0x0021
        L_0x002f:
            java.util.Iterator r5 = r5.iterator()
        L_0x0033:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x0053
            java.lang.Object r1 = r5.next()
            r3.writeObject(r4, r1, r0)
            goto L_0x0033
        L_0x0041:
            java.util.Iterator r5 = r5.iterator()
        L_0x0045:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0053
            java.lang.Object r0 = r5.next()
            r3.writeClassAndObject(r4, r0)
            goto L_0x0045
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.CollectionSerializer.write(com.esotericsoftware.kryo.Kryo, com.esotericsoftware.kryo.io.Output, java.util.Collection):void");
    }

    /* access modifiers changed from: protected */
    public Collection create(Kryo kryo, Input input, Class<Collection> cls) {
        return (Collection) kryo.newInstance(cls);
    }

    public Collection read(Kryo kryo, Input input, Class<Collection> cls) {
        Collection create = create(kryo, input, cls);
        kryo.reference(create);
        int readVarInt = input.readVarInt(true);
        if (create instanceof ArrayList) {
            ((ArrayList) create).ensureCapacity(readVarInt);
        }
        Class cls2 = this.elementClass;
        Serializer serializer2 = this.serializer;
        Class cls3 = this.genericType;
        if (cls3 != null) {
            if (serializer2 == null) {
                serializer2 = kryo.getSerializer(cls3);
                cls2 = cls3;
            }
            this.genericType = null;
        }
        int i = 0;
        if (serializer2 == null) {
            while (i < readVarInt) {
                create.add(kryo.readClassAndObject(input));
                i++;
            }
        } else if (this.elementsCanBeNull) {
            while (i < readVarInt) {
                create.add(kryo.readObjectOrNull(input, cls2, serializer2));
                i++;
            }
        } else {
            while (i < readVarInt) {
                create.add(kryo.readObject(input, cls2, serializer2));
                i++;
            }
        }
        return create;
    }

    /* access modifiers changed from: protected */
    public Collection createCopy(Kryo kryo, Collection collection) {
        return (Collection) kryo.newInstance(collection.getClass());
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r4v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Collection copy(com.esotericsoftware.kryo.Kryo r3, java.util.Collection<java.lang.Object> r4) {
        /*
            r2 = this;
            java.util.Collection r0 = r2.createCopy(r3, r4)
            r3.reference(r0)
            java.util.Iterator r4 = r4.iterator()
        L_0x000b:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x001d
            java.lang.Object r1 = r4.next()
            java.lang.Object r1 = r3.copy(r1)
            r0.add(r1)
            goto L_0x000b
        L_0x001d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.CollectionSerializer.copy(com.esotericsoftware.kryo.Kryo, java.util.Collection):java.util.Collection");
    }
}
