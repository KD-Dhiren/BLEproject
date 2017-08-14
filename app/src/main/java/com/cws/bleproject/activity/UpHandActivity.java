package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.UpHandGestrue;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class UpHandActivity extends BaseActivity {
    LinearLayout ll_up_hand;
    private Switch switchUpHand;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;
    private boolean isHandsUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uphand);
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
        switchUpHand = (Switch) findViewById(R.id.switch_uphand);
        ll_up_hand = (LinearLayout) findViewById(R.id.ll_up_hand);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        // Initialize the wrist recognition intervalList, get the set up the bowl intervalList
        UpHandGestrue gestrue = ProtocolUtils.getInstance().getUpHandGestrue();
        switchUpHand.setChecked(gestrue.getOnOff());
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        switchUpHand.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // The intervalList set after each successful setup is saved in the database for interface display.
                // Set gesture recognition
                dialog.show();
                ProtocolUtils.getInstance().setUPHandGestrue(arg1, 5);// Parameter 1 is the switch, parameter 2 is the number of seconds displayed
            }
        });

        ll_up_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHandsUp) {
                    isHandsUp = false;
                    switchUpHand.setChecked(false);
                } else {
                    isHandsUp = true;
                    switchUpHand.setChecked(true);
                }
            }
        });
    }

    @Override
    public void onSysEvt(int arg0, int action, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, action, arg2, arg3);
        if (action == ProtocolEvt.SET_UP_HAND_GESTURE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    General_Helper.custom_WhiteToast(UpHandActivity.this, "The wrist is set up successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void setupActionBar() {
        setTitle("Up Hand Gesture");
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
