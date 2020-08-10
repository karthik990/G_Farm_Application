package com.crashlytics.android.core;

import com.mobiroller.constants.Constants;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.Logger;
import p043io.fabric.sdk.android.services.common.CommonUtils;
import p043io.fabric.sdk.android.services.common.QueueFile;
import p043io.fabric.sdk.android.services.common.QueueFile.ElementReader;

class QueueFileLogStore implements FileLogStore {
    private QueueFile logFile;
    private final int maxLogSize;
    private final File workingFile;

    public class LogBytes {
        public final byte[] bytes;
        public final int offset;

        public LogBytes(byte[] bArr, int i) {
            this.bytes = bArr;
            this.offset = i;
        }
    }

    public QueueFileLogStore(File file, int i) {
        this.workingFile = file;
        this.maxLogSize = i;
    }

    public void writeToLog(long j, String str) {
        openLogFile();
        doWriteToLog(j, str);
    }

    public ByteString getLogAsByteString() {
        LogBytes logBytes = getLogBytes();
        if (logBytes == null) {
            return null;
        }
        return ByteString.copyFrom(logBytes.bytes, 0, logBytes.offset);
    }

    public byte[] getLogAsBytes() {
        LogBytes logBytes = getLogBytes();
        if (logBytes == null) {
            return null;
        }
        return logBytes.bytes;
    }

    private LogBytes getLogBytes() {
        if (!this.workingFile.exists()) {
            return null;
        }
        openLogFile();
        QueueFile queueFile = this.logFile;
        if (queueFile == null) {
            return null;
        }
        final int[] iArr = {0};
        final byte[] bArr = new byte[queueFile.usedBytes()];
        try {
            this.logFile.forEach(new ElementReader() {
                public void read(InputStream inputStream, int i) throws IOException {
                    try {
                        inputStream.read(bArr, iArr[0], i);
                        int[] iArr = iArr;
                        iArr[0] = iArr[0] + i;
                    } finally {
                        inputStream.close();
                    }
                }
            });
        } catch (IOException e) {
            Fabric.getLogger().mo64077e(CrashlyticsCore.TAG, "A problem occurred while reading the Crashlytics log file.", e);
        }
        return new LogBytes(bArr, iArr[0]);
    }

    public void closeLogFile() {
        CommonUtils.closeOrLog(this.logFile, "There was a problem closing the Crashlytics log file.");
        this.logFile = null;
    }

    public void deleteLogFile() {
        closeLogFile();
        this.workingFile.delete();
    }

    private void openLogFile() {
        if (this.logFile == null) {
            try {
                this.logFile = new QueueFile(this.workingFile);
            } catch (IOException e) {
                Logger logger = Fabric.getLogger();
                StringBuilder sb = new StringBuilder();
                sb.append("Could not open log file: ");
                sb.append(this.workingFile);
                logger.mo64077e(CrashlyticsCore.TAG, sb.toString(), e);
            }
        }
    }

    private void doWriteToLog(long j, String str) {
        String str2 = " ";
        if (this.logFile != null) {
            if (str == null) {
                str = "null";
            }
            try {
                int i = this.maxLogSize / 4;
                if (str.length() > i) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("...");
                    sb.append(str.substring(str.length() - i));
                    str = sb.toString();
                }
                this.logFile.add(String.format(Locale.US, "%d %s%n", new Object[]{Long.valueOf(j), str.replaceAll("\r", str2).replaceAll(Constants.NEW_LINE, str2)}).getBytes("UTF-8"));
                while (!this.logFile.isEmpty() && this.logFile.usedBytes() > this.maxLogSize) {
                    this.logFile.remove();
                }
            } catch (IOException e) {
                Fabric.getLogger().mo64077e(CrashlyticsCore.TAG, "There was a problem writing to the Crashlytics log.", e);
            }
        }
    }
}
