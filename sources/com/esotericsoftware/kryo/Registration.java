package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import org.objenesis.instantiator.ObjectInstantiator;

public class Registration {

    /* renamed from: id */
    private final int f263id;
    private ObjectInstantiator instantiator;
    private Serializer serializer;
    private final Class type;

    public Registration(Class cls, Serializer serializer2, int i) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (serializer2 != null) {
            this.type = cls;
            this.serializer = serializer2;
            this.f263id = i;
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public Class getType() {
        return this.type;
    }

    public int getId() {
        return this.f263id;
    }

    public Serializer getSerializer() {
        return this.serializer;
    }

    public void setSerializer(Serializer serializer2) {
        if (serializer2 != null) {
            this.serializer = serializer2;
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Update registered serializer: ");
                sb.append(this.type.getName());
                sb.append(" (");
                sb.append(serializer2.getClass().getName());
                sb.append(")");
                Log.trace("kryo", sb.toString());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("serializer cannot be null.");
    }

    public ObjectInstantiator getInstantiator() {
        return this.instantiator;
    }

    public void setInstantiator(ObjectInstantiator objectInstantiator) {
        if (objectInstantiator != null) {
            this.instantiator = objectInstantiator;
            return;
        }
        throw new IllegalArgumentException("instantiator cannot be null.");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.f263id);
        sb.append(", ");
        sb.append(Util.className(this.type));
        sb.append("]");
        return sb.toString();
    }
}
