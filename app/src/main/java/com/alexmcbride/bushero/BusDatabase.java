package com.alexmcbride.bushero;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;

public class BusDatabase {
    private SQLiteOpenHelper mOpenHelper;

    public BusDatabase(SQLiteOpenHelper openHelper) {
        mOpenHelper = openHelper;
    }

    public <T> List<T> query(String table, Function<Cursor, T> factory) {
        return query(table, null, null, factory);
    }

    public <T> List<T> query(String table, String selection, String[] selectionArgs, Function<Cursor, T> factory) {
        List<T> items = Lists.newArrayList();
        try (SQLiteDatabase db = mOpenHelper.getReadableDatabase();
             Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                do {
					T item = factory.apply(cursor);
                    items.add(item);
                } while (cursor.moveToNext());
            }
        }
        return items;
    }

    public <T> T queryOne(String table, String selection, String[] selectionArgs, Function<Cursor, T> factory) {
        try (SQLiteDatabase db = mOpenHelper.getReadableDatabase();
             Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                return factory.apply(cursor);
            }
        }
        return null;
    }
}
