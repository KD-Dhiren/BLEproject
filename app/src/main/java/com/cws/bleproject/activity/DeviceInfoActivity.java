package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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

    private TextView btnDeviceInfo;
    private TextView tvDeviceData;
    private Handler handler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        initView();
        setupActionBar();
    }

    public void initView() {
        dialog = new BufferDialog(DeviceInfoActivity.this);
        ProtocolUtils.getInstance().setProtocalCallBack(this);
        btnDeviceInfo = (TextView) findViewById(R.id.btn_device_info);
        tvDeviceData = (TextView) findViewById(R.id.tv_device_info_data);


        //There are two types of device information, the first is the case of equipment connected through the order to the device, the second is the device is not connected to the case from the database to obtain the latest updated device information for the page display.
        btnDeviceInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //BLUETOOTH_NOT_OPEN//
                //DEVICE_NOT_CONNECT//
                //DEVICE_NO_BLUEETOOTH//
                //SUCCESS//
                if (ProtocolUtils.getInstance().isAvailable() == ProtocolUtils.SUCCESS) {
                    dialog.setTitle(getResources().getString(R.string.please_wait));
                    dialog.show();
                    ProtocolUtils.getInstance().getDeviceInfo();
                } else {
                    if (ProtocolUtils.getInstance().getDeviceByDb() != null) {
                        final BasicInfos info = ProtocolUtils.getInstance().getDeviceByDb();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder sb = new StringBuilder();
                                String split[], equalizeSplit[];
                                Log.e("device_info", info.toString());
//                                [dId=1, deivceId=43, macAddress=C2:FA:98:A9:6A:EA, basicName=ID101 HR, sysTime=1501857776122, firmwareVersion=37, battStatus=0, mode=1, pairFlag=1, reboot=0, energe=86]
                                split = info.toString().split(",");
                                for (int i = 1; i < split.length; i++) {
                                    equalizeSplit = split[i].split("=");
                                    sb.append(equalizeSplit[0] + " : " + equalizeSplit[1] + "\n");

                                }
                                tvDeviceData.setText("Device Information ： \n\n" + sb);
                            }
                        });
                    }
                }
            }
        });
    }

    private void setupActionBar() {
        setTitle("Device Information");
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
//            actionBar.setDefaultDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeviceInfo(final BasicInfos device_info) {
        // TODO Auto-generated method stub
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Log.e("Device_Info", device_info.toString());
                try {
                    StringBuilder sb = new StringBuilder();
                    String split[], equalizeSplit[];
//                                [dId=1, deivceId=43, macAddress=C2:FA:98:A9:6A:EA, basicName=ID101 HR, sysTime=1501857776122, firmwareVersion=37, battStatus=0, mode=1, pairFlag=1, reboot=0, energe=86]
                    split = device_info.toString().split(",");
                    for (int i = 1; i < split.length; i++) {
                        equalizeSplit = split[i].split("=");
                        sb.append(equalizeSplit[0] + " : " + equalizeSplit[1] + "\n");

                    }
                    tvDeviceData.setText("Device Information ： \n\n" + sb);
                } catch (Exception ignored) {
                    tvDeviceData.setText("Device Information：\n\n" + device_info.toString());
                }
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
