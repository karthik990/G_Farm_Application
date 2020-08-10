package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ReferenceResolver;
import java.util.ArrayList;

public class MapReferenceResolver implements ReferenceResolver {
    protected Kryo kryo;
    protected final ArrayList readObjects = new ArrayList();
    protected final IdentityObjectIntMap writtenObjects = new IdentityObjectIntMap();

    public void setKryo(Kryo kryo2) {
        this.kryo = kryo2;
    }

    public int addWrittenObject(Object obj) {
        int i = this.writtenObjects.size;
        this.writtenObjects.put(obj, i);
        return i;
    }

    public int getWrittenId(Object obj) {
        return this.writtenObjects.get(obj, -1);
    }

    public int nextReadId(Class cls) {
        int size = this.readObjects.size();
        this.readObjects.add(null);
        return size;
    }

    public void setReadObject(int i, Object obj) {
        this.readObjects.set(i, obj);
    }

    public Object getReadObject(Class cls, int i) {
        return this.readObjects.get(i);
    }

    public void reset() {
        this.readObjects.clear();
        this.writtenObjects.clear();
    }

    public boolean useReferences(Class cls) {
        return !Util.isWrapperClass(cls);
    }
}
