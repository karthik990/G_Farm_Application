package p043io.netty.channel;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.channel.DefaultChannelConfig */
public class DefaultChannelConfig implements ChannelConfig {
    private static final AtomicIntegerFieldUpdater<DefaultChannelConfig> AUTOREAD_UPDATER;
    private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    private static final MessageSizeEstimator DEFAULT_MSG_SIZE_ESTIMATOR = DefaultMessageSizeEstimator.DEFAULT;
    private static final AtomicReferenceFieldUpdater<DefaultChannelConfig, WriteBufferWaterMark> WATERMARK_UPDATER;
    private volatile ByteBufAllocator allocator;
    private volatile boolean autoClose;
    private volatile int autoRead;
    protected final Channel channel;
    private volatile int connectTimeoutMillis;
    private volatile MessageSizeEstimator msgSizeEstimator;
    private volatile boolean pinEventExecutor;
    private volatile RecvByteBufAllocator rcvBufAllocator;
    private volatile WriteBufferWaterMark writeBufferWaterMark;
    private volatile int writeSpinCount;

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
    }

    static {
        Class<DefaultChannelConfig> cls = DefaultChannelConfig.class;
        AUTOREAD_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "autoRead");
        WATERMARK_UPDATER = AtomicReferenceFieldUpdater.newUpdater(cls, WriteBufferWaterMark.class, "writeBufferWaterMark");
    }

    public DefaultChannelConfig(Channel channel2) {
        this(channel2, new AdaptiveRecvByteBufAllocator());
    }

    protected DefaultChannelConfig(Channel channel2, RecvByteBufAllocator recvByteBufAllocator) {
        this.allocator = ByteBufAllocator.DEFAULT;
        this.msgSizeEstimator = DEFAULT_MSG_SIZE_ESTIMATOR;
        this.connectTimeoutMillis = DEFAULT_CONNECT_TIMEOUT;
        this.writeSpinCount = 16;
        this.autoRead = 1;
        this.autoClose = true;
        this.writeBufferWaterMark = WriteBufferWaterMark.DEFAULT;
        this.pinEventExecutor = true;
        setRecvByteBufAllocator(recvByteBufAllocator, channel2.metadata());
        this.channel = channel2;
    }

    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(null, ChannelOption.CONNECT_TIMEOUT_MILLIS, ChannelOption.MAX_MESSAGES_PER_READ, ChannelOption.WRITE_SPIN_COUNT, ChannelOption.ALLOCATOR, ChannelOption.AUTO_READ, ChannelOption.AUTO_CLOSE, ChannelOption.RCVBUF_ALLOCATOR, ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, ChannelOption.WRITE_BUFFER_WATER_MARK, ChannelOption.MESSAGE_SIZE_ESTIMATOR, ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
    }

    /* access modifiers changed from: protected */
    public Map<ChannelOption<?>, Object> getOptions(Map<ChannelOption<?>, Object> map, ChannelOption<?>... channelOptionArr) {
        if (map == null) {
            map = new IdentityHashMap<>();
        }
        for (ChannelOption<?> channelOption : channelOptionArr) {
            map.put(channelOption, getOption(channelOption));
        }
        return map;
    }

    public boolean setOptions(Map<ChannelOption<?>, ?> map) {
        if (map != null) {
            boolean z = true;
            for (Entry entry : map.entrySet()) {
                if (!setOption((ChannelOption) entry.getKey(), entry.getValue())) {
                    z = false;
                }
            }
            return z;
        }
        throw new NullPointerException("options");
    }

    public <T> T getOption(ChannelOption<T> channelOption) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        } else if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            return Integer.valueOf(getConnectTimeoutMillis());
        } else {
            if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
                return Integer.valueOf(getMaxMessagesPerRead());
            }
            if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
                return Integer.valueOf(getWriteSpinCount());
            }
            if (channelOption == ChannelOption.ALLOCATOR) {
                return getAllocator();
            }
            if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
                return getRecvByteBufAllocator();
            }
            if (channelOption == ChannelOption.AUTO_READ) {
                return Boolean.valueOf(isAutoRead());
            }
            if (channelOption == ChannelOption.AUTO_CLOSE) {
                return Boolean.valueOf(isAutoClose());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
                return Integer.valueOf(getWriteBufferHighWaterMark());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
                return Integer.valueOf(getWriteBufferLowWaterMark());
            }
            if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
                return getWriteBufferWaterMark();
            }
            if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
                return getMessageSizeEstimator();
            }
            if (channelOption == ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
                return Boolean.valueOf(getPinEventExecutorPerGroup());
            }
            return null;
        }
    }

    public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
        validate(channelOption, t);
        if (channelOption == ChannelOption.CONNECT_TIMEOUT_MILLIS) {
            setConnectTimeoutMillis(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.MAX_MESSAGES_PER_READ) {
            setMaxMessagesPerRead(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.WRITE_SPIN_COUNT) {
            setWriteSpinCount(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.ALLOCATOR) {
            setAllocator((ByteBufAllocator) t);
        } else if (channelOption == ChannelOption.RCVBUF_ALLOCATOR) {
            setRecvByteBufAllocator((RecvByteBufAllocator) t);
        } else if (channelOption == ChannelOption.AUTO_READ) {
            setAutoRead(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.AUTO_CLOSE) {
            setAutoClose(((Boolean) t).booleanValue());
        } else if (channelOption == ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK) {
            setWriteBufferHighWaterMark(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.WRITE_BUFFER_LOW_WATER_MARK) {
            setWriteBufferLowWaterMark(((Integer) t).intValue());
        } else if (channelOption == ChannelOption.WRITE_BUFFER_WATER_MARK) {
            setWriteBufferWaterMark((WriteBufferWaterMark) t);
        } else if (channelOption == ChannelOption.MESSAGE_SIZE_ESTIMATOR) {
            setMessageSizeEstimator((MessageSizeEstimator) t);
        } else if (channelOption != ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP) {
            return false;
        } else {
            setPinEventExecutorPerGroup(((Boolean) t).booleanValue());
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public <T> void validate(ChannelOption<T> channelOption, T t) {
        if (channelOption != null) {
            channelOption.validate(t);
            return;
        }
        throw new NullPointerException("option");
    }

    public int getConnectTimeoutMillis() {
        return this.connectTimeoutMillis;
    }

    public ChannelConfig setConnectTimeoutMillis(int i) {
        if (i >= 0) {
            this.connectTimeoutMillis = i;
            return this;
        }
        throw new IllegalArgumentException(String.format("connectTimeoutMillis: %d (expected: >= 0)", new Object[]{Integer.valueOf(i)}));
    }

    @Deprecated
    public int getMaxMessagesPerRead() {
        try {
            return ((MaxMessagesRecvByteBufAllocator) getRecvByteBufAllocator()).maxMessagesPerRead();
        } catch (ClassCastException e) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
        }
    }

    @Deprecated
    public ChannelConfig setMaxMessagesPerRead(int i) {
        try {
            ((MaxMessagesRecvByteBufAllocator) getRecvByteBufAllocator()).maxMessagesPerRead(i);
            return this;
        } catch (ClassCastException e) {
            throw new IllegalStateException("getRecvByteBufAllocator() must return an object of type MaxMessagesRecvByteBufAllocator", e);
        }
    }

    public int getWriteSpinCount() {
        return this.writeSpinCount;
    }

    public ChannelConfig setWriteSpinCount(int i) {
        if (i > 0) {
            this.writeSpinCount = i;
            return this;
        }
        throw new IllegalArgumentException("writeSpinCount must be a positive integer.");
    }

    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }

    public ChannelConfig setAllocator(ByteBufAllocator byteBufAllocator) {
        if (byteBufAllocator != null) {
            this.allocator = byteBufAllocator;
            return this;
        }
        throw new NullPointerException("allocator");
    }

    public <T extends RecvByteBufAllocator> T getRecvByteBufAllocator() {
        return this.rcvBufAllocator;
    }

    public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
        this.rcvBufAllocator = (RecvByteBufAllocator) ObjectUtil.checkNotNull(recvByteBufAllocator, "allocator");
        return this;
    }

    private void setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator, ChannelMetadata channelMetadata) {
        if (recvByteBufAllocator instanceof MaxMessagesRecvByteBufAllocator) {
            ((MaxMessagesRecvByteBufAllocator) recvByteBufAllocator).maxMessagesPerRead(channelMetadata.defaultMaxMessagesPerRead());
        } else if (recvByteBufAllocator == null) {
            throw new NullPointerException("allocator");
        }
        setRecvByteBufAllocator(recvByteBufAllocator);
    }

    public boolean isAutoRead() {
        return this.autoRead == 1;
    }

    public ChannelConfig setAutoRead(boolean z) {
        boolean z2 = true;
        if (AUTOREAD_UPDATER.getAndSet(this, z ? 1 : 0) != 1) {
            z2 = false;
        }
        if (z && !z2) {
            this.channel.read();
        } else if (!z && z2) {
            autoReadCleared();
        }
        return this;
    }

    public boolean isAutoClose() {
        return this.autoClose;
    }

    public ChannelConfig setAutoClose(boolean z) {
        this.autoClose = z;
        return this;
    }

    public int getWriteBufferHighWaterMark() {
        return this.writeBufferWaterMark.high();
    }

    public ChannelConfig setWriteBufferHighWaterMark(int i) {
        WriteBufferWaterMark writeBufferWaterMark2;
        if (i >= 0) {
            do {
                writeBufferWaterMark2 = this.writeBufferWaterMark;
                if (i < writeBufferWaterMark2.low()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("writeBufferHighWaterMark cannot be less than writeBufferLowWaterMark (");
                    sb.append(writeBufferWaterMark2.low());
                    sb.append("): ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                }
            } while (!WATERMARK_UPDATER.compareAndSet(this, writeBufferWaterMark2, new WriteBufferWaterMark(writeBufferWaterMark2.low(), i, false)));
            return this;
        }
        throw new IllegalArgumentException("writeBufferHighWaterMark must be >= 0");
    }

    public int getWriteBufferLowWaterMark() {
        return this.writeBufferWaterMark.low();
    }

    public ChannelConfig setWriteBufferLowWaterMark(int i) {
        WriteBufferWaterMark writeBufferWaterMark2;
        if (i >= 0) {
            do {
                writeBufferWaterMark2 = this.writeBufferWaterMark;
                if (i > writeBufferWaterMark2.high()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("writeBufferLowWaterMark cannot be greater than writeBufferHighWaterMark (");
                    sb.append(writeBufferWaterMark2.high());
                    sb.append("): ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                }
            } while (!WATERMARK_UPDATER.compareAndSet(this, writeBufferWaterMark2, new WriteBufferWaterMark(i, writeBufferWaterMark2.high(), false)));
            return this;
        }
        throw new IllegalArgumentException("writeBufferLowWaterMark must be >= 0");
    }

    public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark2) {
        this.writeBufferWaterMark = (WriteBufferWaterMark) ObjectUtil.checkNotNull(writeBufferWaterMark2, "writeBufferWaterMark");
        return this;
    }

    public WriteBufferWaterMark getWriteBufferWaterMark() {
        return this.writeBufferWaterMark;
    }

    public MessageSizeEstimator getMessageSizeEstimator() {
        return this.msgSizeEstimator;
    }

    public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
        if (messageSizeEstimator != null) {
            this.msgSizeEstimator = messageSizeEstimator;
            return this;
        }
        throw new NullPointerException("estimator");
    }

    private ChannelConfig setPinEventExecutorPerGroup(boolean z) {
        this.pinEventExecutor = z;
        return this;
    }

    private boolean getPinEventExecutorPerGroup() {
        return this.pinEventExecutor;
    }
}
