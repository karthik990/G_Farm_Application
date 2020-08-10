package com.esotericsoftware.kryo.pool;

import com.esotericsoftware.kryo.Kryo;
import java.util.Queue;

class KryoPoolQueueImpl implements KryoPool {
    private final KryoFactory factory;
    private final Queue<Kryo> queue;

    KryoPoolQueueImpl(KryoFactory kryoFactory, Queue<Kryo> queue2) {
        this.factory = kryoFactory;
        this.queue = queue2;
    }

    public int size() {
        return this.queue.size();
    }

    public Kryo borrow() {
        Kryo kryo = (Kryo) this.queue.poll();
        if (kryo != null) {
            return kryo;
        }
        return this.factory.create();
    }

    public void release(Kryo kryo) {
        this.queue.offer(kryo);
    }

    public <T> T run(KryoCallback<T> kryoCallback) {
        Kryo borrow = borrow();
        try {
            return kryoCallback.execute(borrow);
        } finally {
            release(borrow);
        }
    }

    public void clear() {
        this.queue.clear();
    }
}
