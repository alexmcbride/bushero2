package com.alexmcbride.bushero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class AddBusStopActivity extends AppCompatActivity implements NearestBusStopsFragment.OnFragmentInteractionListener, MapBusStopsFragment.OnFragmentInteractionListener {
    private NearestBusStopsFragment mNearestBusStopsFragment;
    private FragmentManager mFragmentManager;
    private MapBusStopsFragment mMapBusStopsFragment;

    public static Intent newInstance(Context context) {
        return new Intent(context, AddBusStopActivity.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_nearest:
                    updateFragment(mNearestBusStopsFragment);
                    return true;
                case R.id.navigation_map:
                    updateFragment(mMapBusStopsFragment);
                    return true;
                case R.id.navigation_search:
//                    updateFragment(mSearchBusStopsFragment);
                    return true;
            }
            return false;
        }
    };

    private void updateFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().replace(R.id.container, fragment).commitNow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_stop);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentManager = getSupportFragmentManager();
        mNearestBusStopsFragment = NearestBusStopsFragment.newInstance();
        mMapBusStopsFragment = MapBusStopsFragment.newInstance();
        updateFragment(mNearestBusStopsFragment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_add_bus_stop);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
