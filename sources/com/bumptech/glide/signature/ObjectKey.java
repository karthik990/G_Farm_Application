package com.bumptech.glide.signature;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

public final class ObjectKey implements Key {
    private final Object object;

    public ObjectKey(Object obj) {
        this.object = Preconditions.checkNotNull(obj);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ObjectKey{object=");
        sb.append(this.object);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectKey)) {
            return false;
        }
        return this.object.equals(((ObjectKey) obj).object);
    }

    public int hashCode() {
        return this.object.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(this.object.toString().getBytes(CHARSET));
    }
}
