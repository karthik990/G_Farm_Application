package p043io.fabric.sdk.android;

import p043io.fabric.sdk.android.services.common.TimingMetric;
import p043io.fabric.sdk.android.services.concurrency.Priority;
import p043io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import p043io.fabric.sdk.android.services.concurrency.UnmetDependencyException;

/* renamed from: io.fabric.sdk.android.InitializationTask */
class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    public InitializationTask(Kit<Result> kit2) {
        this.kit = kit2;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
        TimingMetric createAndStartTimingMetric = createAndStartTimingMetric("onPreExecute");
        try {
            boolean onPreExecute = this.kit.onPreExecute();
            createAndStartTimingMetric.stopMeasuring();
            if (onPreExecute) {
                return;
            }
        } catch (UnmetDependencyException e) {
            throw e;
        } catch (Exception e2) {
            Fabric.getLogger().mo64077e(Fabric.TAG, "Failure onPreExecute()", e2);
            createAndStartTimingMetric.stopMeasuring();
        } catch (Throwable th) {
            createAndStartTimingMetric.stopMeasuring();
            cancel(true);
            throw th;
        }
        cancel(true);
    }

    /* access modifiers changed from: protected */
    public Result doInBackground(Void... voidArr) {
        TimingMetric createAndStartTimingMetric = createAndStartTimingMetric("doInBackground");
        Result doInBackground = !isCancelled() ? this.kit.doInBackground() : null;
        createAndStartTimingMetric.stopMeasuring();
        return doInBackground;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Result result) {
        this.kit.onPostExecute(result);
        this.kit.initializationCallback.success(result);
    }

    /* access modifiers changed from: protected */
    public void onCancelled(Result result) {
        this.kit.onCancelled(result);
        StringBuilder sb = new StringBuilder();
        sb.append(this.kit.getIdentifier());
        sb.append(" Initialization was cancelled");
        this.kit.initializationCallback.failure(new InitializationException(sb.toString()));
    }

    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric createAndStartTimingMetric(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.kit.getIdentifier());
        sb.append(".");
        sb.append(str);
        TimingMetric timingMetric = new TimingMetric(sb.toString(), TIMING_METRIC_TAG);
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
