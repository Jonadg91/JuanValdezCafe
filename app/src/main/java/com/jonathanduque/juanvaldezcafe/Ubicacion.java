package com.jonathanduque.juanvaldezcafe;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Ubicacion extends ActionBarActivity {

    public DataBaseManager Manager = MainActivity.getManager();
    private Cursor cursor= Manager.cargarCursorContactos();//para recorrer la base de datos

    private GoogleMap map;
    private CameraUpdate cameraUpdate;
    private final LatLng LOCATION_GUARNE = new LatLng(6.279784, -75.442856);
    private final LatLng LOCATION_MEDELLIN = new LatLng(6.246268, -75.566943);


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


    }
    //Funcion para adicionar sede y marcador
    public void addMarkertoPlace(String Nombre,String Latitud,String Longitud ){
        LatLng LOCATION = new LatLng(Float.parseFloat(Latitud),Float.parseFloat(Longitud));//Ubicacion temporal
        map.addMarker(new MarkerOptions().position(LOCATION).title(Nombre).snippet("Sede Cafe Juan Valdez")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
    //Funcion para poner por defecto el mapa de medellin
    public void setupMap (){
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        cameraUpdate= CameraUpdateFactory.newLatLngZoom(LOCATION_MEDELLIN, 13);//12 es el zonn, mientras mas pequeño, mas alejado.
        map.animateCamera(cameraUpdate);
    }

}
