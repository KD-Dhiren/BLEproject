package com.cws.bleproject.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cws.bleproject.R;
import com.veryfit.multi.share.BleSharedPreferences;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new SP_Helper(this).setHeartRate("");
        methodRequestPermission();
    }

    @AfterPermissionGranted(25)
    private void methodRequestPermission() {
        //new General_Helper(this).settingPermission(this);

        String[] perms = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.GET_TASKS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        // Do not have permissions, request them now
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            if (BleSharedPreferences.getInstance().getIsBind()) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, ScanDeviceActivity.class));
            }
            finish();
        } else
            try {
                EasyPermissions.requestPermissions(this, getResources().getString(R.string.app_permission_denied_dialog_message), R.string.posButton, R.string.negButton, 25, perms);
            } catch (Exception e) {
                Log.e("requesting exception", "" + e.getCause() + "" + e.getMessage());
            }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() >= 6) {
            if (BleSharedPreferences.getInstance().getIsBind()) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, ScanDeviceActivity.class));
            }
            finish();
        }
        Log.e("perms granted", String.valueOf(perms));
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e("perms denied", String.valueOf(perms));
        methodRequestPermission();
        //Toast.makeText(this, "Please enable permission to access features", Toast.LENGTH_SHORT).show();

    }
}
