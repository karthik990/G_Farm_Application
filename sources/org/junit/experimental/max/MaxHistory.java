package org.junit.experimental.max;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class MaxHistory implements Serializable {
    private static final long serialVersionUID = 1;
    private final Map<String, Long> fDurations = new HashMap();
    private final Map<String, Long> fFailureTimestamps = new HashMap();
    private final File fHistoryStore;

    private final class RememberingListener extends RunListener {
        private long overallStart;
        private Map<Description, Long> starts;

        private RememberingListener() {
            this.overallStart = System.currentTimeMillis();
            this.starts = new HashMap();
        }

        public void testStarted(Description description) throws Exception {
            this.starts.put(description, Long.valueOf(System.nanoTime()));
        }

        public void testFinished(Description description) throws Exception {
            MaxHistory.this.putTestDuration(description, System.nanoTime() - ((Long) this.starts.get(description)).longValue());
        }

        public void testFailure(Failure failure) throws Exception {
            MaxHistory.this.putTestFailureTimestamp(failure.getDescription(), this.overallStart);
        }

        public void testRunFinished(Result result) throws Exception {
            MaxHistory.this.save();
        }
    }

    private class TestComparator implements Comparator<Description> {
        private TestComparator() {
        }

        public int compare(Description description, Description description2) {
            if (MaxHistory.this.isNewTest(description)) {
                return -1;
            }
            if (MaxHistory.this.isNewTest(description2)) {
                return 1;
            }
            int compareTo = getFailure(description2).compareTo(getFailure(description));
            if (compareTo == 0) {
                compareTo = MaxHistory.this.getTestDuration(description).compareTo(MaxHistory.this.getTestDuration(description2));
            }
            return compareTo;
        }

        private Long getFailure(Description description) {
            Long failureTimestamp = MaxHistory.this.getFailureTimestamp(description);
            return failureTimestamp == null ? Long.valueOf(0) : failureTimestamp;
        }
    }

    public static MaxHistory forFolder(File file) {
        if (file.exists()) {
            try {
                return readHistory(file);
            } catch (CouldNotReadCoreException e) {
                e.printStackTrace();
                file.delete();
            }
        }
        return new MaxHistory(file);
    }

    private static MaxHistory readHistory(File file) throws CouldNotReadCoreException {
        ObjectInputStream objectInputStream;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                MaxHistory maxHistory = (MaxHistory) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                return maxHistory;
            } catch (Throwable th) {
                fileInputStream.close();
                throw th;
            }
        } catch (Exception e) {
            throw new CouldNotReadCoreException(e);
        }
    }

    private MaxHistory(File file) {
        this.fHistoryStore = file;
    }

    /* access modifiers changed from: private */
    public void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.fHistoryStore));
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    /* access modifiers changed from: 0000 */
    public Long getFailureTimestamp(Description description) {
        return (Long) this.fFailureTimestamps.get(description.toString());
    }

    /* access modifiers changed from: 0000 */
    public void putTestFailureTimestamp(Description description, long j) {
        this.fFailureTimestamps.put(description.toString(), Long.valueOf(j));
    }

    /* access modifiers changed from: 0000 */
    public boolean isNewTest(Description description) {
        return !this.fDurations.containsKey(description.toString());
    }

    /* access modifiers changed from: 0000 */
    public Long getTestDuration(Description description) {
        return (Long) this.fDurations.get(description.toString());
    }

    /* access modifiers changed from: 0000 */
    public void putTestDuration(Description description, long j) {
        this.fDurations.put(description.toString(), Long.valueOf(j));
    }

    public RunListener listener() {
        return new RememberingListener();
    }

    public Comparator<Description> testComparator() {
        return new TestComparator();
    }
}
