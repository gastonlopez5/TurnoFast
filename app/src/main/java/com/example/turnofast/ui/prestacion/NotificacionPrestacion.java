package com.example.turnofast.ui.prestacion;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.turnofast.MainActivity;
import com.example.turnofast.R;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionPrestacion extends IntentService {

    private PendingIntent pendingIntent;
    private Notification notification;
    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 1010;
    private ArrayList<Prestacion> listaAnterior;
    private ArrayList<Prestacion> listaNueva;
    private Boolean bandera = true;
    private Boolean bandera2 = false;

    public NotificacionPrestacion() {
        super("NotificacionPrestacion");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while (true){

            Call<ArrayList<Prestacion>> dato= ApiClient.getMyApiClient().obtenerTodasPrestaciones(obtenerToken());
            dato.enqueue(new Callback<ArrayList<Prestacion>>() {
                @Override
                public void onResponse(Call<ArrayList<Prestacion>> call, Response<ArrayList<Prestacion>> response) {
                    if (response.isSuccessful()){
                        if (bandera){
                            listaAnterior = response.body();
                            bandera = false;
                        }
                        else {
                            listaNueva = response.body();
                            if (listaAnterior.size() != listaNueva.size()){
                                bandera2 = true;
                            }
                            if (bandera2){
                                if (listaAnterior.size() < listaNueva.size()){
                                    Prestacion p = listaNueva.get(listaNueva.size()-1);
                                    String msj = "Nueva prestacion: " + p.getNombre();
                                    triggerNotification(getApplicationContext(), msj);
                                    bandera2 = false;
                                    listaAnterior = listaNueva;
                                }
                                else if (listaAnterior.size() > listaNueva.size()){
                                    for (Prestacion p: listaAnterior) {
                                        if (!listaNueva.contains(p)){
                                            String msj = p.getNombre() + " nos ha dejado :(";
                                            triggerNotification(getApplicationContext(), msj);
                                            bandera2 = false;
                                            listaAnterior = listaNueva;
                                        }
                                    }
                                }
                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Error onResponse!", Toast.LENGTH_LONG).show();
                        Log.d("salida",response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Prestacion>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error onFailure!", Toast.LENGTH_LONG).show();
                    Log.d("salida",t.getMessage());
                }
            });

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String obtenerToken(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("token",0);
        String token = sp.getString("token","-1");
        String tokenFull = "Bearer " + token;

        return  tokenFull;
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
