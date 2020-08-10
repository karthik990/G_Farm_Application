package p043io.netty.handler.timeout;

import com.braintreepayments.api.models.PostalAddressParser;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.timeout.IdleStateEvent */
public class IdleStateEvent {
    public static final IdleStateEvent ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, false);
    public static final IdleStateEvent FIRST_ALL_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.ALL_IDLE, true);
    public static final IdleStateEvent FIRST_READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, true);
    public static final IdleStateEvent FIRST_WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, true);
    public static final IdleStateEvent READER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.READER_IDLE, false);
    public static final IdleStateEvent WRITER_IDLE_STATE_EVENT = new IdleStateEvent(IdleState.WRITER_IDLE, false);
    private final boolean first;
    private final IdleState state;

    protected IdleStateEvent(IdleState idleState, boolean z) {
        this.state = (IdleState) ObjectUtil.checkNotNull(idleState, PostalAddressParser.REGION_KEY);
        this.first = z;
    }

    public IdleState state() {
        return this.state;
    }

    public boolean isFirst() {
        return this.first;
    }
}
