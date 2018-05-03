package itesm.mx.saludintegral.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import itesm.mx.saludintegral.R;

/**
 * Created by FernandoDavid on 03/05/2018.
 */

public class EventoDisplayFragment extends DialogFragment {
    private static final String TAG = "EventoDisplayDialog";
    private TextView tvCerrar;

    public EventoDisplayFragment() {}

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_eventodisplay)

        tvCerrar = rootView.findViewById(R.id.action_cancel);

        tvCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return rootView;
    }
    */
}
