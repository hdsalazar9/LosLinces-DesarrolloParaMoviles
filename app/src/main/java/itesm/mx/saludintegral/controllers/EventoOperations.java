package itesm.mx.saludintegral.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.Evento;

/**
 * Created by josec on 14/04/2018.
 */

public class EventoOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private Evento evento;

    public EventoOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (Evento evento){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE, evento.getName());
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_DESCRICPION, evento.getDescripcion());
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_FECHA, evento.getFecha().toString());
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_PERIODICIDAD, String.valueOf(evento.getPeriodicidad()));
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_TIPO, evento.getTipo());
            newRowId=db.insert(DataBaseSchema.EventosTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<Evento> findEvent(String productName){
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            evento=null;
            if (cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    boolean b = cursor.getString(4).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC,b, cursor.getString(5));
                    listaEventos.add(evento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaEventos;
    }

    public ArrayList<Evento> getAllProducts(){
        ArrayList<Evento> listaEventos=new ArrayList<Evento>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    boolean b = cursor.getString(4).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC,b, cursor.getString(5));
                    listaEventos.add(evento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaEventos;
    }
}
