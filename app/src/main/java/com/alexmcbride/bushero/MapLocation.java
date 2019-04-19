package com.alexmcbride.bushero;

public class MapLocation {
    private double mLatitude;
    private double mLongitude;

    public MapLocation(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
