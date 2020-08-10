package p043io.netty.handler.ssl;

import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

/* renamed from: io.netty.handler.ssl.JdkSslEngine */
class JdkSslEngine extends SSLEngine {
    private final SSLEngine engine;
    private final JdkSslSession session;

    JdkSslEngine(SSLEngine sSLEngine) {
        this.engine = sSLEngine;
        this.session = new JdkSslSession(sSLEngine);
    }

    public JdkSslSession getSession() {
        return this.session;
    }

    public SSLEngine getWrappedEngine() {
        return this.engine;
    }

    public void closeInbound() throws SSLException {
        this.engine.closeInbound();
    }

    public void closeOutbound() {
        this.engine.closeOutbound();
    }

    public String getPeerHost() {
        return this.engine.getPeerHost();
    }

    public int getPeerPort() {
        return this.engine.getPeerPort();
    }

    public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.wrap(byteBuffer, byteBuffer2);
    }

    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(byteBufferArr, byteBuffer);
    }

    public SSLEngineResult wrap(ByteBuffer[] byteBufferArr, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
        return this.engine.wrap(byteBufferArr, i, i2, byteBuffer);
    }

    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBuffer2);
    }

    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBufferArr);
    }

    public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException {
        return this.engine.unwrap(byteBuffer, byteBufferArr, i, i2);
    }

    public Runnable getDelegatedTask() {
        return this.engine.getDelegatedTask();
    }

    public boolean isInboundDone() {
        return this.engine.isInboundDone();
    }

    public boolean isOutboundDone() {
        return this.engine.isOutboundDone();
    }

    public String[] getSupportedCipherSuites() {
        return this.engine.getSupportedCipherSuites();
    }

    public String[] getEnabledCipherSuites() {
        return this.engine.getEnabledCipherSuites();
    }

    public void setEnabledCipherSuites(String[] strArr) {
        this.engine.setEnabledCipherSuites(strArr);
    }

    public String[] getSupportedProtocols() {
        return this.engine.getSupportedProtocols();
    }

    public String[] getEnabledProtocols() {
        return this.engine.getEnabledProtocols();
    }

    public void setEnabledProtocols(String[] strArr) {
        this.engine.setEnabledProtocols(strArr);
    }

    public SSLSession getHandshakeSession() {
        return this.engine.getHandshakeSession();
    }

    public void beginHandshake() throws SSLException {
        this.engine.beginHandshake();
    }

    public HandshakeStatus getHandshakeStatus() {
        return this.engine.getHandshakeStatus();
    }

    public void setUseClientMode(boolean z) {
        this.engine.setUseClientMode(z);
    }

    public boolean getUseClientMode() {
        return this.engine.getUseClientMode();
    }

    public void setNeedClientAuth(boolean z) {
        this.engine.setNeedClientAuth(z);
    }

    public boolean getNeedClientAuth() {
        return this.engine.getNeedClientAuth();
    }

    public void setWantClientAuth(boolean z) {
        this.engine.setWantClientAuth(z);
    }

    public boolean getWantClientAuth() {
        return this.engine.getWantClientAuth();
    }

    public void setEnableSessionCreation(boolean z) {
        this.engine.setEnableSessionCreation(z);
    }

    public boolean getEnableSessionCreation() {
        return this.engine.getEnableSessionCreation();
    }

    public SSLParameters getSSLParameters() {
        return this.engine.getSSLParameters();
    }

    public void setSSLParameters(SSLParameters sSLParameters) {
        this.engine.setSSLParameters(sSLParameters);
    }
}
