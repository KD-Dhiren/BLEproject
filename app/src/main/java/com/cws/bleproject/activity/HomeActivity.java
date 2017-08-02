package com.cws.bleproject.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.Helper.SP_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.model.ActionModel;
import com.cws.bleproject.model.DialogUtil;
import com.cws.bleproject.model.UpdateModel;
import com.cws.bleproject.service.BLE_ACTION;
import com.cws.bleproject.service.ResetHeartBeatService_at_12am;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativedatabase.RealTimeHealthData;
import com.veryfit.multi.nativeprotocol.Protocol;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity implements OnClickListener {
    private Button btnSyncConfig, btnSyncHealth, btnSport, btnSleep, btnHeart, btnDeviceInfo, btnCallAndMsg,
            btnWearMode, btnUpHand, btnLost, btnFindPhone, btnLongSit, btnUnDisturb, btnDisPlay,
            btnHeartInterval, btnHeartMode, btnPhoto, btnMusic, btnAlarm, btnGoal, btnUserInfo,
            btnUnit, btnRestart, btnLiveData, btnSos, btnUnBind, btnTest, btnFuncInfos,
            btnSendLog, btnUnConnect, btnConnect, btnCommission;
    private BufferDialog dialog;
    ActionAdapter adapter;

    private Handler handler = new Handler();

    private static final int REQUEST_ENABLE_BT = 2;
    private static boolean btAsked = false;
    TextView txt_step, txt_cal, txt_distance, txt_active_time, txt_hrt_beat, txt_connection_status;

    GridView layout_grid;
    private ArrayList<ActionModel> actinList;
    public Timer healthTimer;
    ImageView iv_hrt_beat, iv_step, iv_calorie, iv_distance, iv_active_time;
    LinearLayout ll_data_line2, ll_data_line1, ll_all_data;
    RelativeLayout rl_disable_hover;

    AnimatorSet animatorSet = new AnimatorSet();
    Animation zoomIn, zoomOut;
    SP_Helper sp_helper;
    public static String currentHrtRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ProtocolUtils.getInstance().setCanConnect(true);
        sp_helper = new SP_Helper(this);
        currentHrtRate = sp_helper.getHeartRate();
        enableBluetooth();
        initView();
        startServiceToUpdateData();
//        addListener();
    }

    private void startServiceToUpdateData() {
        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        Intent intent = new Intent(this, ResetHeartBeatService_at_12am.class);

        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);

