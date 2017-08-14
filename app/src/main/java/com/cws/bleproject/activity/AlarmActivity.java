package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.config.Constants;
import com.veryfit.multi.entity.Alarm;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator demo
 *         Only to provide a demonstration, only set a group of alarm clock, alarm clock maximum support ten, the developer can set their own, for the convenience of presentation, demo only set one
 *         Each time you add or delete alarm clock must be re-submitted all the alarm clock, or will not set up
 */
public class AlarmActivity extends BaseActivity implements android.widget.CompoundButton.OnCheckedChangeListener {
    private Switch switchAlarm, switch1, switch2, switch3, switch4, switch5, switch6, switch7;
    private RadioGroup rgAlarms;
    private RadioButton rbShuijiao, rbQichuang, rbDuanlian, rbChiyao, rbYuehui, rbJuhui, rbHuiyi, rbZidingyi;
    private Button btnCommit;
    private EditText edMin, edHour;
    private boolean onOff;// Alarm switch

    private int alarmType;// Alarm type
    private ArrayList<Switch> switchs = new ArrayList<Switch>();
    private ArrayList<Alarm> alarms = new ArrayList<Alarm>();
    private boolean[] weeks = new boolean[7];
    private Handler mHandler = new Handler();
    private BufferDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        initData();
        addListener();
        setupActionBar();
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
        rgAlarms = (RadioGroup) findViewById(R.id.rd_type);
        rbChiyao = (RadioButton) findViewById(R.id.chiyao);
        rbShuijiao = (RadioButton) findViewById(R.id.shuijiao);
        rbDuanlian = (RadioButton) findViewById(R.id.duanlian);
        rbHuiyi = (RadioButton) findViewById(R.id.huiyi);
        rbJuhui = (RadioButton) findViewById(R.id.juhui);
        rbQichuang = (RadioButton) findViewById(R.id.qichuang);
        rbYuehui = (RadioButton) findViewById(R.id.yuehui);
        rbZidingyi = (RadioButton) findViewById(R.id.zidingy);
        switchs.add(switch1);
        switchs.add(switch2);
        switchs.add(switch3);
        switchs.add(switch4);
        switchs.add(switch5);
        switchs.add(switch6);
        switchs.add(switch7);
        switchAlarm = (Switch) findViewById(R.id.switch_alarm);
        btnCommit = (Button) findViewById(R.id.btn_alarm_commit);
        edHour = (EditText) findViewById(R.id.ed_alarm_hour);
        edMin = (EditText) findViewById(R.id.ed_alarm_min);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();

        List<Alarm> alarms = ProtocolUtils.getInstance().getAllAlarms();
        for (int i = 0; i < alarms.size(); i++) {
            DebugLog.d("alarm=" + alarms.get(i).toString());
        }

        if (alarms != null && alarms.size() != 0) {
            Alarm alarm = alarms.get(0);// demo只设置一组闹钟
            DebugLog.d("Alarm=" + alarm.toString());
            switchAlarm.setChecked(alarm.isOff_on());
            for (int i = 0; i < 7; i++) {
                switchs.get(i).setChecked(alarm.week[i]);
            }
            weeks = alarm.week;
            onOff = alarm.isOff_on();
            edHour.setText(alarm.getAlarmHour() + "");
            edMin.setText(alarm.getAlarmMinute() + "");
            switch (alarm.getAlarmType()) {
                case Constants.ALARM_TYPE_GETUP:
                    DebugLog.d("type=qichuang");
                    rbQichuang.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_SLEEP:
                    DebugLog.d("type=shuijiao");
                    rbShuijiao.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_EXERCISE:
                    DebugLog.d("type=duanlian");
                    rbDuanlian.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_MEDICINE:
                    DebugLog.d("type=chiyao");
                    rbChiyao.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_ENGAGEMENT:
                    DebugLog.d("type=yuehui");
                    rbYuehui.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_GETTOGETHER:
                    DebugLog.d("type=juhui");
                    rbJuhui.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_MEETIING:
                    DebugLog.d("type=huiyi");
                    rbHuiyi.setChecked(true);
                    break;
                case Constants.ALARM_TYPE_CUSTOMIZE:
                    DebugLog.d("type=zidingy");
                    rbZidingyi.setChecked(true);
                    break;

                default:
                    break;
            }
        }
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
        switchAlarm.setOnCheckedChangeListener(this);
        rgAlarms.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                switch (arg1) {
                    // Alarm type
                    // ALARM_TYPE_GETUP 起床
                    // ALARM_TYPE_SLEEP 睡觉
                    // ALARM_TYPE_EXERCISE 锻炼
                    // ALARM_TYPE_MEDICINE 吃药
                    // ALARM_TYPE_ENGAGEMENT 约会
                    // ALARM_TYPE_GETTOGETHER 聚会
                    // ALARM_TYPE_MEETIING 会议
                    // ALARM_TYPE_CUSTOMIZE 自定义
                    case R.id.qichuang:
                        alarmType = Constants.ALARM_TYPE_GETUP;
                        break;
                    case R.id.shuijiao:
                        alarmType = Constants.ALARM_TYPE_SLEEP;
                        break;
                    case R.id.duanlian:
                        alarmType = Constants.ALARM_TYPE_EXERCISE;
                        break;
                    case R.id.chiyao:
                        alarmType = Constants.ALARM_TYPE_MEDICINE;
                        break;
                    case R.id.yuehui:
                        alarmType = Constants.ALARM_TYPE_ENGAGEMENT;
                        break;
                    case R.id.juhui:
                        alarmType = Constants.ALARM_TYPE_GETTOGETHER;
                        break;
                    case R.id.huiyi:
                        alarmType = Constants.ALARM_TYPE_MEETIING;
                        break;
                    case R.id.zidingy:
                        alarmType = Constants.ALARM_TYPE_CUSTOMIZE;
                        break;

                    default:
                        break;
                }
            }
        });

        btnCommit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Set the alarm clock
                Alarm alarm = new Alarm();
                int hour = Integer.parseInt(edHour.getText().toString());
                int min = Integer.parseInt(edMin.getText().toString());
                alarm.setAlarmHour(hour);
                alarm.setAlarmMinute(min);
                alarm.setAlarmType(alarmType);
                alarm.setOff_on(onOff);
                alarm.setWeek(weeks);
                alarms.add(alarm);
                dialog.show();


                ProtocolUtils.getInstance().setAlarm(alarms);
                for (int i = 0; i < alarms.size(); i++) {
                    DebugLog.d("alarm=" + alarms.get(i).toString());
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
        // TODO Auto-generated method stub
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
            case R.id.switch_alarm:
                onOff = arg1;
                break;
            default:
                break;
        }

    }

    @Override
    public void onSysEvt(int arg0, int action, int isSuccess, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, action, isSuccess, arg3);
        Log.e("Alarm_Action", action + "");
        if (action == ProtocolEvt.SYNC_EVT_ALARM_SYNC_COMPLETE.toIndex() && isSuccess == ProtocolEvt.SUCCESS) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    General_Helper.custom_WhiteToast(AlarmActivity.this, "The alarm was set up successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
//                    Toast.makeText(AlarmActivity.this, "The alarm was set up successfully", Toast.LENGTH_LONG).show();
                }
            }, 200);
        }
    }


    private void setupActionBar() {
        setTitle("Alarm Control");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
