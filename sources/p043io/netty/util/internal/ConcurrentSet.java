package p043io.netty.util.internal;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

/* renamed from: io.netty.util.internal.ConcurrentSet */
public final class ConcurrentSet<E> extends AbstractSet<E> implements Serializable {
    private static final long serialVersionUID = -6761513279741915432L;
    private final ConcurrentMap<E, Boolean> map = PlatformDependent.newConcurrentHashMap();

    public int size() {
        return this.map.size();
    }

    public boolean contains(Object obj) {
        return this.map.containsKey(obj);
    }

    public boolean add(E e) {
        return this.map.putIfAbsent(e, Boolean.TRUE) == null;
    }

    public boolean remove(Object obj) {
        return this.map.remove(obj) != null;
    }

    public void clear() {
        this.map.clear();
    }

    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }
}
