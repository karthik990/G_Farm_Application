package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedFieldNameStrategy;
import com.esotericsoftware.minlog.Log;

public class FieldSerializerConfig implements Cloneable {
    private CachedFieldNameStrategy cachedFieldNameStrategy = CachedFieldNameStrategy.DEFAULT;
    private boolean copyTransient = true;
    private boolean fieldsCanBeNull = true;
    private boolean fixedFieldTypes;
    private boolean ignoreSyntheticFields = true;
    private boolean optimizedGenerics = false;
    private boolean serializeTransient = false;
    private boolean setFieldsAsAccessible = true;
    private boolean useAsm = (true ^ FieldSerializer.unsafeAvailable);

    public FieldSerializerConfig() {
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("useAsm: ");
            sb.append(this.useAsm);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public FieldSerializerConfig clone() {
        try {
            return (FieldSerializerConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFieldsCanBeNull(boolean z) {
        this.fieldsCanBeNull = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setFieldsCanBeNull: ");
            sb.append(z);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    public void setFieldsAsAccessible(boolean z) {
        this.setFieldsAsAccessible = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setFieldsAsAccessible: ");
            sb.append(z);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    public void setIgnoreSyntheticFields(boolean z) {
        this.ignoreSyntheticFields = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setIgnoreSyntheticFields: ");
            sb.append(z);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    public void setFixedFieldTypes(boolean z) {
        this.fixedFieldTypes = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setFixedFieldTypes: ");
            sb.append(z);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    public void setUseAsm(boolean z) {
        this.useAsm = z;
        String str = "kryo.FieldSerializerConfig";
        if (!this.useAsm && !FieldSerializer.unsafeAvailable) {
            this.useAsm = true;
            if (Log.TRACE) {
                Log.trace(str, "sun.misc.Unsafe is unavailable, using ASM.");
            }
        }
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setUseAsm: ");
            sb.append(z);
            Log.trace(str, sb.toString());
        }
    }

    public void setOptimizedGenerics(boolean z) {
        this.optimizedGenerics = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setOptimizedGenerics: ");
            sb.append(z);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }

    public void setCopyTransient(boolean z) {
        this.copyTransient = z;
    }

    public void setSerializeTransient(boolean z) {
        this.serializeTransient = z;
    }

    public boolean isFieldsCanBeNull() {
        return this.fieldsCanBeNull;
    }

    public boolean isSetFieldsAsAccessible() {
        return this.setFieldsAsAccessible;
    }

    public boolean isIgnoreSyntheticFields() {
        return this.ignoreSyntheticFields;
    }

    public boolean isFixedFieldTypes() {
        return this.fixedFieldTypes;
    }

    public boolean isUseAsm() {
        return this.useAsm;
    }

    public boolean isOptimizedGenerics() {
        return this.optimizedGenerics;
    }

    public boolean isCopyTransient() {
        return this.copyTransient;
    }

    public boolean isSerializeTransient() {
        return this.serializeTransient;
    }

    public CachedFieldNameStrategy getCachedFieldNameStrategy() {
        return this.cachedFieldNameStrategy;
    }

    public void setCachedFieldNameStrategy(CachedFieldNameStrategy cachedFieldNameStrategy2) {
        this.cachedFieldNameStrategy = cachedFieldNameStrategy2;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("CachedFieldNameStrategy: ");
            sb.append(cachedFieldNameStrategy2);
            Log.trace("kryo.FieldSerializerConfig", sb.toString());
        }
    }
}
