package itesm.mx.saludintegral.util;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import itesm.mx.saludintegral.adapters.MenuItemAdapter;

/**
 * Created by FernandoDavid on 23/04/2018.
 */

public class Miscellaneous {
    public static int iNumViejo =0;
    public static String strDatePicker = "";
    public static String strTipo = "";
    public static int iSizeMenu = 0;
    public static int iSizeSubMenu = 0;
    public static HashMap<Date,Drawable> mapFechaFondo;


    public static String[] tipos = {
            "Espiritualidad",       //0 Espiritual
            "Actividad Cognitiva",  //1 Cognicion
            "Finanzas",             //2 Cognicion
            "Actividad Física",     //3 Salud
            "Cumpleaños Amigos",    //4
            "Eventos Amigos",       //5 Social
            "Cumpleaños Familia",   //6
            "Eventos Familia",      //7 Social
            "Contacto Emergencia",   //8
            "Perfil",
            "Main Menu",
            "Loading"
    };

    //Metodo que recibe util.Date y lo convierte al patron necesario para utilizarse en a BD
    public static String getStringFromDate(Date dateToConvert){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(dateToConvert);
    }

    //Metodo que recibe string en formato "dd-MM-yyyy" y lo convierte a date
    public static Date getDateFromString(String stringToConvert){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try{
            return df.parse(stringToConvert);
        } catch (Exception e){
            Log.d("getStringFromDate",e.getMessage());
        }
        return null;
    }

    //Método que borra todos los elementos del map
    public static void limpiaMapFechaFondo() {
        mapFechaFondo = new HashMap<Date,Drawable>();
        Log.d("Misc","se limpia el map FechaFondo");
    }

    //Método que actualiza el tamaño de una lista
    public static void refresh(MenuItemAdapter menuItemAdapter){
        menuItemAdapter.notifyDataSetChanged();
        Log.d("REFRESH", "Se actualizó el tamaño de la listView");
    }

    public static void setiSizeMenu(int iNum) {
        iNumViejo = iSizeMenu;
        iSizeMenu = iNum;
    }
}