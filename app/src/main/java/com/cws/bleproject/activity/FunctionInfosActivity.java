package com.cws.bleproject.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.FunctionInfos;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


@SuppressLint("HandlerLeak")
/**
 * There are two ways to get the function list
 * The first is to get from the bracelet, need to connect with the bracelet app
 * The second is obtained from the database, the database function list is obtained from the ring after the list of functions in the database cache (provided that the acquisition from the function list, or the database will not save this data)
 * @author Administrator
 *
 */
public class FunctionInfosActivity extends BaseActivity{
	private TextView tvData;
	private Button btnFunc,btnFuncDb;
	private Handler mHandler = new Handler();
	private BufferDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_functioninfos);
		initView();
	}
	
	
	public void initView(){
		dialog=new BufferDialog(this);
		tvData=(TextView)findViewById(R.id.tv_data);
		btnFunc=(Button)findViewById(R.id.btn_functioninfos);
		btnFuncDb=(Button)findViewById(R.id.btn_functioninfosbydb);
		btnFuncDb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final FunctionInfos infos= ProtocolUtils.getInstance().getFunctionInfosByDb();//从数据库中获取
				if(infos!=null){
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							tvData.setText("function list:\n\n" + infos.toString());
						}
					}, 200);
				}
			}
		});
		btnFunc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
				ProtocolUtils.getInstance().getFunctionInfos();//Get the acquisition directly from the bracelet
			}
		});
	}
	
	@Override
	public void onFuncTable(final FunctionInfos arg0) {
		// TODO Auto-generated method stub
		super.onFuncTable(arg0);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				tvData.setText("function list:\n\n" + arg0.toString());
			}
		}, 200);
	}

}
