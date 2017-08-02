package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativedatabase.Units;
import com.veryfit.multi.nativedatabase.Userinfos;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class UnitActivity extends BaseActivity implements OnCheckedChangeListener {
    private Switch switchTime, switchLanguage, switchGongyingzhi;
    private int gongyingzhi, timemode, language;
    private Button btnCommit;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        initView();
        initData();
        addListener();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        switchGongyingzhi = (Switch) findViewById(R.id.switch_unit_gongyingzhi);
        switchLanguage = (Switch) findViewById(R.id.switch_unit_language);
        switchTime = (Switch) findViewById(R.id.switch_unit_time);
        btnCommit = (Button) findViewById(R.id.btn_unit_commit);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        Units units = ProtocolUtils.getInstance().getUnits();
        if (units != null) {
            //公制 METRIC
            //英制 BRITISH
            gongyingzhi = units.getMode();
            switchGongyingzhi.setChecked(units.getMode() == Constants.METRIC);
            //LANGUAGE_ZH 中文
            //LANGUAGE_EN 英文
            language = units.getLanguage();
            switchLanguage.setChecked(units.getLanguage() == Constants.LANGUAGE_ZH);
            //TIME_MODE_24 24小时制
            //TIME_MODE_12 12小时制
            timemode = units.getTimeMode();
            switchTime.setChecked(units.getTimeMode() == Constants.TIME_MODE_24);
        }
    }


    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        switchGongyingzhi.setOnCheckedChangeListener(this);
        switchLanguage.setOnCheckedChangeListener(this);
        switchTime.setOnCheckedChangeListener(this);
        btnCommit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Userinfos info = ProtocolUtils.getInstance().getUserinfos();
                int stride = 0;//The step size is based on height and gender
                if (info.getGender() == Constants.FEMALE) {
                    stride = (int) (0.413 * info.getHeight());
                } else {
                    stride = (int) (0.415 * info.getHeight());
                }
                dialog.show();
                ProtocolUtils.getInstance().setUint(gongyingzhi, stride, language, timemode);//公英制，步长单位，语言，时间制式
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.switch_unit_gongyingzhi:
                gongyingzhi = arg1 ? Constants.METRIC : Constants.BRITISH;
                break;
            case R.id.switch_unit_language:
                language = arg1 ? Constants.LANGUAGE_ZH : Constants.LANGUAGE_EN;
                break;
            case R.id.switch_unit_time:
                timemode = arg1 ? Constants.TIME_MODE_24 : Constants.TIME_MODE_12;
                break;

            default:
                break;
        }
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, arg1, arg2, arg3);
        if (arg1 == ProtocolEvt.SET_CMD_UINT.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(UnitActivity.this, "The unit is set up successfully", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
