package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class SosActivity extends BaseActivity {
	private Switch switchSos;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog = new BufferDialog(this);
		switchSos = (Switch) findViewById(R.id.switch_sos);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		// Get the set sos switch
		boolean onOff= ProtocolUtils.getInstance().getSos();
		switchSos.setChecked(onOff);
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		super.addListener();
		switchSos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				//Set a key call
				dialog.show();
				ProtocolUtils.getInstance().setSos(arg1);
			}
		});
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if (arg1 == ProtocolEvt.SET_CMD_ONEKEY_SOS.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(SosActivity.this, "A key call is successful", Toast.LENGTH_LONG).show();
				}
			});
		}else if (arg1 == ProtocolEvt.BLE_TO_APP_ONEKEY_SOS.toIndex()) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(SosActivity.this, "Received an emergency call command, the developer himself in this callback design logic", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}
