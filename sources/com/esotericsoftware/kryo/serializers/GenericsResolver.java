package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.minlog.Log;
import java.util.Iterator;
import java.util.LinkedList;

public final class GenericsResolver {
    private LinkedList<Generics> stack = new LinkedList<>();

    /* access modifiers changed from: 0000 */
    public Class getConcreteClass(String str) {
        Iterator it = this.stack.iterator();
        while (it.hasNext()) {
            Class concreteClass = ((Generics) it.next()).getConcreteClass(str);
            if (concreteClass != null) {
                return concreteClass;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public boolean isSet() {
        return !this.stack.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public void pushScope(Class cls, Generics generics) {
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Settting a new generics scope for class ");
            sb.append(cls.getName());
            sb.append(": ");
            sb.append(generics);
            Log.trace("generics", sb.toString());
        }
        this.stack.addFirst(generics);
    }

    /* access modifiers changed from: 0000 */
    public void popScope() {
        this.stack.removeFirst();
    }
}
