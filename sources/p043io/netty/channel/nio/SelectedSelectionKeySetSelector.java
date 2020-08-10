package p043io.netty.channel.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

/* renamed from: io.netty.channel.nio.SelectedSelectionKeySetSelector */
final class SelectedSelectionKeySetSelector extends Selector {
    private final Selector delegate;
    private final SelectedSelectionKeySet selectionKeys;

    SelectedSelectionKeySetSelector(Selector selector, SelectedSelectionKeySet selectedSelectionKeySet) {
        this.delegate = selector;
        this.selectionKeys = selectedSelectionKeySet;
    }

    public boolean isOpen() {
        return this.delegate.isOpen();
    }

    public SelectorProvider provider() {
        return this.delegate.provider();
    }

    public Set<SelectionKey> keys() {
        return this.delegate.keys();
    }

    public Set<SelectionKey> selectedKeys() {
        return this.delegate.selectedKeys();
    }

    public int selectNow() throws IOException {
        this.selectionKeys.reset();
        return this.delegate.selectNow();
    }

    public int select(long j) throws IOException {
        this.selectionKeys.reset();
        return this.delegate.select(j);
    }

    public int select() throws IOException {
        this.selectionKeys.reset();
        return this.delegate.select();
    }

    public Selector wakeup() {
        return this.delegate.wakeup();
    }

    public void close() throws IOException {
        this.delegate.close();
    }
}
