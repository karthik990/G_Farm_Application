package p043io.netty.channel;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import p043io.netty.util.ReferenceCounted;

/* renamed from: io.netty.channel.FileRegion */
public interface FileRegion extends ReferenceCounted {
    long count();

    long position();

    FileRegion retain();

    FileRegion retain(int i);

    FileRegion touch();

    FileRegion touch(Object obj);

    long transferTo(WritableByteChannel writableByteChannel, long j) throws IOException;

    @Deprecated
    long transfered();

    long transferred();
}
