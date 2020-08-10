package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;

class AsmCacheFields {

    static final class AsmBooleanField extends AsmCachedField {
        AsmBooleanField() {
        }

        public void write(Output output, Object obj) {
            output.writeBoolean(this.access.getBoolean(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setBoolean(obj, this.accessIndex, input.readBoolean());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setBoolean(obj2, this.accessIndex, this.access.getBoolean(obj, this.accessIndex));
        }
    }

    static final class AsmByteField extends AsmCachedField {
        AsmByteField() {
        }

        public void write(Output output, Object obj) {
            output.writeByte(this.access.getByte(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setByte(obj, this.accessIndex, input.readByte());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setByte(obj2, this.accessIndex, this.access.getByte(obj, this.accessIndex));
        }
    }

    static abstract class AsmCachedField extends CachedField {
        AsmCachedField() {
        }
    }

    static final class AsmCharField extends AsmCachedField {
        AsmCharField() {
        }

        public void write(Output output, Object obj) {
            output.writeChar(this.access.getChar(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setChar(obj, this.accessIndex, input.readChar());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setChar(obj2, this.accessIndex, this.access.getChar(obj, this.accessIndex));
        }
    }

    static final class AsmDoubleField extends AsmCachedField {
        AsmDoubleField() {
        }

        public void write(Output output, Object obj) {
            output.writeDouble(this.access.getDouble(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setDouble(obj, this.accessIndex, input.readDouble());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setDouble(obj2, this.accessIndex, this.access.getDouble(obj, this.accessIndex));
        }
    }

    static final class AsmFloatField extends AsmCachedField {
        AsmFloatField() {
        }

        public void write(Output output, Object obj) {
            output.writeFloat(this.access.getFloat(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setFloat(obj, this.accessIndex, input.readFloat());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setFloat(obj2, this.accessIndex, this.access.getFloat(obj, this.accessIndex));
        }
    }

    static final class AsmIntField extends AsmCachedField {
        AsmIntField() {
        }

        public void write(Output output, Object obj) {
            if (this.varIntsEnabled) {
                output.writeInt(this.access.getInt(obj, this.accessIndex), false);
            } else {
                output.writeInt(this.access.getInt(obj, this.accessIndex));
            }
        }

        public void read(Input input, Object obj) {
            if (this.varIntsEnabled) {
                this.access.setInt(obj, this.accessIndex, input.readInt(false));
            } else {
                this.access.setInt(obj, this.accessIndex, input.readInt());
            }
        }

        public void copy(Object obj, Object obj2) {
            this.access.setInt(obj2, this.accessIndex, this.access.getInt(obj, this.accessIndex));
        }
    }

    static final class AsmLongField extends AsmCachedField {
        AsmLongField() {
        }

        public void write(Output output, Object obj) {
            if (this.varIntsEnabled) {
                output.writeLong(this.access.getLong(obj, this.accessIndex), false);
            } else {
                output.writeLong(this.access.getLong(obj, this.accessIndex));
            }
        }

        public void read(Input input, Object obj) {
            if (this.varIntsEnabled) {
                this.access.setLong(obj, this.accessIndex, input.readLong(false));
            } else {
                this.access.setLong(obj, this.accessIndex, input.readLong());
            }
        }

        public void copy(Object obj, Object obj2) {
            this.access.setLong(obj2, this.accessIndex, this.access.getLong(obj, this.accessIndex));
        }
    }

    static final class AsmObjectField extends ObjectField {
        public AsmObjectField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            if (this.accessIndex != -1) {
                return this.access.get(obj, this.accessIndex);
            }
            throw new KryoException("Unknown acess index");
        }

        public void setField(Object obj, Object obj2) throws IllegalArgumentException, IllegalAccessException {
            if (this.accessIndex != -1) {
                this.access.set(obj, this.accessIndex, obj2);
                return;
            }
            throw new KryoException("Unknown acess index");
        }

        public void copy(Object obj, Object obj2) {
            String str = ")";
            String str2 = " (";
            try {
                if (this.accessIndex != -1) {
                    this.access.set(obj2, this.accessIndex, this.kryo.copy(this.access.get(obj, this.accessIndex)));
                    return;
                }
                throw new KryoException("Unknown acess index");
            } catch (KryoException e) {
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(str2);
                sb.append(this.type.getName());
                sb.append(str);
                e.addTrace(sb.toString());
                throw e;
            } catch (RuntimeException e2) {
                KryoException kryoException = new KryoException((Throwable) e2);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this);
                sb2.append(str2);
                sb2.append(this.type.getName());
                sb2.append(str);
                kryoException.addTrace(sb2.toString());
                throw kryoException;
            }
        }
    }

    static final class AsmShortField extends AsmCachedField {
        AsmShortField() {
        }

        public void write(Output output, Object obj) {
            output.writeShort(this.access.getShort(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.setShort(obj, this.accessIndex, input.readShort());
        }

        public void copy(Object obj, Object obj2) {
            this.access.setShort(obj2, this.accessIndex, this.access.getShort(obj, this.accessIndex));
        }
    }

    static final class AsmStringField extends AsmCachedField {
        AsmStringField() {
        }

        public void write(Output output, Object obj) {
            output.writeString(this.access.getString(obj, this.accessIndex));
        }

        public void read(Input input, Object obj) {
            this.access.set(obj, this.accessIndex, (Object) input.readString());
        }

        public void copy(Object obj, Object obj2) {
            this.access.set(obj2, this.accessIndex, (Object) this.access.getString(obj, this.accessIndex));
        }
    }

    AsmCacheFields() {
    }
}
