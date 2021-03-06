package view.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import java.io.Serializable;

import Pojos.Trip;
import view.AddNewTrip;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        IntentFilter filter = new IntentFilter("my.action.data");

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        Bundle args = intent.getBundleExtra("Data");
        if (args != null) {
            Trip recTrip = (Trip) args.getSerializable("obj");
            Bundle recArgs = new Bundle();
            args.putSerializable("obj", (Serializable) recTrip);
            serviceIntent.putExtra("Data", args);
        }

        String action = intent.getAction();
        if (action != null) {
            if (action.equals("my.action.data")) {
                String s = intent.getStringExtra("send");
                if (s != null) {
                    if (s.equals("stop")) {
                        serviceIntent.putExtra("send", "stop");
                    } else if (s.equals("notes")) {
                        serviceIntent.putExtra("send", "notes");
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent);
        } else {
            context.startService(serviceIntent);
        }
    }
}

