package com.cws.bleproject.model;

import java.util.List;

public class DeviceUpdateList {

	public List<DeviceUpdateInfo> firmwareInfo;
	
	
	public DeviceUpdateInfo getMyDevice(int deviceId){
		for (DeviceUpdateInfo info : firmwareInfo) {
			if(info.device_id == deviceId){
				return info;
			}
		}
		return null;
	}
	
	
}
