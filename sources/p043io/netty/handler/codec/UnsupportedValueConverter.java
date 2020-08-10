package p043io.netty.handler.codec;

/* renamed from: io.netty.handler.codec.UnsupportedValueConverter */
public final class UnsupportedValueConverter<V> implements ValueConverter<V> {
    private static final UnsupportedValueConverter INSTANCE = new UnsupportedValueConverter();

    private UnsupportedValueConverter() {
    }

    public static <V> UnsupportedValueConverter<V> instance() {
        return INSTANCE;
    }

    public V convertObject(Object obj) {
        throw new UnsupportedOperationException();
    }

    public V convertBoolean(boolean z) {
        throw new UnsupportedOperationException();
    }

    public boolean convertToBoolean(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertByte(byte b) {
        throw new UnsupportedOperationException();
    }

    public byte convertToByte(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertChar(char c) {
        throw new UnsupportedOperationException();
    }

    public char convertToChar(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertShort(short s) {
        throw new UnsupportedOperationException();
    }

    public short convertToShort(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertInt(int i) {
        throw new UnsupportedOperationException();
    }

    public int convertToInt(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertLong(long j) {
        throw new UnsupportedOperationException();
    }

    public long convertToLong(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertTimeMillis(long j) {
        throw new UnsupportedOperationException();
    }

    public long convertToTimeMillis(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertFloat(float f) {
        throw new UnsupportedOperationException();
    }

    public float convertToFloat(V v) {
        throw new UnsupportedOperationException();
    }

    public V convertDouble(double d) {
        throw new UnsupportedOperationException();
    }

    public double convertToDouble(V v) {
        throw new UnsupportedOperationException();
    }
}
