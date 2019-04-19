package com.alexmcbride.bushero;

public class TransportApiKey {
    private String mAppId;
    private String mApiKey;

    public TransportApiKey(String appId, String apiKey) {
        mAppId = appId;
        mApiKey = apiKey;
    }

    public String getAppId() {
        return mAppId;
    }

    public String getApiKey() {
        return mApiKey;
    }
}
