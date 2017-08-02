package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class MusicActivity extends BaseActivity implements OnCheckedChangeListener {
	private Switch switchOnOff,switchStartOrStop;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;
	private TextView tvReceive;
	private StringBuffer receiveSb=new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);
		initView();
		initData();
		addListener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		dialog=new BufferDialog(this);
		switchOnOff=(Switch)findViewById(R.id.switch_music_onoff);
		switchStartOrStop=(Switch)findViewById(R.id.switch_music_startorend);
		tvReceive=(TextView)findViewById(R.id.tv_receive);
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		switchOnOff.setChecked(ProtocolUtils.getInstance().getMusicOnoff());
		
	}
	
	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		super.addListener();
		switchOnOff.setOnCheckedChangeListener(this);
		switchStartOrStop.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.switch_music_onoff:
			dialog.show();
			ProtocolUtils.getInstance().setMusicOnoff(arg1);//Set the music control switch
			break;
		case R.id.switch_music_startorend:
			//App controls the start and end of the bracelet music
			if(arg1){
				dialog.show();
				ProtocolUtils.getInstance().startMusic();
			}else {
				dialog.show();
				ProtocolUtils.getInstance().stopMusic();
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3); 
		if(arg1== ProtocolEvt.SET_CMD_MUSIC_ONOFF.toIndex()&&arg2== ProtocolEvt.SUCCESS){
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(MusicActivity.this, "Set up successfully", Toast.LENGTH_LONG).show();
				}
			});
		}else if (arg1== ProtocolEvt.APP_TO_BLE_MUSIC_START.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(MusicActivity.this, "App control brace music starts to set up successfully", Toast.LENGTH_LONG).show();
				}
			});
		}else if (arg1== ProtocolEvt.APP_TO_BLE_MUSIC_STOP.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(MusicActivity.this, "App control bracelet music ending set up successfully", Toast.LENGTH_LONG).show();
				}
			});
		}else if (arg1== ProtocolEvt.BLE_TO_APP_MUSIC_LAST.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					receiveSb.append("Received a previous command, the developer can handle the corresponding logic in this callback"+"\n\n");
					tvReceive.setText(receiveSb.toString());
				}
			});
		}else if (arg1== ProtocolEvt.BLE_TO_APP_MUSIC_NEXT.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					receiveSb.append("Received the next command, the developer can handle the corresponding logic in this callback"+"\n\n");
					tvReceive.setText(receiveSb.toString());
				}
			});
		}else if (arg1== ProtocolEvt.BLE_TO_APP_MUSIC_PAUSE.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					receiveSb.append("Upon receipt of a pause command, the developer can handle the corresponding logic in this callback"+"\n\n");
					tvReceive.setText(receiveSb.toString());
				}
			});
		}else if (arg1== ProtocolEvt.BLE_TO_APP_MUSIC_START.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					receiveSb.append("Received a music start command, the developer can handle the corresponding logic in this callback"+"\n\n");
					tvReceive.setText(receiveSb.toString());
				}
			});
		}else if (arg1== ProtocolEvt.BLE_TO_APP_MUSIC_STOP.toIndex()&&arg2== ProtocolEvt.SUCCESS) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					receiveSb.append("Received a music stop command, the developer can handle the corresponding logic in this callback"+"\n\n");
					tvReceive.setText(receiveSb.toString());
				}
			});
		}
	}
}
