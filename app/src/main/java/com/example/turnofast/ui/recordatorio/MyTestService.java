package com.example.turnofast.ui.recordatorio;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.turnofast.MainActivity;
import com.example.turnofast.R;

import java.util.Calendar;

public class MyTestService extends IntentService {
    private PendingIntent pendingIntent;
    private Notification notification;
    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 1010;

    public MyTestService() {
        super("Servicio");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true){
            ItemRepository itemRepository = new ItemRepository(getApplicationContext());
            Calendar calendario = Calendar.getInstance();
            int hora, min,dia,mes,ano;
            String fecha_sistema, hora_sistema;

            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH)+1;
            ano = calendario.get(Calendar.YEAR);
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            min = calendario.get(Calendar.MINUTE);
            fecha_sistema = dia+"/"+mes+"/"+ano;
            hora_sistema = (hora-3)+":"+min;

            DbTable dbTable = itemRepository.getItem(fecha_sistema, hora_sistema);
            if (dbTable != null){
                String msj = dbTable.getEncabezado()+"\n"+"Fecha: "+
                        dbTable.getFecha()+"\n"+"Hora: "+dbTable.getHora();
                itemRepository.deleteItem(dbTable);
                triggerNotification(getApplicationContext(), msj);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void triggerNotification(Context context, String t) {
        String NOTIFICATION_CHANNEL_ID = context.getString(R.string.app_name);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, MainActivity.class);
        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        String message = t;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final int NOTIFY_ID = 0; // ID of notification
            String id = NOTIFICATION_CHANNEL_ID; // default_channel_id
            String title = NOTIFICATION_CHANNEL_ID; // Default Channel
            PendingIntent pendingIntent;
            NotificationCompat.Builder builder;
            NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notifManager == null) {
                notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
                    .setSmallIcon(R.drawable.ic_notificacion)   // required
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notificacion))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setSound(soundUri)

                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Notification notification = builder.build();
            notifManager.notify(NOTIFY_ID, notification);

            startForeground(1, notification);

        } else {
            pendingIntent = PendingIntent.getActivity(context, 1, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_notificacion)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_notificacion))
                    .setSound(soundUri)
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name)).setCategory(Notification.CATEGORY_SERVICE)
                    .setContentText(message).build();
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
