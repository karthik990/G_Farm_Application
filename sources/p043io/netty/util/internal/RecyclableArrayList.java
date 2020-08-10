package p043io.netty.util.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;

/* renamed from: io.netty.util.internal.RecyclableArrayList */
public final class RecyclableArrayList extends ArrayList<Object> {
    private static final int DEFAULT_INITIAL_CAPACITY = 8;
    private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler<RecyclableArrayList>() {
        /* access modifiers changed from: protected */
        public RecyclableArrayList newObject(Handle<RecyclableArrayList> handle) {
            return new RecyclableArrayList((Handle) handle);
        }
    };
    private static final long serialVersionUID = -8605125654176467947L;
    private final Handle<RecyclableArrayList> handle;
    private boolean insertSinceRecycled;

    public static RecyclableArrayList newInstance() {
        return newInstance(8);
    }

    public static RecyclableArrayList newInstance(int i) {
        RecyclableArrayList recyclableArrayList = (RecyclableArrayList) RECYCLER.get();
        recyclableArrayList.ensureCapacity(i);
        return recyclableArrayList;
    }

    private RecyclableArrayList(Handle<RecyclableArrayList> handle2) {
        this(handle2, 8);
    }

    private RecyclableArrayList(Handle<RecyclableArrayList> handle2, int i) {
        super(i);
        this.handle = handle2;
    }

    public boolean addAll(Collection<?> collection) {
        checkNullElements(collection);
        if (!super.addAll(collection)) {
            return false;
        }
        this.insertSinceRecycled = true;
        return true;
    }

    public boolean addAll(int i, Collection<?> collection) {
        checkNullElements(collection);
        if (!super.addAll(i, collection)) {
            return false;
        }
        this.insertSinceRecycled = true;
        return true;
    }

    private static void checkNullElements(Collection<?> collection) {
        String str = "c contains null values";
        if (!(collection instanceof RandomAccess) || !(collection instanceof List)) {
            for (Object obj : collection) {
                if (obj == null) {
                    throw new IllegalArgumentException(str);
                }
            }
            return;
        }
        List list = (List) collection;
        int size = list.size();
        int i = 0;
        while (i < size) {
            if (list.get(i) != null) {
                i++;
            } else {
                throw new IllegalArgumentException(str);
            }
        }
    }

    public boolean add(Object obj) {
        if (obj == null) {
            throw new NullPointerException("element");
        } else if (!super.add(obj)) {
            return false;
        } else {
            this.insertSinceRecycled = true;
            return true;
        }
    }

    public void add(int i, Object obj) {
        if (obj != null) {
            super.add(i, obj);
            this.insertSinceRecycled = true;
            return;
        }
        throw new NullPointerException("element");
    }

    public Object set(int i, Object obj) {
        if (obj != null) {
            Object obj2 = super.set(i, obj);
            this.insertSinceRecycled = true;
            return obj2;
        }
        throw new NullPointerException("element");
    }

    public boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    public boolean recycle() {
        clear();
        this.insertSinceRecycled = false;
        this.handle.recycle(this);
        return true;
    }
}
