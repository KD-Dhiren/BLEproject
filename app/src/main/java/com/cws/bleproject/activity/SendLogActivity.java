package com.cws.bleproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.ble.SendLogListener;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class SendLogActivity extends AppCompatActivity implements OnClickListener, SendLogListener {

	private Button btnSend;
	private BufferDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_log);
		initView();
	}

	public void initView() {
		dialog = new BufferDialog(this);
		btnSend = (Button) findViewById(R.id.btn_send_log);
		btnSend.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_send_log:
			dialog.show();
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ProtocolUtils.getInstance().sendLog(SendLogActivity.this, SendLogActivity.this);//耗时操作（压缩文件）
				}
			});
			thread.start();
			break;

		default:
			break;
		}
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
}
