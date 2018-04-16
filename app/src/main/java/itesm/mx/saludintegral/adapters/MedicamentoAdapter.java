package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.Medicamento;

/**
 * Created by josec on 15/04/2018.
 */

public class MedicamentoAdapter extends ArrayAdapter<Medicamento> {
    private Context context;
    ArrayList medicinas;

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
        tvConsumo.setText(getTimeLeft(medicamento.getHora().toString(),medicamento.getCadaCuanto()));
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

    public static String getTimeLeft(String horaInicial, int cadaCuanto){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date();
        String horaActual=format.format(currentTime);
        String resultado="Retraso";
        int iHoraInicial=Integer.parseInt(horaInicial.substring(0,2));
        int iMinInicial=Integer.parseInt(horaInicial.substring(3,5));
        int iHoraAct=Integer.parseInt(horaActual.substring(0,2));
        int iMinAct=Integer.parseInt(horaActual.substring(3,5));
        String sHora=(iHoraAct-cadaCuanto)<10?"0"+String.valueOf(iHoraAct-cadaCuanto):String.valueOf(iHoraAct-cadaCuanto);
        resultado=sHora+":"+horaActual.substring(0,2);
        return resultado;
    }
}
