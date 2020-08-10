package p043io.netty.channel.nio;

import java.nio.channels.SelectionKey;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

/* renamed from: io.netty.channel.nio.SelectedSelectionKeySet */
final class SelectedSelectionKeySet extends AbstractSet<SelectionKey> {
    SelectionKey[] keys = new SelectionKey[1024];
    int size;

    public boolean contains(Object obj) {
        return false;
    }

    public boolean remove(Object obj) {
        return false;
    }

    SelectedSelectionKeySet() {
    }

    public boolean add(SelectionKey selectionKey) {
        if (selectionKey == null) {
            return false;
        }
        SelectionKey[] selectionKeyArr = this.keys;
        int i = this.size;
        this.size = i + 1;
        selectionKeyArr[i] = selectionKey;
        if (this.size == selectionKeyArr.length) {
            increaseCapacity();
        }
        return true;
    }

    public int size() {
        return this.size;
    }

    public Iterator<SelectionKey> iterator() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        reset(0);
    }

    /* access modifiers changed from: 0000 */
    public void reset(int i) {
        Arrays.fill(this.keys, i, this.size, null);
        this.size = 0;
    }

    private void increaseCapacity() {
        SelectionKey[] selectionKeyArr = this.keys;
        SelectionKey[] selectionKeyArr2 = new SelectionKey[(selectionKeyArr.length << 1)];
        System.arraycopy(selectionKeyArr, 0, selectionKeyArr2, 0, this.size);
        this.keys = selectionKeyArr2;
    }
}
