package com.flurry.sdk;

import com.flurry.sdk.C1622bq.C1623a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/* renamed from: com.flurry.sdk.bp */
public final class C1616bp {

    /* renamed from: a */
    public static final Integer f703a = Integer.valueOf(50);

    /* renamed from: b */
    String f704b;

    /* renamed from: c */
    LinkedHashMap<String, List<String>> f705c;

    public C1616bp(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("Main");
        this.f704b = sb.toString();
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized List<String> mo16303a(String str) {
        DataInputStream dataInputStream;
        C1734dz.m869a();
        StringBuilder sb = new StringBuilder("Reading Index File for ");
        sb.append(str);
        sb.append(" file name:");
        sb.append(C1576b.m502a().getFileStreamPath(".FlurrySenderIndex.info.".concat(String.valueOf(str))));
        C1685cy.m754a(5, "FlurryDataSenderIndex", sb.toString());
        File fileStreamPath = C1576b.m502a().getFileStreamPath(".FlurrySenderIndex.info.".concat(String.valueOf(str)));
        List<String> list = null;
        if (fileStreamPath.exists()) {
            StringBuilder sb2 = new StringBuilder("Reading Index File for ");
            sb2.append(str);
            sb2.append(" Found file.");
            C1685cy.m754a(5, "FlurryDataSenderIndex", sb2.toString());
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        C1734dz.m871a((Closeable) dataInputStream);
                        return null;
                    }
                    ArrayList arrayList = new ArrayList(readUnsignedShort);
                    int i = 0;
                    while (i < readUnsignedShort) {
                        try {
                            int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                            StringBuilder sb3 = new StringBuilder("read iter ");
                            sb3.append(i);
                            sb3.append(" dataLength = ");
                            sb3.append(readUnsignedShort2);
                            C1685cy.m754a(4, "FlurryDataSenderIndex", sb3.toString());
                            byte[] bArr = new byte[readUnsignedShort2];
                            dataInputStream.readFully(bArr);
                            arrayList.add(new String(bArr));
                            i++;
                        } catch (Throwable th) {
                            th = th;
                            list = arrayList;
                            try {
                                C1685cy.m755a(6, "FlurryDataSenderIndex", "Error when loading persistent file", th);
                                C1734dz.m871a((Closeable) dataInputStream);
                                return list;
                            } catch (Throwable th2) {
                                C1734dz.m871a((Closeable) dataInputStream);
                                throw th2;
                            }
                        }
                    }
                    dataInputStream.readUnsignedShort();
                    C1734dz.m871a((Closeable) dataInputStream);
                    list = arrayList;
                } catch (Throwable th3) {
                    th = th3;
                    C1685cy.m755a(6, "FlurryDataSenderIndex", "Error when loading persistent file", th);
                    C1734dz.m871a((Closeable) dataInputStream);
                    return list;
                }
            } catch (Throwable th4) {
                th = th4;
                dataInputStream = null;
                C1685cy.m755a(6, "FlurryDataSenderIndex", "Error when loading persistent file", th);
                C1734dz.m871a((Closeable) dataInputStream);
                return list;
            }
        } else {
            C1685cy.m754a(5, "FlurryDataSenderIndex", "Agent cache file doesn't exist.");
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo16306b(String str) {
        List<String> a = mo16303a(str);
        String str2 = "FlurryDataSenderIndex";
        if (a == null) {
            C1685cy.m766c(str2, "No old file to replace");
            return;
        }
        for (String str3 : a) {
            byte[] g = m572g(str3);
            if (g == null) {
                C1685cy.m754a(6, str2, "File does not exist");
            } else {
                C1734dz.m869a();
                StringBuilder sb = new StringBuilder("Saving Block File for ");
                sb.append(str3);
                String str4 = " file name:";
                sb.append(str4);
                sb.append(C1576b.m502a().getFileStreamPath(C1611bo.m563a(str3)));
                C1685cy.m754a(5, str2, sb.toString());
                C1611bo.m564b(str3).mo16528a(new C1611bo(g));
                C1734dz.m869a();
                StringBuilder sb2 = new StringBuilder("Deleting  block File for ");
                sb2.append(str3);
                sb2.append(str4);
                String str5 = ".flurrydatasenderblock.";
                sb2.append(C1576b.m502a().getFileStreamPath(str5.concat(String.valueOf(str3))));
                C1685cy.m754a(5, str2, sb2.toString());
                File fileStreamPath = C1576b.m502a().getFileStreamPath(str5.concat(String.valueOf(str3)));
                if (fileStreamPath.exists()) {
                    boolean delete = fileStreamPath.delete();
                    StringBuilder sb3 = new StringBuilder("Found file for ");
                    sb3.append(str3);
                    sb3.append(". Deleted - ");
                    sb3.append(delete);
                    C1685cy.m754a(5, str2, sb3.toString());
                }
            }
        }
        m569a(str, a, ".YFlurrySenderIndex.info.");
        m570c(str);
    }

    /* renamed from: c */
    static void m570c(String str) {
        C1734dz.m869a();
        StringBuilder sb = new StringBuilder("Deleting Index File for ");
        sb.append(str);
        sb.append(" file name:");
        String str2 = ".FlurrySenderIndex.info.";
        sb.append(C1576b.m502a().getFileStreamPath(str2.concat(String.valueOf(str))));
        String str3 = "FlurryDataSenderIndex";
        C1685cy.m754a(5, str3, sb.toString());
        File fileStreamPath = C1576b.m502a().getFileStreamPath(str2.concat(String.valueOf(str)));
        if (fileStreamPath.exists()) {
            boolean delete = fileStreamPath.delete();
            StringBuilder sb2 = new StringBuilder("Found file for ");
            sb2.append(str);
            sb2.append(". Deleted - ");
            sb2.append(delete);
            C1685cy.m754a(5, str3, sb2.toString());
        }
    }

    /* renamed from: d */
    static String m571d(String str) {
        return ".YFlurrySenderIndex.info.".concat(String.valueOf(str));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: e */
    public final synchronized List<String> mo16307e(String str) {
        C1734dz.m869a();
        StringBuilder sb = new StringBuilder("Reading Index File for ");
        sb.append(str);
        sb.append(" file name:");
        sb.append(C1576b.m502a().getFileStreamPath(m571d(str)));
        C1685cy.m754a(5, "FlurryDataSenderIndex", sb.toString());
        List<C1622bq> list = (List) new C1941l(C1576b.m502a().getFileStreamPath(m571d(str)), ".YFlurrySenderIndex.info.", 1, new C1729dv<List<C1622bq>>() {
            /* renamed from: a */
            public final C1724ds<List<C1622bq>> mo16258a(int i) {
                return new C1721dr(new C1623a());
            }
        }).mo16527a();
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (C1622bq bqVar : list) {
            arrayList.add(bqVar.f711a);
        }
        return arrayList;
    }

    /* renamed from: g */
    private static byte[] m572g(String str) {
        DataInputStream dataInputStream;
        C1734dz.m869a();
        StringBuilder sb = new StringBuilder("Reading block File for ");
        sb.append(str);
        sb.append(" file name:");
        String str2 = ".flurrydatasenderblock.";
        sb.append(C1576b.m502a().getFileStreamPath(str2.concat(String.valueOf(str))));
        String str3 = "FlurryDataSenderIndex";
        C1685cy.m754a(5, str3, sb.toString());
        File fileStreamPath = C1576b.m502a().getFileStreamPath(str2.concat(String.valueOf(str)));
        byte[] bArr = null;
        if (fileStreamPath.exists()) {
            StringBuilder sb2 = new StringBuilder("Reading Index File for ");
            sb2.append(str);
            sb2.append(" Found file.");
            C1685cy.m754a(5, str3, sb2.toString());
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        C1734dz.m871a((Closeable) dataInputStream);
                        return null;
                    }
                    bArr = new byte[readUnsignedShort];
                    dataInputStream.readFully(bArr);
                    dataInputStream.readUnsignedShort();
                    C1734dz.m871a((Closeable) dataInputStream);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                dataInputStream = null;
                try {
                    C1685cy.m755a(6, str3, "Error when loading persistent file", th);
                    C1734dz.m871a((Closeable) dataInputStream);
                    return bArr;
                } catch (Throwable th3) {
                    C1734dz.m871a((Closeable) dataInputStream);
                    throw th3;
                }
            }
        } else {
            C1685cy.m754a(4, str3, "Agent cache file doesn't exist.");
        }
        return bArr;
    }

    /* renamed from: a */
    private synchronized void m569a(String str, List<String> list, String str2) {
        C1734dz.m869a();
        StringBuilder sb = new StringBuilder("Saving Index File for ");
        sb.append(str);
        sb.append(" file name:");
        sb.append(C1576b.m502a().getFileStreamPath(m571d(str)));
        C1685cy.m754a(5, "FlurryDataSenderIndex", sb.toString());
        C1941l lVar = new C1941l(C1576b.m502a().getFileStreamPath(m571d(str)), str2, 1, new C1729dv<List<C1622bq>>() {
            /* renamed from: a */
            public final C1724ds<List<C1622bq>> mo16258a(int i) {
                return new C1721dr(new C1623a());
            }
        });
        ArrayList arrayList = new ArrayList();
        for (String bqVar : list) {
            arrayList.add(new C1622bq(bqVar));
        }
        lVar.mo16528a(arrayList);
    }

    /* renamed from: a */
    public final synchronized void mo16304a(C1611bo boVar, String str) {
        boolean z;
        C1685cy.m754a(4, "FlurryDataSenderIndex", "addBlockInfo".concat(String.valueOf(str)));
        String str2 = boVar.f698a;
        List list = (List) this.f705c.get(str);
        if (list == null) {
            C1685cy.m754a(4, "FlurryDataSenderIndex", "New Data Key");
            list = new LinkedList();
            z = true;
        } else {
            z = false;
        }
        list.add(str2);
        if (list.size() > f703a.intValue()) {
            C1611bo.m564b((String) list.get(0)).mo16529b();
            list.remove(0);
        }
        this.f705c.put(str, list);
        m569a(str, list, ".YFlurrySenderIndex.info.");
        if (z) {
            m568a();
        }
    }

    /* renamed from: a */
    private synchronized void m568a() {
        LinkedList linkedList = new LinkedList(this.f705c.keySet());
        new C1941l(C1576b.m502a().getFileStreamPath(m571d(this.f704b)), ".YFlurrySenderIndex.info.", 1, new C1729dv<List<C1622bq>>() {
            /* renamed from: a */
            public final C1724ds<List<C1622bq>> mo16258a(int i) {
                return new C1721dr(new C1623a());
            }
        }).mo16529b();
        if (!linkedList.isEmpty()) {
            m569a(this.f704b, linkedList, this.f704b);
        }
    }

    /* renamed from: a */
    public final boolean mo16305a(String str, String str2) {
        boolean z;
        List list = (List) this.f705c.get(str2);
        if (list != null) {
            C1611bo.m564b(str).mo16529b();
            z = list.remove(str);
        } else {
            z = false;
        }
        if (list == null || list.isEmpty()) {
            m573h(str2);
        } else {
            this.f705c.put(str2, list);
            m569a(str2, list, ".YFlurrySenderIndex.info.");
        }
        return z;
    }

    /* renamed from: h */
    private synchronized boolean m573h(String str) {
        boolean b;
        C1734dz.m869a();
        C1941l lVar = new C1941l(C1576b.m502a().getFileStreamPath(m571d(str)), ".YFlurrySenderIndex.info.", 1, new C1729dv<List<C1622bq>>() {
            /* renamed from: a */
            public final C1724ds<List<C1622bq>> mo16258a(int i) {
                return new C1721dr(new C1623a());
            }
        });
        List<String> f = mo16308f(str);
        if (f != null && !f.isEmpty()) {
            StringBuilder sb = new StringBuilder("discardOutdatedBlocksForDataKey: notSentBlocks = ");
            sb.append(f.size());
            C1685cy.m754a(4, "FlurryDataSenderIndex", sb.toString());
            for (String str2 : f) {
                C1611bo.m564b(str2).mo16529b();
                C1685cy.m754a(4, "FlurryDataSenderIndex", "discardOutdatedBlocksForDataKey: removed block = ".concat(String.valueOf(str2)));
            }
        }
        this.f705c.remove(str);
        b = lVar.mo16529b();
        m568a();
        return b;
    }

    /* renamed from: f */
    public final List<String> mo16308f(String str) {
        List list = (List) this.f705c.get(str);
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList(list);
    }
}
