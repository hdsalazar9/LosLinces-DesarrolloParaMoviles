package itesm.mx.saludintegral.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.dbcreation.SaludIntegralDBHelper;
import itesm.mx.saludintegral.models.ContactoEmergencia;

/**
 * Created by josec on 14/04/2018.
 */

public class ContactoEmergenciaOperations {
    private SQLiteDatabase db;
    private SaludIntegralDBHelper dbHelper;
    private ContactoEmergencia contactoEmergencia;

    public ContactoEmergenciaOperations(Context context){dbHelper=new SaludIntegralDBHelper(context);}
    public void open()throws SQLException {
        try {
            db=dbHelper.getWritableDatabase();
        }
        catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close(){db.close();}

    public long addEvento (ContactoEmergencia contactoEmergencia){
        long newRowId=0;
        try{
            ContentValues values=new ContentValues();
            values.put(DataBaseSchema.ContactoEmergenciaTable.COLUMN_NAME_NOMBRE, contactoEmergencia.getNombre());
            values.put(DataBaseSchema.ContactoEmergenciaTable.COLUMN_NAME_TELEFONO, contactoEmergencia.getTelefono());
            newRowId=db.insert(DataBaseSchema.ContactoEmergenciaTable.TABLE_NAME, null, values);
            Log.d("Product added", "Product added");
        }
        catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public ArrayList<ContactoEmergencia> findEvent(String productName){
        ArrayList<ContactoEmergencia> listaContactoEmergencia=new ArrayList<ContactoEmergencia>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME+" WHERE "+DataBaseSchema.EventosTable.COLUMN_NAME_NOMBRE+
                " = \""+ productName+"\"";
        try {
            Cursor cursor=db.rawQuery(query, null);
            contactoEmergencia=null;
            if (cursor.moveToFirst()){
                do{
                    contactoEmergencia=new ContactoEmergencia(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2));
                    listaContactoEmergencia.add(contactoEmergencia);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e){
            Log.e("SQLFIND", e.toString());
        }
        return listaContactoEmergencia;
    }

    public ArrayList<ContactoEmergencia> getAllProducts(){
        ArrayList<ContactoEmergencia> listaContactoEmergencia=new ArrayList<ContactoEmergencia>();
        String query="SELECT * FROM "+DataBaseSchema.EventosTable.TABLE_NAME;
        try {
            Cursor cursor=db.rawQuery(query,null);
            if(cursor.moveToFirst()){
                do{
                    contactoEmergencia=new ContactoEmergencia(cursor.getInt(0),cursor.getString(1),
                            cursor.getString(2));
                    listaContactoEmergencia.add(contactoEmergencia);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        catch (SQLException e)
        {
            Log.e("SQLList", e.toString());
        }
        return listaContactoEmergencia;
    }
}
