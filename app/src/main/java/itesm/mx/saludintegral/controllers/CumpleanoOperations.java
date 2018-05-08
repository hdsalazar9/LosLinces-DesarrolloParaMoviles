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

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.util.Miscellaneous;


/**
 * Created by josec on 14/04/2018.
 */

public class CumpleanoOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private Cumpleano cumpleano;

    public CumpleanoOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (Cumpleano cumpleano){
        long newRowId=0;

        Log.d("ADDCUMPLE","tel: " + cumpleano.getTelefono());

        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.CumpleanosTable.COLUMN_NAME_NOMBRE, cumpleano.getNombre());
            String fechaCumpleanos = Miscellaneous.getStringFromDate(cumpleano.getFecha());
            values.put(DataBaseSchema.CumpleanosTable.COLUMN_NAME_FECHA, fechaCumpleanos);

            values.put(DataBaseSchema.CumpleanosTable.COLUMN_NAME_TIPO, cumpleano.getTipo());
            values.put(DataBaseSchema.CumpleanosTable.COLUMN_NAME_TELEFONO, cumpleano.getTelefono());
            newRowId=db.insert(DataBaseSchema.CumpleanosTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<Cumpleano> findEvent(String productName){
        ArrayList<Cumpleano> listaCumpleanos=new ArrayList<Cumpleano>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            cumpleano=null;
            if (cursor.moveToFirst()){
                do{

                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(2));

                    cumpleano=new Cumpleano(cursor.getInt(0),cursor.getString(1),
                            dateC,cursor.getString(3),cursor.getString(4));
                    listaCumpleanos.add(cumpleano);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaCumpleanos;
    }

    public ArrayList<Cumpleano> getAllProducts(){
        ArrayList<Cumpleano> listaCumpleanos=new ArrayList<Cumpleano>();
        String query="SELECT * FROM "+DataBaseSchema.CumpleanosTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{

                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(2));

                    cumpleano=new Cumpleano(cursor.getInt(0),cursor.getString(1),
                            dateC,cursor.getString(3),cursor.getString(4));
                    listaCumpleanos.add(cumpleano);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaCumpleanos;
    }


    public ArrayList<Cumpleano> getAllProductsFromMonthAndType(Integer iMonth, String sType){
        StringBuilder stringBuilder = new StringBuilder();
        if(iMonth < 10){
            stringBuilder.append('0');
            stringBuilder.append(iMonth+1);
        }
        else stringBuilder.append(iMonth+1);

        String sMonth = stringBuilder.toString();

        ArrayList<Cumpleano> listaCumpleanos = new ArrayList<Cumpleano>();
        String query = "SELECT * FROM "+ DataBaseSchema.CumpleanosTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.CumpleanosTable.COLUMN_NAME_FECHA + " LIKE '___" + sMonth + "%' AND " +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_TIPO + " = '" + sType + "'";
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{

                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(2));
                    cumpleano=new Cumpleano(cursor.getInt(0),cursor.getString(1),
                            dateC, cursor.getString(3), cursor.getString(4));

                    listaCumpleanos.add(cumpleano);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {

            Log.e("AllProductsFromMonth: ", e.toString());
        }

        Log.d("DB","Cantidad de resultados en el query de getAllProductsFromMonthAndType: "+ listaCumpleanos.size());

        return listaCumpleanos;
    }



    public boolean deleteCumpleano(String personaName){
        boolean result = false;
        String query="SELECT * FROM "+DataBaseSchema.CumpleanosTable.TABLE_NAME+ " WHERE "+
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_NOMBRE+" = '"+personaName+"'";
        try{
            Cursor cursor = db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do {
                    int id = Integer.parseInt(cursor.getString(0));
                    db.delete(DataBaseSchema.CumpleanosTable.TABLE_NAME, DataBaseSchema.CumpleanosTable._ID + " = ?",
                            new String[]{String.valueOf(id)});
                    result = true;
                } while(cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLiteException e){
            Log.e("Cumpleanos delete: ",e.toString());
        }
        return result;
    }


    public ArrayList<Cumpleano> getAllCumpleanosFromDateAndType(Date date, String sType) {
        ArrayList<Cumpleano> listaCumpleanosDelDia = new ArrayList<Cumpleano>();
        Log.d("QUERY",""+date);
        String strFecha = Miscellaneous.getStringFromDate(date);

        String query = "SELECT * FROM "+ DataBaseSchema.CumpleanosTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.CumpleanosTable.COLUMN_NAME_FECHA + " = '" + strFecha + "' AND " +
                DataBaseSchema.CumpleanosTable.COLUMN_NAME_TIPO + " = '" + sType + "'";

        Log.d("QUERY", query);

        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(2));
                    cumpleano=new Cumpleano(cursor.getInt(0),cursor.getString(1),
                            dateC, cursor.getString(3), cursor.getString(4));
                    listaCumpleanosDelDia.add(cumpleano);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("AllProductsFromMonth: ", e.toString());
        }
        return listaCumpleanosDelDia;
    }

}
