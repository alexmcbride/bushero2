package com.alexmcbride.bushero;

import android.database.Cursor;

import java.util.List;
import java.util.function.Function;

public class BusRepository {
    private final BusDatabase mBusDatabase;
    private final Function<Cursor, BusStop> mBusStopFactory = new java.util.function.Function<Cursor, BusStop>() {
        @Override
        public BusStop apply(Cursor cursor) {
            BusStop busStop = new BusStop();
            busStop.setAtcocode(cursor.getString(0));
            busStop.setName(cursor.getString(1));
            return busStop;
        }
    };

    public BusRepository(BusDatabase busDatabase) {
        mBusDatabase = busDatabase;
    }

    public List<BusStop> getBusStops(final double longitude, final double latitude) {
        return mBusDatabase.query("busStops", "latitude=? AND longitude=?",
                new String[]{String.valueOf(latitude), String.valueOf(longitude)}, mBusStopFactory);
    }

    public List<BusStop> getBusStops() {
        return mBusDatabase.query("busStops", mBusStopFactory);
    }

    public BusStop getBusStop(String atcocode) {
        return mBusDatabase.queryOne("busStop", "atcocode=?", new String[]{atcocode}, mBusStopFactory);
    }
}
