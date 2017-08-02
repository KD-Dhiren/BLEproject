package com.cws.bleproject.service;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.veryfit.multi.ble.AppBleListener;
import com.veryfit.multi.ble.ProtocalCallBack;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.entity.SportData;
import com.veryfit.multi.entity.SwitchDataAppBleEnd;
import com.veryfit.multi.entity.SwitchDataAppBlePause;
import com.veryfit.multi.entity.SwitchDataAppBleRestore;
import com.veryfit.multi.entity.SwitchDataAppEndReply;
import com.veryfit.multi.entity.SwitchDataAppIngReply;
import com.veryfit.multi.entity.SwitchDataAppPauseReply;
import com.veryfit.multi.entity.SwitchDataAppRestoreReply;
import com.veryfit.multi.entity.SwitchDataAppStartReply;
import com.veryfit.multi.entity.SwitchDataBleEnd;
import com.veryfit.multi.entity.SwitchDataBleIng;
import com.veryfit.multi.entity.SwitchDataBlePause;
import com.veryfit.multi.entity.SwitchDataBleRestore;
import com.veryfit.multi.entity.SwitchDataBleStart;
import com.veryfit.multi.nativedatabase.BasicInfos;
import com.veryfit.multi.nativedatabase.FunctionInfos;
import com.veryfit.multi.nativedatabase.GsensorParam;
import com.veryfit.multi.nativedatabase.HealthHeartRate;
import com.veryfit.multi.nativedatabase.HealthHeartRateAndItems;
import com.veryfit.multi.nativedatabase.HealthSport;
import com.veryfit.multi.nativedatabase.HealthSportAndItems;
import com.veryfit.multi.nativedatabase.HrSensorParam;
import com.veryfit.multi.nativedatabase.NoticeOnOff;
import com.veryfit.multi.nativedatabase.RealTimeHealthData;
import com.veryfit.multi.nativedatabase.Units;
import com.veryfit.multi.nativedatabase.healthSleep;
import com.veryfit.multi.nativedatabase.healthSleepAndItems;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.share.BleSharedPreferences;
import com.veryfit.multi.util.DebugLog;

public class CallService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        ProtocolUtils.getInstance().setBleListener(appBleListener);
        ProtocolUtils.getInstance().setProtocalCallBack(protocalCallBack);

        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(phone_state_receiver, filter);
    }

    ProtocalCallBack protocalCallBack = new ProtocalCallBack() {
        @Override
        public void onWriteDataToBle(byte[] bytes) {

        }

        @Override
        public void onDeviceInfo(BasicInfos basicInfos) {

        }

        @Override
        public void healthData(byte[] bytes) {

        }

        @Override
        public void onSensorData(byte[] bytes) {

        }

        @Override
        public void onGsensorParam(GsensorParam gsensorParam) {

        }

        @Override
        public void onHrSensorParam(HrSensorParam hrSensorParam) {

        }

        @Override
        public void onFuncTable(FunctionInfos functionInfos) {

        }

        @Override
        public void onSleepData(healthSleep healthSleep, healthSleepAndItems healthSleepAndItems) {

        }

        @Override
        public void onHealthSport(HealthSport healthSport, HealthSportAndItems healthSportAndItems) {

        }

        @Override
        public void onHealthHeartRate(HealthHeartRate healthHeartRate, HealthHeartRateAndItems healthHeartRateAndItems) {

        }

        @Override
        public void onLiveData(RealTimeHealthData realTimeHealthData) {

        }

        @Override
        public void onMacAddr(byte[] bytes) {

        }

        @Override
        public void onSysEvt(int i, int i1, int i2, int i3) {

        }

        @Override
        public void onLogData(byte[] bytes, boolean b) {

        }

        @Override
        public void onSwitchDataAppStart(SwitchDataAppStartReply switchDataAppStartReply, int i) {

        }

        @Override
        public void onSwitchDataAppIng(SwitchDataAppIngReply switchDataAppIngReply, int i) {

        }

        @Override
        public void onSwitchDataAppEnd(SwitchDataAppEndReply switchDataAppEndReply, int i) {

        }

        @Override
        public void onSwitchDataBleStart(SwitchDataBleStart switchDataBleStart, int i) {

        }

        @Override
        public void onSwitchDataBleIng(SwitchDataBleIng switchDataBleIng, int i) {

        }

        @Override
        public void onSwitchDataBleEnd(SwitchDataBleEnd switchDataBleEnd, int i) {

        }

        @Override
        public void onSwitchDataAppPause(SwitchDataAppPauseReply switchDataAppPauseReply, int i) {

        }

        @Override
        public void onSwitchDataAppRestore(SwitchDataAppRestoreReply switchDataAppRestoreReply, int i) {

        }

        @Override
        public void onSwitchDataBlePause(SwitchDataBlePause switchDataBlePause, int i) {

        }

        @Override
        public void onSwitchDataBleRestore(SwitchDataBleRestore switchDataBleRestore, int i) {

        }

        @Override
        public void onSwitchDataAppBlePause(SwitchDataAppBlePause switchDataAppBlePause, int i) {

        }

        @Override
        public void onSwitchDataAppBleRestore(SwitchDataAppBleRestore switchDataAppBleRestore, int i) {

        }

        @Override
        public void onSwitchDataAppBleEnd(SwitchDataAppBleEnd switchDataAppBleEnd, int i) {

        }

        @Override
        public void onActivityData(SportData sportData, int i) {

        }
    };

    AppBleListener appBleListener = new AppBleListener() {
        @Override
        public void onBlueToothError(int i) {

        }

        @Override
        public void onBLEConnecting(String s) {

        }

        @Override
        public void onBLEConnected(BluetoothGatt bluetoothGatt) {

        }

        @Override
        public void onServiceDiscover(BluetoothGatt bluetoothGatt, int i) {

        }

        @Override
        public void onBLEDisConnected(String s) {

        }

        @Override
        public void onBLEConnectTimeOut() {

        }

        @Override
        public void onDataSendTimeOut(byte[] bytes) {

        }
    };

    public BroadcastReceiver phone_state_receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                switch (tm.getCallState()) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        NoticeOnOff onOff = ProtocolUtils.getInstance().getNotice();
                        String phoneNumber = intent.getStringExtra("incoming_number");
                        DebugLog.e("onOff=" + onOff.getCallonOff());
                        Log.e("incoming_number", phoneNumber);
                        if (onOff.getCallonOff()) {
                            ProtocolUtils.getInstance().setCallEvt(phoneNumber, phoneNumber);
                        }
                        DebugLog.e("phoneNumber=" + phoneNumber);
                        break;
                }
            } else if (intent.getAction().equals(Intent.ACTION_TIME_CHANGED)) {
                DebugLog.e("Time_Changed");//Changed the time standard
                if (BleSharedPreferences.getInstance().getIsBind()) {
                    Units units = ProtocolUtils.getInstance().getUnits();
                    ProtocolUtils.getInstance().setUint(units.getMode(), units.getStride(), units.getLanguage(), is24HourFormat() ? Constants.TIME_MODE_24 : Constants.TIME_MODE_12);
                }
            }
        }
    };

    public boolean is24HourFormat() {
        return DateFormat.is24HourFormat(this);
    }

    public void onDestroy() {
        unregisterReceiver(phone_state_receiver);
        ProtocolUtils.getInstance().removeListener(appBleListener);
        ProtocolUtils.getInstance().removeProtocalCallBack(protocalCallBack);
    }
}