//        pintent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60000, pintent);
        Log.e("Service_started", "will_call_soon");
    }


    private void FlipAnimation(View target) {

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(target, "rotationX", 90, -15, 15, 0),
                ObjectAnimator.ofFloat(target, "alpha", 0.25f, 0.5f, 0.75f, 1)
        );
        animatorSet.start();
    }

    private void heartBeatAnimation(boolean cancelHeartBeatAnim) {
        if (cancelHeartBeatAnim)
            iv_hrt_beat.clearAnimation();
        else {
            zoomOut = AnimationUtils.loadAnimation(this, R.anim.dialog_anim_zoom_out);
            zoomIn = AnimationUtils.loadAnimation(this, R.anim.dialog_anim_zoom_in);

            iv_hrt_beat.startAnimation(zoomIn);

            zoomIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    iv_hrt_beat.startAnimation(zoomOut);
                }

            });

            zoomOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    iv_hrt_beat.startAnimation(zoomIn);
                }

            });
        }
    }

    private void ImagesAnimation() {
        /*FlipAnimation(iv_step);
        FlipAnimation(iv_calorie);
        FlipAnimation(iv_distance);
        FlipAnimation(iv_active_time);*/
        //FlipAnimation(ll_data_line1);
        FlipAnimation(ll_all_data);

    }

    private void hoverFadeAnim(View target, boolean ForfadeIn) {
     /*   Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        if (ForfadeIn) {
            animation.addAnimation(fadeIn);
        } else
            animation.addAnimation(fadeOut);
        target.setAnimation(animation);*/
    }


    public void initView() {
        ProtocolUtils.getInstance().setProtocalCallBack(this);
        dialog = new BufferDialog(HomeActivity.this);
        layout_grid = (GridView) findViewById(R.id.layout_grid);
        txt_step = (TextView) findViewById(R.id.txt_step);
        txt_cal = (TextView) findViewById(R.id.txt_cal);
        txt_distance = (TextView) findViewById(R.id.txt_distance);
        txt_active_time = (TextView) findViewById(R.id.txt_active_time);
        txt_hrt_beat = (TextView) findViewById(R.id.txt_hrt_beat);
        txt_connection_status = (TextView) findViewById(R.id.txt_connection_status);
        iv_hrt_beat = (ImageView) findViewById(R.id.iv_hrt_beat);
        iv_step = (ImageView) findViewById(R.id.iv_step);
        iv_calorie = (ImageView) findViewById(R.id.iv_calorie);
        iv_distance = (ImageView) findViewById(R.id.iv_distance);
        iv_active_time = (ImageView) findViewById(R.id.iv_active_time);
        ll_data_line1 = (LinearLayout) findViewById(R.id.ll_data_line1);
        ll_data_line2 = (LinearLayout) findViewById(R.id.ll_data_line2);
        ll_all_data = (LinearLayout) findViewById(R.id.ll_all_data);
        rl_disable_hover = (RelativeLayout) findViewById(R.id.rl_disable_hover);

        ImagesAnimation();

        txt_connection_status.setSelected(true);
        loadStart();
        actinList = new ArrayList<>();
        /*
        Disconnect
Reconnect
Bind-UnBind
Display Mode-(Vr Hr)
Reboot
BLE Device Info


        Alarm
Music Control (not tested)
Camera Control
Call Control
Alarm Clock
User Info
Send Log Info

Sync Health Info
Call Reminders (Simulate Phone, TextMessage,Facebook,Whatsapp,Twitter,Insta,LinkedIn,)
Anti-Lost Reminder (not tested)
Emergency Help (not tested)
Looking for a Cell Phone (not tested)
Do Not Disturb Mode (not tested)

         */
        actinList.add(new ActionModel("Unbind", BLE_ACTION.ACTION_UNBIND));
        actinList.add(new ActionModel("Disconnect", BLE_ACTION.ACTION_DISCONNECT));
        actinList.add(new ActionModel("Re-Connect", BLE_ACTION.ACTION_RECONNECT));
        actinList.add(new ActionModel("Display-Mode", BLE_ACTION.ACTION_DISPLAYMODE));
        actinList.add(new ActionModel("Reboot", BLE_ACTION.ACTION_REBOOT));
        actinList.add(new ActionModel("BLE device Info", BLE_ACTION.ACTION_DEVICE_INFO));
        actinList.add(new ActionModel("Alarm", BLE_ACTION.ACTION_ALARM));
        actinList.add(new ActionModel("Music Control", BLE_ACTION.ACTION_MUSIC));
        actinList.add(new ActionModel("Camera Control", BLE_ACTION.ACTION_CAMERA));
        actinList.add(new ActionModel("UserInfo", BLE_ACTION.ACTION_USER_INFO));
        actinList.add(new ActionModel("SendLog Info", BLE_ACTION.ACTION_SEND_LOG));
        actinList.add(new ActionModel("Sync Health Info", BLE_ACTION.ACTION_SYNC_HEALTH_INFO));
        actinList.add(new ActionModel("Call Reminders", BLE_ACTION.ACTION_CALL_REMINDERS));
        actinList.add(new ActionModel("Anti Lost Reminders", BLE_ACTION.ACTION_ANTI_LOST_REMINDERS));
        actinList.add(new ActionModel("Emergency Help", BLE_ACTION.ACTION_EMERGENCY_HELP));
        actinList.add(new ActionModel("Looking for a CellPhone", BLE_ACTION.ACTION_LOOKING_CELLPHONE));
        actinList.add(new ActionModel("Do Not Disturb mode", BLE_ACTION.ACTION_DO_NOT_DISTURB_MODE));

        adapter = new ActionAdapter(this, actinList);
        layout_grid.setAdapter(adapter);

        /*btnAlarm = (Button) findViewById(R.id.btn_alarm);
        btnCallAndMsg = (Button) findViewById(R.id.btn_call_msg);
        btnDeviceInfo = (Button) findViewById(R.id.btn_device_info);
        btnDisPlay = (Button) findViewById(R.id.btn_display_mode);
        btnFindPhone = (Button) findViewById(R.id.btn_find_phone);
        btnGoal = (Button) findViewById(R.id.btn_goal);
        btnHeart = (Button) findViewById(R.id.btn_heart_data);
        btnHeartInterval = (Button) findViewById(R.id.btn_heart_Interval);
        btnHeartMode = (Button) findViewById(R.id.btn_heart_mode);
        btnLongSit = (Button) findViewById(R.id.btn_long_sit);
        btnLost = (Button) findViewById(R.id.btn_lost);
        btnMusic = (Button) findViewById(R.id.btn_music);
        btnPhoto = (Button) findViewById(R.id.btn_photo);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        btnSleep = (Button) findViewById(R.id.btn_sleep_data);
        btnSport = (Button) findViewById(R.id.btn_sport_data);
        btnSyncConfig = (Button) findViewById(R.id.btn_sync_config);
        btnSyncHealth = (Button) findViewById(R.id.btn_sync_health);
        btnUnDisturb = (Button) findViewById(R.id.btn_undisturb_mode);
        btnUnit = (Button) findViewById(R.id.btn_unit);
        btnSendLog = (Button) findViewById(R.id.btn_sendlog);
        btnUpHand = (Button) findViewById(R.id.btn_up_hand);
        btnUserInfo = (Button) findViewById(R.id.btn_userinfo);
        btnWearMode = (Button) findViewById(R.id.btn_wear_mode);
        btnLiveData = (Button) findViewById(R.id.btn_live_data);
        btnSos = (Button) findViewById(R.id.btn_sos);
        btnUnBind = (Button) findViewById(R.id.btn_unbind);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnFuncInfos = (Button) findViewById(R.id.btn_function_infos);
        btnUnConnect = (Button) findViewById(R.id.btn_unconnect);
        btnConnect = (Button) findViewById(R.id.btn_connect);
        btnCommission = (Button) findViewById(R.id.btn_commission);*/
    }

    // start the timer
    public void loadStart() {
        if (healthTimer != null) {
            healthTimer.cancel();
            healthTimer = null;
        }
        healthTimer = new Timer();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //BLUETOOTH_NOT_OPEN//
                        //DEVICE_NOT_CONNECT//
                        //DEVICE_NO_BLUEETOOTH//
                        //SUCCESS//
                        if (ProtocolUtils.getInstance().isAvailable() == ProtocolUtils.SUCCESS) {
                            ProtocolUtils.getInstance().getLiveData();
                        } else {
                            txt_connection_status.setText("status : The device is not connected.");
                            layout_grid.setEnabled(false);
                            hoverFadeAnim(rl_disable_hover, true);
                            rl_disable_hover.setVisibility(View.VISIBLE);
                            //setTitle("The device is not connected");
                        }
                        break;
                }
            }
        };
        healthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 2000);
    }

    public void close() {
        if (healthTimer != null) {
            healthTimer.cancel();
            healthTimer = null;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_ENABLE_BT) {
                //Bluetooth allowed
            }

        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (btAsked)
                Toast.makeText(HomeActivity.this, "You twice denied to access Bluetooth. App may Exit in 2 sec..", Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(HomeActivity.this, "You just denied to access Bluetooth. please allow it again..", Toast.LENGTH_LONG).show();

            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (btAsked) {
                        finish();
                        btAsked = false;
                    } else {
                        enableBluetooth();
                        btAsked = true;
                    }
                }
            }, 2000);
        }
    }

    private void enableBluetooth() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new General_Helper(this).displayLocationSettingsRequest();
        }

