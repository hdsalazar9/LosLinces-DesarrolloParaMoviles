package itesm.mx.saludintegral.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

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

            String dateString = Miscellaneous.getStringFromDate(evento.getFecha());
            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_FECHA, dateString);

            values.put(DataBaseSchema.EventosTable.COLUMN_NAME_TIPO, evento.getTipo());
            newRowId=db.insert(DataBaseSchema.EventosTable.TABLE_NAME, null, values);
            Log.d("Evento added", "Evento added");
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
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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

    public ArrayList<Evento> getAllEventosTypeSalud(){
        ArrayList<Evento> listaEventos = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseSchema.EventosTable.TABLE_NAME + " WHERE "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[3]+ "'";
        try{
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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

    public ArrayList<Evento> getAllEventosTypeEspiritual(){
        ArrayList<Evento> listaEventos = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseSchema.EventosTable.TABLE_NAME + " WHERE "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[0]+ "'";
        try{
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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

    public ArrayList<Evento> getAllEventosTypeSocial(){
        ArrayList<Evento> listaEventos = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseSchema.EventosTable.TABLE_NAME + " WHERE "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[5]+ "' OR "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[7]+ "'";
        try{
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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

    public ArrayList<Evento> getAllEventosTypeCognicion(){
        ArrayList<Evento> listaEventos = new ArrayList<>();
        String query = "SELECT * FROM " + DataBaseSchema.EventosTable.TABLE_NAME + " WHERE "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[1]+ "' OR "+
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + Miscellaneous.tipos[2]+ "'";
        try{
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
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



    public ArrayList<Evento> getAllProductsFromMonthAndType(Integer iMonth, String sType){
        StringBuilder stringBuilder = new StringBuilder();
        if(iMonth < 10){
            stringBuilder.append('0');
            stringBuilder.append(iMonth+1);
        }
        else stringBuilder.append(iMonth+1);

        String sMonth = stringBuilder.toString();

        ArrayList<Evento> listaEventos = new ArrayList<Evento>();
        String query = "SELECT * FROM "+ DataBaseSchema.EventosTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.EventosTable.COLUMN_NAME_FECHA + " LIKE '___" + sMonth + "%' AND " +
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + sType + "'";
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    evento=new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
                    listaEventos.add(evento);
                    //System.out.println("UN EVENTO FUE AGREGADO A LA LISTA");
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("AllProductsFromMonth: ", e.toString());
        }

        return listaEventos;
    }


    public boolean deleteEvento(String nombreDeEventoABorrar, Date fechaEvento){
        boolean result = false;
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+
                DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+" = '"+nombreDeEventoABorrar+"' AND " +
                DataBaseSchema.EventosTable.COLUMN_NAME_FECHA + " = '" + Miscellaneous.getStringFromDate(fechaEvento) +
                "'";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                int id = Integer.parseInt(cursor.getString(0));
                db.delete(DataBaseSchema.EventosTable.TABLE_NAME, DataBaseSchema.EventosTable._ID+" = ?",
                        new String[]{String.valueOf(id)});
                result = true;
            }
            cursor.close();
        }
        catch (SQLiteException e){
            Log.e("EliminarEvento: ", e.toString());
        }
        return result;
    }


    public ArrayList<Evento> getAllEventosFromDateAndType(Date date, String sType) {
        ArrayList<Evento> listaEventosDelDia = new ArrayList<Evento>();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Log.d("QUERY",""+date);

        String query = "SELECT * FROM "+ DataBaseSchema.EventosTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.EventosTable.COLUMN_NAME_FECHA + " = '" + dateFormat.format(date) + "' AND " +
                DataBaseSchema.EventosTable.COLUMN_NAME_TIPO + " = '" + sType + "'";

        Log.d("QUERY", query);

        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    evento = new Evento(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2),dateC, cursor.getString(4));
                    listaEventosDelDia.add(evento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("AllProductsFromMonth: ", e.toString());
        }
        return listaEventosDelDia;
    }
}
