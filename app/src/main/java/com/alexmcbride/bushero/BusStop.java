package com.alexmcbride.bushero;

import com.squareup.moshi.Json;

public class BusStop {
    @Json(name = "atcocode") private String mAtcocode;
    @Json(name = "type") private String mType;
    @Json(name = "name") private String mName;
    @Json(name = "description") private String mDescription;
    @Json(name = "latitude") private double mLatitude;
    @Json(name = "longitude") private double mLongitude;
    @Json(name = "accuracy") private int mAccuracy;
    @Json(name = "distance") private int mDistance;

    public String getAtcocode() {
        return mAtcocode;
    }

    public void setAtcocode(String atcocode) {
        mAtcocode = atcocode;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public int getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(int accuracy) {
        mAccuracy = accuracy;
    }

    public int getDistance() {
        return mDistance;
    }

    public void setDistance(int distance) {
        mDistance = distance;
    }
}
