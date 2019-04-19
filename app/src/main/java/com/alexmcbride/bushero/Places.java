package com.alexmcbride.bushero;

import com.squareup.moshi.Json;

import java.util.Date;
import java.util.List;

public class Places {
    //"request_time": "2019-04-19T10:33:31+01:00",
    //"source": "NaPTAN",
    //"acknowledgements": "Contains DfT NaPTAN bus stops data",

    @Json(name = "request_time") private Date mRequestTime;
    @Json(name = "source")private String mSource;
    @Json(name = "acknowledgements")private String mAcknowledgements;
    @Json(name="member")private List<BusStop> mBusStops;

    public Date getRequestTime() {
        return mRequestTime;
    }

    public void setRequestTime(Date requestTime) {
        mRequestTime = requestTime;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getAcknowledgements() {
        return mAcknowledgements;
    }

    public void setAcknowledgements(String acknowledgements) {
        mAcknowledgements = acknowledgements;
    }

    public List<BusStop> getBusStops() {
        return mBusStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        mBusStops = busStops;
    }
}
