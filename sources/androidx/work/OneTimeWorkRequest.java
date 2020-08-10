package androidx.work;

import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.List;

public final class OneTimeWorkRequest extends WorkRequest {

    public static final class Builder extends androidx.work.WorkRequest.Builder<Builder, OneTimeWorkRequest> {
        /* access modifiers changed from: 0000 */
        public Builder getThis() {
            return this;
        }

        public Builder(Class<? extends ListenableWorker> cls) {
            super(cls);
        }

        public Builder setInputMerger(Class<? extends InputMerger> cls) {
            this.mWorkSpec.inputMergerClassName = cls.getName();
            return this;
        }

        /* access modifiers changed from: 0000 */
        public OneTimeWorkRequest buildInternal() {
            if (!this.mBackoffCriteriaSet || VERSION.SDK_INT < 23 || !this.mWorkSpec.constraints.requiresDeviceIdle()) {
                return new OneTimeWorkRequest(this);
            }
            throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
        }
    }

    public static OneTimeWorkRequest from(Class<? extends ListenableWorker> cls) {
        return (OneTimeWorkRequest) new Builder(cls).build();
    }

    public static List<OneTimeWorkRequest> from(List<Class<? extends ListenableWorker>> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Class builder : list) {
            arrayList.add(new Builder(builder).build());
        }
        return arrayList;
    }

    OneTimeWorkRequest(Builder builder) {
        super(builder.mId, builder.mWorkSpec, builder.mTags);
    }
}
