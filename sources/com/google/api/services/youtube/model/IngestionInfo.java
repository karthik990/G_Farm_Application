package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public final class IngestionInfo extends GenericJson {
    @Key
    private String backupIngestionAddress;
    @Key
    private String ingestionAddress;
    @Key
    private String streamName;

    public String getBackupIngestionAddress() {
        return this.backupIngestionAddress;
    }

    public IngestionInfo setBackupIngestionAddress(String str) {
        this.backupIngestionAddress = str;
        return this;
    }

    public String getIngestionAddress() {
        return this.ingestionAddress;
    }

    public IngestionInfo setIngestionAddress(String str) {
        this.ingestionAddress = str;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public IngestionInfo setStreamName(String str) {
        this.streamName = str;
        return this;
    }

    public IngestionInfo set(String str, Object obj) {
        return (IngestionInfo) super.set(str, obj);
    }

    public IngestionInfo clone() {
        return (IngestionInfo) super.clone();
    }
}
