package itesm.mx.saludintegral.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.TomarMedicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by josec on 14/04/2018.
 */

public class TomarMedicamentoOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private TomarMedicamento tomarMedicamento;

    public TomarMedicamentoOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addTomarMedicamento (TomarMedicamento tomarMedicamento){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_ID_MEDICAMENTO, tomarMedicamento.getIdMedicamento());
            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_TOMADOATIEMPO, String.valueOf(tomarMedicamento.getTomadoATiempo()));

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String fechaTomado = df.format(tomarMedicamento.getFechaHora());

            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_FECHAHORA, fechaTomado);
            newRowId=db.insert(DataBaseSchema.TomarMedicamentoTable.TABLE_NAME, null, values);
            Log.d("TomarMedicamento added", "TomarMedicamento added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<TomarMedicamento> findTomarMedicamento(String productName){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            tomarMedicamento=null;
            if (cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    boolean b = cursor.getString(2).equals("true");
                   /* SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    tomarMedicamento=new TomarMedicamento(cursor.getInt(0),cursor.getInt(1),
                            b,dateC);
                    listaTomatMedicamento.add(tomarMedicamento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaTomatMedicamento;
    }

    public ArrayList<TomarMedicamento> getAllTomarMedicamento(){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=Miscellaneous.getDateFromString(cursor.getString(3));
                    boolean b = cursor.getString(2).equals("true");
                    /*SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    tomarMedicamento=new TomarMedicamento(cursor.getInt(0),cursor.getInt(1),
                            b,dateC);
                    listaTomatMedicamento.add(tomarMedicamento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaTomatMedicamento;
    }

    public ArrayList<TomarMedicamento> getAllTomarMedicamentoFrom(String medicamentoID){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.TomarMedicamentoTable.TABLE_NAME +" WHERE "+DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_ID_MEDICAMENTO+
                " =  \""+medicamentoID+"\"";
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;//Miscellaneous.getDateFromString(cursor.getString(3));
                    boolean b = cursor.getString(2).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tomarMedicamento=new TomarMedicamento(cursor.getInt(0),cursor.getInt(1),
                            b,dateC);
                    listaTomatMedicamento.add(tomarMedicamento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaTomatMedicamento;
    }

    public ArrayList<TomarMedicamento> getAllTomarMedicamentoFrom(String medicamentoID){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.TomarMedicamentoTable.TABLE_NAME +" WHERE "+DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_ID_MEDICAMENTO+
                " =  \""+medicamentoID+"\"";
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    boolean b = cursor.getString(2).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    try {
                        dateC= dateFormat.parse(cursor.getString(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    tomarMedicamento=new TomarMedicamento(cursor.getInt(0),cursor.getInt(1),
                            b,dateC);
                    listaTomatMedicamento.add(tomarMedicamento);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaTomatMedicamento;
    }

}
