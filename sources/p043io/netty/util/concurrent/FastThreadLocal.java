package p043io.netty.util.concurrent;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.util.concurrent.FastThreadLocal */
public class FastThreadLocal<V> {
    private static final int variablesToRemoveIndex = InternalThreadLocalMap.nextVariableIndex();
    private final int index = InternalThreadLocalMap.nextVariableIndex();

    /* access modifiers changed from: protected */
    public V initialValue() throws Exception {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onRemoval(V v) throws Exception {
    }

    public static void removeAll() {
        InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet != null) {
            try {
                Object indexedVariable = ifSet.indexedVariable(variablesToRemoveIndex);
                if (!(indexedVariable == null || indexedVariable == InternalThreadLocalMap.UNSET)) {
                    Set set = (Set) indexedVariable;
                    for (FastThreadLocal remove : (FastThreadLocal[]) set.toArray(new FastThreadLocal[set.size()])) {
                        remove.remove(ifSet);
                    }
                }
            } finally {
                InternalThreadLocalMap.remove();
            }
        }
    }

    public static int size() {
        InternalThreadLocalMap ifSet = InternalThreadLocalMap.getIfSet();
        if (ifSet == null) {
            return 0;
        }
        return ifSet.size();
    }

    public static void destroy() {
        InternalThreadLocalMap.destroy();
    }

    private static void addToVariablesToRemove(InternalThreadLocalMap internalThreadLocalMap, FastThreadLocal<?> fastThreadLocal) {
        Set set;
        Object indexedVariable = internalThreadLocalMap.indexedVariable(variablesToRemoveIndex);
        if (indexedVariable == InternalThreadLocalMap.UNSET || indexedVariable == null) {
            set = Collections.newSetFromMap(new IdentityHashMap());
            internalThreadLocalMap.setIndexedVariable(variablesToRemoveIndex, set);
        } else {
            set = (Set) indexedVariable;
        }
        set.add(fastThreadLocal);
    }

    private static void removeFromVariablesToRemove(InternalThreadLocalMap internalThreadLocalMap, FastThreadLocal<?> fastThreadLocal) {
        Object indexedVariable = internalThreadLocalMap.indexedVariable(variablesToRemoveIndex);
        if (indexedVariable != InternalThreadLocalMap.UNSET && indexedVariable != null) {
            ((Set) indexedVariable).remove(fastThreadLocal);
        }
    }

    public final V get() {
        return get(InternalThreadLocalMap.get());
    }

    public final V get(InternalThreadLocalMap internalThreadLocalMap) {
        V indexedVariable = internalThreadLocalMap.indexedVariable(this.index);
        if (indexedVariable != InternalThreadLocalMap.UNSET) {
            return indexedVariable;
        }
        return initialize(internalThreadLocalMap);
    }

    private V initialize(InternalThreadLocalMap internalThreadLocalMap) {
        V v;
        try {
            v = initialValue();
        } catch (Exception e) {
            PlatformDependent.throwException(e);
            v = null;
        }
        internalThreadLocalMap.setIndexedVariable(this.index, v);
        addToVariablesToRemove(internalThreadLocalMap, this);
        return v;
    }

    public final void set(V v) {
        if (v != InternalThreadLocalMap.UNSET) {
            set(InternalThreadLocalMap.get(), v);
        } else {
            remove();
        }
    }

    public final void set(InternalThreadLocalMap internalThreadLocalMap, V v) {
        if (v == InternalThreadLocalMap.UNSET) {
            remove(internalThreadLocalMap);
        } else if (internalThreadLocalMap.setIndexedVariable(this.index, v)) {
            addToVariablesToRemove(internalThreadLocalMap, this);
        }
    }

    public final boolean isSet() {
        return isSet(InternalThreadLocalMap.getIfSet());
    }

    public final boolean isSet(InternalThreadLocalMap internalThreadLocalMap) {
        return internalThreadLocalMap != null && internalThreadLocalMap.isIndexedVariableSet(this.index);
    }

    public final void remove() {
        remove(InternalThreadLocalMap.getIfSet());
    }

    public final void remove(InternalThreadLocalMap internalThreadLocalMap) {
        if (internalThreadLocalMap != null) {
            Object removeIndexedVariable = internalThreadLocalMap.removeIndexedVariable(this.index);
            removeFromVariablesToRemove(internalThreadLocalMap, this);
            if (removeIndexedVariable != InternalThreadLocalMap.UNSET) {
                try {
                    onRemoval(removeIndexedVariable);
                } catch (Exception e) {
                    PlatformDependent.throwException(e);
                }
            }
        }
    }
}
