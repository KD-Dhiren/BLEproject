package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.google.gson.Gson;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativedatabase.NoticeOnOff;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class CallAndMsgActivity extends BaseActivity implements OnCheckedChangeListener,View.OnClickListener {

	private Switch switchCall, switchMsg, switchFicebook, switchMessenger, switchWhatsApp, switchTwitter, switchWx, switchInstagram, switchLinkedin, switchQq;
	private Button btnCall, btnMsg, btnFace, btnMess, btnWhat, btnTwit, btnWx, btnInst, btnLink, btnQq;
	private Handler mHandler=new Handler();

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callandmsg);
		initView();
		initData();
		addListener();
	}

	public void initView() {
		btnCall = (Button) findViewById(R.id.btn_call);
		btnMsg = (Button) findViewById(R.id.btn_msg);
		btnFace = (Button) findViewById(R.id.btn_face);
		btnMess = (Button) findViewById(R.id.btn_mess);
		btnWhat = (Button) findViewById(R.id.btn_what);
		btnTwit = (Button) findViewById(R.id.btn_twit);
		btnWx = (Button) findViewById(R.id.btn_wx);
		btnInst = (Button) findViewById(R.id.btn_inst);
		btnLink = (Button) findViewById(R.id.btn_link);
		btnQq = (Button) findViewById(R.id.btn_qq);
		switchCall = (Switch) findViewById(R.id.switch_call_onoff);
		switchMsg = (Switch) findViewById(R.id.switch_msg_onoff);
		switchFicebook = (Switch) findViewById(R.id.switch_face_onoff);
		switchMessenger = (Switch) findViewById(R.id.switch_messenger_onoff);
		switchWhatsApp = (Switch) findViewById(R.id.switch_whatsapp_onoff);
		switchTwitter = (Switch) findViewById(R.id.switch_twitter_onoff);
		switchWx = (Switch) findViewById(R.id.switch_wx_onoff);
		switchInstagram = (Switch) findViewById(R.id.switch_inst_onoff);
		switchLinkedin = (Switch) findViewById(R.id.switch_linkedin_onoff);
		switchQq = (Switch) findViewById(R.id.switch_qq_onoff);
	}
	
	private Gson gson=new Gson();
	
	public void initData() {
		// Initialize the intelligent reminder message switch
		NoticeOnOff onOff = ProtocolUtils.getInstance().getNotice();
		switchCall.setChecked(onOff.getCallonOff());
		switchMsg.setChecked(onOff.getMsgonOff());
		switchFicebook.setChecked(onOff.getFacebookonOff());
		switchInstagram.setChecked(onOff.getInstagramonOff());
		switchLinkedin.setChecked(onOff.getLinkedinonOff());
		switchMessenger.setChecked(onOff.getMessengeronOff());
		switchQq.setChecked(onOff.getQQonOff());
		switchTwitter.setChecked(onOff.getTwitteronOff());
		switchWhatsApp.setChecked(onOff.getWhatsapponOff());
		switchWx.setChecked(onOff.getWxonOff());
	}

	public void addListener() {
		btnCall.setOnClickListener(this);
		btnMsg.setOnClickListener(this);
		btnFace.setOnClickListener(this);
		btnInst.setOnClickListener(this);
		btnLink.setOnClickListener(this);
		btnMess.setOnClickListener(this);
		btnQq.setOnClickListener(this);
		btnTwit.setOnClickListener(this);
		btnWhat.setOnClickListener(this);
		btnWx.setOnClickListener(this);
		switchCall.setOnCheckedChangeListener(this);
		switchMsg.setOnCheckedChangeListener(this);
		switchFicebook.setOnCheckedChangeListener(this);
		switchInstagram.setOnCheckedChangeListener(this);
		switchLinkedin.setOnCheckedChangeListener(this);
		switchMessenger.setOnCheckedChangeListener(this);
		switchQq.setOnCheckedChangeListener(this);
		switchTwitter.setOnCheckedChangeListener(this);
		switchWhatsApp.setOnCheckedChangeListener(this);
		switchWx.setOnCheckedChangeListener(this);
	}

	@Override
	public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.onSysEvt(arg0, arg1, arg2, arg3);
		if(arg1== ProtocolEvt.SET_NOTICE_MSG.toIndex()){
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(CallAndMsgActivity.this, "The message was sent successfully", Toast.LENGTH_LONG).show();
				}
			}, 200);
		}else if (arg1== ProtocolEvt.SET_NOTICE_CALL.toIndex()) {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(CallAndMsgActivity.this, "The phone was sent successfully", Toast.LENGTH_LONG).show();
				}
			}, 200);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.switch_call_onoff:
			NoticeOnOff onOff = ProtocolUtils.getInstance().getNotice();
			onOff.setCallonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff);
			break;
		case R.id.switch_msg_onoff:
			NoticeOnOff onOff1 = ProtocolUtils.getInstance().getNotice();
			onOff1.setMsgonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff1);
			break;
		case R.id.switch_face_onoff:
			NoticeOnOff onOff2 = ProtocolUtils.getInstance().getNotice();
			onOff2.setFacebookonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff2);
			break;
		case R.id.switch_inst_onoff:
			NoticeOnOff onOff3 = ProtocolUtils.getInstance().getNotice();
			onOff3.setInstagramonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff3);
			break;
		case R.id.switch_linkedin_onoff:
			NoticeOnOff onOff4 = ProtocolUtils.getInstance().getNotice();
			onOff4.setLinkedinonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff4);
			break;
		case R.id.switch_messenger_onoff:
			NoticeOnOff onOff5 = ProtocolUtils.getInstance().getNotice();
			onOff5.setMessengeronOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff5);
			break;
		case R.id.switch_qq_onoff:
			NoticeOnOff onOff6 = ProtocolUtils.getInstance().getNotice();
			onOff6.setQQonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff6);
			break;
		case R.id.switch_twitter_onoff:
			NoticeOnOff onOff7 = ProtocolUtils.getInstance().getNotice();
			onOff7.setTwitteronOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff7);
			break;
		case R.id.switch_whatsapp_onoff:
			NoticeOnOff onOff8 = ProtocolUtils.getInstance().getNotice();
			onOff8.setWhatsapponOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff8);
			break;
		case R.id.switch_wx_onoff:
			NoticeOnOff onOff9 = ProtocolUtils.getInstance().getNotice();
			onOff9.setWxonOff(arg1);
			ProtocolUtils.getInstance().addNotice(onOff9);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_call:
			if (ProtocolUtils.getInstance().getNotice().getCallonOff()) {
				ProtocolUtils.getInstance().setCallEvt("xt", "13252270345");
			}
			break;
		case R.id.btn_msg:
			if (ProtocolUtils.getInstance().getNotice().getMsgonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_MSG, "xt", "13202270345", "Hello there");
			}
			break;
		case R.id.btn_face:
			if (ProtocolUtils.getInstance().getNotice().getFacebookonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_FACEBOOK, "xt", "", "MSG_TYPE_FACEBOOK");
			}
			break;
		case R.id.btn_mess:
			if (ProtocolUtils.getInstance().getNotice().getMessengeronOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_MESSENGER, "xt", "", "MSG_TYPE_MESSENGER");
			}
			break;
		case R.id.btn_what:
			if (ProtocolUtils.getInstance().getNotice().getWhatsapponOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_WHATSAPP, "xt", "", "MSG_TYPE_WHATSAPP");
			}
			break;
		case R.id.btn_twit:
			if (ProtocolUtils.getInstance().getNotice().getTwitteronOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_TWITTER, "xt", "", "MSG_TYPE_TWITTER");
			}
			break;
		case R.id.btn_wx:
			if (ProtocolUtils.getInstance().getNotice().getWxonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_WX, "xt", "", "MSG_TYPE_WX");
			}
			break;
		case R.id.btn_inst:
			if (ProtocolUtils.getInstance().getNotice().getInstagramonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_INSTAGRAM, "xt", "", "MSG_TYPE_INSTAGRAM");
			}
			break; 
		case R.id.btn_link:
			if (ProtocolUtils.getInstance().getNotice().getLinkedinonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_LINKEDIN, "xt", "", "MSG_TYPE_LINKEDIN");
			}
			break;
		case R.id.btn_qq:
			if (ProtocolUtils.getInstance().getNotice().getQQonOff()) {
				ProtocolUtils.getInstance().setSmsEvt(Constants.MSG_TYPE_QQ, "xt", "88888888", "Haha ha ha ha ha ha ha ha ha ha ha ha ha");
			}
			break;

		default:
			break;
		}
	}
}
