package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.DoNotDisturb;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class DoNotDisturbActivity extends BaseActivity {
    private TextView btnCommit;
    private Switch switchDoNotDisturb;
    private EditText edStartHour, edStartMin, edEndHour, edEndMin;
    private boolean onOff;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donotdisturb);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        initData();
        addListener();
        setupActionBar();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        edEndHour = (EditText) findViewById(R.id.ed_donotdisturb_end_hour);
        edEndMin = (EditText) findViewById(R.id.ed_donotdisturb_end_min);
        edStartHour = (EditText) findViewById(R.id.ed_donotdisturb_start_hour);
        edStartMin = (EditText) findViewById(R.id.ed_donotdisturb_start_min);
        btnCommit = (TextView) findViewById(R.id.btn_donotdisturb_commit);
        switchDoNotDisturb = (Switch) findViewById(R.id.switch_donotdisturb);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        //Gets the intervalList that has been set up for interference
        DoNotDisturb disturb = ProtocolUtils.getInstance().getDoNotDisturb();
        if (disturb != null) {
            edEndHour.setText(disturb.getEndHour() + "");
            edEndMin.setText(disturb.getEndMinute() + "");
            edStartHour.setText(disturb.getStartHour() + "");
            edStartMin.setText(disturb.getStartMinute() + "");
            onOff = disturb.getOnOFf();
            switchDoNotDisturb.setChecked(disturb.getOnOFf());
        }
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        btnCommit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int startHour = Integer.parseInt(edStartHour.getText().toString());
                int startMin = Integer.parseInt(edStartMin.getText().toString());
                int endHour = Integer.parseInt(edEndHour.getText().toString());
                int endMin = Integer.parseInt(edEndMin.getText().toString());
                DoNotDisturb disturb = new DoNotDisturb(0, onOff, startHour, startMin, endHour, endMin);
                dialog.show();
                ProtocolUtils.getInstance().setDisturbMode(disturb);
            }
        });

        switchDoNotDisturb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                onOff = arg1;
            }
        });
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, arg1, arg2, arg3);
        if (arg1 == ProtocolEvt.SET_CMD_DO_NOT_DISTURB.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    General_Helper.custom_WhiteToast(DoNotDisturbActivity.this, "Do not disturb set up successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
//                    Toast.makeText(, Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void setupActionBar() {
        setTitle("Do Not Disturb");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(false);
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

}
