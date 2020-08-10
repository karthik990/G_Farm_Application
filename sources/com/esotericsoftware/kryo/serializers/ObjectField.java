package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.reflectasm.FieldAccess;

class ObjectField extends CachedField {
    final FieldSerializer fieldSerializer;
    public Class[] generics;
    final Kryo kryo;
    final Class type;

    static final class ObjectBooleanField extends ObjectField {
        public ObjectBooleanField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Boolean.valueOf(this.field.getBoolean(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeBoolean(this.field.getBoolean(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setBoolean(obj, input.readBoolean());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setBoolean(obj2, this.field.getBoolean(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectByteField extends ObjectField {
        public ObjectByteField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Byte.valueOf(this.field.getByte(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeByte(this.field.getByte(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setByte(obj, input.readByte());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setByte(obj2, this.field.getByte(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectCharField extends ObjectField {
        public ObjectCharField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Character.valueOf(this.field.getChar(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeChar(this.field.getChar(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setChar(obj, input.readChar());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setChar(obj2, this.field.getChar(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectDoubleField extends ObjectField {
        public ObjectDoubleField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Double.valueOf(this.field.getDouble(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeDouble(this.field.getDouble(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setDouble(obj, input.readDouble());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setDouble(obj2, this.field.getDouble(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectFloatField extends ObjectField {
        public ObjectFloatField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Float.valueOf(this.field.getFloat(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeFloat(this.field.getFloat(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setFloat(obj, input.readFloat());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setFloat(obj2, this.field.getFloat(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectIntField extends ObjectField {
        public ObjectIntField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Integer.valueOf(this.field.getInt(obj));
        }

        public void write(Output output, Object obj) {
            try {
                if (this.varIntsEnabled) {
                    output.writeInt(this.field.getInt(obj), false);
                } else {
                    output.writeInt(this.field.getInt(obj));
                }
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                if (this.varIntsEnabled) {
                    this.field.setInt(obj, input.readInt(false));
                } else {
                    this.field.setInt(obj, input.readInt());
                }
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setInt(obj2, this.field.getInt(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectLongField extends ObjectField {
        public ObjectLongField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Long.valueOf(this.field.getLong(obj));
        }

        public void write(Output output, Object obj) {
            try {
                if (this.varIntsEnabled) {
                    output.writeLong(this.field.getLong(obj), false);
                } else {
                    output.writeLong(this.field.getLong(obj));
                }
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                if (this.varIntsEnabled) {
                    this.field.setLong(obj, input.readLong(false));
                } else {
                    this.field.setLong(obj, input.readLong());
                }
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setLong(obj2, this.field.getLong(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    static final class ObjectShortField extends ObjectField {
        public ObjectShortField(FieldSerializer fieldSerializer) {
            super(fieldSerializer);
        }

        public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
            return Short.valueOf(this.field.getShort(obj));
        }

        public void write(Output output, Object obj) {
            try {
                output.writeShort(this.field.getShort(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void read(Input input, Object obj) {
            try {
                this.field.setShort(obj, input.readShort());
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }

        public void copy(Object obj, Object obj2) {
            try {
                this.field.setShort(obj2, this.field.getShort(obj));
            } catch (Exception e) {
                KryoException kryoException = new KryoException((Throwable) e);
                StringBuilder sb = new StringBuilder();
                sb.append(this);
                sb.append(" (");
                sb.append(this.type.getName());
                sb.append(")");
                kryoException.addTrace(sb.toString());
                throw kryoException;
            }
        }
    }

    ObjectField(FieldSerializer fieldSerializer2) {
        this.fieldSerializer = fieldSerializer2;
        this.kryo = fieldSerializer2.kryo;
        this.type = fieldSerializer2.type;
    }

    public Object getField(Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.field.get(obj);
    }

    public void setField(Object obj, Object obj2) throws IllegalArgumentException, IllegalAccessException {
        this.field.set(obj, obj2);
    }

    public void write(Output output, Object obj) {
        String str = ")";
        String str2 = " (";
        try {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Write field: ");
                sb.append(this);
                sb.append(str2);
                sb.append(obj.getClass().getName());
                sb.append(") pos=");
                sb.append(output.position());
                Log.trace("kryo", sb.toString());
            }
            Object field = getField(obj);
            Serializer serializer = this.serializer;
            if (this.valueClass != null) {
                if (serializer == null) {
                    serializer = this.kryo.getSerializer(this.valueClass);
                    this.serializer = serializer;
                }
                serializer.setGenerics(this.kryo, this.generics);
                if (this.canBeNull) {
                    this.kryo.writeObjectOrNull(output, field, serializer);
                } else if (field != null) {
                    this.kryo.writeObject(output, field, serializer);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Field value is null but canBeNull is false: ");
                    sb2.append(this);
                    sb2.append(str2);
                    sb2.append(obj.getClass().getName());
                    sb2.append(str);
                    throw new KryoException(sb2.toString());
                }
            } else if (field == null) {
                this.kryo.writeClass(output, null);
            } else {
                Registration writeClass = this.kryo.writeClass(output, field.getClass());
                if (serializer == null) {
                    serializer = writeClass.getSerializer();
                }
                serializer.setGenerics(this.kryo, this.generics);
                this.kryo.writeObject(output, field, serializer);
            }
        } catch (IllegalAccessException e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Error accessing field: ");
            sb3.append(this);
            sb3.append(str2);
            sb3.append(obj.getClass().getName());
            sb3.append(str);
            throw new KryoException(sb3.toString(), e);
        } catch (KryoException e2) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this);
            sb4.append(str2);
            sb4.append(obj.getClass().getName());
            sb4.append(str);
            e2.addTrace(sb4.toString());
            throw e2;
        } catch (RuntimeException e3) {
            KryoException kryoException = new KryoException((Throwable) e3);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this);
            sb5.append(str2);
            sb5.append(obj.getClass().getName());
            sb5.append(str);
            kryoException.addTrace(sb5.toString());
            throw kryoException;
        }
    }

    public void read(Input input, Object obj) {
        Object obj2;
        String str = ")";
        String str2 = " (";
        try {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Read field: ");
                sb.append(this);
                sb.append(str2);
                sb.append(this.type.getName());
                sb.append(") pos=");
                sb.append(input.position());
                Log.trace("kryo", sb.toString());
            }
            Class cls = this.valueClass;
            Serializer serializer = this.serializer;
            if (cls == null) {
                Registration readClass = this.kryo.readClass(input);
                if (readClass == null) {
                    obj2 = null;
                } else {
                    if (serializer == null) {
                        serializer = readClass.getSerializer();
                    }
                    serializer.setGenerics(this.kryo, this.generics);
                    obj2 = this.kryo.readObject(input, readClass.getType(), serializer);
                }
            } else {
                if (serializer == null) {
                    serializer = this.kryo.getSerializer(this.valueClass);
                    this.serializer = serializer;
                }
                serializer.setGenerics(this.kryo, this.generics);
                if (this.canBeNull) {
                    obj2 = this.kryo.readObjectOrNull(input, cls, serializer);
                } else {
                    obj2 = this.kryo.readObject(input, cls, serializer);
                }
            }
            setField(obj, obj2);
        } catch (IllegalAccessException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Error accessing field: ");
            sb2.append(this);
            sb2.append(str2);
            sb2.append(this.type.getName());
            sb2.append(str);
            throw new KryoException(sb2.toString(), e);
        } catch (KryoException e2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this);
            sb3.append(str2);
            sb3.append(this.type.getName());
            sb3.append(str);
            e2.addTrace(sb3.toString());
            throw e2;
        } catch (RuntimeException e3) {
            KryoException kryoException = new KryoException((Throwable) e3);
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this);
            sb4.append(str2);
            sb4.append(this.type.getName());
            sb4.append(str);
            kryoException.addTrace(sb4.toString());
            throw kryoException;
        }
    }

    public void copy(Object obj, Object obj2) {
        String str = ")";
        String str2 = " (";
        try {
            if (this.accessIndex != -1) {
                FieldAccess fieldAccess = (FieldAccess) this.fieldSerializer.access;
                fieldAccess.set(obj2, this.accessIndex, this.kryo.copy(fieldAccess.get(obj, this.accessIndex)));
                return;
            }
            setField(obj2, this.kryo.copy(getField(obj)));
        } catch (IllegalAccessException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error accessing field: ");
            sb.append(this);
            sb.append(str2);
            sb.append(this.type.getName());
            sb.append(str);
            throw new KryoException(sb.toString(), e);
        } catch (KryoException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this);
            sb2.append(str2);
            sb2.append(this.type.getName());
            sb2.append(str);
            e2.addTrace(sb2.toString());
            throw e2;
        } catch (RuntimeException e3) {
            KryoException kryoException = new KryoException((Throwable) e3);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this);
            sb3.append(str2);
            sb3.append(this.type.getName());
            sb3.append(str);
            kryoException.addTrace(sb3.toString());
            throw kryoException;
        }
    }
}
