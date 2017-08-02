package com.cws.bleproject.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cws.bleproject.R;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")
public class DemoTestActivity extends BaseActivity implements OnClickListener {

	private Button btnAppStart, btnAppIng, btnAppEnd, btnBleStart, btnBleIng, btnBleEnd, btnSyncStart, btnSyncStop, btnSyncStatus;
	private Handler handler=new Handler();
	private MyBroadCastReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo_test);
		initView();
		addListener();
		addFilter();
	}

	public void initView() {
		receiver=new MyBroadCastReceiver();
		btnAppStart = (Button) findViewById(R.id.btn_app_start);
		btnAppIng = (Button) findViewById(R.id.btn_app_ing);
		btnAppEnd = (Button) findViewById(R.id.btn_app_end);
		btnBleStart = (Button) findViewById(R.id.btn_ble_start);
		btnBleIng = (Button) findViewById(R.id.btn_ble_ing);
		btnBleEnd = (Button) findViewById(R.id.btn_ble_end);
		btnSyncStart = (Button) findViewById(R.id.btn_sync_start);
		btnSyncStatus = (Button) findViewById(R.id.btn_sync_status);
		btnSyncStop = (Button) findViewById(R.id.btn_sync_stop);
	}

	public void addListener() {
		btnAppEnd.setOnClickListener(this);
		btnAppIng.setOnClickListener(this);
		btnAppStart.setOnClickListener(this);
		btnBleEnd.setOnClickListener(this);
		btnBleIng.setOnClickListener(this);
		btnBleStart.setOnClickListener(this);
		btnSyncStart.setOnClickListener(this);
		btnSyncStatus.setOnClickListener(this);
		btnSyncStop.setOnClickListener(this);
	}
	
	public Timer healthTimer;

	public void close(){
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
					break;
				}
			}
		};
		healthTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(1);
			}
		}, 0, 20000);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_app_start:
			ProtocolUtils.getInstance().setUnConnect();
			Intent intent=new Intent(DemoTestActivity.this,ScanDeviceActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_app_ing:
			ProtocolUtils.getInstance().scanDevices();
			break;
		case R.id.btn_app_end:
			ProtocolUtils.getInstance().connect("C5:0F:C4:3F:B7:B1");
			break;
		case R.id.btn_ble_start:
			break;
		case R.id.btn_ble_ing:
			break;
		case R.id.btn_ble_end:
			break;
		case R.id.btn_sync_start:
			break;
		case R.id.btn_sync_stop:

			break;
		case R.id.btn_sync_status:
			break;

		default:
			break;
		}
	}

	public void addFilter(){
		IntentFilter filter=new IntentFilter(Constants.BLUEE_TOOTH_STATUS_CONNECT);
		registerReceiver(receiver,filter);
	}

	class  MyBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Constants.BLUEE_TOOTH_STATUS_CONNECT)){
				//ProtocolUtils.getInstance().scanDevices();
			}
		}
	}
	
}
