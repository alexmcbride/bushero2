package com.alexmcbride.bushero;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAddBusStop = findViewById(R.id.fabAddBusStop);
        fabAddBusStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddBusStopActivity.newInstance(getApplicationContext()));
            }
        });

        BusSQLiteOpenHelper openHelper = new BusSQLiteOpenHelper(this, "bus-hero-db");
        BusDatabase busDatabase = new BusDatabase(openHelper);
        BusRepository busRepository = new BusRepository(busDatabase);

        List<BusStop> busStopList = busRepository.getBusStops();
        updateList(busStopList);
    }

    private void updateList(List<BusStop> busStopList) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
