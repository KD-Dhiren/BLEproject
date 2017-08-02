package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativedatabase.AntilostInfos;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class AntilostActivity extends BaseActivity {
	private Switch switchAntiLost;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_antilost);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog = new BufferDialog(this);
		switchAntiLost = (Switch) findViewById(R.id.switch_antilost);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		// Initialize the loss of data
		AntilostInfos infos = ProtocolUtils.getInstance().getAntilostInfos();
		// Anti-throw type
		//LOSE_MODE_NO_ANTI 不防丢  
		//LOSE_MODE_NEAR_ANTI 近距离防丢
		//LOSE_MODE_MID_ANTI 中距离防丢
		//LOSE_MODE_FAR_ANTI 远距离防丢
		switchAntiLost.setChecked(infos.getMode()!= Constants.LOSE_MODE_NO_ANTI);//If the type of anti-lost LOSE_MODE_NO_ANTI that is not anti-lost throw switch is closed, the other type of words is anti-open
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		super.addListener();
		switchAntiLost.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// The data set after each successful setup is saved in the database for interface display.
				dialog.show();
				// Set the anti-lost, passport type LOSE_MODE_NO_ANTI not anti-lost, LOSE_MODE_NEAR_ANTI close anti-lost, LOSE_MODE_MID_ANTI in the distance from the lost, LOSE_MODE_FAR_ANTI long-range anti-lost
				ProtocolUtils.getInstance().setAntilost(arg1? Constants.LOSE_MODE_MID_ANTI: Constants.LOSE_MODE_NO_ANTI);
			}
		});
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if (arg1 == ProtocolEvt.SET_CMD_LOST_FIND.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(AntilostActivity.this, "Anti-lost settings are successful", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}
