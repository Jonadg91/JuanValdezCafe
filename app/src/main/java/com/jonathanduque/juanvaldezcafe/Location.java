package com.jonathanduque.juanvaldezcafe;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Location extends Fragment {
    private MapView mMapView;
    private Bundle mBundle;
    private GoogleMap Map;
    private CameraUpdate cameraUpdate;
    private final LatLng LOCATION_GUARNE = new LatLng(6.279784, -75.442856);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_location, container, false);

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {//GooglePlayServicesNotAvailable
            // TODO handle this situation
        }

        mMapView = (MapView) inflatedView.findViewById(R.id.map);
        mMapView.onCreate(mBundle);
        setUpMapIfNeeded(inflatedView);

        return inflatedView;
    }



    private void setUpMapIfNeeded(View inflatedView) {
        if (Map == null) {
            // Intenta obtener el mapa del MapView.
            Map = ((MapView) inflatedView.findViewById(R.id.map)).getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (Map != null) {
                Map.addMarker(new MarkerOptions().position(LOCATION_GUARNE).title("Guarne").snippet("Parque Principal")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                cameraUpdate= CameraUpdateFactory.newLatLngZoom(LOCATION_GUARNE, 15);//12 es el zonn, mientras mas pequeño, mas alejado.
                Map.animateCamera(cameraUpdate);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }



}