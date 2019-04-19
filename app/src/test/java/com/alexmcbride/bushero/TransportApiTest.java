package com.alexmcbride.bushero;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TransportApiTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetPlaces() throws IOException {
        MockJsonHandler json = new MockJsonHandler();
        json.setJson(FakeJson.PLACES);
        TransportApiKey key = new TransportApiKey("id", "key");
        MapLocation location = new MapLocation(1.1234, 2.2345);

        TransportApi api = new TransportApi(key, json);
        Places places = api.getPlaces(location);

        assertEquals("https://transportapi.com/v3/uk/places.json?app_id=id&app_key=key&lat=1.123400&lon=2.234500&type=bus_stop", json.getUrl());
        assertNotNull(places);
        assertEquals("Fri Apr 19 10:33:31 BST 2019", places.getRequestTime().toString());
        assertEquals("NaPTAN", places.getSource());
        assertEquals("Contains DfT NaPTAN bus stops data", places.getAcknowledgements());
        assertEquals(10, places.getBusStops().size());
        BusStop busStop = places.getBusStops().get(0);
        assertEquals("bus_stop", busStop.getType());
        assertEquals("Olympic Way - W-bound", busStop.getName());
        assertEquals("Wembley Park, Wembley", busStop.getDescription());
        assertEquals(51.55832, busStop.getLatitude(), TestUtil.DELTA);
        assertEquals(-0.27735, busStop.getLongitude(), TestUtil.DELTA);
        assertEquals(20, busStop.getAccuracy());
        assertEquals("490010593W", busStop.getAtcocode());
        assertEquals(308, busStop.getDistance());
    }

    private class MockJsonHandler extends JsonHandler {
        private String mJson;
        private String mUrl;

        public void setJson(String json) {
            this.mJson = json;
        }

        public String getUrl() {
            return mUrl;
        }

        @Override
        public String get(String url) {
            mUrl = url;
            return mJson;
        }
    }
}