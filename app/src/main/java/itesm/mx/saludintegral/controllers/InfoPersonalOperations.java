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
import itesm.mx.saludintegral.models.InfoPersonal;

/**
 * Created by josec on 14/04/2018.
 */

public class InfoPersonalOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private InfoPersonal infoPersonal;

    public InfoPersonalOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (InfoPersonal infoPersonal){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_NOMBRE, infoPersonal.getNombre());
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_APODO, infoPersonal.getApodo());
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_FECHANACIMIENTO, infoPersonal.getFechaNacimiento().toString());
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_CIUDAD, infoPersonal.getCiudad());
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_PAIS, infoPersonal.getPais());
            values.put(DataBaseSchema.InfoPersonalTable.COLUMN_NAME_FOTO, infoPersonal.getFoto());
            newRowId=db.insert(DataBaseSchema.InfoPersonalTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<InfoPersonal> findEvent(String productName){
        ArrayList<InfoPersonal> listaInfoPersonal=new ArrayList<InfoPersonal>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            infoPersonal=null;
            if (cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
                    try {
                        dateC= dateFormat.parse(cursor.getString(2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    infoPersonal=new InfoPersonal(cursor.getString(1),
                            cursor.getString(2),dateC,cursor.getString(4),cursor.getString(5),cursor.getBlob(6));
                    listaInfoPersonal.add(infoPersonal);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaInfoPersonal;
    }

    public ArrayList<InfoPersonal> getAllProducts(){
        ArrayList<InfoPersonal> listaInfoPersonal=new ArrayList<InfoPersonal>();
        String query="SELECT * FROM "+DataBaseSchema.InfoPersonalTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    Date dateC=null;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-yyyy");
                    try {
                        dateC= dateFormat.parse(cursor.getString(2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    infoPersonal=new InfoPersonal(cursor.getString(1),
                            cursor.getString(2),dateC,cursor.getString(4),cursor.getString(5),cursor.getBlob(6));
                    listaInfoPersonal.add(infoPersonal);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaInfoPersonal;
    }
}
