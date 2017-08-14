package com.cws.bleproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.bleproject.Helper.General_Helper;
import com.cws.bleproject.R;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativeprotocol.ProtocolEvt;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.util.DebugLog;

public class CameraActivity extends BaseActivity {

    LinearLayout ll_switch_mode;
    boolean mode = false;
    private Switch switchCamera;
    private TextView tvReceive;
    private Handler handler = new Handler();
    private BufferDialog dialog;
    private StringBuffer receiveSb = new StringBuffer();
    private boolean isScreenCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        setupActionBar();
        addListener();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        dialog = new BufferDialog(this);
        switchCamera = (Switch) findViewById(R.id.switch_camera);
        tvReceive = (TextView) findViewById(R.id.tv_receive);
        ll_switch_mode = (LinearLayout) findViewById(R.id.ll_switch_mode);
    }


    @Override
    public void addListener() {
        // TODO Auto-generated method stub
        super.addListener();
        switchCamera.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean checked) {
                if (checked) {
                    dialog.show();
                    ProtocolUtils.getInstance().startPhoto();//Into the camera mode
                } else {
                    dialog.show();
                    receiveSb.delete(0, receiveSb.length());//Empty intervalList
                    ProtocolUtils.getInstance().stopPhoto();//Exit the camera mode
                }
            }
        });

        ll_switch_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mode) {
                    mode = false;
                    switchCamera.setChecked(false);
                } else {
                    mode = true;
                    switchCamera.setChecked(true);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        dialog.dismiss();
        super.onPause();
    }

    @Override
    public void onSysEvt(int arg0, int actions, int arg2, int arg3) {
        // TODO Auto-generated method stub
        super.onSysEvt(arg0, actions, arg2, arg3);
        DebugLog.d("type=" + actions);
        Log.e("actionType", "" + actions);
        if (actions == ProtocolEvt.APP_TO_BLE_PHOTO_START.toIndex() && arg2 == ProtocolEvt.SUCCESS) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                    Log.e("Camera", "APP_TO_BLE_PHOTO_START");
                    General_Helper.custom_WhiteToast(CameraActivity.this, "Enter the camera mode successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
//                    Toast.makeText(, Toast.LENGTH_LONG).show();
                }
            });
        } else if (actions == ProtocolEvt.BLE_TO_APP_PHOTO_SINGLE_SHOT.toIndex()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("Camera", "BLE_TO_APP_PHOTO_SINGLE_SHOT");
                    //Perform a camera callback callback
                    if (isScreenCamera) {
                        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
//                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, 1);
                        isScreenCamera = false;
                    }
//                    receiveSb.append("Received a single shot command, the developer can handle the callback logic in this callback" + "\n\n");
//                    tvReceive.setText(receiveSb.toString());
                }
            });
        } else if (actions == ProtocolEvt.BLE_TO_APP_PHOTO_BURES.toIndex()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("Camera", "BLE_TO_APP_PHOTO_BURES");
                    receiveSb.append("Received a burst command, the developer can handle the camera in this callback logic" + "\n\n");
                    tvReceive.setText(receiveSb.toString());
                }
            });
        } else if (actions == ProtocolEvt.APP_TO_BLE_PHOTO_STOP.toIndex()) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    General_Helper.custom_WhiteToast(CameraActivity.this, "Exit the camera mode successfully", true, R.drawable.custom_toast_green_bg, Toast.LENGTH_SHORT);
//                    Toast.makeText(, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        isScreenCamera = true;
        super.onResume();
    }


    private void setupActionBar() {
        setTitle("Camera Control");
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
