package com.jonathanduque.juanvaldezcafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JonathanDG on 08/06/2015.
 */

public class DataBaseManager {
    public static final String TABLE_NAME = "sedes";
    public static final String CN_ID = "_id";  // Nombre columna
    public static final String CN_NAME = "nombre";
    public static final String CN_LATITUD = "latitud";
    public static final String CN_LONGITUD = "longitd";
    // create table contactos(
    //                          _id integer primary key autoincrement,
    //                          nombre text not null,
    //                          telefono text);
    public static final String CREATE_TABLE = "create table "+ TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_LATITUD + " text,"
            + CN_LONGITUD + " text);";

    DbHelper helper;
    SQLiteDatabase db;
    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String Nombre, String Latitud, String Longitud){
        ContentValues valores = new ContentValues();
        valores.put(CN_NAME,Nombre);
        valores.put(CN_LATITUD,Latitud);
        valores.put(CN_LONGITUD,Longitud);
        return valores;
    }

    public void insertar(String Nombre, String Latitud, String Longitud){
        db.insert(TABLE_NAME,null,generarContentValues(Nombre,Latitud,Longitud));
    }

    public Cursor cargarCursorContactos(){
        //db = helper.getReadableDatabase();
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);//sintaxis query
        // ( tableName, tableColumns, whereClause, whereArgs, groupBy, having, orde
    }

    public void eliminar(String nombre){
        db.delete(TABLE_NAME,CN_NAME + "=?", new String[]{nombre});
    }

    public void ModificarSede(String nombreviejo, String nombrenuevo, String nuevaLatitud, String nuevaLongitd){
        db.update(TABLE_NAME,generarContentValues(nombrenuevo,nuevaLatitud,nuevaLongitd),CN_NAME+"=?",new String[]{nombreviejo});
    }

    public Cursor buscarContacto(String Nombre) {
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_LATITUD,CN_LONGITUD};
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db.query(TABLE_NAME,columnas,CN_NAME + "=?",new String[]{Nombre},null,null,null);
    }
}