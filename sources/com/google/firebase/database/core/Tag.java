package com.google.firebase.database.core;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Tag {
    private final long tagNumber;

    public Tag(long j) {
        this.tagNumber = j;
    }

    public long getTagNumber() {
        return this.tagNumber;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tag{tagNumber=");
        sb.append(this.tagNumber);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.tagNumber == ((Tag) obj).tagNumber;
    }

    public int hashCode() {
        long j = this.tagNumber;
        return (int) (j ^ (j >>> 32));
    }
}
