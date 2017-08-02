package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.UpHandGestrue;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class UpHandActivity extends BaseActivity {
	private Switch switchUpHand;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uphand);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog = new BufferDialog(this);
		switchUpHand = (Switch) findViewById(R.id.switch_uphand);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		// Initialize the wrist recognition data, get the set up the bowl data
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
				// The data set after each successful setup is saved in the database for interface display.
				// Set gesture recognition
				dialog.show();
				ProtocolUtils.getInstance().setUPHandGestrue(arg1, 5);// Parameter 1 is the switch, parameter 2 is the number of seconds displayed
			}
		});
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if (arg1 == ProtocolEvt.SET_UP_HAND_GESTURE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(UpHandActivity.this, "The wrist is set up successfully", Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}
