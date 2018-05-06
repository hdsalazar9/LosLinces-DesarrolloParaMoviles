package itesm.mx.saludintegral.util;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by FernandoDavid on 23/04/2018.
 */

public class Miscellaneous {
    public static String strDatePicker = "";
    public static String strTipo = "";
    public static HashMap<Date,Drawable> mapFechaFondo;

    public static String[] tipos = {
            "Espiritualidad",       //0
            "Actividad Cognitiva",  //1
            "Finanzas",             //2
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
}
