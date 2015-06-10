package com.jonathanduque.juanvaldezcafe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{

    private static DataBaseManager Manager;//variable para acceder a la base de datos desde las otras actividades
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Manager = new DataBaseManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static DataBaseManager getManager() {
        return Manager;
    }

    public void onClickLocation(View view){
        Intent t = new Intent(this,Ubicacion.class);
        startActivity(t);
    }
    public void onClickUpdate(View view){
        Intent t = new Intent(this,ActualzarSedes.class);
        startActivity(t);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.main) {
            return true;
        }*/
        if (id==R.id.location){
            Toast.makeText(this, "Presiono Location", Toast.LENGTH_SHORT).show();
            Intent t = new Intent(this,Ubicacion.class);
            startActivity(t);
           /*Location location=new Location();
            fragmentTransaction.replace(android.R.id.content, location).commit();*/
        }
        if (id==R.id.actualizardatos){
            Toast.makeText(this, "Presiono actualizar", Toast.LENGTH_SHORT).show();
            Intent t = new Intent(this,ActualzarSedes.class);
            startActivity(t);
            /*            UpdatePlaces updatePlaces=new UpdatePlaces();
            fragmentTransaction.replace(android.R.id.content,updatePlaces).commit();*/
        }

        return super.onOptionsItemSelected(item);
    }
}
