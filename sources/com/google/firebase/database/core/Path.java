package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Path implements Iterable<ChildKey>, Comparable<Path> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Path EMPTY_PATH = new Path("");
    /* access modifiers changed from: private */
    public final int end;
    /* access modifiers changed from: private */
    public final ChildKey[] pieces;
    /* access modifiers changed from: private */
    public final int start;

    public static Path getRelative(Path path, Path path2) {
        ChildKey front = path.getFront();
        ChildKey front2 = path2.getFront();
        if (front == null) {
            return path2;
        }
        if (front.equals(front2)) {
            return getRelative(path.popFront(), path2.popFront());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("INTERNAL ERROR: ");
        sb.append(path2);
        sb.append(" is not contained in ");
        sb.append(path);
        throw new DatabaseException(sb.toString());
    }

    public static Path getEmptyPath() {
        return EMPTY_PATH;
    }

    public Path(ChildKey... childKeyArr) {
        this.pieces = (ChildKey[]) Arrays.copyOf(childKeyArr, childKeyArr.length);
        this.start = 0;
        this.end = childKeyArr.length;
        for (ChildKey childKey : childKeyArr) {
        }
    }

    public Path(List<String> list) {
        this.pieces = new ChildKey[list.size()];
        int i = 0;
        for (String fromString : list) {
            int i2 = i + 1;
            this.pieces[i] = ChildKey.fromString(fromString);
            i = i2;
        }
        this.start = 0;
        this.end = list.size();
    }

    public Path(String str) {
        String[] split = str.split("/", -1);
        int i = 0;
        for (String length : split) {
            if (length.length() > 0) {
                i++;
            }
        }
        this.pieces = new ChildKey[i];
        int i2 = 0;
        for (String str2 : split) {
            if (str2.length() > 0) {
                int i3 = i2 + 1;
                this.pieces[i2] = ChildKey.fromString(str2);
                i2 = i3;
            }
        }
        this.start = 0;
        this.end = this.pieces.length;
    }

    private Path(ChildKey[] childKeyArr, int i, int i2) {
        this.pieces = childKeyArr;
        this.start = i;
        this.end = i2;
    }

    public Path child(Path path) {
        int size = size() + path.size();
        ChildKey[] childKeyArr = new ChildKey[size];
        System.arraycopy(this.pieces, this.start, childKeyArr, 0, size());
        System.arraycopy(path.pieces, path.start, childKeyArr, size(), path.size());
        return new Path(childKeyArr, 0, size);
    }

    public Path child(ChildKey childKey) {
        int size = size();
        int i = size + 1;
        ChildKey[] childKeyArr = new ChildKey[i];
        System.arraycopy(this.pieces, this.start, childKeyArr, 0, size);
        childKeyArr[size] = childKey;
        return new Path(childKeyArr, 0, i);
    }

    public String toString() {
        String str = "/";
        if (isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            sb.append(str);
            sb.append(this.pieces[i].asString());
        }
        return sb.toString();
    }

    public String wireFormat() {
        String str = "/";
        if (isEmpty()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = this.start; i < this.end; i++) {
            if (i > this.start) {
                sb.append(str);
            }
            sb.append(this.pieces[i].asString());
        }
        return sb.toString();
    }

    public List<String> asList() {
        ArrayList arrayList = new ArrayList(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            arrayList.add(((ChildKey) it.next()).asString());
        }
        return arrayList;
    }

    public ChildKey getFront() {
        if (isEmpty()) {
            return null;
        }
        return this.pieces[this.start];
    }

    public Path popFront() {
        int i = this.start;
        if (!isEmpty()) {
            i++;
        }
        return new Path(this.pieces, i, this.end);
    }

    public Path getParent() {
        if (isEmpty()) {
            return null;
        }
        return new Path(this.pieces, this.start, this.end - 1);
    }

    public ChildKey getBack() {
        if (!isEmpty()) {
            return this.pieces[this.end - 1];
        }
        return null;
    }

    public boolean isEmpty() {
        return this.start >= this.end;
    }

    public int size() {
        return this.end - this.start;
    }

    public Iterator<ChildKey> iterator() {
        return new Iterator<ChildKey>() {
            int offset = Path.this.start;

            public boolean hasNext() {
                return this.offset < Path.this.end;
            }

            public ChildKey next() {
                if (hasNext()) {
                    ChildKey[] access$200 = Path.this.pieces;
                    int i = this.offset;
                    ChildKey childKey = access$200[i];
                    this.offset = i + 1;
                    return childKey;
                }
                throw new NoSuchElementException("No more elements.");
            }

            public void remove() {
                throw new UnsupportedOperationException("Can't remove component from immutable Path!");
            }
        };
    }

    public boolean contains(Path path) {
        if (size() > path.size()) {
            return false;
        }
        int i = this.start;
        int i2 = path.start;
        while (i < this.end) {
            if (!this.pieces[i].equals(path.pieces[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Path)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Path path = (Path) obj;
        if (size() != path.size()) {
            return false;
        }
        int i = this.start;
        int i2 = path.start;
        while (i < this.end && i2 < path.end) {
            if (!this.pieces[i].equals(path.pieces[i2])) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = this.start; i2 < this.end; i2++) {
            i = (i * 37) + this.pieces[i2].hashCode();
        }
        return i;
    }

    public int compareTo(Path path) {
        int i = this.start;
        int i2 = path.start;
        while (i < this.end && i2 < path.end) {
            int compareTo = this.pieces[i].compareTo(path.pieces[i2]);
            if (compareTo != 0) {
                return compareTo;
            }
            i++;
            i2++;
        }
        if (i == this.end && i2 == path.end) {
            return 0;
        }
        return i == this.end ? -1 : 1;
    }
}
