package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.minlog.Log;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class VersionFieldSerializer<T> extends FieldSerializer<T> {
    private boolean compatible;
    private int[] fieldVersion;
    private int typeVersion;

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Since {
        int value() default 0;
    }

    public VersionFieldSerializer(Kryo kryo, Class cls) {
        super(kryo, cls);
        this.typeVersion = 0;
        this.compatible = true;
        initializeCachedFields();
    }

    public VersionFieldSerializer(Kryo kryo, Class cls, boolean z) {
        this(kryo, cls);
        this.compatible = z;
    }

    /* access modifiers changed from: protected */
    public void initializeCachedFields() {
        CachedField[] fields = getFields();
        this.fieldVersion = new int[fields.length];
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            Since since = (Since) fields[i].getField().getAnnotation(Since.class);
            if (since != null) {
                this.fieldVersion[i] = since.value();
                this.typeVersion = Math.max(this.fieldVersion[i], this.typeVersion);
            } else {
                this.fieldVersion[i] = 0;
            }
        }
        this.removedFields.clear();
        if (Log.DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("Version for type ");
            sb.append(getType().getName());
            sb.append(" is ");
            sb.append(this.typeVersion);
            Log.debug(sb.toString());
        }
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
        output.writeVarInt(this.typeVersion, true);
        for (CachedField write : fields) {
            write.write(output, t);
        }
    }

    public T read(Kryo kryo, Input input, Class<T> cls) {
        T create = create(kryo, input, cls);
        kryo.reference(create);
        int readVarInt = input.readVarInt(true);
        if (this.compatible || readVarInt == this.typeVersion) {
            CachedField[] fields = getFields();
            int length = fields.length;
            for (int i = 0; i < length; i++) {
                if (this.fieldVersion[i] <= readVarInt) {
                    fields[i].read(input, create);
                } else if (Log.DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Skip field ");
                    sb.append(fields[i].getField().getName());
                    Log.debug(sb.toString());
                }
            }
            return create;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Version not compatible: ");
        sb2.append(readVarInt);
        sb2.append(" <-> ");
        sb2.append(this.typeVersion);
        throw new KryoException(sb2.toString());
    }
}
