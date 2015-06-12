package com.jonathanduque.juanvaldezcafe;

import android.database.Cursor;
import android.location.*;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Ubicacion extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    public DataBaseManager Manager = MainActivity.getManager();
    private Cursor cursor= Manager.cargarCursorContactos();//para recorrer la base de datos

    private GoogleMap map;
    private CameraUpdate cameraUpdate;
    private final LatLng LOCATION_GUARNE = new LatLng(6.279784, -75.442856);
    private final LatLng LOCATION_MEDELLIN = new LatLng(6.246268, -75.566943);

    //para poder ubicar un cursor con mi ubicacion es necesario las siguientes variables
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LatLng currentLocation;
    private boolean newLocationReady=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        map= ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String dbnombre = cursor.getString(cursor.getColumnIndex(Manager.CN_NAME));
                    String dblatitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LATITUD));
                    String dblongitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LONGITUD));
                    addMarkertoPlace(dbnombre,dblatitud,dblongitud);
                } while(cursor.moveToNext());
            }
        else
                Toast.makeText(this,"No Hay Sedes Disponibles",Toast.LENGTH_SHORT).show();

        setupMap();


        buildGoogleApiClient();
        createLocationRequest();
    }
    //Funcion para adicionar sede y marcador
    public void addMarkertoPlace(String Nombre,String Latitud,String Longitud ){
        LatLng LOCATION = new LatLng(Float.parseFloat(Latitud),Float.parseFloat(Longitud));//Ubicacion temporal
        map.addMarker(new MarkerOptions().position(LOCATION).title(Nombre).snippet("Sede Cafe Juan Valdez")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
                        //defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
    //Funcion para poner por defecto el mapa de medellin y mi posicion si esta disponible
    public void setupMap (){
        if (newLocationReady){
            cameraUpdate= CameraUpdateFactory.newLatLngZoom(currentLocation,16);
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        cameraUpdate= CameraUpdateFactory.newLatLngZoom(LOCATION_MEDELLIN, 13);//12 es el zonn, mientras mas pequeño, mas alejado.
        map.animateCamera(cameraUpdate);


    }

    //Funciones usadas para poner mi ubicacion en el mapa
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
    }
    protected void createLocationRequest(){
        mLocationRequest= new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void setNewLocation (Location location){
        double latitude= location.getLatitude();
        double longitude= location.getLongitude();
        currentLocation = new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(currentLocation).title("Mi Ubicación")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.persona)));
                        //defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation !=null){
            setNewLocation(mLastLocation);
            newLocationReady= true;
        }
        // else
        //   LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,
        //         (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        setNewLocation(location);
        newLocationReady =true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }
}
