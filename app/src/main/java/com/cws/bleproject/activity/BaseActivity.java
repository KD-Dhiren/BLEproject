package com.cws.bleproject.activity;

import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.veryfit.multi.ble.AppBleListener;
import com.veryfit.multi.ble.ProtocalCallBack;
import com.veryfit.multi.entity.BleDevice;
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
import com.veryfit.multi.nativedatabase.RealTimeHealthData;
import com.veryfit.multi.nativedatabase.healthSleep;
import com.veryfit.multi.nativedatabase.healthSleepAndItems;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.BleScanTool;

public class BaseActivity extends AppCompatActivity implements BleScanTool.ScanDeviceListener, AppBleListener, ProtocalCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProtocolUtils.getInstance().setProtocalCallBack(this);
        ProtocolUtils.getInstance().setBleListener(this);
        ProtocolUtils.getInstance().setScanDeviceListener(this);
    }

    public void initView() {

    }

    public void addListener() {

    }

    public void initData() {

    }

    @Override
    public void onBLEConnectTimeOut() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBLEConnected(BluetoothGatt arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBLEConnecting(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBLEDisConnected(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBlueToothError(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDataSendTimeOut(byte[] arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onServiceDiscover(BluetoothGatt arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void healthData(byte[] arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDeviceInfo(BasicInfos arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFuncTable(FunctionInfos arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGsensorParam(GsensorParam arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onHealthHeartRate(HealthHeartRate arg0, HealthHeartRateAndItems arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onHealthSport(HealthSport arg0, HealthSportAndItems arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onHrSensorParam(HrSensorParam arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLiveData(RealTimeHealthData arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLogData(byte[] arg0, boolean arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMacAddr(byte[] arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorData(byte[] arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSleepData(healthSleep arg0, healthSleepAndItems arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWriteDataToBle(byte[] arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ProtocolUtils.getInstance().removeProtocalCallBack(this);
        ProtocolUtils.getInstance().removeListener(this);
        ProtocolUtils.getInstance().removeScanDeviceListener(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        /*ProtocolUtils.getInstance().removeProtocalCallBack(this);
        ProtocolUtils.getInstance().removeListener(this);
		ProtocolUtils.getInstance().removeScanDeviceListener(this);*/
    }

    @Override
    public void onFind(BleDevice bleDevice) {
        Log.e("found", bleDevice.mDeviceName);
    }

    @Override
    public void onFinish() {
        Log.e("not found", "finished");
    }

    @Override
    public void onSwitchDataAppEnd(SwitchDataAppEndReply arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppIng(SwitchDataAppIngReply arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppStart(SwitchDataAppStartReply arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataBleEnd(SwitchDataBleEnd arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataBleIng(SwitchDataBleIng arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataBleStart(SwitchDataBleStart arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onActivityData(SportData arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppPause(SwitchDataAppPauseReply arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppRestore(SwitchDataAppRestoreReply arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataBlePause(SwitchDataBlePause arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataBleRestore(SwitchDataBleRestore arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppBleEnd(SwitchDataAppBleEnd arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppBlePause(SwitchDataAppBlePause arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSwitchDataAppBleRestore(SwitchDataAppBleRestore arg0, int arg1) {
        // TODO Auto-generated method stub

    }

}
