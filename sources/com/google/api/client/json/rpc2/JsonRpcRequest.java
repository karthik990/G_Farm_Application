package com.google.api.client.json.rpc2;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public class JsonRpcRequest extends GenericData {
    @Key

    /* renamed from: id */
    private Object f1786id;
    @Key
    private final String jsonrpc = "2.0";
    @Key
    private String method;
    @Key
    private Object params;

    public String getVersion() {
        return "2.0";
    }

    public Object getId() {
        return this.f1786id;
    }

    public void setId(Object obj) {
        this.f1786id = obj;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public Object getParameters() {
        return this.params;
    }

    public void setParameters(Object obj) {
        this.params = obj;
    }

    public JsonRpcRequest set(String str, Object obj) {
        return (JsonRpcRequest) super.set(str, obj);
    }

    public JsonRpcRequest clone() {
        return (JsonRpcRequest) super.clone();
    }
}
