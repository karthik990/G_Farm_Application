package p043io.netty.handler.codec;

import java.util.AbstractList;
import java.util.RandomAccess;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.codec.CodecOutputList */
final class CodecOutputList extends AbstractList<Object> implements RandomAccess {
    private static final Recycler<CodecOutputList> RECYCLER = new Recycler<CodecOutputList>() {
        /* access modifiers changed from: protected */
        public CodecOutputList newObject(Handle<CodecOutputList> handle) {
            return new CodecOutputList(handle);
        }
    };
    private Object[] array;
    private final Handle<CodecOutputList> handle;
    private boolean insertSinceRecycled;
    private int size;

    static CodecOutputList newInstance() {
        return (CodecOutputList) RECYCLER.get();
    }

    private CodecOutputList(Handle<CodecOutputList> handle2) {
        this.array = new Object[16];
        this.handle = handle2;
    }

    public Object get(int i) {
        checkIndex(i);
        return this.array[i];
    }

    public int size() {
        return this.size;
    }

    public boolean add(Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        try {
            insert(this.size, obj);
        } catch (IndexOutOfBoundsException unused) {
            expandArray();
            insert(this.size, obj);
        }
        this.size++;
        return true;
    }

    public Object set(int i, Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        checkIndex(i);
        Object obj2 = this.array[i];
        insert(i, obj);
        return obj2;
    }

    public void add(int i, Object obj) {
        ObjectUtil.checkNotNull(obj, "element");
        checkIndex(i);
        if (this.size == this.array.length) {
            expandArray();
        }
        int i2 = this.size;
        if (i != i2 - 1) {
            Object[] objArr = this.array;
            System.arraycopy(objArr, i, objArr, i + 1, i2 - i);
        }
        insert(i, obj);
        this.size++;
    }

    public Object remove(int i) {
        checkIndex(i);
        Object[] objArr = this.array;
        Object obj = objArr[i];
        int i2 = (this.size - i) - 1;
        if (i2 > 0) {
            System.arraycopy(objArr, i + 1, objArr, i, i2);
        }
        Object[] objArr2 = this.array;
        int i3 = this.size - 1;
        this.size = i3;
        objArr2[i3] = null;
        return obj;
    }

    public void clear() {
        this.size = 0;
    }

    /* access modifiers changed from: 0000 */
    public boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    /* access modifiers changed from: 0000 */
    public void recycle() {
        for (int i = 0; i < this.size; i++) {
            this.array[i] = null;
        }
        clear();
        this.insertSinceRecycled = false;
        this.handle.recycle(this);
    }

    /* access modifiers changed from: 0000 */
    public Object getUnsafe(int i) {
        return this.array[i];
    }

    private void checkIndex(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void insert(int i, Object obj) {
        this.array[i] = obj;
        this.insertSinceRecycled = true;
    }

    private void expandArray() {
        Object[] objArr = this.array;
        int length = objArr.length << 1;
        if (length >= 0) {
            Object[] objArr2 = new Object[length];
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
            this.array = objArr2;
            return;
        }
        throw new OutOfMemoryError();
    }
}
