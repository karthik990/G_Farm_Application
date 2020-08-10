package p043io.opencensus.stats;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.metrics.data.AttachmentValue;
import p043io.opencensus.metrics.data.AttachmentValue.AttachmentValueString;
import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.Measure.MeasureLong;
import p043io.opencensus.tags.TagContext;

/* renamed from: io.opencensus.stats.MeasureMap */
public abstract class MeasureMap {
    public abstract MeasureMap put(MeasureDouble measureDouble, double d);

    public abstract MeasureMap put(MeasureLong measureLong, long j);

    public abstract void record();

    public abstract void record(TagContext tagContext);

    @Deprecated
    public MeasureMap putAttachment(String str, String str2) {
        return putAttachment(str, (AttachmentValue) AttachmentValueString.create(str2));
    }

    public MeasureMap putAttachment(String str, AttachmentValue attachmentValue) {
        C5887Utils.checkNotNull(str, "key");
        C5887Utils.checkNotNull(attachmentValue, Param.VALUE);
        return this;
    }
}
