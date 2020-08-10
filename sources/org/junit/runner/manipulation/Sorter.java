package org.junit.runner.manipulation;

import java.util.Comparator;
import org.junit.runner.Description;

public class Sorter implements Comparator<Description> {
    public static final Sorter NULL = new Sorter(new Comparator<Description>() {
        public int compare(Description description, Description description2) {
            return 0;
        }
    });
    private final Comparator<Description> comparator;

    public Sorter(Comparator<Description> comparator2) {
        this.comparator = comparator2;
    }

    public void apply(Object obj) {
        if (obj instanceof Sortable) {
            ((Sortable) obj).sort(this);
        }
    }

    public int compare(Description description, Description description2) {
        return this.comparator.compare(description, description2);
    }
}
