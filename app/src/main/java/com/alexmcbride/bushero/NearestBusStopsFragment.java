package com.alexmcbride.bushero;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class NearestBusStopsFragment extends Fragment {
    private static final int FINE_LOCATION_REQUEST_CODE = 1;
    private static final String TAG = "NearestBusStopsFragment";
    private OnFragmentInteractionListener mListener;
    private FusedLocationProviderClient mLocationProviderClient;
    private TransportApi mTransportApi;
    private NearestBusStopsAsyncTask mNearestBusStopsAsyncTask;
    private RecyclerView mRecyclerView;

    public NearestBusStopsFragment() {
        // Required empty public constructor
    }

    public static NearestBusStopsFragment newInstance() {
        NearestBusStopsFragment fragment = new NearestBusStopsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));

        initializeTransportApi();

        mNearestBusStopsAsyncTask = new NearestBusStopsAsyncTask(this);

        updateLocation();
    }

    private void initializeTransportApi() {
        String appId = getResources().getString(R.string.appId);
        String apiKey = getResources().getString(R.string.apiKey);
        TransportApiKey transportApiKey = new TransportApiKey(appId, apiKey);
        JsonHandler jsonHandler = new JsonHandler();
        mTransportApi = new TransportApi(transportApiKey, jsonHandler);
    }

    private void updateLocation() {
        Activity activity = Objects.requireNonNull(getActivity());
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    mNearestBusStopsAsyncTask.execute(location);
                }
            });
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("You must allow location permission for the app to work properly")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            } else {
                Toast.makeText(getActivity(), "No permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearest_bus_stops, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private static class NearestBusStopsAsyncTask extends AsyncTask<Location, Void, Places> {
        private final WeakReference<NearestBusStopsFragment> mFragment;
        private Exception mException;

        NearestBusStopsAsyncTask(NearestBusStopsFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        protected Places doInBackground(Location... locations) {
            Location location = locations[0];

            try {
                return mFragment.get().mTransportApi.getPlaces(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                mException = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Places places) {
            NearestBusStopsFragment fragment = mFragment.get();
            if (mException != null) {
                Log.d(TAG, mException.toString());
                Toast.makeText(fragment.getActivity(), "Error: " + mException.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                fragment.updateNearestBusStops(places.getBusStops());
            }
            super.onPostExecute(places);
        }
    }

    private void updateNearestBusStops(List<BusStop> busStops) {
        mRecyclerView.setAdapter(new NearestBusStopsAdapter(busStops));
    }

    private class NearestBusStopsAdapter extends RecyclerView.Adapter<NearestBusStopViewHolder> {
        private final List<BusStop> mBusStops;

        public NearestBusStopsAdapter(List<BusStop> busStops) {
            mBusStops = busStops;
        }

        @NonNull
        @Override
        public NearestBusStopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_nearest_bus_stop, viewGroup, false);
            return new NearestBusStopViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mBusStops.size();
        }

        @Override
        public void onBindViewHolder(@NonNull NearestBusStopViewHolder viewHolder, int position) {
            BusStop busStop = mBusStops.get(position);
            viewHolder.textName.setText(busStop.getName());
        }
    }

    private class NearestBusStopViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;

        public NearestBusStopViewHolder(@NonNull View view) {
            super(view);
            textName = view.findViewById(R.id.textName);
        }
    }
}
