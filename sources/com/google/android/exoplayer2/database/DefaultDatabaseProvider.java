package com.google.android.exoplayer2.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DefaultDatabaseProvider implements DatabaseProvider {
    private final SQLiteOpenHelper sqliteOpenHelper;

    public DefaultDatabaseProvider(SQLiteOpenHelper sQLiteOpenHelper) {
        this.sqliteOpenHelper = sQLiteOpenHelper;
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.sqliteOpenHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.sqliteOpenHelper.getReadableDatabase();
    }
}
