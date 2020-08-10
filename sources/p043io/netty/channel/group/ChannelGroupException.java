package p043io.netty.channel.group;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelException;

/* renamed from: io.netty.channel.group.ChannelGroupException */
public class ChannelGroupException extends ChannelException implements Iterable<Entry<Channel, Throwable>> {
    private static final long serialVersionUID = -4093064295562629453L;
    private final Collection<Entry<Channel, Throwable>> failed;

    public ChannelGroupException(Collection<Entry<Channel, Throwable>> collection) {
        if (collection == null) {
            throw new NullPointerException("causes");
        } else if (!collection.isEmpty()) {
            this.failed = Collections.unmodifiableCollection(collection);
        } else {
            throw new IllegalArgumentException("causes must be non empty");
        }
    }

    public Iterator<Entry<Channel, Throwable>> iterator() {
        return this.failed.iterator();
    }
}
