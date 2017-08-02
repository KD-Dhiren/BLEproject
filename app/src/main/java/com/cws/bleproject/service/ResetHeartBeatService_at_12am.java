package com.cws.bleproject.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cws.bleproject.Helper.SP_Helper;

/**
 * Created by dhiren khatik on 01-08-2017.
 */

public class ResetHeartBeatService_at_12am extends Service {

    SP_Helper sp_helper;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public ResetHeartBeatService_at_12am() {
        super();
    }

    public ResetHeartBeatService_at_12am(Context context) {
        this.context = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();

     /*   sp_helper = new SP_Helper(context);
        sp_helper.setHeartRate("");*/

        IntentFilter filter = new IntentFilter();
        filter.addAction(ALARM_SERVICE);
//        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(alarm_receiver, filter);
    }

    public BroadcastReceiver alarm_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
            Log.e("log", "alarm service called");
            /*sp_helper = new SP_Helper(context);
            sp_helper.setHeartRate("");*/
        }
    };
}
