package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


public class SetGoalActivity extends BaseActivity {

    private EditText edSportGoal;
    private Button btnCommit;
    private Handler handler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        initView();
        initData();
        addListener();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        edSportGoal = (EditText) findViewById(R.id.ed_goal_sport);
        btnCommit = (Button) findViewById(R.id.btn_sport_goal_commit);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
        if (ProtocolUtils.getInstance().getSportGoal() != 0) {
            edSportGoal.setText(ProtocolUtils.getInstance().getSportGoal() + "");
        }
    }

    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        btnCommit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                dialog.show();
                //设置运动目标
                ProtocolUtils.getInstance().setSportgoal(Integer.parseInt(edSportGoal.getText().toString()));
            }
        });
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, arg1, arg2, arg3);
        if (arg1 == ProtocolEvt.SET_CMD_SPORT_GOAL.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    General_Helper.custom_WhiteToast(SetGoalActivity.this, "Set up successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
                }
            });
        }
    }
}
