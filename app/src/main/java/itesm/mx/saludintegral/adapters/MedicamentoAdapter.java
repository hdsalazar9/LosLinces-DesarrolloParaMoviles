package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.TomarMedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.models.TomarMedicamento;

/**
 * Created by josec on 15/04/2018.
 */

public class MedicamentoAdapter extends ArrayAdapter<Medicamento> {
    private Context context;
    ArrayList medicinas;
    TomarMedicamentoOperations dao;
    public MedicamentoAdapter(Context context, ArrayList<Medicamento> medicinas){
        super(context, 0, medicinas);
        this.medicinas=medicinas;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Medicamento medicamento=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.medicamento_row, parent, false);
        }
        TextView tvNombre= (TextView)convertView.findViewById(R.id.text_nombre);
        TextView tvConsumo= (TextView)convertView.findViewById(R.id.text_consumo);
        ImageView pictureIV=(ImageView) convertView.findViewById(R.id.image_producto);
        tvNombre.setText(medicamento.getNombre());
        dao=new TomarMedicamentoOperations(getContext());
        dao.open();
        ArrayList<TomarMedicamento>  tomarMedicamento=dao.getAllTomarMedicamentoFrom(String.valueOf(medicamento.getId()));
        /*Date l=medicamento.getFechaComienzo();
        Toast.makeText(getContext(), l.toString(), Toast.LENGTH_SHORT).show();
**/
        String sTiempo=getTimeTo(medicamento.getHora().toString(),medicamento.getCadaCuanto(), medicamento.getFechaComienzo(), tomarMedicamento);
        if(!sTiempo.equals("Retraso"))
            tvConsumo.setText("Faltan: "+sTiempo);
        else
            tvConsumo.setText(sTiempo);
        byte[] image=medicamento.getFoto();
        if(image!=null)
        {
            Bitmap bmimage= BitmapFactory.decodeByteArray(image,0,image.length);
            pictureIV.setImageBitmap(bmimage);
        }
        else{
            pictureIV.setImageResource(R.drawable.medicina_icon);
        }
        return convertView;
    }

    public void remove(Medicamento evento) {
        medicinas.remove(evento);
    }

    public ArrayList getList() {
        return medicinas;
    }

    public static String getTimeTo(String horaInicial, int cadaCuanto, Date fechaInicio, ArrayList<TomarMedicamento>  tomarMedicamento){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String horaActual=format.format(currentTime);
        String resultado="Retraso";
        int iDiferenciaDias=0;
        int iDiferenciaA=0;
        int iDiferenciaMeses=0;
        int iHoraInicial=Integer.parseInt(horaInicial.substring(0,2));
        int iMinInicial=Integer.parseInt(horaInicial.substring(3,5));//==0?60:Integer.parseInt(horaInicial.substring(3,5));
        int iHoraAct=Integer.parseInt(horaActual.substring(0,2));
        int iMinAct=Integer.parseInt(horaActual.substring(3,5));
        Calendar dateActual = Calendar.getInstance();
        dateActual.setTime(currentTime);
        Calendar dateInicio=Calendar.getInstance();
        dateInicio.setTime(fechaInicio);
        iDiferenciaDias= dateActual.get(Calendar.DAY_OF_MONTH)-dateInicio.get(Calendar.DAY_OF_MONTH);
        iDiferenciaA=dateActual.get(Calendar.YEAR)-dateInicio.get(Calendar.YEAR);
        iDiferenciaMeses=dateActual.get(Calendar.MONTH)-dateInicio.get(Calendar.MONTH);
        if(iDiferenciaA<0){//Si faltan años para la fecha de inicio, se imprime la cantidad de años
            if(iDiferenciaA==-1)//Falta un año
                resultado=String.valueOf(Math.abs(iDiferenciaA))+" Año";
            else//Faltan más años
                resultado=String.valueOf(Math.abs(iDiferenciaA))+" Años";
            return resultado;
        }
        else if(iDiferenciaA==0&&iDiferenciaMeses<0){//Faltan meses para la fecha de inicio
            if(iDiferenciaMeses==-1) {
                if(iDiferenciaDias>0){//Si falta menos de un mes, calcula la cantidad de dias
                    if(iDiferenciaDias<28){//Falta más de 1 o 2 día para la fecha de inicio
                        resultado = String.valueOf(Calendar.getInstance().getActualMaximum(dateActual.get(Calendar.DAY_OF_MONTH))-iDiferenciaDias) + " Días";
                    }
                    else{ //Faltan pocos días para el comienzo de la medicación, por lo que se ponen horas
                        resultado = String.valueOf(24*(Calendar.getInstance().getActualMaximum(dateActual.get(Calendar.DAY_OF_MONTH))-iDiferenciaDias-1)+ (iHoraAct-24+iHoraInicial)) + " Horas";
                    }
                }
                else//Falta un mes para la fecha de inicio
                resultado = String.valueOf(Math.abs(iDiferenciaMeses)) + " Mes";
            }
            else//Falta más de un mes para la fecha de inicio
                resultado=String.valueOf(Math.abs(iDiferenciaMeses))+" Meses";
            return resultado;
        }
        else if(iDiferenciaA==0 && iDiferenciaMeses==0&&iDiferenciaDias<0){//Es el mismo mes que la fecha de inicio pero faltan dias
            if(iDiferenciaDias<-2){//Faltan mas de dos dias
                resultado = String.valueOf(Math.abs(iDiferenciaDias))+ " Días";
            }
            else{//Falta menos de dos dias
                resultado = String.valueOf(24*(Math.abs(iDiferenciaDias)-1)+ (24-iHoraAct+iHoraInicial)) + " Horas";
            }
            return resultado;
        }
        else if(iDiferenciaA==0 && iDiferenciaMeses==0&&iDiferenciaDias==0&&iHoraAct<=iHoraInicial){
            if(iHoraAct<iHoraInicial){
                if(iHoraInicial-iHoraAct==1){//La siguiente hora comienza la medicación
                    resultado=getMinutos(iMinInicial+Math.abs(iMinAct-60))+" Minutos";
                }
                else{//Falta más de una hora para la primera medicación
                    double i=(iHoraInicial*60-iHoraAct*60)+Math.abs(iMinAct-60)+iMinInicial-60;
                    resultado=String.valueOf((int)Math.floor(i/60))+":"+getMinutos((int)(i%60))+ " Horas";
                }
            }
            else if(iHoraInicial==iHoraAct){
               if (iMinAct<iMinInicial)
                    resultado="00:"+getMinutos(iMinInicial-iMinAct)+" Horas";
                else if(iMinAct>iMinInicial){
                    if(yaTomado(tomarMedicamento, iHoraInicial, iMinInicial,dateActual,cadaCuanto,dateInicio)) {
                        double i=(cadaCuanto*60-60)+Math.abs(iMinAct-60)+iMinInicial;
                        resultado = String.valueOf((int)Math.floor(i/60)) + ":" + getMinutos((int)(i%60)) + " Horas";
                    }
                    else resultado="Retraso";
                }
                else if((yaTomado(tomarMedicamento, iHoraInicial, iMinInicial,dateActual,cadaCuanto,dateInicio))){
                   double i=(cadaCuanto*60-60)+Math.abs(iMinAct-60)+iMinInicial;
                   resultado = String.valueOf((int)Math.floor(i/60)) + ":" + getMinutos((int)(i%60)) + " Horas";
               }
               else
                    resultado="00:00";
            }
            return resultado;
        }


        long difAct=((dateActual.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000))-iHoraInicial*60-iMinInicial;
        long horaPuntual=difAct%(cadaCuanto*60);
        double dHoras;
        if(horaPuntual>Math.round(cadaCuanto*60/2)){
            horaPuntual=difAct+(cadaCuanto*60-horaPuntual);
        }
        else{
            horaPuntual=difAct-horaPuntual;
        }
        switch (yaTomado2(tomarMedicamento, iHoraInicial, iMinInicial,dateActual,cadaCuanto,dateInicio)){
            case 0:         //Retraso
                resultado="Retraso";
                break;
            case 1:         //No se ha tomado la siguiente inmediata
                horaPuntual=(horaPuntual%1440)+iHoraInicial*60+iMinInicial;
                horaPuntual=horaPuntual-(iMinAct+iHoraAct*60);
                dHoras=Math.floor(horaPuntual/60);
                resultado=(int)dHoras+":"+getMinutos((int)(horaPuntual%60))+" Horas";
                break;
            case 2:         //Ya se tomó la siguiente inmediata, calcular la siguiente
                horaPuntual=horaPuntual+cadaCuanto*60;
                horaPuntual=(horaPuntual%1440)+iHoraInicial*60+iMinInicial;
                horaPuntual=horaPuntual-(iMinAct+iHoraAct*60);
                dHoras=Math.floor(horaPuntual/60);
                resultado=(int)dHoras+":"+getMinutos((int)(horaPuntual%60))+" Horas";
                break;
            case 3:         //Calcular la siguiente siguiente inmediata
                horaPuntual=horaPuntual+cadaCuanto*60;
                horaPuntual=(horaPuntual%1440)+iHoraInicial*60+iMinInicial;
                horaPuntual=horaPuntual-(iMinAct+iHoraAct*60);
                dHoras=Math.floor(horaPuntual/60);
                resultado=(int)dHoras+":"+getMinutos((int)(horaPuntual%60))+" Horas";
                break;
        }
       /* long iDif=(dateActual.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000)+iMinInicial+iHoraInicial*60;
        long i= cadaCuanto*60-(iDif%(60*cadaCuanto))+10;
        double dHoras=Math.floor(i/60);

        resultado=(int)dHoras+":"+getMinutos((int)(i%60))+" Horas";*/
        return resultado;
    }

    public static boolean yaTomado(ArrayList<TomarMedicamento>  tomarMedicamento, int iHoraInicial, int iMinInicial, Calendar dateActual, int cadaCuanto, Calendar dateInicio){
        TomarMedicamento ultimoTomado;
        if(tomarMedicamento.size()<1)
            return false;
        ultimoTomado=tomarMedicamento.get(tomarMedicamento.size()-1);

        Calendar dateTomado=Calendar.getInstance();
        dateTomado.setTime(ultimoTomado.getFechaHora());

        long iTomadoMin=(dateTomado.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000)+iMinInicial+iHoraInicial*60;
        long iDif=(dateActual.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000)+iMinInicial+iHoraInicial*60;
        long i= iDif%(60*cadaCuanto);
        /*if(i>Math.round(cadaCuanto*60/2)&&dateTomado.getTimeInMillis()<dateActual.getTimeInMillis()){//Checar si está en un nuevo bloque y que no hay ingerido ya la pastilla
            return true;
        }
        else if(i<Math.round(cadaCuanto*60/2)&&iDif-Math.round(cadaCuanto*60/2)-i<iTomadoMin){//Checar que no se haya tomado en su bloque, solo si no ha cruzado al otro bloque
            return true;
        }*/
        if(iDif-iTomadoMin<cadaCuanto*60)
            return true;
        return false;
    }

    public static int yaTomado2(ArrayList<TomarMedicamento>  tomarMedicamento, int iHoraInicial, int iMinInicial, Calendar dateActual, int cadaCuanto, Calendar dateInicio) {
        TomarMedicamento ultimoTomado;
        if(tomarMedicamento.size()<1)
            return 0;
        ultimoTomado=tomarMedicamento.get(tomarMedicamento.size()-1);
        Calendar dateTomado=Calendar.getInstance();
        dateTomado.setTime(ultimoTomado.getFechaHora());
        long difAct=((dateActual.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000))-iHoraInicial*60-iMinInicial;
        long difTomado=((dateTomado.getTimeInMillis()-dateInicio.getTimeInMillis())/(60*1000))-iHoraInicial*60-iMinInicial;
        long horaPuntual=difAct%(cadaCuanto*60);
        if(horaPuntual>Math.round(cadaCuanto*60/2)){
            horaPuntual=difAct+(cadaCuanto*60-horaPuntual);
        }
        else{
            horaPuntual=difAct-horaPuntual;
        }
        if(difAct<horaPuntual){
            if(difTomado<horaPuntual-Math.round(cadaCuanto*60/2)){  //Calcula hora inmediata, no hay medicameto tomado actualmente
                return 1;
            }
            else{
                return 2;
            }
        }
        else if(difTomado<horaPuntual-Math.round(cadaCuanto*60/2)){
            return 0;
        }
        else{
            return 3;
        }

    }

    public static String getMinutos(int iMinutos){
        if(iMinutos>9){
            return String.valueOf(iMinutos);
        }
        return "0"+String.valueOf(iMinutos);
    }
}
