package org.apache.http.client.methods;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.utils.CloneUtils;
import org.apache.http.protocol.HTTP;

public abstract class HttpEntityEnclosingRequestBase extends HttpRequestBase implements HttpEntityEnclosingRequest {
    private HttpEntity entity;

    public HttpEntity getEntity() {
        return this.entity;
    }

    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
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

    public Object clone() throws CloneNotSupportedException {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase) super.clone();
        HttpEntity httpEntity = this.entity;
        if (httpEntity != null) {
            httpEntityEnclosingRequestBase.entity = (HttpEntity) CloneUtils.cloneObject(httpEntity);
        }
        return httpEntityEnclosingRequestBase;
    }
}
