package com.cws.bleproject.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dhiren khatik on 01-08-2017.
 */

public class SP_Helper {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Context context;

    @SuppressLint("CommitPrefEdits")
    public SP_Helper(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("BLE_sharedPref", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public String getHeartRate() {
        return sp.getString("hrt_rte", "");
    }

    public void setHeartRate(String hrtRte) {
        editor.putString("hrt_rte", hrtRte);
        editor.apply();
    }

}
