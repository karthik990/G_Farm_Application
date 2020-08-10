package p043io.opencensus.stats;

import java.util.Set;
import javax.annotation.Nullable;
import p043io.opencensus.stats.View.Name;

/* renamed from: io.opencensus.stats.ViewManager */
public abstract class ViewManager {
    public abstract Set<View> getAllExportedViews();

    @Nullable
    public abstract ViewData getView(Name name);

    public abstract void registerView(View view);
}
