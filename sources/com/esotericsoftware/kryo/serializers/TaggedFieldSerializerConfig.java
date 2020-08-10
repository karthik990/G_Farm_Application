package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.minlog.Log;

public class TaggedFieldSerializerConfig extends FieldSerializerConfig {
    private boolean skipUnknownTags = false;

    @Deprecated
    public boolean isIgnoreUnknownTags() {
        return false;
    }

    @Deprecated
    public void setIgnoreUnknownTags(boolean z) {
    }

    public void setSkipUnknownTags(boolean z) {
        this.skipUnknownTags = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("setSkipUnknownTags: ");
            sb.append(z);
            Log.trace("kryo.TaggedFieldSerializerConfig", sb.toString());
        }
    }

    public boolean isSkipUnknownTags() {
        return this.skipUnknownTags;
    }

    /* access modifiers changed from: protected */
    public TaggedFieldSerializerConfig clone() {
        return (TaggedFieldSerializerConfig) super.clone();
    }
}
