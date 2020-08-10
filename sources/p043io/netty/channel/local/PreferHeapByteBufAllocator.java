package p043io.netty.channel.local;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.CompositeByteBuf;

/* renamed from: io.netty.channel.local.PreferHeapByteBufAllocator */
final class PreferHeapByteBufAllocator implements ByteBufAllocator {
    private final ByteBufAllocator allocator;

    PreferHeapByteBufAllocator(ByteBufAllocator byteBufAllocator) {
        this.allocator = byteBufAllocator;
    }

    public ByteBuf buffer() {
        return this.allocator.heapBuffer();
    }

    public ByteBuf buffer(int i) {
        return this.allocator.heapBuffer(i);
    }

    public ByteBuf buffer(int i, int i2) {
        return this.allocator.heapBuffer(i, i2);
    }

    public ByteBuf ioBuffer() {
        return this.allocator.heapBuffer();
    }

    public ByteBuf ioBuffer(int i) {
        return this.allocator.heapBuffer(i);
    }

    public ByteBuf ioBuffer(int i, int i2) {
        return this.allocator.heapBuffer(i, i2);
    }

    public ByteBuf heapBuffer() {
        return this.allocator.heapBuffer();
    }

    public ByteBuf heapBuffer(int i) {
        return this.allocator.heapBuffer(i);
    }

    public ByteBuf heapBuffer(int i, int i2) {
        return this.allocator.heapBuffer(i, i2);
    }

    public ByteBuf directBuffer() {
        return this.allocator.directBuffer();
    }

    public ByteBuf directBuffer(int i) {
        return this.allocator.directBuffer(i);
    }

    public ByteBuf directBuffer(int i, int i2) {
        return this.allocator.directBuffer(i, i2);
    }

    public CompositeByteBuf compositeBuffer() {
        return this.allocator.compositeHeapBuffer();
    }

    public CompositeByteBuf compositeBuffer(int i) {
        return this.allocator.compositeHeapBuffer(i);
    }

    public CompositeByteBuf compositeHeapBuffer() {
        return this.allocator.compositeHeapBuffer();
    }

    public CompositeByteBuf compositeHeapBuffer(int i) {
        return this.allocator.compositeHeapBuffer(i);
    }

    public CompositeByteBuf compositeDirectBuffer() {
        return this.allocator.compositeDirectBuffer();
    }

    public CompositeByteBuf compositeDirectBuffer(int i) {
        return this.allocator.compositeDirectBuffer(i);
    }

    public boolean isDirectBufferPooled() {
        return this.allocator.isDirectBufferPooled();
    }

    public int calculateNewCapacity(int i, int i2) {
        return this.allocator.calculateNewCapacity(i, i2);
    }
}
