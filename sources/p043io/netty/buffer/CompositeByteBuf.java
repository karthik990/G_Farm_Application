package p043io.netty.buffer;

import com.google.common.base.Ascii;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.UShort;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.buffer.CompositeByteBuf */
public class CompositeByteBuf extends AbstractReferenceCountedByteBuf implements Iterable<ByteBuf> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Iterator<ByteBuf> EMPTY_ITERATOR = Collections.emptyList().iterator();
    private static final ByteBuffer EMPTY_NIO_BUFFER = Unpooled.EMPTY_BUFFER.nioBuffer();
    private final ByteBufAllocator alloc;
    /* access modifiers changed from: private */
    public final List<Component> components;
    private final boolean direct;
    private boolean freed;
    private final int maxNumComponents;

    /* renamed from: io.netty.buffer.CompositeByteBuf$Component */
    private static final class Component {
        final ByteBuf buf;
        int endOffset;
        final int length;
        int offset;

        Component(ByteBuf byteBuf) {
            this.buf = byteBuf;
            this.length = byteBuf.readableBytes();
        }

        /* access modifiers changed from: 0000 */
        public void freeIfNecessary() {
            this.buf.release();
        }
    }

    /* renamed from: io.netty.buffer.CompositeByteBuf$CompositeByteBufIterator */
    private final class CompositeByteBufIterator implements Iterator<ByteBuf> {
        private int index;
        private final int size;

        private CompositeByteBufIterator() {
            this.size = CompositeByteBuf.this.components.size();
        }

        public boolean hasNext() {
            return this.size > this.index;
        }

        public ByteBuf next() {
            if (this.size != CompositeByteBuf.this.components.size()) {
                throw new ConcurrentModificationException();
            } else if (hasNext()) {
                try {
                    List access$100 = CompositeByteBuf.this.components;
                    int i = this.index;
                    this.index = i + 1;
                    return ((Component) access$100.get(i)).buf;
                } catch (IndexOutOfBoundsException unused) {
                    throw new ConcurrentModificationException();
                }
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-Only");
        }
    }

    public CompositeByteBuf touch() {
        return this;
    }

    public CompositeByteBuf touch(Object obj) {
        return this;
    }

    public ByteBuf unwrap() {
        return null;
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator != null) {
            this.alloc = byteBufAllocator;
            this.direct = z;
            this.maxNumComponents = i;
            this.components = newList(i);
            return;
        }
        throw new NullPointerException("alloc");
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i, ByteBuf... byteBufArr) {
        this(byteBufAllocator, z, i, byteBufArr, 0, byteBufArr.length);
    }

    CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i, ByteBuf[] byteBufArr, int i2, int i3) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i >= 2) {
            this.alloc = byteBufAllocator;
            this.direct = z;
            this.maxNumComponents = i;
            this.components = newList(i);
            addComponents0(false, 0, byteBufArr, i2, i3);
            consolidateIfNeeded();
            setIndex(0, capacity());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("maxNumComponents: ");
            sb.append(i);
            sb.append(" (expected: >= 2)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public CompositeByteBuf(ByteBufAllocator byteBufAllocator, boolean z, int i, Iterable<ByteBuf> iterable) {
        super(Integer.MAX_VALUE);
        if (byteBufAllocator == null) {
            throw new NullPointerException("alloc");
        } else if (i >= 2) {
            this.alloc = byteBufAllocator;
            this.direct = z;
            this.maxNumComponents = i;
            this.components = newList(i);
            addComponents0(false, 0, iterable);
            consolidateIfNeeded();
            setIndex(0, capacity());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("maxNumComponents: ");
            sb.append(i);
            sb.append(" (expected: >= 2)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static List<Component> newList(int i) {
        return new ArrayList(Math.min(16, i));
    }

    CompositeByteBuf(ByteBufAllocator byteBufAllocator) {
        super(Integer.MAX_VALUE);
        this.alloc = byteBufAllocator;
        this.direct = false;
        this.maxNumComponents = 0;
        this.components = Collections.emptyList();
    }

    public CompositeByteBuf addComponent(ByteBuf byteBuf) {
        return addComponent(false, byteBuf);
    }

    public CompositeByteBuf addComponents(ByteBuf... byteBufArr) {
        return addComponents(false, byteBufArr);
    }

    public CompositeByteBuf addComponents(Iterable<ByteBuf> iterable) {
        return addComponents(false, iterable);
    }

    public CompositeByteBuf addComponent(int i, ByteBuf byteBuf) {
        return addComponent(false, i, byteBuf);
    }

    public CompositeByteBuf addComponent(boolean z, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        addComponent0(z, this.components.size(), byteBuf);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, ByteBuf... byteBufArr) {
        addComponents0(z, this.components.size(), byteBufArr, 0, byteBufArr.length);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponents(boolean z, Iterable<ByteBuf> iterable) {
        addComponents0(z, this.components.size(), iterable);
        consolidateIfNeeded();
        return this;
    }

    public CompositeByteBuf addComponent(boolean z, int i, ByteBuf byteBuf) {
        ObjectUtil.checkNotNull(byteBuf, "buffer");
        addComponent0(z, i, byteBuf);
        consolidateIfNeeded();
        return this;
    }

    private int addComponent0(boolean z, int i, ByteBuf byteBuf) {
        boolean z2 = false;
        try {
            checkComponentIndex(i);
            int readableBytes = byteBuf.readableBytes();
            Component component = new Component(byteBuf.order(ByteOrder.BIG_ENDIAN).slice());
            if (i == this.components.size()) {
                z2 = this.components.add(component);
                if (i == 0) {
                    component.endOffset = readableBytes;
                } else {
                    component.offset = ((Component) this.components.get(i - 1)).endOffset;
                    component.endOffset = component.offset + readableBytes;
                }
            } else {
                this.components.add(i, component);
                if (readableBytes != 0) {
                    try {
                        updateComponentOffsets(i);
                    } catch (Throwable th) {
                        th = th;
                        z2 = true;
                    }
                }
                z2 = true;
            }
            if (z) {
                writerIndex(writerIndex() + byteBuf.readableBytes());
            }
            if (!z2) {
                byteBuf.release();
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
            if (!z2) {
                byteBuf.release();
            }
            throw th;
        }
    }

    public CompositeByteBuf addComponents(int i, ByteBuf... byteBufArr) {
        addComponents0(false, i, byteBufArr, 0, byteBufArr.length);
        consolidateIfNeeded();
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int addComponents0(boolean r2, int r3, p043io.netty.buffer.ByteBuf[] r4, int r5, int r6) {
        /*
            r1 = this;
            java.lang.String r0 = "buffers"
            p043io.netty.util.internal.ObjectUtil.checkNotNull(r4, r0)
            r1.checkComponentIndex(r3)     // Catch:{ all -> 0x0032 }
        L_0x0008:
            if (r5 >= r6) goto L_0x0025
            int r0 = r5 + 1
            r5 = r4[r5]     // Catch:{ all -> 0x0023 }
            if (r5 != 0) goto L_0x0012
            r5 = r0
            goto L_0x0025
        L_0x0012:
            int r3 = r1.addComponent0(r2, r3, r5)     // Catch:{ all -> 0x0023 }
            int r3 = r3 + 1
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r5 = r1.components     // Catch:{ all -> 0x0023 }
            int r5 = r5.size()     // Catch:{ all -> 0x0023 }
            if (r3 <= r5) goto L_0x0021
            r3 = r5
        L_0x0021:
            r5 = r0
            goto L_0x0008
        L_0x0023:
            r2 = move-exception
            goto L_0x0034
        L_0x0025:
            if (r5 >= r6) goto L_0x0031
            r2 = r4[r5]
            if (r2 == 0) goto L_0x002e
            r2.release()     // Catch:{ all -> 0x002e }
        L_0x002e:
            int r5 = r5 + 1
            goto L_0x0025
        L_0x0031:
            return r3
        L_0x0032:
            r2 = move-exception
            r0 = r5
        L_0x0034:
            if (r0 >= r6) goto L_0x0040
            r3 = r4[r0]
            if (r3 == 0) goto L_0x003d
            r3.release()     // Catch:{ all -> 0x003d }
        L_0x003d:
            int r0 = r0 + 1
            goto L_0x0034
        L_0x0040:
            goto L_0x0042
        L_0x0041:
            throw r2
        L_0x0042:
            goto L_0x0041
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.addComponents0(boolean, int, io.netty.buffer.ByteBuf[], int, int):int");
    }

    public CompositeByteBuf addComponents(int i, Iterable<ByteBuf> iterable) {
        addComponents0(false, i, iterable);
        consolidateIfNeeded();
        return this;
    }

    /* JADX WARNING: type inference failed for: r10v1 */
    /* JADX WARNING: type inference failed for: r0v6, types: [java.util.List, java.lang.Iterable, java.util.ArrayList] */
    /* JADX WARNING: type inference failed for: r10v6 */
    /*  JADX ERROR: UnsupportedOperationException in pass: LoopRegionVisitor
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:438)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:334)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:270)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:75)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:59)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1083)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:57)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:57)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
        	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:53)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    private int addComponents0(boolean r8, int r9, java.lang.Iterable<p043io.netty.buffer.ByteBuf> r10) {
        /*
            r7 = this;
            boolean r0 = r10 instanceof p043io.netty.buffer.ByteBuf
            if (r0 == 0) goto L_0x000b
            io.netty.buffer.ByteBuf r10 = (p043io.netty.buffer.ByteBuf) r10
            int r8 = r7.addComponent0(r8, r9, r10)
            return r8
        L_0x000b:
            java.lang.String r0 = "buffers"
            p043io.netty.util.internal.ObjectUtil.checkNotNull(r10, r0)
            boolean r0 = r10 instanceof java.util.Collection
            if (r0 != 0) goto L_0x0065
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Iterator r1 = r10.iterator()     // Catch:{ all -> 0x0049 }
        L_0x001d:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x002d     // Catch:{ all -> 0x0049 }
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x0049 }
            io.netty.buffer.ByteBuf r2 = (p043io.netty.buffer.ByteBuf) r2     // Catch:{ all -> 0x0049 }
            r0.add(r2)     // Catch:{ all -> 0x0049 }
            goto L_0x001d
        L_0x002d:
            if (r0 == r0) goto L_0x0047
            java.util.Iterator r10 = r0.iterator()
        L_0x0033:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L_0x0047
            java.lang.Object r1 = r10.next()
            io.netty.buffer.ByteBuf r1 = (p043io.netty.buffer.ByteBuf) r1
            if (r1 == 0) goto L_0x0033
            r1.release()     // Catch:{ all -> 0x0045 }
            goto L_0x0033
        L_0x0045:
            goto L_0x0033
        L_0x0047:
            r10 = r0
            goto L_0x0065
        L_0x0049:
            r8 = move-exception
            if (r10 == r0) goto L_0x0064
            java.util.Iterator r9 = r10.iterator()
        L_0x0050:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x0064
            java.lang.Object r10 = r9.next()
            io.netty.buffer.ByteBuf r10 = (p043io.netty.buffer.ByteBuf) r10
            if (r10 == 0) goto L_0x0050
            r10.release()     // Catch:{ all -> 0x0062 }
            goto L_0x0050
        L_0x0062:
            goto L_0x0050
        L_0x0064:
            throw r8
        L_0x0065:
            java.util.Collection r10 = (java.util.Collection) r10
            int r0 = r10.size()
            io.netty.buffer.ByteBuf[] r0 = new p043io.netty.buffer.ByteBuf[r0]
            java.lang.Object[] r0 = r10.toArray(r0)
            r4 = r0
            io.netty.buffer.ByteBuf[] r4 = (p043io.netty.buffer.ByteBuf[]) r4
            r5 = 0
            int r6 = r10.size()
            r1 = r7
            r2 = r8
            r3 = r9
            int r8 = r1.addComponents0(r2, r3, r4, r5, r6)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.addComponents0(boolean, int, java.lang.Iterable):int");
    }

    private void consolidateIfNeeded() {
        int size = this.components.size();
        if (size > this.maxNumComponents) {
            ByteBuf allocBuffer = allocBuffer(((Component) this.components.get(size - 1)).endOffset);
            for (int i = 0; i < size; i++) {
                Component component = (Component) this.components.get(i);
                allocBuffer.writeBytes(component.buf);
                component.freeIfNecessary();
            }
            Component component2 = new Component(allocBuffer);
            component2.endOffset = component2.length;
            this.components.clear();
            this.components.add(component2);
        }
    }

    private void checkComponentIndex(int i) {
        ensureAccessible();
        if (i < 0 || i > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[]{Integer.valueOf(i), Integer.valueOf(this.components.size())}));
        }
    }

    private void checkComponentIndex(int i, int i2) {
        ensureAccessible();
        if (i < 0 || i + i2 > this.components.size()) {
            throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.components.size())}));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateComponentOffsets(int r5) {
        /*
            r4 = this;
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r0 = r4.components
            int r0 = r0.size()
            if (r0 > r5) goto L_0x0009
            return
        L_0x0009:
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r1 = r4.components
            java.lang.Object r1 = r1.get(r5)
            io.netty.buffer.CompositeByteBuf$Component r1 = (p043io.netty.buffer.CompositeByteBuf.Component) r1
            if (r5 != 0) goto L_0x001b
            r2 = 0
            r1.offset = r2
            int r2 = r1.length
            r1.endOffset = r2
            goto L_0x003a
        L_0x001b:
            if (r5 >= r0) goto L_0x003d
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r1 = r4.components
            int r2 = r5 + -1
            java.lang.Object r1 = r1.get(r2)
            io.netty.buffer.CompositeByteBuf$Component r1 = (p043io.netty.buffer.CompositeByteBuf.Component) r1
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r2 = r4.components
            java.lang.Object r2 = r2.get(r5)
            io.netty.buffer.CompositeByteBuf$Component r2 = (p043io.netty.buffer.CompositeByteBuf.Component) r2
            int r1 = r1.endOffset
            r2.offset = r1
            int r1 = r2.offset
            int r3 = r2.length
            int r1 = r1 + r3
            r2.endOffset = r1
        L_0x003a:
            int r5 = r5 + 1
            goto L_0x001b
        L_0x003d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.updateComponentOffsets(int):void");
    }

    public CompositeByteBuf removeComponent(int i) {
        checkComponentIndex(i);
        Component component = (Component) this.components.remove(i);
        component.freeIfNecessary();
        if (component.length > 0) {
            updateComponentOffsets(i);
        }
        return this;
    }

    public CompositeByteBuf removeComponents(int i, int i2) {
        checkComponentIndex(i, i2);
        if (i2 == 0) {
            return this;
        }
        List<Component> subList = this.components.subList(i, i2 + i);
        boolean z = false;
        for (Component component : subList) {
            if (component.length > 0) {
                z = true;
            }
            component.freeIfNecessary();
        }
        subList.clear();
        if (z) {
            updateComponentOffsets(i);
        }
        return this;
    }

    public Iterator<ByteBuf> iterator() {
        ensureAccessible();
        if (this.components.isEmpty()) {
            return EMPTY_ITERATOR;
        }
        return new CompositeByteBufIterator();
    }

    public List<ByteBuf> decompose(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return Collections.emptyList();
        }
        int componentIndex = toComponentIndex(i);
        ArrayList arrayList = new ArrayList(this.components.size());
        Component component = (Component) this.components.get(componentIndex);
        ByteBuf duplicate = component.buf.duplicate();
        duplicate.readerIndex(i - component.offset);
        while (true) {
            int readableBytes = duplicate.readableBytes();
            if (i2 > readableBytes) {
                arrayList.add(duplicate);
                i2 -= readableBytes;
                componentIndex++;
                duplicate = ((Component) this.components.get(componentIndex)).buf.duplicate();
                if (i2 <= 0) {
                    break;
                }
            } else {
                duplicate.writerIndex(duplicate.readerIndex() + i2);
                arrayList.add(duplicate);
                break;
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            arrayList.set(i3, ((ByteBuf) arrayList.get(i3)).slice());
        }
        return arrayList;
    }

    public boolean isDirect() {
        int size = this.components.size();
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!((Component) this.components.get(i)).buf.isDirect()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasArray() {
        int size = this.components.size();
        if (size == 0) {
            return true;
        }
        if (size != 1) {
            return false;
        }
        return ((Component) this.components.get(0)).buf.hasArray();
    }

    public byte[] array() {
        int size = this.components.size();
        if (size == 0) {
            return EmptyArrays.EMPTY_BYTES;
        }
        if (size == 1) {
            return ((Component) this.components.get(0)).buf.array();
        }
        throw new UnsupportedOperationException();
    }

    public int arrayOffset() {
        int size = this.components.size();
        if (size == 0) {
            return 0;
        }
        if (size == 1) {
            return ((Component) this.components.get(0)).buf.arrayOffset();
        }
        throw new UnsupportedOperationException();
    }

    public boolean hasMemoryAddress() {
        int size = this.components.size();
        if (size == 0) {
            return Unpooled.EMPTY_BUFFER.hasMemoryAddress();
        }
        if (size != 1) {
            return false;
        }
        return ((Component) this.components.get(0)).buf.hasMemoryAddress();
    }

    public long memoryAddress() {
        int size = this.components.size();
        if (size == 0) {
            return Unpooled.EMPTY_BUFFER.memoryAddress();
        }
        if (size == 1) {
            return ((Component) this.components.get(0)).buf.memoryAddress();
        }
        throw new UnsupportedOperationException();
    }

    public int capacity() {
        int size = this.components.size();
        if (size == 0) {
            return 0;
        }
        return ((Component) this.components.get(size - 1)).endOffset;
    }

    public CompositeByteBuf capacity(int i) {
        checkNewCapacity(i);
        int capacity = capacity();
        if (i > capacity) {
            int i2 = i - capacity;
            if (this.components.size() < this.maxNumComponents) {
                ByteBuf allocBuffer = allocBuffer(i2);
                allocBuffer.setIndex(0, i2);
                addComponent0(false, this.components.size(), allocBuffer);
            } else {
                ByteBuf allocBuffer2 = allocBuffer(i2);
                allocBuffer2.setIndex(0, i2);
                addComponent0(false, this.components.size(), allocBuffer2);
                consolidateIfNeeded();
            }
        } else if (i < capacity) {
            int i3 = capacity - i;
            List<Component> list = this.components;
            ListIterator listIterator = list.listIterator(list.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    break;
                }
                Component component = (Component) listIterator.previous();
                if (i3 < component.length) {
                    Component component2 = new Component(component.buf.slice(0, component.length - i3));
                    component2.offset = component.offset;
                    component2.endOffset = component2.offset + component2.length;
                    listIterator.set(component2);
                    break;
                }
                i3 -= component.length;
                listIterator.remove();
            }
            if (readerIndex() > i) {
                setIndex(i, i);
            } else if (writerIndex() > i) {
                writerIndex(i);
            }
        }
        return this;
    }

    public ByteBufAllocator alloc() {
        return this.alloc;
    }

    public ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    public int numComponents() {
        return this.components.size();
    }

    public int maxNumComponents() {
        return this.maxNumComponents;
    }

    public int toComponentIndex(int i) {
        checkIndex(i);
        int size = this.components.size();
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            Component component = (Component) this.components.get(i3);
            if (i >= component.endOffset) {
                i2 = i3 + 1;
            } else if (i >= component.offset) {
                return i3;
            } else {
                size = i3 - 1;
            }
        }
        throw new Error("should not reach here");
    }

    public int toByteIndex(int i) {
        checkComponentIndex(i);
        return ((Component) this.components.get(i)).offset;
    }

    public byte getByte(int i) {
        return _getByte(i);
    }

    /* access modifiers changed from: protected */
    public byte _getByte(int i) {
        Component findComponent = findComponent(i);
        return findComponent.buf.getByte(i - findComponent.offset);
    }

    /* access modifiers changed from: protected */
    public short _getShort(int i) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            return findComponent.buf.getShort(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
        }
        return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
    }

    /* access modifiers changed from: protected */
    public short _getShortLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            return findComponent.buf.getShortLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (short) (((_getByte(i + 1) & 255) << 8) | (_getByte(i) & 255));
        }
        return (short) ((_getByte(i + 1) & 255) | ((_getByte(i) & 255) << 8));
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMedium(int i) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            return findComponent.buf.getUnsignedMedium(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getByte(i + 2) & 255) | ((_getShort(i) & UShort.MAX_VALUE) << 8);
        }
        return ((_getByte(i + 2) & 255) << Ascii.DLE) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public int _getUnsignedMediumLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            return findComponent.buf.getUnsignedMediumLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getByte(i + 2) & 255) << Ascii.DLE) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getByte(i + 2) & 255) | ((_getShortLE(i) & UShort.MAX_VALUE) << 8);
    }

    /* access modifiers changed from: protected */
    public int _getInt(int i) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            return findComponent.buf.getInt(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (_getShort(i + 2) & UShort.MAX_VALUE) | ((_getShort(i) & UShort.MAX_VALUE) << 16);
        }
        return ((_getShort(i + 2) & UShort.MAX_VALUE) << 16) | (_getShort(i) & UShort.MAX_VALUE);
    }

    /* access modifiers changed from: protected */
    public int _getIntLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            return findComponent.buf.getIntLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((_getShortLE(i + 2) & UShort.MAX_VALUE) << 16) | (_getShortLE(i) & UShort.MAX_VALUE);
        }
        return (_getShortLE(i + 2) & UShort.MAX_VALUE) | ((_getShortLE(i) & UShort.MAX_VALUE) << 16);
    }

    /* access modifiers changed from: protected */
    public long _getLong(int i) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            return findComponent.buf.getLong(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return ((((long) _getInt(i)) & 4294967295L) << 32) | (4294967295L & ((long) _getInt(i + 4)));
        }
        return (((long) _getInt(i)) & 4294967295L) | ((4294967295L & ((long) _getInt(i + 4))) << 32);
    }

    /* access modifiers changed from: protected */
    public long _getLongLE(int i) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            return findComponent.buf.getLongLE(i - findComponent.offset);
        }
        if (order() == ByteOrder.BIG_ENDIAN) {
            return (((long) _getIntLE(i)) & 4294967295L) | ((4294967295L & ((long) _getIntLE(i + 4))) << 32);
        }
        return ((((long) _getIntLE(i)) & 4294967295L) << 32) | (4294967295L & ((long) _getIntLE(i + 4)));
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr, int i2, int i3) {
        checkDstIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf = component.buf;
            int i4 = i - component.offset;
            int min = Math.min(i3, byteBuf.capacity() - i4);
            byteBuf.getBytes(i4, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (remaining > 0) {
            try {
                Component component = (Component) this.components.get(componentIndex);
                ByteBuf byteBuf = component.buf;
                int i2 = i - component.offset;
                int min = Math.min(remaining, byteBuf.capacity() - i2);
                byteBuffer.limit(byteBuffer.position() + min);
                byteBuf.getBytes(i2, byteBuffer);
                i += min;
                remaining -= min;
                componentIndex++;
            } catch (Throwable th) {
                byteBuffer.limit(limit);
                throw th;
            }
        }
        byteBuffer.limit(limit);
        return this;
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkDstIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf2 = component.buf;
            int i4 = i - component.offset;
            int min = Math.min(i3, byteBuf2.capacity() - i4);
            byteBuf2.getBytes(i4, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return gatheringByteChannel.write(internalNioBuffer(i, i2));
        }
        long write = gatheringByteChannel.write(nioBuffers(i, i2));
        if (write > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) write;
    }

    public int getBytes(int i, FileChannel fileChannel, long j, int i2) throws IOException {
        if (nioBufferCount() == 1) {
            return fileChannel.write(internalNioBuffer(i, i2), j);
        }
        long j2 = 0;
        for (ByteBuffer write : nioBuffers(i, i2)) {
            j2 += (long) fileChannel.write(write, j + j2);
        }
        if (j2 > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j2;
    }

    public CompositeByteBuf getBytes(int i, OutputStream outputStream, int i2) throws IOException {
        checkIndex(i, i2);
        if (i2 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i2 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf = component.buf;
            int i3 = i - component.offset;
            int min = Math.min(i2, byteBuf.capacity() - i3);
            byteBuf.getBytes(i3, outputStream, min);
            i += min;
            i2 -= min;
            componentIndex++;
        }
        return this;
    }

    public CompositeByteBuf setByte(int i, int i2) {
        Component findComponent = findComponent(i);
        findComponent.buf.setByte(i - findComponent.offset, i2);
        return this;
    }

    /* access modifiers changed from: protected */
    public void _setByte(int i, int i2) {
        setByte(i, i2);
    }

    public CompositeByteBuf setShort(int i, int i2) {
        return (CompositeByteBuf) super.setShort(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setShort(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            findComponent.buf.setShort(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setByte(i, (byte) (i2 >>> 8));
            _setByte(i + 1, (byte) i2);
        } else {
            _setByte(i, (byte) i2);
            _setByte(i + 1, (byte) (i2 >>> 8));
        }
    }

    /* access modifiers changed from: protected */
    public void _setShortLE(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 2 <= findComponent.endOffset) {
            findComponent.buf.setShortLE(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setByte(i, (byte) i2);
            _setByte(i + 1, (byte) (i2 >>> 8));
        } else {
            _setByte(i, (byte) (i2 >>> 8));
            _setByte(i + 1, (byte) i2);
        }
    }

    public CompositeByteBuf setMedium(int i, int i2) {
        return (CompositeByteBuf) super.setMedium(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setMedium(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            findComponent.buf.setMedium(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(i, (short) (i2 >> 8));
            _setByte(i + 2, (byte) i2);
        } else {
            _setShort(i, (short) i2);
            _setByte(i + 2, (byte) (i2 >>> 16));
        }
    }

    /* access modifiers changed from: protected */
    public void _setMediumLE(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 3 <= findComponent.endOffset) {
            findComponent.buf.setMediumLE(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShortLE(i, (short) i2);
            _setByte(i + 2, (byte) (i2 >>> 16));
        } else {
            _setShortLE(i, (short) (i2 >> 8));
            _setByte(i + 2, (byte) i2);
        }
    }

    public CompositeByteBuf setInt(int i, int i2) {
        return (CompositeByteBuf) super.setInt(i, i2);
    }

    /* access modifiers changed from: protected */
    public void _setInt(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            findComponent.buf.setInt(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShort(i, (short) (i2 >>> 16));
            _setShort(i + 2, (short) i2);
        } else {
            _setShort(i, (short) i2);
            _setShort(i + 2, (short) (i2 >>> 16));
        }
    }

    /* access modifiers changed from: protected */
    public void _setIntLE(int i, int i2) {
        Component findComponent = findComponent(i);
        if (i + 4 <= findComponent.endOffset) {
            findComponent.buf.setIntLE(i - findComponent.offset, i2);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setShortLE(i, (short) i2);
            _setShortLE(i + 2, (short) (i2 >>> 16));
        } else {
            _setShortLE(i, (short) (i2 >>> 16));
            _setShortLE(i + 2, (short) i2);
        }
    }

    public CompositeByteBuf setLong(int i, long j) {
        return (CompositeByteBuf) super.setLong(i, j);
    }

    /* access modifiers changed from: protected */
    public void _setLong(int i, long j) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            findComponent.buf.setLong(i - findComponent.offset, j);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setInt(i, (int) (j >>> 32));
            _setInt(i + 4, (int) j);
        } else {
            _setInt(i, (int) j);
            _setInt(i + 4, (int) (j >>> 32));
        }
    }

    /* access modifiers changed from: protected */
    public void _setLongLE(int i, long j) {
        Component findComponent = findComponent(i);
        if (i + 8 <= findComponent.endOffset) {
            findComponent.buf.setLongLE(i - findComponent.offset, j);
        } else if (order() == ByteOrder.BIG_ENDIAN) {
            _setIntLE(i, (int) j);
            _setIntLE(i + 4, (int) (j >>> 32));
        } else {
            _setIntLE(i, (int) (j >>> 32));
            _setIntLE(i + 4, (int) j);
        }
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr, int i2, int i3) {
        checkSrcIndex(i, i3, i2, bArr.length);
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf = component.buf;
            int i4 = i - component.offset;
            int min = Math.min(i3, byteBuf.capacity() - i4);
            byteBuf.setBytes(i4, bArr, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        int limit = byteBuffer.limit();
        int remaining = byteBuffer.remaining();
        checkIndex(i, remaining);
        if (remaining == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (remaining > 0) {
            try {
                Component component = (Component) this.components.get(componentIndex);
                ByteBuf byteBuf = component.buf;
                int i2 = i - component.offset;
                int min = Math.min(remaining, byteBuf.capacity() - i2);
                byteBuffer.limit(byteBuffer.position() + min);
                byteBuf.setBytes(i2, byteBuffer);
                i += min;
                remaining -= min;
                componentIndex++;
            } catch (Throwable th) {
                byteBuffer.limit(limit);
                throw th;
            }
        }
        byteBuffer.limit(limit);
        return this;
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2, int i3) {
        checkSrcIndex(i, i3, i2, byteBuf.capacity());
        if (i3 == 0) {
            return this;
        }
        int componentIndex = toComponentIndex(i);
        while (i3 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf2 = component.buf;
            int i4 = i - component.offset;
            int min = Math.min(i3, byteBuf2.capacity() - i4);
            byteBuf2.setBytes(i4, byteBuf, i2, min);
            i += min;
            i2 += min;
            i3 -= min;
            componentIndex++;
        }
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0042 A[EDGE_INSN: B:18:0x0042->B:17:0x0042 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setBytes(int r6, java.io.InputStream r7, int r8) throws java.io.IOException {
        /*
            r5 = this;
            r5.checkIndex(r6, r8)
            if (r8 != 0) goto L_0x000c
            byte[] r6 = p043io.netty.util.internal.EmptyArrays.EMPTY_BYTES
            int r6 = r7.read(r6)
            return r6
        L_0x000c:
            int r0 = r5.toComponentIndex(r6)
            r1 = 0
        L_0x0011:
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r2 = r5.components
            java.lang.Object r2 = r2.get(r0)
            io.netty.buffer.CompositeByteBuf$Component r2 = (p043io.netty.buffer.CompositeByteBuf.Component) r2
            io.netty.buffer.ByteBuf r3 = r2.buf
            int r2 = r2.offset
            int r4 = r3.capacity()
            int r2 = r6 - r2
            int r4 = r4 - r2
            int r4 = java.lang.Math.min(r8, r4)
            if (r4 != 0) goto L_0x002d
        L_0x002a:
            int r0 = r0 + 1
            goto L_0x0040
        L_0x002d:
            int r2 = r3.setBytes(r2, r7, r4)
            if (r2 >= 0) goto L_0x0037
            if (r1 != 0) goto L_0x0042
            r6 = -1
            return r6
        L_0x0037:
            if (r2 != r4) goto L_0x003d
            int r6 = r6 + r4
            int r8 = r8 - r4
            int r1 = r1 + r4
            goto L_0x002a
        L_0x003d:
            int r6 = r6 + r2
            int r8 = r8 - r2
            int r1 = r1 + r2
        L_0x0040:
            if (r8 > 0) goto L_0x0011
        L_0x0042:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.setBytes(int, java.io.InputStream, int):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045 A[EDGE_INSN: B:20:0x0045->B:18:0x0045 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setBytes(int r6, java.nio.channels.ScatteringByteChannel r7, int r8) throws java.io.IOException {
        /*
            r5 = this;
            r5.checkIndex(r6, r8)
            if (r8 != 0) goto L_0x000c
            java.nio.ByteBuffer r6 = EMPTY_NIO_BUFFER
            int r6 = r7.read(r6)
            return r6
        L_0x000c:
            int r0 = r5.toComponentIndex(r6)
            r1 = 0
        L_0x0011:
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r2 = r5.components
            java.lang.Object r2 = r2.get(r0)
            io.netty.buffer.CompositeByteBuf$Component r2 = (p043io.netty.buffer.CompositeByteBuf.Component) r2
            io.netty.buffer.ByteBuf r3 = r2.buf
            int r2 = r2.offset
            int r4 = r3.capacity()
            int r2 = r6 - r2
            int r4 = r4 - r2
            int r4 = java.lang.Math.min(r8, r4)
            if (r4 != 0) goto L_0x002d
        L_0x002a:
            int r0 = r0 + 1
            goto L_0x0043
        L_0x002d:
            int r2 = r3.setBytes(r2, r7, r4)
            if (r2 != 0) goto L_0x0034
            goto L_0x0045
        L_0x0034:
            if (r2 >= 0) goto L_0x003a
            if (r1 != 0) goto L_0x0045
            r6 = -1
            return r6
        L_0x003a:
            if (r2 != r4) goto L_0x0040
            int r6 = r6 + r4
            int r8 = r8 - r4
            int r1 = r1 + r4
            goto L_0x002a
        L_0x0040:
            int r6 = r6 + r2
            int r8 = r8 - r2
            int r1 = r1 + r2
        L_0x0043:
            if (r8 > 0) goto L_0x0011
        L_0x0045:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.setBytes(int, java.nio.channels.ScatteringByteChannel, int):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004b A[EDGE_INSN: B:20:0x004b->B:18:0x004b ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int setBytes(int r11, java.nio.channels.FileChannel r12, long r13, int r15) throws java.io.IOException {
        /*
            r10 = this;
            r10.checkIndex(r11, r15)
            if (r15 != 0) goto L_0x000c
            java.nio.ByteBuffer r11 = EMPTY_NIO_BUFFER
            int r11 = r12.read(r11, r13)
            return r11
        L_0x000c:
            int r0 = r10.toComponentIndex(r11)
            r1 = 0
        L_0x0011:
            java.util.List<io.netty.buffer.CompositeByteBuf$Component> r2 = r10.components
            java.lang.Object r2 = r2.get(r0)
            io.netty.buffer.CompositeByteBuf$Component r2 = (p043io.netty.buffer.CompositeByteBuf.Component) r2
            io.netty.buffer.ByteBuf r3 = r2.buf
            int r2 = r2.offset
            int r4 = r3.capacity()
            int r2 = r11 - r2
            int r4 = r4 - r2
            int r9 = java.lang.Math.min(r15, r4)
            if (r9 != 0) goto L_0x002d
        L_0x002a:
            int r0 = r0 + 1
            goto L_0x0049
        L_0x002d:
            long r4 = (long) r1
            long r6 = r13 + r4
            r4 = r2
            r5 = r12
            r8 = r9
            int r2 = r3.setBytes(r4, r5, r6, r8)
            if (r2 != 0) goto L_0x003a
            goto L_0x004b
        L_0x003a:
            if (r2 >= 0) goto L_0x0040
            if (r1 != 0) goto L_0x004b
            r11 = -1
            return r11
        L_0x0040:
            if (r2 != r9) goto L_0x0046
            int r11 = r11 + r9
            int r15 = r15 - r9
            int r1 = r1 + r9
            goto L_0x002a
        L_0x0046:
            int r11 = r11 + r2
            int r15 = r15 - r2
            int r1 = r1 + r2
        L_0x0049:
            if (r15 > 0) goto L_0x0011
        L_0x004b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.CompositeByteBuf.setBytes(int, java.nio.channels.FileChannel, long, int):int");
    }

    public ByteBuf copy(int i, int i2) {
        checkIndex(i, i2);
        ByteBuf buffer = Unpooled.buffer(i2);
        if (i2 != 0) {
            copyTo(i, i2, toComponentIndex(i), buffer);
        }
        return buffer;
    }

    private void copyTo(int i, int i2, int i3, ByteBuf byteBuf) {
        int i4 = 0;
        while (i2 > 0) {
            Component component = (Component) this.components.get(i3);
            ByteBuf byteBuf2 = component.buf;
            int i5 = i - component.offset;
            int min = Math.min(i2, byteBuf2.capacity() - i5);
            byteBuf2.getBytes(i5, byteBuf, i4, min);
            i += min;
            i4 += min;
            i2 -= min;
            i3++;
        }
        byteBuf.writerIndex(byteBuf.capacity());
    }

    public ByteBuf component(int i) {
        return internalComponent(i).duplicate();
    }

    public ByteBuf componentAtOffset(int i) {
        return internalComponentAtOffset(i).duplicate();
    }

    public ByteBuf internalComponent(int i) {
        checkComponentIndex(i);
        return ((Component) this.components.get(i)).buf;
    }

    public ByteBuf internalComponentAtOffset(int i) {
        return findComponent(i).buf;
    }

    private Component findComponent(int i) {
        checkIndex(i);
        int size = this.components.size();
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            Component component = (Component) this.components.get(i3);
            if (i >= component.endOffset) {
                i2 = i3 + 1;
            } else if (i >= component.offset) {
                return component;
            } else {
                size = i3 - 1;
            }
        }
        throw new Error("should not reach here");
    }

    public int nioBufferCount() {
        int size = this.components.size();
        if (size == 0) {
            return 1;
        }
        if (size == 1) {
            return ((Component) this.components.get(0)).buf.nioBufferCount();
        }
        int i = 0;
        for (int i2 = 0; i2 < this.components.size(); i2++) {
            i += ((Component) this.components.get(i2)).buf.nioBufferCount();
        }
        return i;
    }

    public ByteBuffer internalNioBuffer(int i, int i2) {
        int size = this.components.size();
        if (size == 0) {
            return EMPTY_NIO_BUFFER;
        }
        if (size == 1) {
            return ((Component) this.components.get(0)).buf.internalNioBuffer(i, i2);
        }
        throw new UnsupportedOperationException();
    }

    public ByteBuffer nioBuffer(int i, int i2) {
        checkIndex(i, i2);
        int size = this.components.size();
        if (size == 0) {
            return EMPTY_NIO_BUFFER;
        }
        if (size == 1 && ((Component) this.components.get(0)).buf.nioBufferCount() == 1) {
            return ((Component) this.components.get(0)).buf.nioBuffer(i, i2);
        }
        ByteBuffer order = ByteBuffer.allocate(i2).order(order());
        for (ByteBuffer put : nioBuffers(i, i2)) {
            order.put(put);
        }
        order.flip();
        return order;
    }

    public ByteBuffer[] nioBuffers(int i, int i2) {
        checkIndex(i, i2);
        if (i2 == 0) {
            return new ByteBuffer[]{EMPTY_NIO_BUFFER};
        }
        ArrayList arrayList = new ArrayList(this.components.size());
        int componentIndex = toComponentIndex(i);
        while (i2 > 0) {
            Component component = (Component) this.components.get(componentIndex);
            ByteBuf byteBuf = component.buf;
            int i3 = i - component.offset;
            int min = Math.min(i2, byteBuf.capacity() - i3);
            int nioBufferCount = byteBuf.nioBufferCount();
            if (nioBufferCount != 0) {
                if (nioBufferCount != 1) {
                    Collections.addAll(arrayList, byteBuf.nioBuffers(i3, min));
                } else {
                    arrayList.add(byteBuf.nioBuffer(i3, min));
                }
                i += min;
                i2 -= min;
                componentIndex++;
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return (ByteBuffer[]) arrayList.toArray(new ByteBuffer[arrayList.size()]);
    }

    public CompositeByteBuf consolidate() {
        ensureAccessible();
        int numComponents = numComponents();
        if (numComponents <= 1) {
            return this;
        }
        ByteBuf allocBuffer = allocBuffer(((Component) this.components.get(numComponents - 1)).endOffset);
        for (int i = 0; i < numComponents; i++) {
            Component component = (Component) this.components.get(i);
            allocBuffer.writeBytes(component.buf);
            component.freeIfNecessary();
        }
        this.components.clear();
        this.components.add(new Component(allocBuffer));
        updateComponentOffsets(0);
        return this;
    }

    public CompositeByteBuf consolidate(int i, int i2) {
        checkComponentIndex(i, i2);
        if (i2 <= 1) {
            return this;
        }
        int i3 = i2 + i;
        ByteBuf allocBuffer = allocBuffer(((Component) this.components.get(i3 - 1)).endOffset - ((Component) this.components.get(i)).offset);
        for (int i4 = i; i4 < i3; i4++) {
            Component component = (Component) this.components.get(i4);
            allocBuffer.writeBytes(component.buf);
            component.freeIfNecessary();
        }
        this.components.subList(i + 1, i3).clear();
        this.components.set(i, new Component(allocBuffer));
        updateComponentOffsets(i);
        return this;
    }

    public CompositeByteBuf discardReadComponents() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            for (Component freeIfNecessary : this.components) {
                freeIfNecessary.freeIfNecessary();
            }
            this.components.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int componentIndex = toComponentIndex(readerIndex);
        for (int i = 0; i < componentIndex; i++) {
            ((Component) this.components.get(i)).freeIfNecessary();
        }
        this.components.subList(0, componentIndex).clear();
        int i2 = ((Component) this.components.get(0)).offset;
        updateComponentOffsets(0);
        setIndex(readerIndex - i2, writerIndex - i2);
        adjustMarkers(i2);
        return this;
    }

    public CompositeByteBuf discardReadBytes() {
        ensureAccessible();
        int readerIndex = readerIndex();
        if (readerIndex == 0) {
            return this;
        }
        int writerIndex = writerIndex();
        if (readerIndex == writerIndex && writerIndex == capacity()) {
            for (Component freeIfNecessary : this.components) {
                freeIfNecessary.freeIfNecessary();
            }
            this.components.clear();
            setIndex(0, 0);
            adjustMarkers(readerIndex);
            return this;
        }
        int componentIndex = toComponentIndex(readerIndex);
        for (int i = 0; i < componentIndex; i++) {
            ((Component) this.components.get(i)).freeIfNecessary();
        }
        this.components.subList(0, componentIndex).clear();
        Component component = (Component) this.components.get(0);
        int i2 = readerIndex - component.offset;
        if (i2 == component.length) {
            this.components.remove(0);
        } else {
            this.components.set(0, new Component(component.buf.slice(i2, component.length - i2)));
        }
        updateComponentOffsets(0);
        setIndex(0, writerIndex - readerIndex);
        adjustMarkers(readerIndex);
        return this;
    }

    private ByteBuf allocBuffer(int i) {
        return this.direct ? alloc().directBuffer(i) : alloc().heapBuffer(i);
    }

    public String toString() {
        String abstractReferenceCountedByteBuf = super.toString();
        String substring = abstractReferenceCountedByteBuf.substring(0, abstractReferenceCountedByteBuf.length() - 1);
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append(", components=");
        sb.append(this.components.size());
        sb.append(')');
        return sb.toString();
    }

    public CompositeByteBuf readerIndex(int i) {
        return (CompositeByteBuf) super.readerIndex(i);
    }

    public CompositeByteBuf writerIndex(int i) {
        return (CompositeByteBuf) super.writerIndex(i);
    }

    public CompositeByteBuf setIndex(int i, int i2) {
        return (CompositeByteBuf) super.setIndex(i, i2);
    }

    public CompositeByteBuf clear() {
        return (CompositeByteBuf) super.clear();
    }

    public CompositeByteBuf markReaderIndex() {
        return (CompositeByteBuf) super.markReaderIndex();
    }

    public CompositeByteBuf resetReaderIndex() {
        return (CompositeByteBuf) super.resetReaderIndex();
    }

    public CompositeByteBuf markWriterIndex() {
        return (CompositeByteBuf) super.markWriterIndex();
    }

    public CompositeByteBuf resetWriterIndex() {
        return (CompositeByteBuf) super.resetWriterIndex();
    }

    public CompositeByteBuf ensureWritable(int i) {
        return (CompositeByteBuf) super.ensureWritable(i);
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf) {
        return (CompositeByteBuf) super.getBytes(i, byteBuf);
    }

    public CompositeByteBuf getBytes(int i, ByteBuf byteBuf, int i2) {
        return (CompositeByteBuf) super.getBytes(i, byteBuf, i2);
    }

    public CompositeByteBuf getBytes(int i, byte[] bArr) {
        return (CompositeByteBuf) super.getBytes(i, bArr);
    }

    public CompositeByteBuf setBoolean(int i, boolean z) {
        return (CompositeByteBuf) super.setBoolean(i, z);
    }

    public CompositeByteBuf setChar(int i, int i2) {
        return (CompositeByteBuf) super.setChar(i, i2);
    }

    public CompositeByteBuf setFloat(int i, float f) {
        return (CompositeByteBuf) super.setFloat(i, f);
    }

    public CompositeByteBuf setDouble(int i, double d) {
        return (CompositeByteBuf) super.setDouble(i, d);
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf) {
        return (CompositeByteBuf) super.setBytes(i, byteBuf);
    }

    public CompositeByteBuf setBytes(int i, ByteBuf byteBuf, int i2) {
        return (CompositeByteBuf) super.setBytes(i, byteBuf, i2);
    }

    public CompositeByteBuf setBytes(int i, byte[] bArr) {
        return (CompositeByteBuf) super.setBytes(i, bArr);
    }

    public CompositeByteBuf setZero(int i, int i2) {
        return (CompositeByteBuf) super.setZero(i, i2);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf) super.readBytes(byteBuf);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i) {
        return (CompositeByteBuf) super.readBytes(byteBuf, i);
    }

    public CompositeByteBuf readBytes(ByteBuf byteBuf, int i, int i2) {
        return (CompositeByteBuf) super.readBytes(byteBuf, i, i2);
    }

    public CompositeByteBuf readBytes(byte[] bArr) {
        return (CompositeByteBuf) super.readBytes(bArr);
    }

    public CompositeByteBuf readBytes(byte[] bArr, int i, int i2) {
        return (CompositeByteBuf) super.readBytes(bArr, i, i2);
    }

    public CompositeByteBuf readBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf) super.readBytes(byteBuffer);
    }

    public CompositeByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        return (CompositeByteBuf) super.readBytes(outputStream, i);
    }

    public CompositeByteBuf skipBytes(int i) {
        return (CompositeByteBuf) super.skipBytes(i);
    }

    public CompositeByteBuf writeBoolean(boolean z) {
        return (CompositeByteBuf) super.writeBoolean(z);
    }

    public CompositeByteBuf writeByte(int i) {
        return (CompositeByteBuf) super.writeByte(i);
    }

    public CompositeByteBuf writeShort(int i) {
        return (CompositeByteBuf) super.writeShort(i);
    }

    public CompositeByteBuf writeMedium(int i) {
        return (CompositeByteBuf) super.writeMedium(i);
    }

    public CompositeByteBuf writeInt(int i) {
        return (CompositeByteBuf) super.writeInt(i);
    }

    public CompositeByteBuf writeLong(long j) {
        return (CompositeByteBuf) super.writeLong(j);
    }

    public CompositeByteBuf writeChar(int i) {
        return (CompositeByteBuf) super.writeChar(i);
    }

    public CompositeByteBuf writeFloat(float f) {
        return (CompositeByteBuf) super.writeFloat(f);
    }

    public CompositeByteBuf writeDouble(double d) {
        return (CompositeByteBuf) super.writeDouble(d);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf) {
        return (CompositeByteBuf) super.writeBytes(byteBuf);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return (CompositeByteBuf) super.writeBytes(byteBuf, i);
    }

    public CompositeByteBuf writeBytes(ByteBuf byteBuf, int i, int i2) {
        return (CompositeByteBuf) super.writeBytes(byteBuf, i, i2);
    }

    public CompositeByteBuf writeBytes(byte[] bArr) {
        return (CompositeByteBuf) super.writeBytes(bArr);
    }

    public CompositeByteBuf writeBytes(byte[] bArr, int i, int i2) {
        return (CompositeByteBuf) super.writeBytes(bArr, i, i2);
    }

    public CompositeByteBuf writeBytes(ByteBuffer byteBuffer) {
        return (CompositeByteBuf) super.writeBytes(byteBuffer);
    }

    public CompositeByteBuf writeZero(int i) {
        return (CompositeByteBuf) super.writeZero(i);
    }

    public CompositeByteBuf retain(int i) {
        return (CompositeByteBuf) super.retain(i);
    }

    public CompositeByteBuf retain() {
        return (CompositeByteBuf) super.retain();
    }

    public ByteBuffer[] nioBuffers() {
        return nioBuffers(readerIndex(), readableBytes());
    }

    public CompositeByteBuf discardSomeReadBytes() {
        return discardReadComponents();
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        if (!this.freed) {
            this.freed = true;
            int size = this.components.size();
            for (int i = 0; i < size; i++) {
                ((Component) this.components.get(i)).freeIfNecessary();
            }
        }
    }
}
