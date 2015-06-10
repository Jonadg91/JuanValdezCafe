package com.jonathanduque.juanvaldezcafe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Ubicacion extends ActionBarActivity {

    private GoogleMap map;
    private CameraUpdate cameraUpdate;
    private final LatLng LOCATION_GUARNE = new LatLng(6.279784, -75.442856);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        map= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        map.addMarker(new MarkerOptions().position(LOCATION_GUARNE).title("Guarne").snippet("Parque Principal")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        cameraUpdate= CameraUpdateFactory.newLatLngZoom(LOCATION_GUARNE, 16);//12 es el zonn, mientras mas pequeño, mas alejado.
        map.animateCamera(cameraUpdate);
    }

}
