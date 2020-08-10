package lib.android.paypal.com.magnessdk;

public enum MagnesSource {
    DEFAULT(-1),
    PAYPAL(10),
    EBAY(11),
    BRAINTREE(12);
    
    private int version;

    private MagnesSource(int i) {
        this.version = i;
    }

    public int getVersion() {
        return this.version;
    }
}
