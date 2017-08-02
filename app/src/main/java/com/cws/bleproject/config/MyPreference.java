package com.cws.bleproject.config;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {
	private static final String SAVE_NAME = "UserSaveName";
	private static MyPreference instance = null;
	private Context mContext = null;
	private static SharedPreferences settings = null;
	private static SharedPreferences.Editor editor = null;

	public static final String isFirst = "ISFIRST";//
	public static final String isFirstSync = "ISFIRSTSYNC";//Whether the first synchronization

	

	private MyPreference(Context context) {
		mContext = context;
	}
	
	public void clear(){
		editor.clear();
	}

	public static MyPreference getInstance(Context context) {
		if (instance == null) {
			instance = new MyPreference(context);
			settings = context.getSharedPreferences(SAVE_NAME, 0);
			editor = settings.edit();
			editor.apply();
		}
		return instance;
	}
    public void setIsFirst(Boolean value) {
    	editor.putBoolean(isFirst, value);
    	editor.commit();
    }
    
    public boolean getIsFirst() {
    	return settings.getBoolean(isFirst, false);
    }
    public void setIsFirstSync(Boolean value) {
    	editor.putBoolean(isFirstSync, value);
    	editor.commit();
    }

    public boolean getIsFirstSync() {
    	return settings.getBoolean(isFirstSync, true);
    }

  
}
