package p043io.netty.handler.codec;

import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.codec.ProtocolDetectionResult */
public final class ProtocolDetectionResult<T> {
    private static final ProtocolDetectionResult INVALID = new ProtocolDetectionResult(ProtocolDetectionState.INVALID, null);
    private static final ProtocolDetectionResult NEEDS_MORE_DATE = new ProtocolDetectionResult(ProtocolDetectionState.NEEDS_MORE_DATA, null);
    private final T result;
    private final ProtocolDetectionState state;

    public static <T> ProtocolDetectionResult<T> needsMoreData() {
        return NEEDS_MORE_DATE;
    }

    public static <T> ProtocolDetectionResult<T> invalid() {
        return INVALID;
    }

    public static <T> ProtocolDetectionResult<T> detected(T t) {
        return new ProtocolDetectionResult<>(ProtocolDetectionState.DETECTED, ObjectUtil.checkNotNull(t, "protocol"));
    }

    private ProtocolDetectionResult(ProtocolDetectionState protocolDetectionState, T t) {
        this.state = protocolDetectionState;
        this.result = t;
    }

    public ProtocolDetectionState state() {
        return this.state;
    }

    public T detectedProtocol() {
        return this.result;
    }
}
