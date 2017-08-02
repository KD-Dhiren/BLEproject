package com.cws.bleproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.activity.BleActivity;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.nativedatabase.Userinfos;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;


public class UserInfosActivity extends BleActivity {
    private Button btnSubmit, btnNan, btnNv;
    private EditText edUserName, edYear, edMonth, edDay, edHerght, edWeight;
    private TextView tvSex;
    private int sex = Constants.MALE;
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfos);
        initView();
        initData();
        addListener();
    }

    public void initView() {
        dialog = new BufferDialog(this);
        btnNan = (Button) findViewById(R.id.btn_nan);
        btnNv = (Button) findViewById(R.id.btn_nv);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        edDay = (EditText) findViewById(R.id.ed_day);
        edHerght = (EditText) findViewById(R.id.ed_height);
        edMonth = (EditText) findViewById(R.id.ed_month);
        edUserName = (EditText) findViewById(R.id.ed_username);
        edWeight = (EditText) findViewById(R.id.ed_weight);
        edYear = (EditText) findViewById(R.id.ed_year);
        tvSex = (TextView) findViewById(R.id.tv_sex);
    }

    public void initData() {
        Userinfos infos = ProtocolUtils.getInstance().getUserinfos();
        DebugLog.d("userinfo=" + infos.toString());
        edUserName.setText(infos.getUserName());
        if (infos.getYear() != 0) {
            edYear.setText(infos.getYear() + "");
        }
        if (infos.getMonth() != 0) {
            edMonth.setText(infos.getMonth() + "");
        }
        if (infos.getDay() != 0) {
            edDay.setText(infos.getDay() + "");
        }
        if (infos.getWeight() != 0) {
            edWeight.setText(infos.getWeight() / 100 + "");
        }
        if (infos.getHeight() != 0) {
            edHerght.setText(infos.getHeight() + "");
        }
        if (infos.getGender() == Constants.MALE) {
            tvSex.setText("male");
        } else {
            tvSex.setText("Female");
        }
    }

    public void addListener() {
        btnNan.setOnClickListener(this);
        btnNv.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_nv:
                sex = Constants.FEMALE;
                tvSex.setText("Female");
                break;
            case R.id.btn_nan:
                sex = Constants.MALE;
                tvSex.setText("male");
                break;
            case R.id.btn_submit:
                String userName = edUserName.getText().toString();
                int year = 0;
                if (edYear.getText().length() != 0) {
                    year = Integer.parseInt(edYear.getText().toString());
                }
                int month = 0;
                if (edMonth.getText().length() != 0) {
                    month = Integer.parseInt(edMonth.getText().toString());
                }
                int day = 0;
                if (edDay.getText().length() != 0) {
                    day = Integer.parseInt(edDay.getText().toString());
                }
                int weight = 0;
                if (edWeight.getText().length() != 0) {
                    weight = Integer.parseInt(edWeight.getText().toString());
                }
                int height = 0;
                if (edHerght.getText().length() != 0) {
                    height = Integer.parseInt(edHerght.getText().toString());
                }
                dialog.show();
                ProtocolUtils.getInstance().setUserinfo(userName, year, month, day, weight * 100, height, sex);//必须乘一百
                break;

            default:
                break;
        }
    }

    @Override
    public void onSysEvt(int evt_base, int evt_type, int error, int value) {
        // TODO Auto-generated method stub
        super.onSysEvt(evt_base, evt_type, error, value);
        if (evt_type == ProtocolEvt.SET_CMD_USER_INFO.toIndex() && error == ProtocolEvt.SUCCESS) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Intent intent = new Intent(UserInfosActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(UserInfosActivity.this, "User information is set up successfully", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
