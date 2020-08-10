package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonErrorContainer;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import java.io.IOException;

public abstract class AbstractGoogleJsonClientRequest<T> extends AbstractGoogleClientRequest<T> {
    private final Object jsonContent;

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v2, types: [com.google.api.client.http.json.JsonHttpContent] */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.api.client.http.HttpContent] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.lang.String, com.google.api.client.http.json.JsonHttpContent]
      uses: [?[OBJECT, ARRAY], java.lang.String]
      mth insns count: 21
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected AbstractGoogleJsonClientRequest(com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient r8, java.lang.String r9, java.lang.String r10, java.lang.Object r11, java.lang.Class<T> r12) {
        /*
            r7 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0005
        L_0x0003:
            r5 = r0
            goto L_0x0024
        L_0x0005:
            com.google.api.client.http.json.JsonHttpContent r1 = new com.google.api.client.http.json.JsonHttpContent
            com.google.api.client.json.JsonFactory r2 = r8.getJsonFactory()
            r1.<init>(r2, r11)
            com.google.api.client.json.JsonObjectParser r2 = r8.getObjectParser()
            java.util.Set r2 = r2.getWrapperKeys()
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            java.lang.String r0 = "data"
        L_0x001f:
            com.google.api.client.http.json.JsonHttpContent r0 = r1.setWrapperKey(r0)
            goto L_0x0003
        L_0x0024:
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r6 = r12
            r1.<init>(r2, r3, r4, r5, r6)
            r7.jsonContent = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest.<init>(com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient, java.lang.String, java.lang.String, java.lang.Object, java.lang.Class):void");
    }

    public AbstractGoogleJsonClient getAbstractGoogleClient() {
        return (AbstractGoogleJsonClient) super.getAbstractGoogleClient();
    }

    public AbstractGoogleJsonClientRequest<T> setDisableGZipContent(boolean z) {
        return (AbstractGoogleJsonClientRequest) super.setDisableGZipContent(z);
    }

    public AbstractGoogleJsonClientRequest<T> setRequestHeaders(HttpHeaders httpHeaders) {
        return (AbstractGoogleJsonClientRequest) super.setRequestHeaders(httpHeaders);
    }

    public final void queue(BatchRequest batchRequest, JsonBatchCallback<T> jsonBatchCallback) throws IOException {
        super.queue(batchRequest, GoogleJsonErrorContainer.class, jsonBatchCallback);
    }

    /* access modifiers changed from: protected */
    public GoogleJsonResponseException newExceptionOnError(HttpResponse httpResponse) {
        return GoogleJsonResponseException.from(getAbstractGoogleClient().getJsonFactory(), httpResponse);
    }

    public Object getJsonContent() {
        return this.jsonContent;
    }

    public AbstractGoogleJsonClientRequest<T> set(String str, Object obj) {
        return (AbstractGoogleJsonClientRequest) super.set(str, obj);
    }
}
