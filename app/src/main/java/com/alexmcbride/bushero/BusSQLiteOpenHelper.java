package com.alexmcbride.bushero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.annotation.Nullable;

public class BusSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;

    public BusSQLiteOpenHelper(@Nullable Context context, @Nullable String name) {
        super(context, name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS busStops (" +
                "atocode TEXT PRIMARY KEY," +
                "name TEXT," +
                " UNIQUE (atocode) ON CONFLICT ABORT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS busStops;");
        onCreate(db);
    }
}
