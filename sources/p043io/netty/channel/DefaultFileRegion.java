package p043io.netty.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import p043io.netty.util.AbstractReferenceCounted;
import p043io.netty.util.IllegalReferenceCountException;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.DefaultFileRegion */
public class DefaultFileRegion extends AbstractReferenceCounted implements FileRegion {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
    private final long count;

    /* renamed from: f */
    private final File f3699f;
    private FileChannel file;
    private final long position;
    private long transferred;

    public FileRegion touch() {
        return this;
    }

    public FileRegion touch(Object obj) {
        return this;
    }

    public DefaultFileRegion(FileChannel fileChannel, long j, long j2) {
        if (fileChannel == null) {
            throw new NullPointerException("file");
        } else if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("position must be >= 0 but was ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j2 >= 0) {
            this.file = fileChannel;
            this.position = j;
            this.count = j2;
            this.f3699f = null;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("count must be >= 0 but was ");
            sb2.append(j2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public DefaultFileRegion(File file2, long j, long j2) {
        if (file2 == null) {
            throw new NullPointerException("f");
        } else if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("position must be >= 0 but was ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j2 >= 0) {
            this.position = j;
            this.count = j2;
            this.f3699f = file2;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("count must be >= 0 but was ");
            sb2.append(j2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public boolean isOpen() {
        return this.file != null;
    }

    public void open() throws IOException {
        if (!isOpen() && refCnt() > 0) {
            this.file = new RandomAccessFile(this.f3699f, "r").getChannel();
        }
    }

    public long position() {
        return this.position;
    }

    public long count() {
        return this.count;
    }

    @Deprecated
    public long transfered() {
        return this.transferred;
    }

    public long transferred() {
        return this.transferred;
    }

    public long transferTo(WritableByteChannel writableByteChannel, long j) throws IOException {
        long j2 = this.count - j;
        if (j2 < 0 || j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("position out of range: ");
            sb.append(j);
            sb.append(" (expected: 0 - ");
            sb.append(this.count - 1);
            sb.append(')');
            throw new IllegalArgumentException(sb.toString());
        } else if (j2 == 0) {
            return 0;
        } else {
            if (refCnt() != 0) {
                open();
                long transferTo = this.file.transferTo(this.position + j, j2, writableByteChannel);
                if (transferTo > 0) {
                    this.transferred += transferTo;
                }
                return transferTo;
            }
            throw new IllegalReferenceCountException(0);
        }
    }

    /* access modifiers changed from: protected */
    public void deallocate() {
        FileChannel fileChannel = this.file;
        if (fileChannel != null) {
            this.file = null;
            try {
                fileChannel.close();
            } catch (IOException e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close a file.", (Throwable) e);
                }
            }
        }
    }

    public FileRegion retain() {
        super.retain();
        return this;
    }

    public FileRegion retain(int i) {
        super.retain(i);
        return this;
    }
}
