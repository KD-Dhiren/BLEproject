package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.HeartRateInterval;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class HeartRateIntervalActivity extends BaseActivity {

	private EditText edBurn, edAerobic, edLimit;
	private Button btnCommit;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heartrateinterval);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog = new BufferDialog(this);
		edBurn = (EditText) findViewById(R.id.ed_burn_fat_threshold);// Fat burning
		edAerobic = (EditText) findViewById(R.id.ed_aerobic_threshold);// Aerobic exercise
		edLimit = (EditText) findViewById(R.id.ed_limit_threshold);// Extreme exercise
		btnCommit = (Button) findViewById(R.id.btn_heartrateinterval_commit);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		HeartRateInterval interval = ProtocolUtils.getInstance().getHeartRateInterval();
		if (interval != null) {
			edAerobic.setText(interval.getAerobicThreshold() + "");
			edBurn.setText(interval.getBurnFatThreshold() + "");
			edLimit.setText(interval.getLimintThreshold() + "");
		}
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		super.addListener();
		btnCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int burn = Integer.parseInt(edBurn.getText().toString());// Fat burning
				int aerobic = Integer.parseInt(edAerobic.getText().toString());// Aerobic exercise
				int limit = Integer.parseInt(edLimit.getText().toString());// Extreme exercise
				HeartRateInterval rateInterval = new HeartRateInterval(0, burn, aerobic, limit, 0);// Just pass three thresholds
																									// ,Fat burning <aerobic exercise <extreme exercise
																									// ,Only devices with heart rate are supported
				dialog.show();
				ProtocolUtils.getInstance().setHeartRateInterval(rateInterval);
			}
		});
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if (arg1 == ProtocolEvt.SET_HEART_RATE_INTERVAL.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(HeartRateIntervalActivity.this, "Heart rate setting is successful", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}
