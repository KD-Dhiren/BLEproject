package com.cws.bleproject.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.adapter.ScanAdapter;
import com.cws.bleproject.config.MyPreference;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.entity.BleDevice;
import com.veryfit.multi.nativeprotocol.Protocol;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ScanDeviceActivity extends BaseActivity implements OnClickListener {
    private static final int REQUEST_ENABLE_BT = 55;
    private ListView lView;
    private Button btnScan, btnBind;
    private ScanAdapter adapter;
    private Handler handler = new Handler();
    private Handler handler1 = new Handler();
    private MyPreference pref;
    private ArrayList<BleDevice> list = new ArrayList<BleDevice>();
    private int index;
    private BufferDialog dialog;
    private MyBroadCastReceiver receiver;
    public static boolean btAsked = false, locAsked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        enableBluetoothAndGPSLoc();
//        methodRequestPermission();
        initView();
        addListener();
        addFilter();
    }

    private void enableBluetoothAndGPSLoc() {
        if (locAsked) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                new General_Helper(this).displayLocationSettingsRequest();
            }
            locAsked = false;
        }

//Setting up bluetooth:
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
// Device does not support Bluetooth
        } else {
//Enable bluetooth:
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

//Finding devices:
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    /*ArrayAdapter mArrayAdapter = new ArrayAdapter(this, R.id.lv_device);
                    mArrayAdapter.add(device.getName() + "\n" + device.getAddress());*/
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ENABLE_BT) {
                //Bluetooth allowed
            }

        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (btAsked)
                Toast.makeText(ScanDeviceActivity.this, "You twice denied to access Bluetooth. App may Exit in 2 sec..", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(ScanDeviceActivity.this, "You just denied to access Bluetooth. please allow it again..", Toast.LENGTH_LONG).show();

            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btAsked) {
                        finish();
                        btAsked = false;
                    } else {
                        enableBluetoothAndGPSLoc();
                        btAsked = true;
                    }
                }
            }, 2000);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                ProtocolUtils.getInstance().scanDevices(true);
            }
        });
    }

    @Override
    public void initView() {
        receiver = new MyBroadCastReceiver();
        pref = MyPreference.getInstance(this);
        dialog = new BufferDialog(this);
        lView = (ListView) findViewById(R.id.lv_device);
        btnBind = (Button) findViewById(R.id.btn_bind);
        btnBind.setEnabled(false);
        btnScan = (Button) findViewById(R.id.btn_scan);
        adapter = new ScanAdapter(this, null);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                index = arg2;
                adapter.setSelectItem(arg2);
                btnBind.setEnabled(true);
            }
        });
    }

    @Override
    public void addListener() {
        btnBind.setOnClickListener(this);
        btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_bind:
                enableBluetoothAndGPSLoc();
                DebugLog.e("bind");
                dialog.show();
                ProtocolUtils.getInstance().connect(list.get(index).mDeviceAddress);
                break;
            case R.id.btn_scan:
                enableBluetoothAndGPSLoc();
                list.clear();
                btnScan.setText("Searching...");
                adapter.clear();
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Log.e("Scanning", "BLE devices");
                        ProtocolUtils.getInstance().scanDevices();
                    }
                });
                break;
            default:
                break;
        }
    }

    // Discovery equipment
    @Override
    public void onFind(BleDevice device) {
        // TODO Auto-generated method stub
        super.onFind(device);
        showList(device);
        list.add(device);
        Collections.sort(list);
    }

    // The scan ends
    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        super.onFinish();
        Log.e("onFinish", "No Device Found");
        Toast.makeText(this, "Scanning completed", Toast.LENGTH_SHORT).show();
        btnScan.setText("Scan Device");
    }

    private void showList(final BleDevice device) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.upDada(device);
            }
        });
    }

    @Override
    public void onServiceDiscover(BluetoothGatt gatt, int status) {
        // TODO Auto-generated method stub
        super.onServiceDiscover(gatt, status);
        ProtocolUtils.getInstance().setBind();
    }

    @Override
    public void onSysEvt(int evt_base, int evt_type, int error, int value) {
        // TODO Auto-generated method stub
        super.onSysEvt(evt_base, evt_type, error, value);
        if (evt_type == ProtocolEvt.BIND_CMD_REQUEST.toIndex() && error == ProtocolEvt.SUCCESS) {
            if (dialog != null) {
                dialog.setTitle("Bind is successful and is synchronizing configuration information");
            }
            pref.setIsFirst(true);
            handler1.post(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_BIND);
                    ProtocolUtils.getInstance().StartSyncConfigInfo();
                }
            });
        } else if (evt_type == ProtocolEvt.SYNC_EVT_CONFIG_SYNC_COMPLETE.toIndex() && error == ProtocolEvt.SUCCESS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Intent intent = new Intent(ScanDeviceActivity.this, UserInfosActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ScanDeviceActivity.this, "Synchronization configuration information is successful", Toast.LENGTH_LONG).show();
                }
            }, 200);
        }
    }

    public void addFilter() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                DebugLog.d("find" + device.toString());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
