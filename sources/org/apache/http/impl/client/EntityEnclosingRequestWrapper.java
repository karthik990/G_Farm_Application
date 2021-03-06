package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.ProtocolException;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.protocol.HTTP;

@Deprecated
public class EntityEnclosingRequestWrapper extends RequestWrapper implements HttpEntityEnclosingRequest {
    /* access modifiers changed from: private */
    public boolean consumed;
    private HttpEntity entity;

    class EntityWrapper extends HttpEntityWrapper {
        EntityWrapper(HttpEntity httpEntity) {
            super(httpEntity);
        }

        public void consumeContent() throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            super.consumeContent();
        }

        public InputStream getContent() throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            return super.getContent();
        }

        public void writeTo(OutputStream outputStream) throws IOException {
            EntityEnclosingRequestWrapper.this.consumed = true;
            super.writeTo(outputStream);
        }
    }

    public EntityEnclosingRequestWrapper(HttpEntityEnclosingRequest httpEntityEnclosingRequest) throws ProtocolException {
        super(httpEntityEnclosingRequest);
        setEntity(httpEntityEnclosingRequest.getEntity());
    }

    public HttpEntity getEntity() {
        return this.entity;
    }

    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity != null ? new EntityWrapper(httpEntity) : null;
        this.consumed = false;
    }

    public boolean expectContinue() {
        Header firstHeader = getFirstHeader("Expect");
        if (firstHeader != null) {
            if (HTTP.EXPECT_CONTINUE.equalsIgnoreCase(firstHeader.getValue())) {
                return true;
            }
        }
        return false;
    }

    public boolean isRepeatable() {
        HttpEntity httpEntity = this.entity;
        return httpEntity == null || httpEntity.isRepeatable() || !this.consumed;
    }
}
