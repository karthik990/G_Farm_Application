package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.NaNFilter;
import com.google.firebase.firestore.core.NullFilter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.RelationFilter;
import com.google.firebase.firestore.local.QueryData;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Remove;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Union;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.value.ArrayValue;
import com.google.firebase.firestore.model.value.BlobValue;
import com.google.firebase.firestore.model.value.BooleanValue;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.GeoPointValue;
import com.google.firebase.firestore.model.value.IntegerValue;
import com.google.firebase.firestore.model.value.NullValue;
import com.google.firebase.firestore.model.value.NumberValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.model.value.ReferenceValue;
import com.google.firebase.firestore.model.value.StringValue;
import com.google.firebase.firestore.model.value.TimestampValue;
import com.google.firebase.firestore.remote.WatchChange.ExistenceFilterWatchChange;
import com.google.firebase.firestore.remote.WatchChange.WatchTargetChange;
import com.google.firebase.firestore.remote.WatchChange.WatchTargetChangeType;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p050v1.BatchGetDocumentsResponse;
import com.google.firestore.p050v1.BatchGetDocumentsResponse.ResultCase;
import com.google.firestore.p050v1.Cursor;
import com.google.firestore.p050v1.Document;
import com.google.firestore.p050v1.DocumentChange;
import com.google.firestore.p050v1.DocumentDelete;
import com.google.firestore.p050v1.DocumentMask;
import com.google.firestore.p050v1.DocumentRemove;
import com.google.firestore.p050v1.DocumentTransform;
import com.google.firestore.p050v1.DocumentTransform.FieldTransform.ServerValue;
import com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase;
import com.google.firestore.p050v1.ExistenceFilter;
import com.google.firestore.p050v1.ListenResponse;
import com.google.firestore.p050v1.ListenResponse.ResponseTypeCase;
import com.google.firestore.p050v1.MapValue;
import com.google.firestore.p050v1.Precondition.ConditionTypeCase;
import com.google.firestore.p050v1.StructuredQuery;
import com.google.firestore.p050v1.StructuredQuery.CollectionSelector;
import com.google.firestore.p050v1.StructuredQuery.CompositeFilter;
import com.google.firestore.p050v1.StructuredQuery.Direction;
import com.google.firestore.p050v1.StructuredQuery.FieldFilter;
import com.google.firestore.p050v1.StructuredQuery.FieldReference;
import com.google.firestore.p050v1.StructuredQuery.Filter;
import com.google.firestore.p050v1.StructuredQuery.Filter.FilterTypeCase;
import com.google.firestore.p050v1.StructuredQuery.Order;
import com.google.firestore.p050v1.StructuredQuery.UnaryFilter;
import com.google.firestore.p050v1.StructuredQuery.UnaryFilter.Operator;
import com.google.firestore.p050v1.Target;
import com.google.firestore.p050v1.Target.DocumentsTarget;
import com.google.firestore.p050v1.Target.QueryTarget;
import com.google.firestore.p050v1.TargetChange;
import com.google.firestore.p050v1.TargetChange.TargetChangeType;
import com.google.firestore.p050v1.Value;
import com.google.firestore.p050v1.Write;
import com.google.firestore.p050v1.Write.OperationCase;
import com.google.firestore.p050v1.WriteResult;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import com.google.protobuf.Timestamp.Builder;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p043io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class RemoteSerializer {
    private final DatabaseId databaseId;
    private final String databaseName;

    /* renamed from: com.google.firebase.firestore.remote.RemoteSerializer$1 */
    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static /* synthetic */ class C36701 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = new int[QueryPurpose.values().length];

        /* renamed from: $SwitchMap$com$google$firestore$v1$DocumentTransform$FieldTransform$TransformTypeCase */
        static final /* synthetic */ int[] f2046xdd498c9f = new int[TransformTypeCase.values().length];

        /* renamed from: $SwitchMap$com$google$firestore$v1$ListenResponse$ResponseTypeCase */
        static final /* synthetic */ int[] f2047x1837d9f = new int[ResponseTypeCase.values().length];

        /* renamed from: $SwitchMap$com$google$firestore$v1$Precondition$ConditionTypeCase */
        static final /* synthetic */ int[] f2048x8f18ca41 = new int[ConditionTypeCase.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = new int[Direction.values().length];

        /* renamed from: $SwitchMap$com$google$firestore$v1$StructuredQuery$Filter$FilterTypeCase */
        static final /* synthetic */ int[] f2050x9d2ee979 = new int[FilterTypeCase.values().length];

        /* renamed from: $SwitchMap$com$google$firestore$v1$StructuredQuery$UnaryFilter$Operator */
        static final /* synthetic */ int[] f2051xf473b456 = new int[Operator.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = new int[TargetChangeType.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Write$OperationCase = new int[OperationCase.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(111:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|(2:17|18)|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|(2:55|56)|57|59|60|61|62|63|64|65|66|67|68|(2:69|70)|71|73|74|(2:75|76)|77|79|80|81|82|(2:83|84)|85|87|88|89|90|(2:91|92)|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|(2:109|110)|111|113|114|115|116|(2:117|118)|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|(3:141|142|144)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(112:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|(2:55|56)|57|59|60|61|62|63|64|65|66|67|68|(2:69|70)|71|73|74|(2:75|76)|77|79|80|81|82|(2:83|84)|85|87|88|89|90|(2:91|92)|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|(2:109|110)|111|113|114|115|116|(2:117|118)|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|(3:141|142|144)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(117:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|(2:75|76)|77|79|80|81|82|83|84|85|87|88|89|90|(2:91|92)|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|109|110|111|113|114|115|116|(2:117|118)|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|(3:141|142|144)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(118:0|(2:1|2)|3|(2:5|6)|7|9|10|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|(2:75|76)|77|79|80|81|82|83|84|85|87|88|89|90|(2:91|92)|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|109|110|111|113|114|115|116|(2:117|118)|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|(3:141|142|144)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(123:0|(2:1|2)|3|5|6|7|9|10|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|(2:75|76)|77|79|80|81|82|83|84|85|87|88|89|90|91|92|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|109|110|111|113|114|115|116|117|118|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|144) */
        /* JADX WARNING: Can't wrap try/catch for region: R(125:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|75|76|77|79|80|81|82|83|84|85|87|88|89|90|91|92|93|95|96|97|98|99|100|(2:101|102)|103|105|106|107|108|109|110|111|113|114|115|116|117|118|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|144) */
        /* JADX WARNING: Can't wrap try/catch for region: R(126:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|75|76|77|79|80|81|82|83|84|85|87|88|89|90|91|92|93|95|96|97|98|99|100|101|102|103|105|106|107|108|109|110|111|113|114|115|116|117|118|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|144) */
        /* JADX WARNING: Can't wrap try/catch for region: R(127:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|(2:21|22)|23|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|75|76|77|79|80|81|82|83|84|85|87|88|89|90|91|92|93|95|96|97|98|99|100|101|102|103|105|106|107|108|109|110|111|113|114|115|116|117|118|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|144) */
        /* JADX WARNING: Can't wrap try/catch for region: R(128:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|21|22|23|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|41|42|43|45|46|47|48|49|50|51|52|53|54|55|56|57|59|60|61|62|63|64|65|66|67|68|69|70|71|73|74|75|76|77|79|80|81|82|83|84|85|87|88|89|90|91|92|93|95|96|97|98|99|100|101|102|103|105|106|107|108|109|110|111|113|114|115|116|117|118|119|121|122|123|124|125|126|127|128|129|130|131|132|133|134|135|136|137|138|139|140|141|142|144) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:101:0x01c9 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:107:0x01e6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:109:0x01f0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:115:0x020d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:117:0x0217 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:123:0x0234 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:125:0x023e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:127:0x0248 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:129:0x0252 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:131:0x025c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:133:0x0266 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:135:0x0271 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:137:0x027d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:139:0x0289 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:141:0x0295 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x005e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0068 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0072 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x007c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00a3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00c0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00ca */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x00d4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x00de */
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x00e8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x0105 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x010f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0119 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x0123 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x012d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:75:0x014a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:81:0x0167 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:83:0x0171 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:89:0x018e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:91:0x0198 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:97:0x01b5 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:99:0x01bf */
        static {
            /*
                com.google.firestore.v1.ListenResponse$ResponseTypeCase[] r0 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2047x1837d9f = r0
                r0 = 1
                int[] r1 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r2 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.TARGET_CHANGE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r3 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.DOCUMENT_CHANGE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r4 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.DOCUMENT_DELETE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r5 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.DOCUMENT_REMOVE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                r4 = 5
                int[] r5 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r6 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.FILTER     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r6 = r6.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r5[r6] = r4     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                r5 = 6
                int[] r6 = f2047x1837d9f     // Catch:{ NoSuchFieldError -> 0x004b }
                com.google.firestore.v1.ListenResponse$ResponseTypeCase r7 = com.google.firestore.p050v1.ListenResponse.ResponseTypeCase.RESPONSETYPE_NOT_SET     // Catch:{ NoSuchFieldError -> 0x004b }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r6[r7] = r5     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                com.google.firestore.v1.TargetChange$TargetChangeType[] r6 = com.google.firestore.p050v1.TargetChange.TargetChangeType.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType = r6
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x005e }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.NO_CHANGE     // Catch:{ NoSuchFieldError -> 0x005e }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x005e }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x005e }
            L_0x005e:
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x0068 }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.ADD     // Catch:{ NoSuchFieldError -> 0x0068 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0068 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x0068 }
            L_0x0068:
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x0072 }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.REMOVE     // Catch:{ NoSuchFieldError -> 0x0072 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0072 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x0072 }
            L_0x0072:
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x007c }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.CURRENT     // Catch:{ NoSuchFieldError -> 0x007c }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x007c }
                r6[r7] = r3     // Catch:{ NoSuchFieldError -> 0x007c }
            L_0x007c:
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x0086 }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.RESET     // Catch:{ NoSuchFieldError -> 0x0086 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0086 }
                r6[r7] = r4     // Catch:{ NoSuchFieldError -> 0x0086 }
            L_0x0086:
                int[] r6 = $SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.google.firestore.v1.TargetChange$TargetChangeType r7 = com.google.firestore.p050v1.TargetChange.TargetChangeType.UNRECOGNIZED     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r6[r7] = r5     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                com.google.firestore.v1.StructuredQuery$Direction[] r6 = com.google.firestore.p050v1.StructuredQuery.Direction.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction = r6
                int[] r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction     // Catch:{ NoSuchFieldError -> 0x00a3 }
                com.google.firestore.v1.StructuredQuery$Direction r7 = com.google.firestore.p050v1.StructuredQuery.Direction.ASCENDING     // Catch:{ NoSuchFieldError -> 0x00a3 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a3 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x00a3 }
            L_0x00a3:
                int[] r6 = $SwitchMap$com$google$firestore$v1$StructuredQuery$Direction     // Catch:{ NoSuchFieldError -> 0x00ad }
                com.google.firestore.v1.StructuredQuery$Direction r7 = com.google.firestore.p050v1.StructuredQuery.Direction.DESCENDING     // Catch:{ NoSuchFieldError -> 0x00ad }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ad }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x00ad }
            L_0x00ad:
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator[] r6 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                f2049xaf95d2b = r6
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00c0 }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.LESS_THAN     // Catch:{ NoSuchFieldError -> 0x00c0 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c0 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x00c0 }
            L_0x00c0:
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00ca }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x00ca }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ca }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x00ca }
            L_0x00ca:
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00d4 }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.EQUAL     // Catch:{ NoSuchFieldError -> 0x00d4 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00d4 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x00d4 }
            L_0x00d4:
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00de }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x00de }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00de }
                r6[r7] = r3     // Catch:{ NoSuchFieldError -> 0x00de }
            L_0x00de:
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00e8 }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.GREATER_THAN     // Catch:{ NoSuchFieldError -> 0x00e8 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00e8 }
                r6[r7] = r4     // Catch:{ NoSuchFieldError -> 0x00e8 }
            L_0x00e8:
                int[] r6 = f2049xaf95d2b     // Catch:{ NoSuchFieldError -> 0x00f2 }
                com.google.firestore.v1.StructuredQuery$FieldFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS     // Catch:{ NoSuchFieldError -> 0x00f2 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f2 }
                r6[r7] = r5     // Catch:{ NoSuchFieldError -> 0x00f2 }
            L_0x00f2:
                com.google.firebase.firestore.core.Filter$Operator[] r6 = com.google.firebase.firestore.core.Filter.Operator.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = r6
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0105 }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN     // Catch:{ NoSuchFieldError -> 0x0105 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0105 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x0105 }
            L_0x0105:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x010f }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x010f }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x010f }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x010f }
            L_0x010f:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0119 }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.EQUAL     // Catch:{ NoSuchFieldError -> 0x0119 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0119 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x0119 }
            L_0x0119:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0123 }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN     // Catch:{ NoSuchFieldError -> 0x0123 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0123 }
                r6[r7] = r3     // Catch:{ NoSuchFieldError -> 0x0123 }
            L_0x0123:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x012d }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL     // Catch:{ NoSuchFieldError -> 0x012d }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x012d }
                r6[r7] = r4     // Catch:{ NoSuchFieldError -> 0x012d }
            L_0x012d:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$core$Filter$Operator     // Catch:{ NoSuchFieldError -> 0x0137 }
                com.google.firebase.firestore.core.Filter$Operator r7 = com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS     // Catch:{ NoSuchFieldError -> 0x0137 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0137 }
                r6[r7] = r5     // Catch:{ NoSuchFieldError -> 0x0137 }
            L_0x0137:
                com.google.firestore.v1.StructuredQuery$UnaryFilter$Operator[] r6 = com.google.firestore.p050v1.StructuredQuery.UnaryFilter.Operator.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                f2051xf473b456 = r6
                int[] r6 = f2051xf473b456     // Catch:{ NoSuchFieldError -> 0x014a }
                com.google.firestore.v1.StructuredQuery$UnaryFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.UnaryFilter.Operator.IS_NAN     // Catch:{ NoSuchFieldError -> 0x014a }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x014a }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x014a }
            L_0x014a:
                int[] r6 = f2051xf473b456     // Catch:{ NoSuchFieldError -> 0x0154 }
                com.google.firestore.v1.StructuredQuery$UnaryFilter$Operator r7 = com.google.firestore.p050v1.StructuredQuery.UnaryFilter.Operator.IS_NULL     // Catch:{ NoSuchFieldError -> 0x0154 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0154 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x0154 }
            L_0x0154:
                com.google.firestore.v1.StructuredQuery$Filter$FilterTypeCase[] r6 = com.google.firestore.p050v1.StructuredQuery.Filter.FilterTypeCase.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                f2050x9d2ee979 = r6
                int[] r6 = f2050x9d2ee979     // Catch:{ NoSuchFieldError -> 0x0167 }
                com.google.firestore.v1.StructuredQuery$Filter$FilterTypeCase r7 = com.google.firestore.p050v1.StructuredQuery.Filter.FilterTypeCase.COMPOSITE_FILTER     // Catch:{ NoSuchFieldError -> 0x0167 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0167 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x0167 }
            L_0x0167:
                int[] r6 = f2050x9d2ee979     // Catch:{ NoSuchFieldError -> 0x0171 }
                com.google.firestore.v1.StructuredQuery$Filter$FilterTypeCase r7 = com.google.firestore.p050v1.StructuredQuery.Filter.FilterTypeCase.FIELD_FILTER     // Catch:{ NoSuchFieldError -> 0x0171 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0171 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x0171 }
            L_0x0171:
                int[] r6 = f2050x9d2ee979     // Catch:{ NoSuchFieldError -> 0x017b }
                com.google.firestore.v1.StructuredQuery$Filter$FilterTypeCase r7 = com.google.firestore.p050v1.StructuredQuery.Filter.FilterTypeCase.UNARY_FILTER     // Catch:{ NoSuchFieldError -> 0x017b }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x017b }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x017b }
            L_0x017b:
                com.google.firebase.firestore.local.QueryPurpose[] r6 = com.google.firebase.firestore.local.QueryPurpose.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firebase$firestore$local$QueryPurpose = r6
                int[] r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose     // Catch:{ NoSuchFieldError -> 0x018e }
                com.google.firebase.firestore.local.QueryPurpose r7 = com.google.firebase.firestore.local.QueryPurpose.LISTEN     // Catch:{ NoSuchFieldError -> 0x018e }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x018e }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x018e }
            L_0x018e:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose     // Catch:{ NoSuchFieldError -> 0x0198 }
                com.google.firebase.firestore.local.QueryPurpose r7 = com.google.firebase.firestore.local.QueryPurpose.EXISTENCE_FILTER_MISMATCH     // Catch:{ NoSuchFieldError -> 0x0198 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0198 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x0198 }
            L_0x0198:
                int[] r6 = $SwitchMap$com$google$firebase$firestore$local$QueryPurpose     // Catch:{ NoSuchFieldError -> 0x01a2 }
                com.google.firebase.firestore.local.QueryPurpose r7 = com.google.firebase.firestore.local.QueryPurpose.LIMBO_RESOLUTION     // Catch:{ NoSuchFieldError -> 0x01a2 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01a2 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x01a2 }
            L_0x01a2:
                com.google.firestore.v1.DocumentTransform$FieldTransform$TransformTypeCase[] r6 = com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                f2046xdd498c9f = r6
                int[] r6 = f2046xdd498c9f     // Catch:{ NoSuchFieldError -> 0x01b5 }
                com.google.firestore.v1.DocumentTransform$FieldTransform$TransformTypeCase r7 = com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase.SET_TO_SERVER_VALUE     // Catch:{ NoSuchFieldError -> 0x01b5 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01b5 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x01b5 }
            L_0x01b5:
                int[] r6 = f2046xdd498c9f     // Catch:{ NoSuchFieldError -> 0x01bf }
                com.google.firestore.v1.DocumentTransform$FieldTransform$TransformTypeCase r7 = com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase.APPEND_MISSING_ELEMENTS     // Catch:{ NoSuchFieldError -> 0x01bf }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01bf }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x01bf }
            L_0x01bf:
                int[] r6 = f2046xdd498c9f     // Catch:{ NoSuchFieldError -> 0x01c9 }
                com.google.firestore.v1.DocumentTransform$FieldTransform$TransformTypeCase r7 = com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase.REMOVE_ALL_FROM_ARRAY     // Catch:{ NoSuchFieldError -> 0x01c9 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01c9 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x01c9 }
            L_0x01c9:
                int[] r6 = f2046xdd498c9f     // Catch:{ NoSuchFieldError -> 0x01d3 }
                com.google.firestore.v1.DocumentTransform$FieldTransform$TransformTypeCase r7 = com.google.firestore.p050v1.DocumentTransform.FieldTransform.TransformTypeCase.INCREMENT     // Catch:{ NoSuchFieldError -> 0x01d3 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01d3 }
                r6[r7] = r3     // Catch:{ NoSuchFieldError -> 0x01d3 }
            L_0x01d3:
                com.google.firestore.v1.Precondition$ConditionTypeCase[] r6 = com.google.firestore.p050v1.Precondition.ConditionTypeCase.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                f2048x8f18ca41 = r6
                int[] r6 = f2048x8f18ca41     // Catch:{ NoSuchFieldError -> 0x01e6 }
                com.google.firestore.v1.Precondition$ConditionTypeCase r7 = com.google.firestore.p050v1.Precondition.ConditionTypeCase.UPDATE_TIME     // Catch:{ NoSuchFieldError -> 0x01e6 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01e6 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x01e6 }
            L_0x01e6:
                int[] r6 = f2048x8f18ca41     // Catch:{ NoSuchFieldError -> 0x01f0 }
                com.google.firestore.v1.Precondition$ConditionTypeCase r7 = com.google.firestore.p050v1.Precondition.ConditionTypeCase.EXISTS     // Catch:{ NoSuchFieldError -> 0x01f0 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01f0 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x01f0 }
            L_0x01f0:
                int[] r6 = f2048x8f18ca41     // Catch:{ NoSuchFieldError -> 0x01fa }
                com.google.firestore.v1.Precondition$ConditionTypeCase r7 = com.google.firestore.p050v1.Precondition.ConditionTypeCase.CONDITIONTYPE_NOT_SET     // Catch:{ NoSuchFieldError -> 0x01fa }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x01fa }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x01fa }
            L_0x01fa:
                com.google.firestore.v1.Write$OperationCase[] r6 = com.google.firestore.p050v1.Write.OperationCase.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firestore$v1$Write$OperationCase = r6
                int[] r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase     // Catch:{ NoSuchFieldError -> 0x020d }
                com.google.firestore.v1.Write$OperationCase r7 = com.google.firestore.p050v1.Write.OperationCase.UPDATE     // Catch:{ NoSuchFieldError -> 0x020d }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x020d }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x020d }
            L_0x020d:
                int[] r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase     // Catch:{ NoSuchFieldError -> 0x0217 }
                com.google.firestore.v1.Write$OperationCase r7 = com.google.firestore.p050v1.Write.OperationCase.DELETE     // Catch:{ NoSuchFieldError -> 0x0217 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0217 }
                r6[r7] = r1     // Catch:{ NoSuchFieldError -> 0x0217 }
            L_0x0217:
                int[] r6 = $SwitchMap$com$google$firestore$v1$Write$OperationCase     // Catch:{ NoSuchFieldError -> 0x0221 }
                com.google.firestore.v1.Write$OperationCase r7 = com.google.firestore.p050v1.Write.OperationCase.TRANSFORM     // Catch:{ NoSuchFieldError -> 0x0221 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0221 }
                r6[r7] = r2     // Catch:{ NoSuchFieldError -> 0x0221 }
            L_0x0221:
                com.google.firestore.v1.Value$ValueTypeCase[] r6 = com.google.firestore.p050v1.Value.ValueTypeCase.values()
                int r6 = r6.length
                int[] r6 = new int[r6]
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase = r6
                int[] r6 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0234 }
                com.google.firestore.v1.Value$ValueTypeCase r7 = com.google.firestore.p050v1.Value.ValueTypeCase.NULL_VALUE     // Catch:{ NoSuchFieldError -> 0x0234 }
                int r7 = r7.ordinal()     // Catch:{ NoSuchFieldError -> 0x0234 }
                r6[r7] = r0     // Catch:{ NoSuchFieldError -> 0x0234 }
            L_0x0234:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x023e }
                com.google.firestore.v1.Value$ValueTypeCase r6 = com.google.firestore.p050v1.Value.ValueTypeCase.BOOLEAN_VALUE     // Catch:{ NoSuchFieldError -> 0x023e }
                int r6 = r6.ordinal()     // Catch:{ NoSuchFieldError -> 0x023e }
                r0[r6] = r1     // Catch:{ NoSuchFieldError -> 0x023e }
            L_0x023e:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0248 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.INTEGER_VALUE     // Catch:{ NoSuchFieldError -> 0x0248 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0248 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0248 }
            L_0x0248:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0252 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.DOUBLE_VALUE     // Catch:{ NoSuchFieldError -> 0x0252 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0252 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0252 }
            L_0x0252:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x025c }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.TIMESTAMP_VALUE     // Catch:{ NoSuchFieldError -> 0x025c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x025c }
                r0[r1] = r4     // Catch:{ NoSuchFieldError -> 0x025c }
            L_0x025c:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0266 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.GEO_POINT_VALUE     // Catch:{ NoSuchFieldError -> 0x0266 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0266 }
                r0[r1] = r5     // Catch:{ NoSuchFieldError -> 0x0266 }
            L_0x0266:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0271 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.BYTES_VALUE     // Catch:{ NoSuchFieldError -> 0x0271 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0271 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0271 }
            L_0x0271:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x027d }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.REFERENCE_VALUE     // Catch:{ NoSuchFieldError -> 0x027d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x027d }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x027d }
            L_0x027d:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0289 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.STRING_VALUE     // Catch:{ NoSuchFieldError -> 0x0289 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0289 }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0289 }
            L_0x0289:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x0295 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.ARRAY_VALUE     // Catch:{ NoSuchFieldError -> 0x0295 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0295 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0295 }
            L_0x0295:
                int[] r0 = $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase     // Catch:{ NoSuchFieldError -> 0x02a1 }
                com.google.firestore.v1.Value$ValueTypeCase r1 = com.google.firestore.p050v1.Value.ValueTypeCase.MAP_VALUE     // Catch:{ NoSuchFieldError -> 0x02a1 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x02a1 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x02a1 }
            L_0x02a1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.C36701.<clinit>():void");
        }
    }

    public RemoteSerializer(DatabaseId databaseId2) {
        this.databaseId = databaseId2;
        this.databaseName = encodedDatabaseId(databaseId2).canonicalString();
    }

    public Timestamp encodeTimestamp(com.google.firebase.Timestamp timestamp) {
        Builder newBuilder = Timestamp.newBuilder();
        newBuilder.setSeconds(timestamp.getSeconds());
        newBuilder.setNanos(timestamp.getNanoseconds());
        return (Timestamp) newBuilder.build();
    }

    public com.google.firebase.Timestamp decodeTimestamp(Timestamp timestamp) {
        return new com.google.firebase.Timestamp(timestamp.getSeconds(), timestamp.getNanos());
    }

    public Timestamp encodeVersion(SnapshotVersion snapshotVersion) {
        return encodeTimestamp(snapshotVersion.getTimestamp());
    }

    public SnapshotVersion decodeVersion(Timestamp timestamp) {
        if (timestamp.getSeconds() == 0 && timestamp.getNanos() == 0) {
            return SnapshotVersion.NONE;
        }
        return new SnapshotVersion(decodeTimestamp(timestamp));
    }

    private LatLng encodeGeoPoint(GeoPoint geoPoint) {
        return (LatLng) LatLng.newBuilder().setLatitude(geoPoint.getLatitude()).setLongitude(geoPoint.getLongitude()).build();
    }

    private GeoPoint decodeGeoPoint(LatLng latLng) {
        return new GeoPoint(latLng.getLatitude(), latLng.getLongitude());
    }

    public String encodeKey(DocumentKey documentKey) {
        return encodeResourceName(this.databaseId, documentKey.getPath());
    }

    public DocumentKey decodeKey(String str) {
        ResourcePath decodeResourceName = decodeResourceName(str);
        Assert.hardAssert(decodeResourceName.getSegment(1).equals(this.databaseId.getProjectId()), "Tried to deserialize key from different project.", new Object[0]);
        Assert.hardAssert(decodeResourceName.getSegment(3).equals(this.databaseId.getDatabaseId()), "Tried to deserialize key from different database.", new Object[0]);
        return DocumentKey.fromPath(extractLocalPathFromResourceName(decodeResourceName));
    }

    private String encodeQueryPath(ResourcePath resourcePath) {
        return encodeResourceName(this.databaseId, resourcePath);
    }

    private ResourcePath decodeQueryPath(String str) {
        ResourcePath decodeResourceName = decodeResourceName(str);
        if (decodeResourceName.length() == 4) {
            return ResourcePath.EMPTY;
        }
        return extractLocalPathFromResourceName(decodeResourceName);
    }

    private String encodeResourceName(DatabaseId databaseId2, ResourcePath resourcePath) {
        return ((ResourcePath) ((ResourcePath) encodedDatabaseId(databaseId2).append("documents")).append(resourcePath)).canonicalString();
    }

    private ResourcePath decodeResourceName(String str) {
        ResourcePath fromString = ResourcePath.fromString(str);
        Assert.hardAssert(isValidResourceName(fromString), "Tried to deserialize invalid key %s", fromString);
        return fromString;
    }

    private static ResourcePath encodedDatabaseId(DatabaseId databaseId2) {
        return ResourcePath.fromSegments(Arrays.asList(new String[]{"projects", databaseId2.getProjectId(), "databases", databaseId2.getDatabaseId()}));
    }

    private static ResourcePath extractLocalPathFromResourceName(ResourcePath resourcePath) {
        Assert.hardAssert(resourcePath.length() > 4 && resourcePath.getSegment(4).equals("documents"), "Tried to deserialize invalid key %s", resourcePath);
        return (ResourcePath) resourcePath.popFirst(5);
    }

    private static boolean isValidResourceName(ResourcePath resourcePath) {
        if (resourcePath.length() < 4 || !resourcePath.getSegment(0).equals("projects") || !resourcePath.getSegment(2).equals("databases")) {
            return false;
        }
        return true;
    }

    public String databaseName() {
        return this.databaseName;
    }

    public Value encodeValue(FieldValue fieldValue) {
        Value.Builder newBuilder = Value.newBuilder();
        if (fieldValue instanceof NullValue) {
            newBuilder.setNullValueValue(0);
            return (Value) newBuilder.build();
        }
        Object value = fieldValue.value();
        Assert.hardAssert(value != null, "Encoded field value should not be null.", new Object[0]);
        if (fieldValue instanceof BooleanValue) {
            newBuilder.setBooleanValue(((Boolean) value).booleanValue());
        } else if (fieldValue instanceof IntegerValue) {
            newBuilder.setIntegerValue(((Long) value).longValue());
        } else if (fieldValue instanceof DoubleValue) {
            newBuilder.setDoubleValue(((Double) value).doubleValue());
        } else if (fieldValue instanceof StringValue) {
            newBuilder.setStringValue((String) value);
        } else if (fieldValue instanceof ArrayValue) {
            newBuilder.setArrayValue(encodeArrayValue((ArrayValue) fieldValue));
        } else if (fieldValue instanceof ObjectValue) {
            newBuilder.setMapValue(encodeMapValue((ObjectValue) fieldValue));
        } else if (fieldValue instanceof TimestampValue) {
            newBuilder.setTimestampValue(encodeTimestamp(((TimestampValue) fieldValue).getInternalValue()));
        } else if (fieldValue instanceof GeoPointValue) {
            newBuilder.setGeoPointValue(encodeGeoPoint((GeoPoint) value));
        } else if (fieldValue instanceof BlobValue) {
            newBuilder.setBytesValue(((Blob) value).toByteString());
        } else if (fieldValue instanceof ReferenceValue) {
            newBuilder.setReferenceValue(encodeResourceName(((ReferenceValue) fieldValue).getDatabaseId(), ((DocumentKey) value).getPath()));
        } else {
            throw Assert.fail("Can't serialize %s", fieldValue);
        }
        return (Value) newBuilder.build();
    }

    public FieldValue decodeValue(Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                return NullValue.nullValue();
            case BOOLEAN_VALUE:
                return BooleanValue.valueOf(Boolean.valueOf(value.getBooleanValue()));
            case INTEGER_VALUE:
                return IntegerValue.valueOf(Long.valueOf(value.getIntegerValue()));
            case DOUBLE_VALUE:
                return DoubleValue.valueOf(Double.valueOf(value.getDoubleValue()));
            case TIMESTAMP_VALUE:
                return TimestampValue.valueOf(decodeTimestamp(value.getTimestampValue()));
            case GEO_POINT_VALUE:
                return GeoPointValue.valueOf(decodeGeoPoint(value.getGeoPointValue()));
            case BYTES_VALUE:
                return BlobValue.valueOf(Blob.fromByteString(value.getBytesValue()));
            case REFERENCE_VALUE:
                ResourcePath decodeResourceName = decodeResourceName(value.getReferenceValue());
                return ReferenceValue.valueOf(DatabaseId.forDatabase(decodeResourceName.getSegment(1), decodeResourceName.getSegment(3)), DocumentKey.fromPath(extractLocalPathFromResourceName(decodeResourceName)));
            case STRING_VALUE:
                return StringValue.valueOf(value.getStringValue());
            case ARRAY_VALUE:
                return decodeArrayValue(value.getArrayValue());
            case MAP_VALUE:
                return decodeMapValue(value.getMapValue());
            default:
                throw Assert.fail("Unknown value %s", value);
        }
    }

    private com.google.firestore.p050v1.ArrayValue encodeArrayValue(ArrayValue arrayValue) {
        List<FieldValue> internalValue = arrayValue.getInternalValue();
        com.google.firestore.p050v1.ArrayValue.Builder newBuilder = com.google.firestore.p050v1.ArrayValue.newBuilder();
        for (FieldValue encodeValue : internalValue) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (com.google.firestore.p050v1.ArrayValue) newBuilder.build();
    }

    private ArrayValue decodeArrayValue(com.google.firestore.p050v1.ArrayValue arrayValue) {
        int valuesCount = arrayValue.getValuesCount();
        ArrayList arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(arrayValue.getValues(i)));
        }
        return ArrayValue.fromList(arrayList);
    }

    private MapValue encodeMapValue(ObjectValue objectValue) {
        MapValue.Builder newBuilder = MapValue.newBuilder();
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            newBuilder.putFields((String) entry.getKey(), encodeValue((FieldValue) entry.getValue()));
        }
        return (MapValue) newBuilder.build();
    }

    private ObjectValue decodeMapValue(MapValue mapValue) {
        return decodeFields(mapValue.getFieldsMap());
    }

    public ObjectValue decodeFields(Map<String, Value> map) {
        ObjectValue emptyObject = ObjectValue.emptyObject();
        for (Entry entry : map.entrySet()) {
            emptyObject = emptyObject.set(FieldPath.fromSingleSegment((String) entry.getKey()), decodeValue((Value) entry.getValue()));
        }
        return emptyObject;
    }

    public Document encodeDocument(DocumentKey documentKey, ObjectValue objectValue) {
        Document.Builder newBuilder = Document.newBuilder();
        newBuilder.setName(encodeKey(documentKey));
        Iterator it = objectValue.getInternalValue().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            newBuilder.putFields((String) entry.getKey(), encodeValue((FieldValue) entry.getValue()));
        }
        return (Document) newBuilder.build();
    }

    public MaybeDocument decodeMaybeDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        if (batchGetDocumentsResponse.getResultCase().equals(ResultCase.FOUND)) {
            return decodeFoundDocument(batchGetDocumentsResponse);
        }
        if (batchGetDocumentsResponse.getResultCase().equals(ResultCase.MISSING)) {
            return decodeMissingDocument(batchGetDocumentsResponse);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown result case: ");
        sb.append(batchGetDocumentsResponse.getResultCase());
        throw new IllegalArgumentException(sb.toString());
    }

    private com.google.firebase.firestore.model.Document decodeFoundDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(ResultCase.FOUND), "Tried to deserialize a found document from a missing document.", new Object[0]);
        DocumentKey decodeKey = decodeKey(batchGetDocumentsResponse.getFound().getName());
        ObjectValue decodeFields = decodeFields(batchGetDocumentsResponse.getFound().getFieldsMap());
        SnapshotVersion decodeVersion = decodeVersion(batchGetDocumentsResponse.getFound().getUpdateTime());
        Assert.hardAssert(!decodeVersion.equals(SnapshotVersion.NONE), "Got a document response with no snapshot version", new Object[0]);
        com.google.firebase.firestore.model.Document document = new com.google.firebase.firestore.model.Document(decodeKey, decodeVersion, decodeFields, DocumentState.SYNCED, batchGetDocumentsResponse.getFound());
        return document;
    }

    private NoDocument decodeMissingDocument(BatchGetDocumentsResponse batchGetDocumentsResponse) {
        Assert.hardAssert(batchGetDocumentsResponse.getResultCase().equals(ResultCase.MISSING), "Tried to deserialize a missing document from a found document.", new Object[0]);
        DocumentKey decodeKey = decodeKey(batchGetDocumentsResponse.getMissing());
        SnapshotVersion decodeVersion = decodeVersion(batchGetDocumentsResponse.getReadTime());
        Assert.hardAssert(!decodeVersion.equals(SnapshotVersion.NONE), "Got a no document response with no snapshot version", new Object[0]);
        return new NoDocument(decodeKey, decodeVersion, false);
    }

    public Write encodeMutation(Mutation mutation) {
        Write.Builder newBuilder = Write.newBuilder();
        if (mutation instanceof SetMutation) {
            newBuilder.setUpdate(encodeDocument(mutation.getKey(), ((SetMutation) mutation).getValue()));
        } else if (mutation instanceof PatchMutation) {
            PatchMutation patchMutation = (PatchMutation) mutation;
            newBuilder.setUpdate(encodeDocument(mutation.getKey(), patchMutation.getValue()));
            newBuilder.setUpdateMask(encodeDocumentMask(patchMutation.getMask()));
        } else if (mutation instanceof TransformMutation) {
            TransformMutation transformMutation = (TransformMutation) mutation;
            DocumentTransform.Builder newBuilder2 = DocumentTransform.newBuilder();
            newBuilder2.setDocument(encodeKey(transformMutation.getKey()));
            for (FieldTransform encodeFieldTransform : transformMutation.getFieldTransforms()) {
                newBuilder2.addFieldTransforms(encodeFieldTransform(encodeFieldTransform));
            }
            newBuilder.setTransform(newBuilder2);
        } else if (mutation instanceof DeleteMutation) {
            newBuilder.setDelete(encodeKey(mutation.getKey()));
        } else {
            throw Assert.fail("unknown mutation type %s", mutation.getClass());
        }
        if (!mutation.getPrecondition().isNone()) {
            newBuilder.setCurrentDocument(encodePrecondition(mutation.getPrecondition()));
        }
        return (Write) newBuilder.build();
    }

    public Mutation decodeMutation(Write write) {
        Precondition precondition;
        if (write.hasCurrentDocument()) {
            precondition = decodePrecondition(write.getCurrentDocument());
        } else {
            precondition = Precondition.NONE;
        }
        int i = C36701.$SwitchMap$com$google$firestore$v1$Write$OperationCase[write.getOperationCase().ordinal()];
        boolean z = true;
        if (i != 1) {
            if (i == 2) {
                return new DeleteMutation(decodeKey(write.getDelete()), precondition);
            }
            if (i == 3) {
                ArrayList arrayList = new ArrayList();
                for (DocumentTransform.FieldTransform decodeFieldTransform : write.getTransform().getFieldTransformsList()) {
                    arrayList.add(decodeFieldTransform(decodeFieldTransform));
                }
                Boolean exists = precondition.getExists();
                if (exists == null || !exists.booleanValue()) {
                    z = false;
                }
                Assert.hardAssert(z, "Transforms only support precondition \"exists == true\"", new Object[0]);
                return new TransformMutation(decodeKey(write.getTransform().getDocument()), arrayList);
            }
            throw Assert.fail("Unknown mutation operation: %d", write.getOperationCase());
        } else if (write.hasUpdateMask()) {
            return new PatchMutation(decodeKey(write.getUpdate().getName()), decodeFields(write.getUpdate().getFieldsMap()), decodeDocumentMask(write.getUpdateMask()), precondition);
        } else {
            return new SetMutation(decodeKey(write.getUpdate().getName()), decodeFields(write.getUpdate().getFieldsMap()), precondition);
        }
    }

    private com.google.firestore.p050v1.Precondition encodePrecondition(Precondition precondition) {
        Assert.hardAssert(!precondition.isNone(), "Can't serialize an empty precondition", new Object[0]);
        com.google.firestore.p050v1.Precondition.Builder newBuilder = com.google.firestore.p050v1.Precondition.newBuilder();
        if (precondition.getUpdateTime() != null) {
            return (com.google.firestore.p050v1.Precondition) newBuilder.setUpdateTime(encodeVersion(precondition.getUpdateTime())).build();
        }
        if (precondition.getExists() != null) {
            return (com.google.firestore.p050v1.Precondition) newBuilder.setExists(precondition.getExists().booleanValue()).build();
        }
        throw Assert.fail("Unknown Precondition", new Object[0]);
    }

    private Precondition decodePrecondition(com.google.firestore.p050v1.Precondition precondition) {
        int i = C36701.f2048x8f18ca41[precondition.getConditionTypeCase().ordinal()];
        if (i == 1) {
            return Precondition.updateTime(decodeVersion(precondition.getUpdateTime()));
        }
        if (i == 2) {
            return Precondition.exists(precondition.getExists());
        }
        if (i == 3) {
            return Precondition.NONE;
        }
        throw Assert.fail("Unknown precondition", new Object[0]);
    }

    private DocumentMask encodeDocumentMask(FieldMask fieldMask) {
        DocumentMask.Builder newBuilder = DocumentMask.newBuilder();
        for (FieldPath canonicalString : fieldMask.getMask()) {
            newBuilder.addFieldPaths(canonicalString.canonicalString());
        }
        return (DocumentMask) newBuilder.build();
    }

    private FieldMask decodeDocumentMask(DocumentMask documentMask) {
        int fieldPathsCount = documentMask.getFieldPathsCount();
        HashSet hashSet = new HashSet(fieldPathsCount);
        for (int i = 0; i < fieldPathsCount; i++) {
            hashSet.add(FieldPath.fromServerFormat(documentMask.getFieldPaths(i)));
        }
        return FieldMask.fromSet(hashSet);
    }

    private DocumentTransform.FieldTransform encodeFieldTransform(FieldTransform fieldTransform) {
        TransformOperation operation = fieldTransform.getOperation();
        if (operation instanceof ServerTimestampOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setSetToServerValue(ServerValue.REQUEST_TIME).build();
        }
        if (operation instanceof Union) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setAppendMissingElements(encodeArrayTransformElements(((Union) operation).getElements())).build();
        } else if (operation instanceof Remove) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setRemoveAllFromArray(encodeArrayTransformElements(((Remove) operation).getElements())).build();
        } else if (operation instanceof NumericIncrementTransformOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setIncrement(encodeValue(((NumericIncrementTransformOperation) operation).getOperand())).build();
        } else {
            throw Assert.fail("Unknown transform: %s", operation);
        }
    }

    private com.google.firestore.p050v1.ArrayValue encodeArrayTransformElements(List<FieldValue> list) {
        com.google.firestore.p050v1.ArrayValue.Builder newBuilder = com.google.firestore.p050v1.ArrayValue.newBuilder();
        for (FieldValue encodeValue : list) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (com.google.firestore.p050v1.ArrayValue) newBuilder.build();
    }

    private FieldTransform decodeFieldTransform(DocumentTransform.FieldTransform fieldTransform) {
        int i = C36701.f2046xdd498c9f[fieldTransform.getTransformTypeCase().ordinal()];
        if (i == 1) {
            Assert.hardAssert(fieldTransform.getSetToServerValue() == ServerValue.REQUEST_TIME, "Unknown transform setToServerValue: %s", fieldTransform.getSetToServerValue());
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), ServerTimestampOperation.getInstance());
        } else if (i == 2) {
            return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new Union(decodeArrayTransformElements(fieldTransform.getAppendMissingElements())));
        } else {
            if (i == 3) {
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new Remove(decodeArrayTransformElements(fieldTransform.getRemoveAllFromArray())));
            }
            if (i == 4) {
                FieldValue decodeValue = decodeValue(fieldTransform.getIncrement());
                Assert.hardAssert(decodeValue instanceof NumberValue, "Expected NUMERIC_ADD transform to be of number type, but was %s", decodeValue.getClass().getCanonicalName());
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new NumericIncrementTransformOperation((NumberValue) decodeValue(fieldTransform.getIncrement())));
            }
            throw Assert.fail("Unknown FieldTransform proto: %s", fieldTransform);
        }
    }

    private List<FieldValue> decodeArrayTransformElements(com.google.firestore.p050v1.ArrayValue arrayValue) {
        int valuesCount = arrayValue.getValuesCount();
        ArrayList arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(arrayValue.getValues(i)));
        }
        return arrayList;
    }

    public MutationResult decodeMutationResult(WriteResult writeResult, SnapshotVersion snapshotVersion) {
        SnapshotVersion decodeVersion = decodeVersion(writeResult.getUpdateTime());
        if (!SnapshotVersion.NONE.equals(decodeVersion)) {
            snapshotVersion = decodeVersion;
        }
        ArrayList arrayList = null;
        int transformResultsCount = writeResult.getTransformResultsCount();
        if (transformResultsCount > 0) {
            arrayList = new ArrayList(transformResultsCount);
            for (int i = 0; i < transformResultsCount; i++) {
                arrayList.add(decodeValue(writeResult.getTransformResults(i)));
            }
        }
        return new MutationResult(snapshotVersion, arrayList);
    }

    public Map<String, String> encodeListenRequestLabels(QueryData queryData) {
        String encodeLabel = encodeLabel(queryData.getPurpose());
        if (encodeLabel == null) {
            return null;
        }
        HashMap hashMap = new HashMap(1);
        hashMap.put("goog-listen-tags", encodeLabel);
        return hashMap;
    }

    private String encodeLabel(QueryPurpose queryPurpose) {
        int i = C36701.$SwitchMap$com$google$firebase$firestore$local$QueryPurpose[queryPurpose.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            return "existence-filter-mismatch";
        }
        if (i == 3) {
            return "limbo-document";
        }
        throw Assert.fail("Unrecognized query purpose: %s", queryPurpose);
    }

    public Target encodeTarget(QueryData queryData) {
        Target.Builder newBuilder = Target.newBuilder();
        Query query = queryData.getQuery();
        if (query.isDocumentQuery()) {
            newBuilder.setDocuments(encodeDocumentsTarget(query));
        } else {
            newBuilder.setQuery(encodeQueryTarget(query));
        }
        newBuilder.setTargetId(queryData.getTargetId());
        newBuilder.setResumeToken(queryData.getResumeToken());
        return (Target) newBuilder.build();
    }

    public DocumentsTarget encodeDocumentsTarget(Query query) {
        DocumentsTarget.Builder newBuilder = DocumentsTarget.newBuilder();
        newBuilder.addDocuments(encodeQueryPath(query.getPath()));
        return (DocumentsTarget) newBuilder.build();
    }

    public Query decodeDocumentsTarget(DocumentsTarget documentsTarget) {
        int documentsCount = documentsTarget.getDocumentsCount();
        Assert.hardAssert(documentsCount == 1, "DocumentsTarget contained other than 1 document %d", Integer.valueOf(documentsCount));
        return Query.atPath(decodeQueryPath(documentsTarget.getDocuments(0)));
    }

    public QueryTarget encodeQueryTarget(Query query) {
        QueryTarget.Builder newBuilder = QueryTarget.newBuilder();
        StructuredQuery.Builder newBuilder2 = StructuredQuery.newBuilder();
        ResourcePath path = query.getPath();
        boolean z = true;
        if (query.getCollectionGroup() != null) {
            Assert.hardAssert(path.length() % 2 == 0, "Collection Group queries should be within a document path or root.", new Object[0]);
            newBuilder.setParent(encodeQueryPath(path));
            CollectionSelector.Builder newBuilder3 = CollectionSelector.newBuilder();
            newBuilder3.setCollectionId(query.getCollectionGroup());
            newBuilder3.setAllDescendants(true);
            newBuilder2.addFrom(newBuilder3);
        } else {
            if (path.length() % 2 == 0) {
                z = false;
            }
            Assert.hardAssert(z, "Document queries with filters are not supported.", new Object[0]);
            newBuilder.setParent(encodeQueryPath((ResourcePath) path.popLast()));
            CollectionSelector.Builder newBuilder4 = CollectionSelector.newBuilder();
            newBuilder4.setCollectionId(path.getLastSegment());
            newBuilder2.addFrom(newBuilder4);
        }
        if (query.getFilters().size() > 0) {
            newBuilder2.setWhere(encodeFilters(query.getFilters()));
        }
        for (OrderBy encodeOrderBy : query.getOrderBy()) {
            newBuilder2.addOrderBy(encodeOrderBy(encodeOrderBy));
        }
        if (query.hasLimit()) {
            newBuilder2.setLimit(Int32Value.newBuilder().setValue((int) query.getLimit()));
        }
        if (query.getStartAt() != null) {
            newBuilder2.setStartAt(encodeBound(query.getStartAt()));
        }
        if (query.getEndAt() != null) {
            newBuilder2.setEndAt(encodeBound(query.getEndAt()));
        }
        newBuilder.setStructuredQuery(newBuilder2);
        return (QueryTarget) newBuilder.build();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.firebase.firestore.core.Query decodeQueryTarget(com.google.firestore.p050v1.Target.QueryTarget r14) {
        /*
            r13 = this;
            java.lang.String r0 = r14.getParent()
            com.google.firebase.firestore.model.ResourcePath r0 = r13.decodeQueryPath(r0)
            com.google.firestore.v1.StructuredQuery r14 = r14.getStructuredQuery()
            int r1 = r14.getFromCount()
            r2 = 0
            r3 = 0
            if (r1 <= 0) goto L_0x003b
            r4 = 1
            if (r1 != r4) goto L_0x0018
            goto L_0x0019
        L_0x0018:
            r4 = 0
        L_0x0019:
            java.lang.Object[] r1 = new java.lang.Object[r3]
            java.lang.String r5 = "StructuredQuery.from with more than one collection is not supported."
            com.google.firebase.firestore.util.Assert.hardAssert(r4, r5, r1)
            com.google.firestore.v1.StructuredQuery$CollectionSelector r1 = r14.getFrom(r3)
            boolean r4 = r1.getAllDescendants()
            if (r4 == 0) goto L_0x0031
            java.lang.String r1 = r1.getCollectionId()
            r5 = r0
            r6 = r1
            goto L_0x003d
        L_0x0031:
            java.lang.String r1 = r1.getCollectionId()
            com.google.firebase.firestore.model.BasePath r0 = r0.append(r1)
            com.google.firebase.firestore.model.ResourcePath r0 = (com.google.firebase.firestore.model.ResourcePath) r0
        L_0x003b:
            r5 = r0
            r6 = r2
        L_0x003d:
            boolean r0 = r14.hasWhere()
            if (r0 == 0) goto L_0x004c
            com.google.firestore.v1.StructuredQuery$Filter r0 = r14.getWhere()
            java.util.List r0 = r13.decodeFilters(r0)
            goto L_0x0050
        L_0x004c:
            java.util.List r0 = java.util.Collections.emptyList()
        L_0x0050:
            r7 = r0
            int r0 = r14.getOrderByCount()
            if (r0 <= 0) goto L_0x006e
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
        L_0x005c:
            if (r3 >= r0) goto L_0x006c
            com.google.firestore.v1.StructuredQuery$Order r4 = r14.getOrderBy(r3)
            com.google.firebase.firestore.core.OrderBy r4 = r13.decodeOrderBy(r4)
            r1.add(r4)
            int r3 = r3 + 1
            goto L_0x005c
        L_0x006c:
            r8 = r1
            goto L_0x0073
        L_0x006e:
            java.util.List r0 = java.util.Collections.emptyList()
            r8 = r0
        L_0x0073:
            r0 = -1
            boolean r3 = r14.hasLimit()
            if (r3 == 0) goto L_0x0084
            com.google.protobuf.Int32Value r0 = r14.getLimit()
            int r0 = r0.getValue()
            long r0 = (long) r0
        L_0x0084:
            r9 = r0
            boolean r0 = r14.hasStartAt()
            if (r0 == 0) goto L_0x0095
            com.google.firestore.v1.Cursor r0 = r14.getStartAt()
            com.google.firebase.firestore.core.Bound r0 = r13.decodeBound(r0)
            r11 = r0
            goto L_0x0096
        L_0x0095:
            r11 = r2
        L_0x0096:
            boolean r0 = r14.hasEndAt()
            if (r0 == 0) goto L_0x00a4
            com.google.firestore.v1.Cursor r14 = r14.getEndAt()
            com.google.firebase.firestore.core.Bound r2 = r13.decodeBound(r14)
        L_0x00a4:
            r12 = r2
            com.google.firebase.firestore.core.Query r14 = new com.google.firebase.firestore.core.Query
            r4 = r14
            r4.<init>(r5, r6, r7, r8, r9, r11, r12)
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.decodeQueryTarget(com.google.firestore.v1.Target$QueryTarget):com.google.firebase.firestore.core.Query");
    }

    private Filter encodeFilters(List<com.google.firebase.firestore.core.Filter> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (com.google.firebase.firestore.core.Filter filter : list) {
            if (filter instanceof RelationFilter) {
                arrayList.add(encodeRelationFilter((RelationFilter) filter));
            } else {
                arrayList.add(encodeUnaryFilter(filter));
            }
        }
        if (list.size() == 1) {
            return (Filter) arrayList.get(0);
        }
        CompositeFilter.Builder newBuilder = CompositeFilter.newBuilder();
        newBuilder.setOp(CompositeFilter.Operator.AND);
        newBuilder.addAllFilters(arrayList);
        return (Filter) Filter.newBuilder().setCompositeFilter(newBuilder).build();
    }

    private List<com.google.firebase.firestore.core.Filter> decodeFilters(Filter filter) {
        List<Filter> list;
        if (filter.getFilterTypeCase() == FilterTypeCase.COMPOSITE_FILTER) {
            Assert.hardAssert(filter.getCompositeFilter().getOp() == CompositeFilter.Operator.AND, "Only AND-type composite filters are supported, got %d", filter.getCompositeFilter().getOp());
            list = filter.getCompositeFilter().getFiltersList();
        } else {
            list = Collections.singletonList(filter);
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Filter filter2 : list) {
            int i = C36701.f2050x9d2ee979[filter2.getFilterTypeCase().ordinal()];
            if (i == 1) {
                throw Assert.fail("Nested composite filters are not supported.", new Object[0]);
            } else if (i == 2) {
                arrayList.add(decodeRelationFilter(filter2.getFieldFilter()));
            } else if (i == 3) {
                arrayList.add(decodeUnaryFilter(filter2.getUnaryFilter()));
            } else {
                throw Assert.fail("Unrecognized Filter.filterType %d", filter2.getFilterTypeCase());
            }
        }
        return arrayList;
    }

    private Filter encodeRelationFilter(RelationFilter relationFilter) {
        FieldFilter.Builder newBuilder = FieldFilter.newBuilder();
        newBuilder.setField(encodeFieldPath(relationFilter.getField()));
        newBuilder.setOp(encodeRelationFilterOperator(relationFilter.getOperator()));
        newBuilder.setValue(encodeValue(relationFilter.getValue()));
        return (Filter) Filter.newBuilder().setFieldFilter(newBuilder).build();
    }

    private com.google.firebase.firestore.core.Filter decodeRelationFilter(FieldFilter fieldFilter) {
        return com.google.firebase.firestore.core.Filter.create(FieldPath.fromServerFormat(fieldFilter.getField().getFieldPath()), decodeRelationFilterOperator(fieldFilter.getOp()), decodeValue(fieldFilter.getValue()));
    }

    private Filter encodeUnaryFilter(com.google.firebase.firestore.core.Filter filter) {
        UnaryFilter.Builder newBuilder = UnaryFilter.newBuilder();
        newBuilder.setField(encodeFieldPath(filter.getField()));
        if (filter instanceof NaNFilter) {
            newBuilder.setOp(Operator.IS_NAN);
        } else if (filter instanceof NullFilter) {
            newBuilder.setOp(Operator.IS_NULL);
        } else {
            throw Assert.fail("Unrecognized filter: %s", filter.getCanonicalId());
        }
        return (Filter) Filter.newBuilder().setUnaryFilter(newBuilder).build();
    }

    private com.google.firebase.firestore.core.Filter decodeUnaryFilter(UnaryFilter unaryFilter) {
        FieldPath fromServerFormat = FieldPath.fromServerFormat(unaryFilter.getField().getFieldPath());
        int i = C36701.f2051xf473b456[unaryFilter.getOp().ordinal()];
        if (i == 1) {
            return new NaNFilter(fromServerFormat);
        }
        if (i == 2) {
            return new NullFilter(fromServerFormat);
        }
        throw Assert.fail("Unrecognized UnaryFilter.operator %d", unaryFilter.getOp());
    }

    private FieldReference encodeFieldPath(FieldPath fieldPath) {
        return (FieldReference) FieldReference.newBuilder().setFieldPath(fieldPath.canonicalString()).build();
    }

    private FieldFilter.Operator encodeRelationFilterOperator(com.google.firebase.firestore.core.Filter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return FieldFilter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return FieldFilter.Operator.EQUAL;
            case GREATER_THAN:
                return FieldFilter.Operator.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL:
                return FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case ARRAY_CONTAINS:
                return FieldFilter.Operator.ARRAY_CONTAINS;
            default:
                throw Assert.fail("Unknown operator %d", operator);
        }
    }

    private com.google.firebase.firestore.core.Filter.Operator decodeRelationFilterOperator(FieldFilter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return com.google.firebase.firestore.core.Filter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.EQUAL;
            case GREATER_THAN_OR_EQUAL:
                return com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN_OR_EQUAL;
            case GREATER_THAN:
                return com.google.firebase.firestore.core.Filter.Operator.GREATER_THAN;
            case ARRAY_CONTAINS:
                return com.google.firebase.firestore.core.Filter.Operator.ARRAY_CONTAINS;
            default:
                throw Assert.fail("Unhandled FieldFilter.operator %d", operator);
        }
    }

    private Order encodeOrderBy(OrderBy orderBy) {
        Order.Builder newBuilder = Order.newBuilder();
        if (orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) {
            newBuilder.setDirection(Direction.ASCENDING);
        } else {
            newBuilder.setDirection(Direction.DESCENDING);
        }
        newBuilder.setField(encodeFieldPath(orderBy.getField()));
        return (Order) newBuilder.build();
    }

    private OrderBy decodeOrderBy(Order order) {
        OrderBy.Direction direction;
        FieldPath fromServerFormat = FieldPath.fromServerFormat(order.getField().getFieldPath());
        int i = C36701.$SwitchMap$com$google$firestore$v1$StructuredQuery$Direction[order.getDirection().ordinal()];
        if (i == 1) {
            direction = OrderBy.Direction.ASCENDING;
        } else if (i == 2) {
            direction = OrderBy.Direction.DESCENDING;
        } else {
            throw Assert.fail("Unrecognized direction %d", order.getDirection());
        }
        return OrderBy.getInstance(direction, fromServerFormat);
    }

    private Cursor encodeBound(Bound bound) {
        Cursor.Builder newBuilder = Cursor.newBuilder();
        newBuilder.setBefore(bound.isBefore());
        for (FieldValue encodeValue : bound.getPosition()) {
            newBuilder.addValues(encodeValue(encodeValue));
        }
        return (Cursor) newBuilder.build();
    }

    private Bound decodeBound(Cursor cursor) {
        int valuesCount = cursor.getValuesCount();
        ArrayList arrayList = new ArrayList(valuesCount);
        for (int i = 0; i < valuesCount; i++) {
            arrayList.add(decodeValue(cursor.getValues(i)));
        }
        return new Bound(arrayList, cursor.getBefore());
    }

    public WatchChange decodeWatchChange(ListenResponse listenResponse) {
        WatchTargetChangeType watchTargetChangeType;
        WatchChange watchTargetChange;
        int i = C36701.f2047x1837d9f[listenResponse.getResponseTypeCase().ordinal()];
        Status status = null;
        if (i == 1) {
            TargetChange targetChange = listenResponse.getTargetChange();
            int i2 = C36701.$SwitchMap$com$google$firestore$v1$TargetChange$TargetChangeType[targetChange.getTargetChangeType().ordinal()];
            if (i2 == 1) {
                watchTargetChangeType = WatchTargetChangeType.NoChange;
            } else if (i2 == 2) {
                watchTargetChangeType = WatchTargetChangeType.Added;
            } else if (i2 == 3) {
                watchTargetChangeType = WatchTargetChangeType.Removed;
                status = fromStatus(targetChange.getCause());
            } else if (i2 == 4) {
                watchTargetChangeType = WatchTargetChangeType.Current;
            } else if (i2 == 5) {
                watchTargetChangeType = WatchTargetChangeType.Reset;
            } else {
                throw new IllegalArgumentException("Unknown target change type");
            }
            watchTargetChange = new WatchTargetChange(watchTargetChangeType, targetChange.getTargetIdsList(), targetChange.getResumeToken(), status);
        } else if (i == 2) {
            DocumentChange documentChange = listenResponse.getDocumentChange();
            List targetIdsList = documentChange.getTargetIdsList();
            List removedTargetIdsList = documentChange.getRemovedTargetIdsList();
            DocumentKey decodeKey = decodeKey(documentChange.getDocument().getName());
            SnapshotVersion decodeVersion = decodeVersion(documentChange.getDocument().getUpdateTime());
            Assert.hardAssert(!decodeVersion.equals(SnapshotVersion.NONE), "Got a document change without an update time", new Object[0]);
            com.google.firebase.firestore.model.Document document = new com.google.firebase.firestore.model.Document(decodeKey, decodeVersion, decodeFields(documentChange.getDocument().getFieldsMap()), DocumentState.SYNCED, documentChange.getDocument());
            return new WatchChange.DocumentChange(targetIdsList, removedTargetIdsList, document.getKey(), document);
        } else if (i == 3) {
            DocumentDelete documentDelete = listenResponse.getDocumentDelete();
            List removedTargetIdsList2 = documentDelete.getRemovedTargetIdsList();
            NoDocument noDocument = new NoDocument(decodeKey(documentDelete.getDocument()), decodeVersion(documentDelete.getReadTime()), false);
            return new WatchChange.DocumentChange(Collections.emptyList(), removedTargetIdsList2, noDocument.getKey(), noDocument);
        } else if (i == 4) {
            DocumentRemove documentRemove = listenResponse.getDocumentRemove();
            watchTargetChange = new WatchChange.DocumentChange(Collections.emptyList(), documentRemove.getRemovedTargetIdsList(), decodeKey(documentRemove.getDocument()), null);
        } else if (i == 5) {
            ExistenceFilter filter = listenResponse.getFilter();
            return new ExistenceFilterWatchChange(filter.getTargetId(), new ExistenceFilter(filter.getCount()));
        } else {
            throw new IllegalArgumentException("Unknown change type set");
        }
        return watchTargetChange;
    }

    public SnapshotVersion decodeVersionFromListenResponse(ListenResponse listenResponse) {
        if (listenResponse.getResponseTypeCase() != ResponseTypeCase.TARGET_CHANGE) {
            return SnapshotVersion.NONE;
        }
        if (listenResponse.getTargetChange().getTargetIdsCount() != 0) {
            return SnapshotVersion.NONE;
        }
        return decodeVersion(listenResponse.getTargetChange().getReadTime());
    }

    private Status fromStatus(com.google.rpc.Status status) {
        return Status.fromCodeValue(status.getCode()).withDescription(status.getMessage());
    }
}
