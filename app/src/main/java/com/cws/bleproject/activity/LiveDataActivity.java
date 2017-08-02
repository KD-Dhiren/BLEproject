package com.cws.bleproject.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.veryfit.multi.nativedatabase.RealTimeHealthData;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")

public class LiveDataActivity extends BaseActivity {
    private TextView tvLiveData;
    private ScrollView scrollView;
    private Handler mHandler = new Handler();
    private StringBuffer livedatSb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livedata);
        initView();
    }

    public void initView() {
        ProtocolUtils.getInstance().setProtocalCallBack(this);
        tvLiveData = (TextView) findViewById(R.id.tv_livedata);
        scrollView = (ScrollView) findViewById(R.id.sv_livedata);
        loadStart();
    }

    public Timer healthTimer;

    public void close() {
        if (healthTimer != null) {
            healthTimer.cancel();
            healthTimer = null;
        }
    }

    // start the timer
    public void loadStart() {
        if (healthTimer != null) {
            healthTimer.cancel();
            healthTimer = null;
        }
        healthTimer = new Timer();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //BLUETOOTH_NOT_OPEN// 蓝牙没打开
                        //DEVICE_NOT_CONNECT// 设备未连接
                        //DEVICE_NO_BLUEETOOTH// 无蓝牙设备
                        //SUCCESS// 一切正常
                        if (ProtocolUtils.getInstance().isAvailable() == ProtocolUtils.SUCCESS) {
                            ProtocolUtils.getInstance().getLiveData();
                        } else {
                            setTitle("The device is not connected");
                        }
                        break;
                }
            }
        };
        healthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 2000);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        close();
    }

    @Override
    public void onLiveData(final RealTimeHealthData arg0) {
        // TODO Auto-generated method stub
        if (arg0 != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    livedatSb.append(arg0.toString() + "\n\n");
                    tvLiveData.setText("Real-time data:\n\n" + livedatSb.toString());
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            }, 200);
        }
    }
}
