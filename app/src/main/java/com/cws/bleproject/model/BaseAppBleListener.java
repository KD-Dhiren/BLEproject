package com.cws.bleproject.model;

import android.bluetooth.BluetoothGatt;
import android.util.Log;

import com.veryfit.multi.ble.AppBleListener;

/**
 * Created by asus on 2016/7/7.
 */
public class BaseAppBleListener implements AppBleListener {
    @Override
    public void onBlueToothError(int i) {
        e("onBlueToothError");
    }

    @Override
    public void onBLEConnecting(String s) {
        e("onBLEConnecting");
    }

    @Override
    public void onBLEConnected(BluetoothGatt bluetoothGatt) {
        e("onBLEConnected");
    }

    @Override
    public void onServiceDiscover(BluetoothGatt bluetoothGatt, int i) {
        e("onServiceDiscover");
    }

    @Override
    public void onBLEDisConnected(String s) {
        e("onBLEDisConnected");
    }

    @Override
    public void onBLEConnectTimeOut() {
        e("onBLEConnectTimeOut");
    }

    @Override
    public void onDataSendTimeOut(byte[] bytes) {
        e("onDataSendTimeOut");
    }
    public void e(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }

}
