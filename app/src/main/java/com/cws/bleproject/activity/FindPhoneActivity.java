package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.FindPhoneOnOff;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;


public class FindPhoneActivity extends BaseActivity {
    private Switch switchFindPhone;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findphone);
        initView();
        initData();
        addListener();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        switchFindPhone = (Switch) findViewById(R.id.switch_findphone);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        // Initialize the search for mobile phone switch data
        FindPhoneOnOff find = ProtocolUtils.getInstance().getFindPhone();
        switchFindPhone.setChecked(find.getOnOff());
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        switchFindPhone.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // The data set after each successful setup is saved in the database for interface display.
                dialog.show();
                ProtocolUtils.getInstance().setFindPhone(arg1);//Set to find the phone switch
            }
        });
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, arg1, arg2, arg3);
        DebugLog.d("Receive a command to find a cell phone");
        if (arg1 == ProtocolEvt.SET_CMD_FIND_PHONE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(FindPhoneActivity.this, "Find the phone switch set up successfully", Toast.LENGTH_LONG).show();
                }
            });
        } else if (arg1 == ProtocolEvt.BLE_TO_APP_FIND_PHONE_START.toIndex()) {//Received the bracelet sent to find the phone command, the client received this command to do the appropriate treatment, such as the phone ringing and other operations
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(FindPhoneActivity.this, "Receive a command to find a cell phone", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
