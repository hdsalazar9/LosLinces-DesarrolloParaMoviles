package itesm.mx.saludintegral.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.activities.ExampleReceiver;

/**
 * Created by Jesus on 4/30/2018.
 */

public class Receiver extends BroadcastReceiver{

    NotificationChannel channel;

    Receiver(){
    }

    @Override
    public void onReceive(Context context,Intent intent){

        if (Build.VERSION.SDK_INT < 26) {
            showNotification(context);
            Log.d("Recibido", "Recibido");
        }
        else{
            showNotification26(context);
            Log.d("Recibido26", "Recibido26");
        }
    }

    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, ExampleReceiver.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.heart_icon)
                        .setVibrate(new long[] { 1000, 1000, 1000 })
                        .setContentTitle("Salud Integral")
                        .setContentText("Te toca tomarte tu medicina pls")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showNotification26(Context context) {

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, ExampleReceiver.class), 0);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        channel = new NotificationChannel("default",
                "Salud Integral",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Notificaciones de Salud Integral");
        mNotificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, "default")
                        .setSmallIcon(R.drawable.heart_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.medicina_icon))
                        .setVibrate(new long[] { 1000, 1000, 1000 })
                        .setContentTitle("Salud Integral")
                        .setContentText("Te toca tomarte tu medicina pls")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setAutoCancel(true);

        mNotificationManager.notify(1, mBuilder.build());
    }

}
