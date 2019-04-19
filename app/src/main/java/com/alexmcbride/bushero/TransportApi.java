package com.alexmcbride.bushero;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class TransportApi {
    private static final String BASE_URL = "https://transportapi.com/v3/uk";
    private final JsonHandler mJson;
    private final TransportApiKey mKey;
    private final JsonAdapter<Places> mPlacesAdapter;

    public TransportApi(TransportApiKey key, JsonHandler json) {
        mKey = key;
        mJson = json;
        Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
        mPlacesAdapter = moshi.adapter(Places.class);
    }

    private String getPlacesUrl(double latitude, double longitude) {
        return String.format(Locale.ENGLISH,
                "%s/places.json?app_id=%s&app_key=%s&lat=%f&lon=%f&type=bus_stop",
                BASE_URL, mKey.getAppId(), mKey.getApiKey(), latitude, longitude);
    }

    public Places getPlaces(double latitude, double longitude) throws IOException {
        String url = getPlacesUrl(latitude, longitude);
        String json = mJson.get(url);
        return mPlacesAdapter.fromJson(json);
    }
}
