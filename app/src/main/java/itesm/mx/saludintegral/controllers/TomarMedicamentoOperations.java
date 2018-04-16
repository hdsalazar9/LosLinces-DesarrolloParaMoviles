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

import itesm.mx.saludintegral.models.TomarMedicamento;

/**
 * Created by josec on 14/04/2018.
 */

public class TomarMedicamentoOperations {
    private SQLiteDatabase db;
    private TomarMedicamentoDBHelper dbHelper;
    private TomarMedicamento tomarMedicamento;

    public TomarMedicamentoOperations(Context context){dbHelper=new TomarMedicamentoDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (TomarMedicamento tomarMedicamento){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_ID_MEDICAMENTO, tomarMedicamento.getIdMedicamento());
            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_TOMADOATIEMPO, String.valueOf(tomarMedicamento.getTomadoATiempo()));
            values.put(DataBaseSchema.TomarMedicamentoTable.COLUMN_NAME_FECHAHORA, tomarMedicamento.getFechaHora().toString());
            newRowId=db.insert(DataBaseSchema.TomarMedicamentoTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<TomarMedicamento> findEvent(String productName){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.EventoTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventoTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            tomarMedicamento=null;
            if (cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    boolean b = cursor.getString(2).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");
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
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaTomatMedicamento;
    }

    public ArrayList<TomarMedicamento> getAllProducts(){
        ArrayList<TomarMedicamento> listaTomatMedicamento=new ArrayList<TomarMedicamento>();
        String query="SELECT * FROM "+DataBaseSchema.EventoTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    boolean b = cursor.getString(2).equals("true");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm");
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
