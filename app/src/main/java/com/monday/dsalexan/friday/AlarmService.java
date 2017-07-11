package com.monday.dsalexan.friday;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Danilo on 10/07/2017.
 */

public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("friday", "We r here at onStartCommand");

        NotificationManager mNot = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent mainActivity_intent = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent mainActivity_pending_intent = PendingIntent.getActivity(this, 0, mainActivity_intent, 0);

        Notification not = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_menu_today)
                .setContentTitle("Notificação de Alarme")
                .setContentText("Notice me")
                .setContentIntent(mainActivity_pending_intent)
                .setAutoCancel(true)
                .build();

        mNot.notify(0, not);

        return START_NOT_STICKY;
    }
}
