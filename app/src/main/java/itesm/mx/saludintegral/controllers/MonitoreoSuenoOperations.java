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
import itesm.mx.saludintegral.models.MonitoreoSueno;

/**
 * Created by josec on 14/04/2018.
 */

public class MonitoreoSuenoOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private MonitoreoSueno monitoreoSueno;

    public MonitoreoSuenoOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (MonitoreoSueno monitoreoSueno){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.MonitoreoSuenoTable.COLUMN_NAME_FECHA, monitoreoSueno.getFecha().toString());
            values.put(DataBaseSchema.MonitoreoSuenoTable.COLUMN_NAME_HORAS, monitoreoSueno.getHoras().toString());
            newRowId=db.insert(DataBaseSchema.MonitoreoSuenoTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<MonitoreoSueno> findEvent(String productName){
        ArrayList<MonitoreoSueno> listaMonitoreoSuenos=new ArrayList<MonitoreoSueno>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            monitoreoSueno=null;
            if (cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    //SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
                    try {
                        dateC= dateFormat.parse(cursor.getString(1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    monitoreoSueno=new MonitoreoSueno(cursor.getInt(0),dateC,cursor.getDouble(2));
                    listaMonitoreoSuenos.add(monitoreoSueno);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaMonitoreoSuenos;
    }

    public ArrayList<MonitoreoSueno> getAllProducts(){
        ArrayList<MonitoreoSueno> listaMonitoreoSuenos=new ArrayList<MonitoreoSueno>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
                    try {
                        dateC= dateFormat.parse(cursor.getString(1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    monitoreoSueno=new MonitoreoSueno(cursor.getInt(0),dateC,cursor.getDouble(2));
                    listaMonitoreoSuenos.add(monitoreoSueno);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaMonitoreoSuenos;
    }
}
