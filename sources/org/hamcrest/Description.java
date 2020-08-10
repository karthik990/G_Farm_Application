package org.hamcrest;

public interface Description {
    public static final Description NONE = new NullDescription();

    public static final class NullDescription implements Description {
        public Description appendDescriptionOf(SelfDescribing selfDescribing) {
            return this;
        }

        public Description appendList(String str, String str2, String str3, Iterable<? extends SelfDescribing> iterable) {
            return this;
        }

        public Description appendText(String str) {
            return this;
        }

        public Description appendValue(Object obj) {
            return this;
        }

        public <T> Description appendValueList(String str, String str2, String str3, Iterable<T> iterable) {
            return this;
        }

        public <T> Description appendValueList(String str, String str2, String str3, T... tArr) {
            return this;
        }

        public String toString() {
            return "";
        }
    }

    Description appendDescriptionOf(SelfDescribing selfDescribing);

    Description appendList(String str, String str2, String str3, Iterable<? extends SelfDescribing> iterable);

    Description appendText(String str);

    Description appendValue(Object obj);

    <T> Description appendValueList(String str, String str2, String str3, Iterable<T> iterable);

    <T> Description appendValueList(String str, String str2, String str3, T... tArr);
}
