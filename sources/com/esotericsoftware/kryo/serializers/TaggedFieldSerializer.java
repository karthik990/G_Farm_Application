package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.InputChunked;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.p035io.OutputChunked;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.minlog.Log;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class TaggedFieldSerializer<T> extends FieldSerializer<T> {
    private static final Comparator<CachedField> TAGGED_VALUE_COMPARATOR = new Comparator<CachedField>() {
        public int compare(CachedField cachedField, CachedField cachedField2) {
            return ((Tag) cachedField.getField().getAnnotation(Tag.class)).value() - ((Tag) cachedField2.getField().getAnnotation(Tag.class)).value();
        }
    };
    private boolean[] annexed;
    private boolean[] deprecated;
    private int[] tags;
    private int writeFieldCount;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Tag {
        boolean annexed() default false;

        int value();
    }

    @Deprecated
    public boolean isIgnoreUnkownTags() {
        return false;
    }

    @Deprecated
    public void setIgnoreUnknownTags(boolean z) {
    }

    public TaggedFieldSerializer(Kryo kryo, Class cls) {
        super(kryo, cls, null, kryo.getTaggedFieldSerializerConfig().clone());
    }

    public void setSkipUnknownTags(boolean z) {
        ((TaggedFieldSerializerConfig) this.config).setSkipUnknownTags(z);
        rebuildCachedFields();
    }

    public boolean isSkipUnknownTags() {
        return ((TaggedFieldSerializerConfig) this.config).isSkipUnknownTags();
    }

    /* access modifiers changed from: protected */
    public void initializeCachedFields() {
        CachedField[] fields = getFields();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            if (fields[i].getField().getAnnotation(Tag.class) == null) {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Ignoring field without tag: ");
                    sb.append(fields[i]);
                    Log.trace("kryo", sb.toString());
                }
                super.removeField(fields[i]);
            }
        }
        CachedField[] fields2 = getFields();
        this.tags = new int[fields2.length];
        this.deprecated = new boolean[fields2.length];
        this.annexed = new boolean[fields2.length];
        this.writeFieldCount = fields2.length;
        Arrays.sort(fields2, TAGGED_VALUE_COMPARATOR);
        int length2 = fields2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            Field field = fields2[i2].getField();
            this.tags[i2] = ((Tag) field.getAnnotation(Tag.class)).value();
            if (i2 > 0) {
                int[] iArr = this.tags;
                int i3 = i2 - 1;
                if (iArr[i2] == iArr[i3]) {
                    throw new KryoException(String.format("The fields [%s] and [%s] both have a Tag value of %d.", new Object[]{field, fields2[i3].getField(), Integer.valueOf(this.tags[i2])}));
                }
            }
            if (field.getAnnotation(Deprecated.class) != null) {
                this.deprecated[i2] = true;
                this.writeFieldCount--;
            }
            if (((Tag) field.getAnnotation(Tag.class)).annexed()) {
                this.annexed[i2] = true;
            }
        }
        this.removedFields.clear();
    }

    public void removeField(String str) {
        super.removeField(str);
        initializeCachedFields();
    }

    public void removeField(CachedField cachedField) {
        super.removeField(cachedField);
        initializeCachedFields();
    }

    public void write(Kryo kryo, Output output, T t) {
        CachedField[] fields = getFields();
        output.writeVarInt(this.writeFieldCount, true);
        int length = fields.length;
        OutputChunked outputChunked = null;
        for (int i = 0; i < length; i++) {
            if (!this.deprecated[i]) {
                output.writeVarInt(this.tags[i], true);
                if (this.annexed[i]) {
                    if (outputChunked == null) {
                        outputChunked = new OutputChunked(output, 1024);
                    }
                    fields[i].write(outputChunked, t);
                    outputChunked.endChunks();
                } else {
                    fields[i].write(output, t);
                }
            }
        }
    }

    public T read(Kryo kryo, Input input, Class<T> cls) {
        CachedField cachedField;
        boolean z;
        T create = create(kryo, input, cls);
        kryo.reference(create);
        int readVarInt = input.readVarInt(true);
        int[] iArr = this.tags;
        CachedField[] fields = getFields();
        InputChunked inputChunked = null;
        for (int i = 0; i < readVarInt; i++) {
            int readVarInt2 = input.readVarInt(true);
            int length = iArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    cachedField = null;
                    z = false;
                    break;
                } else if (iArr[i2] == readVarInt2) {
                    cachedField = fields[i2];
                    z = this.annexed[i2];
                    break;
                } else {
                    i2++;
                }
            }
            if (cachedField == null) {
                if (isSkipUnknownTags()) {
                    if (inputChunked == null) {
                        inputChunked = new InputChunked(input, 1024);
                    }
                    inputChunked.nextChunks();
                    if (Log.TRACE) {
                        Log.trace(String.format("Unknown field tag: %d (%s) encountered. Assuming a future annexed tag with chunked encoding and skipping.", new Object[]{Integer.valueOf(readVarInt2), getType().getName()}));
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown field tag: ");
                    sb.append(readVarInt2);
                    sb.append(" (");
                    sb.append(getType().getName());
                    sb.append(")");
                    throw new KryoException(sb.toString());
                }
            } else if (z) {
                if (inputChunked == null) {
                    inputChunked = new InputChunked(input, 1024);
                }
                cachedField.read(inputChunked, create);
                inputChunked.nextChunks();
            } else {
                cachedField.read(input, create);
            }
        }
        return create;
    }
}
