package p043io.grpc.inprocess;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.common.base.Preconditions;
import java.net.SocketAddress;

/* renamed from: io.grpc.inprocess.InProcessSocketAddress */
public final class InProcessSocketAddress extends SocketAddress {
    private static final long serialVersionUID = -2803441206326023474L;
    private final String name;

    public InProcessSocketAddress(String str) {
        this.name = (String) Preconditions.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InProcessSocketAddress)) {
            return false;
        }
        return this.name.equals(((InProcessSocketAddress) obj).name);
    }
}
