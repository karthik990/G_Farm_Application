package p043io.opencensus.tags;

import p043io.opencensus.tags.TagMetadata.TagTtl;

/* renamed from: io.opencensus.tags.AutoValue_TagMetadata */
final class AutoValue_TagMetadata extends TagMetadata {
    private final TagTtl tagTtl;

    AutoValue_TagMetadata(TagTtl tagTtl2) {
        if (tagTtl2 != null) {
            this.tagTtl = tagTtl2;
            return;
        }
        throw new NullPointerException("Null tagTtl");
    }

    public TagTtl getTagTtl() {
        return this.tagTtl;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TagMetadata{tagTtl=");
        sb.append(this.tagTtl);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TagMetadata)) {
            return false;
        }
        return this.tagTtl.equals(((TagMetadata) obj).getTagTtl());
    }

    public int hashCode() {
        return this.tagTtl.hashCode() ^ 1000003;
    }
}
