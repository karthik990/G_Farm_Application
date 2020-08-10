package androidx.work;

import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;

public interface Operation {
    public static final IN_PROGRESS IN_PROGRESS = new IN_PROGRESS();
    public static final SUCCESS SUCCESS = new SUCCESS();

    public static abstract class State {

        public static final class FAILURE extends State {
            private final Throwable mThrowable;

            public FAILURE(Throwable th) {
                this.mThrowable = th;
            }

            public Throwable getThrowable() {
                return this.mThrowable;
            }

            public String toString() {
                return String.format("FAILURE (%s)", new Object[]{this.mThrowable.getMessage()});
            }
        }

        public static final class IN_PROGRESS extends State {
            public String toString() {
                return "IN_PROGRESS";
            }

            private IN_PROGRESS() {
            }
        }

        public static final class SUCCESS extends State {
            public String toString() {
                return "SUCCESS";
            }

            private SUCCESS() {
            }
        }

        State() {
        }
    }

    ListenableFuture<SUCCESS> getResult();

    LiveData<State> getState();
}
