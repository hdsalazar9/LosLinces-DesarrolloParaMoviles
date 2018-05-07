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
    public static HashMap<Date,Drawable> mapFechaFondo;


    public static String[] tipos = {
            "Espiritualidad",       //0
            "Actividad Cognitiva",  //1
            "Finanzas",             //2
            "Actividad Física",     //3
            "Cumpleaños Amigos",    //4
            "Eventos Amigos",       //5
            "Cumpleaños Familia",   //6
            "Eventos Familia"       //7
    };


    public static String getMonthFromInt(Integer iMonth){
     String sMonth = null;

     switch (iMonth){
         case 1:
             sMonth = "Jan";
             break;
         case 2:
             sMonth = "Feb";
             break;
         case 3:
             sMonth = "Mar";
             break;
         case 4:
             sMonth = "Apr";
             break;
         case 5:
             sMonth = "May";
             break;
         case 6:
             sMonth = "Jun";
             break;
         case 7:
             sMonth = "Jul";
             break;
         case 8:
             sMonth = "Aug";
             break;
         case 9:
             sMonth = "Sep";
             break;
         case 10:
             sMonth = "Oct";
             break;
         case 11:
             sMonth = "Nov";
             break;
         case 12:
             sMonth = "Dec";
             break;
     }
     return sMonth;
    }

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
