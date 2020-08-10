package com.esotericsoftware.kryo.pool;

import com.esotericsoftware.kryo.Kryo;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface KryoPool {

    public static class Builder {
        private final KryoFactory factory;
        private Queue<Kryo> queue = new ConcurrentLinkedQueue();
        private boolean softReferences;

        public Builder(KryoFactory kryoFactory) {
            if (kryoFactory != null) {
                this.factory = kryoFactory;
                return;
            }
            throw new IllegalArgumentException("factory must not be null");
        }

        public Builder queue(Queue<Kryo> queue2) {
            if (queue2 != null) {
                this.queue = queue2;
                return this;
            }
            throw new IllegalArgumentException("queue must not be null");
        }

        public Builder softReferences() {
            this.softReferences = true;
            return this;
        }

        public KryoPool build() {
            return new KryoPoolQueueImpl(this.factory, this.softReferences ? new SoftReferenceQueue(this.queue) : this.queue);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getName());
            sb.append("[queue.class=");
            sb.append(this.queue.getClass());
            sb.append(", softReferences=");
            sb.append(this.softReferences);
            sb.append("]");
            return sb.toString();
        }
    }

    Kryo borrow();

    void release(Kryo kryo);

    <T> T run(KryoCallback<T> kryoCallback);
}
