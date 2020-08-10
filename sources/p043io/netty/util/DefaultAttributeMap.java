package p043io.netty.util;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* renamed from: io.netty.util.DefaultAttributeMap */
public class DefaultAttributeMap implements AttributeMap {
    private static final int BUCKET_SIZE = 4;
    private static final int MASK = 3;
    private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
    private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;

    /* renamed from: io.netty.util.DefaultAttributeMap$DefaultAttribute */
    private static final class DefaultAttribute<T> extends AtomicReference<T> implements Attribute<T> {
        private static final long serialVersionUID = -2661411462200283011L;
        private final DefaultAttribute<?> head;
        /* access modifiers changed from: private */
        public final AttributeKey<T> key;
        /* access modifiers changed from: private */
        public DefaultAttribute<?> next;
        /* access modifiers changed from: private */
        public DefaultAttribute<?> prev;
        /* access modifiers changed from: private */
        public volatile boolean removed;

        DefaultAttribute(DefaultAttribute<?> defaultAttribute, AttributeKey<T> attributeKey) {
            this.head = defaultAttribute;
            this.key = attributeKey;
        }

        DefaultAttribute() {
            this.head = this;
            this.key = null;
        }

        public AttributeKey<T> key() {
            return this.key;
        }

        public T setIfAbsent(T t) {
            T t2;
            do {
                t2 = null;
                if (compareAndSet(null, t)) {
                    break;
                }
                t2 = get();
            } while (t2 == null);
            return t2;
        }

        public T getAndRemove() {
            this.removed = true;
            T andSet = getAndSet(null);
            remove0();
            return andSet;
        }

        public void remove() {
            this.removed = true;
            set(null);
            remove0();
        }

        private void remove0() {
            synchronized (this.head) {
                if (this.prev != null) {
                    this.prev.next = this.next;
                    if (this.next != null) {
                        this.next.prev = this.prev;
                    }
                    this.prev = null;
                    this.next = null;
                }
            }
        }
    }

    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        if (attributeKey != null) {
            AtomicReferenceArray<DefaultAttribute<?>> atomicReferenceArray = this.attributes;
            if (atomicReferenceArray == null) {
                atomicReferenceArray = new AtomicReferenceArray<>(4);
                if (!updater.compareAndSet(this, null, atomicReferenceArray)) {
                    atomicReferenceArray = this.attributes;
                }
            }
            int index = index(attributeKey);
            DefaultAttribute defaultAttribute = (DefaultAttribute) atomicReferenceArray.get(index);
            if (defaultAttribute == null) {
                DefaultAttribute defaultAttribute2 = new DefaultAttribute();
                DefaultAttribute defaultAttribute3 = new DefaultAttribute(defaultAttribute2, attributeKey);
                defaultAttribute2.next = defaultAttribute3;
                defaultAttribute3.prev = defaultAttribute2;
                if (atomicReferenceArray.compareAndSet(index, null, defaultAttribute2)) {
                    return defaultAttribute3;
                }
                defaultAttribute = (DefaultAttribute) atomicReferenceArray.get(index);
            }
            synchronized (defaultAttribute) {
                DefaultAttribute defaultAttribute4 = defaultAttribute;
                while (true) {
                    DefaultAttribute access$000 = defaultAttribute4.next;
                    if (access$000 == null) {
                        DefaultAttribute defaultAttribute5 = new DefaultAttribute(defaultAttribute, attributeKey);
                        defaultAttribute4.next = defaultAttribute5;
                        defaultAttribute5.prev = defaultAttribute4;
                        return defaultAttribute5;
                    } else if (access$000.key == attributeKey && !access$000.removed) {
                        return access$000;
                    } else {
                        defaultAttribute4 = access$000;
                    }
                }
            }
        } else {
            throw new NullPointerException("key");
        }
    }

    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        if (attributeKey != null) {
            AtomicReferenceArray<DefaultAttribute<?>> atomicReferenceArray = this.attributes;
            if (atomicReferenceArray == null) {
                return false;
            }
            DefaultAttribute defaultAttribute = (DefaultAttribute) atomicReferenceArray.get(index(attributeKey));
            if (defaultAttribute == null) {
                return false;
            }
            synchronized (defaultAttribute) {
                for (DefaultAttribute access$000 = defaultAttribute.next; access$000 != null; access$000 = access$000.next) {
                    if (access$000.key == attributeKey && !access$000.removed) {
                        return true;
                    }
                }
                return false;
            }
        }
        throw new NullPointerException("key");
    }

    private static int index(AttributeKey<?> attributeKey) {
        return attributeKey.mo67945id() & 3;
    }
}
