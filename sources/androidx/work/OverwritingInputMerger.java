package androidx.work;

import androidx.work.Data.Builder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OverwritingInputMerger extends InputMerger {
    public Data merge(List<Data> list) {
        Builder builder = new Builder();
        HashMap hashMap = new HashMap();
        for (Data keyValueMap : list) {
            hashMap.putAll(keyValueMap.getKeyValueMap());
        }
        builder.putAll((Map<String, Object>) hashMap);
        return builder.build();
    }
}
