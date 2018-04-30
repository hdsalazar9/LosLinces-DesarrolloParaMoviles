package itesm.mx.saludintegral.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jesus on 4/30/2018.
 */

public class Receiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context,Intent intent){

        Toast.makeText(context, "Recibido!", Toast.LENGTH_SHORT).show();
        Log.d("Recibido", "Recibido");

    }
}
