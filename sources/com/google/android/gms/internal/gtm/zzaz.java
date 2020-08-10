package com.google.android.gms.internal.gtm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import com.braintreepayments.api.models.PostalAddressParser;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

final class zzaz extends SQLiteOpenHelper {
    private final /* synthetic */ zzay zzxo;

    zzaz(zzay zzay, Context context, String str) {
        this.zzxo = zzay;
        super(context, str, null, 1);
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (this.zzxo.zzxn.zzj(3600000)) {
            try {
                return super.getWritableDatabase();
            } catch (SQLiteException unused) {
                this.zzxo.zzxn.start();
                this.zzxo.zzu("Opening the database failed, dropping the table and recreating it");
                this.zzxo.getContext().getDatabasePath(zzay.zzdt()).delete();
                try {
                    SQLiteDatabase writableDatabase = super.getWritableDatabase();
                    this.zzxo.zzxn.clear();
                    return writableDatabase;
                } catch (SQLiteException e) {
                    this.zzxo.zze("Failed to open freshly created database", e);
                    throw e;
                }
            }
        } else {
            throw new SQLiteException("Database open failed");
        }
    }

    private final boolean zza(SQLiteDatabase sQLiteDatabase, String str) {
        Cursor cursor = null;
        try {
            SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
            Cursor query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{PostalAddressParser.USER_ADDRESS_NAME_KEY}, "name=?", new String[]{str}, null, null, null);
            boolean moveToFirst = query.moveToFirst();
            if (query != null) {
                query.close();
            }
            return moveToFirst;
        } catch (SQLiteException e) {
            this.zzxo.zzc("Error querying for table", str, e);
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static Set<String> zzb(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 22);
        sb.append("SELECT * FROM ");
        sb.append(str);
        sb.append(" LIMIT 0");
        Cursor rawQuery = sQLiteDatabase.rawQuery(sb.toString(), null);
        try {
            String[] columnNames = rawQuery.getColumnNames();
            for (String add : columnNames) {
                hashSet.add(add);
            }
            return hashSet;
        } finally {
            rawQuery.close();
        }
    }

    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        String str = "hits2";
        if (!zza(sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(zzay.zzxj);
        } else {
            Set zzb = zzb(sQLiteDatabase, str);
            String[] strArr = {"hit_id", "hit_string", "hit_time", "hit_url"};
            for (int i = 0; i < 4; i++) {
                String str2 = strArr[i];
                if (!zzb.remove(str2)) {
                    String str3 = "Database hits2 is missing required column: ";
                    String valueOf = String.valueOf(str2);
                    throw new SQLiteException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
                }
            }
            boolean z = !zzb.remove("hit_app_id");
            if (!zzb.isEmpty()) {
                throw new SQLiteException("Database hits2 has extra columns");
            } else if (z) {
                sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id INTEGER");
            }
        }
        String str4 = "properties";
        if (!zza(sQLiteDatabase, str4)) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS properties ( app_uid INTEGER NOT NULL, cid TEXT NOT NULL, tid TEXT NOT NULL, params TEXT NOT NULL, adid INTEGER NOT NULL, hits_count INTEGER NOT NULL, PRIMARY KEY (app_uid, cid, tid)) ;");
            return;
        }
        Set zzb2 = zzb(sQLiteDatabase, str4);
        String[] strArr2 = {"app_uid", "cid", "tid", "params", "adid", "hits_count"};
        for (int i2 = 0; i2 < 6; i2++) {
            String str5 = strArr2[i2];
            if (!zzb2.remove(str5)) {
                String str6 = "Database properties is missing required column: ";
                String valueOf2 = String.valueOf(str5);
                throw new SQLiteException(valueOf2.length() != 0 ? str6.concat(valueOf2) : new String(str6));
            }
        }
        if (!zzb2.isEmpty()) {
            throw new SQLiteException("Database properties table has extra columns");
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        String path = sQLiteDatabase.getPath();
        if (zzbx.version() >= 9) {
            File file = new File(path);
            file.setReadable(false, false);
            file.setWritable(false, false);
            file.setReadable(true, true);
            file.setWritable(true, true);
        }
    }
}
