package itesm.mx.saludintegral.controllers;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.sql.Time;


import android.database.sqlite.SQLiteException;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.Medicamento;

import itesm.mx.saludintegral.util.Miscellaneous;


/**
 * Created by josec on 14/04/2018.
 */

public class MedicamentoOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private Medicamento medicamento;

    public MedicamentoOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (Medicamento medicamento){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_NOMBRE, medicamento.getNombre());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_GRAMAJE, medicamento.getGramaje());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_CANTIDAD, medicamento.getCantidad());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_PERIOCIDAD, medicamento.getPerodicidad());


            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_HORA, medicamento.getHora().toString());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_CADACUANTO, medicamento.getCadaCuanto());

            String strAux = Miscellaneous.getStringFromDate(medicamento.getFechaComienzo());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_COMIENZO, strAux);
            strAux = Miscellaneous.getStringFromDate(medicamento.getFechaTermino());
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_TERMINO, strAux);


            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_INGERIRANTESDESPUESCOMIDA, String.valueOf(medicamento.getAntesDespuesDeComer()));
            values.put(DataBaseSchema.MedicamentoTable.COLUMN_NAME_FOTO, medicamento.getFoto());
            newRowId=db.insert(DataBaseSchema.MedicamentoTable.TABLE_NAME, null, values);
            Log.d("Medicamento added", "Medicamento added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<Medicamento> findEvent(String medicamentoName){
        ArrayList<Medicamento> listaMedicamentos=new ArrayList<Medicamento>();
        String query="SELECT * FROM "+DataBaseSchema.MedicamentoTable.TABLE_NAME+" WHERE "+DataBaseSchema.MedicamentoTable.COLUMN_NAME_NOMBRE+
                " = \""+ medicamentoName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            medicamento=null;
            if (cursor.moveToFirst()){
                do{
                    java.sql.Time ppstime=null;

                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(7));
                    Date dateT=Miscellaneous.getDateFromString(cursor.getString(8));
                    boolean b = cursor.getString(9).equals("true");

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
                    Date d1 = null;
                    try {
                        d1 = (Date)format.parse(cursor.getString(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ppstime= new java.sql.Time(d1.getTime());


                    medicamento=new Medicamento(cursor.getInt(0),cursor.getString(1),
                            cursor.getDouble(2),cursor.getInt(3),cursor.getString(4),ppstime, cursor.getInt(6),
                            dateC, dateT,b,
                            cursor.getBlob(10));
                    listaMedicamentos.add(medicamento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaMedicamentos;
    }

    public ArrayList<Medicamento> getAllProducts(){
        ArrayList<Medicamento> listaMedicamentos=new ArrayList<Medicamento>();
        String query="SELECT * FROM "+DataBaseSchema.MedicamentoTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{

                    Time ppstime;
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(7));
                    Date dateT=Miscellaneous.getDateFromString(cursor.getString(8));
                    boolean b = cursor.getString(9).equals("true");
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
                    Date d1 = null;
                    try {
                        d1 = (Date)format.parse(cursor.getString(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ppstime= new java.sql.Time(d1.getTime());

                    medicamento=new Medicamento(cursor.getInt(0),cursor.getString(1),
                            cursor.getDouble(2),cursor.getInt(3),cursor.getString(4),ppstime, cursor.getInt(6),
                            dateC, dateT,b,
                            cursor.getBlob(10));
                    listaMedicamentos.add(medicamento);

                    Log.d("DATABASE",""+dateC);

                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaMedicamentos;
    }


    public boolean deleteMedicamento(String medicamentoName){
        boolean result=false;
        String query="SELECT * FROM "+DataBaseSchema.MedicamentoTable.TABLE_NAME+ " WHERE "+DataBaseSchema.MedicamentoTable.COLUMN_NAME_NOMBRE+
                " =  '"+medicamentoName+"'";
              //  " =  \""+medicamentoName+"\"";
        try{
            Cursor cursor=db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                int id=Integer.parseInt(cursor.getString(0));
                db.delete(DataBaseSchema.MedicamentoTable.TABLE_NAME, DataBaseSchema.MedicamentoTable._ID +" = ?",
                        new String[]{String.valueOf(id)});
                result=true;
            }
            cursor.close();
        }
        catch (SQLiteException e){
            Log.e("SQLDELETE", e.toString());
        }
        return result;
    }


}
