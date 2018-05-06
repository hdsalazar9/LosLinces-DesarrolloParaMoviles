package itesm.mx.saludintegral.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.TimeZone;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 23/04/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText etFecha;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        int actualMonth = month+1;
        String strDay = String.valueOf(day);
        String strMonth = String.valueOf(actualMonth);
        String strYear = String.valueOf(year);

        if(strDay.length() == 1) {
            strDay = "0" + String.valueOf(day);
        }

        if(strMonth.length() == 1) {
            strMonth = "0" + String.valueOf(actualMonth);
        }

        String strFecha = strDay + "-" + strMonth + "-" + strYear;

        if(Miscellaneous.strDatePicker.equals("fechaInicio")) {
            etFecha = getActivity().findViewById(R.id.et_inicioMed);
            etFecha.setText(strFecha);
        }

        if(Miscellaneous.strDatePicker.equals("fechaTermino")) {
            etFecha = getActivity().findViewById(R.id.et_terminoMed);
            etFecha.setText(strFecha);
        }

        if(Miscellaneous.strDatePicker.equals("fechaEvento")) {
            etFecha = getActivity().findViewById(R.id.et_addevento_fecha);
            etFecha.setText(strFecha);
        }
    }
}
