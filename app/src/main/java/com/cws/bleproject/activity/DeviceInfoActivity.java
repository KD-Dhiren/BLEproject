package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.activity.BleActivity;
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


public class DeviceInfoActivity extends BleActivity {

    private Button btnDeviceInfo;
    private TextView tvDeviceData;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        initView();
    }

    public void initView() {
        dialog = new BufferDialog(DeviceInfoActivity.this);
        ProtocolUtils.getInstance().setProtocalCallBack(this);
        btnDeviceInfo = (Button) findViewById(R.id.btn_device_info);
        tvDeviceData = (TextView) findViewById(R.id.tv_device_info_data);

        //There are two types of device information, the first is the case of equipment connected through the order to the device, the second is the device is not connected to the case from the database to obtain the latest updated device information for the page display.
        btnDeviceInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //BLUETOOTH_NOT_OPEN// 蓝牙没打开
                //DEVICE_NOT_CONNECT// 设备未连接
                //DEVICE_NO_BLUEETOOTH// 无蓝牙设备
                //SUCCESS// 一切正常
                if (ProtocolUtils.getInstance().isAvailable() == ProtocolUtils.SUCCESS) {
                    dialog.setTitle("Please wait");
                    dialog.show();
                    ProtocolUtils.getInstance().getDeviceInfo();
                } else {
                    if (ProtocolUtils.getInstance().getDeviceByDb() != null) {
                        final BasicInfos info = ProtocolUtils.getInstance().getDeviceByDb();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvDeviceData.setText("Device Information：\n\n" + info.toString());
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onDeviceInfo(final BasicInfos arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                tvDeviceData.setText("Device Information：\n\n" + arg0.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ProtocolUtils.getInstance().removeProtocalCallBack(this);
    }

    @Override
    public void healthData(byte[] arg0) {
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

}
