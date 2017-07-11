package com.monday.dsalexan.friday;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Danilo on 10/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("friday", "AlarmReceiver Works baby");

        Intent service_intent = new Intent(context, AlarmService.class);
        context.startService(service_intent);
    }
}
