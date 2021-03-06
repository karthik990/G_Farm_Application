package p043io.opencensus.stats;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.stats.BucketBoundaries */
public abstract class BucketBoundaries {
    private static final Logger logger = Logger.getLogger(BucketBoundaries.class.getName());

    public abstract List<Double> getBoundaries();

    public static final BucketBoundaries create(List<Double> list) {
        C5887Utils.checkNotNull(list, "bucketBoundaries");
        ArrayList arrayList = new ArrayList(list);
        if (arrayList.size() > 1) {
            double doubleValue = ((Double) arrayList.get(0)).doubleValue();
            int i = 1;
            while (i < arrayList.size()) {
                double doubleValue2 = ((Double) arrayList.get(i)).doubleValue();
                C5887Utils.checkArgument(doubleValue < doubleValue2, "Bucket boundaries not sorted.");
                i++;
                doubleValue = doubleValue2;
            }
        }
        return new AutoValue_BucketBoundaries(Collections.unmodifiableList(dropNegativeBucketBounds(arrayList)));
    }

    private static List<Double> dropNegativeBucketBounds(List<Double> list) {
        int i = 0;
        int i2 = 0;
        for (Double d : list) {
            if (d.doubleValue() > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                break;
            } else if (d.doubleValue() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                i2++;
            } else {
                i++;
            }
        }
        if (i > 0) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            StringBuilder sb = new StringBuilder();
            sb.append("Dropping ");
            sb.append(i);
            sb.append(" negative bucket boundaries, the values must be strictly > 0.");
            logger2.log(level, sb.toString());
        }
        return list.subList(i + i2, list.size());
    }
}
