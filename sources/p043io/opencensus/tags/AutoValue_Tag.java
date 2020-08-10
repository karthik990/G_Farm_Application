package p043io.opencensus.tags;

/* renamed from: io.opencensus.tags.AutoValue_Tag */
final class AutoValue_Tag extends Tag {
    private final TagKey key;
    private final TagMetadata tagMetadata;
    private final TagValue value;

    AutoValue_Tag(TagKey tagKey, TagValue tagValue, TagMetadata tagMetadata2) {
        if (tagKey != null) {
            this.key = tagKey;
            if (tagValue != null) {
                this.value = tagValue;
                if (tagMetadata2 != null) {
                    this.tagMetadata = tagMetadata2;
                    return;
                }
                throw new NullPointerException("Null tagMetadata");
            }
            throw new NullPointerException("Null value");
        }
        throw new NullPointerException("Null key");
    }

    public TagKey getKey() {
        return this.key;
    }

    public TagValue getValue() {
        return this.value;
    }

    public TagMetadata getTagMetadata() {
        return this.tagMetadata;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tag{key=");
        sb.append(this.key);
        sb.append(", value=");
        sb.append(this.value);
        sb.append(", tagMetadata=");
        sb.append(this.tagMetadata);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        if (!this.key.equals(tag.getKey()) || !this.value.equals(tag.getValue()) || !this.tagMetadata.equals(tag.getTagMetadata())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((this.key.hashCode() ^ 1000003) * 1000003) ^ this.value.hashCode()) * 1000003) ^ this.tagMetadata.hashCode();
    }
}
