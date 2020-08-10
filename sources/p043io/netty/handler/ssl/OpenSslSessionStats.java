package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.SSLContext;

/* renamed from: io.netty.handler.ssl.OpenSslSessionStats */
public final class OpenSslSessionStats {
    private final ReferenceCountedOpenSslContext context;

    OpenSslSessionStats(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        this.context = referenceCountedOpenSslContext;
    }

    public long number() {
        return SSLContext.sessionNumber(this.context.ctx);
    }

    public long connect() {
        return SSLContext.sessionConnect(this.context.ctx);
    }

    public long connectGood() {
        return SSLContext.sessionConnectGood(this.context.ctx);
    }

    public long connectRenegotiate() {
        return SSLContext.sessionConnectRenegotiate(this.context.ctx);
    }

    public long accept() {
        return SSLContext.sessionAccept(this.context.ctx);
    }

    public long acceptGood() {
        return SSLContext.sessionAcceptGood(this.context.ctx);
    }

    public long acceptRenegotiate() {
        return SSLContext.sessionAcceptRenegotiate(this.context.ctx);
    }

    public long hits() {
        return SSLContext.sessionHits(this.context.ctx);
    }

    public long cbHits() {
        return SSLContext.sessionCbHits(this.context.ctx);
    }

    public long misses() {
        return SSLContext.sessionMisses(this.context.ctx);
    }

    public long timeouts() {
        return SSLContext.sessionTimeouts(this.context.ctx);
    }

    public long cacheFull() {
        return SSLContext.sessionCacheFull(this.context.ctx);
    }

    public long ticketKeyFail() {
        return SSLContext.sessionTicketKeyFail(this.context.ctx);
    }

    public long ticketKeyNew() {
        return SSLContext.sessionTicketKeyNew(this.context.ctx);
    }

    public long ticketKeyRenew() {
        return SSLContext.sessionTicketKeyRenew(this.context.ctx);
    }

    public long ticketKeyResume() {
        return SSLContext.sessionTicketKeyResume(this.context.ctx);
    }
}
