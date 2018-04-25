package itesm.mx.saludintegral.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import itesm.mx.saludintegral.R;

/**
 * Created by FernandoDavid on 24/04/2018.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget

        EditText etHoraIngesta = (EditText) getActivity().findViewById(R.id.et_horaIngestaMed);
        String strTime;
        String strHora = String.valueOf(hourOfDay);
        String strMinuto = String.valueOf(minute);

        //Set a message for user
        if(strHora.length() == 1) {
            strHora = "0" + strHora;
        }

        if(strMinuto.length() == 1) {
            strMinuto = "0" + strMinuto;
        }

        strTime = strHora + ":" + strMinuto;
        etHoraIngesta.setText(strTime);
    }

}
