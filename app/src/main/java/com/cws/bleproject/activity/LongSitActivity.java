package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.entity.LongSit;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


import java.util.ArrayList;

public class LongSitActivity extends BaseActivity implements OnCheckedChangeListener {
	private Switch switchLongSit, switch1, switch2, switch3, switch4, switch5, switch6, switch7;
	private Button btnCommit;
	private EditText edLenth, edStartHour, edStartMin, edEndHour, edEndMin;
	private int startHour, startMinute, endHour, endMinute, lenth;
	private boolean onOff;
	private ArrayList<Switch> switchs = new ArrayList<Switch>();
	private boolean[] weeks;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_longsit);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog = new BufferDialog(this);
		switch1 = (Switch) findViewById(R.id.switch_1);
		switch2 = (Switch) findViewById(R.id.switch_2);
		switch3 = (Switch) findViewById(R.id.switch_3);
		switch4 = (Switch) findViewById(R.id.switch_4);
		switch5 = (Switch) findViewById(R.id.switch_5);
		switch6 = (Switch) findViewById(R.id.switch_6);
		switch7 = (Switch) findViewById(R.id.switch_7);
		switchs.add(switch1);
		switchs.add(switch2);
		switchs.add(switch3);
		switchs.add(switch4);
		switchs.add(switch5);
		switchs.add(switch6);
		switchs.add(switch7);
		switchLongSit = (Switch) findViewById(R.id.switch_longsit);
		btnCommit = (Button) findViewById(R.id.btn_longsit_commit);
		edEndHour = (EditText) findViewById(R.id.ed_longsit_endhour);
		edEndMin = (EditText) findViewById(R.id.ed_longsit_endmin);
		edStartHour = (EditText) findViewById(R.id.ed_longsit_starthour);
		edStartMin = (EditText) findViewById(R.id.ed_longsit_startmin);
		edLenth = (EditText) findViewById(R.id.ed_longsit_min);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		LongSit longSit = ProtocolUtils.getInstance().getLongSit();
		for (int i = 0; i < 7; i++) {
			switchs.get(i).setChecked(longSit.weeks[i]);
		}
		switchLongSit.setChecked(longSit.isOnOff());
		edStartHour.setText(longSit.getStartHour() + "");
		edStartMin.setText(longSit.getStartMinute() + "");
		edEndHour.setText(longSit.getEndHour() + "");
		edEndMin.setText(longSit.getEndMinute() + "");
		edLenth.setText(longSit.getInterval() + "");
		weeks = longSit.weeks;
		lenth = longSit.getInterval();
		onOff = longSit.isOnOff();
		startHour = longSit.getStartHour();
		startMinute = longSit.getStartMinute();
		endHour = longSit.getEndHour();
		endMinute = longSit.getEndMinute();
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		super.addListener();
		switch1.setOnCheckedChangeListener(this);
		switch2.setOnCheckedChangeListener(this);
		switch3.setOnCheckedChangeListener(this);
		switch4.setOnCheckedChangeListener(this);
		switch5.setOnCheckedChangeListener(this);
		switch6.setOnCheckedChangeListener(this);
		switch7.setOnCheckedChangeListener(this);
		switchLongSit.setOnCheckedChangeListener(this);
		btnCommit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startHour = Integer.parseInt(edStartHour.getText().toString());
				startMinute = Integer.parseInt(edStartMin.getText().toString());
				endHour = Integer.parseInt(edEndHour.getText().toString());
				endMinute = Integer.parseInt(edEndMin.getText().toString());
				lenth = Integer.parseInt(edLenth.getText().toString());
				dialog.show();
				ProtocolUtils.getInstance().setLongsit(startHour, startMinute, endHour, endMinute, lenth, onOff, weeks);
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.switch_1:
			weeks[0] = arg1;
			break;
		case R.id.switch_2:
			weeks[1] = arg1;
			break;
		case R.id.switch_3:
			weeks[2] = arg1;
			break;
		case R.id.switch_4:
			weeks[3] = arg1;
			break;
		case R.id.switch_5:
			weeks[4] = arg1;
			break;
		case R.id.switch_6:
			weeks[5] = arg1;
			break;
		case R.id.switch_7:
			weeks[6] = arg1;
			break;
		case R.id.switch_longsit:
			onOff = arg1;
			break;

		default:
			break;
		}
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if (arg1 == ProtocolEvt.SET_CMD_LONG_SIT.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(LongSitActivity.this, "Sedentary set up successfully", Toast.LENGTH_LONG).show();
				}
			}, 200);
		}
	}
}
