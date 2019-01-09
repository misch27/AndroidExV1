package com.example.misch.androidexv1.googleMapAPI;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapCall implements OnMapReadyCallback {

    private static final int overview = 0;

    private static final int TIMER = 20;

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) {
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        setupGoogleMapScreenSettings(googleMap);

        DirectionsResult results
                = getDirectionsDetails("55.680962, 37.717012","55.794141, 37.701635",TravelMode.DRIVING);
        System.out.println("Построен маршрут ");
        if (results != null) {
            System.out.println("Точно Построен маршрут ");
            addPolyline(results, googleMap);
            positionCamera(results.routes[overview], googleMap);
            addMarkersToMap(results, googleMap);

        }

    }



    private void setupGoogleMapScreenSettings(GoogleMap mMap) {

        mMap.setBuildingsEnabled(true);

        mMap.setIndoorEnabled(true);

        mMap.setTrafficEnabled(true);

        UiSettings mUiSettings = mMap.getUiSettings();

        mUiSettings.setZoomControlsEnabled(true);

        mUiSettings.setCompassEnabled(true);

        mUiSettings.setMyLocationButtonEnabled(true);

        mUiSettings.setScrollGesturesEnabled(true);

        mUiSettings.setZoomGesturesEnabled(true);

        mUiSettings.setTiltGesturesEnabled(true);

        mUiSettings.setRotateGesturesEnabled(true);

    }



    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(results.routes[overview]
                        .legs[overview].startLocation.lat,
                        results.routes[overview].legs[overview].startLocation.lng))
                .title(results.routes[overview].legs[overview].startAddress));

        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview]
                .legs[overview].endLocation.lat,
                results.routes[overview].legs[overview].endLocation.lng))
                .title(results.routes[overview].legs[overview].startAddress)
                .snippet(getEndLocationTitle(results)));

    }



    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat,
                        route.legs[overview].startLocation.lng), 12));

    }



    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview]
                .overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

    }



    private String getEndLocationTitle(DirectionsResult results){
        return  "Time :"+ results.routes[overview].legs[overview]
                .duration.humanReadable + " Distance :" + results
                .routes[overview].legs[overview]
                .distance.humanReadable;

    }



    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey("AIzaSyDeYoAQ5vMzjiqt1UcC1kOFmHNeSudSczQ")
                .setConnectTimeout(TIMER, TimeUnit.SECONDS)
                .setReadTimeout(TIMER, TimeUnit.SECONDS)
                .setWriteTimeout(TIMER, TimeUnit.SECONDS);

    }

}
