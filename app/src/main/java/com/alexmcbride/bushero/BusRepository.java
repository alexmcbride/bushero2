package com.alexmcbride.bushero;

import android.database.Cursor;

import java.util.List;
import java.util.function.Function;

public class BusRepository {
    private final BusDatabase mBusDatabase;

    public BusRepository(BusDatabase busDatabase) {
        mBusDatabase = busDatabase;
    }

    private Function<Cursor, BusStop> getBusStopFactory() {
        return new java.util.function.Function<Cursor, BusStop>() {
            @Override
            public BusStop apply(Cursor cursor) {
                BusStop busStop = new BusStop();
                busStop.setAtcocode(cursor.getString(0));
                busStop.setName(cursor.getString(1));
                return busStop;
            }
        };
    }

    public List<BusStop> getBusStops(double longitude, double latitude) {
        return mBusDatabase.query("busStops", getBusStopFactory());
    }

    public List<BusStop> getBusStops() {
        return mBusDatabase.query("busStops", getBusStopFactory());
    }

    public BusStop getBusStop(String atcocode) {
        return mBusDatabase.queryOne("busStop", "atcocode=?", new String[]{atcocode}, getBusStopFactory());
    }
}
