package org.junit.runner.manipulation;

import java.util.Iterator;
import org.junit.runner.Description;

public abstract class Filter {
    public static final Filter ALL = new Filter() {
        public void apply(Object obj) throws NoTestsRemainException {
        }

        public String describe() {
            return "all tests";
        }

        public Filter intersect(Filter filter) {
            return filter;
        }

        public boolean shouldRun(Description description) {
            return true;
        }
    };

    public abstract String describe();

    public abstract boolean shouldRun(Description description);

    public static Filter matchMethodDescription(final Description description) {
        return new Filter() {
            public boolean shouldRun(Description description) {
                if (description.isTest()) {
                    return description.equals(description);
                }
                Iterator it = description.getChildren().iterator();
                while (it.hasNext()) {
                    if (shouldRun((Description) it.next())) {
                        return true;
                    }
                }
                return false;
            }

            public String describe() {
                return String.format("Method %s", new Object[]{description.getDisplayName()});
            }
        };
    }

    public void apply(Object obj) throws NoTestsRemainException {
        if (obj instanceof Filterable) {
            ((Filterable) obj).filter(this);
        }
    }

    public Filter intersect(final Filter filter) {
        return (filter == this || filter == ALL) ? this : new Filter() {
            public boolean shouldRun(Description description) {
                return this.shouldRun(description) && filter.shouldRun(description);
            }

            public String describe() {
                StringBuilder sb = new StringBuilder();
                sb.append(this.describe());
                sb.append(" and ");
                sb.append(filter.describe());
                return sb.toString();
            }
        };
    }
}
