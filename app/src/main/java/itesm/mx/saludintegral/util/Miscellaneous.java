package itesm.mx.saludintegral.util;

/**
 * Created by FernandoDavid on 23/04/2018.
 */

public class Miscellaneous {
    public static String strDatePicker = "";
    public static String strTipo = "";

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
}
