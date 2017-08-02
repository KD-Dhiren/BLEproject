package com.cws.bleproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.google.gson.Gson;
import com.veryfit.multi.ble.CommissionCallBack;
import com.veryfit.multi.entity.IBeaconGetHead;
import com.veryfit.multi.entity.IBeaconGetUUID;
import com.veryfit.multi.entity.IBeaconRetCode;
import com.veryfit.multi.entity.IBeaconWriteHead;
import com.veryfit.multi.entity.IBeaconWritePassWord;
import com.veryfit.multi.entity.IBeaconWriteUUID;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;
import com.veryfit.multi.share.BleSharedPreferences;
import com.veryfit.multi.util.ByteDataConvertUtil;

public class CommissionActivity extends BaseActivity implements OnClickListener, CommissionCallBack {
    private TextView tvData;
    private Gson gson = new Gson();
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission);
        ProtocolUtils.getInstance().setCommissionCallBack(this);
        initView();
    }

    public void initView() {
        findViewById(R.id.btn_get_head).setOnClickListener(this);
        findViewById(R.id.btn_get_uuid).setOnClickListener(this);
        findViewById(R.id.btn_write_head).setOnClickListener(this);
        findViewById(R.id.btn_write_passwor).setOnClickListener(this);
        findViewById(R.id.btn_write_uuid).setOnClickListener(this);
        tvData = (TextView) findViewById(R.id.tv_data);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.btn_get_head:
                tvData.setText("");
                ProtocolUtils.getInstance().getHead();
                break;
            case R.id.btn_get_uuid:
                tvData.setText("");
                ProtocolUtils.getInstance().getUuid();
                break;
            case R.id.btn_write_head:
                tvData.setText("");
                //004D53
                int[] headData = new int[9];
                headData[0] = ((byte) Integer.parseInt("00", 16)) & 0xFF;
                headData[1] = ((byte) Integer.parseInt("4D", 16)) & 0xFF;
                headData[2] = ((byte) Integer.parseInt("53", 16)) & 0xFF;
                headData[3] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                headData[4] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                headData[5] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                headData[6] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                headData[7] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                headData[8] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;

                IBeaconWriteHead head = new IBeaconWriteHead(false, false, false, headData, 0, 0);
                ProtocolUtils.getInstance().writeHead(head);
                break;
            case R.id.btn_write_passwor:
                tvData.setText("");
                int[] data = new int[8];
                data[0] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                data[1] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                byte[] b = ByteDataConvertUtil.getMacBytes(BleSharedPreferences.getInstance().getBindDeviceAddr());
                data[2] = b[0] & 0xFF;
                data[3] = b[1] & 0xFF;
                data[4] = b[2] & 0xFF;
                data[5] = b[3] & 0xFF;
                data[6] = b[4] & 0xFF;
                data[7] = b[5] & 0xFF;
                IBeaconWritePassWord passWord = new IBeaconWritePassWord(data);
                ProtocolUtils.getInstance().writePassWord(passWord);
                break;
            case R.id.btn_write_uuid:
                tvData.setText("");
                int[] uuidData = new int[16];
                for (int i = 0; i < uuidData.length; i++) {
                    uuidData[i] = ByteDataConvertUtil.Int2Byte(0) & 0xFF;
                }
                ProtocolUtils.getInstance().writeUuid(new IBeaconWriteUUID(uuidData));
                break;

            default:
                break;
        }
    }

    @Override
    public void onGetHead(final IBeaconGetHead arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tvData.setText("onGetHead=" + gson.toJson(arg0));
            }
        });
    }

    @Override
    public void onGetUuid(final IBeaconGetUUID arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tvData.setText("onGetUuid=" + gson.toJson(arg0));
            }
        });
    }

    @Override
    public void onWriteHead(final IBeaconRetCode arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tvData.setText("onWriteHead=" + gson.toJson(arg0));
            }
        });
    }

    @Override
    public void onWritePassWord(final IBeaconRetCode arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tvData.setText("onWritePassWord=" + gson.toJson(arg0));
            }
        });
    }

    @Override
    public void onWriteUuid(final IBeaconRetCode arg0) {
        // TODO Auto-generated method stub
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tvData.setText("onWriteUuid=" + gson.toJson(arg0));
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ProtocolUtils.getInstance().clearCommissionCallBack();
    }

}
