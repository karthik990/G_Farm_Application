package p043io.netty.util;

/* renamed from: io.netty.util.UncheckedBooleanSupplier */
public interface UncheckedBooleanSupplier extends BooleanSupplier {
    public static final UncheckedBooleanSupplier FALSE_SUPPLIER = new UncheckedBooleanSupplier() {
        public boolean get() {
            return false;
        }
    };
    public static final UncheckedBooleanSupplier TRUE_SUPPLIER = new UncheckedBooleanSupplier() {
        public boolean get() {
            return true;
        }
    };

    boolean get();
}
