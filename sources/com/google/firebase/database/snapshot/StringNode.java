package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class StringNode extends LeafNode<StringNode> {
    private final String value;

    /* renamed from: com.google.firebase.database.snapshot.StringNode$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C36291 {

        /* renamed from: $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion */
        static final /* synthetic */ int[] f2017x2aed15f4 = new int[HashVersion.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.google.firebase.database.snapshot.Node$HashVersion[] r0 = com.google.firebase.database.snapshot.Node.HashVersion.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2017x2aed15f4 = r0
                int[] r0 = f2017x2aed15f4     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.snapshot.Node$HashVersion r1 = com.google.firebase.database.snapshot.Node.HashVersion.V1     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2017x2aed15f4     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.snapshot.Node$HashVersion r1 = com.google.firebase.database.snapshot.Node.HashVersion.V2     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.StringNode.C36291.<clinit>():void");
        }
    }

    public StringNode(String str, Node node) {
        super(node);
        this.value = str;
    }

    public Object getValue() {
        return this.value;
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        int i = C36291.f2017x2aed15f4[hashVersion.ordinal()];
        String str = "string:";
        if (i == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(getPriorityHash(hashVersion));
            sb.append(str);
            sb.append(this.value);
            return sb.toString();
        } else if (i == 2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(getPriorityHash(hashVersion));
            sb2.append(str);
            sb2.append(Utilities.stringHashV2Representation(this.value));
            return sb2.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Invalid hash version for string node: ");
            sb3.append(hashVersion);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public StringNode updatePriority(Node node) {
        return new StringNode(this.value, node);
    }

    /* access modifiers changed from: protected */
    public LeafType getLeafType() {
        return LeafType.String;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(StringNode stringNode) {
        return this.value.compareTo(stringNode.value);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof StringNode)) {
            return false;
        }
        StringNode stringNode = (StringNode) obj;
        if (this.value.equals(stringNode.value) && this.priority.equals(stringNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
