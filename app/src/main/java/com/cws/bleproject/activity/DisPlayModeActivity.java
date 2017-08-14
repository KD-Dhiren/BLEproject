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
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class DisPlayModeActivity extends BaseActivity {
    public static boolean dis_mode = false;
    LinearLayout ll_change_mode;
    TextView txt_display_mode;
    private Switch switchDisPlay;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_mode);
        initView();
        initData();
        setupActionBar();
        addListener();
    }

    private void setupActionBar() {
        setTitle("Device Display Mode");
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

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        switchDisPlay = (Switch) findViewById(R.id.switch_display);
        txt_display_mode = (TextView) findViewById(R.id.txt_display_mode);
        ll_change_mode = (LinearLayout) findViewById(R.id.ll_change_mode);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        // Initialize the display mode intervalList
        int mode = ProtocolUtils.getInstance().getDisplayMode();
        dis_mode = (mode == Constants.HORIZONTAL_SCREEN_MODE ? true : false);
        switchDisPlay.setChecked(dis_mode);

        DisplayMode();
    }

    void DisplayMode() {
        if (ProtocolUtils.getInstance().getDisplayMode() == Constants.HORIZONTAL_SCREEN_MODE) {
            txt_display_mode.setText("Current mode : HORIZONTAL");
        }

        if (ProtocolUtils.getInstance().getDisplayMode() == Constants.VERTICAL_SCREEN_MODE) {
            txt_display_mode.setText("Current mode : VERTICAL");
        }
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        switchDisPlay.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isHorizontal) {
                // The intervalList set after each successful setup is saved in the database for interface display.
                dialog.show();
                ProtocolUtils.getInstance().setDisplayMode(isHorizontal ? Constants.HORIZONTAL_SCREEN_MODE : Constants.VERTICAL_SCREEN_MODE);//
                DisplayMode();
            }
        });

        ll_change_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dis_mode) {
                    dis_mode = false;
                    switchDisPlay.setChecked(true);
                } else {
                    dis_mode = true;
                    switchDisPlay.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, arg1, arg2, arg3);
        if (arg1 == ProtocolEvt.SET_CMD_DISPLAY_MODE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    General_Helper.custom_WhiteToast(DisPlayModeActivity.this, "Display setting is successful", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
//                    Toast.makeText(, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