//Setting up bluetooth:
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
// Device does not support Bluetooth
        } else {
//Enable bluetooth:
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

//Finding devices:
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView

                }
            }
        }
    }


    StringBuilder sb;//= new StringBuilder();
    String hRate = "";


    public RealTimeHealthData realHealthData, tempRealData;
    boolean isReleased = true;

    @Override
    public void onLiveData(final RealTimeHealthData realData) {
        super.onLiveData(realData);
        if (realData != null)
            if (isReleased) {
                realHealthData = realData;
                isReleased = false;

                if (tempRealData != null) {
                    if (!tempRealData.toString().equals(realData.toString())) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    hRate = realHealthData.getHeartRate() + "";

                                    float km = realHealthData.getTotalDistances() / 1000f;
                                    String distance = new DecimalFormat("##.##").format(km) + " km";
//                        Log.e("km", distance);
//                        Log.e("RealTimeData", realHealthData.toString());
                                    txt_active_time.setText(String.format(Locale.US, "%d", realHealthData.getTotalActiveTime()));
                                    txt_cal.setText(String.format(Locale.US, "%d", realHealthData.getTotalCalories()));
                                    txt_distance.setText(distance);
                                    txt_hrt_beat.setText(String.format(Locale.US, "%d", realHealthData.getHeartRate()));
                                    txt_step.setText(String.format(Locale.US, "%d", realHealthData.getTotalStep()));

                                    tempRealData = realHealthData;
                                    //25,
                                    //for the first time store in sp_helper
                                  /*  if (!currentHrtRate.equals("")) {
                                        sb.append(currentHrtRate + "_");
                                    }*/
                                    //79
                                    //62_72_73_73_75_75_75_75_76_76_
//                                    sb.append(currentHrtRate);
                                    if (realHealthData.getHeartRate() != 0) {
                                        heartBeatAnimation(false);
                                        if (currentHrtRate.contains("_")) {
                                            String splitting[] = currentHrtRate.split("_");
                                            for (int i = 0; i < splitting.length; i++) {
                                                if (hRate.equals(splitting[i])) {
                                                    Log.e("Existing_Value", "(" + i + ") " + hRate);
                                                    break;//value exist break the loop
                                                } else {
                                                    Log.e("sp_length", splitting.length + "");
                                                    if (splitting.length - 1 == i) {    //add at end
                                                        sb = new StringBuilder();
                                                        sb.append(currentHrtRate + "" + hRate + "_");
                                                        sp_helper.setHeartRate(sb.toString() + "");
                                                    }
                                                }
                                            }
                                        } else {
                                            //First Time Save HeartRate
                                            sp_helper.setHeartRate(hRate + "_");
                                        }
                                    } else {
                                        heartBeatAnimation(true);
                                    }
                                    isReleased = true;
                                    currentHrtRate = sp_helper.getHeartRate();
                                    Log.e("currentSP_HrtRate", currentHrtRate);

                                } catch (Exception e) {
                                    Log.e("RealTimeDataException", realHealthData.toString());
                                }
                            }
                        }, 5000);
                    } else {
                        //just display existing data to user (cause heart beat is 0 currently or data is older and not updated)
                        float km = realHealthData.getTotalDistances() / 1000f;
                        String distance = new DecimalFormat("##.##").format(km) + " km";
                        txt_active_time.setText(String.format(Locale.US, "%d", realHealthData.getTotalActiveTime()));
                        txt_cal.setText(String.format(Locale.US, "%d", realHealthData.getTotalCalories()));
                        txt_distance.setText(distance);
                        txt_hrt_beat.setText(String.format(Locale.US, "%d", realHealthData.getHeartRate()));
                        txt_step.setText(String.format(Locale.US, "%d", realHealthData.getTotalStep()));
                        isReleased = true;
                    }
                } else {
                    //firstTimeCall - if its null
                    isReleased = true;
                    tempRealData = realData;
                }
            }
    }

    @Override
    protected void onRestart() {
        onLiveData(realHealthData);
        //handler.postDelayed(runnable, 2000);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        setTitle(getResources().getString(R.string.app_name));
        ImagesAnimation();
        super.onResume();
    }

    @Override
    protected void onStop() {
        iv_hrt_beat.clearAnimation();
//        handler.removeCallbacks(runnable);
        super.onStop();
    }

    public void addListener() {
        btnAlarm.setOnClickListener(this);
        btnCallAndMsg.setOnClickListener(this);
        btnDeviceInfo.setOnClickListener(this);
        btnDisPlay.setOnClickListener(this);
        btnFindPhone.setOnClickListener(this);
        btnGoal.setOnClickListener(this);
        btnHeart.setOnClickListener(this);
        btnHeartInterval.setOnClickListener(this);
        btnHeartMode.setOnClickListener(this);
        btnLongSit.setOnClickListener(this);
        btnLost.setOnClickListener(this);
        btnMusic.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnSendLog.setOnClickListener(this);
        btnRestart.setOnClickListener(this);
        btnSleep.setOnClickListener(this);
        btnSport.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnSyncConfig.setOnClickListener(this);
        btnSyncHealth.setOnClickListener(this);
        btnUnDisturb.setOnClickListener(this);
        btnUnit.setOnClickListener(this);
        btnFuncInfos.setOnClickListener(this);
        btnUnBind.setOnClickListener(this);
        btnUpHand.setOnClickListener(this);
        btnUserInfo.setOnClickListener(this);
        btnWearMode.setOnClickListener(this);
        btnLiveData.setOnClickListener(this);
        btnSos.setOnClickListener(this);
        btnUnConnect.setOnClickListener(this);
        btnCommission.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
//        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    public static String bytesToHexString(byte src) {
        StringBuilder stringBuilder = new StringBuilder("");
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);

        return stringBuilder.toString();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.btn_sync_health:// Synchronize health data
                Intent syncHealthIntent = new Intent(HomeActivity.this, SyncHealthActivity.class);
                startActivity(syncHealthIntent);
                break;
            case R.id.btn_alarm://Alarm clock
                Intent alarmIntent = new Intent(HomeActivity.this, AlarmActivity.class);
                startActivity(alarmIntent);
                break;
            case R.id.btn_call_msg:// Telephone reminder and SMS reminder
                Intent msgCallIntent = new Intent(HomeActivity.this, CallAndMsgActivity.class);
                startActivity(msgCallIntent);
                break;
            case R.id.btn_device_info://Device Information
                Intent deviceIntent = new Intent(HomeActivity.this, DeviceInfoActivity.class);
                startActivity(deviceIntent);
                break;
            case R.id.btn_function_infos:// function list
                Intent funcIntent = new Intent(HomeActivity.this, FunctionInfosActivity.class);
                startActivity(funcIntent);
                break;
            case R.id.btn_display_mode:// Display mode
                Intent disPlayIntent = new Intent(HomeActivity.this, DisPlayModeActivity.class);
                startActivity(disPlayIntent);
                break;
            case R.id.btn_find_phone:// Looking for a cell phone
                Intent findPhoneIntent = new Intent(HomeActivity.this, FindPhoneActivity.class);
                startActivity(findPhoneIntent);
                break;
            case R.id.btn_goal:// Target setting
                Intent goalIntent = new Intent(HomeActivity.this, SetGoalActivity.class);
                startActivity(goalIntent);
                break;
            case R.id.btn_heart_data:// Heart rate data
                Intent heartRateIntent = new Intent(HomeActivity.this, HeartRateActivity.class);
                startActivity(heartRateIntent);
                break;
            case R.id.btn_sos:// 一Key help
                Intent intervalIntent = new Intent(HomeActivity.this, SosActivity.class);
                startActivity(intervalIntent);
                break;
            case R.id.btn_heart_Interval:// Heart rate interval
                Intent sosIntent = new Intent(HomeActivity.this, HeartRateIntervalActivity.class);
                startActivity(sosIntent);
                break;
            case R.id.btn_heart_mode://Heart rate pattern
                Intent hearRateModeIntent = new Intent(HomeActivity.this, HeartRateModeActivity.class);
                startActivity(hearRateModeIntent);
                break;
            case R.id.btn_live_data: // Real-time data
                Intent livedataIntent = new Intent(HomeActivity.this, LiveDataActivity.class);
                startActivity(livedataIntent);
                break;
            case R.id.btn_long_sit:// Sedentary reminder
                Intent longSitIntent = new Intent(HomeActivity.this, LongSitActivity.class);
                startActivity(longSitIntent);
                break;
            case R.id.btn_lost:// Anti-lost reminder
                Intent antiLostIntent = new Intent(HomeActivity.this, AntilostActivity.class);
                startActivity(antiLostIntent);
                break;
            case R.id.btn_music://Music reminder
                Intent musicIntent = new Intent(HomeActivity.this, MusicActivity.class);
                startActivity(musicIntent);
                break;
            case R.id.btn_photo:// Camera control
                Intent cameraIntent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(cameraIntent);
                break;
            case R.id.btn_test:// Test interface
                Intent intent = new Intent(HomeActivity.this, DemoTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sendlog:// Send log
                Intent logIntent = new Intent(HomeActivity.this, SendLogActivity.class);
                startActivity(logIntent);
                break;
            case R.id.btn_restart:// Reboot the device
                dialog.show();
                ProtocolUtils.getInstance().reStartDevice();
                break;
            case R.id.btn_unbind:// Unbundled
                dialog.show();
                // Unbonded when the device is not connected to the case only to delete the data and delete some of the binding state, reset the APP is not binding device status
                if (ProtocolUtils.getInstance().isAvailable() != ProtocolUtils.SUCCESS) {// To determine whether the device is connected
                    ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                    Calendar mCalendar1 = Calendar.getInstance();
                    int year = mCalendar1.get(Calendar.YEAR);
                    int month = mCalendar1.get(Calendar.MONTH);
                    int day = mCalendar1.get(Calendar.DAY_OF_MONTH);
                    ProtocolUtils.getInstance().enforceUnBind(new Date(year, month, day));// (Month from 0 to start in August, such as August 7)
                    //Intent intent1 =
                    startActivity(new Intent(HomeActivity.this, ScanDeviceActivity.class));

                } else {
                    // If the device is connected, use the following method to unbind
                    ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                    ProtocolUtils.getInstance().setUnBind();
                }
                break;
            case R.id.btn_sleep_data://Sleep data
                Intent sleepIntent = new Intent(HomeActivity.this, SleepActivity.class);
                startActivity(sleepIntent);
                break;
            case R.id.btn_sport_data:// Motion data
                Intent sportIntent = new Intent(HomeActivity.this, SprotActivity.class);
                startActivity(sportIntent);
                break;
            case R.id.btn_sync_config:// Synchronize configuration information
                dialog.setTitle("Synchronizing configuration information");
                dialog.show();
                ProtocolUtils.getInstance().StartSyncConfigInfo();
                break;
            case R.id.btn_undisturb_mode:// Do not disturb mode
                Intent disIntent = new Intent(HomeActivity.this, DoNotDisturbActivity.class);
                startActivity(disIntent);
                break;
            case R.id.btn_unit:// Unit setting
                Intent unitIntent = new Intent(HomeActivity.this, UnitActivity.class);
                startActivity(unitIntent);
                break;
            case R.id.btn_up_hand:// Lift the bowl to identify
                Intent upHandIntent = new Intent(HomeActivity.this, UpHandActivity.class);
                startActivity(upHandIntent);
                break;
            case R.id.btn_userinfo:// User Info
                Intent userInfoIntent = new Intent(HomeActivity.this, UserInfosActivity.class);
                startActivity(userInfoIntent);
                break;
            case R.id.btn_wear_mode:// Wear pattern
                Intent handmodeIntent = new Intent(HomeActivity.this, HandModeActivity.class);
                startActivity(handmodeIntent);
                break;
            case R.id.btn_unconnect:// Set disconnection
                ProtocolUtils.getInstance().setNewUnConnect();
                break;
            case R.id.btn_connect:// Set reconnection
                ProtocolUtils.getInstance().reConnect();
                break;
            case R.id.btn_commission:// custom made
                Intent commissionIntent = new Intent(HomeActivity.this, CommissionActivity.class);
                startActivity(commissionIntent);
                break;
            case R.id.btn_update:
                final View view = findViewById(R.id.btn_update);
                final UpdateModel updateModel = new UpdateModel(this);
                updateModel.setIUpdateListener(new UpdateModel.IUpdateListener() {
                    @Override
                    public void updateFaild() {
                        view.setEnabled(true);
                        DialogUtil.closeAlertDialog();
                        DialogUtil.showToast(HomeActivity.this, "Upgrade failed");
                    }

                    @Override
                    public void updateProgressBar(int progress) {
                        DialogUtil.updateWaitDialog("upgrading..." + Math.abs(progress) + "%");
                    }

                    @Override
                    public void updateSuccess() {
                        view.setEnabled(true);
                        DialogUtil.closeAlertDialog();
                        DialogUtil.showToast(HomeActivity.this, "update successed");
                    }

                    @Override
                    public void synchroData(int progress) {
                    }
                });
                DialogUtil.showWaitDialog(this, "upgrading...");
                updateModel.downAndUpdate();
                view.setEnabled(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSysEvt(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        DebugLog.d("HomeProtocolEvt=" + arg1);

        if (arg1 == ProtocolEvt.SYNC_EVT_CONFIG_SYNC_COMPLETE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Synchronization configuration information is successful", Toast.LENGTH_LONG).show();
                }
            }, 200);
        } else if (arg1 == ProtocolEvt.REBOOT_CMD.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Reboot successful", Toast.LENGTH_LONG).show();
                }
            });
        } else if (arg1 == ProtocolEvt.BIND_CMD_REMOVE.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Unlocking success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(HomeActivity.this, ScanDeviceActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else if (arg1 == ProtocolEvt.BLE_TO_APP_FIND_PHONE_START.toIndex()) {
            // Received the bracelet sent to find the phone command, the client received this command to do the appropriate treatment, such as the phone ringing and other operations
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    DebugLog.d("Receive a command to find a cell phone");
                    Toast.makeText(HomeActivity.this, "Receive a command to find a cell phone", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBLEConnectTimeOut() {
        // TODO Auto-generated method stub
        layout_grid.setEnabled(false);
        rl_disable_hover.setVisibility(View.VISIBLE);
        hoverFadeAnim(rl_disable_hover, true);
        txt_connection_status.setText("status : Connection timed out");
//        setTitle("Connection timed out");

    }

    @Override
    public void onBLEConnected(BluetoothGatt arg0) {
        // TODO Auto-generated method stub
        layout_grid.setEnabled(true);
        rl_disable_hover.setVisibility(View.GONE);
        hoverFadeAnim(rl_disable_hover, false);
        txt_connection_status.setText("status : connected");
//        setTitle("connected");
    }

    @Override
    public void onBLEConnecting(String arg0) {
        // TODO Auto-generated method stub
        layout_grid.setEnabled(false);
        rl_disable_hover.setVisibility(View.VISIBLE);
        hoverFadeAnim(rl_disable_hover, true);
        txt_connection_status.setText("status : connecting ...");
        //   setTitle("connecting");
    }

    @Override
    public void onBLEDisConnected(String arg0) {
        // TODO Auto-generated method stub
        layout_grid.setEnabled(false);
        rl_disable_hover.setVisibility(View.VISIBLE);
        hoverFadeAnim(rl_disable_hover, true);
        txt_connection_status.setText("status : The line is disconnected");
//        setTitle("The line is disconnected");
    }


    private class ActionAdapter extends BaseAdapter {

        Context context;
        ArrayList<ActionModel> actionList;
        LayoutInflater inflater;

        public ActionAdapter(Context context, ArrayList<ActionModel> actionList) {
            this.context = context;
            this.actionList = actionList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //dialog = new BufferDialog(context);
        }

        @Override
        public int getCount() {
            return actionList.size();
        }

        @Override
        public Object getItem(int position) {
            return actionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.layout_ble_action, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            createAction(holder, actionList.get(position));
            return view;
        }

        private void createAction(ViewHolder holder, final ActionModel am) {
            holder.txt_action_text.setText(am.getActionName());

        /*int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        holder.ll_circular.setBackgroundColor(randomAndroidColor);
        holder.ll_circular.setBackgroundResource(R.drawable.circle_action);

        GradientDrawable drawable = (GradientDrawable) holder.ll_circular.getBackground();
        drawable.setColor(randomAndroidColor);*/
        /*
         actinList.add(new ActionModel("Unbind", 1));
        actinList.add(new ActionModel("Disconnect", 2));
        actinList.add(new ActionModel("Re-Connect", 3));
        actinList.add(new ActionModel("Display-Mode", 4));
        actinList.add(new ActionModel("Reboot", 4));
        actinList.add(new ActionModel("BLE device Info", 5));
        actinList.add(new ActionModel("Alarm", 6));
        actinList.add(new ActionModel("Music Control", 7));
        actinList.add(new ActionModel("Camera Control", 8));
        actinList.add(new ActionModel("Call Control", 9));
        actinList.add(new ActionModel("UserInfo", 10));
        actinList.add(new ActionModel("SendLog Info", 11));
        actinList.add(new ActionModel("Sync Health Info", 12));
        actinList.add(new ActionModel("Call Reminders", 13));
        actinList.add(new ActionModel("Anti Lost Reminders", 14));
        actinList.add(new ActionModel("Emergency Help", 15));
        actinList.add(new ActionModel("Looking for a CellPhone", 16));
        actinList.add(new ActionModel("Do Not Disturb mode", 17));
        */

            holder.ll_circular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (am.getActionNumber()) {
                        case BLE_ACTION.ACTION_UNBIND:
                            dialog.show();
                            // Unbonded when the device is not connected to the case only to delete the data and delete some of the binding state, reset the APP is not binding device status
                            if (ProtocolUtils.getInstance().isAvailable() != ProtocolUtils.SUCCESS) {// To determine whether the device is connected
                                ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                                Calendar mCalendar1 = Calendar.getInstance();
                                int year = mCalendar1.get(Calendar.YEAR);
                                int month = mCalendar1.get(Calendar.MONTH);
                                int day = mCalendar1.get(Calendar.DAY_OF_MONTH);
                                ProtocolUtils.getInstance().enforceUnBind(new Date(year, month, day));// (Month from 0 to start in August, such as August 7)
                                //Intent intent1 =
                                startActivity(new Intent(HomeActivity.this, ScanDeviceActivity.class));

                            } else {
                                // If the device is connected, use the following method to unbind
                                ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                                ProtocolUtils.getInstance().setUnBind();
                            }
                            break;
                        case BLE_ACTION.ACTION_DISCONNECT:
                            // Set disconnection
                            ProtocolUtils.getInstance().setNewUnConnect();
                            break;
                        case BLE_ACTION.ACTION_RECONNECT:
                            // Set reconnection
                            ProtocolUtils.getInstance().reConnect();
                            break;
                        case BLE_ACTION.ACTION_DISPLAYMODE:
                            startActivity(new Intent(HomeActivity.this, DisPlayModeActivity.class));
//                            startActivity(disPlayIntent);
                            break;
                        case BLE_ACTION.ACTION_REBOOT:
                            dialog.show();
                            ProtocolUtils.getInstance().reStartDevice();
                            break;
                        case BLE_ACTION.ACTION_DEVICE_INFO:
                            startActivity(new Intent(HomeActivity.this, DeviceInfoActivity.class));
                            break;
                        case BLE_ACTION.ACTION_ALARM:
                            startActivity(new Intent(HomeActivity.this, AlarmActivity.class));
                            break;
                        case BLE_ACTION.ACTION_MUSIC:
                            startActivity(new Intent(HomeActivity.this, MusicActivity.class));
                            break;
                        case BLE_ACTION.ACTION_CAMERA:
                            startActivity(new Intent(HomeActivity.this, CameraActivity.class));
                            break;
                        case BLE_ACTION.ACTION_USER_INFO:
                            startActivity(new Intent(HomeActivity.this, UserInfosActivity.class));
                            break;
                        case BLE_ACTION.ACTION_SEND_LOG:
                            startActivity(new Intent(HomeActivity.this, SendLogActivity.class));
                            break;
                        case BLE_ACTION.ACTION_SYNC_HEALTH_INFO:
                            startActivity(new Intent(HomeActivity.this, SyncHealthActivity.class));
                            break;
                        case BLE_ACTION.ACTION_CALL_REMINDERS:
                            startActivity(new Intent(HomeActivity.this, CallAndMsgActivity.class));
                            break;
                        case BLE_ACTION.ACTION_ANTI_LOST_REMINDERS:
                            startActivity(new Intent(HomeActivity.this, AntilostActivity.class));
                            break;
                        case BLE_ACTION.ACTION_EMERGENCY_HELP:
                            startActivity(new Intent(HomeActivity.this, SosActivity.class));
                            break;
                        case BLE_ACTION.ACTION_LOOKING_CELLPHONE:
                            startActivity(new Intent(HomeActivity.this, FindPhoneActivity.class));
                            break;
                        case BLE_ACTION.ACTION_DO_NOT_DISTURB_MODE:
                            startActivity(new Intent(HomeActivity.this, DoNotDisturbActivity.class));
                            break;
                    }
                }
            });

        }

        private class ViewHolder {
            TextView txt_action_text;
            LinearLayout ll_circular;

            public ViewHolder(View view) {
                ll_circular = (LinearLayout) view.findViewById(R.id.ll_circular);
                txt_action_text = (TextView) view.findViewById(R.id.txt_action_text);
            }
        }
    }


}


/*
 *
 * stand Alone Service

  Calendar cur_cal = new GregorianCalendar();
cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

Calendar cal = new GregorianCalendar();
cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
cal.set(Calendar.HOUR_OF_DAY, 18);
cal.set(Calendar.MINUTE, 32);
cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
Intent intent = new Intent(ProfileList.this, IntentBroadcastedReceiver.class);
PendingIntent pintent = PendingIntent.getService(ProfileList.this, 0, intent, 0);
AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);
 */
