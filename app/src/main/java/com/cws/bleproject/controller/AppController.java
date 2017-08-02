package com.cws.bleproject.controller;

import android.app.Application;
import android.content.Intent;

import com.cws.bleproject.service.CallService;
import com.cws.bleproject.service.ResetHeartBeatService_at_12am;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

/**
 * Created by dhiren khatik on 26-07-2017.
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(getBaseContext(), CallService.class);
        startService(intent);
//        startService(new Intent(getBaseContext(), ResetHeartBeatService_at_12am.class));
        ProtocolUtils.getInstance().init(this);
    }
}
